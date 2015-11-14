package redhat.jee_migration_example;

import org.aries.test.AbstractArquillianEARBuilder;
import org.aries.test.ApplicationXmlBuilder;
import org.aries.test.EnterpriseArchiveBuilder;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;


public class EventLoggerTestEARBuilder extends AbstractArquillianEARBuilder {
	
	public static final String NAME = "jee-migration-example.ear";
	
	private EnterpriseArchiveBuilder earBuilder;
	
	private ApplicationXmlBuilder appXmlBuilder;
	
	private boolean includeWar;
	
	
	public EventLoggerTestEARBuilder() {
		this(NAME);
	}
	
	public EventLoggerTestEARBuilder(String earName) {
		earBuilder = createEnterpriseArchiveBuilder(earName);
		appXmlBuilder = new ApplicationXmlBuilder();
	}
	
	
	public boolean isIncludeWar() {
		return includeWar;
	}
	
	public boolean getIncludeWar() {
		return includeWar;
	}
	
	public void setIncludeWar(boolean includeWar) {
		this.includeWar = includeWar;
	}
	
	public void addAsModule(WebArchive webArchive, String contextRoot) {
		appXmlBuilder.addWebModule(webArchive.getName(), contextRoot);
		earBuilder.addAsModule(webArchive);
	}
	
	public void addAsModule(JavaArchive javaArchive) {
		earBuilder.addAsModule(javaArchive);
	}
	
	public EnterpriseArchive createEAR() {
		earBuilder.addJavaModuleToEar("redhat", "jee-migration-example-model", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("redhat", "jee-migration-example-persistence", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("redhat", "jee-migration-example-client", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("redhat", "jee-migration-example-service", "0.0.1-SNAPSHOT");
		if (includeWar)
			earBuilder.addWarModuleToEar("redhat", "jee-migration-example-ui", "0.0.1-SNAPSHOT");
		earBuilder.addJavaModuleToEar("org.aries", "tx-manager-base", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("org.aries", "tx-manager-client", "0.0.1-SNAPSHOT");
//		earBuilder.addJavaModuleToEar("admin", "admin-model", "0.0.1-SNAPSHOT");
//		earBuilder.addEjbModuleToEar("admin", "admin-client", "0.0.1-SNAPSHOT");
//		earBuilder.addEjbModuleToEar("admin", "admin-service", "0.0.1-SNAPSHOT");
//		earBuilder.addEjbModuleToEar("admin", "admin-data", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("org.aries", "common-client", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("org.aries", "common-data", "0.0.1-SNAPSHOT");
		
		earBuilder.buildApplicationXml(getApplicationXml());
		earBuilder.buildDeploymentXml(getDeploymentXml());
		if (isDeployDs()) {
			earBuilder.addAsResource("redhat-jee-migration-example-ds.xml");
		}
		if (isDeployJms()) {
			earBuilder.addAsResource("redhat-jee-migration-example-jms.xml");
		}
		
		EnterpriseArchive archive = earBuilder.getArchive();
		return archive;
	}
	
	public StringAsset getApplicationXml() {
		appXmlBuilder.setDisplayName("Jee Migration Example");
		appXmlBuilder.addJavaModule("jee-migration-example-model-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("jee-migration-example-persistence-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("jee-migration-example-client-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("jee-migration-example-service-0.0.1-SNAPSHOT.jar");
		if (includeWar)
			appXmlBuilder.addWebModule("jee-migration-example-ui-0.0.1-SNAPSHOT.war", "jee-migration-example");
		appXmlBuilder.addEjbModule("tx-manager-client-0.0.1-SNAPSHOT.jar");
//		appXmlBuilder.addJavaModule("admin-model-0.0.1-SNAPSHOT.jar");
//		appXmlBuilder.addEjbModule("admin-client-0.0.1-SNAPSHOT.jar");
//		appXmlBuilder.addEjbModule("admin-service-0.0.1-SNAPSHOT.jar");
//		appXmlBuilder.addEjbModule("admin-data-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("common-client-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("common-data-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("jboss-seam-2.3.1.Final.jar");
		String xml = appXmlBuilder.createApplicationXml();
		StringAsset asset = new StringAsset(xml);
		return asset;
	}
	
}
