package com.newweb.mrli.ykport.baoshuisystem.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.CDatacodes;
import com.newweb.mrli.ykport.baoshuisystem.entity.CompanyShipNightPlan;
import com.newweb.mrli.ykport.baoshuisystem.entity.CompanyShipNightPlanHead;
import com.newweb.mrli.ykport.baoshuisystem.mapper.CompanyShipNightPlanMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.CompanyShipNightPlanService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@DS("slave2")
@AllArgsConstructor
public class CompanyShipNightPlanServiceImpl extends ServiceImpl<CompanyShipNightPlanMapper, CompanyShipNightPlan> implements CompanyShipNightPlanService{

	@Autowired
	private CompanyShipNightPlanMapper companyShipNightPlanMapper;
	@Override
	public List<CDatacodes> getUnitNam() {
		return companyShipNightPlanMapper.getUnitNam();
	}

	@Override
	public List<CompanyShipNightPlanHead> getHeadMessage(String planDte, String unitCod) {
		return  companyShipNightPlanMapper.getMessage(planDte,unitCod);
	}


}
