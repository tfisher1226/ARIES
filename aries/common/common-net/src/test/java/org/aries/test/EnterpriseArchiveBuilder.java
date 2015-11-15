package org.aries.test;

import java.io.File;

import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;


public class EnterpriseArchiveBuilder {

	private EnterpriseArchive ear;
	
	
	public EnterpriseArchiveBuilder(String fileName) {
		ear = ShrinkWrap.create(EnterpriseArchive.class, fileName);
	}
	
	public EnterpriseArchive getArchive() {
		return ear;
	}

	public void buildApplicationXml(StringAsset xml) {
		ear.addAsManifestResource(xml, "application.xml");
	}

	public void buildDeploymentXml(StringAsset xml) {
		ear.addAsManifestResource(xml, "jboss-deployment-structure.xml");
		//ear.addAsApplicationResource(ResourceUtil.getResource("META-INF/jboss-deployment-structure.xml"), "/META-INF/jboss-deployment-structure.xml");
	}

	public void buildBeansXml(StringAsset xml) {
		ear.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		//ear.addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
	}
	
	public void addAsResource(String fileName) {
		ear.addAsResource(fileName);
	}

	public void addAsResource(StringAsset xml, String fileName) {
		ear.addAsResource(xml, fileName);
	}

	public void addAsManifestResource(String fileName) {
		ear.addAsManifestResource(fileName);
	}

	public void addAsManifestResource(StringAsset xml, String fileName) {
		ear.addAsManifestResource(xml, fileName);
	}

	public void addAsModule(WebArchive webArchive) {
		ear.addAsModule(webArchive);
	}

	public void addAsModule(JavaArchive javaArchive) {
		ear.addAsModule(javaArchive);
	}

	public void addJavaLibraryToEar(String groupId, String artifactId, String version) {
		File jarFile = MavenResolver.getJavaArchiveFile(groupId, artifactId, version);
		String jarFileName = artifactId + ".jar";
		ear.addAsLibrary(jarFile, jarFileName);
	}

	public void addTestLibraryToEar(String groupId, String artifactId, String version) {
		File jarFile = MavenResolver.getTestArchiveFile(groupId, artifactId, version);
		String jarFileName = artifactId + ".jar";
		ear.addAsLibrary(jarFile, jarFileName);
	}

	public void addJavaModuleToEar(String groupId, String artifactId, String version) {
		File jarFile = MavenResolver.getJavaArchiveFile(groupId, artifactId, version);
		String jarFileName = artifactId + "-" + version + ".jar";
		ear.addAsModule(jarFile, jarFileName);
	}

	public void addEjbModuleToEar(String groupId, String artifactId, String version) {
		File jarFile = MavenResolver.getEjbArchiveFile(groupId, artifactId, version);
		String jarFileName = artifactId + "-" + version + ".jar";
		ear.addAsModule(jarFile, jarFileName);
	}

	public void addWarModuleToEar(String groupId, String artifactId, String version) {
		File warFile = MavenResolver.getWebArchiveFile(groupId, artifactId, version);
		String warFileName = artifactId + "-" + version + ".war";
		ear.addAsModule(warFile, warFileName);
	}
	
}
