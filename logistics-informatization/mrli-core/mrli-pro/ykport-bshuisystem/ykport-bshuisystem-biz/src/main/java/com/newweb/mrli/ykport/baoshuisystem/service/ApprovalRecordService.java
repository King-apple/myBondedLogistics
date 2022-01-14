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
import com.newweb.mrli.ykport.baoshuisystem.entity.ApprovalRecord;

import java.util.List;

/**
 * 审批记录表
 *
 * @author pigx code generator
 * @date 2021-08-28 18:10:46
 */
public interface ApprovalRecordService extends IService<ApprovalRecord> {


	R insertRecords(ApprovalRecord approvalRecord);

	R insertUserRecords(String userRecords);

	R getApprovalRecords(ApprovalRecord approvalRecord);

	R updateRecordById(ApprovalRecord approvalRecord) throws Exception;

	List<ApprovalRecord> selectApprovalRecordGroup(ApprovalRecord approvalRecord);

}
