package com.newweb.mrli.ykport.baoshuisystem.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class BillApproveAppProSetVo extends Model<BillApproveAppProSetVo> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.INPUT)
	@ApiModelProperty(value="主键")
	@JsonProperty("fId")
	private Integer fId;

	/**
	 * 单据主键
	 */
	@ApiModelProperty(value="单据主键")
	@JsonProperty("fFid")
	private Integer fFid;

	/**
	 * 单据编号
	 */
	@ApiModelProperty(value="单据编号")
	@JsonProperty("billCod")
	private String billCod;

	/**
	 * 单据类型
	 */
	@ApiModelProperty(value="单据类型")
	@JsonProperty("billType")
	private String billType;

	/**
	 * 日期
	 */
	@ApiModelProperty(value="日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonProperty("billDat")
	private Date billDat;

	/**
	 * 承办单位
	 */
	@ApiModelProperty(value="承办单位")
	@JsonProperty("firstNam")
	private String firstNam;

	/**
	 * 承办单位代码
	 */
	@ApiModelProperty(value="承办单位代码")
	@JsonProperty("firstCod")
	private String firstCod;

	/**
	 * 部门
	 */
	@ApiModelProperty(value="部门")
	@JsonProperty("dept")
	private String dept;

	/**
	 * 经办人
	 */
	@ApiModelProperty(value="经办人")
	@JsonProperty("operator")
	private String operator;

	/**
	 * 合同客户代码
	 */
	@ApiModelProperty(value="合同客户代码")
	@JsonProperty("secondCod")
	private String secondCod;

	/**
	 * 合同客户名称
	 */
	@ApiModelProperty(value="合同客户名称")
	@JsonProperty("secondNam")
	private String secondNam;

	/**
	 * 合同编号
	 */
	@ApiModelProperty(value="合同编号")
	@JsonProperty("contNo")
	private String contNo;

	/**
	 * 船名
	 */
	@ApiModelProperty(value="船名")
	@JsonProperty("shipNam")
	private String shipNam;

	/**
	 * 业务种类
	 */
	@ApiModelProperty(value="业务种类")
	@JsonProperty("businessType")
	private String businessType;

	/**
	 * 货物代码
	 */
	@ApiModelProperty(value="货物代码")
	@JsonProperty("cargoCod")
	private String cargoCod;

	/**
	 * 货物名称
	 */
	@ApiModelProperty(value="货物名称")
	@JsonProperty("cargoNam")
	private String cargoNam;

	/**
	 * 结算账户名称
	 */
	@ApiModelProperty(value="结算账户名称")
	@JsonProperty("accountNam")
	private String accountNam;

	/**
	 * 计费数量
	 */
	@ApiModelProperty(value="计费数量")
	@JsonProperty("settleWgt")
	private Integer settleWgt;

	/**
	 * 金额（元）
	 */
	@ApiModelProperty(value="金额（元）")
	@JsonProperty("money")
	private Integer money;

	/**
	 * 垫款金额（元）
	 */
	@ApiModelProperty(value="垫款金额（元）")
	@JsonProperty("advinceMoney")
	private Integer advinceMoney;

	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	@JsonProperty("markTxt")
	private String markTxt;

	/**
	 * 添加人
	 */
	@ApiModelProperty(value="添加人")
	@JsonProperty("operNam")
	private String operNam;

	/**
	 * 添加时间
	 */
	@ApiModelProperty(value="添加时间")
	@JsonProperty("recordTim")
	private Date recordTim;

	/**
	 * 修改人
	 */
	@ApiModelProperty(value="修改人")
	@JsonProperty("updateNam")
	private String updateNam;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value="修改时间")
	@JsonProperty("updateTim")
	private Date updateTim;

	/**
	 * 删除人
	 */
	@ApiModelProperty(value="删除人")
	@JsonProperty("delNam")
	private String delNam;

	/**
	 * 删除时间
	 */
	@ApiModelProperty(value="删除时间")
	@JsonProperty("delTim")
	private Date delTim;
	/**
	 * 状态
	 */
	@JsonProperty("approveStatus")
	private int approveStatus;

	@JsonProperty("approvalProcessSetFid")
	private Integer approvalProcessSetFid;

	@JsonProperty("flg")
	private Integer flg;

	@JsonProperty("opinion")
	private String opinion;

	@JsonProperty("opinionExplain")
	private String opinionExplain;

	@JsonProperty("times")
	private Integer times;


	@JsonProperty("username")
	private String username;

	/** 请求参数 */
	@ApiModelProperty(value="请求参数")
	@JsonProperty("params")
	private String [] params;


	@JsonProperty("isAgree")
	private String isAgree;
}
