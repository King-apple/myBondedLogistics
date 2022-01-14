package com.newweb.mrli.ykport.baoshuisystem.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("ykport0.YKBF_HPMX")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "未完结货票查询")
public class OutStandingInvoice extends Model<OutStandingInvoice> {

	private String bh;
	private String cm;
	private String cbh;
	private String bq;
	private String cbb;
	private String tdh;
	private String hm;
	private String hl;
	private String ckm;
	private String rkm;
	private String zykq;
	private String wtr;
	private String byh;
	private String jhl;
	private String wcl;
	private String syl;
	private String zj;
	private String ysdw;
	private String cx;
	private String gndzf;
	private String bpwc;
	private String zbrq;
	private String bz;
	private String cbs;
	private String csl;
	private String nwm;
	private String jck;
	private String sqdw;
	private String sqbh;
	private String djdw;
	private String djbh;
	private String bzzd;
	private String sm;
	private String sjbz;
	private String lxdm;
	private String netflag;
	private String synflag;
	private String printnumber;
	private String lasttiem;
	private String jianshu;
	private String iflocal;

}
