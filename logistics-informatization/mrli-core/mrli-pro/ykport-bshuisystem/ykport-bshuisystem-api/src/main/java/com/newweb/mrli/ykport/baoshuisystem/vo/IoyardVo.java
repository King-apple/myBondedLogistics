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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 货物出入库表
 *
 * @author pigx code generator
 * @date 2021-09-06 19:35:20
 */
@Data
@TableName("IOYARD")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "货物出入库视图")
@KeySequence(value = "SEQ_IOYARD",clazz = Integer.class)
public class IoyardVo extends Model<IoyardVo> {

    private static final long serialVersionUID = 1L;

    private Integer inIoyardId;
    private Integer outIoyardId;
    private Date inIoyardDte;
    private Integer inPiecesNum;
    private Double inWeightWgt;
    private String inIoyardNo;

	private Date outIoyardDte;
	private Integer outPiecesNum;
	private Double outWeightWgt;
	private String outIoyardNo;

	private String consignNam;
	private String cargoNam;
	private String cPkgKindNam;
	private String yardNo;
	private String remarkTxt;
	private Date recordTim;
	private String operNam;
	private String inoutYardNo;

	private String inYardWay;
	private String outYardWay;
	private String shipNam;

}
