package com.zyb.controller;

import com.zyb.dao.EmployeeDao;
import com.zyb.entity.Employee;
import com.zyb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *@author: zhouyongbao
 *@description:
 *@date: 2019/12/30 003010:25
 *
 */
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;

    @RequestMapping("/employee/selectAll")
    public Result selectAll(){
        List<Employee> employees = employeeDao.selectList(null);
        return new Result("success","",employees,null);
    }
    @RequestMapping("/employee/login")
    public Result login(String sn,String password){
        Employee employee = employeeDao.selectById(sn);
        if (employee!=null){
            if (employee.getPassword().equals(password)){
                return new Result("success","登录成功",null,employee);
            }
            return new Result("error","用户名或密码错误",null,null);
        }
        return new Result("error","用户不存在",null,null);
    }
}
