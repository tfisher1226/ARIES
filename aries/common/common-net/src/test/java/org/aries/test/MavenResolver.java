package org.aries.test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenStrategyStage;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.jboss.shrinkwrap.resolver.api.maven.strategy.TransitiveStrategy;


public class MavenResolver {

	protected static PomEquippedResolveStage resolverSystem;

	protected static Map<String, File> fileCache = new HashMap<String, File>();
	
	
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
		if (type.equals("test-jar"))
			coordinates = groupId + ":" + artifactId + ":" + type + ":tests:" + version;
		
		if (fileCache.containsKey(coordinates))
			return fileCache.get(coordinates);
		
//		File[] libs = Maven.resolver()  
//				.loadPomFromFile("pom.xml").resolve(coordinates)  
//				.withoutTransitivity().
//				asFile();
		  
		//MavenResolverSystem resolverSystem = Maven.configureResolver().fromClassloaderResource("maven/settings.xml");
		
		if (resolverSystem == null) {
			//resolverSystem = Maven.resolver().loadPomFromClassLoaderResource("maven/pom.xml", Thread.currentThread().getContextClassLoader(), "nexus", "it");
			//resolverSystem = Maven.resolver().loadPomFromFile("src/test/resources/maven/pom.xml").
			resolverSystem = Maven.configureResolver().
					withClassPathResolution(true).
					withMavenCentralRepo(true).
					//loadPomFromFile("pom.xml");
					loadPomFromClassLoaderResource("maven/pom.xml");
					//loadPomFromFile("src/test/resources/maven/pom.xml");
					//importDependencies(ScopeType.COMPILE, ScopeType.PROVIDED, ScopeType.TEST);
		}
		
		//MavenStrategyStage resolve = loadPomFromClassLoaderResource.resolve(coordinates);
		//File[] asFile = resolve.withoutTransitivity().asFile();
		
//		if (resolverSystem == null)
//			resolverSystem = Maven.configureResolver().
//					withMavenCentralRepo(false).
//					loadPomFromClassLoaderResource("maven/pom.xml");
//					//loadPomFromClassLoaderResource("maven/pom.xml", Thread.currentThread().getContextClassLoader(), "nexus", "it");

//		if (type.equals("test-jar"))
//			System.out.println();
		
		System.out.println("Dependency Resolver: resolving "+coordinates);
		MavenStrategyStage resolver = resolverSystem.resolve(coordinates);
		File[] files = resolver.
				//withoutTransitivity().
				using(TransitiveStrategy.INSTANCE).
				//withMavenCentralRepo(false).
				//withoutTransitivity().
				asFile();
		
//		if (files.length == 0)
//			System.out.println();
		
		File file = files[0];
		fileCache.put(coordinates, file);

//		if (type.equals("test-jar"))
//			System.out.println();
//		if (artifactId.equals("common-util"))
//			System.out.println();
		
//		try {
//			List<String> jarContents = JarUtil.getJarContents(file);
//			//JarFile jarFile = new JarFile(file);
//			//jarFile.getJarEntry(name);
//			System.out.println();
//		} catch (Exception e) {
//			throw new RuntimeException("\nProblem reading model: "+e.getMessage()+" from "+file.getName());
//		}
		
		return file;
	}
	
}
