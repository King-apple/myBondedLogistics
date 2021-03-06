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

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.ykport.baoshuisystem.entity.*;
import com.newweb.mrli.ykport.baoshuisystem.service.BcmcxService;
import com.newweb.mrli.ykport.baoshuisystem.service.IoyardService;
import com.newweb.mrli.ykport.baoshuisystem.service.ParameterListService;
import com.newweb.mrli.ykport.baoshuisystem.vo.BcmcxVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.InStrogeDetail;
import com.newweb.mrli.ykport.baoshuisystem.service.InStrogeDetailService;
import com.newweb.mrli.ykport.baoshuisystem.vo.IoyardApprovalProcessSetVO;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * ${comments}
 *
 * @author pigx code generator
 * @date 2021-09-06 19:04:43
 */
@RestController
@AllArgsConstructor
@RequestMapping("/instrogedetail" )
@Api(value = "instrogedetail", tags = "${comments}??????")
public class InStrogeDetailController {

    private final InStrogeDetailService inStrogeDetailService;
	private final BcmcxService bcmcxService;
	private final IoyardService ioyardService;
	private final ParameterListService parameterListService;

    /**
     * ????????????
     * @param page ????????????
     * @param inStrogeDetail ${comments}
     * @return
     */
    @ApiOperation(value = "??????????????????????????????", notes = "????????????")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('instrogedetail_view')" )
    public R getInStrogeDetailPage(Page page, InStrogeDetail inStrogeDetail) {
		IPage<Ioyard> pageList = ioyardService.lambdaQuery()
				.like(StringUtils.isNotBlank(inStrogeDetail.getBh()),Ioyard::getIoyardNo,inStrogeDetail.getBh())
				.like(StringUtils.isNotBlank(inStrogeDetail.getBz()),Ioyard::getClassId,inStrogeDetail.getBz())
				.like(StringUtils.isNotBlank(inStrogeDetail.getSqdw()),Ioyard::getConsignNam,inStrogeDetail.getSqdw())
				.like(StringUtils.isNotBlank(inStrogeDetail.getCm()),Ioyard::getShipNam,inStrogeDetail.getCm())
				.like(StringUtils.isNotBlank(inStrogeDetail.getHm()),Ioyard::getCargoNam,inStrogeDetail.getHm())
				.like(StringUtils.isNotBlank(inStrogeDetail.getBillNO()),Ioyard::getBillNo,inStrogeDetail.getBillNO())
				.like(StringUtils.isNotBlank(inStrogeDetail.getCustomsDeclaration()),Ioyard::getBillNo,inStrogeDetail.getCustomsDeclaration())
				.eq(true,Ioyard::getIoyardId,1)
				.gt(true,Ioyard::getSurplusWeight,0)
				.orderByDesc(true,Ioyard::getRecordTim)
				.page(new Page<>(page.getCurrent(), page.getSize()));;
        return R.ok(pageList);
    }

	/**
	 * ????????????
	 * @param page ????????????
	 * @param inStrogeDetail ${comments}
	 * @return
	 */
	@ApiOperation(value = "??????????????????????????????", notes = "????????????")
	@GetMapping("/instr/page" )
	@PreAuthorize("@pms.hasPermission('instrogedetail_view')" )
	public R getInStrogeDetailPage1(Page page, InStrogeDetail inStrogeDetail) {
		IPage<InStrogeDetail> pageList = inStrogeDetailService.lambdaQuery()
				.like(StringUtils.isNotBlank(inStrogeDetail.getHm()),InStrogeDetail::getHm,inStrogeDetail.getHm())
				.like(StringUtils.isNotBlank(inStrogeDetail.getSqdw()),InStrogeDetail::getSqdw,inStrogeDetail.getSqdw())
				.like(StringUtils.isNotBlank(inStrogeDetail.getCm()),InStrogeDetail::getCm,inStrogeDetail.getCm())
				.like(StringUtils.isNotBlank(inStrogeDetail.getBz()),InStrogeDetail::getBz,inStrogeDetail.getBz())
				.eq(StringUtils.isNotBlank(inStrogeDetail.getBh()),InStrogeDetail::getBh,inStrogeDetail.getBh())
				.isNotNull(true,InStrogeDetail::getBcbh)
				.apply(" ( bccount <> btcount or btcount is null)")
				.page(new Page<>(page.getCurrent(), page.getSize()));;
		return R.ok(pageList);
	}

