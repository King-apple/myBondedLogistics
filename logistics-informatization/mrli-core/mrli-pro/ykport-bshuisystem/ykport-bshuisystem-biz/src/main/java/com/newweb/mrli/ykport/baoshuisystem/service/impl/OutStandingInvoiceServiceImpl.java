package com.newweb.mrli.ykport.baoshuisystem.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newweb.mrli.ykport.baoshuisystem.entity.OutStandingInvoice;
import com.newweb.mrli.ykport.baoshuisystem.mapper.OutStandingInvoiceMapper;
import com.newweb.mrli.ykport.baoshuisystem.service.OutStandingInvoiceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
@DS("slave2")
@AllArgsConstructor
public class OutStandingInvoiceServiceImpl extends ServiceImpl<OutStandingInvoiceMapper, OutStandingInvoice> implements OutStandingInvoiceService{

	@Resource
	private  OutStandingInvoiceMapper outStandingInvoiceMapper;
	@Override
	public List<String> getCBS() {


		return outStandingInvoiceMapper.getCbs();
	}
}
