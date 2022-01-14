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

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.CSubunit;
import com.newweb.mrli.ykport.baoshuisystem.entity.MStoreStatus;
import com.newweb.mrli.ykport.baoshuisystem.mapper.MStoreStatusMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.MStoreStatusService;
import com.newweb.mrli.ykport.baoshuisystem.utils.ExcelUtil;
import com.newweb.mrli.ykport.baoshuisystem.vo.CUnitNamVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.CargoVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.ShipVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.ShipperDocVo;
import org.apache.ibatis.annotations.Select;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;

/**
 * 港存动态（局）
 *
 * @author pigx code generator
 * @date 2021-10-19 18:28:44
 */
@Service
@DS("slave2")
public class MStoreStatusServiceImpl extends ServiceImpl<MStoreStatusMapper, MStoreStatus> implements MStoreStatusService {

	@Autowired
	private MStoreStatusMapper mStoreStatusMapper;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@Override
	public List<CUnitNamVo> getCunitNam() {
		return mStoreStatusMapper.getCunitNam();
	}

	@Override
	public List<CargoVo> getCargoNam() {
		return mStoreStatusMapper.getCargoNam();
	}

	@Override
	public List<CSubunit> getSubunit(String uniCode) {
		return mStoreStatusMapper.getSubunit(uniCode);
	}

	@Override
	public IPage<ShipVo> getShip(Page<?> page, ShipVo shipVo) {
		return mStoreStatusMapper.getShip(page,shipVo);
	}

	@Override
	public List<ShipperDocVo> getShipperDoc() {
		return mStoreStatusMapper.getShipperDoc();
	}

	@Override
	public void printToExcel(MStoreStatus mStoreStatus) throws IOException {
		List<MStoreStatus> list = list(Wrappers.query(mStoreStatus));
		InputStream path = this.getClass().getResourceAsStream("/file/公司货物动态表.xls");
		HSSFWorkbook wb = new HSSFWorkbook(path);

		ExcelUtil.exportExcel("",wb,list,MStoreStatus.class,2,1,request,response);
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.createRow(list.size() + 1);
		HSSFCell cell0 = row.createCell(0);
		cell0.setCellValue("合计");
		HSSFRow row1 = sheet.getRow(1);
		int jianshu = 0;
		int hz = 0;
		int allJianshu = 0;
		BigDecimal allHz = new BigDecimal("0");
		for (int i = 0; i < 21; i++) {
			String stringCellValue = row1.getCell(i).getStringCellValue();
			if("件数".equals(stringCellValue)){
				jianshu = i;
			}
			if("重量".equals(stringCellValue)){
				hz = i;
			}
			if(jianshu !=0 && hz!=0){
				continue;
			}
		}
		for (int i = 0; i < list.size(); i++) {
			BigDecimal bigDecimal = new BigDecimal((list.get(i).getWeightWgt() == null?0:list.get(i).getWeightWgt())+"");
			allHz = allHz.add(bigDecimal);
			allJianshu += (list.get(i).getPiecesNum() == null?0:list.get(i).getPiecesNum());
		}
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		HSSFFont font = wb.createFont();
		font.setFontHeight((short)160);
		cellStyle.setFont(font);
		HSSFCell cell1 = row.createCell(jianshu);
		cell1.setCellValue(allJianshu);
		cell1.setCellStyle(cellStyle);
		HSSFCell cell2 = row.createCell(hz);
		cell2.setCellValue(allHz+"");
		cell2.setCellStyle(cellStyle);
		response.setContentType("application/binary;charset=UTF-8");
		OutputStream output = response.getOutputStream();
		String fn = new String(new String("公司货物动态表").getBytes(), "UTF-8");
		response.setHeader("ContentDisposition", URLEncoder.encode("" + fn + ".xls","UTF-8"));
		wb.write(output);
		output.flush();
		output.close();
	}
}
