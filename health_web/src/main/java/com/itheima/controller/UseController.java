package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UseService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version V1.0
 * @author: Administrator
 * @date: 2019/10/20 0020 下午 19:23
 * @description:用户增删改查
 */
@RestController
@RequestMapping("/use")
public class UseController {

    private static final Logger log = Logger.getLogger(RoleController.class);

    @Reference
    private UseService useService;
    /**
     * 添加user
     * @param user
     * @param roleIds
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody User user, Integer[] roleIds){
        try {
            return useService.add(user,roleIds);
        } catch (Exception e) {
            log.error("Add user error..........",e);
            return Result.error("添加用户失败");
        }
    }

    /**
     * 分页查询
     * @param pageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean pageBean){
        try {
            return useService.findPage(pageBean.getCurrentPage(),pageBean.getPageSize(),pageBean.getQueryString());
        } catch (Exception e) {
            log.error("Find page error.",e);
        }
        return null;
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            User user = useService.findById(id);
            return Result.success("查询用户信息成功", user);
        } catch (Exception e) {
            log.error("Find user by id error.",e);
            return Result.error("查询用户信息失败");
        }
    }

    /**
     * 查询关联角色id
     * @return
     */
    @RequestMapping("/findroleIds")
    public Result findroleIds(Integer id){
        try {
            List<Integer> roleIds = useService.findmenuIds(id);
            return Result.success("查询关角色id成功", roleIds);
        } catch (Exception e) {
            log.error("Find user ids error",e);
            return Result.error("查询关联角色id失败");
        }
    }


    //编辑
    @RequestMapping("/edit")
    public Result edit(@RequestBody User user, Integer [] roleIds){
        try {
            useService.edit(user,roleIds);
            return new Result(true, "编辑成功");
        } catch (Exception e) {
            log.error("Edit user error.",e);
        }
        return Result.error("编辑失败");
    }

    /**
     * 根据id删除
     * @return
     */
    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        try {
            return useService.deleteById(id);
        } catch (Exception e) {
            log.error("delete user error",e);
            return Result.error("删除失败");
        }
    }
}
