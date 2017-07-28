/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月11日
 * Description:AbstractMessage.java 
 */
package com.corbin.tcpm.message;

/**
 * 抽象netty消息对象
 * 
 * @author chong
 */
public interface Message {

	/**
	 * 获取对象的字节数组
	 * 
	 * @return
	 */
	public abstract byte[] getBytes();

	/**
	 * 字节数组转化为对象
	 * 
	 * @param bytes
	 * @return
	 */
	public abstract Message getObj(byte[] bytes);

}
