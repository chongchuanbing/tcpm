/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月28日
 * Description:FieldUtil.java 
 */
package com.corbin.tcpm.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.corbin.tcpm.annotation.MsgAttrAnno;
import com.corbin.tcpm.annotation.MsgClassAnno;
import com.corbin.tcpm.annotation.MsgCountDepAnno;
import com.corbin.tcpm.constant.CommonConstant;
import com.corbin.tcpm.constant.MsgAnnoConstant;
import com.corbin.tcpm.message.AbstractMessage;

/**
 * 属性工具类
 * 
 * @author chong
 */
public class FieldUtil {

	/**
	 * 解析报文解析属性
	 * 
	 * @param obj
	 * @param fieldLinkedList
	 * @param byteBuffer
	 */
	public static void repeatGetObjAttr(Object obj, ByteBuffer byteBuffer, boolean bSerialize) {

		// 迭代获取进行报文解析的属性

		Field[] fieldArr = obj.getClass().getDeclaredFields();
		if (null != fieldArr) {

			// 剔除不含报文解析注解的属性
			List<Field> fieldList = eliminateNonMsgField(fieldArr);
			if (null == fieldList) {
				fieldList = new ArrayList<>();
			}

			// 排序含报文解析注解的属性组
			sortField(fieldList);

			for (Field fieldItem : fieldList) {
				if (bSerialize) {
					serializeSingleObj(obj, fieldItem, byteBuffer);
				} else {
					deserializeSingleObj(obj, fieldItem, byteBuffer);
				}
			}
		}
	}

