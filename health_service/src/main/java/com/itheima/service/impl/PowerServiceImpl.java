package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.PowerDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version V1.0
 * @author: Administrator
 * @date: 2019/10/20 0020 下午 21:17
 * @description:
 */
@Service(interfaceClass = PowerService.class)
@Transactional
public class PowerServiceImpl implements PowerService {
    @Autowired
    private PowerDao powerDao;

    //添加权限
    @Override
    public Result add(Permission permission, Integer[] roleIds) {
        //添加角色
        powerDao.add(permission);
        setCheckgroupAndCheckitem(permission.getId(), roleIds);
        return Result.success("添加成功");
    }

    private void setCheckgroupAndCheckitem(Integer id, Integer[] roleIds) {
        //添加角色，权限关联关系
        for (Integer roleId : roleIds) {
            powerDao.addPermisssionRole(id, roleId);
        }
    }

    //分页
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page page = (Page) powerDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }


    //根据id查找权限
    @Override
    public Permission findById(Integer id) {
        Permission permission = powerDao.queryById(id);
        return permission;
    }

    //查找中间表信息
    @Override
    public List<Integer> findroleIds(Integer id) {
        return powerDao.findroleIds(id);
    }

    //编辑
    @Override
    public void edit(Permission permission, Integer[] roleIds) {
        //1.更新基本信息
        powerDao.edit(permission);

        //2.删除中间表数据
        powerDao.deleteAssocation(permission.getId());

        //3.设置关联信息
        setCheckgroupAndCheckitem(permission.getId(),roleIds);
    }

    //根据id删除
    @Override
    public Result deleteById(Integer id) {
        int count = powerDao.queryPermissionRoleById(id);
        if (count == 0) {
            //删除role
            powerDao.deleteById(id);
            return Result.success("删除成功");
        }else{
            return Result.error("有关联，删除失败");
        }
    }
}
