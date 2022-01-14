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
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.CargoNamCustoms;
import com.newweb.mrli.ykport.baoshuisystem.entity.ClientInfo;
import com.newweb.mrli.ykport.baoshuisystem.entity.FeeUnits;
import com.newweb.mrli.ykport.baoshuisystem.service.CargoNamCustomsService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 海关货名码表
 *
 * @author pigx code generator
 * @date 2021-09-18 09:43:14
 */
@RestController
@AllArgsConstructor
@RequestMapping("/cargonamcustoms" )
@Api(value = "cargonamcustoms", tags = "海关货名码表管理")
public class CargoNamCustomsController {

    private final  CargoNamCustomsService cargoNamCustomsService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param cargoNamCustoms 海关货名码表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('cargonamcustoms_view')" )
    public R getCargoNamCustomsPage(Page page, CargoNamCustoms cargoNamCustoms) {
		QueryWrapper<CargoNamCustoms> query = Wrappers.query(new CargoNamCustoms())
				.like(StringUtils.isNotBlank(cargoNamCustoms.getCargoCod()),"CARGO_COD",cargoNamCustoms.getCargoCod())
				.like(StringUtils.isNotBlank(cargoNamCustoms.getCargoNam()),"CARGO_NAM",cargoNamCustoms.getCargoNam())
				.isNull("DEL_TIM")
				.orderByDesc("record_Tim");
		return R.ok(cargoNamCustomsService.page(page, query));
    }


    /**
     * 通过id查询海关货名码表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('cargonamcustoms_view')" )
    public R getById(@PathVariable("fId" ) Long fId) {
        return R.ok(cargoNamCustomsService.getById(fId));
    }

    /**
     * 新增海关货名码表
     * @param cargoNamCustoms 海关货名码表
     * @return R
     */
    @ApiOperation(value = "新增海关货名码表", notes = "新增海关货名码表")
    @SysLog("新增海关货名码表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('cargonamcustoms_add')" )
    public R save(@RequestBody CargoNamCustoms cargoNamCustoms) {
		CargoNamCustoms cargoNamCustoms1 = new CargoNamCustoms();
		cargoNamCustoms1.setCargoCod(cargoNamCustoms.getCargoCod());
		List<CargoNamCustoms> list = cargoNamCustomsService.list(Wrappers.query(cargoNamCustoms1));
		if(list.size() > 0){
			return R.failed("已存在相同代码");
		}
		cargoNamCustoms1 = new CargoNamCustoms();
		cargoNamCustoms1.setCargoNam(cargoNamCustoms.getCargoNam());
		list = cargoNamCustomsService.list(Wrappers.query(cargoNamCustoms1));
		if(list.size() > 0){
			return R.failed("已存在相同货名");
		}

		cargoNamCustoms.setOperNam(SecurityUtils.getUser().getUsername());
		cargoNamCustoms.setRecordTim(new Date());
        return R.ok(cargoNamCustomsService.save(cargoNamCustoms));
    }

    /**
     * 修改海关货名码表
     * @param cargoNamCustoms 海关货名码表
     * @return R
     */
    @ApiOperation(value = "修改海关货名码表", notes = "修改海关货名码表")
    @SysLog("修改海关货名码表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('cargonamcustoms_edit')" )
    public R updateById(@RequestBody CargoNamCustoms cargoNamCustoms) {
		cargoNamCustoms.setUpdateNam(SecurityUtils.getUser().getUsername());
		cargoNamCustoms.setUpdateTim(new Date());
        return R.ok(cargoNamCustomsService.updateById(cargoNamCustoms));
    }

    /**
     * 通过id删除海关货名码表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除海关货名码表", notes = "通过id删除海关货名码表")
    @SysLog("通过id删除海关货名码表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('cargonamcustoms_del')" )
    public R removeById(@PathVariable Long fId) {
		CargoNamCustoms cargoNamCustoms = new CargoNamCustoms();
		cargoNamCustoms.setFId(fId);
		cargoNamCustoms.setDelNam(SecurityUtils.getUser().getUsername());
		cargoNamCustoms.setDelTim(new Date());
        return R.ok(cargoNamCustomsService.updateById(cargoNamCustoms));
    }

	/**
	 * 整体查询
	 * @return
	 */
	@ApiOperation(value = "整体查询", notes = "整体查询")
	@GetMapping("/all" )
	public R getAll() {
		CargoNamCustoms cargoNamCustoms = new CargoNamCustoms();
		QueryWrapper<CargoNamCustoms> cargoNamCustomsQueryWrapper = Wrappers.query(cargoNamCustoms).isNull("DEL_NAM");
		final List<CargoNamCustoms> cargoNamCustomsList = cargoNamCustomsService.list(cargoNamCustomsQueryWrapper);
		return R.ok(cargoNamCustomsList);
	}
}
