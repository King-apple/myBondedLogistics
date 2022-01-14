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
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.Ioyard;
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardListBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardListHead;
import com.newweb.mrli.ykport.baoshuisystem.entity.ParameterList;
import com.newweb.mrli.ykport.baoshuisystem.mapper.IoyardListHeadMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.IoyardMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.IoyardListBodyService;
import com.newweb.mrli.ykport.baoshuisystem.service.IoyardListHeadService;
import com.newweb.mrli.ykport.baoshuisystem.service.IoyardService;
import com.newweb.mrli.ykport.baoshuisystem.service.ParameterListService;
import com.newweb.mrli.ykport.baoshuisystem.utils.ExcelUtil;
import com.newweb.mrli.ykport.baoshuisystem.vo.IoyardListHeadBodyVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.IoyardVo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 出入库明细表头表
 *
 * @author pigx code generator
 * @date 2021-09-09 09:52:12
 */
@Service
public class IoyardListHeadServiceImpl extends ServiceImpl<IoyardListHeadMapper, IoyardListHead> implements IoyardListHeadService {

	@Autowired
	public IoyardService ioyardService;
	@Autowired
	public IoyardMapper ioyardMapper;
	@Autowired
	public ParameterListService parameterListService;
	@Autowired
	public IoyardListBodyService ioyardListBodyService;
	@Autowired
	public IoyardListHeadMapper ioyardListHeadMapper;

	@Override
	@Transactional
	public R insertIoyardInfo(IoyardListHead ioyardListHead) {
		Ioyard ioyard = new Ioyard();
		ioyard.setContNo(ioyardListHead.getContNo());

//		ioyardListHead.
		ioyardListHead.setSettlementGenerateFlag("0");
		List<IoyardVo> IoyardList = ioyardService.getIoyardVo(ioyard);
		if (!IoyardList.isEmpty()) {
			IoyardListHead getMaxNo = new IoyardListHead();

			ioyardListHead.setOperNam(SecurityUtils.getUser().getUsername());
			ioyardListHead.setRecordTim(new Date());
			ParameterList ioyard_list_name = parameterListService.selectValueByCode("ioyard_list_name");
			String paramValue = ioyard_list_name.getParamValue();
			ioyardListHead.setIoyardListNam(paramValue);

			ioyardListHead.setListDat(new Date());
			QueryWrapper<IoyardListHead> ioyardListHeadQueryWrapper = Wrappers.query(getMaxNo).like("IOYARD_LIST_NO", Calendar.getInstance().get(Calendar.YEAR)).orderByDesc("F_ID");
			List<IoyardListHead> list = list(ioyardListHeadQueryWrapper);
			if(!list.isEmpty()){
				String ioyardListNo = list.get(0).getIoyardListNo();
				String s = ioyardListNo.split("-")[4];
				int i = Integer.parseInt(s);
				i++;
				String no = "YGWL-BSCZ-RCKMX"+"-"+(Calendar.getInstance().get(Calendar.YEAR))+"-"+i;
				ioyardListHead.setIoyardListNo(no);
			}else{
				String no = "YGWL-BSCZ-RCKMX"+"-"+(Calendar.getInstance().get(Calendar.YEAR))+"-1";
				ioyardListHead.setIoyardListNo(no);
			}
			save(ioyardListHead);
			for (int i = 0; i < IoyardList.size(); i++) {
				IoyardListBody ioyardListBody = new IoyardListBody();
				ioyardListBody.setFFid(ioyardListHead.getFId());
				ioyardListBody.setIoyardListNo(IoyardList.get(i).getInoutYardNo());
				ioyardListBody.setInYardDat(IoyardList.get(i).getInIoyardDte());
				ioyardListBody.setInPiecesNum(IoyardList.get(i).getInPiecesNum());
				ioyardListBody.setInWeightWgt(IoyardList.get(i).getOutWeightWgt());
				ioyardListBody.setInSettleWgt(IoyardList.get(i).getOutWeightWgt());
				ioyardListBody.setInYardNo(IoyardList.get(i).getInIoyardNo());
				ioyardListBody.setOutYardDat(IoyardList.get(i).getOutIoyardDte());
				ioyardListBody.setOutPiecesNum(IoyardList.get(i).getOutPiecesNum());
				ioyardListBody.setOutWeightWgt(IoyardList.get(i).getOutWeightWgt());
				ioyardListBody.setOutSettleWgt(IoyardList.get(i).getOutWeightWgt());
				ioyardListBody.setOutYardNo(IoyardList.get(i).getOutIoyardNo());
				ioyardListBody.setInYardWay(IoyardList.get(i).getInYardWay() + "");
				ioyardListBody.setOutYardWay(IoyardList.get(i).getOutYardWay() + "");
				ioyardListBody.setConsignNam(IoyardList.get(i).getConsignNam());
				ioyardListBody.setCargoNam(IoyardList.get(i).getCargoNam());
				ioyardListBody.setCPkgKindNam(IoyardList.get(i).getCPkgKindNam());
				ioyardListBody.setYardNam(IoyardList.get(i).getYardNo());
				ioyardListBody.setMarkTxt(IoyardList.get(i).getRemarkTxt());
				ioyardListBody.setRecordTim(IoyardList.get(i).getRecordTim());
				ioyardListBody.setOperNam(IoyardList.get(i).getOperNam());
				ioyardListBody.setInShipNam(IoyardList.get(i).getShipNam());
				ioyardListBody.setOutShipNam(IoyardList.get(i).getShipNam());
				ioyardListBodyService.save(ioyardListBody);
				Ioyard ioyard1 = new Ioyard();
				ioyard1.setInoutYardNo(IoyardList.get(i).getInoutYardNo());
				ioyard1.setDetailFlag(1);
				ioyardService.updateById(ioyard1);

			}
			return R.ok(1);
		} else {
			return R.ok(0);
		}
	}

