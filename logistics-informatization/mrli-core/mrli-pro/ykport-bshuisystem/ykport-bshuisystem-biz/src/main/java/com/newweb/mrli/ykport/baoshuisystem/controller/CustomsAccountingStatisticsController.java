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
 * ???????????????????????????
 *
 * @author ly
 * @date 2021-08-30 18:00:02
 */
@RestController
@AllArgsConstructor
@RequestMapping("/customsaccountingstatistics" )
@Api(value = "customsaccountingstatistics", tags = "?????????????????????????????????")
public class CustomsAccountingStatisticsController {

	private CustomsAccountingStatisticsService customsAccountingStatisticsService;
	private InoutYardHeadService inoutYardHeadService;
	private InoutYardBodyService inoutYardBodyService;
	private ParameterListService parameterListService;
	/**
	 * ????????????
	 *
	 * @param page                        ????????????
	 * @param
	 * @return
	 */
	@ApiOperation(value = "????????????", notes = "????????????")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('customsaccounting_view')")
	public R getCustomsAccountingStatisticsPage(Page page, CustomsAccountingStatisticsVo customsAccountingStatisticsvo) {

		IPage<CustomsAccountingStatisticsVo> customsAccountingStatisticsVoIPage = customsAccountingStatisticsService.selectPageVo(page, customsAccountingStatisticsvo);
		return R.ok(customsAccountingStatisticsVoIPage);
	}

	/**
	 * ??????id?????????????????????????????????
	 *
	 * @param fId id
	 * @return R
	 */
	@ApiOperation(value = "??????id??????", notes = "??????id??????")
	@GetMapping("/{fId}")
	@PreAuthorize("@pms.hasPermission('customsaccounting_view')")
	public R getById(@PathVariable("fId") Integer fId) {
		return R.ok(customsAccountingStatisticsService.getById(fId));
	}

