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
import com.newweb.mrli.ykport.baoshuisystem.entity.CountryInfo;
import com.newweb.mrli.ykport.baoshuisystem.entity.CustomsBrokerInfo;
import com.newweb.mrli.ykport.baoshuisystem.service.CustomsBrokerInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 报关代理企业信息表
 *
 * @author pigx code generator
 * @date 2021-08-28 18:10:22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/customsbrokerinfo" )
@Api(value = "customsbrokerinfo", tags = "报关代理企业信息表管理")
public class CustomsBrokerInfoController {

    private final  CustomsBrokerInfoService customsBrokerInfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param customsBrokerInfo 报关代理企业信息表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('customsbrokerinfo_view')" )
    public R getCustomsBrokerInfoPage(Page page, CustomsBrokerInfo customsBrokerInfo) {
        return R.ok(customsBrokerInfoService.page(page, Wrappers.query(customsBrokerInfo).isNull("DEL_TIM")));
    }


    /**
     * 通过id查询报关代理企业信息表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('customsbrokerinfo_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(customsBrokerInfoService.getById(fId));
    }

    /**
     * 新增报关代理企业信息表
     * @param customsBrokerInfo 报关代理企业信息表
     * @return R
     */
    @ApiOperation(value = "新增报关代理企业信息表", notes = "新增报关代理企业信息表")
    @SysLog("新增报关代理企业信息表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('customsbrokerinfo_add')" )
    public R save(@RequestBody CustomsBrokerInfo customsBrokerInfo) {

		CustomsBrokerInfo customsBrokerInfo1 = new CustomsBrokerInfo();
		customsBrokerInfo1.setCustomsBrokerCode(customsBrokerInfo.getCustomsBrokerCode());
		List<CustomsBrokerInfo> list = customsBrokerInfoService.list(Wrappers.query(customsBrokerInfo1));
		if(list.size() > 0){
			return R.failed("已存在相同代码");
		}
		customsBrokerInfo1 = new CustomsBrokerInfo();
		customsBrokerInfo1.setCustomsBrokerName(customsBrokerInfo.getCustomsBrokerName());
		list = customsBrokerInfoService.list(Wrappers.query(customsBrokerInfo1));
		if(list.size() > 0){
			return R.failed("已存在相同企业");
		}

		customsBrokerInfo.setOperNam(SecurityUtils.getUser().getUsername());
		customsBrokerInfo.setRecordTim(new Date());
        return R.ok(customsBrokerInfoService.save(customsBrokerInfo));
    }

    /**
     * 修改报关代理企业信息表
     * @param customsBrokerInfo 报关代理企业信息表
     * @return R
     */
    @ApiOperation(value = "修改报关代理企业信息表", notes = "修改报关代理企业信息表")
    @SysLog("修改报关代理企业信息表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('customsbrokerinfo_edit')" )
    public R updateById(@RequestBody CustomsBrokerInfo customsBrokerInfo) {
		customsBrokerInfo.setUpdateNam(SecurityUtils.getUser().getUsername());
		customsBrokerInfo.setUpdateTim(new Date());
        return R.ok(customsBrokerInfoService.updateById(customsBrokerInfo));
    }

    /**
     * 通过id删除报关代理企业信息表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除报关代理企业信息表", notes = "通过id删除报关代理企业信息表")
    @SysLog("通过id删除报关代理企业信息表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('customsbrokerinfo_del')" )
    public R removeById(@PathVariable Integer fId) {
		CustomsBrokerInfo customsBrokerInfo = new CustomsBrokerInfo();
		customsBrokerInfo.setFId(fId);
		customsBrokerInfo.setDelNam(SecurityUtils.getUser().getUsername());
		customsBrokerInfo.setDelTim(new Date());
        return R.ok(customsBrokerInfoService.updateById(customsBrokerInfo));
    }

	/**
	 * 整体查询
	 * @return
	 */
	@ApiOperation(value = "整体查询", notes = "整体查询")
	@GetMapping("/getAll" )
	public R getAll() {
		return R.ok(customsBrokerInfoService.list(Wrappers.query(new CustomsBrokerInfo()).isNull("DEL_NAM")));
	}
}
