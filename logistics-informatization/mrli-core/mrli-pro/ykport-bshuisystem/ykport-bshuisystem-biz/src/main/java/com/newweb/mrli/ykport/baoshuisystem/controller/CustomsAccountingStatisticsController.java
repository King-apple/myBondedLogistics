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
import java.math.BigDecimal;
import java.util.Date;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.common.log.annotation.SysLog;
import com.newweb.mrli.common.security.util.SecurityUtils;
import com.newweb.mrli.ykport.baoshuisystem.entity.CustomsAccountingStatistics;
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardBody;
import com.newweb.mrli.ykport.baoshuisystem.entity.InoutYardHead;
import com.newweb.mrli.ykport.baoshuisystem.entity.ParameterList;
import com.newweb.mrli.ykport.baoshuisystem.service.CustomsAccountingStatisticsService;
import com.newweb.mrli.ykport.baoshuisystem.service.InoutYardBodyService;
import com.newweb.mrli.ykport.baoshuisystem.service.InoutYardHeadService;
import com.newweb.mrli.ykport.baoshuisystem.service.ParameterListService;
import com.newweb.mrli.ykport.baoshuisystem.utils.StrWithNumChkUtil;
import com.newweb.mrli.ykport.baoshuisystem.utils.UnitConversion;
import com.newweb.mrli.ykport.baoshuisystem.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.java.SimpleFormatter;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 海关账册录入统计表
 *
 * @author ly
 * @date 2021-08-30 18:00:02
 */
@RestController
@AllArgsConstructor
@RequestMapping("/customsaccountingstatistics" )
@Api(value = "customsaccountingstatistics", tags = "海关账册录入统计表管理")
public class CustomsAccountingStatisticsController {

