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
import com.newweb.mrli.common.security.service.MrliUser;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.ParameterList;
import com.newweb.mrli.ykport.baoshuisystem.service.ParameterListService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * 参数表
 *
 * @author pigx code generator
 * @date 2021-08-26 15:56:47
 */
@RestController
@AllArgsConstructor
@RequestMapping("/parameterlist" )
@Api(value = "parameterlist", tags = "参数表管理")
public class ParameterListController {

    private final  ParameterListService parameterListService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param parameterList 参数表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('parameterlist_view')" )
    public R getParameterListPage(Page page, ParameterList parameterList) {
        return R.ok(parameterListService.page(page, Wrappers.query(parameterList).isNull("DEL_TIM")));
    }


    /**
     * 通过id查询参数表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(parameterListService.getById(fId));
    }

    /**
     * 新增参数表
     * @param parameterList 参数表
     * @return R
     */
    @ApiOperation(value = "新增参数表", notes = "新增参数表")
    @SysLog("新增参数表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('parameterlist_add')" )
    public R save(@RequestBody ParameterList parameterList) {
    	String param_cod = parameterList.getParamCod();
		ParameterList old = parameterListService.selectValueByCode(param_cod);
		if (old != null) {
			return R.failed("该识别代码已存在，不可重复");
		}
		parameterList.setOperNam(SecurityUtils.getUser().getUsername());
		parameterList.setRecordTim(new Date());
		parameterList.setFId(parameterListService.selectFid());
        return R.ok(parameterListService.save(parameterList));
    }

    /**
     * 修改参数表
     * @param parameterList 参数表
     * @return R
     */
    @ApiOperation(value = "修改参数表", notes = "修改参数表")
    @SysLog("修改参数表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('parameterlist_edit')" )
    public R updateById(@RequestBody ParameterList parameterList) {
		parameterList.setUpdateNam(SecurityUtils.getUser().getUsername());
		parameterList.setUpdateTim(new Date());
		return R.ok(parameterListService.updateById(parameterList));
    }

    /**
     * 通过id删除参数表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除参数表", notes = "通过id删除参数表")
    @SysLog("通过id删除参数表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('parameterlist_del')" )
    public R removeById(@PathVariable Integer fId) {
		ParameterList parameterList = new ParameterList();
		parameterList.setFId(fId);
		parameterList.setDelNam(SecurityUtils.getUser().getUsername());
		parameterList.setDelTim(new Date());
        return R.ok(parameterListService.updateById(parameterList));
    }

}
