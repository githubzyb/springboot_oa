package com.zyb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 *@author: zhouyongbao
 *@description:
 *@date: 2020/1/3 000317:33
 *
 */
@Data
public class Department {
    //部门id
    @TableId
    private String sn;
    //部门名字
    private String name;
    //部门地址
    private String address;
}
