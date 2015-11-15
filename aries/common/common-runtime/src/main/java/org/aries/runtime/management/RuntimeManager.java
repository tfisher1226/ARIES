package org.aries.runtime.management;


public class RuntimeManager {

	public static final RuntimeManager INSTANCE = new RuntimeManager();
	
	
	public String getDefaultURL() {
		return "http://localhost:9080";
	}

}
