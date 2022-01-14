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
 * 货物详细信息
 *
 * @author pigx code generator
 * @date 2021-09-28 10:33:37
 */
@Data
@TableName("BSWL.CARGO")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "货物详细信息")
public class CargoVo extends Model<CargoVo> {

    private static final long serialVersionUID = 1L;

    /**
     * 货主代码
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="货主代码")
    private String shipperCod;

    /**
     * 货主名称
     */
    @ApiModelProperty(value="货主名称")
    private String shipperDoc;

    /**
     * 建帐日期
     */
    @ApiModelProperty(value="建帐日期")
    private Date cargoDte;

    /**
     * 内外贸：1 内贸,0 外贸
     */
    @ApiModelProperty(value="内外贸：1 内贸,0 外贸")
    private Integer tradeId;

    /**
     * 进出口：1进0出
     */
    @ApiModelProperty(value="进出口：1进0出")
    private Integer inoutId;

    /**
     * 转口0不转1转口
     */
    @ApiModelProperty(value="转口0不转1转口")
    private Integer upwaitId;

    /**
     * 货类代码
     */
    @ApiModelProperty(value="货类代码")
    private String cargoKindCod;

    /**
     * 货类名称
     */
    @ApiModelProperty(value="货类名称")
    private String cargoKindNam;

    /**
     * 货号
     */
    @ApiModelProperty(value="货号")
    private Integer cargoNo;

    /**
     * 货名代码
     */
    @ApiModelProperty(value="货名代码")
    private String cargoNamCod;

    /**
     * 货名
     */
    @ApiModelProperty(value="货名")
    private String cargoNam;

    /**
     * 包装代码
     */
    @ApiModelProperty(value="包装代码")
    private String pkgKindCod;

    /**
     * 包装
     */
    @ApiModelProperty(value="包装")
    private String cPkgKindNam;

    /**
     * 常用规格(含量)
     */
    @ApiModelProperty(value="常用规格(含量)")
    private String format;

    /**
     * 件数
     */
    @ApiModelProperty(value="件数")
    private Integer piecesNum;

    /**
     * 件量
     */
    @ApiModelProperty(value="件量")
    private Integer piecesWgt;

    /**
     * 重量
     */
    @ApiModelProperty(value="重量")
    private Integer weightWgt;

    /**
     * 体积
     */
    @ApiModelProperty(value="体积")
    private Integer cargoVol;

    /**
     * 垛名(船名)
     */
    @ApiModelProperty(value="垛名(船名)")
    private String stackNam;

    /**
     * 航次
     */
    @ApiModelProperty(value="航次")
    private String voyage;

    /**
     * 完标：1.完成，0.未完
     */
    @ApiModelProperty(value="完标：1.完成，0.未完")
    private Integer ifendId;

    /**
     * 完标时间
     */
    @ApiModelProperty(value="完标时间")
    private Date endCargoTim;

    /**
     * 备注：双击弹出编辑框
     */
    @ApiModelProperty(value="备注：双击弹出编辑框")
    private String markTxt;

    /**
     * 单位代码
     */
    @ApiModelProperty(value="单位代码")
    private String unitCod;

    /**
     * 单位名称
     */
    @ApiModelProperty(value="单位名称")
    private String unitNam;

    /**
     * *大宗货  杂货
     */
    @ApiModelProperty(value="*大宗货  杂货")
    private Integer maxCargoId;

    /**
     * 子部门
     */
    @ApiModelProperty(value="子部门")
    private String subUnitId;

    /**
     * 长度
     */
    @ApiModelProperty(value="长度")
    private Integer longNum;

    /**
     * 宽度
     */
    @ApiModelProperty(value="宽度")
    private Integer wideNum;

    /**
     * 高度
     */
    @ApiModelProperty(value="高度")
    private Integer highNum;

    /**
     * 代理
     */
    @ApiModelProperty(value="代理")
    private String agentNam;

    /**
     * 包装文本
     */
    @ApiModelProperty(value="包装文本")
    private String contPackTxt;

    /**
     * 操作员
     */
    @ApiModelProperty(value="操作员")
    private String operNam;

    /**
     * 记录时间
     */
    @ApiModelProperty(value="记录时间")
    private Date recordTim;

    /**
     * 最终修改人
     */
    @ApiModelProperty(value="最终修改人")
    private String updateNam;

    /**
     * 最终修改时间
     */
    @ApiModelProperty(value="最终修改时间")
    private Date updateTim;

}
