package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.common.utils.DateUtils;
import com.itheima.common.utils.MD5Utils;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map getMemberReport(String[] value) throws Exception {
        //定义一个list集合，存放每个月的月份
        List<String> months = new ArrayList<>();
        //定义一个list集合，存放每个月的会员数量
        List<Integer> memberCount  = new ArrayList<>();

        //使用日期工具类获取查询期间每月月份
        List<String> monthBetween = DateUtils.getMonthBetween(value[0], value[1], "yyyy-MM");
        for (String month : monthBetween) {
            //定义每个月开始日期
            String monthBegin = month + "-1";
            //定义每个月结束时间
            String monthEnd = month + "-31";

            //统计每个月人数
            int count = memberDao.findMemberCountByMonth(monthBegin, monthEnd);

            //添加每一个月
            months.add(month);
            //添加每一个月的会员数量
            memberCount.add(count);
        }

        //创建一个map，将月份的集合以及每个月会员数量的集合存入其中
        Map<String, List> map = new HashMap<>();
        map.put("a", months);
        map.put("b", memberCount);

        return map;
    }
}