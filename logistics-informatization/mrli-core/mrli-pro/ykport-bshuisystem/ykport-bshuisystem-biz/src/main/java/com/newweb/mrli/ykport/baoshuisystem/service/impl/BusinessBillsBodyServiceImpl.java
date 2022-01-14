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
import com.newweb.mrli.ykport.baoshuisystem.entity.BusinessBillsBody;
import com.newweb.mrli.ykport.baoshuisystem.mapper.BusinessBillsBodyMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.BusinessBillsBodyService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务单据体表
 *
 * @author 李洋
 * @date 2021-09-06 09:13:02
 */
@Service
public class BusinessBillsBodyServiceImpl extends ServiceImpl<BusinessBillsBodyMapper, BusinessBillsBody> implements BusinessBillsBodyService {


	@Override
	public boolean addList(List<BusinessBillsBody> bbBodyList) {
		int insert = 0;
		for (int i = 0; i <bbBodyList.size() ; i++) {
			Long hFid = bbBodyList.get(i).getHFid();
			insert = baseMapper.insert(bbBodyList.get(i));
		}
		if(insert > 0){
			return true;
		}else {
			return false;
		}

	}
}
