package com.zyb.controller;
import	java.awt.Desktop.Action;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyb.dao.*;
import com.zyb.entity.ClaimVoucher;
import com.zyb.entity.ClaimVoucherItem;
import com.zyb.entity.DealRecord;
import com.zyb.entity.Employee;
import com.zyb.global.Contant;
import com.zyb.util.Result;
import io.netty.handler.ipfilter.IpFilterRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 *@author: zhouyongbao
 *@description:
 *@date: 2020/1/7 000716:01
 *
 */
@RestController
public class ClaimVoucherController {

    @Autowired
    private ClaimVoucherDao claimVoucherDao;
    @Autowired
    private ClaimVoucherItemDao claimVoucherItemDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private DealRecordDao dealRecordDao;


    //费用类型
    @RequestMapping("/claimVoucher/getItems")
    public Result getItems(){
        List<String> items = Contant.getItems();
        return new Result("success","",items,null);
    }

    @RequestMapping("/claimVoucher/addClaimVoucher")
    public Result addClaimVoucher(HttpSession session, String cause,
                                  Double totalAmount,
                                  @RequestParam(value = "items",required = false) String items){
        System.out.println("  费用详细"+items);
        JSONArray arrayList= JSONArray.parseArray(items);
        //转换为目标对象list
        List<ClaimVoucherItem> groupList = JSONObject.parseArray(arrayList.toJSONString(), ClaimVoucherItem.class);
        Employee employee = (Employee) session.getAttribute("employee");
        ClaimVoucher claimVoucher = new ClaimVoucher();
        claimVoucher.setCause(cause);
        claimVoucher.setTotalAmount(totalAmount);
        claimVoucher.setCreateSn(employee.getSn());
        claimVoucher.setCreateTime(new Date());
        claimVoucher.setNextDealSn(employee.getSn());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        int insert = claimVoucherDao.insert(claimVoucher);
        for (ClaimVoucherItem cvi:groupList
             ) {
            cvi.setClaimVoucherId(claimVoucher.getId());
            claimVoucherItemDao.insert(cvi);
        }

        return new Result("success","",null,null);
    }
    //待处理列表
    @RequestMapping("/claimVoucher/dealClaimPage")
    public Result dealClaimPage(HttpSession session,Integer current,Integer limit){
        Employee employee = (Employee) session.getAttribute("employee");
        Page<ClaimVoucher> page = new Page<>(current, limit);
        QueryWrapper<ClaimVoucher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("next_deal_sn",employee.getSn());
        IPage<ClaimVoucher> iPage = claimVoucherDao.selectPage(page,queryWrapper);
        return new Result("success","",iPage.getRecords(),iPage.getTotal());
    }

    //个人报销单
    @RequestMapping("/claimVoucher/selfClaimPage")
    public Result selfClaimPage(HttpSession session,Integer current,Integer limit){
        Employee employee = (Employee) session.getAttribute("employee");
        Page<ClaimVoucher> page = new Page<>(current, limit);
        QueryWrapper<ClaimVoucher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("create_sn",employee.getSn());
        IPage<ClaimVoucher> iPage = claimVoucherDao.selectPage(page,queryWrapper);
        return new Result("success","",iPage.getRecords(),iPage.getTotal());
    }
    //提交报销单
    @RequestMapping("/claimVoucher/submit")
    public Result submit(int id) {
        ClaimVoucher claimVoucher = claimVoucherDao.selectById(id);

        Employee employee = employeeDao.selectById(claimVoucher.getCreateSn());

        claimVoucher.setStatus(Contant.CLAIMVOUCHER_SUBMIT);
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("department_sn",employee.getDepartmentSn()).eq("post",Contant.POST_FM);
        List<Employee> employees = employeeDao.selectList(queryWrapper);
        claimVoucher.setNextDealSn(employees.get(0).getSn());
        claimVoucherDao.updateById(claimVoucher);

        DealRecord dealRecord = new DealRecord();
        dealRecord.setDealWay(Contant.DEAL_SUBMIT);
        dealRecord.setDealSn(employee.getSn());
        dealRecord.setClaimVoucherId(id);
        dealRecord.setDealResult(Contant.CLAIMVOUCHER_SUBMIT);
        dealRecord.setDealTime(new Date());
        dealRecord.setComment("无");
        dealRecordDao.insert(dealRecord);
        return new Result("success","",null,null);
    }

