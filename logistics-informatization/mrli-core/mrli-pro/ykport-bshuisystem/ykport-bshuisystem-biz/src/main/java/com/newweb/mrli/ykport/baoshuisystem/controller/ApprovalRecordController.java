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
import com.newweb.mrli.ykport.baoshuisystem.entity.ApprovalRecord;
import com.newweb.mrli.ykport.baoshuisystem.entity.BSUser;
import com.newweb.mrli.ykport.baoshuisystem.service.ApprovalRecordService;
import com.newweb.mrli.ykport.baoshuisystem.service.BSUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 审批记录表
 *
 * @author pigx code generator
 * @date 2021-08-28 18:10:46
 */
@RestController
@AllArgsConstructor
@RequestMapping("/approvalrecord" )
@Api(value = "approvalrecord", tags = "审批记录表管理")
public class ApprovalRecordController {

    private final  ApprovalRecordService approvalRecordService;

	private final BSUserService bsUserService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param approvalRecord 审批记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('approvalrecord_view')" )
    public R getApprovalRecordPage(Page page, ApprovalRecord approvalRecord) {
        return R.ok(approvalRecordService.page(page, Wrappers.query(approvalRecord)));
    }


    /**
     * 通过id查询审批记录表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('approvalrecord_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(approvalRecordService.getById(fId));
    }

    /**
     * 新增审批记录表
     * @param approvalRecord 审批记录表
     * @return R
     */
    @ApiOperation(value = "新增审批记录表", notes = "新增审批记录表")
    @SysLog("新增审批记录表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('approvalrecord_add')" )
    public R save(@RequestBody ApprovalRecord approvalRecord) {
		approvalRecord.setApprover(SecurityUtils.getUser().getUsername());
		approvalRecord.setApprovalTim(new Date());
        return R.ok(approvalRecordService.save(approvalRecord));
    }

    /**
     * 修改审批记录表
     * @param approvalRecord 审批记录表
     * @return R
     */
    @ApiOperation(value = "修改审批记录表", notes = "修改审批记录表")
    @SysLog("修改审批记录表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('approvalrecord_edit')" )
    public R updateById(@RequestBody ApprovalRecord approvalRecord) {
        return R.ok(approvalRecordService.updateById(approvalRecord));
    }

    /**
     * 通过id删除审批记录表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除审批记录表", notes = "通过id删除审批记录表")
    @SysLog("通过id删除审批记录表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('approvalrecord_del')" )
    public R removeById(@PathVariable Integer fId) {
        return R.ok(approvalRecordService.removeById(fId));
    }


	/**
	 * 新增审批记录表
	 * @param approvalRecord
	 * @return R
	 */
	@ApiOperation(value = "批量新增审批记录表(旧)", notes = "批量新增审批记录表(旧)")
	@SysLog("批量新增审批记录表" )
	@PostMapping("/saveRecords")
	@PreAuthorize("@pms.hasPermission('approvalrecord_add')" )
	public R saveRecords(@RequestBody ApprovalRecord approvalRecord) {
		return R.ok(approvalRecordService.insertRecords(approvalRecord));
	}

	/**
	 * 修改审批记录表
	 * @param approvalRecord 审批记录表
	 * @return R
	 */
	@ApiOperation(value = "修改审批记录表", notes = "修改审批记录表")
	@SysLog("修改审批记录表" )
	@PutMapping("/updateRec" )
	@PreAuthorize("@pms.hasPermission('approvalrecord_edit')" )
	public R updateRecordById(@RequestBody ApprovalRecord approvalRecord) throws Exception {
		return R.ok(approvalRecordService.updateRecordById(approvalRecord));
	}

	/**
	 * 查询审批记录组
	 * @param approvalRecord
	 * @return R
	 */
	@ApiOperation(value = "查询审批记录组", notes = "查询审批记录组")
	@SysLog("查询审批记录组" )
	@PostMapping("/getRecordGroup")
	@PreAuthorize("@pms.hasPermission('contract_view')" )
	public R getRecordGroup(@RequestBody ApprovalRecord approvalRecord) {
		return R.ok(approvalRecordService.selectApprovalRecordGroup(approvalRecord));
	}

	/**
	 * 新增审批记录表
	 * @param userRecords
	 * @return R
	 */
	@ApiOperation(value = "批量新增审批记录表（新）", notes = "批量新增审批记录表（新）")
	@SysLog("批量新增审批记录表（新）" )
	@PostMapping("/saveUserRecords")
	@PreAuthorize("@pms.hasPermission('approvalrecord_add')" )
	public R saveUserRecords(@RequestBody String userRecords) {
		return R.ok(approvalRecordService.insertUserRecords(userRecords));
	}


	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param approvalRecord 审批记录表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/getApprovalRecordUserPage" )
	@PreAuthorize("@pms.hasPermission('approvalrecord_view')" )
	public R getApprovalRecordUserPage(Page page, ApprovalRecord approvalRecord) {
		Page approvalRecordPage = approvalRecordService.page(page, Wrappers.query(approvalRecord));

		return R.ok(approvalRecordPage);
	}

	/**
	 * 分页查询
	 * @param approvalRecord 审批记录表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/getApprovalRecords" )
	@PreAuthorize("@pms.hasPermission('approvalrecord_view')" )
	public R getApprovalRecords(ApprovalRecord approvalRecord) {

		return approvalRecordService.getApprovalRecords(approvalRecord);
	}
}
