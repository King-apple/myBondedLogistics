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

import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.*;
import com.newweb.mrli.ykport.baoshuisystem.mapper.ApprovalProcessSetMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.*;
import com.newweb.mrli.ykport.baoshuisystem.vo.InStrogeDetail;
import com.newweb.mrli.ykport.baoshuisystem.mapper.InStrogeDetailMapper;
import com.newweb.mrli.ykport.baoshuisystem.vo.IoyardApprovalProcessSetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ${comments}
 *
 * @author pigx code generator
 * @date 2021-09-06 19:04:43
 */
@Service
public class InStrogeDetailServiceImpl extends ServiceImpl<InStrogeDetailMapper, InStrogeDetail> implements InStrogeDetailService {

	@Autowired
	private IoyardService ioyardService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private YardInfoService yardInfoService;
	@Autowired
	private BcmcxtabService bcmcxtabService;
	@Autowired
	private ApprovalProcessSetService approvalProcessSetService;
	@Autowired
	private ApprovalProcessSetMapper approvalProcessSetMapper;
	@Autowired
	private ApprovalProcessService approvalProcessService;
	@Autowired
	private ApprovalProcessUserService approvalProcessUserService;
	@Autowired
	private ApprovalRecordService approvalRecordService;

	@Override
	@Transactional
	public R upImage(InStrogeDetail inStrogeDetail,List<Bcmcxtab> bcmcxtabList) {
		String contNo = inStrogeDetail.getContractNo();
		inStrogeDetail.setContractNo(null);
		List<InStrogeDetail> list = list(Wrappers.query(new InStrogeDetail()).eq("BH",inStrogeDetail.getBh()));
		inStrogeDetail.setContractNo(contNo);
		Contract contract = new Contract();
		contract.setContNo(contNo);
		List<Contract> contractList = contractService.list(Wrappers.query(contract).isNull("DEL_TIM"));

		if(!list.isEmpty()){


			Ioyard ioyard = new Ioyard();
			if(!contractList.isEmpty()){
				ioyard.setCPkgKindNam(contractList.get(0).getPkgKindNam());
			}
			ioyard.setPiecesNum(Integer.parseInt(inStrogeDetail.getJianshu()));
			ioyard.setSurplusPieceNum(Integer.parseInt(inStrogeDetail.getJianshu()));
			ioyard.setUnitCod(list.get(0).getUnitCod());
			ioyard.setIoyardDte(inStrogeDetail.getInStrogeDate());
			ioyard.setClassId("2");//班次待商议
			ioyard.setIoyardNo(list.get(0).getBh());
			ioyard.setTallySeqNo(1L);
//			ioyard.setInoutYardNo(0L);//自动生成
			ioyard.setSubunitCod(list.get(0).getSubunitCod());
			ioyard.setSubunitNam(list.get(0).getSubunitNam());
			ioyard.setShipNo(Long.parseLong(list.get(0).getCbh()));
			ioyard.setShipNam(list.get(0).getCm());
			ioyard.setBillNo(list.get(0).getTdh());
			ioyard.setInShipNo(0L);//进口船信息
			ioyard.setInShipNam("");//进口船信息
			if(list.get(0).getJck()!=null&&list.get(0).getJck().indexOf("进")!=-1){
				ioyard.setInoutId(0);
			}else if(list.get(0).getJck().indexOf("出")!=-1){
				ioyard.setInoutId(1);
			}else{
				ioyard.setInoutId(1);
			}

			if(list.get(0).getNwm()!=null&&list.get(0).getNwm().indexOf("内")!=-1){
				ioyard.setTradeId(0);
			}else if(list.get(0).getNwm().indexOf("外")!=-1){
				ioyard.setTradeId(1);
			}else {
				ioyard.setTradeId(1);
			}

			ioyard.setTrainNo(list.get(0).getCbb()==null?null:Integer.parseInt(list.get(0).getCbb()));
			ioyard.setIoyardId(1);
			ioyard.setInYardWay(Integer.parseInt(inStrogeDetail.getInStrogeMethod()));
			ioyard.setInPortWay(1);
			ioyard.setFirstInDte(list.get(0).getFirstInStrogeDate());
			ioyard.setYardNo(inStrogeDetail.getZykq());
			ioyard.setYardNam(list.get(0).getStrogeName());
			ioyard.setLocNo("");//货位号
			ioyard.setConsignCod(list.get(0).getAgentCod());
			ioyard.setConsignNam(list.get(0).getAgentNam());
			ioyard.setShipperCod(list.get(0).getCargoOwnerCode());
			ioyard.setShipperDoc(list.get(0).getCargoOwnerName());
			ioyard.setCargoNo(list.get(0).getCargoNo()==null?null:Long.parseLong(list.get(0).getCargoNo()));
			ioyard.setCargoKindCod(list.get(0).getCargoKindCod());
			ioyard.setCargoKindNam(list.get(0).getHl());
			ioyard.setCargoNamCod(list.get(0).getHm());
			ioyard.setCargoNam(list.get(0).getCargoNam());
			ioyard.setPkgKindCod(list.get(0).getPackingCode());
//			ioyard.setCPkgKindNam(list.get(0).getCargoPackage());
			ioyard.setWeightWgt(inStrogeDetail.getHz());
			ioyard.setSurplusWeight(new BigDecimal(inStrogeDetail.getHz()+""));
			ioyard.setFormat(list.get(0).getSpecification());
			ioyard.setFeeId(0+"");
			ioyard.setIfendId(list.get(0).getIfendId() == null?null:Integer.parseInt(list.get(0).getIfendId()));
			ioyard.setOperNam(SecurityUtils.getUser().getManName());
			ioyard.setRecordTim(new Date());
			ioyard.setContNo(inStrogeDetail.getContractNo());
			ioyard.setSettlementDtePre(inStrogeDetail.getInStrogeDate());
			ioyard.setCustomsDeclaration(inStrogeDetail.getInCustomsDeclaration());
			boolean save = ioyardService.save(ioyard);
			saveLocationDate(ioyard);
			for (int i = 0; i < bcmcxtabList.size(); i++) {
				bcmcxtabList.get(i).setIoyardPk(ioyard.getInoutYardNo()+"");
				bcmcxtabService.save(bcmcxtabList.get(i));
			}

			return R.ok("上图成功");
		}else{
			return R.ok("上图失败");
		}
	}

