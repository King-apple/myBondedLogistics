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
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardListHead;
import com.newweb.mrli.ykport.baoshuisystem.vo.ContractFileVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.IoyardListHeadBodyVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 出入库明细表头表
 *
 * @author pigx code generator
 * @date 2021-09-09 09:52:12
 */
public interface IoyardListHeadService extends IService<IoyardListHead> {

	R insertIoyardInfo(IoyardListHead ioyardListHead);

	IPage<IoyardListHeadBodyVo> getIoyardListHead(Page<?> page, IoyardListHeadBodyVo ioyardListHeadBodyVo);

	void exportFile(IoyardListHeadBodyVo ioyardListHeadBodyVo, HttpServletRequest request, HttpServletResponse response);

	R removeIoyardListHeadAndBody(Integer fId);
}
