package org.aries.client.jmx;


public class Test implements TestMBean {

	public String test(String request) {
		return "Hello "+request;
	}

}
