package com.itheima.service;

import com.itheima.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 模拟冲数据库获取用户信息
     * @param username
     * @return
     */
    private User getUserFromDb(String username){
        Map<String,User> map = new HashMap<>();

        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));

        map.put("admin",user);

        User  user1 = new User();
        user1.setUsername("test");
        user1.setPassword(passwordEncoder.encode("test"));

        map.put("test",user1);

        return map.get(username);
    }

    /**
     *
     * @param username 页面传入的username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = getUserFromDb(username);

        if(user==null){
            return null;
        }

        Set<SimpleGrantedAuthority> auths = new HashSet<>();
        auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        auths.add(new SimpleGrantedAuthority("DEL"));
        auths.add(new SimpleGrantedAuthority("ADD"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),auths);
    }
}