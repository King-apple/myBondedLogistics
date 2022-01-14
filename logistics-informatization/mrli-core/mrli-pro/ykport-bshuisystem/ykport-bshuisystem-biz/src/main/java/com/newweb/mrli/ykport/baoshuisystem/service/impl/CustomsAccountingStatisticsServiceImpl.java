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
import com.alibaba.excel.util.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.CustomsAccountingStatistics;
import com.newweb.mrli.ykport.baoshuisystem.entity.ParameterList;
import com.newweb.mrli.ykport.baoshuisystem.mapper.CustomsAccountingStatisticsMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.ParameterListMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.CustomsAccountingStatisticsService;
import com.newweb.mrli.ykport.baoshuisystem.service.InoutYardHeadService;
import com.newweb.mrli.ykport.baoshuisystem.vo.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

//import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_CENTER;

/**
 * 海关账册录入统计表
 *
 * @author ly
 * @date 2021-08-30 18:00:02
 */
@Service
public class CustomsAccountingStatisticsServiceImpl extends ServiceImpl<CustomsAccountingStatisticsMapper, CustomsAccountingStatistics> implements CustomsAccountingStatisticsService {
	@Autowired
	private CustomsAccountingStatisticsMapper customsAccountingStatisticsMapper;
	@Autowired
	private ParameterListMapper parameterListMapper;
	@Autowired
	private InoutYardHeadService inoutYardHeadService;

	@Override
	public IPage<CustomsAccountingStatisticsVo> selectPageVo(Page<?> page, CustomsAccountingStatisticsVo customsAccountingStatisticsvo) {
		return customsAccountingStatisticsMapper.selectPageVo(page, customsAccountingStatisticsvo);
	}

	@Override
	public List<CustomsAccountingStatistics> selectAll() {
		List<CustomsAccountingStatistics> customsAccountingStatistics = baseMapper.selectList(null);
		return customsAccountingStatistics;
	}

	@Override
	public List<CustomsVo> getManipulated(CustomsVo customsVo){
		return customsAccountingStatisticsMapper.getManipulated(customsVo);
	}

	@Override
	public List<CustomsCargoVo> getManipulatedCargo(CustomsCargoVo customsCargoVo){
		return customsAccountingStatisticsMapper.getManipulatedCargo(customsCargoVo);
	}

	@Override
	public List<CustomsOutAmountVo> getOutAmount(CustomsOutAmountVo customsOutAmountVo){
		return customsAccountingStatisticsMapper.getOutAmount(customsOutAmountVo);
	}

	@Override
	public List<CargoKindTypeVo> getCargoKind(CargoKindTypeVo cargoKindTypeVo){
		return customsAccountingStatisticsMapper.getCargoKind(cargoKindTypeVo);
	}

	@Override
	public List<CustomsVo> getManipulatedForNew(CustomsVo customsVo){
		return customsAccountingStatisticsMapper.getManipulatedForNew(customsVo);
	}

	@Override
	public List<CustomsAccountingStatistics> selectList() {
		QueryWrapper<CustomsAccountingStatistics> queryWrapper = new QueryWrapper();
		queryWrapper.isNull("del_nam ");
		queryWrapper.isNull("del_tim");
		List<CustomsAccountingStatistics> list = baseMapper.selectList(queryWrapper);
		return list;
	}

