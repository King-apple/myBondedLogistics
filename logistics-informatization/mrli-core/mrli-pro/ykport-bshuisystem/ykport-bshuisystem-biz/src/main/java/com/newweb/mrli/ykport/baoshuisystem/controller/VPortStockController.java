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

import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.ykport.baoshuisystem.entity.VPortStock;
import com.newweb.mrli.ykport.baoshuisystem.service.VPortStockService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;


/**
 * 实时港存信息查询（理货）
 *
 * @author pigx code generator
 * @date 2021-10-23 17:21:47
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vportstock" )
@Api(value = "vportstock", tags = "实时港存信息（理货）")
public class VPortStockController {

    private final  VPortStockService vPortStockService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param vPortStock ${comments}
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('vportstock_view')" )
    public R getVPortStockPage(Page page, VPortStock vPortStock) {
		QueryWrapper<VPortStock> query = Wrappers.query(vPortStock);
		if(vPortStock.getParams()!=null){
			if(StringUtil.isNotBlank(vPortStock.getParams().get("startDte")+"")&&StringUtil.isNotBlank(vPortStock.getParams().get("endDte")+"")){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date st = null;
				java.util.Date en = null;
				try {
					st = format.parse(vPortStock.getParams().get("startDte")+"");
					en = format.parse(vPortStock.getParams().get("endDte")+"");
				} catch (Exception e) {
					e.printStackTrace();
				}
				java.sql.Date sts = new java.sql.Date(st.getTime());
				java.sql.Date ens = new java.sql.Date(en.getTime());
				query.between("IOYARD_DTE",st,en);
			}
		}
		return R.ok(vPortStockService.page(page, query));
    }


    /**
     * 通过id查询${comments}
     * @param unitCod id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{unitCod}" )
    @PreAuthorize("@pms.hasPermission('vportstock_view')" )
    public R getById(@PathVariable("unitCod" ) String unitCod) {
        return R.ok(vPortStockService.getById(unitCod));
    }


}
