package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map; /**
 * 套餐数据库操作
 */
public interface SetmealDao {
    /**
     * 添加套餐
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 设置检查组与套餐关联关系
     * @param map
     */
    void setSetmealAndCheckgroup(Map<String, Integer> map);

    Page<Setmeal> queryByCondition(String queryString);

    /**
     * 查询所有套餐
     * @return
     */
    List<Setmeal> findAll();

    /**
     * 根据套餐id，查询套餐信息
     * @param id
     * @return
     */
    Setmeal findById(Integer id);
}