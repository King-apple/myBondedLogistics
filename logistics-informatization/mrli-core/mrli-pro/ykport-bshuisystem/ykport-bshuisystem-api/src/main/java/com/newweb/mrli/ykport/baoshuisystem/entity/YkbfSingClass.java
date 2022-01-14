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
import com.newweb.mrli.ykport.baoshuisystem.utils.ExcelUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 单个工班查询
 *
 * @author pigx code generator
 * @date 2021-10-24 15:49:10
 */
@Data
@TableName("ykport1.YKBF_SING_CLASS")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "单个工班查询")
public class YkbfSingClass extends Model<YkbfSingClass> {

    private static final long serialVersionUID = 1L;

    /**
     * 代码
     */
    @ApiModelProperty(value="代码")
    private String lxdm;

    /**
     * 货票号
     */
    @ApiModelProperty(value="货票号")
    private String bh;

    /**
     * 货名
     */
	@ExcelUtil.ExcelAttribute(column = "2",name = "品名")
    @ApiModelProperty(value="货名")
    private String hm;

    /**
     * 提单号
     */
    @ApiModelProperty(value="提单号")
    private String tdh;

    /**
     * 船名
     */
    @ApiModelProperty(value="船名")
    private String cm;

    /**
     * 计划量
     */
    @ApiModelProperty(value="计划量")
    private String jhl;

    /**
     * 完成车
     */
    @ApiModelProperty(value="完成车")
    private String csl;

    /**
     * 完成量
     */
	@ExcelUtil.ExcelAttribute(column = "2",name = "出库量")
    @ApiModelProperty(value="完成量")
    private String wcl;

    /**
     * 剩余量
     */
    @ApiModelProperty(value="剩余量")
    private String syl;

    /**
     * 本班车
     */
    @ApiModelProperty(value="本班车")
    private String benc;

    /**
     * 未回车
     */
    @ApiModelProperty(value="未回车")
    private String weic;

    /**
     * 本班货重
     */
    @ApiModelProperty(value="本班货重")
    private String benhz;

    /**
     * 称别组
     */
    @ApiModelProperty(value="称别组")
    private String cbs;

    /**
     * 申请单位
     */
    @ApiModelProperty(value="申请单位")
    private String sqdw;

    /**
     * 班次
     */
	@ExcelUtil.ExcelAttribute(column = "3",name = "日期")
    @ApiModelProperty(value="班次")
    private String rqcbs;

	/**
	 * 班次
	 */
	@ApiModelProperty(value="班次")
	@TableField(exist = false)
	private String[] startDte;

}
