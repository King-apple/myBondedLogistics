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
 * 业务单据审批单
 *
 * @author ly
 * @date 2021-09-17 10:48:12
 */
@Data
@TableName("BILL_APPROVE")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "业务单据审批单")
@KeySequence(value = "SEQ_BILL_APPROVE",clazz = Long.class)
public class BillApprove extends Model<BillApprove> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value="主键")
	@JsonProperty("fId")
    private Integer fId;

    /**
     * 单据主键
     */
    @ApiModelProperty(value="单据主键")
	@JsonProperty("fFid")
    private Integer fFid;

    /**
     * 单据编号
     */
    @ApiModelProperty(value="单据编号")
    private String billCod;

    /**
     * 单据类型
     */
    @ApiModelProperty(value="单据类型")
    private String billType;

    /**
     * 日期
     */
    @ApiModelProperty(value="日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date billDat;

    /**
     * 承办单位
     */
    @ApiModelProperty(value="承办单位")
    private String firstNam;

    /**
     * 承办单位代码
     */
    @ApiModelProperty(value="承办单位代码")
    private String firstCod;

    /**
     * 部门
     */
    @ApiModelProperty(value="部门")
    private String dept;

    /**
     * 经办人
     */
    @ApiModelProperty(value="经办人")
    private String operator;
	@ApiModelProperty(value="是否作废（1：已作废）")
	private String isCancel;
    /**
     * 合同客户代码
     */
    @ApiModelProperty(value="合同客户代码")
    private String secondCod;

    /**
     * 合同客户名称
     */
    @ApiModelProperty(value="合同客户名称")
    private String secondNam;

    /**
     * 合同编号
     */
    @ApiModelProperty(value="合同编号")
	@JsonProperty("contNo")
    private String contNo;

    /**
     * 船名
     */
    @ApiModelProperty(value="船名")
    private String shipNam;

    /**
     * 业务种类
     */
    @ApiModelProperty(value="业务种类")
    private String businessType;

    /**
     * 货物代码
     */
    @ApiModelProperty(value="货物代码")
    private String cargoCod;

    /**
     * 货物名称
     */
    @ApiModelProperty(value="货物名称")
    private String cargoNam;

    /**
     * 结算账户名称
     */
    @ApiModelProperty(value="结算账户名称")
    private String accountNam;

    /**
     * 计费数量
     */
    @ApiModelProperty(value="计费数量")
    private Double settleWgt;

    /**
     * 金额（元）
     */
    @ApiModelProperty(value="金额（元）")
    private Double money;

    /**
     * 垫款金额（元）
     */
    @ApiModelProperty(value="垫款金额（元）")
    private Double advinceMoney;

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
	/**
	 * 状态
	 */
	@ApiModelProperty(value="状态")
	private Integer approveStatus;
	@ApiModelProperty(value="次数")
	private Integer times;

	/**
	 * 业务审核状态（0:未审核;1:审核中;2:审核通过;3:审核未通过）
	 */
	@ApiModelProperty(value="业务审核状态（0:未审核;1:审核中;2:审核通过;3:审核未通过）")
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

	/**
	 * 审核人姓名
	 */
	@ApiModelProperty(value="审核人姓名")
	private String checkManname;
	/**
	 * 入境船名
	 */
	@ApiModelProperty(value="入境船名")
	private String inShipName;

	/**
	 * 出境船名
	 */
	@ApiModelProperty(value="出境船名")
	private String outShipName;

	/**
	 * 部门id
	 */
	@ApiModelProperty(value="部门id")
	private String deptId;
	@ApiModelProperty(value="货类编码")
	private String cargoKindCod;

	/**
	 * 货类名称
	 */
	@ApiModelProperty(value="货类名称")
	private String cargoKindNam;
}
