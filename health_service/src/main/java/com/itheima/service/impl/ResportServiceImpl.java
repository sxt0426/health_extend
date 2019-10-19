package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.common.utils.DateUtils;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = ReportService.class)
@Transactional
public class ResportServiceImpl implements ReportService {


    @Autowired
    MemberDao memberDao;

    @Autowired
    OrderDao orderDao;

    /**
     * reportDate:null,
     * todayNewMember :0,
     * totalMember :0,
     * thisWeekNewMember :0,
     * thisMonthNewMember :0,
     * todayOrderNumber :0,
     * todayVisitsNumber :0,
     * thisWeekOrderNumber :0,
     * thisWeekVisitsNumber :0,
     * thisMonthOrderNumber :0,
     * thisMonthVisitsNumber :0,
     * hotSetmeal :[]
     *
     * @return
     */
    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        Map<String,Object> result = new HashMap<>();

        //1.今日新增会员 根据今天的日期查询 今日的注册会员数量
        Date today = DateUtils.getToday();

        String todayStr = DateUtils.parseDate2String(today);

        Integer todayNewMember = memberDao.findCountByDate(todayStr);

        //2.总的会员数量 统计t_member表的数据量
        Integer totalMember = memberDao.findAllCount();

        //3.本周新增会员数量  本周一之后的日期新增的会员数量 都是本周的新增会员
        Date monDate = DateUtils.getFirstDayOfWeek(DateUtils.getThisWeekMonday());
        String monDateStr = DateUtils.parseDate2String(monDate);
        Integer thisWeekNewMember = memberDao.findCountByAfterDate(monDateStr);

        //4.本月新增会员数量 将本月所有的天，注册的会员的数量加起来
        Date monthFisrtDay = DateUtils.getFirstDay4ThisMonth();
        String monthFisrtDayStr = DateUtils.parseDate2String(monthFisrtDay);
        Integer thisMonthNewMember = memberDao.findCountByAfterDate(monthFisrtDayStr);


        //5.今天预约的数量
        Integer todayOrderNumber = orderDao.findCountByDate(todayStr);

        //6.本周的预约数量 本周一之后的所有预约数量相加
        Integer thisWeekOrderNumber = orderDao.findByCountAfterDate(monDateStr);

        //7.本月的预约数量 将本月第一天之后的所有预约数量相加
        Integer thisMonthOrderNumber = orderDao.findByCountAfterDate(monthFisrtDayStr);


        //8.今天体检了多少人 统计今天的人数 并且 orderStatus='已到诊'
        Integer todayVisitsNumber = orderDao.findVisitCountByDate(todayStr);

        //9.本周体检了多少人 将本周一之后所有的体检人数相加
        Integer thisWeekVisitsNumber = orderDao.findVisitCountByAfterDate(monDateStr);

        //10.本月体检了多少人 将本月第一天之后所有的体检人数相加
        Integer thisMonthVisitsNumber = orderDao.findVisitCountByAfterDate(monthFisrtDayStr);

        //11.热门套餐
        List<Map<String,Object>> hotSetmealCount = orderDao.hotSetmeal();

        result.put("reportDate",todayStr);
        result.put("todayNewMember",todayNewMember);
        result.put("totalMember",totalMember);
        result.put("thisWeekNewMember",thisWeekNewMember);
        result.put("thisMonthNewMember",thisMonthNewMember);
        result.put("todayOrderNumber",todayOrderNumber);
        result.put("thisWeekOrderNumber",thisWeekOrderNumber);
        result.put("thisMonthOrderNumber",thisMonthOrderNumber);
        result.put("todayVisitsNumber",todayVisitsNumber);
        result.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        result.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        result.put("hotSetmeal",hotSetmealCount);

        return result;
    }
}