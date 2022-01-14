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

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.Ioyard;
import com.newweb.mrli.ykport.baoshuisystem.entity.YardInfo;
import com.newweb.mrli.ykport.baoshuisystem.mapper.YardInfoMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.YardInfoService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 电子货位数据表
 *
 * @author pigx code generator
 * @date 2021-09-17 15:39:11
 */
@Service
public class YardInfoServiceImpl extends ServiceImpl<YardInfoMapper, YardInfo> implements YardInfoService {

	@Override
	public String cargoSpaceMerage(String yardInfoList) {
		List<YardInfo> yardInfoLists = JSONArray.parseArray(yardInfoList,YardInfo.class);
		int outJianshu = 0;
		Double outWeight = 0.0;
		int inJianshu = 0;
		Double inWeight = 0.0;
		YardInfo yardInfo = new YardInfo();
		String bh = "";
		for (int i = 0; i < yardInfoLists.size(); i++) {
			if(i == 0){
				bh = yardInfoLists.get(i).getBh();
			}
			if(!bh.equals(yardInfoLists.get(i).getBh())){
				return "1";
			}
		}
		for (int i = 0; i < yardInfoLists.size(); i++) {
			outJianshu+=(yardInfoLists.get(i).getOutPiecesNum()==null?0:yardInfoLists.get(i).getOutPiecesNum());
			outWeight+=(yardInfoLists.get(i).getOutWeightWgt()==null?0:yardInfoLists.get(i).getOutWeightWgt());
			inJianshu+=(yardInfoLists.get(i).getInPiecesNum()==null?0:yardInfoLists.get(i).getInPiecesNum());
			inWeight+=(yardInfoLists.get(i).getInWeightWgt()==null?0:yardInfoLists.get(i).getInWeightWgt());
			if(i == 0){
				yardInfo = yardInfoLists.get(i);
			}
			yardInfoLists.get(i).setDelNam(SecurityUtils.getUser().getUsername());
			yardInfoLists.get(i).setDelTim(new Date());
			updateById(yardInfoLists.get(i));
		}
		yardInfo.setDelNam(null);
		yardInfo.setDelTim(null);
		yardInfo.setOutPiecesNum(outJianshu);
		yardInfo.setOutWeightWgt(outWeight);
		yardInfo.setInPiecesNum(inJianshu);
		yardInfo.setInWeightWgt(inWeight);
		yardInfo.setSurplusPiecesNum(inJianshu-outJianshu);
		yardInfo.setSurplusWeightWgt(inWeight-outWeight);
		save(yardInfo);
		System.out.println(yardInfoList);
		return "2";
	}

	@Override
	public R cargoSpaceSplit(YardInfo yardInfo) {
		if(yardInfo.getFId() != null){
			YardInfo yardInfoOne = getById(yardInfo.getFId());
			int surplusPiecesNum = yardInfoOne.getSurplusPiecesNum();
			Double surplusWeightWgt =  yardInfoOne.getSurplusWeightWgt();
			int inPiecesNum = yardInfoOne.getInPiecesNum();
			Double inWeightWgt = yardInfoOne.getInWeightWgt();

			if(yardInfo.getSurplusPiecesNum() >inPiecesNum){
				return R.failed("拆分件数不可大于剩余件数");
			}
			if(yardInfo.getSurplusWeightWgt() >inWeightWgt){
				return R.failed("拆分量不可大于剩余量");
			}
			yardInfoOne.setSurplusPiecesNum(surplusPiecesNum-yardInfo.getSurplusPiecesNum());
			yardInfoOne.setSurplusWeightWgt(surplusWeightWgt-yardInfo.getSurplusWeightWgt());
			yardInfoOne.setInPiecesNum(inPiecesNum-yardInfo.getSurplusPiecesNum());
			yardInfoOne.setInWeightWgt(inWeightWgt-yardInfo.getSurplusWeightWgt());
			updateById(yardInfoOne);
			yardInfoOne.setSurplusPiecesNum(yardInfo.getSurplusPiecesNum());
			yardInfoOne.setSurplusWeightWgt(yardInfo.getSurplusWeightWgt());
			yardInfoOne.setInPiecesNum(yardInfo.getSurplusPiecesNum());
			yardInfoOne.setInWeightWgt(yardInfo.getSurplusWeightWgt());
			yardInfoOne.setAreaNam(yardInfo.getAreaNam());
			save(yardInfoOne);
		}
		return R.ok("拆分成功");
	}

	@Override
	public List<Ioyard> getCargo() {
		return baseMapper.getCargo();
	}

	@Override
	public List<Ioyard> getConSign() {
		return baseMapper.getConSign();
	}
}
