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
import com.newweb.mrli.ykport.baoshuisystem.utils.ExcelUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 出入库明细表体表
 *
 * @author pigx code generator
 * @date 2021-09-09 15:02:10
 */
@Data
@TableName("IOYARD_LIST_BODY")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "出入库明细表体表")
@KeySequence(value = "SEQ_IOYARD_LIST_BODY",clazz = Long.class)
public class IoyardListBody extends Model<IoyardListBody> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="主键")
	@JsonProperty("fId")
	private Long fId;

    /**
     * 头表主键
     */
    @ApiModelProperty(value="头表主键")
	@JsonProperty("fFid")
    private Long fFid;

    /**
     * 单据编号
     */
    @ApiModelProperty(value="单据编号")
    private String ioyardListNo;

    /**
     * 入库日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    @ApiModelProperty(value="入库日期")
	@ExcelUtil.ExcelAttribute(name = "入库日期")
    private Date inYardDat;

    /**
     * 入库方式
     */
    @ApiModelProperty(value="入库方式")
//	@ExcelUtil.ExcelAttribute(name = "入库方式")
    private String inYardWay;

    /**
     * 货主
     */
    @ApiModelProperty(value="货主")
	@ExcelUtil.ExcelAttribute(name = "货主")
    private String consignNam;

    /**
     * 品名
     */
    @ApiModelProperty(value="品名")
	@ExcelUtil.ExcelAttribute(name = "品名")
    private String cargoNam;

    /**
     * 包装
     */
    @ApiModelProperty(value="包装")
	@ExcelUtil.ExcelAttribute(name = "包装")
    private String cPkgKindNam;

    /**
     * 堆存地点
     */
    @ApiModelProperty(value="堆存地点")
	@ExcelUtil.ExcelAttribute(name = "堆存地点")
    private String yardNam;

    /**
     * 入库量（件数）
     */
    @ApiModelProperty(value="入库量（件数）")
	@ExcelUtil.ExcelAttribute(name = "入库量(件数)")
    private Integer inPiecesNum;

    /**
     * 入库过磅量（吨）
     */
    @ApiModelProperty(value="入库过磅量（吨）")
	@ExcelUtil.ExcelAttribute(name = "入库过磅数量(吨)")
    private Double inWeightWgt;

    /**
     * 入库结算量（吨）
     */
    @ApiModelProperty(value="入库结算量（吨）")
	@ExcelUtil.ExcelAttribute(name = "入库结算数量(吨)")
    private Double inSettleWgt;

    /**
     * 入库磅单号
     */
    @ApiModelProperty(value="入库磅单号")
    private String inYardNo;

    /**
     * 出库日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    @ApiModelProperty(value="出库日期")
	@ExcelUtil.ExcelAttribute(name = "出库日期")
    private Date outYardDat;

    /**
     * 出库方式
     */
    @ApiModelProperty(value="出库方式")
//	@ExcelUtil.ExcelAttribute(name = "出库方式")
    private String outYardWay;

    /**
     * 出库量（件）
     */
    @ApiModelProperty(value="出库量（件）")
	@ExcelUtil.ExcelAttribute(name = "出库量(件数)")
    private Integer outPiecesNum;

    /**
     * 出库过磅量（吨）
     */
    @ApiModelProperty(value="出库过磅量（吨）")
	@ExcelUtil.ExcelAttribute(name = "出库过磅数量(吨)")
    private Double outWeightWgt;

    /**
     * 出库结算数量（吨）
     */
    @ApiModelProperty(value="出库结算数量（吨）")
	@ExcelUtil.ExcelAttribute(name = "出库结算数量(吨)")
    private Double outSettleWgt;

    /**
     * 出库磅单号
     */
    @ApiModelProperty(value="出库磅单号")
    private String outYardNo;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
	@ExcelUtil.ExcelAttribute(name = "备注")
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

	@ApiModelProperty(value="入库船名")
	private String inShipNam;
	@ApiModelProperty(value="出库船名")
	private String outShipNam;
}
