/*
 *    Copyright (c) 2018-2025, mrli All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: mrli
 */

package com.newweb.mrli.ykport.baoshuisystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.ykport.baoshuisystem.entity.SettlementContractLedger;
import com.newweb.mrli.ykport.baoshuisystem.service.ContractService;
import com.newweb.mrli.ykport.baoshuisystem.service.SettlementContractLedgerService;
import com.newweb.mrli.ykport.baoshuisystem.vo.ContractFileVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * 保税业务合同结算台账
 *
 * @author ly
 * @date 2021-12-14 10:25:42
 */
@RestController
@AllArgsConstructor
@RequestMapping("/settlementcontractledger" )
@Api(value = "settlementcontractledger", tags = "保税业务合同结算台账管理")
public class SettlementContractLedgerController {

    private final SettlementContractLedgerService settlementContractLedgerService;
	private final ContractService contractService;
    /**
     * 分页查询
     * @param page 分页对象
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSettlementContractLedgerPage(Page page, ContractFileVo contract) {
//       return R.ok(settlementContractLedgerService.page(page, Wrappers.query(settlementContractLedger)));
		return R.ok(contractService.selectPageVoJoinApprovalRecord(page, contract));
    }


    /**
     * 通过id查询保税业务合同结算台账
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(settlementContractLedgerService.getById(fId));
    }

    /**
     * 新增保税业务合同结算台账
     * @param settlementContractLedger 保税业务合同结算台账
     * @return R
     */
    @ApiOperation(value = "新增保税业务合同结算台账", notes = "新增保税业务合同结算台账")
    @SysLog("新增保税业务合同结算台账" )
    @PostMapping
    public R save(@RequestBody SettlementContractLedger settlementContractLedger) {
        return R.ok(settlementContractLedgerService.save(settlementContractLedger));
    }

    /**
     * 修改保税业务合同结算台账
     * @param settlementContractLedger 保税业务合同结算台账
     * @return R
     */
    @ApiOperation(value = "修改保税业务合同结算台账", notes = "修改保税业务合同结算台账")
    @SysLog("修改保税业务合同结算台账" )
    @PutMapping
    public R updateById(@RequestBody SettlementContractLedger settlementContractLedger) {
        return R.ok(settlementContractLedgerService.updateById(settlementContractLedger));
    }

    /**
     * 通过id删除保税业务合同结算台账
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除保税业务合同结算台账", notes = "通过id删除保税业务合同结算台账")
    @SysLog("通过id删除保税业务合同结算台账" )
    @DeleteMapping("/{fId}" )
    public R removeById(@PathVariable Integer fId) {
        return R.ok(settlementContractLedgerService.removeById(fId));
    }

    @ApiOperation(value = "导出", notes = "导出")
	@SysLog("导出")
	@PostMapping("/download")
	public void exportByBusId(@RequestBody List<ContractFileVo> contract, HttpServletResponse response) throws IOException {
		settlementContractLedgerService.export(contract, response);
	}

	@ApiOperation(value = "获取每个合同列表信息", notes = "获取每个合同列表信息")
	@SysLog("获取每个合同列表信息")
	@GetMapping("/getListALL/{contNo}")
	public R getListALL(@PathVariable String contNo) throws IOException {
		List<SettlementContractLedger> list= settlementContractLedgerService.getListALL(contNo);
		for (int i = 0; i <list.size() ; i++) {

		}
		return R.ok(list);
	}
}
