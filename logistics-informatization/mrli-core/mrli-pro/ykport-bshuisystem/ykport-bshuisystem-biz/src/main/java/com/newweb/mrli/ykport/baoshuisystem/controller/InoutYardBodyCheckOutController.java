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
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardHead;
import com.newweb.mrli.ykport.baoshuisystem.service.InoutYardBodyService;
import com.newweb.mrli.ykport.baoshuisystem.service.InoutYardHeadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * 出入库单表体
 *
 * @author zada
 * @date 2021-09-02 18:42:44
 */
@RestController
@AllArgsConstructor
@RequestMapping("/inoutyardbodyCheckOut" )
@Api(value = "inoutyardbody", tags = "出入库单表体管理")
public class InoutYardBodyCheckOutController {

    private final InoutYardBodyService inoutYardBodyService;
	private final InoutYardHeadService inoutYardHeadService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param inoutYardBody 出入库单表体
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('inoutyardbody_view')" )
    public R getInoutYardBodyPage(Page page, InoutYardBody inoutYardBody) {
        return R.ok(inoutYardBodyService.page(page, Wrappers.query(inoutYardBody)));
    }


    /**
     * 通过id查询出入库单表体
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('inoutyardbody_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(inoutYardBodyService.getById(fId));
    }

    /**
     * 新增出入库单表体
     * @param inoutYardBody 出入库单表体
     * @return R
     */
    @ApiOperation(value = "新增出入库单表体", notes = "新增出入库单表体")
    @SysLog("新增出入库单表体" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('inoutyardbody_add')" )
    public R save(@RequestBody InoutYardBody inoutYardBody) {
		InoutYardHead inoutYardHead = inoutYardHeadService.getById(inoutYardBody.getHFid());
		if(inoutYardHead!=null){
			if(inoutYardHead.getBusinessCheckStatus().equals("1")){
				return R.failed("当前保税物流数据已经审核通过，不允许新增！");
			}

		}
        return R.ok(inoutYardBodyService.save(inoutYardBody));

    }

    /**
     * 修改出入库单表体
     * @param inoutYardBody 出入库单表体
     * @return R
     */
    @ApiOperation(value = "修改出入库单表体", notes = "修改出入库单表体")
    @SysLog("修改出入库单表体" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('inoutyardbody_edit')" )
    public R updateById(@RequestBody InoutYardBody inoutYardBody) {
		InoutYardHead inoutYardHead = inoutYardHeadService.getById(inoutYardBody.getHFid());
		if(inoutYardHead!=null){
			if(inoutYardHead.getBusinessCheckStatus().equals("1")){
				return R.failed("当前保税物流数据已经审核通过，不允许修改！");
			}

		}
        return R.ok(inoutYardBodyService.updateById(inoutYardBody));
    }

    /**
     * 通过id删除出入库单表体
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除出入库单表体", notes = "通过id删除出入库单表体")
    @SysLog("通过id删除出入库单表体" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('inoutyardbody_del')" )
    public R removeById(@PathVariable Integer fId) {
		InoutYardBody inoutYardBody = inoutYardBodyService.getById(fId);
		InoutYardHead inoutYardHead = inoutYardHeadService.getById(inoutYardBody.getHFid());
		if(inoutYardHead!=null){
			if(inoutYardHead.getBusinessCheckStatus().equals("1")){
				return R.failed("当前保税物流数据已经审核通过，不允许删除！");
			}
			if(inoutYardHead.getOperCheckStatus().equals("1") && !inoutYardHead.getInCheckNum().equals(inoutYardHead.getInCheckNum())){
				return R.failed("出区核注清单号已经审核通过，不允许s删除！");
			}
		}
		InoutYardBody inoutYardBodyOL = new InoutYardBody();
		inoutYardBodyOL.setDelNam(SecurityUtils.getUser().getUsername());
		inoutYardBodyOL.setDelTim(new Date());
		inoutYardBodyOL.setFId(fId);
		return R.ok(inoutYardBodyService.updateById(inoutYardBodyOL));
    }

}
