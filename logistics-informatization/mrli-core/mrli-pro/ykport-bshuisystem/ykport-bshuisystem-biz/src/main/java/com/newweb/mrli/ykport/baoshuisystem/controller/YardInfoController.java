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

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.ykport.baoshuisystem.entity.Bcmcxtab;
import com.newweb.mrli.ykport.baoshuisystem.entity.Ioyard;
import com.newweb.mrli.ykport.baoshuisystem.entity.YardInfo;
import com.newweb.mrli.ykport.baoshuisystem.service.YardInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 电子货位数据表
 *
 * @author pigx code generator
 * @date 2021-09-17 15:39:11
 */
@RestController
@AllArgsConstructor
@RequestMapping("/yardinfo" )
@Api(value = "yardinfo", tags = "电子货位数据表管理")
public class YardInfoController {

    private final  YardInfoService yardInfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param yardInfo 电子货位数据表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('baoshuisystem_yardinfo_view')" )
    public R getYardInfoPage(Page page, YardInfo yardInfo) {
		IPage<YardInfo> yardInfoPageList = yardInfoService.lambdaQuery()
				.like(yardInfo.getBh()!=null, YardInfo::getBh, yardInfo.getBh())
				.like(StringUtils.isNotBlank(yardInfo.getConsignNam()), YardInfo::getConsignNam, yardInfo.getConsignNam())
				.like(StringUtils.isNotBlank(yardInfo.getCargoNam()), YardInfo::getCargoNam, yardInfo.getCargoNam())
				.eq(StringUtils.isNotBlank(yardInfo.getAreaNam()), YardInfo::getAreaNam, yardInfo.getAreaNam())
				.isNull(true,YardInfo::getDelNam)
				.orderByDesc(true,YardInfo::getRecordTim)
				.page(new Page<>(page.getCurrent(), page.getSize()));
		return R.ok(yardInfoPageList);
    }


    /**
     * 通过id查询电子货位数据表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('baoshuisystem_yardinfo_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(yardInfoService.getById(fId));
    }

    /**
     * 新增电子货位数据表
     * @param yardInfo 电子货位数据表
     * @return R
     */
    @ApiOperation(value = "新增电子货位数据表", notes = "新增电子货位数据表")
    @SysLog("新增电子货位数据表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('baoshuisystem_yardinfo_add')" )
    public R save(@RequestBody YardInfo yardInfo) {
        return R.ok(yardInfoService.save(yardInfo));
    }

    /**
     * 修改电子货位数据表
     * @param yardInfo 电子货位数据表
     * @return R
     */
    @ApiOperation(value = "修改电子货位数据表", notes = "修改电子货位数据表")
    @SysLog("修改电子货位数据表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('baoshuisystem_yardinfo_edit')" )
    public R updateById(@RequestBody YardInfo yardInfo) {
        return R.ok(yardInfoService.updateById(yardInfo));
    }

    /**
     * 通过id删除电子货位数据表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除电子货位数据表", notes = "通过id删除电子货位数据表")
    @SysLog("通过id删除电子货位数据表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('baoshuisystem_yardinfo_del')" )
    public R removeById(@PathVariable Integer fId) {
        return R.ok(yardInfoService.removeById(fId));
    }

	/**
	 * 分页查询
	 * @param yardInfo 电子货位数据表
	 * @return
	 */
	@ApiOperation(value = "全部查询", notes = "全部查询")
	@GetMapping("/all" )
	public R getYardInfoAll(YardInfo yardInfo) {
		return R.ok(yardInfoService.list(Wrappers.query(yardInfo).isNull("DEL_NAM")));
	}

	/**
	 * 货位合并
	 * @param yardInfoList 电子货位数据表
	 * @return
	 */
	@ApiOperation(value = "货位合并", notes = "货位合并")
	@PostMapping("/cargoSpaceMerage" )
	public R cargoSpaceMerage(@RequestBody String yardInfoList) {
		String flag = yardInfoService.cargoSpaceMerage(yardInfoList);
		if("1".equals(flag)){
			return R.failed("请选择同一货票编号下数据合并");
		}else{
			return R.ok("合并成功");
		}
	}

	/**
	 * 货位拆分
	 * @param yardInfo 电子货位数据表
	 * @return
	 */
	@ApiOperation(value = "货位拆分", notes = "货位拆分")
	@PostMapping("/cargoSpaceSplit" )
	public R cargoSpaceSplit(@RequestBody YardInfo yardInfo) {
		return yardInfoService.cargoSpaceSplit(yardInfo);
	}
	/**
	 * 下拉查询调教
	 * @return
	 */
	@ApiOperation(value = "查询下拉", notes = "查询下拉")
	@SysLog("查询下拉" )
	@GetMapping("/getSelectConditionInfo" )
	public R getSelectCondition() {
		//货名
		List<Ioyard> listCargo =  yardInfoService.getCargo();
		//作业委托人
		List<Ioyard> listConSign =  yardInfoService.getConSign();
		Map<String, Object> map = new HashMap<>();
		map.put("listCargo",listCargo);
		map.put("listConSign",listConSign);
		return R.ok(map);
	}
}
