/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月28日
 * Description:StringFormat.java 
 */
package com.corbin.tcpm.format;

import java.nio.ByteBuffer;

/**
 * 字符串format
 * 
 * @author chong
 */
public class StringFormat extends AbstractFormat {

	@Override
	public byte[] serialize(Object obj, String formatParam) {
		if (null == obj) {
			return null;
		}

		if (null == formatParam || "".equals(formatParam)) {
			return null;
		}

		if (obj instanceof String) {

			String objStr = (String) obj;

			int length = 0;
			try {
				length = Integer.valueOf(formatParam);
			} catch (NumberFormatException e) {
				throw new RuntimeException(e);
			}

			byte[] bytes = objStr.getBytes();

			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(length);
			byteBuffer.put(bytes, 0, length);

			return byteBuffer.array();
		}
		return null;
	}

	@Override
	public Object deserialize(byte[] bytes, String formatParam) {

		if (null == bytes) {
			return null;
		}

		if (null == formatParam || "".equals(formatParam)) {
			return null;
		}

		int length = 0;
		try {
			length = Integer.valueOf(formatParam);
		} catch (NumberFormatException e) {
			throw new RuntimeException(e);
		}

		if (length >= bytes.length) {
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(length);
			byteBuffer.put(bytes);
			byteBuffer.put(new byte[length - bytes.length]);

			return new String(byteBuffer.array());
		} else {
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(length);
			byteBuffer.put(bytes, 0, length);

			return new String(byteBuffer.array());
		}
	}

}
