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
package com.newweb.mrli.ykport.baoshuisystem.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.ykport.baoshuisystem.entity.CodeList;
import com.newweb.mrli.ykport.baoshuisystem.mapper.CodeListMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.ParameterListMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.CodeListService;
import com.newweb.mrli.ykport.baoshuisystem.service.ParameterListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * 编号表
 *
 * @author pigx code generator
 * @date 2021-08-30 15:26:28
 */
@Service
public class CodeListServiceImpl extends ServiceImpl<CodeListMapper, CodeList> implements CodeListService {

	@Autowired
	private CodeListMapper codeListMapper;

	@Autowired
	private ParameterListMapper parameterListMapper;
	@Override
	public int updateCodeList(CodeList codelist) {
		return codeListMapper.updateCodeList(codelist);
	}

	@Override
	public int insertCodeList(CodeList codelist) {
		return codeListMapper.insertCodeList(codelist);
	}

	@Override
	public CodeList getContractCode(CodeList codeList) {
//		String year = Calendar.getInstance().get(Calendar.YEAR)+"";
		String year = parameterListMapper.getYear();
		codeList.setParamDate(year);
		List<CodeList> list = list(Wrappers.query(codeList));
		if(list.isEmpty()){
			codeList.setParamCode("001");
		}else{
			int i = Integer.parseInt(list.get(0).getParamCode());
			i++;
			String result = String.format("%0" + 3 + "d", Integer.parseInt(i+""));
			codeList.setParamCode(result);
		}
		return codeList;
	}
}
