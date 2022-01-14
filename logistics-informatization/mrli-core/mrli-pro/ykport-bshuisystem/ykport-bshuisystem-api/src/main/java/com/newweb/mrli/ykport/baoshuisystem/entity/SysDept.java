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
 * 部门管理
 *
 * @author pigx code generator
 * @date 2021-09-28 16:42:13
 */
@Data
@TableName("sys_dept")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "部门管理")
public class SysDept extends Model<SysDept> {

    private static final long serialVersionUID = 1L;

    /**
     * deptId
     */
    @TableId
    @ApiModelProperty(value="deptId")
    private Integer deptId;

    /**
     * name
     */
    @ApiModelProperty(value="name")
    private String name;

    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Integer sort;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private LocalDateTime updateTime;

    /**
     * delFlag
     */
    @ApiModelProperty(value="delFlag")
    private String delFlag;

    /**
     * parentId
     */
    @ApiModelProperty(value="parentId")
    private Integer parentId;

    /**
     * tenantId
     */
    @ApiModelProperty(value="tenantId",hidden=true)
    private Integer tenantId;

    /**
     * unitCod
     */
    @ApiModelProperty(value="unitCod")
    private String unitCod;

    /**
     * deptType
     */
    @ApiModelProperty(value="deptType")
    private String deptType;

}
