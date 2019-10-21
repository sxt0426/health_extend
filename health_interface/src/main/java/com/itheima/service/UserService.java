package com.itheima.service;

import com.itheima.pojo.Menu;
import com.itheima.entity.Result;
import com.itheima.pojo.User;

import java.util.List;

/**
 * 用户接口
 */
public interface UserService {

    /**
     * 根据名称查询用户信息
     * @param username
     * @return
     */
    User findByUsername(String username);

    List<Menu> getMenus(String username);


}