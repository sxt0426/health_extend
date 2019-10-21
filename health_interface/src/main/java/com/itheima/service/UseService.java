package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.User;

import java.util.List;

/**
 * @version V1.0
 * @author: Administrator
 * @date: 2019/10/20 0020 下午 19:25
 * @description:
 */
public interface UseService {
    //添加用户
    Result add(User user, Integer[] roleIds);

    //分页
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    //根据id查询
    User findById(Integer id);

    //查询关联角色id
    List<Integer> findmenuIds(Integer id);

    //编辑
    void edit(User user, Integer[] roleIds);

    //根据id删除
    Result deleteById(Integer id);
}
