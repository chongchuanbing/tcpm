/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月11日
 * Description:AbstractMessage.java 
 */
package com.corbin.tcpm.message;

import java.nio.ByteBuffer;

import com.corbin.tcpm.util.ByteUtil;

/**
 * 抽象netty消息对象
 * 
 * @author chong
 */
public abstract class AbstractMessage implements Message {

	/**
	 * 获取对象的字节数组
	 * 
	 * @return
	 */
	@Override
	public byte[] getBytes() {
		ByteBuffer byteBuffer = ByteUtil.getByte(this);
		return byteBuffer.array();
	}

	/**
	 * 字节数组转化为对象
	 * 
	 * @param bytes
	 * @return
	 */
	@Override
	public AbstractMessage getObj(byte[] bytes) {

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bytes.length);

		// TODO

		return this;
	}

}
