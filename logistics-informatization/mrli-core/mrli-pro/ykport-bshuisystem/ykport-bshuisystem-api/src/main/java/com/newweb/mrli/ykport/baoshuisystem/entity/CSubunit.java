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
 * 货管库区
 *
 * @author pigx code generator
 * @date 2021-08-28 15:17:55
 */
@Data
@TableName("BSWL.C_SUBUNIT")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "货管库区")
public class CSubunit extends Model<CSubunit> {

    private static final long serialVersionUID = 1L;

    /**
     * 代码
     */
	@ApiModelProperty(value="公司代码")
    private String unitCod;

    /**
     * 名称
     */
    @ApiModelProperty(value="公司名称")
    private String unitNam;

	/**
	 * 字段名
	 */
	@ApiModelProperty(value="部门代码")
	private String subunitCod;

	/**
	 * 备注
	 */
	@ApiModelProperty(value="部门名称")
	private String subunitNam;

	/**
	 * 级别(0系统1应用)
	 */
	@ApiModelProperty(value="级别(0系统1应用)")
	private String markId;

	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String markTxt;

	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String remarkId;


}
