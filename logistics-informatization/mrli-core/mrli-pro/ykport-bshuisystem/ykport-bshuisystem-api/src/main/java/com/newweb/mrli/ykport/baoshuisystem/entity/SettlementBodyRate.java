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
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * 出入库结算单明细费用表
 *
 * @author pigx code generator
 * @date 2021-11-01 09:15:08
 */
@Data
@TableName("SETTLEMENT_BODY_RATE")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "出入库结算单明细费用表")
@KeySequence(value = "SEQ_IOYARD_SETTLEMENT_BODY", clazz = Integer.class)
public class SettlementBodyRate extends Model<SettlementBodyRate> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value="主键")
	@JsonProperty("fId")
    private Integer fId;

    /**
     * 出入库结算单体表主键
     */
    @ApiModelProperty(value="出入库结算单体表主键")
	@JsonProperty("bFid")
    private Integer bFid;

    /**
     * 费目编码
     */
    @ApiModelProperty(value="费目编码")
    private String feeNameCod;

    /**
     * 费目
     */
    @ApiModelProperty(value="费目")
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
     * 费用
     */
    @ApiModelProperty(value="费用")
    private Double money;

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
     * 出入库结算单费目信息表主键
     */
    @ApiModelProperty(value="出入库结算单费目信息表主键")
    private Integer rFid;
	/**
	 * 备注
	 */
	@ApiModelProperty(value="出入库结算单费目信息表主键")
	private String markTxt;
}
