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
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 审批流程表
 *
 * @author pigx code generator
 * @date 2021-07-28 14:27:51
 */
@Data
@TableName("APPROVAL_PROCESS")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "审批流程表")
public class ApprovalProcessPO extends Model<ApprovalProcessPO> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="主键")
	@JsonProperty("fId")
    private Long fId;

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
    private String recordTim;

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
	 * 是否按照用户名查询
	 */
	@ApiModelProperty(value="是否按照用户名查询")
	private String isListbyuser;

	/**
	 * 部门编号
	 */
	@ApiModelProperty(value="部门编号")
	private String linkCode;


	@TableField(exist = false)
	private Integer[] userList;
}
