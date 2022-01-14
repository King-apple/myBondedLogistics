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
import com.newweb.mrli.ykport.baoshuisystem.entity.PriceLibrary;
import com.newweb.mrli.ykport.baoshuisystem.service.PriceLibraryService;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * 价格库表
 *
 * @author pigx code generator
 * @date 2021-08-28 15:18:15
 */
@RestController
@AllArgsConstructor
@RequestMapping("/pricelibrary" )
@Api(value = "pricelibrary", tags = "价格库表管理")
public class PriceLibraryController {

    private final  PriceLibraryService priceLibraryService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param priceLibrary 价格库表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('pricelibrary_view')" )
    public R getPriceLibraryPage(Page page, PriceLibrary priceLibrary) {
    	if("".equals(priceLibrary.getCompany())){
			priceLibrary.setCompany(null);
		}
		if("".equals(priceLibrary.getCargoNam())){
			priceLibrary.setCargoNam(null);
		}
		if("".equals(priceLibrary.getCargoKindNam())){
			priceLibrary.setCargoKindNam(null);
		}
		if("".equals(priceLibrary.getLocate())){
			priceLibrary.setLocate(null);
		}
        return R.ok(priceLibraryService.page(page, Wrappers.query(priceLibrary).isNull("DEL_TIM").orderByDesc("RECORD_TIM")));
    }

    /**
     * 通过id查询价格库表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('pricelibrary_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(priceLibraryService.getById(fId));
    }

    /**
     * 新增价格库表
     * @param priceLibrary 价格库表
     * @return R
     */
    @ApiOperation(value = "新增价格库表", notes = "新增价格库表")
    @SysLog("新增价格库表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('pricelibrary_add')" )
    public R save(@RequestBody PriceLibrary priceLibrary) {
		priceLibrary.setOperNam(SecurityUtils.getUser().getUsername());
		priceLibrary.setRecordTim(new Date());
        return R.ok(priceLibraryService.save(priceLibrary));
    }

    /**
     * 修改价格库表
     * @param priceLibrary 价格库表
     * @return R
     */
    @ApiOperation(value = "修改价格库表", notes = "修改价格库表")
    @SysLog("修改价格库表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('pricelibrary_edit')" )
    public R updateById(@RequestBody PriceLibrary priceLibrary) {
		priceLibrary.setUpdateNam(SecurityUtils.getUser().getUsername());
		priceLibrary.setUpdateTim(new Date());
        return R.ok(priceLibraryService.updateById(priceLibrary));
    }

    /**
     * 通过id删除价格库表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除价格库表", notes = "通过id删除价格库表")
    @SysLog("通过id删除价格库表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('pricelibrary_del')" )
    public R removeById(@PathVariable Integer fId) {
		PriceLibrary priceLibrary = new PriceLibrary();
		priceLibrary.setFId(fId);
		priceLibrary.setDelNam(SecurityUtils.getUser().getUsername());
		priceLibrary.setDelTim(new Date());
        return R.ok(priceLibraryService.updateById(priceLibrary));
    }

}
