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
@ApiModel(value = "操作量统计")
public class CustomsCargoVo implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * 货种
	 */
	@ApiModelProperty(value = "货种")
	private String consignNam;

	/**
	 * 货类
	 */
	@ApiModelProperty(value = "货类")
	private String cargoKindNam;


	/**
	 * 货主
	 */
	@ApiModelProperty(value = "货主")
	private String cargoNamCustoms;

	/**
	 * 当月入库量
	 */
	@ApiModelProperty(value = "当月入库量(吨)")
	private String mInNetWet;

	/**
	 * 月出库货值二线
	 */
	@ApiModelProperty(value = "当月出库量(吨)")
	private String mOutNetWet;


	/**
	 * 年入库货值一线
	 */
	@ApiModelProperty(value = "年入库货值一线")
	private String yInNetWet;

	/**
	 * 年入库货值二线
	 */
	@ApiModelProperty(value = "年入库货值二线")
	private String yOutNetWet;

	/**
	 * 年出库货值一线
	 */
	@ApiModelProperty(value = "库存")
	private String yKucun;

	@ApiModelProperty(value = "时间")
	private String inDte;

	/**
	 * 开始年
	 */
	@ApiModelProperty(value = "开始年")
	private String yearStart;

	/**
	 * 结束年
	 */
	@ApiModelProperty(value = "结束年")
	private String yearEnd;

}
