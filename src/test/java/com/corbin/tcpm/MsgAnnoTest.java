/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月27日
 * Description:MsgAnnoTest.java 
 */
package com.corbin.tcpm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chong
 */
public class MsgAnnoTest {

	public static void main(String[] args) {
		TestMessage testMsg = new TestMessage();
		testMsg.setId(12);
		testMsg.setName("test");
		testMsg.setCreateTime(new Date());
		
		TestMessageInner testMsgInner = new TestMessageInner();
		testMsgInner.setId(13);
		testMsgInner.setName("test33");
		testMsgInner.setCreateTime(new Date());

		testMsg.setTestMsg(testMsgInner);
		
		List<TestMessageInner> testMsgList = new ArrayList<>();
		
		TestMessageInner testMsgInner1 = new TestMessageInner();
		testMsgInner1.setId(15);
		testMsgInner1.setName("test44");
		testMsgInner1.setCreateTime(new Date());
		
		testMsgList.add(testMsgInner1);
		testMsg.setTestMsgList(testMsgList);
		
		testMsg.setCount(testMsgList.size());
		
		
		byte[] bytes = testMsg.getBytes();

		System.out.println(bytes);

		TestMessage testMsg1 = new TestMessage();
		
		testMsg1.getObj(bytes);
		
		System.out.println(testMsg1);
	}

}
