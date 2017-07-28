/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月27日
 * Description:TestMessage.java 
 */
package com.corbin.tcpm;

import java.util.Date;

import com.corbin.tcpm.annotation.MsgAttrAnno;
import com.corbin.tcpm.message.AbstractMessage;

/**
 * @author chong
 */
public class TestMessage extends AbstractMessage {

	@MsgAttrAnno(byteLength = 2, index = 1)
	private Integer id;

	@MsgAttrAnno(byteLength = 11, index = 2)
	private String name;

	@MsgAttrAnno(byteLength = 19, index = 3)
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
