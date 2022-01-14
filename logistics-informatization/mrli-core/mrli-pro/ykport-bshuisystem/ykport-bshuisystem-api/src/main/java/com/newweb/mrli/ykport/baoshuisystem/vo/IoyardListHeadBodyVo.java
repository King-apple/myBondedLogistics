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

package com.newweb.mrli.ykport.baoshuisystem.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.newweb.mrli.ykport.baoshuisystem.entity.Contract;
import com.newweb.mrli.ykport.baoshuisystem.entity.ContractRate;
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardListBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 出入库明细表头表
 *
 * @author pigx code generator
 * @date 2021-09-09 09:52:12
 */
@Data
@TableName("IOYARD_LIST_HEAD")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "出入库明细表头表体连查")
@KeySequence(value = "SEQ_IOYARD_LIST_HEAD",clazz = Long.class)
public class IoyardListHeadBodyVo extends Model<IoyardListHeadBodyVo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value="主键")
	@JsonProperty("fId")
    private Long fId;

    /**
     * 单据名称
     */
    @ApiModelProperty(value="单据名称")

    private String ioyardListNam;

    /**
     * 编号
     */
    @ApiModelProperty(value="编号")
    private String ioyardListNo;

    /**
     * 合同编号
     */
    @ApiModelProperty(value="合同编号")
    private String contNo;

    /**
     * 日期
     */
    @ApiModelProperty(value="日期")
    private Date listDat;

    /**
     * 制表人
     */
    @ApiModelProperty(value="制表人")
    private String operNam;

    /**
     * 制表时间
     */
    @ApiModelProperty(value="制表时间")
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
	 * 表体
	 */
	@ApiModelProperty(value="表体")
    private List<IoyardListBody> ioyardListBodyList;

	@ApiModelProperty(value="参数")
	private String [] params;

	/***
	 * 合同信息
	 */
	private List<Contract> contractList;
;
}
