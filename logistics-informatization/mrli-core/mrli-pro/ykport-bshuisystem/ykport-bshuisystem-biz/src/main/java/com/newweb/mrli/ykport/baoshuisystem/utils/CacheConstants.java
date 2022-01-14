package com.newweb.mrli.ykport.baoshuisystem.utils;

import com.alibaba.fastjson.JSONObject;
import feign.Param;
import feign.RequestLine;

public interface CacheConstants {

	/**
	 * 保管单位信息缓存
	 */
	String SUBUNIT_DETAILS = "subunit_details";

	/**
	 * 查询保管单位信息缓存
	 */
	String SHIP_DETAILS = "ship_details";



	@RequestLine("GET /admin/user/info/{tel}")
	JSONObject callback(@Param(value = "tel") String tel);




}
