/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月13日
 * Description:ByteUtil.java 
 */
package com.corbin.tcpm.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.LinkedList;

import com.corbin.tcpm.annotation.MsgAttrAnno;
import com.corbin.tcpm.message.AbstractMessage;

/**
 * byte工具类
 * 
 * @author chong
 */
public class ByteUtil {

	/**
	 * 获取对象的byteBuffer
	 * 
	 * @param obj
	 * @return
	 */
	public static ByteBuffer getByte(Object obj) {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(Integer.MAX_VALUE);

		LinkedList<Field> fieldLinkedList = new LinkedList<>();

		// 迭代获取报文
		FieldUtil.repeatGetObjAttr(obj, fieldLinkedList, byteBuffer);

		// Iterator<Field> fieldIter = fieldLinkedList.iterator();
		// while (fieldIter.hasNext()) {
		// Field field = fieldIter.next();
		//
		// if (null == field) {
		// continue;
		// }
		//
		// // 这边的field肯定都是基础类型，直接按照注解配置规则解析为byte即可
		// byte[] bytes = parseField(field);
		//
		// byteBuffer.put(bytes);
		// }
		return byteBuffer;
	}

	/**
	 * 解析field为对应的byte数组
	 * 
	 * @param field
	 * @return
	 */
	public static byte[] parseField(Object obj, Field field) {

		MsgAttrAnno msgAttrAnno = field.getAnnotation(MsgAttrAnno.class);
		if (null == msgAttrAnno) {
			throw new RuntimeException("the attribute [" + field.getName() + "] has nono message annotation.");
		}

		int byteLength = 0;
		String formatParam = msgAttrAnno.formatParam();
		if (null == formatParam || "".equals(formatParam)) {
			try {
				byteLength = Integer.valueOf(formatParam);
			} catch (NumberFormatException e) {
				throw new RuntimeException(e);
			}
		}
		if (0 >= byteLength) {
			throw new RuntimeException("the attribute [" + field.getName() + "] byteLength has error data.");
		}

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

		if (null == bytes) {
			return null;
		}

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bytes.length);
		if (byteLength >= bytes.length) {
			byteBuffer.put(bytes);
			byteBuffer.put(new byte[byteLength - bytes.length]);
		} else {
			byteBuffer.put(bytes, 0, byteLength);
		}

		return byteBuffer.array();
	}

	/**
	 * 字节数组转化为指定对象
	 * 
	 * @param bytebuf
	 * @param class1
	 * @return
	 */
	public static <T extends AbstractMessage> T byteToObj(byte[] bytes, Class<T> clasz) {

		T message = null;
		try {
			Constructor<T> constructor = clasz.getDeclaredConstructor(new Class[] {});

			message = constructor.newInstance(new Object[] {});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (null == message) {
			return null;
		}

		message.getObj(bytes);

		return message;
	}

}
