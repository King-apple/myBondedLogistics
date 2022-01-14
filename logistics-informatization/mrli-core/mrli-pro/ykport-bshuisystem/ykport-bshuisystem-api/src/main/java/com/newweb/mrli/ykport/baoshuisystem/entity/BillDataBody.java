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

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 计费信息体信息
 *
 * @author pigx code generator
 * @date 2021-11-05 13:56:49
 */
@Data
@TableName("BILL_DATA_BODY")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "计费信息体信息")
@KeySequence(value = "SEQ_BILL_DATA_BODY", clazz = Integer.class)
public class BillDataBody extends Model<BillDataBody> {

    private static final long serialVersionUID = 1L;

    /**
     * seqNo
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value="seqNo")
	@JSONField(name="SEQ_NO")
    private String seqNo;

    /**
     * 业务单号(必填，唯一，关联头表里的同名字段)
     */
    @ApiModelProperty(value="业务单号(必填，唯一，关联头表里的同名字段)")
	@JSONField(name="FEE_BILL_NO")
    private String feeBillNo;

    /**
     * 单位代码
     */
    @ApiModelProperty(value="单位代码")
	@JSONField(name="UNIT_COD")
    private String unitCod;

    /**
     * 单位名称
     */
    @ApiModelProperty(value="单位名称")
	@JSONField(name="UNIT_NAM")
    private String unitNam;

    /**
     * 货类代码
     */
    @ApiModelProperty(value="货类代码")
	@JSONField(name="CARGO_KIND_COD")
    private String cargoKindCod;

    /**
     * 货名代码
     */
    @ApiModelProperty(value="货名代码")
	@JSONField(name="CARGO_NAM_COD")
    private String cargoNamCod;

    /**
     * 货名
     */
    @ApiModelProperty(value="货名")
	@JSONField(name="CARGO_NAM")
    private String cargoNam;

    /**
     * 费目代码
     */
    @ApiModelProperty(value="费目代码")
	@JSONField(name="FEE_NAME_COD")
    private String feeNameCod;

    /**
     * 费目名称
     */
	@JsonProperty("cFeeNam")
    @ApiModelProperty(value="费目名称")
	@JSONField(name="C_FEE_NAM")
    private String  cFeeNam;


    /**
     * 费率
     */
    @ApiModelProperty(value="费率")
	@JSONField(name="FEE_RAT")
    private Integer feeRat;

    /**
     * 吨数
     */
    @ApiModelProperty(value="吨数")
	@JSONField(name="WEIGHT_WGT")
    private Integer weightWgt;

    /**
     * 含税金额
     */
    @ApiModelProperty(value="含税金额")
	@JSONField(name="FEE_MNY")
    private Integer feeMny;

    /**
     * ifendId
     */
    @ApiModelProperty(value="ifendId")
	@JSONField(name="IFEND_ID")
    private Integer ifendId;

    /**
     * 操作人
     */
    @ApiModelProperty(value="操作人")
	@JSONField(name="OPER_NAM")
    private String operNam;

    /**
     * 操作时间
     */
    @ApiModelProperty(value="操作时间")
	@JSONField(name="RECORD_TIM")
    private Date recordTim;

    /**
     * 修改人
     */
    @ApiModelProperty(value="修改人")
	@JSONField(name="UPDATE_NAM")
    private String updateNam;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
	@JSONField(name="UPDATE_TIM")
    private Date updateTim;

    /**
     * 审核人
     */
    @ApiModelProperty(value="审核人")
	@JSONField(name="CHECKER_NAM")
    private String checkerNam;

    /**
     * 审核时间
     */
    @ApiModelProperty(value="审核时间")
	@JSONField(name="CHECKER_NAM_DTE")
    private Date checkerNamDte;

    /**
     * 最后审核人
     */
    @ApiModelProperty(value="最后审核人")
	@JSONField(name="END_CHECKER_NAM")
    private String endCheckerNam;

    /**
     * 最后审核时间
     */
    @ApiModelProperty(value="最后审核时间")
	@JSONField(name="END_CHECKER_NAM_DTE")
    private Date endCheckerNamDte;

