package com.newweb.mrli.ykport.baoshuisystem.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.BSUser;
import com.newweb.mrli.ykport.baoshuisystem.entity.CFeeWay;
import com.newweb.mrli.ykport.baoshuisystem.mapper.CFeeWayMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.CFeeWayService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@DS("slave2")
@AllArgsConstructor
public class CFeeWayServiceImpl extends ServiceImpl<CFeeWayMapper, CFeeWay> implements CFeeWayService {

}
