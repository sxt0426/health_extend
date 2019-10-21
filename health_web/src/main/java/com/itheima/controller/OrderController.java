package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @version V1.0
 * @author: Administrator
 * @date: 2019/10/21 0021 上午 10:06
 * @description:
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private static final Logger log = Logger.getLogger(OrderController.class);

    @Reference
    private OrderService orderService;

    /**
     * 分页查询
     * @param pageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean pageBean){
        try {
            return orderService.findPage(pageBean.getCurrentPage(),pageBean.getPageSize(),pageBean.getQueryString());
        } catch (Exception e) {
            log.error("Find page error.",e);
        }
        return null;
    }
}
