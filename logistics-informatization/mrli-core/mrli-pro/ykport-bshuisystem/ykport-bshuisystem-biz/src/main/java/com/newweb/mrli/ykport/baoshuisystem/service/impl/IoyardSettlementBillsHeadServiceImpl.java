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

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.*;
import com.newweb.mrli.ykport.baoshuisystem.mapper.InoutYardBodyMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.IoyardSettlementBillsHeadMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.ParameterListMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.IoyardSettlementBillsHeadService;
import com.newweb.mrli.ykport.baoshuisystem.service.SettlementBodyRateService;
import com.newweb.mrli.ykport.baoshuisystem.service.SettlementHeadRateService;
import com.newweb.mrli.ykport.baoshuisystem.vo.ExternalPaymentVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.IoyardListHeadBodyVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.IoyardSettlementBilldHeadBodyVo;
import lombok.val;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 出入库结算单头表
 *
 * @author ly
 * @date 2021-09-14 18:43:46
 */
@Service
public class IoyardSettlementBillsHeadServiceImpl extends ServiceImpl<IoyardSettlementBillsHeadMapper, IoyardSettlementBillsHead> implements IoyardSettlementBillsHeadService {

	@Autowired
	private ParameterListMapper parameterListMapper;
	@Autowired
	private SettlementBodyRateService settlementBodyRateService;

	@Override
	public IPage<ExternalPaymentVo> selectPageVo(Page page, IoyardSettlementBilldHeadBodyVo ioYardVo) {
		return baseMapper.selectPageVo(page, ioYardVo);
	}

	@Override
	public List<IoyardListHeadBodyVo> getCodeInoutYard(IoyardListHead ioyardListHead) {
		return baseMapper.getCodeInoutYard(ioyardListHead);
	}

