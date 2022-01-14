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
import com.newweb.mrli.ykport.baoshuisystem.entity.BusinessBillsBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.InvoiceDetailBody;
import com.newweb.mrli.ykport.baoshuisystem.service.InvoiceDetailBodyService;
import com.newweb.mrli.ykport.baoshuisystem.vo.InvoiceDetailHeadVo;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 开票明细体表
 *
 * @author pigx code generator
 * @date 2021-09-22 09:23:25
 */
@RestController
@AllArgsConstructor
@RequestMapping("/invoicedetailbody" )
@Api(value = "invoicedetailbody", tags = "开票明细体表管理")
public class InvoiceDetailBodyController {

    private final  InvoiceDetailBodyService invoiceDetailBodyService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param invoiceDetailBody 开票明细体表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getInvoiceDetailBodyPage(Page page, InvoiceDetailBody invoiceDetailBody) {
        return R.ok(invoiceDetailBodyService.page(page, Wrappers.query(invoiceDetailBody)));
    }


    /**
     * 通过id查询开票明细体表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    public R getById(@PathVariable("fId" ) Integer fId) {
		QueryWrapper<InvoiceDetailBody> wrapper = new QueryWrapper<>();
		wrapper.eq("h_fid", fId);
		wrapper.isNull("del_nam");
		List<InvoiceDetailBody> list = invoiceDetailBodyService.list(wrapper);
		return R.ok(list);
    }

    /**
     * 新增开票明细体表
     * @param invoiceDetailBody 开票明细体表
     * @return R
     */
    @ApiOperation(value = "新增开票明细体表", notes = "新增开票明细体表")
    @SysLog("新增开票明细体表" )
    @PostMapping
    public R save(@RequestBody List<InvoiceDetailBody> invoiceDetailBody)    {
		for (InvoiceDetailBody s:invoiceDetailBody
		) {
			s.setOperNam(SecurityUtils.getUser().getUsername());
			s.setRecordTim(new Date());
		}
		invoiceDetailBodyService.saveBatch(invoiceDetailBody);
		return R.ok(invoiceDetailBody);
    }

    /**
     * 修改开票明细体表
     * @param invoiceDetailBody 开票明细体表
     * @return R
     */
    @ApiOperation(value = "修改开票明细体表", notes = "修改开票明细体表")
    @SysLog("修改开票明细体表" )
    @PutMapping
    public R updateById(@RequestBody List<InvoiceDetailBody> invoiceDetailBody) {
		boolean b = true;
		for (int i = 0; i < invoiceDetailBody.size(); i++) {
			if(invoiceDetailBody.get(i) != null && invoiceDetailBody.get(i).getDelNam()== null && invoiceDetailBody.get(i).getDelTim() == null){
				InvoiceDetailBody body = invoiceDetailBody.get(i);
				if (body.getFId() != null) {
					body.setUpdateTim(new Date());
					body.setUpdateNam(SecurityUtils.getUser().getUsername());
					b = invoiceDetailBodyService.updateById(body);
				}else if(body.getFId() == null && invoiceDetailBody.get(i).getDelNam()== null && invoiceDetailBody.get(i).getDelTim() == null) {
					body.setOperNam(SecurityUtils.getUser().getUsername());
					body.setRecordTim(new Date());
					invoiceDetailBodyService.save(invoiceDetailBody.get(i));
				}
			}
		}
		return R.ok(b);
    }

    /**
     * 通过id删除开票明细体表
     * @param
     * @return R
     */
    @ApiOperation(value = "通过id删除开票明细体表", notes = "通过id删除开票明细体表")
    @SysLog("通过id删除开票明细体表" )
	@PostMapping("/deleteBody")
    public R removeById(@RequestBody InvoiceDetailBody invoiceDetailBody) {
		invoiceDetailBody.setDelNam(SecurityUtils.getUser().getUsername());
		invoiceDetailBody.setDelTim(new Date());
		return R.ok(invoiceDetailBodyService.updateById(invoiceDetailBody));
    }
}
