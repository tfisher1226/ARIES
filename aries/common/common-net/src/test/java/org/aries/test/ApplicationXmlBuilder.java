package org.aries.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.aries.Assert;


public class ApplicationXmlBuilder {

	private String displayName;
	
	private List<String> javaModules;
	
	private Map<String, String> webModules;

	private List<String> ejbModules;

	
	public ApplicationXmlBuilder() {
		javaModules = new ArrayList<String>();
		webModules = new HashMap<String, String>();
		ejbModules = new ArrayList<String>();
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public void addJavaModule(String fileName) {
		javaModules.add(fileName);
	}
	
	public void addWebModule(String fileName, String contextRoot) {
		webModules.put(fileName, contextRoot);
	}
	
	public void addEjbModule(String fileName) {
		ejbModules.add(fileName);
	}
	
	public String createApplicationXml() {
		Assert.notEmpty(displayName, "DisplayName must be specified");
		
		StringBuilder buf = new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
		buf.append("\n");
		buf.append("<application\n");
		buf.append("	xmlns=\"http://java.sun.com/xml/ns/javaee\"\n");
		buf.append("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"6\">\n");
		buf.append("\n");
		buf.append("	<display-name>" + displayName + "</display-name>\n");
		buf.append("\n");
		
		Iterator<String> iterator = webModules.keySet().iterator();
		while (iterator.hasNext()) {
			String warFileName = iterator.next();
			String contextRoot = webModules.get(warFileName);
			buf.append("	<module>\n");
			buf.append("		<web>\n");
			buf.append("			<context-root>" + contextRoot + "</context-root>\n");
			buf.append("			<web-uri>" + warFileName + "</web-uri>\n");
			buf.append("		</web>\n");
			buf.append("	</module>\n");
		}
		
		iterator = ejbModules.iterator();
		while (iterator.hasNext()) {
			String fileName = iterator.next();
			buf.append("	<module>\n");
			buf.append("		<ejb>" + fileName + "</ejb>\n");
			buf.append("	</module>\n");
		}
		
		iterator = javaModules.iterator();
		while (iterator.hasNext()) {
			String fileName = iterator.next();
			buf.append("	<module>\n");
			buf.append("		<java>" + fileName + "</java>\n");
			buf.append("	</module>\n");
		}

		buf.append("</application>\n");
		buf.append("\n");
		return buf.toString();
	}
	
}


