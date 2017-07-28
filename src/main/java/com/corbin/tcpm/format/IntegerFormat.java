/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月28日
 * Description:ByteFormat.java 
 */
package com.corbin.tcpm.format;

import java.nio.ByteBuffer;

/**
 * 整型format
 * 
 * @author chong
 */
public class IntegerFormat extends AbstractFormat {

	private int INTEGER_BYTE_LENGTH = 4;
	
	@Override
	public byte[] serialize(Object obj, String formatParam) {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(INTEGER_BYTE_LENGTH);

		if (null == obj) {
			return byteBuffer.array();
		}

		if (obj instanceof Integer) {
			Integer objInt = (Integer) obj;
			byteBuffer.putShort(objInt.shortValue());
		}

		return byteBuffer.array();
	}
	
	@Override
	public Object deserialize(byte[] bytes, String formatParam) {
		if (null == bytes) {
			return null;
		}

		if (INTEGER_BYTE_LENGTH <= bytes.length) {
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(INTEGER_BYTE_LENGTH);
			byteBuffer.put(bytes, 0, INTEGER_BYTE_LENGTH);

			return byteBuffer.getInt();
		}
		return null;
	}

}
