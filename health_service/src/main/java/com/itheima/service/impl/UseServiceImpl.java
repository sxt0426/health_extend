package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.UseDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version V1.0
 * @author: Administrator
 * @date: 2019/10/20 0020 下午 19:26
 * @description:
 */
@Service(interfaceClass = UseService.class)
@Transactional
public class UseServiceImpl implements UseService {
    @Autowired
    private UseDao useDao;

    //添加用户
    @Override
    public Result add(User user, Integer[] roleIds) {
        //判断用户名唯一
        int count = useDao.checkUsername(user.getUsername());
        if (count > 0) {
            //已存在用户名
            return Result.error("用户名已存在");
        }
        //添加角色
        useDao.add(user);
        setCheckgroupAndCheckitem(user.getId(), roleIds);
        return Result.success("添加成功");
    }

    //分页
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page page = (Page) useDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    //根据id查询
    @Override
    public User findById(Integer id) {
        User user = useDao.queryById(id);
        return user;
    }

    //查询关联角色id
    @Override
    public List<Integer> findmenuIds(Integer id) {
        return useDao.findmenuIds(id);
    }


    //编辑
    @Override
    public void edit(User user, Integer[] roleIds) {

        //1.更新基本信息
        useDao.edit(user);

        //2.删除中间表数据
        useDao.deleteAssocation(user.getId());

        //3.设置检查组与检查项关联信息
        setCheckgroupAndCheckitem(user.getId(), roleIds);

    }

    private void setCheckgroupAndCheckitem(Integer id, Integer[] roleIds) {
        //添加角色，权限关联关系
        for (Integer roleId : roleIds) {
            useDao.addUserRole(id, roleId);
        }
    }

    //根据id删除
    @Override
    public Result deleteById(Integer id) {
        int count = useDao.queryUserRole(id);
        if (count == 0) {
            useDao.deleteUserById(id);
            return Result.success("删除成功");
        }
        //删除用户
        return Result.error("删除失败");
    }
}
