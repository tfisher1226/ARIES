package org.aries.test;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;


public class WebArchiveBuilder {

	private WebArchive war;
	
	
	public WebArchiveBuilder(String fileName) {
		war = ShrinkWrap.create(WebArchive.class, fileName);
	}
	
	public WebArchive getArchive() {
		return war;
	}

	public void addWebXml(Asset xml) {
		addAsWebInfResource(xml, "web.xml");
	}

//	public void addDeploymentXml(StringAsset xml) {
//		war.addAsManifestResource(xml, "jboss-deployment-structure.xml");
//	}

	public void addAsResource(String fileName) {
		war.addAsResource(fileName);
	}

	public void addAsResource(Asset xml, String fileName) {
		war.addAsResource(xml, fileName);
	}

	public void addAsManifestResource(String fileName) {
		war.addAsManifestResource(fileName);
	}

	public void addAsManifestResource(Asset xml, String fileName) {
		war.addAsManifestResource(xml, fileName);
	}

	public void addAsWebInfResource(String fileName) {
		war.addAsWebInfResource(fileName);
	}

	public void addAsWebInfResource(Asset asset, String fileName) {
		war.addAsWebInfResource(asset, fileName);
	}
	
	public void addJavaLibraryToWar(String groupId, String artifactId, String version) {
		File jarFile = MavenResolver.getJavaArchiveFile(groupId, artifactId, version);
		String jarFileName = artifactId + ".jar";
		war.addAsLibrary(jarFile, jarFileName);
	}

	public void addTestLibraryToWar(String groupId, String artifactId, String version) {
		File jarFile = MavenResolver.getTestArchiveFile(groupId, artifactId, version);
		String jarFileName = artifactId + "-tests.jar";
		war.addAsLibrary(jarFile, jarFileName);
	}

	public void addClass(String fullyQualifiedClassName) {
		war.addClass(fullyQualifiedClassName);
	}
	
	public void addClass(Class<?> testClassObject) {
		war.addClass(testClassObject);
	}

}
