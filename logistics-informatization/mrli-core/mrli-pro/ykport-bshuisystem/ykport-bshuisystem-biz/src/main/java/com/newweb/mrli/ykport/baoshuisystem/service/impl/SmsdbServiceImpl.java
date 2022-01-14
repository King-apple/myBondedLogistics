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
package com.newweb.mrli.ykport.baoshuisystem.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.Smsdb;
import com.newweb.mrli.ykport.baoshuisystem.mapper.SmsdbMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.SmsdbService;
import org.springframework.stereotype.Service;

/**
 * ${comments}
 *
 * @author pigx code generator
 * @date 2021-11-02 16:44:49
 */
@Service
@DS("slave4")
public class SmsdbServiceImpl extends ServiceImpl<SmsdbMapper, Smsdb> implements SmsdbService {

}
