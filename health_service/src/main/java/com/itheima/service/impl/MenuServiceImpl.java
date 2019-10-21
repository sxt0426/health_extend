package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.MenuDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @author: Administrator
 * @date: 2019/10/20 0020 上午 10:01
 * @description:
 */
@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    /**
     * 添加菜单
     * @param menu
     */
    @Override
    public void add(Menu menu) {
        menuDao.add(menu);
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
        Page page = (Page) menuDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据id查找menu
     * @param id
     * @return
     */
    @Override
    public Menu findById(Integer id) {
        Menu menu = menuDao.queryById(id);
        return menu;
    }

    /**
     * 编辑菜单
     * @param menu
     */
    @Override
    public void edit(Menu menu) {
        menuDao.edit(menu);
    }

    /**
     * 根据id删除
     * @param id
     */
    @Override
    public Result deleteById(Integer id) {
        //判断有无关联
        Integer count = menuDao.queryMenuAndRoleById(id);
        if(count == 0){
            //没有
            menuDao.deleteById(id);
            return Result.success("删除成功");
        }else {
            return Result.error("有关联角色，不能删除");
        }
    }

    /**
     * 查询所有菜单
     * @return
     */
    @Override
    public List<Menu> findAll() {
        return menuDao.findAll();
    }
}
