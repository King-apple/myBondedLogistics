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
import com.newweb.mrli.ykport.baoshuisystem.entity.CostType;
import com.newweb.mrli.ykport.baoshuisystem.service.CostTypeService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * 费用类型码表
 *
 * @author pigx code generator
 * @date 2021-07-28 10:07:25
 */
@RestController
@AllArgsConstructor
@RequestMapping("/costtype" )
@Api(value = "costtype", tags = "费用类型码表管理")
public class CostTypeController {

    private final  CostTypeService costTypeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param costType 费用类型码表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('costtype_view')" )
    public R getCostTypePage(Page page, CostType costType) {
        return R.ok(costTypeService.page(page, Wrappers.query(costType).isNull("DEL_TIM")));
    }


    /**
     * 通过id查询费用类型码表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('costtype_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(costTypeService.getById(fId));
    }

    /**
     * 新增费用类型码表
     * @param costType 费用类型码表
     * @return R
     */
    @ApiOperation(value = "新增费用类型码表", notes = "新增费用类型码表")
    @SysLog("新增费用类型码表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('costtype_add')" )
    public R save(@RequestBody CostType costType) {
		Integer fId = costTypeService.selectFid();
		costType.setFId(fId);
		costType.setOperNam(SecurityUtils.getUser().getUsername());
		costType.setRecordTim(new Date());
		return R.ok(costTypeService.save(costType));
    }

    /**
     * 修改费用类型码表
     * @param costType 费用类型码表
     * @return R
     */
    @ApiOperation(value = "修改费用类型码表", notes = "修改费用类型码表")
    @SysLog("修改费用类型码表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('costtype_edit')" )
    public R updateById(@RequestBody CostType costType) {
		costType.setUpdateNam(SecurityUtils.getUser().getUsername());
		costType.setUpdateTim(new Date());
        return R.ok(costTypeService.updateById(costType));
    }

    /**
     * 通过id删除费用类型码表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除费用类型码表", notes = "通过id删除费用类型码表")
    @SysLog("通过id删除费用类型码表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('costtype_del')" )
    public R removeById(@PathVariable Integer fId) {
    	CostType costType = new CostType();
		costType.setDelNam(SecurityUtils.getUser().getUsername());
		costType.setDelTim(new Date());
		costType.setFId(fId);
        return R.ok(costTypeService.updateById(costType));
    }

}
