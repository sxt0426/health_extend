package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.数据结构 user->roles->permission
        com.itheima.pojo.User user = userService.findByUsername(username);

        if (user == null) {//用户不存在
            return null;
        }

        //2.封装权限信息
        Set<Role> roles = user.getRoles();

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        if (roles != null && roles.size() > 0) {
            for (Role role : roles) {
                //拿到权限信息
                Set<Permission> permissions = role.getPermissions();
                if (permissions != null && permissions.size() > 0) {
                    for (Permission permission : permissions) {
                        grantedAuthorities.add(new SimpleGrantedAuthority(permission.getKeyword()));
                    }
                }
            }
        }

        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("admin"));
    }
}