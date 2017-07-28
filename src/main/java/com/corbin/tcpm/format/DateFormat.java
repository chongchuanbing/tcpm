/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月28日
 * Description:DateFormat.java 
 */
package com.corbin.tcpm.format;

import java.nio.ByteBuffer;
import java.util.Date;

import com.corbin.tcpm.util.DateUtil;

/**
 * 日期format
 * 
 * @author chong
 */
public class DateFormat extends AbstractFormat {

	@Override
	public byte[] serialize(Object obj, String formatParam) {
		if (null == obj) {
			return null;
		}

		if (null == formatParam || "".equals(formatParam)) {
			return null;
		}

		if (obj instanceof Date) {

			Date objDate = (Date) obj;

			String objStr = null;
			try {
				objStr = DateUtil.format(objDate, formatParam);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			byte[] bytes = objStr.getBytes();

			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(objStr.length());
			byteBuffer.put(bytes);

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

		int length = formatParam.length();

		String objStr = null;
		if (length >= bytes.length) {
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(length);
			byteBuffer.put(bytes);
			byteBuffer.put(new byte[length - bytes.length]);

			objStr = new String(byteBuffer.array());
		} else {
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(length);
			byteBuffer.put(bytes, 0, length);

			objStr = new String(byteBuffer.array());
		}

		return DateUtil.str2DateTime(objStr);

	}

}
