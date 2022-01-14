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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.ykport.baoshuisystem.entity.ShipTon;
import com.newweb.mrli.ykport.baoshuisystem.entity.SingShip;
import com.newweb.mrli.ykport.baoshuisystem.service.ShipTonService;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;


/**
 * ${comments}
 *
 * @author pigx code generator
 * @date 2021-10-27 09:49:48
 */
@RestController
@AllArgsConstructor
@RequestMapping("/shipton" )
@Api(value = "shipton", tags = "${comments}管理")
public class ShipTonController {

    private final  ShipTonService shipTonService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param shipTon ${comments}
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('shipton_view')" )
	@DS("slave2")
    public R getShipTonPage(Page page, ShipTon shipTon) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		boolean b =false;
		String date = "";
		if(shipTon.getRecordTim()!=null){
			date = sdf.format(shipTon.getRecordTim());
			b = true;
		}
		IPage<ShipTon> page1 = shipTonService.lambdaQuery()
				.eq(shipTon.getShipNo()!=null, ShipTon::getShipNo, shipTon.getShipNo())
				.eq(StringUtils.isNotBlank(shipTon.getUnitCod()), ShipTon::getUnitCod, shipTon.getUnitCod())
				.eq(shipTon.getInoutId()!=null, ShipTon::getInoutId, shipTon.getInoutId())
				.apply(b,"to_char(RECORD_TIM,'yyyy-MM-dd') = '"+date+"'")
				.page(new Page<>(page.getCurrent(), page.getSize()));
        return R.ok(page1);
    }


    /**
     * 通过id查询${comments}
     * @param contNo id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{contNo}" )
    @PreAuthorize("@pms.hasPermission('shipton_view')" )
    public R getById(@PathVariable("contNo" ) String contNo) {
        return R.ok(shipTonService.getById(contNo));
    }

}
