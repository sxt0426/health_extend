package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.common.constant.MessageConstant;
import com.itheima.common.utils.DateUtils;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 预约服务
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    public Result submit(Map map) throws Exception {
        //1.判断预约的日期 是否进行了预约设置
        //如果没有设置 则 提示用户 当前日期不能预约

        String orderDate = (String) map.get("orderDate");
        String telephone = (String) map.get("telephone");

        Date date = DateUtils.parseString2Date(orderDate);

        //根据预约日期查询 是否进行了预约设置
        OrderSetting orderSetting = orderSettingDao.findOrderSettingByOrderDate(date);

        if (orderSetting == null) {//为空 代表 没有预约设置
            return Result.error(MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        //2.判断预约日期 是否预约满了（可预约人数<=已预约人数）
        //如果预约满了 则 提示用户 预约满了 不能进行预约
        int number = orderSetting.getNumber();//可预约人数
        int reservations = orderSetting.getReservations();//已经预约人数

        if (number < reservations || number == reservations) {
            return Result.error(MessageConstant.ORDER_FULL);
        }

        //3.由于我们的系统没有登录 所以需要判断 用户在我们系统是否注册了
        //如果没有注册 则 注册新用户
        Member member = memberDao.findByTelephone(telephone);

        if (member == null) {//member==null 代表没有注册会员
            member = new Member();
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setPhoneNumber(telephone);
            member.setName((String) map.get("name"));
            member.setRegTime(new Date());
            member.setContactsName((String) map.get("contactsName"));
            member.setContactsSex((String) map.get("contactsSex"));
            member.setContactsTelephone((String) map.get("contactsTelephone"));

            memberDao.add(member);
        }

        //4.检查用户是否重复预约
        //如果重复预约了 则 提示用户 已预约过了 不能再预约了

        Order orderQueryParam = new Order();
        orderQueryParam.setMemberId(member.getId());
        orderQueryParam.setOrderDate(date);
        orderQueryParam.setSetmealId(Integer.parseInt((String) map.get("setmealId")));

        List<Order> orders = orderDao.findByCondition(orderQueryParam);

        if (orders != null && orders.size() > 0) {//已经预约过了
            return Result.error(MessageConstant.HAS_ORDERED);
        }



        Order order = new Order();
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
        order.setMemberId(member.getId());
        order.setOrderDate(date);
        order.setOrderType((String) map.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);

        orderDao.add(order);


        //5.将预约信息提交到数据库  1.预约设置（已预约人数+1） 2.t_order新增一条数据
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingDao.updateReservationsByDate(orderSetting);

        return Result.success(MessageConstant.ORDER_SUCCESS, order);
    }

    /**
     * 根据id，查询预约信息
     * @param id
     * @return
     */
    @Override
    public Map findById(Integer id) {
        return orderDao.findById(id);
    }
}