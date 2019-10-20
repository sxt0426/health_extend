package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * 套餐接口
 */
public interface SetmealService {

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 获取所有套餐列表
     * @return
     */
    List<Setmeal> findAll();

    /**
     * 根据套餐id，查询套餐信息
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

    /**
     * 统计套餐信息
     * @return
     */
    List<Map<String,Object>> getSetmealReport();

    List getSexSetmealReport();

    List getAgeSetmealReport();
}