    /**
     * ??????id??????${comments}
     * @param bh
     * @return R
     */
    @ApiOperation(value = "??????id??????", notes = "??????id??????")
    @GetMapping("/{bh}" )
    @PreAuthorize("@pms.hasPermission('instrogedetail_view')" )
    public R getById(@PathVariable("bh" ) String bh) {
        return R.ok(inStrogeDetailService.getById(bh));
    }


	/**
	 * ??????id??????${comments}
	 * @param inStrogeDetail
	 * @return R
	 */
	@ApiOperation(value = "??????", notes = "??????")
	@PostMapping("upImage" )
	@PreAuthorize("@pms.hasPermission('instrogedetail_upImage')" )
	public R upImage(@RequestBody InStrogeDetail inStrogeDetail) {

		List<Bcmcxtab> bcmcxtabs = JSONArray.parseArray(inStrogeDetail.getBcmcxtabList(),Bcmcxtab.class);
		return R.ok(inStrogeDetailService.upImage(inStrogeDetail,bcmcxtabs));
	}

	/**
	 * ??????id??????${comments}
	 * @param inStrogeDetail
	 * @return R
	 */
	@ApiOperation(value = "??????", notes = "??????")
	@PostMapping("destroyImage" )
	@PreAuthorize("@pms.hasPermission('instrogedetail_upImage')" )
	public R destroyImage(@RequestBody InStrogeDetail inStrogeDetail) {
		return R.ok(inStrogeDetailService.destroyImage(inStrogeDetail));
	}

	/**
	 * ????????????????????????
	 * @param bh
	 * @return R
	 */
	@ApiOperation(value = "????????????????????????", notes = "????????????????????????")
	@GetMapping("getBcmcx/{bh}" )
	@PreAuthorize("@pms.hasPermission('instrogedetail_getBcmcx')" )
	public R getBcmcx(@PathVariable("bh" ) String bh) {
		BcmcxVo bcmcxVo = new BcmcxVo();
		bcmcxVo.setBh(bh);
		return R.ok(bcmcxService.list(Wrappers.query(bcmcxVo).isNull("TJLH")));
	}

	/**
	 * ????????????????????????
	 * @param approvalProcessSet
	 * @return R
	 */
	@ApiOperation(value = "????????????????????????", notes = "????????????????????????")
	@PostMapping("/submitOverShortCheck" )
	public R submitOverShortCheck(@RequestBody ApprovalProcessSet approvalProcessSet) {
		return inStrogeDetailService.submitOverShortCheck(approvalProcessSet);
	}

	/**
	 * ????????????????????????
	 * @param ioyardApprovalProcessSetVO
	 * @return R
	 */
	@ApiOperation(value = "????????????????????????", notes = "????????????????????????")
	@GetMapping("/getOverShortCargo" )
	public R getOverShortCargo(Page page, IoyardApprovalProcessSetVO ioyardApprovalProcessSetVO) {
		return inStrogeDetailService.getOverShortCargo(page,ioyardApprovalProcessSetVO);
	}

	/**
	 * ????????????????????????
	 * @param approvalProcessSet
	 * @return R
	 */
	@ApiOperation(value = "??????????????????", notes = "??????????????????")
	@PostMapping("/submitCheck" )
	public R submitCheck(@RequestBody  ApprovalProcessSet approvalProcessSet) {
		return inStrogeDetailService.submitCheck(approvalProcessSet);
	}
	/**
	 * ??????0.03
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "??????????????????", notes = "??????????????????")
	@GetMapping("/getOverShortMax" )
	public R getOverShortMax() {
		QueryWrapper<ParameterList> wrapper = new QueryWrapper();
		wrapper.eq("PARAM_COD","over_short_max");
		ParameterList one = parameterListService.getOne(wrapper);
		return R.ok(one);
	}

}
