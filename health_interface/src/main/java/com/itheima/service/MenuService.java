package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;

import java.util.List;

/**
 * @version V1.0
 * @author: Administrator
 * @date: 2019/10/20 0020 上午 10:00
 * @description:菜单栏接口
 */
public interface MenuService {
    /**
     * 添加菜单信息
     * @param menu
     */
    void add(Menu menu);


    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 根据id查找菜单
     * @param id
     * @return
     */
    Menu findById(Integer id);

    /**
     *  编辑菜单
     * @param menu
     */
    void edit(Menu menu);

    /**
     * 根据id删除
     * @param id
     */
    Result deleteById(Integer id);

    /**
     * 查询所有菜单
     * @return
     */
    List<Menu> findAll();

}
