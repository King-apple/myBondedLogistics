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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * ${comments}
 *	单船登记帐
 * @author pigx code generator
 * @date 2021-10-23 15:55:20
 */
@Data
@TableName("ykport0.SING_SHIP")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "${comments}")
public class SingShip extends Model<SingShip> {

    private static final long serialVersionUID = 1L;

    /**
     * 船号
     */
    @ApiModelProperty(value="船号")
    private Integer shipNo;

    /**
     * 记录时间
     */
    @ApiModelProperty(value="记录时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    private Date recordTim;

    /**
     * 完工时间
     */
    @ApiModelProperty(value="完工时间")
    private Date endTim;

    /**
     * 货运员代码
     */
    @ApiModelProperty(value="货运员代码")
    private String cargoManCod;

    /**
     * 货运员
     */
    @ApiModelProperty(value="货运员")
    private String cargoMan;

    /**
     * 货运员备注
     */
    @ApiModelProperty(value="货运员备注")
    private String cargoTxt;

    /**
     * 理货员备注
     */
    @ApiModelProperty(value="理货员备注")
    private String tallyTxt;

    /**
     * 理货员
     */
    @ApiModelProperty(value="理货员")
    private String tallyMan;

    /**
     * 船名
     */
    @ApiModelProperty(value="船名")
    private String shipNam;

    /**
     * 到港时间
     */
    @ApiModelProperty(value="到港时间")
    private Date toPortTim;

    /**
     * 来港
     */
    @ApiModelProperty(value="来港")
    private String fromPortNam;

    /**
     * 到港
     */
    @ApiModelProperty(value="到港")
    private String toPortNam;

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
     * 操作员
     */
    @ApiModelProperty(value="操作员")
    private String operNam;

    /**
     * 最终修改时间
     */
    @ApiModelProperty(value="最终修改时间")
    private Date updateTim;

    /**
     * 最终修改人
     */
    @ApiModelProperty(value="最终修改人")
    private String updateNam;

    /**
     * 理货员代码
     */
    @ApiModelProperty(value="理货员代码")
    private String tallyManCod;

    /**
     * 单位代码
     */
    @ApiModelProperty(value="单位代码")
    private String unitCod;

    /**
     * 单位名称
     */
    @ApiModelProperty(value="单位名称")
    private String unitNam;

    /**
     * 合同统计（0否1是）
     */
    @ApiModelProperty(value="合同统计（0否1是）")
    private String countId;

}
