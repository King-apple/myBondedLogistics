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
import com.newweb.mrli.ykport.baoshuisystem.entity.BusinessBillsHead;
import com.newweb.mrli.ykport.baoshuisystem.vo.ExternalPaymentVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.IncomeStatistcsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 业务单据头表
 *
 * @author 李洋
 * @date 2021-09-06 09:12:35
 */
@Mapper
public interface BusinessBillsHeadMapper extends MrliBaseMapper<BusinessBillsHead> {

    IPage<ExternalPaymentVo> selectPageVo(Page page, @Param("externalPaymentVo") ExternalPaymentVo externalPaymentVo);

    IPage<ExternalPaymentVo> selectBusinessPageVo(Page page, @Param("externalPaymentVo") ExternalPaymentVo externalPaymentVo);

	List<ExternalPaymentVo> selectPayment();

	List<ExternalPaymentVo> selectSettlement();

	IPage<ExternalPaymentVo> getCargoKindOperation(Page page,@Param("externalPaymentVo") ExternalPaymentVo externalPaymentVo);

	List<ExternalPaymentVo> getCargoKindOperation(@Param("externalPaymentVo") ExternalPaymentVo externalPaymentVo);

	IPage<IncomeStatistcsVo> getIncomeStatistcs(Page page, @Param("externalPaymentVo") ExternalPaymentVo externalPaymentVo);

	List<IncomeStatistcsVo> getIncomeStatistcs(@Param("externalPaymentVo") ExternalPaymentVo externalPaymentVo);

}
