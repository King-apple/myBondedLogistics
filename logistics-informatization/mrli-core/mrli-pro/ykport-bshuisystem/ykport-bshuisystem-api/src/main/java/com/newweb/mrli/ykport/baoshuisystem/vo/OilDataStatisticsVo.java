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

package com.newweb.mrli.ykport.baoshuisystem.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.newweb.mrli.ykport.baoshuisystem.entity.SqlCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.JdbcType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 油品录入数据统计表
 *
 * @author Tara
 * @date 2021-10-19 09:48:47
 */
@Data
@TableName("OIL_DATA_STATISTICS")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "油品录入数据统计表")
@KeySequence(value = "SEQ_oil_data_statistics",clazz = Integer.class)
public class OilDataStatisticsVo extends Model<OilDataStatisticsVo> {

    private static final long serialVersionUID = 1L;

    // 入库报送日期范围
    private String[] inAddDteStr;
    // 出库报送日期范围
	private String[] outAddDteStr;

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value="主键")
	@JsonProperty("fId")
    private Long fId;

    /**
     * 账册号
     */
    @ApiModelProperty(value="账册号")
	@TableField(condition = SqlCondition.like)
    private String inAccountNum;

    /**
     * 经营单位
     */
    @ApiModelProperty(value="经营单位")
	@TableField(condition = SqlCondition.like)
    private String inOperationUnit;

    /**
     * 代码
     */
    @ApiModelProperty(value="代码")
    private String inOperationUnitCod;

    /**
     * 更新日期
     */
    @ApiModelProperty(value="更新日期")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private Date inUpdateDte;

    /**
     * 油品类型
     */
    @ApiModelProperty(value="油品类型")
    private String oilKnd;

    /**
     * 登录账号
     */
    @ApiModelProperty(value="登录账号")
    private String loginAccount;

    /**
     * 入库报送日期
     */
    @ApiModelProperty(value="入库报送日期")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private Date inAddDte;

    /**
     * 货主单位
     */
    @ApiModelProperty(value="货主单位")
	@TableField(condition = SqlCondition.like)
    private String inConsignNam;

    /**
     * 商品名称
     */
    @ApiModelProperty(value="商品名称")
	@TableField(condition = SqlCondition.like)
    private String inCargoNam;

    /**
     * 入库方式
     */
    @ApiModelProperty(value="入库方式")
    private String inType;

    /**
     * 提单号
     */
    @ApiModelProperty(value="提单号")
    private String inBlno;

    /**
     * 储罐名称
     */
    @ApiModelProperty(value="储罐名称")
    private String inStorageTank;

    /**
     * 计划数量（千克）
     */
    @ApiModelProperty(value="计划数量（千克）")
    private Double inQuantityPlan;

    /**
     * 密度标准
     */
    @ApiModelProperty(value="密度标准")
    private String inDensityStandard;

    /**
     * 密度值
     */
    @ApiModelProperty(value="密度值")
    private Double inDensity;

    /**
     * 计划开始时间
     */
    @ApiModelProperty(value="计划开始时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private Date inStartTim;

    /**
     * 计划结束时间
     */
    @ApiModelProperty(value="计划结束时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private Date inEndTim;

    /**
     * 进仓确认报送时间
     */
    @ApiModelProperty(value="进仓确认报送时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private Date inConfirmTim;

    /**
     * 实际数量（千克）
     */
    @ApiModelProperty(value="实际数量（千克）")
    private Double inQuantityReal;

    /**
     * 进仓报关单号
     */
    @ApiModelProperty(value="进仓报关单号")
    private String inCustomsDeclarationNum;

    /**
     * 账册号
     */
    @ApiModelProperty(value="账册号")
    private String outAccountNum;

    /**
     * 经营单位
     */
    @ApiModelProperty(value="经营单位")
    private String outOperationUnit;

    /**
     * 代码
     */
    @ApiModelProperty(value="代码")
    private String outOperationUnitCod;

    /**
     * 更新日期
     */
    @ApiModelProperty(value="更新日期")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private Date outUpdateDte;

    /**
     * 出库报送日期
     */
    @ApiModelProperty(value="出库报送日期")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private Date outAddDte;

    /**
     * 货主单位
     */
    @ApiModelProperty(value="货主单位")
    private String outConsignNam;

    /**
     * 商品名称
     */
    @ApiModelProperty(value="商品名称")
    private String outCargoNam;

    /**
     * 出库方式
     */
    @ApiModelProperty(value="出库方式")
    private String outType;

    /**
     * 提单号
     */
    @ApiModelProperty(value="提单号")
    private String outBlno;

    /**
     * 储罐名称
     */
    @ApiModelProperty(value="储罐名称")
    private String outStorageTank;

    /**
     * 计划数量（千克）
     */
    @ApiModelProperty(value="计划数量（千克）")
    private Double outQuantityPlan;

    /**
     * 密度标准
     */
    @ApiModelProperty(value="密度标准")
    private String outDensityStandard;

    /**
     * 密度值
     */
    @ApiModelProperty(value="密度值")
    private Double outDensity;

    /**
     * 计划开始时间
     */
    @ApiModelProperty(value="计划开始时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private Date outStartTim;

    /**
     * 计划结束时间
     */
    @ApiModelProperty(value="计划结束时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private Date outEndTim;

    /**
     * 出仓确认报送时间
     */
    @ApiModelProperty(value="出仓确认报送时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private Date outConfirmTim;

    /**
     * 实际数量（千克）
     */
    @ApiModelProperty(value="实际数量（千克）")
    private Double outQuantityReal;

    /**
     * 出仓报关单号
     */
    @ApiModelProperty(value="出仓报关单号")
    private String outCustomsDeclarationNum;

    /**
     * 库存情况
     */
    @ApiModelProperty(value="库存情况")
    private Double inventory;

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
	@ApiModelProperty(value="入库备注")
	private String inMarkTxt;
	@ApiModelProperty(value="出库备注")
	private String outMarkTxt;
}
