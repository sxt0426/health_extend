package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.common.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 套餐逻辑
 */
@RestController
@RequestMapping("setmeal")
public class SetmealController {
    private static final Logger log = Logger.getLogger(SetmealController.class);

    @Reference
    private SetmealService setmealService;

    /**
     * 获取套餐列表
     *
     * @return
     */
    @RequestMapping("getSetmeal")
    public Result getSetmeal() {
        try {
            List<Setmeal> setmeals = setmealService.findAll();

            if (setmeals != null && setmeals.size() > 0) {
                return Result.success(MessageConstant.QUERY_SETMEALLIST_SUCCESS, setmeals);
            }
        } catch (Exception e) {
            log.error("Query setmeal list error.", e);
        }

        return Result.error(MessageConstant.QUERY_SETMEALLIST_FAIL);
    }

    /**
     * 根据套餐id，查询套餐信息
     * @return
     */
    @RequestMapping("findById")
    public Result findById(Integer id){
        try {
            Setmeal setmeal = setmealService.findById(id);

            if (setmeal != null) {
                return Result.success(MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
            }
        } catch (Exception e) {
           log.error("Find setmeal by id error.",e);
        }

        return Result.error(MessageConstant.QUERY_SETMEAL_FAIL);
    }
}