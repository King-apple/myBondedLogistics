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
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 历史车信息
 *
 * @author pigx code generator
 * @date 2021-09-27 10:35:40
 */
@Data
@TableName("BCMCXTAB")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "上图详细信息")
public class Bcmcxtab extends Model<Bcmcxtab> {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value="货票编号")
    private String bh;

    /**
     * 车号
     */
    @ApiModelProperty(value="车号")
    private String ch;

    /**
     * 完成量
     */
    @ApiModelProperty(value="完成量")
    private String hz;

    /**
     * 件数
     */
    @ApiModelProperty(value="件数")
    private String jianshu;

	/**
	 * 班组
	 */
	@ApiModelProperty(value="班组")
	@TableField(exist = false)
	private String rqcbs;


	/**
	 * 货票编号
	 */
	@TableId(type = IdType.INPUT)
    @ApiModelProperty(value="记录号")
    private String jlh;

	/**
	 * ioyard表主键
	 */
	@ApiModelProperty(value="ioyard表主键")
	private String ioyardPk;


}
