package com.zyb.service.impl;

import com.zyb.dao.EmployeeDao;
import com.zyb.entity.Employee;
import com.zyb.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@author: zhouyongbao
 *@description:
 *@date: 2020/1/4 000414:51
 *
 */
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public Integer insert(Employee employee) {
        employee.setPassword("000000");
        return employeeDao.insert(employee);
    }

    @Override
    public Integer delete(String sn) {
        return employeeDao.deleteById(sn);
    }
}
