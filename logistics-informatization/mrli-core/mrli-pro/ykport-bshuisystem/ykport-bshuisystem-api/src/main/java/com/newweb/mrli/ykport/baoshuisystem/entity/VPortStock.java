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
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * 实时港存信息查询（理货）
 *
 * @author pigx code generator
 * @date 2021-10-23 17:21:47
 */
@Data
@TableName("ykport0.V_PORT_STOCK")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "实时港存信息查询（理货）")
public class VPortStock extends Model<VPortStock> {

    private static final long serialVersionUID = 1L;

    /**
     * unitCod
     */
    @ApiModelProperty(value="unitCod")
    private String unitCod;

    /**
     * ioyardDte
     */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    @ApiModelProperty(value="ioyardDte")
    private Date ioyardDte;

    /**
     * inoutYardNo
     */
    @ApiModelProperty(value="inoutYardNo")
    private String inoutYardNo;

    /**
     * subunitCod
     */
    @ApiModelProperty(value="subunitCod")
    private String subunitCod;

    /**
     * subunitNam
     */
    @ApiModelProperty(value="subunitNam")
    private String subunitNam;

    /**
     * billNo
     */
    @ApiModelProperty(value="billNo")
    private String billNo;

    /**
     * inShipNo
     */
    @ApiModelProperty(value="inShipNo")
    private String inShipNo;

    /**
     * inShipNam
     */
    @ApiModelProperty(value="inShipNam")
    private String inShipNam;

    /**
     * inoutId
     */
    @ApiModelProperty(value="inoutId")
    private String inoutId;

    /**
     * tradeId
     */
    @ApiModelProperty(value="tradeId")
    private String tradeId;

    /**
     * trainNo
     */
    @ApiModelProperty(value="trainNo")
    private String trainNo;

    /**
     * trainCod
     */
    @ApiModelProperty(value="trainCod")
    private String trainCod;

    /**
     * inYardWay
     */
    @ApiModelProperty(value="inYardWay")
    private String inYardWay;

    /**
     * inPortWay
     */
    @ApiModelProperty(value="inPortWay")
    private String inPortWay;

    /**
     * firstInDte
     */
    @ApiModelProperty(value="firstInDte")
    private Date firstInDte;

    /**
     * yardNo
     */
    @ApiModelProperty(value="yardNo")
    private String yardNo;

    /**
     * yardNam
     */
    @ApiModelProperty(value="yardNam")
    private String yardNam;

    /**
     * locNo
     */
    @ApiModelProperty(value="locNo")
    private String locNo;

    /**
     * consignCod
     */
    @ApiModelProperty(value="consignCod")
    private String consignCod;

    /**
     * consignNam
     */
    @ApiModelProperty(value="consignNam")
    private String consignNam;

    /**
     * shipperCod
     */
    @ApiModelProperty(value="shipperCod")
    private String shipperCod;

    /**
     * shipperDoc
     */
    @ApiModelProperty(value="shipperDoc")
    private String shipperDoc;

    /**
     * cargoNo
     */
    @ApiModelProperty(value="cargoNo")
    private String cargoNo;

    /**
     * cargoKindCod
     */
    @ApiModelProperty(value="cargoKindCod")
    private String cargoKindCod;

    /**
     * cargoKindNam
     */
    @ApiModelProperty(value="cargoKindNam")
    private String cargoKindNam;

    /**
     * cargoNamCod
     */
    @ApiModelProperty(value="cargoNamCod")
    private String cargoNamCod;

    /**
     * cargoNam
     */
    @ApiModelProperty(value="cargoNam")
    private String cargoNam;

    /**
     * pkgKindCod
     */
    @ApiModelProperty(value="pkgKindCod")
    private String pkgKindCod;

    /**
     * cPkgKindNam
     */
    @ApiModelProperty(value="cPkgKindNam")
    private String cPkgKindNam;

    /**
     * format
     */
    @ApiModelProperty(value="format")
    private String format;

    /**
     * piecesNum
     */
    @ApiModelProperty(value="piecesNum")
    private String piecesNum;

    /**
     * piecesWgt
     */
    @ApiModelProperty(value="piecesWgt")
    private String piecesWgt;

    /**
     * weightWgt
     */
    @ApiModelProperty(value="weightWgt")
    private String weightWgt;

    /**
     * cargoVol
     */
    @ApiModelProperty(value="cargoVol")
    private String cargoVol;

    /**
     * remarkTxt
     */
    @ApiModelProperty(value="remarkTxt")
    private String remarkTxt;

    /**
     * inProveNo
     */
    @ApiModelProperty(value="inProveNo")
    private String inProveNo;

    /**
     * inYardNo
     */
    @ApiModelProperty(value="inYardNo")
    private String inYardNo;

    /**
     * inContNo
     */
    @ApiModelProperty(value="inContNo")
    private String inContNo;

	/**
	 * 其他参数
	 */
	@TableField(exist = false)
	@ApiModelProperty(value="其他参数")
	private Map<String,Object> params;

}
