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

import com.alibaba.fastjson.JSONArray;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.*;
import com.newweb.mrli.ykport.baoshuisystem.service.ApprovalProcessSetService;
import com.newweb.mrli.ykport.baoshuisystem.service.BSUserService;
import com.newweb.mrli.ykport.baoshuisystem.service.BillApproveService;
import com.newweb.mrli.ykport.baoshuisystem.service.SmsdbService;
import com.newweb.mrli.ykport.baoshuisystem.vo.BillApproveAppProSetVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.BillApproveVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.CustomsAccountingStatisticsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * ?????????????????????
 *
 * @author ly
 * @date 2021-09-17 10:48:12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/billapprove" )
@Api(value = "billapprove", tags = "???????????????????????????")
public class BillApproveController {

    private final BillApproveService billApproveService;
    private final ApprovalProcessSetService approvalProcessSetService;
	@Autowired
	private BSUserService bSUserService;
	@Autowired
	private SmsdbService smsdbService;
	private final BSUserService bsUserService;
    /**
     * ????????????
     * @param page ????????????
     * @param billApprove ?????????????????????
     * @return
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('billapprove_view')" )
    public R getBillApprovePage(Page page, BillApproveVo billApprove) {
		if (!"1".equals(billApprove.getCheckStatus())) {
			billApprove.setOperator(SecurityUtils.getUser().getUsername());
			Collection<? extends GrantedAuthority> authorities = SecurityUtils.getAuthentication().getAuthorities();
			for (GrantedAuthority item : authorities) {
				if(item.toString().equals("settlement_view_admin")){
					billApprove.setOperator(null);
					break;
				}
				if (item.toString().equals("settlement_view_deptAdmin")){
					BSUser bsUser = new BSUser();
					bsUser.setUsername(SecurityUtils.getUser().getUsername());
					billApprove.setOperator(null);
					billApprove.setDeptId(getUserSubDeptId(bsUser).getDeptSubId()+"");
					break;
				}
			}
		}
		IPage<BillApproveVo> billApproveVoIPage = billApproveService.selectPageVo(page, billApprove);
        return R.ok(billApproveVoIPage);
    }

	@DS("slave")
	public BSUser getUserSubDeptId(BSUser bsUser) {
		return bsUserService.list(Wrappers.query(bsUser)).get(0);
	}
	@GetMapping("/getAllBills" )
	public R getAllBills() {
		Map<String, Object> map = new HashMap();
		//???????????????
		List<BusinessBillsHead> list1 = billApproveService.selectPayment();
		//?????????????????????
		List<BusinessBillsHead> list2 = billApproveService.selectSettlement();
		//???????????????
		List<IoyardSettlementBillsHead> list3 = billApproveService.selectInOut();

		map.put("angle",list1);
		map.put("brixton",list2);
		map.put("camden",list3);
		//??????????????????list???????????????????????????
		List<Object> listAll = Stream.of(list1, list2,list3).flatMap(Collection::stream).collect(Collectors.toList());
		map.put("dalston",listAll);
		return R.ok(map);
	}

    /**
     * ??????id???????????????????????????
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "??????id??????", notes = "??????id??????")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('billapprove_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
		BillApprove byId = billApproveService.getById(fId);
		return R.ok(byId);
    }

    /**
     * ???????????????????????????
     * @param billApprove ?????????????????????
     * @return R
     */
    @ApiOperation(value = "???????????????????????????", notes = "???????????????????????????")
    @SysLog("???????????????????????????" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('billapprove_add')" )
    public R save(@RequestBody BillApprove billApprove) {
		billApprove.setApproveStatus(0);
    	billApprove.setOperator(SecurityUtils.getUser().getUsername());
		billApprove.setOperNam(SecurityUtils.getUser().getUsername());
		billApprove.setRecordTim(new Date());
		billApproveService.save(billApprove);
    	return R.ok(billApprove);
    }

    /**
     * ???????????????????????????
     * @param billApprove ?????????????????????
     * @return R
     */
    @ApiOperation(value = "???????????????????????????", notes = "???????????????????????????")
    @SysLog("???????????????????????????" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('billapprove_edit')" )
    public R updateById(@RequestBody BillApprove billApprove) {
		billApprove.setUpdateNam(SecurityUtils.getUser().getUsername());
		billApprove.setUpdateTim(new Date());
        return R.ok(billApproveService.updateById(billApprove));
    }
	/**
	 * ??????id??????????????????
	 * @param  billApproveAppProSetVo
	 * @return R
	 */
	@ApiOperation(value = "??????id??????????????????", notes = "??????id??????????????????")
	@SysLog("??????id??????????????????" )
	@PostMapping("setAudit" )
	public R setAudit(@RequestBody BillApproveAppProSetVo billApproveAppProSetVo) {
		billApproveService.setAudit(billApproveAppProSetVo);
		return R.ok();
	}


	/**
	 * ??????id???????????????????????????
	 * @param fId id
	 * @return R
	 */
	@ApiOperation(value = "??????id???????????????????????????", notes = "??????id???????????????????????????")
	@SysLog("??????id???????????????????????????" )
	@DeleteMapping("/{fId}" )
	@PreAuthorize("@pms.hasPermission('billapprove_del')" )
	public R removeById(@PathVariable Integer fId) {
		BillApprove byId = billApproveService.getById(fId);
		byId.setIsCancel("1");
		boolean b = billApproveService.updateById(byId);
		return R.ok(b);
	}


	//	@ApiOperation(value = "????????????", notes = "????????????")
//	@GetMapping("/page" )
//	@PreAuthorize("@pms.hasPermission('billapprove_view')" )
//	public R getExaminationPage(Page page, BillApproveVo billApprove) {
//		IPage<BillApproveVo> billApproveVoIPage = billApproveService.getExaminationPage(page, billApprove);
//		return R.ok(billApproveVoIPage);
//	}
	@ApiOperation(value = "??????id???????????????????????????", notes = "??????id???????????????????????????")
	@SysLog("??????")
	@PostMapping("/export")
	@PreAuthorize("@pms.hasPermission('billapprove_export')")
	public void exportBill(@RequestBody BillApproveVo billApproveVo, HttpServletResponse response) throws IOException {
		billApproveService.exportBill(billApproveVo, response);
	}

	@PostMapping("/updateInfo")
	public R updateInfo(@RequestBody BillApproveVo billApproveVo) {
		Integer fId = billApproveVo.getFId();
		QueryWrapper<ApprovalProcessSet> query = new QueryWrapper<>();
		query.eq("BILL_FID",fId);
		List<ApprovalProcessSet> list = approvalProcessSetService.list(query);
		for (int i = 0; i <list.size() ; i++) {
			ApprovalProcessSet approvalProcessSet = list.get(i);
			UpdateWrapper<ApprovalProcessSet> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("F_ID",approvalProcessSet.getFId());
			updateWrapper.set("OPINION","");
			updateWrapper.set("OPINION_EXPLAIN","");
			updateWrapper.set("IS_AGREE",0);
			updateWrapper.set("APPROVER","");
			updateWrapper.set("APPROVAL_TIM","");
			if(list.get(i).getOrderBy() !=null&&list.get(i).getOrderBy() == 1){
				BSUser bsUser1 = new BSUser();
				bsUser1.setUsername(list.get(i).getUsername());
				List<BSUser> userList = bSUserService.list(Wrappers.query(bsUser1));
				Smsdb smsdb = new Smsdb();
				if(userList.get(0).getPhone() != null && !"".equals(userList.get(0).getPhone().trim())){
					smsdb.setCallnum(userList.get(0).getPhone());
					smsdb.setContent("??????????????????????????? --??????????????????");
					Date date = new Date();
					date.setMinutes(date.getMinutes()+1);
					smsdb.setCreatetime(date);
					smsdb.setFlag("0");
					smsdb.setMsgtype("????????????");
					smsdbService.save(smsdb);
				}

			}
			approvalProcessSetService.update(updateWrapper);

		}
		return R.ok();
	}

	/**
	 * ??????????????????
	 * @param billApprove
	 * @return R
	 */
	@ApiOperation(value = "??????????????????", notes = "??????????????????")
	@SysLog("??????????????????" )
	@PostMapping("/submitCheck" )
	public R submitCheck(@RequestBody BillApprove billApprove) {
		return billApproveService.buinessCheck(billApprove);
	}
}
