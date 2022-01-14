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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 电子货位数据表
 *
 * @author pigx code generator
 * @date 2021-09-17 15:39:11
 */
@Data
@TableName("YARD_INFO")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "电子货位数据表")
@KeySequence(value = "seq_yard_info",clazz = Integer.class)
public class YardInfo extends Model<YardInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value="主键")
	@JsonProperty("fId")
    private Long fId;

    /**
     * 入库单编号
     */
    @ApiModelProperty(value="入库单编号")
    private String inYardNo;

	/**
	 * 货票编号
	 */
	@ApiModelProperty(value="货票编号")
	private String bh;

    /**
     * 货主
     */
    @ApiModelProperty(value="货主")
    private String consignNam;

    /**
     * 货名
     */
    @ApiModelProperty(value="货名")
    private String cargoNam;

    /**
     * 船名
     */
    @ApiModelProperty(value="船名")
    private String shipNam;

    /**
     * 入库件数
     */
    @ApiModelProperty(value="入库件数")
    private Integer inPiecesNum;

    /**
     * 入库重量
     */
    @ApiModelProperty(value="入库重量")
    private Double inWeightWgt;

    /**
     * 入库日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    @ApiModelProperty(value="入库日期")
    private Date inYardDat;

    /**
     * 入库结算日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    @ApiModelProperty(value="入库结算日期")
    private Date inSettleDat;

    /**
     * 出库件数
     */
    @ApiModelProperty(value="出库件数")
    private Integer outPiecesNum;

    /**
     * 出库重量
     */
    @ApiModelProperty(value="出库重量")
    private Double outWeightWgt;

    /**
     * 剩余件数
     */
    @ApiModelProperty(value="剩余件数")
    private Integer surplusPiecesNum;

    /**
     * 剩余重量
     */
    @ApiModelProperty(value="剩余重量")
    private Double surplusWeightWgt;

    /**
     * 堆存地点
     */
    @ApiModelProperty(value="堆存地点")
    private String yardNam;

    /**
     * 库区
     */
    @ApiModelProperty(value="库区")
    private String areaNam;

    /**
     * 起点
     */
    @ApiModelProperty(value="起点")
    private Integer areaStart;

    /**
     * 终点
     */
    @ApiModelProperty(value="终点")
    private Integer areaEnd;

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

}
