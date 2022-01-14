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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.JdbcType;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务单据头表
 *
 * @author 李洋
 * @date 2021-09-06 09:12:35
 */
@Data
@TableName("BUSINESS_BILLS_HEAD")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "业务单据头表")
@KeySequence(value = "SEQ_BUSINESS_BILLS_HEAD", clazz = Integer.class)
public class BusinessBillsHead extends Model<BusinessBillsHead> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value="主键")
	@JsonProperty("fId")
    private Long fId;

    /**
     * 公司名称
     */
    @ApiModelProperty(value="公司名称")
    private String companyNam;

    /**
     * 单据类型
     */
    @ApiModelProperty(value="单据类型")
    private String billKnd;

    /**
     * 单据名称
     */
    @ApiModelProperty(value="单据名称")
    private String billNam;

    /**
     * 付款编号
     */
    @ApiModelProperty(value="付款编号")
    private String billCod;

    /**
     * 合同编号
     */
    @ApiModelProperty(value="合同编号")
    private String contCod;

    /**
     * 承办部门
     */
    @ApiModelProperty(value="承办部门")
    private String dept;

    /**
     * 日期
     */
    @ApiModelProperty(value="日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date billDate;

    /**
     * 客户代码
     */
    @ApiModelProperty(value="客户代码")
    private String secondCod;

    /**
     * 客户
     */
    @ApiModelProperty(value="客户")
    private String secondNam;

    /**
     * 船名
     */
    @ApiModelProperty(value="船名")
    private String shipNam;

    /**
     * 货名代码
     */
    @ApiModelProperty(value="货名代码")
    private String cargoCod;

    /**
     * 货名
     */
    @ApiModelProperty(value="货名")
    private String cargoNam;

    /**
     * 计费数量
     */
    @ApiModelProperty(value="计费数量")
    private BigDecimal feeQuantity;

    /**
     * 计费单位
     */
    @ApiModelProperty(value="计费单位")
    private String feeNam;

    /**
     * 经办人
     */
    @ApiModelProperty(value="经办人")
    private String operator;

    /**
     * 添加人
     */
    @ApiModelProperty(value="添加人")
    private String operNam;

    /**
     * 添加时间
     */
    @ApiModelProperty(value="添加时间")
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
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
	@ApiModelProperty(value="承办单位")
	private String firstNam;
	@ApiModelProperty(value="承办单位代码")
	private String firstCod;
	@ApiModelProperty(value="发票类型")
	private String invoiceType;
	/**
	 * 货类编码
	 */
	@ApiModelProperty(value="货类编码")
	private String cargoKindCod;

	/**
	 * 货类名称
	 */
	@ApiModelProperty(value="货类名称")
	private String cargoKindNam;

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

	/**
	 * 录入方式(0:选择录入 ；1:手动录入)
	 */
	@ApiModelProperty(value="录入方式")
	private String inputType;

	/**
	 * 部门id
	 */
	@ApiModelProperty(value="部门id")
	private String deptId;

	/**
	 * 付款单位
	 */
	@ApiModelProperty(value="付款单位")
	private String paymentUnit;

	/**
	 * 收款单位
	 */
	@ApiModelProperty(value="收款单位")
	private String ceceivingUnit;

	/**
	 * 内外贸
	 */
	@ApiModelProperty(value="内外贸")
	private String tradeId;

	/**
	 * 装卸船
	 */
	@ApiModelProperty(value="装卸船")
	private String loadingShip;

	@ApiModelProperty(value="一日游费用")
	@JsonProperty("oneDayTour")
	private String oneDayTour;

}
