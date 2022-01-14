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
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardHead;
import com.newweb.mrli.ykport.baoshuisystem.service.CheckWeightService;
import com.newweb.mrli.ykport.baoshuisystem.service.InoutYardBodyService;
import com.newweb.mrli.ykport.baoshuisystem.service.InoutYardHeadService;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 出入库单表体
 *
 * @author zada
 * @date 2021-09-02 18:42:44
 */
@RestController
@AllArgsConstructor
@RequestMapping("/inoutyardbody" )
@Api(value = "inoutyardbody", tags = "出入库单表体管理")
public class InoutYardBodyController {

    private final InoutYardBodyService inoutYardBodyService;
	private final InoutYardHeadService inoutYardHeadService;
	private final CheckWeightService checkWeightService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param inoutYardBody 出入库单表体
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
//    @PreAuthorize("@pms.hasPermission('inoutyardbody_view')" )
    public R getInoutYardBodyPage(Page page, InoutYardBody inoutYardBody) {
		System.out.println(StringUtil.isNullOrEmpty(inoutYardBody.getInCheckNum()));
		
//        return R.ok(inoutYardBodyService.page(page, Wrappers.query(inoutYardBody)
//				.isNull("DEL_TIM")
//				.like((!StringUtil.isNullOrEmpty(inoutYardBody.getInCheckNum())),"IN_CHECK_NUM",inoutYardBody.getInCheckNum())
//				.orderByDesc("RECORD_TIM")));
		IPage<InoutYardBody> pageList = null;
		if(StringUtil.isNullOrEmpty(inoutYardBody.getInCustomsDeclarationStatus())){
			pageList = inoutYardBodyService.lambdaQuery()
					.like(!StringUtil.isNullOrEmpty(inoutYardBody.getInCheckNum()),InoutYardBody::getInCheckNum,inoutYardBody.getInCheckNum())
					.like(!StringUtil.isNullOrEmpty(inoutYardBody.getOutCheckNum()),InoutYardBody::getOutCheckNum,inoutYardBody.getOutCheckNum())
					.isNull(true,InoutYardBody::getDelTim)
					.eq(inoutYardBody.getHFid()>0,InoutYardBody::getHFid,inoutYardBody.getHFid())
//				.eq(StringUtils.isNotBlank(inoutYardHead.getInoutYardKnd()),InoutYardHead::getInoutYardKnd,inoutYardHead.getInoutYardKnd())
//				.between(StringUtils.isNotBlank(inoutYardHead.getStartTime()), InoutYardHead::getRecordTim,startTime,endTime)
					.orderByDesc(true,InoutYardBody::getRecordTim)
					.page(new Page<>(page.getCurrent(), page.getSize()));
		}else if("success".equals(inoutYardBody.getInCustomsDeclarationStatus())){
			pageList = inoutYardBodyService.lambdaQuery()
					.like(!StringUtil.isNullOrEmpty(inoutYardBody.getInCheckNum()),InoutYardBody::getInCheckNum,inoutYardBody.getInCheckNum())
					.like(!StringUtil.isNullOrEmpty(inoutYardBody.getOutCheckNum()),InoutYardBody::getOutCheckNum,inoutYardBody.getOutCheckNum())
					.isNull(true,InoutYardBody::getDelTim)
					.eq(inoutYardBody.getHFid()>0,InoutYardBody::getHFid,inoutYardBody.getHFid())
//				.eq(StringUtils.isNotBlank(inoutYardHead.getInoutYardKnd()),InoutYardHead::getInoutYardKnd,inoutYardHead.getInoutYardKnd())
//				.between(StringUtils.isNotBlank(inoutYardHead.getStartTime()), InoutYardHead::getRecordTim,startTime,endTime)
					.isNotNull(true, InoutYardBody::getCustomsDeclarationUser)
					.orderByDesc(true,InoutYardBody::getRecordTim)
					.page(new Page<>(page.getCurrent(), page.getSize()));
		} else if("fail".equals(inoutYardBody.getInCustomsDeclarationStatus())){
			pageList = inoutYardBodyService.lambdaQuery()
					.like(!StringUtil.isNullOrEmpty(inoutYardBody.getInCheckNum()),InoutYardBody::getInCheckNum,inoutYardBody.getInCheckNum())
					.like(!StringUtil.isNullOrEmpty(inoutYardBody.getOutCheckNum()),InoutYardBody::getOutCheckNum,inoutYardBody.getOutCheckNum())
					.isNull(true,InoutYardBody::getDelTim)
					.eq(inoutYardBody.getHFid()>0,InoutYardBody::getHFid,inoutYardBody.getHFid())
					.isNull(true, InoutYardBody::getCustomsDeclarationUser)
//				.eq(StringUtils.isNotBlank(inoutYardHead.getInoutYardKnd()),InoutYardHead::getInoutYardKnd,inoutYardHead.getInoutYardKnd())
//				.between(StringUtils.isNotBlank(inoutYardHead.getStartTime()), InoutYardHead::getRecordTim,startTime,endTime)
					.orderByDesc(true,InoutYardBody::getRecordTim)
					.page(new Page<>(page.getCurrent(), page.getSize()));
		}

		return R.ok(pageList);



    }
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/pageOne")
	public R getPage(Page page, InoutYardBody inoutYardBody) {
//		Page pageOne = inoutYardBodyService.page(page, Wrappers.query(inoutYardBody).isNull("DEL_TIM").eq("IN_OUT",1));
		IPage<InoutYardBody>  pageOne= inoutYardBodyService.selectPageVo(page,inoutYardBody );
		return R.ok(pageOne);
	}

