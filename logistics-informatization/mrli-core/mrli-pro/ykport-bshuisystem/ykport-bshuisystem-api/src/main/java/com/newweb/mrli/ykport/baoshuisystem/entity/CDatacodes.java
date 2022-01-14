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

package com.newweb.mrli.ykport.baoshuisystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 作业类型码表
 *
 * @author pigx code generator
 * @date 2021-08-28 15:17:55
 */
@Data
@TableName("BSWL.C_DATACODES")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "作业类型码表")
public class CDatacodes extends Model<CDatacodes> {

    private static final long serialVersionUID = 1L;

    /**
     * 代码
     */
	@ApiModelProperty(value="代码")
    private String code;

    /**
     * 作业类型
     */
    @ApiModelProperty(value="作业类型")
    private String meaning;

	/**
	 * 字段名
	 */
	@ApiModelProperty(value="字段名")
	private String fldname;

	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String markTxt;

	/**
	 * 级别(0系统1应用)
	 */
	@ApiModelProperty(value="级别(0系统1应用)")
	private String markId;

	/**
	 * 标志
	 */
	@ApiModelProperty(value="标志")
	private String itemTyp;


}
