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

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.Bcmcxtab;
import com.newweb.mrli.ykport.baoshuisystem.entity.Ioyard;
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardListBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.YardInfo;
import com.newweb.mrli.ykport.baoshuisystem.mapper.IoyardMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.BcmcxtabService;
import com.newweb.mrli.ykport.baoshuisystem.service.IoyardListBodyService;
import com.newweb.mrli.ykport.baoshuisystem.service.IoyardService;
import com.newweb.mrli.ykport.baoshuisystem.service.YardInfoService;
import com.newweb.mrli.ykport.baoshuisystem.vo.IoyardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ??????????????????
 *
 * @author pigx code generator
 * @date 2021-09-06 19:35:20
 */
@Service
public class IoyardServiceImpl extends ServiceImpl<IoyardMapper, Ioyard> implements IoyardService {
	@Autowired
	public IoyardMapper ioyardMapper;
	@Autowired
	public YardInfoService yardInfoService;
	@Autowired
	public IoyardListBodyService ioyardListBodyService;
	@Autowired
	public BcmcxtabService bcmcxtabService;
	@Override
	public int insertOutStroge(Ioyard ioyard) {
		return ioyardMapper.insertOutStroge(ioyard);
	}

	@Override
	public List<IoyardVo> getIoyardVo(Ioyard ioyard) {
		return ioyardMapper.getIoyardVo(ioyard);
	}

	@Override
	@Transactional
	public R inoutyardRecordRemove(Ioyard ioyard) {
		IoyardListBody ioyardListBody = new IoyardListBody();
		Ioyard ioyardEntity = getById(ioyard.getInoutYardNo());
		//?????????????????????
		if(ioyardEntity.getPrepareOutyardFlag()!=null&&"1".equals(ioyardEntity.getPrepareOutyardFlag())){
			ioyardListBody.setIoyardListNo(ioyard.getInoutYardNo()+"");
			List<IoyardListBody> list = ioyardListBodyService
					.list(Wrappers.query(ioyardListBody).isNull("DEL_NAM"));
			//?????????????????????????????????????????????
			if(list.size()>0){
				return R.failed("???????????????????????????????????????????????????");
			}
			removeById(ioyard.getInoutYardNo());
			return R.ok("???????????????????????????");
		}else{
			if(ioyardEntity.getIoyardId() == 1){
				ioyardListBody = new IoyardListBody();
				//??????????????????????????????????????????
				Ioyard ioyardCheck = new Ioyard();
				ioyardCheck.setInYardNo(ioyard.getInoutYardNo()+"");
				List<Ioyard> list = list(Wrappers.query(ioyardCheck));
				List<String> inoutYardNoList = new ArrayList<>();
				for (int i = 0; i < list.size(); i++) {
					inoutYardNoList.add(list.get(i).getInoutYardNo()+"");
				}
				//???????????????????????????????????????
				if(inoutYardNoList.size()>0){
					List<IoyardListBody> ioyardListBodyCheckList = ioyardListBodyService
							.list(Wrappers.query(ioyardListBody).in("IOYARD_LIST_NO",inoutYardNoList));
					if(ioyardListBodyCheckList.size()>0){
						return R.failed("???????????????????????????????????????????????????");
					}
				}
				//??????ioyard??????
				removeById(ioyard.getInoutYardNo());
				//??????yardinfo??????
				YardInfo yardInfo = new YardInfo();
				yardInfo.setDelTim(new Date());
				yardInfo.setDelNam(SecurityUtils.getUser().getUsername());
				yardInfoService.update(yardInfo, Wrappers.query(new YardInfo()).eq("IN_YARD_NO",ioyard.getInoutYardNo()));
				//??????bcmcxTab??????
				Bcmcxtab bcmcxtab = new Bcmcxtab();
				bcmcxtab.setIoyardPk(ioyard.getInoutYardNo()+"");
				bcmcxtabService.remove(Wrappers.query(bcmcxtab));
				return R.ok("???????????????????????????");
			}else if(ioyardEntity.getIoyardId() == 0){
				ioyardListBody.setIoyardListNo(ioyard.getInoutYardNo()+"");
				List<IoyardListBody> list = ioyardListBodyService
						.list(Wrappers.query(ioyardListBody).isNull("DEL_NAM"));
				//?????????????????????????????????????????????
				if(list.size()>0){
					return R.failed("???????????????????????????????????????????????????");
				}
				//????????????????????????????????????????????????
				Ioyard inIoyard = new Ioyard();
				inIoyard.setInoutYardNo(ioyardEntity.getInYardNo());
				List<Ioyard> inIoyardList = list(Wrappers.query(inIoyard));
				if(!inIoyardList.isEmpty()){
					inIoyard.setSurplusPieceNum(inIoyardList.get(0).getSurplusPieceNum()+ioyardEntity.getPiecesNum());
					inIoyard.setSurplusWeight(inIoyardList.get(0).getSurplusWeight().add(new BigDecimal(ioyardEntity.getWeightWgt()+"")));
					updateById(inIoyard);
				}
				//??????ioyard?????????
				removeById(ioyard.getInoutYardNo());
				//??????yardInfo??????
				YardInfo yardInfo = new YardInfo();
				yardInfo.setInYardNo(ioyardEntity.getInYardNo());
				List<YardInfo> yardInfoList = yardInfoService
						.list(Wrappers.query(yardInfo).isNull("DEL_NAM"));
				if(yardInfoList.size()==1){
					yardInfo.setSurplusWeightWgt(yardInfoList.get(0).getSurplusWeightWgt()+ioyardEntity.getWeightWgt());
					yardInfo.setSurplusPiecesNum(yardInfoList.get(0).getSurplusPiecesNum()+ioyardEntity.getPiecesNum());
					yardInfo.setOutWeightWgt(yardInfoList.get(0).getOutWeightWgt()-ioyardEntity.getWeightWgt());
					yardInfo.setOutPiecesNum(yardInfoList.get(0).getOutPiecesNum()-ioyardEntity.getPiecesNum());

					yardInfoService.update(yardInfo,Wrappers.query(new YardInfo())
							.eq("IN_YARD_NO",ioyardEntity.getInYardNo()));
				}else{
					return R.ok("????????????????????????????????????????????????????????????????????????????????????");
				}
				return R.ok("???????????????????????????");
			}else{
				return R.failed("????????????");
			}
		}
	}

	@Override
	public List<Ioyard> getContNo() {
		return baseMapper.getContNo();
	}

	@Override
	public List<Ioyard> getShip() {
		return baseMapper.getShip();
	}

	@Override
	public List<Ioyard> getCargo() {
		return baseMapper.getCargo();
	}

	@Override
	public List<Ioyard> getConSign() {
		return baseMapper.getConSign();
	}

	@Override
	public Ioyard getAll(Ioyard ioyard, String parse, String parse1) {
		return  baseMapper.getAll(ioyard,parse,parse1);
	}

	@Override
	public List<Ioyard> getBillNO() {
		return baseMapper.getBillNO();
	}

	@Override
	public List<Ioyard> getCustomsDeclaration() {
		return baseMapper.getCustomsDeclaration();
	}
}
