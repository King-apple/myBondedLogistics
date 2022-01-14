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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.newweb.mrli.ykport.baoshuisystem.entity.Bcmcxtab;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * ${comments}
 *
 * @author pigx code generator
 * @date 2021-09-06 19:04:43
 */
@Data
@TableName("IN_STROGE_DETAILED")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "${comments}")
public class InStrogeDetail extends Model<InStrogeDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 单位代码
     */

    @ApiModelProperty(value="单位代码")
    private String unitCod;

    /**
     * 班次
     */
    @ApiModelProperty(value="班次")
    private String bz;

    /**
     * 入库日期
     */
    @ApiModelProperty(value="入库日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    private Date inStrogeDate;

    /**
     * 入库号
     */
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="入库号")
    private String bh;

    /**
     * 理货流水号
     */
    @ApiModelProperty(value="理货流水号")
    private String liushuihao;

    /**
     * 入库单号
     */
    @TableField(exist=false)
    @ApiModelProperty(value="入库单号")
    private String inoutYardNo;

    /**
     * 科室队代码
     */
    @ApiModelProperty(value="科室队代码")
    private String subunitCod;

    /**
     * 科室队名称
     */
    @ApiModelProperty(value="科室队名称")
    private String subunitNam;

    /**
     * 船号
     */
    @ApiModelProperty(value="船号")
    private String cbh;

    /**
     * 船名
     */
    @ApiModelProperty(value="船名")
    private String cm;

    /**
     * 提单号
     */
    @ApiModelProperty(value="提单号")
    private String tdh;

    /**
     * 进出口
     */
    @ApiModelProperty(value="进出口")
    private String jck;

    /**
     * 内外贸
     */
    @ApiModelProperty(value="内外贸")
    private String nwm;

    /**
     * 车次
     */
    @ApiModelProperty(value="车次")
    private String cbb;

    /**
     * 入库标志
     */
    @ApiModelProperty(value="入库标志")
    private String inStrogeFlag;

    /**
     * 入库方式
     */
    @ApiModelProperty(value="入库方式")
    private String inStrogeMethod;

    /**
     * 库场号
     */
    @ApiModelProperty(value="库场号")
    private String zykq;

    /**
     * 初次入库日期
     */
    @ApiModelProperty(value="初次入库日期")
    private Date firstInStrogeDate;

    /**
     * 库场名称
     */
    @ApiModelProperty(value="库场名称")
    private String strogeName;

    /**
     * 货位号
     */
    @ApiModelProperty(value="货位号")
    private String locationNo;

    /**
     * 代理货主代码
     */
    @ApiModelProperty(value="代理货主代码")
    private String agentCod;

    /**
     * 代理货主
     */
    @ApiModelProperty(value="代理货主")
    private String agentNam;

    /**
     * 港口货主代码
     */
    @ApiModelProperty(value="港口货主代码")
    private String cargoOwnerCode;

    /**
     * 港口货主
     */
    @ApiModelProperty(value="港口货主")
    private String cargoOwnerName;

    /**
     * 货号
     */
    @ApiModelProperty(value="货号")
    private String cargoNo;

    /**
     * 货类代码
     */
    @ApiModelProperty(value="货类代码")
    private String cargoKindCod;

    /**
     * 货类名称
     */
    @ApiModelProperty(value="货类名称")
    private String hl;

    /**
     * 货名代码
     */
    @ApiModelProperty(value="货名代码")
    private String hm;

    /**
     * 货名
     */
    @ApiModelProperty(value="货名")
    private String cargoNam;

    /**
     * 包装代码
     */
    @ApiModelProperty(value="包装代码")
    private String packingCode;

    /**
     * 包装
     */
    @ApiModelProperty(value="包装")
    private String cargoPackage;

    /**
     * 规格型号
     */
    @ApiModelProperty(value="规格型号")
    private String specification;

    /**
     * 件数
     */
    @ApiModelProperty(value="件数")
    private String jianshu;

    /**
     * 件重
     */
    @ApiModelProperty(value="件重")
    private String piecesWgt;

    /**
     * 体积
     */
    @ApiModelProperty(value="体积")
    private String volume;

    /**
     * 堆存费统计标志
     */
    @ApiModelProperty(value="堆存费统计标志")
    private String stackingFeeFlag;

    /**
     * 完标
     */
    @ApiModelProperty(value="完标")
    private String ifendId;

    /**
     * 操作员
     */
    @ApiModelProperty(value="操作员")
    private String operator;

    /**
     * 记录时间
     */
    @ApiModelProperty(value="记录时间")
    private Date recordTim;

    /**
     * 重量
     */
    @ApiModelProperty(value="重量")
    private Double hz;

	/**
	 * 合同编号
	 */
	@TableField(exist = false)
	@ApiModelProperty(value="合同编号")
	private String contractNo;

	@TableField(exist=false)
	@ApiModelProperty(value="出入库标志")
	private String ioyardId;

	/**
	 * 申请单位
	 */
	@ApiModelProperty(value="申请单位")
	private String sqdw;

	/**
	 * 明细编号
	 */
	@ApiModelProperty(value="明细编号")
	private String bcbh;

	/**
	 * 上图详细信息
	 */
	@TableField(exist=false)
	@ApiModelProperty(value="上图详细信息")
	private String bcmcxtabList;

	/**
	 * 是否预出库
	 */
	@TableField(exist=false)
	@ApiModelProperty(value="是否预出库")
	private String prepareFlag;


	/**
	 * 所有车数
	 */
	@ApiModelProperty(value="所有车数")
	private String bccount;

	/**
	 * 入库车数
	 */
	@ApiModelProperty(value="入库车数")
	private String btcount;

	/**
	 * 报关单号
	 */
	@ApiModelProperty(value="报关单号")
	private String inCustomsDeclaration;

	/**
	 * 货溢处理次数
	 */
	@ApiModelProperty(value="货溢处理次数（null：不可货溢；0：可货溢处理；1：货溢已处理不可再进行货溢处理，也不可再提交货溢短量审核）")
	@TableField(exist = false)
	private String overShortTimes;


	@ApiModelProperty(value="提单号")
	@TableField(exist = false)
	private String billNO;

	@ApiModelProperty(value="报关单号")
	@TableField(exist = false)
	private String customsDeclaration;
}
