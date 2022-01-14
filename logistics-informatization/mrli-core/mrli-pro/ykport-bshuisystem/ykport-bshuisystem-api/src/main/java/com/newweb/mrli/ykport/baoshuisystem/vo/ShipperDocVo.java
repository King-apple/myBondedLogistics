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
@TableName("ykport0.SHIPPER_DOC")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "获取港口货主信息")
public class ShipperDocVo extends Model<ShipperDocVo> {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "货主代码")
	private String shipperCod;

	@ApiModelProperty(value = "货主名称")
	private String shipperDoc;

	@ApiModelProperty(value = "shotNam")
	private String shotNam;
}
