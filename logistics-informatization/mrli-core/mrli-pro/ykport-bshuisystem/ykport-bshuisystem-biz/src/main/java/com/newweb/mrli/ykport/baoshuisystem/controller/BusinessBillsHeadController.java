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

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.BSUser;
import com.newweb.mrli.ykport.baoshuisystem.entity.BusinessBillsHead;
import com.newweb.mrli.ykport.baoshuisystem.service.BSUserService;
import com.newweb.mrli.ykport.baoshuisystem.service.BusinessBillsHeadService;
import com.newweb.mrli.ykport.baoshuisystem.vo.ExternalPaymentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * 业务单据头表
 *
 * @author 李洋
 * @date 2021-09-06 09:12:35
 */
@RestController
@AllArgsConstructor
@RequestMapping("/businessbillshead")
@Api(value = "businessbillshead", tags = "业务单据头表管理")
public class BusinessBillsHeadController {
	@Autowired
	private final BusinessBillsHeadService businessBillsHeadService;

	private final BSUserService bsUserService;

	/**
	 * 分页查询
	 *
	 * @param page 分页对象
	 * @param
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('externalpayment_view')")
	public R getBusinessBillsHeadPage(Page page, ExternalPaymentVo externalPaymentVo) {
		String username = SecurityUtils.getUser().getUsername();
		externalPaymentVo.setOperNam(username);
		Collection<? extends GrantedAuthority> authorities = SecurityUtils.getAuthentication().getAuthorities();
		for (GrantedAuthority item : authorities) {
			if(item.toString().equals("settlement_view_admin")){
				externalPaymentVo.setOperNam(null);
				break;
			}
			if (item.toString().equals("settlement_view_deptAdmin")){
				BSUser bsUser = new BSUser();
				bsUser.setUsername(SecurityUtils.getUser().getUsername());
				externalPaymentVo.setOperNam(null);
				externalPaymentVo.setDeptId(getUserSubDeptId(bsUser).getDeptSubId()+"");
				break;
			}

		}
		IPage<ExternalPaymentVo> externalPaymentVoIPage = businessBillsHeadService.selectPageVo(page, externalPaymentVo);
		return R.ok(externalPaymentVoIPage);
	}

	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/pageBusiness")
	@PreAuthorize("@pms.hasPermission('business_view')")
	public R selectBusinessPageVo(Page page, ExternalPaymentVo externalPaymentVo) {
		String username = SecurityUtils.getUser().getUsername();
		externalPaymentVo.setOperNam(username);

		Collection<? extends GrantedAuthority> authorities = SecurityUtils.getAuthentication().getAuthorities();
		for (GrantedAuthority item : authorities) {
			if(item.toString().equals("settlement_view_admin")){
				externalPaymentVo.setOperNam(null);
				break;
			}
			if (item.toString().equals("settlement_view_deptAdmin")){
				BSUser bsUser = new BSUser();
				bsUser.setUsername(SecurityUtils.getUser().getUsername());
				externalPaymentVo.setOperNam(null);
				externalPaymentVo.setDeptId(getUserSubDeptId(bsUser).getDeptSubId()+"");
				break;
			}
		}
		IPage<ExternalPaymentVo> externalPaymentVoIPage = businessBillsHeadService.selectBusinessPageVo(page, externalPaymentVo);
		return R.ok(externalPaymentVoIPage);
	}

	@DS("slave")
	public BSUser getUserSubDeptId(BSUser bsUser) {
		return bsUserService.list(Wrappers.query(bsUser)).get(0);
	}
	/**
	 * 通过id查询业务单据头表
	 *
	 * @param fId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{fId}")
	@PreAuthorize("@pms.hasPermission('externalpayment_view')")
	public R getById(@PathVariable("fId") Long fId) {
		return R.ok(businessBillsHeadService.getById(fId));
	}

	/**
	 * 新增业务单据头表
	 *
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "新增业务单据头表", notes = "新增业务单据头表")
	@SysLog("新增业务单据头表")
	@PostMapping
	@Transactional
	@PreAuthorize("@pms.hasPermission('externalpayment_add')")
	public synchronized  R save(@RequestBody ExternalPaymentVo externalPaymentVo) {
		//通过合同编号来查有几个相同的合同
		String contCod = externalPaymentVo.getContCod();
		String fk = "-FK-";
		String js = "-JS-";
		if (externalPaymentVo.getInvoiceType() == null) {
			BusinessBillsHead businessBillsHead = new BusinessBillsHead();
			BeanUtils.copyProperties(externalPaymentVo, businessBillsHead);
			QueryWrapper<BusinessBillsHead> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("cont_cod", contCod);
			List<BusinessBillsHead> list = businessBillsHeadService.list(queryWrapper);
			//合同编号-FK-01开始排序
			if (list.size() + 1 < 10) {
				businessBillsHead.setBillCod(externalPaymentVo.getContCod() + fk + "0" + (list.size() + 1));
			} else {
				businessBillsHead.setBillCod(externalPaymentVo.getContCod() + fk + (list.size() + 1));
			}
//			Integer deptId = SecurityUtils.getUser().getDeptId();
//			businessBillsHead.setDept(deptId.toString());
			businessBillsHead.setOperator(SecurityUtils.getUser().getUsername());
			businessBillsHead.setOperNam(SecurityUtils.getUser().getUsername());
			businessBillsHead.setRecordTim(new Date());
			boolean save = businessBillsHeadService.save(businessBillsHead);
			return R.ok(businessBillsHead);
		} else {
			BusinessBillsHead businessBillsHead1 = new BusinessBillsHead();
			BeanUtils.copyProperties(externalPaymentVo, businessBillsHead1);
			QueryWrapper<BusinessBillsHead> queryWrapper1 = new QueryWrapper<>();
			queryWrapper1.eq("cont_cod", contCod);
			List<BusinessBillsHead> list = businessBillsHeadService.list(queryWrapper1);
			//合同编号-FK-01开始排序
			if (list.size() + 1 < 10) {
				businessBillsHead1.setBillCod(externalPaymentVo.getContCod() + js + "0" + (list.size() + 1));
			} else {
				businessBillsHead1.setBillCod(externalPaymentVo.getContCod() + js + (list.size() + 1));
			}
//			Integer deptId = SecurityUtils.getUser().getDeptId();
//			businessBillsHead1.setDept(deptId.toString());
			businessBillsHead1.setOperator(SecurityUtils.getUser().getUsername());
			businessBillsHead1.setOperNam(SecurityUtils.getUser().getUsername());
			businessBillsHead1.setRecordTim(new Date());
			boolean save = businessBillsHeadService.save(businessBillsHead1);
			return R.ok(businessBillsHead1);
		}


	}

	/**
	 * 修改业务单据头表
	 *
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "修改业务单据头表", notes = "修改业务单据头表")
	@SysLog("修改业务单据头表")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('externalpayment_edit')")
	public R updateById(@RequestBody ExternalPaymentVo externalPaymentVo) {
		BusinessBillsHead bbHead = new BusinessBillsHead();
		BeanUtils.copyProperties(externalPaymentVo, bbHead);
		bbHead.setUpdateNam(SecurityUtils.getUser().getUsername());
		bbHead.setUpdateTim(new Date());
		boolean b = businessBillsHeadService.updateById(bbHead);
		return R.ok(bbHead);
	}

	/**
	 * 通过id删除业务单据头表
	 *
	 * @param fId id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除业务单据头表", notes = "通过id删除业务单据头表")
	@SysLog("通过id删除业务单据头表")
	@DeleteMapping("/{fId}")
	@PreAuthorize("@pms.hasPermission('externalpayment_del')")
	public R removeById(@PathVariable Long fId) {
		//通过id查询数据
		BusinessBillsHead byId = businessBillsHeadService.getById(fId);
		byId.setDelNam(SecurityUtils.getUser().getUsername());
		byId.setDelTim(new Date());
		boolean b = businessBillsHeadService.updateById(byId);
		return R.ok(b);
	}

	@ApiOperation(value = "导出", notes = "导出")
	@SysLog("下载")
	@PostMapping("/download")
	@PreAuthorize("@pms.hasPermission('externalpayment_exp')")
	public void exportById(@RequestBody ExternalPaymentVo externalPaymentVo, HttpServletResponse response) throws IOException {
		businessBillsHeadService.exportById(externalPaymentVo, response);
	}

	@ApiOperation(value = "导出", notes = "导出")
	@SysLog("下载")
	@PostMapping("/downloadBus")
	@PreAuthorize("@pms.hasPermission('business_exp')")
	public void exportByBusId(@RequestBody ExternalPaymentVo externalPaymentVo, HttpServletResponse response) throws IOException {
		businessBillsHeadService.exportByBusId(externalPaymentVo, response);
	}

	@ApiOperation(value = "分货种操作量表", notes = "分货种操作量表")
	@SysLog("分货种操作量表")
	@GetMapping("/getCargoKindOperation")
	@PreAuthorize("@pms.hasPermission('cargoKindOperation_view','incomeStatistcs_view')")
	public R getCargoKindOperation(Page page, ExternalPaymentVo externalPaymentVo){
		return R.ok(businessBillsHeadService.getCargoKindOperation(page,externalPaymentVo));
	}

	@ApiOperation(value = "导出分货种操作量表", notes = "导出分货种操作量表")
	@SysLog("导出分货种操作量表")
	@PostMapping("/cargoKindOperationPrintToExcel")
	@PreAuthorize("@pms.hasPermission('cargoKindOperation_print')")
	public void cargoKindOperationPrintToExcel(@RequestBody ExternalPaymentVo externalPaymentVo) throws IOException {
		businessBillsHeadService.cargoKindOperationPrintToExcel(externalPaymentVo);
	}

	@ApiOperation(value = "收入统计查询", notes = "收入统计查询")
	@SysLog("收入统计查询")
	@GetMapping("/getIncomeStatistcs")
	@PreAuthorize("@pms.hasPermission('incomeStatistcs_view')")
	public R getIncomeStatistcs(Page page, ExternalPaymentVo externalPaymentVo){
		return R.ok(businessBillsHeadService.getIncomeStatistcs(page,externalPaymentVo));
	}

	@ApiOperation(value = "导出货代部收入统计查询表", notes = "导出货代部收入统计查询表")
	@SysLog("导出分货种操作量表")
	@PostMapping("/incomeStatistcsPrintToExcel")
	@PreAuthorize("@pms.hasPermission('incomeStatistcs_print')")
	public void incomeStatistcsPrintToExcel(@RequestBody ExternalPaymentVo externalPaymentVo) throws IOException {
		businessBillsHeadService.incomeStatistcsPrintToExcel(externalPaymentVo);
	}
}
