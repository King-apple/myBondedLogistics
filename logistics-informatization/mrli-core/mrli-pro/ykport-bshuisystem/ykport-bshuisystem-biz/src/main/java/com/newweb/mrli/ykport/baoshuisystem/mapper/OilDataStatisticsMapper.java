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
import com.newweb.mrli.ykport.baoshuisystem.entity.OilDataStatistics;
import com.newweb.mrli.ykport.baoshuisystem.vo.OilDataStatisticsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 油品录入数据统计表
 *
 * @author Tara
 * @date 2021-10-19 09:48:47
 */
@Mapper
public interface OilDataStatisticsMapper extends MrliBaseMapper<OilDataStatistics> {

	IPage<OilDataStatisticsVo> selectPageData(Page<?> page,@Param("oilDataStatisticsVo") OilDataStatisticsVo oilDataStatisticsVo);

	List<OilDataStatisticsVo> selectOilDataStatisticsVoList(@Param("oilDataStatisticsVo") OilDataStatisticsVo oilDataStatisticsVo);
}
