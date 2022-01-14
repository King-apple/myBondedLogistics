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

import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardHead;
import com.newweb.mrli.ykport.baoshuisystem.entity.SettlementContractLedger;
import com.newweb.mrli.ykport.baoshuisystem.mapper.ContractMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.InoutYardBodyMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.InoutYardHeadMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.SettlementContractLedgerMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.InoutYardBodyService;
import com.newweb.mrli.ykport.baoshuisystem.service.SettlementContractLedgerService;
import com.newweb.mrli.ykport.baoshuisystem.vo.ContractFileVo;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import io.swagger.models.auth.In;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * 保税业务合同结算台账
 *
 * @author ly
 * @date 2021-12-14 10:25:42
 */
@Service
public class SettlementContractLedgerServiceImpl extends ServiceImpl<SettlementContractLedgerMapper, SettlementContractLedger> implements SettlementContractLedgerService {
	@Autowired
	private ContractMapper contractMapper;
	@Autowired
	private InoutYardHeadMapper inoutYardHeadMapper;
	@Autowired
	private InoutYardBodyMapper inoutYardBodyMapper;
	@Autowired
	private InoutYardBodyService inoutYardBodyService;
	@Override
	public void export(List<ContractFileVo> contract, HttpServletResponse response) throws IOException {

		List<SettlementContractLedger> list = new ArrayList<>();
		for (int i = 0; i < contract.size() ; i++) {
			List<SettlementContractLedger> settlementContractLedger = contractMapper.getAllInfo(contract.get(i).getContNo());
			//当长度大于1的时候，把所有为空的都赋值
			if(settlementContractLedger.size()>1){
				for (int j = 1; j <settlementContractLedger.size() ; j++) {
					SettlementContractLedger temp = settlementContractLedger.get(0);
					settlementContractLedger.get(j).setContNo(temp.getContNo());
					settlementContractLedger.get(j).setDept(temp.getDept());
					settlementContractLedger.get(j).setContractType(temp.getContractType());
					settlementContractLedger.get(j).setSecondNam(temp.getSecondNam());
					settlementContractLedger.get(j).setFirstNam(temp.getFirstNam());
					settlementContractLedger.get(j).setShipNam(temp.getShipNam());
					settlementContractLedger.get(j).setCargoKindNam(temp.getCargoKindNam());
					settlementContractLedger.get(j).setCargoNam(temp.getCargoNam());
					settlementContractLedger.get(j).setCargoNum(temp.getCargoNum());
					settlementContractLedger.get(j).setCargoFee(temp.getCargoFee());
					settlementContractLedger.get(j).setSettlementBasis(temp.getSettlementBasis());
					settlementContractLedger.get(j).setStartDte(temp.getStartDte());
					settlementContractLedger.get(j).setEndDte(temp.getEndDte());
					settlementContractLedger.get(j).setOperator(temp.getOperator());
					settlementContractLedger.get(j).setFeeNam(temp.getFeeNam());
					settlementContractLedger.get(j).setFeeRat(temp.getFeeRat());
				}
				//取出所有计算客余额和利润放在第一行，每个合同只有一个
				//收款金额
				double sumMoneyCollection = settlementContractLedger.stream().mapToDouble(x -> x.getMoneyCollection() == null ? 0 :x.getMoneyCollection() ).sum();
				//结算金额
				double sumMoneySettlement = settlementContractLedger.stream().mapToDouble(x -> x.getMoneySettlement() == null ? 0 :x.getMoneySettlement() ).sum();
				//返款金额
				double MoneyRebates = settlementContractLedger.stream().mapToDouble(x -> x.getMoneyRebates() == null ? 0 :x.getMoneyRebates() ).sum();
				double balanced = sumMoneyCollection-sumMoneySettlement-MoneyRebates;
				settlementContractLedger.get(0).setBalance(balanced);
				settlementContractLedger.get(0).setProfit(sumMoneySettlement/1.06);
			}

			list.addAll(settlementContractLedger);

		}
		SettlementContractLedger s = new SettlementContractLedger();
		List<SettlementContractLedger> addList = new ArrayList();
		Map<String, List<SettlementContractLedger>> collect = list.stream().collect(groupingBy(SettlementContractLedger::getContNo));
		collect.forEach((k,v) ->{
			v.add(s);
		});
		Collection<List<SettlementContractLedger>> collect1 = collect.values().stream().collect(Collectors.toList());
		for (int i = 0; i <collect1.size() ; i++) {
			List<List<SettlementContractLedger>> collect2 = collect1.stream().collect(Collectors.toList());
			for (int j = 0; j <collect2.get(i).size() ; j++) {
				addList.add(collect2.get(i).get(j));
			}
		}


		ClassPathResource classPathResource = new ClassPathResource("/file/settlement_contract_ledger.xlsx");
		InputStream fileInput = classPathResource.getInputStream();
		OutputStream output = null;
		//创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wb = new XSSFWorkbook(fileInput);
		//对应Excel表中的sheet,0代表第一个
		XSSFSheet sheet = wb.getSheetAt(0);
		int index = 0;
		int flag = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		for (int i = 0; i < addList.size(); i++) {
			sheet.getRow(3+i).getCell(0).setCellValue(i+1);
			sheet.getRow(3+i).getCell(1).setCellValue(addList.get(i).getContractType()==null? " ":addList.get(i).getContractType());
			sheet.getRow(3+i).getCell(2).setCellValue(addList.get(i).getDept()==null? " ":addList.get(i).getDept());
			sheet.getRow(3+i).getCell(3).setCellValue(addList.get(i).getContNo()==null? " ":addList.get(i).getContNo());
			sheet.getRow(3+i).getCell(4).setCellValue(addList.get(i).getFirstNam()==null? " ":addList.get(i).getFirstNam());
			sheet.getRow(3+i).getCell(5).setCellValue(addList.get(i).getSecondNam()==null? " ":addList.get(i).getSecondNam());
			sheet.getRow(3+i).getCell(6).setCellValue(addList.get(i).getShipNam()==null? " ":addList.get(i).getShipNam());
			sheet.getRow(3+i).getCell(7).setCellValue(addList.get(i).getCargoKindNam()==null? " ":addList.get(i).getCargoKindNam());
			sheet.getRow(3+i).getCell(8).setCellValue(addList.get(i).getCargoNam()==null? " ":addList.get(i).getCargoNam());
			sheet.getRow(3+i).getCell(9).setCellValue(addList.get(i).getFeeNam()==null? " ":addList.get(i).getFeeNam());
			sheet.getRow(3+i).getCell(10).setCellValue(addList.get(i).getFeeRat()==null? " ":addList.get(i).getFeeRat());
			sheet.getRow(3+i).getCell(11).setCellValue(addList.get(i).getCargoNum()==null? " ":addList.get(i).getCargoNum().toString());
			sheet.getRow(3+i).getCell(12).setCellValue(addList.get(i).getCargoFee()==null? " ":addList.get(i).getCargoFee().toString());
			sheet.getRow(3+i).getCell(13).setCellValue(addList.get(i).getSettlementBasis()==null? " ":addList.get(i).getSettlementBasis());
			if(addList.get(i).getStartDte() != null){
				sheet.getRow(3+i).getCell(14).setCellValue(sdf.format(addList.get(i).getStartDte()==null? " ":addList.get(i).getStartDte()));
			}else {
				sheet.getRow(3+i).getCell(14).setCellValue("");
			}
			if(addList.get(i).getEndDte()!=null){
				sheet.getRow(3+i).getCell(15).setCellValue(sdf.format(addList.get(i).getEndDte()==null? " ":addList.get(i).getEndDte()));
			}
			else {
				sheet.getRow(3+i).getCell(15).setCellValue("");
			}
			sheet.getRow(3+i).getCell(16).setCellValue(addList.get(i).getOperator()==null? " ":addList.get(i).getOperator());
			sheet.getRow(3+i).getCell(17).setCellValue(addList.get(i).getInYardNo()==null? " ":addList.get(i).getInYardNo());
			if(addList.get(i).getInRecordTim()!= null) {
				sheet.getRow(3+i).getCell(18).setCellValue(sdf.format(addList.get(i).getInRecordTim())==null? " ":sdf.format(addList.get(i).getInRecordTim()));
			}else {
				sheet.getRow(3+i).getCell(18).setCellValue("");
			}
			//19入库毛重
			if(StringUtils.isNotEmpty(addList.get(i).getInYardNo())){
				QueryWrapper<InoutYardHead>queryWrapper = new QueryWrapper<>();
				queryWrapper.eq("inout_yard_no",addList.get(i).getInYardNo());
				queryWrapper.isNull("del_nam");
				InoutYardHead inoutYardHeads = inoutYardHeadMapper.selectOne(queryWrapper);
				QueryWrapper<InoutYardBody> queryWrapper1 = new QueryWrapper<>();
				queryWrapper1.eq("IN_OUT",1);
				queryWrapper1.isNull("del_nam");
				queryWrapper1.eq("h_fid",inoutYardHeads.getFId());
				queryWrapper1.select(
						"SUM(rough_wet) as rough_wet"
				);
				Map<String, Object> map = inoutYardBodyService.getMap(queryWrapper1);
				if(map != null){
					BigDecimal sumCount = (BigDecimal) map.get( "ROUGH_WET");
					if(StringUtils.isNotBlank(String.valueOf(sumCount))){
						sheet.getRow(3+i).getCell(19).setCellValue(sumCount.toString() == null ? "" : sumCount.toString());
					}else {
						sheet.getRow(3+i).getCell(19).setCellValue("");
					}

				}

			}else {
				sheet.getRow(3+i).getCell(19).setCellValue("");
			}

			sheet.getRow(3+i).getCell(20).setCellValue(addList.get(i).getOutYardNo()==null? " ":addList.get(i).getOutYardNo());
			if(addList.get(i).getOutRecordTim()!= null) {
				sheet.getRow(3+i).getCell(21).setCellValue(sdf.format(addList.get(i).getOutRecordTim())==null? " ":sdf.format(addList.get(i).getOutRecordTim()));
			}else {
				sheet.getRow(3+i).getCell(21).setCellValue("");
			}
			//22出库毛重
			if(StringUtils.isNotEmpty(addList.get(i).getOutYardNo())){
				QueryWrapper<InoutYardHead>queryWrapper = new QueryWrapper<>();
				queryWrapper.eq("inout_yard_no",addList.get(i).getOutYardNo());
				queryWrapper.isNull("del_nam");
				InoutYardHead inoutYardHeads = inoutYardHeadMapper.selectOne(queryWrapper);
				QueryWrapper<InoutYardBody> queryWrapper1 = new QueryWrapper<>();
				queryWrapper1.eq("IN_OUT",0);
				queryWrapper1.isNull("del_nam");
				queryWrapper1.eq("h_fid",inoutYardHeads.getFId());
				queryWrapper1.select(
						"SUM(rough_wet) as rough_wet"
				);
				Map<String, Object> map = inoutYardBodyService.getMap(queryWrapper1);
				if(map!=null){
					BigDecimal sumCount = (BigDecimal) map.get("ROUGH_WET");
					if(StringUtils.isNotBlank(String.valueOf(sumCount))){
						sheet.getRow(3+i).getCell(22).setCellValue(sumCount.toString() == null ? "" : sumCount.toString());
					}else {
						sheet.getRow(3+i).getCell(22).setCellValue("");
					}

				}

			}else {
				sheet.getRow(3+i).getCell(22).setCellValue("");
			}
			sheet.getRow(3+i).getCell(23).setCellValue(addList.get(i).getIoyardListNo()==null? " ":addList.get(i).getIoyardListNo());
			if(addList.get(i).getInYardDat() !=  null) {
				sheet.getRow(3+i).getCell(24).setCellValue(sdf.format(addList.get(i).getInYardDat())==null? " ":sdf.format(addList.get(i).getInYardDat()));
			}else{
				sheet.getRow(3+i).getCell(24).setCellValue("");
			}

			if(addList.get(i).getOutYardDat() !=  null) {
				sheet.getRow(3+i).getCell(26).setCellValue(sdf.format(addList.get(i).getOutYardDat())==null? " ":sdf.format(addList.get(i).getOutYardDat()));
			}else{
				sheet.getRow(3+i).getCell(26).setCellValue("");
			}
			if(addList.get(i).getInWeightWgt() != null){
				sheet.getRow(3+i).getCell(25).setCellValue(addList.get(i).getInWeightWgt().toString()==null? " ":addList.get(i).getInWeightWgt().toString());
			}else{
				sheet.getRow(3+i).getCell(25).setCellValue("");
			}
			if(addList.get(i).getOutWeightWgt() != null){
				sheet.getRow(3+i).getCell(27).setCellValue(addList.get(i).getOutWeightWgt().toString()==null? " ":addList.get(i).getOutWeightWgt().toString());
			}else{
				sheet.getRow(3+i).getCell(27).setCellValue("");
			}
			if(addList.get(i).getBillDatCollection() != null){
				sheet.getRow(3+i).getCell(28).setCellValue(sdf.format(addList.get(i).getBillDatCollection())==null? " ":sdf.format(addList.get(i).getBillDatCollection()));
			}else{
				sheet.getRow(3+i).getCell(28).setCellValue("");
			}
			sheet.getRow(3+i).getCell(29).setCellValue(addList.get(i).getBillCodCollection()==null? " ":addList.get(i).getBillCodCollection());
			if(addList.get(i).getMoneyCollection() != null){
				sheet.getRow(3+i).getCell(30).setCellValue(addList.get(i).getMoneyCollection().toString()==null? " ":addList.get(i).getMoneyCollection().toString());
			}else{
				sheet.getRow(3+i).getCell(30).setCellValue("");
			}
			sheet.getRow(3+i).getCell(31).setCellValue(addList.get(i).getBillCodSettlement()==null? " ":addList.get(i).getBillCodSettlement());
			if(addList.get(i).getBillDatSettlement()!= null){
				sheet.getRow(3+i).getCell(32).setCellValue(sdf.format(addList.get(i).getBillDatSettlement())==null? " ":sdf.format(addList.get(i).getBillDatSettlement()));
			}else{
				sheet.getRow(3+i).getCell(32).setCellValue("");
			}
			if(addList.get(i).getMoneySettlement() != null){
				sheet.getRow(3+i).getCell(33).setCellValue(addList.get(i).getMoneySettlement().toString()==null? " ":addList.get(i).getMoneySettlement().toString());
			}else {
				sheet.getRow(3+i).getCell(33).setCellValue("");
			}
			if(addList.get(i).getMoneyRebates() != null){
				sheet.getRow(3+i).getCell(34).setCellValue(addList.get(i).getMoneyRebates().toString()==null? " ":addList.get(i).getMoneyRebates().toString());
			}else {
				sheet.getRow(3+i).getCell(34).setCellValue("");
			}
			if (addList.get(i).getBalance()!= null){
				sheet.getRow(3+i).getCell(35).setCellValue(addList.get(i).getBalance().toString() == null? "" :addList.get(i).getBalance().toString());
			}else {
				sheet.getRow(3+i).getCell(35).setCellValue("");
			}
			if (addList.get(i).getProfit() != null) {
				java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.##");
				sheet.getRow(3+i).getCell(36).setCellValue(addList.get(i).getProfit().toString() == null? "" :df.format(addList.get(i).getProfit()) );
			}
			else {
				sheet.getRow(3+i).getCell(36).setCellValue("");
			}
			index++;
			if (index < addList.size()) {
				sheet.shiftRows(3+i, 4+i, 1,true,false);
				sheet.copyRows(4+i,4+i, 3+i,new CellCopyPolicy());
			}
			flag++;
			int inFlag = 0;
//				if(" ".equals(sheet.getRow(3+i).getCell(3).getStringCellValue())){
//				System.out.println("11111"+(flag-inFlag));
//				sheet.addMergedRegion(new CellRangeAddress(3+i, 3+i, 15, 16));
//				sheet.getRow(3+i).getCell(15).setCellValue("合计");
//				for (int j =0; j <flag-inFlag-1 ; j++) {
//					if(sheet.getRow(3+i+j-1).getCell(19).getStringCellValue()!= null){
//						String stringCellValue = sheet.getRow(3 + i + j - 1).getCell(19).getStringCellValue();
//						sheet.getRow(3+i+j).getCell(19).setCellValue(stringCellValue);
//					}
//				}
//				inFlag = flag;
//				}
		}



		output = response.getOutputStream();
		wb.write(output);
		output.close();
		fileInput.close();
		wb.close();

	}

	@Override
	public List<SettlementContractLedger> getListALL(String contNo) {
		List<SettlementContractLedger> allInfo = contractMapper.getAllInfo(contNo);
		return allInfo;
	}
}
