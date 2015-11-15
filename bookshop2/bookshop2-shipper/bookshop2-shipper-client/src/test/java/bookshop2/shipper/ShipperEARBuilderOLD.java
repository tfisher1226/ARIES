package bookshop2.shipper;

import org.aries.test.AbstractArquillianEARBuilder;
import org.aries.test.ApplicationXmlBuilder;
import org.aries.test.EnterpriseArchiveBuilder;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;


public class ShipperEARBuilderOLD extends AbstractArquillianEARBuilder {

	private static ShipperEARBuilderOLD INSTANCE = new ShipperEARBuilderOLD();
	
	
	public static EnterpriseArchive buildEAR() {
		EnterpriseArchive ear = INSTANCE.createEAR();
		return ear;
	}
	
	public EnterpriseArchive createEAR() {
		EnterpriseArchiveBuilder builder = createEnterpriseArchiveBuilder("bookshop2-shipper.ear"); 
		builder.addJavaModuleToEar("bookshop2", "bookshop2-model", "0.0.1-SNAPSHOT");
		builder.addJavaModuleToEar("bookshop2", "bookshop2-seller-model", "0.0.1-SNAPSHOT");
		builder.addJavaModuleToEar("bookshop2", "bookshop2-seller-client", "0.0.1-SNAPSHOT");
		builder.addJavaModuleToEar("bookshop2", "bookshop2-supplier-client", "0.0.1-SNAPSHOT");
		builder.addJavaModuleToEar("bookshop2", "bookshop2-shipper-client", "0.0.1-SNAPSHOT");
		builder.addJavaModuleToEar("bookshop2", "bookshop2-shipper-service", "0.0.1-SNAPSHOT");
		builder.addWarModuleToEar("bookshop2", "bookshop2-shipper-view", "0.0.1-SNAPSHOT");
		builder.addJavaModuleToEar("org.aries", "admin-model", "0.0.1-SNAPSHOT");
		builder.addJavaModuleToEar("org.aries", "admin-client", "0.0.1-SNAPSHOT");
		builder.buildApplicationXml(getApplicationXml());
		builder.buildDeploymentXml(getDeploymentXml());
		if (isDeployJms())
			builder.addAsResource("bookshop2-jms.xml");
		EnterpriseArchive archive = builder.getArchive();
		return archive;
	}

	public StringAsset getApplicationXml() {
		ApplicationXmlBuilder builder = new ApplicationXmlBuilder();
		builder.setDisplayName("Bookshop2 Buyer");
		builder.addJavaModule("bookshop2-model-0.0.1-SNAPSHOT.jar");
		builder.addJavaModule("bookshop2-seller-model-0.0.1-SNAPSHOT.jar");
		builder.addJavaModule("bookshop2-seller-client-0.0.1-SNAPSHOT.jar");
		builder.addJavaModule("bookshop2-supplier-client-0.0.1-SNAPSHOT.jar");
		builder.addJavaModule("bookshop2-shipper-client-0.0.1-SNAPSHOT.jar");
		builder.addJavaModule("bookshop2-shipper-service-0.0.1-SNAPSHOT.jar");
		builder.addWebModule("bookshop2-shipper-view-0.0.1-SNAPSHOT.war", "bookshop2-shipper");
		builder.addJavaModule("admin-model-0.0.1-SNAPSHOT.jar");
		builder.addJavaModule("admin-client-0.0.1-SNAPSHOT.jar");
		builder.addJavaModule("jboss-seam-2.3.0.Final.jar");
		String xml = builder.createApplicationXml();
		StringAsset asset = new StringAsset(xml);
		return asset;
	}
	
}
