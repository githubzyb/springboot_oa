package com.zyb;

import com.zyb.dao.EmployeeDao;
import com.zyb.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringbootOaApplicationTests {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    void contextLoads() {
    }

    @Test
    public void test1(){
        Employee employee = new Employee();
        employee.setSn("10005");
        employee.setName("张三丰");
        employee.setDepartmentSn("10001");
        employeeDao.insert(employee);
    }

    @Test
    public void test2(){
        List<Employee> employees = employeeDao.selectList(null);

        for (Employee employee : employees){
            System.out.println(employee);
        }
    }

}
