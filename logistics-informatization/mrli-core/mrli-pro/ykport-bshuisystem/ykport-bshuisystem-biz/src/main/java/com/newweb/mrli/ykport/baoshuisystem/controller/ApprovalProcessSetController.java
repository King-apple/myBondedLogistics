/*
 *    Copyright (c) 2018-2025, mrli All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: mrli
 */

package com.newweb.mrli.ykport.baoshuisystem.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.*;
import com.newweb.mrli.ykport.baoshuisystem.service.ApprovalProcessSetService;
import com.newweb.mrli.ykport.baoshuisystem.service.BSUserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.ykport.baoshuisystem.entity.ApprovalProcessSet;
import com.newweb.mrli.ykport.baoshuisystem.entity.ApprovalRecord;
import com.newweb.mrli.ykport.baoshuisystem.service.ApprovalProcessSetService;
import com.newweb.mrli.ykport.baoshuisystem.service.BillApproveService;
import com.newweb.mrli.ykport.baoshuisystem.vo.BillApproveAppProSetVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.BillApproveVo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 审批流程设定表
 *
 * @author pigx code generator
 * @date 2021-10-08 15:18:31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/approvalprocessset" )
@Api(value = "approvalprocessset", tags = "审批流程设定表管理")
public class ApprovalProcessSetController {

    private final ApprovalProcessSetService approvalProcessSetService;
	private final BSUserService bsUserService;
	private final BillApproveService billApproveService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param approvalProcessSet 审批流程设定表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('approvalprocessset_view')" )
    public R getApprovalProcessSetPage(Page page, ApprovalProcessSet approvalProcessSet) {
        return R.ok(approvalProcessSetService.page(page, Wrappers.query(approvalProcessSet)));
    }


    /**
     * 通过id查询审批流程设定表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('approvalprocessset_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(approvalProcessSetService.getById(fId));
    }

    /**
     * 新增审批流程设定表
     * @param approvalProcessSet 审批流程设定表
     * @return R
     */
    @ApiOperation(value = "新增审批流程设定表", notes = "新增审批流程设定表")
    @SysLog("新增审批流程设定表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('approvalprocessset_add')" )
    public R save(@RequestBody ApprovalProcessSet approvalProcessSet) {
        return R.ok(approvalProcessSetService.save(approvalProcessSet));
    }

    /**
     * 修改审批流程设定表
     * @param approvalProcessSet 审批流程设定表
     * @return R
     */
    @ApiOperation(value = "修改审批流程设定表", notes = "修改审批流程设定表")
    @SysLog("修改审批流程设定表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('approvalprocessset_edit')" )
    public R updateById(@RequestBody ApprovalProcessSet approvalProcessSet) {
        return R.ok(approvalProcessSetService.updateById(approvalProcessSet));
    }

    /**
     * 通过id删除审批流程设定表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除审批流程设定表", notes = "通过id删除审批流程设定表")
    @SysLog("通过id删除审批流程设定表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('approvalprocessset_del')" )
    public R removeById(@PathVariable Integer fId) {
        return R.ok(approvalProcessSetService.removeById(fId));
    }


	/**
	 * 新增审批流程设定表（通过业务单据审批单管理）
	 * @param info 审批流程设定表
	 * @return R
	 */
	@ApiOperation(value = "新增审批流程设定表", notes = "新增审批流程设定表")
	@SysLog("新增审批流程设定表" )
	@PostMapping("/saveForOpear" )
	public R saveForOpear(@RequestBody Object[] info) {
		return approvalProcessSetService.saveForOpear(info);
	}