	@Override
	public IPage<IoyardListHeadBodyVo> getIoyardListHead(Page<?> page, IoyardListHeadBodyVo ioyardListHeadBodyVo) {
		return ioyardListHeadMapper.getIoyardListHead(page, ioyardListHeadBodyVo);
	}

	@Override
	public void exportFile(IoyardListHeadBodyVo ioyardListHeadBodyVo, HttpServletRequest request, HttpServletResponse response) {
//		List<IoyardListHeadBodyVo> ioyardListHead = ioyardListHeadMapper.getIoyardListHead(ioyardListHeadBodyVo);
//		if(ioyardListHead.size() == 0){
//			return;
//		}

		InputStream path = this.getClass().getResourceAsStream("/file/inoutList.xls");
		OutputStream output = null;
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(path);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow titleRow = sheet.getRow(1);
			HSSFCell cellNo = titleRow.getCell(1);
			cellNo.setCellValue(ioyardListHeadBodyVo.getListDat());
			HSSFCell cellDte = titleRow.getCell(11);
			cellDte.setCellValue(ioyardListHeadBodyVo.getIoyardListNo());
			ParameterList ioyard_list_name = parameterListService.selectValueByCode("ioyard_list_name");
			ExcelUtil.exportExcel(ioyard_list_name.getParamValue(), wb, ioyardListHeadBodyVo.getIoyardListBodyList(), IoyardListBody.class,
					4, 3, request, response);
			int inNum = 0;
			int outNum = 0;
			Double inWei = 0.0;
			Double outWei = 0.0;
			for (IoyardListBody ioyardListBody : ioyardListHeadBodyVo.getIoyardListBodyList()) {
				inNum += ioyardListBody.getInPiecesNum();
				outNum += ioyardListBody.getOutPiecesNum();
				inWei += ioyardListBody.getInWeightWgt();
				outWei += ioyardListBody.getOutWeightWgt();

			}
			int countRow = 4 + ioyardListHeadBodyVo.getIoyardListBodyList().size();
			HSSFRow cotRow = sheet.getRow(countRow);
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
			cellStyle.setWrapText(true);
			cellStyle.setBorderBottom(BorderStyle.THIN);
			HSSFFont font = wb.createFont();
			font.setFontHeight((short) 170);
			cellStyle.setFont(font);
			cotRow.getCell(7).setCellValue(inNum);
			cotRow.getCell(7).setCellStyle(cellStyle);
			cotRow.getCell(8).setCellValue(inWei);
			cotRow.getCell(8).setCellStyle(cellStyle);
			cotRow.getCell(12).setCellValue(outNum);
			cotRow.getCell(12).setCellStyle(cellStyle);
			cotRow.getCell(13).setCellValue(outWei);
			cotRow.getCell(13).setCellStyle(cellStyle);
//			output = new FileOutputStream(ioyard_list_name.getParamValue());
			for (int i = 0; i <ioyardListHeadBodyVo.getIoyardListBodyList().size() ; i++) {
				IoyardListBody ioyardListBody = ioyardListHeadBodyVo.getIoyardListBodyList().get(i);
				sheet.getRow(4+i).getCell(2).setCellValue(ioyardListBody.getInYardWay().equals("卸船") ? ioyardListBody.getInYardWay()+"("+ (ioyardListBody.getInShipNam() == null?"":ioyardListBody.getInShipNam()) +")":ioyardListBody.getInYardWay() );
				sheet.getRow(4+i).getCell(2).setCellStyle(cellStyle);
				sheet.getRow(4+i).getCell(11).setCellValue(ioyardListBody.getOutYardWay().equals("装船") ? ioyardListBody.getOutYardWay()+"("+ (ioyardListBody.getOutShipNam() == null?"":ioyardListBody.getOutShipNam()) +")":ioyardListBody.getInYardWay() );
				sheet.getRow(4+i).getCell(11).setCellStyle(cellStyle);
			}
			response.setContentType("application/binary;charset=UTF-8");
			output = response.getOutputStream();
			String fn = new String(ioyard_list_name.getParamValue().getBytes(), "UTF-8");
			response.setHeader("ContentDisposition", URLEncoder.encode("" + fn + ".xls", "UTF-8"));
			wb.write(output);
			output.flush();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	@Transactional
	public R removeIoyardListHeadAndBody(Integer fId) {
		//删除头表
		IoyardListHead ioyardListHead = new IoyardListHead();
			ioyardListHead.setFId(new Long(fId));
			ioyardListHead.setDelNam(SecurityUtils.getUser().getUsername());
			ioyardListHead.setDelTim(new Date());
		updateById(ioyardListHead);
		//删除表体
		IoyardListBody ioyardListBody = new IoyardListBody();
			ioyardListBody.setDelNam(SecurityUtils.getUser().getUsername());
			ioyardListBody.setDelTim(new Date());
		IoyardListBody ioyardListBodyWhere = new IoyardListBody();
			ioyardListBodyWhere.setFFid(new Long(fId));
		List<IoyardListBody> bodyList = ioyardListBodyService.list(Wrappers.query(ioyardListBodyWhere).isNull("DEl_NAM"));
		String[] inoutYardNoArray = new String[bodyList.size()];
		for (int i = 0; i <bodyList.size() ; i++) {
			inoutYardNoArray[i] = bodyList.get(i).getIoyardListNo();
		}
		ioyardListBodyService.update(ioyardListBody,Wrappers.query(ioyardListBodyWhere));
		//修改ioyard 状态
		Ioyard ioyard = new Ioyard();
			ioyard.setDetailFlag(null);
		LambdaUpdateWrapper<Ioyard> updateWrapper = new LambdaUpdateWrapper<>();
			updateWrapper.set(Ioyard::getDetailFlag,null);
			updateWrapper.in(Ioyard::getInoutYardNo,inoutYardNoArray);
		return R.ok(ioyardMapper.update(null,updateWrapper));
	}
}
