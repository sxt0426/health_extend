package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.common.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckgroupService;
import com.itheima.service.CheckitemService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 检查组管理
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckgroupController {

    @Reference
    private CheckgroupService checkgroupService;

    /**
     * 打日志的目的：1.跟踪代码；2.程序错误分析 logback,log4j2
     */
    private static final Logger log = Logger.getLogger(CheckgroupController.class);

    /**
     * 新增检查组
     * @param checkGroup
     * @param checkitemids
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer [] checkitemids){
        try {
            checkgroupService.add(checkGroup,checkitemids);
            return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            log.error("Add checkgroup error.",e);
        }

        return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return checkgroupService.pageQuery(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
    }

    /**
     * 根据检查组id，查询检查组信息
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(Integer id){
        try {
            CheckGroup checkGroup = checkgroupService.findById(id);

            if(checkGroup!=null) {
                return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
            }
        } catch (Exception e) {
            log.error("Find checkgroup by id error.",e);
        }

        return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
    }

    /**
     * 根据检查组id，查询所有关联的检查项ids
     * @param id
     * @return
     */
    @GetMapping("/findCheckitemIds")
    public Result findCheckitemIds(Integer id){
        try {
            List<Integer> ids = checkgroupService.findCheckitemIds(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,ids);
        } catch (Exception e) {
            log.error("Find Checkitem Ids by checkgroupId error.",e);
        }

        return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
    }

    /**
     * 编辑检查组
     * @param checkGroup
     * @param checkitemids
     * @return
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup, Integer [] checkitemids){
        try {
            checkgroupService.edit(checkGroup,checkitemids);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
           log.error("Edit checkgroup error.",e);
        }
        return Result.error(MessageConstant.EDIT_CHECKGROUP_FAIL);
        //return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
    }

    /**
     * 查询所有检查组
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<CheckGroup> checkGroups = checkgroupService.findAll();

            if(checkGroups!=null&&checkGroups.size()>0) {
                return Result.success(MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroups);
            }
        } catch (Exception e) {
            log.error("Find all checkgroup error.",e);
        }

        return Result.error(MessageConstant.QUERY_CHECKGROUP_FAIL);
    }
}