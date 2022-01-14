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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.SettledCompanyInfo;
import com.newweb.mrli.ykport.baoshuisystem.mapper.SettledCompanyInfoMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.SettledCompanyInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 入驻企业信息表
 *
 * @author pigx code generator
 * @date 2021-08-20 08:44:33
 */
@Service
@AllArgsConstructor
public class SettledCompanyInfoServiceImpl extends ServiceImpl<SettledCompanyInfoMapper, SettledCompanyInfo> implements SettledCompanyInfoService {
	private final SettledCompanyInfoMapper settledCompanyInfoMapper;
	@Override
	public Integer selectFid() {
		return settledCompanyInfoMapper.selectFid();
	}
}
