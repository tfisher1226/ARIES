package org.aries;

import javax.servlet.ServletContext;


public class RuntimeContext {

	private static final RuntimeContext instance = new RuntimeContext();
	
	public static RuntimeContext getInstance() {
		return instance;
	}
	
	
	private ServletContext servletContext;
	

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
