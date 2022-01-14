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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.*;
import com.newweb.mrli.ykport.baoshuisystem.mapper.ApprovalProcessSetMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.ApprovalRecordMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.*;
import com.newweb.mrli.ykport.baoshuisystem.vo.BillApproveAppProSetVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.BillApproveVo;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 审批流程设定表
 *
 * @author zada
 * @date 2021-10-08 14:39:44
 */
@Service
public class ApprovalProcessSetServiceImpl extends ServiceImpl<ApprovalProcessSetMapper, ApprovalProcessSet> implements ApprovalProcessSetService {
	@Autowired
	public ApprovalProcessSetMapper approvalProcessSetMapper;
	@Autowired
	public ApprovalProcessSetService approvalProcessSetService;

	@Autowired
	private ApprovalProcessService approvalProcessService;

	@Autowired
	private ApprovalRecordMapper approvalRecordMapper;

	@Autowired
	private BSUserService bSUserService;

	@Autowired
	private SmsdbService smsdbService;
	@Override
	public List<Map<Integer,Object>> getOpearAudit(Integer billFid){
		return approvalProcessSetMapper.getOpearAudit(billFid);
	}

	/**
	 * 获取billId分组数据
	 * @return
	 */
	@Override
	public List<ApprovalProcessSet> getBillIdList(ApprovalProcessSet approvalProcessSet){
		return approvalProcessSetMapper.getBillIdList(approvalProcessSet);
	}

	@Override
	public ApprovalProcessSet getApprovalProcessSetList(ApprovalProcessSet approvalProcessSet){
		return approvalProcessSetMapper.getApprovalProcessSetList(approvalProcessSet);
	}

	@Override
	public ApprovalProcessSet getApprovalProcessSetCheck(ApprovalProcessSet approvalProcessSet){
		return approvalProcessSetMapper.getApprovalProcessSetCheck(approvalProcessSet);
	}


	@Override
	public IPage<BillApproveAppProSetVo> getBillApproveList(Page<?> page, List idList, BillApproveAppProSetVo billApprove){
		return approvalProcessSetMapper.getBillApproveList(page, idList,billApprove);
	}


	@Override
	public IPage<BillApproveAppProSetVo> getBillApproveListForSuccess(Page<?> page, List idList, BillApproveAppProSetVo billApprove){
		return approvalProcessSetMapper.getBillApproveListForSuccess(page, idList,billApprove);
	}

	@Override
	public int updateByIdForNull(ApprovalProcessSet approvalProcessSet){
		return approvalProcessSetMapper.updateByIdForNull(approvalProcessSet);
	}

	@Override
	public  List<ApprovalProcessSet> getMessageMerge(Integer fId) {
		return  approvalProcessSetMapper.getMessageMerge(fId);
	}

	@Override
	public List<ApprovalProcessSet> selectApprovalProcessSetGroup(ApprovalProcessSet approvalProcessSet) {
		return approvalProcessSetMapper.selectApprovalProcessSetGroup(approvalProcessSet);
	}

