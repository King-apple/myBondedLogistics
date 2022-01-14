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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * 货代合同结算台账
 *
 * @author ly
 * @date 2021-10-27 09:00:08
 */
@Data
@TableName("FORWARDER_PARAMETER")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "货代合同结算台账")
@KeySequence(value = "SEQ_FORWARDER_PARAMETER",clazz = Integer.class)
public class ForwarderParameter extends Model<ForwarderParameter> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value="主键")
    private Integer fId;

    /**
     * 序号(付款)
     */
    @ApiModelProperty(value="序号(付款)")
    private Integer serialNumberPay;

    /**
     * 合同编号
     */
    @ApiModelProperty(value="合同编号")
    private String contCod;

    /**
     * 日期(单据)
     */
    @ApiModelProperty(value="日期(单据)")
    private Date billDate;

    /**
     * 客户
     */
    @ApiModelProperty(value="客户")
    private String secondNam;

    /**
     * 货名
     */
    @ApiModelProperty(value="货名")
    private String cargoNam;

    /**
     * 操作量(吨)
     */
    @ApiModelProperty(value="操作量(吨)")
    private Integer feeQuantity;

    /**
     * 件数
     */
    @ApiModelProperty(value="件数")
    private Integer nember;

    /**
     * 船名
     */
    @ApiModelProperty(value="船名")
    private String shipNam;

    /**
     * 装/卸日期
     */
    @ApiModelProperty(value="装/卸日期")
    private Date loadUnloadDate;

    /**
     * 往来款(元)
     */
    @ApiModelProperty(value="往来款(元)")
    private Integer currentAccount;

    /**
     * 付款内容
     */
    @ApiModelProperty(value="付款内容")
    private String payContent;

    /**
     * 付款单位
     */
    @ApiModelProperty(value="付款单位")
    private String payUnit;

    /**
     * 收款单位
     */
    @ApiModelProperty(value="收款单位")
    private String firstNam;

    /**
     * 金额
     */
    @ApiModelProperty(value="金额")
    private Integer money;

    /**
     * 收到发票
     */
    @ApiModelProperty(value="收到发票")
    private String receiptInvoice;

    /**
     * 业务结算
     */
    @ApiModelProperty(value="业务结算")
    private String businessSettlementNam;

    /**
     * 开票申请
     */
    @ApiModelProperty(value="开票申请")
    private String applicationInvoice;

    /**
     * 开出发票
     */
    @ApiModelProperty(value="开出发票")
    private String openInvoice;

    /**
     * 序号(结算)
     */
    @ApiModelProperty(value="序号(结算)")
    private Integer serialNumberSet;

    /**
     * 结算日期(月份)
     */
    @ApiModelProperty(value="结算日期(月份)")
    private Integer settlemnentDataMonth;

    /**
     * 阶梯价格
     */
    @ApiModelProperty(value="阶梯价格")
    private Integer ladderPrice;

    /**
     * 寄出发票
     */
    @ApiModelProperty(value="寄出发票")
    private String sendInvoice;

    /**
     * 收到款项
     */
    @ApiModelProperty(value="收到款项")
    private Integer receivedPay;

    /**
     * 利润
     */
    @ApiModelProperty(value="利润")
    private Integer profits;

    /**
     * 余款
     */
    @ApiModelProperty(value="余款")
    private Integer spareMoney;

    /**
     * 差额
     */
    @ApiModelProperty(value="差额")
    private Integer differenceMoney;

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
     * 添加人时间
     */
    @ApiModelProperty(value="添加人时间")
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
