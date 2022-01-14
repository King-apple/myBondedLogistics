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

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.StorageLocation;
import com.newweb.mrli.ykport.baoshuisystem.service.StorageLocationService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * 存放地点码表
 *
 * @author pigx code generator
 * @date 2021-09-18 15:57:39
 */
@RestController
@AllArgsConstructor
@RequestMapping("/storagelocation" )
@Api(value = "storagelocation", tags = "存放地点码表管理")
public class StorageLocationController {

    private final  StorageLocationService storageLocationService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param storageLocation 存放地点码表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('storagelocation_view')" )
    public R getStorageLocationPage(Page page, StorageLocation storageLocation) {
        return R.ok(storageLocationService.lambdaQuery()
				.isNull(true,StorageLocation::getDelNam)
				.like(StringUtils.isNotBlank(storageLocation.getLocation()),StorageLocation::getLocation,storageLocation.getLocation())
				.page(new Page<>(page.getCurrent(), page.getSize())));
    }


    /**
     * 通过id查询存放地点码表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('storagelocation_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(storageLocationService.getById(fId));
    }

    /**
     * 新增存放地点码表
     * @param storageLocation 存放地点码表
     * @return R
     */
    @ApiOperation(value = "新增存放地点码表", notes = "新增存放地点码表")
    @SysLog("新增存放地点码表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('storagelocation_add')" )
    public R save(@RequestBody StorageLocation storageLocation) {
		storageLocation.setOperator(SecurityUtils.getUser().getUsername());
		storageLocation.setRecordTim(new Date());
        return R.ok(storageLocationService.save(storageLocation));
    }

    /**
     * 修改存放地点码表
     * @param storageLocation 存放地点码表
     * @return R
     */
    @ApiOperation(value = "修改存放地点码表", notes = "修改存放地点码表")
    @SysLog("修改存放地点码表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('storagelocation_edit')" )
    public R updateById(@RequestBody StorageLocation storageLocation) {
		storageLocation.setUpdateNam(SecurityUtils.getUser().getUsername());
		storageLocation.setUpdateTim(new Date());
        return R.ok(storageLocationService.updateById(storageLocation));
    }

    /**
     * 通过id删除存放地点码表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除存放地点码表", notes = "通过id删除存放地点码表")
    @SysLog("通过id删除存放地点码表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('storagelocation_del')" )
    public R removeById(@PathVariable Long fId) {
		StorageLocation storageLocation = new StorageLocation();
		storageLocation.setFId(fId);
		storageLocation.setDelNam(SecurityUtils.getUser().getUsername());
		storageLocation.setDelTim(new Date());
		return R.ok(storageLocationService.updateById(storageLocation));
    }

	/**
	 * 整体查询
	 * @return
	 */
	@ApiOperation(value = "整体查询", notes = "整体查询")
	@GetMapping("/all" )
	public R getStorageLocationAll() {
		return R.ok(storageLocationService.list(Wrappers.query(new StorageLocation()).isNull("DEL_NAM")));
	}
}
