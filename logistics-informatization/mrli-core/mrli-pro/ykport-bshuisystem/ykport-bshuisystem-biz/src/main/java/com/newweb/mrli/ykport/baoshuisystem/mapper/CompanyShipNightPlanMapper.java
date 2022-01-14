package com.newweb.mrli.ykport.baoshuisystem.mapper;

import com.newweb.mrli.common.data.datascope.MrliBaseMapper;
import com.newweb.mrli.ykport.baoshuisystem.entity.CDatacodes;
import com.newweb.mrli.ykport.baoshuisystem.entity.CompanyShipNightPlan;
import com.newweb.mrli.ykport.baoshuisystem.entity.CompanyShipNightPlanHead;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyShipNightPlanMapper  extends MrliBaseMapper<CompanyShipNightPlan> {
	List<CDatacodes> getUnitNam();

	List<CompanyShipNightPlanHead> getMessage(@Param("planDte") String planDte, @Param("unitCod")String unitCod);
}
