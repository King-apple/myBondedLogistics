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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.BusinessBillsBody;
import com.newweb.mrli.ykport.baoshuisystem.service.BusinessBillsBodyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 业务单据体表
 *
 * @author 李洋
 * @date 2021-09-06 09:13:02
 */
@RestController
@AllArgsConstructor
@RequestMapping("/businessbillsbody")
@Api(value = "businessbillsbody", tags = "业务单据体表管理")
public class BusinessBillsBodyController {

	@Autowired
	private final BusinessBillsBodyService businessBillsBodyService;

	/**
	 * 分页查询
	 *
	 * @param page              分页对象
	 * @param businessBillsBody 业务单据体表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('businessbillsbody_view')")
	public R getBusinessBillsBodyPage(Page page, BusinessBillsBody businessBillsBody) {
		return R.ok(businessBillsBodyService.page(page, Wrappers.query(businessBillsBody)));
	}


	/**
	 * 通过id查询业务单据体表
	 *
	 * @param fId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{fId}")
	@PreAuthorize("@pms.hasPermission('businessbillsbody_view')")
	public R getById(@PathVariable("fId") Integer fId) {
		QueryWrapper<BusinessBillsBody> wrapper = new QueryWrapper<>();
		wrapper.eq("h_fid", fId);
		wrapper.eq("item_nam","应收款");
		wrapper.isNull("del_nam");
		List<BusinessBillsBody> list = businessBillsBodyService.list(wrapper);
		QueryWrapper<BusinessBillsBody> wrapper2 = new QueryWrapper<>();
		wrapper2.eq("h_fid", fId);
		wrapper2.eq("item_nam","应付款");
		wrapper2.isNull("del_nam");
		List<BusinessBillsBody> list2 = businessBillsBodyService.list(wrapper2);
		Map<String, Object> map = new HashMap<>();
		map.put("first", list);
		map.put("second", list2);
		return R.ok(map);
	}

	/**
	 * 新增业务单据体表
	 *
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "新增业务单据体表", notes = "新增业务单据体表")
	@SysLog("新增业务单据体表")
	@PostMapping("/addList")
	@PreAuthorize("@pms.hasPermission('businessbillsbody_add')")
	public R save(@RequestBody List<BusinessBillsBody> bbBodyList) {
		for (BusinessBillsBody s:bbBodyList
		) {
			s.setOperNam(SecurityUtils.getUser().getUsername());
			s.setRecordTim(new Date());
		}
		businessBillsBodyService.saveBatch(bbBodyList);
		//return R.ok(businessBillsBodyService.addList(bbBodyList));
		return R.ok(bbBodyList);
	}

	/**
	 * 修改业务单据体表
	 *
	 * @param businessBillsBody 业务单据体表
	 * @return R
	 */
	@ApiOperation(value = "修改业务单据体表", notes = "修改业务单据体表")
	@SysLog("修改业务单据体表")
	@PutMapping("/putList")
	@PreAuthorize("@pms.hasPermission('businessbillsbody_edit')")
	public R updateById(@RequestBody List<BusinessBillsBody> businessBillsBody) {
		boolean b = true;
		for (int i = 0; i < businessBillsBody.size(); i++) {
			if(businessBillsBody.get(i) != null && businessBillsBody.get(i).getDelNam()== null && businessBillsBody.get(i).getDelTim() == null){
				BusinessBillsBody body = businessBillsBody.get(i);
				if (body.getFId() != null) {
					body.setUpdateTim(new Date());
					body.setUpdateNam(SecurityUtils.getUser().getUsername());
					b = businessBillsBodyService.updateById(body);

				}else if(body.getFId() == null && businessBillsBody.get(i).getDelNam()== null && businessBillsBody.get(i).getDelTim() == null) {
					body.setOperNam(SecurityUtils.getUser().getUsername());
					body.setRecordTim(new Date());
					businessBillsBodyService.save(businessBillsBody.get(i));
				}
			}
		}
		return R.ok(b);
	}

	/**
	 * 通过id删除业务单据体表
	 *
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "通过id删除业务单据体表", notes = "通过id删除业务单据体表")
	@SysLog("通过id删除业务单据体表")
	@PostMapping("/deleteBody")
	@PreAuthorize("@pms.hasPermission('businessbillsbody_del')")
	public R removeById(@RequestBody BusinessBillsBody businessBillsBody) {
		businessBillsBody.setDelNam(SecurityUtils.getUser().getUsername());
		businessBillsBody.setDelTim(new Date());
		return R.ok(businessBillsBodyService.updateById(businessBillsBody));
	}

}
