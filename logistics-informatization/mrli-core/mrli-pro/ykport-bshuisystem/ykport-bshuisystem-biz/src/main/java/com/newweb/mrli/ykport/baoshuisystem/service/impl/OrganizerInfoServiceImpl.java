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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.OrganizerInfo;
import com.newweb.mrli.ykport.baoshuisystem.mapper.OrganizerInfoMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.OrganizerInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 承办单位信息表
 *
 * @author pigx code generator
 * @date 2021-07-29 10:08:16
 */
@Service
@AllArgsConstructor
public class OrganizerInfoServiceImpl extends ServiceImpl<OrganizerInfoMapper, OrganizerInfo> implements OrganizerInfoService {

	private final OrganizerInfoMapper organizerInfoMapper;
	@Override
	public Integer selectFid() {
		return organizerInfoMapper.selectFid();
	}

	@Override
	public List<OrganizerInfo> selectAll(OrganizerInfo organizerInfo) {
		QueryWrapper<OrganizerInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(organizerInfo);
		return organizerInfoMapper.selectList(queryWrapper.isNull("del_nam"));
	}

	@Override
	public R insertInfo(OrganizerInfo organizerInfo) {
		Integer fId = selectFid();
		organizerInfo.setFId(fId);
		organizerInfo.setRecordTim(new Date());
		organizerInfo.setOperNam(SecurityUtils.getUser().getUsername());

		OrganizerInfo organizerInfoCheck = new OrganizerInfo();

		organizerInfoCheck.setOperNam(organizerInfo.getOperNam());
		List<OrganizerInfo> list = list(Wrappers.query(organizerInfoCheck));
		if(!list.isEmpty()){
			return R.failed("承办单位名称输入重复，请检查后添加");
		}else{
			organizerInfoCheck = new OrganizerInfo();
			organizerInfoCheck.setOrganizerCode(organizerInfo.getOrganizerCode());
			list = list(Wrappers.query(organizerInfoCheck));
			if(list.isEmpty()){
				return R.ok(save(organizerInfo));
			}else{
				return R.failed("承办单位编号输入重复，请检查后添加");
			}
		}
	}
}
