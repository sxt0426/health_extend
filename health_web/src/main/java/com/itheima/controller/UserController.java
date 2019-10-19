package com.itheima.controller;

import com.itheima.common.constant.MessageConstant;
import com.itheima.entity.Result;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理
 */
@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger log = Logger.getLogger(UserController.class);


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
}