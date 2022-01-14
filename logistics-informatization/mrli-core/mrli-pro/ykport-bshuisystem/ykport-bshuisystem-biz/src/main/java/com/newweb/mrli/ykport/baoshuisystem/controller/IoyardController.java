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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.Ioyard;
import com.newweb.mrli.ykport.baoshuisystem.entity.YardInfo;
import com.newweb.mrli.ykport.baoshuisystem.service.IoyardService;
import com.newweb.mrli.ykport.baoshuisystem.service.YardInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 货物出入库表
 *
 * @author pigx code generator
 * @date 2021-09-06 19:35:20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ioyard" )
@Api(value = "ioyard", tags = "货物出入库表管理")
public class IoyardController {

    private final  IoyardService ioyardService;
	private final YardInfoService yardInfoService;
    /**
     * 分页查询
     * @param page 分页对象
     * @param ioyard 货物出入库表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('ioyard_view')" )
    public R getIoyardPage(Page page, Ioyard ioyard) throws ParseException {
		String [] recordDte = new String[]{};
		if(ioyard.getParams()!=null&&ioyard.getParams().get("recordDte")!=null&&!"".equals(ioyard.getParams().get("recordDte"))){
			recordDte =(String[]) ioyard.getParams().get("recordDte");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date[] date = new Date[2];
		if(recordDte.length>0){
			Date parse = sdf.parse(recordDte[0]);
			Date parse1 = sdf.parse(recordDte[1]);
			date = new Date[]{parse, parse1};
		}
		IPage<Ioyard> page1 = ioyardService.lambdaQuery()
				.like(ioyard.getIoyardNo() != null, Ioyard::getIoyardNo, ioyard.getIoyardNo())
				.like(StringUtils.isNotBlank(ioyard.getContNo()), Ioyard::getContNo, ioyard.getContNo())
				.like(StringUtils.isNotBlank(ioyard.getShipNam()),Ioyard::getShipNam,ioyard.getShipNam())
				.like(StringUtils.isNotBlank(ioyard.getCargoNam()),Ioyard::getCargoNam,ioyard.getCargoNam())
				.like(StringUtils.isNotBlank(ioyard.getConsignNam()),Ioyard::getConsignNam,ioyard.getConsignNam())
//				.like(StringUtils.isNotBlank(ioyard.getBillNo()),Ioyard::getBillNo,ioyard.getBillNo())
//				.like(StringUtils.isNotBlank(ioyard.getCustomsDeclaration()),Ioyard::getCustomsDeclaration,ioyard.getCustomsDeclaration())
				.between(recordDte.length > 0, Ioyard::getIoyardDte, date[0], date[1])
				.eq(ioyard.getIoyardId()!=null,Ioyard::getIoyardId,ioyard.getIoyardId())
				.isNull(ioyard.getIoyardId() != null, Ioyard::getPrepareOutyardFlag)
				.eq(ioyard.getPrepareOutyardFlag()!=null&&ioyard.getPrepareOutyardFlag()!=0,Ioyard::getPrepareOutyardFlag,ioyard.getPrepareOutyardFlag())
				.eq(StringUtils.isNotBlank(ioyard.getCustomsDeclaration()),Ioyard::getCustomsDeclaration,ioyard.getCustomsDeclaration())
				.eq(StringUtils.isNotBlank(ioyard.getBillNo()),Ioyard::getBillNo,ioyard.getBillNo())
				.orderByDesc(true,Ioyard::getRecordTim)
				.page(new Page<>(page.getCurrent(), page.getSize()));
		Map<String,Object> map = new HashMap<>();
		if(ioyard.getSearchValue() != null&&"1".equals(ioyard.getSearchValue())){
			QueryWrapper<Ioyard> getCountWrapper = Wrappers.query(new Ioyard())
					.select("IOYARD_ID,sum(WEIGHT_WGT) WEIGHT_WGT,sum(PIECES_NUM) PIECES_NUM,sum(SURPLUS_WEIGHT) SURPLUS_WEIGHT,sum(SURPLUS_PIECE_NUM) SURPLUS_PIECE_NUM");
			if(ioyard.getIoyardNo() != null){
				getCountWrapper = getCountWrapper.like("IOYARD_NO", ioyard.getIoyardNo());
			}
			if(StringUtils.isNotBlank(ioyard.getContNo())){
				getCountWrapper = getCountWrapper.like("CONT_NO", ioyard.getContNo());
			}
			if(StringUtils.isNotBlank(ioyard.getShipNam())){
				getCountWrapper = getCountWrapper.like("SHIP_NAM", ioyard.getShipNam());
			}
			if(StringUtils.isNotBlank(ioyard.getCargoNam())){
				getCountWrapper = getCountWrapper.like("CARGO_NAM", ioyard.getCargoNam());
			}
			if(StringUtils.isNotBlank(ioyard.getConsignNam())){
				getCountWrapper = getCountWrapper.like("CONSIGN_NAM", ioyard.getConsignNam());
			}
			if(recordDte.length > 0){
				getCountWrapper = getCountWrapper.between("IOYARD_DTE", date[0], date[1]);
			}
			if(ioyard.getIoyardId()!=null){
				getCountWrapper = getCountWrapper.eq("IOYARD_ID", ioyard.getIoyardId());
			}
			if(ioyard.getIoyardId() != null){
				getCountWrapper = getCountWrapper.isNull("PREPARE_OUTYARD_FLAG");
			}
			if(ioyard.getPrepareOutyardFlag()!=null&&ioyard.getPrepareOutyardFlag()!=0){
				getCountWrapper = getCountWrapper.eq("PREPARE_OUTYARD_FLAG",ioyard.getPrepareOutyardFlag());
			}
			if(StringUtils.isNotBlank(ioyard.getCustomsDeclaration())){
				getCountWrapper = getCountWrapper.eq("CUSTOMS_DECLARATION",ioyard.getCustomsDeclaration());
			}
			if(StringUtils.isNotBlank(ioyard.getBillNo())){
				getCountWrapper = getCountWrapper.eq("BILL_NO",ioyard.getBillNo());
			}
			getCountWrapper = getCountWrapper.isNull("PREPARE_OUTYARD_FLAG").groupBy("IOYARD_ID").orderByDesc("IOYARD_ID");
			List<Ioyard> list = ioyardService.list(getCountWrapper);
			map.put("countList",list);
		}
		map.put("record",page1);
		return R.ok(map);
    }

	@ApiOperation(value = "合计", notes = "合计")
	@GetMapping("/getIoYard")
	public R aaa(Ioyard ioyard) throws ParseException {
		String [] recordDte = new String[]{};
		if(ioyard.getParams()!=null&&ioyard.getParams().get("recordDte")!=null&&!"".equals(ioyard.getParams().get("recordDte"))){
			recordDte =(String[]) ioyard.getParams().get("recordDte");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date[] date = new Date[2];
		Date parse = null;
		Date parse1	 = null;
		if(recordDte.length>0){
			 parse = sdf.parse(recordDte[0]);
			 parse1 = sdf.parse(recordDte[1]);
			date = new Date[]{parse, parse1};
		}
		Ioyard ioYard;
		if (ioyard.getParams() != null &&ioyard.getParams().get("recordDte")!=null &&!"".equals(ioyard.getParams().get("recordDte"))){
			 ioYard = ioyardService.getAll(ioyard,recordDte[0],recordDte[1]);
		}else{
			 ioYard = ioyardService.getAll(ioyard,null,null);
		}

		return R.ok(ioYard);
	}
    /**
     * 通过id查询货物出入库表
     * @param unitCod id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{unitCod}" )
    @PreAuthorize("@pms.hasPermission('ioyard_view')" )
    public R getById(@PathVariable("unitCod" ) String unitCod) {
        return R.ok(ioyardService.getById(unitCod));
    }

    /**
     * 新增货物出入库表
     * @param ioyard 货物出入库表
     * @return R
     */
    @ApiOperation(value = "新增货物出入库表", notes = "新增货物出入库表")
    @SysLog("新增货物出入库表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('ioyard_add')" )
    public R save(@RequestBody Ioyard ioyard) {
		ioyard.setOperNam(SecurityUtils.getUser().getManName());
		ioyard.setRecordTim(new Date());
		ioyard.setSettlementDteLater(new Date());
		ioyard.setSurplusWeight(new BigDecimal(ioyard.getWeightWgt()+""));
		ioyard.setSurplusPieceNum(ioyard.getPiecesNum());
		ioyard.setOverShortFlag("0");
		if(ioyard.getIoyardNo() == null||"".equals(ioyard.getIoyardNo())){
			ioyard.setBillNo("08818");
		}

		ioyardService.save(ioyard);
		if(ioyard.getIoyardNo() == null||"".equals(ioyard.getIoyardNo())){
			Ioyard ioyard1 = new Ioyard();
			ioyard1.setInoutYardNo(ioyard.getInoutYardNo());
			ioyard1.setIoyardNo(ioyard.getUnitCod()+ioyard.getInoutYardNo()+"0000");
			ioyardService.updateById(ioyard1);
		}
		Ioyard ioyardNew = ioyardService.getById(ioyard.getInoutYardNo());
		YardInfo yardInfo = new YardInfo();
		yardInfo.setInYardNo(ioyardNew.getInoutYardNo()+"");
		yardInfo.setConsignNam(ioyardNew.getConsignNam());
		yardInfo.setCargoNam(ioyardNew.getCargoNam());
		yardInfo.setShipNam(ioyardNew.getShipNam());
		yardInfo.setInPiecesNum(ioyardNew.getPiecesNum());
		yardInfo.setInWeightWgt(ioyardNew.getWeightWgt());
		yardInfo.setInYardDat(ioyardNew.getIoyardDte());
		yardInfo.setOutPiecesNum(0);
		yardInfo.setOutWeightWgt(0.0);
		yardInfo.setSurplusPiecesNum(ioyardNew.getPiecesNum());
		yardInfo.setSurplusWeightWgt(ioyardNew.getWeightWgt());
		yardInfo.setYardNam(ioyardNew.getYardNam());
		yardInfo.setOperNam(SecurityUtils.getUser().getUsername());
		yardInfo.setRecordTim(new Date());
		yardInfo.setBh(ioyardNew.getIoyardNo());
		yardInfoService.save(yardInfo);
        return R.ok(ioyard);
    }

