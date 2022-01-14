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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardBody;
import com.newweb.mrli.ykport.baoshuisystem.mapper.InoutYardBodyMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.InoutYardHeadMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.InoutYardBodyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;

/**
 * 出入库单表体
 *
 * @author zada
 * @date 2021-09-02 18:42:44
 */
@Service
@AllArgsConstructor
public class InoutYardBodyServiceImpl extends ServiceImpl<InoutYardBodyMapper, InoutYardBody> implements InoutYardBodyService {


	@Override
	public List<InoutYardBody> getList() {
		return baseMapper.getList();
	}

	@Override
	public IPage<InoutYardBody> selectPageVo(Page page, InoutYardBody inoutYardBody) {
		return baseMapper.selectPageVo(page,inoutYardBody);
	}


}
