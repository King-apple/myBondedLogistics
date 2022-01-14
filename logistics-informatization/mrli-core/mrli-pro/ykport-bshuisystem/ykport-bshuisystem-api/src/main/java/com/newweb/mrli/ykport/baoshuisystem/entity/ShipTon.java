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
 * ${comments}
 *
 * @author pigx code generator
 * @date 2021-10-27 09:49:48
 */
@Data
@TableName("ykport0.SHIP_TON")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "${comments}")
public class ShipTon extends Model<ShipTon> {

    private static final long serialVersionUID = 1L;

    /**
     * 是否自动,未使用
     */
    @ApiModelProperty(value="是否自动,未使用")
    private String contNo;

    /**
     * 内外贸
     */
    @ApiModelProperty(value="内外贸")
    private Integer tradeId;

    /**
     * 内外贸名称
     */
    @ApiModelProperty(value="内外贸名称")
    private String tradeNam;

    /**
     * 进出口
     */
    @ApiModelProperty(value="进出口")
    private Integer inoutId;

    /**
     * 进出口名称
     */
    @ApiModelProperty(value="进出口名称")
    private String inoutNam;

    /**
     * 进出港方式
     */
    @ApiModelProperty(value="进出港方式")
    private String portWay;

    /**
     * 发货人代码
     */
    @ApiModelProperty(value="发货人代码")
    private String setownerCod;

    /**
     * 发货人名称
     */
    @ApiModelProperty(value="发货人名称")
    private String setownerNam;

    /**
     * 收货人代码
     */
    @ApiModelProperty(value="收货人代码")
    private String getownerCod;

    /**
     * 收货人
     */
    @ApiModelProperty(value="收货人")
    private String getownerNam;

    /**
     * 船号
     */
    @ApiModelProperty(value="船号")
    private Integer shipNo;

    /**
     * 委托单号
     */
    @ApiModelProperty(value="委托单号")
    private String trustNo;

    /**
     * 提单号
     */
    @ApiModelProperty(value="提单号")
    private String billNo;

    /**
     * 计重方式
     */
    @ApiModelProperty(value="计重方式")
    private String countWay;

    /**
     * 磅号
     */
    @ApiModelProperty(value="磅号")
    private String poundNo;

    /**
     * 检算标志
     */
    @ApiModelProperty(value="检算标志")
    private Integer checkId;

    /**
     * 运力安排
     */
    @ApiModelProperty(value="运力安排")
    private String trafficPlan;

    /**
     * 委托人代码
     */
    @ApiModelProperty(value="委托人代码")
    private String trustManCod;

    /**
     * 委托人
     */
    @ApiModelProperty(value="委托人")
    private String trustMan;

    /**
     * 港口货主代码
     */
    @ApiModelProperty(value="港口货主代码")
    private String shipperCod;

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
     * 货类代码
     */
    @ApiModelProperty(value="货类代码")
    private String cargoKindCod;

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
     * 件数
     */
    @ApiModelProperty(value="件数")
    private Integer piecesNum;

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
     * 正负范围
     */
    @ApiModelProperty(value="正负范围")
    private Integer addMinRat;

    /**
     * 完成件数
     */
    @ApiModelProperty(value="完成件数")
    private Integer endPiecesNum;

    /**
     * 完成重量
     */
    @ApiModelProperty(value="完成重量")
    private Integer endWeightWgt;

    /**
     * 完成体积
     */
    @ApiModelProperty(value="完成体积")
    private Integer endCargoVol;

    /**
     * 完工时间
     */
    @ApiModelProperty(value="完工时间")
    private Date endTim;

    /**
     * 新货号
     */
    @ApiModelProperty(value="新货号")
    private Integer newCargoNo;

    /**
     * 新货名代码
     */
    @ApiModelProperty(value="新货名代码")
    private String newCargoNameCod;

    /**
     * 新货名
     */
    @ApiModelProperty(value="新货名")
    private String newCargoNam;

    /**
     * 新货类号
     */
    @ApiModelProperty(value="新货类号")
    private String newCargoKindCod;

    /**
     * 到港
     */
    @ApiModelProperty(value="到港")
    private String toPortNam;

    /**
     * 到港代码
     */
    @ApiModelProperty(value="到港代码")
    private String toPortCod;

    /**
     * 标记代码
     */
    @ApiModelProperty(value="标记代码")
    private String markCod;

    /**
     * 商检报检单号
     */
    @ApiModelProperty(value="商检报检单号")
    private String markKnd;

    /**
     * 作业方式代码
     */
    @ApiModelProperty(value="作业方式代码")
    private String workWayCod;

