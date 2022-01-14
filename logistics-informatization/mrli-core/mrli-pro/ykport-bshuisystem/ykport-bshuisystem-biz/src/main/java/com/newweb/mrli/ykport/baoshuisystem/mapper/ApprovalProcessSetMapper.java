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

package com.newweb.mrli.ykport.baoshuisystem.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.newweb.mrli.common.data.datascope.MrliBaseMapper;
import com.newweb.mrli.ykport.baoshuisystem.entity.ApprovalProcessSet;
import com.newweb.mrli.ykport.baoshuisystem.entity.ApprovalRecord;
import com.newweb.mrli.ykport.baoshuisystem.entity.BillApprove;
import com.newweb.mrli.ykport.baoshuisystem.vo.BillApproveAppProSetVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 审批流程设定表
 *
 * @author pigx code generator
 * @date 2021-10-08 15:18:31
 */
@Mapper
public interface ApprovalProcessSetMapper extends MrliBaseMapper<ApprovalProcessSet> {

	List<ApprovalProcessSet> selectApprovalProcessSetGroup(ApprovalProcessSet approvalProcessSet4);
	public List<Map<Integer,Object>> getOpearAudit(Integer billFid);

	List<ApprovalProcessSet> getBillIdList(ApprovalProcessSet approvalProcessSet);
	ApprovalProcessSet getApprovalProcessSetList(ApprovalProcessSet approvalProcessSet);
	ApprovalProcessSet getApprovalProcessSetCheck(ApprovalProcessSet approvalProcessSet);
	IPage<BillApproveAppProSetVo> getBillApproveList(IPage<?> page, @Param("ids")List idList, @Param("billApprove")BillApproveAppProSetVo billApprove);
	int updateByIdForNull(ApprovalProcessSet approvalProcessSet);

	List<ApprovalProcessSet>getMessageMerge(Integer fId);

	List<ApprovalProcessSet> getApprovalSetList(ApprovalProcessSet approvalProcessSet);
	IPage<BillApproveAppProSetVo> getBillApproveListForSuccess(IPage<?> page, @Param("ids")List idList, @Param("billApprove")BillApproveAppProSetVo billApprove);

}
