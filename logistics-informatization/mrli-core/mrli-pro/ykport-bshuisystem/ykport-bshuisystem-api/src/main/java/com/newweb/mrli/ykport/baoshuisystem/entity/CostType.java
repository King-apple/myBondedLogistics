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
 * 费用类型码表
 *
 * @author pigx code generator
 * @date 2021-07-28 10:07:25
 */
@Data
@TableName("COST_TYPE")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "费用类型码表")
public class CostType extends Model<CostType> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="主键")
    private Integer fId;

    /**
     * 费用类型编码
     */
    @ApiModelProperty(value="费用类型编码")
    private String costTypeCode;

    /**
     * 费用类型名称
     */
    @ApiModelProperty(value="费用类型名称")
    private String costType;

    /**
     * 费率
     */
    @ApiModelProperty(value="费率")
    private Double rate;

	/**
	 * 费率单位
	 */
	@ApiModelProperty(value="费率单位")
	private String rateUnit;

	/**
	 * 货类
	 */
	@ApiModelProperty(value="货类")
	private String goodsCategory;

	/**
	 * 是否为协议用户
	 */
	@ApiModelProperty(value="是否为协议用户")
	private String userAgreement;

	/**
	 * 存放地点
	 */
	@ApiModelProperty(value="存放地点")
	private String storagePlace;

	/**
	 * 内外贸
	 */
	@ApiModelProperty(value="内外贸")
	private String tradeType;

	/**
	 * 进出口
	 */
	@ApiModelProperty(value="进出口")
	private String importExport;

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
