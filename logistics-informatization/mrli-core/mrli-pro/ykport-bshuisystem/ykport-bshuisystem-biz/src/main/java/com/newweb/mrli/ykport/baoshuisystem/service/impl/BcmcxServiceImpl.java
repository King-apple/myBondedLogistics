package com.newweb.mrli.ykport.baoshuisystem.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.BSUser;
import com.newweb.mrli.ykport.baoshuisystem.mapper.BSUserMapper;
import com.newweb.mrli.ykport.baoshuisystem.mapper.BcmcxMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.BSUserService;
import com.newweb.mrli.ykport.baoshuisystem.service.BcmcxService;
import com.newweb.mrli.ykport.baoshuisystem.vo.BcmcxVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BcmcxServiceImpl extends ServiceImpl<BcmcxMapper, BcmcxVo> implements BcmcxService{

}
