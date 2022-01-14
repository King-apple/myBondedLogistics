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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.ApprovalProcess;
import com.newweb.mrli.ykport.baoshuisystem.entity.ApprovalRecord;
import com.newweb.mrli.ykport.baoshuisystem.entity.BSUser;
import com.newweb.mrli.ykport.baoshuisystem.entity.Contract;
import com.newweb.mrli.ykport.baoshuisystem.mapper.ApprovalRecordMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.ApprovalProcessService;
import com.newweb.mrli.ykport.baoshuisystem.service.ApprovalRecordService;
import com.newweb.mrli.ykport.baoshuisystem.service.BSUserService;
import com.newweb.mrli.ykport.baoshuisystem.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 审批记录表
 *
 * @author pigx code generator
 * @date 2021-08-28 18:10:46
 */
@Service
public class ApprovalRecordServiceImpl extends ServiceImpl<ApprovalRecordMapper, ApprovalRecord> implements ApprovalRecordService {

	@Autowired
	private ApprovalProcessService approvalProcessService;

	@Autowired
	private ApprovalRecordMapper approvalRecordMapper;

	@Autowired
	private ContractService contractService;
	@Autowired
	private BSUserService bsUserService;

	@Override
	public R insertRecords(ApprovalRecord approvalRecord1) {
		try {
			ApprovalRecord approvalRecord = new ApprovalRecord();
			approvalRecord.setCFid(approvalRecord1.getCFid());
			approvalRecord.setProcessCode(approvalRecord1.getProcessCode());
			approvalRecord.setApprover(SecurityUtils.getUser().getUsername());
			approvalRecord.setApprovalTim(new Date());
			approvalRecord.setIsAgree("0");
			approvalRecord.setTimes(approvalRecord1.getTimes());
			ApprovalProcess approvalProcess = new ApprovalProcess();
			approvalProcess.setProcessNo(approvalRecord1.getProcessCode());
			QueryWrapper<ApprovalProcess> query = Wrappers.query(approvalProcess);
			List<ApprovalProcess> approvalProcessList = approvalProcessService.list(query);

			if (!approvalProcessList.isEmpty()) {
				for (int i = 0; i < approvalProcessList.size(); i++) {
					approvalRecord.setPFid(approvalProcessList.get(i).getFId());
					approvalRecord.setOrderBy(approvalProcessList.get(i).getOrderBy());
					approvalRecord.setLinkNam(approvalProcessList.get(i).getLinkNam());
					save(approvalRecord);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.failed("添加审核记录失败");
		}

		return R.ok(1);
	}

	@Override
	@Transactional
	public R insertUserRecords(String userRecords) {
		System.out.println(userRecords);
		Map maps = (Map) JSON.parse(userRecords);
		int flag = 0;
		try {
			ApprovalRecord approvalRecord = new ApprovalRecord();
			approvalRecord.setCFid(maps.get("fId")==null?0:Long.parseLong(maps.get("fId").toString()));
			approvalRecord.setProcessCode(maps.get("processNo")==null?"":maps.get("processNo").toString());
//			approvalRecord.setApprover(SecurityUtils.getUser().getUsername());
			approvalRecord.setApprovalTim(new Date());
			approvalRecord.setIsAgree("0");
			approvalRecord.setTimes(maps.get("times")==null?0:Integer.parseInt(maps.get("times").toString()));
			ApprovalProcess approvalProcess = new ApprovalProcess();
			approvalProcess.setProcessNo(maps.get("processNo")==null?"":maps.get("processNo").toString());
			QueryWrapper<ApprovalProcess> query = Wrappers.query(approvalProcess);
			List<ApprovalProcess> approvalProcessList = approvalProcessService.list(query);

			if (!approvalProcessList.isEmpty()) {

				for (int i = 0; i < approvalProcessList.size(); i++) {
					JSONArray objects = JSONArray.parseArray(maps.get(approvalProcessList.get(i).getLinkNam()).toString());
					if(objects!=null&&objects.size()>0){
						flag = 1;
						approvalRecord.setPFid(approvalProcessList.get(i).getFId());
						approvalRecord.setOrderBy(approvalProcessList.get(i).getOrderBy());
						approvalRecord.setLinkNam(approvalProcessList.get(i).getLinkNam());
						approvalRecord.setLinkCode(approvalProcessList.get(i).getLinkCode());
						for (int j = 0; j < objects.size(); j++) {
							approvalRecord.setApprover(objects.get(j).toString());
							save(approvalRecord);
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.failed("添加审核记录失败");
		}
		if(flag == 0){
			return R.failed("添加审核记录失败(未选择审批人)");
		}
		Contract contract = new Contract();
		contract.setFId(Long.parseLong(maps.get("fId").toString()));
		contract.setContractStatus("1");
		contract.setTimes(Integer.parseInt(maps.get("times").toString()));
		boolean b = contractService.updateById(contract);
		if(b){
			return R.ok("添加审核记录成功");
		}else{
			return R.failed("添加审核记录失败");
		}

	}



	@Override
	public R updateRecordById(ApprovalRecord approvalRecord) throws Exception {

		ApprovalRecord byId = getById(approvalRecord.getFId());

		int isFinalFlag = 0;
		ApprovalProcess approvalProcess = new ApprovalProcess();
		approvalProcess.setProcessNo(byId.getProcessCode());
		List<ApprovalProcess> approvalProcessList = approvalProcessService.list(Wrappers.query(approvalProcess)
				.isNull("DEL_NAM")
				.orderByDesc("ORDER_BY"));
		if(!approvalProcessList.isEmpty()){
			if(byId.getOrderBy().equals(approvalProcessList.get(0).getOrderBy())){
				isFinalFlag = 1;
			}
		}
		int i = 0;
		Contract contract = new Contract();
		contract.setFId(byId.getCFid());
		if (approvalRecord.getIsAgree() != null && approvalRecord.getIsAgree().equals("2")) {
			contract.setContractStatus("3");
			i = contractService.updateContract(contract);

			ApprovalRecord isCancelApprovalRecord = new ApprovalRecord();
			isCancelApprovalRecord.setIsCancel("1");
			UpdateWrapper<ApprovalRecord> approvalRecordUpdateWrapper = new UpdateWrapper<>();
			approvalRecordUpdateWrapper.eq("C_FID",byId.getCFid());
			approvalRecordUpdateWrapper.eq("PROCESS_CODE",byId.getProcessCode());
			boolean update = update(isCancelApprovalRecord, approvalRecordUpdateWrapper);

		}else if(isFinalFlag == 1&&approvalRecord.getIsAgree() != null && approvalRecord.getIsAgree().equals("1")){
			contract.setContractStatus("2");
			i = contractService.updateContract(contract);
		}else{
			i = 1;
		}
		if(i == 0){
			throw new Exception("更新失败");
		}
		approvalRecord.setApprover(SecurityUtils.getUser().getUsername());
		approvalRecord.setApprovalTim(new Date());
		return R.ok(updateById(approvalRecord));
	}

	@Override
	public List<ApprovalRecord> selectApprovalRecordGroup(ApprovalRecord approvalRecord) {
		return approvalRecordMapper.selectApprovalRecordGroup(approvalRecord);
	}

	@Override
	public R getApprovalRecords(ApprovalRecord approvalRecord) {
		List<ApprovalRecord> list = list(Wrappers.query(approvalRecord));
		List<String> username = new ArrayList<>();
		for (ApprovalRecord item: list) {
			username.add(item.getApprover());
		}
		BSUser bsUser = new BSUser();
		QueryWrapper<BSUser> query = Wrappers.query(bsUser).in("USERNAME",username);
		List<BSUser> list1 = bsUserService.list(query);
		for (ApprovalRecord item: list) {
			for (BSUser user:list1) {
				if(item.getApprover().equals(user.getUsername())){
					item.setApprover(user.getManName());
				}
			}
		}
		return R.ok(list);
	}
}
