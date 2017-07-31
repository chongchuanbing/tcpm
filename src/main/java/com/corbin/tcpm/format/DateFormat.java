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
		if (null == formatParam || "".equals(formatParam)) {
			throw new RuntimeException("cannot get String length.");
		}
		
		if (null == obj) {
			return new byte[formatParam.length()];
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
			
			byteBuffer.flip();
			byte[] byteRe = new byte[formatParam.length()];
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

		int length = formatParam.length();

		byte[] bytes = new byte[length];

		String objStr = null;
		if (length > byteBuf.remaining()) {
			byte[] byteTemp = new byte[byteBuf.remaining()];
			
			byteBuf.get(byteTemp);

			objStr = new String(byteTemp);
		} else {
			byteBuf.get(bytes);

			objStr = new String(bytes);
		}

		return DateUtil.str2DateTime(objStr);

	}

}
