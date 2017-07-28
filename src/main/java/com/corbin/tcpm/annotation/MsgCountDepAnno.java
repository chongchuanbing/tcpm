/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月28日
 * Description:MsgCountDepAnno.java 
 */
package com.corbin.tcpm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * tcp报文解析属性依赖注解
 * 
 * 主要是集合依赖count
 * 
 * @author chong
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MsgCountDepAnno {

	/**
	 * 依赖的属性名称，依赖的属性必须是int型
	 * 
	 * @return
	 */
	String attrName();
}
