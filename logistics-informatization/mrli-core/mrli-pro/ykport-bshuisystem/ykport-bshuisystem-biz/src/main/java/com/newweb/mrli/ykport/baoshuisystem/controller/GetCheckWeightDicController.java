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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.CDatacodes;
import com.newweb.mrli.ykport.baoshuisystem.entity.CFeeWay;
import com.newweb.mrli.ykport.baoshuisystem.entity.CSubunit;
import com.newweb.mrli.ykport.baoshuisystem.entity.ClientInfo;
import com.newweb.mrli.ykport.baoshuisystem.service.CDatacodesService;
import com.newweb.mrli.ykport.baoshuisystem.service.CFeeWayService;
import com.newweb.mrli.ykport.baoshuisystem.service.CSubunitService;
import com.newweb.mrli.ykport.baoshuisystem.service.ClientInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 客户信息表
 *
 * @author pigx code generator
 * @date 2021-07-30 08:53:57
 */
@RestController
@AllArgsConstructor
@RequestMapping("/getCheckWeightDic" )
@Api(value = "getCheckWeightDic", tags = "查询衡重申请单字典数据")
public class GetCheckWeightDicController {

	private final CFeeWayService cFeeWayService;

	private final CDatacodesService cDatacodesService;

	private final CSubunitService cSubunitService;
	/**
	 * 整体查询结算方式码表
	 * @return
	 */
	@ApiOperation(value = "整体查询结算方式码表", notes = "整体查询结算方式码表")
	@GetMapping("/getCFeeWayAll" )
	public R getCFeeWayAll() {
		List<CFeeWay> list = cFeeWayService.list();
		return R.ok(list);
	}

	/**
	 * 整体查询作业类型码表
	 * @return
	 */
	@ApiOperation(value = "整体查询作业类型码表", notes = "整体查询作业类型码表")
	@GetMapping("/getCDatacodesAll" )
	@DS("slave2")
	public R getCDatacodesAll() {
		List<CDatacodes> list = cDatacodesService.list(Wrappers.query(new CDatacodes()).like("FLDNAME","BANG_TYP2"));
		return R.ok(list);
	}

	/**
	 * 整体查询作业类型码表
	 * @return
	 */
	@ApiOperation(value = "整体查询货管库区", notes = "整体查询货管库区")
	@GetMapping("/getCSubunitAll" )
	public R getCSubunitAll() {
		List<CSubunit> list = cSubunitService.list(Wrappers.query(new CSubunit()).eq("UNIT_COD","14").eq("MARK_ID","0"));
		return R.ok(list);
	}

	/**
	 * 整体查询作业类型码表
	 * @return
	 */
	@ApiOperation(value = "整体查询入港方式码表", notes = "整体查询入港方式码表")
	@GetMapping("/getInportWayAll" )
	@DS("slave2")
	public R getInportWayAll() {
		List<CDatacodes> list = cDatacodesService.list(Wrappers.query(new CDatacodes()).like("FLDNAME","IOPORT_WAY"));
		return R.ok(list);
	}
}
