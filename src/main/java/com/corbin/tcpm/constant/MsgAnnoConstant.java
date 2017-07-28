/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月28日
 * Description:MsgAnnoConstant.java 
 */
package com.corbin.tcpm.constant;

import java.lang.annotation.Annotation;

import com.corbin.tcpm.annotation.MsgAttrAnno;
import com.corbin.tcpm.annotation.MsgClassAnno;

/**
 * 报文解析注解配置
 * 
 * @author chong
 */
public enum MsgAnnoConstant {

	ANNO_ATTR(MsgAttrAnno.class, "属性解析注解"), 
	ANNO_CLASS(MsgClassAnno.class, "类解析注解");

	private Class<? extends Annotation> clasz;
	private String name;

	private MsgAnnoConstant(Class<? extends Annotation> clasz, String name) {
		this.clasz = clasz;
		this.name = name;
	}

	public Class<? extends Annotation> getClasz() {
		return clasz;
	}

	public void setClasz(Class<? extends Annotation> clasz) {
		this.clasz = clasz;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
