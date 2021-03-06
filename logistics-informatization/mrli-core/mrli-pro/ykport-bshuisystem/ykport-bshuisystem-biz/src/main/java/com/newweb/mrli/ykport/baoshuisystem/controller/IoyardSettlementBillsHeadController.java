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

package com.newweb.mrli.ykport.baoshuisystem.controller;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.BSUser;
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardListHead;
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardSettlementBillsBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardSettlementBillsHead;
import com.newweb.mrli.ykport.baoshuisystem.service.BSUserService;
import com.newweb.mrli.ykport.baoshuisystem.service.IoyardListHeadService;
import com.newweb.mrli.ykport.baoshuisystem.service.IoyardSettlementBillsHeadService;
import com.newweb.mrli.ykport.baoshuisystem.vo.ExternalPaymentVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.IoyardListHeadBodyVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.IoyardSettlementBilldHeadBodyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * ????????????????????????
 *
 * @author ly
 * @date 2021-09-14 18:43:46
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ioyardsettlementbillshead" )
@Api(value = "ioyardsettlementbillshead", tags = "??????????????????????????????")
public class IoyardSettlementBillsHeadController {

	private final  IoyardSettlementBillsHeadService ioYardService;
	private final BSUserService bsUserService;
	private final IoyardListHeadService ioyardListHeadService;


	/**
	 * ????????????
	 * @param page ????????????
	 * @param
	 * @return
	 */
	@ApiOperation(value = "????????????", notes = "????????????")
	@GetMapping("/page" )
	@PreAuthorize("@pms.hasPermission('ioyardsettlementbillshead_view')" )
	public R getIoyardSettlementBillsHeadPage(Page page, IoyardSettlementBilldHeadBodyVo ioYardVo) {
		String username = SecurityUtils.getUser().getUsername();
		ioYardVo.setOperNam(username);
		Collection<? extends GrantedAuthority> authorities = SecurityUtils.getAuthentication().getAuthorities();
		for (GrantedAuthority item : authorities) {
			if(item.toString().equals("settlement_view_admin")){
				ioYardVo.setOperNam(null);
				break;
			}
			if (item.toString().equals("settlement_view_deptAdmin")){
				BSUser bsUser = new BSUser();
				bsUser.setUsername(SecurityUtils.getUser().getUsername());
				ioYardVo.setOperNam(null);
				ioYardVo.setDeptId(getUserSubDeptId(bsUser).getDeptSubId()+"");
				break;
			}
		}
		IPage<ExternalPaymentVo> externalPaymentVoIPage = ioYardService.selectPageVo(page, ioYardVo);
		return R.ok(externalPaymentVoIPage);
	}


	/**
	 * ??????id??????????????????????????????
	 * @param fId id
	 * @return R
	 */
	@ApiOperation(value = "??????id??????", notes = "??????id??????")
	@GetMapping("/{fId}" )
	@PreAuthorize("@pms.hasPermission('ioyardsettlementbillshead_view')" )
	public R getById(@PathVariable("fId" ) Integer fId) {
		return R.ok(ioYardService.getById(fId));
	}

	/**
	 * ??????????????????????????????
	 * @return R
	 */
	@ApiOperation(value = "??????????????????????????????", notes = "??????????????????????????????")
	@SysLog("??????????????????????????????" )
	@PostMapping
	@PreAuthorize("@pms.hasPermission('ioyardsettlementbillshead_add')" )
	public R save(@RequestBody IoyardSettlementBilldHeadBodyVo ioYardVo) {
		IoyardSettlementBillsHead ioYard = new IoyardSettlementBillsHead();
		//????????????????????????????????????????????????
		String contNo = ioYardVo.getContNo();
		QueryWrapper<IoyardSettlementBillsHead> query = new QueryWrapper<>();
		query.eq("CONT_NO",contNo);
		List<IoyardSettlementBillsHead> list = ioYardService.list(query);
		String fk ="-js-";
		String zero = "0";
		BeanUtils.copyProperties(ioYardVo, ioYard);
		//????????????10
		if(list.size()<10){
			ioYard.setBillCod(ioYardVo.getContNo()+fk+zero+(list.size()+1));
			//????????????10
		}else{
			ioYard.setBillCod(ioYardVo.getContNo()+fk+(list.size()+1));
		}
		List<IoyardSettlementBillsBody> iList = ioYardVo.getIoYardSBBodyList();
		BigDecimal money1 = new BigDecimal("6");
		BigDecimal money2 = new BigDecimal("0.2");
		//???????????????
		ioYard.setListFid(ioYardVo.getListFid());
		//???????????????
		ioYard.setContFid(ioYardVo.getContFid());
		ioYard.setManagementRat(money1);
		ioYard.setManagementRatUnit("???/???");
		ioYard.setStorageRat(money2);
		ioYard.setStorageRatUnit("???/???*???");
		ioYard.setOperDat(new Date());
		ioYard.setOperNam(SecurityUtils.getUser().getUsername());
		ioYard.setRecordTim(new Date());
		ioYardService.save(ioYard);
		IoyardListHead ioyardListHead = new IoyardListHead();
		ioyardListHead.setIoyardListNo(ioYard.getIoyardListNo());
		IoyardListHead ioyardListHead1 = new IoyardListHead();
		ioyardListHead1.setSettlementGenerateFlag("1");
		ioyardListHeadService.update(ioyardListHead1,Wrappers.query(ioyardListHead));
		return R.ok(ioYard);
	}

