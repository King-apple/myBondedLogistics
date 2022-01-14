package com.newweb.mrli.ykport.baoshuisystem.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.BSUser;
import com.newweb.mrli.ykport.baoshuisystem.entity.CDatacodes;
import com.newweb.mrli.ykport.baoshuisystem.mapper.CDatacodesMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.CDatacodesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@DS("slave2")
@AllArgsConstructor
public class CDatacodesServiceImpl extends ServiceImpl<CDatacodesMapper, CDatacodes> implements CDatacodesService {

}
