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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.newweb.mrli.ykport.baoshuisystem.entity.ContractRate;
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardListBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.IoyardSettlementBillsBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.SysFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.JdbcType;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 出入库明细表头表
 *
 * @author pigx code generator
 * @date 2021-09-09 09:52:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@KeySequence(value = "SEQ_IOYARD_SETTLEMENT_HEAD",clazz = Long.class)
public class IoyardSettlementBilldHeadBodyVo extends Model<IoyardSettlementBilldHeadBodyVo> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.INPUT)
	@ApiModelProperty(value="主键")
	@JsonProperty("fId")
	private Long fId;

	/**
	 * 公司名称
	 */
	@ApiModelProperty(value="公司名称")
	private String companyNam;

	/**
	 * 单据名称
	 */
	@ApiModelProperty(value="单据名称")
	private String billNam;

	/**
	 * 客户代码
	 */
	@ApiModelProperty(value="客户代码")
	private String secondCod;

	/**
	 * 客户名称
	 */
	@ApiModelProperty(value="客户名称")
	private String secondNam;

	/**
	 * 出入库明细表主键
	 */
	@ApiModelProperty(value="出入库明细表主键")
	private Long listFid;

	/**
	 * 出入库明细编码
	 */
	@ApiModelProperty(value="出入库明细编码")
	private String ioyardListNo;

	/**
	 * 入境船名
	 */
	@ApiModelProperty(value="入境船名")
	private String inYardWay;

	/**
	 * 合同主键
	 */
	@ApiModelProperty(value="合同主键")
	private Integer contFid;

	/**
	 * 合同编号
	 */
	@ApiModelProperty(value="合同编号")
	private String contNo;

	/**
	 * 承办单位
	 */
	@ApiModelProperty(value="承办单位")
	private String firstNam;

	/**
	 * 承办单位代码
	 */
	@ApiModelProperty(value="承办单位代码")
	private String firstCod;

	/**
	 * 承办部门
	 */
	@ApiModelProperty(value="承办部门")
	private String dept;

	/**
	 * 货物diamante
	 */
	@ApiModelProperty(value="货物diamante")
	private String cargoCod;

	/**
	 * 货物名称
	 */
	@ApiModelProperty(value="货物名称")
	private String cargoNam;

	/**
	 * 出境船名
	 */
	@ApiModelProperty(value="出境船名")
	private String outYardWay;

	/**
	 * 结算单编号
	 */
	@ApiModelProperty(value="结算单编号")
	private String billCod;

	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String markTxt;

	/**
	 * 制表日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
	@ApiModelProperty(value="制表日期")
	private Date operDat;

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
	 * 发票类型
	 */
	@ApiModelProperty(value="发票类型")
	private String invoiceType;

	@ApiModelProperty(value="参数")
	private String [] params;

	@ApiModelProperty(value="表体")
	private List<IoyardSettlementBillsBody> ioYardSBBodyList;
	/**
	 * 文件集合
	 */
	@ApiModelProperty(value="文件集合")
	private List<SysFile> sysFileList;
	/**
	 * 文件集合
	 */
	@ApiModelProperty(value="费目费率集合")
	private List<ContractRate> contractRateList;

	@ApiModelProperty(value="出入库管理费总额")
	private BigDecimal managementMoney;

	@ApiModelProperty(value="出入库管理费总额")
	private BigDecimal storageMoney;
	@ApiModelProperty(value="出入库管理费费率")
	private BigDecimal managementRat;

	@ApiModelProperty(value="出入库管理费费率单位")
	private String managementRatUnit;

	@ApiModelProperty(value="保税仓储费费率")
	private BigDecimal storageRat;

	@ApiModelProperty(value="保税仓储费费率单位")
	private String storageRatUnit;
	@ApiModelProperty(value="总出库结算数量")
	private BigDecimal outWgt;
	@ApiModelProperty(value="总吨天数")
	private BigDecimal tonDay;

	/**
	 * 货类编码
	 */
	@ApiModelProperty(value="货类编码")
	private String cargoKindCod;

	/**
	 * 货类名称
	 */
	@ApiModelProperty(value="货类名称")
	private String cargoKindNam;

	/**
	 * 入境船名
	 */
	@ApiModelProperty(value="入境船名")
	private String inShipName;

	/**
	 * 出境船名
	 */
	@ApiModelProperty(value="出境船名")
	private String outShipName;

	/**
	 * 录入方式(0:选择录入 ；1:手动录入)
	 */
	@ApiModelProperty(value="录入方式")
	private String inputType;

	/**
	 * 部门id
	 */
	@ApiModelProperty(value="部门id")
	private String deptId;
}
