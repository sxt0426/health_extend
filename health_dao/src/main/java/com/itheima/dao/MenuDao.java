package com.itheima.dao;

import com.itheima.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDao {
    List<Integer> findByUsername(@Param("username") String username);

    Menu findMainMenuById(@Param("menuId")Integer menuId);

    Menu findChildMenuById(@Param("parentMenuId")Integer parentMenuId, @Param("id")Integer id);
}
