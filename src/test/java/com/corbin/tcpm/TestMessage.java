/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月27日
 * Description:TestMessage.java 
 */
package com.corbin.tcpm;

import java.util.Date;
import java.util.List;

import com.corbin.tcpm.annotation.MsgAttrAnno;
import com.corbin.tcpm.annotation.MsgCountDepAnno;
import com.corbin.tcpm.format.DateFormat;
import com.corbin.tcpm.format.IntegerFormat;
import com.corbin.tcpm.format.StringFormat;
import com.corbin.tcpm.message.AbstractMessage;

/**
 * 测试报文
 * 
 * @author chong
 */
public class TestMessage extends AbstractMessage {

	@MsgAttrAnno(index = 1, format = IntegerFormat.class)
	private Integer id;

	@MsgAttrAnno(index = 2, format = StringFormat.class, formatParam = "11")
	private String name;

	@MsgAttrAnno(index = 3, format = DateFormat.class, formatParam = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	@MsgAttrAnno(index = 4)
	private TestMessageInner testMsg;
	
	@MsgAttrAnno(index = 5, format = IntegerFormat.class, formatParam = "yyyy-MM-dd HH:mm:ss")
	private Integer count;
	
	@MsgAttrAnno(index = 6)
	@MsgCountDepAnno(attrName="count")
	private List<TestMessageInner> testMsgList;

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

	public TestMessageInner getTestMsg() {
		return testMsg;
	}

	public void setTestMsg(TestMessageInner testMsg) {
		this.testMsg = testMsg;
	}

	public List<TestMessageInner> getTestMsgList() {
		return testMsgList;
	}

	public void setTestMsgList(List<TestMessageInner> testMsgList) {
		this.testMsgList = testMsgList;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
