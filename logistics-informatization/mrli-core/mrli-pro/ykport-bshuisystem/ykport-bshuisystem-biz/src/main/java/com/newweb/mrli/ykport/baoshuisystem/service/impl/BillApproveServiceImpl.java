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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.controller.BSUserController;
import com.newweb.mrli.ykport.baoshuisystem.entity.*;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.*;
import com.newweb.mrli.ykport.baoshuisystem.mapper.BillApproveMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.BusinessBillsHeadMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.IoyardSettlementBillsHeadMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.*;
import com.newweb.mrli.ykport.baoshuisystem.vo.BillApproveAppProSetVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.BillApproveVo;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 业务单据审批单
 *
 * @author ly
 * @date 2021-09-17 10:48:12
 */
@Service
public class BillApproveServiceImpl extends ServiceImpl<BillApproveMapper, BillApprove> implements BillApproveService {
	@Autowired
	private BusinessBillsHeadMapper billsHeadMapper ;
	@Autowired
	private IoyardSettlementBillsHeadMapper ioyardSettlementBillsHeadMapper ;
	@Autowired
	private  BillApproveService billApproveService;
	@Autowired
	private ApprovalProcessSetService approvalProcessSetService;
	@Autowired
	private ApprovalRecordService approvalRecordService;
	@Autowired
	private SysFileService sysFileService;
	@Autowired
	private ApprovalProcessService approvalProcessService;
	@Autowired
	private BSUserService bsUserService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private SmsdbService smsdbService;



	@Override
	public IPage<BillApproveVo> selectPageVo(Page page, BillApproveVo billApprove) {

		return baseMapper.selectPageVo(page,billApprove);
	}

	@Override
	public List<BusinessBillsHead> selectPayment() {
		QueryWrapper<BusinessBillsHead> queryWrapper = new QueryWrapper();
		queryWrapper.isNull("DEL_NAM");
		queryWrapper.isNull("DEL_TIM");
		queryWrapper.eq("BILL_KND","业务结算单");
		List<BusinessBillsHead> list = billsHeadMapper.selectList(queryWrapper);
			return list;
	}

	@Override
	public List<BusinessBillsHead> selectSettlement() {
		QueryWrapper<BusinessBillsHead> queryWrapper = new QueryWrapper();
		queryWrapper.isNull("DEL_NAM");
		queryWrapper.isNull("DEL_TIM");
		queryWrapper.eq("BILL_KND","付款单");
		List<BusinessBillsHead> list = billsHeadMapper.selectList(queryWrapper);
		return list;
	}

	@Override
	public List<IoyardSettlementBillsHead> selectInOut() {
		QueryWrapper<IoyardSettlementBillsHead> queryWrapper = new QueryWrapper();
		queryWrapper.isNull("DEL_NAM");
		queryWrapper.isNull("DEL_TIM");
		List<IoyardSettlementBillsHead> list = ioyardSettlementBillsHeadMapper.selectList(queryWrapper);
		return list;
	}

