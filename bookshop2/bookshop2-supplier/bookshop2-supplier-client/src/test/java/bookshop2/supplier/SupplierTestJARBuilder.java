package bookshop2.supplier;

import org.aries.test.AbstractArquillianJARBuilder;
import org.aries.test.JavaArchiveBuilder;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;


public class SupplierTestJARBuilder extends AbstractArquillianJARBuilder {

	private JavaArchiveBuilder builder;
	
	
	public SupplierTestJARBuilder(String fileName) {
		builder = createJavaArchiveBuilder(fileName); 
	}
	
	public void addClass(String fullyQualifiedClassName) {
		builder.addClass(fullyQualifiedClassName);
	}
	
	public void addClass(Class<?> testClassObject) {
		builder.addClass(testClassObject);
	}
	
	public JavaArchive create() {
		//addDefaultTestLibraries(builder);
		builder.addTestLibraryToWar("org.aries", "common-net", "0.0.1-SNAPSHOT");
		builder.addTestLibraryToWar("org.aries", "common-jms", "0.0.1-SNAPSHOT");
		builder.addTestLibraryToWar("org.aries", "common-jmx", "0.0.1-SNAPSHOT");
		builder.addTestLibraryToWar("org.aries", "common-jndi", "0.0.1-SNAPSHOT");
		builder.addTestLibraryToWar("org.aries", "common-data", "0.0.1-SNAPSHOT");
		//builder.addTestLibraryToWar("org.aries", "common-client", "0.0.1-SNAPSHOT");
		builder.addTestLibraryToWar("org.aries", "common-runtime", "0.0.1-SNAPSHOT");

//		builder.addJavaLibraryToWar("bookshop2", "bookshop2-model", "0.0.1-SNAPSHOT");
//		builder.addJavaLibraryToWar("bookshop2", "bookshop2-seller-model", "0.0.1-SNAPSHOT");
//		builder.addJavaLibraryToWar("bookshop2", "bookshop2-seller-client", "0.0.1-SNAPSHOT");
//		builder.addJavaLibraryToWar("bookshop2", "bookshop2-shipper-client", "0.0.1-SNAPSHOT");
//		builder.addJavaLibraryToWar("bookshop2", "bookshop2-supplier-model", "0.0.1-SNAPSHOT");
//		builder.addJavaLibraryToWar("bookshop2", "bookshop2-supplier-client", "0.0.1-SNAPSHOT");
//		//builder.addWarModuleToEar("bookshop2", "bookshop2-supplier-view", "0.0.1-SNAPSHOT");
		
//		builder.addTestLibraryToWar("bookshop2", "bookshop2-model", "0.0.1-SNAPSHOT");
		builder.addTestLibraryToWar("bookshop2.supplier", "bookshop2-supplier-data", "0.0.1-SNAPSHOT");
		builder.addTestLibraryToWar("bookshop2.supplier", "bookshop2-supplier-service", "0.0.1-SNAPSHOT");

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
		
		builder.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		
		JavaArchive archive = builder.getArchive();
		return archive;
	}
	
}
