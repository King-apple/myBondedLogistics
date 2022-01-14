package com.newweb.mrli.ykport.baoshuisystem.entity;

public class SqlCondition {
	/**
	 *mybatis原生的SqlCondition是面向mysql的，在使用字段注解LIKE时，
	 * 用一个COMTACT函数拼接三个参数，会报错，因此重写
	 */
	public static final String like = "%s LIKE CONCAT(CONCAT('%%',#{%s}),'%%')";
}
