package redhat.jee_migration_example;

import org.aries.test.AbstractArquillianWARBuilder;
import org.aries.test.ComponentsXmlBuilder;
import org.aries.test.WebArchiveBuilder;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;


public class EventLoggerTestWARBuilder extends AbstractArquillianWARBuilder {

	private WebArchiveBuilder builder;
	
	
	public EventLoggerTestWARBuilder(String fileName) {
		builder = createWebArchiveBuilder(fileName); 
	}
	
	public void addClass(String fullyQualifiedClassName) {
		builder.addClass(fullyQualifiedClassName);
	}
	
	public void addClass(Class<?> testClassObject) {
		builder.addClass(testClassObject);
	}
	
	public WebArchive create() {
		//addDefaultTestLibraries(builder);
		builder.addTestLibraryToWar("org.aries", "common-net", "0.0.1-SNAPSHOT");
		builder.addTestLibraryToWar("org.aries", "common-jms", "0.0.1-SNAPSHOT");
		builder.addTestLibraryToWar("org.aries", "common-jmx", "0.0.1-SNAPSHOT");
		builder.addTestLibraryToWar("org.aries", "common-jndi", "0.0.1-SNAPSHOT");
		builder.addTestLibraryToWar("org.aries", "common-data", "0.0.1-SNAPSHOT");
		//builder.addTestLibraryToWar("org.aries", "common-client", "0.0.1-SNAPSHOT");
		builder.addTestLibraryToWar("org.aries", "common-runtime", "0.0.1-SNAPSHOT");
		
		builder.addTestLibraryToWar("redhat", "jee-migration-example-persistence", "0.0.1-SNAPSHOT");
		builder.addTestLibraryToWar("redhat", "jee-migration-example-service", "0.0.1-SNAPSHOT");

		//builder.addJavaLibraryToWar("org.aries", "tx-manager-model", "0.0.1-SNAPSHOT");
		builder.addJavaLibraryToWar("org.aries", "tx-manager-base", "0.0.1-SNAPSHOT");
		//builder.addJavaLibraryToWar("org.aries", "tx-manager-client", "0.0.1-SNAPSHOT");
		//builder.addJavaLibraryToWar("org.aries", "tx-manager-registry", "0.0.1-SNAPSHOT");
		builder.addTestLibraryToWar("org.aries", "tx-manager-client", "0.0.1-SNAPSHOT");
		builder.addTestLibraryToWar("org.aries", "tx-manager-test", "0.0.1-SNAPSHOT");
		
//		builder.addJavaLibraryToWar("org.aries", "admin-model", "0.0.1-SNAPSHOT");
//		builder.addJavaLibraryToWar("org.aries", "admin-client", "0.0.1-SNAPSHOT");

//		builder.addJavaLibraryToWar("org.jboss.byteman", "byteman-install", "2.1.4.1");
		builder.addJavaLibraryToWar("org.jboss.byteman", "byteman-submit", "2.1.4.1");
		builder.addJavaLibraryToWar("org.jboss.byteman", "byteman-bmunit", "2.1.4.1");
		
		builder.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		builder.addAsWebInfResource(getComponentsXml(), "components.xml");
		//builder.addAsManifestResource(getJNDIProperties(), "jndi.properties");
		//builder.addWebXml(getWebXml());
		
		WebArchive archive = builder.getArchive();
		return archive;
	}
	
	public Asset getComponentsXml() {
		ComponentsXmlBuilder builder = new ComponentsXmlBuilder();
		builder.addEntityManager("redhat.itemInventory");
		String xml = builder.createApplicationXml();
		StringAsset asset = new StringAsset(xml);
		return asset;
	}
	
//	public Asset getJNDIProperties() {
//		StringBuilder buf = new StringBuilder();
//		buf.append("java.naming.factory.initial=org.jboss.as.naming.InitialContextFactory\n");
//		buf.append("java.naming.factory.url.pkgs=org.jboss.ejb.client.naming\n");
//		buf.append("java.naming.provider.url=jnp://localhost:9999\n");
//		StringAsset asset = new StringAsset(buf.toString());
//		return asset;
//	}

}
