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
import com.newweb.mrli.ykport.baoshuisystem.entity.OrganizerInfo;
import com.newweb.mrli.ykport.baoshuisystem.entity.OrganizerUser;
import com.newweb.mrli.ykport.baoshuisystem.service.OrganizerUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 承办单位与用户关联表
 *
 * @author pigx code generator
 * @date 2021-07-29 10:55:29
 */
@RestController
@AllArgsConstructor
@RequestMapping("/organizeruser" )
@Api(value = "organizeruser", tags = "承办单位与用户关联表管理")
public class OrganizerUserController {

    private final  OrganizerUserService organizerUserService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param organizerUser 承办单位与用户关联表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOrganizerUserPage(Page page, OrganizerUser organizerUser) {
        return R.ok(organizerUserService.page(page, Wrappers.query(organizerUser)));
    }


    /**
     * 通过id查询承办单位与用户关联表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(organizerUserService.getById(fId));
    }

    /**
     * 新增承办单位与用户关联表
     * @param
     * @return R
     */
    @ApiOperation(value = "新增承办单位与用户关联表", notes = "新增承办单位与用户关联表")
    @SysLog("新增承办单位与用户关联表" )
    @GetMapping("/{uFid}/{oFid}")
    public R save(@PathVariable Integer uFid,@PathVariable Integer oFid) {
		OrganizerUser organizerUser = new OrganizerUser();
    	Integer fId = organizerUserService.selectFid();
    	organizerUser.setFId(fId);
    	organizerUser.setUFid(uFid);
    	organizerUser.setOFid(oFid);
        return R.ok(organizerUserService.save(organizerUser));
    }

    /**
     * 修改承办单位与用户关联表
     * @param organizerUser 承办单位与用户关联表
     * @return R
     */
    @ApiOperation(value = "修改承办单位与用户关联表", notes = "修改承办单位与用户关联表")
    @SysLog("修改承办单位与用户关联表" )
    @PutMapping
    public R updateById(@RequestBody OrganizerUser organizerUser) {
        return R.ok(organizerUserService.updateById(organizerUser));
    }

    /**
     * 删除承办单位与用户关联
     * @return R
     */
    @ApiOperation(value = "通过id删除承办单位与用户关联表", notes = "通过id删除承办单位与用户关联表")
    @SysLog("通过id删除承办单位与用户关联表" )
    @DeleteMapping("/{uFid}/{oFid}" )
    public R removeById(@PathVariable Integer uFid,@PathVariable Integer oFid) {
		QueryWrapper<OrganizerUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("O_FID", oFid);
		queryWrapper.eq("U_FID", uFid);
        return R.ok(organizerUserService.remove(queryWrapper));
    }

	/**
	 * 根据用户id查询当前用户详细信息
	 * @param fId
	 * @return
	 */
	@ApiOperation(value = "用户详细信息", notes = "用户详细信息")
	@GetMapping("/getOrgUserInfo/{fId}" )
	public R getOrgUserInfo(@PathVariable("fId" ) Integer fId) {
		return R.ok(organizerUserService.selectOrgUserInfoVo(fId));
	}
}
