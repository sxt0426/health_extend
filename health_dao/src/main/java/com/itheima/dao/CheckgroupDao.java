package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;

import java.util.List;
import java.util.Map; /**
 * 检查组数据库操作
 */
public interface CheckgroupDao {
    /**
     * 新增检查组基本信息
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 设置检查组关联信息
     * @param map
     */
    void setCheckgroupAndCheckitem(Map<String, Integer> map);

    Page<CheckGroup> queryByCondition(String queryString);

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
     * 编辑
     * @param checkGroup
     */
    void edit(CheckGroup checkGroup);

    /**
     * 删除检查组与检查项关联信息
     * @param id
     */
    void deleteAssocation(Integer id);

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();
}