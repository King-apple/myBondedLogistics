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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.ykport.baoshuisystem.entity.OutportCertificate;
import com.newweb.mrli.ykport.baoshuisystem.service.OutportCertificateService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * 一日游出港证统计(暂时)
 *
 * @author zada
 * @date 2021-10-22 11:06:37
 */
@RestController
@AllArgsConstructor
@RequestMapping("/outportcertificate" )
@Api(value = "outportcertificate", tags = "一日游出港证统计(暂时)管理")
public class OutportCertificateController {

    private final OutportCertificateService outportCertificateService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param outportCertificate 一日游出港证统计(暂时)
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('onedaycensus_view')" )
    public R getOutportCertificatePage(Page page, OutportCertificate outportCertificate) {
		IPage<?> pages = outportCertificateService.getList(page, outportCertificate);
		//outportCertificateService.page(page, Wrappers.query(outportCertificate))
        return R.ok(pages);
    }


	/**
	 * 导出
	 * @param outportCertificate 导出
	 * @param outportCertificate 一日游出港证统计(暂时)
	 * @return
	 */
	@ApiOperation(value = "导出", notes = "导出")
	@PostMapping("/exportExcel" )
	public void exportExcel(@RequestBody OutportCertificate outportCertificate, HttpServletResponse response) throws IOException {
		List<OutportCertificate> list = outportCertificateService.getListExcel(outportCertificate);
		//outportCertificateService.page(page, Wrappers.query(outportCertificate))
		outportCertificateService.exportExcel(outportCertificate,list,response);
	}

    /**
     * 通过id查询一日游出港证统计(暂时)
     * @param recordDte id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{recordDte}" )
    @PreAuthorize("@pms.hasPermission('onedaycensus_view')" )
    public R getById(@PathVariable("recordDte" ) Date recordDte) {
        return R.ok(outportCertificateService.getById(recordDte));
    }

    /**
     * 新增一日游出港证统计(暂时)
     * @param outportCertificate 一日游出港证统计(暂时)
     * @return R
     */
    @ApiOperation(value = "新增一日游出港证统计(暂时)", notes = "新增一日游出港证统计(暂时)")
    @SysLog("新增一日游出港证统计(暂时)" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('onedaycensus_add')" )
    public R save(@RequestBody OutportCertificate outportCertificate) {
        return R.ok(outportCertificateService.save(outportCertificate));
    }

    /**
     * 修改一日游出港证统计(暂时)
     * @param outportCertificate 一日游出港证统计(暂时)
     * @return R
     */
    @ApiOperation(value = "修改一日游出港证统计(暂时)", notes = "修改一日游出港证统计(暂时)")
    @SysLog("修改一日游出港证统计(暂时)" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('onedaycensus_edit')" )
    public R updateById(@RequestBody OutportCertificate outportCertificate) {
        return R.ok(outportCertificateService.updateById(outportCertificate));
    }

    /**
     * 通过id删除一日游出港证统计(暂时)
     * @param recordDte id
     * @return R
     */
    @ApiOperation(value = "通过id删除一日游出港证统计(暂时)", notes = "通过id删除一日游出港证统计(暂时)")
    @SysLog("通过id删除一日游出港证统计(暂时)" )
    @DeleteMapping("/{recordDte}" )
    @PreAuthorize("@pms.hasPermission('onedaycensus_del')" )
    public R removeById(@PathVariable Date recordDte) {
        return R.ok(outportCertificateService.removeById(recordDte));
    }

}
