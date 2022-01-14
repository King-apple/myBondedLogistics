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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.ykport.baoshuisystem.entity.ApprovalProcess;
import com.newweb.mrli.ykport.baoshuisystem.entity.ApprovalProcessUser;
import com.newweb.mrli.ykport.baoshuisystem.mapper.ApprovalProcessMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.ApprovalProcessService;
import com.newweb.mrli.ykport.baoshuisystem.service.ApprovalProcessUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 审批流程表
 *
 * @author pigx code generator
 * @date 2021-07-28 14:27:51
 */
@Service
@AllArgsConstructor
public class ApprovalProcessServiceImpl extends ServiceImpl<ApprovalProcessMapper, ApprovalProcess> implements ApprovalProcessService {

	
	private final ApprovalProcessMapper approvalProcessMapper;
	private final ApprovalProcessUserService approvalProcessUserService;
	@Override
	public Long selectFid() {
		return approvalProcessMapper.selectFid();
	}

	@Override
	public R selectNextCheckUser(ApprovalProcess approvalProcess) {
		approvalProcess.setOrderBy(approvalProcess.getOrderBy()+1);
		List<ApprovalProcess> list = list(Wrappers.query(approvalProcess));
		if(!list.isEmpty()){
			ApprovalProcessUser approvalProcessUser = new ApprovalProcessUser();
			approvalProcessUser.setProcessFid(Integer.parseInt(list.get(0).getFId()+""));
			return R.ok(approvalProcessUserService.list(Wrappers.query(approvalProcessUser)));
		}else{
			return R.ok(new ArrayList<>());
		}

	}
}
