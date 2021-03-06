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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.*;
import com.newweb.mrli.ykport.baoshuisystem.mapper.SysFileMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.*;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.oss.OssProperties;
import com.newweb.mrli.common.oss.service.OssTemplate;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.utils.ExcelUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

/**
 * ????????????
 *
 * @author Luckly
 * @date 2019-06-18 17:18:42
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {

	private final OssProperties ossProperties;

	private final OssTemplate minioTemplate;

	private SysFileMapper mapper;

	@Autowired
	private InoutYardBodyService inoutYardBodyService;

	@Autowired
	private ContractService contractService;

	@Autowired
	private SettledCompanyInfoService settledCompanyInfoService;

	@Autowired
	private InoutYardHeadService inoutYardHeadService;

	@Autowired
	private ParameterListService parameterListService;

	@Autowired
	private CheckWeightService checkWeightService;

	@Autowired
	private WarehouseNamInfoService warehouseNamInfoService;

	@Autowired
	private ClientInfoService clientInfoService;

	@Autowired
	private CustomsBrokerInfoService customsBrokerInfoService;
	/**
	 * ????????????
	 *
	 * @param file
	 * @return
	 */
	@Override
	public R uploadFile(MultipartFile file) {
		String fileName = IdUtil.simpleUUID() + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());
		Map<String, Object> resultMap = new HashMap<>(4);
		resultMap.put("bucketName", ossProperties.getBucketName());
		resultMap.put("fileName", fileName);
		resultMap.put("url", String.format("/baoshuisystem/%s/%s", ossProperties.getBucketName(), fileName));
		resultMap.put("originalFilename",file.getOriginalFilename());
		resultMap.put("fileSize",file.getSize());
		try {
			minioTemplate.putObject(ossProperties.getBucketName(), fileName, file.getInputStream());
			// ????????????????????????,????????????????????????
//			 fileLog(file, fileName);
		} catch (Exception e) {
			log.error("????????????", e);
			return R.failed(e.getLocalizedMessage());
		}
		return R.ok(resultMap);
	}

	/**
	 * ????????????
	 *
	 * @param bucket
	 * @param fileName
	 * @param response
	 */
	@Override
	public void getFile(String bucket, String fileName, HttpServletResponse response) {
		try (S3Object s3Object = minioTemplate.getObject(bucket, fileName)) {
			response.setContentType("application/octet-stream; charset=UTF-8");

			IoUtil.copy(s3Object.getObjectContent(), response.getOutputStream());
			response.addHeader("Content-Disposition", "attachment;fileName=pppppppppppppp");
		} catch (Exception e) {
			log.error("??????????????????: {}", e.getLocalizedMessage());
		}
	}

	/**
	 * ????????????
	 *
	 * @param id
	 * @return
	 */
	@Override
	@SneakyThrows
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteFile(Long id) {
		SysFile file = this.getById(id);
		minioTemplate.removeObject(ossProperties.getBucketName(), file.getFileName());
		return this.removeById(id);
	}

	/**
	 * ????????????????????????,????????????????????????
	 *
	 * @param fileVal     ????????????
	 */
	public R fileLog(List<SysFile> fileVal) {
		int flag = 0;
		Long fid = 0L;
		for (int i = 0; i < fileVal.size(); i++) {
			SysFile sysFile = new SysFile();
			sysFile.setFileName(fileVal.get(i).getFileName());
			sysFile.setOriginal(fileVal.get(i).getOriginal());
			sysFile.setFileSize(fileVal.get(i).getFileSize());
			sysFile.setType(FileUtil.extName(fileVal.get(i).getOriginal()));
			sysFile.setBucketName(ossProperties.getBucketName());
			sysFile.setTableFid(fileVal.get(i).getTableFid());
			sysFile.setTableName(fileVal.get(i).getTableName());
			sysFile.setCreateTime(new Date());
			sysFile.setCreateUser(SecurityUtils.getUser().getUsername());
			sysFile.setDelFlag(fileVal.get(i).getDelFlag() == null?0:fileVal.get(i).getDelFlag());
			sysFile.setRemark(fileVal.get(i).getRemark());
			if (ObjectUtil.isNotEmpty(SecurityUtils.getUser())) {
				sysFile.setCreateUser(SecurityUtils.getUser().getUsername());
			}

			fid = mapper.selectFid();
			sysFile.setId(fid);
			flag = mapper.insert(sysFile);
			if(flag != 1){
				return R.failed();
			}
		}

		return R.ok(fid);

	}

	@Override
	public R updateFile(List<SysFile> fileVal) {
		for (int i = 0; i < fileVal.size(); i++) {
//			SysFile sysFile = new SysFile();
//			sysFile.setId(fileVal.get(i).getId());
//			sysFile.setRemark(fileVal.get(i).getRemark());
//			sysFile.setUpdateTime(new Date());
//			sysFile.setUpdateUser(SecurityUtils.getUser().getUsername());
//			if(fileVal.get(i).getDelFlag() != null){
//				sysFile.setDelFlag(fileVal.get(i).getDelFlag() );
//			}
			LambdaUpdateWrapper<SysFile> updateWrapper = new LambdaUpdateWrapper<>();
			updateWrapper.set(fileVal.get(i).getDelFlag() != null
					,SysFile::getDelFlag,fileVal.get(i).getDelFlag())
					.set(StringUtil.isNotBlank(fileVal.get(i).getRemark())
							,SysFile::getRemark,fileVal.get(i).getRemark())
					.set(true
							,SysFile::getUpdateTime,new Date())
					.set(true
							,SysFile::getUpdateUser,SecurityUtils.getUser().getUsername())
			.eq(SysFile::getId,fileVal.get(i).getId());
			update(updateWrapper);
		}
		return R.ok("ok");
	}

	private static FormulaEvaluator evaluator;


	/**
	 * ????????????
	 *
	 * @param file
	 * @return
	 */
	@Override
	@Transactional
	public R uploadForInoutyardBodyAndHead(MultipartFile file) throws IOException, InvalidFormatException {
		String unitPriceKnd; //????????????
		String unit; //????????????
		String netWetUnit; //????????????

		Workbook workbook = null;
		InputStream is = file.getInputStream();
		String name = file.getOriginalFilename().toLowerCase();
		// ??????excel????????????
		if (name.contains(".xlsx") || name.contains(".xls")) {
			//????????????????????????.
			workbook = WorkbookFactory.create(is);
		}
		//?????????????????????
		Sheet sheet = workbook.getSheetAt(0);
		evaluator=workbook.getCreationHelper().createFormulaEvaluator();
		Row  row=sheet.getRow(0);
		Row  inCompanyNameRow=sheet.getRow(1);
		int check = 0;
		int index = 0;
		String inCompanyName = "";
		if(name.indexOf("????????????")!=-1) {
			try {
				inCompanyName = inCompanyNameRow.getCell(0).getStringCellValue().split("???")[1];
			} catch (Exception e) {
				e.printStackTrace();
				return R.failed("????????????????????????????????????");
			}
		}
		//???????????????
		Map<String,Object> hFid = insertIntoHead(sheet,inCompanyName,name,file);
		if(Integer.parseInt(hFid.get("fId").toString()) == 0){
			return R.failed("??????????????????");
		}
		if(Integer.parseInt(hFid.get("fId").toString()) == -1){
			return R.failed(hFid.get("Msg"));
		}
		if(Integer.parseInt(hFid.get("fId").toString()) == -2){
			return R.failed(hFid.get("Msg"));
		}
		if(Integer.parseInt(hFid.get("fId").toString()) == -3){
			return R.failed(hFid.get("Msg"));
		}
		if(Integer.parseInt(hFid.get("fId").toString()) == -4){
			return R.failed(hFid.get("Msg"));
		}


//		InoutYardHead inoutYardHead = new InoutYardHead();
//		inoutYardHead.setFId(hFid);
		InoutYardHead inoutYardHead = inoutYardHeadService.getById(Integer.parseInt(hFid.get("fId").toString()));

		//?????????????????????
		while(check == 0){
			Row valRow = sheet.getRow(index+3);
			valRow.getCell(0).setCellType(CellType.STRING);
			valRow.getCell(1).setCellType(CellType.STRING);
			if(!valRow.getCell(0).getStringCellValue().equals("??????")){
				if(valRow.getCell(1)!=null&&valRow.getCell(1)
						.getStringCellValue()!=null&&!""
						.equals(valRow.getCell(1).getStringCellValue())){
				}else{
					check =1;
					continue;
				}
			}else{
				check =1;
				continue;
			}
			index++;
		}
		//?????????????????????
		List<InoutYardBody> inoutYardBodyList = ExcelUtil.getExcelToList("aaa", file, InoutYardBody.class, 3,index+2,2);
		if(inoutYardBodyList.size()<=0){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.failed("??????????????????????????????????????????????????????????????????");
		}
		if(inoutYardBodyList.size() == 1&&inoutYardBodyList.get(0).getMarkTxt()!=null&&inoutYardBodyList.get(0).getMarkTxt().indexOf("????????????")>=0){
			return R.failed("????????????????????????excel??????"+inoutYardBodyList.get(0).getMarkTxt());
		}
		int saveIndex = 0;
		//????????????
		for (InoutYardBody inoutYardBody:inoutYardBodyList){
			inoutYardBody.setOperNam(SecurityUtils.getUser().getManName());
			inoutYardBody.setRecordTim(new Date());
			inoutYardBody.setHFid(Integer.parseInt(hFid.get("fId").toString()));
			inoutYardBody.setContNo(inoutYardHead.getContNo());
			Contract contract = new Contract();
			contract.setContNo(inoutYardBody.getContNo());
			List<Contract> conList = contractService.list(Wrappers.query(contract));

			if(conList.isEmpty()){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return R.failed("???"+(saveIndex+1)+"???????????????????????????,?????????");
			}

			if(name.indexOf("??????")!=-1){
				if(name.indexOf("??????")!=-1){
					inoutYardBody.setInOut("0");
					inoutYardBody.setPieceNum(inoutYardBody.getBucketNum());
				}else{
					inoutYardBody.setInOut("1");
				}

			} else{
				if(name.indexOf("??????")!=-1){
					inoutYardBody.setInOut("0");
				}else{
					inoutYardBody.setInOut("1");
				}
				inoutYardBody.setUnitNam("??????????????????????????????");
				inoutYardBody.setUnitCod("14");
				if(conList.isEmpty()){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return R.failed("???"+(saveIndex+1)+"???????????????????????????,?????????");
				}else{
//					inoutYardBody.setCargoNam(conList.get(0).getCargoNam());//??????
//					inoutYardBody.setCargoNo(conList.get(0).getCargoCod()); //H.S??????????????????
//					inoutYardBody.setCargoKindNam(conList.get(0).getCargoKindNam()); //????????????
//					inoutYardBody.setCargoKindNo(conList.get(0).getCargoKindCod()); //????????????
					//????????????id
				}
			}

			if(inoutYardBody.getCargoNam() == null || inoutYardBody.getCargoNam().equals("")){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return R.failed("?????????????????????");
			}
			if(inoutYardBody.getCargoKindNam() == null || inoutYardBody.getCargoKindNam().equals("")){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return R.failed("?????????????????????");
			}


			inoutYardBody.setCFid(conList.get(0).getFId());
			if(inoutYardBody.getBoundFlag()!=null&&"???".equals(inoutYardBody.getBoundFlag())){
				inoutYardBody.setBoundFlag("1");
				if(inoutYardBody.getInoutId() == null || inoutYardBody.getInoutId().equals("")){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return R.failed("?????????????????????");
				}
				if(inoutYardBody.getTradeId() == null || inoutYardBody.getTradeId().equals("")){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return R.failed("?????????????????????");
				}
				if(inoutYardBody.getBillNo() == null || inoutYardBody.getBillNo().equals("")){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return R.failed("?????????????????????");
				}
//				if(inoutYardBody.getAddMinRat() == null || inoutYardBody.getAddMinRat().equals("")){
//					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//					return R.failed("????????????????????????");
//				}
				if(inoutYardBody.getUnitCod() == null || inoutYardBody.getUnitCod().equals("")){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return R.failed("????????????????????????");
				}
				if(inoutYardBody.getUnitNam() == null || inoutYardBody.getUnitNam().equals("")){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return R.failed("????????????????????????");
				}


//				if(inoutYardBody.getSubunitCod() == null || inoutYardBody.getSubunitCod().equals("")){
//					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//					return R.failed("???????????????????????????");
//				}
//				if(inoutYardBody.getSubunitNam() == null || inoutYardBody.getSubunitNam().equals("")){
//					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//					return R.failed("???????????????????????????");
//				}
//				if(inoutYardBody.getFeeWayId() == null || inoutYardBody.getFeeWayId().equals("")){
//					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//					return R.failed("??????????????????????????????");
//				}
//				if(inoutYardBody.getFeeWayNam() == null || inoutYardBody.getFeeWayNam().equals("")){
//					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//					return R.failed("????????????????????????");
//				}
			}else{
				inoutYardBody.setBoundFlag("0");
			}

			CheckWeight checkWeight = new CheckWeight();
			checkWeight.setRecordDte(new Date());

//			checkWeightService.save(checkWeight);
			boolean save = inoutYardBodyService.save(inoutYardBody);
			saveIndex++;
		}

		is.close();
		return R.ok("????????????");
	}



	public Map<String,Object> insertIntoHead(Sheet sheet,String inCompanyName,String name,MultipartFile file){
		int headRowIndex = 0;
		InoutYardHead inoutYardHead = new InoutYardHead();

		List<SettledCompanyInfo> count = new ArrayList<SettledCompanyInfo>();
		//??????????????? ??? ?????????????????? ??? ??????????????????
		if(name.indexOf("????????????")!=-1){
			inoutYardHead.setSettledCompany(inCompanyName);
			//????????????????????????
			SettledCompanyInfo settledCompanyInfo = new SettledCompanyInfo();
			settledCompanyInfo.setCompanyName(inCompanyName);
			count = settledCompanyInfoService.list(Wrappers.query(settledCompanyInfo));
			if(count.size() == 0){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("fId", "0");
				map.put("Msg", "?????????????????????");
				return map;
			}
			while(true){
				Row firstRow = sheet.getRow(headRowIndex);
				firstRow.getCell(0).setCellType(CellType.STRING);
				String firstCell = firstRow.getCell(0).getStringCellValue();
				if("??????".equals(firstCell)){
					headRowIndex++;
					break;
				}
				headRowIndex++;
			}
		}

		if(name.indexOf("????????????")!=-1){
			while(true){
				Row firstRow = sheet.getRow(headRowIndex);
				firstRow.getCell(0).setCellType(CellType.STRING);
				String firstCell = firstRow.getCell(0).getStringCellValue();
				if("??????".equals(firstCell)){
					headRowIndex++;
					break;
				}
				headRowIndex++;
			}
		}


		Integer fid = 0;

		List<InoutYardHead> inoutYardHeadList = ExcelUtil.getExcelToList(name, file, InoutYardHead.class, headRowIndex,headRowIndex,headRowIndex-1);
		int saveIndex=0;
		for (InoutYardHead inoutYardHeadVar:inoutYardHeadList) {

			ParameterList parameterList = new ParameterList();
			inoutYardHeadVar.setOperNam(SecurityUtils.getUser().getUsername());
			inoutYardHeadVar.setRecordTim(new Date());

			//???????????????
			ClientInfo clientInfo = new ClientInfo();
			clientInfo.setCompany(inoutYardHeadVar.getConsignNam());
			List<ClientInfo> detailList = clientInfoService.list(Wrappers.query(clientInfo).isNull("DEL_NAM"));
			if(detailList.isEmpty()){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("fId", "-3");
				map.put("Msg", "??????????????????????????????");
				return map;
			}
//			ClientInfo detail = clientInfoService.getOne(Wrappers.query(clientInfo));
//			if(detail == null){
//				
//			}
			ClientInfo detail = detailList.get(0);
			inoutYardHeadVar.setConsignCod(detail.getCompanyCode());

			//??????????????????
			CustomsBrokerInfo customsBrokerInfo = new CustomsBrokerInfo();
			customsBrokerInfo.setCustomsBrokerName(inoutYardHeadVar.getCustomsBroker());
			List<CustomsBrokerInfo> detailsList = customsBrokerInfoService.list(Wrappers.query(customsBrokerInfo).isNull("DEL_NAM"));
			if(detailsList.isEmpty()){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("fId", "-4");
				map.put("Msg", "?????????????????????????????????");
				return map;
			}
			CustomsBrokerInfo details = detailsList.get(0);

			inoutYardHeadVar.setCustomsBrokerCod(details.getCustomsBrokerCode());

			if(name.indexOf("????????????")!=-1&&name.indexOf("??????")!=-1){
				Row valRowData = sheet.getRow(2);
				String unitPriceKnd = valRowData.getCell(8).getStringCellValue(); //????????????
				String unit = valRowData.getCell(9).getStringCellValue(); //????????????
				String netWetUnit = valRowData.getCell(7).getStringCellValue(); //????????????
				unitPriceKnd=unitPriceKnd.substring(unitPriceKnd.indexOf("???")+1,unitPriceKnd.indexOf("???"));
				unit=unit.substring(unit.indexOf("???")+1,unit.indexOf("???"));
				netWetUnit=netWetUnit.substring(netWetUnit.indexOf("???")+1,netWetUnit.indexOf("???"));
				inoutYardHeadVar.setUnitPriceKnd(unitPriceKnd);//????????????
				inoutYardHeadVar.setUnit(unit);//????????????
				inoutYardHeadVar.setNetWetUnit(netWetUnit);//????????????


				inoutYardHeadVar.setSettledCompany(inCompanyName);
				inoutYardHeadVar.setSettledCod(count.get(0).getCompanyCode());
				inoutYardHeadVar.setInoutYardKnd("????????????????????????");
				inoutYardHeadVar.setInOut("0");
				parameterList.setParamCod("out_bill_name_one");
				List<ParameterList> list = parameterListService.list(Wrappers.query(parameterList));
				inoutYardHeadVar.setInoutYardNam(list.get(0).getParamValue());
			}
			if(name.indexOf("????????????")!=-1&&name.indexOf("??????")!=-1){
				Row  rowCell=sheet.getRow(0);
				inoutYardHeadVar.setInoutYardNam(rowCell.getCell(0).getStringCellValue());

				WarehouseNamInfo warehouseNamInfo = new WarehouseNamInfo();
				warehouseNamInfo.setInOut("1");
				warehouseNamInfo.setInoutYardNam(inoutYardHeadVar.getInoutYardNam());

				List<WarehouseNamInfo> detailWarehouseList = warehouseNamInfoService
						.list(Wrappers.query(warehouseNamInfo)
								.isNull("DEL_NAM"));
				if(detailWarehouseList.isEmpty()){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					Map<String, Object> map = new HashMap<String,Object>();
					map.put("fId", "-2");
					map.put("Msg", "????????????????????????");
					saveIndex++;
					return map;
				}
				WarehouseNamInfo detailWarehouse = detailWarehouseList.get(0);

				inoutYardHeadVar.setWarehouseCod(detailWarehouse.getWarehouseCod());
				inoutYardHeadVar.setElectronicAccountNum(detailWarehouse.getElectronicAccountNum());
				inoutYardHeadVar.setWarehouseNam(detailWarehouse.getWarehouseNam());
				inoutYardHeadVar.setExportCod(detailWarehouse.getExportCod());

				Row valRowData = sheet.getRow(2);
				String unitPriceKnd = valRowData.getCell(7).getStringCellValue(); //????????????
				String unit = valRowData.getCell(8).getStringCellValue(); //????????????
				String netWetUnit = valRowData.getCell(6).getStringCellValue(); //????????????
				unitPriceKnd=unitPriceKnd.substring(unitPriceKnd.indexOf("???")+1,unitPriceKnd.indexOf("???"));
				unit=unit.substring(unit.indexOf("???")+1,unit.indexOf("???"));
				netWetUnit=netWetUnit.substring(netWetUnit.indexOf("???")+1,netWetUnit.indexOf("???"));
				inoutYardHeadVar.setUnitPriceKnd(unitPriceKnd);//????????????
				inoutYardHeadVar.setUnit(unit);//????????????
				inoutYardHeadVar.setNetWetUnit(netWetUnit);//????????????

				inoutYardHeadVar.setInoutYardKnd("????????????????????????");
				inoutYardHeadVar.setInOut("1");
//				parameterList.setParamCod("in_bill_name_two");
//				List<ParameterList> list = parameterListService.list(Wrappers.query(parameterList));


//				ParameterList parameterList2 = new ParameterList();
//				parameterList2.setParamCod("warehouse_name");
//				ParameterList detail = parameterListService.getOne(Wrappers.query(parameterList2));
//				inoutYardHeadVar.setWarehouseNam(detail.getParamValue());
//
//				ParameterList parameterList3 = new ParameterList();
//				parameterList3.setParamCod("warehouse_name_zmdm");
//				detail = parameterListService.getOne(Wrappers.query(parameterList3));
//				inoutYardHeadVar.setWarehouseCod(detail.getParamValue());



			}
			if(name.indexOf("????????????")!=-1&&name.indexOf("??????")!=-1){
				Row valRowData = sheet.getRow(2);
				String unitPriceKnd = valRowData.getCell(7).getStringCellValue(); //????????????
				String unit = valRowData.getCell(8).getStringCellValue(); //????????????
				String netWetUnit = valRowData.getCell(6).getStringCellValue(); //????????????
				unitPriceKnd=unitPriceKnd.substring(unitPriceKnd.indexOf("???")+1,unitPriceKnd.indexOf("???"));
				unit=unit.substring(unit.indexOf("???")+1,unit.indexOf("???"));
				netWetUnit=netWetUnit.substring(netWetUnit.indexOf("???")+1,netWetUnit.indexOf("???"));
				inoutYardHeadVar.setUnitPriceKnd(unitPriceKnd);//????????????
				inoutYardHeadVar.setUnit(unit);//????????????
				inoutYardHeadVar.setNetWetUnit(netWetUnit);//????????????

				inoutYardHeadVar.setSettledCompany(inCompanyName);
				inoutYardHeadVar.setSettledCod(count.get(0).getCompanyCode());
				inoutYardHeadVar.setInoutYardKnd("????????????????????????");
				inoutYardHeadVar.setInOut("1");
				parameterList.setParamCod("in_bill_name_one");
				List<ParameterList> list = parameterListService.list(Wrappers.query(parameterList));
				inoutYardHeadVar.setInoutYardNam(list.get(0).getParamValue());
			}
			if(name.indexOf("????????????")!=-1&&name.indexOf("??????")!=-1){
				Row  rowCell=sheet.getRow(0);
				inoutYardHeadVar.setInoutYardNam(rowCell.getCell(0).getStringCellValue());
				WarehouseNamInfo warehouseNamInfo = new WarehouseNamInfo();
				warehouseNamInfo.setInOut("0");
				warehouseNamInfo.setInoutYardNam(inoutYardHeadVar.getInoutYardNam());
				List<WarehouseNamInfo> detailWarehouseList = warehouseNamInfoService
						.list(Wrappers.query(warehouseNamInfo).isNull("DEL_NAM"));
				if(detailWarehouseList.isEmpty()){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					Map<String, Object> map = new HashMap<String,Object>();
					map.put("fId", "-2");
					map.put("Msg", "????????????????????????");
					saveIndex++;
					return map;
				}
				WarehouseNamInfo detailWarehouse = detailWarehouseList.get(0);

				inoutYardHeadVar.setWarehouseCod(detailWarehouse.getWarehouseCod());
				inoutYardHeadVar.setElectronicAccountNum(detailWarehouse.getElectronicAccountNum());
				inoutYardHeadVar.setWarehouseNam(detailWarehouse.getWarehouseNam());
				inoutYardHeadVar.setExportCod(detailWarehouse.getExportCod());

				Row valRowData = sheet.getRow(2);

				String unitPriceKnd = valRowData.getCell(7).getStringCellValue(); //????????????
				String unit = valRowData.getCell(8).getStringCellValue(); //????????????
				String netWetUnit = valRowData.getCell(6).getStringCellValue(); //????????????
				unitPriceKnd=unitPriceKnd.substring(unitPriceKnd.indexOf("???")+1,unitPriceKnd.indexOf("???"));
				unit=unit.substring(unit.indexOf("???")+1,unit.indexOf("???"));
				netWetUnit=netWetUnit.substring(netWetUnit.indexOf("???")+1,netWetUnit.indexOf("???"));
				inoutYardHeadVar.setUnitPriceKnd(unitPriceKnd);//????????????
				inoutYardHeadVar.setUnit(unit);//????????????
				inoutYardHeadVar.setNetWetUnit(netWetUnit);//????????????

				inoutYardHeadVar.setInoutYardKnd("????????????????????????");
				inoutYardHeadVar.setInOut("0");
//				parameterList.setParamCod("out_bill_name_two");
//				List<ParameterList> list = parameterListService.list(Wrappers.query(parameterList));
//				inoutYardHeadVar.setInoutYardNam(list.get(0).getParamValue());

//				ParameterList parameterList2 = new ParameterList();
//				parameterList2.setParamCod("warehouse_name");
//				ParameterList detail = parameterListService.getOne(Wrappers.query(parameterList2));
//				inoutYardHeadVar.setWarehouseNam(detail.getParamValue());
//
//				ParameterList parameterList3 = new ParameterList();
//				parameterList3.setParamCod("warehouse_name_zmdm");
//				detail = parameterListService.getOne(Wrappers.query(parameterList3));
//				inoutYardHeadVar.setWarehouseCod(detail.getParamValue());
			}
			inoutYardHeadVar.setBusinessCheckStatus("0");
			inoutYardHeadVar.setOperCheckStatus("0");

			Contract contract = new Contract();
			contract.setContNo(inoutYardHeadVar.getContNo());
			List<Contract> conList = contractService.list(Wrappers.query(contract));
				if(conList.isEmpty()) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					Map<String, Object> map = new HashMap<String,Object>();
					map.put("fId", "-1");
					map.put("Msg", "???" + (saveIndex + 1) + "???????????????????????????,?????????");
					saveIndex++;
					return map;
				}


			inoutYardHeadVar.setCFid(conList.get(0).getFId().toString());
			InoutYardHead inoutYardHeadSerch = new InoutYardHead();
			String inOutYardNo = "";
			if(name.indexOf("????????????")!=-1){
				inOutYardNo = inoutYardHeadVar.getSettledCod()+ Calendar.getInstance().get(Calendar.YEAR);
			}

			if(name.indexOf("????????????")!=-1){
				inOutYardNo = inoutYardHeadVar.getWarehouseCod()+ Calendar.getInstance().get(Calendar.YEAR);
			}

			inoutYardHeadSerch.setInoutYardNo(inOutYardNo);
			List<InoutYardHead> inout_yard_no = inoutYardHeadService
					.list(Wrappers
							.query(new InoutYardHead())
							.like("INOUT_YARD_NO", inoutYardHeadSerch.getInoutYardNo())
							.orderByDesc("INOUT_YARD_NO"));
			if(inout_yard_no.isEmpty()){
				inoutYardHeadVar.setInoutYardNo(inOutYardNo+"0001");
			}else{
				int number = Integer.parseInt(inout_yard_no.get(0).getInoutYardNo().split(inOutYardNo)[1]);
				number++;
				String num = String.format("%0" + 4 + "d", Integer.parseInt(number+""));
				inoutYardHeadVar.setInoutYardNo(inOutYardNo+num);
			}


			inoutYardHeadService.save(inoutYardHeadVar);
			fid = inoutYardHeadVar.getFId();

		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("fId", fid);
		map.put("Msg", "ok");
		return map;
	}

	@Override
	public void exportExcelForInoutyardBodyAndHead(InoutYardHead inoutYardHead, HttpServletRequest request, HttpServletResponse response) {

//		InoutYardHead byId = inoutYardHeadService.getById(inoutYardHead.getFId());
		InoutYardHead inDetail = new InoutYardHead();
		inDetail.setFId(inoutYardHead.getFId());
		InoutYardHead byId = inoutYardHeadService.getHeadForDetail(inDetail);
		if("??????".equals(inoutYardHead.getOperNam())){
			inoutYardHead.setInoutYardNam(inoutYardHead.getInoutYardNam()+"??????");
		}
		InputStream path = this.getClass().getResourceAsStream("/file/"+inoutYardHead.getInoutYardNam()+".xls");
		OutputStream output = null;
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(path);
			HSSFSheet sheet = wb.getSheetAt(0);
			Row row1 = sheet.getRow(1);

			Cell row1Cell1 = null;
			Cell row1Cell2 = null;
			if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1 && inoutYardHead.getInoutYardNam().indexOf("??????") != -1) {
				row1Cell1 = row1.getCell(0);
				row1Cell2 = row1.createCell(12);
				if(byId!=null){
					sheet.getRow(2).getCell(5).setCellValue("?????????"+byId.getNetWetUnit()+'???');//????????????
					sheet.getRow(2).getCell(6).setCellValue("?????????"+byId.getNetWetUnit()+'???');//????????????
					sheet.getRow(2).getCell(7).setCellValue("?????????"+byId.getUnitPriceKnd()+'???');//????????????
					sheet.getRow(2).getCell(8).setCellValue("?????????"+byId.getUnit()+'???');//????????????
				}

			}

			if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1 && inoutYardHead.getInoutYardNam().indexOf("??????") != -1) {
				row1Cell1 = row1.getCell(0);
				row1Cell2 = row1.createCell(13);
				if(byId!=null){
					sheet.getRow(2).getCell(5).setCellValue("?????????"+byId.getNetWetUnit()+'???');//????????????
					sheet.getRow(2).getCell(6).setCellValue("?????????"+byId.getNetWetUnit()+'???');//????????????
					sheet.getRow(2).getCell(7).setCellValue("?????????"+byId.getUnitPriceKnd()+'???');//????????????
					sheet.getRow(2).getCell(8).setCellValue("?????????"+byId.getUnit()+'???');//????????????
				}

			}

			if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1 && inoutYardHead.getInoutYardNam().indexOf("??????") != -1) {
				if(byId != null)
				{
					Row row0 = sheet.getRow(0);
					WarehouseNamInfo warehouseNamInfo = new WarehouseNamInfo();
					warehouseNamInfo.setExportCod(byId.getExportCod());
					warehouseNamInfo.setWarehouseNam(byId.getWarehouseNam());
					warehouseNamInfo.setInOut("1");
					List<WarehouseNamInfo> listName = warehouseNamInfoService.list(Wrappers.query(warehouseNamInfo));
					row0.getCell(0).setCellValue(listName.get(0).getInoutYardNam());
//					row0.getCell(0).setCellValue(inoutYardHead.getModel());

					sheet.getRow(2).getCell(5).setCellValue("?????????"+byId.getNetWetUnit()+'???');//????????????
					sheet.getRow(2).getCell(6).setCellValue("?????????"+byId.getNetWetUnit()+'???');//????????????
					sheet.getRow(2).getCell(7).setCellValue("?????????"+byId.getUnitPriceKnd()+'???');//????????????
					sheet.getRow(2).getCell(8).setCellValue("?????????"+byId.getUnit()+'???');//????????????
				}

				row1Cell1 = row1.getCell(0);
				row1Cell2 = row1.createCell(13);

			}

			if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1 && inoutYardHead.getInoutYardNam().indexOf("??????") != -1) {
				if(byId != null) {
					Row row0 = sheet.getRow(0);
					WarehouseNamInfo warehouseNamInfo = new WarehouseNamInfo();
					warehouseNamInfo.setExportCod(byId.getExportCod());
					warehouseNamInfo.setWarehouseNam(byId.getWarehouseNam());
					warehouseNamInfo.setInOut("0");
					List<WarehouseNamInfo> listName = warehouseNamInfoService.list(Wrappers.query(warehouseNamInfo));
					row0.getCell(0).setCellValue(listName.get(0).getInoutYardNam());
//					row0.getCell(0).setCellValue(inoutYardHead.getModel());

					sheet.getRow(2).getCell(5).setCellValue("?????????"+byId.getNetWetUnit()+'???');//????????????
					sheet.getRow(2).getCell(6).setCellValue("?????????"+byId.getNetWetUnit()+'???');//????????????
					sheet.getRow(2).getCell(7).setCellValue("?????????"+byId.getUnitPriceKnd()+'???');//????????????
					sheet.getRow(2).getCell(8).setCellValue("?????????"+byId.getUnit()+'???');//????????????
				}
				row1Cell1 = row1.getCell(0);
				row1Cell2 = row1.createCell(13);

			}

			InoutYardBody inoutYardBody = new InoutYardBody();
			if (byId != null) {
				if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1) {
					row1Cell1.setCellValue(row1Cell1.getStringCellValue() + byId.getSettledCompany());
					row1Cell2.setCellValue(byId.getInoutYardNo());
				}

				if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1) {
					row1Cell1.setCellValue(row1Cell1.getStringCellValue() + byId.getWarehouseNam());
					row1Cell2.setCellValue(byId.getInoutYardNo());
				}

				inoutYardBody.setHFid(inoutYardHead.getFId());
				List<InoutYardBody> list = inoutYardBodyService.list(Wrappers.query(inoutYardBody));
				List<InoutYardHead> headList = new ArrayList<>();
				headList.add(byId);
				//?????????????????????????????????address??????
				List<CellRangeAddress> originMerged = sheet.getMergedRegions();
				List<Map<String, Object>> listttt = new ArrayList<>();
				for (CellRangeAddress cellRangeAddress : originMerged) {
					Map<String, Object> map = new HashMap<>();
					map.put("firstRow", cellRangeAddress.getFirstRow());
					map.put("firstColumn", cellRangeAddress.getFirstColumn());
					map.put("lastRow", cellRangeAddress.getLastRow());
					map.put("lastColumn", cellRangeAddress.getLastColumn());
					map.put("numberOfCells", cellRangeAddress.getNumberOfCells());
					listttt.add(map);
				}
				ExcelUtil.exportExcel(inoutYardHead.getInoutYardNam(), wb, list, InoutYardBody.class, 3, 2, request, response);
				if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1) {
					ExcelUtil.exportExcel2(inoutYardHead.getInoutYardNam(), wb, list, InoutYardBody.class, 3 + list.size(), 3 + list.size(), request, response);
					ExcelUtil.exportExcel(byId.getInoutYardNam(), wb, headList, InoutYardHead.class, 4 + list.size() + 1, 3 + list.size() + 1, request, response);
				}

				if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1) {
					ExcelUtil.exportExcel3(inoutYardHead.getInoutYardNam(), wb, list, InoutYardBody.class, 3 + list.size(), 3 + list.size(), request, response);
//					ExcelUtil.exportExcel(byId.getInoutYardNam(), wb, headList, InoutYardHead.class, 4 + list.size(), 3 + list.size(), request, response);
					ExcelUtil.exportExcel(byId.getInoutYardNam(), wb, headList, InoutYardHead.class, 4 + list.size() + 1, 3 + list.size() + 1, request, response);
				}


				//?????????????????????for??????
				if (list.size() > 0) {
					if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1 && inoutYardHead.getInoutYardNam().indexOf("??????") != -1) {

						for(int i=1;i<=list.size();i++){
							Row row_bus3_3 = sheet.getRow(2+i );
							short a1 = row_bus3_3.getHeight();
							row_bus3_3.setHeight((short)(a1*2.5));
						}


						Row row_bus3 = sheet.getRow(5 + list.size());
						short a = row_bus3.getHeight();
						row_bus3.setHeight((short)(a*2.5));


						CellRangeAddress newCellRangeAddress = new CellRangeAddress(3 + list.size(), 3 + list.size(),
								0,
								1);

						sheet.addMergedRegion(newCellRangeAddress);
						CellRangeAddress newCellRangeAddress2 = new CellRangeAddress(3 + list.size() + 2, 3 + list.size() + 2,
								2,
								3);
						sheet.addMergedRegion(newCellRangeAddress2);
						CellRangeAddress newCellRangeAddress3 = new CellRangeAddress(3 + list.size() + 2, 3 + list.size() + 2,
								4,
								5);
						sheet.addMergedRegion(newCellRangeAddress3);
						CellRangeAddress newCellRangeAddress4 = new CellRangeAddress(3 + list.size() + 2, 3 + list.size() + 2,
								7,
								8);
						sheet.addMergedRegion(newCellRangeAddress4);
						CellRangeAddress newCellRangeAddress5 = new CellRangeAddress(3 + list.size() + 2, 3 + list.size() + 2,
								9,
								12);
						sheet.addMergedRegion(newCellRangeAddress5);
						CellRangeAddress newCellRangeAddress6 = new CellRangeAddress(4 + list.size() + 2, 4 + list.size() + 2,
								0,
								5);
						sheet.addMergedRegion(newCellRangeAddress6);

						if("?????????".equals(inoutYardHead.getModel())){
							Row row_bus = sheet.getRow(4 + list.size() + 2);
							row_bus.getCell(6).setCellValue("");
						}
						CellRangeAddress newCellRangeAddress7 = new CellRangeAddress(4 + list.size() + 2, 4 + list.size() + 2,
								6,
								12);
						sheet.addMergedRegion(newCellRangeAddress7);

					}

					if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1 && inoutYardHead.getInoutYardNam().indexOf("??????") != -1) {

						for(int i=1;i<=list.size();i++){
							Row row_bus3_3 = sheet.getRow(2+i );
							short a1 = row_bus3_3.getHeight();
							row_bus3_3.setHeight((short)(a1*2.5));
						}


						Row row_bus3 = sheet.getRow(5 + list.size());
						short a = row_bus3.getHeight();
						row_bus3.setHeight((short)(a*2.5));


						CellRangeAddress newCellRangeAddress = new CellRangeAddress(3 + list.size(), 3 + list.size(),
								0,
								1);
						sheet.addMergedRegion(newCellRangeAddress);
						CellRangeAddress newCellRangeAddress2 = new CellRangeAddress(3 + list.size() + 2, 3 + list.size() + 2,
								3,
								4);
						sheet.addMergedRegion(newCellRangeAddress2);
						CellRangeAddress newCellRangeAddress3 = new CellRangeAddress(3 + list.size() + 2, 3 + list.size() + 2,
								5,
								6);
						sheet.addMergedRegion(newCellRangeAddress3);
						CellRangeAddress newCellRangeAddress4 = new CellRangeAddress(3 + list.size() + 2, 3 + list.size() + 2,
								8,
								10);
						sheet.addMergedRegion(newCellRangeAddress4);
						CellRangeAddress newCellRangeAddress5 = new CellRangeAddress(3 + list.size() + 2, 3 + list.size() + 2,
								11,
								13);
						sheet.addMergedRegion(newCellRangeAddress5);
//						CellRangeAddress newCellRangeAddress6 = new CellRangeAddress(3 + list.size() + 2, 3 + list.size() + 2,
//								11,
//								13);
//						sheet.addMergedRegion(newCellRangeAddress6);
						CellRangeAddress newCellRangeAddress7 = new CellRangeAddress(4 + list.size() + 2, 4 + list.size() + 2,
								0,
								6);
						sheet.addMergedRegion(newCellRangeAddress7);
						if("?????????".equals(inoutYardHead.getModel())){
							Row row_bus = sheet.getRow(4 + list.size() + 2);
							row_bus.getCell(7).setCellValue("");
						}
						CellRangeAddress newCellRangeAddress8 = new CellRangeAddress(4 + list.size() + 2, 4 + list.size() + 2,
								7,
								13);
						sheet.addMergedRegion(newCellRangeAddress8);
					}

					if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1 && inoutYardHead.getInoutYardNam().indexOf("??????") != -1) {
						Row row_bus = sheet.getRow(6 + list.size());
						row_bus.getCell(2).setCellValue(byId.getBusinessType());

						for(int i=1;i<=list.size();i++){
							Row row_bus3_3 = sheet.getRow(2+i );
							short a1 = row_bus3_3.getHeight();
							row_bus3_3.setHeight((short)(a1*2.5));
						}


						Row row_bus3 = sheet.getRow(5 + list.size());
						short a = row_bus3.getHeight();
						row_bus3.setHeight((short)(a*2.5));



						if("?????????".equals(inoutYardHead.getModel())){
							Row row_bus2 = sheet.getRow(8 + list.size());
							row_bus2.getCell(9).setCellValue("");
						}

						CellRangeAddress newCellRangeAddress2 = new CellRangeAddress(5 + list.size(), 5 + list.size(),
								1,
								5);
						sheet.addMergedRegion(newCellRangeAddress2);
						CellRangeAddress newCellRangeAddress3 = new CellRangeAddress(5 + list.size(), 5 + list.size(),
								6,
								8);
						sheet.addMergedRegion(newCellRangeAddress3);
//						CellRangeAddress newCellRangeAddress4 = new CellRangeAddress(5 + list.size(), 5 + list.size(),
//								9,
//								9);
//						sheet.addMergedRegion(newCellRangeAddress4);
						CellRangeAddress newCellRangeAddress5 = new CellRangeAddress(5 + list.size(), 5 + list.size(),
								10,
								11);
						sheet.addMergedRegion(newCellRangeAddress5);
						CellRangeAddress newCellRangeAddress51 = new CellRangeAddress(5 + list.size(), 5 + list.size(),
								12,
								13);
						sheet.addMergedRegion(newCellRangeAddress51);

						CellRangeAddress newCellRangeAddress52 = new CellRangeAddress(6 + list.size(), 6 + list.size(),
								2,
								5);
						sheet.addMergedRegion(newCellRangeAddress52);
						CellRangeAddress newCellRangeAddress53 = new CellRangeAddress(6 + list.size(), 6 + list.size(),
								6,
								8);
						sheet.addMergedRegion(newCellRangeAddress53);
						CellRangeAddress newCellRangeAddress54 = new CellRangeAddress(6 + list.size(), 6 + list.size(),
								10,
								11);
						sheet.addMergedRegion(newCellRangeAddress54);
						CellRangeAddress newCellRangeAddress55 = new CellRangeAddress(6 + list.size(), 6 + list.size(),
								12,
								13);
						sheet.addMergedRegion(newCellRangeAddress55);

						CellRangeAddress newCellRangeAddress6 = new CellRangeAddress(8 + list.size(), 8 + list.size(),
								0,
								4);
						sheet.addMergedRegion(newCellRangeAddress6);
						CellRangeAddress newCellRangeAddress7 = new CellRangeAddress(8 + list.size(), 8 + list.size(),
								5,
								8);
						sheet.addMergedRegion(newCellRangeAddress7);
						CellRangeAddress newCellRangeAddress8 = new CellRangeAddress(8 + list.size(), 8 + list.size(),
								9,
								13);
						sheet.addMergedRegion(newCellRangeAddress8);

					}

					if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1 && inoutYardHead.getInoutYardNam().indexOf("??????") != -1) {
						if("?????????".equals(inoutYardHead.getModel())){
							Row row_bus2 = sheet.getRow(7 + list.size());
							row_bus2.getCell(9).setCellValue("");
						}

						for(int i=1;i<=list.size();i++){
							Row row_bus3_3 = sheet.getRow(2+i );
							short a1 = row_bus3_3.getHeight();
							row_bus3_3.setHeight((short)(a1*2.5));
						}


						Row row_bus3 = sheet.getRow(5 + list.size());
						short a = row_bus3.getHeight();
						row_bus3.setHeight((short)(a*2.5));

						CellRangeAddress newCellRangeAddress2 = new CellRangeAddress(5 + list.size(), 5 + list.size(),
								1,
								2);
						sheet.addMergedRegion(newCellRangeAddress2);
						CellRangeAddress newCellRangeAddress3 = new CellRangeAddress(5 + list.size(), 5 + list.size(),
								3,
								4);
						sheet.addMergedRegion(newCellRangeAddress3);
						CellRangeAddress newCellRangeAddress4 = new CellRangeAddress(5 + list.size(), 5 + list.size(),
								5,
								6);
						sheet.addMergedRegion(newCellRangeAddress4);
						CellRangeAddress newCellRangeAddress5 = new CellRangeAddress(5 + list.size(), 5 + list.size(),
								7,
								8);
						sheet.addMergedRegion(newCellRangeAddress5);
						CellRangeAddress newCellRangeAddress5_1 = new CellRangeAddress(5 + list.size(), 5 + list.size(),
								9,
								11);
						sheet.addMergedRegion(newCellRangeAddress5_1);
						CellRangeAddress newCellRangeAddress5_2 = new CellRangeAddress(5 + list.size(), 5 + list.size(),
								12,
								13);
						sheet.addMergedRegion(newCellRangeAddress5_2);




						CellRangeAddress newCellRangeAddress6 = new CellRangeAddress(7 + list.size(), 7 + list.size(),
								0,
								4);
						sheet.addMergedRegion(newCellRangeAddress6);
						CellRangeAddress newCellRangeAddress7 = new CellRangeAddress(7 + list.size(), 7 + list.size(),
								5,
								8);
						sheet.addMergedRegion(newCellRangeAddress7);
						CellRangeAddress newCellRangeAddress8 = new CellRangeAddress(7 + list.size(), 7 + list.size(),
								9,
								13);
						sheet.addMergedRegion(newCellRangeAddress8);

					}


