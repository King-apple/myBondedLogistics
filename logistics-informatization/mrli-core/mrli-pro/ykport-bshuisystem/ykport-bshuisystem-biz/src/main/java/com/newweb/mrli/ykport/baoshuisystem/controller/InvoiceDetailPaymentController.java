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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.InvoiceDetailPayment;
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardSettlementBillsBody;
import com.newweb.mrli.ykport.baoshuisystem.service.InvoiceDetailPaymentService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 开票明细缴费方式表
 *
 * @author pigx code generator
 * @date 2021-09-22 09:23:42
 */
@RestController
@AllArgsConstructor
@RequestMapping("/invoicedetailpayment" )
@Api(value = "invoicedetailpayment", tags = "开票明细缴费方式表管理")
public class InvoiceDetailPaymentController {

    private final  InvoiceDetailPaymentService invoiceDetailPaymentService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param invoiceDetailPayment 开票明细缴费方式表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('generator_invoicedetailpayment_view')" )
    public R getInvoiceDetailPaymentPage(Page page, InvoiceDetailPayment invoiceDetailPayment) {
        return R.ok(invoiceDetailPaymentService.page(page, Wrappers.query(invoiceDetailPayment)));
    }


    /**
     * 通过id查询开票明细缴费方式表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    public R getById(@PathVariable("fId" ) Integer fId) {
		QueryWrapper<InvoiceDetailPayment> query = new QueryWrapper<>();
		query.eq("h_fid",fId);
		query.isNull("del_nam");
		List<InvoiceDetailPayment> list = invoiceDetailPaymentService.list(query);
		return R.ok(list);
    }

    /**
     * 新增开票明细缴费方式表
     * @param invoiceDetailPayment 开票明细缴费方式表
     * @return R
     */
    @ApiOperation(value = "新增开票明细缴费方式表", notes = "新增开票明细缴费方式表")
    @SysLog("新增开票明细缴费方式表" )
    @PostMapping
    public R save(@RequestBody List<InvoiceDetailPayment>  invoiceDetailPayment) {
		for (InvoiceDetailPayment s:invoiceDetailPayment
		) {
			s.setOperNam(SecurityUtils.getUser().getUsername());
			s.setRecordTim(new Date());
		}
		boolean b = invoiceDetailPaymentService.saveBatch(invoiceDetailPayment);
		return R.ok(b);
    }

    /**
     * 修改开票明细缴费方式表
     * @param invoiceDetailPayment 开票明细缴费方式表
     * @return R
     */
    @ApiOperation(value = "修改开票明细缴费方式表", notes = "修改开票明细缴费方式表")
    @SysLog("修改开票明细缴费方式表" )
    @PutMapping
    public R updateById(@RequestBody List<InvoiceDetailPayment>  invoiceDetailPayment) {
		boolean b= false;
		//如果fid不为空，并且删除时间和删除人是空，走修改
		for (int i = 0; i < invoiceDetailPayment.size(); i++) {
			if (invoiceDetailPayment.get(i).getFId() != null
					&& invoiceDetailPayment.get(i).getDelNam() == null
					&& invoiceDetailPayment.get(i).getDelTim() == null) {
				invoiceDetailPayment.get(i).setUpdateTim(new Date());
				invoiceDetailPayment.get(i).setUpdateNam(SecurityUtils.getUser().getUsername());
				b = invoiceDetailPaymentService.updateById(invoiceDetailPayment.get(i));
				//如果fid为空，并且删除时间和删除人是空，走新增
			} else if (invoiceDetailPayment.get(i).getFId() == null
					&& invoiceDetailPayment.get(i).getDelNam() == null
					&& invoiceDetailPayment.get(i).getDelTim() == null) {
				invoiceDetailPayment.get(i).setOperNam(SecurityUtils.getUser().getUsername());
				invoiceDetailPayment.get(i).setRecordTim(new Date());
				invoiceDetailPaymentService.save(invoiceDetailPayment.get(i));
			}else {
				return  R.failed("发生了未知错误");
			}
		}
		return R.ok(b);
    }

    /**
     * 通过id删除开票明细缴费方式表
     * @return R
     */
    @ApiOperation(value = "通过id删除开票明细缴费方式表", notes = "通过id删除开票明细缴费方式表")
    @SysLog("通过id删除开票明细缴费方式表" )
	@PostMapping("/deletePayment")
    public R removeById(@RequestBody InvoiceDetailPayment invoiceDetailPayment) {
		invoiceDetailPayment.setDelNam(SecurityUtils.getUser().getUsername());
		invoiceDetailPayment.setDelTim(new Date());
		return R.ok(invoiceDetailPaymentService.updateById(invoiceDetailPayment));
    }

}
