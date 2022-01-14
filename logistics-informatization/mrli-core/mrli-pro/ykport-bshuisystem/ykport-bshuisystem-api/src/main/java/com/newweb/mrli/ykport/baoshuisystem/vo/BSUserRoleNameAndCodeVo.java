package com.newweb.mrli.ykport.baoshuisystem.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "角色列表")
public class BSUserRoleNameAndCodeVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色名称
	 */
	@ApiModelProperty(value = "角色名称")
	private String roleName;

	/**
	 * 角色Code
	 */
	@ApiModelProperty(value = "角色Code")
	private String roleCode;
}
