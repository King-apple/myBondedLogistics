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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.ykport.baoshuisystem.entity.MachineManpowerExpense;
import com.newweb.mrli.ykport.baoshuisystem.service.MachineManpowerExpenseService;
import com.newweb.mrli.ykport.baoshuisystem.vo.InoutYardHeadVo;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * 机械人力作业费用明细表
 *
 * @author zada
 * @date 2021-10-22 16:46:51
 */
@RestController
@AllArgsConstructor
@RequestMapping("/machinemanpowerexpense" )
@Api(value = "machinemanpowerexpense", tags = "机械人力作业费用明细表管理")
public class MachineManpowerExpenseController {

    private final MachineManpowerExpenseService machineManpowerExpenseService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param machineManpowerExpense 机械人力作业费用明细表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('machinemanpowerexpense_view')" )
    public R getMachineManpowerExpensePage(Page page, MachineManpowerExpense machineManpowerExpense) {
        return R.ok(machineManpowerExpenseService.getList(page, machineManpowerExpense));
    }


    /**
     * 通过id查询机械人力作业费用明细表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('machinemanpowerexpense_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(machineManpowerExpenseService.getById(fId));
    }

    /**
     * 新增机械人力作业费用明细表
     * @param machineManpowerExpense 机械人力作业费用明细表
     * @return R
     */
    @ApiOperation(value = "新增机械人力作业费用明细表", notes = "新增机械人力作业费用明细表")
    @SysLog("新增机械人力作业费用明细表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('machinemanpowerexpense_add')" )
    public R save(@RequestBody MachineManpowerExpense machineManpowerExpense) {
        return R.ok(machineManpowerExpenseService.save(machineManpowerExpense));
    }

    /**
     * 修改机械人力作业费用明细表
     * @param machineManpowerExpense 机械人力作业费用明细表
     * @return R
     */
    @ApiOperation(value = "修改机械人力作业费用明细表", notes = "修改机械人力作业费用明细表")
    @SysLog("修改机械人力作业费用明细表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('machinemanpowerexpense_edit')" )
    public R updateById(@RequestBody MachineManpowerExpense machineManpowerExpense) {
        return R.ok(machineManpowerExpenseService.updateById(machineManpowerExpense));
    }

    /**
     * 通过id删除机械人力作业费用明细表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除机械人力作业费用明细表", notes = "通过id删除机械人力作业费用明细表")
    @SysLog("通过id删除机械人力作业费用明细表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('machinemanpowerexpense_del')" )
    public R removeById(@PathVariable Integer fId) {
        return R.ok(machineManpowerExpenseService.removeById(fId));
    }

	/**
	 * 导出机械人力作业费用明细表
	 * @param machineManpowerExpense 出入库表头
	 * @return R
	 */
	@ApiOperation(value = "导出机械人力作业费用明细表", notes = "导出机械人力作业费用明细表")
	@SysLog("导出机械人力作业费用明细表" )
	@PostMapping("/getMachineManpowerExcel" )
	public void getMachineManpowerExcel(@RequestBody MachineManpowerExpense machineManpowerExpense, HttpServletResponse response) throws IOException {
		List<MachineManpowerExpense> list = machineManpowerExpenseService.getMachineManpowerExcel(machineManpowerExpense);
		machineManpowerExpenseService.expoprtMachineManpowerExcel(machineManpowerExpense,list,response);
	}


}