	private CustomsAccountingStatisticsService customsAccountingStatisticsService;
	private InoutYardHeadService inoutYardHeadService;
	private InoutYardBodyService inoutYardBodyService;
	private ParameterListService parameterListService;
	/**
	 * 分页查询
	 *
	 * @param page                        分页对象
	 * @param
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('customsaccounting_view')")
	public R getCustomsAccountingStatisticsPage(Page page, CustomsAccountingStatisticsVo customsAccountingStatisticsvo) {

		IPage<CustomsAccountingStatisticsVo> customsAccountingStatisticsVoIPage = customsAccountingStatisticsService.selectPageVo(page, customsAccountingStatisticsvo);
		return R.ok(customsAccountingStatisticsVoIPage);
	}

	/**
	 * 通过id查询海关账册录入统计表
	 *
	 * @param fId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{fId}")
	@PreAuthorize("@pms.hasPermission('customsaccounting_view')")
	public R getById(@PathVariable("fId") Integer fId) {
		return R.ok(customsAccountingStatisticsService.getById(fId));
	}

	/**
	 * 新增海关账册录入统计表
	 *
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "新增海关账册录入统计表", notes = "新增海关账册录入统计表")
	@SysLog("新增海关账册录入统计表")
	@PostMapping("/save")
	public R save(@RequestBody List<InoutYardBody> inoutYardBody) {
		boolean save =false;
		for (InoutYardBody body : inoutYardBody) {
			Integer hFid = body.getHFid();
			String inCheckNum = body.getInCheckNum();
			QueryWrapper<InoutYardHead> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("f_id",hFid);
			queryWrapper.isNull("DEL_NAM");
			//循环每一条取得头部信息
			InoutYardHead one = inoutYardHeadService.getOne(queryWrapper);
			//通过和注清单号和in_out等于0 找到有几个出库
			QueryWrapper<InoutYardBody> queryWrapper1 = new QueryWrapper<>();
			queryWrapper1.eq("IN_OUT",0);
			queryWrapper1.eq("IN_CHECK_NUM",inCheckNum);
			queryWrapper1.isNull("DEL_NAM");
			//一个出库对应了多个入库
			List<InoutYardBody> list = inoutYardBodyService.list(queryWrapper1);
			//人民币转美元
			QueryWrapper<ParameterList>wrapper = new QueryWrapper<>();
			wrapper.eq("param_cod","euro_to_dollar_rate");
			QueryWrapper<ParameterList>wrapper2 = new QueryWrapper<>();
			wrapper2.eq("param_cod","rmb_to_dollar_rate");
			ParameterList rmbToDollar = parameterListService.getOne(wrapper2);
			//欧元转美元
			ParameterList euroToDollar = parameterListService.getOne(wrapper);
			//有出库的情况
			if(list.size()>0){
				for (InoutYardBody yardBody : list) {
					Integer hFid1 = yardBody.getHFid();
					QueryWrapper<InoutYardHead> queryWrapper2 = new QueryWrapper<>();
					queryWrapper2.eq("f_id",hFid1);
					queryWrapper2.isNull("DEL_NAM");
					//一个核注单可能有多个头部信息
					List<InoutYardHead> list1 = inoutYardHeadService.list(queryWrapper2);
					for (InoutYardHead inoutYardHead : list1) {
						CustomsAccountingStatistics customsAccountingStatistics = new CustomsAccountingStatistics();
						//作业委托人
						customsAccountingStatistics.setConsignNam(one.getConsignNam());
						//品名
						customsAccountingStatistics.setCargoNamCustoms(body.getCargoNam());
						//货类
						customsAccountingStatistics.setCargoKindNam(body.getCargoKindNam());
						//日期
						customsAccountingStatistics.setInDte(one.getRecordTim());
						//入库毛重（kg）
						//判断单位是不是吨，如果是吨的话就转换为千克
						//if("吨".equals(one.getNetWetUnit()))
						if("吨".equals(one.getNetWetUnit())){
							BigDecimal bigDecimal = new BigDecimal(body.getRoughWet());
							//默认保留两位小数,
							BigDecimal conversion = UnitConversion.conversion(bigDecimal, UnitConversion.UnitsEnum.EG_T, UnitConversion.UnitsEnum.EG_KG);
							customsAccountingStatistics.setInRoughWet(conversion.doubleValue());
						}else if("千克".equals(one.getNetWetUnit())){
							customsAccountingStatistics.setInRoughWet(body.getRoughWet());
						}
						//入库净重（kg）
						if("吨".equals(one.getNetWetUnit())){
							//默认保留两位小数
							if(StrWithNumChkUtil.isRealNumber(body.getNetWet()) ){
								BigDecimal bigDecimal = new BigDecimal(body.getNetWet());
								BigDecimal conversion = UnitConversion.conversion(bigDecimal, UnitConversion.UnitsEnum.EG_T, UnitConversion.UnitsEnum.EG_KG);
								customsAccountingStatistics.setInNetWet(conversion.doubleValue());
							}
						}else if("千克".equals(one.getNetWetUnit())){
							customsAccountingStatistics.setInNetWet(StrWithNumChkUtil.isRealNumber(body.getNetWet()) ? Double.parseDouble(body.getNetWet()) : 0);
						}
						//入库货品额（美元） --> 总价
						if("人民币".equals(one.getUnit())){
							customsAccountingStatistics.setInCargoAccount(body.getTotalPrice()*Double.parseDouble(rmbToDollar.getParamValue()));
						}else if("欧元".equals(one.getUnit())){
							customsAccountingStatistics.setInCargoAccount(body.getTotalPrice()*Double.parseDouble(euroToDollar.getParamValue()));
						}else if("美元".equals(one.getUnit())){
							customsAccountingStatistics.setInCargoAccount(body.getTotalPrice());
						}

						//原产国（入库）
						customsAccountingStatistics.setInOriginCountry(body.getImportCountry());
						//最终目的地（入库）
						customsAccountingStatistics.setInFinalDestination(body.getDistinationCountry());
						//核注清单号（入库）
						customsAccountingStatistics.setInCheckNum(body.getInCheckNum());
						// TODO 核对联（入库）
						//customsAccountingStatistics.setInNuclearCouplet("");
						//报关单号（入库）
						//customsAccountingStatistics.setInCustomsDeclarationNum(body.getInCustomsDeclaration());
						// TODO 识别代码（入库）
						//customsAccountingStatistics.setInIdentificationCod("");
						// TODO 货票号（入库）
						//customsAccountingStatistics.setInWaybillNum("");
						//类别（进区）
						customsAccountingStatistics.setInCategory(one.getInCategory());
						////日期
						customsAccountingStatistics.setOutDte(yardBody.getRecordTim());
						//出库毛重（kg )
						QueryWrapper<InoutYardHead> yardWrapper= new QueryWrapper<>();
						yardWrapper.eq("f_id",yardBody.getHFid());
						yardWrapper.isNull("DEL_NAM");
						InoutYardHead one1 = inoutYardHeadService.getOne(yardWrapper);
						if("吨".equals(one1.getNetWetUnit())){
							BigDecimal bigDecimal = new BigDecimal(body.getRoughWet());
							//默认保留两位小数,
							BigDecimal conversion = UnitConversion.conversion(bigDecimal, UnitConversion.UnitsEnum.EG_T, UnitConversion.UnitsEnum.EG_KG);
							customsAccountingStatistics.setOutRoughWet(conversion.doubleValue());
						}else if("千克".equals(one1.getNetWetUnit())){
							customsAccountingStatistics.setOutRoughWet(yardBody.getRoughWet());
						}
						//入库净重（kg）
						if("吨".equals(one1.getNetWetUnit())){
							//默认保留两位小数
							if(StrWithNumChkUtil.isRealNumber(body.getNetWet()) ){
								BigDecimal bigDecimal = new BigDecimal(body.getNetWet());
								BigDecimal conversion = UnitConversion.conversion(bigDecimal, UnitConversion.UnitsEnum.EG_T, UnitConversion.UnitsEnum.EG_KG);
								customsAccountingStatistics.setOutNetWet(conversion.doubleValue());
							}
						}else if("千克".equals(one1.getNetWetUnit())){
							//出库净重（kg）
							customsAccountingStatistics.setOutNetWet(StrWithNumChkUtil.isRealNumber(yardBody.getNetWet()) ? Double.parseDouble(yardBody.getNetWet()) : 0);
						}
						if("人民币".equals(one.getUnit())){
							customsAccountingStatistics.setInCargoAccount(body.getTotalPrice()*Double.parseDouble(rmbToDollar.getParamValue()));
						}else if("欧元".equals(one.getUnit())){
							customsAccountingStatistics.setInCargoAccount(body.getTotalPrice()*Double.parseDouble(euroToDollar.getParamValue()));
						}else if("美元".equals(one.getUnit())){
							customsAccountingStatistics.setInCargoAccount(yardBody.getTotalPrice());
						}

						//原产国（出库）
						customsAccountingStatistics.setOutOriginCountry(yardBody.getImportCountry());
						//最终目的地（出库）
						customsAccountingStatistics.setOutFinalDestination(yardBody.getDistinationCountry());
						//核注清单号（出库）
						customsAccountingStatistics.setOutCheckNum(yardBody.getOutCheckNum());
						// TODO 核对联（出库）
						//customsAccountingStatistics.setOutNuclearCouplet("");
						// TODO 报关单号（出库）
						customsAccountingStatistics.setOutCustomsDeclarationNum(yardBody.getOutCustomsDeclaration());
						// TODO 识别代码（出库）
						//customsAccountingStatistics.setOutIdentificationCod("");
						//TODO 货票号（出库）
						//customsAccountingStatistics.setOutWaybillNum("");
						//类别（出区）
						customsAccountingStatistics.setOutCategory(inoutYardHead.getOutCategory());
						customsAccountingStatistics.setOperNam(SecurityUtils.getUser().getUsername());
						customsAccountingStatistics.setRecordTim(new Date());
						save = customsAccountingStatisticsService.save(customsAccountingStatistics);
					}
				}
			//没有出库的情况
			}else{
				CustomsAccountingStatistics customsAccountingStatistics = new CustomsAccountingStatistics();
				//作业委托人
				customsAccountingStatistics.setConsignNam(one.getConsignNam());
				//品名
				customsAccountingStatistics.setCargoNamCustoms(body.getCargoNam());
				//货类
				customsAccountingStatistics.setCargoKindNam(body.getCargoKindNam());
				//日期
				customsAccountingStatistics.setInDte(one.getRecordTim());
//				//入库毛重（kg）
//				customsAccountingStatistics.setInRoughWet(body.getRoughWet());
//				//入库净重（kg）
//				customsAccountingStatistics.setInNetWet(StrWithNumChkUtil.isRealNumber(body.getNetWet()) ? Double.parseDouble(body.getNetWet()) : 0);
				//入库毛重（kg）
				//判断单位是不是吨，如果是吨的话就转换为千克
				//if("吨".equals(one.getNetWetUnit()))
				if("吨".equals(one.getNetWetUnit())){
					BigDecimal bigDecimal = new BigDecimal(body.getRoughWet());
					//默认保留两位小数,
					BigDecimal conversion = UnitConversion.conversion(bigDecimal, UnitConversion.UnitsEnum.EG_T, UnitConversion.UnitsEnum.EG_KG);
					customsAccountingStatistics.setInRoughWet(conversion.doubleValue());
				}else if("千克".equals(one.getNetWetUnit())){
					customsAccountingStatistics.setInRoughWet(body.getRoughWet());
				}
				//入库净重（kg）
				if("吨".equals(one.getNetWetUnit())){
					//默认保留两位小数
					if(StrWithNumChkUtil.isRealNumber(body.getNetWet()) ){
						BigDecimal bigDecimal = new BigDecimal(body.getNetWet());
						BigDecimal conversion = UnitConversion.conversion(bigDecimal, UnitConversion.UnitsEnum.EG_T, UnitConversion.UnitsEnum.EG_KG);
						customsAccountingStatistics.setInNetWet(conversion.doubleValue());
					}
				}else if("千克".equals(one.getNetWetUnit())){
					customsAccountingStatistics.setInNetWet(StrWithNumChkUtil.isRealNumber(body.getNetWet()) ? Double.parseDouble(body.getNetWet()) : 0);
				}

				//入库货品额（美元） --> 总价
				if("人民币".equals(one.getUnit())){
					customsAccountingStatistics.setInCargoAccount(body.getTotalPrice()*0.1567);
				}else if("欧元".equals(one.getUnit())){
					customsAccountingStatistics.setInCargoAccount(body.getTotalPrice()*1.13);
				}else if("美元".equals(one.getUnit())){
					customsAccountingStatistics.setInCargoAccount(body.getTotalPrice());
				}
				//原产国（入库）
				customsAccountingStatistics.setInOriginCountry(body.getImportCountry());
				//最终目的地（入库）
				customsAccountingStatistics.setInFinalDestination(body.getDistinationCountry());
				//核注清单号（入库）
				customsAccountingStatistics.setInCheckNum(body.getInCheckNum());
				// TODO 核对联（入库）
				//customsAccountingStatistics.setInNuclearCouplet("");
				//报关单号（入库）
				//customsAccountingStatistics.setInCustomsDeclarationNum(body.getInCustomsDeclaration());
				// TODO 识别代码（入库）
				//customsAccountingStatistics.setInIdentificationCod("");
				// TODO 货票号（入库）
				//customsAccountingStatistics.setInWaybillNum("");
				//类别（进区）
				customsAccountingStatistics.setInCategory(one.getInCategory());
				save = customsAccountingStatisticsService.save(customsAccountingStatistics);
			}
			//循环出库取得头部信息

		}
		if(save){
			return  R.ok(save);

		}else {
			return R.failed("请联系管理员~~~");
		}
	}
	@ApiOperation(value = "新增海关账册录入统计表", notes = "新增海关账册录入统计表")
	@SysLog("新增海关账册录入统计表")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('customsaccounting_add')")
	public R save() {
		List<InoutYardBody>inoutYardBodyList = inoutYardBodyService.getList();
		return R.ok(inoutYardBodyList);
	}
	/**
	 * 修改海关账册录入统计表
	 *
	 * @param customsAccountingStatistics 海关账册录入统计表
	 * @return R
	 */
	@ApiOperation(value = "修改海关账册录入统计表", notes = "修改海关账册录入统计表")
	@SysLog("修改海关账册录入统计表")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('customsaccounting_edit')")
	public R updateById(@RequestBody CustomsAccountingStatistics customsAccountingStatistics) {
		customsAccountingStatistics.setUpdateNam(SecurityUtils.getUser().getUsername());
		customsAccountingStatistics.setUpdateTim(new Date());
		return R.ok(customsAccountingStatisticsService.updateById(customsAccountingStatistics));
	}

