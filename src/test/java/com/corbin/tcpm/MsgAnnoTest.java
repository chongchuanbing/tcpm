/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年7月27日
 * Description:MsgAnnoTest.java 
 */
package com.corbin.tcpm;

import java.util.Date;

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
		
		byte[] bytes = testMsg.getBytes();

		System.out.println(bytes);

		TestMessage testMsg1 = new TestMessage();
		
		testMsg1.getObj(bytes);
		
		System.out.println(testMsg1);
	}

}
