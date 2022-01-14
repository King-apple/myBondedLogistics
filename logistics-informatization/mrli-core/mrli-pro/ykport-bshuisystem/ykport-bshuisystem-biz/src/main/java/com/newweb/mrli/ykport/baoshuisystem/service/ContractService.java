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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.ykport.baoshuisystem.dto.ContractInfoDto;
import com.newweb.mrli.ykport.baoshuisystem.entity.ApprovalProcessSet;
import com.newweb.mrli.ykport.baoshuisystem.entity.ApprovalRecord;
import com.newweb.mrli.ykport.baoshuisystem.entity.Contract;
import com.newweb.mrli.ykport.baoshuisystem.vo.ContractFileVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 合同信息管理表
 *
 * @author pigx code generator
 * @date 2021-08-03 09:54:56
 */
public interface ContractService extends IService<Contract> {

	public boolean insertContract(Contract contract);

	IPage<ContractFileVo> selectPageVo(Page<?> page, ContractFileVo contractFileVo);

	IPage<ContractFileVo> selectPageVoJoinApprovalRecord(Page<?> page, ContractFileVo contractFileVo);

	int updateContract(Contract contract);


	ContractInfoDto getContractInsertValue();

	R insertCon(Contract contract);

	void exportExcel(Long fid, HttpServletRequest request, HttpServletResponse response) throws IOException;

	List<ContractFileVo> getEffectiveInfo(Contract contract);

	R updateApprovalprocessset(ApprovalProcessSet approvalProcessSet) throws Exception;

	R insertUserRecords(String userRecords);

	List<ContractFileVo> getEffectiveInfoOther(Contract contract, String[] deptArr);
}
