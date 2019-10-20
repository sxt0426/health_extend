package com.itheima.dao;

/**
 * @author john
 * @version v1.0
 * @date 2019/10/20 15:23
 * @description
 */
public interface JobDao {
    /**
     * 删除本月一号之前的预约数据
     * @param firstDay4ThisMonth
     */
    void deleteOrderSettingSchedule(String firstDay4ThisMonth);
}
