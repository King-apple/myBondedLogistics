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

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newweb.mrli.ykport.baoshuisystem.entity.CheckWeight;
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardBody;

import java.text.DecimalFormat;

/**
 * 衡重申请单
 *
 * @author pigx code generator
 * @date 2021-09-06 13:35:12
 */
public interface CheckWeightService extends IService<CheckWeight> {

	/**
	 * 获取衡重单号
	 * @return
	 */
	public String getCheckWeightForCheckNo(InoutYardBody inoutYardBody);

	public int saveForCheckWeight(CheckWeight checkWeight);

	public int deleteForCheckWeight(CheckWeight checkWeight);

}
