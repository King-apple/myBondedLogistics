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
@ApiModel(value = "货类")
public class CargoKindTypeVo implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * 月份
	 */
	@ApiModelProperty(value = "月份")
	private String mon;

	/**
	 * 当年入库量
	 */
	@ApiModelProperty(value = "当年入库量")
	private String yearNewInNetWet;

	/**
	 * 去年入库量
	 */
	@ApiModelProperty(value = "去年入库量")
	private String yearOldInNetWet;


	/**
	 * 入库量同比
	 */
	@ApiModelProperty(value = "入库量同比")
	private String yearInComparedWet;

	/**
	 * 当年出库量
	 */
	@ApiModelProperty(value = "当年出库量")
	private String yearNewOutNetWet;

	/**
	 * 去年出库量
	 */
	@ApiModelProperty(value = "去年出库量")
	private String yearOldOutNetWet;

	/**
	 * 出库量同比
	 */
	@ApiModelProperty(value = "出库量同比")
	private String yearOutComparedWet;

	@ApiModelProperty(value = "时间")
	private String nowYear;

	/**
	 * 去年当月
	 */
	@ApiModelProperty(value = "去年当月")
	private String oldYear;

	/**
	 * 结束年
	 */
	@ApiModelProperty(value = "货类")
	private String cargoKindNam;


}
