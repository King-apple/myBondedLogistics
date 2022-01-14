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
 * 衡重申请单
 *
 * @author pigx code generator
 * @date 2021-09-06 13:35:12
 */
@Data
@TableName("CHECK_WEIGHT")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "衡重申请单")
public class CheckWeight extends Model<CheckWeight> {

    private static final long serialVersionUID = 1L;

    /**
     * 衡重单日期     +sysdate
     */

    @ApiModelProperty(value="衡重单日期")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
	@JsonFormat(pattern ="yyyy-MM-dd" )
    private Date recordDte;

    /**
     * 衡重单号     +无
     */
	@TableId(value = "CHECK_NO",type = IdType.INPUT)
    @ApiModelProperty(value="衡重单号")
    private String checkNo;

    /**
     * 委托单号      +'1'
     */
    @ApiModelProperty(value="委托单号")
    private String trustNo;

    /**
     * 货号    +
     */
    @ApiModelProperty(value="货号")
    private String cargoNo;

    /**
     * 货名    +
     */
    @ApiModelProperty(value="货名")
    private String cargoNam;

    /**
     * 货类代码    +无
     */
    @ApiModelProperty(value="货类代码")
    private String cargoKindCod;

    /**
     * 结算方式代码     +'1'
     */
    @ApiModelProperty(value="结算方式代码")
    private String feeWayId;

    /**
     * 结算方式      +'支票'
     */
    @ApiModelProperty(value="结算方式")
    private String feeWayNam;

    /**
     * 船号    +13888
     */
    @ApiModelProperty(value="船号")
    private Integer shipNo;

    /**
     * 船名    +'市入作业'      +'市出作业'
     */
    @ApiModelProperty(value="船名")
    private String shipNam;

    /**
     * 泊位
     */
    @ApiModelProperty(value="泊位")
    private String berthCod;

    /**
     * 进出口   +无
     */
    @ApiModelProperty(value="进出口")
    private Integer inoutId;

    /**
     * 内外贸  +无
     */
    @ApiModelProperty(value="内外贸")
    private Integer tradeId;

    /**
     * 提单号   +无
     */
    @ApiModelProperty(value="提单号")
    private String billNo;

    /**
     * 件数    +
     */
    @ApiModelProperty(value="件数")
    private Integer piecesNum;

    /**
     * 重量    +
     */
    @ApiModelProperty(value="重量")
    private Double weightWgt;

    /**
     * 重量单位   +'吨'
     */
    @ApiModelProperty(value="重量单位")
    private String wgtUnit;

    /**
     * 上下限额    +无
     */
    @ApiModelProperty(value="上下限额")
    private Double addMinRat;

    /**
     * 运往地点    
     */
    @ApiModelProperty(value="运往地点")
    private String toPlaceNam;

    /**
     * 检车标记(0通过1未通     +'0'
     */
    @ApiModelProperty(value="检车标记(0通过1未通")
    private Integer checkId;

    /**
     * 申请单位     +表中的consign_nam
     */
    @ApiModelProperty(value="申请单位")
    private String requireUnitNam;

    /**
     * 代理公司代码   +无   
     */
    @ApiModelProperty(value="代理公司代码")
    private String agentCod;

    /**
     * 代理公司        +无
     */
    @ApiModelProperty(value="代理公司")
    private String agentNam;

    /**
     * 作业地点     +'港内'
     */
    @ApiModelProperty(value="作业地点")
    private String workPlace;

    /**
     * 作业地点代码    +'1'
     */
    @ApiModelProperty(value="作业地点代码")
    private String workPlaceCod;

    /**
     * 单位代码  +无
     */
    @ApiModelProperty(value="单位代码")
    private String unitCod;

    /**
     * 单位名称   +无
     */
    @ApiModelProperty(value="单位名称")
    private String unitNam;

    /**
     * 科室队代码   +无
     */
    @ApiModelProperty(value="科室队代码")
    private String subunitCod;

    /**
     * 科室队名称   +无
     */
    @ApiModelProperty(value="科室队名称")
    private String subunitNam;

    /**
     * 完标   +0
     */
    @ApiModelProperty(value="完标")
    private Integer ifendId;

    /**
     * 备注   +
     */
    @ApiModelProperty(value="备注")
    private String remarkTxt;

    /**
     * 操作员   +表中的oper_nam
     */
    @ApiModelProperty(value="操作员")
    private String operNam;

    /**
     * 记录时间   +sysdate
     */
    @ApiModelProperty(value="记录时间")
    private Date recordTim;

    /**
     * 最终修改人 +表中的oper_nam
     */
    @ApiModelProperty(value="最终修改人")
    private String updateNam;

    /**
     * 最终修改时间  +sysdate
     */
    @ApiModelProperty(value="最终修改时间")
    private Date updateTim;

    /**
     * 计重方式代码
     */
    @ApiModelProperty(value="计重方式代码")
    private String countWayCod;

    /**
     * 计重方式
     */
    @ApiModelProperty(value="计重方式")
    private String countWay;

    /**
     * 车次号
     */
    @ApiModelProperty(value="车次号")
    private Integer trainNo;

    /**
     * 车皮号
     */
    @ApiModelProperty(value="车皮号")
    private String trainCod;

    /**
     * 出入库单号   +
     */
    @ApiModelProperty(value="出入库单号")
    private String inoutYardNo;

    /**
     * carId
     */
    @ApiModelProperty(value="carId")
    private String carId;

    /**
     * gCarId
     */
    @ApiModelProperty(value="gCarId")
    private String gCarId;

	/**
	 * gCarId
	 */
	@ApiModelProperty(value="params")
	@TableField(exist = false)
	private Map<String,Object> params;

	/**
	 * bodyId
	 */
	@ApiModelProperty(value="bodyId")
	@TableField(exist = false)
	private Integer bodyId;

	/**
	 * 审核状态
	 */
	@ApiModelProperty(value="审核状态")
	private String checkStatus;

	/**
	 * 审核人
	 */
	@ApiModelProperty(value="审核人")
	private String checkUser;

	/**
	 * 审核时间
	 */
	@ApiModelProperty(value="审核时间")
	private Date checkTime;

}
