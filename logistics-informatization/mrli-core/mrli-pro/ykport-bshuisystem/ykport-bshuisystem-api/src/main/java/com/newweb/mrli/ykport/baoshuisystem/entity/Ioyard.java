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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 货物出入库表
 *
 * @author pigx code generator
 * @date 2021-09-06 19:35:20
 */
@Data
@TableName("IOYARD")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "货物出入库表")
@KeySequence(value = "SEQ_IOYARD",clazz = Integer.class)
public class Ioyard extends Model<Ioyard> {

    private static final long serialVersionUID = 1L;

    /**
     * 单位代码
     */

    @ApiModelProperty(value="单位代码")
    private String unitCod;

    /**
     * 出入库日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    @ApiModelProperty(value="出入库日期")
    private Date ioyardDte;

    /**
     * 班次
     */
    @ApiModelProperty(value="班次")
    private String classId;

    /**
     * 出入库号
     */

    @ApiModelProperty(value="出入库号")
    private String ioyardNo;

    /**
     * 理货流水号
     */
    @ApiModelProperty(value="理货流水号")
    private Long tallySeqNo;

    /**
     * 出入库单号
     */
	@TableId(value = "INOUT_YARD_NO",type = IdType.INPUT)
    @ApiModelProperty(value="出入库单号")
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
    private Long shipNo;

    /**
     * 船名
     */
    @ApiModelProperty(value="船名")
    private String shipNam;

    /**
     * 提单号
     */
    @ApiModelProperty(value="提单号")
    private String billNo;

    /**
     * 进口船号
     */
    @ApiModelProperty(value="进口船号")
    private Long inShipNo;

    /**
     * 进口船名
     */
    @ApiModelProperty(value="进口船名")
    private String inShipNam;

    /**
     * 进出口
     */
    @ApiModelProperty(value="进出口")
    private Integer inoutId;

    /**
     * 内外贸
     */
    @ApiModelProperty(value="内外贸")
    private Integer tradeId;

    /**
     * 车次
     */
    @ApiModelProperty(value="车次")
    private Integer trainNo;

    /**
     * 车皮号
     */
    @ApiModelProperty(value="车皮号")
    private String trainCod;

    /**
     * 出入库标志
     */
    @ApiModelProperty(value="出入库标志")
    private Integer ioyardId;

    /**
     * 入库方式
     */
    @ApiModelProperty(value="入库方式")
    private Integer inYardWay;

    /**
     * 入港方式
     */
    @ApiModelProperty(value="入港方式")
    private Integer inPortWay;

    /**
     * 初次入库日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    @ApiModelProperty(value="初次入库日期")
    private Date firstInDte;

    /**
     * 出库方式
     */
    @ApiModelProperty(value="出库方式")
    private Integer outYardWay;

    /**
     * 出港方式
     */
    @ApiModelProperty(value="出港方式")
    private Integer outPortWay;

    /**
     * 库场号
     */
    @ApiModelProperty(value="库场号")
    private String yardNo;

    /**
     * 库场名称
     */
    @ApiModelProperty(value="库场名称")
    private String yardNam;

    /**
     * 货位号
     */
    @ApiModelProperty(value="货位号")
    private String locNo;

    /**
     * 代理货主代码
     */
    @ApiModelProperty(value="代理货主代码")
    private String consignCod;

    /**
     * 代理货主
     */
    @ApiModelProperty(value="代理货主")
    private String consignNam;

    /**
     * 港口货主代码
     */
    @ApiModelProperty(value="港口货主代码")
    private String shipperCod;

    /**
     * 港口货主
     */
    @ApiModelProperty(value="港口货主")
    private String shipperDoc;

    /**
     * 货号
     */
    @ApiModelProperty(value="货号")
    private Long cargoNo;

    /**
     * 货类代码
     */
    @ApiModelProperty(value="货类代码")
    private String cargoKindCod;

    /**
     * 货类名称
     */
    @ApiModelProperty(value="货类名称")
    private String cargoKindNam;

    /**
     * 货名代码
     */
    @ApiModelProperty(value="货名代码")
    private String cargoNamCod;

    /**
     * 货名
     */
    @ApiModelProperty(value="货名")
    private String cargoNam;

    /**
     * 包装代码
     */
    @ApiModelProperty(value="包装代码")
    private String pkgKindCod;

    /**
     * 包装
     */
    @ApiModelProperty(value="包装")
    private String cPkgKindNam;

    /**
     * 规格型号
     */
    @ApiModelProperty(value="规格型号")
    private String format;

    /**
     * 件数
     */
    @ApiModelProperty(value="件数")
    private Integer piecesNum;

    /**
     * 件重
     */
    @ApiModelProperty(value="件重")
    private Double piecesWgt;

    /**
     * 重量
     */
    @ApiModelProperty(value="重量")
    private Double weightWgt;

    /**
     * 体积
     */
    @ApiModelProperty(value="体积")
    private Double cargoVol;

    /**
     * 原库场号
     */
    @ApiModelProperty(value="原库场号")
    private String oldYardNo;

    /**
     * 原库场名
     */
    @ApiModelProperty(value="原库场名")
    private String oldYardNam;

    /**
     * 原货位
     */
    @ApiModelProperty(value="原货位")
    private String oldLocNo;

    /**
     * 原包装代码
     */
    @ApiModelProperty(value="原包装代码")
    private String oldPkgKindCod;

    /**
     * 原包装
     */
    @ApiModelProperty(value="原包装")
    private String oldCPkgKindNam;

    /**
     * 原规格
     */
    @ApiModelProperty(value="原规格")
    private String oldFormat;

    /**
     * 原件数
     */
    @ApiModelProperty(value="原件数")
    private Integer oldPiecesNum;

