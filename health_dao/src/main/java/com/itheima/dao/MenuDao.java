package com.itheima.dao;

import com.itheima.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version V1.0
 * @author: Administrator
 * @date: 2019/10/20 0020 上午 10:02
 * @description:
 */
public interface MenuDao {
    /**
     * 添加菜单
     * @param menu
     */
    void add(Menu menu);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    List<Menu> selectByCondition(String queryString);

    /**
     * 根据id查找菜单
     * @param id
     * @return
     */
    Menu queryById(Integer id);

    /**
     * 编辑菜单
     * @param menu
     */
    void edit(Menu menu);

    /**
     * 查询关联关系
     * @param id
     * @return
     */
    Integer queryMenuAndRoleById(Integer id);

    /**
     * 删除
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 查询所有菜单
     * @return
     */
    List<Menu> findAll();

    List<Integer> findByUsername(@Param("username") String username);

    Menu findMainMenuById(@Param("menuId")Integer menuId);

    Menu findChildMenuById(@Param("parentMenuId")Integer parentMenuId, @Param("id")Integer id);
}
