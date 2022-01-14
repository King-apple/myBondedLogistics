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

package com.newweb.mrli.ykport.baoshuisystem.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newweb.mrli.common.data.datascope.MrliBaseMapper;
import com.newweb.mrli.ykport.baoshuisystem.entity.CSubunit;
import com.newweb.mrli.ykport.baoshuisystem.entity.MStoreStatus;
import com.newweb.mrli.ykport.baoshuisystem.vo.CUnitNamVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.CargoVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.ShipVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.ShipperDocVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 港存动态（局）
 *
 * @author pigx code generator
 * @date 2021-10-19 18:28:44
 */
@Mapper
public interface MStoreStatusMapper extends MrliBaseMapper<MStoreStatus> {

	@Select("SELECT UNIT_COD unitCod,UNIT_NAM unitNam FROM ykport0.C_UNIT_NAM WHERE MARK_TXT='1'")
	public List<CUnitNamVo> getCunitNam();

	@Select("SELECT CARGO_NAM_COD cargoNamCod, CARGO_NAM cargoNam, CARGO_KIND_COD cargoKindCod,CARGO_KIND_NAM cargoKindNam FROM ykport0.C_CARGO_NAM ORDER BY CARGO_NAM_COD")
	public List<CargoVo> getCargoNam();

	@Select("SELECT SUBUNIT_COD subunitCod,SUBUNIT_NAM subunitNam FROM ykport0.C_SUBUNIT WHERE UNIT_COD= #{uniCode} " +
			"AND MARK_ID='0'")
//	@Select("SELECT SUBUNIT_COD subunitCod,SUBUNIT_NAM subunitNam FROM C_SUBUNIT WHERE 1=1 " +
//		"AND MARK_ID='0'")
	List<CSubunit> getSubunit(String uniCode);

	@Select("<script>" +
			"SELECT ALL SHIP.SHIP_NO, SHIP.SHIP_NAM, TO_CHAR(SHIP.TO_PORT_TIM,'YYYY/MM/DDHH24:MI'),TO_CHAR(SHIP.LEV_PORT_TIM,'YYYY/MM/DD HH24:MI') FROM ykport0.SHIP " +
			"<if test=\"shipVo.shipNam != null and shipVo.shipNam != ''\">"+
			" where	ship_nam = #{shipVo.shipNam}" +
			"</if>" +
			" ORDER BY TO_PORT_TIM" +
			"</script>")
	IPage<ShipVo> getShip(Page<?> page, @Param("shipVo")ShipVo shipVo);

	@Select("SELECT ALL SHIPPER_DOC.SHIPPER_COD, SHIPPER_DOC.SHIPPER_DOC, SHIPPER_DOC.SHOT_NAM " +
			"FROM ykport0.SHIPPER_DOC  ORDER BY SHIPPER_COD")
	List<ShipperDocVo> getShipperDoc();
}
