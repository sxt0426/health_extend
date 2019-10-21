package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.common.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @version V1.0
 * @author: Administrator
 * @date: 2019/10/20 0020 上午 9:54
 * @description:处理菜单栏
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    private static final Logger log = Logger.getLogger(MenuController.class);

    @Reference
    private MenuService menuService;

    /**
     * 添加菜单信息
     * @param menu
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Menu menu){
        try {
            menuService.add(menu);
            return Result.success("添加菜单成功");
        } catch (Exception e) {
            log.error("Add menu bar error.",e);
            return Result.error("添加菜单失败");
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean pageBean){
        try {
            return menuService.findPage(pageBean.getCurrentPage(),pageBean.getPageSize(),pageBean.getQueryString());
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
            Menu menu = menuService.findById(id);
            return Result.success("查询菜单信息成功", menu);
        } catch (Exception e) {
            log.error("Find menu by id error.",e);
            return Result.error("查询菜单信息失败");
        }
    }

    /**
     * 编辑
     * @return
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody Menu menu){
        try {
            menuService.edit(menu);
            return Result.success("编辑菜单成功");
        } catch (Exception e) {
            log.error("Edit menu error.",e);
            return Result.error("编辑菜单失败");
        }
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        try {
            return menuService.deleteById(id);
        } catch (Exception e) {
            log.error("delete menu error .............",e);
            return Result.error("删除失败");
        }
    }

    /**
     * 查询所有菜单信息
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<Menu> list = menuService.findAll();
            return Result.success("查询菜单项成功", list);
        } catch (Exception e) {
            log.error("Find all menu error ============",e);
            return Result.error("查询菜单项失败");
        }
    }
}
