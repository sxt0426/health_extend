package com.itheima.dao;

import com.itheima.pojo.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    /**
     * 根据用户id查询角色信息
     * @param id
     * @return
     */
    Set<Role> findByUserId(Integer id);
}