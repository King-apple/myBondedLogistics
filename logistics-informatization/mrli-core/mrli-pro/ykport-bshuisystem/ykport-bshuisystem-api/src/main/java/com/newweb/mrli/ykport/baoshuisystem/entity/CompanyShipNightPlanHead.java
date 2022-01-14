package com.newweb.mrli.ykport.baoshuisystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@TableName("ykport0.SUB_SHIP_PLAN_HEAD")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "未完结货票查询头")
public class CompanyShipNightPlanHead extends Model<CompanyShipNightPlanHead> {
	private String unitCod;
	private String unitNam;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String planDte;
	//计划主任
	private String masterMan;
	//计划员
	private String planMan;
	//重点内容
	private String contentTxt;
	private String dataKnd;
}
