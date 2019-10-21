package com.itheima.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.common.constant.RedisConstant;
import com.itheima.common.utils.QiniuUtils;
import com.itheima.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.Set;

public class ClearJob {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private JobService jobService;

    private void clearImg(){
        System.out.println("===定时调用开始===");
        //清理图片
        //1.比较两个集合中的差值 图片
        Set<String> imgs = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_IMG_TMP,RedisConstant.SETMEAL_IMG_DB);

        //2.清理图片
        Iterator<String> iter = imgs.iterator();

        while (iter.hasNext()){
            //需要清理的图片的名字
            String filename = iter.next();

            System.out.println("=========>"+filename);

            //调用七牛云的sdk删除 图片
            QiniuUtils.deleteFileFromQiniu(filename);

            //删除保存在RedisConstant.SETMEAL_IMG_TMP 的图片名 filename
            jedisPool.getResource().srem(RedisConstant.SETMEAL_IMG_TMP,filename);
        }
    }

    private void deleteOrderSettingSchedule() throws Exception {
        System.out.println("清理预约数据");
        jobService.deleteOrderSettingSchedule();
    }
}