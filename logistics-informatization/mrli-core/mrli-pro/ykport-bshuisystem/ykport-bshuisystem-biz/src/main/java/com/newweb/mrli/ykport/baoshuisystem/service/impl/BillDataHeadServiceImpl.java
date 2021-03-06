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
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.*;
import com.newweb.mrli.ykport.baoshuisystem.mapper.BillDataHeadMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.*;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ?????????????????????
 *
 * @author pigx code generator
 * @date 2021-11-05 13:58:42
 */
@Service
public class BillDataHeadServiceImpl extends ServiceImpl<BillDataHeadMapper, BillDataHead> implements BillDataHeadService {
	@Autowired
	private OrganizerInfoService organizerInfoService;
	@Autowired
	private BillApproveService billApproveService;
	@Autowired
	private ContractObjectService contractObjectService;
	@Autowired
	private FeeNamesService feeNamesService;
	@Autowired
	private BillDataBodyService billDataBodyService;
	@Autowired
	private InvoiceDetailHeadService invoiceDetailHeadService;

	@Override
	@Transactional
	public R saveBillData(BillDataHead billDataHead) {

		billDataHead.setOperNam(SecurityUtils.getUser().getManName());
		billDataHead.setRecordTim(new Date());
		billDataHead.setUpdateNam(SecurityUtils.getUser().getManName());
		billDataHead.setUpdateTim(new Date());
		billDataHead.setFpzl("????????????");
		OrganizerInfo organizerInfo = new OrganizerInfo();
		organizerInfo.setOrganizer(billDataHead.getUnitNam());
		List<OrganizerInfo> list = organizerInfoService.list(Wrappers.query(organizerInfo));
		billDataHead.setUnitCod(list.get(0).getOrganizerCode());
		List<BillDataBody> billDataBodyList = billDataHead.getBillDataBodyList();
		BillApprove billApprove = new BillApprove();
		billApprove.setBillCod(billDataHead.getBillApprovalNo());
		List<BillApprove> list1 = billApproveService.list(Wrappers.query(billApprove).isNull("DEL_NAM"));
		String cargoNo = "";
		String cargoNam = "";
		String cargoKindNo = "";
		String cargoKindNam = "";
		if(!list1.isEmpty()){
			cargoNo = list1.get(0).getCargoCod();
			cargoNam = list1.get(0).getCargoNam();
			ContractObject contractObject = new ContractObject();
			contractObject.setCargoCod(cargoNo);
			List<ContractObject> contractObjectList = contractObjectService.list(Wrappers.query(contractObject).isNull("DEL_NAM"));
			if(!contractObjectList.isEmpty()){
				cargoKindNo = contractObjectList.get(0).getCargoKindCod();
				cargoKindNam = contractObjectList.get(0).getCargoKindNam();
			}
		}
		List<String> feeList = new ArrayList<>();
		for (BillDataBody billDataBody:billDataBodyList) {
			feeList.add(billDataBody.getCFeeNam());
		}
		List<FeeNames> list2 = feeNamesService.list(Wrappers.query(new FeeNames()).in("FEE_NAM",feeList).isNull("DEL_NAM"));
		List<BillDataBody> billDataBodys = new ArrayList<>();
		for (BillDataBody billDataBody:billDataBodyList){
			billDataBody.setFeeBillNo(billDataHead.getFeeBillNo());
			billDataBody.setUnitCod(billDataHead.getUnitCod());
			billDataBody.setUnitNam(billDataHead.getUnitNam());
			billDataBody.setCargoNamCod(cargoNo);
			billDataBody.setCargoNam(cargoNam);
			billDataBody.setCargoKindCod(cargoKindNo);
			billDataBody.setCargoKindNam(cargoKindNam);
			billDataBody.setEndCheckerNam(SecurityUtils.getUser().getManName());
			billDataBody.setEndCheckerNamDte(new Date());
			billDataBody.setOperNam(SecurityUtils.getUser().getManName());
			billDataBody.setRecordTim(new Date());
			billDataBody.setContNo(billDataHead.getContNo());
			billDataBody.setShipperCod(billDataHead.getShipperCod());
			billDataBody.setShipperDoc(billDataHead.getShipperDoc());
			for(int i = 0;i < list2.size();i++){
				if(list2.get(i).getFeeNam().equals(billDataBody.getCFeeNam())){
					billDataBody.setFeeNameCod(list2.get(i).getFeeNameCod());
				}
			}
			billDataBodys.add(billDataBody);
			billDataBodyService.save(billDataBody);
		}
		List<BillDataHead> billDataHeads = new ArrayList<>();
		billDataHeads.add(billDataHead);
		String flag = "";
		flag = httpPost(billDataBodys,"http://www.ykgportal.com/apidataserver/api/save?id=6445");
		if(!"????????????".equals(flag)){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.failed(flag);
		}
		flag = httpPost(billDataHeads,"http://www.ykgportal.com/apidataserver/api/save?id=6444");
		if(!"????????????".equals(flag)){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.failed(flag);
		}
		boolean save = save(billDataHead);
		if(save){
			InvoiceDetailHead invoiceDetailHead = new InvoiceDetailHead();
			invoiceDetailHead.setFId(billDataHead.getPkid());
			invoiceDetailHead.setUploadBillFlag("1");
			invoiceDetailHeadService.updateById(invoiceDetailHead);
			return R.ok("????????????","????????????");

		}else{
			return R.failed("????????????");
		}

	}

	private String httpPost(List billDataHead,String url) {
		// ??????Http?????????(???????????????:???????????????????????????;??????:?????????HttpClient???????????????????????????)
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		String ifError = "";
		// ??????Post??????
		HttpPost httpPost = new HttpPost(url);

		// ????????????????????????fastjson??????Object?????????json?????????;
		// (????????????com.alibaba.fastjson.JSON???)
		String jsonString = JSON.toJSONString(billDataHead);
		System.out.println(jsonString);
		StringEntity entity = new StringEntity(jsonString, "UTF-8");

		// post???????????????????????????????????????????????????;?????????entity??????post????????????
		httpPost.setEntity(entity);

		httpPost.setHeader("Content-Type", "application/json;charset=utf8");

		// ????????????
		CloseableHttpResponse response = null;
		try {
			// ??????????????????(??????)Post??????
			response = httpClient.execute(httpPost);
			// ????????????????????????????????????
			HttpEntity responseEntity = response.getEntity();

			System.out.println("???????????????:" + response.getStatusLine());
			if (responseEntity != null) {
				System.out.println("?????????????????????:" + responseEntity.getContentLength());
				System.out.println("???????????????:" + EntityUtils.toString(responseEntity));
				Map jsonToMap =  JSONObject.parseObject(EntityUtils.toString(responseEntity));
				if(jsonToMap == null || jsonToMap.get("message")!="??????"){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					ifError = EntityUtils.toString(responseEntity);
				}else{
					ifError = "????????????";
				}
			}
		} catch (ClientProtocolException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ifError = e.getMessage();
			e.printStackTrace();
		} catch (ParseException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ifError = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ifError = e.getMessage();
			e.printStackTrace();
		} finally {
			try {
				// ????????????
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				ifError = e.getMessage();
				e.printStackTrace();
			}
		}
		return ifError;
	}

}