    /**
     * 修改货物出入库表
     * @param ioyard 货物出入库表
     * @return R
     */
    @ApiOperation(value = "修改货物出入库表", notes = "修改货物出入库表")
    @SysLog("修改货物出入库表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('ioyard_edit')" )
    public R updateById(@RequestBody Ioyard ioyard) {
        return R.ok(ioyardService.updateById(ioyard));
    }

    /**
     * 通过id删除货物出入库表
     * @param unitCod id
     * @return R
     */
    @ApiOperation(value = "通过id删除货物出入库表", notes = "通过id删除货物出入库表")
    @SysLog("通过id删除货物出入库表" )
    @DeleteMapping("/{unitCod}" )
    @PreAuthorize("@pms.hasPermission('ioyard_del')" )
    public R removeById(@PathVariable String unitCod) {
        return R.ok(ioyardService.removeById(unitCod));
    }

	/**
	 * 修改货物出入库表
	 * @param ioyard 货物出入库表
	 * @return R
	 */
	@ApiOperation(value = "查询ioyard视图", notes = "查询ioyard视图")
	@SysLog("查询ioyard视图" )
	@PostMapping("/getIoyardVo")
	@PreAuthorize("@pms.hasPermission('ioyard_edit')" )
	public R getIoyardVo(@RequestBody Ioyard ioyard) {
		return R.ok(ioyardService.getIoyardVo(ioyard));
	}

