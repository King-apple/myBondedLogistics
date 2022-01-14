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
import com.newweb.mrli.ykport.baoshuisystem.entity.ClientInfo;
import com.newweb.mrli.ykport.baoshuisystem.entity.ContractRate;
import com.newweb.mrli.ykport.baoshuisystem.service.ContractRateService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 合同费率表
 *
 * @author pigx code generator
 * @date 2021-08-28 16:36:26
 */
@RestController
@AllArgsConstructor
@RequestMapping("/contractrate" )
@Api(value = "contractrate", tags = "合同费率表管理")
public class ContractRateController {

    private final  ContractRateService contractRateService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param contractRate 合同费率表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('contractrate_view')" )
    public R getContractRatePage(Page page, ContractRate contractRate) {
        return R.ok(contractRateService.page(page, Wrappers.query(contractRate)));
    }


    /**
     * 通过id查询合同费率表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('contractrate_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(contractRateService.getById(fId));
    }

    /**
     * 新增合同费率表
     * @param contractRate 合同费率表
     * @return R
     */
    @ApiOperation(value = "新增合同费率表", notes = "新增合同费率表")
    @SysLog("新增合同费率表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('contractrate_add')" )
    public R save(@RequestBody ContractRate contractRate) {
        return R.ok(contractRateService.save(contractRate));
    }

    /**
     * 修改合同费率表
     * @param contractRate 合同费率表
     * @return R
     */
    @ApiOperation(value = "修改合同费率表", notes = "修改合同费率表")
    @SysLog("修改合同费率表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('contractrate_edit')" )
    public R updateById(@RequestBody ContractRate contractRate) {
        return R.ok(contractRateService.updateById(contractRate));
    }

    /**
     * 通过id删除合同费率表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除合同费率表", notes = "通过id删除合同费率表")
    @SysLog("通过id删除合同费率表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('contractrate_del')" )
    public R removeById(@PathVariable Integer fId) {
        return R.ok(contractRateService.removeById(fId));
    }


	/**
	 * 整体查询
	 * @return
	 */
	@ApiOperation(value = "整体查询", notes = "整体查询")
	@GetMapping("/all" )
	@PreAuthorize("@pms.hasPermission('contractrate_view')" )
	public R getContractRateAll() {
		return R.ok(contractRateService.list());
	}

	/**
	 * 批量新增合同费率表
	 * @param contractRate 合同费率表
	 * @return R
	 */
	@ApiOperation(value = "新增合同费率表", notes = "新增合同费率表")
	@SysLog("新增合同费率表" )
	@PostMapping("/saveList" )
	@PreAuthorize("@pms.hasPermission('contractrate_add')" )
	public R saveList(@RequestBody List<ContractRate> contractRate) {
		return R.ok(contractRateService.saveList(contractRate));
	}

	/**
	 * 通过id删除合同费率表
	 * @param cFid id
	 * @return R
	 */
	@ApiOperation(value = "通过cFid删除合同费率表", notes = "通过cFid删除合同费率表")
	@SysLog("通过cFid删除合同费率表" )
	@DeleteMapping("removeBycFid/{cFid}" )
	@PreAuthorize("@pms.hasPermission('contractrate_del')" )
	public R removeBycFid(@PathVariable Integer cFid) {
		ContractRate contractRate = new ContractRate();
		contractRate.setCFid(cFid);
		return R.ok(contractRateService.removeByCFid(contractRate));
	}
}
