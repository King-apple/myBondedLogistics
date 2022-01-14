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
import com.newweb.mrli.ykport.baoshuisystem.entity.SingClassInfo;
import com.newweb.mrli.ykport.baoshuisystem.entity.YkbfSingClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 单个工班查询
 *
 * @author pigx code generator
 * @date 2021-10-24 15:49:10
 */
@Mapper
public interface YkbfSingClassMapper extends MrliBaseMapper<YkbfSingClass> {

    IPage<SingClassInfo> getPage(Page page, @Param("ykbfSingClass") YkbfSingClass ykbfSingClass);

	List<SingClassInfo> getNoPage( @Param("ykbfSingClass")  YkbfSingClass ykbfSingClass);
}
