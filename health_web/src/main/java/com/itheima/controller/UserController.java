package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.common.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.service.UserService;
import com.itheima.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户管理
 */
@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger log = Logger.getLogger(UserController.class);

    @Reference
    private UserService userService;


    /**
     * 获取登录的用户名
     *
     * @return
     */
    @RequestMapping("/getUsername")
    public Result getUsername() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user != null && user.getUsername() != null) {
            return Result.success(MessageConstant.GET_USERNAME_SUCCESS, user.getUsername());
        }

        return Result.error(MessageConstant.GET_USERNAME_FAIL);
    }

    @RequestMapping("/getMenus")
    public Result getMenus() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = user.getUsername();

        if (username!=null) {
            List<Menu> menuList= userService.getMenus(username);
            if (menuList != null && menuList.size() > 0) {
                return Result.success("查询菜单成功",menuList);
            }
        }

        return Result.error("查询菜单失败");
    }


}