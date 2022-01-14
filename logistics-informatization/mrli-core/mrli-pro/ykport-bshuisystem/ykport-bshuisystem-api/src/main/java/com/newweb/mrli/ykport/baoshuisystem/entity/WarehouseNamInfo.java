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
 * 仓库名称信息表
 *
 * @author Tara
 * @date 2021-10-28 17:35:01
 */
@Data
@TableName("WAREHOUSE_NAM_INFO")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "仓库名称信息表")
@KeySequence(value = "SEQ_WAREHOUSE_NAM_INFO",clazz = Integer.class)
public class WarehouseNamInfo extends Model<WarehouseNamInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="主键")
	@JsonProperty("fId")
    private Long fId;

    /**
     * 单据名称
     */
    @ApiModelProperty(value="单据名称")
	@TableField(condition = SqlCondition.like)
    private String inoutYardNam;

    /**
     * 仓库编号
     */
    @ApiModelProperty(value="仓库编号")
    private String warehouseCod;

    /**
     * 仓库名称
     */
    @ApiModelProperty(value="仓库名称")
	@TableField(condition = SqlCondition.like)
    private String warehouseNam;

    /**
     * 电子账册号
     */
    @ApiModelProperty(value="电子账册号")
    private String electronicAccountNum;

    /**
     * 模板标识
     */
    @ApiModelProperty(value="模板标识")
    private String exportCod;

    /**
     * 模板名称
     */
    @ApiModelProperty(value="模板名称")
    private String exportNam;

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

	/**
	 * 进出 1：进， 0：出
	 */
	@ApiModelProperty(value="进出")
	private String inOut;


}
