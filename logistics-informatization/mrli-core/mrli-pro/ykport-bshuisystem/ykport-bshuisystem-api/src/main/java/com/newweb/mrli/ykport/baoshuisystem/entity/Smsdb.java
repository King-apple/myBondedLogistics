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
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * ${comments}
 *
 * @author pigx code generator
 * @date 2021-11-02 16:44:49
 */
@Data
@TableName("SMSDB")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "${comments}")
@KeySequence(value = "SEQ_SMSKEY",clazz = Integer.class)
public class Smsdb extends Model<Smsdb> {

    private static final long serialVersionUID = 1L;

    /**
     * 序列号ID
     */

    @ApiModelProperty(value="序列号ID")
    private String id;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    private String callnum;

    /**
     * 短消息内容
     */
    @ApiModelProperty(value="短消息内容")
    private String content;

    /**
     * 数据状态位:0(新)1(中心)2(成功-senddate)3(尝试-但如果sendtime>=5则停止发送)4(失败-手机号码或者信息内容有错)
     */
    @ApiModelProperty(value="数据状态位:0(新)1(中心)2(成功-senddate)3(尝试-但如果sendtime>=5则停止发送)4(失败-手机号码或者信息内容有错)")
    private String flag;

    /**
     * 发送日期,由短信引擎维护,外部系统无须管理.
     */
    @ApiModelProperty(value="发送日期,由短信引擎维护,外部系统无须管理.")
    private String senddate;

    /**
     * 发送时间.,由短信引擎维护,外部系统无须管理.
     */
    @ApiModelProperty(value="发送时间.,由短信引擎维护,外部系统无须管理.")
    private String sendtime;

    /**
     * ,由短信引擎维护,外部系统无须管理.
     */
    @ApiModelProperty(value=",由短信引擎维护,外部系统无须管理.")
    private Date createtime;

    /**
     * 消息来源,默认为OA
     */
    @ApiModelProperty(value="消息来源,默认为OA")
    private String msgcatalog;

    /**
     * 消息类型.针对消息来源与具体系统哪个功能模块
     */
    @ApiModelProperty(value="消息类型.针对消息来源与具体系统哪个功能模块")
    private String msgtype;

    /**
     * 用户ID
     */
    @ApiModelProperty(value="用户ID")
    private String userid;

    /**
     * rtxcode
     */
    @ApiModelProperty(value="rtxcode")
    private String rtxcode;

    /**
     * serveraccount
     */
    @ApiModelProperty(value="serveraccount")
    private String serveraccount;

    /**
     * forignid
     */
    @ApiModelProperty(value="forignid")
    private String forignid;

    /**
     * 信息单价
     */
    @ApiModelProperty(value="信息单价")
    private String price;

    /**
     * smsseqno
     */
	@TableId(type = IdType.INPUT)
    @ApiModelProperty(value="smsseqno")
    private Integer smsseqno;

}
