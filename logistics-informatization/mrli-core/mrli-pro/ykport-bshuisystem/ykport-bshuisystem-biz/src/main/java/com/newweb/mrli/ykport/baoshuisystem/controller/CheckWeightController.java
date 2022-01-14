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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.CheckWeight;
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardHead;
import com.newweb.mrli.ykport.baoshuisystem.service.CheckWeightService;
import com.newweb.mrli.ykport.baoshuisystem.service.InoutYardBodyService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 衡重申请单
 *
 * @author pigx code generator
 * @date 2021-09-15 14:04:21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/checkweight" )
@Api(value = "checkweight", tags = "衡重申请单管理")
public class CheckWeightController {

    private final  CheckWeightService checkWeightService;


    /**
     * 分页查询
     * @param page 分页对象
     * @param checkWeight 衡重申请单
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('checkweight_view')" )
    public R getCheckWeightPage(Page page, CheckWeight checkWeight) throws ParseException {
    	String [] recordDte = new String[]{};
    	if(checkWeight.getParams().get("recordDte")!=null&&!"".equals(checkWeight.getParams().get("recordDte"))){
			recordDte =(String[]) checkWeight.getParams().get("recordDte");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date [] date = new Date[2];
		if(recordDte.length>0){
			Date parse = sdf.parse(recordDte[0]);
			Date parse1 = sdf.parse(recordDte[1]);
			date = new Date[]{parse, parse1};
		}

		IPage<CheckWeight> pageList = checkWeightService.lambdaQuery()
				.like(StringUtils.isNotBlank(checkWeight.getCheckNo()),CheckWeight::getCheckNo,checkWeight.getCheckNo())
				.like(StringUtils.isNotBlank(checkWeight.getCargoNam()),CheckWeight::getCargoNam,checkWeight.getCargoNam())
				.eq(checkWeight.getInoutYardNo()!=null,CheckWeight::getInoutYardNo,checkWeight.getInoutYardNo())
				.eq(StringUtils.isNotBlank(checkWeight.getCheckStatus()),CheckWeight::getCheckStatus,checkWeight.getCheckStatus())
				.like(StringUtils.isNotBlank(checkWeight.getUnitNam()),CheckWeight::getUnitNam,checkWeight.getUnitNam())
				.like(StringUtils.isNotBlank(checkWeight.getRequireUnitNam()),CheckWeight::getRequireUnitNam,checkWeight.getRequireUnitNam())
				.between(recordDte.length>0,CheckWeight::getRecordDte,date[0],date[1])
				.page(new Page<>(page.getCurrent(), page.getSize()));;
        return R.ok(pageList);
    }


    /**
     * 通过id查询衡重申请单
     * @param recordDte id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{recordDte}" )
    @PreAuthorize("@pms.hasPermission('checkweight_view')" )
    public R getById(@PathVariable("recordDte" ) Date recordDte) {
        return R.ok(checkWeightService.getById(recordDte));
    }

    /**
     * 新增衡重申请单
     * @param checkWeight 衡重申请单
     * @return R
     */
    @ApiOperation(value = "新增衡重申请单", notes = "新增衡重申请单")
    @SysLog("新增衡重申请单" )
    @PostMapping
//    @PreAuthorize("@pms.hasPermission('checkweight_add')" )
    public R save(@RequestBody CheckWeight checkWeight) {
		int i = checkWeightService.saveForCheckWeight(checkWeight);
		if(i==2){
			return R.failed("衡重清单号已经存在，请重新生成！");
		}
        return R.ok(i);
    }

    /**
     * 修改衡重申请单
     * @param checkWeight 衡重申请单
     * @return R
     */
    @ApiOperation(value = "修改衡重申请单", notes = "修改衡重申请单")
    @SysLog("修改衡重申请单" )
    @PutMapping
//    @PreAuthorize("@pms.hasPermission('checkweight_edit')" )
    public R updateById(@RequestBody CheckWeight checkWeight) {
		checkWeight.setUpdateNam(SecurityUtils.getUser().getUsername());
		checkWeight.setUpdateTim(new Date());
        return R.ok(checkWeightService.updateById(checkWeight));
    }

    /**
     * 通过id删除衡重申请单
     * @param checkNo id
     * @return R
     */
    @ApiOperation(value = "通过id删除衡重申请单", notes = "通过id删除衡重申请单")
    @SysLog("通过id删除衡重申请单" )
    @DeleteMapping("/{checkNo}" )
    @PreAuthorize("@pms.hasPermission('checkweight_del')" )
    public R removeById(@PathVariable String checkNo) {
        return R.ok(checkWeightService.removeById(checkNo));
    }

	/**
	 * 自动生成衡中单号
	 * @param unitCod id
	 * @return R
	 */
	@ApiOperation(value = "自动生成衡中单号", notes = "自动生成衡中单号")
	@SysLog("自动生成衡中单号" )
	@GetMapping("autoCreate/{unitCod}" )
	public R autoCreate(@PathVariable String unitCod) {
		InoutYardBody inoutYardBody = new InoutYardBody();
		inoutYardBody.setUnitCod(unitCod);
		return R.ok(checkWeightService.getCheckWeightForCheckNo(inoutYardBody));
	}

	/**
	 * 通过checkNo查询衡重申请单
	 * @param checkNo id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("getCheckNo/{checkNo}" )
	public R getCheckNo(@PathVariable("checkNo") String checkNo) {
		CheckWeight checkWeight = new CheckWeight();
		checkWeight.setCheckNo(checkNo);
		CheckWeight info = checkWeightService.getOne(Wrappers.query(checkWeight));
		System.out.println(info);
		return R.ok(info);
	}

	/**
	 * 通过checkNo删除衡重申请单
	 * @param checkWeight
	 * @return R
	 */
	@ApiOperation(value = "通过checkNo删除衡重申请单", notes = "通过checkNo删除衡重申请单")
	@DeleteMapping("/delObjForCheckNo" )
	public R delObjForCheckNo(@RequestBody CheckWeight checkWeight) {
		int i = checkWeightService.deleteForCheckWeight(checkWeight);
		return R.ok(i);
	}


}
