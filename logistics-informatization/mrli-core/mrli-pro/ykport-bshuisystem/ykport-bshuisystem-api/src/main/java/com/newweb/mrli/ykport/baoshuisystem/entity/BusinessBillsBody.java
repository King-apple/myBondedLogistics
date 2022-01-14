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
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 业务单据体表
 *
 * @author 李洋
 * @date 2021-09-06 09:13:02
 */
@Data
@TableName("BUSINESS_BILLS_BODY")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "业务单据体表")
@KeySequence(value = "SEQ_BUSINESS_BILLS_BODY", clazz = Integer.class)
public class BusinessBillsBody extends Model<BusinessBillsBody> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="主键")
	@JsonProperty("fId")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.BIGINT)
    private Long fId;

    /**
     * 头表主键
     */
    @ApiModelProperty(value="头表主键")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.BIGINT)
    private Long hFid;

    /**
     * 付款编号
     */
    @ApiModelProperty(value="付款编号")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private String billCod;

    /**
     * 项目
     */
    @ApiModelProperty(value="项目")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private String itemNam;

    /**
     * 费用名称
     */
    @ApiModelProperty(value="费用名称")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private String feeNam;

    /**
     * 费率单位
     */
    @ApiModelProperty(value="费率单位")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private String feeUnit;

    /**
     * 费率值
     */
    @ApiModelProperty(value="费率值")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.DECIMAL)
    private BigDecimal feeRat;

    /**
     * 应收应付款
     */
    @ApiModelProperty(value="应收应付款")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.DECIMAL)
    private BigDecimal dueMoney;

    /**
     * 预收预付款
     */
    @ApiModelProperty(value="预收预付款")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.DECIMAL)
    private BigDecimal advinceMoney;

    /**
     * 金额
     */
    @ApiModelProperty(value="金额")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.DECIMAL)
    private BigDecimal money;

    /**
     * 税额
     */
    @ApiModelProperty(value="税额")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.DECIMAL)
    private BigDecimal taxMoney;

    /**
     * 价税合计
     */
    @ApiModelProperty(value="价税合计")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.DECIMAL)
    private BigDecimal totalMoney;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private String markTxt;

    /**
     * 添加人
     */
    @ApiModelProperty(value="添加人")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private String operNam;

    /**
     * 添加时间
     */
    @ApiModelProperty(value="添加时间")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.TIMESTAMP)
    private Date recordTim;

    /**
     * 修改人
     */
    @ApiModelProperty(value="修改人")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private String updateNam;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.TIMESTAMP)
    private Date updateTim;

    /**
     * 删除人
     */
    @ApiModelProperty(value="删除人")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private String delNam;

    /**
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
	@TableField(updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.TIMESTAMP)
    private Date delTim;
    //新增数量字段
	private  BigDecimal feeQuantity;

	//税率
	private  BigDecimal taxRate;

}
