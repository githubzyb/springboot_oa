package com.zyb.controller;
import	java.awt.Desktop.Action;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyb.dao.DepartmentDao;
import com.zyb.entity.Department;
import com.zyb.entity.Employee;
import com.zyb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *@author: zhouyongbao
 *@description:
 *@date: 2020/1/3 000317:37
 *
 */
@RestController
public class DepartmentController {

    @Autowired
    private DepartmentDao departmentDao;

    @RequestMapping("/department/selectAll")
    public Result selectAll(){
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        List<Department> departments = departmentDao.selectList(queryWrapper);
        return new Result("success","",departments,"");
    }
    //查看所有部门
    @RequestMapping("/department/selectAllPage")
    public Result selectAllPage(Integer current,Integer limit){
        Page<Department> page = new Page<>(current, limit);
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        IPage<Department> iPage = departmentDao.selectPage(page,queryWrapper);
        return new Result("success","",iPage.getRecords(),iPage.getTotal());
    }

    //添加部门
    @RequestMapping("/department/addDepartment" )
    public Result addDepartment(Department department){
        int insert = departmentDao.insert(department);
        if (insert==1){
            return new Result("success","",null,null);
        }
        return new Result("error","",null,null);
    }
}
