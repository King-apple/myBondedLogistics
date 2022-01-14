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
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 港存动态（局）
 *
 * @author pigx code generator
 * @date 2021-10-19 18:28:44
 */
@Data
@TableName("ykport0.M_STOR_STATUS_ALL")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "公司货物动态")
public class MStoreStatus extends Model<MStoreStatus> {

    private static final long serialVersionUID = 1L;

    /**
     * 单位代码
     */
    @ApiModelProperty(value="单位代码")
	@ExcelUtil.ExcelAttribute(column = "1",name = "单位代码")
	private String unitCod;

    /**
     * 单位名称
     */
    @ApiModelProperty(value="单位名称")
	@ExcelUtil.ExcelAttribute(column = "2",name = "单位名称")
	private String unitNam;


    /**
     * 保管单位代码
     */
    @ApiModelProperty(value="保管单位代码")
	@ExcelUtil.ExcelAttribute(column = "4",name = "保管单位代码")
	private String subunitCod;

    /**
     * 保管单位
     */
    @ApiModelProperty(value="保管单位")
	@ExcelUtil.ExcelAttribute(column = "5",name = "保管单位")
	private String subunitNam;

    /**
     * 船号
     */
    @ApiModelProperty(value="船号")
	@ExcelUtil.ExcelAttribute(column = "6",name = "船号")
	private Integer shipNo;

    /**
     * 船名
     */
    @ApiModelProperty(value="船名")
	@ExcelUtil.ExcelAttribute(column = "7",name = "船名")
	private String shipNam;

    /**
     * 进出口
     */
    @ApiModelProperty(value="进出口")
	private Integer inoutId;

    /**
     * 进出口 
     */
    @ApiModelProperty(value="进出口 ")
	@ExcelUtil.ExcelAttribute(column = "8",name = "进出口")
	private String inoutNam;

    /**
     * 内外贸
     */
    @ApiModelProperty(value="内外贸")
    private Integer tradeId;

    /**
     * 内外贸
     */
    @ApiModelProperty(value="内外贸")
	@ExcelUtil.ExcelAttribute(column = "9",name = "内外贸")
    private String tradeNam;

    /**
     * 入港方式
     */
    @ApiModelProperty(value="入港方式")
    private Integer inPortWay;

    /**
     * 入港方式
     */
    @ApiModelProperty(value="入港方式")
	@ExcelUtil.ExcelAttribute(column = "10",name = "进出港方式")
    private String inPortWayNam;

    /**
     * 库场号
     */
    @ApiModelProperty(value="库场号")
	@ExcelUtil.ExcelAttribute(column = "15",name = "库场号")
    private String yardNo;

    /**
     * 库场名
     */
    @ApiModelProperty(value="库场名")
	@ExcelUtil.ExcelAttribute(column = "16",name = "库场名")
    private String yardNam;

    /**
     * 代理货主代码
     */
    @ApiModelProperty(value="代理货主代码")
	@ExcelUtil.ExcelAttribute(column = "11",name = "代理货主代码")
    private String consignCod;

    /**
     * 代理货主
     */
    @ApiModelProperty(value="代理货主")
	@ExcelUtil.ExcelAttribute(column = "12",name = "代理货主")
    private String consignNam;

    /**
     * 港口货主代码
     */
    @ApiModelProperty(value="港口货主代码")
	@ExcelUtil.ExcelAttribute(column = "13",name = "港口货主代码")
    private String shipperCod;

    /**
     * 港口货主
     */
    @ApiModelProperty(value="港口货主")
	@ExcelUtil.ExcelAttribute(column = "14",name = "港口货主")
    private String shipperDoc;

    /**
     * 货号
     */
    @ApiModelProperty(value="货号")
    private Integer cargoNo;

    /**
     * 货类代码
     */
    @ApiModelProperty(value="货类代码")
    private String cargoKindCod;

    /**
     * 货类名称
     */
    @ApiModelProperty(value="货类名称")
	@ExcelUtil.ExcelAttribute(column = "17",name = "货类")
    private String cargoKindNam;

    /**
     * 货名代码
     */
    @ApiModelProperty(value="货名代码")
	@ExcelUtil.ExcelAttribute(column = "18",name = "货名代码")
    private String cargoNamCod;

    /**
     * 货名
     */
    @ApiModelProperty(value="货名")
	@ExcelUtil.ExcelAttribute(column = "19",name = "货名")
    private String cargoNam;

    /**
     * 件数
     */
    @ApiModelProperty(value="件数")
	@ExcelUtil.ExcelAttribute(column = "20",name = "件数")
    private Integer piecesNum;

    /**
     * 重量
     */
    @ApiModelProperty(value="重量")
	@ExcelUtil.ExcelAttribute(column = "21",name = "重量")
    private Integer weightWgt;

    /**
     * 操作员
     */
    @ApiModelProperty(value="操作员")
    private String operNam;

    /**
     * 记录时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    @ApiModelProperty(value="记录时间")
	@ExcelUtil.ExcelAttribute(column = "3",name = "日期")
    private Date recordTim;

}
