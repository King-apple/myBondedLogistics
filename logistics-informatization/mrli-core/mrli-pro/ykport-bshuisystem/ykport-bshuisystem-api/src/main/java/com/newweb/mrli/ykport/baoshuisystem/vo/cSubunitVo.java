package com.newweb.mrli.ykport.baoshuisystem.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 保管单位信息表
 * </p>
 *
 * @author mrli
 * @since 2017-10-29
 */
@Data

@TableName("ykport0.C_SUBUNIT")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "获取保管单位信息")
public class cSubunitVo extends Model<cSubunitVo> {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "单位编码")
	private String subunitCod;

	@ApiModelProperty(value = "单位名称")
	private String subunitNam;

	@ApiModelProperty(value = "公司编码")
	private String uniCode;

}
