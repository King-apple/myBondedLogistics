package com.newweb.mrli.ykport.baoshuisystem.entity;

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
@TableName("sys_user")
public class BSUser implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "user_id", type = IdType.AUTO)
	@ApiModelProperty(value = "主键id")
	private Integer userId;

	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String username;

	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;

	/**
	 * 随机盐
	 */
	@JsonIgnore
	@ApiModelProperty(value = "随机盐")
	private String salt;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;

	/**
	 * 0-正常，1-删除
	 */
	@TableLogic
	@ApiModelProperty(value = "删除标记,1:已删除,0:正常")
	private String delFlag;

	/**
	 * 锁定标记
	 */
	@ApiModelProperty(value = "锁定标记")
	private String lockFlag;

	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号")
	private String phone;

	/**
	 * 头像
	 */
	@ApiModelProperty(value = "头像地址")
	private String avatar;

	/**
	 * 部门ID
	 */
	@ApiModelProperty(value = "用户所属部门id")
	private Integer deptId;

	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "用户所属租户id")
	private Integer tenantId;

	/**
	 * 微信openid
	 */
	@ApiModelProperty(value = "微信openid")
	private String wxOpenid;

	/**
	 * 微信小程序openId
	 */
	@ApiModelProperty(value = "微信小程序openid")
	private String miniOpenid;

	/**
	 * QQ openid
	 */
	@ApiModelProperty(value = "QQ openid")
	private String qqOpenid;

	/**
	 * 码云唯一标识
	 */
	@ApiModelProperty(value = "码云唯一标识")
	private String giteeLogin;

	/**
	 * 开源中国唯一标识
	 */
	@ApiModelProperty(value = "开源中国唯一标识")
	private String oscId;

	@ApiModelProperty(value = "人员名称")
	private String manName;
	@ApiModelProperty(value = "公司代码")
	private String unitCod;
	@ApiModelProperty(value = "部门代码")
	private String subunitCod;
	@ApiModelProperty(value = "部门对应主键")
	private Integer deptSubId;
}
