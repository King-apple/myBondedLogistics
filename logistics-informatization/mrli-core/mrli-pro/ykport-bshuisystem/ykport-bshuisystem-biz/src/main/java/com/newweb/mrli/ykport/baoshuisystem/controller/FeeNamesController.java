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
import com.newweb.mrli.ykport.baoshuisystem.entity.FeeNames;
import com.newweb.mrli.ykport.baoshuisystem.service.FeeNamesService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 费目码表
 *
 * @author pigx code generator
 * @date 2021-08-28 15:17:55
 */
@RestController
@AllArgsConstructor
@RequestMapping("/feenames" )
@Api(value = "feenames", tags = "费目码表管理")
public class FeeNamesController {

    private final  FeeNamesService feeNamesService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param feeNames 费目码表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('feenames_view')" )
    public R getFeeNamesPage(Page page, FeeNames feeNames) {
        return R.ok(feeNamesService.page(page, Wrappers.query(feeNames).isNull("DEL_TIM")));
    }


    /**
     * 通过id查询费目码表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('feenames_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(feeNamesService.getById(fId));
    }

    /**
     * 新增费目码表
     * @param feeNames 费目码表
     * @return R
     */
    @ApiOperation(value = "新增费目码表", notes = "新增费目码表")
    @SysLog("新增费目码表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('feenames_add')" )
    public R save(@RequestBody FeeNames feeNames) {
		List<FeeNames> list = feeNamesService.list(Wrappers.query(new FeeNames()).select("max(to_number(FEE_NAME_COD)) as FEE_NAME_COD"));
		List<FeeNames> feeNamCheckList= feeNamesService.list(Wrappers.query(new FeeNames()).eq("FEE_NAM", feeNames.getFeeNam()).isNull("DEL_NAM"));
		if(feeNamCheckList.size()>0){
			return R.failed("添加失败，费目名称重复");
		}
		feeNames.setFeeNameCod((Integer.parseInt(list.get(0).getFeeNameCod())+1)+"");
		feeNames.setOperNam(SecurityUtils.getUser().getUsername());
		feeNames.setRecordTim(new Date());
        return R.ok(feeNamesService.save(feeNames));
    }

    /**
     * 修改费目码表
     * @param feeNames 费目码表
     * @return R
     */
    @ApiOperation(value = "修改费目码表", notes = "修改费目码表")
    @SysLog("修改费目码表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('feenames_edit')" )
    public R updateById(@RequestBody FeeNames feeNames) {
		feeNames.setUpdateNam(SecurityUtils.getUser().getUsername());
		feeNames.setUpdateTim(new Date());
        return R.ok(feeNamesService.updateById(feeNames));
    }

    /**
     * 通过id删除费目码表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除费目码表", notes = "通过id删除费目码表")
    @SysLog("通过id删除费目码表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('feenames_del')" )
    public R removeById(@PathVariable Integer fId) {
		FeeNames feeNames = new FeeNames();
		feeNames.setFId(fId);
		feeNames.setDelNam(SecurityUtils.getUser().getUsername());
		feeNames.setDelTim(new Date());
        return R.ok(feeNamesService.updateById(feeNames));
    }

	/**
	 * 分页查询
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/all" )
	public R getFeeNames() {
		return R.ok(feeNamesService.list());
	}
}
