package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.common.constant.MessageConstant;
import com.itheima.common.constant.RedisMessageConstant;
import com.itheima.common.utils.SMSUtils;
import com.itheima.common.utils.ValidateCodeUtils;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 登录
 */
@RestController
public class LoginController {

    private static final Logger log = Logger.getLogger(LoginController.class);

    @Reference
    MemberService memberService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 登录
     *
     * @param response
     * @param map
     * @return
     */
    @RequestMapping("/login")
    public Result login(HttpServletResponse response, @RequestBody Map map) {
        //1.校验请求的合法性 校验验证码是否正确
        String telephone = (String) map.get("telephone");
        String code = (String) map.get("validateCode");

        String codeInRedis = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_LOGIN + ":" + telephone);

        if (codeInRedis == null || !(codeInRedis.equals(code))) { //codeInRedis==null 1.没有发送过验证码 2.数据失效了
            //验证不通过 提示用户
            return Result.error(MessageConstant.VALIDATECODE_ERROR);
        }

        //2.验证手机号是否已经注册过会员
        //如果没有注册会员 注册新会员
        Member member = memberService.findByTelephone(telephone);

        if (member == null) {//没有注册会员
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());

            memberService.add(member);
        }

        //3.将用户登录信息写入cookie，下次使用我们系统的时候，就不需要 再次登录
        Cookie cookie = new Cookie("member_telephone", telephone);
        cookie.setMaxAge(60 * 60 * 24 * 30); //过期时间 到了过期时间我们设置的member_telephone值将会清除掉
        cookie.setPath("/user/");

        response.addCookie(cookie);//将cookie写入到 用户手机浏览器或者pc浏览器

        return Result.success(MessageConstant.LOGIN_SUCCESS);
    }
}