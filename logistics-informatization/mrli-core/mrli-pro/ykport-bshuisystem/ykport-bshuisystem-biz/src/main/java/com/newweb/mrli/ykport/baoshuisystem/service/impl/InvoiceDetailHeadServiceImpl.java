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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.InvoiceDetailBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.InvoiceDetailHead;
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardSettlementBillsBody;
import com.newweb.mrli.ykport.baoshuisystem.mapper.InvoiceDetailHeadMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.InvoiceDetailBodyService;
import com.newweb.mrli.ykport.baoshuisystem.service.InvoiceDetailHeadService;
import com.newweb.mrli.ykport.baoshuisystem.vo.InvoiceDetailHeadVo;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
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
import java.util.List;
import java.util.Queue;

/**
 * ??????????????????
 *
 * @author pigx code generator
 * @date 2021-09-22 09:23:31
 */
@Service
public class InvoiceDetailHeadServiceImpl extends ServiceImpl<InvoiceDetailHeadMapper, InvoiceDetailHead> implements InvoiceDetailHeadService {

	@Autowired
	private InvoiceDetailBodyService invoiceService;
	@Override
	public IPage<InvoiceDetailHeadVo> selectPageVo(Page page, InvoiceDetailHeadVo invoiceDetailHead) {


		return 	baseMapper.selectPageVo(page,invoiceDetailHead);
	}

