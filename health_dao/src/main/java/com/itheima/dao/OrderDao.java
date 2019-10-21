package com.itheima.dao;

import com.itheima.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * 预约数据库操作
 */
public interface OrderDao {
    /**
     * 查询预约信息
     * @param orderQueryParam
     * @return
     */
    List<Order> findByCondition(Order orderQueryParam);

    /**
     * 新增预约信息
     * @param order
     */
    void add(Order order);

    /**
     * 根据id，查询预约信息
     * @param id
     * @return
     */
    Map findById(Integer id);

    /**
     * 统计所有卖出的套餐
     * @return
     */
    List<Map<String,Object>> getSetmealReport();

    Integer findCountByDate(String todayStr);

    Integer findByCountAfterDate(String monDateStr);

    /**
     * 某个日期有多少人做了体检
     * @param todayStr
     * @return
     */
    Integer findVisitCountByDate(String todayStr);

    /**
     * 某个日期之后 体检了多少人
     * @param monDateStr
     * @return
     */
    Integer findVisitCountByAfterDate(String monDateStr);

    List<Map<String,Object>> hotSetmeal();

    //分页
    List<Order> selectByCondition();
}