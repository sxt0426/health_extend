package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约设置业务实现
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 新增
     *
     * @param orderSettings
     */
    @Override
    public void add(List<OrderSetting> orderSettings) {

        if (orderSettings == null || orderSettings.size() == 0) {
            return;
        }

        for (OrderSetting orderSetting : orderSettings) {
            //1.判断数据库中 某个 日期 是否已经设置了
            long count = orderSettingDao.findSettingCountByDate(orderSetting.getOrderDate());

            if (count > 0) {
                //2.如果已经设置 修改数据
                orderSettingDao.updateNumberByDate(orderSetting);
            } else {
                //3.如果没有设置 新增数据
                orderSettingDao.add(orderSetting);
            }
        }
    }

    /**
     * @param date yyyy-MM 2019-10
     * @return 页面需要的数据结构
     * { date: 1, number: 120, reservations: 1 },
     * { date: 3, number: 120, reservations: 1 },
     * { date: 4, number: 120, reservations: 120 },
     * { date: 6, number: 120, reservations: 1 },
     * { date: 8, number: 120, reservations: 1 }
     */
    @Override
    public List<Map> findByMonth(String date) {
        //1.封装查询数据
        //2019-10-1
        String startDate = date + "-1";
        //2019-10-31
        String endDate = date + "-31";

        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("startDate", startDate);
        queryParam.put("endDate", endDate);

        //2.查询预约数据
        List<OrderSetting> settings = orderSettingDao.findByMonth(queryParam);

        //3.封装页面需要的数据结构
        List<Map> result = new ArrayList<>();

        if (settings != null && settings.size() > 0) {
            for (OrderSetting setting : settings) {
                Map<String, Integer> item = new HashMap<>();
                item.put("date", setting.getOrderDate().getDate());
                item.put("number", setting.getNumber());
                item.put("reservations", setting.getReservations());

                result.add(item);
            }
        }

        return result;
    }

    /**
     * 根据日期进行 预约人数设置
     *
     * @param orderSetting
     */
    @Override
    public void settingByDay(OrderSetting orderSetting) {
        //1.数据 预约日期和预约人数

        long count = orderSettingDao.findSettingCountByDate(orderSetting.getOrderDate());

        if (count > 0) {
            //count >0 表示 已经设置了预约。更新已设置的数据
            orderSettingDao.updateNumberByDate(orderSetting);
        } else {
            //count =0 表示 没有设置预约。新增一条数据数据
            orderSettingDao.add(orderSetting);
        }

    }
}