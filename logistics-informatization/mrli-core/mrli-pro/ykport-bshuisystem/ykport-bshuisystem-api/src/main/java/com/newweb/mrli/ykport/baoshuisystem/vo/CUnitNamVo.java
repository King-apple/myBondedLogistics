package com.newweb.mrli.ykport.baoshuisystem.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.newweb.mrli.ykport.baoshuisystem.entity.ClientInfo;
import com.newweb.mrli.ykport.baoshuisystem.entity.ContractObject;
import com.newweb.mrli.ykport.baoshuisystem.entity.OrganizerInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author mrli
 * @since 2017-10-29
 */
@Data

@TableName("ykport0.C_UNIT_NAM")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "获取公司信息")
public class CUnitNamVo extends Model<CUnitNamVo> {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "公司编码")
	private String unitCod;

	@ApiModelProperty(value = "公司名称")
	private String unitNam;

	@ApiModelProperty(value = "备注")
	private String markTxt;

}
