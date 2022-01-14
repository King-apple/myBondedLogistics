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

import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.WarehouseNamInfo;
import com.newweb.mrli.ykport.baoshuisystem.service.WarehouseNamInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 仓库名称信息表
 *
 * @author Tara
 * @date 2021-10-28 17:35:01
 */
@RestController
@AllArgsConstructor
@RequestMapping("/warehousenaminfo" )
@Api(value = "warehousenaminfo", tags = "仓库名称信息表管理")
public class WarehouseNamInfoController {

    private final  WarehouseNamInfoService warehouseNamInfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param warehouseNamInfo 仓库名称信息表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('warehousenaminfo_view')" )
    public R getWarehouseNamInfoPage(Page page, WarehouseNamInfo warehouseNamInfo) {
    	if (StringUtil.isEmpty(warehouseNamInfo.getInOut())) {
			warehouseNamInfo.setInOut(null);
		}
        return R.ok(warehouseNamInfoService.page(page, Wrappers.query(warehouseNamInfo).isNull("DEL_TIM")));
    }


    /**
     * 通过id查询仓库名称信息表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('warehousenaminfo_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(warehouseNamInfoService.getById(fId));
    }

    /**
     * 新增仓库名称信息表
     * @param warehouseNamInfo 仓库名称信息表
     * @return R
     */
    @ApiOperation(value = "新增仓库名称信息表", notes = "新增仓库名称信息表")
    @SysLog("新增仓库名称信息表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('warehousenaminfo_add')" )
    public R save(@RequestBody WarehouseNamInfo warehouseNamInfo) {
    	String msg = checkItem(warehouseNamInfo);
    	if (StringUtil.isEmpty(msg)) {
			warehouseNamInfo.setOperNam(SecurityUtils.getUser().getUsername());
			warehouseNamInfo.setRecordTim(new Date());
			return R.ok(warehouseNamInfoService.save(warehouseNamInfo));
		} else {
    		return R.failed(msg);
		}
    }

    /**
     * 修改仓库名称信息表
     * @param warehouseNamInfo 仓库名称信息表
     * @return R
     */
    @ApiOperation(value = "修改仓库名称信息表", notes = "修改仓库名称信息表")
    @SysLog("修改仓库名称信息表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('warehousenaminfo_edit')" )
    public R updateById(@RequestBody WarehouseNamInfo warehouseNamInfo) {
		String msg = checkItem(warehouseNamInfo);
		if (StringUtil.isEmpty(msg)) {
			warehouseNamInfo.setOperNam(SecurityUtils.getUser().getUsername());
			warehouseNamInfo.setRecordTim(new Date());
			return R.ok(warehouseNamInfoService.updateById(warehouseNamInfo));
		} else {
			return R.failed(msg);
		}
    }

    /**
     * 通过id删除仓库名称信息表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除仓库名称信息表", notes = "通过id删除仓库名称信息表")
    @SysLog("通过id删除仓库名称信息表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('warehousenaminfo_del')" )
    public R removeById(@PathVariable Long fId) {
		WarehouseNamInfo warehouseNamInfo = new WarehouseNamInfo();
		warehouseNamInfo.setFId(fId);
		warehouseNamInfo.setDelNam(SecurityUtils.getUser().getUsername());
		warehouseNamInfo.setDelTim(new Date());
        return R.ok(warehouseNamInfoService.updateById(warehouseNamInfo));
    }

	/**
	 * 其他页面查询全部
	 * @param warehouseNamInfo
	 * @return
	 */
	@GetMapping("/all" )
	public R getAll(WarehouseNamInfo warehouseNamInfo) {
		return R.ok(warehouseNamInfoService.selectAll(warehouseNamInfo));
	}

	private String checkItem(WarehouseNamInfo warehouseNamInfo) {
		String msg = "";

		WarehouseNamInfo param = new WarehouseNamInfo();
		List<WarehouseNamInfo> list = null;
		if (StringUtil.isNotEmpty(warehouseNamInfo.getInoutYardNam())) {
			param.setInoutYardNam(warehouseNamInfo.getInoutYardNam());
			list = warehouseNamInfoService.selectOldWarehouseNamInfoList(param);
			if (list != null && list.size() > 0) {
				if (warehouseNamInfo.getFId() == null || list.size() > 1 || list.get(0).getFId() != warehouseNamInfo.getFId()) {
					msg = "单据名称已存在，不可重复";
					return msg;
				}
			}
		}

		return msg;
	}

}
