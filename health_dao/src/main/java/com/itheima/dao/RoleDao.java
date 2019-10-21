package com.itheima.dao;

import com.itheima.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    /**
     * 根据用户id查询角色信息
     * @param id
     * @return
     */
    Set<Role> findByUserId(Integer id);

    /**
     * 添加角色
     * @param role
     */
    void add(Role role);

    /**
     * 添加角色，菜单关联关系
     * @param id
     * @param menuId
     */
    void addRoleMenu(@Param("roleId") Integer id,@Param("menuId") Integer menuId);

    /**
     * 分页
     * @param queryString
     * @return
     */
    List<Role> selectByCondition(String queryString);

    /**
     * 根据id查找role
     * @param id
     * @return
     */
    Role queryById(Integer id);

    /**
     * 查询关联菜单id
     * @param id
     * @return
     */
    List<Integer> findmenuIds(Integer id);

    /**
     * 删除role
     * @param id
     */
    void deleteById(Integer id);


    /**
     * 查询与menu关系
     * @param id
     * @return
     */
    int queryRoleMenuById(Integer id);

    /**
     * -查询role和permission关联关系
     * @param id
     * @return
     */
    int queryRolePermissionById(Integer id);

    /**
     *查询所有角色
     * @return
     */
    List<Role> findAll();

    //更新
    void edit(Role role);

    //删除中间数据
    void deleteAssocation(Integer id);
}