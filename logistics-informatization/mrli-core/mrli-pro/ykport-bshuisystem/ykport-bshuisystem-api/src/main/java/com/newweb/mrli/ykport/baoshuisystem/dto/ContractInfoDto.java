package com.newweb.mrli.ykport.baoshuisystem.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.newweb.mrli.ykport.baoshuisystem.entity.ClientInfo;
import com.newweb.mrli.ykport.baoshuisystem.entity.ContractObject;
import com.newweb.mrli.ykport.baoshuisystem.entity.OrganizerInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@ApiModel(value = "合同insert详情")
public class ContractInfoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "合同名称")
	private String contractName;

	@ApiModelProperty(value = "承办单位")
	private List<OrganizerInfo> organizerInfos;

	@ApiModelProperty(value = "经办人")
	private String username;

	@ApiModelProperty(value = "部门")
	private String deptName;

	@ApiModelProperty(value = "对方签约单位")
	private List<ClientInfo> clientInfos;

	@ApiModelProperty(value = "合同标的")
	private List<ContractObject> contractObjects ;

	@ApiModelProperty(value = "合同编号")
	private String cont_no ;
}
