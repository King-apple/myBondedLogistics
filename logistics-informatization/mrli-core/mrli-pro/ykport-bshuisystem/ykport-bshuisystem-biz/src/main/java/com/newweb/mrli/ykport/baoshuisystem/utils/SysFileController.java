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
import com.newweb.mrli.ykport.baoshuisystem.entity.SysFile;
import com.newweb.mrli.ykport.baoshuisystem.service.SysFileService;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.annotation.Inner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件管理
 *
 * @author Luckly
 * @date 2019-06-18 17:18:42
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys-file")
@Api(value = "sys-file", tags = "文件管理")
public class SysFileController {

	private final SysFileService sysFileService;

	/**
	 * 分页查询
	 *
	 * @param page    分页对象
	 * @param sysFile 文件管理
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getSysFilePage(Page page, SysFile sysFile) {
		return R.ok(sysFileService.page(page, Wrappers.query(sysFile)));
	}

	/**
	 * 通过id删除文件管理
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除文件管理", notes = "通过id删除文件管理")
	@SysLog("删除文件管理")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Long id) {
		return R.ok(sysFileService.deleteFile(id));
	}

	/**
	 * 上传文件 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
	 *
	 * @param file 资源
	 * @return R(/ admin / bucketName / filename)
	 */
	@PostMapping(value = "/upload")
	public R upload(@RequestPart("file") MultipartFile file) {
		return sysFileService.uploadFile(file);
	}

	/**
	 * 上传文件 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
	 *
	 * @param file 资源
	 * @return R(/ admin / bucketName / filename)
	 */
	@PostMapping(value = "/uploadForOther")
	@CrossOrigin
	public R uploadForOther(@RequestPart("file") MultipartFile file) {
		return sysFileService.uploadFile(file);
	}

	/**
	 * 获取文件
	 *
	 * @param bucket   桶名称
	 * @param fileName 文件空间/名称
	 * @param response
	 * @return
	 */
	@Inner(false)
	@GetMapping("/{bucket}/{fileName}")
	public void file(@PathVariable String bucket, @PathVariable String fileName, HttpServletResponse response) {
		sysFileService.getFile(bucket, fileName, response);
	}


	/**
	 * 上传文件 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
	 *
	 * @param fileVal 资源
	 * @return R(/ admin / bucketName / filename)
	 */
	@PostMapping(value = "/fileSave")
	public R fileSave(@RequestBody String fileVal) {
		List<SysFile> fileList = JSONArray.parseArray(fileVal,SysFile.class);
		return sysFileService.fileLog(fileList);
	}

	/**
	 * 上传文件 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
	 *
	 * @param fileVal 资源
	 * @return R(/ admin / bucketName / filename)
	 */
	@PostMapping(value = "/fileUpdate")
	public R fileUpdate(@RequestBody String fileVal) {
		List<SysFile> fileList = JSONArray.parseArray(fileVal,SysFile.class);
		return sysFileService.updateFile(fileList);
	}

	/**
	 * 查询对应附件
	 *
	 * @param sysFile 文件管理
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@PostMapping("/getSysFile")
	public R getSysFile(@RequestBody SysFile sysFile) {
		return R.ok(sysFileService.list(Wrappers.query(sysFile)));
	}

	/**
	 * 删除文件
	 *
	 * @param sysFile 文件管理
	 * @return
	 */
	@ApiOperation(value = "删除文件", notes = "删除文件")
	@PostMapping("/delSysFile")
	public R delSysFile(@RequestBody SysFile sysFile) {
		return R.ok(sysFileService.remove(Wrappers.query(sysFile)));
	}
}
