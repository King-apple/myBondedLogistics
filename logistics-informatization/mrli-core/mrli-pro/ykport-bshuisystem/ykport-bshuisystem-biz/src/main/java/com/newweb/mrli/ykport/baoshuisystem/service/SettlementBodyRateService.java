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
import com.newweb.mrli.ykport.baoshuisystem.entity.SettlementBodyRate;
import com.newweb.mrli.ykport.baoshuisystem.entity.SettlementHeadRate;

import java.util.List;

/**
 * 出入库结算单明细费用表
 *
 * @author pigx code generator
 * @date 2021-11-01 09:15:08
 */
public interface SettlementBodyRateService extends IService<SettlementBodyRate> {

    List<SettlementBodyRate> getList(Integer id);

	boolean getTrueUpdate(Integer fId);

	void deleteById(Integer fId);

	List<SettlementBodyRate> addMessage(Integer addId,Integer addHfId);

}
