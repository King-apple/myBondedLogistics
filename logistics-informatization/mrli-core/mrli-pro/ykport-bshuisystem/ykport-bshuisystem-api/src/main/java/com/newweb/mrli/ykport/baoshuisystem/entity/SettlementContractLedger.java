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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 保税业务合同结算台账
 *
 * @author ly
 * @date 2021-12-14 10:25:42
 */
@Data
@TableName("SETTLEMENT_CONTRACT_LEDGER")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "保税业务合同结算台账")
@KeySequence(value = "SEQ_SETTLEMENT_CONTRACT_LEDGER", clazz = Integer.class)
public class SettlementContractLedger extends Model<SettlementContractLedger> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="主键")
    private Integer fId;

    /**
     * 合同类型
     */
    @ApiModelProperty(value="合同类型")
    private String contractType;

    /**
     * 部门
     */
    @ApiModelProperty(value="部门")
    private String dept;

    /**
     * 合同编号
     */
    @ApiModelProperty(value="合同编号")
    private String contNo;
	/**
	 * 对方签约单位
	 */
	@ApiModelProperty(value="我司签约名头")
	private String firstNam;

    /**
     * 对方签约单位
     */
    @ApiModelProperty(value="对方签约单位")
    private String secondNam;

    /**
     * 船名
     */
    @ApiModelProperty(value="船名")
    private String shipNam;

    /**
     * 货类名称
     */
    @ApiModelProperty(value="货类名称")
    private String cargoKindNam;

    /**
     * 合同标的
     */
    @ApiModelProperty(value="合同标的")
    private String cargoNam;

    /**
     * 合同标的数量
     */
    @ApiModelProperty(value="合同标的数量")
    private Double cargoNum;

    /**
     * 标的额
     */
    @ApiModelProperty(value="标的额")
    private Double cargoFee;

    /**
     * 结算依据
     */
    @ApiModelProperty(value="结算依据")
    private String settlementBasis;

    /**
     * 合同开始日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    @ApiModelProperty(value="合同开始日期")
    private Date startDte;

    /**
     * 合同结束日期
     */
    @ApiModelProperty(value="合同结束日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    private Date endDte;

    /**
     * 经办人
     */
    @ApiModelProperty(value="经办人")
    private String operator;

    /**
     * 费率值
     */
    @ApiModelProperty(value="费率值")
    private String feeRat;

    /**
     * 费目名称
     */
    @ApiModelProperty(value="费目名称")
    private String feeNam;

    /**
     * 入库单据编号
     */
    @ApiModelProperty(value="入库单据编号")
    private String inYardNo;

    /**
     * 出库单据编号
     */
    @ApiModelProperty(value="出库单据编号")
    private String outYardNo;

    /**
     * 计划入库日期
     */
    @ApiModelProperty(value="计划入库日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    private Date inRecordTim;

    /**
     * 计划出库日期
     */
    @ApiModelProperty(value="计划出库日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    private Date outRecordTim;

    /**
     * 计划入库吨数（毛重）
     */
    @ApiModelProperty(value="计划入库吨数（毛重）")
    private Double inRoughWet;

    /**
     * 计划出库吨数（毛重）
     */
    @ApiModelProperty(value="计划出库吨数（毛重）")
    private Double outRoughWet;

    /**
     * 编号
     */
    @ApiModelProperty(value="编号")
    private String ioyardListNo;

    /**
     * 入库日期
     */
    @ApiModelProperty(value="入库日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    private Date inYardDat;

    /**
     * 出库日期
     */
    @ApiModelProperty(value="出库日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    private Date outYardDat;

    /**
     * 实际入库吨数
     */
    @ApiModelProperty(value="实际入库吨数")
    private Double inWeightWgt;

    /**
     * 实际出库吨数
     */
    @ApiModelProperty(value="实际出库吨数")
    private Double outWeightWgt;

    /**
     * 收款时间日期
     */
    @ApiModelProperty(value="收款时间日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    private Date billDatCollection;

    /**
     * 结算单日期
     */
    @ApiModelProperty(value="结算单日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    private Date billDatSettlement;

    /**
     * 收款单编号
     */
    @ApiModelProperty(value="收款单编号")
    private String billCodCollection;

    /**
     * 结算单编号
     */
    @ApiModelProperty(value="结算单编号")
    private String billCodSettlement;

    /**
     * 收款金额
     */
    @ApiModelProperty(value="收款金额")
    private Double moneyCollection;

    /**
     * 结算金额
     */
    @ApiModelProperty(value="结算金额")
    private Double moneySettlement;

    /**
     * 返款金额(付款单)
     */
    @ApiModelProperty(value="返款金额(付款单)")
    private Double moneyRebates;

    /**
     * 客户余额
     */
    @ApiModelProperty(value="客户余额")
    private Double balance;

    /**
     * 利润（不含税）
     */
    @ApiModelProperty(value="利润（不含税）")
    private Double profit;

}
