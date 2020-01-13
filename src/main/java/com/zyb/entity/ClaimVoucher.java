package com.zyb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *@author: zhouyongbao
 *@description:
 *@date: 2020/1/7 000710:49
 *
 */
@Data
public class ClaimVoucher {
    //编号
    @TableId(type = IdType.AUTO)
    private Integer id;
    //事由
    private String cause;
    //创建人
    private String createSn;
    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date createTime;
    //待处理人
    private String nextDealSn;
    //总金额
    private Double totalAmount;
    //状态
    private String status;
}
