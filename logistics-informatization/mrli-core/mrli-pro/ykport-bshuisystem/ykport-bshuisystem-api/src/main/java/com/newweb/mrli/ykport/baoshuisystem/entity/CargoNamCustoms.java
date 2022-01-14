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
 * 海关货名码表
 *
 * @author pigx code generator
 * @date 2021-09-18 09:43:14
 */
@Data
@TableName("CARGO_NAM_CUSTOMS")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "海关货名码表")
@KeySequence(value = "SEQ_CARGO_NAM_CUSTOMS",clazz = Integer.class)
public class CargoNamCustoms extends Model<CargoNamCustoms> {

    private static final long serialVersionUID = 1L;

    /**
     * fId
     */
    @TableId(type = IdType.INPUT)
	@ApiModelProperty(value="主键")
	@JsonProperty("fId")
    private Long fId;

    /**
     * cargoCod
     */
    @ApiModelProperty(value="cargoCod")
    private String cargoCod;

    /**
     * cargoNam
     */
    @ApiModelProperty(value="cargoNam")
    private String cargoNam;

    /**
     * markTxt
     */
    @ApiModelProperty(value="markTxt")
    private String markTxt;

    /**
     * operNam
     */
    @ApiModelProperty(value="operNam")
    private String operNam;

    /**
     * recordTim
     */
    @ApiModelProperty(value="recordTim")
    private Date recordTim;

    /**
     * updateNam
     */
    @ApiModelProperty(value="updateNam")
    private String updateNam;

    /**
     * updateTim
     */
    @ApiModelProperty(value="updateTim")
    private Date updateTim;

    /**
     * delNam
     */
    @ApiModelProperty(value="delNam")
    private String delNam;

    /**
     * delTim
     */
    @ApiModelProperty(value="delTim")
    private Date delTim;

}
