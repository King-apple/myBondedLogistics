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
import com.newweb.mrli.ykport.baoshuisystem.entity.BusinessBillsBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.BusinessBillsHead;
import com.newweb.mrli.ykport.baoshuisystem.vo.ExternalPaymentVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.IncomeStatistcsVo;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 业务单据头表
 *
 * @author 李洋
 * @date 2021-09-06 09:12:35
 */
public interface BusinessBillsHeadService extends IService<BusinessBillsHead> {

	IPage<ExternalPaymentVo> selectPageVo(Page page, ExternalPaymentVo externalPaymentVo);

    void exportById(ExternalPaymentVo externalPaymentVo, HttpServletResponse response) throws IOException;

	IPage<ExternalPaymentVo> selectBusinessPageVo(Page page, ExternalPaymentVo externalPaymentVo);

    void exportByBusId(ExternalPaymentVo externalPaymentVo, HttpServletResponse response) throws IOException;

	List<ExternalPaymentVo> selectPayment();

	List<ExternalPaymentVo> selectSettlement();

	IPage<ExternalPaymentVo> getCargoKindOperation(Page page,@Param("externalPaymentVo") ExternalPaymentVo externalPaymentVo);

	IPage<IncomeStatistcsVo> getIncomeStatistcs(Page page, @Param("externalPaymentVo") ExternalPaymentVo externalPaymentVo);

	void cargoKindOperationPrintToExcel(ExternalPaymentVo externalPaymentVo) throws IOException;

	void incomeStatistcsPrintToExcel(ExternalPaymentVo externalPaymentVo) throws IOException;

}