	/**
	 * 通过id删除海关账册录入统计表
	 *
	 * @param fId id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除海关账册录入统计表", notes = "通过id删除海关账册录入统计表")
	@SysLog("通过id删除海关账册录入统计表")
	@DeleteMapping("/{fId}")
	@PreAuthorize("@pms.hasPermission('customsaccounting_del')")
	public R removeById(@PathVariable Long fId) {
		CustomsAccountingStatistics byId = customsAccountingStatisticsService.getById(fId);
		byId.setFId(fId);
		byId.setDelNam(SecurityUtils.getUser().getUsername());
		byId.setDelTim(new Date());
		return R.ok(customsAccountingStatisticsService.updateById(byId));
	}

	@GetMapping("/all")
	public R selectAll() {
		CustomsAccountingStatistics customsAccountingStatistics = new CustomsAccountingStatistics();
		final List<CustomsAccountingStatistics> customsAccountingStatisticsList = customsAccountingStatisticsService.selectAll();
		return R.ok(customsAccountingStatisticsList);
	}

	@GetMapping("/getManipulated")
	public R getManipulated(CustomsVo customsVo) {

		final List<CustomsVo> customsVoList = customsAccountingStatisticsService.getManipulated(customsVo);
		return R.ok(customsVoList);
	}


	@GetMapping("/getManipulatedForNew")
	public R getManipulatedForNew(CustomsVo customsVo) {

		final List<CustomsVo> customsVoList = customsAccountingStatisticsService.getManipulatedForNew(customsVo);
		Double monthCZL=0.0; //月操作量
		Double monthMYE=0.0; //一线出口贸易额
		Double yearCZL=0.0; //年操作量
		Double yearCZLB=0.0; //年累计操作量同比
		Double yearHZ=0.0; //年累计进出口货值
		Double yearHZB=0.0; //年累计进出口货值同比
		Double monthOneDay=0.0;
		Double yearOneDay=0.0;
		String yearCZLFlg="下降";
		String yearHZFlg="下降";


		for(CustomsVo detail:customsVoList){
			monthCZL+=Double.parseDouble(detail.getMInNetWet())+Double.parseDouble(detail.getMOutNetWet())/10000;
			monthMYE+=Double.parseDouble(detail.getMOutCargoAccount())/10000;
			yearCZL+= Double.parseDouble(detail.getYInNetWet())+Double.parseDouble(detail.getYOutNetWet())/10000;
			yearCZLB+=Double.parseDouble(detail.getYNetWetScale().replace("%",""));
			yearHZ+=Double.parseDouble(detail.getYSumCargoAccount())/10000;
			yearHZB+=Double.parseDouble(detail.getYCargoAccountScale().replace("%",""));
		}
		if(yearCZLB>0){
			yearCZLFlg = "上升";
		}
		if(yearHZB>0){
			yearHZFlg = "上升";
		}

		Map<String,String> map = new HashMap<String,String>();
		map.put("mTime",customsVo.getInDte());
		map.put("yOldStartTime",customsVo.getYearStart());
		map.put("yOldEndTime",customsVo.getYearEnd());
		Map<String,Object> mapInfo = inoutYardHeadService.getOneDayCount(map);
		String param = "_year_年_month_月操作量_monthCZL_万吨,一线出口贸易额_monthMYE_亿美元。_year_年1-_month_月" +
				"操作量_yearCZL_万吨,同比_oldyear_年_yearCZLFlg__yearCZLB_%,年累计进出口货值_yearHZ_亿美元,同比_yearHZFlg__yearHZB_%。一日游_month_月份完成_m_cnt_票," +
				"年累计完成_y_cnt_票";
		DecimalFormat df = new DecimalFormat("####0.000");
		param = param.replaceAll("_year_",customsVo.getInDte().substring(0,4));//
		param = param.replaceAll("_month_",customsVo.getInDte().substring(5,7));
		param = param.replaceAll("_monthCZL_",df.format(monthCZL));
		param = param.replaceAll("_monthMYE_",df.format(monthMYE));
		param = param.replaceAll("_yearCZL_",df.format(yearCZL));
		param = param.replaceAll("_oldyear_",customsVo.getOldYearStart().substring(0,4));
		param = param.replaceAll("_yearCZLFlg_",yearCZLFlg);
		param = param.replaceAll("_yearCZLB_",yearCZLB.toString());
		param = param.replaceAll("_yearHZ_",df.format(yearHZ));
		param = param.replaceAll("_yearHZFlg_",yearHZFlg);
		param = param.replaceAll("_yearHZB_",df.format(yearHZB));
		param = param.replaceAll("_m_cnt_",mapInfo.get("M_CNT").toString());
		param = param.replaceAll("_y_cnt_",mapInfo.get("Y_CNT").toString());
		System.out.println(param);
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("list",customsVoList);
		map2.put("param",param);
		return R.ok(map2);
	}

	@GetMapping("/getManipulatedCargo")
	public R getManipulatedCargo(CustomsCargoVo customsCargoVo) {
		List<CustomsCargoVo> carGoList = new ArrayList<CustomsCargoVo>();
		List<CustomsCargoVo> customsVoList = customsAccountingStatisticsService.getManipulatedCargo(customsCargoVo);
		Map<String,List<CustomsCargoVo>> userMap = customsVoList.stream().collect(Collectors.groupingBy(CustomsCargoVo::getConsignNam));
		System.out.println(userMap);
		for (Map.Entry<String, List<CustomsCargoVo>> entry : userMap.entrySet()) {
			System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue() + ", value = " + entry.getValue().get(0).getConsignNam());
			double mInNetWetValue = 0;
			double mOutNetWetValue = 0;
			double yInNetWetValue = 0;
			double yOutNetWetValue = 0;
			double yKucunValue = 0;
			for(int i=0;i<entry.getValue().size();i++){
				carGoList.add(entry.getValue().get(i));
				mInNetWetValue += Double.parseDouble(entry.getValue().get(i).getMInNetWet());
				mOutNetWetValue += Double.parseDouble(entry.getValue().get(i).getMOutNetWet());
				yInNetWetValue += Double.parseDouble(entry.getValue().get(i).getYInNetWet());
				yOutNetWetValue += Double.parseDouble(entry.getValue().get(i).getYOutNetWet());
				yKucunValue += Double.parseDouble(entry.getValue().get(i).getYKucun());

				if(entry.getValue().size()-1==i){
					CustomsCargoVo info = new CustomsCargoVo();
					info.setConsignNam("");
					info.setCargoKindNam("小计");
					info.setMInNetWet(String.valueOf(mInNetWetValue));
					info.setMOutNetWet(String.valueOf(mOutNetWetValue));
					info.setYInNetWet(String.valueOf(yInNetWetValue));
					info.setYOutNetWet(String.valueOf(yOutNetWetValue));
					info.setYKucun(String.valueOf(yKucunValue));
					carGoList.add(info);
				}
			}
		}


		return R.ok(carGoList);
	}


	/**
	 * 出口贸易额
	 * @param customsOutAmountVo
	 * @return
	 */
	@GetMapping("/getOutAmount")
	public R getOutAmount(CustomsOutAmountVo customsOutAmountVo) {
		List<CustomsOutAmountVo> carGoList = new ArrayList<CustomsOutAmountVo>();
		List<CustomsOutAmountVo> customsVoList = customsAccountingStatisticsService.getOutAmount(customsOutAmountVo);
//		Map<String,List<CustomsOutAmountVo>> userMap = customsVoList.stream().collect(Collectors.groupingBy(CustomsOutAmountVo::getCargoNamCustoms));
//		System.out.println(userMap);

			double mOutCargoAccountValue = 0; //当月出货额
			double mOldoutCargoAccountValue = 0; //去年当月出货额
			double mUpoutCargoAccountValue = 0; //当年上个月出货额
			double mComparedCargoAccountValue = 0; //当月同比
			double mRingCargoAccoutValue = 0; //当月环比
			double yearOutCargoAccountValue = 0; //当年出货额
			double yearOldoutCargoAccountValue = 0; //去年出货额
			double yearComparedCargoAccountValue = 0; //当年同比
		DecimalFormat df = new DecimalFormat("#########0.00");
			double yKucunValue = 0;
			for(int i=0;i<customsVoList.size();i++){
				carGoList.add(customsVoList.get(i));
				mOutCargoAccountValue += Double.parseDouble(customsVoList.get(i).getMOutCargoAccount());
				yearOutCargoAccountValue += Double.parseDouble(customsVoList.get(i).getYearOutCargoAccount());
				mOldoutCargoAccountValue += Double.parseDouble(customsVoList.get(i).getMOldoutCargoAccount());
				mUpoutCargoAccountValue += Double.parseDouble(customsVoList.get(i).getMUpoutCargoAccount());
				yearOldoutCargoAccountValue += Double.parseDouble(customsVoList.get(i).getYearOldoutCargoAccount());

				if(customsVoList.size()-1==i){
					CustomsOutAmountVo info = new CustomsOutAmountVo();
					info.setCargoKindNam("共计");
					info.setMOutCargoAccount(String.valueOf(mOutCargoAccountValue));
					info.setMComparedCargoAccount(mOldoutCargoAccountValue == 0 ? "0%":df.format((mOutCargoAccountValue-mOldoutCargoAccountValue)/mOldoutCargoAccountValue)+'%');
					info.setMRingCargoAccout(mUpoutCargoAccountValue == 0 ? "0%":df.format((mOutCargoAccountValue-mUpoutCargoAccountValue)/mUpoutCargoAccountValue)+'%');
					info.setYearOutCargoAccount(String.valueOf(yearOutCargoAccountValue));
					info.setYearComparedCargoAccount(yearOldoutCargoAccountValue == 0 ? "0%":df.format((yearOutCargoAccountValue-yearOldoutCargoAccountValue)/yearOldoutCargoAccountValue)+'%');
					carGoList.add(info);
				}
			}



		return R.ok(carGoList);
	}



