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
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardSettlementBillsBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.SettlementBodyRate;
import com.newweb.mrli.ykport.baoshuisystem.entity.SettlementHeadRate;
import com.newweb.mrli.ykport.baoshuisystem.service.SettlementBodyRateService;
import com.newweb.mrli.ykport.baoshuisystem.service.SettlementHeadRateService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.Date;
import java.util.List;


/**
 * 出入库结算单费目表
 *
 * @author pigx code generator
 * @date 2021-11-01 09:16:01
 */
@RestController
@AllArgsConstructor
@RequestMapping("/settlementheadrate" )
@Api(value = "settlementheadrate", tags = "出入库结算单费目表管理")
public class SettlementHeadRateController {

    private final SettlementHeadRateService settlementHeadRateService;
    private final SettlementBodyRateService settlementBodyRateService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param settlementHeadRate 出入库结算单费目表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('generator_settlementheadrate_view')" )
    public R getSettlementHeadRatePage(Page page, SettlementHeadRate settlementHeadRate) {
        return R.ok(settlementHeadRateService.page(page, Wrappers.query(settlementHeadRate)));
    }


    /**
     * 通过id查询出入库结算单费目表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    public R getById(@PathVariable("fId" ) Integer fId) {
    	QueryWrapper<SettlementHeadRate> qw = new QueryWrapper<>();
    	qw.eq("H_FID",fId);
		qw.isNull("DEL_NAM");
		qw.isNull("DEL_TIM");
        return R.ok(settlementHeadRateService.list(qw));
    }

    /**
     * 新增出入库结算单费目表
     * @param settlementHeadRate 出入库结算单费目表
     * @return R
     */
    @ApiOperation(value = "新增出入库结算单费目表", notes = "新增出入库结算单费目表")
    @SysLog("新增出入库结算单费目表" )
    @PostMapping
    public R save(@RequestBody List<SettlementHeadRate> settlementHeadRate) {
		for (SettlementHeadRate headRate : settlementHeadRate) {
			headRate.setOperNam(SecurityUtils.getUser().getUsername());
			headRate.setRecordTim(new Date());
		}
		boolean b = settlementHeadRateService.saveBatch(settlementHeadRate);
			if(b){
				return R.ok(b);
			}else {
				return R.failed("新增失败，请联系管理员");
			}
    }

    /**
     * 修改出入库结算单费目表
     * @param settlementHeadRate 出入库结算单费目表
     * @return R
     */
    @ApiOperation(value = "修改出入库结算单费目表", notes = "修改出入库结算单费目表")
    @SysLog("修改出入库结算单费目表" )
    @PutMapping
	@Transactional
    public R updateById(@RequestBody List<SettlementHeadRate> settlementHeadRate) {
		boolean b= false;
		//如果fid不为空，并且删除时间和删除人是空，走修改
		for (int i = 0; i < settlementHeadRate.size(); i++) {
			if (settlementHeadRate.get(i).getFId() != null
					&& settlementHeadRate.get(i).getDelNam() == null
					&& settlementHeadRate.get(i).getDelTim() == null) {
				Integer a = settlementHeadRate.get(i).getFId();
				SettlementHeadRate byId = settlementHeadRateService.getById(a);
				SettlementHeadRate s = settlementHeadRate.get(i);

				System.out.println("111"+byId.getFeeNam().equals(s.getFeeNam()));
				System.out.println( "111"+byId.getFeeUnit().equals(s.getFeeUnit()));
				System.out.println(Double.doubleToLongBits(byId.getFeeRat()) != Double.doubleToLongBits(s.getFeeRat()));
				if(!(byId.getFeeNam().equals(s.getFeeNam()))  || !(byId.getFeeUnit().equals(s.getFeeUnit()))
				||Double.doubleToLongBits(byId.getFeeRat()) != Double.doubleToLongBits(s.getFeeRat())){
					settlementHeadRate.get(i).setUpdateTim(new Date());
					settlementHeadRate.get(i).setUpdateNam(SecurityUtils.getUser().getUsername());
					b = settlementHeadRateService.updateById(settlementHeadRate.get(i));
					boolean flag = settlementBodyRateService.getTrueUpdate(settlementHeadRate.get(i).getFId());
				}
				//如果fid为空，并且删除时间和删除人是空，走新增
			} else if (settlementHeadRate.get(i).getFId() == null
					&& settlementHeadRate.get(i).getDelNam() == null
					&& settlementHeadRate.get(i).getDelTim() == null) {
				settlementHeadRate.get(i).setOperNam(SecurityUtils.getUser().getUsername());
				settlementHeadRate.get(i).setRecordTim(new Date());
				settlementHeadRateService.save(settlementHeadRate.get(i));
				Integer addId= settlementHeadRate.get(i).getFId();
				Integer addHfId=  settlementHeadRate.get(i).getHFid();
				List<SettlementBodyRate> list = settlementBodyRateService.addMessage(addId,addHfId);
				settlementBodyRateService.saveBatch(list);
			}else {
				return  R.failed("发生了未知错误");
			}
		}
		return R.ok(b);
    }

    /**
     * 通过id删除出入库结算单费目表
     * @param
     * @return R
     */
    @ApiOperation(value = "通过id删除出入库结算单费目表", notes = "通过id删除出入库结算单费目表")
    @SysLog("通过id删除出入库结算单费目表" )
    @PostMapping("/deleteBody")
	public R removeById(@RequestBody SettlementHeadRate settlementHeadRate) {
		//删除的同时删除中间表
		SettlementHeadRate se = new SettlementHeadRate();
		se.setDelNam(SecurityUtils.getUser().getUsername());
		se.setDelTim(new Date());
		se.setFId(settlementHeadRate.getFId());
		settlementBodyRateService.deleteById(settlementHeadRate.getFId());
		return R.ok(settlementHeadRateService.updateById(se));
	}
	/**
	 * 通过hfid获取费用信息
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "通过hfid获取费用信息", notes = "通过hfid获取费用信息")
	@SysLog("通过hfid获取费用信息" )
	@GetMapping("/getMessage/{id}")
	public R getMessage(@PathVariable String id){

		QueryWrapper<SettlementHeadRate> qw = new QueryWrapper<>();
		qw.eq("H_FID",id);
		qw.isNull("DEL_NAM");
		qw.isNull("DEL_TIM");
		List<SettlementHeadRate> list = settlementHeadRateService.list(qw);
		return R.ok(list);
	}
}
