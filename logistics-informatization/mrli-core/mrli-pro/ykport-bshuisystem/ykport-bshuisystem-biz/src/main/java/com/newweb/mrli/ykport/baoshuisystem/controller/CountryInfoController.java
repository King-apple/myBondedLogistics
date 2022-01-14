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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.CargoNamCustoms;
import com.newweb.mrli.ykport.baoshuisystem.entity.ClientInfo;
import com.newweb.mrli.ykport.baoshuisystem.entity.CountryInfo;
import com.newweb.mrli.ykport.baoshuisystem.service.CountryInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 国别信息表
 *
 * @author pigx code generator
 * @date 2021-08-28 18:10:12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/countryinfo" )
@Api(value = "countryinfo", tags = "国别信息表管理")
public class CountryInfoController {

	private final CountryInfoService countryInfoService;

	/**
	 * 分页查询
	 *
	 * @param page        分页对象
	 * @param countryInfo 国别信息表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('countryinfo_view')")
	public R getCountryInfoPage(Page page, CountryInfo countryInfo) {
		return R.ok(countryInfoService.page(page, Wrappers.query(countryInfo).isNull("DEL_TIM")));
	}


	/**
	 * 通过id查询国别信息表
	 *
	 * @param fId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{fId}")
	@PreAuthorize("@pms.hasPermission('countryinfo_view')")
	public R getById(@PathVariable("fId") Integer fId) {
		return R.ok(countryInfoService.getById(fId));
	}

	/**
	 * 新增国别信息表
	 *
	 * @param countryInfo 国别信息表
	 * @return R
	 */
	@ApiOperation(value = "新增国别信息表", notes = "新增国别信息表")
	@SysLog("新增国别信息表")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('countryinfo_add')")
	public R save(@RequestBody CountryInfo countryInfo) {

		CountryInfo countryInfo1 = new CountryInfo();
		countryInfo1.setCountryCode(countryInfo.getCountryCode());
		List<CountryInfo> list = countryInfoService.list(Wrappers.query(countryInfo1));
		if(list.size() > 0){
			return R.failed("已存在相同代码");
		}
		countryInfo1 = new CountryInfo();
		countryInfo1.setCountryName(countryInfo.getCountryName());
		list = countryInfoService.list(Wrappers.query(countryInfo1));
		if(list.size() > 0){
			return R.failed("已存在相同国家");
		}

		countryInfo.setOperNam(SecurityUtils.getUser().getUsername());
		countryInfo.setRecordTim(new Date());
		return R.ok(countryInfoService.save(countryInfo));
	}

	/**
	 * 修改国别信息表
	 *
	 * @param countryInfo 国别信息表
	 * @return R
	 */
	@ApiOperation(value = "修改国别信息表", notes = "修改国别信息表")
	@SysLog("修改国别信息表")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('countryinfo_edit')")
	public R updateById(@RequestBody CountryInfo countryInfo) {
		countryInfo.setUpdateNam(SecurityUtils.getUser().getUsername());
		countryInfo.setUpdateTim(new Date());
		return R.ok(countryInfoService.updateById(countryInfo));
	}

	/**
	 * 通过id删除国别信息表
	 *
	 * @param fId id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除国别信息表", notes = "通过id删除国别信息表")
	@SysLog("通过id删除国别信息表")
	@DeleteMapping("/{fId}")
	@PreAuthorize("@pms.hasPermission('countryinfo_del')")
	public R removeById(@PathVariable Integer fId) {
		CountryInfo countryInfo = new CountryInfo();
		countryInfo.setFId(fId);
		countryInfo.setDelNam(SecurityUtils.getUser().getUsername());
		countryInfo.setDelTim(new Date());
		return R.ok(countryInfoService.updateById(countryInfo));
	}

	/**
	 * 整体查询
	 *
	 * @return
	 */
	@ApiOperation(value = "外部页面查询全部", notes = "外部页面查询全部")
	@GetMapping("/all")
	public R getCountryInfoAll() {
		CountryInfo countryInfo = new CountryInfo();
		List<CountryInfo> countryInfos = countryInfoService.selectAll(countryInfo);
		return R.ok(countryInfos);
	}

}
