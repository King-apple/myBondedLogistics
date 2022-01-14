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

import com.baomidou.mybatisplus.extension.service.IService;
import com.newweb.mrli.ykport.baoshuisystem.entity.SettlementContractLedger;
import com.newweb.mrli.ykport.baoshuisystem.vo.ContractFileVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 保税业务合同结算台账
 *
 * @author ly
 * @date 2021-12-14 10:25:42
 */
public interface SettlementContractLedgerService extends IService<SettlementContractLedger> {

	void export(List<ContractFileVo> contract, HttpServletResponse response) throws IOException;

	List<SettlementContractLedger> getListALL(String contNo);
}
