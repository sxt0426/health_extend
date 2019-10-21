package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.Result;

import java.util.Map; /**
 * 预约接口
 */
public interface OrderService {

    /**
     * 提交预约信息
     * @param map
     * @return
     */
    Result submit(Map map) throws Exception;

    /**
     * 根据id，查询预约信息
     * @param id
     * @return
     */
    Map findById(Integer id);

    //分页
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);
}