	/**
	 * ??????????????????????????????
	 * @return R
	 */
	@ApiOperation(value = "??????????????????????????????", notes = "??????????????????????????????")
	@SysLog("??????????????????????????????" )
	@PutMapping
	@PreAuthorize("@pms.hasPermission('ioyardsettlementbillshead_edit')" )
	public R updateById(@RequestBody IoyardSettlementBilldHeadBodyVo ioYardVo) {
		IoyardSettlementBillsHead ioYard = new IoyardSettlementBillsHead();
		BeanUtils.copyProperties(ioYardVo,ioYard);
		ioYard.setUpdateNam(SecurityUtils.getUser().getUsername());
		ioYard.setUpdateTim(new Date());
		boolean b = ioYardService.updateById(ioYard);
		return R.ok(ioYard);
	}

	/**
	 * ??????id??????????????????????????????
	 * @param fId id
	 * @return R
	 */
	@ApiOperation(value = "??????id??????????????????????????????", notes = "??????id??????????????????????????????")
	@SysLog("??????id??????????????????????????????" )
	@DeleteMapping("/{fId}" )
	@PreAuthorize("@pms.hasPermission('ioyardsettlementbillshead_del')" )
	public R removeById(@PathVariable Long fId) {
		//??????id????????????
		IoyardSettlementBillsHead byId = ioYardService.getById(fId);
		IoyardListHead ioyardListHead = new IoyardListHead();
		ioyardListHead.setIoyardListNo(byId.getIoyardListNo());
		IoyardListHead ioyardListHead1 = new IoyardListHead();
		ioyardListHead1.setSettlementGenerateFlag("0");
		ioyardListHeadService.update(ioyardListHead1,Wrappers.query(ioyardListHead));
		byId.setDelNam(SecurityUtils.getUser().getUsername());
		byId.setDelTim(new Date());
		boolean b = ioYardService.updateById(byId);
		return R.ok(b);
	}
	@GetMapping("/getCodeInoutYard/{id}")
	public R getCodeInoutYard(@PathVariable String id) {
		IoyardListHead ioyardListHead = new IoyardListHead();
		ioyardListHead.setContNo(id);
		ioyardListHead.setSettlementGenerateFlag("0");
		List<IoyardListHeadBodyVo> list = ioYardService.getCodeInoutYard(ioyardListHead);
		return R.ok(list);
	}

	@ApiOperation(value = "??????", notes = "??????")
	@SysLog("??????")
	@PostMapping("/downloadBill")
	@PreAuthorize("@pms.hasPermission('ioyardsettlementbillshead_exp')")
	public void exportByBusId(@RequestBody IoyardSettlementBilldHeadBodyVo ioYardVo, HttpServletResponse response) throws IOException {
		ioYardService.exportById(ioYardVo,response);
	}

	@GetMapping("/getBillCodInfo")
	public R getSelectContract() {
		List<String> list = ioYardService.selectBillCod();
		return R.ok(list);
	}
	@DS("slave")
	public BSUser getUserSubDeptId(BSUser bsUser) {
		return bsUserService.list(Wrappers.query(bsUser)).get(0);
	}

}
