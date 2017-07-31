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
		if (null == obj) {
			return new byte[SHORT_BYTE_LENGTH];
		}

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(SHORT_BYTE_LENGTH);
		if (obj instanceof Integer) {
			Integer objInt = (Integer) obj;
			byteBuffer.putShort(objInt.shortValue());
		} else if (obj instanceof Short) {
			Short objShort = (Short) obj;
			byteBuffer.putShort(objShort);
		}
		
		byteBuffer.slice();
		byte[] bytes = new byte[SHORT_BYTE_LENGTH];
		byteBuffer.get(bytes);

		return bytes;
	}

	@Override
	public Object deserialize(ByteBuffer byteBuf, String formatParam) {
		if (null == byteBuf) {
			return null;
		}

		if (SHORT_BYTE_LENGTH <= byteBuf.remaining()) {
			return byteBuf.getShort();
		}
		return null;
	}

}
