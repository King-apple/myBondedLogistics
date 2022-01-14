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
import com.newweb.mrli.ykport.baoshuisystem.entity.CustomsBrokerInfo;
import com.newweb.mrli.ykport.baoshuisystem.entity.DeptInfo;
import com.newweb.mrli.ykport.baoshuisystem.service.DeptInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 部门信息码表
 *
 * @author pigx code generator
 * @date 2021-08-30 17:34:01
 */
@RestController
@AllArgsConstructor
@RequestMapping("/deptinfo" )
@Api(value = "deptinfo", tags = "部门信息码表管理")
public class DeptInfoController {

    private final  DeptInfoService deptInfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param deptInfo 部门信息码表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )

    public R getDeptInfoPage(Page page, DeptInfo deptInfo) {
        return R.ok(deptInfoService.page(page, Wrappers.query(deptInfo).isNull("DEL_TIM")));
    }


    /**
     * 通过id查询部门信息码表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('deptinfo_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(deptInfoService.getById(fId));
    }

    /**
     * 新增部门信息码表
     * @param deptInfo 部门信息码表
     * @return R
     */
    @ApiOperation(value = "新增部门信息码表", notes = "新增部门信息码表")
    @SysLog("新增部门信息码表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('deptinfo_add')" )
    public R save(@RequestBody DeptInfo deptInfo) {
		DeptInfo deptInfo1 = new DeptInfo();
		deptInfo1.setDeptNo(deptInfo.getDeptNo());
		List<DeptInfo> list = deptInfoService.list(Wrappers.query(deptInfo1));
		if(list.size() > 0){
			return R.failed("已存在相同代码");
		}
		deptInfo1 = new DeptInfo();
		deptInfo1.setDeptNam(deptInfo.getDeptNam());
		list = deptInfoService.list(Wrappers.query(deptInfo1));
		if(list.size() > 0){
			return R.failed("已存在相同部门");
		}

		deptInfo.setOperNam(SecurityUtils.getUser().getUsername());
		deptInfo.setRecordTim(new Date());
		deptInfo.setDeptNo(deptInfo.getDeptNo().toUpperCase());
        return R.ok(deptInfoService.save(deptInfo));
    }

    /**
     * 修改部门信息码表
     * @param deptInfo 部门信息码表
     * @return R
     */
    @ApiOperation(value = "修改部门信息码表", notes = "修改部门信息码表")
    @SysLog("修改部门信息码表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('deptinfo_edit')" )
    public R updateById(@RequestBody DeptInfo deptInfo) {
		deptInfo.setUpdateNam(SecurityUtils.getUser().getUsername());
		deptInfo.setRecordTim(new Date());
        return R.ok(deptInfoService.updateById(deptInfo));
    }

    /**
     * 通过id删除部门信息码表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除部门信息码表", notes = "通过id删除部门信息码表")
    @SysLog("通过id删除部门信息码表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('deptinfo_del')" )
    public R removeById(@PathVariable Integer fId) {
		DeptInfo deptInfo = new DeptInfo();
		deptInfo.setFId(fId);
		deptInfo.setDelNam(SecurityUtils.getUser().getUsername());
		deptInfo.setDelTim(new Date());
        return R.ok(deptInfoService.removeById(fId));
    }

}