	/**
	 * ?????????????????????????????????
	 *
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "?????????????????????????????????", notes = "?????????????????????????????????")
	@SysLog("?????????????????????????????????")
	@PostMapping("/save")
	public R save(@RequestBody List<InoutYardBody> inoutYardBody) {
		boolean save =false;
		for (InoutYardBody body : inoutYardBody) {
			Integer hFid = body.getHFid();
			String inCheckNum = body.getInCheckNum();
			QueryWrapper<InoutYardHead> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("f_id",hFid);
			queryWrapper.isNull("DEL_NAM");
			//?????????????????????????????????
			InoutYardHead one = inoutYardHeadService.getOne(queryWrapper);
			//????????????????????????in_out??????0 ?????????????????????
			QueryWrapper<InoutYardBody> queryWrapper1 = new QueryWrapper<>();
			queryWrapper1.eq("IN_OUT",0);
			queryWrapper1.eq("IN_CHECK_NUM",inCheckNum);
			queryWrapper1.isNull("DEL_NAM");
			//?????????????????????????????????
			List<InoutYardBody> list = inoutYardBodyService.list(queryWrapper1);
			//??????????????????
			QueryWrapper<ParameterList>wrapper = new QueryWrapper<>();
			wrapper.eq("param_cod","euro_to_dollar_rate");
			QueryWrapper<ParameterList>wrapper2 = new QueryWrapper<>();
			wrapper2.eq("param_cod","rmb_to_dollar_rate");
			ParameterList rmbToDollar = parameterListService.getOne(wrapper2);
			//???????????????
			ParameterList euroToDollar = parameterListService.getOne(wrapper);
			//??????????????????
			if(list.size()>0){
				for (InoutYardBody yardBody : list) {
					Integer hFid1 = yardBody.getHFid();
					QueryWrapper<InoutYardHead> queryWrapper2 = new QueryWrapper<>();
					queryWrapper2.eq("f_id",hFid1);
					queryWrapper2.isNull("DEL_NAM");
					//??????????????????????????????????????????
					List<InoutYardHead> list1 = inoutYardHeadService.list(queryWrapper2);
					for (InoutYardHead inoutYardHead : list1) {
						CustomsAccountingStatistics customsAccountingStatistics = new CustomsAccountingStatistics();
						//???????????????
						customsAccountingStatistics.setConsignNam(one.getConsignNam());
						//??????
						customsAccountingStatistics.setCargoNamCustoms(body.getCargoNam());
						//??????
						customsAccountingStatistics.setCargoKindNam(body.getCargoKindNam());
						//??????
						customsAccountingStatistics.setInDte(one.getRecordTim());
						//???????????????kg???
						//???????????????????????????????????????????????????????????????
						//if("???".equals(one.getNetWetUnit()))
						if("???".equals(one.getNetWetUnit())){
							BigDecimal bigDecimal = new BigDecimal(body.getRoughWet());
							//????????????????????????,
							BigDecimal conversion = UnitConversion.conversion(bigDecimal, UnitConversion.UnitsEnum.EG_T, UnitConversion.UnitsEnum.EG_KG);
							customsAccountingStatistics.setInRoughWet(conversion.doubleValue());
						}else if("??????".equals(one.getNetWetUnit())){
							customsAccountingStatistics.setInRoughWet(body.getRoughWet());
						}
						//???????????????kg???
						if("???".equals(one.getNetWetUnit())){
							//????????????????????????
							if(StrWithNumChkUtil.isRealNumber(body.getNetWet()) ){
								BigDecimal bigDecimal = new BigDecimal(body.getNetWet());
								BigDecimal conversion = UnitConversion.conversion(bigDecimal, UnitConversion.UnitsEnum.EG_T, UnitConversion.UnitsEnum.EG_KG);
								customsAccountingStatistics.setInNetWet(conversion.doubleValue());
							}
						}else if("??????".equals(one.getNetWetUnit())){
							customsAccountingStatistics.setInNetWet(StrWithNumChkUtil.isRealNumber(body.getNetWet()) ? Double.parseDouble(body.getNetWet()) : 0);
						}
						//??????????????????????????? --> ??????
						if("?????????".equals(one.getUnit())){
							customsAccountingStatistics.setInCargoAccount(body.getTotalPrice()*Double.parseDouble(rmbToDollar.getParamValue()));
						}else if("??????".equals(one.getUnit())){
							customsAccountingStatistics.setInCargoAccount(body.getTotalPrice()*Double.parseDouble(euroToDollar.getParamValue()));
						}else if("??????".equals(one.getUnit())){
							customsAccountingStatistics.setInCargoAccount(body.getTotalPrice());
						}

						//?????????????????????
						customsAccountingStatistics.setInOriginCountry(body.getImportCountry());
						//???????????????????????????
						customsAccountingStatistics.setInFinalDestination(body.getDistinationCountry());
						//???????????????????????????
						customsAccountingStatistics.setInCheckNum(body.getInCheckNum());
						// TODO ?????????????????????
						//customsAccountingStatistics.setInNuclearCouplet("");
						//????????????????????????
						//customsAccountingStatistics.setInCustomsDeclarationNum(body.getInCustomsDeclaration());
						// TODO ????????????????????????
						//customsAccountingStatistics.setInIdentificationCod("");
						// TODO ?????????????????????
						//customsAccountingStatistics.setInWaybillNum("");
						//??????????????????
						customsAccountingStatistics.setInCategory(one.getInCategory());
						////??????
						customsAccountingStatistics.setOutDte(yardBody.getRecordTim());
						//???????????????kg )
						QueryWrapper<InoutYardHead> yardWrapper= new QueryWrapper<>();
						yardWrapper.eq("f_id",yardBody.getHFid());
						yardWrapper.isNull("DEL_NAM");
						InoutYardHead one1 = inoutYardHeadService.getOne(yardWrapper);
						if("???".equals(one1.getNetWetUnit())){
							BigDecimal bigDecimal = new BigDecimal(body.getRoughWet());
							//????????????????????????,
							BigDecimal conversion = UnitConversion.conversion(bigDecimal, UnitConversion.UnitsEnum.EG_T, UnitConversion.UnitsEnum.EG_KG);
							customsAccountingStatistics.setOutRoughWet(conversion.doubleValue());
						}else if("??????".equals(one1.getNetWetUnit())){
							customsAccountingStatistics.setOutRoughWet(yardBody.getRoughWet());
						}
						//???????????????kg???
						if("???".equals(one1.getNetWetUnit())){
							//????????????????????????
							if(StrWithNumChkUtil.isRealNumber(body.getNetWet()) ){
								BigDecimal bigDecimal = new BigDecimal(body.getNetWet());
								BigDecimal conversion = UnitConversion.conversion(bigDecimal, UnitConversion.UnitsEnum.EG_T, UnitConversion.UnitsEnum.EG_KG);
								customsAccountingStatistics.setOutNetWet(conversion.doubleValue());
							}
						}else if("??????".equals(one1.getNetWetUnit())){
							//???????????????kg???
							customsAccountingStatistics.setOutNetWet(StrWithNumChkUtil.isRealNumber(yardBody.getNetWet()) ? Double.parseDouble(yardBody.getNetWet()) : 0);
						}
						if("?????????".equals(one.getUnit())){
							customsAccountingStatistics.setInCargoAccount(body.getTotalPrice()*Double.parseDouble(rmbToDollar.getParamValue()));
						}else if("??????".equals(one.getUnit())){
							customsAccountingStatistics.setInCargoAccount(body.getTotalPrice()*Double.parseDouble(euroToDollar.getParamValue()));
						}else if("??????".equals(one.getUnit())){
							customsAccountingStatistics.setInCargoAccount(yardBody.getTotalPrice());
						}

						//?????????????????????
						customsAccountingStatistics.setOutOriginCountry(yardBody.getImportCountry());
						//???????????????????????????
						customsAccountingStatistics.setOutFinalDestination(yardBody.getDistinationCountry());
						//???????????????????????????
						customsAccountingStatistics.setOutCheckNum(yardBody.getOutCheckNum());
						// TODO ?????????????????????
						//customsAccountingStatistics.setOutNuclearCouplet("");
						// TODO ????????????????????????
						customsAccountingStatistics.setOutCustomsDeclarationNum(yardBody.getOutCustomsDeclaration());
						// TODO ????????????????????????
						//customsAccountingStatistics.setOutIdentificationCod("");
						//TODO ?????????????????????
						//customsAccountingStatistics.setOutWaybillNum("");
						//??????????????????
						customsAccountingStatistics.setOutCategory(inoutYardHead.getOutCategory());
						customsAccountingStatistics.setOperNam(SecurityUtils.getUser().getUsername());
						customsAccountingStatistics.setRecordTim(new Date());
						save = customsAccountingStatisticsService.save(customsAccountingStatistics);
					}
				}
			//?????????????????????
			}else{
				CustomsAccountingStatistics customsAccountingStatistics = new CustomsAccountingStatistics();
				//???????????????
				customsAccountingStatistics.setConsignNam(one.getConsignNam());
				//??????
				customsAccountingStatistics.setCargoNamCustoms(body.getCargoNam());
				//??????
				customsAccountingStatistics.setCargoKindNam(body.getCargoKindNam());
				//??????
				customsAccountingStatistics.setInDte(one.getRecordTim());
//				//???????????????kg???
//				customsAccountingStatistics.setInRoughWet(body.getRoughWet());
//				//???????????????kg???
//				customsAccountingStatistics.setInNetWet(StrWithNumChkUtil.isRealNumber(body.getNetWet()) ? Double.parseDouble(body.getNetWet()) : 0);
				//???????????????kg???
				//???????????????????????????????????????????????????????????????
				//if("???".equals(one.getNetWetUnit()))
				if("???".equals(one.getNetWetUnit())){
					BigDecimal bigDecimal = new BigDecimal(body.getRoughWet());
					//????????????????????????,
					BigDecimal conversion = UnitConversion.conversion(bigDecimal, UnitConversion.UnitsEnum.EG_T, UnitConversion.UnitsEnum.EG_KG);
					customsAccountingStatistics.setInRoughWet(conversion.doubleValue());
				}else if("??????".equals(one.getNetWetUnit())){
					customsAccountingStatistics.setInRoughWet(body.getRoughWet());
				}
				//???????????????kg???
				if("???".equals(one.getNetWetUnit())){
					//????????????????????????
					if(StrWithNumChkUtil.isRealNumber(body.getNetWet()) ){
						BigDecimal bigDecimal = new BigDecimal(body.getNetWet());
						BigDecimal conversion = UnitConversion.conversion(bigDecimal, UnitConversion.UnitsEnum.EG_T, UnitConversion.UnitsEnum.EG_KG);
						customsAccountingStatistics.setInNetWet(conversion.doubleValue());
					}
				}else if("??????".equals(one.getNetWetUnit())){
					customsAccountingStatistics.setInNetWet(StrWithNumChkUtil.isRealNumber(body.getNetWet()) ? Double.parseDouble(body.getNetWet()) : 0);
				}

				//??????????????????????????? --> ??????
				if("?????????".equals(one.getUnit())){
					customsAccountingStatistics.setInCargoAccount(body.getTotalPrice()*0.1567);
				}else if("??????".equals(one.getUnit())){
					customsAccountingStatistics.setInCargoAccount(body.getTotalPrice()*1.13);
				}else if("??????".equals(one.getUnit())){
					customsAccountingStatistics.setInCargoAccount(body.getTotalPrice());
				}
				//?????????????????????
				customsAccountingStatistics.setInOriginCountry(body.getImportCountry());
				//???????????????????????????
				customsAccountingStatistics.setInFinalDestination(body.getDistinationCountry());
				//???????????????????????????
				customsAccountingStatistics.setInCheckNum(body.getInCheckNum());
				// TODO ?????????????????????
				//customsAccountingStatistics.setInNuclearCouplet("");
				//????????????????????????
				//customsAccountingStatistics.setInCustomsDeclarationNum(body.getInCustomsDeclaration());
				// TODO ????????????????????????
				//customsAccountingStatistics.setInIdentificationCod("");
				// TODO ?????????????????????
				//customsAccountingStatistics.setInWaybillNum("");
				//??????????????????
				customsAccountingStatistics.setInCategory(one.getInCategory());
				save = customsAccountingStatisticsService.save(customsAccountingStatistics);
			}
			//??????????????????????????????

		}
		if(save){
			return  R.ok(save);

		}else {
			return R.failed("??????????????????~~~");
		}
	}
	@ApiOperation(value = "?????????????????????????????????", notes = "?????????????????????????????????")
	@SysLog("?????????????????????????????????")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('customsaccounting_add')")
	public R save() {
		List<InoutYardBody>inoutYardBodyList = inoutYardBodyService.getList();
		return R.ok(inoutYardBodyList);
	}
	/**
	 * ?????????????????????????????????
	 *
	 * @param customsAccountingStatistics ???????????????????????????
	 * @return R
	 */
	@ApiOperation(value = "?????????????????????????????????", notes = "?????????????????????????????????")
	@SysLog("?????????????????????????????????")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('customsaccounting_edit')")
	public R updateById(@RequestBody CustomsAccountingStatistics customsAccountingStatistics) {
		customsAccountingStatistics.setUpdateNam(SecurityUtils.getUser().getUsername());
		customsAccountingStatistics.setUpdateTim(new Date());
		return R.ok(customsAccountingStatisticsService.updateById(customsAccountingStatistics));
	}