    /**
     * 通过id查询出入库单表体
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
//    @PreAuthorize("@pms.hasPermission('inoutyardbody_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(inoutYardBodyService.getById(fId));
    }

    /**
     * 新增出入库单表体
     * @param inoutYardBody 出入库单表体
     * @return R
     */
    @ApiOperation(value = "新增出入库单表体", notes = "新增出入库单表体")
    @SysLog("新增出入库单表体" )
    @PostMapping
//    @PreAuthorize("@pms.hasPermission('inoutyardbody_add')" )
    public R save(@RequestBody InoutYardBody inoutYardBody) {
		InoutYardHead inoutYardHead = inoutYardHeadService.getById(inoutYardBody.getHFid());
		if(inoutYardHead!=null){
			if(inoutYardHead.getBusinessCheckStatus().equals("1")){
				return R.failed("当前保税物流数据已经审核通过，不允许新增！");
			}
		}

		inoutYardBody.setOperNam(SecurityUtils.getUser().getUsername());
		inoutYardBody.setRecordTim(new Date());
        return R.ok(inoutYardBodyService.save(inoutYardBody));

    }

    /**
     * 修改出入库单表体
     * @param inoutYardBody 出入库单表体
     * @return R
     */
    @ApiOperation(value = "修改出入库单表体", notes = "修改出入库单表体")
    @SysLog("修改出入库单表体" )
    @PutMapping
//    @PreAuthorize("@pms.hasPermission('inoutyardbody_edit')" )
    public R updateById(@RequestBody InoutYardBody inoutYardBody) {
		InoutYardHead inoutYardHead = inoutYardHeadService.getById(inoutYardBody.getHFid());
		if(inoutYardHead!=null){
			if(inoutYardHead.getBusinessCheckStatus().equals("1")){
				return R.failed("当前数据已经审核通过，不允许修改！");
			}
		}
        return R.ok(inoutYardBodyService.updateById(inoutYardBody));
    }


	/**
	 * 修改核注清单号
	 * @param inoutYardBody 修改核注清单号
	 * @return R
	 */
	@ApiOperation(value = "修改核注清单号", notes = "修改核注清单号")
	@SysLog("修改核注清单号" )
	@PutMapping("/updateByForCheckNum")
//    @PreAuthorize("@pms.hasPermission('inoutyardbody_edit')" )
	public R updateByForCheckNum(@RequestBody InoutYardBody inoutYardBody) {
//		InoutYardHead inoutYardHead = inoutYardHeadService.getById(inoutYardBody.getHFid());
//		if(inoutYardHead!=null){
//			if(inoutYardHead.getBusinessCheckStatus().equals("1")){
//				return R.failed("当前数据已经审核通过，不允许修改！");
//			}
//		}
		inoutYardBody.setCheckNumUser(SecurityUtils.getUser().getUsername());
		inoutYardBody.setCheckNumTim(new Date());
		return R.ok(inoutYardBodyService.updateById(inoutYardBody));
	}