	@Override
	public R setAudit(BillApproveAppProSetVo billApproveAppProSetVo){
		ApprovalProcessSet ApprovalProcessSetOne = new ApprovalProcessSet();
		ApprovalProcessSetOne.setFId(billApproveAppProSetVo.getApprovalProcessSetFid());
		ApprovalProcessSet detail = approvalProcessSetService.getById(ApprovalProcessSetOne);
		//判断是否审核通过
		try {


			if (billApproveAppProSetVo.getFlg() == 1) { //审核通过
				//更新billApprove 状态 （判断当前审核是否是最后一步，如果是更新状态为成功）
				ApprovalProcessSetOne = new ApprovalProcessSet();
				ApprovalProcessSetOne.setBillFid(billApproveAppProSetVo.getFId().longValue());
				List<ApprovalProcessSet> listApprovalProSet = approvalProcessSetService.list(Wrappers.query(ApprovalProcessSetOne).orderByDesc("ORDER_BY"));
				List<ApprovalProcessSet> list = listApprovalProSet.stream().filter(entity -> entity.getOrderBy().toString().equals(listApprovalProSet.get(0).getOrderBy().toString())).collect(Collectors.toList());
				Boolean bool = false;
				if (list.get(0).getOrderBy() == detail.getOrderBy()) {//判断是否是最后一个审核
					for (ApprovalProcessSet info : list) {
						System.out.println(info.getFId());
						System.out.println(billApproveAppProSetVo.getApprovalProcessSetFid());
						System.out.println(info.getFId() == billApproveAppProSetVo.getApprovalProcessSetFid());
						if (info.getFId().longValue() == billApproveAppProSetVo.getApprovalProcessSetFid().longValue()) {
							continue;
						} else {
							if (StringUtils.checkValNull(info.getOpinion())) {
								bool = true;
								break;
							}
						}
					}
					if (!bool) {//更新billApprove 审核通过
						BillApprove billApprove = new BillApprove();
						billApprove.setApproveStatus(2); //审核成功
						billApprove.setFId(billApproveAppProSetVo.getFId());
						billApproveService.updateById(billApprove);
					}
				}

				//更新ApprovalProcessSet 字段
				ApprovalProcessSet approvalProcessSet = new ApprovalProcessSet();
				approvalProcessSet.setFId(billApproveAppProSetVo.getApprovalProcessSetFid());
				approvalProcessSet.setOpinion("同意");
				approvalProcessSet.setOpinionExplain(billApproveAppProSetVo.getOpinionExplain());
				approvalProcessSet.setIsAgree("1");
				approvalProcessSet.setApprover(SecurityUtils.getUser().getManName());
				approvalProcessSet.setApprovalTim(new Date());
				approvalProcessSetService.updateById(approvalProcessSet);
				approvalProcessSet.setProcessNo(detail.getProcessNo());
				approvalProcessSet.setBillFid(billApproveAppProSetVo.getFId().longValue());
				approvalProcessSet.setOrderBy(detail.getOrderBy());
				approvalProcessSetService.sendMessage(approvalProcessSet, "您有待审批的结算单 --保税物流系统");
				//新增approval_record
				ApprovalRecord approvalRecord = new ApprovalRecord();
				approvalRecord.setPFid(billApproveAppProSetVo.getApprovalProcessSetFid().longValue());
				approvalRecord.setCFid(billApproveAppProSetVo.getFId().longValue());
				approvalRecord.setProcessCode(detail.getProcessNo());
				approvalRecord.setOrderBy(detail.getOrderBy());
				approvalRecord.setLinkNam(detail.getLinkNam());
				approvalRecord.setOpinion(billApproveAppProSetVo.getOpinion());
				approvalRecord.setOpinionExplain(billApproveAppProSetVo.getOpinionExplain());
				approvalRecord.setIsAgree("1");
				approvalRecord.setApprover(SecurityUtils.getUser().getManName());
				approvalRecord.setApprovalTim(new Date());
				approvalRecord.setTimes(billApproveAppProSetVo.getTimes());
				approvalRecordService.save(approvalRecord);
				return R.ok();
			}

			if (billApproveAppProSetVo.getFlg() == 2) {//审核驳回
				//更新billApprove 状态
				BillApprove billApprove = new BillApprove();
				billApprove.setApproveStatus(3);
				billApprove.setFId(billApproveAppProSetVo.getFId());
				billApproveService.updateById(billApprove);
				//更新ApprovalProcessSet 字段
				ApprovalProcessSet approvalProcessSet = new ApprovalProcessSet();
				approvalProcessSet.setFId(billApproveAppProSetVo.getApprovalProcessSetFid());
				approvalProcessSet.setOpinion("不同意");
				approvalProcessSet.setOpinionExplain(billApproveAppProSetVo.getOpinionExplain());
				approvalProcessSet.setIsAgree("2");
				approvalProcessSet.setApprover(SecurityUtils.getUser().getManName());
				approvalProcessSet.setApprovalTim(new Date());
				approvalProcessSetService.updateById(approvalProcessSet);
//			approvalProcessSetService.updateByIdForNull(approvalProcessSet);
//			approvalProcessSetService.update(
//					null,
//					Wrappers.<ApprovalProcessSet>lambdaUpdate()
//							.set(ApprovalProcessSet::getOpinion, null)
//							.set(ApprovalProcessSet::getOpinionExplain, null)
//							.set(ApprovalProcessSet::getIsAgree, null)
//							.set(ApprovalProcessSet::getApprover, null)
//							.set(ApprovalProcessSet::getApprovalTim, null)
//							.eq(ApprovalProcessSet::getFId, Long.parseLong(billApproveAppProSetVo.getFId().toString()))
//					);


				//新增approval_record
				ApprovalRecord approvalRecord = new ApprovalRecord();
				approvalRecord.setPFid(billApproveAppProSetVo.getApprovalProcessSetFid().longValue());
				approvalRecord.setCFid(billApproveAppProSetVo.getFId().longValue());
				approvalRecord.setProcessCode(detail.getProcessNo());
				approvalRecord.setOrderBy(detail.getOrderBy());
				approvalRecord.setLinkNam(detail.getLinkNam());
				approvalRecord.setOpinion(billApproveAppProSetVo.getOpinion());
				approvalRecord.setOpinionExplain(billApproveAppProSetVo.getOpinionExplain());
				approvalRecord.setIsAgree("2");
				approvalRecord.setApprover(SecurityUtils.getUser().getManName());
				approvalRecord.setApprovalTim(new Date());
				approvalRecord.setTimes(billApproveAppProSetVo.getTimes());
				approvalRecordService.save(approvalRecord);
				return R.ok();
			}
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.failed("失败");
		}

		return R.failed();
	}

