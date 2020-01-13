package com.zyb.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyb.dao.EmployeeDao;
import com.zyb.entity.Employee;
import com.zyb.global.Contant;
import com.zyb.service.EmployeeService;
import com.zyb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.net.HttpCookie;
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
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/employee/selectAll")
    public Result selectAll(){
        List<Employee> employees = employeeDao.selectList(null);
        return new Result("success","",employees,null);
    }
    //分页查询所有用户
    @RequestMapping("/employee/selectAllPage")
    public Result selectAllPage(Integer current,Integer limit){
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        Page<Employee> page = new Page<>(current, limit);
        IPage<Employee> iPage = employeeDao.selectPage(page, queryWrapper);
        System.out.println("总页数"+iPage.getPages());
        System.out.println("总记录数"+iPage.getTotal());
        List<Employee> records = iPage.getRecords();
        return new Result("success","",records,iPage.getTotal());
    }
    //用户登录
    @RequestMapping("/employee/login")
    public Result login(HttpSession session,String sn, String password){
        System.out.println("sn"+sn+"password"+password);
        Employee employee = employeeDao.selectById(sn);
        if (employee!=null){
            if (employee.getPassword().equals(password)){
                session.setAttribute("employee",employee);
                return new Result("success","登录成功",null,employee);
            }
            return new Result("error","用户名或密码错误",null,null);
        }
        return new Result("error","用户不存在",null,null);
    }

    @RequestMapping("/employee/getEmployee" )
    public Result getEmployee(HttpSession session){
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee==null){
            return new Result("error","",null,null);
        }
        return new Result("success","",null,employee);
    }

    @RequestMapping("/employee/quit" )
    public Result quit(HttpSession session){
        session.setAttribute("employee",null);
        return new Result("success","",null,null);
    }


    //员工职位
    @RequestMapping("/employee/checkPost" )
    public Result checkPost(){
        return new Result("success","",Contant.getPosts(),null);
    }
    //添加用户
    @RequestMapping("/employee/add" )
    public Result addEmployee(Employee employee){
        Integer insert = employeeService.insert(employee);
        if (insert!=1){
            return new Result("error","",null,null);
        }
        return new Result("success","",null,null);
    }

    //删除员工
    @RequestMapping("/employee/deleteEmployee" )
    public Result deleteEmployee(String sn){
        System.out.println(sn);
        Integer delete = employeeService.delete(sn);
        if (delete!=1){
            return new Result("error","",null,null);
        }
        return new Result("success","",null,null);
    }
}
