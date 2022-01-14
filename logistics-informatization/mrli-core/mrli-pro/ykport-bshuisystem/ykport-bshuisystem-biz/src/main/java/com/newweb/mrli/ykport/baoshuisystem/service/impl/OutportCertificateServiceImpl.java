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
import com.alibaba.excel.util.DateUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.OutportCertificate;
import com.newweb.mrli.ykport.baoshuisystem.mapper.OutportCertificateMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.OutportCertificateService;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 一日游出港证统计(暂时)
 *
 * @author zada
 * @date 2021-10-22 11:06:37
 */
@Service
@DS("slave2")
@AllArgsConstructor
public class OutportCertificateServiceImpl extends ServiceImpl<OutportCertificateMapper, OutportCertificate> implements OutportCertificateService {

	@Resource
	public OutportCertificateMapper outportCertificateMapper;

	@Override
	public IPage<List<OutportCertificate>> getList(Page<?> page, OutportCertificate outportCertificate){
		return outportCertificateMapper.getList(page, outportCertificate);
	}

	@Override
	public List<OutportCertificate> getListExcel(OutportCertificate outportCertificate){
		return outportCertificateMapper.getListExcel(outportCertificate);
	}

	@Override
	public void exportExcel(OutportCertificate outportCertificate, List<OutportCertificate> list, HttpServletResponse response) throws IOException {
		// 获取标题名称
		String fileName = "一日游出港证登记表";

		// 告诉浏览器用什么软件打开此文件
		response.setHeader("content-Type","application/vnd.ms-excel");
		// 下载文件的默认名称
		response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));

		ClassPathResource classPathResource = new ClassPathResource("/file/oneday_clearing_label.xlsx");
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
			row.getCell(1).setCellValue(list.get(i).getRecordDte()==null?"": DateUtil.format(list.get(i).getRecordDte(), DatePattern.NORM_DATE_PATTERN));
			row.getCell(2).setCellValue(list.get(i).getCargoNam()==null?"0":list.get(i).getCargoNam());
			row.getCell(3).setCellValue(list.get(i).getPiecesNum()==null?"0":list.get(i).getPiecesNum().toString());

			row.getCell(4).setCellValue(list.get(i).getCarNo()==null?"0":list.get(i).getCarNo());
			row.getCell(5).setCellValue(list.get(i).getOutportNo()==null?"0":list.get(i).getOutportNo());
			row.getCell(6).setCellValue(list.get(i).getAgentNam()==null?"0":list.get(i).getAgentNam());
			row.getCell(7).setCellValue(list.get(i).getOperNam()==null?"0":list.get(i).getOperNam());
			row.getCell(8).setCellValue(list.get(i).getOutportRemark()==null?"0":list.get(i).getOutportRemark());
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
