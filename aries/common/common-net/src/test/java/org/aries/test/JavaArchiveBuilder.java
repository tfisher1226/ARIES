package org.aries.test;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;


public class JavaArchiveBuilder {

	private JavaArchive jar;
	
	
	public JavaArchiveBuilder(String fileName) {
		jar = ShrinkWrap.create(JavaArchive.class, fileName);
	}
	
	public JavaArchive getArchive() {
		return jar;
	}

	public void addAsResource(String fileName) {
		jar.addAsResource(fileName);
	}

	public void addAsResource(Asset xml, String fileName) {
		jar.addAsResource(xml, fileName);
	}

	public void addAsManifestResource(String fileName) {
		jar.addAsManifestResource(fileName);
	}

	public void addAsManifestResource(Asset xml, String fileName) {
		jar.addAsManifestResource(xml, fileName);
	}

	public void addJavaLibraryToWar(String groupId, String artifactId, String version) {
		File jarFile = MavenResolver.getJavaArchiveFile(groupId, artifactId, version);
		String jarFileName = artifactId + ".jar";
		FileAsset fileAsset = new FileAsset(jarFile);
		jar.add(fileAsset, jarFileName);
	}

	public void addTestLibraryToWar(String groupId, String artifactId, String version) {
		File jarFile = MavenResolver.getTestArchiveFile(groupId, artifactId, version);
		String jarFileName = artifactId + "-tests.jar";
		FileAsset fileAsset = new FileAsset(jarFile);
		jar.add(fileAsset, jarFileName);
	}

	public void addClass(String fullyQualifiedClassName) {
		jar.addClass(fullyQualifiedClassName);
	}
	
	public void addClass(Class<?> testClassObject) {
		jar.addClass(testClassObject);
	}

}
