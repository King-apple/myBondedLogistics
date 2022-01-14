package com.newweb.mrli.ykport.baoshuisystem.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.newweb.mrli.ykport.baoshuisystem.entity.InvoiceDetailBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.InvoiceDetailPayment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@KeySequence(value = "SEQ_INVOICE_DETAIL_HEAD",clazz = Long.class)
public class InvoiceDetailHeadVo extends Model<IoyardListHeadBodyVo> {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.INPUT)
	@ApiModelProperty(value="主键")
	@JsonProperty("fId")
	private Integer fId;

	/**
	 * 审批单主键
	 */
	@ApiModelProperty(value="审批单主键")
	@JsonProperty("approveFid")
	private Integer approveFid;

	/**
	 * 结算单主键
	 */
	@ApiModelProperty(value="结算单主键")
	@JsonProperty("billFid")
	private Integer billFid;

	/**
	 * 结算单编号
	 */
	@ApiModelProperty(value="结算单编号")
	@JsonProperty("billCod")
	private String billCod;

	/**
	 * 对应账户
	 */
	@ApiModelProperty(value="对应账户")
	private String accountNam;

	/**
	 * 发票类型
	 */
	@ApiModelProperty(value="发票类型")
	private String invoiceType;

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
	 * 合计金额
	 */
	@ApiModelProperty(value="合计金额")
	private BigDecimal totalMoney;

	/**
	 * 船名
	 */
	@ApiModelProperty(value="船名")
	private String shipNam;

	/**
	 * 日期
	 */
	@ApiModelProperty(value="日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
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

	@ApiModelProperty(value="参数")
	private String [] params;

	private List<InvoiceDetailBody> invoiceDetailBodyList;

	private List<InvoiceDetailPayment> invoiceDetailPaymentList;

	@ApiModelProperty(value="转账")
	private String[] payment;

	/**
	 * 起草人部门id
	 */
	@ApiModelProperty(value="起草人部门id")
	private String deptId;
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
}
