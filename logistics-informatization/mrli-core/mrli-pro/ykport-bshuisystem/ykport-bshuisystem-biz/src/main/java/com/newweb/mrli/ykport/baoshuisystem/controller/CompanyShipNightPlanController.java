package com.newweb.mrli.ykport.baoshuisystem.controller;

import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.ykport.baoshuisystem.entity.CompanyShipNightPlan;
import com.newweb.mrli.ykport.baoshuisystem.entity.CompanyShipNightPlanHead;
import com.newweb.mrli.ykport.baoshuisystem.service.CompanyShipNightPlanService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/companyshipnightplan" )
public class CompanyShipNightPlanController {
	private final CompanyShipNightPlanService companyShipNightPlanService;
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getCustomsAccountingStatisticsPage(Page page, CompanyShipNightPlan companyShipNightPlan) {

		return R.ok(companyShipNightPlanService.page(page, Wrappers.query(companyShipNightPlan)));
	}
	@GetMapping("/getUnitNam")
	public R getUnitNam(){
		return R.ok(companyShipNightPlanService.getUnitNam());
	}
	@PostMapping("/getHeadMessage")
	public R getHeadMessage(@RequestBody CompanyShipNightPlan companyShipNightPlan){
		Date planDte = companyShipNightPlan.getPlanDte();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String planDat = dateFormat.format(planDte);
		String unitCod = companyShipNightPlan.getUnitCod();
		if(StringUtils.isNotEmpty(planDat) && StringUtils.isNotEmpty(unitCod)){
			List<CompanyShipNightPlanHead>head = companyShipNightPlanService.getHeadMessage(planDat,unitCod);
			return R.ok(head);
		}else{
			return R.failed("失败");
		}

	}

}
