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

import com.newweb.mrli.common.data.datascope.MrliBaseMapper;
import com.newweb.mrli.ykport.baoshuisystem.entity.Contract;
import com.newweb.mrli.ykport.baoshuisystem.entity.Ioyard;
import com.newweb.mrli.ykport.baoshuisystem.vo.IoyardVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 货物出入库表
 *
 * @author pigx code generator
 * @date 2021-09-06 19:35:20
 */
@Mapper
public interface IoyardMapper extends MrliBaseMapper<Ioyard> {


	int insertOutStroge(Ioyard ioyard);

	List<IoyardVo> getIoyardVo(Ioyard ioyard);

    List<Ioyard> getContNo();

	List<Ioyard> getShip();

	List<Ioyard> getCargo();

	List<Ioyard> getConSign();

	Ioyard getAll(@Param("ioYard")Ioyard ioyard,@Param("parse") String parse,@Param("parse1") String parse1);

    List<Ioyard> getBillNO();

	List<Ioyard> getCustomsDeclaration();

//	Ioyard getAll(@Param("ioYard") Ioyard ioyard);
}
