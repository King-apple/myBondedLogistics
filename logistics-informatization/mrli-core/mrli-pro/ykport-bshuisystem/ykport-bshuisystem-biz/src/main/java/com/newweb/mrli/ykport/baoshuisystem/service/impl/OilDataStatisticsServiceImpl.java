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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.ykport.baoshuisystem.entity.OilDataStatistics;
import com.newweb.mrli.ykport.baoshuisystem.entity.ParameterList;
import com.newweb.mrli.ykport.baoshuisystem.mapper.OilDataStatisticsMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.ParameterListMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.OilDataStatisticsService;
import com.newweb.mrli.ykport.baoshuisystem.vo.OilDataStatisticsVo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 油品录入数据统计表
 *
 * @author Tara
 * @date 2021-10-19 09:48:47
 */
@Service
public class OilDataStatisticsServiceImpl extends ServiceImpl<OilDataStatisticsMapper, OilDataStatistics> implements OilDataStatisticsService {

	@Autowired
	private OilDataStatisticsMapper oilDataStatisticsMapper;
	@Autowired
	private ParameterListMapper parameterListMapper;

	@Override
	public IPage<OilDataStatisticsVo> selectPageData(Page<?> page, OilDataStatisticsVo oilDataStatisticsVo) {
		return oilDataStatisticsMapper.selectPageData(page,oilDataStatisticsVo);
	}

	@Override
	public void expoprtExcel(OilDataStatisticsVo oilDataStatisticsVo, HttpServletResponse response, Page page) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<OilDataStatisticsVo> list = oilDataStatisticsMapper.selectOilDataStatisticsVoList(oilDataStatisticsVo);

		// 获取入库更新时间和出库更新时间
//		List<OilDataStatisticsVo> listSortIn = list;
//		listSortIn.sort(Comparator.comparing(OilDataStatisticsVo::getInUpdateDte).reversed());
//		Date inUpdateDte = listSortIn.get(0).getInUpdateDte();
//		String inUpdateDteStr = inUpdateDte == null ? "" : sdf.format(inUpdateDte);
//		List<OilDataStatisticsVo> listSortOut = list;
//		listSortOut.sort(Comparator.comparing(OilDataStatisticsVo::getOutUpdateDte).reversed());
//		Date OutUpdateDte = listSortIn.get(0).getOutUpdateDte();
//		String OutUpdateDteStr = OutUpdateDte == null ? "" : sdf.format(OutUpdateDte);

		// 获取标题名称
		String fileName = "油品录入数据统计表";
		ParameterList parameter = parameterListMapper.selectValueByCode("oil_data_statistics_name");
		if (parameter != null && !StringUtil.isEmpty(parameter.getParamValue())) {
			fileName = parameter.getParamValue();
		}

		String fileNameIn = "油品入库数据统计表";
		ParameterList parameterIn = parameterListMapper.selectValueByCode("oil_data_statistics_in_name");
		if (parameterIn != null && !StringUtil.isEmpty(parameterIn.getParamValue())) {
			fileNameIn = parameterIn.getParamValue();
		}

		String fileNameOut = "油品出库数据统计表";
		ParameterList parameterOut = parameterListMapper.selectValueByCode("oil_data_statistics_out_name");
		if (parameterOut != null && !StringUtil.isEmpty(parameterOut.getParamValue())) {
			fileNameOut = parameterOut.getParamValue();
		}

