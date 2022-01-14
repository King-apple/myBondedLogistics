package com.newweb.mrli.ykport.baoshuisystem.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.BSUser;
import com.newweb.mrli.ykport.baoshuisystem.mapper.BSUserMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.BSUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@DS("slave")
@AllArgsConstructor
public class BSUserServiceImpl extends ServiceImpl<BSUserMapper,BSUser> implements BSUserService{

}