	@Override
	@Transactional
	public R destroyImage(InStrogeDetail inStrogeDetail) {
		Ioyard ioyard = new Ioyard();
		Ioyard inIoyardEntity = new Ioyard();
		//设置出入库单号
		ioyard.setInoutYardNo(inStrogeDetail.getInoutYardNo());
		//根据出入库单号复制一条出入库详情数据
		Ioyard byId = ioyardService.getById(ioyard);
		inIoyardEntity = byId;
		if(StringUtil.isBlank(inStrogeDetail.getJianshu())||((byId.getSurplusPieceNum()==null?0:byId.getSurplusPieceNum() )- Integer.parseInt(inStrogeDetail.getJianshu())) <0){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.failed("超出件数");
		}
		//审核通过时当次数等于1不走超出重量，直接修改ioyard
		if(!("1".equals(inStrogeDetail.getOverShortTimes()))){
			if(inStrogeDetail.getHz() == null||byId.getSurplusWeight().subtract(new BigDecimal(inStrogeDetail.getHz()+"")).compareTo(new BigDecimal("0"))==-1){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return R.failed("超出重量");
			}
		}

		boolean save = ioyardService.save(byId);
		//int i = ioyardService.insertOutStroge(ioyard);
		//设置出入库标志为出库
		byId.setIoyardId(0);
		//设置班组
		byId.setClassId(inStrogeDetail.getBz()==null||"".equals(inStrogeDetail.getBz())?null:inStrogeDetail.getBz());
		//设置完成量
		byId.setWeightWgt(inStrogeDetail.getHz());

//		byId.setIoyardDte(new Date());

		byId.setInYardNo(inStrogeDetail.getInoutYardNo());

		byId.setInYardWay(null);

		byId.setOutYardWay(1);
		//update
		Ioyard ioyard1 = new Ioyard();
		UpdateWrapper<Ioyard> ioyardUpdateWrapper = new UpdateWrapper<>();
		ioyardUpdateWrapper.eq("INOUT_YARD_NO",byId.getInoutYardNo());
		ioyardUpdateWrapper.set("Ioyard_Id",byId.getIoyardId());
		if (byId.getClassId() != null) {
			ioyardUpdateWrapper.set("Class_Id",byId.getClassId());
		}
		ioyardUpdateWrapper.set("Weight_Wgt",inStrogeDetail.getHz());
		ioyardUpdateWrapper.set("Ioyard_Dte",inStrogeDetail.getInStrogeDate());
		ioyardUpdateWrapper.set("In_Yard_No",inStrogeDetail.getInoutYardNo());
		ioyardUpdateWrapper.set("Out_Yard_Way",inStrogeDetail.getInStrogeMethod());
		ioyardUpdateWrapper.set("PIECES_NUM",inStrogeDetail.getJianshu());
		ioyardUpdateWrapper.set("OPER_NAM",SecurityUtils.getUser().getManName());
		ioyardUpdateWrapper.set("RECORD_TIM",new Date());
		//将over_times改为1
		if("1".equals(inStrogeDetail.getOverShortTimes())){
			ioyardUpdateWrapper.set("OVER_SHORT_TIMES",inStrogeDetail.getOverShortTimes());
		}

		if(inStrogeDetail.getPrepareFlag() !=null&&!"".equals(inStrogeDetail.getPrepareFlag())){
			ioyardUpdateWrapper.set("PREPARE_OUTYARD_FLAG",1);
			ioyardService.update(ioyardUpdateWrapper);
			Ioyard ioyardIn = ioyardService.getById(ioyard);
			ioyardIn.setSettlementDteLater(ioyardIn.getSettlementDtePre());
			ioyardIn.setSettlementDtePre(inStrogeDetail.getInStrogeDate());
			boolean b = ioyardService.updateById(ioyardIn);
			return R.ok("预出库成功")	;
		}else{
//			ioyardUpdateWrapper.set("SURPLUS_PIECE_NUM",byId.getSurplusPieceNum()- Integer.parseInt(inStrogeDetail.getJianshu()));
//			ioyardUpdateWrapper.set("SURPLUS_WEIGHT",byId.getSurplusWeight().subtract(new BigDecimal(inStrogeDetail.getHz()+"")));
			 Ioyard inIoyard = new Ioyard();
			inIoyard.setInoutYardNo(inStrogeDetail.getInoutYardNo());
			inIoyard.setSurplusWeight(byId.getSurplusWeight().subtract(new BigDecimal(inStrogeDetail.getHz()+"")));
			inIoyard.setSurplusPieceNum(byId.getSurplusPieceNum()- Integer.parseInt(inStrogeDetail.getJianshu()));
			inIoyard.setSettlementDtePre(inIoyardEntity.getIoyardDte());
			ioyardService.updateById(inIoyard);
			ioyardService.update(ioyardUpdateWrapper);
			byId.setPiecesNum(Integer.parseInt(inStrogeDetail.getJianshu()));
			byId.setWeightWgt(inStrogeDetail.getHz());
			byId.setInoutYardNo(inStrogeDetail.getInoutYardNo());

			String s = updateLocationDate(byId);
			if("true".equals(s)){
				return R.ok("销图成功")	;
			}else if("noMap".equals(s)) {
				return R.ok("销图成功,该垛位已拆分或合并，请手动更新垛位信息")	;
			}else{
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return R.failed(s);

			}
		}
	}

