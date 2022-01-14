package com.newweb.mrli.ykport.baoshuisystem.api.feign;

import com.newweb.mrli.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mrli-upms-biz")
public interface UserFeignClient {
	@RequestMapping("user/{id}")
	R queryUserById(@PathVariable("id") Integer id);

	@GetMapping("role/{id}")
	R getById(@PathVariable("id") Integer id);

}
