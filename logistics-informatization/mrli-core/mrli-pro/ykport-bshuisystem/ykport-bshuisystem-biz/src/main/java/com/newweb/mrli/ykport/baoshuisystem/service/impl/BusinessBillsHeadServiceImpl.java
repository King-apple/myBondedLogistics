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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.BusinessBillsBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.BusinessBillsHead;
import com.newweb.mrli.ykport.baoshuisystem.entity.ParameterList;
import com.newweb.mrli.ykport.baoshuisystem.entity.YkbfSingClass;
import com.newweb.mrli.ykport.baoshuisystem.mapper.BusinessBillsHeadMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.ParameterListMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.BusinessBillsBodyService;
import com.newweb.mrli.ykport.baoshuisystem.service.BusinessBillsHeadService;
import com.newweb.mrli.ykport.baoshuisystem.service.ParameterListService;
import com.newweb.mrli.ykport.baoshuisystem.utils.ExcelUtil;
import com.newweb.mrli.ykport.baoshuisystem.vo.ExternalPaymentVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.IncomeStatistcsVo;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 业务单据头表
 *
 * @date 2021-09-06 09:12:35
 */
@Service
public class BusinessBillsHeadServiceImpl extends ServiceImpl<BusinessBillsHeadMapper, BusinessBillsHead> implements BusinessBillsHeadService {

	@Autowired
	private BusinessBillsBodyService bodyService;
	@Autowired
	private ParameterListMapper parameterListMapper;
	@Autowired
	private BusinessBillsHeadMapper businessBillsHeadMapper;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ParameterListService parameterListService;

	@Override
	public IPage<ExternalPaymentVo> selectPageVo(Page page, ExternalPaymentVo externalPaymentVo) {
		return baseMapper.selectPageVo(page,externalPaymentVo);
	}

