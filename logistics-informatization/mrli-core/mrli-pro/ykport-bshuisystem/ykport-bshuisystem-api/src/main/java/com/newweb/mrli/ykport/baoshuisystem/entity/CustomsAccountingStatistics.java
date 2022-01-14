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

import java.util.Date;

/**
 * 海关账册录入统计表
 *
 * @author ly
 * @date 2021-08-30 18:00:02
 */
@Data
@TableName("CUSTOMS_ACCOUNTING_STATISTICS")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "海关账册录入统计表")
@KeySequence(value = "SEQ_CUSTOMS_ACCOUNTING", clazz = Integer.class)

public class CustomsAccountingStatistics extends Model<CustomsAccountingStatistics> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.INPUT)
	@ApiModelProperty(value = "主键")
	@JsonProperty("fId")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
	private Long fId;

	/**
	 * 货主
	 */
	@ApiModelProperty(value = "货主")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
	private String consignNam;

	/**
	 * 货品名
	 */
	@ApiModelProperty(value = "货品名")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.BIGINT)
	private String cargoNamCustoms;

	/**
	 * 货类
	 */
	@ApiModelProperty(value = "货类")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.BIGINT)
	private String cargoKindNam;

	/**
	 * 入库日期
	 */
	@ApiModelProperty(value = "入库日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.DATE)
	private Date inDte;

	/**
	 * 入库毛重（kg）
	 */
	@ApiModelProperty(value = "入库毛重（kg）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.DOUBLE)
	private Double inRoughWet;

	/**
	 * 入库净重（kg）
	 */
	@ApiModelProperty(value = "入库净重（kg）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.DOUBLE)
	private Double inNetWet;

	/**
	 * 入库货品额（美元）
	 */
	@ApiModelProperty(value = "入库货品额（美元）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.DOUBLE)
	private Double inCargoAccount;

	/**
	 * 原产国（入库）
	 */
	@ApiModelProperty(value = "原产国（入库）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
	private String inOriginCountry;

	/**
	 * 最终目的地（入库）
	 */
	@ApiModelProperty(value = "最终目的地（入库）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
	private String inFinalDestination;

	/**
	 * 核注清单号（入库）
	 */
	@ApiModelProperty(value = "核注清单号（入库）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
	private String inCheckNum;

	/**
	 * 核对联（入库）
	 */
	@ApiModelProperty(value = "核对联（入库）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
	private String inNuclearCouplet;

	/**
	 * 报关单号（入库）
	 */
	@ApiModelProperty(value = "报关单号（入库）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
	private String inCustomsDeclarationNum;

	/**
	 * 识别代码（入库）
	 */
	@ApiModelProperty(value = "识别代码（入库）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
	private String inIdentificationCod;

	/**
	 * 货票号（入库）
	 */
	@ApiModelProperty(value = "货票号（入库）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
	private String inWaybillNum;

	/**
	 * 类别（进区）
	 */
	@ApiModelProperty(value = "类别（进区）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
	private String inCategory;

	/**
	 * 出库日期
	 */
	@ApiModelProperty(value = "出库日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.DATE)
	private Date outDte;

	/**
	 * 出库毛重（kg )
	 */
	@ApiModelProperty(value = "出库毛重（kg）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.DOUBLE)
	private Double outRoughWet;

	/**
	 * 出库净重（kg）
	 */
	@ApiModelProperty(value = "出库净重（kg）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.DOUBLE)
	private Double outNetWet;

	/**
	 * 出库货品额（美元）
	 */
	@ApiModelProperty(value = "出库货品额（美元）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.DOUBLE)
	private Double outCargoAccount;

	/**
	 * 原产国（出库）
	 */
	@ApiModelProperty(value = "原产国（出库）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
	private String outOriginCountry;

	/**
	 * 最终目的地（出库）
	 */
	@ApiModelProperty(value = "最终目的地（出库）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
	private String outFinalDestination;

	/**
	 * 核注清单号（出库）
	 */
	@ApiModelProperty(value = "核注清单号（出库）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
	private String outCheckNum;

	/**
	 * 核对联（出库）
	 */
	@ApiModelProperty(value = "核对联（出库）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
	private String outNuclearCouplet;

	/**
	 * 报关单号（出库）
	 */
	@ApiModelProperty(value = "报关单号（出库）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
	private String outCustomsDeclarationNum;

	/**
	 * 识别代码（出库）
	 */
	@ApiModelProperty(value = "识别代码（出库）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
	private String outIdentificationCod;

	/**
	 * 货票号（出库）
	 */
	@ApiModelProperty(value = "货票号（出库）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
	private String outWaybillNum;

	/**
	 * 类别（出区）
	 */
	@ApiModelProperty(value = "类别（出区）")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
	private String outCategory;

	/**
	 * 添加人
	 */
	@ApiModelProperty(value = "添加人")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
	private String operNam;

	/**
	 * 添加时间
	 */
	@ApiModelProperty(value = "添加时间")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.TIMESTAMP)
	private Date recordTim;

	/**
	 * 修改人
	 */
	@ApiModelProperty(value = "修改人")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
	private String updateNam;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.TIMESTAMP)
	private Date updateTim;

	/**
	 * 删除人
	 */
	@ApiModelProperty(value = "删除人")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
	private String delNam;

	/**
	 * 删除时间
	 */
	@ApiModelProperty(value = "删除时间")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.TIMESTAMP)
	private Date delTim;


}
