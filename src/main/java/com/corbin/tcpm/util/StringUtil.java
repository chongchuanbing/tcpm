/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月28日
 * Description:StringUtil.java 
 */
package com.corbin.tcpm.util;

/**
 * 字符串工具类
 * 
 * @author chong
 */
public class StringUtil {
	/**
	 * 首字母转大写
	 * 
	 * @param s
	 * @return
	 */
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0))) {
			return s;
		} else {
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
		}
	}
}
