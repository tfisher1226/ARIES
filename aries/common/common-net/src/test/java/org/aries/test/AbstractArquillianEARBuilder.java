package org.aries.test;

import org.jboss.shrinkwrap.api.asset.StringAsset;


public abstract class AbstractArquillianEARBuilder {

	private boolean deployDs = true;

	private boolean deployJms = true;
	
	private boolean runningAsClient = false;
	

	public boolean isDeployDs() {
		return deployDs;
	}

	public void setDeployDs(boolean deployDs) {
		this.deployDs = deployDs;
	}

	public boolean isDeployJms() {
		return deployJms;
	}

	public void setDeployJms(boolean deployJms) {
		this.deployJms = deployJms;
	}

	public boolean isRunningAsClient() {
		return runningAsClient;
	}

	public void setRunningAsClient(boolean runningAsClient) {
		this.runningAsClient = runningAsClient;
	}

	//Make empty beans.xml by default
	public StringAsset getBeansXml() {
		BeansXmlBuilder builder = new BeansXmlBuilder();
		String xml = builder.createBeansXml();
		StringAsset asset = new StringAsset(xml);
		return asset;
	}

	//Make empty application.xml by default
	public StringAsset getApplicationXml() {
		ApplicationXmlBuilder builder = new ApplicationXmlBuilder();
		builder.setDisplayName("Default Application XML");
		String xml = builder.createApplicationXml();
		StringAsset asset = new StringAsset(xml);
		return asset;
	}

	//Make base jboss-deployment-structure.xml by default
	public StringAsset getDeploymentXml() {
		StringBuilder buf = new StringBuilder();
		buf.append("<jboss-deployment-structure xmlns=\"urn:jboss:deployment-structure:1.1\">\n");
		buf.append("\n");
		buf.append("	<ear-subdeployments-isolated>false</ear-subdeployments-isolated>\n");
		buf.append("\n");
//		buf.append("	<resources>\n");
//		buf.append("		<resource-root path=\"lib/bookshop2-model.jar\" />\n");
//		buf.append("	</resources>\n");
//		buf.append("\n");
		buf.append("	<deployment>\n");
		buf.append("		<dependencies>\n");
		buf.append("			<module name=\"aries.antlr\" export=\"true\" />\n");
		buf.append("			<module name=\"aries.apache.ant\" export=\"true\" />\n");
		buf.append("			<module name=\"aries.apache.commons\" export=\"true\" />\n");
		buf.append("			<module name=\"aries.drools\" export=\"true\" />\n");
		buf.append("			<module name=\"aries.eclipse.core\" export=\"true\" />\n");
		buf.append("			<module name=\"aries.eclipse.emf\" export=\"true\" />\n");
		buf.append("			<module name=\"aries.eclipse.jdt\" export=\"true\" />\n");
		buf.append("			<module name=\"aries.ehcache\" export=\"true\" />\n");
		buf.append("			<module name=\"aries.ibm\" export=\"true\" />\n");
//		buf.append("			<module name=\"aries.jboss.ts\" export=\"true\" />\n");
		buf.append("			<module name=\"aries.jgroups\" export=\"true\" />\n");
		buf.append("			<module name=\"aries.util\" export=\"true\" />\n");
		buf.append("\n");
//		buf.append("			<module name=\"bookshop2.model\" export=\"true\" />\n");
//		buf.append("\n");
		buf.append("			<module name=\"org.antlr\" export=\"true\" />\n");
		buf.append("			<module name=\"org.apache.log4j\" export=\"true\" />\n");
		buf.append("			<module name=\"org.apache.commons.logging\" export=\"true\" />\n");
		buf.append("			<module name=\"org.apache.commons.collections\" export=\"true\" />\n");
		buf.append("			<module name=\"org.apache.xerces\" export=\"true\" />\n");
		buf.append("			<module name=\"org.dom4j\" export=\"true\" />\n");
		buf.append("			<module name=\"org.hibernate\" export=\"true\" />\n");
		buf.append("			<module name=\"org.hibernate.validator\" export=\"true\" />\n");
		buf.append("			<module name=\"org.hornetq\" export=\"true\" />\n");
		buf.append("			<module name=\"org.slf4j\" export=\"true\" />\n");
		buf.append("			<module name=\"org.javassist\" export=\"true\" />\n");
		buf.append("			<module name=\"org.jboss.as.jmx\" export=\"true\" />\n");
		buf.append("			<module name=\"org.jboss.jts\" export=\"true\" />\n");
		buf.append("			<module name=\"org.jboss.logging\" export=\"true\" />\n");
		buf.append("			<module name=\"org.jboss.remoting3.remoting-jmx\" services=\"import\" export=\"true\" />\n");
//		buf.append("				<imports>\n");
//		buf.append("					 <include-set>\n");
//		buf.append("					 	<path name=\"bookshop\" />\n");
//		buf.append("					 </include-set>\n");
//		buf.append("				</imports>\n");
//		buf.append("			</module>\n");
		buf.append("			<module name=\"javax.api\" export=\"true\" />\n");
		buf.append("			<module name=\"javax.el.api\" export=\"true\" />\n");
		buf.append("			<module name=\"javax.faces.api\" export=\"true\" />\n");
		buf.append("			<module name=\"javax.servlet.jstl.api\" export=\"true\" />\n");
		buf.append("			<module name=\"javax.transaction.api\" export=\"true\" />\n");
		buf.append("			<module name=\"javax.xml.stream.api\" export=\"true\" />\n");
		buf.append("			<module name=\"com.sun.jsf-impl\" export=\"true\" />\n");
		buf.append("			<module name=\"com.sun.xml.bind\" export=\"true\" />\n");
		buf.append("		</dependencies>\n");
		buf.append("	</deployment>\n");
		buf.append("</jboss-deployment-structure>\n");
		StringAsset asset = new StringAsset(buf.toString());
		return asset;
	}
	

