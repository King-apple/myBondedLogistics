package com.newweb.mrli.ykport.baoshuisystem.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.BSUserRole;
import com.newweb.mrli.ykport.baoshuisystem.mapper.BSUserRoleMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.BSUserRoleService;
import com.newweb.mrli.ykport.baoshuisystem.vo.BSUserRoleNameAndCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("slave")
public class BSUserRoleServiceImpl extends ServiceImpl<BSUserRoleMapper, BSUserRole> implements BSUserRoleService {
	@Autowired
	public BSUserRoleMapper bSUserRoleMapper;

	@Override
	public List<BSUserRoleNameAndCodeVo> getSelectRoleNameAndroleCodeList(List<Integer> list){
		return bSUserRoleMapper.getSelectRoleNameAndroleCodeList(list);
	}
}
