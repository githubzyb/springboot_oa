package com.zyb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyb.entity.DealRecord;
import com.zyb.entity.DealRecordEmp;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *@author: zhouyongbao
 *@description:
 *@date: 2020/1/7 000710:59
 *
 */
public interface DealRecordDao extends BaseMapper<DealRecord> {

    @Select("select d.*,e.name ename,e.post epost from deal_record d\n" +
            "        left join employee e on d.deal_sn = e.sn\n" +
            "        where d.claim_voucher_id=#{cvid} order by d.deal_time;")
    List<DealRecordEmp> selectByEmpId(Integer cvid);
}
