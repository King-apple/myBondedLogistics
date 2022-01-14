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
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardListBody;
import com.newweb.mrli.ykport.baoshuisystem.service.IoyardListBodyService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 出入库明细表体表
 *
 * @author pigx code generator
 * @date 2021-09-09 15:02:10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ioyardlistbody" )
@Api(value = "ioyardlistbody", tags = "出入库明细表体表管理")
public class IoyardListBodyController {

    private final  IoyardListBodyService ioyardListBodyService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ioyardListBody 出入库明细表体表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('ioyardlistbody_view')" )
    public R getIoyardListBodyPage(Page page, IoyardListBody ioyardListBody) {
        return R.ok(ioyardListBodyService.page(page, Wrappers.query(ioyardListBody)));
    }


    /**
     * 通过id查询出入库明细表体表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('ioyardlistbody_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(ioyardListBodyService.getById(fId));
    }

    /**
     * 新增出入库明细表体表
     * @param ioyardListBody 出入库明细表体表
     * @return R
     */
    @ApiOperation(value = "新增出入库明细表体表", notes = "新增出入库明细表体表")
    @SysLog("新增出入库明细表体表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('ioyardlistbody_add')" )
    public R save(@RequestBody IoyardListBody ioyardListBody) {
        return R.ok(ioyardListBodyService.save(ioyardListBody));
    }

    /**
     * 修改出入库明细表体表
     * @param ioyardListBody 出入库明细表体表
     * @return R
     */
    @ApiOperation(value = "修改出入库明细表体表", notes = "修改出入库明细表体表")
    @SysLog("修改出入库明细表体表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('ioyardlistbody_edit')" )
    public R updateById(@RequestBody IoyardListBody ioyardListBody) {
        return R.ok(ioyardListBodyService.updateById(ioyardListBody));
    }

    /**
     * 通过id删除出入库明细表体表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除出入库明细表体表", notes = "通过id删除出入库明细表体表")
    @SysLog("通过id删除出入库明细表体表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('ioyardlistbody_del')" )
    public R removeById(@PathVariable Long fId) {
        return R.ok(ioyardListBodyService.removeIoyardListBody(fId));
    }

}
