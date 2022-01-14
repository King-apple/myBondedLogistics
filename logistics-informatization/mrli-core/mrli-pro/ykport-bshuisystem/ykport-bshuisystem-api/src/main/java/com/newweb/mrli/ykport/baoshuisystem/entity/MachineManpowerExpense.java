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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 机械人力作业费用明细表
 *
 * @author zada
 * @date 2021-10-22 16:46:51
 */
@Data
@TableName("MACHINE_MANPOWER_EXPENSE")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "机械人力作业费用明细表")
@KeySequence(value = "SEQ_MACHINE_MANPOWER_EXPENSE",clazz = Integer.class)
public class MachineManpowerExpense extends Model<MachineManpowerExpense> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="主键")
	@JsonProperty("fId")
    private Integer fId;

    /**
     * 工票号
     */
    @ApiModelProperty(value="工票号")
    private String workbillNo;

    /**
     * 作业日期
     */
    @ApiModelProperty(value="作业日期")
    private Date workDte;

    /**
     * 单位名称
     */
    @ApiModelProperty(value="单位名称")
    private String subunitNam;

    /**
     * 车型
     */
    @ApiModelProperty(value="车型")
    private String machTypeNam;

    /**
     * 车型号
     */
    @ApiModelProperty(value="车型号")
    private String machKndCod;

    /**
     * 作业内容
     */
    @ApiModelProperty(value="作业内容")
    private String workTypeNam;

    /**
     * 小时/吨
     */
    @ApiModelProperty(value="小时/吨")
    private Double hours;

    /**
     * 单价
     */
    @ApiModelProperty(value="单价")
    private Double unitPrice;

    /**
     * 作业金额（元）
     */
    @ApiModelProperty(value="作业金额（元）")
    private Double sum;

    /**
     * 检算日期
     */
    @ApiModelProperty(value="检算日期")
    private Date checkDte;

    /**
     * 结算日期
     */
    @ApiModelProperty(value="结算日期")
    private Date settleDte;

    /**
     * 收到发票日期
     */
    @ApiModelProperty(value="收到发票日期")
    private Date receiptInvoiceDte;

    /**
     * 已结算金额
     */
    @ApiModelProperty(value="已结算金额")
    private Double settledAmount;

    /**
     * 未结算金额
     */
    @ApiModelProperty(value="未结算金额")
    private Double outstandingAmount;

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
	private String startTime;

	@TableField(exist = false)
	private String endTime;

}
