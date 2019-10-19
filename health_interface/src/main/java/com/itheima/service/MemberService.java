package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.List;

/**
 * 会员接口
 */
public interface MemberService {

    /**
     * 根据手机号查询 会员信息
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 新增会员
     * @param member
     */
    void add(Member member);

    /**
     * 根据月份查询，当前月份之前所有的用户数据
     * @param queryDataParam
     * @return
     */
    List<Integer> findCountByBeforeMonth(List<String> queryDataParam);
}