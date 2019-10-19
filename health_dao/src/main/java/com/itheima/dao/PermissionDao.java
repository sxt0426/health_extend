package com.itheima.dao;

import com.itheima.pojo.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionDao {

    /**
     * 根据角色id，查询权限信息
     * @param id
     * @return
     */
    Set<Permission> findByRoleId(Integer id);
}