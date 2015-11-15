package bookshop2.supplier;

import org.aries.test.AbstractArquillianEARBuilder;
import org.aries.test.ApplicationXmlBuilder;
import org.aries.test.EnterpriseArchiveBuilder;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;


public class SupplierTestEARBuilder extends AbstractArquillianEARBuilder {

	public static final String NAME = "bookshop2-supplier.ear";

	private EnterpriseArchiveBuilder earBuilder;
	
	private ApplicationXmlBuilder appXmlBuilder;

	private boolean includeWar;
	
	
	public SupplierTestEARBuilder() {
		this(NAME);
	}
	
	public SupplierTestEARBuilder(String earName) {
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
		earBuilder.addJavaModuleToEar("bookshop2", "bookshop2-model", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("bookshop2.supplier", "bookshop2-supplier-data", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("bookshop2.supplier", "bookshop2-supplier-client", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("bookshop2.supplier", "bookshop2-supplier-service", "0.0.1-SNAPSHOT");
		if (includeWar)
			earBuilder.addWarModuleToEar("bookshop2.supplier", "bookshop2-supplier-view", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("bookshop2.shipper", "bookshop2-shipper-client", "0.0.1-SNAPSHOT");
		earBuilder.addJavaModuleToEar("org.aries", "tx-manager-base", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("org.aries", "tx-manager-client", "0.0.1-SNAPSHOT");
		earBuilder.addJavaModuleToEar("admin", "admin-model", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("admin", "admin-client", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("admin", "admin-service", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("admin", "admin-data", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("org.aries", "common-client", "0.0.1-SNAPSHOT");
		earBuilder.addEjbModuleToEar("org.aries", "common-data", "0.0.1-SNAPSHOT");
		
		earBuilder.buildApplicationXml(getApplicationXml());
		earBuilder.buildDeploymentXml(getDeploymentXml());
		earBuilder.buildBeansXml(getBeansXml());
		if (isDeployDs()) {
			earBuilder.addAsResource("admin-ds.xml");
			earBuilder.addAsResource("bookshop2-supplier-ds.xml");
		}
		if (isDeployJms()) {
			earBuilder.addAsResource("admin-jms.xml");
			earBuilder.addAsResource("bookshop2-jms.xml");
		}
		
		EnterpriseArchive archive = earBuilder.getArchive();
		return archive;
	}

	public StringAsset getApplicationXml() {
		appXmlBuilder.setDisplayName("Bookshop2 Supplier");
		appXmlBuilder.addJavaModule("bookshop2-model-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("bookshop2-supplier-data-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("bookshop2-supplier-client-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("bookshop2-supplier-service-0.0.1-SNAPSHOT.jar");
		if (includeWar)
			appXmlBuilder.addWebModule("bookshop2-supplier-view-0.0.1-SNAPSHOT.war", "bookshop2-supplier");
		appXmlBuilder.addEjbModule("bookshop2-shipper-client-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("tx-manager-client-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addJavaModule("admin-model-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("admin-client-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("admin-service-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("admin-data-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("common-client-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("common-data-0.0.1-SNAPSHOT.jar");
		appXmlBuilder.addEjbModule("jboss-seam-2.3.1.Final.jar");
		String xml = appXmlBuilder.createApplicationXml();
		StringAsset asset = new StringAsset(xml);
		return asset;
	}
	
}
