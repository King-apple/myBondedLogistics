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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.SingClassInfo;
import com.newweb.mrli.ykport.baoshuisystem.entity.YkbfSingClass;
import com.newweb.mrli.ykport.baoshuisystem.mapper.SingClassInfoMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.YkbfSingClassMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.YkbfSingClassService;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 单个工班查询
 *
 * @author pigx code generator
 * @date 2021-10-24 15:49:10
 */
@Service
public class YkbfSingClassServiceImpl extends ServiceImpl<YkbfSingClassMapper, YkbfSingClass> implements YkbfSingClassService {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private  YkbfSingClassMapper ykbfSingClassMapper;
	@Autowired
	private SingClassInfoMapper singClassInfoMapper;
	@Override
	public void printToExcel(YkbfSingClass ykbfSingClass) throws IOException {
		List<SingClassInfo> list1 = singClassInfoMapper.printToExcel(ykbfSingClass);
		List<SingClassInfo> list2 = singClassInfoMapper.printInOutFlag(ykbfSingClass);
		String date = "20"+""+ykbfSingClass.getStartDte()[0].substring(0,2)+"/"+ykbfSingClass.getStartDte()[0].substring(2,4)+"/"+ykbfSingClass.getStartDte()[0].substring(4,6);
		ClassPathResource classPathResource = new ClassPathResource("/file/日操作量表.xlsx");
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
		int  index = 0;
		for (int i = 0; i <list1.size() ; i++) {
			sheet.getRow(2+i).getCell(0).setCellValue(date);
			sheet.getRow(2+i).getCell(1).setCellValue(list1.get(i).getHm());
			sheet.getRow(2+i).getCell(2).setCellValue(list1.get(i).getOnInventory());
			sheet.getRow(2+i).getCell(5).setCellValue(list1.get(i).getThisInventory());
			//入库量
			sheet.getRow(2+i).getCell(3).setCellValue(0);
			sheet.getRow(2+i).getCell(4).setCellValue(0);
			for (int j = 0; j <list2.size() ; j++) {
				if(list1.get(i).getHm().equals(list2.get(j).getHm()) ){
					if("1".equals(list2.get(j).getInoutFlag())){
						sheet.getRow(2+i).getCell(3).setCellValue(list2.get(j).getBenhz());
					}else if("0".equals(list2.get(j).getInoutFlag())){
						//出库量
						sheet.getRow(2+i).getCell(4).setCellValue(list2.get(j).getBenhz());
					}
				}
			}
			index++;
			if (index < list1.size()) {
				sheet.shiftRows(2 + i, 3 + i, 1, true, false);
				sheet.copyRows(3 + i, 3 + i, 2 + i, new CellCopyPolicy());
			}
		}
		output = response.getOutputStream();
		wb.write(output);
		output.close();
		fileInput.close();
		wb.close();
	}

	@Override
	public IPage<SingClassInfo> getPage(Page page, YkbfSingClass ykbfSingClass) {
		return ykbfSingClassMapper.getPage(page,ykbfSingClass);
	}
	@Override
	public List<SingClassInfo> getNoPage(YkbfSingClass ykbfSingClass) {
		return ykbfSingClassMapper.getNoPage(ykbfSingClass);
	}
	@Override
	public void exportList(List<SingClassInfo> singClassInfo)throws IOException {
		ClassPathResource classPathResource = new ClassPathResource("/file/单个工班查询.xlsx");
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
		int  index = 0;
		for (int i = 0; i < singClassInfo.size() ; i++) {
			sheet.getRow(2+i).getCell(0).setCellValue(singClassInfo.get(i).getLxdm());
			sheet.getRow(2+i).getCell(1).setCellValue(singClassInfo.get(i).getBh());
			sheet.getRow(2+i).getCell(2).setCellValue(singClassInfo.get(i).getHm());
			sheet.getRow(2+i).getCell(3).setCellValue(singClassInfo.get(i).getTdh());
			sheet.getRow(2+i).getCell(4).setCellValue(singClassInfo.get(i).getCm());
			sheet.getRow(2+i).getCell(5).setCellValue(singClassInfo.get(i).getJhl());

			sheet.getRow(2+i).getCell(6).setCellValue(singClassInfo.get(i).getCsl());
			sheet.getRow(2+i).getCell(7).setCellValue(singClassInfo.get(i).getWcl());
			sheet.getRow(2+i).getCell(8).setCellValue(singClassInfo.get(i).getSyl());
			sheet.getRow(2+i).getCell(9).setCellValue(singClassInfo.get(i).getBenc());
			sheet.getRow(2+i).getCell(10).setCellValue(singClassInfo.get(i).getWeic());

			sheet.getRow(2+i).getCell(11).setCellValue(singClassInfo.get(i).getBenhz());
			sheet.getRow(2+i).getCell(12).setCellValue(singClassInfo.get(i).getCbs());
			sheet.getRow(2+i).getCell(13).setCellValue(singClassInfo.get(i).getSqdw());
			sheet.getRow(2+i).getCell(14).setCellValue(singClassInfo.get(i).getRqcbs());
			if("0".equals(singClassInfo.get(i).getInoutFlag())){
				sheet.getRow(2+i).getCell(15).setCellValue("出库");
			}else if("1".equals(singClassInfo.get(i).getInoutFlag())){
				sheet.getRow(2+i).getCell(15).setCellValue("入库");
			}else{
				sheet.getRow(2+i).getCell(15).setCellValue("");
			}
			sheet.getRow(2+i).getCell(16).setCellValue(singClassInfo.get(i).getOnInventory()== null? "":singClassInfo.get(i).getOnInventory());
			sheet.getRow(2+i).getCell(17).setCellValue(singClassInfo.get(i).getThisInventory() == null? "":singClassInfo.get(i).getThisInventory());
			index++;
			if (index < singClassInfo.size()) {
				sheet.shiftRows(2 + i, 3 + i, 1, true, false);
				sheet.copyRows(3 + i, 3 + i, 2 + i, new CellCopyPolicy());
			}
		}


		output = response.getOutputStream();

		wb.write(output);
		output.close();
		fileInput.close();
		wb.close();
	}


}
