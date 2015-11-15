package bookshop2.shipper;

import org.aries.test.AbstractArquillianEARBuilder;
import org.aries.test.ApplicationXmlBuilder;
import org.aries.test.EnterpriseArchiveBuilder;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;


public class ShipperTestEARBuilder extends AbstractArquillianEARBuilder {

	public static final String NAME = "bookshop2-shipper.ear";

	private EnterpriseArchiveBuilder earBuilder;
	
	private ApplicationXmlBuilder appXmlBuilder;

	private boolean isIncludeWar;
	
	
	public ShipperTestEARBuilder() {
		this(NAME);
	}
	
	public ShipperTestEARBuilder(String earName) {
		earBuilder = createEnterpriseArchiveBuilder(earName);
		appXmlBuilder = new ApplicationXmlBuilder();
	}
	
	public boolean isIncludeWar() {
		return isIncludeWar;
	}

	public void setIncludeWar(boolean isIncludeWar) {
		this.isIncludeWar = isIncludeWar;
	}

	public void addAsModule(WebArchive webArchive, String contextRoot) {
		appXmlBuilder.addWebModule(webArchive.getName(), contextRoot);
		earBuilder.addAsModule(webArchive);
	}

	public void addAsModule(JavaArchive javaArchive) {
		earBuilder.addAsModule(javaArchive);
	}
	
	public EnterpriseArchive createEAR() {
		earBuilder.addJavaModuleToEar("bookshop2", "bookshop2-model", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("bookshop2.shipper", "bookshop2-shipper-client", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("bookshop2.shipper", "bookshop2-shipper-service", "0.0.1-SNAPSHOT");
		if (isIncludeWar)
			earBuilder.addWarModuleToEar("bookshop2.shipper", "bookshop2-shipper-view", "0.0.1-SNAPSHOT");
		//earBuilder.addJavaModuleToEar("org.aries", "tx-manager-model", "0.0.1-SNAPSHOT");
		//earBuilder.addJavaModuleToEar("org.aries", "tx-manager-base", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("org.aries", "tx-manager-client", "0.0.1-SNAPSHOT");
		//earBuilder.addJavaModuleToEar("org.aries", "tx-manager-registry", "0.0.1-SNAPSHOT");
		earBuilder.addJavaModuleToEar("admin", "admin-model", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("admin", "admin-client", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("admin", "admin-service", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("admin", "admin-data", "0.0.1-SNAPSHOT");
		//earBuilder.addJavaModuleToEar("org.aries", "nam-model", "0.0.1-SNAPSHOT");
		//earBuilder.addJavaModuleToEar("org.aries", "nam-engine", "0.0.1-SNAPSHOT");
		//earBuilder.addJavaModuleToEar("org.aries", "event-model", "0.0.1-SNAPSHOT");
		//earBuilder.addJavaModuleToEar("org.aries", "event-client", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("org.aries", "common-client", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("org.aries", "common-data", "0.0.1-SNAPSHOT");

		earBuilder.buildApplicationXml(getApplicationXml());
		earBuilder.buildDeploymentXml(getDeploymentXml());
		earBuilder.buildBeansXml(getBeansXml());
		if (isDeployDs()) {
			earBuilder.addAsResource("admin-ds.xml");
		}
		if (isDeployJms()) {
			earBuilder.addAsResource("admin-jms.xml");
			earBuilder.addAsResource("bookshop2-jms.xml");
		}
		EnterpriseArchive archive = earBuilder.getArchive();
		return archive;
	}

	public StringAsset getApplicationXml() {
		appXmlBuilder.setDisplayName("Bookshop2 Shipper");
		appXmlBuilder.addJavaModule("bookshop2-model-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("bookshop2-shipper-client-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("bookshop2-shipper-service-0.0.1-SNAPSHOT.jar");
		if (isIncludeWar)
			appXmlBuilder.addWebModule("bookshop2-shipper-view-0.0.1-SNAPSHOT.war", "bookshop2-shipper");
		//appXmlBuilder.addJavaModule("tx-manager-model-0.0.1-SNAPSHOT.jar");
		//appXmlBuilder.addJavaModule("tx-manager-base-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("tx-manager-client-0.0.1-SNAPSHOT.jar");
		//appXmlBuilder.addJavaModule("tx-manager-registry-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addJavaModule("admin-model-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("admin-client-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("admin-service-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("admin-data-0.0.1-SNAPSHOT.jar");
		//appXmlBuilder.addJavaModule("nam-model-0.0.1-SNAPSHOT.jar");
		//appXmlBuilder.addJavaModule("nam-engine-0.0.1-SNAPSHOT.jar");
		//appXmlBuilder.addJavaModule("event-model-0.0.1-SNAPSHOT.jar");
		//appXmlBuilder.addJavaModule("event-client-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("common-client-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("common-data-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("jboss-seam-2.3.1.Final.jar");
		String xml = appXmlBuilder.createApplicationXml();
		StringAsset asset = new StringAsset(xml);
		return asset;
	}

	
}
