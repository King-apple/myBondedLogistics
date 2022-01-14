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
import com.newweb.mrli.ykport.baoshuisystem.entity.ApprovalProcess;
import com.newweb.mrli.ykport.baoshuisystem.entity.ApprovalRecord;
import com.newweb.mrli.ykport.baoshuisystem.entity.ContractRate;
import com.newweb.mrli.ykport.baoshuisystem.entity.SysFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 合同信息管理表
 *
 * @author pigx code generator
 * @date 2021-08-03 09:54:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "合同信息文件管理表")
public class ContractFileVo extends Model<ContractFileVo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
	@JsonProperty("fId")
    private Long fId;

	/**
	 * 合同名称
	 */
	@ApiModelProperty(value="合同名称")
	private String contNam;

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
	 * 部门
	 */
	@ApiModelProperty(value="部门")
	private String dept;

	/**
	 * 经办人
	 */
	@ApiModelProperty(value="经办人")
	private String operator;

	/**
	 * 对方签约单位
	 */
	@ApiModelProperty(value="对方签约单位")
	private String secondNam;

	/**
	 * 合同类型
	 */
	@ApiModelProperty(value="合同类型")
	private String contractType;

	/**
	 * 合同标的
	 */
	@ApiModelProperty(value="合同标的")
	private String cargoNam;

	/**
	 * 合同标的数量
	 */
	@ApiModelProperty(value="合同标的数量")
	private Double cargoNum;

	/**
	 * 标的额
	 */
	@ApiModelProperty(value="标的额")
	private BigDecimal cargoFee;

	/**
	 * 合同开始日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
	@ApiModelProperty(value="合同开始日期")
	private Date startDte;

	/**
	 * 合同结束日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
	@ApiModelProperty(value="合同结束日期")
	private Date endDte;

	/**
	 * 合同签订日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
	@ApiModelProperty(value="合同签订日期")
	private Date conDte;

	/**
	 * 结算依据
	 */
	@ApiModelProperty(value="结算依据")
	private String settlementBasis;

	/**
	 * 结算方式
	 */
	@ApiModelProperty(value="结算方式")
	private String clearingForm;

	/**
	 * 客户联系人
	 */
	@ApiModelProperty(value="客户联系人")
	private String contact;

	/**
	 * 联系电话
	 */
	@ApiModelProperty(value="联系电话")
	private String phone;

	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String markTxt;

	/**
	 * 附件
	 */
	@ApiModelProperty(value="附件")
	private String urlFile;

	/**
	 * 是否作废（1：已作废）
	 */
	@ApiModelProperty(value="是否作废（1：已作废）")
	private String isCancel;

	/**
	 * 进入审批流次数
	 */
	@ApiModelProperty(value="进入审批流次数")
	private Integer times;

	/**
	 * 合同状态（0：未审批，1：审批中，2：审批完成）
	 */
	@ApiModelProperty(value="合同状态（0：未审批，1：审批中，2：审批完成）")
	private String contractStatus;

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
	 * 甲方代码
	 */
	@ApiModelProperty(value="甲方代码")
	private String firstCod;

	/**
	 * 乙方代码
	 */
	@ApiModelProperty(value="乙方代码")
	private String secondCod;

	/**
	 * 规格
	 */
	@ApiModelProperty(value="规格")
	private String format;

	/**
	 * 船名
	 */
	@ApiModelProperty(value="船名")
	private String shipNam;

	/**
	 * 关联主合同编号
	 */
	@ApiModelProperty(value="关联主合同编号")
	private String contNoMaster;

	/**
	 * 合同标的编码
	 */
	@ApiModelProperty(value="合同标的编码")
	private String cargoCod;

	/**
	 * 包装
	 */
	@ApiModelProperty(value="包装")
	private String pkgKindNam;

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

	/** 请求参数 */
	@ApiModelProperty(value="请求参数")
	private String [] params;

	/** 请求参数 */
	@ApiModelProperty(value="请求参数")
	private String [] conDteParams;

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
	 * 审批流程表主键
	 */
	@ApiModelProperty(value="审批流程表主键")
	@JsonProperty("pFid")
	private String pFid;

	/**
	 * 审批记录表主键
	 */
	@ApiModelProperty(value="审批记录表主键")
	@JsonProperty("rFid")
	private String rFid;

	/**
	 * 审批意见
	 */
	@ApiModelProperty(value="审批意见")
	private String opinion;

	/**
	 * 审批意见说明
	 */
	@ApiModelProperty(value="审批意见说明")
	private String opinionExplain;

	/**
	 * 审批意见说明
	 */
	@ApiModelProperty(value="是否同意")
	private String isAgree;

	/**
	 * 审批意见说明
	 */
	@ApiModelProperty(value="部门名称")
	private String linkNam;

	/**
	 * 审批意见说明
	 */
	@ApiModelProperty(value="部门名称列表")
	private String[] linkNamArray;

	/**
	 * 审批意见说明
	 */
	@ApiModelProperty(value="记录是否取消")
	private String recIsCancel;

	/**
	 * 审批意见说明
	 */
	@ApiModelProperty(value="所属公司")
	private String company;

	/**
	 * 审核排序
	 */
	@ApiModelProperty(value="审核排序")
	private String orderBy;

	/**
	 * 部门编码
	 */
	@ApiModelProperty(value="部门编码")
	private String deptNo;

	/**
	 * 审批意见说明
	 */
	@ApiModelProperty(value="部门名称列表")
	private List<ApprovalProcess> approvalProcess;

	/**
	 * 审批意见说明
	 */
	@ApiModelProperty(value="部门名称列表")
	private String[] approvalProcessArray;

	/**
	 * 合同归档
	 */
	@ApiModelProperty(value="合同归档")
	private String conFile;

	/**
	 * 存放地点
	 */
	@ApiModelProperty(value="存放地点")
	private String locate;

	/**
	 * 内外贸
	 */
	@ApiModelProperty(value="内外贸")
	private Integer tradeId;

	/**
	 * 存放地点
	 */
	@ApiModelProperty(value="进出口")
	private Integer inoutId;


	/**
	 * 标的量单位
	 */
	@ApiModelProperty(value="标的量单位")
	private String cargoNumUnit;

	/**
	 * 标的额单位
	 */
	@ApiModelProperty(value="标的额单位")
	private String cargoFeeUnit;

	/**
	 * 起草人部门id
	 */
	@ApiModelProperty(value="起草人部门id")
	private String userSubDeptId;

	/**
	 * 作废原因
	 */
	@ApiModelProperty(value="作废状态")
	private String cancelStatus;

	/**
	 * 作废原因
	 */
	@ApiModelProperty(value="作废原因")
	private String cancelReason;

	/**
	 * 流程编号
	 */
	@ApiModelProperty(value="流程编号")
	private String processNo;
	@ApiModelProperty(value="作废原因")
	private String completeApprovalFlag;
}
