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
import com.newweb.mrli.ykport.baoshuisystem.entity.OutportCertificate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 一日游出港证统计(暂时)
 *
 * @author zada
 * @date 2021-10-22 11:06:37
 */
@Mapper
public interface OutportCertificateMapper extends MrliBaseMapper<OutportCertificate> {

	IPage<List<OutportCertificate>> getList(Page<?> page, @Param("outportCertificate")OutportCertificate outportCertificate);

	List<OutportCertificate> getListExcel(@Param("outportCertificate")OutportCertificate outportCertificate);
}