	@Override
	@Transactional
	public R submitOverShortCheck(ApprovalProcessSet approvalProcessSet) {
		Ioyard ioyard = new Ioyard();
		ioyard.setInoutYardNo(approvalProcessSet.getBillFid()+"");
		ioyard.setOverShortFlag("1");
		ioyard.setOverShortType(approvalProcessSet.getOverShortType());
		ioyard.setOverShortTimes(null);
		ioyardService.updateById(ioyard);
		approvalProcessSet.setRecordTim(new Date());
		approvalProcessSet.setOperNam(SecurityUtils.getUser().getUsername());
		return R.ok(approvalProcessSetService.save(approvalProcessSet));
	}

	@Override
	public R getOverShortCargo(Page page, IoyardApprovalProcessSetVO approvalProcessSet) {
		ApprovalProcessSet approvalProcessSet1 = new ApprovalProcessSet();
		approvalProcessSet1.setUsername(SecurityUtils.getUser().getUsername());
		approvalProcessSet1.setProcessNo(approvalProcessSet.getProcessNo());
		approvalProcessSet1.setIsAgree(approvalProcessSet.getIsAgree());
		List<ApprovalProcessSet> approvalSetList = approvalProcessSetMapper.getApprovalSetList(approvalProcessSet1);
		List<String> idList = new ArrayList<>();
		for (int i = 0; i < approvalSetList.size(); i++) {
			idList.add(approvalSetList.get(i).getBillFid()+"");
		}
		return R.ok(ioyardService.lambdaQuery()
				.like(StringUtil.isNotBlank(approvalProcessSet.getIoyardNo()),Ioyard::getIoyardNo,approvalProcessSet.getIoyardNo())
				.like(StringUtil.isNotBlank(approvalProcessSet.getConsignNam()),Ioyard::getConsignNam,approvalProcessSet.getConsignNam())
				.like(StringUtil.isNotBlank(approvalProcessSet.getShipNam()),Ioyard::getShipNam,approvalProcessSet.getShipNam())
				.like(StringUtil.isNotBlank(approvalProcessSet.getCargoNam()),Ioyard::getCargoNam,approvalProcessSet.getCargoNam())
				.in(idList.size()>0,Ioyard::getInoutYardNo,idList)
				.apply(idList.size()<=0,"1=2")
				.page(new Page<>(page.getCurrent(), page.getSize())));
	}

