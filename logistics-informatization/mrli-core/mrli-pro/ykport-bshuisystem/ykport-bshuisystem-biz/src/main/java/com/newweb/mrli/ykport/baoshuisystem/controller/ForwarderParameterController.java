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
import com.newweb.mrli.ykport.baoshuisystem.entity.ForwarderParameter;
import com.newweb.mrli.ykport.baoshuisystem.service.ForwarderParameterService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 货代合同结算台账
 *
 * @author ly
 * @date 2021-10-27 09:00:08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/forwarderparameter" )
@Api(value = "forwarderparameter", tags = "货代合同结算台账管理")
public class ForwarderParameterController {

    private final ForwarderParameterService forwarderParameterService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param forwarderParameter 货代合同结算台账
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('forwarderparameter_view')" )
    public R getForwarderParameterPage(Page page, ForwarderParameter forwarderParameter) {
        return R.ok(forwarderParameterService.page(page, Wrappers.query(forwarderParameter)));
    }


    /**
     * 通过id查询货代合同结算台账
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('forwarderparameter_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(forwarderParameterService.getById(fId));
    }

    /**
     * 新增货代合同结算台账
     * @param forwarderParameter 货代合同结算台账
     * @return R
     */
    @ApiOperation(value = "新增货代合同结算台账", notes = "新增货代合同结算台账")
    @SysLog("新增货代合同结算台账" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('forwarderparameter_add')" )
    public R save(@RequestBody ForwarderParameter forwarderParameter) {
        return R.ok(forwarderParameterService.save(forwarderParameter));
    }

    /**
     * 修改货代合同结算台账
     * @param forwarderParameter 货代合同结算台账
     * @return R
     */
    @ApiOperation(value = "修改货代合同结算台账", notes = "修改货代合同结算台账")
    @SysLog("修改货代合同结算台账" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('forwarderparameter_edit')" )
    public R updateById(@RequestBody ForwarderParameter forwarderParameter) {
        return R.ok(forwarderParameterService.updateById(forwarderParameter));
    }

    /**
     * 通过id删除货代合同结算台账
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除货代合同结算台账", notes = "通过id删除货代合同结算台账")
    @SysLog("通过id删除货代合同结算台账" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('forwarderparameter_del')" )
    public R removeById(@PathVariable Integer fId) {
        return R.ok(forwarderParameterService.removeById(fId));
    }

}
