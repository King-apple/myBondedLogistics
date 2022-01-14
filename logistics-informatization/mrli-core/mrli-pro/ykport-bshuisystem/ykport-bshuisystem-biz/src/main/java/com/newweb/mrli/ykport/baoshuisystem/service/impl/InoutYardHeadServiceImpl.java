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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardHead;
import com.newweb.mrli.ykport.baoshuisystem.entity.ParameterList;
import com.newweb.mrli.ykport.baoshuisystem.mapper.GoodsCategoryMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.InoutYardHeadMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.InoutYardBodyService;
import com.newweb.mrli.ykport.baoshuisystem.service.InoutYardHeadService;
import com.newweb.mrli.ykport.baoshuisystem.service.ParameterListService;
import com.newweb.mrli.ykport.baoshuisystem.vo.InoutYardHeadVo;
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
import java.sql.Wrapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.util.Map;

/**
 * 出入库表头
 *
 * @author zada
 * @date 2021-09-02 18:43:01
 */
@Service
public class InoutYardHeadServiceImpl extends ServiceImpl<InoutYardHeadMapper, InoutYardHead> implements InoutYardHeadService {

	@Resource
	private InoutYardHeadMapper inoutYardHeadMapper;
	@Resource
	private InoutYardHeadService inoutYardHeadService;
	@Resource
	private InoutYardBodyService inoutYardBodyService;
	@Resource
	private ParameterListService parameterListService;

	/**
	 * 插入入库head数据
	 * @param inoutYardHead
	 * @return
	 */
	@Override
	public InoutYardHead saveForServer(InoutYardHead inoutYardHead){
//		Integer fid = inoutYardHeadMapper.selectInoutYardHeadFid();
		//获取单据名称
		ParameterList detail = new ParameterList();
		if(inoutYardHead.getInoutYardKnd().equals("保税物流中心入库")){
			ParameterList parameterList = new ParameterList();
			parameterList.setParamCod("in_bill_name_one");
			detail = parameterListService.getOne(Wrappers.query(parameterList));
			inoutYardHead.setInoutYardNam(detail.getParamValue());
		}

		if(inoutYardHead.getInoutYardKnd().equals("保税物流中心出库")){
			ParameterList parameterList = new ParameterList();
			parameterList.setParamCod("out_bill_name_one");
			detail = parameterListService.getOne(Wrappers.query(parameterList));
			inoutYardHead.setInoutYardNam(detail.getParamValue());
		}

		if(inoutYardHead.getInoutYardKnd().equals("保税仓库货物入库")){
			ParameterList parameterList = new ParameterList();
			parameterList.setParamCod("in_bill_name_two");
			detail = parameterListService.getOne(Wrappers.query(parameterList));
			inoutYardHead.setInoutYardNam(detail.getParamValue());

			ParameterList parameterList2 = new ParameterList();
			parameterList2.setParamCod("warehouse_name");
			detail = parameterListService.getOne(Wrappers.query(parameterList2));
//			inoutYardHead.setWarehouseNam(detail.getParamValue());

			ParameterList parameterList3 = new ParameterList();
			parameterList3.setParamCod("warehouse_name_zmdm");
			detail = parameterListService.getOne(Wrappers.query(parameterList3));
			inoutYardHead.setWarehouseCod(detail.getParamValue());
		}

		if(inoutYardHead.getInoutYardKnd().equals("保税仓库货物出库")){
			ParameterList parameterList = new ParameterList();
			parameterList.setParamCod("out_bill_name_two");
			detail = parameterListService.getOne(Wrappers.query(parameterList));
			inoutYardHead.setInoutYardNam(detail.getParamValue());

			ParameterList parameterList2 = new ParameterList();
			parameterList2.setParamCod("warehouse_name");
			detail = parameterListService.getOne(Wrappers.query(parameterList2));
//			inoutYardHead.setWarehouseNam(detail.getParamValue());

			ParameterList parameterList3 = new ParameterList();
			parameterList3.setParamCod("warehouse_name_zmdm");
			detail = parameterListService.getOne(Wrappers.query(parameterList3));
			inoutYardHead.setWarehouseCod(detail.getParamValue());
		}



//		inoutYardHead.setFId(fid);
//		inoutYardHead.setInOut("入库"); //出库/入库
//		inoutYardHead.setInoutYardKnd("保税物流中心"); //单据类型
//		inoutYardHead.setBusinessKnd("一日游业务"); //业务类型
		inoutYardHead.setOperNam(SecurityUtils.getUser().getUsername()); //添加人
		inoutYardHead.setRecordTim(new Date()); //添加时间


		Boolean bool = inoutYardHeadService.save(inoutYardHead);

		return inoutYardHead;
	}

