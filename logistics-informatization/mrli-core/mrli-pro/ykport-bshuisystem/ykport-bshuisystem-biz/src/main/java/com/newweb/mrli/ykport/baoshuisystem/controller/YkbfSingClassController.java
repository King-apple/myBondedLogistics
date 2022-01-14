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

import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.ykport.baoshuisystem.entity.SingClassInfo;
import com.newweb.mrli.ykport.baoshuisystem.entity.YkbfSingClass;
import com.newweb.mrli.ykport.baoshuisystem.service.YkbfSingClassService;
import com.newweb.mrli.ykport.baoshuisystem.vo.ExternalPaymentVo;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


/**
 * 单个工班查询
 *
 * @author pigx code generator
 * @date 2021-10-24 15:49:10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ykbfsingclass" )
@Api(value = "ykbfsingclass", tags = "单个工班查询管理")
public class YkbfSingClassController {

    private final  YkbfSingClassService ykbfSingClassService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ykbfSingClass 单个工班查询
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('ykbfsingclass_view')" )
    public R getYkbfSingClassPage(Page page, YkbfSingClass ykbfSingClass) {
		String[] startDte = ykbfSingClass.getStartDte();
		String[] startDate2 = new String[2];
		for (int i = 0; i <startDte.length ; i++) {
			startDate2[i] = startDte[i]+"0000";
		}
		ykbfSingClass.setStartDte(startDate2);
		IPage<SingClassInfo> externalPaymentVoIPage = ykbfSingClassService.getPage(page,ykbfSingClass);
		return R.ok(externalPaymentVoIPage);
    }


    /**
     * 通过id查询单个工班查询
     * @param lxdm id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{lxdm}" )
    @PreAuthorize("@pms.hasPermission('ykbfsingclass_view')" )
    public R getById(@PathVariable("lxdm" ) String lxdm) {
        return R.ok(ykbfSingClassService.getById(lxdm));
    }

	/**
	 * 分页查询
	 * @param ykbfSingClass 导出日操作量表
	 * @return
	 */
	@ApiOperation(value = "导出日操作量表", notes = "导出日操作量表")
	@PostMapping("/printToExcel" )
	@PreAuthorize("@pms.hasPermission('ykbfsingclass_view')" )
	public void printToExcel(@RequestBody YkbfSingClass ykbfSingClass) throws IOException {
		ykbfSingClassService.printToExcel(ykbfSingClass);
	}

	/**
	 * @param
	 * @return
	 */
	@ApiOperation(value = "导出日操作量表", notes = "导出日操作量表")
	@PostMapping("/exportList" )
	public void exportList(@RequestBody List<SingClassInfo> singClassInfo) throws IOException {
		ykbfSingClassService.exportList(singClassInfo);
	}

	/**
	 * 不分页查询
	 * @param page 分页对象
	 * @param ykbfSingClass 单个工班查询
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/noPage" )
	public R getAll( YkbfSingClass ykbfSingClass) {
		String[] startDte = ykbfSingClass.getStartDte();
		String[] startDate2 = new String[2];
		for (int i = 0; i <startDte.length ; i++) {
			startDate2[i] = startDte[i]+"0000";
		}
		ykbfSingClass.setStartDte(startDate2);
		List<SingClassInfo> externalPaymentVoIPage = ykbfSingClassService.getNoPage(ykbfSingClass);
		return R.ok(externalPaymentVoIPage);
	}
}
