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
 * 费目码表
 *
 * @author pigx code generator
 * @date 2021-08-28 15:17:55
 */
@Data
@TableName("FEE_NAMES")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "费目码表")
@KeySequence(value = "SEQ_fee_names",clazz = Integer.class)
public class FeeNames extends Model<FeeNames> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value = "F_ID",type = IdType.INPUT)
	@ApiModelProperty(value="主键")
	@JsonProperty("fId")
    private Integer fId;

    /**
     * 费目编码
     */
    @ApiModelProperty(value="费目编码")
    private String feeNameCod;

    /**
     * 费目名称
     */
    @ApiModelProperty(value="费目名称")
	@TableField(condition = SqlCondition.like)
    private String feeNam;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String markTxt;

    /**
     * 操作人
     */
    @ApiModelProperty(value="操作人")
    private String operNam;

    /**
     * 操作时间
     */
    @ApiModelProperty(value="操作时间")
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

}
