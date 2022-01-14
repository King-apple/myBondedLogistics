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

package com.newweb.mrli.ykport.baoshuisystem.utils;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.annotation.Inner;
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardHead;
import com.newweb.mrli.ykport.baoshuisystem.entity.SysFile;
import com.newweb.mrli.ykport.baoshuisystem.service.SysFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 文件管理
 *
 * @author Luckly
 * @date 2019-06-18 17:18:42
 */
@RestController
@AllArgsConstructor
@RequestMapping("/excelUploadDownload")
@Api(value = "excelUploadDownload", tags = "excel管理")
public class ExcelUploadDownloadController {

	private final SysFileService sysFileService;


	/**
	 * 上传文件 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
	 *
	 * @param file 资源
	 * @return R(/ admin / bucketName / filename)
	 */
	@PostMapping(value = "/uploadForInoutyardBodyAndHead")
	public R upload(@RequestParam("file")MultipartFile files) throws IOException, InvalidFormatException {
		return sysFileService.uploadForInoutyardBodyAndHead(files);
	}

	/**
	 * 上传文件 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
	 *
	 * @param file 资源
	 * @return R(/ admin / bucketName / filename)
	 */
	@PostMapping(value = "/uploadForOther")
	@CrossOrigin
	public R uploadForOther(@RequestPart("file") MultipartFile file) throws IOException, InvalidFormatException {
		return sysFileService.uploadForInoutyardBodyAndHead(file);
	}

	/**
	 * 获取默认编号
	 * @return R
	 */
	@ApiOperation(value = "导出excel", notes = "导出excel")
	@SysLog("导出excel" )
	@PostMapping("/exportExcelForInoutyardBodyAndHead" )
	@PreAuthorize("@pms.hasPermission('contract_view_export')" )
	public void exportExcelForInoutyardBodyAndHead(@RequestBody InoutYardHead inoutYardHead, HttpServletRequest request, HttpServletResponse response) throws IOException {
		sysFileService.exportExcelForInoutyardBodyAndHead(inoutYardHead,request,response);
	}
}
