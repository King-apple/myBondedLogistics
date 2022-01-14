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

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.newweb.mrli.ykport.baoshuisystem.utils.ExcelUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 出入库表头
 *
 * @author zada
 * @date 2021-09-02 18:43:01
 */
@Data
public class InoutYardHeadVo extends Model<InoutYardHeadVo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
	@JsonProperty("fId")
    private Integer fId;

    /**
     * 出入库
     */
    @ApiModelProperty(value="出入库")
    private String inOut;

    /**
     * 单据类型
     */
    @ApiModelProperty(value="单据类型")
    private String inoutYardKnd;

    /**
     * 业务类型
     */
    @ApiModelProperty(value="业务类型")
    private String businessKnd;

    /**
     * 单据名称
     */
    @ApiModelProperty(value="单据名称")
    private String inoutYardNam;

    /**
     * 单据编号
     */
    @ApiModelProperty(value="单据编号")
    private String inoutYardNo;

    /**
     * 入驻企业名称
     */
    @ApiModelProperty(value="入驻企业名称")
    private String settledCompany;

    /**
     * 仓库名称
     */
    @ApiModelProperty(value="仓库名称")
	@ExcelUtil.ExcelAttribute(name = "仓库名称")
    private String warehouseNam;

    /**
     * 进区报关单号
     */
    @ApiModelProperty(value="进区报关单号")
	@ExcelUtil.ExcelAttribute(name = "进区报关单号")
    private String inCustomsDeclaration;

    /**
     * 进区核注清单号
     */
    @ApiModelProperty(value="进区核注清单号")
	@ExcelUtil.ExcelAttribute(name = "进区核注清单号")
    private String inCheckNum;

    /**
     * 电子账册号
     */
    @ApiModelProperty(value="电子账册号")
	@ExcelUtil.ExcelAttribute(name = "电子账册号")
    private String electronicAccountNum;

    /**
     * 入区类别
     */
    @ApiModelProperty(value="入区类别")
	@ExcelUtil.ExcelAttribute(name = "入区类别")
    private String inCategory;

    /**
     * 报关代理企业
     */
    @ApiModelProperty(value="报关代理企业")
	@ExcelUtil.ExcelAttribute(name = "报关代理企业")
    private String customsBroker;

    /**
     * 货主代码
     */
    @ApiModelProperty(value="货主代码")
    private String consignCod;

    /**
     * 货主单位
     */
    @ApiModelProperty(value="货主单位")
	@ExcelUtil.ExcelAttribute(name = "货主单位")
    private String consignNam;

    /**
     * 出区核注清单号
     */
    @ApiModelProperty(value="出区核注清单号")
	@ExcelUtil.ExcelAttribute(name = "出区核注清单号")
    private String outCheckNum;

    /**
     * 出区类别
     */
    @ApiModelProperty(value="出区类别")
	@ExcelUtil.ExcelAttribute(name = "出区类别")
    private String outCategory;

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
     * 报关代理企业代码
     */
    @ApiModelProperty(value="报关代理企业代码")
    private String customsBrokerCod;

    /**
     * 入驻企业字母代码
     */
    @ApiModelProperty(value="入驻企业字母代码")
    private String settledCod;

    /**
     * 仓库代码
     */
    @ApiModelProperty(value="仓库代码")
    private String warehouseCod;

	/**
	 * 业务部审核状态（0：未审核，1：审核通过，2：审核不通过）
	 */
	@ApiModelProperty(value="务部审核状态")
	private String businessCheckStatus;

	/**
	 * 业务部审核人
	 */
	@ApiModelProperty(value="业务部审核人")
	private String businessChecker;

	/**
	 * 业务部审核时间
	 */
	@ApiModelProperty(value="业务部审核时间")
	private Date businessCheckTim;

	/**
	 * 操作部审核状态（0：未审核，1：审核通过，2：审核不通过）
	 */
	@ApiModelProperty(value="操作部审核状态")
	private String operCheckStatus;

	/**
	 * 操作部审核人
	 */
	@ApiModelProperty(value="操作部审核人")
	private String operChecker;

	/**
	 * 操作部审核时间
	 */
	@ApiModelProperty(value="操作部审核时间")
	private Date operCheckTim;


	/**
	 * 单价类型
	 */
	@ApiModelProperty(value="单价类型")
	private String unitPriceKnd;

	/**
	 * 总价单位
	 */
	@ApiModelProperty(value="总价单位")
	private String unit;

	/**
	 * 净重单位
	 */
	@ApiModelProperty(value="净重单位")
	private String netWetUnit;


	@ApiModelProperty(value="进区核注清单号FLG")
	private String inCheckNumFlg;

	@ApiModelProperty(value="进出区核注清单号FLG")
	private String inOutFlg;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value="开始时间")
	private String startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value="结束时间")
	private String endTime;

	/**
	 * 净重（总和）
	 */
	@ApiModelProperty(value="净重（总和）")
	private String sumNetWet;

	/**
	 * 总价（总和）
	 */
	@ApiModelProperty(value="总价（总和）")
	private String sumTotalPrice;

	/**
	 * 净重（总和）
	 */
	@ApiModelProperty(value="净重（总和）")
	private String inSumNetWet;

	/**
	 * 总价（总和）
	 */
	@ApiModelProperty(value="总价（总和）")
	private String inSumTotalPrice;

	@ApiModelProperty(value="进区日期")
	private String inRecordTim;



	/**
	 * 出区日期
	 */
	@ApiModelProperty(value="出区日期")
	private String outRecordTim;

	/**
	 * 出区核注清单号
	 */
	@ApiModelProperty(value="出区核注清单")
	private String outOutCheckNum;

	/**
	 * 出区净重
	 */
	@ApiModelProperty(value="出区净重")
	private String outSumNetWet;

	/**
	 * 出区总价
	 */
	@ApiModelProperty(value="出区总价")
	private String outSumTotalPrice;


	/**
	 * 出区货主单位
	 */
	@ApiModelProperty(value="出区货主单位")
	private String outConsignNam;

}
