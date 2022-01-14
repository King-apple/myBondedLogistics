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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.service.MrliUser;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.dto.ContractInfoDto;
import com.newweb.mrli.ykport.baoshuisystem.entity.*;
import com.newweb.mrli.ykport.baoshuisystem.service.*;
import com.newweb.mrli.ykport.baoshuisystem.vo.ContractFileVo;
import oracle.jdbc.proxy.annotation.Post;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Security;
import java.util.*;


/**
 * 合同信息管理表
 *
 * @author pigx code generator
 * @date 2021-08-03 09:54:56
 */
@RestController
@AllArgsConstructor
@RequestMapping("/contract" )
@Api(value = "contract", tags = "合同信息管理表管理")
public class ContractController {

	private final SysDeptService sysDeptService;

	@Autowired
	private ContractService contractService;
	@Autowired
	private CodeListService codeListService;
	@Autowired
	private BSUserService bsUserService;


	/**
	 * 分页查询
	 *
	 * @param page     分页对象
	 * @param contract 合同信息管理表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('contract_view')")
	public R getContractPage(Page page, Contract contract) {

		return R.ok(contractService.page(page, Wrappers.query(contract)));
	}


	/**
	 * 通过id查询合同信息管理表
	 *
	 * @param fId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{fId}")
	@PreAuthorize("@pms.hasPermission('contract_view')")
	public R getById(@PathVariable("fId") Long fId) {
		return R.ok(contractService.getById(fId));
	}

	/**
	 * 新增合同信息管理表
	 *
	 * @param contract 合同信息管理表
	 * @return R
	 */
	@ApiOperation(value = "新增合同信息管理表", notes = "新增合同信息管理表")
	@SysLog("新增合同信息管理表")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('contract_add')")
	public R save(@RequestBody Contract contract) {
		return contractService.insertCon(contract);
	}

	/**
	 * 修改合同信息管理表
	 *
	 * @param contract 合同信息管理表
	 * @return R
	 */
	@ApiOperation(value = "修改合同信息管理表", notes = "修改合同信息管理表")
	@SysLog("修改合同信息管理表")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('contract_edit')")
	public R updateById(@RequestBody Contract contract) {
		return R.ok(contractService.updateById(contract));
	}

	/**
	 * 通过id删除合同信息管理表
	 *
	 * @param fId id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除合同信息管理表", notes = "通过id删除合同信息管理表")
	@SysLog("通过id删除合同信息管理表")
	@DeleteMapping("/{fId}")
	@PreAuthorize("@pms.hasPermission('contract_view')")
	public R removeById(@PathVariable Long fId) {
		Contract contract = new Contract();
		contract.setFId(fId);
		contract.setIsCancel("1");
		contract.setDelNam(SecurityUtils.getUser().getUsername());
		contract.setDelTim(DateUtil.now());
		return R.ok(contractService.updateContract(contract));
	}


	/**
	 * 通过id删除合同信息管理表
	 *
	 * @return R
	 */
	@ApiOperation(value = "获取默认信息", notes = "获取默认信息")
	@SysLog("获取默认信息")
	@GetMapping("/getContractInsertValue")
	@PreAuthorize("@pms.hasPermission('contract_view')")
	public R getContractInsertValue() {
		return R.ok(contractService.getContractInsertValue());
	}


	/**
	 * 分页查询
	 *
	 * @param page     分页对象
	 * @param contract 合同信息文件管理表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/filePage")
	@PreAuthorize("@pms.hasPermission('contract_view')")
	public R getContractFilePage(Page page, ContractFileVo contract) {
		contract.setOperNam(SecurityUtils.getUser().getUsername());
		Collection<? extends GrantedAuthority> authorities = SecurityUtils.getAuthentication().getAuthorities();
		for (GrantedAuthority item : authorities) {
			if(item.toString().equals("contract_view_admin")){
				contract.setOperNam(null);
				break;
			}
			if (item.toString().equals("contract_view_deptAdmin")){
				BSUser bsUser = new BSUser();
				bsUser.setUsername(SecurityUtils.getUser().getUsername());
				contract.setOperNam(null);
				contract.setUserSubDeptId(getUserSubDeptId(bsUser).getDeptSubId()+"");
				break;
			}

		}
		return R.ok(contractService.selectPageVo(page, contract));
	}

	public ContractFileVo setVo(ContractFileVo contract) {
		contract.setOperator("");
		return contract;
	}

	@DS("slave")
	public BSUser getUserSubDeptId(BSUser bsUser) {
		return bsUserService.list(Wrappers.query(bsUser)).get(0);
	}


	/**
	 * 获取默认编号
	 *
	 * @return R
	 */
	@ApiOperation(value = "获取默认编号", notes = "获取默认编号")
	@SysLog("获取默认编号")
	@GetMapping("/getContractCode")
	@PreAuthorize("@pms.hasPermission('contract_view')")
	@Transactional
	public R getContractCode(CodeList codeList) {
		return R.ok(codeListService.getContractCode(codeList));
	}

