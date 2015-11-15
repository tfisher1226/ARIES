package org.aries.service;


public class ServiceTestContext {

	public static String getServiceIdForTest() {
		return getServiceGroupForTest()+"."+getServiceNameForTest();
	}

	public static String getServiceGroupForTest() {
		return "org.aries";
	}

	public static String getServiceNameForTest() {
		return "simpleService";
	}

	public static String getServiceClassForTest() {
		return "org.aries.SimpleService";
	}

	public static String getActionNameForTest() {
		return "getPersonById";
	}

	public static String getActionClassForTest() {
		return "org.aries.GetPersonAction";
	}


}
