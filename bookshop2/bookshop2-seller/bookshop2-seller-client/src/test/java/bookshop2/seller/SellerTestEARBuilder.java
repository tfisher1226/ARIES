package bookshop2.seller;

import org.aries.test.AbstractArquillianEARBuilder;
import org.aries.test.ApplicationXmlBuilder;
import org.aries.test.EnterpriseArchiveBuilder;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;


public class SellerTestEARBuilder extends AbstractArquillianEARBuilder {

	public static final String NAME = "bookshop2-seller.ear";

	private EnterpriseArchiveBuilder earBuilder;
	
	private ApplicationXmlBuilder appXmlBuilder;

	private boolean isIncludeWar;
	
	
	public SellerTestEARBuilder() {
		this(NAME);
	}
	
	public SellerTestEARBuilder(String earName) {
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
		earBuilder.addJavaModuleToEar("bookshop2.seller", "bookshop2-seller-model", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("bookshop2.seller", "bookshop2-seller-client", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("bookshop2.seller", "bookshop2-seller-service", "0.0.1-SNAPSHOT");
		if (isIncludeWar)
			earBuilder.addWarModuleToEar("bookshop2.seller", "bookshop2-seller-view", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("bookshop2.supplier", "bookshop2-supplier-client", "0.0.1-SNAPSHOT");
//		earBuilder.addJavaModuleToEar("org.aries", "tx-manager-model", "0.0.1-SNAPSHOT");
//		earBuilder.addJavaModuleToEar("org.aries", "tx-manager-base", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("org.aries", "tx-manager-client", "0.0.1-SNAPSHOT");
//		earBuilder.addJavaModuleToEar("org.aries", "tx-manager-registry", "0.0.1-SNAPSHOT");
		earBuilder.addJavaModuleToEar("admin", "admin-model", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("admin", "admin-client", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("admin", "admin-service", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("admin", "admin-data", "0.0.1-SNAPSHOT");
		//earBuilder.addJavaModuleToEar("org.aries", "event-model", "0.0.1-SNAPSHOT");
		//earBuilder.addJavaModuleToEar("org.aries", "event-client", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("org.aries", "common-client", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("org.aries", "common-data", "0.0.1-SNAPSHOT");
		
		earBuilder.buildApplicationXml(getApplicationXml());
		earBuilder.buildDeploymentXml(getDeploymentXml());
		earBuilder.buildBeansXml(getBeansXml());
		if (isDeployDs()) {
			earBuilder.addAsResource("admin-ds.xml");
			//earBuilder.addAsResource("bookshop2-seller-ds.xml");
		}
		if (isDeployJms()) {
			earBuilder.addAsResource("admin-jms.xml");
			earBuilder.addAsResource("bookshop2-jms.xml");
		}
		EnterpriseArchive archive = earBuilder.getArchive();
		return archive;
	}

	public StringAsset getApplicationXml() {
		appXmlBuilder.setDisplayName("Bookshop2 Buyer");
		appXmlBuilder.addJavaModule("bookshop2-model-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addJavaModule("bookshop2-seller-model-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("bookshop2-seller-client-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("bookshop2-seller-service-0.0.1-SNAPSHOT.jar");
		if (isIncludeWar)
			appXmlBuilder.addWebModule("bookshop2-seller-view-0.0.1-SNAPSHOT.war", "bookshop2-seller");
		appXmlBuilder.addEjbModule("bookshop2-supplier-client-0.0.1-SNAPSHOT.jar");
//		appXmlBuilder.addJavaModule("tx-manager-model-0.0.1-SNAPSHOT.jar");
//		appXmlBuilder.addJavaModule("tx-manager-base-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("tx-manager-client-0.0.1-SNAPSHOT.jar");
//		appXmlBuilder.addJavaModule("tx-manager-registry-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addJavaModule("admin-model-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("admin-client-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("admin-service-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("admin-data-0.0.1-SNAPSHOT.jar");
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
