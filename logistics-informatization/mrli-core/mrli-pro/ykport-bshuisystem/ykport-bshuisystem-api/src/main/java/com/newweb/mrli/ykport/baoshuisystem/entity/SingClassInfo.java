package com.newweb.mrli.ykport.baoshuisystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.newweb.mrli.ykport.baoshuisystem.utils.ExcelUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("SING_CLASS_INFO")
@EqualsAndHashCode(callSuper = true)
public class SingClassInfo extends Model<SingClassInfo> {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键 是货票号 bh+班次 rqcbs
	 * */
	@TableId(type = IdType.INPUT)
	private String bhrqcbs;

	/**
	 * 代码
	 */
	@ApiModelProperty(value="代码")
	private String lxdm;

	/**
	 * 货票号
	 */
	@ApiModelProperty(value="货票号")
	private String bh;

	/**
	 * 货名
	 */
	@ApiModelProperty(value="货名")
	private String hm;

	/**
	 * 提单号
	 */
	@ApiModelProperty(value="提单号")
	private String tdh;

	/**
	 * 船名
	 */
	@ApiModelProperty(value="船名")
	private String cm;

	/**
	 * 计划量
	 */
	@ApiModelProperty(value="计划量")
	private String jhl;

	/**
	 * 完成车
	 */
	@ApiModelProperty(value="完成车")
	private String csl;

	/**
	 * 完成量
	 */
	@ExcelUtil.ExcelAttribute(column = "2",name = "出库量")
	@ApiModelProperty(value="完成量")
	private String wcl;

	/**
	 * 剩余量
	 */
	@ApiModelProperty(value="剩余量")
	private String syl;

	/**
	 * 本班车
	 */
	@ApiModelProperty(value="本班车")
	private String benc;

	/**
	 * 未回车
	 */
	@ApiModelProperty(value="未回车")
	private String weic;

	/**
	 * 本班货重
	 */
	@ApiModelProperty(value="本班货重")
	private String benhz;

	/**
	 * 称别组
	 */
	@ApiModelProperty(value="称别组")
	private String cbs;

	/**
	 * 申请单位
	 */
	@ApiModelProperty(value="申请单位")
	private String sqdw;

	/**
	 * 班次
	 */
	@ApiModelProperty(value="班次")
	private String rqcbs;

	private String inoutFlag;
	private String onInventory;
	private String thisInventory;
	/**
	 * 班次
	 */
//	@ApiModelProperty(value="班次")
//	@TableField(exist = false)
//	private String startDte;
}


