package com.newweb.mrli.ykport.baoshuisystem.mapper;


import com.newweb.mrli.common.data.datascope.MrliBaseMapper;
import com.newweb.mrli.ykport.baoshuisystem.entity.OutStandingInvoice;
import com.newweb.mrli.ykport.baoshuisystem.entity.ParameterList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OutStandingInvoiceMapper  extends MrliBaseMapper<OutStandingInvoice> {
	List<String> getCbs();

}
