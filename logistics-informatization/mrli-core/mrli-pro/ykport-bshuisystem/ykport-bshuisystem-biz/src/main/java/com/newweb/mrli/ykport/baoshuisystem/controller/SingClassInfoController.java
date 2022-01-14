package com.newweb.mrli.ykport.baoshuisystem.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.newweb.mrli.ykport.baoshuisystem.entity.SingClassInfo;
import com.newweb.mrli.ykport.baoshuisystem.entity.YkbfSingClass;
import com.newweb.mrli.ykport.baoshuisystem.service.SingClassInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.newweb.mrli.common.core.util.R;
@RestController
@AllArgsConstructor
@RequestMapping("/singClassInfo" )
@Api(value = "singClassInfo", tags = "单个公办查询")
public class SingClassInfoController {
	private SingClassInfoService singClassInfoService;
	@ApiOperation(value = "新增", notes = "新增")
	@PostMapping("/getMessageToAdd" )
	public R getMessageToAdd(@RequestBody SingClassInfo singClassInfo){

		singClassInfo.setBhrqcbs(singClassInfo.getBh() + singClassInfo.getRqcbs());
		boolean save = singClassInfoService.save(singClassInfo);
		return save ? R.ok():R.failed("新增失败");
	}

	@ApiOperation(value = "修改", notes = "修改")
	@PostMapping("/modify" )
	public R modify(@RequestBody SingClassInfo singClassInfo){
		UpdateWrapper<SingClassInfo> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("BHRQCBS",singClassInfo.getBhrqcbs());
		updateWrapper.set("INOUT_FLAG",singClassInfo.getInoutFlag());
		if(singClassInfo.getOnInventory() != null){
			updateWrapper.set("ON_INVENTORY",singClassInfo.getOnInventory());
		}
		if(singClassInfo.getThisInventory() != null){
			updateWrapper.set("THIS_INVENTORY",singClassInfo.getThisInventory());
		}

		boolean modify = singClassInfoService.update(updateWrapper);
		return modify ? R.ok():R.failed("修改失败");
	}

	@ApiOperation(value = "查询", notes = "查询")
	@GetMapping("/getOne/{id}" )
	public R modify(@PathVariable String id){
		SingClassInfo byId = singClassInfoService.getById(id);
		return R.ok(byId);
	}

}
