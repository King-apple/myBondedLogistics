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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.newweb.mrli.ykport.baoshuisystem.entity.SettlementHeadRate;
import com.newweb.mrli.ykport.baoshuisystem.mapper.SettlementHeadRateMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.SettlementHeadRateService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 出入库结算单费目表
 *
 * @author pigx code generator
 * @date 2021-11-01 09:16:01
 */
@Service
@AllArgsConstructor
public class SettlementHeadRateServiceImpl extends ServiceImpl<SettlementHeadRateMapper, SettlementHeadRate> implements SettlementHeadRateService {


	private  final  SettlementHeadRateMapper settlementHeadRateMapper;

	@Override
	public List<SettlementHeadRate> getSetHeadRate(Integer fId) {
		QueryWrapper<SettlementHeadRate> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("H_FID",fId);
		queryWrapper.isNull("DEL_NAM");
		queryWrapper.isNull("DEL_TIM");
		return settlementHeadRateMapper.selectList(queryWrapper);
	}
}
