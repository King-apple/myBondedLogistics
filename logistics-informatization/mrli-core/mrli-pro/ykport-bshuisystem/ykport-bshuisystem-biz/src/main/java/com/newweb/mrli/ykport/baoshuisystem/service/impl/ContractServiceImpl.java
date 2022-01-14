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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.xdevapi.JsonArray;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.dto.ContractInfoDto;
import com.newweb.mrli.ykport.baoshuisystem.entity.*;
import com.newweb.mrli.ykport.baoshuisystem.mapper.ContractMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.*;
import com.newweb.mrli.ykport.baoshuisystem.vo.ContractFileVo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 合同信息管理表
 *
 * @author pigx code generator
 * @date 2021-08-03 09:54:56
 */
@Service
public class ContractServiceImpl extends ServiceImpl<ContractMapper, Contract> implements ContractService {

	@Autowired
	private ContractMapper ContractMapper;
	@Autowired
	private ParameterListService parameterListService;
	@Autowired
	private OrganizerInfoService organizerInfoService;
	@Autowired
	private ClientInfoService clientInfoService;
	@Autowired
	private ContractObjectService contractObjectService;
	@Autowired
	private CodeListService codeListService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private ApprovalRecordService approvalRecordService;
	@Autowired
	private ApprovalProcessService approvalProcessService;
	@Autowired
	private BSUserService bSUserService;
	@Autowired
	private ApprovalProcessSetService approvalProcessSetService;
	@Autowired
	private SmsdbService smsdbService;


	@Override
	public boolean insertContract(Contract contract) {
		contract.setRecordTim(new Date());
		contract.setOperNam(SecurityUtils.getUser().getUsername());
		return this.save(contract);
	}

	@Override
	public IPage<ContractFileVo> selectPageVo(Page<?> page, ContractFileVo contractFileVo) {
		return ContractMapper.selectPageVo(page, contractFileVo);
	}


	@Override
	public int updateContract(Contract contract) {
		return ContractMapper.updateContract(contract);
	}

	@Override
	@Transactional
	public ContractInfoDto getContractInsertValue() {
		String contractName = "";
		ParameterList parameterList = new ParameterList();
		parameterList.setParamCod("contract_name");
		QueryWrapper<ParameterList> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(parameterList);
		List<ParameterList> list = parameterListService.list(queryWrapper.isNull("del_nam"));
		if (!list.isEmpty()) {
			contractName = list.get(0).getParamValue();
		}
		List<OrganizerInfo> organizerInfos = organizerInfoService.selectAll(new OrganizerInfo());
		String username = SecurityUtils.getUser().getManName();
		Integer dept = SecurityUtils.getUser().getDeptId();
		List<ClientInfo> clientInfos = clientInfoService.selectAll(new ClientInfo());
		List<ContractObject> contractObjects = contractObjectService.selectAll(new ContractObject());
		String cont_no = "YGWL-";

		ContractInfoDto contractInfoDto = new ContractInfoDto();
		contractInfoDto.setContractName(contractName);
		contractInfoDto.setClientInfos(clientInfos);
		contractInfoDto.setCont_no(cont_no);
		contractInfoDto.setContractObjects(contractObjects);
		contractInfoDto.setDeptName(dept + "");
		contractInfoDto.setUsername(username);
		contractInfoDto.setOrganizerInfos(organizerInfos);
		return contractInfoDto;
	}

