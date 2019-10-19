package com.itheima.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HelloController {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("add")
    public String add(){
        System.out.println("add=========");
        return null;
    }

    @PreAuthorize("hasAuthority('DEL')")
    @RequestMapping("delete")
    public String delete(){
        System.out.println("del=========");
        return null;
    }
}