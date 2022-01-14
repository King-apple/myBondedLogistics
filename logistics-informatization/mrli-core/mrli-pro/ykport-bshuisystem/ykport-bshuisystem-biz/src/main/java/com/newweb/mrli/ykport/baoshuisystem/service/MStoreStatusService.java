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

package com.newweb.mrli.ykport.baoshuisystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newweb.mrli.ykport.baoshuisystem.entity.CSubunit;
import com.newweb.mrli.ykport.baoshuisystem.entity.MStoreStatus;
import com.newweb.mrli.ykport.baoshuisystem.vo.CUnitNamVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.CargoVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.ShipVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.ShipperDocVo;

import java.io.IOException;
import java.util.List;

/**
 * 港存动态（局）
 *
 * @author pigx code generator
 * @date 2021-10-19 18:28:44
 */
public interface MStoreStatusService extends IService<MStoreStatus> {

	List<CUnitNamVo> getCunitNam();

	List<CargoVo> getCargoNam();

	List<CSubunit> getSubunit(String uniCode);

	IPage<ShipVo> getShip(Page<?> page, ShipVo shipVo);

	List<ShipperDocVo> getShipperDoc();

	void printToExcel(MStoreStatus mStoreStatus) throws IOException;
}
