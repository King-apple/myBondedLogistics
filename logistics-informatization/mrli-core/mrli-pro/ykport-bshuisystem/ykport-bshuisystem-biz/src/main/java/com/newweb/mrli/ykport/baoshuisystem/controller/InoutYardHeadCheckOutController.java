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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardHead;
import com.newweb.mrli.ykport.baoshuisystem.service.InoutYardHeadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;


/**
 * 出入库表头
 *
 * @author zada
 * @date 2021-09-02 18:43:01
 */
@RestController
@AllArgsConstructor
@RequestMapping("/inoutyardheadCheckOut" )
@Api(value = "inoutyardhead", tags = "出入库表头管理")
public class InoutYardHeadCheckOutController {

    private final InoutYardHeadService inoutYardHeadService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param inoutYardHead 出入库表头
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('inoutyardhead_view')" )
    public R getInoutYardHeadPage(Page page, InoutYardHead inoutYardHead) {
		IPage<InoutYardHead> pageList = inoutYardHeadService.lambdaQuery()
				.like(StringUtils.isNotBlank(inoutYardHead.getSettledCompany()),InoutYardHead::getSettledCompany,inoutYardHead.getSettledCompany())
				.like(StringUtils.isNotBlank(inoutYardHead.getInoutYardNo()),InoutYardHead::getInoutYardNo,inoutYardHead.getInoutYardNo())
				.like(StringUtils.isNotBlank(inoutYardHead.getConsignNam()),InoutYardHead::getConsignNam,inoutYardHead.getConsignNam())
				.isNull(true,InoutYardHead::getDelTim)
				.orderByDesc(true,InoutYardHead::getRecordTim)
				.page(new Page<>(page.getCurrent(), page.getSize()));;
//        return R.ok(inoutYardHeadService.page(page, Wrappers.query(inoutYardHead).isNull("DEL_TIM").orderByDesc("RECORD_TIM")));
		return R.ok(pageList);
    }


    /**
     * 通过id查询出入库表头
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('inoutyardhead_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(inoutYardHeadService.getById(fId));
    }

    /**
     * 新增出入库表头
     * @param inoutYardHead 出入库表头
     * @return R
     */
    @ApiOperation(value = "新增出入库表头", notes = "新增出入库表头")
    @SysLog("新增出入库表头" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('inoutyardhead_add')" )
    public R save(@RequestBody InoutYardHead inoutYardHead) {
        return R.ok(inoutYardHeadService.saveForServer(inoutYardHead));
    }

    /**
     * 修改出入库表头
     * @param inoutYardHead 出入库表头
     * @return R
     */
    @ApiOperation(value = "修改出入库表头", notes = "修改出入库表头")
    @SysLog("修改出入库表头" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('inoutyardhead_edit')" )
    public R updateById(@RequestBody InoutYardHead inoutYardHead) {
		InoutYardHead info = inoutYardHeadService.getById(inoutYardHead.getFId());
		if(inoutYardHead.getInCheckNumFlg().equals("1")){
			inoutYardHead.setOperCheckStatus("0");
			if(info.getOperCheckStatus().equals("1")){
				return R.failed("出区核注清单号已经审核通过，不允许更改！");
			}
		}else{
			inoutYardHead.setBusinessCheckStatus("0");
			if(info.getBusinessCheckStatus().equals("1")){
				return R.failed("当前数据已经审核通过不允许更改！");
			}
			if(info.getOperCheckStatus().equals("1") && !inoutYardHead.getInCheckNum().equals(info.getInCheckNum())){
				return R.failed("出区核注清单号已经审核通过，不允许更改！");
			}
		}

        return R.ok(inoutYardHeadService.updateById(inoutYardHead));
    }

    /**
     * 通过id删除出入库表头
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除出入库表头", notes = "通过id删除出入库表头")
    @SysLog("通过id删除出入库表头" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('inoutyardhead_del')" )
    public R removeById(@PathVariable Integer fId) {

		InoutYardHead info = inoutYardHeadService.getById(fId);
		if(info.getBusinessCheckStatus().equals("1")){
			return R.failed("当前数据已经审核通过不允许删除！");
		}
		if(info.getOperCheckStatus().equals("1")){

			return R.failed("出区核注清单号已经审核通过，不允许删除！");
		}

		InoutYardHead inoutYardHead = new InoutYardHead();
		inoutYardHead.setDelNam(SecurityUtils.getUser().getUsername());
		inoutYardHead.setDelTim(new Date());
		inoutYardHead.setFId(fId);
        return R.ok(inoutYardHeadService.updateById(inoutYardHead));
    }


	/**
	 * 通过id删除出入库表头
	 * @param companyCode id
	 * @return R
	 */
	@ApiOperation(value = "查询最新编号叠加", notes = "查询最新编号叠加")
	@SysLog("查询最新编号叠加" )
	@GetMapping("getInoutYardNo/{companyCode}" )
	public R getInoutYardNo(@PathVariable String companyCode) {
		InoutYardHead info = inoutYardHeadService.getInoutYardNo(companyCode);
		LocalDate date = LocalDate.now();

		if(info==null){
			return R.ok(companyCode+date.getYear()+"0001");
		}
		String no = info.getInoutYardNo().substring(companyCode.length()+4,info.getInoutYardNo().length());
		DecimalFormat decimalFormat=new DecimalFormat("0000");
		int i=Integer.parseInt(no)+1;
		String k=decimalFormat.format(i);
		return R.ok(companyCode+date.getYear()+k);
	}


	/**
	 * 更新业务审核状态
	 * @param inoutYardHead 出入库表头
	 * @return R
	 */
	@ApiOperation(value = "更新业务审核状态", notes = "更新业务审核状态")
	@SysLog("更新业务审核状态" )
	@PutMapping("/updateByIdForBusinessCheckStatus" )
	public R updateByIdForBusinessCheckStatus(@RequestBody InoutYardHead inoutYardHead) {
		inoutYardHead.setBusinessChecker(SecurityUtils.getUser().getUsername());
		inoutYardHead.setBusinessCheckTim(new Date());
		return R.ok(inoutYardHeadService.updateById(inoutYardHead));
	}

	/**
	 * 更新操作审核状态
	 * @param inoutYardHead 出入库表头
	 * @return R
	 */
	@ApiOperation(value = "更新操作审核状态", notes = "更新操作审核状态")
	@SysLog("更新操作审核状态" )
	@PutMapping("/updateByIdForOperCheckStatus" )
	public R updateByIdForOperCheckStatus(@RequestBody InoutYardHead inoutYardHead) {
		inoutYardHead.setOperChecker(SecurityUtils.getUser().getUsername());
		inoutYardHead.setOperCheckTim(new Date());
		return R.ok(inoutYardHeadService.updateById(inoutYardHead)); 
	}


}
