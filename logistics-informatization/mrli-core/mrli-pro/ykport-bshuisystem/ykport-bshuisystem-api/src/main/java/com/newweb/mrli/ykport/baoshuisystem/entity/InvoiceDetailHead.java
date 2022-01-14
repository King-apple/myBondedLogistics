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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 开票明细头表
 *
 * @author pigx code generator
 * @date 2021-09-22 09:23:31
 */
@Data
@TableName("INVOICE_DETAIL_HEAD")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "开票明细头表")
@KeySequence(value = "SEQ_INVOICE_DETAIL_HEAD",clazz = Long.class)
public class InvoiceDetailHead extends Model<InvoiceDetailHead> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value="主键")
	@JsonProperty("fId")
    private Integer fId;

    /**
     * 审批单主键
     */
    @ApiModelProperty(value="审批单主键")
	@JsonProperty("approveFid")
    private Integer approveFid;

    /**
     * 结算单主键
     */
    @ApiModelProperty(value="结算单主键")
	@JsonProperty("billFid")
    private Integer billFid;

    /**
     * 结算单编号
     */
    @ApiModelProperty(value="结算单编号")
	@JsonProperty("billCod")
    private String billCod;

    /**
     * 对应账户
     */
    @ApiModelProperty(value="对应账户")
    private String accountNam;

    /**
     * 发票类型
     */
    @ApiModelProperty(value="发票类型")
    private String invoiceType;

    /**
     * 客户代码
     */
    @ApiModelProperty(value="客户代码")
    private String secondCod;

    /**
     * 客户名称
     */
    @ApiModelProperty(value="客户名称")
    private String secondNam;

    /**
     * 合计金额
     */
    @ApiModelProperty(value="合计金额")
    private BigDecimal totalMoney;

    /**
     * 船名
     */
    @ApiModelProperty(value="船名")
    private String shipNam;

    /**
     * 日期
     */
    @ApiModelProperty(value="日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date operDat;

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
	/**
	 * 转账
	 */
	@ApiModelProperty(value="转账")
	private String payment;

	/**
	 * 是否上传至发票系统（0或null：未上传；1：已上传）
	 */
	@ApiModelProperty(value="是否上传至发票系统（0或null：未上传；1：已上传）")
	@JsonProperty("uploadBillFlag")
	private String uploadBillFlag;

	/**
	 * 起草人部门id
	 */
	@ApiModelProperty(value="起草人部门id")
	private String deptId;
	/**
	 * 入境船名
	 */
	@ApiModelProperty(value="入境船名")
	private String inShipName;

	/**
	 * 出境船名
	 */
	@ApiModelProperty(value="出境船名")
	private String outShipName;
}
