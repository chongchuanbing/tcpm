/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月28日
 * Description:BigDecimalFormat.java 
 */
package com.corbin.tcpm.format;

import java.math.BigDecimal;
import java.nio.ByteBuffer;

/**
 * @author chong
 */
public class BigDecimalFormat extends AbstractFormat {

	private static int FLOAT_BYTE_LENGTH = 4;

	@Override
	public byte[] serialize(Object obj, String formatParam) {
		if (null == obj) {
			return new byte[FLOAT_BYTE_LENGTH];
		}
		if (obj instanceof BigDecimal) {

			BigDecimal objDecimal = (BigDecimal) obj;

			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(FLOAT_BYTE_LENGTH);
			byteBuffer.putFloat(objDecimal.floatValue());

			return byteBuffer.array();
		}
		return null;
	}

	@Override
	public Object deserialize(ByteBuffer byteBuffer, String formatParam) {
		if (null == byteBuffer) {
			return null;
		}

		if (FLOAT_BYTE_LENGTH <= byteBuffer.remaining()) {
			return byteBuffer.getShort();
		}
		return null;
	}

}