	@Override
	public void exportById(ExternalPaymentVo externalPaymentVo, HttpServletResponse response) throws IOException {
		List<BusinessBillsBody> list = externalPaymentVo.getBbBodyList();
		List<BusinessBillsBody> list1= new ArrayList<>();
		List<BusinessBillsBody> list2= new ArrayList<>();
		Double dueMoneyList1 =0.00;
		Double dueMoneyList2 =0.00;
		Double advinceMoney1 =0.00;
		Double advinceMoney2 =0.00;
		for (int i = 0; i <list.size() ; i++) {
			if("应付款".equals(list.get(i).getItemNam())){
				list2.add(list.get(i));
			}else {
				list1.add(list.get(i));
			}
		}
		//取得list1中所有的钱
		for (BusinessBillsBody  body :list1) {
			dueMoneyList1 +=body.getDueMoney().doubleValue();
			advinceMoney1 += body.getAdvinceMoney().doubleValue();
		}
		//取得list2中所有的钱
		for (BusinessBillsBody  body :list2) {
			dueMoneyList2 +=body.getDueMoney().doubleValue();
			advinceMoney2 += body.getAdvinceMoney().doubleValue();
		}
		// 获取标题名称
		String fileName = "对外付款审批单";
		ParameterList parameter = parameterListMapper.selectValueByCode("business_bills_head");
		if (parameter != null && !StringUtil.isEmpty(parameter.getParamValue())) {
			fileName = parameter.getParamValue();
		}
		// 告诉浏览器用什么软件打开此文件
		response.setHeader("content-Type","application/vnd.ms-excel");
		// 下载文件的默认名称
		response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));
		ClassPathResource classPathResource = new ClassPathResource("/file/business_bills_head.xlsx");
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

		//对应Excel表中的sheet,0代表第一个
		XSSFSheet sheet = wb.getSheetAt(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		sheet.getRow(0).getCell(0).setCellValue(fileName);// 标题行

		//第二行第1列填写部门 2B
		sheet.getRow(1).getCell(1).setCellValue(externalPaymentVo.getDept() == "" ? "" :externalPaymentVo.getDept() );
		//第二行第3列填写合同编号 2D
		sheet.getRow(1).getCell(3).setCellValue(externalPaymentVo.getBillCod()== "" ? "" :externalPaymentVo.getBillCod());
		//第二行第3列填写合日期 5I
		sheet.getRow(1).getCell(8).setCellValue(sdf.format(externalPaymentVo.getBillDate()));
		//第三行第2列填写合客户 3C
		sheet.getRow(2).getCell(2).setCellValue(externalPaymentVo.getSecondNam()== "" ? "" :externalPaymentVo.getSecondNam());
		//第四行第3列合同编号4C
		sheet.getRow(3).getCell(2).setCellValue(externalPaymentVo.getContCod() == "" ? "" :externalPaymentVo.getContCod());
		//第四行第8列船名字 4H
		sheet.getRow(3).getCell(7).setCellValue(externalPaymentVo.getInShipName() == ""?"":externalPaymentVo.getInShipName());
		sheet.getRow(3).getCell(9).setCellValue(externalPaymentVo.getOutShipName() == ""?"":externalPaymentVo.getOutShipName());
		//第五行第3列 货名 5C
		sheet.getRow(4).getCell(2).setCellValue(externalPaymentVo.getCargoNam() == "" ? "" :externalPaymentVo.getCargoNam());
		//第五行第8列 计费数量 5H
		sheet.getRow(4).getCell(7).setCellValue(String.valueOf(externalPaymentVo.getFeeQuantity()));

		// 处理数据
		int index = 0;
		for (int i = 0; i <list1.size() ; i++) {
			BusinessBillsBody bbBody = list1.get(i);
			sheet.getRow(6+i).getCell(2).setCellValue(bbBody.getFeeNam() == "" ? "" :bbBody.getFeeNam());
			//7行E列  费率
			sheet.getRow(6+i).getCell(4).setCellValue(bbBody.getFeeUnit()== "" ? "" :bbBody.getFeeUnit());
			//应收款
			sheet.getRow(6+i).getCell(5).setCellValue(String.valueOf(bbBody.getDueMoney()) == "" ? "" :String.valueOf(bbBody.getDueMoney()));
			//预收款
			sheet.getRow(6+i).getCell(7).setCellValue(String.valueOf(bbBody.getAdvinceMoney())== "" ? "" :String.valueOf(bbBody.getAdvinceMoney()));
			//备注
			sheet.getRow(6+i).getCell(9).setCellValue(bbBody.getMarkTxt() == "" ? "" :bbBody.getMarkTxt());
			index++;
			if (index < list1.size()) {
				sheet.shiftRows(6+i, 7+i, 1,true,false);
				sheet.copyRows(7+i,7+i, 6+i,new CellCopyPolicy());
			}

		}
		//创建小计行
		XSSFRow row = sheet.createRow(6 + list1.size());
		row.setHeight((short) 420);
		for (int i = 0; i <11 ; i++) {
			XSSFCell cell = row.createCell(i);
			row.getCell(i).setCellStyle(xssfCell);
		}
		sheet.getRow(6 + list1.size()).getCell(5).setCellValue(String.format("%.2f",dueMoneyList1));
		sheet.getRow(6 + list1.size()).getCell(7).setCellValue(String.format("%.2f",advinceMoney1));
		if(list1.size()>0){
			sheet.addMergedRegion(new CellRangeAddress(6 + list1.size(), 6 + list1.size(), 2, 4));
			sheet.addMergedRegion(new CellRangeAddress(6 + list1.size(), 6 + list1.size(), 5, 6));
			sheet.addMergedRegion(new CellRangeAddress(6 + list1.size(), 6 + list1.size(), 7, 8));
			sheet.addMergedRegion(new CellRangeAddress(6 + list1.size(), 6 + list1.size(), 9, 10));
			sheet.addMergedRegion(new CellRangeAddress(6, 6+ list1.size(), 0, 1));
		}else{
			sheet.addMergedRegion(new CellRangeAddress(6, 6+ list1.size(), 0, 1));
		}


		//应预收款
		sheet.getRow(6).getCell(0).setCellValue("应预收款");
		sheet.getRow(6).getCell(0).setCellStyle(xssfCell);
		row.getCell(2).setCellValue("小计");
		XSSFRow row2 = sheet.createRow(6 + list1.size()+1);
		row2.copyRowFrom(sheet.getRow(5),new CellCopyPolicy());
		XSSFRow row3 = sheet.createRow(8 + list1.size());
		int index2 = 0;
		//设置每一行样式
		for (int i = 0; i <11 ; i++) {
			XSSFCell cell = row3.createCell(i);
			row3.getCell(i).setCellStyle(xssfCell);
		}

		for (int i = 0; i <list2.size() ; i++) {
			BusinessBillsBody bbBody = list2.get(i);
			sheet.getRow(8 + list1.size()+i).getCell(2).setCellValue(bbBody.getFeeNam());
			sheet.getRow(8 + list1.size()+i).getCell(4).setCellValue(bbBody.getFeeUnit());
			//应收款
			sheet.getRow(8 + list1.size()+i).getCell(5).setCellValue(String.valueOf(bbBody.getDueMoney()));
			//预收款
			sheet.getRow(8 + list1.size()+i).getCell(7).setCellValue(String.valueOf(bbBody.getAdvinceMoney()));
			//备注
			sheet.getRow(8 + list1.size()+i).getCell(9).setCellValue(bbBody.getMarkTxt());
			index2++;
			if (index2 < list2.size()) {
				sheet.shiftRows(8 + list1.size()+i, 9 + list1.size()+i, 1,true,false);
				sheet.copyRows(9 + list1.size()+i,9 + list1.size()+i, 8 + list1.size()+i,new CellCopyPolicy());
			}
			sheet.addMergedRegion(new CellRangeAddress(8 + list1.size()+i, 8 + list1.size()+i, 2, 3));
			sheet.addMergedRegion(new CellRangeAddress(8 + list1.size()+i, 8 + list1.size()+i, 5, 6));
			sheet.addMergedRegion(new CellRangeAddress(8 + list1.size()+i, 8 + list1.size()+i, 7, 8));
			sheet.addMergedRegion(new CellRangeAddress(8 + list1.size()+i, 8 + list1.size()+i, 9, 10));
		}
//		//创建小计行
		XSSFRow row4 = sheet.createRow(8+ list1.size()+list2.size());
		row4.setHeight((short) 420);
		for (int i = 0; i <11 ; i++) {
			XSSFCell cell = row4.createCell(i);
			row4.getCell(i).setCellStyle(xssfCell);
		}
		row4.getCell(2).setCellValue("小计");
		if(sheet.getRow(8+list1.size())!= null){
			sheet.getRow(8+list1.size()).getCell(0).setCellValue("应预付款");
			sheet.getRow(7+list1.size()).getCell(5).setCellValue("应付款");
			sheet.getRow(7+list1.size()).getCell(7).setCellValue("预付款");
		}
		sheet.getRow(8 + list1.size()+list2.size()).getCell(5).setCellValue(String.format("%.2f",dueMoneyList2));
		sheet.getRow(8 + list1.size()+list2.size()).getCell(7).setCellValue(String.format("%.2f",advinceMoney2));
		sheet.addMergedRegion(new CellRangeAddress(8 + list1.size()+list2.size(), 8 + list1.size()+list2.size(), 2, 4));
		sheet.addMergedRegion(new CellRangeAddress(8 + list1.size()+list2.size(), 8 + list1.size()+list2.size(), 5, 6));
		sheet.addMergedRegion(new CellRangeAddress(8 + list1.size()+list2.size(), 8 + list1.size()+list2.size(), 7, 8));
		sheet.addMergedRegion(new CellRangeAddress(8 + list1.size()+list2.size(), 8 + list1.size()+list2.size(), 9, 10));
		sheet.addMergedRegion(new CellRangeAddress(8 + list1.size(), 8 + list1.size()+list2.size(), 0, 1));

		//经办人
		XSSFRow row5 = sheet.createRow(10 + list1.size() + list2.size());
		row5.createCell(0).setCellValue("经办人:");
		output = response.getOutputStream();
		wb.write(output);
		output.close();
		fileInput.close();
		wb.close();

	}

	@Override
	public IPage<ExternalPaymentVo> selectBusinessPageVo(Page page, ExternalPaymentVo externalPaymentVo) {
		return baseMapper.selectBusinessPageVo(page,externalPaymentVo);
	}

	@Override
	public void exportByBusId(ExternalPaymentVo externalPaymentVo, HttpServletResponse response) throws IOException {
		List<BusinessBillsBody> bbBodyList = externalPaymentVo.getBbBodyList();
		List<BusinessBillsBody> list1= new ArrayList<>();
		List<BusinessBillsBody> list2= new ArrayList<>();
		for (int i = 0; i < bbBodyList .size() ; i++) {
			if("应收款".equals(bbBodyList.get(i).getItemNam())){
				list1.add(bbBodyList.get(i));
			}else {
				list2.add(bbBodyList.get(i));
			}
		}
		BigDecimal money1 = BigDecimal.ZERO;
		BigDecimal taxMoney1 = BigDecimal.ZERO;
		BigDecimal totalMoney1 = BigDecimal.ZERO;

		BigDecimal money2 = BigDecimal.ZERO;
		BigDecimal taxMoney2 = BigDecimal.ZERO;
		BigDecimal totalMoney2 = BigDecimal.ZERO;
		for ( BusinessBillsBody bb : list1) {
			money1 = money1.add(bb.getMoney());
			totalMoney1 = totalMoney1.add(bb.getTotalMoney());
			taxMoney1 =taxMoney1.add(bb.getTaxMoney());
		}
		for ( BusinessBillsBody bb : list2) {
			money2 = money2.add(bb.getMoney());
			totalMoney2 = totalMoney2.add(bb.getTotalMoney());
			taxMoney2 =taxMoney2.add(bb.getTaxMoney());
		}

		// 获取标题名称
		String fileName = "业务结算单";
		ParameterList parameter = parameterListMapper.selectValueByCode("business_document");
		if (parameter != null && !StringUtil.isEmpty(parameter.getParamValue())) {
			fileName = parameter.getParamValue();
		}
		// 告诉浏览器用什么软件打开此文件
		response.setHeader("content-Type","application/vnd.ms-excel");
		// 下载文件的默认名称
		response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));
		ClassPathResource classPathResource = new ClassPathResource("/file/business_document.xlsx");
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

		//对应Excel表中的sheet,0代表第一个
		XSSFSheet sheet = wb.getSheetAt(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		sheet.getRow(1).getCell(1).setCellValue(fileName);// 标题行
		//第二行第1列填写部门 2B
		sheet.getRow(2).getCell(1).setCellValue(externalPaymentVo.getDept()+" "+externalPaymentVo.getBillCod());
		sheet.getRow(2).getCell(7).setCellValue(sdf.format(externalPaymentVo.getBillDate()));
		//第三行第2列填写合客户 3C
		sheet.getRow(3).getCell(1).setCellValue(externalPaymentVo.getSecondNam());
		//第四行第3列合同编号4C
		sheet.getRow(4).getCell(1).setCellValue(externalPaymentVo.getContCod());
		//第四行第8列船名字 4H
		sheet.getRow(4).getCell(5).setCellValue(externalPaymentVo.getInShipName() == ""? "":externalPaymentVo.getInShipName());
		sheet.getRow(4).getCell(7).setCellValue(externalPaymentVo.getOutShipName()== ""? "":externalPaymentVo.getOutShipName());
		//第五行第3列 货名 5C
		sheet.getRow(5).getCell(1).setCellValue(externalPaymentVo.getCargoNam());
		//第五行第8列 计费数量 5H
		sheet.getRow(5).getCell(7).setCellValue(String.valueOf(externalPaymentVo.getFeeQuantity()));

		// 处理数据
		int index = 0;
		for (int i = 0; i <list1.size() ; i++) {
			BusinessBillsBody bbBody = list1.get(i);
			//7行C列 费用名称
			sheet.getRow(7+i).getCell(1).setCellValue(bbBody.getFeeNam());
			//7行E列  费率
			sheet.getRow(7+i).getCell(3).setCellValue(bbBody.getFeeRat().doubleValue());
			//应收款
			sheet.getRow(7+i).getCell(4).setCellValue(bbBody.getMoney().doubleValue());
			//预收款
			sheet.getRow(7+i).getCell(5).setCellValue(bbBody.getTaxMoney().doubleValue());
			sheet.getRow(7+i).getCell(6).setCellValue(bbBody.getTotalMoney().doubleValue());
			sheet.getRow(7+i).getCell(7).setCellValue(bbBody.getMarkTxt());
			index++;
			if (index < list1.size()) {
				sheet.shiftRows(7+i, 8+i, 1,true,false);
				sheet.copyRows(8+i,8+i, 7+i,new CellCopyPolicy());
			}

		}

		//创建小计行
		XSSFRow row = sheet.createRow(7 + list1.size());
		row.setHeight((short) 420);
		for (int i = 0; i <8; i++) {
			XSSFCell cell = row.createCell(i);
			row.getCell(i).setCellStyle(xssfCell);
		}
		//应预收款
		sheet.getRow(7).getCell(0).setCellValue("应收款");
		sheet.getRow(7).getCell(0).setCellStyle(xssfCell);
		sheet.addMergedRegion(new CellRangeAddress(7 + list1.size(), 7 + list1.size(), 1, 3));
		if(list1.size()>0){
			sheet.addMergedRegion(new CellRangeAddress(7, 7+ list1.size(), 0, 0));
		}
		XSSFCell cell = sheet.getRow(7 + list1.size()).getCell(1);
		cell.setCellValue("小计");
		cell.setCellStyle(xssfCell);
		XSSFRow row2 = sheet.createRow(7 + list1.size()+1);
		row2.copyRowFrom(sheet.getRow(6),new CellCopyPolicy());
		XSSFCell cell1 = sheet.getRow(7 + list1.size()).getCell(4);
		XSSFCell cell2 = sheet.getRow(7 + list1.size()).getCell(5);
		XSSFCell cell3 = sheet.getRow(7 + list1.size()).getCell(6);
		cell1.setCellValue(String.format("%.2f", money1));
		cell2.setCellValue(String.format("%.2f",taxMoney1));
		cell3.setCellValue(String.format("%.2f",totalMoney1));
		XSSFRow row1 = sheet.createRow(9 + list1.size());
		row1.setHeight((short) 420);
		for (int i = 0; i <8; i++) {
			XSSFCell cell4 = row1.createCell(i);
			row1.getCell(i).setCellStyle(xssfCell);
		}
		if(list2.size()>0){
			sheet.addMergedRegion(new CellRangeAddress(9 + list1.size(), 9 + list1.size(), 1, 2));
		}
		int index2 =0;
		for (int i = 0; i <list2.size() ; i++) {
			BusinessBillsBody bbBody = list2.get(i);
			sheet.getRow(9+list1.size()+i).getCell(1).setCellValue(bbBody.getFeeNam());
			sheet.getRow(9+list1.size()+i).getCell(3).setCellValue(bbBody.getFeeRat().doubleValue());
			sheet.getRow(9+list1.size()+i).getCell(4).setCellValue(bbBody.getMoney().doubleValue());
			sheet.getRow(9+list1.size()+i).getCell(5).setCellValue(bbBody.getTaxMoney().doubleValue());
			sheet.getRow(9+list1.size()+i).getCell(6).setCellValue(bbBody.getTotalMoney().doubleValue());
			sheet.getRow(9+list1.size()+i).getCell(7).setCellValue(bbBody.getMarkTxt());
			index2++;
			if (index2 < list2.size()) {
				sheet.shiftRows(9+i+list1.size(), 10+i+list1.size(), 1,true,false);
				sheet.copyRows(10+i+list1.size(),10+list1.size()+i, 9+list1.size()+i,new CellCopyPolicy());
			}
		}

		XSSFRow row3 = sheet.createRow(9 + list1.size()+list2.size());
		row3.setHeight((short) 420);
		for (int i = 0; i <8; i++) {
			XSSFCell cell5 = row3.createCell(i);
			row3.getCell(i).setCellStyle(xssfCell);
		}
		sheet.getRow(9+list1.size()).getCell(0).setCellValue("应付款");
		sheet.getRow(9+list1.size()).getCell(0).setCellStyle(xssfCell);
		sheet.addMergedRegion(new CellRangeAddress(9 + list1.size()+list2.size(), 9 + list1.size()+list2.size(), 1, 3));
		if(list2.size()>0 ){
			sheet.addMergedRegion(new CellRangeAddress(9 + list1.size(), 9 + list1.size()+list2.size(), 0, 0));
		}
		sheet.getRow(9 + list1.size()+list2.size()).getCell(1).setCellValue("小计");
		sheet.getRow(9 + list1.size()+list2.size()).getCell(4).setCellValue(String.format("%.2f",money2));
		sheet.getRow(9 + list1.size()+list2.size()).getCell(5).setCellValue(String.format("%.2f",taxMoney2));
		sheet.getRow(9 + list1.size()+list2.size()).getCell(6).setCellValue(String.format("%.2f",totalMoney2));
		XSSFRow row4 = sheet.createRow(10 + list1.size()+list2.size());
		row4.setHeight((short) 420);
		for (int i = 0; i <8; i++) {
			XSSFCell cell6 = row4.createCell(i);
			row4.getCell(i).setCellStyle(xssfCell);
		}
		sheet.getRow(10 + list1.size()+list2.size()).getCell(0).setCellValue("利润");
		sheet.getRow(10 + list1.size()+list2.size()).getCell(1).setCellValue(String.format("%.2f",money1.subtract(money2)));
		sheet.getRow(10 + list1.size()+list2.size()).getCell(0).setCellStyle(xssfCell);
		sheet.getRow(10 + list1.size()+list2.size()).getCell(1).setCellStyle(xssfCell);
		sheet.addMergedRegion(new CellRangeAddress(10 + list1.size()+list2.size(), 10 + list1.size()+list2.size(), 1, 6));
		//经办人
		XSSFRow row5 = sheet.createRow(15 + list1.size() + list2.size());
		row5.createCell(0).setCellValue("经办人:");
		row5.createCell(5).setCellValue("审核人:");


		output = response.getOutputStream();
		wb.write(output);
		output.close();
		fileInput.close();
		wb.close();
	}

	@Override
	public List<ExternalPaymentVo> selectPayment() {
		return baseMapper.selectPayment();
	}

	@Override
	public List<ExternalPaymentVo> selectSettlement() {
		return baseMapper.selectSettlement();
	}

	@Override
	public IPage<ExternalPaymentVo> getCargoKindOperation(Page page,ExternalPaymentVo externalPaymentVo) {
		return businessBillsHeadMapper.getCargoKindOperation(page,externalPaymentVo);
	}

	@Override
	public IPage<IncomeStatistcsVo> getIncomeStatistcs(Page page, ExternalPaymentVo externalPaymentVo) {
		externalPaymentVo.setFeeNam("代理报关费");
		IPage<IncomeStatistcsVo> incomeStatistcs = businessBillsHeadMapper.getIncomeStatistcs(page, externalPaymentVo);
		List<IncomeStatistcsVo> recordsList = incomeStatistcs.getRecords();
		String [] monthChinese = {"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
		ParameterList parameterList = new ParameterList();
		parameterList.setParamCod("tax_rate");

		final List<ParameterList> paramList = parameterListService.list(Wrappers.query(parameterList));
		String rate = paramList.get(0).getParamValue();
		double v = Double.parseDouble(rate)+1.00;
		rate = v+"";
		BigDecimal taxRate = new BigDecimal(rate);
		for (IncomeStatistcsVo item:recordsList) {
			Calendar instance = Calendar.getInstance();
			instance.setTime(item.getBillDate());
			int i = instance.get(Calendar.MONTH);
			item.setMonth(monthChinese[i]);
			item.setTotalMoney(item.getTotalMoney().subtract(item.getAgencyCustomsDeclarationFee()));
			item.setAgencyTotalMoneyAfterTax(item.getTotalMoney().divide(taxRate,2,BigDecimal.ROUND_HALF_UP));
			BigDecimal b = item.getTotalMoney().add(item.getAgencyCustomsDeclarationFee());
			b = b.add(item.getOneDayTour());
			item.setIncomeWithTax(b);
			b = b.divide(taxRate,2,BigDecimal.ROUND_HALF_UP);
			item.setIncomeWithoutTax(b);
		}
		incomeStatistcs.setRecords(recordsList);
		return incomeStatistcs;
	}

	@Override
	public void cargoKindOperationPrintToExcel(ExternalPaymentVo externalPaymentVo) throws IOException {
		List<ExternalPaymentVo> cargoKindOperationList = businessBillsHeadMapper.getCargoKindOperation(externalPaymentVo);
		InputStream path = this.getClass().getResourceAsStream("/file/分货种操作量表.xls");
		HSSFWorkbook wb = new HSSFWorkbook(path);
		for (ExternalPaymentVo item:cargoKindOperationList) {
			if("0".equals(item.getLoadingShip() )){
				item.setLoadShip("√");
			}else if("1".equals(item.getLoadingShip())){
				item.setUnloadShip("√");
			}
		}
		for (ExternalPaymentVo item:cargoKindOperationList) {
			if("0".equals(item.getTradeId() )){
				item.setTradeId("外贸");
			}else if("1".equals(item.getTradeId())){
				item.setTradeId("内贸");
			}
		}

		ExcelUtil.exportExcel("",wb,cargoKindOperationList, ExternalPaymentVo.class,2,1,request,response);
		HSSFSheet sheetAt = wb.getSheetAt(0);
		HSSFRow row = sheetAt.getRow(0);
		row.getCell(0).setCellValue(externalPaymentVo.getYear()+"年货代部分货种操作量表");
		response.setContentType("application/binary;charset=UTF-8");
		OutputStream output = response.getOutputStream();
		String fn = new String(new String("分货种操作量表").getBytes(), "UTF-8");
		response.setHeader("ContentDisposition", URLEncoder.encode("" + fn + ".xls","UTF-8"));
		wb.write(output);
		output.flush();
		output.close();
	}

	@Override
	public void incomeStatistcsPrintToExcel(ExternalPaymentVo externalPaymentVo) throws IOException {
		externalPaymentVo.setFeeNam("代理报关费");
		List<IncomeStatistcsVo> recordsList = businessBillsHeadMapper.getIncomeStatistcs(externalPaymentVo);
		String [] monthChinese = {"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
		ParameterList parameterList = new ParameterList();
		parameterList.setParamCod("tax_rate");

		final List<ParameterList> paramList = parameterListService.list(Wrappers.query(parameterList));
		String rate = paramList.get(0).getParamValue();
		double v = Double.parseDouble(rate)+1.00;
		rate = v+"";
		BigDecimal taxRate = new BigDecimal(rate);
		for (IncomeStatistcsVo item:recordsList) {
			Calendar instance = Calendar.getInstance();
			instance.setTime(item.getBillDate());
			int i = instance.get(Calendar.MONTH);
			item.setMonth(monthChinese[i]);
			item.setTotalMoney(item.getTotalMoney().subtract(item.getAgencyCustomsDeclarationFee()));
			item.setAgencyTotalMoneyAfterTax(item.getTotalMoney().divide(taxRate,2,BigDecimal.ROUND_HALF_UP));
			BigDecimal b = item.getTotalMoney().add(item.getAgencyCustomsDeclarationFee());
			b = b.add(item.getOneDayTour());
			item.setIncomeWithTax(b);
			b = b.divide(taxRate,2,BigDecimal.ROUND_HALF_UP);
			item.setIncomeWithoutTax(b);
		}

		InputStream path = this.getClass().getResourceAsStream("/file/货代部收入统计.xls");
		HSSFWorkbook wb = new HSSFWorkbook(path);
		ExcelUtil.exportExcel("",wb,recordsList, IncomeStatistcsVo.class,3,2,request,response);

		HSSFSheet sheetAt = wb.getSheetAt(0);
		HSSFRow row = sheetAt.getRow(0);
		row.getCell(0).setCellValue(externalPaymentVo.getYear()+"年货代部收入统计");
		int mergeCell = 0;
		//合并单元格的数据
		BigDecimal totalWithTax  =new BigDecimal("0");
		BigDecimal totalWithoutTax  =new BigDecimal("0");
		BigDecimal agencyFee = new BigDecimal("0");
		BigDecimal oneDayTour = new BigDecimal("0");
		BigDecimal incomeWithTax  =new BigDecimal("0");
		BigDecimal incomeWithoutTax  =new BigDecimal("0");
		//合计的数据
		BigDecimal countTotalMoney = new BigDecimal("0");
		BigDecimal countTotalMoneyAfterTax = new BigDecimal("0");
		BigDecimal countAgencyMoney = new BigDecimal("0");
		BigDecimal countOneDayTour = new BigDecimal("0");
		BigDecimal countIncomeWithTax = new BigDecimal("0");
		BigDecimal countIncomeWithoutTax = new BigDecimal("0");
		for(int i = 0;i<recordsList.size();i++){
			//或和合并单元格数据
			totalWithTax = totalWithTax.add(recordsList.get(i).getTotalMoney()) ;
			totalWithoutTax = totalWithoutTax.add(recordsList.get(i).getAgencyTotalMoneyAfterTax()) ;
			agencyFee = agencyFee.add(recordsList.get(i).getAgencyCustomsDeclarationFee()) ;
			oneDayTour = oneDayTour.add(recordsList.get(i).getOneDayTour()) ;
			incomeWithTax = incomeWithTax.add(recordsList.get(i).getIncomeWithTax()) ;
			incomeWithoutTax = incomeWithoutTax.add(recordsList.get(i).getIncomeWithoutTax()) ;

			countTotalMoney = countTotalMoney.add(recordsList.get(i).getTotalMoney());
			countTotalMoneyAfterTax = countTotalMoneyAfterTax.add(recordsList.get(i).getAgencyTotalMoneyAfterTax());
			countAgencyMoney = countAgencyMoney.add(recordsList.get(i).getAgencyCustomsDeclarationFee());
			countOneDayTour = countOneDayTour.add(recordsList.get(i).getOneDayTour());
			countIncomeWithTax = countIncomeWithTax.add(recordsList.get(i).getIncomeWithTax()) ;
			countIncomeWithoutTax = countIncomeWithoutTax.add(recordsList.get(i).getIncomeWithoutTax()) ;
			if(sheetAt.getRow(3+i)!=null&&sheetAt.getRow(4+i)!=null){
				String thisMonth = sheetAt.getRow(3+i).getCell(0).getStringCellValue();
				String nextMonth = sheetAt.getRow(4+i).getCell(0).getStringCellValue();

				if(thisMonth.equals(nextMonth)){
					mergeCell++;
				}else{
					//合并单元格
					sheetAt.getRow(3+i-mergeCell).getCell(4).setCellValue(totalWithTax+"");
					sheetAt.getRow(3+i-mergeCell).getCell(5).setCellValue(totalWithoutTax+"");

					sheetAt.getRow(3+i-mergeCell).getCell(6).setCellValue(agencyFee+"");
					sheetAt.getRow(3+i-mergeCell).getCell(7).setCellValue(oneDayTour+"");

					sheetAt.getRow(3+i-mergeCell).getCell(8).setCellValue(incomeWithTax+"");
					sheetAt.getRow(3+i-mergeCell).getCell(9).setCellValue(incomeWithoutTax+"");
					if(mergeCell>0){
						sheetAt.addMergedRegion(new CellRangeAddress(3+i-mergeCell,3+i,4,4));
						sheetAt.addMergedRegion(new CellRangeAddress(3+i-mergeCell,3+i,5,5));
						sheetAt.addMergedRegion(new CellRangeAddress(3+i-mergeCell,3+i,6,6));
						sheetAt.addMergedRegion(new CellRangeAddress(3+i-mergeCell,3+i,7,7));
						sheetAt.addMergedRegion(new CellRangeAddress(3+i-mergeCell,3+i,0,0));
						sheetAt.addMergedRegion(new CellRangeAddress(3+i-mergeCell,3+i,8,8));
						sheetAt.addMergedRegion(new CellRangeAddress(3+i-mergeCell,3+i,9,9));
					}
					agencyFee = new BigDecimal("0");
					oneDayTour = new BigDecimal("0");
					totalWithoutTax=new BigDecimal("0");
					totalWithTax  =new BigDecimal("0");
					incomeWithTax  =new BigDecimal("0");
					incomeWithoutTax  =new BigDecimal("0");
					mergeCell = 0;
				}
			}else{
				sheetAt.getRow(3+i-mergeCell).getCell(5).setCellValue(totalWithoutTax+"");
				sheetAt.getRow(3+i-mergeCell).getCell(4).setCellValue(totalWithTax+"");
				sheetAt.getRow(3+i-mergeCell).getCell(6).setCellValue(agencyFee+"");
				sheetAt.getRow(3+i-mergeCell).getCell(7).setCellValue(oneDayTour+"");
				sheetAt.getRow(3+i-mergeCell).getCell(8).setCellValue(incomeWithTax+"");
				sheetAt.getRow(3+i-mergeCell).getCell(9).setCellValue(incomeWithoutTax+"");
				if(mergeCell>0){
					sheetAt.addMergedRegion(new CellRangeAddress(3+i-mergeCell,3+i,6,6));
					sheetAt.addMergedRegion(new CellRangeAddress(3+i-mergeCell,3+i,7,7));
					sheetAt.addMergedRegion(new CellRangeAddress(3+i-mergeCell,3+i,4,4));
					sheetAt.addMergedRegion(new CellRangeAddress(3+i-mergeCell,3+i,5,5));
					sheetAt.addMergedRegion(new CellRangeAddress(3+i-mergeCell,3+i,0,0));
					sheetAt.addMergedRegion(new CellRangeAddress(3+i-mergeCell,3+i,8,8));
					sheetAt.addMergedRegion(new CellRangeAddress(3+i-mergeCell,3+i,9,9));
				}
				agencyFee = new BigDecimal("0");
				oneDayTour = new BigDecimal("0");
				totalWithoutTax=new BigDecimal("0");
				totalWithTax  =new BigDecimal("0");
				incomeWithTax  =new BigDecimal("0");
				incomeWithoutTax  =new BigDecimal("0");
				mergeCell = 0;
			}

		}
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
		cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
		cellStyle.setBorderTop(BorderStyle.THIN);//上边框
		cellStyle.setBorderRight(BorderStyle.THIN);//右边框
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setWrapText(true);
		HSSFRow countRow = sheetAt.createRow(recordsList.size() + 3);
		countRow.createCell(0).setCellValue("合计");
		countRow.getCell(0).setCellStyle(cellStyle);
		countRow.createCell(1).setCellStyle(cellStyle);
		countRow.createCell(2).setCellStyle(cellStyle);
		int [] cell = {3,4,5,6,7,8,9};
		BigDecimal [] cellVal = {countTotalMoney,countTotalMoney,countTotalMoneyAfterTax,countAgencyMoney,countOneDayTour,countIncomeWithTax,countIncomeWithoutTax};
		for (int i = 0; i < cell.length; i++) {
			countRow.createCell(cell[i]).setCellValue(cellVal[i]+"");
			countRow.getCell(cell[i]).setCellStyle(cellStyle);
		}
		response.setContentType("application/binary;charset=UTF-8");
		OutputStream output = response.getOutputStream();
		String fn = new String(new String("货代部收入统计").getBytes(), "UTF-8");
		response.setHeader("ContentDisposition", URLEncoder.encode("" + fn + ".xls","UTF-8"));
		wb.write(output);
		output.flush();
		output.close();
	}


}