	public EnterpriseArchiveBuilder createEnterpriseArchiveBuilder(String fileName) {
		EnterpriseArchiveBuilder builder = new EnterpriseArchiveBuilder(fileName);
		
		//add local libraries
		builder.addJavaLibraryToEar("org.aries", "common-util", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "common-action", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "common-cache", "0.0.1-SNAPSHOT");
		//builder.addJavaLibraryToEar("org.aries", "common-data", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "common-ejb", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "common-jms", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "common-jmx", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "common-jndi", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "common-jaxb", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "common-jaxws", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "common-rmi", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "common-net", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "common-model", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "common-event", "0.0.1-SNAPSHOT");
		//builder.addJavaLibraryToEar("org.aries", "common-client", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "common-service", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "common-process", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "common-runtime", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "common-transaction", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "common-workflow", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "nam-model", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "nam-engine", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "ariel-compiler", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "aries-launcher", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "aries-registry", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "aries-validate", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "tx-manager-model", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "tx-manager-base", "0.0.1-SNAPSHOT");
		//builder.addJavaLibraryToEar("org.aries", "tx-manager-client", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToEar("org.aries", "tx-manager-registry", "0.0.1-SNAPSHOT");
		//builder.addJavaLibraryToEar("org.aries", "tx-manager-service", "0.0.1-SNAPSHOT");

		//add local test-scope libraries
		//addTestLibraryToEar("org.aries", "common-jms", "0.0.1-SNAPSHOT");
		//addTestLibraryToEar("org.aries", "common-client", "0.0.1-SNAPSHOT");

		//add external libraries
		builder.addJavaLibraryToEar("org.apache.ant", "ant", "1.8.1");
		builder.addJavaLibraryToEar("commons-io", "commons-io", "1.3.2");
		builder.addJavaLibraryToEar("commons-lang", "commons-lang", "2.5");
		builder.addJavaLibraryToEar("commons-digester", "commons-digester", "2.0");
		builder.addJavaLibraryToEar("commons-validator", "commons-validator", "1.3.1");
		builder.addJavaLibraryToEar("joda-time", "joda-time", "1.5.1");
		builder.addJavaLibraryToEar("mysql", "mysql-connector-java", "5.1.34");
		
		if (isRunningAsClient()) {
			builder.addJavaLibraryToEar("net.sf.ehcache", "ehcache-core", "2.5.2");
			builder.addJavaLibraryToEar("net.sf.ehcache", "ehcache-jgroupsreplication", "1.7");
			builder.addJavaLibraryToEar("org.hibernate", "hibernate-ehcache", "4.0.1.Final");
		}
	
		builder.addJavaLibraryToEar("org.hornetq", "hornetq-core", "2.2.14.Final");
		builder.addJavaLibraryToEar("org.javassist", "javassist", "3.15.0-GA");
		//builder.addJavaLibraryToEar("org.jboss.as", "jboss-as-jmx", "7.1.1.Final");
		builder.addJavaLibraryToEar("org.jboss.el", "jboss-el", "1.0_02.CR6");
		//builder.addJavaLibraryToEar("org.jboss.jbossts", "jbossjta", "4.16.4.Final");
		//builder.addJavaLibraryToEar("org.jboss.jbossts", "jbossjts", "4.16.4.Final");
		builder.addJavaLibraryToEar("org.jgroups", "jgroups", "3.1.0.Final");
		builder.addJavaLibraryToEar("com.thoughtworks.xstream", "xstream", "1.2.2");
		builder.addJavaLibraryToEar("xml-apis", "xml-apis", "1.4.01");

		//builder.addJavaLibraryToEar("org.jboss.byteman", "byteman", "2.1.4.1");
		builder.addJavaLibraryToEar("org.jboss.byteman", "byteman-install", "2.1.4.1");
		builder.addJavaLibraryToEar("org.jboss.byteman", "byteman-submit", "2.1.4.1");
		builder.addJavaLibraryToEar("org.jboss.byteman", "byteman-bmunit", "2.1.4.1");

		//add external modules
		builder.addJavaModuleToEar("org.jboss.seam", "jboss-seam", "2.3.1.Final");
		return builder;
	}

}
