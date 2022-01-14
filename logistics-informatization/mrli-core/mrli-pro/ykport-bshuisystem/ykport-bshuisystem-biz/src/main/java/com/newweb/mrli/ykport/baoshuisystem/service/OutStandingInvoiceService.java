package com.newweb.mrli.ykport.baoshuisystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newweb.mrli.ykport.baoshuisystem.entity.CustomsAccountingStatistics;
import com.newweb.mrli.ykport.baoshuisystem.entity.OutStandingInvoice;

import java.util.List;

public interface OutStandingInvoiceService extends IService<OutStandingInvoice> {


	List<String> getCBS();
}
