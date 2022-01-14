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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.OilDataStatistics;
import com.newweb.mrli.ykport.baoshuisystem.service.OilDataStatisticsService;
import com.newweb.mrli.ykport.baoshuisystem.vo.OilDataStatisticsVo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


/**
 * 油品录入数据统计表
 *
 * @author Tara
 * @date 2021-10-19 09:48:47
 */
@RestController
@AllArgsConstructor
@RequestMapping("/oildatastatistics" )
@Api(value = "oildatastatistics", tags = "油品录入数据统计表管理")
public class OilDataStatisticsController {

    private final  OilDataStatisticsService oilDataStatisticsService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param oilDataStatisticsVo 油品录入数据统计表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('oildatastatistics_view')" )
    public R getOilDataStatisticsPage(Page page, OilDataStatisticsVo oilDataStatisticsVo) {
//        return R.ok(oilDataStatisticsService.page(page, Wrappers.query(oilDataStatistics)
////				.isNull("DEL_TIM")
////				.orderByDesc("RECORD_TIM")
////		));
		IPage<OilDataStatisticsVo> OilDataStatisticsVoIPage = oilDataStatisticsService.selectPageData(page,oilDataStatisticsVo);
		return R.ok(OilDataStatisticsVoIPage);
    }

    /**
     * 通过id查询油品录入数据统计表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('oildatastatistics_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(oilDataStatisticsService.getById(fId));
    }

    /**
     * 新增油品录入数据统计表
     * @param oilDataStatistics 油品录入数据统计表
     * @return R
     */
    @ApiOperation(value = "新增油品录入数据统计表", notes = "新增油品录入数据统计表")
    @SysLog("新增油品录入数据统计表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('oildatastatistics_add')" )
    public R save(@RequestBody OilDataStatistics oilDataStatistics) {
		oilDataStatistics.setOperNam(SecurityUtils.getUser().getUsername());
		oilDataStatistics.setRecordTim(new Date());
        return R.ok(oilDataStatisticsService.save(oilDataStatistics));
    }

    /**
     * 修改油品录入数据统计表
     * @param oilDataStatistics 油品录入数据统计表
     * @return R
     */
    @ApiOperation(value = "修改油品录入数据统计表", notes = "修改油品录入数据统计表")
    @SysLog("修改油品录入数据统计表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('oildatastatistics_edit')" )
    public R updateById(@RequestBody OilDataStatistics oilDataStatistics) {
		oilDataStatistics.setUpdateNam(SecurityUtils.getUser().getUsername());
		oilDataStatistics.setUpdateTim(new Date());
        return R.ok(oilDataStatisticsService.updateById(oilDataStatistics));
    }

    /**
     * 通过id删除油品录入数据统计表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除油品录入数据统计表", notes = "通过id删除油品录入数据统计表")
    @SysLog("通过id删除油品录入数据统计表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('oildatastatistics_del')" )
    public R removeById(@PathVariable Long fId) {
		OilDataStatistics oilDataStatistics = new OilDataStatistics();
		oilDataStatistics.setDelNam(SecurityUtils.getUser().getUsername());
		oilDataStatistics.setDelTim(new Date());
		oilDataStatistics.setFId(fId);
        return R.ok(oilDataStatisticsService.updateById(oilDataStatistics));
    }

	@SysLog("导出excel前检查" )
	@PostMapping ("/exportCheckBefore")
    public R exportCheckBefore(@RequestBody OilDataStatisticsVo oilDataStatisticsVo){
    	String msg = oilDataStatisticsService.exportCheckBefore(oilDataStatisticsVo);
    	if (StringUtil.isEmpty(msg)) {
    		return R.ok();
		} else {
    		return R.ok(null,msg);
		}
	}

	@ApiOperation(value = "导出excel", notes = "导出excel")
	@SysLog("导出excel" )
	@PostMapping ("/export")
	@PreAuthorize("@pms.hasPermission('oildatastatistics_export')" )
	public void exportExcel(@RequestBody OilDataStatisticsVo oilDataStatisticsVo , HttpServletResponse response, Page page) throws IOException {
		oilDataStatisticsService.expoprtExcel(oilDataStatisticsVo,response,page);
	}

	@ApiOperation(value = "下载导入模板", notes = "下载导入模板")
	@SysLog("下载导入模板" )
	@PostMapping ("/downloadTemplate")
	@PreAuthorize("@pms.hasPermission('oildatastatistics_import')" )
	public void downloadTemplate(HttpServletResponse response, Page page) throws IOException {
		oilDataStatisticsService.downloadTemplate(response,page);
	}

	@ApiOperation(value = "导入Excel", notes = "导入Excel")
	@SysLog("导入Excel" )
	@PostMapping(value = "/import")
	@PreAuthorize("@pms.hasPermission('oildatastatistics_import')" )
	public R importExcel(@RequestParam("file") MultipartFile files) throws IOException, InvalidFormatException {
		return oilDataStatisticsService.uploadOildatastatisticsExcel(files);
	}

}
