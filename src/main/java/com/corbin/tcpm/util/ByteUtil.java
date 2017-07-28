/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月13日
 * Description:ByteUtil.java 
 */
package com.corbin.tcpm.util;

import java.lang.reflect.Constructor;

import com.corbin.tcpm.message.AbstractMessage;

/**
 * byte工具类
 * 
 * @author chong
 */
public class ByteUtil {

	/**
	 * 字节数组转化为指定对象
	 * 
	 * @param bytebuf
	 * @param class1
	 * @return
	 */
	public static <T extends AbstractMessage> T byteToObj(byte[] bytes, Class<T> clasz) {

		T message = null;
		try {
			Constructor<T> constructor = clasz.getDeclaredConstructor(new Class[] {});

			message = constructor.newInstance(new Object[] {});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (null == message) {
			return null;
		}

		message.getObj(bytes);

		return message;
	}

}