	/**
	 * 反序列化对象
	 * 
	 * @param obj
	 * @param fieldItem
	 * @param fieldLinkedList
	 * @param byteBuffer
	 */
	private static void deserializeSingleObj(Object obj, Field fieldItem, ByteBuffer byteBuffer) {
		if (doJudgeCommonType(fieldItem)) {
			// 反序列化
			ByteUtil.deserializeFieldFormat(obj, fieldItem, byteBuffer);
		} else {
			Object objInner = refectGetObj(obj, fieldItem, true);
			if (null == objInner) {
				throw new RuntimeException("cannot instance the obj [" + fieldItem.getName() + "]");
			}

			if (objInner instanceof List) {
				// 集合对象,需要知道集合的大小，读取依赖的属性
				MsgCountDepAnno msgCountDepAnno = fieldItem.getAnnotation(MsgCountDepAnno.class);
				if (null == msgCountDepAnno) {
					throw new RuntimeException("cannot get the annotation [" + MsgCountDepAnno.class.getName() + "]");
				}

				String attrName = msgCountDepAnno.attrName();
				if (null == attrName || "".equals(attrName)) {
					throw new RuntimeException(
							"the annotation [" + MsgCountDepAnno.class.getName() + " cannot point related attribute.]");
				}

				Object objRelated = refectGetObj(obj, attrName, true);
				if (objRelated instanceof Integer) {
					Integer count = (Integer) objRelated;
					for (int i = 0; i < count; i++) {
						// 普通对象
						Type genericType = fieldItem.getGenericType();

						if (genericType == null)
							continue;
						// 如果是泛型参数的类型
						if (genericType instanceof ParameterizedType) {
							ParameterizedType pt = (ParameterizedType) genericType;
							// 得到泛型里的class类型对象
							Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];

							Object typeObj = instanceObj(genericClazz);

							repeatGetObjAttr(typeObj, byteBuffer, false);

							((List) objInner).add(typeObj);
						}
					}
				}

			} else {
				// 普通对象
				repeatGetObjAttr(objInner, byteBuffer, false);
			}
		}
	}

	/**
	 * 系列化对象
	 * 
	 * @param obj
	 * @param fieldItem
	 * @param fieldLinkedList
	 * @param byteBuffer
	 */
	private static void serializeSingleObj(Object obj, Field fieldItem, ByteBuffer byteBuffer) {
		if (doJudgeCommonType(fieldItem)) {

			Object objInner = refectGetObj(obj, fieldItem, false);

			// 这边的field肯定都是基础类型，直接按照注解配置规则解析为byte即可
			byte[] bytes = ByteUtil.serializeFieldFormat(objInner, fieldItem);
			if (null == bytes) {
				throw new RuntimeException(
						"the format serialize return null. the fieldName [" + fieldItem.getName() + "].");
			}
			if (byteBuffer.capacity() - byteBuffer.position() < bytes.length) {
				// 缓冲区容量不足
				ByteBuffer byteBufNew = ByteBuffer.allocateDirect(byteBuffer.capacity() + CommonConstant.CAPACITY_SIZE);
				byteBufNew.put(byteBuffer);

				byteBuffer = byteBufNew.duplicate();
			}

			byteBuffer.put(bytes);
		} else {

			Object objInner = refectGetObj(obj, fieldItem, true);
			if (null == objInner) {
				throw new RuntimeException("cannot instance the obj [" + fieldItem.getName() + "]");
			}

			if (objInner instanceof List) {

				List<?> objList = (List<?>) objInner;
				for (Object objItem : objList) {
					repeatGetObjAttr(objItem, byteBuffer, true);
				}
			} else {
				repeatGetObjAttr(objInner, byteBuffer, true);
			}
		}
	}

	/**
	 * 根据属性field反射获取指定对象该属性的对象
	 * 
	 * @param obj
	 * @param fieldName
	 * @param bNeedInstace
	 * @return
	 */
	private static Object refectGetObj(Object obj, String fieldName, boolean bNeedInstace) {

		Field field = null;
		try {
			field = obj.getClass().getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}

		if (null == field) {
			return null;
		}

		return refectGetObj(obj, field, bNeedInstace);
	}

	/**
	 * 根据属性field反射获取指定对象该属性的对象
	 * 
	 * @param obj
	 * @param field
	 * @return
	 */
	private static Object refectGetObj(Object obj, Field field, boolean bNeedInstace) {
		String fieldName = field.getName();
		String methodName = "get" + StringUtil.toUpperCaseFirstOne(fieldName);

		Method method = null;

		try {
			method = obj.getClass().getDeclaredMethod(methodName, new Class[] {});
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}

		Object objInner = null;
		try {
			objInner = method.invoke(obj);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}

		if (null == objInner && bNeedInstace) {
			objInner = instanceObj(field.getType());
			refectSetObj(obj, field, objInner);
		}

		return objInner;
	}

	/**
	 * class实例化对象
	 * 
	 * @param clasz
	 * @return
	 */
	private static Object instanceObj(Class<?> clasz) {
		if (clasz.getTypeName().equals("java.util.List")) {
			return new ArrayList<AbstractMessage>();
		} else {
			try {
				return clasz.newInstance();
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 对象放入值
	 * 
	 * @param obj
	 * @param field
	 * @return
	 */
	public static void refectSetObj(Object obj, Field field, Object objAttr) {
		String fieldName = field.getName();
		String methodName = "set" + StringUtil.toUpperCaseFirstOne(fieldName);

		Method[] methodArr = obj.getClass().getDeclaredMethods();
		for (Method methodItem : methodArr) {
			if (methodItem.getName().equals(methodName)) {
				try {
					methodItem.invoke(obj, objAttr);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				} catch (IllegalArgumentException e) {
					throw new RuntimeException(e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e);
				}
				return;
			}
		}
	}

	/**
	 * 排序指定属性组，使用报文解析注解配置的解析索引进行排序
	 * 
	 * @param fieldList
	 */
	public static void sortField(List<Field> fieldList) {

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
	public static List<Field> eliminateNonMsgField(Field[] fieldArr) {
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
	public static boolean doJudgeHasMsgAnno(Field field) {
		MsgAnnoConstant msgAnnoArr[] = MsgAnnoConstant.values();
		for (MsgAnnoConstant msgAnnoItem : msgAnnoArr) {
			if (field.isAnnotationPresent(msgAnnoItem.getClasz())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断一个属性是否是java内置类型
	 * 
	 * @param field
	 * @return
	 */
	public static boolean doJudgeCommonType(Field field) {
		// 判断一个属性是否是java内置类型
		Class<?> clasz = field.getType();

		return CommonConstant.commonTypeSet.contains(clasz.getName());
	}
}
