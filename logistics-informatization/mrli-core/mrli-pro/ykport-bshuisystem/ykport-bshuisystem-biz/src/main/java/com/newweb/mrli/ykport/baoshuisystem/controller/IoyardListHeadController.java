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
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardListHead;
import com.newweb.mrli.ykport.baoshuisystem.service.IoyardListHeadService;
import com.newweb.mrli.ykport.baoshuisystem.vo.IoyardListHeadBodyVo;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 出入库明细表头表
 *
 * @author pigx code generator
 * @date 2021-09-09 09:52:12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ioyardlisthead" )
@Api(value = "ioyardlisthead", tags = "出入库明细表头表管理")
public class IoyardListHeadController {

    private final  IoyardListHeadService ioyardListHeadService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ioyardListHead 出入库明细表头表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('ioyardlisthead_view')" )
    public R getIoyardListHeadPage(Page page, IoyardListHead ioyardListHead) {
        return R.ok(ioyardListHeadService.page(page, Wrappers.query(ioyardListHead)));
    }


    /**
     * 通过id查询出入库明细表头表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('ioyardlisthead_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(ioyardListHeadService.getById(fId));
    }

    /**
     * 新增出入库明细表头表
     * @param ioyardListHead 出入库明细表头表
     * @return R
     */
    @ApiOperation(value = "新增出入库明细表头表", notes = "新增出入库明细表头表")
    @SysLog("新增出入库明细表头表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('ioyardlisthead_add')" )
    public R save(@RequestBody IoyardListHead ioyardListHead) {
        return R.ok(ioyardListHeadService.insertIoyardInfo(ioyardListHead));
    }

    /**
     * 修改出入库明细表头表
     * @param ioyardListHead 出入库明细表头表
     * @return R
     */
    @ApiOperation(value = "修改出入库明细表头表", notes = "修改出入库明细表头表")
    @SysLog("修改出入库明细表头表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('ioyardlisthead_edit')" )
    public R updateById(@RequestBody IoyardListHead ioyardListHead) {
        return R.ok(ioyardListHeadService.updateById(ioyardListHead));
    }

    /**
     * 通过id删除出入库明细表头表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除出入库明细表头表", notes = "通过id删除出入库明细表头表")
    @SysLog("通过id删除出入库明细表头表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('ioyardlisthead_del')" )
    public R removeById(@PathVariable Integer fId) {
        return R.ok(ioyardListHeadService.removeIoyardListHeadAndBody(fId));
    }

	/**
	 * 查询主子表
	 * @param ioyardListHeadBodyVo 查询主子表
	 * @return R
	 */
	@ApiOperation(value = "查询主子表", notes = "查询主子表")
	@SysLog("查询主子表" )
	@GetMapping("/getHeadBodyPage")
	@PreAuthorize("@pms.hasPermission('ioyardlisthead_view')" )
	public R getIoyardListHead(Page page, IoyardListHeadBodyVo ioyardListHeadBodyVo) {
		return R.ok(ioyardListHeadService.getIoyardListHead(page,ioyardListHeadBodyVo));
	}

	/**
	 * 导出出入库明细表
	 * @param ioyardListHeadBodyVo 导出出入库明细表
	 * @return R
	 */
	@ApiOperation(value = "导出出入库明细表", notes = "导出出入库明细表")
	@SysLog("导出出入库明细表" )
	@PostMapping("/exportFile")
	@PreAuthorize("@pms.hasPermission('ioyardlisthead_export')" )
	public void getIoyardListHead(@RequestBody IoyardListHeadBodyVo ioyardListHeadBodyVo, HttpServletRequest request, HttpServletResponse response) {
		ioyardListHeadService.exportFile(ioyardListHeadBodyVo,request,response);
	}
}