	/**
	 * 查询最新的一条编号
	 * @param inoutYardHead
	 * @return
	 */
	@Override
	public InoutYardHead getInoutYardNo(String inoutYardHead){
		return inoutYardHeadMapper.getInoutYardNo(inoutYardHead);
	}

	@Override
	public R getInoutHeadBody(InoutYardHead inoutYardHead) {
		if((inoutYardHeadService.list(Wrappers.query(inoutYardHead))).isEmpty()){
			return R.ok(new ArrayList<>());
		}else{
			List<InoutYardBody> list = inoutYardBodyService.list(Wrappers.query(new InoutYardBody())
					.eq("H_FID",(inoutYardHeadService.list(Wrappers.query(inoutYardHead))).get(0).getFId()));
			return R.ok(list);
		}

	}

	/**
	 *一日游进出区统计
	 * @param inoutYardHeadVo
	 * @return
	 */
	@Override
	public IPage<List<InoutYardHeadVo>> getOneDay(InoutYardHeadVo inoutYardHeadVo, Page page){
		IPage<List<InoutYardHeadVo>> list = inoutYardHeadMapper.getOneDay(inoutYardHeadVo,page);
		return list;
//		List<InoutYardHeadVo> listinit = new ArrayList<InoutYardHeadVo>();
//		List<InoutYardHeadVo> listIn = list.stream().filter(info -> info.getInOut().toString().equals("1")).collect(Collectors.toList());
//		listIn.sort(Comparator.comparing(InoutYardHeadVo::getRecordTim));
//		List<InoutYardHeadVo> listOut = list.stream().filter(info -> info.getInOut().toString().equals("0")).collect(Collectors.toList());
//		listOut.sort(Comparator.comparing(InoutYardHeadVo::getRecordTim));
//		for(InoutYardHeadVo info1:listIn){
//
//			for(InoutYardHeadVo info2:listOut){
//
//				if(DateUtil.isSameTime(info1.getRecordTim(),info2.getRecordTim())){
//					info1.setInSumNetWet(info1.getSumNetWet());
//					info1.setInSumTotalPrice(info1.getSumTotalPrice());
//					info1.setInRecordTim(DateUtil.format(info1.getRecordTim(),"YYYY-MM-dd"));
//					info1.setOutOutCheckNum(info2.getOutCheckNum());
//					info1.setOutSumNetWet(info2.getSumNetWet());
//					info1.setOutRecordTim(DateUtil.format(info2.getRecordTim(),"YYYY-MM-dd"));
//					info1.setOutSumTotalPrice(info2.getSumTotalPrice());
//					info1.setOutConsignNam(info2.getConsignNam());
//					listinit.add(info1);
//					listOut.remove(info2);
//					break;
//				}
//			}
//			info1.setInSumNetWet(info1.getSumNetWet());
//			info1.setInSumTotalPrice(info1.getSumTotalPrice());
//			info1.setInRecordTim(DateUtil.format(info1.getRecordTim(),"YYYY-MM-dd"));
//			listinit.add(info1);
//
//		}
//
//		for(InoutYardHeadVo detail:listOut){
//			InoutYardHeadVo lo = new InoutYardHeadVo();
//			lo.setOutOutCheckNum(detail.getOutCheckNum());
//			lo.setOutSumNetWet(detail.getSumNetWet());
//			lo.setOutRecordTim(DateUtil.format(detail.getRecordTim(),"YYYY-MM-dd"));
//			lo.setOutSumTotalPrice(detail.getSumTotalPrice());
//			lo.setOutConsignNam(detail.getConsignNam());
//			listinit.add(lo);
//		}
//		return listinit;
	}

