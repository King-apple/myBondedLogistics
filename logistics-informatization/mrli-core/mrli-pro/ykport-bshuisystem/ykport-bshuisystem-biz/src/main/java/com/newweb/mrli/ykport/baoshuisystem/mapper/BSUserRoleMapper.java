package com.newweb.mrli.ykport.baoshuisystem.mapper;

import com.newweb.mrli.common.data.datascope.MrliBaseMapper;
import com.newweb.mrli.ykport.baoshuisystem.entity.BSUserRole;
import com.newweb.mrli.ykport.baoshuisystem.vo.BSUserRoleNameAndCodeVo;
import com.newweb.mrli.ykport.baoshuisystem.vo.BillApproveVo;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BSUserRoleMapper extends MrliBaseMapper<BSUserRole> {

	public List<BSUserRoleNameAndCodeVo> getSelectRoleNameAndroleCodeList(@Param("list")List<Integer> list);
}
