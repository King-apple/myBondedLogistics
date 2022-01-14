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
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardHead;
import com.newweb.mrli.ykport.baoshuisystem.vo.InoutYardHeadVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 出入库表头
 *
 * @author zada
 * @date 2021-09-02 18:43:01
 */
@Mapper
public interface InoutYardHeadMapper extends MrliBaseMapper<InoutYardHead> {
	Integer selectInoutYardHeadFid();

	/**
	 * 查询最新的一条编号
	 * @param inoutYardHead
	 * @return
	 */
	InoutYardHead getInoutYardNo(String inoutYardHead);

	/**
	 * 一日游进出区统计
	 * @param inoutYardHeadVo
	 * @return
	 */
	IPage<List<InoutYardHeadVo>> getOneDay(@Param("inoutYardHeadVo")InoutYardHeadVo inoutYardHeadVo, Page<?> page);

	List<InoutYardHeadVo> getOneDay(@Param("inoutYardHeadVo")InoutYardHeadVo inoutYardHeadVo);

	Map<String,Object> getOneDayCount(Map<String,String> map);

	IPage<List<InoutYardHead>> getHeadList(@Param("inoutYardHead")InoutYardHead inoutYardHead, Page<?> page);

	InoutYardHead getHeadForDetail(@Param("inoutYardHead")InoutYardHead inoutYardHead);
}