	/**
	 * 货类信息
	 * @param cargoKindTypeVo
	 * @return
	 */
	@GetMapping("/getCargoKind")
	public R getCargoKind(CargoKindTypeVo cargoKindTypeVo) {
		List<CargoKindTypeVo> customsVoList = customsAccountingStatisticsService.getCargoKind(cargoKindTypeVo);
		return R.ok(customsVoList);
	}

	/**
	 * 货类信息导出
	 * @param cargoKindTypeVo
	 * @return
	 */
	@PostMapping("/getCargoKindExcel")
	public R getCargoKindExcel(@RequestBody CargoKindTypeVo cargoKindTypeVo, HttpServletResponse response) throws IOException {
		List<CargoKindTypeVo> customsVoList = customsAccountingStatisticsService.getCargoKind(cargoKindTypeVo);

		customsAccountingStatisticsService.exportCargoKindExcel(cargoKindTypeVo,customsVoList,response);

		return R.ok(customsVoList);
	}



	/**
	 * 出口贸易额导出excel
	 * @param customsOutAmountVo
	 * @return
	 */
	@PostMapping("/getOutAmountExcel")
	public void getOutAmountExcel(@RequestBody CustomsOutAmountVo customsOutAmountVo, HttpServletResponse response) throws IOException {
		List<CustomsOutAmountVo> carGoList = new ArrayList<CustomsOutAmountVo>();
		List<CustomsOutAmountVo> customsVoList = customsAccountingStatisticsService.getOutAmount(customsOutAmountVo);
//		Map<String,List<CustomsOutAmountVo>> userMap = customsVoList.stream().collect(Collectors.groupingBy(CustomsOutAmountVo::getCargoNamCustoms));
//		System.out.println(userMap);

		double mOutCargoAccountValue = 0; //当月出货额
		double mOldoutCargoAccountValue = 0; //去年当月出货额
		double mUpoutCargoAccountValue = 0; //当年上个月出货额
		double mComparedCargoAccountValue = 0; //当月同比
		double mRingCargoAccoutValue = 0; //当月环比
		double yearOutCargoAccountValue = 0; //当年出货额
		double yearOldoutCargoAccountValue = 0; //去年出货额
		double yearComparedCargoAccountValue = 0; //当年同比
		DecimalFormat df = new DecimalFormat("##########0.00");
		double yKucunValue = 0;
		for(int i=0;i<customsVoList.size();i++){
			carGoList.add(customsVoList.get(i));
			mOutCargoAccountValue += Double.parseDouble(customsVoList.get(i).getMOutCargoAccount());
			yearOutCargoAccountValue += Double.parseDouble(customsVoList.get(i).getYearOutCargoAccount());
			mOldoutCargoAccountValue += Double.parseDouble(customsVoList.get(i).getMOldoutCargoAccount());
			mUpoutCargoAccountValue += Double.parseDouble(customsVoList.get(i).getMUpoutCargoAccount());
			yearOldoutCargoAccountValue += Double.parseDouble(customsVoList.get(i).getYearOldoutCargoAccount());

			if(customsVoList.size()-1==i){
				CustomsOutAmountVo info = new CustomsOutAmountVo();
				info.setCargoKindNam("小计");
				info.setMOutCargoAccount(String.valueOf(mOutCargoAccountValue));
				info.setMComparedCargoAccount(mOldoutCargoAccountValue == 0 ? "0%":df.format((mOutCargoAccountValue-mOldoutCargoAccountValue)/mOldoutCargoAccountValue)+"%");
				info.setMRingCargoAccout(mUpoutCargoAccountValue == 0 ? "0%":df.format((mOutCargoAccountValue-mUpoutCargoAccountValue)/mUpoutCargoAccountValue)+"%");
				info.setYearOutCargoAccount(String.valueOf(yearOutCargoAccountValue));
				info.setYearComparedCargoAccount(yearOldoutCargoAccountValue == 0 ? "0%":df.format((yearOutCargoAccountValue-yearOldoutCargoAccountValue)/yearOldoutCargoAccountValue)+"%");
				carGoList.add(info);
			}
		}

		customsAccountingStatisticsService.exportOutAmountExcel(customsOutAmountVo,carGoList,response);

//		return R.ok(carGoList);
	}