	/**
	 * 查询对应的审核人员
	 * @param billApproveVo
	 * @return R
	 */
	@ApiOperation(value = "通过参数查询", notes = "通过参数查询")
	@GetMapping("/getAuditList" )
//	@PreAuthorize("@pms.hasPermission('generator_approvalprocessset_view')" )
	public R getAuditList(Page page, BillApproveAppProSetVo billApproveVo) {
		ApprovalProcessSet approvalProcessSet = new ApprovalProcessSet();
		List<ApprovalProcessSet> listArr = new ArrayList<ApprovalProcessSet>();
		System.out.println(approvalProcessSet);
		approvalProcessSet.setUserId(SecurityUtils.getUser().getId());//SecurityUtils.getUser().getId()
		approvalProcessSet.setProcessNo("1");
		List<ApprovalProcessSet> listBillId = approvalProcessSetService.getBillIdList(approvalProcessSet); //根据审核设定表 分组求出结算审批单billId
		for(int i=0;i<listBillId.size();i++){ //循环根据业务单据审批单billId
			approvalProcessSet.setBillFid(listBillId.get(i).getBillFid());
			//根据用户id和billfid 求出当前是否有审核数据
			ApprovalProcessSet approvalProcessSetInfo = approvalProcessSetService.getApprovalProcessSetList(approvalProcessSet);
			System.out.println(approvalProcessSetInfo);
			ApprovalProcessSet approvalSet = new ApprovalProcessSet();
			approvalSet.setBillFid(listBillId.get(i).getBillFid());
//			List<ApprovalProcessSet> listOrdeyByGroup= approvalProcessSetService.list(Wrappers.query(approvalSet).groupBy("ORDER_BY").orderByAsc("ORDER_BY"));
			QueryWrapper<ApprovalProcessSet> wrapper = new QueryWrapper<ApprovalProcessSet>()//查询对应审核设定所有的流程排序
					.select("ORDER_BY")
					.eq("BILL_FID",listBillId.get(i).getBillFid())
					.groupBy("ORDER_BY")
					.orderByAsc("ORDER_BY");
			List<Map<String,Object>> listOrdeyByGroup = approvalProcessSetService.listMaps(wrapper);

			if(approvalProcessSetInfo!=null){//当前用户是否有审核任务
				//start  用于查询当前任务的上一个任务是否已经审核，审核了轮到当前用户，没有审核完不显示
				int count =0;
				int orderbyNum = 1;
				for(Map<String,Object> detail:listOrdeyByGroup){
					if(1==approvalProcessSetInfo.getOrderBy() && count==0){
						orderbyNum=0;
						break;
					}
					if(Integer.parseInt(detail.get("ORDER_BY").toString())==approvalProcessSetInfo.getOrderBy()){
						orderbyNum = Integer.parseInt(listOrdeyByGroup.get(count-1).get("ORDER_BY").toString());
					}
					count++;
				}

				//查询当前审核条件上一个条件是否已经审核过，审核过，说明显示当前审核，如果没审核过或者审核失败都不显示
				approvalProcessSet.setOrderBy(orderbyNum);//approvalProcessSetInfo.getOrderBy()-1
				ApprovalProcessSet detail = approvalProcessSetService.getApprovalProcessSetCheck(approvalProcessSet);
				//end  用于查询当前任务的上一个任务是否已经审核，审核了轮到当前用户，没有审核完不显示
				System.out.println(detail);
				if(detail==null || (StringUtils.isNotBlank(detail.getIsAgree()) && "1".equals(detail.getIsAgree()))){
					listArr.add(approvalProcessSetInfo);
				}
			}
		}
		List<Integer> la = new ArrayList<Integer>();
		if(listArr.size()>0){
			for(ApprovalProcessSet info:listArr){
				la.add(info.getFId());
			}
		}
		IPage<BillApproveAppProSetVo> billApproveVoIPage = null;
		if(billApproveVo.getApproveStatus()==1){ //审批中
			billApproveVoIPage = approvalProcessSetService.getBillApproveList(page, la, billApproveVo);
		} else if(billApproveVo.getApproveStatus()==2){//审批成功
			billApproveVo.setApproveStatus(0);
			billApproveVo.setUsername(SecurityUtils.getUser().getUsername());
			billApproveVoIPage = approvalProcessSetService.getBillApproveListForSuccess(page, la, billApproveVo);
		} else {

			List<Integer> billIds = new ArrayList<Integer>();
			for(ApprovalProcessSet billId:listBillId){
				billIds.add(billId.getBillFid().intValue());
			}
			BillApprove billApproveDetail = new BillApprove();
			billApproveDetail.setApproveStatus(billApproveVo.getApproveStatus());
			if(billApproveVo.getParams()!=null && billApproveVo.getParams().length>0){
				billApproveVoIPage = billApproveService.page(page,Wrappers.query(billApproveDetail)
						.eq(StringUtils.checkValNotNull(billApproveVo.getBillType()),"BILL_TYPE",billApproveVo.getBillType())
						.eq(StringUtils.checkValNotNull(billApproveVo.getBillCod()),"BILL_COD",billApproveVo.getBillCod())
						.eq(StringUtils.checkValNotNull(billApproveVo.getContNo()),"CONT_NO",billApproveVo.getContNo())
						.eq(StringUtils.checkValNotNull(billApproveVo.getSecondCod()),"SECOND_COD",billApproveVo.getSecondCod())
						.eq(StringUtils.checkValNotNull(billApproveVo.getCargoNam()),"CARGO_NAM",billApproveVo.getCargoNam())
						.between(billApproveVo.getParams()!=null,"BILL_DAT", DateUtil.parse(billApproveVo.getParams()[0],"yyyy-MM-dd") ,DateUtil.parse(billApproveVo.getParams()[1],"yyyy-MM-dd")));

			} else {
				billApproveVoIPage = billApproveService.page(page,Wrappers.query(billApproveDetail)
						.eq(StringUtils.checkValNotNull(billApproveVo.getBillType()),"BILL_TYPE",billApproveVo.getBillType())
						.eq(StringUtils.checkValNotNull(billApproveVo.getBillCod()),"BILL_COD",billApproveVo.getBillCod())
						.eq(StringUtils.checkValNotNull(billApproveVo.getContNo()),"CONT_NO",billApproveVo.getContNo())
						.eq(StringUtils.checkValNotNull(billApproveVo.getSecondCod()),"SECOND_COD",billApproveVo.getSecondCod())
						.eq(StringUtils.checkValNotNull(billApproveVo.getCargoNam()),"CARGO_NAM",billApproveVo.getCargoNam()));
//						.between(billApproveVo.getParams()!=null,"BILL_DAT",billApproveVo.getParams()[0],billApproveVo.getParams()[1])

			}


		}

		return R.ok(billApproveVoIPage);


	}


