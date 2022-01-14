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
import com.newweb.mrli.ykport.baoshuisystem.entity.SettlementBodyRate;
import com.newweb.mrli.ykport.baoshuisystem.service.SettlementBodyRateService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 出入库结算单明细费用表
 *
 * @author pigx code generator
 * @date 2021-11-01 09:15:08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/settlementbodyrate" )
@Api(value = "settlementbodyrate", tags = "出入库结算单明细费用表管理")
public class SettlementBodyRateController {

    private final SettlementBodyRateService settlementBodyRateService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param settlementBodyRate 出入库结算单明细费用表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSettlementBodyRatePage(Page page, SettlementBodyRate settlementBodyRate) {
        return R.ok(settlementBodyRateService.page(page, Wrappers.query(settlementBodyRate)));
    }


    /**
     * 通过id查询出入库结算单明细费用表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    public R getById(@PathVariable("fId" ) Integer fId) {
		QueryWrapper<SettlementBodyRate> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("R_FID",fId);
		queryWrapper.isNull("DEL_NAM");
		queryWrapper.isNull("DEL_TIM");
    	return R.ok(settlementBodyRateService.list(queryWrapper));
    }

    /**
     * 新增出入库结算单明细费用表
     * @return R
     */
    @ApiOperation(value = "新增出入库结算单明细费用表", notes = "新增出入库结算单明细费用表")
    @SysLog("新增出入库结算单明细费用表" )
    @GetMapping("/add/{id}")
    public R save(@PathVariable Integer id) {
		List<SettlementBodyRate> s = settlementBodyRateService.getList(id);
		boolean b = settlementBodyRateService.saveBatch(s);
		return R.ok(b);
    }

    /**
     * 修改出入库结算单明细费用表
     * @param settlementBodyRate 出入库结算单明细费用表
     * @return R
     */
    @ApiOperation(value = "修改出入库结算单明细费用表", notes = "修改出入库结算单明细费用表")
    @SysLog("修改出入库结算单明细费用表" )
    @PutMapping
    public R updateById(@RequestBody SettlementBodyRate settlementBodyRate) {
        return R.ok(settlementBodyRateService.updateById(settlementBodyRate));
    }

    /**
     * 通过id删除出入库结算单明细费用表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除出入库结算单明细费用表", notes = "通过id删除出入库结算单明细费用表")
    @SysLog("通过id删除出入库结算单明细费用表" )
    @DeleteMapping("/{fId}" )
    public R removeById(@PathVariable Integer fId) {
        return R.ok(settlementBodyRateService.removeById(fId));
    }

}