    /**
     * 货类名称
     */
    @ApiModelProperty(value="货类名称")
	@JSONField(name="CARGO_KIND_NAM")
    private String cargoKindNam;

    /**
     * 合同号
     */
    @ApiModelProperty(value="合同号")
	@JSONField(name="CONT_NO")
    private String contNo;

    /**
     * 客户代码
     */
    @ApiModelProperty(value="客户代码")
	@JSONField(name="SHIPPER_COD")
    private String shipperCod;

    /**
     * 客户名称
     */
    @ApiModelProperty(value="客户名称")
	@JSONField(name="SHIPPER_DOC")
    private String shipperDoc;

    /**
     * takeUnitCod
     */
    @ApiModelProperty(value="takeUnitCod")
	@JSONField(name="TAKE_UNIT_COD")
    private String takeUnitCod;

    /**
     * takeUnitNam
     */
    @ApiModelProperty(value="takeUnitNam")
	@JSONField(name="TAKE_UNIT_NAM")
    private String takeUnitNam;

    /**
     * priceNo
     */
    @ApiModelProperty(value="priceNo")
	@JSONField(name="PRICE_NO")
    private String priceNo;

    /**
     * fapiaoNo
     */
    @ApiModelProperty(value="fapiaoNo")
	@JSONField(name="FAPIAO_NO")
    private String fapiaoNo;

    /**
     * exFeeCod
     */
    @ApiModelProperty(value="exFeeCod")
	@JSONField(name="EX_FEE_COD")
    private String exFeeCod;

    /**
     * exFeeNam
     */
    @ApiModelProperty(value="exFeeNam")
	@JSONField(name="EX_FEE_NAM")
    private String exFeeNam;

    /**
     * 税率
     */
    @ApiModelProperty(value="税率")
	@JSONField(name="GS_TAX_RAT")
    private BigDecimal gsTaxRat;

    /**
     * 税额
     */
    @ApiModelProperty(value="税额")
	@JSONField(name="GS_TAX_FEE")
    private BigDecimal gsTaxFee;

    /**
     * 税后额
     */
    @ApiModelProperty(value="税后额")
	@JSONField(name="GS_SE_FEE")
    private BigDecimal gsSeFee;

    /**
     * 单位
     */
	@JsonProperty("cFeeUnit")
    @ApiModelProperty(value="单位")
	@JSONField(name="C_FEE_UNIT")
    private String cFeeUnit;

    /**
     * fapiaoKind
     */
    @ApiModelProperty(value="fapiaoKind")
	@JSONField(name="FAPIAO_KIND")
    private String fapiaoKind;

    /**
     * fapiaoDm
     */
    @ApiModelProperty(value="fapiaoDm")
	@JSONField(name="FAPIAO_DM")
    private String fapiaoDm;

    /**
     * fapiaoTime
     */
    @ApiModelProperty(value="fapiaoTime")
	@JSONField(name="FAPIAO_TIME")
    private Date fapiaoTime;

    /**
     * cancelTime
     */
    @ApiModelProperty(value="cancelTime")
	@JSONField(name="CANCEL_TIME")
    private Date cancelTime;

    /**
     * temp1
     */
    @ApiModelProperty(value="temp1")
	@JSONField(name="TEMP1")
    private String temp1;

    /**
     * temp2
     */
    @ApiModelProperty(value="temp2")
	@JSONField(name="TEMP2")
    private String temp2;

    /**
     * temp3
     */
    @ApiModelProperty(value="temp3")
	@JSONField(name="TEMP3")
    private String temp3;

    /**
     * 税收分类编码(必填，找业务人员去税务系统里查找)
     */
    @ApiModelProperty(value="税收分类编码(必填，找业务人员去税务系统里查找)")
	@JSONField(name="SLFLBM")
    private String slflbm;

    /**
     * 序列号
     */
    @ApiModelProperty(value="序列号")
	@JSONField(name="PKID")
    private Integer pkid;

}
