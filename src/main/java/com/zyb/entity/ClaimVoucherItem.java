package com.zyb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 *@author: zhouyongbao
 *@description:
 *@date: 2020/1/7 000710:50
 *
 */
@Data
public class ClaimVoucherItem {
    //编号
    @TableId(type = IdType.AUTO)
    private Integer id;
    //报销单
    private Integer claimVoucherId;
    //费用类型
    private String item;
    //金额
    private Double amount;
    //描述
    private String comment;
}
