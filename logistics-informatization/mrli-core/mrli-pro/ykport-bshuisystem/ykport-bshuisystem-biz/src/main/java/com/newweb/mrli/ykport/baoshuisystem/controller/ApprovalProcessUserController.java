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

import com.alibaba.nacos.common.utils.Objects;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.ykport.baoshuisystem.entity.ApprovalProcessUser;
import com.newweb.mrli.ykport.baoshuisystem.entity.BSUser;
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardHead;
import com.newweb.mrli.ykport.baoshuisystem.service.ApprovalProcessUserService;
import com.newweb.mrli.ykport.baoshuisystem.service.BSUserService;
import io.swagger.annotations.ApiModelProperty;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 审批流程与人员关联表
 *
 * @author zada
 * @date 2021-09-27 16:59:06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/approvalprocessuser" )
@Api(value = "approvalprocessuser", tags = "审批流程与人员关联表管理")
public class ApprovalProcessUserController {

    private final ApprovalProcessUserService approvalProcessUserService;
	private final BSUserService bsUserService;
    /**
     * 分页查询
     * @param page 分页对象
     * @param approvalProcessUser 审批流程与人员关联表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('approvalprocessuser_view')" )
    public R getApprovalProcessUserPage(Page page, ApprovalProcessUser approvalProcessUser) {
		IPage<T> s = approvalProcessUserService.page(page, Wrappers.query(approvalProcessUser));
        return R.ok(s);
    }


    /**
     * 通过id查询审批流程与人员关联表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('approvalprocessuser_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(approvalProcessUserService.getById(fId));
    }

    /**
     * 新增审批流程与人员关联表
     * @param approvalProcessUser 审批流程与人员关联表
     * @return R
     */
    @ApiOperation(value = "新增审批流程与人员关联表", notes = "新增审批流程与人员关联表")
    @SysLog("新增审批流程与人员关联表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('approvalprocessuser_add')" )
    public R save(@RequestBody ApprovalProcessUser approvalProcessUser) {
        return R.ok(approvalProcessUserService.save(approvalProcessUser));
    }

    /**
     * 修改审批流程与人员关联表
     * @param approvalProcessUser 审批流程与人员关联表
     * @return R
     */
    @ApiOperation(value = "修改审批流程与人员关联表", notes = "修改审批流程与人员关联表")
    @SysLog("修改审批流程与人员关联表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('approvalprocessuser_edit')" )
    public R updateById(@RequestBody ApprovalProcessUser approvalProcessUser) {
        return R.ok(approvalProcessUserService.updateById(approvalProcessUser));
    }

    /**
     * 通过id删除审批流程与人员关联表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除审批流程与人员关联表", notes = "通过id删除审批流程与人员关联表")
    @SysLog("通过id删除审批流程与人员关联表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('approvalprocessuser_del')" )
    public R removeById(@PathVariable Integer fId) {
        return R.ok(approvalProcessUserService.removeById(fId));
    }

	/**
	 * 通过fid 查询出用户关联用户
	 * @param approvalProcessUser id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询出用户关联用户", notes = "通过id查询出用户关联用户")
	@SysLog("通过id查询出用户关联用户" )
	@GetMapping("/getInfoList")
	public R getInfoList(ApprovalProcessUser approvalProcessUser,Page page) {
		ApprovalProcessUser approvalProcessUser2 = new ApprovalProcessUser();
		approvalProcessUser2.setProcessFid(approvalProcessUser.getProcessFid());
		if(StringUtils.isNotBlank(approvalProcessUser.getUsername())){
			approvalProcessUser2.setUsername(approvalProcessUser.getUsername());
		}
		if(StringUtils.isNotBlank(approvalProcessUser.getManName())){
			approvalProcessUser2.setManName(approvalProcessUser.getManName());
		}
		QueryWrapper<ApprovalProcessUser> query = Wrappers.query(new ApprovalProcessUser());
		if(approvalProcessUser2.getUsername() != null){
			query = query.like("UPPER(username)",approvalProcessUser2.getUsername().toUpperCase());

		}
		if(approvalProcessUser.getManName() != null){
			query = query.like("UPPER(man_name)",approvalProcessUser.getManName().toUpperCase());

		}
		query = query.eq("Process_Fid",approvalProcessUser.getProcessFid());
		List<ApprovalProcessUser> list = approvalProcessUserService.list(query);

		Collection<Integer> collection = new ArrayList<Integer>();
		// 添加元素
		for(ApprovalProcessUser info:list){
			collection.add(info.getUserId());
		}
		if(collection.size()>0){
			IPage<BSUser> userList = bsUserService.page(page, Wrappers.query(new BSUser()).in("user_id", collection));
			return R.ok(userList);
		} else {
			IPage<BSUser> userList = bsUserService.page(page, Wrappers.query(new BSUser()).eq("user_id", 0));
			return R.ok(userList);
		}


	}


	/**
	 * 通过fid 添加审核流程用户关联用户
	 * @param approvalProcessUser id
	 * @return R
	 */
	@ApiOperation(value = "添加审核流程用户关联用户", notes = "添加审核流程用户关联用户")
	@SysLog("添加审核流程用户关联用户" )
	@PostMapping("addApprovalProcessuser" )
	public R addApprovalProcessuser(@RequestBody ApprovalProcessUser approvalProcessUser) {

		ApprovalProcessUser approvalProcessUserCheck = new ApprovalProcessUser();
		approvalProcessUserCheck.setProcessFid(approvalProcessUser.getProcessFid());
		approvalProcessUserCheck.setUserId(approvalProcessUser.getUserId());
		approvalProcessUserCheck.setUsername(approvalProcessUser.getUsername());
		int count = approvalProcessUserService.count(Wrappers.query(approvalProcessUserCheck));
		if(count>0){
			return R.failed("当前用户已经绑定！");
		}
		return R.ok(approvalProcessUserService.save(approvalProcessUser));
	}

	/**
	 * 通过fid 解绑审核流程用户关联用户
	 * @param approvalProcessUser id
	 * @return R
	 */
	@ApiOperation(value = "解绑审核流程用户关联用户", notes = "解绑审核流程用户关联用户")
	@SysLog("解绑审核流程用户关联用户" )
	@DeleteMapping("delApprovalProcessuser" )
	public R delApprovalProcessuser(@RequestBody ApprovalProcessUser approvalProcessUser) {
		return R.ok(approvalProcessUserService.remove(Wrappers.query(approvalProcessUser)));
	}

	/**
	 * 查询审批流程对应人员
	 * @param approvalProcessUser 查询审批流程对应人员
	 * @return R
	 */
	@ApiOperation(value = "查询审批流程对应人员", notes = "查询审批流程对应人员")
	@SysLog("查询审批流程对应人员" )
	@PostMapping("selectAllProcessUser")
	public R selectAllProcessUser(@RequestBody ApprovalProcessUser approvalProcessUser) {
		return R.ok(approvalProcessUserService.list(Wrappers.query(approvalProcessUser)));
	}
}
