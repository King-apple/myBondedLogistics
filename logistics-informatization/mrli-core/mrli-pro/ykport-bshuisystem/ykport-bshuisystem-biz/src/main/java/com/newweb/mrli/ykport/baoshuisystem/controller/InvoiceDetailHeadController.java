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

import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.*;
import com.newweb.mrli.ykport.baoshuisystem.service.*;
import com.newweb.mrli.ykport.baoshuisystem.vo.ExternalPaymentVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.InvoiceDetailHeadVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.IoyardListHeadBodyVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.IoyardSettlementBilldHeadBodyVo;
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
 * 开票明细头表
 *
 * @author pigx code generator
 * @date 2021-09-22 09:23:31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/invoicedetailhead")
@Api(value = "invoicedetailhead", tags = "开票明细头表管理")
public class InvoiceDetailHeadController {

	private final InvoiceDetailHeadService invoiceDetailHeadService;

	private final BusinessBillsHeadService businessBillsHeadService;

	private final IoyardSettlementBillsHeadService ioYardService;

	private final BSUserService bsUserService;
	
	
	
	private final SettlementBodyRateService settlementBodyRateService;

	private final OrganizerInfoService organizerInfoService;

	private final BillDataHeadService billDataHeadService;
	
	/**
	 * @param invoiceDetailHead 开票明细头表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('invoicedetailhead_view')")
	public R getInvoiceDetailHeadPage(Page page, InvoiceDetailHeadVo invoiceDetailHead) {
		invoiceDetailHead.setOperNam(SecurityUtils.getUser().getUsername());
		Collection<? extends GrantedAuthority> authorities = SecurityUtils.getAuthentication().getAuthorities();
		for (GrantedAuthority item : authorities) {
			if(item.toString().equals("settlement_view_admin")){
				invoiceDetailHead.setOperNam(null);
				break;
			}
			if (item.toString().equals("settlement_view_deptAdmin")){
				BSUser bsUser = new BSUser();
				bsUser.setUsername(SecurityUtils.getUser().getUsername());
				invoiceDetailHead.setOperNam(null);
				invoiceDetailHead.setDeptId(getUserSubDeptId(bsUser).getDeptSubId()+"");
				break;
			}
		}
		IPage<InvoiceDetailHeadVo> list = invoiceDetailHeadService.selectPageVo(page, invoiceDetailHead);
		return R.ok(list);
	}

	@DS("slave")
	public BSUser getUserSubDeptId(BSUser bsUser) {
		return bsUserService.list(Wrappers.query(bsUser)).get(0);
	}
	/**
	 * 通过id查询开票明细头表
	 *
	 * @param fId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{fId}")
	@PreAuthorize("@pms.hasPermission('invoicedetailhead_view')")
	public R getById(@PathVariable("fId") Integer fId) {
		InvoiceDetailHead byId = invoiceDetailHeadService.getById(fId);
		return R.ok(byId);
	}

	/**
	 * 新增开票明细头表
	 *
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "新增开票明细头表", notes = "新增开票明细头表")
	@SysLog("新增开票明细头表")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('invoicedetailhead_add')")
	public R save(@RequestBody InvoiceDetailHeadVo invoiceDetailHeadvo) {
		InvoiceDetailHead invoiceDetailHead = new InvoiceDetailHead();
		BeanUtils.copyProperties(invoiceDetailHeadvo,invoiceDetailHead);
		String[] payment = invoiceDetailHeadvo.getPayment();
		String join = StringUtils.join(Arrays.asList(payment), ",");
		if(join != null) invoiceDetailHead.setPayment(join);
		invoiceDetailHead.setOperNam(SecurityUtils.getUser().getUsername());
		invoiceDetailHead.setRecordTim(new Date());
		invoiceDetailHeadService.save(invoiceDetailHead);
		return R.ok(invoiceDetailHead);
	}

	/**
	 * 修改开票明细头表
	 *
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "修改开票明细头表", notes = "修改开票明细头表")
	@SysLog("修改开票明细头表")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('invoicedetailhead_edit')")
	public R updateById(@RequestBody InvoiceDetailHeadVo invoiceDetailHeadvo) {
		InvoiceDetailHead invoiceDetailHead = new InvoiceDetailHead();
		String[] payment = invoiceDetailHeadvo.getPayment();
		String paymentS = "";
		if(payment.length == 2){
			for (int i = 0; i <payment.length ; i++) {
				paymentS += payment[i]+",";
			}
			paymentS = paymentS.substring(0,paymentS.length() - 1);
		}else if( payment.length == 1) {
			paymentS = payment[0];
		}
		BeanUtils.copyProperties(invoiceDetailHeadvo,invoiceDetailHead);
		invoiceDetailHead.setPayment(paymentS);

		invoiceDetailHeadService.updateById(invoiceDetailHead);
		return R.ok(invoiceDetailHead);
	}

	/**
	 * 通过id删除开票明细头表
	 *
	 * @param fId id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除开票明细头表", notes = "通过id删除开票明细头表")
	@SysLog("通过id删除开票明细头表")
	@DeleteMapping("/{fId}")
	@PreAuthorize("@pms.hasPermission('invoicedetailhead_del')")
	public R removeById(@PathVariable Integer fId) {
		//通过id查询数据
		InvoiceDetailHead byId = invoiceDetailHeadService.getById(fId);
		byId.setDelNam(SecurityUtils.getUser().getUsername());
		byId.setDelTim(new Date());
		boolean b = invoiceDetailHeadService.updateById(byId);
		return R.ok(b);
	}


	@GetMapping("/getInfo")
	public R getInfo() {
		Map<String, Object> map = new HashMap();
		//对外付款结算单
		List<ExternalPaymentVo> list1 = businessBillsHeadService.selectPayment();
		//业务结算单 发票类型部位等于空
		List<ExternalPaymentVo> list2 = businessBillsHeadService.selectSettlement();
		//出入库结算
		List<IoyardSettlementBilldHeadBodyVo> list3 = ioYardService.selectInoutBill();
		for (IoyardSettlementBilldHeadBodyVo ioyardSettlementBilldHeadBodyVo : list3) {
			List<IoyardSettlementBillsBody> ioYardSBBodyList = ioyardSettlementBilldHeadBodyVo.getIoYardSBBodyList();
			for (IoyardSettlementBillsBody ioyardSettlementBillsBody : ioYardSBBodyList) {
				QueryWrapper<SettlementBodyRate> queryWrapper = new QueryWrapper<>();
				Long fId = ioyardSettlementBillsBody.getFId();
				queryWrapper.eq("R_FID",fId);
				queryWrapper.isNull("DEL_NAM");
				List<SettlementBodyRate> list = settlementBodyRateService.list(queryWrapper);
				ioyardSettlementBillsBody.setList(list);
			}
		}

		map.put("angle",list1);
		map.put("brixton",list2);
		map.put("camden",list3);
		//合并以上三个list，为了取出所有编号
		List<Object> listAll = Stream.of(list1, list2,list3).flatMap(Collection::stream).collect(Collectors.toList());
		map.put("dalston",listAll);
		return R.ok(map);
	}


	@PostMapping("/exportExcel")
	public void expoprtExcel(@RequestBody InvoiceDetailHeadVo detailHeadVo, HttpServletResponse response) throws IOException {
		invoiceDetailHeadService.exportExcel(detailHeadVo,response);
	}

	/**
	 * 上传数据至发票系统
	 * @param billDataHead 开票明细头表
	 * @return R
	 */
	@ApiOperation(value = "上传数据至发票系统", notes = "上传数据至发票系统")
	@SysLog("上传数据至发票系统" )
	@PostMapping("/saveBillData")
	public R saveBillData(@RequestBody BillDataHead billDataHead) {
		return R.ok(billDataHeadService.saveBillData(billDataHead));
	}
}
