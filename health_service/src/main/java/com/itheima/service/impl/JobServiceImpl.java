package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.common.utils.DateUtils;
import com.itheima.dao.JobDao;
import com.itheima.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author john
 * @version v1.0
 * @date 2019/10/20 15:22
 * @description
 */
@Service(interfaceClass = JobService.class)
@Transactional
public class JobServiceImpl implements JobService {

    @Autowired
    private JobDao jobDao;


    @Override
    public void deleteOrderSettingSchedule() throws Exception {
        // 获取当月一号的时间
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());

        // 删除本月一号之前的预约数据
        jobDao.deleteOrderSettingSchedule(firstDay4ThisMonth);

    }
}
