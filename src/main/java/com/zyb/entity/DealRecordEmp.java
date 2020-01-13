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
 *@date: 2020/1/7 000710:48
 *
 */
@Data
public class DealRecordEmp {
    //编号
    private Integer id;
    //报销单
    private Integer claimVoucherId;
    //处理人
    private String dealSn;
    //处理时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date dealTime;
    //处理类型
    private String dealWay;
    //处理结果
    private String dealResult;
    //备注
    private String comment;

    private String  ename;
    private String  epaost;

}
