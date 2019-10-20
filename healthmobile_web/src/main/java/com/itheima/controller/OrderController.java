package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.common.constant.MessageConstant;
import com.itheima.common.constant.RedisMessageConstant;
import com.itheima.common.utils.SMSUtils;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * 预约
 */
@RestController
@RequestMapping("order")
public class OrderController {

    private static final Logger log = Logger.getLogger(OrderController.class);

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    /**
     * 提交预约信息
     *
     * @param map
     * @return
     */
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map) throws Exception {
        //1.验证请求的合法性 将用户提交过来的 验证码 与 redis中保存的验证码进行比对

        String telephone = (String) map.get("telephone");
        String code = (String) map.get("validateCode");

        //取出用户保存在redis中的验证码
        String codeInRedis = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_ORDER + ":" + telephone);

        if (codeInRedis == null || !(codeInRedis.equals(code))) {
            //codeInRedis == null 1.没有发送过验证码；2.验证码失效了
            //验证码不一致
            //提示用户验证码不正确
            return Result.error(MessageConstant.VALIDATECODE_ERROR);
        }
        //2.将数据提交到业务处理层
        map.put("orderType", Order.ORDERTYPE_WEIXIN);
        Result result = orderService.submit(map);

        if (result.isFlag()) {
            //3.提示用户(发送短信) 预约成功
            //SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE, telephone, "6666");
            int u = 1;
        }

        return result;
    }

    /**
     * 根据id，查询预约信息
     *
     * @param id
     * @return
     */
    @RequestMapping("findById")
    public Result findById(Integer id) {
        try {
            Map map = orderService.findById(id);

            return Result.success(MessageConstant.QUERY_ORDER_SUCCESS, map);
        } catch (Exception e) {
            log.error("Find order by id error.", e);
        }

        return Result.error(MessageConstant.QUERY_ORDER_FAIL);
    }
}