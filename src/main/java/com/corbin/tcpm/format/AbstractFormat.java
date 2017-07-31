/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月28日
 * Description:AbstractFormat.java 
 */
package com.corbin.tcpm.format;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * 抽象报文format
 * 
 * @author chong
 */
public abstract class AbstractFormat implements Format {

	@Override
	public byte[] serialize(Object obj, String formatParam) {
		byte[] bytes = null;

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
			oos.close();
			bos.close();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		return bytes;
	}

	@Override
	public Object deserialize(ByteBuffer byteBuffer, String formatParam) {
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(byteBuffer.array());
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			ois.close();
			bis.close();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
		return obj;
	}
}
