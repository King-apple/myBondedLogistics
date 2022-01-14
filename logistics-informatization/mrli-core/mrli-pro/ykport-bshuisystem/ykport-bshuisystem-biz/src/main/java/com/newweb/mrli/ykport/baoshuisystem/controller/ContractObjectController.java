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

import com.alibaba.druid.sql.visitor.functions.Now;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.CheckWeight;
import com.newweb.mrli.ykport.baoshuisystem.entity.ClientInfo;
import com.newweb.mrli.ykport.baoshuisystem.entity.ContractObject;
import com.newweb.mrli.ykport.baoshuisystem.entity.GoodsCategory;
import com.newweb.mrli.ykport.baoshuisystem.service.CargoVoService;
import com.newweb.mrli.ykport.baoshuisystem.service.ContractObjectService;
import com.newweb.mrli.ykport.baoshuisystem.vo.CargoVo;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 合同标的码表
 *
 * @author pigx code generator
 * @date 2021-07-27 11:58:08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/contractobject" )
@Api(value = "contractobject", tags = "合同标的码表管理")
public class ContractObjectController {

    private final  ContractObjectService contractObjectService;

	private final CargoVoService cargoVoService;
    /**
     * 分页查询
     * @param page 分页对象
     * @param contractObject 合同标的码表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('contractobject_view')" )
    public R getContractObjectPage(Page page, ContractObject contractObject) {
		if("".equals(contractObject.getCargoKindNam())){
			contractObject.setCargoKindNam(null);
		}

		return R.ok(contractObjectService.page(page, Wrappers.query(contractObject).isNull("DEL_TIM")));
    }


    /**
     * 通过id查询合同标的码表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('contractobject_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(contractObjectService.getById(fId));
    }

    /**
     * 新增合同标的码表
     * @param contractObject 合同标的码表
     * @return R
     */
    @ApiOperation(value = "新增合同标的码表", notes = "新增合同标的码表")
    @SysLog("新增合同标的码表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('contractobject_add')" )
    public R save(@RequestBody ContractObject contractObject) {
		ContractObject contractObject1 = new ContractObject();
		contractObject1.setCargoCod(contractObject.getCargoCod());
		List<ContractObject> list = contractObjectService.list(Wrappers.query(contractObject1));
		if(list.size()>0){
			return R.failed("编号录入重复，请检重新录入");
		}
		contractObject1 = new ContractObject();
		contractObject1.setCargoNam(contractObject.getCargoNam());
		list = contractObjectService.list(Wrappers.query(contractObject1));
		if(list.size()>0){
			return R.failed("货物名称录入重复，请检重新录入");
		}
		Integer fid = contractObjectService.selectFid();
    	contractObject.setFId(fid);
    	contractObject.setRecordTim(new Date());
    	contractObject.setOperNam(SecurityUtils.getUser().getUsername());
        return R.ok(contractObjectService.save(contractObject));
    }

    /**
     * 修改合同标的码表
     * @param contractObject 合同标的码表
     * @return R
     */
    @ApiOperation(value = "修改合同标的码表", notes = "修改合同标的码表")
    @SysLog("修改合同标的码表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('contractobject_edit')" )
    public R updateById(@RequestBody ContractObject contractObject) {
    	contractObject.setUpdateNam(SecurityUtils.getUser().getUsername());
    	contractObject.setUpdateTim(new Date());
        return R.ok(contractObjectService.updateById(contractObject));
    }

    /**
     * 通过id删除合同标的码表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除合同标的码表", notes = "通过id删除合同标的码表")
    @SysLog("通过id删除合同标的码表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('contractobject_del')" )
    public R removeById(@PathVariable Integer fId) {
    	ContractObject contractObject = new ContractObject();
    	contractObject.setDelNam(SecurityUtils.getUser().getUsername());
    	contractObject.setDelTim(new Date());
    	contractObject.setFId(fId);
        return R.ok(contractObjectService.updateById(contractObject));
    }

	/**
	 * 整体查询
	 * @return
	 */
	@ApiOperation(value = "整体查询", notes = "整体查询")
	@GetMapping("/all" )
	public R getOrganizerInfoAll() {
		ContractObject clientInfo = new ContractObject();
		final List<ContractObject> contractObject = contractObjectService.selectAll(clientInfo);
		return R.ok(contractObject);
	}

	/**
	 * 查询货名货类申请单位视图
	 * @return R
	 */
	@ApiOperation(value = "查询货名货类申请单位视图", notes = "查询货名货类申请单位视图")
	@GetMapping("/getCargo" )
	@DS("slave2")
	public R getCargo(Page page, CargoVo cargoVo) {
		IPage<CargoVo> pageList = cargoVoService.lambdaQuery()
				.like(StringUtils.isNotBlank(cargoVo.getCargoNam()),CargoVo::getCargoNam,cargoVo.getCargoNam())
				.page(new Page<>(page.getCurrent(), page.getSize()));
		return R.ok(pageList);
	}

}
