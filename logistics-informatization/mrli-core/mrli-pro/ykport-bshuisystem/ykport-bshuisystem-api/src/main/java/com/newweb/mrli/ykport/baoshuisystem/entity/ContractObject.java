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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 合同标的码表
 *
 * @author pigx code generator
 * @date 2021-07-27 11:58:08
 */
@Data
@TableName("CARGO_NAMES")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "合同标的码表")
public class ContractObject extends Model<ContractObject> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="主键")
    private Integer fId;

    /**
     * 合同标的编码
     */
    @ApiModelProperty(value="货类编码")
    private String cargoKindCod;

    /**
     * 合同标的名称
     */
    @ApiModelProperty(value="货类名称")
    private String cargoKindNam;

	/**
	 * 货物编码
	 */
	@ApiModelProperty(value="货物编码")
	private String cargoCod;

	/**
	 * 货物名称
	 */
	@ApiModelProperty(value="货物名称")
	@TableField(condition = SqlCondition.like)
	private String cargoNam;

	/**
	 * 货物名称
	 */
	@ApiModelProperty(value="货物名称（海关）")
	private String cargoNamCustoms;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String markTxt;

    /**
     * 添加人
     */
    @ApiModelProperty(value="添加人")
    private String operNam;

    /**
     * 添加时间
     */
    @ApiModelProperty(value="添加时间")
    private Date recordTim;

    /**
     * 修改人
     */
    @ApiModelProperty(value="修改人")
    private String updateNam;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date updateTim;

    /**
     * 删除人
     */
    @ApiModelProperty(value="删除人")
    private String delNam;

    /**
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private Date delTim;

}
