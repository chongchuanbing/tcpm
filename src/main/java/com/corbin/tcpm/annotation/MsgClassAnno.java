/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月27日
 * Description:MsgClassAnno.java 
 */
package com.corbin.tcpm.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * tcp报文解析类注解(组合关系使用)
 * 
 * @author chong
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface MsgClassAnno {
	/**
	 * 解析位置,从1开始
	 * 
	 * @return
	 */
	int index();
}
