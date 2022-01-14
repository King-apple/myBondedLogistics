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
import java.util.Date;

/**
 * 客户信息表
 *
 * @author pigx code generator
 * @date 2021-07-30 08:53:57
 */
@Data
@TableName("CLIENT_INFO")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "客户信息表")
public class ClientInfo extends Model<ClientInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="主键")
    private Long fId;

    /**
     * 公司代码
     */
    @ApiModelProperty(value="公司代码")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value="公司名称")
	@TableField(condition = SqlCondition.like)
    private String company;

    /**
     * 营业执照号
     */
    @ApiModelProperty(value="营业执照号")
    private String businessLicenseNumber;

    /**
     * 营业执照照片（反）
     */
    @ApiModelProperty(value="营业执照照片（反）")
    private String licensePhotoBack;

    /**
     * 营业执照照片（反）
     */
    @ApiModelProperty(value="营业执照照片（反）")
    private String licensePhotoFront;

    /**
     * 法人
     */
    @ApiModelProperty(value="法人")
    private String legalPerson;

    /**
     * 法人证件号码
     */
    @ApiModelProperty(value="法人证件号码")
    private String certificateNumber;

    /**
     * 法人证件照片（正）
     */
    @ApiModelProperty(value="法人证件照片（正）")
    private String certificatePhotoFront;

    /**
     * 法人证件照片（反）
     */
    @ApiModelProperty(value="法人证件照片（反）")
    private String certificatePhotoBack;

    /**
     * 公司地址
     */
    @ApiModelProperty(value="公司地址")
    private String address;

    /**
     * 公司电话
     */
    @ApiModelProperty(value="公司电话")
    private String companyPhone;

    /**
     * 银行账号
     */
    @ApiModelProperty(value="银行账号")
    private String bankAccount;

    /**
     * 开户行名称
     */
    @ApiModelProperty(value="开户行名称")
    private String openingBank;

    /**
     * 开户名
     */
    @ApiModelProperty(value="开户名")
    private String accountName;

    /**
     * 联系人姓名
     */
    @ApiModelProperty(value="联系人姓名")
    private String contactName;

    /**
     * 联系人电话
     */
    @ApiModelProperty(value="联系人电话")
    private String contactPhone;

    /**
     * 联系人邮箱
     */
    @ApiModelProperty(value="联系人邮箱")
    private String contactMail;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String markTxt;

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

}
