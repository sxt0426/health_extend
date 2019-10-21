package com.itheima.dao;

import com.itheima.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version V1.0
 * @author: Administrator
 * @date: 2019/10/20 0020 下午 21:19
 * @description:
 */
public interface PowerDao {
    //添加权限
    void add(Permission permission);

    //添加中间表数据
    void addPermisssionRole(@Param("permission_id") Integer id, @Param("role_id") Integer roleId);

    //分页
    List<Permission> selectByCondition(String queryString);

    //根据id查找
    Permission queryById(Integer id);

    //查询中间表信息
    List<Integer> findroleIds(Integer id);

    //编辑
    void edit(Permission permission);

    //删除中间表信息
    void deleteAssocation(Integer id);

    //查询中间表关联关系
    int queryPermissionRoleById(Integer id);

    //删除权限
    void deleteById(Integer id);
}
