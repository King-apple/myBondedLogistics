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
import com.newweb.mrli.ykport.baoshuisystem.entity.BillApprove;
import com.newweb.mrli.ykport.baoshuisystem.entity.BillApprove;
import com.newweb.mrli.ykport.baoshuisystem.entity.BusinessBillsHead;
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardSettlementBillsHead;
import com.newweb.mrli.ykport.baoshuisystem.vo.BillApproveAppProSetVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.BillApproveVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 业务单据审批单
 *
 * @author ly
 * @date 2021-09-17 10:48:12
 */
public interface BillApproveService extends IService<BillApprove> {

	IPage<BillApproveVo> selectPageVo(Page page, BillApproveVo billApprove);

	List<BusinessBillsHead> selectPayment();

	List<BusinessBillsHead> selectSettlement();

	List<IoyardSettlementBillsHead> selectInOut();

	R setAudit(BillApproveAppProSetVo billApproveAppProSetVo);

	void exportBill(BillApproveVo billApproveVo, HttpServletResponse response) throws IOException;

	R buinessCheck(BillApprove billApprove);
}
