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
import com.newweb.mrli.ykport.baoshuisystem.entity.OrganizerInfo;
import com.newweb.mrli.ykport.baoshuisystem.service.OrganizerInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 承办单位信息表
 *
 * @author pigx code generator
 * @date 2021-07-29 10:08:16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/organizerinfo" )
@Api(value = "organizerinfo", tags = "承办单位信息表管理")
public class OrganizerInfoController {

    private final   OrganizerInfoService organizerInfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param organizerInfo 承办单位信息表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('organizerinfo_view')" )
    public R getOrganizerInfoPage(Page page, OrganizerInfo organizerInfo) {
        return R.ok(organizerInfoService.page(page, Wrappers.query(organizerInfo).isNull("DEL_TIM")));
    }


    /**
     * 通过id查询承办单位信息表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('organizerinfo_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(organizerInfoService.getById(fId));
    }

    /**
     * 新增承办单位信息表
     * @param organizerInfo 承办单位信息表
     * @return R
     */
    @ApiOperation(value = "新增承办单位信息表", notes = "新增承办单位信息表")
    @SysLog("新增承办单位信息表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('organizerinfo_add')" )
    public R save(@RequestBody OrganizerInfo organizerInfo) {
		OrganizerInfo organizerInfo1 = new OrganizerInfo();
		organizerInfo1.setOrganizerCode(organizerInfo.getOrganizerCode());
		List<OrganizerInfo> list = organizerInfoService.list(Wrappers.query(organizerInfo1));
		if(list.size() > 0){
			return R.failed("已存在相同编号");
		}
		organizerInfo1 = new OrganizerInfo();
		organizerInfo1.setOrganizer(organizerInfo.getOrganizer());
		list = organizerInfoService.list(Wrappers.query(organizerInfo1));
		if(list.size() > 0){
			return R.failed("已存在相同承办单位");
		}
		return organizerInfoService.insertInfo(organizerInfo);

    }

    /**
     * 修改承办单位信息表
     * @param organizerInfo 承办单位信息表
     * @return R
     */
    @ApiOperation(value = "修改承办单位信息表", notes = "修改承办单位信息表")
    @SysLog("修改承办单位信息表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('organizerinfo_edit')" )
    public R updateById(@RequestBody OrganizerInfo organizerInfo) {
    	organizerInfo.setUpdateNam(SecurityUtils.getUser().getUsername());
    	organizerInfo.setUpdateTim(new Date());
        return R.ok(organizerInfoService.updateById(organizerInfo));
    }

    /**
     * 通过id删除承办单位信息表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除承办单位信息表", notes = "通过id删除承办单位信息表")
    @SysLog("通过id删除承办单位信息表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('organizerinfo_del')" )
    public R removeById(@PathVariable Integer fId) {
    	OrganizerInfo organizerInfo = new OrganizerInfo();
    	organizerInfo.setDelNam(SecurityUtils.getUser().getUsername());
    	organizerInfo.setDelTim(new Date());
    	organizerInfo.setFId(fId);
        return R.ok(organizerInfoService.updateById(organizerInfo));
    }


	/**
	 * 整体查询
	 * @return
	 */
	@ApiOperation(value = "整体查询", notes = "整体查询")
	@GetMapping("/all" )
	@PreAuthorize("@pms.hasPermission('organizerinfo_view')" )
	public R getOrganizerInfoAll() {
		OrganizerInfo organizerInfo = new OrganizerInfo();
		final List<OrganizerInfo> organizerInfos = organizerInfoService.selectAll(organizerInfo);
		return R.ok(organizerInfos);
	}
}