	/**
	 * 修改核注清单号
	 * @param inoutYardBody 修改核注清单号
	 * @return R
	 */
	@ApiOperation(value = "修改核注清单号", notes = "修改核注清单号")
	@SysLog("修改核注清单号" )
	@PutMapping("/updateByForCustomsDeclaration")
//    @PreAuthorize("@pms.hasPermission('inoutyardbody_edit')" )
	public R updateByForCustomsDeclaration(@RequestBody InoutYardBody inoutYardBody) {
//		InoutYardHead inoutYardHead = inoutYardHeadService.getById(inoutYardBody.getHFid());
//		if(inoutYardHead!=null){
//			if(inoutYardHead.getBusinessCheckStatus().equals("1")){
//				return R.failed("当前数据已经审核通过，不允许修改！");
//			}
//		}
		inoutYardBody.setCustomsDeclarationUser(SecurityUtils.getUser().getUsername());
		inoutYardBody.setCustomsDeclarationTim(new Date());
		return R.ok(inoutYardBodyService.updateById(inoutYardBody));
	}

    /**
     * 通过id删除出入库单表体
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除出入库单表体", notes = "通过id删除出入库单表体")
    @SysLog("通过id删除出入库单表体" )
    @DeleteMapping("/{fId}" )
//    @PreAuthorize("@pms.hasPermission('inoutyardbody_del')" )
    public R removeById(@PathVariable Integer fId) {
		InoutYardBody inoutYardBody = inoutYardBodyService.getById(fId);
		InoutYardHead inoutYardHead = inoutYardHeadService.getById(inoutYardBody.getHFid());
		if(inoutYardHead!=null){
			if(inoutYardHead.getBusinessCheckStatus().equals("1")){
				return R.failed("当前保税物流数据已经审核通过，不允许删除！");
			}
			if(inoutYardHead.getOperCheckStatus().equals("1") && !inoutYardHead.getInCheckNum().equals(inoutYardHead.getInCheckNum())){
				return R.failed("出区核注清单号已经审核通过，不允许s删除！");
			}
		}
		InoutYardBody inoutYardBodyOL = new InoutYardBody();
		inoutYardBodyOL.setDelNam(SecurityUtils.getUser().getUsername());
		inoutYardBodyOL.setDelTim(new Date());
		inoutYardBodyOL.setFId(fId);
		return R.ok(inoutYardBodyService.updateById(inoutYardBodyOL));
    }


	/**
	 * 修改核注清单号
	 * @param inoutYardBody 修改核注清单号
	 * @return R
	 */
	@ApiOperation(value = "复制表体数据", notes = "复制表体数据")
	@SysLog("复制表体数据" )
	@PutMapping("/updateCopy")
	public R updateCopy(@RequestBody InoutYardBody inoutYardBody) {
		InoutYardBody oldInoutYardBody = inoutYardBodyService.getById(inoutYardBody.getFId());
		oldInoutYardBody.setFId(null);
		oldInoutYardBody.setInCheckNum("");
		oldInoutYardBody.setOutCheckNum("");
		oldInoutYardBody.setCustomsDeclarationTim(null);
		oldInoutYardBody.setCustomsDeclarationUser("");
		oldInoutYardBody.setCheckNumTim(null);
		oldInoutYardBody.setCheckNumUser("");
		if(oldInoutYardBody.getInOut().equals("1")){
			oldInoutYardBody.setInCustomsDeclaration("");
		}
		oldInoutYardBody.setRecordTim(new Date());
		oldInoutYardBody.setOperNam(SecurityUtils.getUser().getUsername());
		return R.ok(inoutYardBodyService.save(oldInoutYardBody));
	}