//					for(Map<String,Object> cellRangeAddress : listttt) {
//
//						//?????????8???????????????index????????????????????????????????????
//						if(Integer.parseInt(cellRangeAddress.get("firstRow")+"") >= 5) {
//							//????????????????????????????????????????????????????????????1
//							int firstRow = Integer.parseInt(cellRangeAddress.get("firstRow")+"") + list.size();
//							CellRangeAddress newCellRangeAddress = new CellRangeAddress(firstRow,
//									(firstRow + (Integer.parseInt(cellRangeAddress.get("lastRow")+"") - Integer.parseInt(cellRangeAddress.get("firstRow")+""))),
//									Integer.parseInt(cellRangeAddress.get("firstColumn")+""),
//									Integer.parseInt(cellRangeAddress.get("lastColumn")+""));
//							sheet.addMergedRegion(newCellRangeAddress);
//						}
//					}
				}


			}

			response.setContentType("application/binary;charset=UTF-8");
			output = response.getOutputStream();
			String fn = new String(inoutYardHead.getInoutYardNam().getBytes(), "UTF-8");
			response.setHeader("ContentDisposition", URLEncoder.encode("" + fn + ".xls","UTF-8"));
			wb.write(output);
			output.flush();
			output.close();

//			response.setContentType("application/zip; charset=UTF-8");
//			//?????????????????????????????????????????????
//			String agent = request.getHeader("USER-AGENT");
//			String downloadName = "??????????????????.zip";
//			//??????IE?????????IE????????????????????????
//			if (agent.contains("MSIE") || agent.contains("Trident")) {
//				downloadName = java.net.URLEncoder.encode(downloadName, "UTF-8");
//			} else {
//				downloadName = new String(downloadName.getBytes("UTF-8"), "ISO-8859-1");
//			}
//			response.setHeader("Content-disposition", "attachment;filename=" + downloadName);
//
//			ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
//			//??????????????????????????????
//			// --start
//			ZipEntry entry = new ZipEntry("??????????????????.xls");
//			zipOutputStream.putNextEntry(entry);
//
//			ByteOutputStream byteOutputStream = new ByteOutputStream();
//			wb.write(byteOutputStream);
//			byteOutputStream.writeTo(zipOutputStream);
////            zipOutputStream.write(workbook.getBytes());
//			byteOutputStream.close();
//			zipOutputStream.closeEntry();
//			// --end
//
//			response.setHeader("ContentDisposition", URLEncoder.encode("???????????????.zip","UTF-8"));
//			zipOutputStream.close();


		} catch (IOException e) {
			e.printStackTrace();
		}

	}








	public HSSFWorkbook getExcelZip(InoutYardHead inoutYardHead, HttpServletRequest request, HttpServletResponse response, int cnt){
		InoutYardHead byId = inoutYardHeadService.getById(inoutYardHead.getFId());
		if("??????".equals(inoutYardHead.getOperNam())){
			inoutYardHead.setInoutYardNam(inoutYardHead.getInoutYardNam()+"??????");
		}
		InputStream path = this.getClass().getResourceAsStream("/file/"+inoutYardHead.getInoutYardNam()+".xls");
		OutputStream output = null;
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(path);
			HSSFSheet sheet = wb.getSheetAt(0);
			Row row1 = sheet.getRow(1);

			Cell row1Cell1 = null;
			Cell row1Cell2 = null;
			if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1 && inoutYardHead.getInoutYardNam().indexOf("??????") != -1) {
				row1Cell1 = row1.getCell(0);
				row1Cell2 = row1.createCell(11);
			}

			if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1 && inoutYardHead.getInoutYardNam().indexOf("??????") != -1) {
				row1Cell1 = row1.getCell(0);
				row1Cell2 = row1.createCell(13);
			}

			if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1 && inoutYardHead.getInoutYardNam().indexOf("??????") != -1) {
				row1Cell1 = row1.getCell(0);
				row1Cell2 = row1.createCell(9);
			}

			if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1 && inoutYardHead.getInoutYardNam().indexOf("??????") != -1) {
				row1Cell1 = row1.getCell(0);
				row1Cell2 = row1.createCell(10);
			}

			InoutYardBody inoutYardBody = new InoutYardBody();
			if (byId != null) {
				if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1) {
					row1Cell1.setCellValue(row1Cell1.getStringCellValue() + byId.getSettledCompany());
					row1Cell2.setCellValue(byId.getInoutYardNo()+"-"+cnt);
				}

				if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1) {
					row1Cell1.setCellValue(row1Cell1.getStringCellValue() + byId.getWarehouseNam());
					row1Cell2.setCellValue(byId.getInoutYardNo());
				}

				inoutYardBody.setHFid(inoutYardHead.getFId());
				List<InoutYardBody> list = inoutYardBodyService.list(Wrappers.query(inoutYardBody));
				List<InoutYardHead> headList = new ArrayList<>();
				headList.add(byId);
				//?????????????????????????????????address??????
				List<CellRangeAddress> originMerged = sheet.getMergedRegions();
				List<Map<String, Object>> listttt = new ArrayList<>();
				for (CellRangeAddress cellRangeAddress : originMerged) {
					Map<String, Object> map = new HashMap<>();
					map.put("firstRow", cellRangeAddress.getFirstRow());
					map.put("firstColumn", cellRangeAddress.getFirstColumn());
					map.put("lastRow", cellRangeAddress.getLastRow());
					map.put("lastColumn", cellRangeAddress.getLastColumn());
					map.put("numberOfCells", cellRangeAddress.getNumberOfCells());
					listttt.add(map);
				}
				ExcelUtil.exportExcel(inoutYardHead.getInoutYardNam(), wb, list, InoutYardBody.class, 3, 2, request, response);
				if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1) {
					ExcelUtil.exportExcel2(byId.getInoutYardNam(), wb, list, InoutYardBody.class, 3 + list.size(), 3 + list.size(), request, response);
					ExcelUtil.exportExcel(byId.getInoutYardNam(), wb, headList, InoutYardHead.class, 4 + list.size() + 1, 3 + list.size() + 1, request, response);
				}

				if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1) {
					ExcelUtil.exportExcel(byId.getInoutYardNam(), wb, headList, InoutYardHead.class, 4 + list.size(), 3 + list.size(), request, response);
				}


				//?????????????????????for??????
				if (list.size() > 0) {
					if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1 && inoutYardHead.getInoutYardNam().indexOf("??????") != -1) {
						CellRangeAddress newCellRangeAddress = new CellRangeAddress(3 + list.size(), 3 + list.size(),
								0,
								1);

						sheet.addMergedRegion(newCellRangeAddress);
						CellRangeAddress newCellRangeAddress2 = new CellRangeAddress(3 + list.size() + 2, 3 + list.size() + 2,
								2,
								3);
						sheet.addMergedRegion(newCellRangeAddress2);
						CellRangeAddress newCellRangeAddress3 = new CellRangeAddress(3 + list.size() + 2, 3 + list.size() + 2,
								4,
								5);
						sheet.addMergedRegion(newCellRangeAddress3);
						CellRangeAddress newCellRangeAddress4 = new CellRangeAddress(3 + list.size() + 2, 3 + list.size() + 2,
								7,
								8);
						sheet.addMergedRegion(newCellRangeAddress4);
						CellRangeAddress newCellRangeAddress5 = new CellRangeAddress(3 + list.size() + 2, 3 + list.size() + 2,
								9,
								11);
						sheet.addMergedRegion(newCellRangeAddress5);
						CellRangeAddress newCellRangeAddress6 = new CellRangeAddress(4 + list.size() + 2, 4 + list.size() + 2,
								0,
								3);
						sheet.addMergedRegion(newCellRangeAddress6);
						CellRangeAddress newCellRangeAddress7 = new CellRangeAddress(4 + list.size() + 2, 4 + list.size() + 2,
								4,
								11);
						sheet.addMergedRegion(newCellRangeAddress7);

					}

					if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1 && inoutYardHead.getInoutYardNam().indexOf("??????") != -1) {
						CellRangeAddress newCellRangeAddress = new CellRangeAddress(3 + list.size(), 3 + list.size(),
								0,
								1);
						sheet.addMergedRegion(newCellRangeAddress);
						CellRangeAddress newCellRangeAddress2 = new CellRangeAddress(3 + list.size() + 2, 3 + list.size() + 2,
								2,
								3);
						sheet.addMergedRegion(newCellRangeAddress2);
						CellRangeAddress newCellRangeAddress3 = new CellRangeAddress(3 + list.size() + 2, 3 + list.size() + 2,
								4,
								5);
						sheet.addMergedRegion(newCellRangeAddress3);
						CellRangeAddress newCellRangeAddress4 = new CellRangeAddress(3 + list.size() + 2, 3 + list.size() + 2,
								6,
								7);
						sheet.addMergedRegion(newCellRangeAddress4);
						CellRangeAddress newCellRangeAddress5 = new CellRangeAddress(3 + list.size() + 2, 3 + list.size() + 2,
								9,
								10);
						sheet.addMergedRegion(newCellRangeAddress5);
						CellRangeAddress newCellRangeAddress6 = new CellRangeAddress(3 + list.size() + 2, 3 + list.size() + 2,
								11,
								13);
						sheet.addMergedRegion(newCellRangeAddress6);
						CellRangeAddress newCellRangeAddress7 = new CellRangeAddress(4 + list.size() + 2, 4 + list.size() + 2,
								0,
								5);
						sheet.addMergedRegion(newCellRangeAddress7);
						CellRangeAddress newCellRangeAddress8 = new CellRangeAddress(4 + list.size() + 2, 4 + list.size() + 2,
								6,
								13);
						sheet.addMergedRegion(newCellRangeAddress8);
					}

					if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1 && inoutYardHead.getInoutYardNam().indexOf("??????") != -1) {
						CellRangeAddress newCellRangeAddress2 = new CellRangeAddress(4 + list.size(), 4 + list.size(),
								2,
								3);
						sheet.addMergedRegion(newCellRangeAddress2);
						CellRangeAddress newCellRangeAddress3 = new CellRangeAddress(4 + list.size(), 4 + list.size(),
								4,
								5);
						sheet.addMergedRegion(newCellRangeAddress3);
						CellRangeAddress newCellRangeAddress4 = new CellRangeAddress(4 + list.size(), 4 + list.size(),
								6,
								7);
						sheet.addMergedRegion(newCellRangeAddress4);
						CellRangeAddress newCellRangeAddress5 = new CellRangeAddress(4 + list.size(), 4 + list.size(),
								8,
								9);
						sheet.addMergedRegion(newCellRangeAddress5);
						CellRangeAddress newCellRangeAddress6 = new CellRangeAddress(5 + list.size(), 5 + list.size(),
								0,
								2);
						sheet.addMergedRegion(newCellRangeAddress6);
						CellRangeAddress newCellRangeAddress7 = new CellRangeAddress(5 + list.size(), 5 + list.size(),
								3,
								5);
						sheet.addMergedRegion(newCellRangeAddress7);
						CellRangeAddress newCellRangeAddress8 = new CellRangeAddress(5 + list.size(), 5 + list.size(),
								6,
								9);
						sheet.addMergedRegion(newCellRangeAddress8);
					}

					if (inoutYardHead.getInoutYardNam().indexOf("????????????") != -1 && inoutYardHead.getInoutYardNam().indexOf("??????") != -1) {
						CellRangeAddress newCellRangeAddress2 = new CellRangeAddress(4 + list.size(), 4 + list.size(),
								2,
								3);
						sheet.addMergedRegion(newCellRangeAddress2);
						CellRangeAddress newCellRangeAddress3 = new CellRangeAddress(4 + list.size(), 4 + list.size(),
								4,
								5);
						sheet.addMergedRegion(newCellRangeAddress3);
						CellRangeAddress newCellRangeAddress4 = new CellRangeAddress(4 + list.size(), 4 + list.size(),
								6,
								7);
						sheet.addMergedRegion(newCellRangeAddress4);
						CellRangeAddress newCellRangeAddress5 = new CellRangeAddress(4 + list.size(), 4 + list.size(),
								9,
								10);
						sheet.addMergedRegion(newCellRangeAddress5);
						CellRangeAddress newCellRangeAddress6 = new CellRangeAddress(5 + list.size(), 5 + list.size(),
								0,
								2);
						sheet.addMergedRegion(newCellRangeAddress6);
						CellRangeAddress newCellRangeAddress7 = new CellRangeAddress(5 + list.size(), 5 + list.size(),
								3,
								5);
						sheet.addMergedRegion(newCellRangeAddress7);
						CellRangeAddress newCellRangeAddress8 = new CellRangeAddress(5 + list.size(), 5 + list.size(),
								6,
								10);
						sheet.addMergedRegion(newCellRangeAddress8);

					}


//					for(Map<String,Object> cellRangeAddress : listttt) {
//
//						//?????????8???????????????index????????????????????????????????????
//						if(Integer.parseInt(cellRangeAddress.get("firstRow")+"") >= 5) {
//							//????????????????????????????????????????????????????????????1
//							int firstRow = Integer.parseInt(cellRangeAddress.get("firstRow")+"") + list.size();
//							CellRangeAddress newCellRangeAddress = new CellRangeAddress(firstRow,
//									(firstRow + (Integer.parseInt(cellRangeAddress.get("lastRow")+"") - Integer.parseInt(cellRangeAddress.get("firstRow")+""))),
//									Integer.parseInt(cellRangeAddress.get("firstColumn")+""),
//									Integer.parseInt(cellRangeAddress.get("lastColumn")+""));
//							sheet.addMergedRegion(newCellRangeAddress);
//						}
//					}
				}


			}
			return wb;
		}catch (Exception e){
			e.printStackTrace();
			return wb;
		}
	}

}
