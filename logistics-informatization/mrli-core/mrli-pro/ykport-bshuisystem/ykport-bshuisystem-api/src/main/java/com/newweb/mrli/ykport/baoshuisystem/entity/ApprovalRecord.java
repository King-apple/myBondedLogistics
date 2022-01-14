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
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 审批记录表
 *
 * @author pigx code generator
 * @date 2021-08-28 18:10:46
 */
@Data
@TableName("APPROVAL_RECORD")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "审批记录表")
@KeySequence(value = "SEQ_approval_record",clazz = Long.class)
public class ApprovalRecord extends Model<ApprovalRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value = "F_ID",type = IdType.INPUT)
	@ApiModelProperty(value="主键")
	@JsonProperty("fId")
    private Long fId;

    /**
     * 审批流程表主键
     */
    @ApiModelProperty(value="审批流程表主键")
	@JsonProperty("pFid")
    private Long pFid;

    /**
     * 合同信息管理表主键
     */
    @ApiModelProperty(value="合同信息管理表主键")
	@JsonProperty("cFid")
    private Long cFid;

    /**
     * 审批流程
     */
    @ApiModelProperty(value="审批流程")
    private String processCode;

    /**
     * 审批序号
     */
    @ApiModelProperty(value="审批序号")
    private Integer orderBy;

    /**
     * 部门名称
     */
    @ApiModelProperty(value="部门名称")
    private String linkNam;

    /**
     * 审批意见：同意/不同意
     */
    @ApiModelProperty(value="审批意见：同意/不同意")
    private String opinion;

    /**
     * 审批意见说明
     */
    @ApiModelProperty(value="审批意见说明")
    private String opinionExplain;

    /**
     * 是否同意
     */
    @ApiModelProperty(value="是否同意")
    private String isAgree;

    /**
     * 审批人
     */
    @ApiModelProperty(value="审批人")
    private String approver;

    /**
     * 审批时间
     */
    @ApiModelProperty(value="审批时间")
    private Date approvalTim;

    /**
     * 是否作废（1：作废）
     */
    @ApiModelProperty(value="是否作废（1：作废）")
    private String isCancel;

	/**
	 * 审批次数
	 */
	@ApiModelProperty(value="审批次数")
	private Integer times;

	/**
	 * 审批人姓名
	 */
	@ApiModelProperty(value="审批人姓名")
	private String approverName;

	/**
	 * 部门编号
	 */
	@ApiModelProperty(value="部门编号")
	private String linkCode;

}
