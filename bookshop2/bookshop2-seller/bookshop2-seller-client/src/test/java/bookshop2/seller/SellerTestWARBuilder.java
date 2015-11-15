package bookshop2.seller;

import org.aries.test.AbstractArquillianWARBuilder;
import org.aries.test.WebArchiveBuilder;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;


public class SellerTestWARBuilder extends AbstractArquillianWARBuilder {

	private WebArchiveBuilder builder;
	
	
	public SellerTestWARBuilder(String fileName) {
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
		builder.addTestLibraryToWar("org.aries", "common-data", "0.0.1-SNAPSHOT");
		//builder.addTestLibraryToWar("org.aries", "common-client", "0.0.1-SNAPSHOT");

//		builder.addJavaLibraryToWar("bookshop2", "bookshop2-model", "0.0.1-SNAPSHOT");
//		builder.addJavaLibraryToWar("bookshop2", "bookshop2-seller-model", "0.0.1-SNAPSHOT");
//		builder.addJavaLibraryToWar("bookshop2", "bookshop2-seller-client", "0.0.1-SNAPSHOT");
//		builder.addJavaLibraryToWar("bookshop2", "bookshop2-shipper-client", "0.0.1-SNAPSHOT");
//		builder.addJavaLibraryToWar("bookshop2", "bookshop2-supplier-model", "0.0.1-SNAPSHOT");
//		builder.addJavaLibraryToWar("bookshop2", "bookshop2-supplier-client", "0.0.1-SNAPSHOT");
//		//builder.addWarModuleToEar("bookshop2", "bookshop2-supplier-view", "0.0.1-SNAPSHOT");
		
//		builder.addTestLibraryToWar("bookshop2", "bookshop2-model", "0.0.1-SNAPSHOT");
//		builder.addTestLibraryToWar("bookshop2.seller", "bookshop2-seller-data", "0.0.1-SNAPSHOT");
		builder.addTestLibraryToWar("bookshop2.seller", "bookshop2-seller-service", "0.0.1-SNAPSHOT");

		//builder.addJavaLibraryToWar("org.aries", "tx-manager-model", "0.0.1-SNAPSHOT");
		//builder.addJavaLibraryToWar("org.aries", "tx-manager-base", "0.0.1-SNAPSHOT");
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
		
		//builder.addWebXml(getWebXml());
		WebArchive archive = builder.getArchive();
		return archive;
	}
	
}
