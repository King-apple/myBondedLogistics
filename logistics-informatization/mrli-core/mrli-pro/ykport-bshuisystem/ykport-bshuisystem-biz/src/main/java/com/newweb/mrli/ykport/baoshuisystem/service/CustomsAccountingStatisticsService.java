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
import com.newweb.mrli.ykport.baoshuisystem.entity.CustomsAccountingStatistics;
import com.newweb.mrli.ykport.baoshuisystem.vo.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 海关账册录入统计表
 *
 * @author ly
 * @date 2021-08-30 18:00:02
 */
public interface CustomsAccountingStatisticsService extends IService<CustomsAccountingStatistics> {


	IPage<CustomsAccountingStatisticsVo> selectPageVo(Page<?> page, CustomsAccountingStatisticsVo customsAccountingStatisticsvo);

    List<CustomsAccountingStatistics> selectAll();

	List<CustomsVo> getManipulated(CustomsVo customsVo);

	List<CustomsCargoVo> getManipulatedCargo(CustomsCargoVo customsCargoVo);

	List<CustomsOutAmountVo> getOutAmount(CustomsOutAmountVo customsOutAmountVo);

	List<CargoKindTypeVo> getCargoKind(CargoKindTypeVo cargoKindTypeVo);

	List<CustomsVo> getManipulatedForNew(CustomsVo customsVo);

	List<CustomsAccountingStatistics> selectList();

	void expoprtManipulatedExcel(CustomsVo customsVo, List<CustomsVo> list, HttpServletResponse response) throws IOException;

	void expoprtManipulatedForNewExcel(CustomsVo customsVo, List<CustomsVo> list, HttpServletResponse response) throws IOException;

	void exportManipulatedCargoExcel(CustomsCargoVo customsVo,List<CustomsCargoVo> customsVoList,HttpServletResponse response) throws IOException;

    void expoprtExcel(CustomsAccountingStatisticsVo customsAccountingStatisticsVo, HttpServletResponse response,Page page) throws IOException;

	void exportOutAmountExcel(CustomsOutAmountVo customsOutAmountVo,List<CustomsOutAmountVo> carGoList,HttpServletResponse response) throws IOException;

	void exportCargoKindExcel(CargoKindTypeVo cargoKindTypeVo,List<CargoKindTypeVo> customsVoList,HttpServletResponse response) throws IOException;

	CustomsAccountingStatisticsVo countAll(CustomsAccountingStatisticsVo customsAccountingStatisticsVo);
}
