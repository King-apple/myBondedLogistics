package com.newweb.mrli.ykport.baoshuisystem.vo;



import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class CustomsAccountingStatisticsVo extends Model<CustomsAccountingStatisticsVo> {

	private static final long serialVersionUID = 1L;
	/**
	 * 入库范围时间
	 */

	private String[] inDteStr;
	private String[] outDteStr;
	/**
	 * 主键
	 */
	@ApiModelProperty(value="主键")
	@JsonProperty("fId")
	private Long fId;

	/**
	 * 货主
	 */
	@ApiModelProperty(value="货主")
	private String consignNam;

	/**
	 * 货品名
	 */
	@ApiModelProperty(value="货品名")
	private String cargoNamCustoms;

	/**
	 * 货类
	 */
	@ApiModelProperty(value = "货类")
	private String cargoKindNam;

	/**
	 * 入库日期
	 */
	@ApiModelProperty(value="入库日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private Date inDte;

	/**
	 * 入库毛重（kg）
	 */
	@ApiModelProperty(value="入库毛重（kg）")
	private Double inRoughWet;

	/**
	 * 入库净重（kg）
	 */
	@ApiModelProperty(value="入库净重（kg）")
	private Double inNetWet;

	/**
	 * 入库货品额（美元）
	 */
	@ApiModelProperty(value="入库货品额（美元）")
	private Double inCargoAccount;

	/**
	 * 原产国（入库）
	 */
	@ApiModelProperty(value="原产国（入库）")
	private String inOriginCountry;

	/**
	 * 最终目的地（入库）
	 */
	@ApiModelProperty(value="最终目的地（入库）")
	private String inFinalDestination;

	/**
	 * 核注清单号（入库）
	 */
	@ApiModelProperty(value="核注清单号（入库）")
	private String inCheckNum;

	/**
	 * 核对联（入库）
	 */
	@ApiModelProperty(value="核对联（入库）")
	private String inNuclearCouplet;

	/**
	 * 报关单号（入库）
	 */
	@ApiModelProperty(value="报关单号（入库）")
	private String inCustomsDeclarationNum;

	/**
	 * 识别代码（入库）
	 */
	@ApiModelProperty(value="识别代码（入库）")
	private String inIdentificationCod;

	/**
	 * 货票号（入库）
	 */
	@ApiModelProperty(value="货票号（入库）")
	private String inWaybillNum;

	/**
	 * 类别（进区）
	 */
	@ApiModelProperty(value="类别（进区）")
	private String inCategory;

	/**
	 * 出库日期
	 */
	@ApiModelProperty(value="出库日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern ="yyyy-MM-dd" )
	private Date outDte;

	/**
	 * 出库毛重（kg )
	 */
	@ApiModelProperty(value="出库毛重（kg )")
	private Double outRoughWet;

	/**
	 * 出库净重（kg）
	 */
	@ApiModelProperty(value="出库净重（kg）")
	private Double outNetWet;

	/**
	 * 出库货品额（美元）
	 */
	@ApiModelProperty(value="出库货品额（美元）")
	private Double outCargoAccount;

	/**
	 * 原产国（出库）
	 */
	@ApiModelProperty(value="原产国（出库）")
	private String outOriginCountry;

	/**
	 * 最终目的地（出库）
	 */
	@ApiModelProperty(value="最终目的地（出库）")
	private String outFinalDestination;

	/**
	 * 核注清单号（出库）
	 */
	@ApiModelProperty(value="核注清单号（出库）")
	private String outCheckNum;

	/**
	 * 核对联（出库）
	 */
	@ApiModelProperty(value="核对联（出库）")
	private String outNuclearCouplet;

	/**
	 * 报关单号（出库）
	 */
	@ApiModelProperty(value="报关单号（出库）")
	private String outCustomsDeclarationNum;

	/**
	 * 识别代码（出库）
	 */
	@ApiModelProperty(value="识别代码（出库）")
	private String outIdentificationCod;

	/**
	 * 货票号（出库）
	 */
	@ApiModelProperty(value="货票号（出库）")
	private String outWaybillNum;

	/**
	 * 类别（出区）
	 */
	@ApiModelProperty(value="类别（出区）")
	private String outCategory;

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


}
