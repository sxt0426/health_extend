package com.itheima.dao;

import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * 检查项数据库操作
 */
public interface CheckitemDao {

    /**
     * 新增检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    List<CheckItem> selectByCondition(String queryString);

    /**
     * 查询检查项是否关联检查组
     * @param id
     * @return
     */
    Long findCountByCheckitemId(Integer id);

    /**
     * 删除检查项
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据检查项id，查询检查项信息
     * @param id
     * @return
     */
    CheckItem findById(Integer id);

    /**
     * 编辑检查项信息
     * @param checkItem
     */
    void edit(CheckItem checkItem);

    /**
     * 查询所有检查项
     * @return
     */
    List<CheckItem> findAll();
}