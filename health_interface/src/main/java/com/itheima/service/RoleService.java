package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;

import java.util.List;

/**
 * @version V1.0
 * @author: Administrator
 * @date: 2019/10/20 0020 下午 15:06
 * @description:角色接口
 */
public interface RoleService {
    /**
     * 添加角色
     * @param role
     * @param menuIds
     * @return
     */
    Result add(Role role, Integer[] menuIds);

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 根据id查找role
     * @param id
     * @return
     */
    Role findById(Integer id);

    /**
     * 查询关联菜单id
     * @param id
     * @return
     */
    List<Integer> findmenuIds(Integer id);

    /**
     * 根据id删除
     * @param id
     */
    Result deleteById(Integer id);

    /**
     * 查询所有角色
     * @return
     */
    List<Role> findAll();

    //编辑
    void edit(Role role, Integer[] menuIds);
}
