package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckitemDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckitemService.class)
@Transactional
public class CheckitemServiceImpl implements CheckitemService {

    @Autowired
    private CheckitemDao checkitemDao;

    /**
     * 新增检查项
     *
     * @param checkItem
     */
    @Override
    public void add(CheckItem CheckItem) {
        checkitemDao.add(CheckItem);
    }

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);

        com.github.pagehelper.Page page = (Page) checkitemDao.selectByCondition(queryString);

        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据id删除检查项
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        //1.判断检查项是否和检查组有关联
        Long result = checkitemDao.findCountByCheckitemId(id);

        if (result > 0) {
            //有关联检查组 不能删除
            throw new RuntimeException("有检查组关联，不能删除");
        }
        //2.删除数据

        checkitemDao.deleteById(id);
    }

    /**
     * 根据检查项id查询检查项信息
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(Integer id) {
        return checkitemDao.findById(id);
    }

    /**
     * 编辑检查项
     * @param checkItem
     */
    @Override
    public void edit(CheckItem checkItem) {
        checkitemDao.edit(checkItem);
    }

    /**
     * 查询所有检查项
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return checkitemDao.findAll();
    }
}