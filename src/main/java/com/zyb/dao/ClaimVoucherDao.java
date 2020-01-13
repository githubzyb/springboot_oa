package com.zyb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyb.entity.ClaimVoucher;
import com.zyb.entity.ClaimVoucherEmp;
import org.apache.ibatis.annotations.Select;

/**
 *@author: zhouyongbao
 *@description:
 *@date: 2020/1/7 000711:00
 *
 */
public interface ClaimVoucherDao  extends BaseMapper<ClaimVoucher>  {

    @Select("select cv.*,ce.name cname,ce.post cpost,d.name dname,d.post dpost\n" +
            "        from claim_voucher cv\n" +
            "        left join employee ce on ce.sn=cv.create_sn\n" +
            "        left join employee d on d.sn = cv.next_deal_sn\n" +
            "        where cv.id=#{id}")
    ClaimVoucherEmp selectByEmpId(Integer id);
}
