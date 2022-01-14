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
import com.newweb.mrli.ykport.baoshuisystem.entity.CustomsAccountingStatistics;
import com.newweb.mrli.ykport.baoshuisystem.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 海关账册录入统计表
 *
 * @author ly
 * @date 2021-08-30 18:00:02
 */
@Mapper
public interface CustomsAccountingStatisticsMapper extends MrliBaseMapper<CustomsAccountingStatistics> {

	IPage<CustomsAccountingStatisticsVo> selectPageVo(Page<?> page, @Param("customsAccountingStatisticsvo")  CustomsAccountingStatisticsVo customsAccountingStatisticsvo);

	List<CustomsVo> getManipulated(CustomsVo customsVo);

	List<CustomsCargoVo> getManipulatedCargo(CustomsCargoVo customsCargoVo);

	List<CustomsOutAmountVo> getOutAmount(CustomsOutAmountVo customsOutAmountVo);

	List<CustomsVo> getManipulatedForNew(CustomsVo customsVo);

	List<CargoKindTypeVo> getCargoKind(CargoKindTypeVo cargoKindTypeVo);
	CustomsAccountingStatisticsVo finalSql( @Param("customsAccountingStatisticsvo")CustomsAccountingStatisticsVo customsAccountingStatisticsVo);

	List<CustomsAccountingStatisticsVo> getAll( @Param("customsAccountingStatisticsvo") CustomsAccountingStatisticsVo customsAccountingStatisticsvo);
}
