package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.common.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version V1.0
 * @author: Administrator
 * @date: 2019/10/20 0020 下午 15:00
 * @description:角色控制器
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    private static final Logger log = Logger.getLogger(RoleController.class);

    @Reference
    private RoleService roleService;

    @RequestMapping("/add")
    public Result add(@RequestBody Role role,Integer[] menuIds){
        try {
            return roleService.add(role,menuIds);
        } catch (Exception e) {
            log.error("Add role error..........",e);
            return Result.error("添加角色失败");
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
            return roleService.findPage(pageBean.getCurrentPage(),pageBean.getPageSize(),pageBean.getQueryString());
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
            Role role = roleService.findById(id);
            return Result.success("查询菜单信息成功", role);
        } catch (Exception e) {
            log.error("Find menu by id error.",e);
            return Result.error("查询菜单信息失败");
        }
    }

    /**
     * 查找已关联菜单id
     * @return
     */
    @RequestMapping("/findmenuIds")
    public Result findmenuIds(Integer id){
        try {
            List<Integer> menuIds = roleService.findmenuIds(id);
            return Result.success("查询关联菜单id成功", menuIds);
        } catch (Exception e) {
            log.error("Find menu ids error",e);
            return Result.error("查询关联菜单id失败");
        }
    }

    /**
     * 根据id删除
     * @return
     */
    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        try {
            return roleService.deleteById(id);
        } catch (Exception e) {
            log.error("delete role error",e);
            return Result.error("删除失败");
        }
    }

    /**
     * 查询所有role
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<Role> list = roleService.findAll();
            return Result.success("查询角色成功", list);
        } catch (Exception e) {
            log.error("find all role error ==========-=-=-=-=",e);
            return Result.error("查询角色失败");
        }
    }

    //编辑
    @RequestMapping("/edit")
    public Result edit(@RequestBody Role role, Integer [] menuIds){
        try {
            roleService.edit(role,menuIds);
            return new Result(true, "编辑成功");
        } catch (Exception e) {
            log.error("Edit role error.",e);
        }
        return Result.error("编辑失败");
    }
}
