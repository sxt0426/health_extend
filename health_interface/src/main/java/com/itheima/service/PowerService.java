package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;

import java.util.List;

/**
 * @version V1.0
 * @author: Administrator
 * @date: 2019/10/20 0020 下午 21:16
 * @description:
 */
public interface PowerService {
    //添加权限
    Result add(Permission permission, Integer[] roleIds);

    //分页
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    //根据id查找权限
    Permission findById(Integer id);

    //查询中间表信息
    List<Integer> findroleIds(Integer id);

    //编辑
    void edit(Permission permission, Integer[] roleIds);

    //根据id删除
    Result deleteById(Integer id);
}
