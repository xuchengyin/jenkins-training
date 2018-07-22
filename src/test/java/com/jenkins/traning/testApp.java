package com.jenkins.traning;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class testApp{
	App app;
	@Before
	public void before() {
		System.out.println("before");
	 app=new App();	
	}
	@Test
	public void testAdd() {
		int c=app.sum(3, 5);
		 Assert.assertEquals(c, 8);
	}
	@Test
   public void testSub() {
		int c=app.sub(6, 2);
		Assert.assertEquals(c, 4);
	}
}

