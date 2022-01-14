package com.newweb.mrli.ykport.baoshuisystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newweb.mrli.ykport.baoshuisystem.entity.BSUserRole;
import com.newweb.mrli.ykport.baoshuisystem.vo.BSUserRoleNameAndCodeVo;

import java.util.List;

public interface BSUserRoleService extends IService<BSUserRole> {
	public List<BSUserRoleNameAndCodeVo> getSelectRoleNameAndroleCodeList(List<Integer> list);
}
