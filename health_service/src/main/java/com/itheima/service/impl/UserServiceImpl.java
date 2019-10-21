package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MenuDao;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 用户逻辑
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private MenuDao menuDao;


    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    PermissionDao permissionDao;

    @Override
    public User findByUsername(String username) {

        //数据结构 user{roles{permission}}
        //1.拿到用户的基本信息 userId
        User user = userDao.findByUsername(username);

        if (user == null) {
            return null;
        }

        //2.根据userId查询 角色信息
        Set<Role> roles = roleDao.findByUserId(user.getId());

        //3.根据roleId查询 权限信息
        for (Role role : roles) {
            Set<Permission> permissions = permissionDao.findByRoleId(role.getId());

            if (permissions != null && permissions.size() > 0) {
                role.setPermissions(permissions);
            }
        }

        user.setRoles(roles);

        return user;
    }

    @Override
    public List<Menu> getMenus(String username) {

        List<Integer> menuIds=menuDao.findByUsername(username);

        List<Menu> menuList=new ArrayList<>();

        for (Integer menuId : menuIds) {
            Menu parentMenu=menuDao.findMainMenuById(menuId);

            if (parentMenu != null) {

                List<Menu> children =new ArrayList<>();

                for (Integer id : menuIds) {
                   Menu childMenu = menuDao.findChildMenuById(menuId,id);
                    if (childMenu != null) {
                        children.add(childMenu);
                    }
                }

                parentMenu.setChildren(children);
                menuList.add(parentMenu);
            }
        }

        return menuList;
    }
}