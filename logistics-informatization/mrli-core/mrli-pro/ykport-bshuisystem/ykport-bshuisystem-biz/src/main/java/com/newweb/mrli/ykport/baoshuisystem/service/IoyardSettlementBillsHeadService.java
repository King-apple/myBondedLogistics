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
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardListHead;
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardSettlementBillsBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardSettlementBillsHead;
import com.newweb.mrli.ykport.baoshuisystem.vo.ContractFileVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.ExternalPaymentVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.IoyardListHeadBodyVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.IoyardSettlementBilldHeadBodyVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 出入库结算单头表
 *
 * @author ly
 * @date 2021-09-14 18:43:46
 */
public interface IoyardSettlementBillsHeadService extends IService<IoyardSettlementBillsHead> {

    IPage<ExternalPaymentVo> selectPageVo(Page page, IoyardSettlementBilldHeadBodyVo ioYardVo);

    List<IoyardListHeadBodyVo> getCodeInoutYard(IoyardListHead ioyardListHead);

    void exportById(IoyardSettlementBilldHeadBodyVo ioYardVo, HttpServletResponse response) throws IOException;

    List<String> selectBillCod();

	List<IoyardSettlementBilldHeadBodyVo> selectInoutBill();


}
