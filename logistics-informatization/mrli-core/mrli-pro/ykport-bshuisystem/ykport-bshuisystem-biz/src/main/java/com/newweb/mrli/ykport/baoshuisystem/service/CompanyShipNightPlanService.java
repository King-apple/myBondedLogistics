package com.newweb.mrli.ykport.baoshuisystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newweb.mrli.ykport.baoshuisystem.entity.CDatacodes;
import com.newweb.mrli.ykport.baoshuisystem.entity.CompanyShipNightPlan;
import com.newweb.mrli.ykport.baoshuisystem.entity.CompanyShipNightPlanHead;

import java.util.List;
public interface CompanyShipNightPlanService  extends IService<CompanyShipNightPlan> {
	List<CDatacodes> getUnitNam();

	List<CompanyShipNightPlanHead> getHeadMessage(String planDat, String unitCod);
}