	@Override
	public R saveForOpear(Object[] info){
		List<ApprovalProcess> list = new ArrayList<ApprovalProcess>();
		for(int i=0;i<info.length;i++){
			System.out.println(info[i]);
			ApprovalProcess demandAnalysisDo = JSON.parseObject(JSON.toJSONString(info[i]), ApprovalProcess.class);

			if(demandAnalysisDo!=null){
				list.add(demandAnalysisDo);
			}
		}

		if(list.size()>0){
			//删除审批流程设定中的流程设定
			ApprovalProcessSet approvalProcessSet = new ApprovalProcessSet();
			approvalProcessSet.setBillFid(Long.parseLong(list.get(0).getBillFid()));
			approvalProcessSetService.remove(Wrappers.query(approvalProcessSet));
		}

		for(ApprovalProcess map:list){
			if(map.getUserList()!=null){
				for(int i=0;i<map.getUserList().length;i++){
					ApprovalProcessSet approvalProcessSet = new ApprovalProcessSet();
					approvalProcessSet.setProcessFid(map.getFId().longValue());
					approvalProcessSet.setProcessNo("1");
					approvalProcessSet.setOrderBy(map.getOrderBy());
					approvalProcessSet.setBillFid(Long.parseLong(map.getBillFid()));
					approvalProcessSet.setIsAgree("0");
					approvalProcessSet.setOperNam(SecurityUtils.getUser().getUsername());
					approvalProcessSet.setRecordTim(new Date());
					approvalProcessSet.setUpdateNam(SecurityUtils.getUser().getUsername());
					approvalProcessSet.setUpdateTim(new Date());
					approvalProcessSet.setLinkNam(map.getLinkNam());
					approvalProcessSet.setRoleNam(map.getRoleNam());

					BSUser bsuser = new BSUser();
					bsuser.setUserId(map.getUserList()[i]);
					BSUser bsUserInfo = bSUserService.getById(map.getUserList()[i]);
					approvalProcessSet.setUsername(bsUserInfo.getUsername());
					approvalProcessSet.setUserId(bsUserInfo.getUserId());
					approvalProcessSet.setManName(bsUserInfo.getManName());
					approvalProcessSetService.save(approvalProcessSet);


//select process_fid ,listagg(username, ',') within group( order by process_fid) as name from APPROVAL_PROCESS_SET group by process_fid

				}
			}

		}

		return R.ok();
	}

	@Override
	public R getProcessSets(ApprovalProcessSet approvalProcessSet) {
		List<ApprovalProcessSet> list = list(Wrappers.query(approvalProcessSet).orderByAsc("ORDER_BY"));
		return R.ok(list);
	}


	@Override
	public void sendMessage(ApprovalProcessSet approvalProcessSet, String content){
		//判断当前审批顺序是否已经全部同意，来向下一步骤的用户发送短信
		ApprovalProcessSet approvalProcessSetMessage = new ApprovalProcessSet();
		approvalProcessSetMessage.setProcessNo(approvalProcessSet.getProcessNo());
		approvalProcessSetMessage.setBillFid(approvalProcessSet.getBillFid());
		approvalProcessSetMessage.setOrderBy(approvalProcessSet.getOrderBy());
		List<ApprovalProcessSet> messAppList = approvalProcessSetService.list(Wrappers.query(approvalProcessSetMessage).isNull("DEL_NAM"));
		int messFlag = 0;
		for (ApprovalProcessSet item:messAppList) {
			if((!item.getUsername().equals(SecurityUtils.getUser().getUsername())&&!"1".equals(item.getIsAgree()))||"2".equals(approvalProcessSet.getIsAgree())){
				messFlag =1;
			}
		}
		if(messFlag == 0){
			approvalProcessSetMessage.setOrderBy(approvalProcessSetMessage.getOrderBy()+1);
			messAppList = approvalProcessSetService.list(Wrappers.query(approvalProcessSetMessage).isNull("DEL_NAM"));
			List<String> userList = new ArrayList<>();
			if(messAppList.size()>0){
				for (ApprovalProcessSet item:messAppList) {
					if(!userList.contains(item.getUsername())){
						userList.add(item.getUsername());
					}
				}
				for (String item:userList) {
					BSUser bsUser1 = new BSUser();
					bsUser1.setUsername(item);
					List<BSUser> list = bSUserService.list(Wrappers.query(bsUser1));
					Smsdb smsdb = new Smsdb();
					if(list.get(0).getPhone() != null && !"".equals(list.get(0).getPhone().trim())){
						smsdb.setCallnum(list.get(0).getPhone());
						smsdb.setContent(content);
						Date date = new Date();
						date.setMinutes(date.getMinutes()+1);
						smsdb.setCreatetime(date);
						smsdb.setFlag("0");
						smsdb.setMsgtype("保税物流");
						smsdbService.save(smsdb);
					}
				}
			}
		}
		//短信end
	}

}
