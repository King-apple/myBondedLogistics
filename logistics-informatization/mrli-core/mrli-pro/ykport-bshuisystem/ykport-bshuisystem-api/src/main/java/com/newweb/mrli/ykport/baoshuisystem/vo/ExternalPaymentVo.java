package com.newweb.mrli.ykport.baoshuisystem.vo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.newweb.mrli.ykport.baoshuisystem.entity.BusinessBillsBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.ContractRate;
import com.newweb.mrli.ykport.baoshuisystem.entity.SysFile;
import com.newweb.mrli.ykport.baoshuisystem.utils.ExcelUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.JdbcType;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("结算管理")
//头和体一共的
public class ExternalPaymentVo extends Model<ExternalPaymentVo> {
	//根据日期时间段来区分
	private String[] params;
	/**
	 * 主键
	 */
	@ApiModelProperty(value="主键")
	@JsonProperty("fId")
	private Long fId;

	/**
	 * 公司名称
	 */
	@ApiModelProperty(value="公司名称")
	private String companyNam;

	/**
	 * 单据类型
	 */
	@ApiModelProperty(value="单据类型")
	private String billKnd;

	/**
	 * 单据名称
	 */
	@ApiModelProperty(value="单据名称")
	private String billNam;

	/**
	 * 付款编号
	 */
	@ApiModelProperty(value="付款编号")
	private String billCod;

	/**
	 * 合同编号
	 */
	@ApiModelProperty(value="合同编号")
	private String contCod;

	/**
	 * 承办部门
	 */
	@ApiModelProperty(value="承办部门")
	private String dept;

	/**
	 * 部门id
	 */
	@ApiModelProperty(value="部门id")
	private String deptId;

	/**
	 * 日期
	 */
	@ApiModelProperty(value="日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ExcelUtil.ExcelAttribute(column = "1",name = "结算日期")
	private Date billDate;

	/**
	 * 客户代码
	 */
	@ApiModelProperty(value="客户代码")
	private String secondCod;

	/**
	 * 客户
	 */
	@ExcelUtil.ExcelAttribute(column = "3",name = "客户")
	@ApiModelProperty(value="客户")
	private String secondNam;

	/**
	 * 船名
	 */
	@ApiModelProperty(value="船名")
	private String shipNam;

	/**
	 * 货名代码
	 */
	@ApiModelProperty(value="货名代码")
	private String cargoCod;

	/**
	 * 货名
	 */
	@ExcelUtil.ExcelAttribute(column = "2",name = "货名")
	@ApiModelProperty(value="货名")
	private String cargoNam;

	/**
	 * 计费数量
	 */
	@ApiModelProperty(value="计费数量")
	@ExcelUtil.ExcelAttribute(column = "4",name = "作业量")
	private BigDecimal feeQuantity;

	/**
	 * 计费单位
	 */
	@ApiModelProperty(value="计费单位")
	private String feeNam;

	/**
	 * 经办人
	 */
	@ApiModelProperty(value="经办人")
	private String operator;

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

	@ApiModelProperty(value="费目费率集合")
	private List<BusinessBillsBody> bbBodyList;

	/**
	 * 文件集合
	 */
	@ApiModelProperty(value="文件集合")
	private List<SysFile> sysFileList;
	@ApiModelProperty(value="承办单位")
	private String firstNam;
	@ApiModelProperty(value="承办单位代码")
	private String firstCod;
	private String invoiceType;
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
	@ExcelUtil.ExcelAttribute(column = "5",name = "入境船名")
	@ApiModelProperty(value="入境船名")
	private String inShipName;

	/**
	 * 出境船名
	 */
	@ExcelUtil.ExcelAttribute(column = "5",name = "出境船名")
	@ApiModelProperty(value="出境船名")
	private String outShipName;

	/**
	 * 录入方式(0:选择录入 ；1:手动录入)
	 */
	@ApiModelProperty(value="录入方式")
	private String inputType;

	/**
	 * 付款单位
	 */
	@ApiModelProperty(value="付款单位")
	private String paymentUnit;

	/**
	 * 收款单位
	 */
	@ApiModelProperty(value="收款单位")
	private String ceceivingUnit;

	/**
	 * 收款单位
	 */
	@ApiModelProperty(value="总利润")
	@ExcelUtil.ExcelAttribute(column = "5",name = "利润")
	private String totalMoney;

	/**
	 * 年份
	 */
	@ApiModelProperty(value="年份")
	private String year;

	/**
	 * 内外贸
	 */
	@ApiModelProperty(value="内外贸")
	@ExcelUtil.ExcelAttribute(column = "6",name = "备注（内外贸）")
	private String tradeId;

	/**
	 * 装船
	 */
	@ApiModelProperty(value="装船")
	@ExcelUtil.ExcelAttribute(column = "6",name = "装船")
	private String loadShip;

	/**
	 * 装船
	 */
	@ApiModelProperty(value="卸船")
	@ExcelUtil.ExcelAttribute(column = "6",name = "卸船")
	private String unloadShip;

	@ApiModelProperty(value="装卸船")
	private String loadingShip;

	@ApiModelProperty(value="一日游费用")
	@JsonProperty("oneDayTour")
	private String oneDayTour;
}