	@Override
	public R insertCon(Contract contract) {
		contract.setOperNam(SecurityUtils.getUser().getUsername());
		contract.setRecordTim(new Date());
		contract.setContractStatus("0");
		BSUser bsUser = new BSUser();
		bsUser.setUsername(SecurityUtils.getUser().getUsername());
		List<BSUser> bsuserList = getBsuserList(bsUser);
		contract.setUserSubDeptId(bsuserList.get(0).getDeptSubId() + "");
		//校验合同编号
		if (contract.getContNo() == null || contract.getContNo().equals("")) {
			String conCode = "YGWL-" + contract.getDeptNo();
			CodeList codeList = new CodeList();
			codeList.setParamName(contract.getDeptNo());
			CodeList contractCode = codeListService.getContractCode(codeList);
			conCode = conCode + "-" + contractCode.getParamCode() + "-" + contractCode.getParamDate();
			contract.setContNo(conCode);
		}

		Contract contractCheck = new Contract();
		contractCheck.setContNo(contract.getContNo());
		QueryWrapper<Contract> query = Wrappers.query(contractCheck);
		List<Contract> list = list(query);
		if (!list.isEmpty()) {
			return R.ok(0);
		} else {
			CodeList codeList = new CodeList();
			String[] code = contract.getContNo().split("-");
			codeList.setParamName(code[1]);
			codeList.setParamCode(code[2]);
			codeList.setParamDate(code[3]);
			if (code.length > 4) {
				codeList.setParamDate(code[4]);
			}
			insOrUpdateContractCode(codeList);
		}
		contract.setConFile("0");
		contract.setCancelStatus("0");
		boolean boo = save(contract);
		return R.ok(contract);
	}

	@DS("slave")
	public List<BSUser> getBsuserList(BSUser bsUser) {
		return bSUserService.list(Wrappers.query(bsUser));
	}

