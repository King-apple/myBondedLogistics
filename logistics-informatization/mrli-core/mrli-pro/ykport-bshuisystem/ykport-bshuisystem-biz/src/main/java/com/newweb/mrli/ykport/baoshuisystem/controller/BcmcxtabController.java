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
import com.newweb.mrli.ykport.baoshuisystem.entity.Bcmcxtab;
import com.newweb.mrli.ykport.baoshuisystem.service.BcmcxtabService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 历史车信息
 *
 * @author pigx code generator
 * @date 2021-09-27 10:35:40
 */
@RestController
@AllArgsConstructor
@RequestMapping("/bcmcxtab" )
@Api(value = "bcmcxtab", tags = "历史车信息管理")
public class BcmcxtabController {

    private final  BcmcxtabService bcmcxtabService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param bcmcxtab 历史车信息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('baoshuisystem_bcmcxtab_view')" )
    public R getBcmcxtabPage(Page page, Bcmcxtab bcmcxtab) {
        return R.ok(bcmcxtabService.page(page, Wrappers.query(bcmcxtab)));
    }


    /**
     * 通过id查询历史车信息
     * @param bh id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{bh}" )
    @PreAuthorize("@pms.hasPermission('baoshuisystem_bcmcxtab_view')" )
    public R getById(@PathVariable("bh" ) String bh) {
        return R.ok(bcmcxtabService.getById(bh));
    }

    /**
     * 新增历史车信息
     * @param bcmcxtab 历史车信息
     * @return R
     */
    @ApiOperation(value = "新增历史车信息", notes = "新增历史车信息")
    @SysLog("新增历史车信息" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('baoshuisystem_bcmcxtab_add')" )
    public R save(@RequestBody Bcmcxtab bcmcxtab) {
        return R.ok(bcmcxtabService.save(bcmcxtab));
    }

    /**
     * 修改历史车信息
     * @param bcmcxtab 历史车信息
     * @return R
     */
    @ApiOperation(value = "修改历史车信息", notes = "修改历史车信息")
    @SysLog("修改历史车信息" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('baoshuisystem_bcmcxtab_edit')" )
    public R updateById(@RequestBody Bcmcxtab bcmcxtab) {
        return R.ok(bcmcxtabService.updateById(bcmcxtab));
    }

    /**
     * 通过id删除历史车信息
     * @param bh id
     * @return R
     */
    @ApiOperation(value = "通过id删除历史车信息", notes = "通过id删除历史车信息")
    @SysLog("通过id删除历史车信息" )
    @DeleteMapping("/{bh}" )
    @PreAuthorize("@pms.hasPermission('baoshuisystem_bcmcxtab_del')" )
    public R removeById(@PathVariable String bh) {
        return R.ok(bcmcxtabService.removeById(bh));
    }

}