	@Override
	public void exportExcel(InvoiceDetailHeadVo detailHeadVo, HttpServletResponse response) throws IOException {
		List<InvoiceDetailBody> invoiceDetailBodyList = detailHeadVo.getInvoiceDetailBodyList();
		BigDecimal bMoney = new BigDecimal("0");
		for (InvoiceDetailBody i: invoiceDetailBodyList) {
			bMoney =bMoney.add(i.getMoney());
		}
		// ??????????????????
//		ParameterList parameter = parameterListMapper.selectValueByCode("ioyard_settlement_bills_head");
//		if (parameter != null && !StringUtil.isEmpty(parameter.getParamValue())) {
//			fileName = parameter.getParamValue();
//		}
		// ?????????????????????????????????????????????
//		response.setHeader("content-Type","application/vnd.ms-excel");
//		// ???????????????????????????
//		response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));
		ClassPathResource classPathResource = new ClassPathResource("/file/ioyard_detail_head.xlsx");
		InputStream fileInput = classPathResource.getInputStream();
		OutputStream output = null;
		XSSFWorkbook wb = new XSSFWorkbook(fileInput);
		XSSFCellStyle xssfCell2 = wb.createCellStyle();
		XSSFCellStyle xssfCell3 = wb.createCellStyle();
		xssfCell3.setAlignment(HorizontalAlignment.LEFT);
		xssfCell3.setVerticalAlignment(VerticalAlignment.CENTER);
		xssfCell3.setBorderBottom(HSSFCellStyle.BORDER_THIN); //?????????
		xssfCell3.setBorderLeft(HSSFCellStyle.BORDER_THIN);//?????????
		xssfCell3.setBorderTop(HSSFCellStyle.BORDER_THIN);//?????????
		xssfCell3.setBorderRight(HSSFCellStyle.BORDER_THIN);//?????????
		xssfCell2.setAlignment(HorizontalAlignment.RIGHT);
		xssfCell2.setVerticalAlignment(VerticalAlignment.CENTER);
		xssfCell2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //?????????
		xssfCell2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//?????????
		xssfCell2.setBorderTop(HSSFCellStyle.BORDER_THIN);//?????????
		xssfCell2.setBorderRight(HSSFCellStyle.BORDER_THIN);//?????????
		XSSFCellStyle xssfCell = wb.createCellStyle();
		xssfCell.setAlignment(HorizontalAlignment.CENTER);
		xssfCell.setVerticalAlignment(VerticalAlignment.CENTER);
		//???????????????????????????????????????????????????
		xssfCell.setBorderBottom(HSSFCellStyle.BORDER_THIN); //?????????
		xssfCell.setBorderLeft(HSSFCellStyle.BORDER_THIN);//?????????
		xssfCell.setBorderTop(HSSFCellStyle.BORDER_THIN);//?????????
		xssfCell.setBorderRight(HSSFCellStyle.BORDER_THIN);//?????????

		XSSFSheet sheet = wb.getSheetAt(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		sheet.getRow(1).getCell(0).setCellValue("????????????:"+detailHeadVo.getAccountNam());
		sheet.getRow(2).getCell(0).setCellValue("????????????:"+detailHeadVo.getInvoiceType());
		sheet.getRow(3).getCell(0).setCellValue("????????????:"+detailHeadVo.getSecondNam());
		int index = 0;
		for (int i = 0; i < invoiceDetailBodyList.size(); i++) {
			InvoiceDetailBody iBody = invoiceDetailBodyList.get(i);

			sheet.getRow(6 + i).getCell(0).setCellValue(iBody.getProjectNam());
			//??????
			sheet.getRow(6+i).getCell(2).setCellValue(iBody.getUnit());
			//??????
			if(iBody.getAmount() != null){
				sheet.getRow(6+i).getCell(3).setCellValue(iBody.getAmount().doubleValue());
			}else {
				sheet.getRow(6+i).getCell(3).setCellValue(" ");
			}

			//??????
			sheet.getRow(6+i).getCell(4).setCellValue(iBody.getUnitPrice().doubleValue());
			//??????
			sheet.getRow(6+i).getCell(5).setCellValue(iBody.getMoney().doubleValue());

			index++;
			if (index <invoiceDetailBodyList.size()) {
				sheet.shiftRows(6+i, 7+i, 1,true,false);
				sheet.copyRows(7+i,7+i, 6+i,new CellCopyPolicy());
			}
		}

		XSSFRow row2 = sheet.createRow(6+invoiceDetailBodyList.size());
		//????????????????????????
		row2.copyRowFrom(sheet.getRow(5),new CellCopyPolicy());
		//????????????
		XSSFRow row = sheet.createRow(7 + invoiceDetailBodyList.size());
		for (int i = 0; i <6; i++) {
			XSSFCell cell = row.createCell(i);
			row.getCell(i).setCellStyle(xssfCell);
		}
		sheet.getRow(7 + invoiceDetailBodyList.size()).getCell(0).setCellValue("??????");
		sheet.addMergedRegion(new CellRangeAddress (7 + invoiceDetailBodyList.size(), 7 + invoiceDetailBodyList.size(), 0, 4));
		sheet.getRow(7 + invoiceDetailBodyList.size()).getCell(5).setCellValue(bMoney.doubleValue());
		sheet.getRow(7 + invoiceDetailBodyList.size()).getCell(5).setCellStyle(xssfCell2);
		XSSFRow row3 = sheet.createRow(8+invoiceDetailBodyList.size());
		for (int i = 0; i <6; i++) {
			XSSFCell cell = row3.createCell(i);
			row3.getCell(i).setCellStyle(xssfCell);
		}
		sheet.getRow(8+invoiceDetailBodyList.size()).getCell(0).setCellValue("????????????");
		sheet.getRow(8+invoiceDetailBodyList.size()).getCell(0).setCellStyle(xssfCell3);
		sheet.addMergedRegion(new CellRangeAddress (8 + invoiceDetailBodyList.size(), 8 + invoiceDetailBodyList.size(), 0, 1));
		sheet.getRow(8+invoiceDetailBodyList.size()).getCell(2).setCellValue("??????");
		sheet.getRow(8+invoiceDetailBodyList.size()).getCell(2).setCellStyle(xssfCell3);
		XSSFRow row4 = sheet.createRow(9+invoiceDetailBodyList.size());
		for (int i = 0; i <6; i++) {
			XSSFCell cell = row4.createCell(i);
			row4.getCell(i).setCellStyle(xssfCell);
		}
		sheet.addMergedRegion(new CellRangeAddress (9 + invoiceDetailBodyList.size(), 9 + invoiceDetailBodyList.size(), 0, 1));
		sheet.getRow(9+invoiceDetailBodyList.size()).getCell(2).setCellValue("??????");
		sheet.getRow(9+invoiceDetailBodyList.size()).getCell(2).setCellStyle(xssfCell3);
		XSSFRow row5 = sheet.createRow(10+invoiceDetailBodyList.size());
		for (int i = 0; i <6; i++) {
			XSSFCell cell = row5.createCell(i);
		}
		sheet.getRow(10+invoiceDetailBodyList.size()).getCell(0).setCellValue("????????????:"+detailHeadVo.getInShipName());
		sheet.getRow(10+invoiceDetailBodyList.size()).getCell(1).setCellValue("????????????:"+detailHeadVo.getOutShipName());
		sheet.getRow(10+invoiceDetailBodyList.size()).getCell(4).setCellValue("???????????????:"+detailHeadVo.getBillCod());
		XSSFRow row6 = sheet.createRow(11+invoiceDetailBodyList.size());
		for (int i = 0; i <6; i++) {
			XSSFCell cell = row6.createCell(i);
		}
		sheet.getRow(11+invoiceDetailBodyList.size()).getCell(0).setCellValue("?????????:");
		sheet.getRow(11+invoiceDetailBodyList.size()).getCell(2).setCellValue("??????:"+sdf.format(detailHeadVo.getOperDat()));
		sheet.getRow(11+invoiceDetailBodyList.size()).getCell(5).setCellValue("?????????:");
		output = response.getOutputStream();
		wb.write(output);
		output.close();
		fileInput.close();
		wb.close();
		}
	}
