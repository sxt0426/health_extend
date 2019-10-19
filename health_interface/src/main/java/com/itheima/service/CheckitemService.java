package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * 检查项接口
 */
public interface CheckitemService {
    /**
     * 新增检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 删除检查项
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据检查项id查询检查项信息
     * @param id
     * @return
     */
    CheckItem findById(Integer id);

    /**
     * 编辑检查项
     * @param checkItem
     */
    void edit(CheckItem checkItem);

    /**
     * 查询所有检查项
     * @return
     */
    List<CheckItem> findAll();
}