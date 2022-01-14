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
 * 编号表
 *
 * @author pigx code generator
 * @date 2021-08-30 15:26:28
 */
@Data
@TableName("CODE_LIST")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "编号表")
public class CodeList extends Model<CodeList> {

    private static final long serialVersionUID = 1L;

    /**
     * 参数值
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="参数值")
    private String paramCode;

    /**
     * 参数绑定名称
     */
    @ApiModelProperty(value="参数绑定名称")
    private String paramName;

    /**
     * 参数绑定日期
     */
    @ApiModelProperty(value="参数绑定日期")
    private String paramDate;

    /**
     * 子参数值
     */
    @ApiModelProperty(value="子参数值")
    private String paramChildCode;

}
