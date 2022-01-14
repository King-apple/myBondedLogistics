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

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.CheckWeight;
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardHead;
import com.newweb.mrli.ykport.baoshuisystem.mapper.CheckWeightMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.CheckWeightService;
import com.newweb.mrli.ykport.baoshuisystem.service.InoutYardBodyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * 衡重申请单
 *
 * @author pigx code generator
 * @date 2021-09-06 13:35:12
 */
@Service
public class CheckWeightServiceImpl extends ServiceImpl<CheckWeightMapper, CheckWeight> implements CheckWeightService {

	@Resource
	public CheckWeightMapper checkWeightMapper;
	@Resource
	public CheckWeightService checkWeightService;
	@Resource
	private InoutYardBodyService inoutYardBodyService;

	/**
	 * 获取衡重单号
	 * @return
	 */
	@Override
	public String getCheckWeightForCheckNo(InoutYardBody inoutYardBody){
		CheckWeight info = checkWeightMapper.getCheckWeightForCheckNo(inoutYardBody.getUnitCod());
		if(info==null){
			return inoutYardBody.getUnitCod()+"000001";
		}

		String no = info.getCheckNo().substring(inoutYardBody.getUnitCod().length(),info.getCheckNo().length());

		DecimalFormat decimalFormat=new DecimalFormat("000000");
		int i=Integer.parseInt(no)+1;
		String k=inoutYardBody.getUnitCod()+decimalFormat.format(i);
		System.out.println(k);
		return k;
	}

	/**
	 * 插入衡中清单号
	 * @return
	 */
	@Override
	@Transactional
	public int saveForCheckWeight(CheckWeight checkWeight){
		CheckWeight forCheckNo = new CheckWeight();
		forCheckNo.setCheckNo(checkWeight.getCheckNo());
		int count = checkWeightService.count(Wrappers.query(forCheckNo));

		if(count>0){
			return 2;
		}

		checkWeight.setRecordDte(new Date());
		checkWeight.setOperNam(SecurityUtils.getUser().getUsername());
		checkWeight.setRecordTim(new Date());
		checkWeight.setUpdateNam(SecurityUtils.getUser().getUsername());
		checkWeight.setUpdateTim(new Date());
		checkWeight.setCheckStatus("0");
		boolean a = checkWeightService.save(checkWeight);

		InoutYardBody inoutYardBody = new InoutYardBody();
		inoutYardBody.setFId(checkWeight.getBodyId());
		inoutYardBody.setCheckNo(checkWeight.getCheckNo());
		boolean b = inoutYardBodyService.updateById(inoutYardBody);

		if(a && b){
			return 1;
		} else {
			return 0;
		}
	}


	/**
	 * 删除衡重量清单号
	 * @return
	 */
	@Override
	@Transactional
	public int deleteForCheckWeight(CheckWeight checkWeight){
		boolean a = checkWeightService.remove(Wrappers.query(checkWeight));

		InoutYardBody inoutYardBody = new InoutYardBody();
		inoutYardBody.setFId(checkWeight.getBodyId());
//		inoutYardBody.setCheckNo("");
//		boolean b = inoutYardBodyService.updateById(inoutYardBody);

		LambdaUpdateWrapper<InoutYardBody> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(InoutYardBody::getFId,inoutYardBody.getFId());
		updateWrapper.set(InoutYardBody::getCheckNo,"");
		boolean b = inoutYardBodyService.update(inoutYardBody,updateWrapper);

		if(a && b){
			return 1;
		} else {
			return 0;
		}
	}
}
