package org.aries.test;

import java.io.File;

import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenStrategyStage;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;


public class MavenUtil {

	protected static PomEquippedResolveStage resolverSystem;

	
	public static File getJavaArchiveFile(String groupId, String artifactId, String version) {
		return getArchiveFile(groupId, artifactId, version, "jar");
	}
	
	public static File getTestArchiveFile(String groupId, String artifactId, String version) {
		return getArchiveFile(groupId, artifactId, version, "test-jar");
	}
	
	public static File getEjbArchiveFile(String groupId, String artifactId, String version) {
		return getArchiveFile(groupId, artifactId, version, "ejb");
	}
	
	public static File getWebArchiveFile(String groupId, String artifactId, String version) {
		return getArchiveFile(groupId, artifactId, version, "war");
	}
	
	public static File getEnterpriseArchiveFile(String groupId, String artifactId, String version) {
		return getArchiveFile(groupId, artifactId, version, "ear");
	}
	
	public static File getArchiveFile(String groupId, String artifactId, String version, String type) {
		String coordinates = groupId + ":" + artifactId + ":" + type + ":" + version;
		
		if (resolverSystem == null)
			resolverSystem = Maven.resolver().loadPomFromClassLoaderResource("maven/pom.xml");
		
		//System.out.println(">>> "+coordinates);
		MavenStrategyStage resolver = resolverSystem.resolve(coordinates);
		File[] files = resolver.
				withMavenCentralRepo(false).
				withoutTransitivity().
				asFile();
		File file = files[0];
		return file;
	}
	
}
