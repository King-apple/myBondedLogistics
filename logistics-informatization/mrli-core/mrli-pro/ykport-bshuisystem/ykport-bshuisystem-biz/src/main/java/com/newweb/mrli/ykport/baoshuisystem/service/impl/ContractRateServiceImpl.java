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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.Contract;
import com.newweb.mrli.ykport.baoshuisystem.entity.ContractRate;
import com.newweb.mrli.ykport.baoshuisystem.entity.PriceLibrary;
import com.newweb.mrli.ykport.baoshuisystem.mapper.ContractMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.ContractRateMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.PriceLibraryMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.ContractRateService;
import com.newweb.mrli.ykport.baoshuisystem.service.PriceLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 合同费率表
 *
 * @author pigx code generator
 * @date 2021-08-28 16:36:26
 */
@Service
public class ContractRateServiceImpl extends ServiceImpl<ContractRateMapper, ContractRate> implements ContractRateService {

	@Autowired
	private ContractRateMapper contractRateMapper;

	@Autowired
	private PriceLibraryMapper priceLibraryMapper;

	@Autowired
	private PriceLibraryService priceLibraryService;

	@Autowired
	private ContractMapper contractMapper;

	@Override
	public R saveList(List<ContractRate> list) {
		boolean a = true;
		int cFid = 0;
		if(!list.isEmpty()){
			cFid = list.get(0).getCFid();
			Contract contract = contractMapper.selectById(cFid);
			PriceLibrary priceLibrary = new PriceLibrary();
			priceLibrary.setCompanyCod(contract.getSecondCod());
			priceLibrary.setCompany(contract.getSecondNam());
			priceLibrary.setCargoCod(contract.getCargoCod());
			priceLibrary.setCargoKindCod(contract.getCargoKindCod());
			priceLibrary.setCargoKindNam(contract.getCargoKindNam());
			priceLibrary.setCargoNam(contract.getCargoNam());
			priceLibrary.setFormat(contract.getFormat());
			priceLibrary.setPkgKindNam(contract.getPkgKindNam());
			priceLibrary.setLocate(contract.getLocate());
			priceLibrary.setTradeId(contract.getTradeId());
			priceLibrary.setInoutId(contract.getInoutId());

			for (int i = 0; i <list.size() ; i++) {
				priceLibrary.setFeeNameCod(list.get(i).getFeeNameCod());
				priceLibrary.setFeeNam(list.get(i).getFeeNam());
				priceLibrary.setFeeRat(list.get(i).getFeeRat());
				priceLibrary.setFeeUnit(list.get(i).getFeeUnit());

				QueryWrapper<PriceLibrary> query = Wrappers.query(priceLibrary);
				List<PriceLibrary> priceLibraryList = priceLibraryService.list(query);
				priceLibrary.setMarkTxt(list.get(i).getMarkTxt());
				if(priceLibraryList.isEmpty()){
					priceLibrary.setOperNam(SecurityUtils.getUser().getManName());
					priceLibrary.setRecordTim(new Date());
					priceLibraryService.save(priceLibrary);
				}else{
					priceLibrary.setUpdateNam(SecurityUtils.getUser().getManName());
					priceLibrary.setUpdateTim(new Date());
					priceLibrary.setFId(priceLibraryList.get(0).getFId());
					priceLibraryService.updateById(priceLibrary);
				}

				a = save(list.get(i));
				if(a == false){
					return R.failed(String.valueOf(a));
				}
			}
		}

		return R.ok(a);
	}

	@Override
	public boolean removeByCFid(ContractRate contractRate) {
		return contractRateMapper.removeByCFid(contractRate);
	}
}
