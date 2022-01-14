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
 * 审批流程设定表
 *
 * @author zada
 * @date 2021-10-08 14:39:44
 */
@Data
@TableName("APPROVAL_PROCESS_SET")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "审批流程设定表")
@KeySequence(value = "SEQ_APPROVAL_PROCESS_SET",clazz = Long.class)
public class ApprovalProcessSet extends Model<ApprovalProcessSet> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value="主键")
	@JsonProperty("fId")
    private Integer fId;

    /**
     * 审批流程表主键
     */
    @ApiModelProperty(value="审批流程表主键")
    private Long processFid;

    /**
     * 审批流程
     */
    @ApiModelProperty(value="审批流程")
    private String processNo;

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
     * 角色
     */
    @ApiModelProperty(value="角色")
    private String roleNam;

    /**
     * 审批单主键
     */
    @ApiModelProperty(value="审批单主键")
    private Long billFid;

    /**
     * 用户表主键
     */
    @ApiModelProperty(value="用户表主键")
    private Integer userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value="用户名")
    private String username;

    /**
     * 用户姓名
     */
    @ApiModelProperty(value="用户姓名")
    private String manName;

    /**
     * 审批意见
     */
    @ApiModelProperty(value="审批意见")
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
	private String overShortType;

	/**
	 * 货溢处理次数
	 */
	@ApiModelProperty(value="货溢处理次数（null：不可货溢；0：可货溢处理；1：货溢已处理不可再进行货溢处理，也不可再提交货溢短量审核）")
	@TableField(exist = false)
	private String overShortTimes;


	/**
	 * 是否完整标识
	 */
	@ApiModelProperty(value="是否完整审核")
	@TableField(exist = false)
	private String completeApprovalFlag;
}
