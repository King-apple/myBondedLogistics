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
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.*;
import com.newweb.mrli.ykport.baoshuisystem.service.IoyardSettlementBillsBodyService;
import com.newweb.mrli.ykport.baoshuisystem.service.SettlementBodyRateService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 出入库结算单体表
 *
 * @author ly
 * @date 2021-09-14 18:44:52
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ioyardsettlementbillsbody" )
@Api(value = "ioyardsettlementbillsbody", tags = "出入库结算单体表管理")
public class IoyardSettlementBillsBodyController {

    private final IoyardSettlementBillsBodyService ioyardSettlementBillsBodyService;
    private final SettlementBodyRateService settlementBodyRateService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ioyardSettlementBillsBody 出入库结算单体表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getIoyardSettlementBillsBodyPage(Page page, IoyardSettlementBillsBody ioyardSettlementBillsBody) {
        return R.ok(ioyardSettlementBillsBodyService.page(page, Wrappers.query(ioyardSettlementBillsBody)));
    }


    /**
     * 通过id查询出入库结算单体表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )

    public R getById(@PathVariable("fId" ) Integer fId) {
		QueryWrapper<IoyardSettlementBillsBody> query = new QueryWrapper<>();
		query.eq("f_fid",fId);
		query.isNull("del_nam");
		query.orderByAsc("record_tim");
		List<IoyardSettlementBillsBody> list = ioyardSettlementBillsBodyService.list(query);
    	return R.ok(list);
    }

    /**
     * 新增出入库结算单体表
     * @param
     * @return R
     */
    @ApiOperation(value = "新增出入库结算单体表", notes = "新增出入库结算单体表")
    @SysLog("新增出入库结算单体表" )
    @PostMapping

    public R save(@RequestBody List<IoyardSettlementBillsBody>  iBoy) {
		for (IoyardSettlementBillsBody s:iBoy
			 ) {
			s.setOperNam(SecurityUtils.getUser().getUsername());
			s.setRecordTim(new Date());
		}
		ioyardSettlementBillsBodyService.saveBatch(iBoy);
        return R.ok(iBoy);
    }

    /**
     * 修改出入库结算单体表
     * @param ioyardSettlementBillsBody 出入库结算单体表
     * @return R
     */
    @ApiOperation(value = "修改出入库结算单体表", notes = "修改出入库结算单体表")
    @SysLog("修改出入库结算单体表" )
    @PostMapping("/update")
    public R updateById(@RequestBody List<IoyardSettlementBillsBody> ioyardSettlementBillsBody) {
    	boolean b= false;
    	//如果fid不为空，并且删除时间和删除人是空，走修改
		for (int i = 0; i < ioyardSettlementBillsBody.size(); i++) {
				if (ioyardSettlementBillsBody.get(i).getFId() != null
						&& ioyardSettlementBillsBody.get(i).getDelNam() == null
						&& ioyardSettlementBillsBody.get(i).getDelTim() == null) {
					ioyardSettlementBillsBody.get(i).setUpdateTim(new Date());
					ioyardSettlementBillsBody.get(i).setUpdateNam(SecurityUtils.getUser().getUsername());
					b = ioyardSettlementBillsBodyService.updateById(ioyardSettlementBillsBody.get(i));
					List<SettlementBodyRate> settlementBodyRates = ioyardSettlementBillsBody.get(i).getList();
					for (SettlementBodyRate settlementBodyRate : settlementBodyRates) {
						SettlementBodyRate s = new SettlementBodyRate();
						s.setFeeNameCod(settlementBodyRate.getFeeNameCod());
						s.setFeeNam(settlementBodyRate.getFeeNam() );
						s.setFeeRat(settlementBodyRate.getFeeRat() );
						s.setFeeUnit(settlementBodyRate.getFeeUnit());
						s.setMoney(settlementBodyRate.getMoney());
						s.setMarkTxt(settlementBodyRate.getMarkTxt());
						s.setUpdateNam(SecurityUtils.getUser().getUsername());
						s.setRecordTim(new Date());
						UpdateWrapper<SettlementBodyRate> updateWrapper= new UpdateWrapper<>();
						updateWrapper.eq("B_FID",settlementBodyRate.getBFid());
						updateWrapper.eq("R_FID",settlementBodyRate.getRFid());
						boolean update = settlementBodyRateService.update(s, updateWrapper);
			}
				//如果fid为空，并且删除时间和删除人是空，走新增
			} else if (ioyardSettlementBillsBody.get(i).getFId() == null
					&& ioyardSettlementBillsBody.get(i).getDelNam() == null
					&& ioyardSettlementBillsBody.get(i).getDelTim() == null) {
				ioyardSettlementBillsBody.get(i).setOperNam(SecurityUtils.getUser().getUsername());
				ioyardSettlementBillsBody.get(i).setRecordTim(new Date());
				ioyardSettlementBillsBodyService.save(ioyardSettlementBillsBody.get(i));
				Long id = ioyardSettlementBillsBody.get(i).getFId();
				List<SettlementBodyRate> settlementBodyRates = ioyardSettlementBillsBody.get(i).getList();
				for (SettlementBodyRate settlementBodyRate : settlementBodyRates) {
					SettlementBodyRate s = new SettlementBodyRate();
					s.setFeeNameCod(settlementBodyRate.getFeeNameCod());
					s.setFeeNam(settlementBodyRate.getFeeNam() );
					s.setFeeRat(settlementBodyRate.getFeeRat() );
					s.setFeeUnit(settlementBodyRate.getFeeUnit());
					s.setMoney(settlementBodyRate.getMoney());
					s.setMarkTxt(settlementBodyRate.getMarkTxt());
					s.setBFid(settlementBodyRate.getFId());
					s.setRFid(id.intValue());
					s.setOperNam(SecurityUtils.getUser().getUsername());
					s.setRecordTim(new Date());
					boolean save = settlementBodyRateService.save(s);
				}
			}else {
				return  R.failed("发生了未知错误");
			}
		}
    	return R.ok(b);
    }

    /**
     * 通过id删除出入库结算单体表
     * @param
     * @return R
     */
    @ApiOperation(value = "通过id删除出入库结算单体表", notes = "通过id删除出入库结算单体表")
    @SysLog("通过id删除出入库结算单体表" )
	@PostMapping("/deleteBody")
    public R removeById(@RequestBody IoyardSettlementBillsBody ioyardSettlementBillsBody) {
		IoyardSettlementBillsBody io = new IoyardSettlementBillsBody();
		io.setDelNam(SecurityUtils.getUser().getUsername());
		io.setDelTim(new Date());
		io.setFId(ioyardSettlementBillsBody.getFId());
		SettlementBodyRate settlementBodyRate = new SettlementBodyRate();
		settlementBodyRate.setDelNam(SecurityUtils.getUser().getUsername());
		settlementBodyRate.setDelTim(new Date());
		UpdateWrapper<SettlementBodyRate> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("R_FID",ioyardSettlementBillsBody.getFId());
		settlementBodyRateService.update(settlementBodyRate,updateWrapper);
		return R.ok(ioyardSettlementBillsBodyService.updateById(io));
    }
}
