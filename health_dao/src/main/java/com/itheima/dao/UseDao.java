package com.itheima.dao;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version V1.0
 * @author: Administrator
 * @date: 2019/10/20 0020 下午 19:27
 * @description:
 */
public interface UseDao {
    //查询用户名唯一
    int checkUsername(String username);

    //添加用户
    void add(User user);

    //添加用户角色关系
    void addUserRole(@Param("user_id") Integer id, @Param("role_id") Integer roleId);

    //分页
    List<User> selectByCondition(String queryString);

    //根据id查询
    User queryById(Integer id);

    //查询关联角色id
    List<Integer> findmenuIds(Integer id);

    //编辑
    void edit(User user);

    //删除中间表数据
    void deleteAssocation(Integer id);

    //删除用户
    void deleteUserById(Integer id);

    //查询中间表
    int queryUserRole(Integer id);
}
