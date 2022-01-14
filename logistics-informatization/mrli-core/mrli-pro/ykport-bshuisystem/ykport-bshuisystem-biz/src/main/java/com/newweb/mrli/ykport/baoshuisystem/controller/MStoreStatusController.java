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

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.ykport.baoshuisystem.entity.MStoreStatus;
import com.newweb.mrli.ykport.baoshuisystem.service.MStoreStatusService;
import com.newweb.mrli.ykport.baoshuisystem.vo.ShipVo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


/**
 * 港存动态（局）
 *
 * @author pigx code generator
 * @date 2021-10-19 18:28:44
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mstorestatus" )
@Api(value = "mstorestatus", tags = "港存动态（局）管理")
public class MStoreStatusController {

    private final  MStoreStatusService mStoreStatusService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param mStoreStatus 港存动态（局）
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('mstorestatus_view')" )
	@DS("slave2")
    public R getMStoreStatusPage(Page page, MStoreStatus mStoreStatus) {
		Page<MStoreStatus> page1 = mStoreStatusService.lambdaQuery()
				.eq(mStoreStatus.getRecordTim() != null, MStoreStatus::getRecordTim, mStoreStatus.getRecordTim())
				.eq(StringUtils.isNotBlank(mStoreStatus.getUnitCod()), MStoreStatus::getUnitCod, mStoreStatus.getUnitCod())
				.eq(mStoreStatus.getInoutId() != null, MStoreStatus::getUnitCod, mStoreStatus.getUnitCod())
				.eq(mStoreStatus.getTradeId() != null, MStoreStatus::getTradeId, mStoreStatus.getTradeId())
				.eq(StringUtils.isNotBlank(mStoreStatus.getCargoNamCod()), MStoreStatus::getCargoNamCod, mStoreStatus.getCargoNamCod())
				.eq(StringUtils.isNotBlank(mStoreStatus.getSubunitCod()), MStoreStatus::getSubunitCod, mStoreStatus.getSubunitCod())
				.eq(StringUtils.isNotBlank(mStoreStatus.getShipNam()), MStoreStatus::getShipNam, mStoreStatus.getShipNam())
				.eq(StringUtils.isNotBlank(mStoreStatus.getConsignCod()), MStoreStatus::getConsignCod, mStoreStatus.getConsignCod())
				.eq(StringUtils.isNotBlank(mStoreStatus.getShipperCod()), MStoreStatus::getShipperCod, mStoreStatus.getShipperCod())
				.page(new Page<>(page.getCurrent(), page.getSize()));
		return R.ok(page1);
    }


    /**
     * 通过id查询港存动态（局）
     * @param unitCod id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{unitCod}" )
    @PreAuthorize("@pms.hasPermission('mstorestatus_view')" )
    public R getById(@PathVariable("unitCod" ) String unitCod) {
        return R.ok(mStoreStatusService.getById(unitCod));
    }

	/**
	 * 查询公司信息
	 * @return R
	 */
	@ApiOperation(value = "查询公司信息", notes = "查询公司信息")
	@GetMapping("/getCunitNam" )
	public R getCunitNam() {
		return R.ok(mStoreStatusService.getCunitNam());
	}

	/**
	 * 查询货名信息
	 * @return R
	 */
	@ApiOperation(value = "查询货名信息", notes = "查询货名信息")
	@GetMapping("/getCargoNam" )
	public R getCargoNam() {
		return R.ok(mStoreStatusService.getCargoNam());
	}

	/**
	 * 查询保管单位信息
	 * @return R
	 */
	@ApiOperation(value = "查询保管单位信息", notes = "查询保管单位信息")
	@GetMapping("/getSubunit/{unitCod}" )
	public R getSubunit(@PathVariable("unitCod" ) String uniCode) {
		return R.ok(mStoreStatusService.getSubunit(uniCode));
	}

	/**
	 * 查询保管单位信息
	 * @return R
	 */
	@ApiOperation(value = "查询进口船名", notes = "查询进口船名")
	@GetMapping("/getShip" )
	public R getShip(Page page, ShipVo shipVo) {
		return R.ok(mStoreStatusService.getShip(page,shipVo));
	}

	/**
	 * 查询港口货主
	 * @return R
	 */
	@ApiOperation(value = "查询港口货主", notes = "查询港口货主")
	@GetMapping("/getShipperDoc" )
	public R getShipperDoc() {
		return R.ok(mStoreStatusService.getShipperDoc());
	}

	/**
	 * 打印excel
	 * @return R
	 */
	@ApiOperation(value = "打印excel", notes = "打印excel")
	@GetMapping("/printToExcel" )
	public void printToExcel(MStoreStatus mStoreStatus) throws IOException {
		mStoreStatusService.printToExcel(mStoreStatus);
	}
}
