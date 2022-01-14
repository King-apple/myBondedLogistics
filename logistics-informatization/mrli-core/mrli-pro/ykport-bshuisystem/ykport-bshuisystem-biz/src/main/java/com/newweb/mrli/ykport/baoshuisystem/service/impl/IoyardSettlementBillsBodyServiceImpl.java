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
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardSettlementBillsBody;
import com.newweb.mrli.ykport.baoshuisystem.mapper.IoyardSettlementBillsBodyMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.IoyardSettlementBillsBodyService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出入库结算单体表
 *
 * @author ly
 * @date 2021-09-14 18:44:52
 */
@Service
public class IoyardSettlementBillsBodyServiceImpl extends ServiceImpl<IoyardSettlementBillsBodyMapper, IoyardSettlementBillsBody> implements IoyardSettlementBillsBodyService {

	@Override
	public List<IoyardSettlementBillsBody> getInOutYard(Integer id) {
		QueryWrapper<IoyardSettlementBillsBody> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("F_FID",id);
		queryWrapper.isNull("DEL_NAM");
		queryWrapper.isNull("DEL_TIM");
		return baseMapper.selectList(queryWrapper);
	}
}
