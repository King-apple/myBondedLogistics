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
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.GoodsCategory;
import com.newweb.mrli.ykport.baoshuisystem.service.GoodsCategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * 货类码表
 *
 * @author pigx code generator
 * @date 2021-08-03 14:19:59
 */
@RestController
@AllArgsConstructor
@RequestMapping("/goodscategory" )
@Api(value = "goodscategory", tags = "货类码表管理")
public class GoodsCategoryController {

    private final  GoodsCategoryService goodsCategoryService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param goodsCategory 货类码表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('goodscategory_view')" )
    public R getGoodsCategoryPage(Page page, GoodsCategory goodsCategory) {
        return R.ok(goodsCategoryService.page(page, Wrappers.query(goodsCategory).isNull("DEL_TIM")));
    }

	/**
	 * 查询货类
	 *
	 */
	@ApiOperation(value = "查询货类", notes = "查询货类")
	@GetMapping("/dict" )
	public R getGoodsCategoryList() {
		GoodsCategory goodsCategory = new GoodsCategory();
		return R.ok(goodsCategoryService.list(Wrappers.query(goodsCategory).isNull("DEL_TIM")));
	}


    /**
     * 通过id查询货类码表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('goodscategory_view')" )
    public R getById(@PathVariable("fId" ) Integer fId) {
        return R.ok(goodsCategoryService.getById(fId));
    }

    /**
     * 新增货类码表
     * @param goodsCategory 货类码表
     * @return R
     */
    @ApiOperation(value = "新增货类码表", notes = "新增货类码表")
    @SysLog("新增货类码表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('goodscategory_add')" )
    public R save(@RequestBody GoodsCategory goodsCategory) {
    	if(goodsCategory.getCargoKindCod() == null ||"".equals(goodsCategory.getCargoKindCod().trim())){
			GoodsCategory cargo_kind_cod = goodsCategoryService.getOne(Wrappers.query(new GoodsCategory()).select("max(to_number(cargo_kind_cod)) cargo_kind_cod"));
			goodsCategory.setCargoKindCod((Integer.parseInt(cargo_kind_cod.getCargoKindCod())+1)+"");
		}

    	Integer fId = goodsCategoryService.selectFid();
    	goodsCategory.setFId(fId);
    	goodsCategory.setRecordTim(new Date());
    	goodsCategory.setOperNam(SecurityUtils.getUser().getUsername());
        return R.ok(goodsCategoryService.save(goodsCategory));
    }

    /**
     * 修改货类码表
     * @param goodsCategory 货类码表
     * @return R
     */
    @ApiOperation(value = "修改货类码表", notes = "修改货类码表")
    @SysLog("修改货类码表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('goodscategory_edit')" )
    public R updateById(@RequestBody GoodsCategory goodsCategory) {
    	goodsCategory.setUpdateNam(SecurityUtils.getUser().getUsername());
    	goodsCategory.setUpdateTim(new Date());
        return R.ok(goodsCategoryService.updateById(goodsCategory));
    }

    /**
     * 通过id删除货类码表
     * @param fId id
     * @return R
     */
    @ApiOperation(value = "通过id删除货类码表", notes = "通过id删除货类码表")
    @SysLog("通过id删除货类码表" )
    @DeleteMapping("/{fId}" )
    @PreAuthorize("@pms.hasPermission('goodscategory_del')" )
    public R removeById(@PathVariable Integer fId) {
    	GoodsCategory goodsCategory = new GoodsCategory();
    	goodsCategory.setDelNam(SecurityUtils.getUser().getUsername());
    	goodsCategory.setDelTim(new Date());
    	goodsCategory.setFId(fId);
        return R.ok(goodsCategoryService.updateById(goodsCategory));
    }

	/**
	 * 查询所有
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/all" )
	public R getAll() {
		GoodsCategory goodsCategory = new GoodsCategory();

		return R.ok(goodsCategoryService.list(Wrappers.query(goodsCategory).isNull("DEL_TIM")));
	}
}
