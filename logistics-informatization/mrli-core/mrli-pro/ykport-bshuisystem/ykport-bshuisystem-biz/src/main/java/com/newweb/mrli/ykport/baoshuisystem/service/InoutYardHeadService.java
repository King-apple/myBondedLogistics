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
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardHead;
import com.newweb.mrli.ykport.baoshuisystem.vo.InoutYardHeadVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 出入库表头
 *
 * @author zada
 * @date 2021-09-02 18:43:01
 */
public interface InoutYardHeadService extends IService<InoutYardHead> {
	/**
	 * 插入入库head数据
	 * @param inoutYardHead
	 * @return
	 */
	InoutYardHead saveForServer(InoutYardHead inoutYardHead);

	/**
	 * 查询最新的一条编号
	 * @param inoutYardHead
	 * @return
	 */
	InoutYardHead getInoutYardNo(String inoutYardHead);

	R getInoutHeadBody(InoutYardHead inoutYardHead);

	/**
	 *一日游进出区统计
	 * @param inoutYardHeadVo
	 * @return
	 */
	IPage<List<InoutYardHeadVo>> getOneDay(InoutYardHeadVo inoutYardHeadVo, Page page);

	List<InoutYardHeadVo> getOneDayExcel(InoutYardHeadVo inoutYardHeadVo);

	Map<String,Object> getOneDayCount(Map<String,String> map);

	void expoprtOneDayExcel(InoutYardHeadVo inoutYardHeadVo, List<InoutYardHeadVo> list, HttpServletResponse esponse) throws IOException;

	IPage<List<InoutYardHead>> getHeadList(InoutYardHead inoutYardHead, Page<?> page);
	InoutYardHead getHeadForDetail(InoutYardHead inoutYardHead);
}
