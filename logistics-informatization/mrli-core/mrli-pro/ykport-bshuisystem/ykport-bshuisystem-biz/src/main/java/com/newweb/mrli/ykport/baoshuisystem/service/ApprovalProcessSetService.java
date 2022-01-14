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

package com.newweb.mrli.ykport.baoshuisystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.ykport.baoshuisystem.entity.ApprovalProcessSet;
import com.newweb.mrli.ykport.baoshuisystem.entity.ApprovalRecord;
import com.newweb.mrli.ykport.baoshuisystem.entity.BillApprove;
import com.newweb.mrli.ykport.baoshuisystem.vo.BillApproveAppProSetVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.BillApproveVo;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * 审批流程设定表
 *
 * @author pigx code generator
 * @date 2021-10-08 15:18:31
 */
public interface ApprovalProcessSetService extends IService<ApprovalProcessSet> {

	public List<Map<Integer,Object>> getOpearAudit(Integer billFid);

	/**
	 * 获取billId分组数据
	 * @return
	 */
	List<ApprovalProcessSet> getBillIdList(ApprovalProcessSet approvalProcessSet);

	ApprovalProcessSet getApprovalProcessSetList(ApprovalProcessSet approvalProcessSet);
	ApprovalProcessSet getApprovalProcessSetCheck(ApprovalProcessSet approvalProcessSet);

	List<ApprovalProcessSet> selectApprovalProcessSetGroup(ApprovalProcessSet approvalProcessSet);

	R saveForOpear(Object[] info);

	R getProcessSets(ApprovalProcessSet approvalProcessSet);

	IPage<BillApproveAppProSetVo> getBillApproveList(Page<?> page, List idList, BillApproveAppProSetVo billApprove);
	IPage<BillApproveAppProSetVo> getBillApproveListForSuccess(Page<?> page, List idList, BillApproveAppProSetVo billApprove);

	int updateByIdForNull(ApprovalProcessSet approvalProcessSet);

    List<ApprovalProcessSet> getMessageMerge(Integer fId);

    void sendMessage(ApprovalProcessSet approvalProcessSet, String content);
}
