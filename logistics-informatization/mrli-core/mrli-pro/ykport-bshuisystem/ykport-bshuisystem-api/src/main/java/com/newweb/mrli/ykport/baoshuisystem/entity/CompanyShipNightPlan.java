package com.newweb.mrli.ykport.baoshuisystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("ykport0.SUB_SHIP_PLAN")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "未完结货票查询")
public class CompanyShipNightPlan extends Model<CompanyShipNightPlan> {
	private String unitCod;
	//单位代码
	private String berthCod;
	//船名
	private String shipNam;
	//货名
	private String cargoNam;
	//装卸别
	private String unloadId;
	//到港时间
	private String toPortTim;
	//靠泊时间
	private String toBerthTim;
	//开工时间
	private String beginTim;
	//完工时间
	private String endTim;
	//计划移泊时间
	private String planMoveTim;
	//离港时间
	private String levPortTim;
	//总载重量
	private String shipCap;
	//全船剩吨
	private String surplusTon;
	//上昼夜剩吨
	private String lastSurTon;
	//上昼夜完成吨
	private String lastWorkTon;
	//本昼夜计划完成吨
	private String planWorkTon;
	//16点安排
	private String firstTxt;
	//00点安排
	private String secondTxt;
	//08点安排
	private String thirdTxt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date planDte;
	private String unitNam;

}
