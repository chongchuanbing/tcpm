/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月31日
 * Description:CommonConstant.java 
 */
package com.corbin.tcpm.constant;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 通用常量
 * 
 * @author chong
 */
public interface CommonConstant {

	// 扩容大小
	public static int CAPACITY_SIZE = 200;

	/**
	 * 基础包装类
	 */
	public static Set<String> commonTypeSet = new HashSet<String>(){
		
		private static final long serialVersionUID = 1L;

		{
			add(Integer.class.getName());
			add(Short.class.getName());
			add(Long.class.getName());
			add(Double.class.getName());
			add(Float.class.getName());
			add(BigDecimal.class.getName());
			add(String.class.getName());
			add(Date.class.getName());
		}
	};

}
