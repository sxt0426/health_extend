package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckgroupDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckgroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检查组业务实现
 */
@Service(interfaceClass = CheckgroupService.class)
@Transactional
public class CheckgroupServiceImpl implements CheckgroupService {

    @Autowired
    private CheckgroupDao checkgroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1.保存检查组基本信息 获取检查组id t_checkgroup
        checkgroupDao.add(checkGroup);

        //2.设置检查组与检查项关联关系 t_checkgroup_checkitem
        setCheckgroupAndCheckitem(checkGroup.getId(),checkitemIds);
    }

    private void setCheckgroupAndCheckitem(Integer groupId,Integer [] checkitemIds){
        if(checkitemIds==null || checkitemIds.length==0){
            return;
        }

        for (Integer id:checkitemIds){
            Map<String,Integer> map = new HashMap<>();
            map.put("checkgroup_id",groupId);
            map.put("checkitem_id",id);

            checkgroupDao.setCheckgroupAndCheckitem(map);
        }

    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        //1.将currentPage，pageSize 参数与当前线程进行绑定， 通过ThreadLocal 封装page对象
        PageHelper.startPage(currentPage,pageSize);

        //2.拦截器 拦截查询操作 判断是否是分页查询
        //如果是分页查询 就会走分页逻辑
        //count(*) 判断是否有分页数据 如果没有 直接返回
        //count(*)>0 拼装 分页sql limit ？ ？
        Page page = checkgroupDao.queryByCondition(queryString);

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkgroupDao.findById(id);
    }

    /**
     * 根据检查组id，查询所有关联的检查项ids
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckitemIds(Integer id) {
        return checkgroupDao.findCheckitemIds(id);
    }

    /**
     * 编辑检查组
     * @param checkGroup
     * @param checkitemids
     */
    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemids) {
        //1.更新检查组基本信息 根据id更新
        checkgroupDao.edit(checkGroup);

        //2.删除中间表数据 t_checkgroup_checkitem 根据检查组id，删除
        checkgroupDao.deleteAssocation(checkGroup.getId());

        //3.设置检查组与检查项关联信息
        setCheckgroupAndCheckitem(checkGroup.getId(),checkitemids);
    }

    /**
     * 查询所有检查组
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkgroupDao.findAll();
    }
}