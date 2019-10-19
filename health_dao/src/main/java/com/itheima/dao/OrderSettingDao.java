package com.itheima.dao;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map; /**
 * 预约设置数据库操作
 */
public interface OrderSettingDao {
    /**
     * 根据某个日期查询 是否已经设置
     * @param orderDate
     * @return
     */
    long findSettingCountByDate(Date orderDate);

    /**
     * 根据预约日期更新可预约人数
     * @param orderSetting
     */
    void updateNumberByDate(OrderSetting orderSetting);

    /**
     * 新增预约设置
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 根据日期查询 某一个月 所有的设置信息
     * @param queryParam
     * @return
     */
    List<OrderSetting> findByMonth(Map<String, String> queryParam);

    /**
     * 根据预约日期查询 是否进行了预约设置
     * @param date
     * @return
     */
    OrderSetting findOrderSettingByOrderDate(Date date);

    /**
     * 根据日期 更新已预约人数
     * @param orderSetting
     */
    void updateReservationsByDate(OrderSetting orderSetting);
}