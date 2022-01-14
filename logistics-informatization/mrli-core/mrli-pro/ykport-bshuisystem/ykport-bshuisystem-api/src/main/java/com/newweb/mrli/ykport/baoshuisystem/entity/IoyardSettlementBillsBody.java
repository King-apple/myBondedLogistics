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
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 出入库结算单体表
 *
 * @author ly
 * @date 2021-09-14 18:44:52
 */
@Data
@TableName("IOYARD_SETTLEMENT_BILLS_BODY")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "出入库结算单体表")
@KeySequence(value="SEQ_IOYARD_SETTLEMENT_BODY",clazz=long.class)
public class IoyardSettlementBillsBody extends Model<IoyardSettlementBillsBody> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
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
     * 结算单编号
     */
    @ApiModelProperty(value="结算单编号")
    private String billCod;

    /**
     * 入库日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    @ApiModelProperty(value="入库日期")
    private Date inYardDat;

    /**
     * 入库结算数量
     */
    @ApiModelProperty(value="入库结算数量")
    private BigDecimal inSettleWgt;

    /**
     * 出库日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    @ApiModelProperty(value="出库日期")
    private Date outYardDat;

    /**
     * 出库结算数量
     */
    @ApiModelProperty(value="出库结算数量")
    private BigDecimal outSettleWgt;

    /**
     * 天数
     */
    @ApiModelProperty(value="天数")
    private Integer dayNum;

    /**
     * 吨天数
     */
    @ApiModelProperty(value="吨天数")
    private BigDecimal tonDayNum;

//    /**
//     * 出入库管理费费率
//     */
//    @ApiModelProperty(value="出入库管理费费率")
//    private BigDecimal managementRat;

    /**
     * 出入库管理费
     */
    @ApiModelProperty(value="出入库管理费")
    private BigDecimal managementMoney;

    /**
     * 保税仓储费费率
     */
    @ApiModelProperty(value="保税仓储费费率")
    private BigDecimal storageRat;

    /**
     * 保税仓储费
     */
    @ApiModelProperty(value="保税仓储费")
    private BigDecimal storageMoney;

    /**
     * 合计
     */
    @ApiModelProperty(value="合计")
    private BigDecimal totalMoney;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
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


    @TableField(exist = false)
    private List<SettlementBodyRate> list;
}