	@Override
	public void exportById(IoyardSettlementBilldHeadBodyVo ioYardVo, HttpServletResponse response) throws IOException {
		// 获取标题名称
		String fileName = "保税货物出入库结算单";
		ParameterList parameter = parameterListMapper.selectValueByCode("ioyard_statement_name");
		if (parameter != null && !StringUtils.isEmpty(parameter.getParamValue())) {
			fileName = parameter.getParamValue();
		}
		// 告诉浏览器用什么软件打开此文件
//		response.setHeader("content-Type","application/vnd.ms-excel");
//		// 下载文件的默认名称
//		response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));
		ClassPathResource classPathResource = new ClassPathResource("/file/ioyard_settlement_bills_head.xlsx");
		InputStream fileInput = classPathResource.getInputStream();
		OutputStream output = null;
		//创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wb = new XSSFWorkbook(fileInput);
		XSSFCellStyle xssfCell = wb.createCellStyle();
		xssfCell.setAlignment(HorizontalAlignment.CENTER);
		xssfCell.setVerticalAlignment(VerticalAlignment.CENTER);
		//设置样式对象，这里仅设置了边框属性
		xssfCell.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		xssfCell.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		xssfCell.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		xssfCell.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		XSSFSheet sheet = wb.getSheetAt(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		sheet.getRow(2).getCell(1).setCellValue(ioYardVo.getSecondNam());
		sheet.getRow(2).getCell(4).setCellValue(ioYardVo.getIoyardListNo());
		sheet.getRow(2).getCell(7).setCellValue(ioYardVo.getInShipName());
		sheet.getRow(2).getCell(9).setCellValue(ioYardVo.getContNo());
		sheet.getRow(3).getCell(1).setCellValue(ioYardVo.getDept());
		sheet.getRow(3).getCell(4).setCellValue(ioYardVo.getCargoNam());
		sheet.getRow(3).getCell(7).setCellValue(ioYardVo.getOutShipName());
		sheet.getRow(3).getCell(9).setCellValue(ioYardVo.getBillCod());

		List<IoyardSettlementBillsBody> ioYardSBBodyList = ioYardVo.getIoYardSBBodyList();
		List<SettlementBodyRate> list = new ArrayList<>();
		for (int i = 0; i < ioYardSBBodyList.size(); i++) {
			IoyardSettlementBillsBody ioyardSettlementBillsBody = ioYardSBBodyList.get(i);
			Long fId = ioyardSettlementBillsBody.getFId();
			QueryWrapper<SettlementBodyRate> wapper = new QueryWrapper<>();
			wapper.eq("R_FID", fId);
			list = settlementBodyRateService.list(wapper);
		}
		//创建行
		XSSFRow row = sheet.createRow(4);
		row.setHeight((short) 460);
		for (int i = 0; i < 9 + list.size(); i++) {
			XSSFCell cell = row.createCell(i);
			row.getCell(i).setCellStyle(xssfCell);
		}
		sheet.getRow(4).getCell(0).setCellValue("序号");
		sheet.getRow(4).getCell(1).setCellValue("入库日期");
		sheet.getRow(4).getCell(2).setCellValue("入库结算数量");
		sheet.getRow(4).getCell(3).setCellValue("出库日期");
		sheet.getRow(4).getCell(4).setCellValue("出库结算数量");
		sheet.getRow(4).getCell(5).setCellValue("天数");
		sheet.getRow(4).getCell(6).setCellValue("吨天数");
		int index = 0;
		for (int i = 0; i < list.size(); i++) {
			sheet.getRow(4).getCell(6 + i + 1).setCellValue(list.get(i).getFeeNam());
		}
		sheet.getRow(4).getCell(6 + list.size() + 1).setCellValue("合计");
		sheet.getRow(4).getCell(6 + list.size() + 2).setCellValue("备注");
		XSSFRow row2 = sheet.createRow(5);
		row2.setHeight((short) 460);
		for (int i = 0; i < 9 + list.size(); i++) {
			XSSFCell cell2 = row2.createCell(i);
			row2.getCell(i).setCellStyle(xssfCell);
		}
		//入库结算数量
		BigDecimal angle = BigDecimal.ZERO;
		//出库结算数量
		BigDecimal brixton = BigDecimal.ZERO;
		//吨天数
		BigDecimal camden = BigDecimal.ZERO;
		BigDecimal dalston = BigDecimal.ZERO;
		//合计
		BigDecimal finchley = BigDecimal.ZERO;
		for (int i = 0; i < ioYardSBBodyList.size(); i++) {
			IoyardSettlementBillsBody iBody = ioYardSBBodyList.get(i);
			sheet.getRow(5 + i).getCell(0).setCellValue(i + 1);
			sheet.getRow(5 + i).getCell(1).setCellValue(sdf.format(iBody.getInYardDat()));
			sheet.getRow(5 + i).getCell(2).setCellValue(String.valueOf(iBody.getInSettleWgt()));
			sheet.getRow(5 + i).getCell(3).setCellValue(sdf.format(iBody.getOutYardDat()));
			sheet.getRow(5 + i).getCell(4).setCellValue(String.valueOf(iBody.getOutSettleWgt()));
			sheet.getRow(5 + i).getCell(5).setCellValue(String.valueOf(iBody.getDayNum()));
			sheet.getRow(5 + i).getCell(6).setCellValue(String.valueOf(iBody.getTonDayNum()));

			index++;
			if (index < ioYardSBBodyList.size()) {
				sheet.shiftRows(5 + i, 6 + i, 1, true, false);
				sheet.copyRows(6 + i, 6 + i, 5 + i, new CellCopyPolicy());
			}
			angle = angle.add(iBody.getInSettleWgt());
			brixton = brixton.add(iBody.getOutSettleWgt());
			camden = camden.add(iBody.getTonDayNum());

		}
		for (int i = 0; i < ioYardSBBodyList.size(); i++) {
			IoyardSettlementBillsBody ioyardSettlementBillsBody = ioYardSBBodyList.get(i);
			Long fId = ioyardSettlementBillsBody.getFId();
			QueryWrapper<SettlementBodyRate> wapper = new QueryWrapper<>();
			wapper.eq("R_FID", fId);
			list = settlementBodyRateService.list(wapper);
			Double money = 0.00;
			for (int j = 0; j < list.size(); j++) {
				SettlementBodyRate settlementBodyRate = list.get(j);
				money += settlementBodyRate.getMoney();
//				sheet.getRow(5+i).getCell(6+j+1).setCellValue(settlementBodyRate.getFeeRat());
				sheet.getRow(5 + i).getCell(6 + j + 1).setCellValue(settlementBodyRate.getMoney());
				dalston =dalston.add(BigDecimal.valueOf(settlementBodyRate.getMoney()));
			}
			sheet.getRow(5 + i).getCell(7 + list.size() + 1).setCellValue(ioyardSettlementBillsBody.getMarkTxt() == null ? "" : ioyardSettlementBillsBody.getMarkTxt());

			sheet.getRow(5 + i).getCell(6 + list.size() + 1).setCellValue(money);
			finchley = finchley.add(BigDecimal.valueOf(money));
		}

		XSSFRow row3 = sheet.createRow(5 + ioYardSBBodyList.size());
		row3.setHeight((short) 460);
		for (int i = 0; i < 9 + list.size(); i++) {
			XSSFCell cell3 = row3.createCell(i);
			row3.getCell(i).setCellStyle(xssfCell);
		}


		row3.getCell(0).setCellValue("小计");
		sheet.addMergedRegion(new CellRangeAddress(5 + ioYardSBBodyList.size(), 5 + ioYardSBBodyList.size(), 0, 1));
		sheet.getRow(5 + ioYardSBBodyList.size()).getCell(2).setCellValue(String.valueOf(angle));
		sheet.getRow(5+ ioYardSBBodyList.size()).getCell(4).setCellValue(String.valueOf(brixton));
		sheet.getRow(5 + ioYardSBBodyList.size()).getCell(6).setCellValue(String.valueOf(camden));
//		List<SettlementBodyRate> list2 = new ArrayList<>();
//		sheet.getRow(5 + ioYardSBBodyList.size()).getCell(6).getCellValue();
		for (int j = 0; j < list.size(); j++) {
			Double d =0.00;
			for (int i = 0; i <ioYardSBBodyList.size() ; i++) {
				String cellValue = getCellValue(sheet.getRow(4 + i + 1).getCell(6 + j + 1));
				d +=  Double.parseDouble(cellValue);
			}
			sheet.getRow(5 + ioYardSBBodyList.size()).getCell(6+1+j).setCellValue(d);
		}

//		sheet.getRow(5 + ioYardSBBodyList.size()).getCell(6+i+1).setCellValue();
		sheet.getRow(5 + ioYardSBBodyList.size()).getCell(6 + list.size() + 1).setCellValue(finchley.setScale(2).doubleValue());

//
//		for (int i = 0; i < ioYardSBBodyList.size(); i++) {
//			IoyardSettlementBillsBody iBody = ioYardSBBodyList.get(i);
//			sheet.getRow(6 + i).getCell(0).setCellValue(i+1);
//			sheet.getRow(6 + i).getCell(1).setCellValue(sdf.format(iBody.getInYardDat()));
//			sheet.getRow(6 + i).getCell(2).setCellValue(String.valueOf(iBody.getInSettleWgt()));
//			sheet.getRow(6 + i).getCell(3).setCellValue(sdf.format(iBody.getOutYardDat()));
//			sheet.getRow(6 + i).getCell(4).setCellValue(String.valueOf(iBody.getOutSettleWgt()));
//			sheet.getRow(6 + i).getCell(5).setCellValue(String.valueOf(iBody.getDayNum()));
//			sheet.getRow(6 + i).getCell(6).setCellValue(String.valueOf(iBody.getTonDayNum()));
//			sheet.getRow(6 + i).getCell(7).setCellValue(String.valueOf(iBody.getManagementMoney()));
//			sheet.getRow(6 + i).getCell(8).setCellValue(String.valueOf(iBody.getStorageMoney()));
//			sheet.getRow(6 + i).getCell(9).setCellValue(String.valueOf(iBody.getTotalMoney()));
//
//			if(iBody.getMarkTxt() != null){
//				sheet.getRow(6 + i).getCell(10).setCellValue(String.valueOf(iBody.getMarkTxt()));
//			}else {
//				sheet.getRow(6 + i).getCell(10).setCellValue("");
//			}
//
//
//			index++;
//			if (index <ioYardSBBodyList.size()) {
//				sheet.shiftRows(6+i, 7+i, 1,true,false);
//				sheet.copyRows(7+i,7+i, 6+i,new CellCopyPolicy());
//			}
//			angle = angle.add(iBody.getInSettleWgt());
//			brixton = brixton.add(iBody.getOutSettleWgt());
//			camden = camden.add(iBody.getTonDayNum());
//			dalston = dalston.add(iBody.getManagementMoney());
//			edgware = edgware.add(iBody.getStorageMoney());
//			finchley = finchley.add(iBody.getTotalMoney());
//		}
//
//		//创建一行小计
//		XSSFRow row2 = sheet.createRow(6 + ioYardSBBodyList.size());
//		row.setHeight((short) 250);
//		for (int i = 0; i <11; i++) {
//			XSSFCell cell = row2.createCell(i);
//			row2.getCell(i).setCellStyle(xssfCell);
//		}
//		sheet.getRow(6 + ioYardSBBodyList.size()).getCell(0).setCellValue("小计");
//		sheet.addMergedRegion(new CellRangeAddress(6 + ioYardSBBodyList.size(),6 + ioYardSBBodyList.size(),0,1));
//		sheet.getRow(6 + ioYardSBBodyList.size()).getCell(2).setCellValue(String.valueOf(angle));
//		sheet.getRow(6 + ioYardSBBodyList.size()).getCell(4).setCellValue(String.valueOf(brixton));
//		sheet.getRow(6 + ioYardSBBodyList.size()).getCell(6).setCellValue(String.valueOf(camden));
//		sheet.getRow(6+ ioYardSBBodyList.size()).getCell(7).setCellValue(String.valueOf(dalston));
//		sheet.getRow(6 + ioYardSBBodyList.size()).getCell(8).setCellValue(String.valueOf(edgware));
//		sheet.getRow(6 + ioYardSBBodyList.size()).getCell(9).setCellValue(String.valueOf(finchley));
		XSSFRow row6 = sheet.createRow(8 + ioYardSBBodyList.size());
		row6.setHeight((short) 300);
		for (int i = 0; i <11; i++) {
			XSSFCell cell = row6.createCell(i);

		}
		sheet.getRow(8 + ioYardSBBodyList.size()).getCell(0).setCellValue("备注:");
		sheet.getRow(8 + ioYardSBBodyList.size()).getCell(1).setCellValue(ioYardVo.getMarkTxt());

		XSSFRow row7 = sheet.createRow(10 + ioYardSBBodyList.size());
		row7.setHeight((short) 300);
		for (int i = 0; i <11; i++) {
			XSSFCell cell = row7.createCell(i);
		}
		sheet.getRow(10 + ioYardSBBodyList.size()).getCell(0).setCellValue("制表人:");
		sheet.getRow(10 + ioYardSBBodyList.size()).getCell(3).setCellValue("审核人:");
		XSSFRow row5 = sheet.createRow(12 + ioYardSBBodyList.size());
		row5.setHeight((short) 330);
		for (int i = 0; i <11; i++) {
			XSSFCell cell = row5.createCell(i);
		}
		sheet.getRow(12 + ioYardSBBodyList.size()).getCell(6).setCellValue("制表时间:");
		String format = sdf.format(ioYardVo.getOperDat());
		sheet.getRow(12 + ioYardSBBodyList.size()).getCell(7).setCellValue(format.substring(0,4)+"年"+format.substring(5,7)+"月"+format.substring(8,10)+"日");
		sheet.addMergedRegion(new CellRangeAddress(12 + ioYardSBBodyList.size(),12 + ioYardSBBodyList.size(),7,8));

		output = response.getOutputStream();
		wb.write(output);
		output.close();
		fileInput.close();
		wb.close();
	}

	@Override
	public List<String> selectBillCod() {
		return baseMapper.selectBillCod();
	}

	@Override
	public List<IoyardSettlementBilldHeadBodyVo> selectInoutBill() {
		return baseMapper.selectInoutBill();
	}



	public static String getCellValue(Cell cell) {
		String cellValue = "";
		// 以下是判断数据的类型
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC: // 数字
				if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellValue = sdf.format(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue())).toString();
				} else {
					DataFormatter dataFormatter = new DataFormatter();
					cellValue = dataFormatter.formatCellValue(cell);
				}
				break;
			case Cell.CELL_TYPE_STRING: // 字符串
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN: // Boolean
				cellValue = cell.getBooleanCellValue() + "";
				break;
			case Cell.CELL_TYPE_FORMULA: // 公式
				cellValue = cell.getCellFormula() + "";
				break;
			case Cell.CELL_TYPE_BLANK: // 空值
				cellValue = "";
				break;
			case Cell.CELL_TYPE_ERROR: // 故障
				cellValue = "非法字符";
				break;
			default:
				cellValue = "未知类型";
				break;
		}
		return cellValue;
	}
}