    @Override
	public List<InoutYardHeadVo> getOneDayExcel(InoutYardHeadVo inoutYardHeadVo){
		List<InoutYardHeadVo> list = inoutYardHeadMapper.getOneDay(inoutYardHeadVo);
		return list;
	}

	@Override
	public void expoprtOneDayExcel(InoutYardHeadVo inoutYardHeadVo, List<InoutYardHeadVo> list, HttpServletResponse response) throws IOException {
// 获取标题名称
		String fileName = "一日游统计表";

//		ParameterList parameter = parameterListMapper.selectValueByCode("customs_account_statistical_name");
//		if (parameter != null && !StringUtil.isEmpty(parameter.getParamValue())) {
//			fileName = parameter.getParamValue();
//		}

		// 告诉浏览器用什么软件打开此文件
		response.setHeader("content-Type","application/vnd.ms-excel");
		// 下载文件的默认名称
		response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));

		ClassPathResource classPathResource = new ClassPathResource("/file/in_out_oneday_detail.xlsx");
		InputStream fileInput = classPathResource.getInputStream();
		OutputStream output = null;
		//创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wb = new XSSFWorkbook(fileInput);
		//对应Excel表中的sheet,0代表第一个
		XSSFSheet sheet = wb.getSheetAt(0);

		sheet.getRow(0).getCell(0).setCellValue(fileName);// 标题行
//		sheet.getRow(1).getCell(0).setCellValue(customsVo.getInDte().substring(5,7)+"月份");// 副标题行

		// 处理数据
		int index = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		for (int i = 0; i < list.size(); i++) {
			XSSFRow row = sheet.getRow(i + 3);
			row.getCell(0).setCellValue(i+1);
			row.getCell(1).setCellValue(list.get(i).getInRecordTim()==null?"":list.get(i).getInRecordTim().substring(0,10));
			row.getCell(2).setCellValue(list.get(i).getInCheckNum()==null?"":list.get(i).getInCheckNum());
			row.getCell(3).setCellValue(list.get(i).getInSumNetWet()==null?"0":list.get(i).getInSumNetWet());
			row.getCell(4).setCellValue(list.get(i).getInSumTotalPrice()==null?"0":list.get(i).getInSumTotalPrice());
			row.getCell(5).setCellValue(list.get(i).getConsignNam()==null?"":list.get(i).getConsignNam());


			row.getCell(7).setCellValue(i+1);
			row.getCell(8).setCellValue(list.get(i).getOutRecordTim()==null?"":list.get(i).getOutRecordTim().substring(0,10));
			row.getCell(9).setCellValue(list.get(i).getOutOutCheckNum()==null?"":list.get(i).getOutOutCheckNum());
			row.getCell(10).setCellValue(list.get(i).getOutSumNetWet()==null?"0":list.get(i).getOutSumNetWet());
			row.getCell(11).setCellValue(list.get(i).getOutSumTotalPrice()==null?"0":list.get(i).getOutSumTotalPrice());
			row.getCell(12).setCellValue(list.get(i).getOutConsignNam()==null?"":list.get(i).getOutConsignNam());

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
	public Map<String,Object> getOneDayCount(Map<String,String> map){
		return inoutYardHeadMapper.getOneDayCount(map);
	}

	@Override
	public IPage<List<InoutYardHead>> getHeadList(InoutYardHead inoutYardHead, Page<?> page){
		return inoutYardHeadMapper.getHeadList(inoutYardHead,page);
	}

	@Override
	public InoutYardHead getHeadForDetail(InoutYardHead inoutYardHead){
		return inoutYardHeadMapper.getHeadForDetail(inoutYardHead);
	}


}
