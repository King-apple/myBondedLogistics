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
import com.newweb.mrli.ykport.baoshuisystem.entity.CountryInfo;
import com.newweb.mrli.ykport.baoshuisystem.mapper.CountryInfoMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.CountryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 国别信息表
 *
 * @author pigx code generator
 * @date 2021-08-28 18:10:12
 */
@Service
public class CountryInfoServiceImpl extends ServiceImpl<CountryInfoMapper, CountryInfo> implements CountryInfoService {

	@Autowired
	private CountryInfoMapper countryInfoMapper;

	@Override
	public List<CountryInfo> selectAll(CountryInfo countryInfo) {
		QueryWrapper<CountryInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(countryInfo).isNull("del_tim");
		return countryInfoMapper.selectList(queryWrapper);
	}
}
