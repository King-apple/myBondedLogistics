package com.newweb.mrli.ykport.baoshuisystem.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newweb.mrli.ykport.baoshuisystem.entity.BSUser;

@DS("slave")
public interface BSUserService extends IService<BSUser> {

}
