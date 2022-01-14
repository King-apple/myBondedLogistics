package com.newweb.mrli.ykport.baoshuisystem.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.newweb.mrli.ykport.baoshuisystem.utils.ExcelUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 货代部收入统计视图
 * </p>
 *
 * @author mrli
 * @since 2017-10-29
 */
@Data
@ApiModel(value = "货代部收入统计视图")
public class IncomeStatistcsVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@JsonProperty("fId")
	private String fId;

	/**
	 * 月份
	 */
	@ApiModelProperty(value = "月份")
	@ExcelUtil.ExcelAttribute(column = "1",name = "月份")
	private String month;

	/**
	 * 结算日期
	 */
	@ApiModelProperty(value = "结算日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
	@ExcelUtil.ExcelAttribute(column = "1",name = "结算日期（入账）")
	private Date billDate;

	/**
	 * 客户
	 */
	@ApiModelProperty(value = "客户")
	@ExcelUtil.ExcelAttribute(column = "1",name = "客户")
	private String secondNam;

	/**
	 * 利润
	 */
	@ApiModelProperty(value = "利润")
	@ExcelUtil.ExcelAttribute(column = "1",name = "利润")
	private BigDecimal totalMoney;

	/**
	 * 件数
	 */
	@ApiModelProperty(value = "税后利润")
	@ExcelUtil.ExcelAttribute(column = "1",name = "代理费净收入（税后）")
	@JsonProperty("agencyTotalMoneyAfterTax")
	private BigDecimal agencyTotalMoneyAfterTax;

	/**
	 * 代理报关费利润
	 */
	@ApiModelProperty(value = "代理报关费利润")
	@JsonProperty("agencyCustomsDeclarationFee")
	@ExcelUtil.ExcelAttribute(column = "1",name = "本部门")
	private BigDecimal agencyCustomsDeclarationFee;

	/**
	 * 一日游费用
	 */
	@ApiModelProperty(value = "一日游费用")
	@ExcelUtil.ExcelAttribute(column = "1",name = "一日游")
	@JsonProperty("oneDayTour")
	private BigDecimal oneDayTour;

	/**
	 * 收入合计（含税）
	 */
	@ApiModelProperty(value = "收入合计（含税）")
	@JsonProperty("incomeWithTax")
	@ExcelUtil.ExcelAttribute(column = "1",name = "收入合计（含税）")
	private BigDecimal incomeWithTax;


	/**
	 * 收入合计（税后）
	 */
	@ApiModelProperty(value = "收入合计（税后）")
	@JsonProperty("incomeWithoutTax")
	@ExcelUtil.ExcelAttribute(column = "1",name = "净收入合计（税后）")
	private BigDecimal incomeWithoutTax;


}
