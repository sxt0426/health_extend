package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.common.constant.MessageConstant;
import com.itheima.common.utils.POIUtils;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 预约设置
 */
@RestController
@RequestMapping("ordersetting")
public class OrderSettingController {
    private static final Logger log = Logger.getLogger(OrderSettingController.class);

    @Reference
    OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile file) {
        //1.获取到上传文件
        try {
            if (file.getInputStream() == null) {
                return Result.error(MessageConstant.IMPORT_ORDERSETTING_FAIL);
            }
        } catch (IOException e) {
            log.error("获取的文件为空", e);
            return Result.error(MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }

        //2.解析excel中的数据
        List<String[]> data = null;
        try {
            data = POIUtils.readExcel(file);
        } catch (IOException e) {
            log.error("Parse excel error", e);
            return Result.error(MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }

        List<OrderSetting> orderSettings = new ArrayList<>();

        //3.数据封装
        if (data != null && data.size() > 0) {
            for (String[] s : data) {
                OrderSetting orderSetting = new OrderSetting();
                orderSetting.setOrderDate(new Date(s[0]));
                orderSetting.setNumber(Integer.parseInt(s[1]));

                orderSettings.add(orderSetting);
            }
        }

        //4.调用service处理数据
        try {
            orderSettingService.add(orderSettings);
            return Result.success(MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            log.error("Import excel data error", e);
        }

        return Result.error(MessageConstant.IMPORT_ORDERSETTING_FAIL);
    }

    /**
     * 根据日期查询 某一月的 预约设置数据
     *
     * @param date yyyy-MM
     * @return
     */
    @RequestMapping("findByMonth")
    public Result findByMonth(String date) {
        List<Map> list = orderSettingService.findByMonth(date);

        if (list != null && list.size() > 0) {
            try {
                return Result.success(MessageConstant.GET_ORDERSETTING_SUCCESS, list);
            } catch (Exception e) {
                log.error("Query order setting by month error", e);
            }
        }

        return Result.error(MessageConstant.GET_ORDERSETTING_FAIL);
    }

    /**
     * 根据日期进行 预约人数设置
     *
     * @param orderSetting
     * @return
     */
    @RequestMapping("settingByDay")
    public Result settingByDay(@RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.settingByDay(orderSetting);
            return Result.success(MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            log.error("Setting order set error.", e);
        }

        return Result.error(MessageConstant.ORDERSETTING_FAIL);
    }
}