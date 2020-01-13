package com.zyb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 *@author: zhouyongbao
 *@description:
 *@date: 2019/12/27 002710:17
 *
 */
@Data
public class Employee {
    //工号
    @TableId
    private String sn;
    //密码
    private String password;
    //姓名
    private String name;
    //部门编号
    private String departmentSn;
    //职位
    private String post;
    //头像
    @TableField("headPortrait")
    private String headPortrait;


}
