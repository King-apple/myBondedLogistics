package com.newweb.mrli.ykport.baoshuisystem.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 获取船舶信息
 * </p>
 *
 * @author mrli
 * @since 2017-10-29
 */
@Data

@TableName("ykport0.SHIP")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "获取船舶信息")
public class ShipVo extends Model<ShipVo> {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "船舶编号")
	private String shipNo;

	@ApiModelProperty(value = "船舶名称")
	private String shipNam;

	@ApiModelProperty(value = "进港时间")
	private String toPortTim;

	@ApiModelProperty(value = "时间")
	private String levPortTim;



}
