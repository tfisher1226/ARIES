package org.aries.tx;

import org.aries.test.AbstractArquillianEARBuilder;
import org.aries.test.ApplicationXmlBuilder;
import org.aries.test.EnterpriseArchiveBuilder;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;


public class TXManagerTestEARBuilder extends AbstractArquillianEARBuilder {

	private static TXManagerTestEARBuilder INSTANCE = new TXManagerTestEARBuilder();
	
	
	public static EnterpriseArchive buildEAR() {
		EnterpriseArchive ear = INSTANCE.createEAR();
		return ear;
	}
	
	public EnterpriseArchive createEAR() {
		EnterpriseArchiveBuilder builder = createEnterpriseArchiveBuilder("tx-manager.ear"); 
		builder.addJavaModuleToEar("org.aries", "tx-manager-model", "0.0.1-SNAPSHOT");
		builder.addJavaModuleToEar("org.aries", "tx-manager-base", "0.0.1-SNAPSHOT");
		//builder.addJavaModuleToEar("org.aries", "tx-manager-client", "0.0.1-SNAPSHOT");
		builder.addJavaModuleToEar("org.aries", "tx-manager-registry", "0.0.1-SNAPSHOT");
		builder.addJavaModuleToEar("org.aries", "tx-manager-service", "0.0.1-SNAPSHOT");
		//builder.addJavaModuleToEar("org.aries", "tx-manager-recovery", "0.0.1-SNAPSHOT");
		builder.addJavaModuleToEar("org.aries", "tx-manager-bridge", "0.0.1-SNAPSHOT");
		builder.buildApplicationXml(getApplicationXml());
		builder.buildDeploymentXml(getDeploymentXml());
		builder.buildBeansXml(getBeansXml());
		//builder.addAsResource("tx-manager-jms.xml");
		EnterpriseArchive archive = builder.getArchive();
		return archive;
	}

	public StringAsset getApplicationXml() {
		ApplicationXmlBuilder builder = new ApplicationXmlBuilder();
		builder.setDisplayName("TX Manager");
		builder.addJavaModule("tx-manager-model-0.0.1-SNAPSHOT.jar");
		builder.addJavaModule("tx-manager-base-0.0.1-SNAPSHOT.jar");
		//builder.addJavaModule("tx-manager-client-0.0.1-SNAPSHOT.jar");
		builder.addJavaModule("tx-manager-registry-0.0.1-SNAPSHOT.jar");
		builder.addJavaModule("tx-manager-service-0.0.1-SNAPSHOT.jar");
		//builder.addJavaModule("tx-manager-recovery-0.0.1-SNAPSHOT.jar");
		builder.addJavaModule("tx-manager-bridge-0.0.1-SNAPSHOT.jar");
		builder.addJavaModule("jboss-seam-2.3.0.Final.jar");
		String xml = builder.createApplicationXml();
		StringAsset asset = new StringAsset(xml);
		return asset;
	}
	
}
