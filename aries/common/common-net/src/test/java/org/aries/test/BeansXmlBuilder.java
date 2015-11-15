package org.aries.test;



public class BeansXmlBuilder {

	public BeansXmlBuilder() {
	}

	public String createBeansXml() {
		StringBuilder buf = new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		buf.append("\n");
		buf.append("<beans\n"); 
		buf.append("	xmlns=\"http://java.sun.com/xml/ns/javaee\"\n"); 
		buf.append("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
		buf.append("	xsi:schemaLocation=\"http://jboss.org/schema/weld/beans http://jboss.org/schema/weld/beans_1_1.xsd\"\n");
		buf.append("	bean-discovery-mode=\"annotated\">\n");
		buf.append("	\n");
		buf.append("</beans>\n");
		buf.append("\n");
		return buf.toString();
	}
	
}