    //审核报销单
    @RequestMapping("/claimVoucher/check")
    public Result check(HttpSession session,HashMap<String,Object> map,Integer id){
        Employee employee = (Employee) session.getAttribute("employee");
        map.put("employee",employee);
        map.put("CliamVoucher",claimVoucherDao.selectByEmpId(id));
        map.put("Items",claimVoucherItemDao.selectList((new QueryWrapper<ClaimVoucherItem>()).eq("claim_voucher_id",id)));
        map.put("DealRecords",dealRecordDao.selectByEmpId(id));
        return new Result("success","",null,map);
    }
    //更改报销单
    @RequestMapping("/claimVoucher/updateClaim")
    public Result updateClaim(ClaimVoucher claimVoucher, String items) {
        System.out.println("claimVoucher"+claimVoucher+"items"+items);

        JSONArray arrayList= JSONArray.parseArray(items);
        //转换为目标对象list
        List<ClaimVoucherItem> groupList = JSONObject.parseArray(arrayList.toJSONString(), ClaimVoucherItem.class);

        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        claimVoucherDao.updateById(claimVoucher);

        QueryWrapper<ClaimVoucherItem> queryWrapper = new QueryWrapper<>();
        List<ClaimVoucherItem> olds = claimVoucherItemDao.selectList(queryWrapper.eq("claim_voucher_id",claimVoucher.getId()));
        for(ClaimVoucherItem old:olds){
            boolean isHave=false;
            for(ClaimVoucherItem item:groupList){
                if(item.getId()==old.getId()){
                    isHave=true;
                    break;
                }
            }
            if(!isHave){
                claimVoucherItemDao.deleteById(old.getId());
            }
        }
        for(ClaimVoucherItem item:groupList){
            item.setClaimVoucherId(claimVoucher.getId());
            System.out.println("iddd"+item.getId());
            if(item.getId()!=null){
                claimVoucherItemDao.updateById(item);
            }else{
                claimVoucherItemDao.insert(item);
            }
        }
        return new Result("success","",null,null);

    }

    //操作报销单
    @RequestMapping("/claimVoucher/deal")
    public Result deal(HttpSession session,DealRecord dealRecord) {
        System.out.println("dealRecord"+dealRecord);
        Employee emp = (Employee)session.getAttribute("employee");
        dealRecord.setDealSn(emp.getSn());
        ClaimVoucher claimVoucher = claimVoucherDao.selectById(dealRecord.getClaimVoucherId());
        Employee employee = employeeDao.selectById(dealRecord.getDealSn());
        dealRecord.setDealTime(new Date());

        if(dealRecord.getDealWay().equals(Contant.DEAL_PASS)){
            if(claimVoucher.getTotalAmount()<=Contant.LIMIT_CHECK || employee.getPost().equals(Contant.POST_GM)){
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_APPROVED);
                QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("post",Contant.POST_CASHIER);
                List<Employee> employees = employeeDao.selectList(queryWrapper);
                claimVoucher.setNextDealSn(employees.get(0).getSn());

                dealRecord.setDealResult(Contant.CLAIMVOUCHER_APPROVED);
            }else{
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_RECHECK);
                QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("post",Contant.POST_GM);
                List<Employee> employees = employeeDao.selectList(queryWrapper);
                claimVoucher.setNextDealSn(employees.get(0).getSn());

                dealRecord.setDealResult(Contant.CLAIMVOUCHER_RECHECK);
            }
        }else if(dealRecord.getDealWay().equals(Contant.DEAL_BACK)){
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_BACK);
            claimVoucher.setNextDealSn(claimVoucher.getCreateSn());

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_BACK);
        }else if(dealRecord.getDealWay().equals(Contant.DEAL_REJECT)){
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_TERMINATED);
            claimVoucher.setNextDealSn(null);

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_TERMINATED);
        }else if(dealRecord.getDealWay().equals(Contant.DEAL_PAID)){
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_PAID);
            claimVoucher.setNextDealSn(null);

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_PAID);
        }

        claimVoucherDao.updateById(claimVoucher);
        dealRecordDao.insert(dealRecord);
        return new Result("success","",null,null);
    }

}
