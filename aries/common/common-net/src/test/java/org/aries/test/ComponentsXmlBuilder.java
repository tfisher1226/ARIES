package org.aries.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.aries.util.NameUtil;


public class ComponentsXmlBuilder {

	private List<String> entityManagers;
	
	
	public ComponentsXmlBuilder() {
		entityManagers = new ArrayList<String>();
	}

	public void addEntityManager(String entityManagerName) {
		entityManagers.add(entityManagerName);
	}
	
	public String createApplicationXml() {
		StringBuilder buf = new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
		buf.append("\n");
		buf.append("<components\n"); 
		buf.append("	xmlns=\"http://jboss.org/schema/seam/components\"\n");
		buf.append("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
		buf.append("	xmlns:core=\"http://jboss.org/schema/seam/core\"\n");
		buf.append("	xmlns:mail=\"http://jboss.org/schema/seam/mail\"\n"); 
		buf.append("	xmlns:pdf=\"http://jboss.org/schema/seam/pdf\"\n");
		buf.append("	xmlns:excel=\"http://jboss.org/schema/seam/excel\"\n");
		buf.append("	xmlns:async=\"http://jboss.org/schema/seam/async\"\n");
		buf.append("	xmlns:security=\"http://jboss.org/schema/seam/security\"\n");
		buf.append("	xmlns:persistence=\"http://jboss.org/schema/seam/persistence\"\n");
		buf.append("	xmlns:transaction=\"http://jboss.org/schema/seam/transaction\"\n");
		buf.append("	xmlns:framework=\"http://jboss.org/schema/seam/framework\"\n");
		buf.append("	xmlns:document=\"http://jboss.org/schema/seam/document\"\n");
		buf.append("	xmlns:web=\"http://jboss.org/schema/seam/web\"\n");
		buf.append("	xsi:schemaLocation=\n");
		buf.append("		\"http://jboss.org/schema/seam/core http://jboss.org/schema/seam/core-2.3.xsd\n");
		buf.append("		http://jboss.org/schema/seam/mail http://jboss.org/schema/seam/mail-2.3.xsd\n");
		buf.append("		http://jboss.org/schema/seam/pdf http://jboss.org/schema/seam/pdf-2.3.xsd\n");
		buf.append("		http://jboss.org/schema/seam/excel http://jboss.org/schema/seam/excel-2.3.xsd\n");
		buf.append("		http://jboss.org/schema/seam/async http://jboss.org/schema/seam/async-2.3.xsd\n");
		buf.append("		http://jboss.org/schema/seam/security http://jboss.org/schema/seam/security-2.3.xsd\n");
		buf.append("		http://jboss.org/schema/seam/persistence http://jboss.org/schema/seam/persistence-2.3.xsd\n"); 
		buf.append("		http://jboss.org/schema/seam/transaction http://jboss.org/schema/seam/transaction-2.3.xsd\n"); 
		buf.append("		http://jboss.org/schema/seam/components http://jboss.org/schema/seam/components-2.3.xsd\n");
		buf.append("		http://jboss.org/schema/seam/framework http://jboss.org/schema/seam/framework-2.3.xsd\n");
		buf.append("		http://jboss.org/schema/seam/document http://jboss.org/schema/seam/document-2.3.xsd\n");
		buf.append("		http://jboss.org/schema/seam/web http://jboss.org/schema/seam/web-2.3.xsd\">\n");
		buf.append("	\n");
		buf.append("	<core:manager\n"); 
		buf.append("		default-flush-mode=\"MANUAL\"\n"); 
		buf.append("		concurrent-request-timeout=\"5000\"\n");
		buf.append("		conversation-timeout=\"120000\"\n");
		buf.append("		conversation-id-parameter=\"conversationId\"\n");
		buf.append("		parent-conversation-id-parameter=\"parentConversationId\" />\n");
		buf.append("	\n");
		buf.append("	<core:init\n"); 
		buf.append("		debug=\"false\"\n"); 
		buf.append("		jndi-pattern=\"@jndiPattern@\"\n");
		buf.append("		distributable=\"@distributable@\"\n");
		buf.append("		transaction-management-enabled=\"false\"/>\n");
		buf.append("	\n");
		buf.append("    <transaction:ejb-transaction/>\n");
		buf.append("	\n");
		buf.append("	<security:identity\n"); 
		buf.append("		remember-me=\"true\"\n");
		buf.append("		authenticate-method=\"#{securityManager.authenticate}\" />\n");

		Iterator<String> iterator = entityManagers.iterator();
		while (iterator.hasNext()) {
			String entityManagerFullName = iterator.next();
			String entityManagerShortName = NameUtil.getBaseName(entityManagerFullName);
			
			buf.append("	\n");
			buf.append("	<!-- Entity manager for: "+entityManagerShortName+" -->\n"); 
			buf.append("	<persistence:managed-persistence-context\n"); 
			buf.append("		name=\""+entityManagerFullName+".entityManager\"\n"); 
			buf.append("		persistence-unit-jndi-name=\"java:/"+entityManagerShortName+"EntityManagerFactory\"\n"); 
			buf.append("		auto-create=\"true\" />\n");
		}

		buf.append("	\n");
		buf.append("	<!-- Seam -->\n");
		buf.append("	<component\n"); 
		buf.append("		class=\"org.jboss.seam.transaction.EjbSynchronizations\"\n");
		buf.append("		jndi-name=\"java:global/bookshop2-app/jboss-seam-2.3.1.Final/EjbSynchronizations\"/>\n");
		buf.append("	\n");
		buf.append("</components>\n");
		buf.append("\n");
		return buf.toString();
	}

}


