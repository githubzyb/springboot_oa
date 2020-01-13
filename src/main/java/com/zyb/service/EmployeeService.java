package com.zyb.service;

import com.zyb.entity.Employee;
import org.springframework.stereotype.Service;

/**
 *@author: zhouyongbao
 *@description:
 *@date: 2020/1/4 000414:50
 *
 */

public interface EmployeeService {

    Integer insert(Employee employee);
    Integer delete(String sn);
}
