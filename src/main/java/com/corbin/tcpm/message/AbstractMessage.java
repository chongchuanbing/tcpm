/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月11日
 * Description:AbstractMessage.java 
 */
package com.corbin.tcpm.message;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.corbin.tcpm.annotation.MsgAttrAnno;
import com.corbin.tcpm.annotation.MsgClassAnno;
import com.corbin.tcpm.constant.MsgAnnoConstant;

/**
 * 抽象netty消息对象
 * 
 * @author chong
 */
public abstract class AbstractMessage extends Message {

	/**
	 * 获取对象的字节数组
	 * 
	 * @return
	 */
	public byte[] getBytes() {
		ByteBuffer byteBuffer = getByte(this);
		return byteBuffer.array();
	}

	/**
	 * 字节数组转化为对象
	 * 
	 * @param bytes
	 * @return
	 */
	public AbstractMessage getObj(byte[] bytes) {

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bytes.length);

		// TODO

		return this;
	}

	/**
	 * 获取对象的byteBuffer
	 * 
	 * @param obj
	 * @return
	 */
	private ByteBuffer getByte(Object obj) {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(Integer.MAX_VALUE);

		LinkedList<Field> fieldLinkedList = new LinkedList<>();

		// 迭代获取报文
		repeatGetObjAttr(obj.getClass(), fieldLinkedList);

		Iterator<Field> fieldIter = fieldLinkedList.iterator();
		while (fieldIter.hasNext()) {
			Field field = fieldIter.next();

			if (null == field) {
				continue;
			}

			// 这边的field肯定都是基础类型，直接按照注解配置规则解析为byte即可
			byte[] bytes = parseField(field);

			byteBuffer.put(bytes);
		}
		return byteBuffer;
	}

	/**
	 * 解析field为对应的byte数组
	 * 
	 * @param field
	 * @return
	 */
	private byte[] parseField(Field field) {

		// TODO

		return null;
	}

	/**
	 * 解析报文解析属性
	 * 
	 * @param obj
	 * @param fieldLinkedList
	 * @param curLevel
	 *            针对非java内置对象要进行报文解析，解析时需要指定当前深度,0默认为第一级
	 */
	private void repeatGetObjAttr(Class<?> clasz, LinkedList<Field> fieldLinkedList) {
		// 迭代获取进行报文解析的属性

		Field[] fieldArr = clasz.getDeclaredFields();
		if (null != fieldArr) {

			// 剔除不含报文解析注解的属性
			List<Field> fieldList = eliminateNonMsgField(fieldArr);
			if (null == fieldList) {
				fieldList = new ArrayList<>();
			}

			// 排序含报文解析注解的属性组
			sortField(fieldList);

			for (Field fieldItem : fieldList) {

				if (doJudgeCommonType(fieldItem)) {
					// 如果是基础类型，根据配置的解析索引，放入解析属性列表(因为属性已经进行过排序，所以这边不需要管配置的索引，直接放入结合就好)
					fieldLinkedList.add(fieldItem);
				} else {
					repeatGetObjAttr(fieldItem.getClass(), fieldLinkedList);
				}
			}

		}
	}

	/**
	 * 排序指定属性组，使用报文解析注解配置的解析索引进行排序
	 * 
	 * @param fieldList
	 */
	private void sortField(List<Field> fieldList) {

		Collections.sort(fieldList, new Comparator<Field>() {
			@Override
			public int compare(Field o1, Field o2) {

				MsgAttrAnno msgAttrAnno1 = o1.getAnnotation(MsgAttrAnno.class);

				MsgClassAnno msgClassAnno1 = o1.getAnnotation(MsgClassAnno.class);

				MsgAttrAnno msgAttrAnno2 = o2.getAnnotation(MsgAttrAnno.class);

				MsgClassAnno msgClassAnno2 = o2.getAnnotation(MsgClassAnno.class);

				if (null == msgAttrAnno1 && null == msgClassAnno1) {
					throw new RuntimeException("the attribute [" + o1.getName() + "] has nono message annotation.");
				}

				if (null == msgAttrAnno2 && null == msgClassAnno2) {
					throw new RuntimeException("the attribute [" + o2.getName() + "] has nono message annotation.");
				}

				if (null != msgAttrAnno1 && null != msgClassAnno1) {
					throw new RuntimeException("the attribute [" + o1.getName() + "] has more message annotation.");
				}

				if (null != msgAttrAnno2 && null != msgClassAnno2) {
					throw new RuntimeException("the attribute [" + o2.getName() + "] has more message annotation.");
				}

				int firstIndex = 0;
				int secondIndex = 0;

				if (null != msgAttrAnno1 && null != msgAttrAnno2) {
					// 两者都是基本属性
					firstIndex = msgAttrAnno1.index();
					secondIndex = msgAttrAnno2.index();
				}

				if (null != msgAttrAnno1 && null != msgClassAnno2) {
					// 前者为基本属性，后者为类
					firstIndex = msgAttrAnno1.index();
					secondIndex = msgClassAnno2.index();
				}

				if (null != msgClassAnno1 && null != msgAttrAnno2) {
					// 前者为类，后者为基本属性
					firstIndex = msgClassAnno1.index();
					secondIndex = msgAttrAnno2.index();
				}

				if (null != msgClassAnno1 && null != msgClassAnno2) {
					// 两者都是类
					firstIndex = msgClassAnno1.index();
					secondIndex = msgClassAnno2.index();
				}

				if (0 == firstIndex) {
					throw new RuntimeException("cannot get attribute [" + o1.getName() + "] index.");
				}

				if (0 == secondIndex) {
					throw new RuntimeException("cannot get attribute [" + o2.getName() + "] index.");
				}

				if (firstIndex == secondIndex) {
					throw new RuntimeException(
							"cannot sort attribute [" + o1.getName() + "] and [" + o2.getName() + "] by index.");
				}

				return firstIndex - secondIndex;
			}
		});
	}

	/**
	 * 针对属性组剔除不含报文解析注解的属性
	 * 
	 * @param fieldArr
	 * @return
	 */
	private List<Field> eliminateNonMsgField(Field[] fieldArr) {
		if (null == fieldArr) {
			return null;
		}

		List<Field> fieldList = new ArrayList<>();

		for (Field fieldItem : fieldArr) {
			if (doJudgeHasMsgAnno(fieldItem)) {
				// 如果该属性拥有报文解析注解, 则添加进集合
				fieldList.add(fieldItem);
			}
		}

		return fieldList;
	}

	/**
	 * 判断该属性是否拥有报文解析注解
	 * 
	 * @param field
	 * @return
	 */
	private boolean doJudgeHasMsgAnno(Field field) {
		MsgAnnoConstant msgAnnoArr[] = MsgAnnoConstant.values();
		for (MsgAnnoConstant msgAnnoItem : msgAnnoArr) {
			if (field.isAnnotationPresent(msgAnnoItem.getClasz())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断一个属性是否是java基础类型
	 * 
	 * @param field
	 * @return
	 */
	private boolean doJudgeCommonType(Field field) {
		// TODO 判断一个属性是否是java基础类型
		return true;
	}

}