	// 自适应宽度(中文支持)
	private void setSizeColumn(HSSFSheet sheet) {
		for (int columnNum = 0; columnNum <= 26; columnNum++) {
			int columnWidth = sheet.getColumnWidth(columnNum) / 256;
			for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
				HSSFRow currentRow;
				//当前行未被使用过
				if (sheet.getRow(rowNum) == null) {
					currentRow = sheet.createRow(rowNum);
				} else {
					currentRow = sheet.getRow(rowNum);
				}

				if (currentRow.getCell(columnNum) != null) {
					HSSFCell currentCell = currentRow.getCell(columnNum);
					if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
						int length = currentCell.getStringCellValue().getBytes().length;
						if (columnWidth < length) {
							columnWidth = length;
						}
					}
				}
			}
			sheet.setColumnWidth(columnNum, columnWidth * 256);
		}
	}

	/*@Override
	public void expoprtExcel(CustomsAccountingStatisticsVo customsAccountingStatisticsVo, HttpServletResponse response, Page page) throws IOException {
		IPage<CustomsAccountingStatisticsVo> pages = baseMapper.selectPageVo(page, customsAccountingStatisticsVo);
		List<CustomsAccountingStatisticsVo> list = pages.getRecords();

		// 获取标题名称


		OutputStream output = null;
		//创建HSSFWorkbook对象(excel的文档对象)
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle headerStyle = wb.createCellStyle();
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		//建立新的sheet对象（excel的表单）
		HSSFSheet sheet = wb.createSheet("海关账册统计");
		//在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
		HSSFRow row1 = sheet.createRow(0);
		//创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
		HSSFCell cell = row1.createCell(0);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("海关账册统计表");
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 25));
		HSSFRow row2 = sheet.createRow(1);
		//创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
		HSSFCell cell2 = row2.createCell(0);
		cell2.setCellValue("入库");
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 13));
		cell2.setCellStyle(headerStyle);
		HSSFCell cell3 = row2.createCell(14);
		cell3.setCellValue("出库");
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 14, 25));
		cell3.setCellStyle(headerStyle);
		HSSFRow row3 = sheet.createRow(2);
		String[] headers = {"货主", "货品名", "入库日期", "入库毛重(kg)", "入库净重(kg)", "入库货品额(美元)", "原产国(入库)", "最终目的地(入库)", "核注清单号(入库)", "核对联(入库)", "报关单号(入库)",
				"识别代码(入库)", "货票号(入库)", "类别(进区)", "出库日期", "出库毛重(kg)", "出库净重(美元)", "出库货品额(美元)", "原产国(出库)", "最终目的地(出库)", "核注清单号(出库)", "核对联（出库）", "报关单号(出库)", "识别代码(出库)", "货票号(出库)", "类别(出区)"};
		for (int j = 0; j < headers.length; j++) {
			HSSFCell cellHeader = row3.createCell(j);
			HSSFRichTextString text = new HSSFRichTextString(headers[j]);
			cellHeader.setCellValue(text);
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < list.size(); i++) {
			HSSFRow rowI = sheet.createRow(i + 3); // 获得工作薄的第i行
			String inDte = "";
			String outDte = "";
			// 设置日期格式
			if (list.get(i).getInDte() != null) {
				inDte = simpleDateFormat.format(list.get(i).getInDte());
			}
			if (list.get(i).getOutDte() != null) {
				outDte = simpleDateFormat.format(list.get(i).getOutDte());
			}
			rowI.createCell(0).setCellValue(list.get(i).getConsignNam());
			rowI.createCell(1).setCellValue(list.get(i).getCargoNamCustoms());
			rowI.createCell(2).setCellValue(inDte);
			rowI.createCell(3).setCellValue(list.get(i).getInRoughWet());
			rowI.createCell(4).setCellValue(list.get(i).getInNetWet());
			rowI.createCell(5).setCellValue(list.get(i).getInCargoAccount());

			rowI.createCell(6).setCellValue(list.get(i).getInOriginCountry());
			rowI.createCell(7).setCellValue(list.get(i).getInFinalDestination());
			rowI.createCell(8).setCellValue(list.get(i).getInCheckNum());
			rowI.createCell(9).setCellValue(list.get(i).getInNuclearCouplet());
			rowI.createCell(10).setCellValue(list.get(i).getInCustomsDeclarationNum());
			rowI.createCell(11).setCellValue(list.get(i).getInIdentificationCod());
			rowI.createCell(12).setCellValue(list.get(i).getInWaybillNum());
			rowI.createCell(13).setCellValue(list.get(i).getInCategory());
			rowI.createCell(14).setCellValue(outDte);
			rowI.createCell(15).setCellValue(list.get(i).getOutRoughWet());
			rowI.createCell(16).setCellValue(list.get(i).getOutNetWet());
			rowI.createCell(17).setCellValue(list.get(i).getOutCargoAccount());
			rowI.createCell(18).setCellValue(list.get(i).getOutOriginCountry());
			rowI.createCell(19).setCellValue(list.get(i).getOutFinalDestination());
			rowI.createCell(20).setCellValue(list.get(i).getOutCheckNum());
			rowI.createCell(21).setCellValue(list.get(i).getOutNuclearCouplet());
			rowI.createCell(22).setCellValue(list.get(i).getOutCustomsDeclarationNum());
			rowI.createCell(23).setCellValue(list.get(i).getOutIdentificationCod());
			rowI.createCell(24).setCellValue(list.get(i).getOutWaybillNum());
			rowI.createCell(25).setCellValue(list.get(i).getOutCategory());

		}
		this.setSizeColumn(sheet);
		output = response.getOutputStream();
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename=details.xls");
		response.setContentType("application/msexcel");
		wb.write(output);
		output.close();
	}*/

	@Override
	public void expoprtManipulatedExcel(CustomsVo customsVo, List<CustomsVo> list, HttpServletResponse response) throws IOException {
		// 获取标题名称
		customsVo.getInDte().substring(5,7);
		String fileName = "保税物流中心"+customsVo.getInDte().substring(5,7)+"月份各部门操作量";

//		ParameterList parameter = parameterListMapper.selectValueByCode("customs_account_statistical_name");
//		if (parameter != null && !StringUtil.isEmpty(parameter.getParamValue())) {
//			fileName = parameter.getParamValue();
//		}

		// 告诉浏览器用什么软件打开此文件
		response.setHeader("content-Type","application/vnd.ms-excel");
		// 下载文件的默认名称
		response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));

		ClassPathResource classPathResource = new ClassPathResource("/file/customs_manipulated.xlsx");
		InputStream fileInput = classPathResource.getInputStream();
		OutputStream output = null;
		//创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wb = new XSSFWorkbook(fileInput);
		//对应Excel表中的sheet,0代表第一个
		XSSFSheet sheet = wb.getSheetAt(0);

		sheet.getRow(0).getCell(0).setCellValue(fileName);// 标题行
		sheet.getRow(1).getCell(0).setCellValue(customsVo.getInDte().substring(5,7)+"月份");// 副标题行

		// 处理数据
		int index = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		for (int i = 0; i < list.size(); i++) {
			XSSFRow row = sheet.getRow(i + 4);
			row.getCell(0).setCellValue(i+1);
			row.getCell(1).setCellValue(list.get(i).getCargoNamCustoms()==null?"":list.get(i).getCargoNamCustoms());
			row.getCell(2).setCellValue(list.get(i).getMInNetWet()==null?"0":list.get(i).getMInNetWet());
			row.getCell(3).setCellValue(list.get(i).getMInFirst()==null?"0":list.get(i).getMInFirst());
			row.getCell(4).setCellValue(list.get(i).getMInSecond()==null?"0":list.get(i).getMInSecond());
			row.getCell(5).setCellValue(list.get(i).getMOutNetWet()==null?"":list.get(i).getMOutNetWet());
			row.getCell(6).setCellValue(list.get(i).getMOutFirst()==null?"0":list.get(i).getMOutFirst());
			row.getCell(7).setCellValue(list.get(i).getMOutSecond()==null?"0":list.get(i).getMOutSecond());

			row.getCell(9).setCellValue(list.get(i).getYInNetWet()==null?"0":list.get(i).getYInNetWet());
			row.getCell(10).setCellValue(list.get(i).getYInFirst()==null?"0":list.get(i).getYInFirst());
			row.getCell(11).setCellValue(list.get(i).getYOutFirst()==null?"0":list.get(i).getYOutFirst());
			row.getCell(12).setCellValue(list.get(i).getYOutNetWet()==null?"0":list.get(i).getYOutNetWet());
			row.getCell(13).setCellValue(list.get(i).getYOutFirst()==null?"0":list.get(i).getYOutFirst());
			row.getCell(14).setCellValue(list.get(i).getYOutSecond()==null?"0":list.get(i).getYOutSecond());

			index++;
			if (index < list.size()) {
				sheet.shiftRows(i+4, i+5, 1,true,false);
				sheet.copyRows(i+5,i+5, i+4,new CellCopyPolicy());
			}
		}

		output = response.getOutputStream();
		wb.write(output);
		output.close();
		fileInput.close();
		wb.close();

	}


	@Override
	public void expoprtManipulatedForNewExcel(CustomsVo customsVo, List<CustomsVo> list, HttpServletResponse response) throws IOException {
		Double monthCZL=0.0; //月操作量
		Double monthMYE=0.0; //一线出口贸易额
		Double yearCZL=0.0; //年操作量
		Double yearCZLB=0.0; //年累计操作量同比
		Double yearHZ=0.0; //年累计进出口货值
		Double yearHZB=0.0; //年累计进出口货值同比
		Double monthOneDay=0.0;
		Double yearOneDay=0.0;
		String yearCZLFlg="下降";
		String yearHZFlg="下降";

		double[] arr = new double[12];

		for(CustomsVo detail:list){
			String aa = StringUtils.checkValNotNull(detail.getMInNetWet()) ? detail.getMInNetWet():"0";
			double ads = Double.parseDouble(aa);
			arr[0]+=Double.parseDouble(StringUtils.checkValNotNull(detail.getMInNetWet()) ? detail.getMInNetWet():"0");
			arr[1]+=Double.parseDouble(StringUtils.checkValNotNull(detail.getMOutNetWet()) ? detail.getMOutNetWet():"0");
			arr[2]+=Double.parseDouble(StringUtils.checkValNotNull(detail.getMOutCargoAccount()) ? detail.getMOutCargoAccount():"0");

			arr[3]+=Double.parseDouble(StringUtils.checkValNotNull(detail.getYInNetWet()) ? detail.getYInNetWet():"0");
			arr[4]+=Double.parseDouble(StringUtils.checkValNotNull(detail.getYInCargoAccount()) ? detail.getYInCargoAccount():"0");
			arr[5]+=Double.parseDouble(StringUtils.checkValNotNull(detail.getYOutNetWet()) ? detail.getYOutNetWet():"0");
			arr[6]+=Double.parseDouble(StringUtils.checkValNotNull(detail.getYOutCargoAccount()) ? detail.getYOutCargoAccount():"0");
			arr[7]+=Double.parseDouble(StringUtils.checkValNotNull(detail.getYFirstOutCargoAccount()) ? detail.getYFirstOutCargoAccount():"0");
			arr[8]+=Double.parseDouble(StringUtils.checkValNotNull(detail.getYSumNetWet()) ? detail.getYSumNetWet():"0");
			arr[9]+=Double.parseDouble(StringUtils.checkValNotNull(detail.getYNetWetScale().replace("%","")) ? detail.getYNetWetScale().replace("%",""):"0");
			arr[10]+=Double.parseDouble(StringUtils.checkValNotNull(detail.getYSumCargoAccount()) ? detail.getYSumCargoAccount():"0");
			arr[11]+=Double.parseDouble(StringUtils.checkValNotNull(detail.getYCargoAccountScale().replace("%","")) ? detail.getYCargoAccountScale().replace("%",""):"0");

			monthCZL+=Double.parseDouble(detail.getMInNetWet())+Double.parseDouble(detail.getMOutNetWet())/10000;
			monthMYE+=Double.parseDouble(detail.getMOutCargoAccount())/10000;
			yearCZL+= Double.parseDouble(detail.getYInNetWet())+Double.parseDouble(detail.getYOutNetWet())/10000;
			yearCZLB+=Double.parseDouble(detail.getYNetWetScale().replace("%",""));
			yearHZ+=Double.parseDouble(detail.getYSumCargoAccount())/10000;
			yearHZB+=Double.parseDouble(detail.getYCargoAccountScale().replace("%",""));
		}
		if(yearCZLB>0){
			yearCZLFlg = "上升";
		}
		if(yearHZB>0){
			yearHZFlg = "上升";
		}

		Map<String,String> map = new HashMap<String,String>();
		map.put("mTime",customsVo.getInDte());
		map.put("yOldStartTime",customsVo.getYearStart());
		map.put("yOldEndTime",customsVo.getYearEnd());
		Map<String,Object> mapInfo = inoutYardHeadService.getOneDayCount(map);
		String param = "_year_年_month_月操作量_monthCZL_万吨,一线出口贸易额_monthMYE_亿美元。_year_年1-_month_月" +
				"操作量_yearCZL_万吨,同比_oldyear_年_yearCZLFlg__yearCZLB_%,年累计进出口货值_yearHZ_亿美元,同比_yearHZFlg__yearHZB_%。一日游_month_月份完成_m_cnt_票," +
				"年累计完成_y_cnt_票";
		DecimalFormat df = new DecimalFormat("####0.000");
		param = param.replaceAll("_year_",customsVo.getInDte().substring(0,4));//
		param = param.replaceAll("_month_",customsVo.getInDte().substring(5,7));
		param = param.replaceAll("_monthCZL_",df.format(monthCZL));
		param = param.replaceAll("_monthMYE_",df.format(monthMYE));
		param = param.replaceAll("_yearCZL_",df.format(yearCZL));
		param = param.replaceAll("_oldyear_",customsVo.getOldYearStart().substring(0,4));
		param = param.replaceAll("_yearCZLFlg_",yearCZLFlg);
		param = param.replaceAll("_yearCZLB_",yearCZLB.toString());
		param = param.replaceAll("_yearHZ_",df.format(yearHZ));
		param = param.replaceAll("_yearHZFlg_",yearHZFlg);
		param = param.replaceAll("_yearHZB_",df.format(yearHZB));
		param = param.replaceAll("_m_cnt_",mapInfo.get("M_CNT").toString());
		param = param.replaceAll("_y_cnt_",mapInfo.get("Y_CNT").toString());


		// 获取标题名称
		customsVo.getInDte().substring(5,7);
//		String fileName = "保税物流中心"+customsVo.getInDte().substring(5,7)+"月份各部门操作量";
		String fileName = customsVo.getInDte().substring(0,4)+"年"+customsVo.getInDte().substring(5,7)+"月及01-"+customsVo.getInDte().substring(5,7)+"月保税货物数据统计";
//		ParameterList parameter = parameterListMapper.selectValueByCode("customs_account_statistical_name");
//		if (parameter != null && !StringUtil.isEmpty(parameter.getParamValue())) {
//			fileName = parameter.getParamValue();
//		}

		// 告诉浏览器用什么软件打开此文件
		response.setHeader("content-Type","application/vnd.ms-excel");
		// 下载文件的默认名称
		response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));

		ClassPathResource classPathResource = new ClassPathResource("/file/bonded_goods_statistics.xlsx");
		InputStream fileInput = classPathResource.getInputStream();
		OutputStream output = null;
		//创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wb = new XSSFWorkbook(fileInput);
		//对应Excel表中的sheet,0代表第一个
		XSSFSheet sheet = wb.getSheetAt(0);

		sheet.getRow(0).getCell(0).setCellValue(fileName);// 标题行
		sheet.getRow(1).getCell(0).setCellValue(customsVo.getInDte().substring(5,7)+"月份");// 副标题行

		// 处理数据
		int index = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		for (int i = 0; i < list.size(); i++) {
			XSSFRow row = sheet.getRow(i + 3);
			row.getCell(0).setCellValue(list.get(i).getCargoKindNam()==null?"":list.get(i).getCargoKindNam());
			row.getCell(1).setCellValue(list.get(i).getMInNetWet()==null?"0":list.get(i).getMInNetWet());
			row.getCell(2).setCellValue(list.get(i).getMOutNetWet()==null?"0":list.get(i).getMOutNetWet());
			row.getCell(3).setCellValue(list.get(i).getMOutCargoAccount()==null?"0":list.get(i).getMOutCargoAccount());

			row.getCell(5).setCellValue(list.get(i).getYInNetWet()==null?"":list.get(i).getYInNetWet());
			row.getCell(6).setCellValue(list.get(i).getYInCargoAccount()==null?"0":list.get(i).getYInCargoAccount());
			row.getCell(7).setCellValue(list.get(i).getYOutNetWet()==null?"0":list.get(i).getYOutNetWet());
			row.getCell(8).setCellValue(list.get(i).getYOutCargoAccount()==null?"0":list.get(i).getYOutCargoAccount());
			row.getCell(9).setCellValue(list.get(i).getYFirstOutCargoAccount()==null?"0":list.get(i).getYFirstOutCargoAccount());
			row.getCell(10).setCellValue(list.get(i).getYSumNetWet()==null?"0":list.get(i).getYSumNetWet());
			row.getCell(11).setCellValue(list.get(i).getYNetWetScale()==null?"0":list.get(i).getYNetWetScale());
			row.getCell(12).setCellValue(list.get(i).getYSumCargoAccount()==null?"0":list.get(i).getYSumCargoAccount());
			row.getCell(13).setCellValue(list.get(i).getYCargoAccountScale()==null?"0":list.get(i).getYCargoAccountScale());

			index++;
			if (index < list.size()) {
				sheet.shiftRows(i+3, i+4, 1,true,false);
				sheet.copyRows(i+4,i+4, i+3,new CellCopyPolicy());
			}
		}
		sheet.getRow(list.size()+3).getCell(1).setCellValue(arr[0]);
		sheet.getRow(list.size()+3).getCell(2).setCellValue(arr[1]);
		sheet.getRow(list.size()+3).getCell(3).setCellValue(arr[2]);
		sheet.getRow(list.size()+3).getCell(5).setCellValue(arr[3]);
		sheet.getRow(list.size()+3).getCell(6).setCellValue(arr[4]);
		sheet.getRow(list.size()+3).getCell(7).setCellValue(arr[5]);
		sheet.getRow(list.size()+3).getCell(8).setCellValue(arr[6]);
		sheet.getRow(list.size()+3).getCell(9).setCellValue(arr[7]);
		sheet.getRow(list.size()+3).getCell(10).setCellValue(arr[8]);
		sheet.getRow(list.size()+3).getCell(11).setCellValue(arr[9]);
		sheet.getRow(list.size()+3).getCell(12).setCellValue(arr[10]);
		sheet.getRow(list.size()+3).getCell(13).setCellValue(arr[11]);
		sheet.shiftRows(list.size()+3, list.size()+4, 1,true,false);
		sheet.copyRows(list.size()+4,list.size()+4, list.size()+3,new CellCopyPolicy());

		CellRangeAddress newCellRangeAddress = new CellRangeAddress(list.size()+4,list.size()+4,
				0,
				13);
		sheet.addMergedRegion(newCellRangeAddress);
		short asd = 600;
		CellStyle a = wb.createCellStyle();
		Font font = wb.createFont();
		font.setBold(true);
		a.setFont(font);
		a.setAlignment(HorizontalAlignment.LEFT);
		sheet.getRow(list.size()+4).setRowStyle(a);
		sheet.getRow(list.size()+4).setHeight(asd);
		sheet.getRow(list.size()+4).getCell(0).setCellValue(param);
		output = response.getOutputStream();
		wb.write(output);
		output.close();
		fileInput.close();
		wb.close();

	}

	@Override
	public void exportManipulatedCargoExcel(CustomsCargoVo customsVo,List<CustomsCargoVo> customsVoList,HttpServletResponse response) throws IOException{

		List<CustomsCargoVo> carGoList = new ArrayList<CustomsCargoVo>();
		Map<String,List<CustomsCargoVo>> userMap = customsVoList.stream().collect(Collectors.groupingBy(CustomsCargoVo::getConsignNam));
		System.out.println(userMap);
		for (Map.Entry<String, List<CustomsCargoVo>> entry : userMap.entrySet()) {
			System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue() + ", value = " + entry.getValue().get(0).getConsignNam());
			double mInNetWetValue = 0;
			double mOutNetWetValue = 0;
			double yInNetWetValue = 0;
			double yOutNetWetValue = 0;
			double yKucunValue = 0;
			for(int i=0;i<entry.getValue().size();i++){
				carGoList.add(entry.getValue().get(i));
				mInNetWetValue += Double.parseDouble(entry.getValue().get(i).getMInNetWet());
				mOutNetWetValue += Double.parseDouble(entry.getValue().get(i).getMOutNetWet());
				yInNetWetValue += Double.parseDouble(entry.getValue().get(i).getYInNetWet());
				yOutNetWetValue += Double.parseDouble(entry.getValue().get(i).getYOutNetWet());
				yKucunValue += Double.parseDouble(entry.getValue().get(i).getYKucun());

				if(entry.getValue().size()-1==i){
					CustomsCargoVo info = new CustomsCargoVo();
					info.setConsignNam("");
					info.setCargoKindNam("小计");
					info.setMInNetWet(String.valueOf(mInNetWetValue));
					info.setMOutNetWet(String.valueOf(mOutNetWetValue));
					info.setYInNetWet(String.valueOf(yInNetWetValue));
					info.setYOutNetWet(String.valueOf(yOutNetWetValue));
					info.setYKucun(String.valueOf(yKucunValue));
					carGoList.add(info);
				}
			}
		}



		// 获取标题名称
		customsVo.getInDte().substring(5,7);
		String fileName = "保税物流中心各货种操作量";

		// 告诉浏览器用什么软件打开此文件
		response.setHeader("content-Type","application/vnd.ms-excel");
		// 下载文件的默认名称
		response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));

		ClassPathResource classPathResource = new ClassPathResource("/file/bonded_type_good.xlsx");
		InputStream fileInput = classPathResource.getInputStream();
		OutputStream output = null;
		//创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wb = new XSSFWorkbook(fileInput);
		//对应Excel表中的sheet,0代表第一个
		XSSFSheet sheet = wb.getSheetAt(0);

		sheet.getRow(0).getCell(0).setCellValue(fileName);// 标题行
		sheet.getRow(1).getCell(0).setCellValue(DateUtils.format(new Date(),DateUtils.DATE_FORMAT_10));// 副标题行
		sheet.getRow(2).getCell(0).setCellValue("当月入库量");// 副标题行
		sheet.getRow(2).getCell(5).setCellValue("01-"+customsVo.getInDte().substring(5,7)+"月年累计出入库量");// 副标题行
		// 处理数据
		int index = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		for (int i = 0; i < carGoList.size(); i++) {
			XSSFRow row = sheet.getRow(i + 4);
			row.getCell(0).setCellValue(carGoList.get(i).getCargoKindNam()==null?"":carGoList.get(i).getCargoKindNam());
			row.getCell(1).setCellValue(carGoList.get(i).getConsignNam()==null?"":carGoList.get(i).getConsignNam());
			row.getCell(2).setCellValue(carGoList.get(i).getMInNetWet()==null?"0":carGoList.get(i).getMInNetWet());
			row.getCell(3).setCellValue(carGoList.get(i).getMOutNetWet()==null?"0":carGoList.get(i).getMOutNetWet());

			row.getCell(5).setCellValue(carGoList.get(i).getYInNetWet()==null?"0":carGoList.get(i).getYInNetWet());
			row.getCell(6).setCellValue(carGoList.get(i).getYOutNetWet()==null?"0":carGoList.get(i).getYOutNetWet());
			row.getCell(7).setCellValue(carGoList.get(i).getYKucun()==null?"0":carGoList.get(i).getYKucun());

			index++;
			if (index < carGoList.size()) {
				sheet.shiftRows(i+4, i+5, 1,true,false);
				sheet.copyRows(i+5,i+5, i+4,new CellCopyPolicy());
			}
		}

		output = response.getOutputStream();
		wb.write(output);
		output.close();
		fileInput.close();
		wb.close();
	}


	@Override
	public void exportOutAmountExcel(CustomsOutAmountVo customsOutAmountVo,List<CustomsOutAmountVo> list,HttpServletResponse response) throws IOException{
// 获取标题名称
		customsOutAmountVo.getInDte().substring(5,7);
		String fileName = "出口贸易额";

//		ParameterList parameter = parameterListMapper.selectValueByCode("customs_account_statistical_name");
//		if (parameter != null && !StringUtil.isEmpty(parameter.getParamValue())) {
//			fileName = parameter.getParamValue();
//		}

		// 告诉浏览器用什么软件打开此文件
		response.setHeader("content-Type","application/vnd.ms-excel");
		// 下载文件的默认名称
		response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));

		ClassPathResource classPathResource = new ClassPathResource("/file/total_import_and_export.xlsx");
		InputStream fileInput = classPathResource.getInputStream();
		OutputStream output = null;
		//创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wb = new XSSFWorkbook(fileInput);
		//对应Excel表中的sheet,0代表第一个
		XSSFSheet sheet = wb.getSheetAt(0);

		sheet.getRow(0).getCell(0).setCellValue(customsOutAmountVo.getYear()+"年");// 标题行
		sheet.getRow(1).getCell(1).setCellValue(customsOutAmountVo.getInDte().substring(5,7)+"月");// 副标题行

		// 处理数据
		int index = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		for (int i = 0; i < list.size(); i++) {
			XSSFRow row = sheet.getRow(i + 2);
			row.getCell(0).setCellValue(list.get(i).getCargoKindNam()==null?"":list.get(i).getCargoKindNam());
			row.getCell(1).setCellValue(list.get(i).getMOutCargoAccount()==null?"0":list.get(i).getMOutCargoAccount());
			row.getCell(2).setCellValue(list.get(i).getMComparedCargoAccount()==null?"0":list.get(i).getMComparedCargoAccount());
			row.getCell(3).setCellValue(list.get(i).getMRingCargoAccout()==null?"0":list.get(i).getMRingCargoAccout());
			row.getCell(4).setCellValue(list.get(i).getYearOutCargoAccount()==null?"":list.get(i).getYearOutCargoAccount());
			row.getCell(5).setCellValue(list.get(i).getYearComparedCargoAccount()==null?"0":list.get(i).getYearComparedCargoAccount());

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


	@Override
	public void expoprtExcel(CustomsAccountingStatisticsVo customsAccountingStatisticsVo, HttpServletResponse response, Page page) throws IOException {
//		IPage<CustomsAccountingStatisticsVo> pages = baseMapper.selectPageVo(page, customsAccountingStatisticsVo);
		List<CustomsAccountingStatisticsVo> list = baseMapper.getAll(customsAccountingStatisticsVo);

		// 获取标题名称
		String fileName = "保税操作部海关账册录入统计表";
		ParameterList parameter = parameterListMapper.selectValueByCode("customs_account_statistical_name");
		if (parameter != null && !StringUtil.isEmpty(parameter.getParamValue())) {
			fileName = parameter.getParamValue();
		}

		// 告诉浏览器用什么软件打开此文件
		response.setHeader("content-Type","application/vnd.ms-excel");
		// 下载文件的默认名称
		response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));

		ClassPathResource classPathResource = new ClassPathResource("/file/customs_accounting_statistics.xlsx");
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
			XSSFRow row = sheet.getRow(i + 3);

			row.getCell(0).setCellValue(list.get(i).getInDte()==null?"":sdf.format(list.get(i).getInDte()));
			row.getCell(1).setCellValue(list.get(i).getConsignNam()==null?"":list.get(i).getConsignNam());
			row.getCell(2).setCellValue(list.get(i).getCargoNamCustoms()==null?"":list.get(i).getCargoNamCustoms());
			row.getCell(3).setCellValue(list.get(i).getInRoughWet()==null?0:list.get(i).getInRoughWet());
			row.getCell(4).setCellValue(list.get(i).getInNetWet()==null?0:list.get(i).getInNetWet());
			row.getCell(5).setCellValue(list.get(i).getInCargoAccount()==null?0:list.get(i).getInCargoAccount());
			row.getCell(6).setCellValue(list.get(i).getInOriginCountry()==null?"":list.get(i).getInOriginCountry());
			row.getCell(7).setCellValue(list.get(i).getInFinalDestination()==null?"":list.get(i).getInFinalDestination());
			row.getCell(8).setCellValue(list.get(i).getInCheckNum()==null?"":list.get(i).getInCheckNum());
			row.getCell(9).setCellValue(list.get(i).getInNuclearCouplet()==null?"":list.get(i).getInNuclearCouplet());
			row.getCell(10).setCellValue(list.get(i).getInCustomsDeclarationNum()==null?"":list.get(i).getInCustomsDeclarationNum());
			row.getCell(11).setCellValue(list.get(i).getInIdentificationCod()==null?"":list.get(i).getInIdentificationCod());
			row.getCell(12).setCellValue(list.get(i).getInWaybillNum()==null?"":list.get(i).getInWaybillNum());
			row.getCell(13).setCellValue(list.get(i).getInCategory()==null?"":list.get(i).getInCategory());
			row.getCell(15).setCellValue(list.get(i).getOutDte()==null?"":sdf.format(list.get(i).getOutDte()));
			row.getCell(16).setCellValue(list.get(i).getOutRoughWet()==null?0:list.get(i).getOutRoughWet());
			row.getCell(17).setCellValue(list.get(i).getOutNetWet()==null?0:list.get(i).getOutNetWet());
			row.getCell(18).setCellValue(list.get(i).getOutCargoAccount()==null?0:list.get(i).getOutCargoAccount());
			row.getCell(19).setCellValue(list.get(i).getOutOriginCountry()==null?"":list.get(i).getOutOriginCountry());
			row.getCell(20).setCellValue(list.get(i).getOutFinalDestination()==null?"":list.get(i).getOutFinalDestination());
			row.getCell(21).setCellValue(list.get(i).getOutCheckNum()==null?"":list.get(i).getOutCheckNum());
			row.getCell(22).setCellValue(list.get(i).getOutNuclearCouplet()==null?"":list.get(i).getOutNuclearCouplet());
			row.getCell(23).setCellValue(list.get(i).getOutCustomsDeclarationNum()==null?"":list.get(i).getOutCustomsDeclarationNum());
			row.getCell(24).setCellValue(list.get(i).getOutIdentificationCod()==null?"":list.get(i).getOutIdentificationCod());
			row.getCell(25).setCellValue(list.get(i).getOutWaybillNum()==null?"":list.get(i).getOutWaybillNum());
			row.getCell(26).setCellValue(list.get(i).getOutCategory()==null?"":list.get(i).getOutCategory());

			index++;
			if (index < list.size()) {
				sheet.shiftRows(i+3, i+4, 1,true,false);
				sheet.copyRows(i+4,i+4, i+3,new CellCopyPolicy());
			}
		}

		output = response.getOutputStream();
		wb.write(output);
		output.close();
		fileInput.close();
		wb.close();
	}


	@Override
	public void exportCargoKindExcel(CargoKindTypeVo cargoKindTypeVo,List<CargoKindTypeVo> list,HttpServletResponse response) throws IOException{

		// 获取标题名称
		String fileName = cargoKindTypeVo.getCargoKindNam()+"货类信息";
		ParameterList parameter = parameterListMapper.selectValueByCode("customs_account_statistical_name");
		if (parameter != null && !StringUtil.isEmpty(parameter.getParamValue())) {
			fileName = parameter.getParamValue();
		}

		// 告诉浏览器用什么软件打开此文件
		response.setHeader("content-Type","application/vnd.ms-excel");
		// 下载文件的默认名称
		response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));

		ClassPathResource classPathResource = new ClassPathResource("/file/cargo_type_detail_export.xlsx");
		InputStream fileInput = classPathResource.getInputStream();
		OutputStream output = null;
		//创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wb = new XSSFWorkbook(fileInput);
		//对应Excel表中的sheet,0代表第一个
		XSSFSheet sheet = wb.getSheetAt(0);

		sheet.getRow(0).getCell(0).setCellValue(cargoKindTypeVo.getCargoKindNam());// 标题行
		sheet.getRow(1).getCell(1).setCellValue(cargoKindTypeVo.getNowYear());
		sheet.getRow(1).getCell(2).setCellValue(cargoKindTypeVo.getOldYear());
		sheet.getRow(1).getCell(4).setCellValue(cargoKindTypeVo.getNowYear());
		sheet.getRow(1).getCell(5).setCellValue(cargoKindTypeVo.getOldYear());


		// 处理数据
		int index = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		for (int i = 0; i < list.size(); i++) {
			XSSFRow row = sheet.getRow(i + 3);

			row.getCell(0).setCellValue(list.get(i).getMon()==null?"":list.get(i).getMon());
			row.getCell(1).setCellValue(list.get(i).getYearNewInNetWet()==null?"":list.get(i).getYearNewInNetWet());
			row.getCell(2).setCellValue(list.get(i).getYearOldInNetWet()==null?"":list.get(i).getYearOldInNetWet());
			row.getCell(3).setCellValue(list.get(i).getYearInComparedWet()==null?"":list.get(i).getYearInComparedWet());
			row.getCell(4).setCellValue(list.get(i).getYearNewOutNetWet()==null?"":list.get(i).getYearNewOutNetWet());
			row.getCell(5).setCellValue(list.get(i).getYearOldOutNetWet()==null?"":list.get(i).getYearOldOutNetWet());
			row.getCell(6).setCellValue(list.get(i).getYearOutComparedWet()==null?"":list.get(i).getYearOutComparedWet());


			index++;
			if (index < list.size()) {
				sheet.shiftRows(i+3, i+4, 1,true,false);
				sheet.copyRows(i+4,i+4, i+3,new CellCopyPolicy());
			}
		}

		output = response.getOutputStream();
		wb.write(output);
		output.close();
		fileInput.close();
		wb.close();
	}

	@Override
	public CustomsAccountingStatisticsVo countAll(CustomsAccountingStatisticsVo customsAccountingStatisticsVo) {

		return baseMapper.finalSql(customsAccountingStatisticsVo);
	}

}


