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
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 计费数据头信息
 *
 * @author pigx code generator
 * @date 2021-11-05 13:58:42
 */
@Data
@TableName("BILL_DATA_HEAD")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "计费数据头信息")
@KeySequence(value = "SEQ_BILL_DATA_HEAD", clazz = Integer.class)
public class BillDataHead extends Model<BillDataHead> {

    private static final long serialVersionUID = 1L;

    /**
     * 业务单号
     */

    @ApiModelProperty(value="业务单号")
	@JSONField(name="FEE_BILL_NO")
    private String feeBillNo;

    /**
     * 船号
     */
    @ApiModelProperty(value="船号")
	@JSONField(name="SHIP_NO")
    private Integer shipNo;

    /**
     * 船名
     */
    @ApiModelProperty(value="船名")
	@JSONField(name="SHIP_NAM")
    private String shipNam;

    /**
     * 英文船名
     */
    @ApiModelProperty(value="英文船名")
	@JSONField(name="E_SHIP_NAM")
    private String eShipNam;

    /**
     * 航次
     */
    @ApiModelProperty(value="航次")
	@JSONField(name="VOYAGE")
    private String voyage;

    /**
     * 内外贸
     */
    @ApiModelProperty(value="内外贸")
	@JSONField(name="TRADE_ID")
    private Integer tradeId;

    /**
     * 进出口
     */
    @ApiModelProperty(value="进出口")
	@JSONField(name="INOUT_ID")
    private Integer inoutId;

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
     * 靠泊时间
     */
    @ApiModelProperty(value="靠泊时间")
	@JSONField(name="TO_BERTH_TIM")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    private Date toBerthTim;

    /**
     * 离港时间
     */
    @ApiModelProperty(value="离港时间")
	@JSONField(name="LEV_PORT_TIM")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    private Date levPortTim;

    /**
     * 合同号
     */
    @ApiModelProperty(value="合同号")
	@JSONField(name="CONT_NO")
    private String contNo;

    /**
     * 单号
     */
    @ApiModelProperty(value="单号")
	@JSONField(name="BILL_NO")
    private String billNo;

    /**
     * 净吨
     */
    @ApiModelProperty(value="净吨")
	@JSONField(name="NET_WGT")
    private BigDecimal netWgt;

    /**
     * 往来港代码
     */
    @ApiModelProperty(value="往来港代码")
	@JSONField(name="FROM_PORT_COD")
    private String fromPortCod;

    /**
     * 往来港名称
     */
    @ApiModelProperty(value="往来港名称")
	@JSONField(name="FROM_PORT_COD")
    private String FROM_PORT_NAM;

    /**
     * 船方/货方
     */
    @ApiModelProperty(value="船方/货方")
	@JSONField(name="CODE")
    private String code;

    /**
     * 是否作业完成
     */
    @ApiModelProperty(value="是否作业完成")
	@JSONField(name="IFEND_ID")
    private Integer ifendId;

    /**
     * 操作员
     */
    @ApiModelProperty(value="操作员")
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
     * 单位代码
     */
    @ApiModelProperty(value="单位代码")
	@JSONField(name="UNIT_NAM")
    private String unitNam;

    /**
     * 单位名称
     */
    @ApiModelProperty(value="单位名称")
	@JSONField(name="UNIT_COD")
    private String unitCod;

    /**
     * cId
     */
    @ApiModelProperty(value="cId")
	@JSONField(name="C_ID")
    private String cId;

    /**
     * up
     */
    @ApiModelProperty(value="up")
	@JSONField(name="UP")
    private String up;

    /**
     * 开票类型
     */
    @ApiModelProperty(value="开票类型")
	@JSONField(name="KP_KIND")
    private String kpKind;

    /**
     * 客户地址
     */
    @ApiModelProperty(value="客户地址")
	@JSONField(name="SHIPPER_ADD")
    private String shipperAdd;

    /**
     * 开户行
     */
    @ApiModelProperty(value="开户行")
	@JSONField(name="SHIPPER_ACN")
    private String shipperAcn;

    /**
     * 纳税人识别号
     */
    @ApiModelProperty(value="纳税人识别号")
	@JSONField(name="SHIPPER_IRD_NO")
    private String shipperIrdNo;