		// 告诉浏览器用什么软件打开此文件
		response.setHeader("content-Type","application/vnd.ms-excel");
		// 下载文件的默认名称
		response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));

		ClassPathResource classPathResource = new ClassPathResource("/file/oil_data_statistics.xlsx");
		InputStream fileInput = classPathResource.getInputStream();
		OutputStream output = null;
		//创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wb = new XSSFWorkbook(fileInput);
		//对应Excel表中的sheet,0代表第一个
		XSSFSheet sheet = wb.getSheetAt(0);

		// 插入标题数据
		sheet.getRow(0).getCell(0).setCellValue(fileNameIn);
		sheet.getRow(0).getCell(17).setCellValue(fileNameOut);

		// 插入出入库总数据
		String inAccountNum = list.get(0).getInAccountNum();
		String inOperationUnit = list.get(0).getInOperationUnit();
		String inOperationUnitCod = list.get(0).getInOperationUnitCod();
		String outAccountNum = list.get(0).getOutAccountNum();
		String outOperationUnit = list.get(0).getOutOperationUnit();
		String outOperationUnitCod = list.get(0).getOutOperationUnitCod();
		sheet.getRow(1).getCell(3).setCellValue(inAccountNum == null ? "" : inAccountNum);
		sheet.getRow(1).getCell(7).setCellValue(inOperationUnit == null ? "" : inOperationUnit);
		sheet.getRow(1).getCell(12).setCellValue(inOperationUnitCod == null ? "" : inOperationUnitCod);
		sheet.getRow(1).getCell(19).setCellValue(outAccountNum == null ? "" : outAccountNum);
		sheet.getRow(1).getCell(23).setCellValue(outOperationUnit == null ? "" : outOperationUnit);
		sheet.getRow(1).getCell(28).setCellValue(outOperationUnitCod == null ? "" : outOperationUnitCod);

		// 处理列表数据
		int index = 0;
		String inUpdateDteStr = "";
		String outUpdateDteStr = "";
		for (int i = 0; i < list.size(); i++) {
			XSSFRow row = sheet.getRow(i + 4);
			OilDataStatisticsVo itemData = list.get(i);
			if (itemData.getInUpdateDte() != null) {
				if (StringUtil.isEmpty(inUpdateDteStr)) {
					inUpdateDteStr = sdf.format(itemData.getInUpdateDte());
				} else {
					String inUpdateDteStrNew = sdf.format(itemData.getInUpdateDte());
					if (inUpdateDteStrNew.compareTo(inUpdateDteStr) > 0) {
						inUpdateDteStr = inUpdateDteStrNew;
					}
				}
			}
			if (itemData.getOutUpdateDte() != null) {
				if (StringUtil.isEmpty(outUpdateDteStr)) {
					outUpdateDteStr = sdf.format(itemData.getOutUpdateDte());
				} else {
					String outUpdateDteStrNew = sdf.format(itemData.getOutUpdateDte());
					if (outUpdateDteStrNew.compareTo(outUpdateDteStr) > 0) {
						outUpdateDteStr = outUpdateDteStrNew;
					}
				}
			}

			row.getCell(0).setCellValue(index + 1);
			row.getCell(1).setCellValue(itemData.getOilKnd() == null ? "" : itemData.getOilKnd());
			row.getCell(2).setCellValue(itemData.getLoginAccount() == null ? "" : itemData.getLoginAccount());
			row.getCell(3).setCellValue(itemData.getInAddDte() == null ? "" : sdf.format(itemData.getInAddDte()));
			row.getCell(4).setCellValue(itemData.getInConsignNam() == null ? "" : itemData.getInConsignNam());
			row.getCell(5).setCellValue(itemData.getInCargoNam() == null ? "" : itemData.getInCargoNam());
			row.getCell(6).setCellValue(itemData.getInType() == null ? "" : itemData.getInType());
			row.getCell(7).setCellValue(itemData.getInBlno() == null ? "" : itemData.getInBlno());
			row.getCell(8).setCellValue(itemData.getInStorageTank() == null ? "" : itemData.getInStorageTank());
			row.getCell(9).setCellValue(itemData.getInQuantityPlan() == null ? "" : itemData.getInQuantityPlan() + "");
			row.getCell(10).setCellValue(itemData.getInDensityStandard() == null ? "" : itemData.getInDensityStandard());
			row.getCell(11).setCellValue(itemData.getInDensity() == null ? "" : itemData.getInDensity() + "");
			row.getCell(12).setCellValue(itemData.getInStartTim() == null ? "" : sdf.format(itemData.getInStartTim()));
			row.getCell(13).setCellValue(itemData.getInEndTim() == null ? "" : sdf.format(itemData.getInEndTim()));
			row.getCell(14).setCellValue(itemData.getInConfirmTim() == null ? "" : sdf.format(itemData.getInConfirmTim()));
			row.getCell(15).setCellValue(itemData.getInQuantityReal() == null ? "" : itemData.getInQuantityReal() + "");
			row.getCell(16).setCellValue(itemData.getInCustomsDeclarationNum() == null ? "" : itemData.getInCustomsDeclarationNum());
			row.getCell(17).setCellValue(index + 1);
			row.getCell(18).setCellValue(itemData.getOutAddDte() == null ? "" : sdf.format(itemData.getOutAddDte()));
			row.getCell(19).setCellValue(itemData.getOutConsignNam() == null ? "" : itemData.getOutConsignNam());
			row.getCell(20).setCellValue(itemData.getOutCargoNam() == null ? "" : itemData.getOutCargoNam());
			row.getCell(21).setCellValue(itemData.getOutType() == null ? "" : itemData.getOutType());
			row.getCell(22).setCellValue(itemData.getOutBlno() == null ? "" : itemData.getOutBlno());
			row.getCell(23).setCellValue(itemData.getOutStorageTank() == null ? "" : itemData.getOutStorageTank());
			row.getCell(24).setCellValue(itemData.getOutQuantityPlan() == null ? "" : itemData.getOutQuantityPlan() + "");
			row.getCell(25).setCellValue(itemData.getOutDensityStandard() == null ? "" : itemData.getOutDensityStandard());
			row.getCell(26).setCellValue(itemData.getOutDensity() == null ? "" : itemData.getOutDensity() + "");
			row.getCell(27).setCellValue(itemData.getOutStartTim() == null ? "" : sdf.format(itemData.getOutStartTim()));
			row.getCell(28).setCellValue(itemData.getOutEndTim() == null ? "" : sdf.format(itemData.getOutEndTim()));
			row.getCell(29).setCellValue(itemData.getOutConfirmTim() == null ? "" : sdf.format(itemData.getOutConfirmTim()));
			row.getCell(30).setCellValue(itemData.getOutQuantityReal() == null ? "" : itemData.getOutQuantityReal() + "");
			row.getCell(31).setCellValue(itemData.getOutCustomsDeclarationNum() == null ? "" : itemData.getOutCustomsDeclarationNum());
			row.getCell(32).setCellValue(itemData.getInventory() == null ? "" : itemData.getInventory() + "");

			index++;
			if (index < list.size()) {
				sheet.shiftRows(i+4, i+5, 1,true,false);
				sheet.copyRows(i+5,i+5, i+4,new CellCopyPolicy());
			}
		}
		// 插入出入库更新日期
		sheet.getRow(1).getCell(15).setCellValue(inUpdateDteStr);
		sheet.getRow(1).getCell(31).setCellValue(outUpdateDteStr);

		output = response.getOutputStream();
		wb.write(output);
		output.close();
		fileInput.close();
		wb.close();
	}

	@Override
	public String exportCheckBefore(OilDataStatisticsVo oilDataStatisticsVo) {
		String msg = null;
		List<OilDataStatisticsVo> list = oilDataStatisticsMapper.selectOilDataStatisticsVoList(oilDataStatisticsVo);
		if (list != null && list.size() > 0) {
			String inOperationUnit = list.get(0).getInOperationUnit();
			String outOperationUnit = list.get(0).getOutOperationUnit();
			for (OilDataStatisticsVo item : list) {
				if (!item.getInOperationUnit().equals(inOperationUnit)) {
					msg = "查询到的数据入库经营单位不同，不可同时导出";
					break;
				}
				if (!item.getOutOperationUnit().equals(outOperationUnit)) {
					msg = "查询到的数据出库经营单位不同，不可同时导出";
					break;
				}
			}
		} else {
			msg = "未查询到数据";
		}
		return msg;
	}

	@Override
	public void downloadTemplate(HttpServletResponse response, Page page) throws IOException {
		// 获取标题名称
		String fileName = "油品录入数据统计表";
		ParameterList parameter = parameterListMapper.selectValueByCode("oil_data_statistics_name");
		if (parameter != null && !StringUtil.isEmpty(parameter.getParamValue())) {
			fileName = parameter.getParamValue();
		}

		String fileNameIn = "油品入库数据统计表";
		ParameterList parameterIn = parameterListMapper.selectValueByCode("oil_data_statistics_in_name");
		if (parameterIn != null && !StringUtil.isEmpty(parameterIn.getParamValue())) {
			fileNameIn = parameterIn.getParamValue();
		}

		String fileNameOut = "油品出库数据统计表";
		ParameterList parameterOut = parameterListMapper.selectValueByCode("oil_data_statistics_out_name");
		if (parameterOut != null && !StringUtil.isEmpty(parameterOut.getParamValue())) {
			fileNameOut = parameterOut.getParamValue();
		}

		// 告诉浏览器用什么软件打开此文件
		response.setHeader("content-Type","application/vnd.ms-excel");
		// 下载文件的默认名称
		response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));

		ClassPathResource classPathResource = new ClassPathResource("/file/oil_data_statistics.xlsx");
		InputStream fileInput = classPathResource.getInputStream();
		OutputStream output = null;
		//创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wb = new XSSFWorkbook(fileInput);
		//对应Excel表中的sheet,0代表第一个
		XSSFSheet sheet = wb.getSheetAt(0);

		// 插入标题数据
		sheet.getRow(0).getCell(0).setCellValue(fileNameIn);
		sheet.getRow(0).getCell(17).setCellValue(fileNameOut);

		output = response.getOutputStream();
		wb.write(output);
		output.close();
		fileInput.close();
		wb.close();
	}

	@Override
	@Transactional
	public R uploadOildatastatisticsExcel(MultipartFile files) throws IOException, InvalidFormatException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		OilDataStatistics oilDataStatistics = new OilDataStatistics();

		Workbook workbook = null;
		InputStream is = files.getInputStream();
		String name = files.getOriginalFilename().toLowerCase();
		// 创建excel操作对象
		if (name.contains(".xlsx") || name.contains(".xls")) {
			//使用工厂方法创建.
			workbook = WorkbookFactory.create(is);
		}
		//得到一个工作表
		Sheet sheet = workbook.getSheetAt(0);

		// 获取第二行的数据
		Row row1 = sheet.getRow(1);
		String inAccountNum = row1.getCell(3).getStringCellValue();
		oilDataStatistics.setInAccountNum(inAccountNum);
		String inOperationUnit = row1.getCell(7).getStringCellValue();
		oilDataStatistics.setInOperationUnit(inOperationUnit);
		String inOperationUnitCod = row1.getCell(12).getStringCellValue();
		oilDataStatistics.setInOperationUnitCod(inOperationUnitCod);
		Date inUpdateDte = row1.getCell(15).getDateCellValue();
		oilDataStatistics.setInUpdateDte(inUpdateDte);

		String outAccountNum = row1.getCell(19).getStringCellValue();
		oilDataStatistics.setOutAccountNum(outAccountNum);
		String outOperationUnit = row1.getCell(23).getStringCellValue();
		oilDataStatistics.setOutOperationUnit(outOperationUnit);
		String outOperationUnitCod = row1.getCell(25).getStringCellValue();
		oilDataStatistics.setOutOperationUnitCod(outOperationUnitCod);
		Date outUpdateDte = row1.getCell(28).getDateCellValue();
		oilDataStatistics.setOutUpdateDte(outUpdateDte);

		// 处理列表数据
		int index = 4;
		boolean finished = false;
		while (!finished) {
			Row row = sheet.getRow(index);
			if (row == null) {
				finished = true;
				break;
			}
			String oilKnd = row.getCell(1).getStringCellValue();
			if (StringUtil.isEmpty(oilKnd)) {
				finished = true;
				break;
			}
			oilDataStatistics.setOilKnd(oilKnd);
			String loginAccount = row.getCell(2).getStringCellValue();
			oilDataStatistics.setLoginAccount(loginAccount);
			Date inAddDte = row.getCell(3).getDateCellValue();
			oilDataStatistics.setInAddDte(inAddDte);
			String inConsignNam = row.getCell(4).getStringCellValue();
			oilDataStatistics.setInConsignNam(inConsignNam);
			String inCargoNam = row.getCell(5).getStringCellValue();
			oilDataStatistics.setInCargoNam(inCargoNam);
			String inType = row.getCell(6).getStringCellValue();
			oilDataStatistics.setInType(inType);
			row.getCell(7).setCellType(CellType.STRING);
			String inBlno = row.getCell(7).getStringCellValue();
			oilDataStatistics.setInBlno(inBlno);
			row.getCell(8).setCellType(CellType.STRING);
			String inStorageTank = row.getCell(8).getStringCellValue();
			oilDataStatistics .setInStorageTank(inStorageTank);
			Double inQuantityPlan = row.getCell(9).getNumericCellValue();
			oilDataStatistics.setInQuantityPlan(inQuantityPlan);
			String inDensityStandard = row.getCell(10).getStringCellValue();
			oilDataStatistics.setInDensityStandard(inDensityStandard);
			Double inDensity = row.getCell(11).getNumericCellValue();
			oilDataStatistics.setInDensity(inDensity);
			Date inStartTim = row.getCell(12).getDateCellValue();
			oilDataStatistics.setInStartTim(inStartTim);
			Date inEndTim = row.getCell(13).getDateCellValue();
			oilDataStatistics.setInEndTim(inEndTim);
			Date inConfirmTim = row.getCell(14).getDateCellValue();
			oilDataStatistics.setInConfirmTim(inConfirmTim);
			Double inQuantityReal = row.getCell(15).getNumericCellValue();
			oilDataStatistics.setInQuantityReal(inQuantityReal);
			String inCustomsDeclarationNum = row.getCell(16).getStringCellValue();
			oilDataStatistics.setInCustomsDeclarationNum(inCustomsDeclarationNum);

			Date outAddDte = row.getCell(18).getDateCellValue();
			oilDataStatistics.setOutAddDte(outAddDte);
			String outConsignNam = row.getCell(19).getStringCellValue();
			oilDataStatistics.setOutConsignNam(outConsignNam);
			String outCargoNam = row.getCell(20).getStringCellValue();
			oilDataStatistics.setOutCargoNam(outCargoNam);
			String outType = row.getCell(21).getStringCellValue();
			oilDataStatistics.setOutType(outType);
			row.getCell(22).setCellType(CellType.STRING);
			String outBlno = row.getCell(22).getStringCellValue();
			oilDataStatistics.setOutBlno(outBlno);
			row.getCell(23).setCellType(CellType.STRING);
			String outStorageTank = row.getCell(23).getStringCellValue();
			oilDataStatistics.setOutStorageTank(outStorageTank);
			Double outQuantityPlan = row.getCell(24).getNumericCellValue();
			oilDataStatistics.setOutQuantityPlan(outQuantityPlan);
			String outDensityStandard = row.getCell(25).getStringCellValue();
			oilDataStatistics.setOutDensityStandard(outDensityStandard);
			Double outDensity = row.getCell(26).getNumericCellValue();
			oilDataStatistics.setOutDensity(outDensity);
			Date outStartTim = row.getCell(27).getDateCellValue();
			oilDataStatistics.setOutStartTim(outStartTim);
			Date outEndTim = row.getCell(28).getDateCellValue();
			oilDataStatistics.setOutEndTim(outEndTim);
			Date outConfirmTim = row.getCell(29).getDateCellValue();
			oilDataStatistics.setOutConfirmTim(outConfirmTim);
			Double outQuantityReal = row.getCell(30).getNumericCellValue();
			oilDataStatistics.setOutQuantityReal(outQuantityReal);
			String outCustomsDeclarationNum = row.getCell(31).getStringCellValue();
			oilDataStatistics.setOutCustomsDeclarationNum(outCustomsDeclarationNum);
			Double inventory = row.getCell(32).getNumericCellValue();
			oilDataStatistics.setInventory(inventory);

			oilDataStatisticsMapper.insert(oilDataStatistics);
			index ++;
		}

		is.close();
		return R.ok("导入成功");
	}
}