    /**
     * 作业方式
     */
    @ApiModelProperty(value="作业方式")
		private String workWay;

    /**
     * 委托单号
     */
    @ApiModelProperty(value="委托单号")
    private String checkNo;

    /**
     * 作业位置代码
     */
    @ApiModelProperty(value="作业位置代码")
    private String workPlaceCod;

    /**
     * 作业地点
     */
    @ApiModelProperty(value="作业地点")
    private String workPlace;

    /**
     * 保管单位代码
     */
    @ApiModelProperty(value="保管单位代码")
    private String subunitCod;

    /**
     * 科室队名称
     */
    @ApiModelProperty(value="科室队名称")
    private String subunitNam;

    /**
     * 单位代码
     */
    @ApiModelProperty(value="单位代码")
    private String unitCod;

    /**
     * 操作员
     */
    @ApiModelProperty(value="操作员")
    private String operNam;

    /**
     * 最终修改时间
     */
    @ApiModelProperty(value="最终修改时间")
    private Date updateTim;

    /**
     * 最终修改人
     */
    @ApiModelProperty(value="最终修改人")
    private String updateNam;

    /**
     * 记录时间
     */
    @ApiModelProperty(value="记录时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
    private Date recordTim;

    /**
     * 港口货主
     */
    @ApiModelProperty(value="港口货主")
    private String shipperDoc;

    /**
     * 计重方式代码
     */
    @ApiModelProperty(value="计重方式代码")
    private String countWayCod;

    /**
     * 船名
     */
    @ApiModelProperty(value="船名")
    private String shipNam;

    /**
     * 货类名称
     */
    @ApiModelProperty(value="货类名称")
    private String cargoKindNam;

    /**
     * 进出港方式
     */
    @ApiModelProperty(value="进出港方式")
    private String ioportWay;

    /**
     * 规格
     */
    @ApiModelProperty(value="规格")
    private String format;

    /**
     * 合同统计否（1统计0不统计）
     */
    @ApiModelProperty(value="合同统计否（1统计0不统计）")
    private String countId;

    /**
     * 合同中的堆存地点代码
     */
    @ApiModelProperty(value="合同中的堆存地点代码")
    private String contPlaceCod;

    /**
     * 合同中堆存地点
     */
    @ApiModelProperty(value="合同中堆存地点")
    private String contPlace;

    /**
     * 合同中的费率
     */
    @ApiModelProperty(value="合同中的费率")
    private Integer contFeeRat;

    /**
     * 合同计费单位代码
     */
    @ApiModelProperty(value="合同计费单位代码")
    private String contFeeUnitCod;

    /**
     * 合同计费单位
     */
    @ApiModelProperty(value="合同计费单位")
    private String contCFeeUnit;

    /**
     * 合同金额
     */
    @ApiModelProperty(value="合同金额")
    private Integer contFeeMny;

    /**
     * 是否自动
     */
    @ApiModelProperty(value="是否自动")
    private String autoId;

    /**
     * 件重
     */
    @ApiModelProperty(value="件重")
    private Integer piecesWgt;

    /**
     * 完成件重
     */
    @ApiModelProperty(value="完成件重")
    private Integer endPiecesWgt;

    /**
     * 装船清单号
     */
    @ApiModelProperty(value="装船清单号")
    private String shipBillNo;

    /**
     * 清单版本号
     */
    @ApiModelProperty(value="清单版本号")
    private Integer verNo;
//
//    /**
//     * ifendId1
//     */
//    @ApiModelProperty(value="ifendId1")
//    private String ifendId1;
//
//    /**
//     * 价格数据库价格编码
//     */
//    @ApiModelProperty(value="价格数据库价格编码")
//    private String priceNo;
//
//    /**
//     * 是否是电子理货
//     */
//    @ApiModelProperty(value="是否是电子理货")
//    private String ifEtwork;
//
//    /**
//     * 价格货主代码
//     */
//    @ApiModelProperty(value="价格货主代码")
//    private String shipperCodPrice;
//
//    /**
//     * 价格货主名称
//     */
//    @ApiModelProperty(value="价格货主名称")
//    private String shipperDocPrice;
//
//    /**
//     * 订购单号
//     */
//    @ApiModelProperty(value="订购单号")
//    private String orderContNo;
//
//    /**
//     * 杂作业费目名称
//     */
//    @ApiModelProperty(value="杂作业费目名称")
//    private String cFeeNam;
//
//    /**
//     * 杂作业费目代码
//     */
//    @ApiModelProperty(value="杂作业费目代码")
//    private String feeNameCod;
//
//    /**
//     * pkid
//     */
//    @ApiModelProperty(value="pkid")
//    private Integer pkid;

}
