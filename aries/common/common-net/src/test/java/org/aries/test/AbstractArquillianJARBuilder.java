package org.aries.test;



public abstract class AbstractArquillianJARBuilder {

	public JavaArchiveBuilder createJavaArchiveBuilder(String fileName) {
		JavaArchiveBuilder builder = new JavaArchiveBuilder(fileName);
		return builder;
	}
	
	public void addDefaultTestLibraries(JavaArchiveBuilder builder) {
	}
	
	public void addOtherLibraries(JavaArchiveBuilder builder) {
		//add local libraries
		builder.addJavaLibraryToWar("org.aries", "common-util", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "common-action", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "common-cache", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "common-data", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "common-ejb", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "common-jms", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "common-jmx", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "common-jndi", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "common-jaxb", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "common-jaxws", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "common-rmi", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "common-net", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "common-model", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "common-event", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "common-client", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "common-service", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "common-process", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "common-runtime", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "nam-model", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "nam-engine", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "ariel-compiler", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "aries-launcher", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "aries-registry", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "aries-validate", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "tx-manager-model", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "tx-manager-base", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "tx-manager-client", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "tx-manager-registry", "0.0.1-SNAPSHOT");
		//builder.addJavaLibraryToWar("org.aries", "tx-manager-service", "0.0.1-SNAPSHOT");

		//add local test-scope libraries
		builder.addTestLibraryToWar("org.aries", "common-jms", "0.0.1-SNAPSHOT");
		builder.addTestLibraryToWar("org.aries", "common-client", "0.0.1-SNAPSHOT");

		//add external libraries
		builder.addJavaLibraryToWar("org.apache.ant", "ant", "1.8.1");
		builder.addJavaLibraryToWar("commons-io", "commons-io", "1.3.2");
		builder.addJavaLibraryToWar("commons-lang", "commons-lang", "2.5");
		builder.addJavaLibraryToWar("commons-digester", "commons-digester", "2.0");
		builder.addJavaLibraryToWar("commons-validator", "commons-validator", "1.3.1");
		builder.addJavaLibraryToWar("joda-time", "joda-time", "1.5.1");
		//builder.addJavaLibraryToWar("net.sf.ehcache", "ehcache-core", "2.5.0");
		//builder.addJavaLibraryToWar("net.sf.ehcache", "ehcache-jgroupsreplication", "1.5");
		//builder.addJavaLibraryToWar("org.hibernate", "hibernate-ehcache", "4.0.1.Final");
		builder.addJavaLibraryToWar("org.hornetq", "hornetq-core", "2.2.14.Final");
		builder.addJavaLibraryToWar("org.javassist", "javassist", "3.15.0-GA");
		//builder.addJavaLibraryToWar("org.jboss.as", "jboss-as-jmx", "7.1.1.Final");
		builder.addJavaLibraryToWar("org.jboss.el", "jboss-el", "1.0_02.CR6");
		//builder.addJavaLibraryToWar("org.jboss.jbossts", "jbossjta", "4.16.4.Final");
		//builder.addJavaLibraryToWar("org.jboss.jbossts", "jbossjts", "4.16.4.Final");
		builder.addJavaLibraryToWar("org.jgroups", "jgroups", "2.10.0.GA");
		builder.addJavaLibraryToWar("xml-apis", "xml-apis", "1.3.04");

		builder.addJavaLibraryToWar("org.jboss.byteman", "byteman", "2.1.4.1");
		builder.addJavaLibraryToWar("org.jboss.byteman", "byteman-install", "2.1.4.1");
		builder.addJavaLibraryToWar("org.jboss.byteman", "byteman-submit", "2.1.4.1");
		builder.addJavaLibraryToWar("org.jboss.byteman", "byteman-bmunit", "2.1.4.1");
		
		//add external test-scope libraries
		builder.addJavaLibraryToWar("org.jboss.arquillian.protocol", "arquillian-protocol-servlet", "1.1.1.Final");

		//add external modules
		//builder.addJavaModuleToEar("org.jboss.seam", "jboss-seam", "2.3.0.Final");
	}

}
