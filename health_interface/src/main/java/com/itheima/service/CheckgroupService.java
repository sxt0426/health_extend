package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;

import java.util.List;

/**
 * 检查组接口
 */
public interface CheckgroupService {

    /**
     * 新增检查组
     * @param checkGroup
     * @param checkitemIds
     */
    void add(CheckGroup checkGroup,Integer [] checkitemIds);

    /**
     * 分页查询接口
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 根据id，查询检查组信息
     * @param id
     * @return
     */
    CheckGroup findById(Integer id);

    /**
     * 根据检查组id，查询所有关联的检查项ids
     * @param id
     * @return
     */
    List<Integer> findCheckitemIds(Integer id);

    /**
     * 编辑检查组
     * @param checkGroup
     * @param checkitemids
     */
    void edit(CheckGroup checkGroup, Integer[] checkitemids);

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();
}