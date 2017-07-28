/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月28日
 * Description:ShortFormat.java 
 */
package com.corbin.tcpm.format;

import java.nio.ByteBuffer;

/**
 * 短整型format
 * 
 * @author chong
 */
public class ShortFormat extends AbstractFormat {

	// short类型字节长度
	private int SHORT_BYTE_LENGTH = 2;

	@Override
	public byte[] serialize(Object obj, String formatParam) {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(SHORT_BYTE_LENGTH);

		if (null == obj) {
			return byteBuffer.array();
		}

		if (obj instanceof Integer) {
			Integer objInt = (Integer) obj;
			byteBuffer.putShort(objInt.shortValue());
		} else if (obj instanceof Short) {
			Short objShort = (Short) obj;
			byteBuffer.putShort(objShort);
		}

		return byteBuffer.array();
	}

	@Override
	public Object deserialize(byte[] bytes, String formatParam) {
		if (null == bytes) {
			return null;
		}

		if (SHORT_BYTE_LENGTH <= bytes.length) {
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(SHORT_BYTE_LENGTH);
			byteBuffer.put(bytes, 0, SHORT_BYTE_LENGTH);

			return byteBuffer.getShort();
		}
		return null;
	}

}
