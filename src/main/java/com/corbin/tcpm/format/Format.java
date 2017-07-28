/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月28日
 * Description:Format.java 
 */
package com.corbin.tcpm.format;

/**
 * 报文解析属性格式化接口
 * 
 * @author chong
 */
public interface Format {

	/**
	 * 对象转字节报文
	 * 
	 * @return
	 */
	public byte[] serialize(Object obj, String formatParam);

	/**
	 * 字节报文转对象
	 * 
	 * @param bytes
	 * @return
	 */
	public Object deserialize(byte[] bytes, String formatParam);

}