    /**
     * 原件重
     */
    @ApiModelProperty(value="原件重")
    private Double oldPiecesWgt;

    /**
     * 原吨数
     */
    @ApiModelProperty(value="原吨数")
    private Double oldWeightWgt;

    /**
     * 原体积
     */
    @ApiModelProperty(value="原体积")
    private Double oldCargoVol;

    /**
     * 堆存费统计标志
     */
    @ApiModelProperty(value="堆存费统计标志")
    private String feeId;

    /**
     * 完标
     */
    @ApiModelProperty(value="完标")
    private Integer ifendId;

    /**
     * 完工时间
     */
    @ApiModelProperty(value="完工时间")
    private Date endTim;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remarkTxt;

    /**
     * 操作员
     */
    @ApiModelProperty(value="操作员")
    private String operNam;

    /**
     * 记录时间
     */
    @ApiModelProperty(value="记录时间")
    private Date recordTim;

    /**
     * proveNo
     */
    @ApiModelProperty(value="proveNo")
    private String proveNo;

    /**
     * 过户单号
     */
    @ApiModelProperty(value="过户单号")
    private String inProveNo;

    /**
     * changeId
     */
    @ApiModelProperty(value="changeId")
    private Double changeId;

    /**
     * outTrainCod
     */
    @ApiModelProperty(value="outTrainCod")
    private String outTrainCod;

    /**
     * inTrainNo
     */
    @ApiModelProperty(value="inTrainNo")
    private Double inTrainNo;

    /**
     * inTrainCod
     */
    @ApiModelProperty(value="inTrainCod")
    private String inTrainCod;

    /**
     * 车站电报呼号
     */
    @ApiModelProperty(value="车站电报呼号")
    private String teleNo;

    /**
     * 车站省略名
     */
    @ApiModelProperty(value="车站省略名")
    private String sanothNam;

    /**
     * 站名
     */
    @ApiModelProperty(value="站名")
    private String stationNam;

    /**
     * 合同号
     */
    @ApiModelProperty(value="合同号")
    private String contNo;

    /**
     * inYardNo
     */
    @ApiModelProperty(value="inYardNo")
    private String inYardNo;

    /**
     * 入库进出口标志
     */
    @ApiModelProperty(value="入库进出口标志")
    private Integer inInoutId;

    /**
     * 入库内外贸标志
     */
    @ApiModelProperty(value="入库内外贸标志")
    private Integer inTradeId;

    /**
     * inContNo
     */
    @ApiModelProperty(value="inContNo")
    private String inContNo;

    /**
     * inBillNo
     */
    @ApiModelProperty(value="inBillNo")
    private String inBillNo;

    /**
     * 冲账标志
     */
    @ApiModelProperty(value="冲账标志")
    private String washId;

    /**
     * modiPortTxt
     */
    @ApiModelProperty(value="modiPortTxt")
    private String modiPortTxt;

    /**
     * carNo
     */
    @ApiModelProperty(value="carNo")
    private String carNo;

    /**
     * steelNo
     */
    @ApiModelProperty(value="steelNo")
    private String steelNo;

    /**
     * colorId
     */
    @ApiModelProperty(value="colorId")
    private String colorId;

    /**
     * material
     */
    @ApiModelProperty(value="material")
    private String material;

    /**
     * tradeContNo
     */
    @ApiModelProperty(value="tradeContNo")
    private String tradeContNo;

    /**
     * 卷板相关
     */
    @ApiModelProperty(value="卷板相关")
    private String steelSeqNo;

    /**
     * jfDteIn
     */
    @ApiModelProperty(value="jfDteIn")
    private Date jfDteIn;

    /**
     * jfDteOut
     */
    @ApiModelProperty(value="jfDteOut")
    private Date jfDteOut;

	/**
	 * 是否生成出入库明细
	 */
	@ApiModelProperty(value="detailFlag")
	private Integer detailFlag;

	/**
	 * gCarId
	 */
	@ApiModelProperty(value="params")
	@TableField(exist = false)
	private Map<String,Object> params;

	/**
	 * 上次结算日期
	 */
	@ApiModelProperty(value="上次结算日期")
	private Date settlementDtePre;

	/**
	 * 本次结算日期
	 */
	@ApiModelProperty(value="本次结算日期")
	private Date settlementDteLater;

	/**
	 * 预出库标志
	 */
	@ApiModelProperty(value="预出库标志")
	private Integer prepareOutyardFlag;

	/**
	 * 剩余量
	 */
	@ApiModelProperty(value="剩余量")
	private BigDecimal surplusWeight;

	/**
	 * 剩余件数
	 */
	@ApiModelProperty(value="剩余件数")
	private Integer surplusPieceNum;

	/**
	 * 是否溢短（0或null：否；1：审核中；2：审核通过；3审核失败）
	 */
	@ApiModelProperty(value="是否溢短（0或null：否；1：审核中；2：审核通过；3审核失败）")
	private String overShortFlag;

	/**
	 * 报关单号
	 */
	@ApiModelProperty(value="报关单号")
	private String customsDeclaration;

	/**
	 * 是否点击查询（0:否,初始加载;1:是）
	 */
	@ApiModelProperty(value="是否点击查询（0:否,初始加载;1:是）")
	@TableField(exist = false)
	private String searchValue;
	/**
	 * 审核类型货溢处理次数
	 */
	@ApiModelProperty(value="审核类型（0：货溢；1：短量）")
	private String overShortType;
	/**
	 * 货溢处理次数
	 */
	@ApiModelProperty(value="货溢处理次数（null：不可货溢；0：可货溢处理；1：货溢已处理不可再进行货溢处理，也不可再提交货溢短量审核）")
	private String overShortTimes;
}
