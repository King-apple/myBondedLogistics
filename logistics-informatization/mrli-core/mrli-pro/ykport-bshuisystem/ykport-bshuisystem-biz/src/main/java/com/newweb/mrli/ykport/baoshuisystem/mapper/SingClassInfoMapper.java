package com.newweb.mrli.ykport.baoshuisystem.mapper;

import com.newweb.mrli.common.data.datascope.MrliBaseMapper;
import com.newweb.mrli.ykport.baoshuisystem.entity.SingClassInfo;
import com.newweb.mrli.ykport.baoshuisystem.entity.YkbfSingClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SingClassInfoMapper  extends MrliBaseMapper<SingClassInfo> {
	List<SingClassInfo> printToExcel(@Param("ykbfSingClass") YkbfSingClass ykbfSingClass);

	List<SingClassInfo> printInOutFlag(@Param("ykbfSingClass") YkbfSingClass ykbfSingClass);
}
