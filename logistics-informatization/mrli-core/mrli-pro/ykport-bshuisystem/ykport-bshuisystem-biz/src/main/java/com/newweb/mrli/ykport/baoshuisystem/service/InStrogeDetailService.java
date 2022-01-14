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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newweb.mrli.common.core.util.R;
import com.newweb.mrli.ykport.baoshuisystem.entity.ApprovalProcessSet;
import com.newweb.mrli.ykport.baoshuisystem.entity.Bcmcxtab;
import com.newweb.mrli.ykport.baoshuisystem.vo.InStrogeDetail;
import com.newweb.mrli.ykport.baoshuisystem.vo.IoyardApprovalProcessSetVO;

import java.util.List;

/**
 * ${comments}
 *
 * @author pigx code generator
 * @date 2021-09-06 19:04:43
 */
public interface InStrogeDetailService extends IService<InStrogeDetail> {

	R upImage(InStrogeDetail inStrogeDetail, List<Bcmcxtab> bcmcxtabList);

	R destroyImage(InStrogeDetail inStrogeDetail);

	R submitOverShortCheck(ApprovalProcessSet approvalProcessSet);

	R getOverShortCargo(Page page, IoyardApprovalProcessSetVO approvalProcessSet);

	R submitCheck(ApprovalProcessSet approvalProcessSet);
}