	@Override
	public void exportBill(BillApproveVo billApproveVo, HttpServletResponse response) throws IOException {

		Integer fId = billApproveVo.getFId();
		//合并数据
		List<ApprovalProcessSet> mergeList = approvalProcessSetService.getMessageMerge(fId);
		QueryWrapper<ApprovalProcessSet> queryWrapper = new QueryWrapper<>();
		QueryWrapper<SysFile> queryWrapper2 = new QueryWrapper<>();
		queryWrapper.eq("BILL_FID", fId);
		List<ApprovalProcessSet> list = approvalProcessSetService.list(queryWrapper);
		queryWrapper2.eq("TABLE_FID", fId);
		List<SysFile> fileList = sysFileService.list(queryWrapper2);
		//获取所有部门
		QueryWrapper<ApprovalProcess> query = new QueryWrapper<>();
		query.eq("PROCESS_NO", 1);
		List<ApprovalProcess> approvalProcessList = approvalProcessService.list(query);
		String str ="";
		for (int i = 0; i <mergeList.size(); i++) {
			str+=mergeList.get(i).getLinkNam()+",";
		}
		if(str != ""){
			str = str.substring(0,str.length() - 1);
			String finalStr = str;
			approvalProcessList = approvalProcessList.stream().filter(item -> ! finalStr.contains(item.getLinkNam())).collect(Collectors.toList());
		}
//		List<Object> listAll = Stream.of(approvalProcessList, mergeList).flatMap(Collection::stream).collect(Collectors.toList());
//		for (int i = 0; i <listAll.size() ; i++) {
//			System.out.println(listAll.get(i));
//		}
		// 获取标题名称
		ClassPathResource classPathResource = new ClassPathResource("/file/billApprove.xlsx");
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
		xssfCell.setWrapText(true);
		XSSFSheet sheet = wb.getSheetAt(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sheet.getRow(1).getCell(5).setCellValue(billApproveVo.getBillCod());
		sheet.getRow(2).getCell(1).setCellValue(billApproveVo.getBillType());
		if(billApproveVo.getBillDat() != null){
			sheet.getRow(2).getCell(5).setCellValue(sdf.format(billApproveVo.getBillDat()));
		}else{
			sheet.getRow(2).getCell(5).setCellValue("");
		}

		sheet.getRow(3).getCell(1).setCellValue(billApproveVo.getFirstNam() + billApproveVo.getDept());
		//根据用户登录名获取真实姓名
		BSUser bsUser = new BSUser();
		bsUser.setUsername(billApproveVo.getOperator());
		List<BSUser> list1 = bsUserService.list(Wrappers.query(bsUser));
		if(!list1.isEmpty()){
			sheet.getRow(3).getCell(5).setCellValue(list1.get(0).getManName());
		}else{
			sheet.getRow(3).getCell(5).setCellValue(billApproveVo.getOperator());
		}

		sheet.getRow(4).getCell(1).setCellValue(billApproveVo.getSecondNam());
		sheet.getRow(5).getCell(1).setCellValue(billApproveVo.getContNo());
		sheet.getRow(5).getCell(4).setCellValue(billApproveVo.getInShipName()==""?"" :billApproveVo.getInShipName() );
		sheet.getRow(5).getCell(6).setCellValue(billApproveVo.getInShipName()==""?"" :billApproveVo.getInShipName() );
		sheet.getRow(6).getCell(1).setCellValue(billApproveVo.getBusinessType());
		sheet.getRow(6).getCell(5).setCellValue(billApproveVo.getCargoNam());
		sheet.getRow(7).getCell(1).setCellValue(billApproveVo.getAccountNam());
		sheet.getRow(7).getCell(5).setCellValue(billApproveVo.getSettleWgt());
		sheet.getRow(8).getCell(1).setCellValue(billApproveVo.getMoney());
		if(billApproveVo.getAdvinceMoney() != null){
			sheet.getRow(8).getCell(5).setCellValue(billApproveVo.getAdvinceMoney());
		}else{
			sheet.getRow(8).getCell(5).setCellValue("");
		}
		int index = 0;
		for (int i = 0; i < approvalProcessList.size(); i++) {
			ApprovalProcess approvalProcess = approvalProcessList.get(i);
			sheet.getRow(9 + i).getCell(0).setCellValue(approvalProcess.getLinkNam());
			index++;
			if (index < approvalProcessList.size()) {
				sheet.shiftRows(9 + i, 10 + i, 1, true, false);
				sheet.copyRows(10 + i, 10 + i, 9 + i, new CellCopyPolicy());
			}
//			for (int j = 0; j < mergeList.size(); j++) {
//				if (approvalProcess.getFId() == mergeList.get(j).getProcessFid()) {
//					String opinion = mergeList.get(j).getOpinion();
//					String replace = opinion.replace(",", "\r\n");
//					sheet.getRow(9 + i).getCell(1).setCellValue(replace);
//				}
//			}
		}
		XSSFRow row = sheet.createRow(9+ approvalProcessList.size());
		row.setHeight((short) 800);
		for (int i = 0; i <8; i++) {
			XSSFCell cell = row.createCell(i);
			row.getCell(i).setCellStyle(xssfCell);
		}
		int index2 = 0;

		XSSFCellStyle xssfCellMerge = wb.createCellStyle();
		xssfCellMerge.setWrapText(true);
		for (int i = 0; i < mergeList.size(); i++) {
			ApprovalProcessSet approvalProcessSet = mergeList.get(i);
			sheet.getRow(9+ approvalProcessList.size() + i).getCell(0).setCellValue(approvalProcessSet.getLinkNam());
			approvalProcessSet.setOpinion(approvalProcessSet.getOpinion().replace(",","\n\r"));
			sheet.getRow(9+ approvalProcessList.size() + i).getCell(1).setCellValue(approvalProcessSet.getOpinion());
			sheet.getRow(9+ approvalProcessList.size() + i).getCell(1).setCellStyle(xssfCellMerge);
			index2++;
			if (index2 < mergeList.size()) {
				sheet.shiftRows(9+ approvalProcessList.size() + i, 10+ approvalProcessList.size() + i, 1, true, false);
				sheet.copyRows(10+ approvalProcessList.size() + i, 10+ approvalProcessList.size() + i, 9+ approvalProcessList.size() + i, new CellCopyPolicy());
			}
			sheet.addMergedRegion(new CellRangeAddress(9+ approvalProcessList.size() + i,9+ approvalProcessList.size() + i,1,7));
		}

		XSSFRow row2 = sheet.createRow(9+ approvalProcessList.size()+mergeList.size());
		row2.setHeight((short) 800);
		for (int i = 0; i <8; i++) {
			XSSFCell cell = row2.createCell(i);
			row2.getCell(i).setCellStyle(xssfCell);
		}
		sheet.getRow(9+ approvalProcessList.size()+mergeList.size()).getCell(0).setCellValue("备注");
		if(billApproveVo.getMarkTxt() != null){
			sheet.getRow(9+ approvalProcessList.size()+mergeList.size()).getCell(1).setCellValue(billApproveVo.getMarkTxt());
		}else{
			sheet.getRow(9+ approvalProcessList.size()+mergeList.size()).getCell(1).setCellValue("");
		}
		sheet.addMergedRegion(new CellRangeAddress(9+ approvalProcessList.size()+mergeList.size(),9+ approvalProcessList.size()+mergeList.size(),1,7));
		XSSFRow row3 = sheet.createRow(10+ approvalProcessList.size()+mergeList.size());
		for (int i = 0; i <8; i++) {
			XSSFCell cell1 = row3.createCell(i);
		}
		sheet.getRow(10 + approvalProcessList.size()+mergeList.size()).getCell(0).setCellValue("附件");
		for (SysFile s:fileList) {
			sheet.getRow(10 + approvalProcessList.size()+mergeList.size()).getCell(1).setCellValue(s.getOriginal());

		}
		output = response.getOutputStream();
		wb.write(output);
		output.close();
		fileInput.close();
		wb.close();
	}

	@Override
	public R buinessCheck(BillApprove billApprove) {
		billApprove.setCheckTime(new Date());
		if("1".equals(billApprove.getCheckStatus())){
			BillApprove billApprove1 = new BillApprove();
			billApprove1.setFId(billApprove.getFId());
			List<BillApprove> list = list(Wrappers.query(billApprove1));
			String contNo = "";
			if(!list.isEmpty()){
				contNo = list.get(0).getContNo();
				Contract contract = new Contract();
				contract.setContNo(contNo);
				List<Contract> contractList = contractService.list(Wrappers.query(contract).isNull("DEL_NAM"));
				if(!contractList.isEmpty()){
					contractList.get(0).getOperNam();
					contractList.get(0).getOperator();
					billApprove.setCheckUser(contractList.get(0).getOperNam());
					billApprove.setCheckManname(contractList.get(0).getOperator());
					BSUser bsUser1 = new BSUser();
					bsUser1.setUsername(contractList.get(0).getOperNam());
					List<BSUser> userList = bsUserService.list(Wrappers.query(bsUser1));
					Smsdb smsdb = new Smsdb();
					if(userList.get(0).getPhone() != null && !"".equals(userList.get(0).getPhone().trim())){
						smsdb.setCallnum(userList.get(0).getPhone());
						smsdb.setContent("您有待业务审核的业务单据 ---保税物流系统");
						Date date = new Date();
						date.setMinutes(date.getMinutes()+1);
						smsdb.setCreatetime(date);
						smsdb.setFlag("0");
						smsdb.setMsgtype("保税物流");
						smsdbService.save(smsdb);
					}
					return R.ok(updateById(billApprove));
				}else{
					return R.failed("未查询到该合同编号，请修改后重新提交");
				}
			}else{
				return R.failed("业务审核失败，请重试");
			}
		}else{
			return R.ok(updateById(billApprove));
		}

	}
}
