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
import com.newweb.mrli.ykport.baoshuisystem.utils.ExcelUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 出入库单表体
 *
 * @author zada
 * @date 2021-09-02 18:42:44
 */
@Data
@TableName("INOUT_YARD_BODY")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "出入库单表体")
@KeySequence(value = "SEQ_INOUT_YARD_BODY",clazz = Integer.class)
public class InoutYardBody extends Model<InoutYardBody> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value = "F_ID",type = IdType.INPUT)
	@ApiModelProperty(value="主键")
	@JsonProperty("fId")
    private Integer fId;

    /**
     * 表头主键
     */
    @ApiModelProperty(value="表头主键")
	@JsonProperty("hFid")
    private Integer hFid;

    /**
     * 合同主键
     */
    @ApiModelProperty(value="合同主键")
	@JsonProperty("cFid")
    private Long cFid;

    /**
     * 合同编号
     */
    @ApiModelProperty(value="合同编号")
	@ExcelUtil.ExcelAttribute(column = "1",name = "合同编号")
    private String contNo;

    /**
     * H.S（商品编码）
     */
    @ApiModelProperty(value="H.S（商品编码）")
	@ExcelUtil.ExcelAttribute(column = "2",name = "H·S（商品编码）")
    private String cargoNo;

    /**
     * 品名
     */
    @ApiModelProperty(value="品名")
	@ExcelUtil.ExcelAttribute(column = "3",name = "品名")
    private String cargoNam;

    /**
     * 规格
     */
    @ApiModelProperty(value="规格")
	@ExcelUtil.ExcelAttribute(column = "4",name = "规格")
    private String format;

    /**
     * 件数
     */
    @ApiModelProperty(value="件数")
	@ExcelUtil.ExcelAttribute(column = "5",name = "件数")
    private Integer pieceNum;

    /**
     * 桶数
     */
    @ApiModelProperty(value="桶数")
	@ExcelUtil.ExcelAttribute(column = "6",name = "件数/桶数")
    private Integer bucketNum;

    /**
     * 毛重
     */
    @ApiModelProperty(value="毛重")
	@ExcelUtil.ExcelAttribute(column = "7",name = "毛重")
    private Double roughWet;

    /**
     * 净重
     */
    @ApiModelProperty(value="净重")
	@ExcelUtil.ExcelAttribute(column = "8",name = "净重")
    private String netWet;

    /**
     * 单价
     */
    @ApiModelProperty(value="单价")
	@ExcelUtil.ExcelAttribute(column = "9",name = "单价")
    private Double unitPrice;

    /**
     * 单价类型
     */
    @ApiModelProperty(value="单价类型")
	@ExcelUtil.ExcelAttribute(column = "10",name = "单价类型")
    private String unitPriceKnd;

    /**
     * 总价
     */
    @ApiModelProperty(value="总价")
	@ExcelUtil.ExcelAttribute(column = "11",name = "总价")
    private Double totalPrice;

    /**
     * 单位
     */
    @ApiModelProperty(value="单位")
	@ExcelUtil.ExcelAttribute(column = "12",name = "价格单位")
    private String unit;

    /**
     * 运输工具
     */
    @ApiModelProperty(value="运输工具")
	@ExcelUtil.ExcelAttribute(column = "13",name = "运输工具")
    private String transportMeans;

    /**
     * 进口国别
     */
    @ApiModelProperty(value="进口国别")
	@ExcelUtil.ExcelAttribute(column = "14",name = "原产国")
    private String importCountry;

    /**
     * 目的国
     */
    @ApiModelProperty(value="目的国")
	@ExcelUtil.ExcelAttribute(column = "15",name = "目的国")
    private String distinationCountry;

    /**
     * 自重
     */
    @ApiModelProperty(value="自重")
	@ExcelUtil.ExcelAttribute(column = "16",name = "自重")
    private Double deadLoad;

    /**
     * 磅房编号
     */
    @ApiModelProperty(value="磅房编号")

    private String checkNo;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
	@ExcelUtil.ExcelAttribute(column = "30",name = "备注")
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

    /**
     * 是否需要插入衡重申请单(0:否,1:是)
     */
    @ApiModelProperty(value="是否需要插入衡重申请单(0:否,1:是)")
	@ExcelUtil.ExcelAttribute(column = "17",name = "是否插入衡重申请单")
    private String boundFlag;

    /**
     * 货类代码
     */
    @ApiModelProperty(value="货类代码")
	@ExcelUtil.ExcelAttribute(column = "18",name = "货类代码")
    private String cargoKindNo;

    /**
     * 货类名称
     */
    @ApiModelProperty(value="货类名称")
	@ExcelUtil.ExcelAttribute(column = "19",name = "货类")
    private String cargoKindNam;

    /**
     * 进出口
     */
    @ApiModelProperty(value="进出口")
	@ExcelUtil.ExcelAttribute(column = "20",name = "进出口")
    private Integer inoutId;

    /**
     * 内外贸
     */
    @ApiModelProperty(value="内外贸")
	@ExcelUtil.ExcelAttribute(column = "21",name = "内外贸")
    private Integer tradeId;

    /**
     * 提单号
     */
    @ApiModelProperty(value="提单号")
	@ExcelUtil.ExcelAttribute(column = "22",name = "提单号")
    private String billNo;

    /**
     * 上下限额
     */
