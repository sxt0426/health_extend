package com.itheima.dao;

import com.itheima.pojo.Member;

/**
 * 会员数据库操作
 */
public interface MemberDao {

    /**
     * 根据手机号查询 会员信息
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 新增用户
     * @param member
     */
    void add(Member member);

    /**
     * 查询某个月 之前的所有用户统计数据
     * @param date
     * @return
     */
    int findCountByBeforeMonth(String date);

    /**
     * 根据注册日期查询 会员数量
     * @param todayStr
     * @return
     */
    Integer findCountByDate(String todayStr);

    /**
     * 统计所有的会员数量
     * @return
     */
    Integer findAllCount();

    /**
     * 查询指定日期之后的所有数据量统计
     * @param monDateStr
     * @return
     */
    Integer findCountByAfterDate(String monDateStr);
}