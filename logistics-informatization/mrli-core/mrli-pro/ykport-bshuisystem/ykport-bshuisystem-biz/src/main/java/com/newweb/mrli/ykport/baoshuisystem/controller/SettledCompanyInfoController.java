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
import com.newweb.mrli.ykport.baoshuisystem.entity.OrganizerInfo;
import com.newweb.mrli.ykport.baoshuisystem.entity.SettledCompanyInfo;
import com.newweb.mrli.ykport.baoshuisystem.service.SettledCompanyInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 入驻企业信息表
 *
 * @author pigx code generator
 * @date 2021-08-20 08:44:33
 */
@RestController
@AllArgsConstructor
@RequestMapping("/settledcompanyinfo" )
@Api(value = "settledcompanyinfo", tags = "入驻企业信息表管理")
public class SettledCompanyInfoController {

    private final  SettledCompanyInfoService settledCompanyInfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param settledCompanyInfo 入驻企业信息表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('settledcompanyinfo_view')" )
    public R getSettledCompanyInfoPage(Page page, SettledCompanyInfo settledCompanyInfo) {
        return R.ok(settledCompanyInfoService.page(page, Wrappers.query(settledCompanyInfo).isNull("DEL_TIM")));
    }


    /**
     * 通过id查询入驻企业信息表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('settledcompanyinfo_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(settledCompanyInfoService.getById(fId));
    }

    /**
     * 新增入驻企业信息表
     * @param settledCompanyInfo 入驻企业信息表
     * @return R
     */
    @ApiOperation(value = "新增入驻企业信息表", notes = "新增入驻企业信息表")
    @SysLog("新增入驻企业信息表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('settledcompanyinfo_add')" )
    public R save(@RequestBody SettledCompanyInfo settledCompanyInfo) {
		SettledCompanyInfo settledCompanyInfo1 = new SettledCompanyInfo();
		settledCompanyInfo1.setCompanyCode(settledCompanyInfo.getCompanyCode());
		List<SettledCompanyInfo> list = settledCompanyInfoService.list(Wrappers.query(settledCompanyInfo1));
		if(list.size() > 0){
			return R.failed("已存在相同编号");
		}
		settledCompanyInfo1 = new SettledCompanyInfo();
		settledCompanyInfo1.setCompanyName(settledCompanyInfo.getCompanyName());
		list = settledCompanyInfoService.list(Wrappers.query(settledCompanyInfo1));
		if(list.size() > 0){
			return R.failed("已存在相同入驻企业");
		}
    	Integer fid = settledCompanyInfoService.selectFid();
    	settledCompanyInfo.setFId(fid);
		settledCompanyInfo.setOperNam(SecurityUtils.getUser().getUsername());
		settledCompanyInfo.setRecordTim(new Date());
        return R.ok(settledCompanyInfoService.save(settledCompanyInfo));
    }

    /**
     * 修改入驻企业信息表
     * @param settledCompanyInfo 入驻企业信息表
     * @return R
     */
    @ApiOperation(value = "修改入驻企业信息表", notes = "修改入驻企业信息表")
    @SysLog("修改入驻企业信息表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('settledcompanyinfo_edit')" )
    public R updateById(@RequestBody SettledCompanyInfo settledCompanyInfo) {
		settledCompanyInfo.setUpdateNam(SecurityUtils.getUser().getUsername());
		settledCompanyInfo.setUpdateTim(new Date());
        return R.ok(settledCompanyInfoService.updateById(settledCompanyInfo));
    }

    /**
     * 通过id删除入驻企业信息表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除入驻企业信息表", notes = "通过id删除入驻企业信息表")
    @SysLog("通过id删除入驻企业信息表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('settledcompanyinfo_del')" )
    public R removeById(@PathVariable Integer fId) {
    	SettledCompanyInfo settledCompanyInfo = new SettledCompanyInfo();
		settledCompanyInfo.setDelNam(SecurityUtils.getUser().getUsername());
		settledCompanyInfo.setDelTim(new Date());
		settledCompanyInfo.setFId(fId);
        return R.ok(settledCompanyInfoService.updateById(settledCompanyInfo));
    }

	/**
	 * 整体查询
	 * @return
	 */
	@ApiOperation(value = "整体查询", notes = "整体查询")
	@GetMapping("/getAll" )
	public R getAll() {
		return R.ok(settledCompanyInfoService.list(Wrappers.query(new SettledCompanyInfo()).isNull("DEL_NAM")));
	}

}
