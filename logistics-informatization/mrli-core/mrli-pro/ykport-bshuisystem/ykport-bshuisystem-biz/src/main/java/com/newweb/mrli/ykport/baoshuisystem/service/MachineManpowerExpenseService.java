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
import com.newweb.mrli.ykport.baoshuisystem.entity.MachineManpowerExpense;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 机械人力作业费用明细表
 *
 * @author zada
 * @date 2021-10-22 16:46:51
 */
public interface MachineManpowerExpenseService extends IService<MachineManpowerExpense> {
	public IPage<?> getList(Page<?> page, MachineManpowerExpense machineManpowerExpense);

	public List<MachineManpowerExpense> getMachineManpowerExcel(MachineManpowerExpense machineManpowerExpense);

	public void expoprtMachineManpowerExcel(MachineManpowerExpense machineManpowerExpense, List<MachineManpowerExpense> list, HttpServletResponse response) throws IOException;
}
