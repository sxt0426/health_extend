package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.common.constant.RedisConstant;
import com.itheima.dao.OrderDao;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 套餐业务实现
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private OrderDao orderDao;

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //1.保存套餐基本信息 获取setmeal的id
        setmealDao.add(setmeal);

        //2.保存套餐与检查组关联信息
        setSetmealAndCheckgroup(setmeal.getId(),checkgroupIds);

        //将保存成功的图片名称到redis
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_IMG_DB,setmeal.getImg());
    }

    /**
     * 保存套餐与检查组关联信息
     * @param setmealId
     * @param checkgroupIds
     */
    private void setSetmealAndCheckgroup(Integer setmealId, Integer[] checkgroupIds) {
        if(checkgroupIds==null||checkgroupIds.length==0){
            return;
        }

        for (Integer id:checkgroupIds) {
            Map<String,Integer>  map = new HashMap<>();
            map.put("setmeal_id",setmealId);
            map.put("checkgroup_id",id);

            setmealDao.setSetmealAndCheckgroup(map);
        }
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);

        Page page = (Page)setmealDao.queryByCondition(queryString);

        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 查询所有套餐
     * @return
     */
    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    /**
     * 根据套餐id,查询套餐信息
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    /**
     * 套餐统计
     * List<Map  <name,value>>
     * Map  <name,value> name套餐的名字 value 套餐的统计数据
     */
    @Override
    public List<Map<String, Object>> getSetmealReport() {
        //构建套餐id，在t_order表中，进行分组 查询出有多少个套餐
        //将分组后的套餐数量加起来 每个套餐的总共卖出的数量

        List<Map<String,Object>> setmealCounts = orderDao.getSetmealReport();

        return setmealCounts;
    }
}