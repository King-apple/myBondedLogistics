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
import com.newweb.mrli.ykport.baoshuisystem.mapper.ClientInfoMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.ClientInfoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户信息表
 *
 * @author pigx code generator
 * @date 2021-07-30 08:53:57
 */
@Service
@AllArgsConstructor
public class ClientInfoServiceImpl extends ServiceImpl<ClientInfoMapper, ClientInfo> implements ClientInfoService {
	@Autowired
	private ClientInfoMapper clientInfoMapper;

	@Override
	public Long selectFid() {
		return clientInfoMapper.selectFid();
	}

	@Override
	public List<ClientInfo> selectAll(ClientInfo clientInfo) {
		QueryWrapper<ClientInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(clientInfo).isNull("del_tim");
		return clientInfoMapper.selectList(queryWrapper);
	}
}