	@Override
	@Transactional
	public R submitCheck(ApprovalProcessSet approvalProcessSet) {
		ApprovalProcessSet approvalProcessSet1 = new ApprovalProcessSet();
		approvalProcessSet1.setFId(approvalProcessSet.getFId());
		approvalProcessSet1.setIsAgree(approvalProcessSet.getIsAgree());
		approvalProcessSet1.setOpinion(approvalProcessSet.getOpinion());
		approvalProcessSet1.setOpinionExplain(approvalProcessSet.getOpinionExplain());
		approvalProcessSet1.setApprover(SecurityUtils.getUser().getUsername());
		approvalProcessSet1.setApprovalTim(new Date());
		approvalProcessSetService.updateById(approvalProcessSet1);
		ApprovalProcessSet f_id = approvalProcessSetService.getOne(Wrappers.query(new ApprovalProcessSet())
				.eq("F_ID", approvalProcessSet.getFId()));
		if("1".equals(approvalProcessSet.getIsAgree())){
			//若审核通过，需要判断一下over_short_type，若为0则将over_times改为0
			if( "0".equals(approvalProcessSet.getOverShortType())){
				Ioyard ioyard = new Ioyard();
				ioyard.setInoutYardNo(approvalProcessSet.getBillFid()+"");
				ioyard.setOverShortTimes("0");
				ioyardService.updateById(ioyard);
			}
			if(approvalProcessSet.getUsername() == null){
				updateIoyard(approvalProcessSet);
			}else{
				ApprovalProcess approvalProcess = new ApprovalProcess();
				approvalProcess.setProcessNo("2");
				approvalProcess.setOrderBy(approvalProcessSet.getOrderBy()+1);
				List<ApprovalProcess> list = approvalProcessService.list(Wrappers.query(approvalProcess).isNull("DEL_NAM"));
				List<ApprovalProcessUser> userList = approvalProcessUserService.list(Wrappers.query(new ApprovalProcessUser())
						.eq("PROCESS_FID", list.get(0).getFId())
						.eq("USERNAME", approvalProcessSet.getUsername())
						.isNull("DEL_NAM"));
				ApprovalProcessSet approvalProcessSet2 = new ApprovalProcessSet();
				approvalProcessSet2.setProcessNo("2");
				approvalProcessSet2.setProcessFid(list.get(0).getFId());
				approvalProcessSet2.setOrderBy(list.get(0).getOrderBy());
				approvalProcessSet2.setLinkNam(list.get(0).getLinkNam());
				approvalProcessSet2.setRoleNam(list.get(0).getRoleNam());
				approvalProcessSet2.setBillFid(approvalProcessSet.getBillFid());
				approvalProcessSet2.setUserId(userList.get(0).getUserId());
				approvalProcessSet2.setUsername(userList.get(0).getUsername());
				approvalProcessSet2.setManName(userList.get(0).getManName());
				approvalProcessSet2.setIsAgree("0");
				approvalProcessSet2.setOperNam(SecurityUtils.getUser().getUsername());
				approvalProcessSet2.setRecordTim(new Date());
				approvalProcessSetService.save(approvalProcessSet2);
			}
		}else if("2".equals(approvalProcessSet.getIsAgree())){
			updateIoyard(approvalProcessSet);
		}

		ApprovalRecord approvalRecord = new ApprovalRecord();
		approvalRecord.setPFid(f_id.getProcessFid());
		approvalRecord.setCFid(f_id.getBillFid());
		approvalRecord.setProcessCode("2");
		approvalRecord.setOrderBy(f_id.getOrderBy());
		approvalRecord.setLinkNam(f_id.getLinkNam());
		approvalRecord.setOpinion(approvalProcessSet.getOpinion());
		approvalRecord.setOpinionExplain(approvalProcessSet.getOpinionExplain());
		approvalRecord.setIsAgree(f_id.getIsAgree());
		approvalRecord.setApprover(f_id.getApprover());
		approvalRecord.setApprovalTim(new Date());
		approvalRecord.setApproverName(f_id.getManName());
		return R.ok(approvalRecordService.save(approvalRecord));
	}

