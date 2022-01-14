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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.data.datascope.MrliBaseMapper;
import com.newweb.mrli.ykport.baoshuisystem.entity.Contract;
import com.newweb.mrli.ykport.baoshuisystem.entity.SettlementContractLedger;
import com.newweb.mrli.ykport.baoshuisystem.vo.ContractFileVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 合同信息管理表
 *
 * @author pigx code generator
 * @date 2021-08-03 09:54:56
 */
@Mapper
public interface ContractMapper extends MrliBaseMapper<Contract> {

	IPage<ContractFileVo> selectPageVo(Page<?> page, @Param("contractFileVo") ContractFileVo contractFileVo);

	IPage<ContractFileVo> selectPageVoJoinApprovalRecord(Page<?> page, @Param("contractFileVo") ContractFileVo contractFileVo);

	int updateContract(Contract contract);

    List<ContractFileVo> getEffectiveInfo(Contract contract);

	List<SettlementContractLedger> getContractMessage(@Param("set") Set<String> contNoSet);

	List<SettlementContractLedger> getInYard(@Param("set") Set<String> contNoSet);
	List<SettlementContractLedger> getOutYard(@Param("set") Set<String> contNoSet);
	List<SettlementContractLedger> getRealInOut(@Param("set") Set<String> contNoSet);
	List<SettlementContractLedger> getCollection(@Param("set") Set<String> contNoSet);
	List<SettlementContractLedger> getSettlement(@Param("set") Set<String> contNoSet);
	List<SettlementContractLedger> getRebates(@Param("set") Set<String> contNoSet);

	List<SettlementContractLedger> getAllInfo(String contNo);

	List<ContractFileVo> getEffectiveInfoOther(@Param("contract") Contract contract,@Param("deptArr") String[] deptArr);
}
