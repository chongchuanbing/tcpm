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
		if (null == formatParam || "".equals(formatParam)) {
			throw new RuntimeException("cannot get String length.");
		}
		
		int length = 0;
		try {
			length = Integer.valueOf(formatParam);
		} catch (NumberFormatException e) {
			throw new RuntimeException(e);
		}

		if (null == obj) {
			return new byte[length];
		}

		if (obj instanceof String) {

			String objStr = (String) obj;

			byte[] bytes = objStr.getBytes();

			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(length);
			if (length >= bytes.length) {
				byteBuffer.put(bytes);
				byteBuffer.put(new byte[length - bytes.length]);
			} else {
				byteBuffer.put(bytes, 0, length);
			}
			
			byteBuffer.flip();
			byte[] byteRe = new byte[length];
			byteBuffer.get(byteRe);

			return byteRe;
		}
		return null;
	}

	@Override
	public Object deserialize(ByteBuffer byteBuf, String formatParam) {

		if (null == byteBuf) {
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

		byte[] bytes = new byte[length];
		if (length >= byteBuf.remaining()) {
			byteBuf.get(byteBuf.remaining());

			return new String(bytes);
		} else {
			byteBuf.get(bytes);

			return new String(bytes);
		}
	}

}
