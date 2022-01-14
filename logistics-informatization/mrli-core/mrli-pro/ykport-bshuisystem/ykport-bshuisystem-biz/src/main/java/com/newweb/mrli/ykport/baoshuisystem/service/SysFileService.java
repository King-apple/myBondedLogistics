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

package com.newweb.mrli.ykport.baoshuisystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardHead;
import com.newweb.mrli.ykport.baoshuisystem.entity.SysFile;
import com.newweb.mrli.common.core.util.R;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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
public interface SysFileService extends IService<SysFile> {

	/**
	 * 上传文件
	 * @param file
	 * @return
	 */
	R uploadFile(MultipartFile file);

	/**
	 * 读取文件
	 * @param bucket 桶名称
	 * @param fileName 文件名称
	 * @param response 输出流
	 */
	void getFile(String bucket, String fileName, HttpServletResponse response);

	/**
	 * 删除文件
	 * @param id
	 * @return
	 */
	Boolean deleteFile(Long id);


	/**
	 * 保存文件
	 * @param file
	 * @return
	 */
	R fileLog(List<SysFile> fileVal);

	/**
	 * 更新文件
	 * @param fileVal
	 * @return
	 */
	R updateFile(List<SysFile> fileVal);

	/**
	 * 上传保税物流入库单
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public R uploadForInoutyardBodyAndHead(MultipartFile file) throws IOException, InvalidFormatException;

	/**
	 * 下载保税物流入库单
	 * @param inoutYardHead
	 * @param request
	 * @param response
	 */
	void exportExcelForInoutyardBodyAndHead(InoutYardHead inoutYardHead, HttpServletRequest request, HttpServletResponse response);
}
