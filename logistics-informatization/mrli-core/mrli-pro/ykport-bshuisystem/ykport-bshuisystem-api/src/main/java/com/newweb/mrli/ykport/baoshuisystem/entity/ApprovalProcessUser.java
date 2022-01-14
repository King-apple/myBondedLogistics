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
 * 审批流程与人员关联表
 *
 * @author zada
 * @date 2021-09-27 16:59:06
 */
@Data
@TableName("APPROVAL_PROCESS_USER")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "审批流程与人员关联表")
@KeySequence(value = "SEQ_APPROVAL_PROCESS_USER",clazz = Long.class)
public class ApprovalProcessUser extends Model<ApprovalProcessUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="主键")
	@JsonProperty("fId")
    private Integer fId;

    /**
     * 审批流程表主键
     */
    @ApiModelProperty(value="审批流程表主键")
    private Integer processFid;

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
