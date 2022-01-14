package com.newweb.mrli.ykport.baoshuisystem.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author mrli
 * @since 2017-10-29
 */
@Data
@ApiModel(value = "用户")
@TableName("BCMCX")
public class BcmcxVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 记录号
	 */
	@TableId(value = "jlh", type = IdType.AUTO)
	@ApiModelProperty(value = "记录号")
	private String jlh;

	/**
	 * 磅房编号
	 */
	@ApiModelProperty(value = "磅房编号")
	private String bh;

	/**
	 * 车牌号
	 */
	@ApiModelProperty(value = "车牌号")
	private String ch;

	/**
	 * 件数
	 */
	@ApiModelProperty(value = "件数")
	private String jianshu;

	/**
	 * 完成量
	 */
	@ApiModelProperty(value = "完成量")
	private String hz;

	/**
	 * 已上图数据
	 */
	@ApiModelProperty(value = "已上图数据")
	private String tjlh;

	/**
	 * 称别组
	 */
	@ApiModelProperty(value = "称别组")
	private String cbs;


	/**
	 * 班次
	 */
	@ApiModelProperty(value = "班次")
	private String rqcbs;

	/**
	 * 重车时间
	 */
	@ApiModelProperty(value = "重车时间")
	private String zcsj;

	/**
	 * 空车时间
	 */
	@ApiModelProperty(value = "空车时间")
	private String kcsj;

	/**
	 * 已上图数据
	 */
	@ApiModelProperty(value = "流水号")
	private String liushuihao;
}
