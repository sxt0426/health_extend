package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.common.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckitemService;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 检查项管理
 */
@RestController
@RequestMapping("/checkitem")
public class CheckitemController {

    /**
     * 打日志的目的：1.跟踪代码；2.程序错误分析 logback,log4j2
     */
    private static final Logger log = Logger.getLogger(CheckitemController.class);

    @Reference
    CheckitemService checkitemService;

    /**
     * 新增检查项
     * @param checkItem
     * @return
     * @RequestBody 将json字符串 转换为 CheckItem 对象
     */
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        //log.info();//程序启动的时候，查看关键程序节点 只输出一次
        //log.debug();//调试程序的时候使用
        //log.trace();//跟踪程序运行达到哪一步
        //log.warn()//对程序流程不会产生影响
        try {
            checkitemService.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            //企业中出现异常 一定要将异常输出到日志文件中，e.printStackTrace()这种方式严格禁止
            //e.printStackTrace();
            log.error("Add checkitem error.",e);
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return checkitemService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
    }

    /**
     * 删除检查项
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        try {
            checkitemService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        }catch (RuntimeException e){
            log.error("delete checkitem error.",e);
            return new Result(false, e.getMessage());
        }catch (Exception e) {
            log.error("delete checkitem cause error.",e);
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    /**
     * 根据检查项id 查询检查项信息
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            CheckItem checkitem = checkitemService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkitem);
        } catch (Exception e){
            log.error("Find checkitem by id cause error.",e);
        }
        return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
    }

    /**
     * 编辑检查项
     * @param checkItem
     * @return
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        try {
            checkitemService.edit(checkItem);
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e){
            log.error("Edite checkitem by id cause error.",e);
        }
        return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
    }

    /**
     * 查询所有检查项
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll(){
        try {
            List<CheckItem> checkItems = checkitemService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItems);
        } catch (Exception e) {
            log.error("Find all checkitem error.");
        }

        return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
    }
}