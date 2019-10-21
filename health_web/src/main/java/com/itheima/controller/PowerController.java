package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.service.PowerService;
import com.itheima.service.RoleService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version V1.0
 * @author: Administrator
 * @date: 2019/10/20 0020 下午 21:15
 * @description:
 */
@RestController
@RequestMapping("/power")
public class PowerController {
    private static final Logger log = Logger.getLogger(RoleController.class);

    @Reference
    private PowerService powerService;

    @RequestMapping("/add")
    public Result add(@RequestBody Permission permission, Integer[] roleIds){
        try {
            return powerService.add(permission,roleIds);
        } catch (Exception e) {
            log.error("Add permission error..........",e);
            return Result.error("添加权限失败");
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
            return powerService.findPage(pageBean.getCurrentPage(),pageBean.getPageSize(),pageBean.getQueryString());
        } catch (Exception e) {
            log.error("Find page error.",e);
        }
        return null;
    }

    /**
     * 根据id查找
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Permission permission = powerService.findById(id);
            return Result.success("查询权限信息成功", permission);
        } catch (Exception e) {
            log.error("Find permission by id error.",e);
            return Result.error("查询权限信息失败");
        }
    }

    /**
     * 查找已关联role id
     * @return
     */
    @RequestMapping("/findroleIds")
    public Result findroleIds(Integer id){
        try {
            List<Integer> roleIds = powerService.findroleIds(id);
            return Result.success("查询关联角色id成功", roleIds);
        } catch (Exception e) {
            log.error("Find role ids error",e);
            return Result.error("查询关联角色id失败");
        }
    }

    //编辑
    @RequestMapping("/edit")
    public Result edit(@RequestBody Permission permission, Integer [] roleIds){
        try {
            powerService.edit(permission,roleIds);
            return new Result(true, "编辑成功");
        } catch (Exception e) {
            log.error("Edit permission error.",e);
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
            return powerService.deleteById(id);
        } catch (Exception e) {
            log.error("delete permission error",e);
            return Result.error("删除失败");
        }
    }
}