	/**
	 * ??????id?????????????????????????????????
	 *
	 * @param fId id
	 * @return R
	 */
	@ApiOperation(value = "??????id?????????????????????????????????", notes = "??????id?????????????????????????????????")
	@SysLog("??????id?????????????????????????????????")
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
		Double monthCZL=0.0; //????????????
		Double monthMYE=0.0; //?????????????????????
		Double yearCZL=0.0; //????????????
		Double yearCZLB=0.0; //????????????????????????
		Double yearHZ=0.0; //????????????????????????
		Double yearHZB=0.0; //??????????????????????????????
		Double monthOneDay=0.0;
		Double yearOneDay=0.0;
		String yearCZLFlg="??????";
		String yearHZFlg="??????";


		for(CustomsVo detail:customsVoList){
			monthCZL+=Double.parseDouble(detail.getMInNetWet())+Double.parseDouble(detail.getMOutNetWet())/10000;
			monthMYE+=Double.parseDouble(detail.getMOutCargoAccount())/10000;
			yearCZL+= Double.parseDouble(detail.getYInNetWet())+Double.parseDouble(detail.getYOutNetWet())/10000;
			yearCZLB+=Double.parseDouble(detail.getYNetWetScale().replace("%",""));
			yearHZ+=Double.parseDouble(detail.getYSumCargoAccount())/10000;
			yearHZB+=Double.parseDouble(detail.getYCargoAccountScale().replace("%",""));
		}
		if(yearCZLB>0){
			yearCZLFlg = "??????";
		}
		if(yearHZB>0){
			yearHZFlg = "??????";
		}

