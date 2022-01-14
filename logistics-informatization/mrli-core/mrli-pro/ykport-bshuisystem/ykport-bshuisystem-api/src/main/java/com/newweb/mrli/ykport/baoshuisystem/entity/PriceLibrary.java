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

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 价格库表
 *
 * @author pigx code generator
 * @date 2021-08-28 15:18:15
 */
@Data
@TableName("PRICE_LIBRARY")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "价格库表")
@KeySequence(value = "SEQ_PRICE_LIBRARY",clazz = Integer.class)
public class PriceLibrary extends Model<PriceLibrary> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value = "F_ID",type = IdType.INPUT)
	@ApiModelProperty(value="主键")
	@JsonProperty("fId")
    private Integer fId;

    /**
     * 客户代码
     */
    @ApiModelProperty(value="客户代码")
    private String companyCod;

    /**
     * 客户名称
     */
    @ApiModelProperty(value="客户名称")
	@TableField(condition = SqlCondition.like)
    private String company;

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
     * 规格型号
     */
    @ApiModelProperty(value="规格型号")
    private String format;

    /**
     * 包装
     */
    @ApiModelProperty(value="包装")
    private String pkgKindNam;

    /**
     * 费目编码
     */
    @ApiModelProperty(value="费目编码")
    private String feeNameCod;

    /**
     * 费目
     */
    @ApiModelProperty(value="费目")
	@TableField(condition = SqlCondition.like)
    private String feeNam;

    /**
     * 费率值
     */
    @ApiModelProperty(value="费率值")
    private Double feeRat;

    /**
     * 费率单位
     */
    @ApiModelProperty(value="费率单位")
    private String feeUnit;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String markTxt;

    /**
     * 操作人
     */
    @ApiModelProperty(value="操作人")
    private String operNam;

    /**
     * 操作时间
     */
    @ApiModelProperty(value="操作时间")
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

	/**
	 * 货类名称
	 */
	@ApiModelProperty(value="货类名称")
	private String cargoKindNam;

	/**
	 * 删除人
	 */
	@ApiModelProperty(value="货类编码")
	private String cargoKindCod;

	/**
	 * 存放地点
	 */
	@ApiModelProperty(value="存放地点")
	private String locate;

	/**
	 * 内外贸
	 */
	@ApiModelProperty(value="内外贸")
	private Integer tradeId;

	/**
	 * 进出口
	 */
	@ApiModelProperty(value="进出口")
	private Integer inoutId;
}
