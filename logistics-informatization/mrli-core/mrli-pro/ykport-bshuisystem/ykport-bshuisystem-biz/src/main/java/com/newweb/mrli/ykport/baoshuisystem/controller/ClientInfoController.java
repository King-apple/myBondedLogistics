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
import com.newweb.mrli.ykport.baoshuisystem.entity.ClientInfo;
import com.newweb.mrli.ykport.baoshuisystem.entity.OrganizerInfo;
import com.newweb.mrli.ykport.baoshuisystem.service.ClientInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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
@RequestMapping("/clientinfo" )
@Api(value = "clientinfo", tags = "客户信息表管理")
public class ClientInfoController {

    private final  ClientInfoService clientInfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param clientInfo 客户信息表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('clientinfo_view')" )
    public R getClientInfoPage(Page page, ClientInfo clientInfo) {
        return R.ok(clientInfoService.page(page, Wrappers.query(clientInfo).isNull("DEL_TIM")));
    }
    /**
     * 通过id查询客户信息表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('clientinfo_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(clientInfoService.getById(fId));
    }

    /**
     * 新增客户信息表
     * @param clientInfo 客户信息表
     * @return R
     */
    @ApiOperation(value = "新增客户信息表", notes = "新增客户信息表")
    @SysLog("新增客户信息表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('clientinfo_add')" )
    public R save(@RequestBody ClientInfo clientInfo) {
    	Long fId = clientInfoService.selectFid();
    	clientInfo.setFId(fId);
    	clientInfo.setOperNam(SecurityUtils.getUser().getUsername());
    	clientInfo.setRecordTim(new Date());
        return R.ok(clientInfoService.save(clientInfo));
    }

    /**
     * 修改客户信息表
     * @param clientInfo 客户信息表
     * @return R
     */
    @ApiOperation(value = "修改客户信息表", notes = "修改客户信息表")
    @SysLog("修改客户信息表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('clientinfo_edit')" )
    public R updateById(@RequestBody ClientInfo clientInfo) {
    	clientInfo.setUpdateNam(SecurityUtils.getUser().getUsername());
    	clientInfo.setUpdateTim(new Date());
        return R.ok(clientInfoService.updateById(clientInfo));
    }

    /**
     * 通过id删除客户信息表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除客户信息表", notes = "通过id删除客户信息表")
    @SysLog("通过id删除客户信息表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('clientinfo_del')" )
    public R removeById(@PathVariable Long fId) {
    	ClientInfo clientInfo = new ClientInfo();
    	clientInfo.setDelNam(SecurityUtils.getUser().getUsername());
    	clientInfo.setDelTim(new Date());
    	clientInfo.setFId(fId);
        return R.ok(clientInfoService.removeById(fId));
    }

	/**
	 * 整体查询
	 * @return
	 */
	@ApiOperation(value = "整体查询", notes = "整体查询")
	@GetMapping("/all" )
	public R getOrganizerInfoAll() {
		ClientInfo clientInfo = new ClientInfo();
		final List<ClientInfo> clientInfos = clientInfoService.selectAll(clientInfo);
		return R.ok(clientInfos);
	}
}
