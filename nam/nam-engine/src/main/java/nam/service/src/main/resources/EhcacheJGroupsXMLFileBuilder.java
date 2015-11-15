package nam.service.src.main.resources;

import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Cache;
import nam.model.Process;
import nam.model.util.CacheUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.ServiceUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class EhcacheJGroupsXMLFileBuilder extends AbstractFileBuilder {

	public EhcacheJGroupsXMLFileBuilder(GenerationContext context) {
		this.context = context;
	}

	public ModelFile buildFile() throws Exception {
		String fileNamePrefix = NameUtil.splitStringUsingDashes(context.getApplication().getArtifactId());
		String fileName = NameUtil.uncapName(fileNamePrefix) + "-ehcache-jgroups.xml";
		ModelFile modelFile = createMainResourcesFile(fileName);
		modelFile.setTextContent(getFileContent(false));
		return modelFile;
	}
	
	public String getFileContent(boolean isTest) throws Exception {
		String namespace = context.getModule().getNamespace();
		String namePrefix = NameUtil.getLocalPartFromNamespace(namespace);
		String managerName = NameUtil.capName(namePrefix) + "CacheManager";
		
		Buf buf = new Buf();
		buf.put(buildEhcacheXmlOpen(managerName));
		buf.put(buildEhcacheXmlContent_Settings());
		buf.put(buildEhcacheXmlContent_DefaultCache());
		
		List<Process> processes = ModuleUtil.getProcesses(context.getModule());
		Iterator<Process> iterator = processes.iterator();
		while (iterator.hasNext()) {
			Process process = iterator.next();
			namespace = process.getNamespace();
			List<Cache> cacheUnits = process.getCacheUnits();
			Iterator<Cache> cacheIterator = cacheUnits.iterator();
			while (cacheIterator.hasNext()) {
				Cache cache = cacheIterator.next();
				buf.put(buildEhcacheXmlContent_UserCache(cache.getName()));
			}
		}

		buf.put(buildEhcacheXmlClose());
		return buf.get();
	}
	
	protected String buildEhcacheXmlOpen(String managerName) {
		Buf buf = new Buf();
		buf.putLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buf.putLine("");
		
		buf.putLine("<ehcache");
		buf.putLine("	name=\""+managerName+"\"");
		buf.putLine("	updateCheck=\"false\""); 
		buf.putLine("	monitoring=\"autodetect\"");
		buf.putLine("	dynamicConfig=\"true\"");
		buf.putLine("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		buf.putLine("	xsi:noNamespaceSchemaLocation=\"ehcache.xsd\">");
		return buf.get();
	}

	protected String buildEhcacheXmlContent_Settings() {
		Buf buf = new Buf();
		buf.putLine1("");
		buf.putLine1("<!--");
		buf.putLine1("	Sets the path to the directory where cache data and index files are created.");
		buf.putLine1("	If the path is a Java System Property it is replaced by its value in the running VM.");
		buf.putLine1("	Subdirectories can be specified below the property e.g. java.io.tmpdir/one"); 
		buf.putLine1("-->");
		buf.putLine1("");
		buf.putLine1("<diskStore path=\"java.io.tmpdir/registry-cache\" />");
		buf.putLine1("");
		buf.putLine1("");
		buf.putLine1("<!--");
		buf.putLine1("	Specifies a CacheManagerPeerProviderFactory which will be used to create a"); 
		buf.putLine1("	CacheManagerPeerProvider, which discovers other CacheManagers in the cluster.");  
		buf.putLine1("-->");
		buf.putLine1("");
		buf.putLine1("<cacheManagerPeerProviderFactory");
		buf.putLine1("	class=\"net.sf.ehcache.distribution.jgroups.JGroupsCacheManagerPeerProviderFactory\" />");
		buf.putLine1("");
		buf.putLine1("<!--");
		buf.putLine1("<cacheManagerPeerProviderFactory");
		buf.putLine1("	class=\"net.sf.ehcache.distribution.jgroups.JGroupsCacheManagerPeerProviderFactory\"");
		buf.putLine1("	properties=\"file=registry-jgroups.xml\" />");
		buf.putLine1("-->");
		buf.putLine1("");
		buf.putLine1("<transactionManagerLookup");
		buf.putLine1("	class=\"net.sf.ehcache.transaction.manager.DefaultTransactionManagerLookup\"");
		buf.putLine1("	properties=\"jndiName=java:jboss/TransactionManager\""); 
		buf.putLine1("	propertySeparator=\";\" />");
		//buf.putLine1("");
		//buf.putLine1("<!--");  
		//buf.putLine1("<transactionManagerLookup");
		//buf.putLine1("	class=\"org.aries.cache.TransactionManagerLookupClass\"");
		//buf.putLine1("	properties=\"\""); 
		//buf.putLine1("	propertySeparator=\";\" />");
		//buf.putLine1("-->");
		return buf.get();
	}

	protected String buildEhcacheXmlContent_DefaultCache() {
		Buf buf = new Buf();
		buf.putLine1("");
		buf.putLine1("");
		buf.putLine1("<!--");
		buf.putLine1("	******************************************************************************");
		buf.putLine1("	Mandatory Default Cache configuration. These settings will be applied to"); 
		buf.putLine1("	caches created programmatically using CacheManager.add(String cacheName).");
		buf.putLine1("	******************************************************************************");
		buf.putLine1("-->");
		buf.putLine1("");
		buf.putLine1("<defaultCache"); 
		buf.putLine1("	eternal=\"false\"");
		buf.putLine1("	maxElementsInMemory=\"0\""); 
		buf.putLine1("	timeToIdleSeconds=\"100\""); 
		buf.putLine1("	timeToLiveSeconds=\"100\""); 
		buf.putLine1("	overflowToDisk=\"true\"");
		buf.putLine1("	transactionalMode=\"off\">");
		buf.putLine1("");
		buf.putLine1("	<cacheEventListenerFactory");
		buf.putLine1("		class=\"net.sf.ehcache.distribution.jgroups.JGroupsCacheReplicatorFactory\"");
		buf.putLine1("		properties=\"");
		buf.putLine1("			replicateAsynchronously=true,"); 
		buf.putLine1("			replicatePuts=true,");
		buf.putLine1("			replicateUpdates=true,"); 
		buf.putLine1("			replicateUpdatesViaCopy=false,"); 
		buf.putLine1("			replicateRemovals=true\" />");
		buf.putLine1("</defaultCache>");
		return buf.get();
	}

	protected String buildEhcacheXmlContent_UserCache(String cacheName) {
		Buf buf = new Buf();
		buf.putLine1("");
		buf.putLine1("");
		buf.putLine1("<!--");
		buf.putLine1("	***********************************************************");
		buf.putLine1("	Cache configuration for \""+cacheName+"\" cache."); 
		buf.putLine1("	***********************************************************");
		buf.putLine1("-->");
		buf.putLine1("");
		buf.putLine1("<cache"); 
		buf.putLine1("	name=\""+cacheName+"\""); 
		buf.putLine1("	eternal=\"true\"");
		buf.putLine1("	maxElementsInMemory=\"0\""); 
		buf.putLine1("	clearOnFlush=\"false\""); 
		buf.putLine1("	overflowToDisk=\"false\"");
		buf.putLine1("	overflowToOffHeap=\"false\"");
		buf.putLine1("	transactionalMode=\"xa_strict\">");
		buf.putLine1("");
		buf.putLine1("<searchable>");
		buf.putLine1("	<searchAttribute name=\"elementName\" class=\"org.aries.cache.CachedItemNameAttributeExtractor\" />");
		buf.putLine1("</searchable>");
		buf.putLine1("");
		buf.putLine1("	<!--");
		buf.putLine1("	<persistence strategy=\"none\" />");
		buf.putLine1("	-->");
		buf.putLine1("");
		buf.putLine1("	<!-- JGROUPS Cache Replication -->");
		buf.putLine1("	<cacheEventListenerFactory");
		buf.putLine1("		class=\"net.sf.ehcache.distribution.jgroups.JGroupsCacheReplicatorFactory\"");
		buf.putLine1("		properties=\"");
		buf.putLine1("			replicatePuts=true,");
		buf.putLine1("			replicateUpdates=true,"); 
		buf.putLine1("			replicateUpdatesViaCopy=false,"); 
		buf.putLine1("			replicateRemovals=true,");
		buf.putLine1("			replicateAsynchronously=true,");
		buf.putLine1("			asynchronousReplicationIntervalMillis=1000\" />");
		buf.putLine1("");
		buf.putLine1("	<!-- General Exception Handling -->");
		buf.putLine1("	<cacheExceptionHandlerFactory"); 
		buf.putLine1("		class=\"org.aries.cache.GeneralCacheExceptionHandlerFactory\"");
		buf.putLine1("		properties=\"logLevel=FINE\" />");
		buf.putLine1("");
		buf.putLine1("	<!-- JGROUPS Cache bootstrap -->");
		buf.putLine1("	<bootstrapCacheLoaderFactory");
		buf.putLine1("		class=\"net.sf.ehcache.distribution.jgroups.JGroupsBootstrapCacheLoaderFactory\"");
		buf.putLine1("		properties=\"bootstrapAsynchronously=false\" />");
		//buf.putLine1("");
		//buf.putLine1("	<!--");
		//buf.putLine1("	<transactionManagerLookup");
		//buf.putLine1("		class=\"org.aries.tx.TransactionManagerLookupClass\"");
		//buf.putLine1("		propertySeparator=\":\""); 
		//buf.putLine1("		properties=\"\" />");
		//buf.putLine1("		-->");
		buf.putLine1("");
		buf.putLine1("	<!--");  	
		buf.putLine1("	<pinning storage=\"onHeap | inMemory | inCache\" />");
		buf.putLine1("	-->");
		buf.putLine1("</cache>");
		return buf.get();
	}

	protected String buildEhcacheXmlClose() {
		Buf buf = new Buf();
		buf.putLine("</ehcache>");
		return buf.get();
	}

}
