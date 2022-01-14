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
import com.newweb.mrli.ykport.baoshuisystem.entity.ClientInfo;
import com.newweb.mrli.ykport.baoshuisystem.entity.ContractObject;
import com.newweb.mrli.ykport.baoshuisystem.mapper.ContractObjectMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.ContractObjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 合同标的码表
 *
 * @author pigx code generator
 * @date 2021-07-27 11:58:08
 */
@Service
@AllArgsConstructor
public class ContractObjectServiceImpl extends ServiceImpl<ContractObjectMapper, ContractObject> implements ContractObjectService {

	private final ContractObjectMapper contractObjectMapper;
	@Override
	public Integer selectFid() {
		return contractObjectMapper.selectFid();
	}

	@Override
	public List<ContractObject> selectAll(ContractObject contractObject) {
		QueryWrapper<ContractObject> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(contractObject);
		return contractObjectMapper.selectList(queryWrapper.isNull("DEL_TIM"));
	}
}