	/**
	 * 获取默认编号
	 *
	 * @return R
	 */
	@ApiOperation(value = "获取当前子编号", notes = "获取当前子编号")
	@SysLog("获取当前子编号")
	@GetMapping("/getChildCode")
	@PreAuthorize("@pms.hasPermission('contract_view')")
	@Transactional
	public R getChildCode(ContractFileVo contract) {
		IPage<ContractFileVo> contractFileVoIPage = contractService.selectPageVo(new Page<>(), contract);
		int size = Integer.parseInt(contractFileVoIPage.getTotal() + "");
		int[] childArray = new int[size];
		Long conCode = 0l;
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				if (contractFileVoIPage.getRecords().get(i).getContNo().split("-").length > 4) {
					String childCode = contractFileVoIPage.getRecords().get(i).getContNo().split("-")[4];
					childArray[i] = Integer.parseInt(childCode);
				} else {
					childArray[i] = 0;
					conCode = contractFileVoIPage.getRecords().get(i).getFId();
				}
			}
		}
		Arrays.sort(childArray);
		String[] arr = new String[2];
		String paramChildCode = String.format("%0" + 2 + "d", Integer.parseInt(childArray[childArray.length - 1] + 1 + ""));
		arr[0] = paramChildCode;
		arr[1] = conCode + "";
		return R.ok(arr);
	}

	/**
	 * 获取默认编号
	 *
	 * @return R
	 */
	@ApiOperation(value = "导出excel", notes = "导出excel")
	@SysLog("导出excel")
	@PostMapping("/exportExcel")
	@PreAuthorize("@pms.hasPermission('contract_view_export')")
	public void expoprtExcel(Long fid, HttpServletRequest request, HttpServletResponse response) throws IOException {
		contractService.exportExcel(fid, request, response);
	}


	/**
	 * 分页查询
	 *
	 * @param page     分页对象
	 * @param contract 合同信息文件管理表视图
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/getContractCheckRecord")
	@PreAuthorize("@pms.hasPermission('contract_view')")
	public R getContractCheckRecord(Page page, ContractFileVo contract) {
		return R.ok(contractService.selectPageVoJoinApprovalRecord(page, contract));
	}

	/**
	 * 下拉查询合同
	 *
	 * @param contract 合同信息文件管理表视图
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/getSelectContract")
	public R getSelectContract(Contract contract) {
		contract.setStartDte(new Date());
		contract.setEndDte(new Date());
		QueryWrapper<Contract> queryWrapper = Wrappers.query();
		List<Contract> pageList = contractService.list(
				queryWrapper.le(true, "start_dte", contract.getStartDte())
				.ge(true, "end_dte", contract.getEndDte()));

		return R.ok(pageList);
	}
	@GetMapping("/getEffectiveInfo")
	public R getSelectContract() {
		Contract contract = new Contract();
		BSUser bsUser = new BSUser();
		bsUser.setUsername(SecurityUtils.getUser().getUsername());
		SysDept deptInfo = sysDeptService.getById(getUserSubDeptId(bsUser).getDeptSubId());
		String deptName = deptInfo.getName();
		if ("仓储业务部".equals(deptName) || "货代部".equals(deptName)) {
			contract.setDept(deptName);
			List<ContractFileVo> list = contractService.getEffectiveInfo(contract);
			return R.ok(list);
		} else {
			String[] deptArr = {"仓储业务部","货代部"};
			List<ContractFileVo> list = contractService.getEffectiveInfoOther(contract,deptArr);
			return R.ok(list);
		}
//		List<ContractFileVo> list = contractService.getEffectiveInfo(contract);
//		return R.ok(list);
	}


	/**
	 * 修改审批记录表
	 * @param approvalProcessSet 审批记录表
	 * @return R
	 */
	@ApiOperation(value = "修改审批记录表", notes = "修改审批记录表")
	@SysLog("修改审批记录表" )
	@PutMapping("/updateApprovalprocessset" )
	@PreAuthorize("@pms.hasPermission('approvalprocessset_edit')" )
	public R updateApprovalprocessset(@RequestBody ApprovalProcessSet approvalProcessSet) throws Exception {
		return R.ok(contractService.updateApprovalprocessset(approvalProcessSet));
	}

	/**
	 * 批量新增审批流程设定表
	 * @param userRecords
	 * @return R
	 */
	@ApiOperation(value = "批量新增审批流程设定", notes = "批量新增审批流程设定表")
	@SysLog("批量新增审批流程设定表" )
	@PostMapping("/saveUserProcessSet")
	public R saveUserProcessSet(@RequestBody String userRecords) {
		return R.ok(contractService.insertUserRecords(userRecords));
	}
	/**
	 * 修改合同状态
	 *
	 **/
	@ApiOperation(value = "修改状态是否完整审核", notes = "修改状态是否完整审核")
	@SysLog("修改是否完整审核状态")
	@PostMapping("/modify/completeApprovalFlag")
	public R modifyCompleteApprovalFlag(@RequestBody Contract contract){
//		QueryWrapper<Contract> wrapper = new QueryWrapper<>();
//		wrapper.eq("F_ID",contract.getFId());
		UpdateWrapper<Contract> wrapper = new UpdateWrapper<>();
		wrapper.eq("F_ID",contract.getFId());
		wrapper.set("COMPLETE_APPROVAL_FLAG",contract.getCompleteApprovalFlag());
		boolean update = contractService.update(wrapper);

		return R.ok(update);
	}


}