	@Override
	public void exportExcel(Long fid, HttpServletRequest request, HttpServletResponse response) {

//		Contract contract = getById(fid);
		InputStream path = this.getClass().getResourceAsStream("/file/contract.xls");


		Contract contract = getById(fid);
		ApprovalProcessSet approvalProcessSet = new ApprovalProcessSet();
		approvalProcessSet.setBillFid(fid);
		approvalProcessSet.setProcessNo("0");
		List<ApprovalProcessSet> list = approvalProcessSetService.list(Wrappers.query(approvalProcessSet));

		List<ApprovalProcessSet> cancelList = new ArrayList<>();
		if (contract.getCancelStatus() != null && !"0".equals(contract.getCancelStatus())) {
			approvalProcessSet.setProcessNo("3");
			cancelList = approvalProcessSetService.list(Wrappers.query(approvalProcessSet));
		}
		String undertakerComments = "";
		String functionalComments = "";
		String contractManagerComments = "";
		String cantonalLeadersComments = "";
		String companyLeadersComments = "";
		BSUser bsUser = new BSUser();
//		ApprovalProcess approvalProcess = new ApprovalProcess();
//		approvalProcess.setProcessNo("0");
//		List<ApprovalProcess> approvalProcessList = approvalProcessService.list(Wrappers.query(approvalProcess).isNull("DEL_NAM"));
		//合同审批详情打印
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getOpinion() == null || "".equals(list.get(i).getOpinion().trim())) {
				list.get(i).setOpinion(" ");
			} else {
				if (list.get(i).getOpinionExplain() == null || "".equals(list.get(i).getOpinionExplain().trim())) {
					list.get(i).setOpinionExplain(" ");
				}
				switch (list.get(i).getOrderBy()) {
					case 1:
						bsUser.setUsername(list.get(i).getApprover());
						List<BSUser> list1 = bSUserService.list(Wrappers.query(bsUser));
						undertakerComments += list1.get(0).getManName() + ":" + list.get(i).getOpinion() + "\r\n" + list.get(i).getOpinionExplain() + "\r\n";
						break;
					case 2:
						bsUser.setUsername(list.get(i).getApprover());
						List<BSUser> list2 = bSUserService.list(Wrappers.query(bsUser));
						functionalComments += list.get(i).getLinkNam() + "意见:" + list2.get(0).getManName() + ":" + list.get(i).getOpinion() + "\r\n" + list.get(i).getOpinionExplain() + "\r\n";
						break;
					case 3:
						bsUser.setUsername(list.get(i).getApprover());
						List<BSUser> list4 = bSUserService.list(Wrappers.query(bsUser));
						contractManagerComments += list4.get(0).getManName() + ":" + list.get(i).getOpinion() + "\r\n" + list.get(i).getOpinionExplain() + "\r\n";
						break;
					case 4:
						bsUser.setUsername(list.get(i).getApprover());
						List<BSUser> list5 = bSUserService.list(Wrappers.query(bsUser));
						cantonalLeadersComments += list5.get(0).getManName() + ":" + list.get(i).getOpinion() + "\r\n" + list.get(i).getOpinionExplain() + "\r\n";
						break;
					case 5:
						bsUser.setUsername(list.get(i).getApprover());
						List<BSUser> list6 = bSUserService.list(Wrappers.query(bsUser));
						companyLeadersComments += list6.get(0).getManName() + ":" + list.get(i).getOpinion() + "\r\n" + list.get(i).getOpinionExplain() + "\r\n";
						break;
				}
			}
		}
		//合同作废审批详情打印
		if (!cancelList.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
			for (int i = 0; i < cancelList.size(); i++) {
				String date = "";
				if (cancelList.get(i).getApprovalTim() != null) {
					date = sdf.format(cancelList.get(i).getApprovalTim());
				}
				if (cancelList.get(i).getOpinion() == null || "".equals(cancelList.get(i).getOpinion().trim())) {
					cancelList.get(i).setOpinion(" ");
				} else {
					if (cancelList.get(i).getOpinionExplain() == null || "".equals(cancelList.get(i).getOpinionExplain().trim())) {
						cancelList.get(i).setOpinionExplain(" ");
					}
					switch (cancelList.get(i).getOrderBy()) {
						case 1:
							bsUser.setUsername(cancelList.get(i).getApprover());
							List<BSUser> list1 = bSUserService.list(Wrappers.query(bsUser));

							undertakerComments += list1.get(0).getManName() + ":" + cancelList.get(i).getOpinion() + "作废\r\n" + cancelList.get(i).getOpinionExplain() + "\r\n" + date;
							break;
						case 2:
							bsUser.setUsername(cancelList.get(i).getApprover());
							List<BSUser> list2 = bSUserService.list(Wrappers.query(bsUser));
							functionalComments += cancelList.get(i).getLinkNam() + "意见:" + list2.get(0).getManName() + ":" + cancelList.get(i).getOpinion() + "作废\r\n" + cancelList.get(i).getOpinionExplain() + "\r\n" + date;
							break;
						case 3:
							bsUser.setUsername(cancelList.get(i).getApprover());
							List<BSUser> list4 = bSUserService.list(Wrappers.query(bsUser));
							contractManagerComments += list4.get(0).getManName() + ":" + cancelList.get(i).getOpinion() + "作废\r\n" + cancelList.get(i).getOpinionExplain() + "\r\n" + date;
							break;
						case 4:
							bsUser.setUsername(cancelList.get(i).getApprover());
							List<BSUser> list5 = bSUserService.list(Wrappers.query(bsUser));
							cantonalLeadersComments += list5.get(0).getManName() + ":" + cancelList.get(i).getOpinion() + "作废\r\n" + cancelList.get(i).getOpinionExplain() + "\r\n" + date;
							break;
						case 5:
							bsUser.setUsername(cancelList.get(i).getApprover());
							List<BSUser> list6 = bSUserService.list(Wrappers.query(bsUser));
							companyLeadersComments += list6.get(0).getManName() + ":" + cancelList.get(i).getOpinion() + "作废\r\n" + cancelList.get(i).getOpinionExplain() + "\r\n" + date;
							break;
					}
				}
			}
		}


		OutputStream output = null;
		try {
			HSSFWorkbook wb = new HSSFWorkbook(path);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
			cellStyle.setWrapText(true);
			cellStyle.setBorderBottom(BorderStyle.THIN);
			HSSFCellStyle cellStyle1 = wb.createCellStyle();
			cellStyle1.setWrapText(true);
			cellStyle1.setBorderBottom(BorderStyle.THIN);
			cellStyle1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			cellStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
			HSSFRow row = sheet.getRow(1);
			HSSFCell contCodeCell = row.getCell(3);
			contCodeCell.setCellValue(contract.getContNo());

			HSSFRow row1 = sheet.getRow(2);
			HSSFCell contDeptCell = row1.getCell(1);
			HSSFCell operatorCell = row1.getCell(3);
			contDeptCell.setCellValue(contract.getDept());
			operatorCell.setCellValue(contract.getOperator());

			HSSFRow row2 = sheet.getRow(3);
			HSSFCell secondCell = row2.getCell(1);
			HSSFCell contTypeCell = row2.getCell(3);
			secondCell.setCellValue(contract.getSecondNam());
			contTypeCell.setCellValue(contract.getContractType());

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			String strat = sdf.format(contract.getStartDte());
			String end = sdf.format(contract.getEndDte());
			HSSFRow row3 = sheet.getRow(4);
			HSSFCell contCargoCell = row3.getCell(1);
			HSSFCell contDateCell = row3.getCell(3);
			contCargoCell.setCellValue(contract.getCargoNam());
			contDateCell.setCellValue(strat + "-" + end);

			HSSFRow row4 = sheet.getRow(5);
			HSSFCell contCargoNumCell = row4.getCell(1);
			HSSFCell contCargoValCell = row4.getCell(3);
			contCargoNumCell.setCellValue(contract.getCargoNum() ==  null ? "" : contract.getCargoNum()+ "吨");
			contCargoValCell.setCellValue(contract.getCargoFee() ==  null ? "" : contract.getCargoFee()+"元");

			HSSFRow row5 = sheet.getRow(6);
			HSSFCell contConcatUserCell = row5.getCell(1);
			HSSFCell contPhoneCell = row5.getCell(3);
			contConcatUserCell.setCellValue(contract.getContact());
			contPhoneCell.setCellValue(contract.getPhone());

			HSSFRow row6 = sheet.getRow(7);
			HSSFCell undertakerCommentsCell = row6.getCell(1);
			undertakerCommentsCell.setCellValue(undertakerComments);
			undertakerCommentsCell.setCellStyle(cellStyle);
			undertakerCommentsCell.setCellStyle(cellStyle1);

			HSSFRow row7 = sheet.getRow(8);
			HSSFCell functionalCommentsCell = row7.getCell(1);
			functionalCommentsCell.setCellValue(functionalComments);
			functionalCommentsCell.setCellStyle(cellStyle);
			functionalCommentsCell.setCellStyle(cellStyle1);

			HSSFRow row8 = sheet.getRow(9);
			HSSFCell contractManagerCommentsCell = row8.getCell(1);
			contractManagerCommentsCell.setCellValue(contractManagerComments);
			contractManagerCommentsCell.setCellStyle(cellStyle);
			contractManagerCommentsCell.setCellStyle(cellStyle1);

			HSSFRow row9 = sheet.getRow(10);
			HSSFCell cantonalLeadersCommentsCell = row9.getCell(1);
			cantonalLeadersCommentsCell.setCellValue(cantonalLeadersComments);
			cantonalLeadersCommentsCell.setCellStyle(cellStyle);
			cantonalLeadersCommentsCell.setCellStyle(cellStyle1);

			HSSFRow row10 = sheet.getRow(11);
			HSSFCell companyLeadersCommentsCell = row10.getCell(1);
			companyLeadersCommentsCell.setCellValue(companyLeadersComments);
			companyLeadersCommentsCell.setCellStyle(cellStyle);
			companyLeadersCommentsCell.setCellStyle(cellStyle1);

			String name = contract.getContNam();
//			output = new FileOutputStream(name);
			response.setContentType("application/binary;charset=UTF-8");
			output = response.getOutputStream();
			String fn = new String(name.getBytes(), "UTF-8");
			response.setHeader("ContentDisposition", URLEncoder.encode(fn + ".xls", "UTF-8"));
			wb.write(output);
			output.flush();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public int insOrUpdateContractCode(CodeList codeList) {
		int i = 0;
		if (codeList.getParamCode().equals("001")) {
			i = codeListService.insertCodeList(codeList);
		} else {
			i = codeListService.updateCodeList(codeList);
		}
		return i;
	}


	@Override
	public IPage<ContractFileVo> selectPageVoJoinApprovalRecord(Page<?> page, ContractFileVo contractFileVo) {
		contractFileVo.setApprovalProcess(new ArrayList<>());
		if (contractFileVo.getApprovalProcessArray() != null) {
			for (int i = 0; i < contractFileVo.getApprovalProcessArray().length; i++) {

				ApprovalProcess a = JSONArray.parseObject(contractFileVo.getApprovalProcessArray()[i], ApprovalProcess.class);
				if (a.getLinkCode() != null && !"".equals(a.getLinkCode())) {
					contractFileVo.getApprovalProcess().add(a);
				}

			}

		}
		ApprovalProcess approvalProcess = new ApprovalProcess();
		approvalProcess.setProcessNo(contractFileVo.getProcessNo());
		approvalProcess.setLinkNam(contractFileVo.getLinkNam());

		Integer orderBy = 0;
		String isListByUser = "";

		List<ApprovalProcess> list = approvalProcessService.list(Wrappers.query(approvalProcess).isNull("DEL_TIM"));

		List<ApprovalProcess> proList = contractFileVo.getApprovalProcess();
		List<ApprovalProcess> linkList = new ArrayList<>();
		if (!proList.isEmpty()) {
			for (int i = 0; i < proList.size(); i++) {

				List<ApprovalProcess> app = approvalProcessService.list(Wrappers.query(proList.get(i)).isNull("DEL_TIM"));
				if (!app.isEmpty()) {
					linkList.add(app.get(0));
				}
			}
			contractFileVo.setApprovalProcess(linkList);
		}
		if (!list.isEmpty()) {
			orderBy = list.get(0).getOrderBy();
			isListByUser = list.get(0).getIsListbyuser();
		}
		if (isListByUser != null && !"".equals(isListByUser)) {
			contractFileVo.setFirstNam("");
			contractFileVo.setLinkNamArray(contractFileVo.getCompany().split(","));
		}
		if (orderBy != 0) {
			contractFileVo.setOrderBy(orderBy + "");
		}
		contractFileVo.setOperator(SecurityUtils.getUser().getUsername());
		if (contractFileVo.getApprovalProcess().size() > 0) {
			return ContractMapper.selectPageVoJoinApprovalRecord(page, contractFileVo);
		} else {
			return null;
		}

	}

	@Override
	public List<ContractFileVo> getEffectiveInfo(Contract contract) {
		List<ContractFileVo> list = baseMapper.getEffectiveInfo(contract);
		return list;
	}

	@Override
	public R updateApprovalprocessset(ApprovalProcessSet approvalProcessSet) throws Exception {
		ApprovalProcessSet byId = approvalProcessSetService.getById(approvalProcessSet.getFId());

		int isFinalFlag = 0;
		ApprovalProcess approvalProcess = new ApprovalProcess();
		approvalProcess.setProcessNo(byId.getProcessNo());
		List<ApprovalProcess> approvalProcessList = approvalProcessService.list(Wrappers.query(approvalProcess)
				.isNull("DEL_NAM")
				.orderByDesc("ORDER_BY"));
		QueryWrapper<ApprovalProcessSet> query = new QueryWrapper<>();
		query.eq("bill_fid", byId.getBillFid());

		query.eq("PROCESS_NO", approvalProcessSet.getProcessNo());
		query.isNull("DEL_NAM");
		//查询order最高的所有数据
		List<ApprovalProcessSet> list = new ArrayList<>();
//		if ("0".equals(approvalProcessSet.getCompleteApprovalFlag())) {
		if("1".equals(approvalProcessSet.getCompleteApprovalFlag())){
			ApprovalProcessSet checkMaxSelect = new ApprovalProcessSet();
			checkMaxSelect.setBillFid(byId.getBillFid());
			checkMaxSelect.setProcessNo("0");

			List<ApprovalProcessSet> checkMaxList = approvalProcessSetService.list(Wrappers.query(checkMaxSelect)
					.orderByDesc("ORDER_BY"));
			query.eq("ORDER_BY", checkMaxList.get(0).getOrderBy());
			list = approvalProcessSetService.list(query);
			if(!checkMaxList.isEmpty()){
				approvalProcessList.get(0).setOrderBy(checkMaxList.get(0).getOrderBy());
			}
		}else{
			query.eq("ORDER_BY", approvalProcessList.get(0).getOrderBy());
			list = approvalProcessSetService.list(query);
		}
		boolean flag = false;
		for (int i = 0; i < list.size(); i++) {
			if (!list.get(i).getUsername().equals(SecurityUtils.getUser().getUsername()) || list.size() == 1) {
				if ("1".equals(list.get(i).getIsAgree()) || list.size() == 1) {
					flag = true;
				} else {
					flag = false;
					break;
				}
			}
		}
		if (!approvalProcessList.isEmpty()) {
			if (flag) {
				if (byId.getOrderBy().equals(approvalProcessList.get(0).getOrderBy())) {
					isFinalFlag = 1;
				}
			}
		}


		int i = 0;
		Contract contract = new Contract();
		contract.setFId(byId.getBillFid());
		if (approvalProcessSet.getIsAgree() != null && approvalProcessSet.getIsAgree().equals("2")) {
			if ("0".equals(approvalProcessSet.getProcessNo())) {
				contract.setContractStatus("3");

			} else if ("3".equals(approvalProcessSet.getProcessNo())) {
				contract.setCancelStatus("3");
			}

			i = updateContract(contract);
//
//			ApprovalProcessSet isCancelApprovalRecord = new ApprovalProcessSet();
////			isCancelApprovalRecord.setIsCancel("1");
//			UpdateWrapper<ApprovalProcessSet> approvalRecordUpdateWrapper = new UpdateWrapper<>();
//			approvalRecordUpdateWrapper.eq("BILL_FID",byId.getBillFid());
//			approvalRecordUpdateWrapper.eq("PROCESS_NO",byId.getProcessNo());
//			boolean update = approvalProcessSetService.update(isCancelApprovalRecord, approvalRecordUpdateWrapper);

		} else if (isFinalFlag == 1 && approvalProcessSet.getIsAgree() != null && approvalProcessSet.getIsAgree().equals("1")) {
			if ("0".equals(approvalProcessSet.getProcessNo())) {
				contract.setContractStatus("2");
			} else if ("3".equals(approvalProcessSet.getProcessNo())) {
				contract.setCancelStatus("2");
			}

			i = updateContract(contract);
		} else {
			i = 1;
		}
		if (i == 0) {
			throw new Exception("更新失败");
		}
//		}
//		else if ("1".equals(approvalProcessSet.getCompleteApprovalFlag())) {
//
//			int i = 0;
//			Contract contract = new Contract();
//			contract.setFId(byId.getBillFid());
//			if (approvalProcessSet.getIsAgree() != null && approvalProcessSet.getIsAgree().equals("2")) {
//				if ("0".equals(approvalProcessSet.getProcessNo())) {
//					contract.setContractStatus("3");
//
//				} else if ("3".equals(approvalProcessSet.getProcessNo())) {
//					contract.setCancelStatus("3");
//				}
//
//				i = updateContract(contract);
//			} else if ( approvalProcessSet.getIsAgree() != null && approvalProcessSet.getIsAgree().equals("1")) {
//				if ("0".equals(approvalProcessSet.getProcessNo())) {
//					contract.setContractStatus("2");
//				} else if ("3".equals(approvalProcessSet.getProcessNo())) {
//					contract.setCancelStatus("2");
//				}
//
//				i = updateContract(contract);
//			} else {
//				i = 1;
//			}
//			if (i == 0) {
//				throw new Exception("更新失败");
//			}
//
//		}


		approvalProcessSet.setApprover(SecurityUtils.getUser().getUsername());
		approvalProcessSet.setApprovalTim(new Date());
		boolean b = approvalProcessSetService.updateById(approvalProcessSet);
		approvalProcessSet = approvalProcessSetService.getById(approvalProcessSet.getFId());
		approvalProcessSetService.sendMessage(approvalProcessSet, "您有待审批的合同 --保税物流系统");

		ApprovalRecord approvalRecord = new ApprovalRecord();
		approvalRecord.setPFid(approvalProcessSet.getProcessFid());
		approvalRecord.setCFid(approvalProcessSet.getBillFid());
		approvalRecord.setProcessCode(approvalProcessSet.getProcessNo());
		approvalRecord.setOrderBy(approvalProcessSet.getOrderBy());
		approvalRecord.setLinkNam(approvalProcessSet.getLinkNam());
		approvalRecord.setOpinion(approvalProcessSet.getOpinion());
		approvalRecord.setOpinionExplain(approvalProcessSet.getOpinionExplain());
		approvalRecord.setIsAgree(approvalProcessSet.getIsAgree());
		approvalRecord.setApprover(approvalProcessSet.getApprover());
		approvalRecord.setApprovalTim(new Date());
		approvalRecord.setApproverName(approvalProcessSet.getManName());

		return R.ok(approvalRecordService.save(approvalRecord));
	}

	@Override
	public R insertUserRecords(String userRecords) {
		System.out.println(userRecords);
		Map maps = (Map) JSON.parse(userRecords);
		int flag = 0;
		try {
			ApprovalProcessSet approvalRecord = new ApprovalProcessSet();
			approvalRecord.setBillFid(maps.get("fId") == null ? 0 : Long.parseLong(maps.get("fId").toString()));
			approvalRecord.setProcessNo(maps.get("processNo") == null ? "" : maps.get("processNo").toString());
			approvalRecord.setApprovalTim(new Date());
			approvalRecord.setIsAgree("0");
			approvalRecord.setOperNam(SecurityUtils.getUser().getUsername());
			approvalRecord.setRecordTim(new Date());
//			approvalRecord.setTimes(maps.get("times")==null?0:Integer.parseInt(maps.get("times").toString()));
			ApprovalProcess approvalProcess = new ApprovalProcess();
			approvalProcess.setProcessNo(maps.get("processNo") == null ? "" : maps.get("processNo").toString());
			QueryWrapper<ApprovalProcess> query = Wrappers.query(approvalProcess);
			List<ApprovalProcess> approvalProcessList = approvalProcessService.list(query);
			ApprovalProcessSet approvalProcessSet = new ApprovalProcessSet();
			approvalProcessSet.setProcessNo(maps.get("processNo") == null ? "" : maps.get("processNo").toString());
			approvalProcessSet.setBillFid(approvalRecord.getBillFid());
			boolean remove = approvalProcessSetService.remove(Wrappers.query(approvalProcessSet));
			if (!approvalProcessList.isEmpty()) {
				if(maps.get("completeApprovalFlag").toString().equals("0")){ //判断是是否完整审核状态 0：完整审核 1：不完整
					for (int i = 0; i < approvalProcessList.size(); i++) {
						JSONArray objects = JSONArray.parseArray(maps.get(approvalProcessList.get(i).getLinkNam()).toString());
						if (objects != null && objects.size() > 0) {
							flag = 1;
							approvalRecord.setProcessFid(approvalProcessList.get(i).getFId());
							approvalRecord.setOrderBy(approvalProcessList.get(i).getOrderBy());
							approvalRecord.setLinkNam(approvalProcessList.get(i).getLinkNam());
							approvalRecord.setRoleNam(approvalProcessList.get(i).getRoleNam());

//						approvalRecord.setLinkCode(approvalProcessList.get(i).getLinkCode());
							for (int j = 0; j < objects.size(); j++) {
//							approvalRecord.setApprover(objects.get(j).toString());
								approvalRecord.setUsername(objects.get(j).toString());
								BSUser bsUser = new BSUser();
								bsUser.setUsername(objects.get(j).toString());
								List<BSUser> userList = bSUserService.list(Wrappers.query(bsUser));

								if (!userList.isEmpty()) {
									approvalRecord.setManName(userList.get(0).getManName());
									approvalRecord.setUserId(userList.get(0).getUserId());
								}
//							approvalRecord.setManName(SecurityUtils.getUser().getManName());
								if (approvalRecord.getOrderBy() != null && approvalRecord.getOrderBy() == 1) {
									BSUser bsUser1 = new BSUser();
									bsUser1.setUsername(approvalRecord.getUsername());
									List<BSUser> list = bSUserService.list(Wrappers.query(bsUser1));
									Smsdb smsdb = new Smsdb();
									if (list.get(0).getPhone() != null && !"".equals(list.get(0).getPhone().trim())) {
										smsdb.setCallnum(list.get(0).getPhone());
										smsdb.setContent("您有待审批的合同 --保税物流系统");
										Date date = new Date();
										date.setMinutes(date.getMinutes() + 1);
										smsdb.setCreatetime(date);
										smsdb.setFlag("0");
										smsdb.setMsgtype("保税物流");
										smsdbService.save(smsdb);
									}

								}
								approvalProcessSetService.save(approvalRecord);
							}
						}
					}
				} else {
					int count = 1;
					for (int i = 0; i < approvalProcessList.size(); i++) {
						JSONArray objects = JSONArray.parseArray(maps.get(approvalProcessList.get(i).getLinkNam()).toString());
						if (objects != null && objects.size() > 0) {
							flag = 1;
							approvalRecord.setProcessFid(approvalProcessList.get(i).getFId());
//							approvalRecord.setOrderBy(approvalProcessList.get(i).getOrderBy());
							approvalRecord.setOrderBy(count++);
							approvalRecord.setLinkNam(approvalProcessList.get(i).getLinkNam());
							approvalRecord.setRoleNam(approvalProcessList.get(i).getRoleNam());

//						approvalRecord.setLinkCode(approvalProcessList.get(i).getLinkCode());
							for (int j = 0; j < objects.size(); j++) {
//							approvalRecord.setApprover(objects.get(j).toString());
								approvalRecord.setUsername(objects.get(j).toString());
								BSUser bsUser = new BSUser();
								bsUser.setUsername(objects.get(j).toString());
								List<BSUser> userList = bSUserService.list(Wrappers.query(bsUser));

								if (!userList.isEmpty()) {
									approvalRecord.setManName(userList.get(0).getManName());
									approvalRecord.setUserId(userList.get(0).getUserId());
								}
//							approvalRecord.setManName(SecurityUtils.getUser().getManName());
								if (approvalRecord.getOrderBy() != null && approvalRecord.getOrderBy() == 1) {
									BSUser bsUser1 = new BSUser();
									bsUser1.setUsername(approvalRecord.getUsername());
									List<BSUser> list = bSUserService.list(Wrappers.query(bsUser1));
									Smsdb smsdb = new Smsdb();
									if (list.get(0).getPhone() != null && !"".equals(list.get(0).getPhone().trim())) {
										smsdb.setCallnum(list.get(0).getPhone());
										smsdb.setContent("您有待审批的合同 --保税物流系统");
										Date date = new Date();
										date.setMinutes(date.getMinutes() + 1);
										smsdb.setCreatetime(date);
										smsdb.setFlag("0");
										smsdb.setMsgtype("保税物流");
										smsdbService.save(smsdb);
									}

								}
								approvalProcessSetService.save(approvalRecord);
							}
						}
					}
				}


			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.failed("添加审核记录失败");
		}
		if (flag == 0) {
			return R.failed("添加审核记录失败(未选择审批人)");
		}
		Contract contract = new Contract();
		contract.setFId(Long.parseLong(maps.get("fId").toString()));
		if ("0".equals(maps.get("processNo") + "")) {
			contract.setContractStatus("1");
		} else if ("3".equals(maps.get("processNo") + "")) {
			contract.setCancelStatus("1");
			contract.setCancelReason(maps.get("cancelReason") + "");
		}

		contract.setTimes(Integer.parseInt(maps.get("times").toString()));
		boolean b = contractService.updateById(contract);
		if (b) {
			return R.ok("添加审核记录成功");
		} else {
			return R.failed("添加审核记录失败");
		}
	}

	@Override
	public List<ContractFileVo> getEffectiveInfoOther(Contract contract, String[] deptArr) {
		return baseMapper.getEffectiveInfoOther(contract, deptArr);
	}

}


