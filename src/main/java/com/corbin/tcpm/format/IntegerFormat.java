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
		if (null == obj) {
			return new byte[INTEGER_BYTE_LENGTH];
		}

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(INTEGER_BYTE_LENGTH);
		if (obj instanceof Integer) {
			Integer objInt = (Integer) obj;
			byteBuffer.putInt(objInt);
		}
		
		byteBuffer.flip();
		byte[] bytes = new byte[INTEGER_BYTE_LENGTH];
		byteBuffer.get(bytes);

		return bytes;
	}
	
	@Override
	public Object deserialize(ByteBuffer byteBuf, String formatParam) {
		if (null == byteBuf) {
			return null;
		}

		if (INTEGER_BYTE_LENGTH <= byteBuf.remaining()) {
			return byteBuf.getInt();
		}
		return null;
	}

}
