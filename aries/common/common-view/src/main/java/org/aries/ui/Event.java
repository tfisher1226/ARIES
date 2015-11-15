package org.aries.ui;


public enum Event {

	RESET,

	REFRESH,

	LOGIN_SUCCESS,
	
	LOGIN_FAILED;


	public static Event fromValue(String name) {
		return valueOf(name);
	}

	public String value() {
		return name();
	}

}