	@ApiOperation(value = "导出excel", notes = "导出excel")
	@SysLog("导出excel" )
	@PostMapping("/exportManipulatedCargoExcel")
	public void exportManipulatedCargoExcel(@RequestBody CustomsCargoVo customsCargoVo, HttpServletResponse response) throws IOException {
		List<CustomsCargoVo> customsVoList = customsAccountingStatisticsService.getManipulatedCargo(customsCargoVo);
		customsAccountingStatisticsService.exportManipulatedCargoExcel(customsCargoVo,customsVoList,response);
	}

	@ApiOperation(value = "导出excel", notes = "导出excel")
	@SysLog("导出excel" )
	@PostMapping ("/getManipulatedExcel")
	public void getManipulatedExcel(@RequestBody CustomsVo customsVo, HttpServletResponse response) throws IOException {

		final List<CustomsVo> customsVoList = customsAccountingStatisticsService.getManipulated(customsVo);
		customsAccountingStatisticsService.expoprtManipulatedExcel(customsVo,customsVoList,response);
	}

	@ApiOperation(value = "导出excel", notes = "导出excel")
	@SysLog("导出excel" )
	@PostMapping ("/expoprtManipulatedForNewExcel")
	public void expoprtManipulatedForNewExcel(@RequestBody CustomsVo customsVo, HttpServletResponse response) throws IOException {

		final List<CustomsVo> customsVoList = customsAccountingStatisticsService.getManipulatedForNew(customsVo);
		Double monthCZL=0.0; //月操作量
		Double monthMYE=0.0; //一线出口贸易额
		Double yearCZL=0.0; //年操作量
		Double yearCZLB=0.0; //年累计操作量同比
		Double yearHZ=0.0; //年累计进出口货值
		Double yearHZB=0.0; //年累计进出口货值同比
		Double monthOneDay=0.0;
		Double yearOneDay=0.0;
		String yearCZLFlg="下降";
		String yearHZFlg="下降";

		for(CustomsVo detail:customsVoList){
			monthCZL+=Double.parseDouble(detail.getMInNetWet())+Double.parseDouble(detail.getMOutNetWet());
			monthMYE+=Double.parseDouble(detail.getMOutCargoAccount());
			yearCZL+= Double.parseDouble(detail.getYInNetWet())+Double.parseDouble(detail.getYOutNetWet());
			yearCZLB+=Double.parseDouble(detail.getYNetWetScale().replace("%",""));
			yearHZ+=Double.parseDouble(detail.getYSumCargoAccount());
			yearHZB+=Double.parseDouble(detail.getYCargoAccountScale().replace("%",""));
		}
		if(yearCZLB>0){
			yearCZLFlg = "上升";
		}
		if(yearHZB>0){
			yearHZFlg = "上升";
		}

		Map<String,String> map = new HashMap<String,String>();
		map.put("mTime",customsVo.getInDte());
		map.put("yOldStartTime",customsVo.getYearStart());
		map.put("yOldEndTime",customsVo.getYearEnd());
		Map<String,Object> mapInfo = inoutYardHeadService.getOneDayCount(map);
		String param = "_year_年_month_月操作量_monthCZL_万吨,一线出口贸易额_monthMYE_亿美元。_year_年1-_month_月" +
				"操作量_yearCZL_万吨,同比_oldyear_年_yearCZLFlg__yearCZLB_%,年累计进出口货值_yearHZ_亿美元,同比_yearHZFlg__yearHZB_%。一日游_month_月份完成_m_cnt_票," +
				"年累计完成_y_cnt_票";
		param = param.replaceAll("_year_",customsVo.getInDte().substring(0,4));//
		param = param.replaceAll("_month_",customsVo.getInDte().substring(5,7));
		param = param.replaceAll("_monthCZL_",monthCZL.toString());
		param = param.replaceAll("_monthMYE_",monthMYE.toString());
		param = param.replaceAll("_yearCZL_",yearCZL.toString());
		param = param.replaceAll("_oldyear_",customsVo.getOldYearStart().substring(0,4));
		param = param.replaceAll("_yearCZLFlg_",yearCZLFlg);
		param = param.replaceAll("_yearCZLB_",yearCZLB.toString());
		param = param.replaceAll("_yearHZ_",yearHZ.toString());
		param = param.replaceAll("_yearHZFlg_",yearHZFlg);
		param = param.replaceAll("_yearHZB_",yearHZB.toString());
		param = param.replaceAll("_m_cnt_",mapInfo.get("M_CNT").toString());
		param = param.replaceAll("_y_cnt_",mapInfo.get("Y_CNT").toString());
		System.out.println(param);
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("list",customsVoList);
		map2.put("param",param);
//		return R.ok(map2);

		customsAccountingStatisticsService.expoprtManipulatedForNewExcel(customsVo,customsVoList,response);
	}


	@ApiOperation(value = "导出excel", notes = "导出excel")
	@SysLog("导出excel" )
	@PostMapping ("/e1")
	@PreAuthorize("@pms.hasPermission('customsaccounting_export')" )
	public void expoprtExcel( @RequestBody CustomsAccountingStatisticsVo customsAccountingStatisticsVo , HttpServletResponse response,Page page) throws IOException {
		customsAccountingStatisticsService.expoprtExcel(customsAccountingStatisticsVo,response,page);
	}
	@ApiOperation(value = "合计", notes = "合计")
	@SysLog("合计" )
	@PostMapping ("/count")
	public R count( @RequestBody CustomsAccountingStatisticsVo customsAccountingStatisticsVo)  {
		CustomsAccountingStatisticsVo customs =customsAccountingStatisticsService.countAll(customsAccountingStatisticsVo);
		return R.ok(customs);
	}


}