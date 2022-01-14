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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 一日游出港证统计(暂时)
 *
 * @author zada
 * @date 2021-10-22 11:06:37
 */
@Data
@TableName("OUTPORT_CERTIFICATE")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "一日游出港证统计(暂时)")
public class OutportCertificate extends Model<OutportCertificate> {

    private static final long serialVersionUID = 1L;

    /**
     * 日期
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="日期")
    private Date recordDte;

    /**
     * 货名
     */
    @ApiModelProperty(value="货名")
    private String cargoNam;

    /**
     * 每车件数
     */
    @ApiModelProperty(value="每车件数")
    private Integer piecesNum;

    /**
     * 车牌号
     */
    @ApiModelProperty(value="车牌号")
    private String carNo;

    /**
     * 条码编号
     */
    @ApiModelProperty(value="条码编号")
    private String outportNo;

    /**
     * 提货单位
     */
    @ApiModelProperty(value="提货单位")
    private String agentNam;

    /**
     * 开票人
     */
    @ApiModelProperty(value="开票人")
    private String operNam;
	@ApiModelProperty(value="备注")
	private String outportRemark;

	@TableField(exist = false)
	private String timeStart;

	@TableField(exist = false)
	private String timeEnd;


}