		Map<String,String> map = new HashMap<String,String>();
		map.put("mTime",customsVo.getInDte());
		map.put("yOldStartTime",customsVo.getYearStart());
		map.put("yOldEndTime",customsVo.getYearEnd());
		Map<String,Object> mapInfo = inoutYardHeadService.getOneDayCount(map);
		String param = "_year_???_month_????????????_monthCZL_??????,?????????????????????_monthMYE_????????????_year_???1-_month_???" +
				"?????????_yearCZL_??????,??????_oldyear_???_yearCZLFlg__yearCZLB_%,????????????????????????_yearHZ_?????????,??????_yearHZFlg__yearHZB_%????????????_month_????????????_m_cnt_???," +
				"???????????????_y_cnt_???";
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
					info.setCargoKindNam("??????");
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
	 * ???????????????
	 * @param customsOutAmountVo
	 * @return
	 */
	@GetMapping("/getOutAmount")
	public R getOutAmount(CustomsOutAmountVo customsOutAmountVo) {
		List<CustomsOutAmountVo> carGoList = new ArrayList<CustomsOutAmountVo>();
		List<CustomsOutAmountVo> customsVoList = customsAccountingStatisticsService.getOutAmount(customsOutAmountVo);
//		Map<String,List<CustomsOutAmountVo>> userMap = customsVoList.stream().collect(Collectors.groupingBy(CustomsOutAmountVo::getCargoNamCustoms));
//		System.out.println(userMap);

			double mOutCargoAccountValue = 0; //???????????????
			double mOldoutCargoAccountValue = 0; //?????????????????????
			double mUpoutCargoAccountValue = 0; //????????????????????????
			double mComparedCargoAccountValue = 0; //????????????
			double mRingCargoAccoutValue = 0; //????????????
			double yearOutCargoAccountValue = 0; //???????????????
			double yearOldoutCargoAccountValue = 0; //???????????????
			double yearComparedCargoAccountValue = 0; //????????????
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
					info.setCargoKindNam("??????");
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
	 * ????????????
	 * @param cargoKindTypeVo
	 * @return
	 */
	@GetMapping("/getCargoKind")
	public R getCargoKind(CargoKindTypeVo cargoKindTypeVo) {
		List<CargoKindTypeVo> customsVoList = customsAccountingStatisticsService.getCargoKind(cargoKindTypeVo);
		return R.ok(customsVoList);
	}

	/**
	 * ??????????????????
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
	 * ?????????????????????excel
	 * @param customsOutAmountVo
	 * @return
	 */
	@PostMapping("/getOutAmountExcel")
	public void getOutAmountExcel(@RequestBody CustomsOutAmountVo customsOutAmountVo, HttpServletResponse response) throws IOException {
		List<CustomsOutAmountVo> carGoList = new ArrayList<CustomsOutAmountVo>();
		List<CustomsOutAmountVo> customsVoList = customsAccountingStatisticsService.getOutAmount(customsOutAmountVo);
//		Map<String,List<CustomsOutAmountVo>> userMap = customsVoList.stream().collect(Collectors.groupingBy(CustomsOutAmountVo::getCargoNamCustoms));
//		System.out.println(userMap);

		double mOutCargoAccountValue = 0; //???????????????
		double mOldoutCargoAccountValue = 0; //?????????????????????
		double mUpoutCargoAccountValue = 0; //????????????????????????
		double mComparedCargoAccountValue = 0; //????????????
		double mRingCargoAccoutValue = 0; //????????????
		double yearOutCargoAccountValue = 0; //???????????????
		double yearOldoutCargoAccountValue = 0; //???????????????
		double yearComparedCargoAccountValue = 0; //????????????
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
				info.setCargoKindNam("??????");
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

	@ApiOperation(value = "??????excel", notes = "??????excel")
	@SysLog("??????excel" )
	@PostMapping("/exportManipulatedCargoExcel")
	public void exportManipulatedCargoExcel(@RequestBody CustomsCargoVo customsCargoVo, HttpServletResponse response) throws IOException {
		List<CustomsCargoVo> customsVoList = customsAccountingStatisticsService.getManipulatedCargo(customsCargoVo);
		customsAccountingStatisticsService.exportManipulatedCargoExcel(customsCargoVo,customsVoList,response);
	}

	@ApiOperation(value = "??????excel", notes = "??????excel")
	@SysLog("??????excel" )
	@PostMapping ("/getManipulatedExcel")
	public void getManipulatedExcel(@RequestBody CustomsVo customsVo, HttpServletResponse response) throws IOException {

		final List<CustomsVo> customsVoList = customsAccountingStatisticsService.getManipulated(customsVo);
		customsAccountingStatisticsService.expoprtManipulatedExcel(customsVo,customsVoList,response);
	}

	@ApiOperation(value = "??????excel", notes = "??????excel")
	@SysLog("??????excel" )
	@PostMapping ("/expoprtManipulatedForNewExcel")
	public void expoprtManipulatedForNewExcel(@RequestBody CustomsVo customsVo, HttpServletResponse response) throws IOException {

		final List<CustomsVo> customsVoList = customsAccountingStatisticsService.getManipulatedForNew(customsVo);
		Double monthCZL=0.0; //????????????
		Double monthMYE=0.0; //?????????????????????
		Double yearCZL=0.0; //????????????
		Double yearCZLB=0.0; //????????????????????????
		Double yearHZ=0.0; //????????????????????????
		Double yearHZB=0.0; //??????????????????????????????
		Double monthOneDay=0.0;
		Double yearOneDay=0.0;
		String yearCZLFlg="??????";
		String yearHZFlg="??????";

		for(CustomsVo detail:customsVoList){
			monthCZL+=Double.parseDouble(detail.getMInNetWet())+Double.parseDouble(detail.getMOutNetWet());
			monthMYE+=Double.parseDouble(detail.getMOutCargoAccount());
			yearCZL+= Double.parseDouble(detail.getYInNetWet())+Double.parseDouble(detail.getYOutNetWet());
			yearCZLB+=Double.parseDouble(detail.getYNetWetScale().replace("%",""));
			yearHZ+=Double.parseDouble(detail.getYSumCargoAccount());
			yearHZB+=Double.parseDouble(detail.getYCargoAccountScale().replace("%",""));
		}
		if(yearCZLB>0){
			yearCZLFlg = "??????";
		}
		if(yearHZB>0){
			yearHZFlg = "??????";
		}

		Map<String,String> map = new HashMap<String,String>();
		map.put("mTime",customsVo.getInDte());
		map.put("yOldStartTime",customsVo.getYearStart());
		map.put("yOldEndTime",customsVo.getYearEnd());
		Map<String,Object> mapInfo = inoutYardHeadService.getOneDayCount(map);
		String param = "_year_???_month_????????????_monthCZL_??????,?????????????????????_monthMYE_????????????_year_???1-_month_???" +
				"?????????_yearCZL_??????,??????_oldyear_???_yearCZLFlg__yearCZLB_%,????????????????????????_yearHZ_?????????,??????_yearHZFlg__yearHZB_%????????????_month_????????????_m_cnt_???," +
				"???????????????_y_cnt_???";
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


	@ApiOperation(value = "??????excel", notes = "??????excel")
	@SysLog("??????excel" )
	@PostMapping ("/e1")
	@PreAuthorize("@pms.hasPermission('customsaccounting_export')" )
	public void expoprtExcel( @RequestBody CustomsAccountingStatisticsVo customsAccountingStatisticsVo , HttpServletResponse response,Page page) throws IOException {
		customsAccountingStatisticsService.expoprtExcel(customsAccountingStatisticsVo,response,page);
	}
	@ApiOperation(value = "??????", notes = "??????")
	@SysLog("??????" )
	@PostMapping ("/count")
	public R count( @RequestBody CustomsAccountingStatisticsVo customsAccountingStatisticsVo)  {
		CustomsAccountingStatisticsVo customs =customsAccountingStatisticsService.countAll(customsAccountingStatisticsVo);
		return R.ok(customs);
	}


}