	/**
	 * 查询对应人员审核列表
	 * @param billFid id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("getOpearAudit/{billFid}" )
//	@PreAuthorize("@pms.hasPermission('generator_approvalprocessset_view')" )
	public R getOpearAudit(@PathVariable("billFid") Integer billFid) {
		List<Map<Integer,Object>> map = approvalProcessSetService.getOpearAudit(billFid);
		return R.ok(map);
	}



	/**
	 * 查询审批记录组
	 * @param approvalProcessSet
	 * @return R
	 */
	@ApiOperation(value = "查询审批记录组", notes = "查询审批记录组")
	@SysLog("查询审批记录组" )
	@PostMapping("/getProcessSetGroup")
	public R getProcessSetGroup(@RequestBody ApprovalProcessSet approvalProcessSet) {
		return R.ok(approvalProcessSetService.selectApprovalProcessSetGroup(approvalProcessSet));
	}

	/**
	 * 分页查询
	 * @param approvalProcessSet 审批记录表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/getProcessSets" )
	public R getProcessSets(ApprovalProcessSet approvalProcessSet) {
		return approvalProcessSetService.getProcessSets(approvalProcessSet);
	}

	/**
	 * 分页查询
	 * @param approvalProcessSet 审批记录表
	 * @return
	 */
	@ApiOperation(value = "根据billFid查询", notes = "根据billFid查询")
	@PostMapping("/getProcessByBillFidAndUserInfo" )
	public R getProcessByBillFidAndUserInfo(@RequestBody ApprovalProcessSet approvalProcessSet) {
		approvalProcessSet.setUsername(SecurityUtils.getUser().getUsername());
		return R.ok(approvalProcessSetService.list(Wrappers.query(approvalProcessSet).isNull("DEL_NAM")));
	}
}
