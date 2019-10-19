package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.common.utils.MD5Utils;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员服务
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        if (member.getPassword() != null) {
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }

        memberDao.add(member);
    }

    @Override
    public List<Integer> findCountByBeforeMonth(List<String> queryDataParam) {
        List<Integer> result = new ArrayList<>();

        //yyyy.MM  2019.2 包含当前月份数据
        //封装查询月份
        for (String date : queryDataParam) {
            date = date + ".31";//2019.02.31

            int count = memberDao.findCountByBeforeMonth(date);
            result.add(count);
        }

        return result;
    }
}