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
package com.newweb.mrli.ykport.baoshuisystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardSettlementBillsBody;
import  com.newweb.mrli.ykport.baoshuisystem.entity.SettlementBodyRate;
import com.newweb.mrli.ykport.baoshuisystem.entity.SettlementHeadRate;
import com.newweb.mrli.ykport.baoshuisystem.mapper.SettlementBodyRateMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

/**
 * 出入库结算单明细费用表
 *
 * @author pigx code generator
 * @date 2021-11-01 09:15:08
 */
@Service
@AllArgsConstructor
public class SettlementBodyRateServiceImpl extends ServiceImpl<SettlementBodyRateMapper, SettlementBodyRate> implements SettlementBodyRateService {
	private final SettlementHeadRateService settlementHeadRateService;
	private final IoyardSettlementBillsBodyService ioyardSettlementBillsBodyService;
	@Override
	public List<SettlementBodyRate> getList(Integer id) {
		List<SettlementBodyRate> bodyRateList = new ArrayList<>();
		//费目表信息
		List<SettlementHeadRate> headRateList = settlementHeadRateService.getSetHeadRate(id);
		//明细相关信息
		List<IoyardSettlementBillsBody> inOutYardBodyList = ioyardSettlementBillsBodyService.getInOutYard(id);
		for (int i = 0; i <headRateList.size() ; i++) {
			for (int j = 0; j <inOutYardBodyList.size() ; j++) {
				SettlementBodyRate settlementBodyRate = new SettlementBodyRate();
				settlementBodyRate.setBFid(headRateList.get(i).getFId());
				settlementBodyRate.setRFid(inOutYardBodyList.get(j).getFId().intValue());
				settlementBodyRate.setFeeNam(headRateList.get(i).getFeeNam());
				settlementBodyRate.setFeeUnit(headRateList.get(i).getFeeUnit());
				settlementBodyRate.setFeeNameCod(headRateList.get(i).getFeeNameCod());
				settlementBodyRate.setFeeRat(headRateList.get(i).getFeeRat());
				settlementBodyRate.setMoney(0.00);
				settlementBodyRate.setOperNam(SecurityUtils.getUser().getUsername());
				settlementBodyRate.setRecordTim(new Date());
				bodyRateList.add(settlementBodyRate);
			}
		}

		return bodyRateList;
	}

	@Override
	public boolean getTrueUpdate(Integer fId) {
		//确定这个id是否存在且没有被删除  当费目被修改或者删除的时候不涉及新增
		QueryWrapper<SettlementBodyRate> qw = new QueryWrapper<>();
		qw.eq("B_FID",fId);
		qw.isNull("DEL_NAM");
		qw.isNull("DEL_TIM");
		SettlementHeadRate settlementHeadRate = settlementHeadRateService.getById(fId);
		SettlementBodyRate settlementBodyRate = new SettlementBodyRate();
		settlementBodyRate.setFeeUnit(settlementHeadRate.getFeeUnit());
		settlementBodyRate.setFeeRat(settlementHeadRate.getFeeRat());
		settlementBodyRate.setFeeNam(settlementHeadRate.getFeeNam());
		settlementBodyRate.setFeeNameCod(settlementHeadRate.getFeeNameCod());
		settlementBodyRate.setUpdateNam(SecurityUtils.getUser().getUsername());
		settlementBodyRate.setUpdateTim(new Date());
		UpdateWrapper<SettlementBodyRate> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("B_FID",settlementHeadRate.getFId());
		int update = baseMapper.update(settlementBodyRate, updateWrapper);
		return update>0;
	}

	@Override
	public void deleteById(Integer fId) {
		QueryWrapper<SettlementBodyRate> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("B_FID",fId);
		List<SettlementBodyRate> settlementBodyRates = baseMapper.selectList(queryWrapper);
		for (SettlementBodyRate settlementBodyRate : settlementBodyRates) {
			settlementBodyRate.setDelNam(SecurityUtils.getUser().getUsername());
			settlementBodyRate.setDelTim(new Date());
			UpdateWrapper<SettlementBodyRate> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("B_FID",fId);
			baseMapper.update(settlementBodyRate,updateWrapper);
		}
	}

	@Override
	public List<SettlementBodyRate> addMessage(Integer addId,Integer addHfId) {
		List<SettlementBodyRate> bodyRateList = new ArrayList<>();
		SettlementHeadRate settlementHeadRate = settlementHeadRateService.getById(addId);
		//明细相关信息
		QueryWrapper<IoyardSettlementBillsBody> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("F_FID",addHfId);
		List<IoyardSettlementBillsBody> list = ioyardSettlementBillsBodyService.list(queryWrapper);
		for (int i = 0; i <list.size() ; i++) {
				SettlementBodyRate settlementBodyRate = new SettlementBodyRate();
				settlementBodyRate.setBFid(settlementHeadRate.getFId());
				settlementBodyRate.setFeeNam(settlementHeadRate.getFeeNam());
				settlementBodyRate.setFeeUnit(settlementHeadRate.getFeeUnit());
				settlementBodyRate.setFeeNameCod(settlementHeadRate.getFeeNameCod());
				settlementBodyRate.setFeeRat(settlementHeadRate.getFeeRat());
				settlementBodyRate.setMoney(0.00);
				settlementBodyRate.setRFid(list.get(i).getFId().intValue());
				settlementBodyRate.setOperNam(SecurityUtils.getUser().getUsername());
				settlementBodyRate.setRecordTim(new Date());
				bodyRateList.add(settlementBodyRate);
		}
		return bodyRateList;
	}
}
