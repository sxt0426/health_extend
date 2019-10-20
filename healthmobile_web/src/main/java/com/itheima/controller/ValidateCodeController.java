package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.common.constant.MessageConstant;
import com.itheima.common.constant.RedisMessageConstant;
import com.itheima.common.utils.SMSUtils;
import com.itheima.common.utils.ValidateCodeUtils;
import com.itheima.entity.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * 短信
 */
@RestController
@RequestMapping("validateCode")
public class ValidateCodeController {

    private static final Logger log = Logger.getLogger(ValidateCodeController.class);

    @Autowired
    private JedisPool jedisPool;

    /**
     * 发送预约短信验证码
     *
     * @param telephone
     * @return
     */
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        //1.判断手机号 在5分钟内是否已经发送过，验证码
        //如果已经发送验证码 告知用户已经发送过了 稍后再试
        //key=业务逻辑编号:手机号
        String codeInRedis = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_ORDER + ":" + telephone);
        if (codeInRedis != null) {
            //在5分钟内已经发送过了
            return Result.error("已经发送过验证码，请注意查收");
        }

        //2.生成验证码
        int code = ValidateCodeUtils.generateValidateCode(4);

        //3.通过aliyun短信服务发送短信
        try {
            int i = 1;
           // SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code + "");
        } catch (Exception e) {
            log.error("Aliyun send msg error.", e);
            return Result.error(MessageConstant.SEND_VALIDATECODE_FAIL);
        }

        //4.发送成功保存到redis中， 在用户提交预约信息的时候，验证用户请求的合法性
        //setex 向redis中，保存数据，并设置 数据失效时间
        //第一个参数 key=业务编号：手机号
        //第二个参数 数据失效时间 单位：秒
        //第三个参数 数据
        jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_ORDER + ":" + telephone, 5 * 60, code + "");

        return Result.success(MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    /**
     * 获取验证码
     * @param telephone
     * @return
     */
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone) {
        //1.校验验证码 在规定时间内 是否已经发送过
        //如果发送过了 提示用户已经发送过了
        String codeInRedis = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_LOGIN + ":" + telephone);

        if (codeInRedis != null) {//验证码已发送过了
            return Result.error("验证码已发送，请注意查收");
        }

        //2.生成验证码
        Integer code = ValidateCodeUtils.generateValidateCode(4);

        //3.通过阿里云 短信服务发送验证码
        try {
            //SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code + "");
        } catch (Exception e) {
            log.error("Send msg by aliyun error.", e);
            return Result.error(MessageConstant.SEND_VALIDATECODE_FAIL);
        }

        //4.发送成功过 将验证码 存入redis中，在提交登录信息的时候 校验用户的请求是否合法
        //key = 业务编号:手机号
        jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_LOGIN + ":" + telephone, 5 * 60, code + "");

        return Result.success(MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}