	/**
	 * 删除出入库记录
	 * @param ioyard id
	 * @return R
	 */
	@ApiOperation(value = "删除出入库记录", notes = "删除出入库记录")
	@SysLog("删除出入库记录" )
	@PostMapping("/inoutyardRecordRemove" )
	@PreAuthorize("@pms.hasPermission('ioyard_del')" )
	public R inoutyardRecordRemove(@RequestBody Ioyard ioyard) {
		return R.ok(ioyardService.inoutyardRecordRemove(ioyard));
	}

	/**
	 * 通过出库（销图）单id查询入库（上图）单
	 * @param fId id
	 * @return R
	 */
	@ApiOperation(value = "通过出库（销图）单id查询入库（上图）单", notes = "通过出库（销图）单id查询入库（上图）单")
	@SysLog("通过出库（销图）单id查询入库（上图）单" )
	@GetMapping("/getInIoyard/{fId}" )
	public R getInYardInfoByOutId(@PathVariable Integer fId) {
		Ioyard outIoyard = ioyardService.getById(fId);
		return R.ok(outIoyard.getInYardNo());
	}
	/**
	 * 通过出库（销图）单id查询入库（上图）单
	 * @return R
	 */
	@ApiOperation(value = "查询下拉", notes = "查询下拉")
	@SysLog("查询下拉" )
	@GetMapping("/getSelectCondition" )
	public R getSelectCondition() {
		List<Ioyard> listCont =  ioyardService.getContNo();
		List<Ioyard> listShip =  ioyardService.getShip();
		List<Ioyard> listCargo =  ioyardService.getCargo();
		List<Ioyard> listConSign =  ioyardService.getConSign();
		List<Ioyard> listBillNO = ioyardService.getBillNO();
		List<Ioyard> listCustomsDeclaration = ioyardService.getCustomsDeclaration();
		Map<String, Object> map = new HashMap<>();
		map.put("listCont",listCont);
		map.put("listShip",listShip);
		map.put("listCargo",listCargo);
		map.put("listConSign",listConSign);
		map.put("listBillNO",listBillNO);
		map.put("listCustomsDeclaration",listCustomsDeclaration);
		return R.ok(map);
	}

}