    /**
     * printId
     */
    @ApiModelProperty(value="printId")
	@JSONField(name="PRINT_ID")
    private String printId;

    /**
     * 是否开票状态标志
     */
    @ApiModelProperty(value="是否开票状态标志")
	@JSONField(name="UP_DF")
    private String upDf;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
	@JSONField(name="REMARK")
    private String remark;

    /**
     * 收款人
     */
    @ApiModelProperty(value="收款人")
	@JSONField(name="MONEY_WAY_NAM")
    private String moneyWayNam;

    /**
     * 审核人
     */
    @ApiModelProperty(value="审核人")
	@JSONField(name="END_CHECKER_NAM")
    private String endCheckerNam;

    /**
     * 销方地址
     */
    @ApiModelProperty(value="销方地址")
	@JSONField(name="XF_SHIPPER_ADD")
    private String xfShipperAdd;

    /**
     * 销方银行
     */
    @ApiModelProperty(value="销方银行")
	@JSONField(name="XF_SHIPPER_ACN")
    private String xfShipperAcn;

    /**
     * 销方纳税人识别号
     */
    @ApiModelProperty(value="销方纳税人识别号")
	@JSONField(name="XF_SHIPPER_IRD_NO")
    private String xfShipperIrdNo;

    /**
     * 红冲发票信息表编号
     */
    @ApiModelProperty(value="红冲发票信息表编号")
	@JSONField(name="NEGATIVE_INFO")
    private String negativeInfo;

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
     * shipperAdd2
     */
    @ApiModelProperty(value="shipperAdd2")
	@JSONField(name="SHIPPER_ADD2")
    private String shipperAdd2;

    /**
     * drawPerson
     */
    @ApiModelProperty(value="drawPerson")
	@JSONField(name="DRAW_PERSON")
    private String drawPerson;

    /**
     * drawDate
     */
    @ApiModelProperty(value="drawDate")
	@JSONField(name="DRAW_DATE")
    private String drawDate;

    /**
     * drawCompany
     */
    @ApiModelProperty(value="drawCompany")
	@JSONField(name="DRAW_COMPANY")
    private String drawCompany;

    /**
     * 是否已经上传到发票系统
     */
    @ApiModelProperty(value="是否已经上传到发票系统")
	@JSONField(name="IF_UP_XY")
    private String ifUpXy;

    /**
     * 发票种类
     */
    @ApiModelProperty(value="发票种类")
	@JSONField(name="FPZL")
    private String fpzl;

    /**
     * 销方电话
     */
    @ApiModelProperty(value="销方电话")
	@JSONField(name="XFDH")
    private String xfdh;

    /**
     * 序列号
     */
	@TableId(type = IdType.INPUT)
    @ApiModelProperty(value="序列号")
	@JSONField(name="PKID")
    private Integer pkid;

    /**
     * 是否分票
     */
    @ApiModelProperty(value="是否分票")
	@JSONField(name="IF_SPLIT")
    private String ifSplit;

    /**
     * 购方地址
     */
    @ApiModelProperty(value="购方地址")
	@JSONField(name="GF_ADDRESS")
    private String gfAddress;

    /**
     * 购方电话
     */
    @ApiModelProperty(value="购方电话")
	@JSONField(name="GF_PHONE")
    private String gfPhone;

    /**
     * 购方开户行
     */
    @ApiModelProperty(value="购方开户行")
	@JSONField(name="GF_BANK")
    private String gfBank;

    /**
     * 购方账号
     */
    @ApiModelProperty(value="购方账号")
	@JSONField(name="GF_ACCONT")
    private String gfAccont;

    /**
     * 是否作废
     */
    @ApiModelProperty(value="是否作废")
	@JSONField(name="IF_ZF")
    private String ifZf;

    /**
     * feeBillNoPkid
     */
    @ApiModelProperty(value="feeBillNoPkid")
	@JSONField(name="FEE_BILL_NO_PKID")
    private String feeBillNoPkid;

	/**
	 * 表体集合
	 */
	@TableField(exist = false)
	@ApiModelProperty(value="表体集合")
	@JSONField(serialize=false)
	private List<BillDataBody> billDataBodyList;

	/**
	 * 结算单编号
	 */
	@TableField(exist = false)
	@ApiModelProperty(value="结算单编号")
	@JSONField(serialize=false)
	private String billApprovalNo;


}