	public void saveLocationDate(Ioyard ioyard){
		YardInfo yardInfo = new YardInfo();
		yardInfo.setInYardNo(ioyard.getInoutYardNo()+"");
		yardInfo.setConsignNam(ioyard.getConsignNam());
		yardInfo.setCargoNam(ioyard.getCargoNam());
		yardInfo.setShipNam(ioyard.getShipNam());
		yardInfo.setInPiecesNum(ioyard.getPiecesNum());
		yardInfo.setInWeightWgt(ioyard.getWeightWgt());
		yardInfo.setInYardDat(ioyard.getIoyardDte());
		yardInfo.setOutPiecesNum(0);
		yardInfo.setOutWeightWgt(0.0);
		yardInfo.setSurplusPiecesNum(ioyard.getPiecesNum());
		yardInfo.setSurplusWeightWgt(ioyard.getWeightWgt());
		yardInfo.setYardNam(ioyard.getYardNo());
		yardInfo.setOperNam(SecurityUtils.getUser().getUsername());
		yardInfo.setRecordTim(new Date());
		yardInfo.setBh(ioyard.getIoyardNo());
		yardInfoService.save(yardInfo);
	}

	String updateLocationDate(Ioyard ioyard){
		YardInfo yardInfo = new YardInfo();
		yardInfo.setInYardNo(ioyard.getInoutYardNo()+"");
		List<YardInfo> list = yardInfoService.list(Wrappers.query(yardInfo));

		int lastPie =0;
		Double lastWei = 0.0;
		int nowOutPie = 0;
		Double nowOutWei = 0.0;
		DecimalFormat df = new DecimalFormat("#.##");
		if(!list.isEmpty()){
			lastPie = list.get(0).getSurplusPiecesNum()-ioyard.getPiecesNum();
			if(lastPie <0){
				return "超出当前件数，请核对后重新操作";
			}
			lastWei = list.get(0).getSurplusWeightWgt()-ioyard.getWeightWgt();

			lastWei = Double.parseDouble(df.format(lastWei));
			if(lastWei <0.0){
				return "超出当前库存量，请核对后重新操作";
			}
			nowOutPie = list.get(0).getOutPiecesNum();
			nowOutWei = list.get(0).getOutWeightWgt();
		}else{
			return "noMap";
		}
		System.out.println(lastWei);
		yardInfo.setSurplusPiecesNum(lastPie);
		yardInfo.setSurplusWeightWgt(lastWei);
		yardInfo.setOutPiecesNum(nowOutPie+ioyard.getPiecesNum());
		nowOutWei = Double.parseDouble(df.format(nowOutWei+ioyard.getWeightWgt()));

		yardInfo.setOutWeightWgt(nowOutWei);
		yardInfo.setUpdateNam(SecurityUtils.getUser().getUsername());
		yardInfo.setUpdateTim(new Date());
		boolean updateRes = yardInfoService.update(yardInfo, Wrappers.query(new YardInfo()).eq("IN_YARD_NO", ioyard.getInoutYardNo()));
		return updateRes+"";
	}

	void updateIoyard(ApprovalProcessSet approvalProcessSet){
		Ioyard ioyard = new Ioyard();
		ioyard.setInoutYardNo(approvalProcessSet.getBillFid()+"");
		if("1".equals(approvalProcessSet.getIsAgree())){
//			ioyard.setSurplusPieceNum(0);
//			ioyard.setSurplusWeight(new BigDecimal(0+""));
			ioyard.setOverShortFlag("2");
		}else if("2".equals(approvalProcessSet.getIsAgree())){
			ioyard.setOverShortFlag("3");
		}
		ioyardService.updateById(ioyard);
	}
}
