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
import com.newweb.mrli.ykport.baoshuisystem.entity.FeeUnits;
import com.newweb.mrli.ykport.baoshuisystem.entity.SettledCompanyInfo;
import com.newweb.mrli.ykport.baoshuisystem.service.FeeUnitsService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 费率单位码表
 *
 * @author pigx code generator
 * @date 2021-09-09 16:11:04
 */
@RestController
@AllArgsConstructor
@RequestMapping("/feeunits" )
@Api(value = "feeunits", tags = "费率单位码表管理")
public class FeeUnitsController {

    private final  FeeUnitsService feeUnitsService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param feeUnits 费率单位码表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('feeunits_view')" )
    public R getFeeUnitsPage(Page page, FeeUnits feeUnits) {
        return R.ok(feeUnitsService.page(page, Wrappers.query(feeUnits)));
    }


    /**
     * 通过id查询费率单位码表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('feeunits_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(feeUnitsService.getById(fId));
    }

    /**
     * 新增费率单位码表
     * @param feeUnits 费率单位码表
     * @return R
     */
    @ApiOperation(value = "新增费率单位码表", notes = "新增费率单位码表")
    @SysLog("新增费率单位码表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('feeunits_add')" )
    public R save(@RequestBody FeeUnits feeUnits) {

		FeeUnits feeUnits1 = new FeeUnits();
		feeUnits1.setFeeUnitCod(feeUnits.getFeeUnitCod());
		List<FeeUnits> list = feeUnitsService.list(Wrappers.query(feeUnits1));
		if(list.size() > 0){
			return R.failed("已存在相同代码");
		}
		feeUnits1 = new FeeUnits();
		feeUnits1.setFeeUnit(feeUnits.getFeeUnit());
		list = feeUnitsService.list(Wrappers.query(feeUnits1));
		if(list.size() > 0){
			return R.failed("已存在相同费率单位");
		}

        return R.ok(feeUnitsService.save(feeUnits));
    }

    /**
     * 修改费率单位码表
     * @param feeUnits 费率单位码表
     * @return R
     */
    @ApiOperation(value = "修改费率单位码表", notes = "修改费率单位码表")
    @SysLog("修改费率单位码表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('feeunits_edit')" )
    public R updateById(@RequestBody FeeUnits feeUnits) {
        return R.ok(feeUnitsService.updateById(feeUnits));
    }

    /**
     * 通过id删除费率单位码表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除费率单位码表", notes = "通过id删除费率单位码表")
    @SysLog("通过id删除费率单位码表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('feeunits_del')" )
    public R removeById(@PathVariable Integer fId) {
        return R.ok(feeUnitsService.removeById(fId));
    }


	/**
	 * 分页查询
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/all" )
	public R getFeeUnits() {
		return R.ok(feeUnitsService.list());
	}
}
