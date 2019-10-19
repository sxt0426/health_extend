package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.common.constant.MessageConstant;
import com.itheima.common.constant.RedisConstant;
import com.itheima.common.utils.QiniuUtils;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    private static final Logger log = Logger.getLogger(SetmealController.class);

    @Reference
    private SetmealService setmealService;

    @Autowired
    JedisPool jedisPool;

    /**
     * 上传图片
     *
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile file) {
        //1.重命名文件 防止重名 覆盖 xxssdfsffsdf.jpg
        String filename = file.getOriginalFilename();
        //2.获取扩展名
        //在字符串中 查找指定 字符 在字符串中的索引
        int indx = filename.lastIndexOf(".");
        //截取扩展名
        String ext = filename.substring(indx);

        //3.生成自定义文件名
        String finalFilename = UUID.randomUUID().toString() + ext;

        //4.通过七牛云 文件上传
        try {
            QiniuUtils.upload2Qiniu(file.getBytes(),finalFilename);
        } catch (IOException e) {
            log.error("Upload img error.",e);
            return Result.error(MessageConstant.PIC_UPLOAD_FAIL);
        }

        //七牛云 上传图片成功保存到redis
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_IMG_TMP,finalFilename);

        //5.将上传成功的文件名返回到前端页面
        return Result.success(MessageConstant.PIC_UPLOAD_SUCCESS, finalFilename);
    }

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer [] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);
            return Result.success(MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            log.error("Add setmeal error.",e);
        }

        return Result.error(MessageConstant.ADD_SETMEAL_FAIL);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setmealService.pageQuery(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
    }
}