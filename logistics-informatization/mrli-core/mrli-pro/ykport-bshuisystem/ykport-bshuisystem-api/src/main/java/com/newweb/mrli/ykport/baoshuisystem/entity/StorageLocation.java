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
 * 存放地点码表
 *
 * @author pigx code generator
 * @date 2021-09-18 15:57:39
 */
@Data
@TableName("STORAGE_LOCATION")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "存放地点码表")
@KeySequence(value = "seq_Storage_Location",clazz = Integer.class)
public class StorageLocation extends Model<StorageLocation> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value="主键")
	@JsonProperty("fId")
    private Long fId;

    /**
     * 存放位置
     */
    @ApiModelProperty(value="存放位置")
    private String location;

    /**
     * 添加人
     */
    @ApiModelProperty(value="添加人")
    private String operator;

    /**
     * 添加时间
     */
    @ApiModelProperty(value="添加时间")
    private Date recordTim;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String markTxt;

    /**
     * 更新人
     */
    @ApiModelProperty(value="更新人")
    private String updateNam;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
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
