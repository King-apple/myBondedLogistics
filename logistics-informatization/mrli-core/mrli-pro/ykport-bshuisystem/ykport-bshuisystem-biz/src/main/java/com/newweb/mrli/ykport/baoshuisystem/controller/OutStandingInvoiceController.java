package com.newweb.mrli.ykport.baoshuisystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.ykport.baoshuisystem.entity.OutStandingInvoice;
import com.newweb.mrli.ykport.baoshuisystem.service.OutStandingInvoiceService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/outstandinginvoice" )
public class OutStandingInvoiceController {
	private OutStandingInvoiceService outStandingInvoiceService;


	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getCustomsAccountingStatisticsPage(Page page, OutStandingInvoice outStandingInvoice) {

		return R.ok(outStandingInvoiceService.page(page, Wrappers.query(outStandingInvoice)));
	}
	@GetMapping("/getGroup")
	public R getGroup(){

		return R.ok(outStandingInvoiceService.getCBS());
	}
}
