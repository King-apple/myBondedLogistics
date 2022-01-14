package com.newweb.mrli.ykport.baoshuisystem.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.CSubunit;
import com.newweb.mrli.ykport.baoshuisystem.mapper.CSubunitMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.CSubunitService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@DS("slave2")
@AllArgsConstructor
public class CSubunitServiceImpl extends ServiceImpl<CSubunitMapper, CSubunit> implements CSubunitService {

}
