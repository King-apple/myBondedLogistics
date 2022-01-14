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
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 承办单位与用户关联表
 *
 * @author pigx code generator
 * @date 2021-07-29 10:55:29
 */
@Data
@TableName("ORGANIZER_USER")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "承办单位与用户关联表")
public class OrganizerUser extends Model<OrganizerUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="主键")
    private Integer fId;

    /**
     * 承办单位信息表主键
     */
    @ApiModelProperty(value="承办单位信息表主键")
    private Integer oFid;

    /**
     * 用户表主键
     */
    @ApiModelProperty(value="用户表主键")
    private Integer uFid;

}
