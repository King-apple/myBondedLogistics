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

import com.baomidou.mybatisplus.extension.service.IService;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.ykport.baoshuisystem.entity.OrganizerInfo;

import java.util.List;

/**
 * 承办单位信息表
 *
 * @author pigx code generator
 * @date 2021-07-29 10:08:16
 */
public interface OrganizerInfoService extends IService<OrganizerInfo> {

	Integer selectFid();

	List<OrganizerInfo> selectAll(OrganizerInfo organizerInfo);

	R insertInfo(OrganizerInfo organizerInfo);
}
