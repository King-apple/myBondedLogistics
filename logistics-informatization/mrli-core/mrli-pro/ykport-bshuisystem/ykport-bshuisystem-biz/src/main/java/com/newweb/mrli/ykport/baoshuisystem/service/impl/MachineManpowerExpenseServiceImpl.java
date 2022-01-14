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

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardListBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.MachineManpowerExpense;
import com.newweb.mrli.ykport.baoshuisystem.entity.ParameterList;
import com.newweb.mrli.ykport.baoshuisystem.mapper.MachineManpowerExpenseMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.MachineManpowerExpenseService;
import com.newweb.mrli.ykport.baoshuisystem.utils.ExcelUtil;
import com.newweb.mrli.ykport.baoshuisystem.vo.InoutYardHeadVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.IoyardListHeadBodyVo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 机械人力作业费用明细表
 *
 * @author zada
 * @date 2021-10-22 16:46:51
 */
@Service
public class MachineManpowerExpenseServiceImpl extends ServiceImpl<MachineManpowerExpenseMapper, MachineManpowerExpense> implements MachineManpowerExpenseService {

	@Resource
	private MachineManpowerExpenseMapper machineManpowerExpenseMapper;

	@Override
	public IPage<List<MachineManpowerExpense>> getList(Page<?> page, MachineManpowerExpense machineManpowerExpense){
		return machineManpowerExpenseMapper.getList(page, machineManpowerExpense);
	}

	@Override
	public List<MachineManpowerExpense> getMachineManpowerExcel(MachineManpowerExpense machineManpowerExpense){
		return machineManpowerExpenseMapper.getMachineManpowerExcel(machineManpowerExpense);
	}

	@Override
	public void expoprtMachineManpowerExcel(MachineManpowerExpense machineManpowerExpense, List<MachineManpowerExpense> list, HttpServletResponse response) throws IOException {
		// 获取标题名称
		String fileName = "机械人力作业费明细表";

		// 告诉浏览器用什么软件打开此文件
		response.setHeader("content-Type","application/vnd.ms-excel");
		// 下载文件的默认名称
		response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));

		ClassPathResource classPathResource = new ClassPathResource("/file/machine_manpower.xlsx");

		InputStream fileInput = classPathResource.getInputStream();
		OutputStream output = null;
		//创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wb = new XSSFWorkbook(fileInput);
		//对应Excel表中的sheet,0代表第一个
		XSSFSheet sheet = wb.getSheetAt(0);

		sheet.getRow(0).getCell(0).setCellValue(fileName);// 标题行



		// 处理数据
		int index = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		for (int i = 0; i < list.size(); i++) {
			XSSFRow row = sheet.getRow(i + 2);
			row.getCell(0).setCellValue(i+1);
			row.getCell(1).setCellValue(list.get(i).getWorkDte()==null?"": DateUtil.format(list.get(i).getWorkDte(), DatePattern.CHINESE_DATE_PATTERN));
			row.getCell(2).setCellValue(list.get(i).getSubunitNam()==null?"":list.get(i).getSubunitNam());
			row.getCell(3).setCellValue(list.get(i).getMachTypeNam()==null?"":list.get(i).getMachTypeNam());
			row.getCell(4).setCellValue(list.get(i).getMachKndCod()==null?"":list.get(i).getMachKndCod());
			row.getCell(5).setCellValue(list.get(i).getWorkTypeNam()==null?"":list.get(i).getWorkTypeNam());

			row.getCell(6).setCellValue(list.get(i).getHours()==null?0:list.get(i).getHours());
			row.getCell(7).setCellValue(list.get(i).getUnitPrice()==null?0:list.get(i).getUnitPrice());
			row.getCell(8).setCellValue(list.get(i).getSum()==null?0:list.get(i).getSum());
			row.getCell(9).setCellValue(list.get(i).getCheckDte()==null?"":DateUtil.format(list.get(i).getCheckDte(), DatePattern.CHINESE_DATE_PATTERN));
			row.getCell(10).setCellValue(list.get(i).getSettleDte()==null?"":DateUtil.format(list.get(i).getSettleDte(), DatePattern.CHINESE_DATE_PATTERN));
			row.getCell(11).setCellValue(list.get(i).getReceiptInvoiceDte()==null?"":DateUtil.format(list.get(i).getReceiptInvoiceDte(), DatePattern.CHINESE_DATE_PATTERN));
			row.getCell(12).setCellValue(list.get(i).getSettledAmount()==null?0:list.get(i).getSettledAmount());
			row.getCell(13).setCellValue(list.get(i).getOutstandingAmount()==null?0:list.get(i).getOutstandingAmount());
			index++;
			if (index < list.size()) {
				sheet.shiftRows(i+2, i+3, 1,true,false);
				sheet.copyRows(i+3,i+3, i+2,new CellCopyPolicy());
			}
		}

		output = response.getOutputStream();
		wb.write(output);
		output.close();
		fileInput.close();
		wb.close();
	}



}
