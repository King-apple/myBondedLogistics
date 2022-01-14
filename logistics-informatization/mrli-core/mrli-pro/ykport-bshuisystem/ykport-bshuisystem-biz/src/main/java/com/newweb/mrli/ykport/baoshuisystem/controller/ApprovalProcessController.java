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
import com.newweb.mrli.ykport.baoshuisystem.entity.ApprovalProcess;
import com.newweb.mrli.ykport.baoshuisystem.entity.ApprovalProcessUser;
import com.newweb.mrli.ykport.baoshuisystem.entity.BSUserRole;
import com.newweb.mrli.ykport.baoshuisystem.service.ApprovalProcessService;
import com.newweb.mrli.ykport.baoshuisystem.service.ApprovalProcessUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 审批流程表
 *
 * @author pigx code generator
 * @date 2021-07-28 14:27:51
 */
@RestController
@AllArgsConstructor
@RequestMapping("/approvalprocess" )
@Api(value = "approvalprocess", tags = "审批流程表管理")
public class ApprovalProcessController {

    private final  ApprovalProcessService approvalProcessService;

	private final ApprovalProcessUserService approvalProcessUserService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param approvalProcess 审批流程表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('approvalprocess_view')" )
    public R getApprovalProcessPage(Page page, ApprovalProcess approvalProcess) {

        return R.ok(approvalProcessService.page(page, Wrappers.query(approvalProcess)
				.isNull("DEL_TIM")
				.orderByAsc("ORDER_BY")));
    }

//	/**
//	 * 查询角色信息
//	 */
//	@ApiOperation(value = "查询角色信息", notes = "查询角色信息")
//	@PostMapping("/role" )
//	@PreAuthorize("@pms.hasPermission('approvalprocess_add')" )
//	public R getRole() {
//		int dept = SecurityUtils.getUser().getDeptId();
//		//通过部门id查询部门下所有用户
//
//		return R.ok(approvalProcessService.page(page, Wrappers.query(approvalProcess).isNull("DEL_TIM")));
//	}


    /**
     * 通过id查询审批流程表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('approvalprocess_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(approvalProcessService.getById(fId));
    }

    /**
     * 新增审批流程表
     * @param approvalProcess 审批流程表
     * @return R
     */
    @ApiOperation(value = "新增审批流程表", notes = "新增审批流程表")
    @SysLog("新增审批流程表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('approvalprocess_add')" )
    public R save(@RequestBody ApprovalProcess approvalProcess) {
		Long fId = approvalProcessService.selectFid();
		approvalProcess.setFId(fId);
    	approvalProcess.setOperNam(SecurityUtils.getUser().getUsername());
    	approvalProcess.setRecordTim(new Date());
		BSUserRole bsUserRole = new BSUserRole();
        return R.ok(approvalProcessService.save(approvalProcess));
    }

    /**
     * 修改审批流程表
     * @param approvalProcess 审批流程表
     * @return R
     */
    @ApiOperation(value = "修改审批流程表", notes = "修改审批流程表")
    @SysLog("修改审批流程表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('approvalprocess_edit')" )
    public R updateById(@RequestBody ApprovalProcess approvalProcess) {
        return R.ok(approvalProcessService.updateById(approvalProcess));
    }

    /**
     * 通过id删除审批流程表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除审批流程表", notes = "通过id删除审批流程表")
    @SysLog("通过id删除审批流程表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('approvalprocess_del')" )
    public R removeById(@PathVariable Long fId) {
    	ApprovalProcess approvalProcess = new ApprovalProcess();
    	approvalProcess.setDelNam(SecurityUtils.getUser().getUsername());
    	approvalProcess.setDelTim(new Date());
    	approvalProcess.setFId(fId);
        return R.ok(approvalProcessService.updateById(approvalProcess));
    }


	/**
	 * 通过id查询审批流程表
	 * @param processNo
	 * @return R
	 */
	@ApiOperation(value = "查询流程对应用户", notes = "通过id查询")
	@GetMapping("/getProcessUser/{processNo}" )
	@PreAuthorize("@pms.hasPermission('approvalprocess_view')" )
	public R getProcessUser(@PathVariable("processNo" ) String processNo) {
		ApprovalProcess approvalProcess = new ApprovalProcess();
		approvalProcess.setProcessNo(processNo);
		List<ApprovalProcess> approvalProcessList = approvalProcessService.list(Wrappers.query(approvalProcess)
				.orderByAsc("PROCESS_NO"));
		List<ApprovalProcessUser> processUserList = approvalProcessUserService
				.list(Wrappers.query(new ApprovalProcessUser())
				.inSql("PROCESS_FID","select F_ID from approval_process where process_no = '"+processNo+"'"));
		return R.ok(new List[]{processUserList, approvalProcessList});
	}

	/**
	 * 整体查询
	 * @param approvalProcess 审批流程表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@PostMapping("/getApprovalProcessList" )
	public R getApprovalProcessList(@RequestBody  ApprovalProcess approvalProcess) {
		return R.ok(approvalProcessService.list(Wrappers.query(approvalProcess)
				.isNull("DEL_TIM")
				.orderByAsc("ORDER_BY")));
	}

	/**
	 * 查询下一审批环节审批人
	 * @param approvalProcess 查询下一审批环节审批人
	 * @return R
	 */
	@ApiOperation(value = "查询下一审批环节审批人", notes = "查询下一审批环节审批人")
	@SysLog("查询下一审批环节审批人" )
	@PostMapping("selectNextCheckUser")
	public R selectNextCheckUser(@RequestBody ApprovalProcess approvalProcess) {
		return approvalProcessService.selectNextCheckUser(approvalProcess);
	}
}