	/**
	 * 修改showFlag
	 * @param inoutYardBody 修改核注清单号
	 * @return R
	 */
	@ApiOperation(value = "复制表体数据", notes = "复制表体数据")
	@SysLog("复制表体数据" )
	@PutMapping("/showFlag")
	public R modifyShowFlag(@RequestBody List<InoutYardBody> inoutYardBody) {
		boolean update =false;
		for (InoutYardBody yardBody : inoutYardBody) {
			UpdateWrapper<InoutYardBody> body = new UpdateWrapper<>();
			body.eq("F_ID",yardBody.getFId());
			body.set("SHOW_FLAG",2);
			update = inoutYardBodyService.update(body);
		}

		return R.ok(update);
	}
	//	select t.IN_CUSTOMS_DECLARATION from inout_yard_body t WHERE in_out = '1' AND del_Nam IS NULL
	/**
	 * 修改showFlag
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "列表", notes = "列表")
	@SysLog("列表" )
	@GetMapping("/getInNumberListAndCustoms")
	public R modifyShowFlag() {
		QueryWrapper<InoutYardBody> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("in_out","1");
		queryWrapper1.select("distinct IN_CHECK_NUM,IN_CUSTOMS_DECLARATION,ROUGH_WET,PIECE_NUM");
		queryWrapper1.isNull("del_Nam");
		queryWrapper1.isNotNull("IN_CHECK_NUM");
		QueryWrapper<InoutYardBody> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("in_out","1");
		queryWrapper2.select("distinct IN_CHECK_NUM,IN_CUSTOMS_DECLARATION,ROUGH_WET,PIECE_NUM");
		queryWrapper2.isNull("del_Nam");
		queryWrapper2.isNotNull("IN_CUSTOMS_DECLARATION");
		//和注清单号
		List<InoutYardBody> list = inoutYardBodyService.list(queryWrapper1);
		List<InoutYardBody> list2 = inoutYardBodyService.list(queryWrapper2);
		Map<String,Object> map = new HashMap<>();
		map.put("list",list);
		map.put("list2",list2);
		return  R.ok(map);

	}

	/**
	 * 修改showFlag
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "通过id获取值", notes = "通过id获取值")
	@SysLog("通过id获取值" )
	@GetMapping("/getId/{id}")
	public R getId(@PathVariable String id) {
		QueryWrapper<InoutYardBody> wapper1 = new QueryWrapper<>();
		wapper1.eq("IN_CHECK_NUM",id);
		wapper1.eq("in_out","1");
		wapper1.isNull("del_Nam");
		wapper1.isNotNull("IN_CHECK_NUM");
		InoutYardBody one = inoutYardBodyService.getOne(wapper1);
		QueryWrapper<InoutYardHead> wapper2= new QueryWrapper<>();
		wapper2.eq("F_ID",one.getHFid());
		InoutYardHead one1 = inoutYardHeadService.getOne(wapper2);
		String netWetUnit = one1.getNetWetUnit();
		return  R.ok(netWetUnit);
	}
	/**
	 * 修改showFlag
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "通过id获取值", notes = "通过id获取值")
	@SysLog("通过id获取值" )
	@GetMapping("/getBgId/{id}")
	public R getBgId(@PathVariable String id) {
		QueryWrapper<InoutYardBody> wapper1 = new QueryWrapper<>();
		wapper1.eq("IN_CUSTOMS_DECLARATION",id);
		wapper1.eq("in_out","1");
		wapper1.isNull("del_Nam");
		wapper1.isNotNull("IN_CHECK_NUM");
		InoutYardBody one = inoutYardBodyService.getOne(wapper1);
		QueryWrapper<InoutYardHead> wapper2= new QueryWrapper<>();
		wapper2.eq("F_ID",one.getHFid());
		InoutYardHead one1 = inoutYardHeadService.getOne(wapper2);
		String netWetUnit = one1.getNetWetUnit();
		return  R.ok(netWetUnit);
	}

}
