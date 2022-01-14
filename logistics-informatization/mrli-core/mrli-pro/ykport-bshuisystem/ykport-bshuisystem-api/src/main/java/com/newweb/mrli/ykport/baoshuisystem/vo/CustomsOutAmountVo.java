package com.newweb.mrli.ykport.baoshuisystem.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author mrli
 * @since 2017-10-29
 */
@Data
@ApiModel(value = "出口贸易额")
public class CustomsOutAmountVo implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * 货种
	 */
	@ApiModelProperty(value = "货种")
	private String cargoNamCustoms;

	/**
	 * 货类
	 */
	@ApiModelProperty(value = "货类")
	private String cargoKindNam;

	/**
	 * 当月出货额
	 */
	@ApiModelProperty(value = "当月出货额")
	private String mOutCargoAccount;

	/**
	 * 去年当月出货额
	 */
	@ApiModelProperty(value = "去年当月出货额")
	private String mOldoutCargoAccount;


	/**
	 * 当年上个月出货额
	 */
	@ApiModelProperty(value = "当年上个月出货额")
	private String mUpoutCargoAccount;

	/**
	 * 当月同比
	 */
	@ApiModelProperty(value = "当月同比")
	private String mComparedCargoAccount;

	/**
	 * 当月环比
	 */
	@ApiModelProperty(value = "当月环比")
	private String mRingCargoAccout;

	/**
	 * 当年出货额
	 */
	@ApiModelProperty(value = "当年出货额")
	private String yearOutCargoAccount;

	/**
	 * 去年出货额
	 */
	@ApiModelProperty(value = "去年出货额")
	private String yearOldoutCargoAccount;

	/**
	 * 当年同比
	 */
	@ApiModelProperty(value = "当年同比")
	private String yearComparedCargoAccount;

	@ApiModelProperty(value = "时间")
	private String inDte;

	/**
	 * 去年当月
	 */
	@ApiModelProperty(value = "去年当月")
	private String oldYearMonth;

	/**
	 * 结束年
	 */
	@ApiModelProperty(value = "当前年上个月")
	private String yearOldMonth;

	/**
	 * 当前年
	 */
	@ApiModelProperty(value = "当前年")
	private String year;

	/**
	 * 去年
	 */
	@ApiModelProperty(value = "去年")
	private String oldYear;
}
