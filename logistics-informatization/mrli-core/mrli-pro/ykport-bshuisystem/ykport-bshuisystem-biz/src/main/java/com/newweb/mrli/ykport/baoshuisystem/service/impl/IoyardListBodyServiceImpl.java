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

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.Ioyard;
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardListBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardListHead;
import com.newweb.mrli.ykport.baoshuisystem.mapper.IoyardListBodyMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.IoyardListHeadMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.IoyardMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.IoyardListBodyService;
import com.newweb.mrli.ykport.baoshuisystem.service.IoyardListHeadService;
import com.newweb.mrli.ykport.baoshuisystem.service.IoyardService;
import com.newweb.mrli.ykport.baoshuisystem.service.ParameterListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 出入库明细表体表
 *
 * @author pigx code generator
 * @date 2021-09-09 15:02:10
 */
@Service
public class IoyardListBodyServiceImpl extends ServiceImpl<IoyardListBodyMapper, IoyardListBody> implements IoyardListBodyService {
	@Autowired
	public IoyardService ioyardService;
	@Autowired
	public IoyardMapper ioyardMapper;
	@Autowired
	public ParameterListService parameterListService;
	@Autowired
	public IoyardListBodyMapper ioyardListBodyMapper;
	@Autowired
	public IoyardListHeadService ioyardListHeadService;

	@Override
	@Transactional
	public R removeIoyardListBody(Long fId) {
		String inoutYardNo = null;
		// 判断头表中是否还有明细数据
		List<IoyardListBody> listOther = ioyardListBodyMapper.selectListOther(fId);
		if (listOther != null && listOther.size() == 1) {
			// 删除头表
			IoyardListHead ioyardListHead = new IoyardListHead();
			ioyardListHead.setFId(listOther.get(0).getFFid());
			ioyardListHead.setDelNam(SecurityUtils.getUser().getUsername());
			ioyardListHead.setDelTim(new Date());
			ioyardListHeadService.updateById(ioyardListHead);
			inoutYardNo = listOther.get(0).getIoyardListNo();
		} else {
			if (listOther != null) {
				for (IoyardListBody item : listOther) {
					if (item.getFId().equals(fId)) {
						inoutYardNo = item.getIoyardListNo();
					}
				}
			}
		}
		// 删除体表
		IoyardListBody ioyardListBody = new IoyardListBody();
		ioyardListBody.setDelNam(SecurityUtils.getUser().getUsername());
		ioyardListBody.setDelTim(new Date());
		ioyardListBody.setFId(fId);
		updateById(ioyardListBody);
		// 修改ioyard表数据
		String[] inoutYardNoArray = {inoutYardNo};

		Ioyard ioyard = new Ioyard();
		ioyard.setDetailFlag(null);
		LambdaUpdateWrapper<Ioyard> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.set(Ioyard::getDetailFlag,null);
		updateWrapper.in(Ioyard::getInoutYardNo,inoutYardNoArray);
		return R.ok(ioyardMapper.update(null,updateWrapper));
	}
}
