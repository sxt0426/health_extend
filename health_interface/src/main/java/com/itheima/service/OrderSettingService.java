package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * 预约设置接口
 */
public interface OrderSettingService {

    /**
     * 新增预约设置
     * @param orderSettings
     */
    void add(List<OrderSetting> orderSettings);

    /**
     *
     * @param date
     */
    List<Map> findByMonth(String date);

    /**
     * 根据日期进行 预约人数设置
     * @param orderSetting
     */
    void settingByDay(OrderSetting orderSetting);
}