//    @ApiModelProperty(value="上下限额")
//	@ExcelUtil.ExcelAttribute(column = "23",name = "上下限额")
//    private Double addMinRat;

    /**
     * 单位代码
     */
    @ApiModelProperty(value="单位代码")
	@ExcelUtil.ExcelAttribute(column = "24",name = "单位代码")
    private String unitCod;

    /**
     * 单位名称
     */
    @ApiModelProperty(value="单位名称")
	@ExcelUtil.ExcelAttribute(column = "25",name = "单位名称")
    private String unitNam;

	@ApiModelProperty(value="存放区域")
	@ExcelUtil.ExcelAttribute(column = "26",name = "存放区域")
	private String locate;
	@ApiModelProperty(value="进区报关单号")
	@ExcelUtil.ExcelAttribute(column = "27",name = "进区报关单号")
	private String inCustomsDeclaration;
	@ApiModelProperty(value="进区核注清单号")
	@ExcelUtil.ExcelAttribute(column = "28",name = "进区核注清单号")
	private String inCheckNum;
	@ApiModelProperty(value="出区核注清单号")
	@ExcelUtil.ExcelAttribute(column = "29",name = "出区核注清单号")
	private String outCheckNum;
	@ApiModelProperty(value="报关单号维护人")
	private String customsDeclarationUser;
	@ApiModelProperty(value="报关单号维护时间")
	private Date customsDeclarationTim;
	@ApiModelProperty(value="核注清单号维护人")
	private String checkNumUser;
	@ApiModelProperty(value="核注清单号维护时间")
	private Date checkNumTim;
	@ApiModelProperty(value="出库（0）/入库（1）")
	private String inOut;
	@ApiModelProperty(value="出区报关单号")
	private String outCustomsDeclaration;

	@TableField(exist = false)
	@ApiModelProperty(value="进区核注清单号FLG")
	private String inCheckNumFlg;

	@TableField(exist = false)
	@ApiModelProperty(value="船名")
	private String shipNam;

	@TableField(exist = false)
	@ApiModelProperty(value="报关单号状态")
	private String inCustomsDeclarationStatus;

	@ApiModelProperty(value="显示隐藏")
	private Integer showFlag;
//	/**
//	 * 科室队代码
//	 */
//	@ApiModelProperty(value="科室队代码")
//	@ExcelUtil.ExcelAttribute(column = "26",name = "科室队代码")
//	private String subunitCod;
//
//	/**
//	 * 科室队名称
//	 */
//	@ApiModelProperty(value="科室队名称")
//	@ExcelUtil.ExcelAttribute(column = "27",name = "科室队名称")
//	private String subunitNam;
//
//
//	/**
//	 * 结算方式代码
//	 */
//	@ApiModelProperty(value="结算方式代码")
//	@ExcelUtil.ExcelAttribute(column = "28",name = "结算方式代码")
//	private String feeWayId;
//
//	/**
//	 * 结算方式
//	 */
//	@ApiModelProperty(value="结算方式")
//	@ExcelUtil.ExcelAttribute(column = "29",name = "结算方式")
//	private String feeWayNam;
}
