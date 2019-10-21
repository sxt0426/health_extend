package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.RoleDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @author: Administrator
 * @date: 2019/10/20 0020 下午 15:07
 * @description:角色实现类
 */
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    /**
     * 添加角色
     *
     * @param role
     * @param menuIds
     * @return
     */
    @Override
    public Result add(Role role, Integer[] menuIds) {
        //添加角色
        roleDao.add(role);
        setCheckgroupAndCheckitem(role.getId(), menuIds);
        return Result.success("添加成功");
    }

    public void  setCheckgroupAndCheckitem(Integer id, Integer[] menuIds){
        //添加角色，权限关联关系
        for (Integer menuId : menuIds) {
            roleDao.addRoleMenu(id, menuId);
        }
    }

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page page = (Page) roleDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据id查找role
     *
     * @param id
     * @return
     */
    @Override
    public Role findById(Integer id) {
        Role role = roleDao.queryById(id);
        return role;
    }

    /**
     * 查询关联菜单id
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findmenuIds(Integer id) {
        return roleDao.findmenuIds(id);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @Override
    public Result deleteById(Integer id) {
        if (id == 1) {
            return Result.error("不能删除管理员账号");
        }
        int count1 = roleDao.queryRoleMenuById(id);
        int count2 = roleDao.queryRolePermissionById(id);
        if (count1 == 0 && count2 == 0) {
            //删除role
            roleDao.deleteById(id);
            return Result.success("删除成功");
        }else{
            return Result.error("有关联，删除失败");
        }
    }

    /**
     * 查询所有角色
     * @return
     */
    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    //编辑
    @Override
    public void edit(Role role, Integer[] menuIds) {
        //1.更新基本信息
        roleDao.edit(role);

        //2.删除中间表数据
        roleDao.deleteAssocation(role.getId());

        //3.设置检查组与检查项关联信息
        setCheckgroupAndCheckitem(role.getId(),menuIds);
    }
}
