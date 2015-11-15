package org.aries.test;

import org.aries.Assert;


public class WebXmlBuilder {

	private String displayName;
	
	
	public WebXmlBuilder() {
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String createWebXml() {
		Assert.notEmpty(displayName, "DisplayName must be specified");
		
		StringBuilder buf = new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
		buf.append("\n");

		buf.append("<web-app");
		buf.append("	version=\"3.0\"");
		buf.append("	id=\"BOOKSHOP2\"");
		buf.append("	xmlns=\"http://java.sun.com/xml/ns/javaee\""); 
		buf.append("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		buf.append("	xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd\">");
		buf.append("\n");
		buf.append("	<display-name>" + displayName + "</display-name>\n");
		buf.append("\n");
		buf.append("</web-app>\n");
		buf.append("\n");
		return buf.toString();
	}
	
}
