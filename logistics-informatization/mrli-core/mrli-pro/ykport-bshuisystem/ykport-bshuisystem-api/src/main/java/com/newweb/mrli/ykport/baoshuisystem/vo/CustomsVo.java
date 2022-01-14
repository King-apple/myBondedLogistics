package com.newweb.mrli.ykport.baoshuisystem.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class CustomsVo implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * 月入库货值一线
	 */
	@ApiModelProperty(value = "月入库货值一线")
	private String mInFirst;

	/**
	 * 月入库货值二线
	 */
	@ApiModelProperty(value = "月入库货值二线")
	private String mInSecond;

	/**
	 * 月出库货值一线
	 */
	@ApiModelProperty(value = "月出库货值一线")
	private String mOutFirst;

	/**
	 * 月出库货值二线
	 */
	@ApiModelProperty(value = "月出库货值二线")
	private String mOutSecond;


	/**
	 * 年入库货值一线
	 */
	@ApiModelProperty(value = "年入库货值一线")
	private String yInFirst;

	/**
	 * 年入库货值二线
	 */
	@ApiModelProperty(value = "年入库货值二线")
	private String yInSecond;

	/**
	 * 年出库货值一线
	 */
	@ApiModelProperty(value = "年出库货值一线")
	private String yOutFirst;

	/**
	 * 年出库货值二线
	 */
	@ApiModelProperty(value = "年出库货值二线")
	private String yOutSecond;
	/**
	 * 年
	 */
	@ApiModelProperty(value = "年")
	private String year;





	/**
	 * 货品名
	 */
	@ApiModelProperty(value = "货品名")
	private String cargoNamCustoms;


	/**
	 * 货类
	 */
	@ApiModelProperty(value = "货类")
	private String cargoKindNam;

	/**
	 * 月入库量
	 */
	@ApiModelProperty(value = "月入库量")
	private String mInNetWet;

	/**
	 * 月出库量
	 */
	@ApiModelProperty(value = "月出库量")
	private String mOutNetWet;


	/**
	 * 一线出口贸易额（万美元）
	 */
	@ApiModelProperty(value = "一线出口贸易额")
	private String mOutCargoAccount;


	/**
	 * 年累计入库量（吨）
	 */
	@ApiModelProperty(value = "年累计入库量")
	private String yInNetWet;

	/**
	 * 入库货值（万美元）
	 */
	@ApiModelProperty(value = "入库货值（万美元）")
	private String yInCargoAccount;

	/**
	 * 年累计出库量（吨）
	 */
	@ApiModelProperty(value = "年累计出库量（吨）")
	private String yOutNetWet;

	/**
	 * 出库货值（万美元）
	 */
	@ApiModelProperty(value = "出库货值（万美元）")
	private String yOutCargoAccount;

	/**
	 * 年累计一线出口贸易额（万美元）
	 */
	@ApiModelProperty(value = "年累计一线出口贸易额（万美元）")
	private String yFirstOutCargoAccount;

	/**
	 * 年累计操作量
	 */
	@ApiModelProperty(value = "年累计操作量")
	private String ySumNetWet;

	/**
	 * 累计操作量同比
	 */
	@ApiModelProperty(value = "累计操作量同比")
	private String yNetWetScale;

	/**
	 * 年累计进出口货值
	 */
	@ApiModelProperty(value = "年累计进出口货值")
	private String ySumCargoAccount;

	/**
	 * 累计进出口货值同比
	 */
	@ApiModelProperty(value = "年累计进出口货值")
	private String yCargoAccountScale;


	/**
	 * 年月
	 */
	@ApiModelProperty(value = "年月")
	private String inDte;

	/**
	 * 年月开始
	 */
	@ApiModelProperty(value = "年月")
	private String inDteStart;

	/**
	 * 年月结束
	 */
	@ApiModelProperty(value = "年月")
	private String inDteEnd;

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

	/**
	 * 旧开始年
	 */
	@ApiModelProperty(value = "旧开始年")
	private String oldYearStart;

	/**
	 * 旧结束年
	 */
	@ApiModelProperty(value = "旧结束年")
	private String oldYearEnd;

}
