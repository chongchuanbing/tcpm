/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月27日
 * Description:MsgAttrAnno.java 
 */
package com.corbin.tcpm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.corbin.tcpm.format.AbstractFormat;
import com.corbin.tcpm.format.CommonFormat;

/**
 * tcp报文解析属性注解
 * 
 * @author chong
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MsgAttrAnno {
	/**
	 * 解析位置,从1开始
	 * 
	 * @return
	 */
	int index();

	/**
	 * 序列化模版
	 * 
	 * @return
	 */
	Class<? extends AbstractFormat> format() default CommonFormat.class;

	/**
	 * format的动态参数
	 * 
	 * @return
	 */
	String formatParam() default "";
}
