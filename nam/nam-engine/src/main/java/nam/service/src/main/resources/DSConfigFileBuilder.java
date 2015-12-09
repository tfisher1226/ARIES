package nam.service.src.main.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nam.model.Application;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Persistence;
import nam.model.Project;
import nam.model.Unit;
import nam.model.util.ApplicationUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.ProjectUtil;
import aries.codegen.AbstractFileBuilder;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class DSConfigFileBuilder extends AbstractFileBuilder {

	public DSConfigFileBuilder(GenerationContext context) {
		this.context = context;
	}

	public List<ModelFile> buildFiles() throws Exception {
		return buildFiles(false);
	}

	public List<ModelFile> buildForTest() throws Exception {
		return buildFiles(true);
	}

//	public List<ModelFile> buildFiles(boolean isTest) throws Exception {
//		List<ModelFile> modelFiles = new ArrayList<ModelFile>();
//		List<Persistence> blocks = ProjectUtil.getPersistenceBlocks(context.getProject());
//		Iterator<Persistence> iterator = blocks.iterator();
//		while (iterator.hasNext()) {
//			Persistence block = iterator.next();
//			String name = NameUtil.uncapName(block.getName());
//			ModelFile modelFile = buildFile(isTest, name+"-ds.xml", block);
//			modelFiles.add(modelFile);
//		}
//		return modelFiles;
//	}
	
	public List<ModelFile> buildFiles(boolean isTest) throws Exception {
		List<ModelFile> modelFiles = new ArrayList<ModelFile>();
		
		Module module = context.getModule();
		List<Persistence> persistenceBlocks = getRequiredPersistenceBlocks(module);
		Iterator<Persistence> iterator = persistenceBlocks.iterator();
		while (iterator.hasNext()) {
			Persistence persistenceBlock = iterator.next();
			modelFiles.add(buildDSFile(isTest, persistenceBlock));
		}
		
		Project project = context.getProject();
		Persistence persistenceBlock = ProjectUtil.getPersistenceBlockByNamespace(project, module.getNamespace());
		//Persistence persistenceBlock = ModuleUtil.getPersistenceBlock(module);
		if (persistenceBlock != null)
			modelFiles.add(buildDSFile(isTest, persistenceBlock));
		
		Map<String, Namespace> importedNamespaces = CodeUtil.getImportedNamespaces(module);
		modelFiles.addAll(buildDSFiles(isTest, importedNamespaces));
		return modelFiles;
	}

	protected List<Persistence> getRequiredPersistenceBlocks(Module module) {
		List<Persistence> persistenceBlocks = new ArrayList<Persistence>();
		Application application = context.getApplication();
		List<String> projectNames = ApplicationUtil.getProjectNames(application);
		Iterator<String> iterator = projectNames.iterator();
		while (iterator.hasNext()) {
			String projectName = iterator.next();
			Project project = context.getProjectByName(projectName);
			List<Persistence> blocks = ProjectUtil.getPersistenceBlocks(project);
			persistenceBlocks.addAll(blocks);
		}
		return persistenceBlocks;
	}

	protected List<ModelFile> buildDSFiles(boolean isTest, Map<String, Namespace> namespaceMap) throws Exception {
		List<ModelFile> modelFiles = new ArrayList<ModelFile>();
		Iterator<String> iterator = namespaceMap.keySet().iterator();
		Project project = context.getProject();
		while (iterator.hasNext()) {
			String uri = iterator.next();
			Persistence persistenceBlock = ProjectUtil.getPersistenceBlockByNamespace(project, uri);
			if (persistenceBlock != null) {
				Collection<Unit> units = PersistenceUtil.getUnits(persistenceBlock);
				if (!units.isEmpty()) {
					ModelFile modelFile = buildDSFile(isTest, persistenceBlock);
					modelFiles.add(modelFile);
				}
			}
		}
		return modelFiles;
	}
	
	protected ModelFile buildDSFile(boolean isTest, Persistence persistenceBlock) throws Exception {
		String fileName = PersistenceUtil.getDataSourceFileName(persistenceBlock);
		ModelFile modelFile = buildDSFile(isTest, fileName, persistenceBlock);
		return modelFile;
	}
	
	protected ModelFile buildDSFile(boolean isTest, String fileName, Persistence persistenceBlock) throws Exception {
		ModelFile modelFile = createResourcesFile(isTest, fileName);
		modelFile.setTextContent(getFileContent(isTest, persistenceBlock));
		return modelFile;
	}
	
	public String getFileContent(boolean isTest, Persistence persistenceBlock) throws Exception {
		Buf buf = new Buf();
		buf.put(buildXmlOpen());
		buf.put(buildXmlContent(persistenceBlock));
		buf.put(buildXmlClose());
		return buf.get();
	}
	
	protected String buildXmlOpen() {
		Buf buf = new Buf();
		buf.putLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buf.putLine("");
		buf.putLine("<datasources>"); 
		return buf.get();
	}

	protected String buildXmlContent(Persistence persistenceBlock) {
		return buildXmlContent_Datasource(persistenceBlock);
	}

	protected String buildXmlContent_Datasource(Persistence persistenceBlock) {
		String hostName = "localhost";
		//String hostName = "dc1-mysql-01.kattare.com";
		int portNumber = 3306;

		String databaseName = PersistenceUtil.getDatabaseName(persistenceBlock);
		String dataSourceName = PersistenceUtil.getDataSourceName(persistenceBlock);
		String blockName = PersistenceUtil.getBlockName(persistenceBlock);
		String driverName = "mysql-5-driver";
		String jndiName = "java:/"+dataSourceName;
		String poolName = blockName + "_pool";
		String connectionUrl = "jdbc:mysql://"+hostName+":"+portNumber+"/"+databaseName;
		String userName = PersistenceUtil.getUserName(persistenceBlock);
		String password = "password";
		
		Buf buf = new Buf();
		/*
		 * TODO Add description comment here
		 */
		buf.putLine("	<datasource"); 
		buf.putLine("		enabled=\"true\""); 
		buf.putLine("		use-java-context=\"true\"");
		buf.putLine("		jndi-name=\""+jndiName+"\"");
		buf.putLine("		pool-name=\""+poolName+"\">"); 
		buf.putLine("");
		buf.putLine("		<driver>"+driverName+"</driver>");
		buf.putLine("");
		buf.putLine("		<connection-url>"+connectionUrl+"</connection-url>");
		buf.putLine("		<security>");
		buf.putLine("			<user-name>"+userName+"</user-name>");
		buf.putLine("			<password>"+password+"</password>");
		buf.putLine("		</security>");
		buf.putLine("");
		//buf.putLine("		<!--");  
		//buf.putLine("		<connection-url>jdbc:mysql://dc1-mysql-01.kattare.com:3306/sgiusadb</connection-url>");
		//buf.putLine("		<security>");
		//buf.putLine("			<user-name>sgiusa</user-name>");
		//buf.putLine("			<password>db-!*usa.1</password>");
		//buf.putLine("		</security>");
		//buf.putLine("		-->");
		//buf.putLine("");
		buf.putLine("		<pool>");
		buf.putLine("			<min-pool-size>2</min-pool-size>");
		buf.putLine("			<max-pool-size>10</max-pool-size>");
		buf.putLine("			<!--");  
		buf.putLine("			<blocking-timeout-millis>5000</blocking-timeout-millis>");
		buf.putLine("			<idle-timeout-minutes>15</idle-timeout-minutes>");
		buf.putLine("			-->");
		buf.putLine("			<prefill>true</prefill>");
		buf.putLine("		</pool>");
		buf.putLine("");
		buf.putLine("		<statement>");
		buf.putLine("			<prepared-statement-cache-size>32</prepared-statement-cache-size>");
		buf.putLine("			<share-prepared-statements />");
		buf.putLine("		</statement>");
		buf.putLine("");
		buf.putLine("		<!-- reduce isolation from the default level (repeatable read) -->");
		buf.putLine("		<transaction-isolation>TRANSACTION_READ_COMMITTED</transaction-isolation>");
		buf.putLine("");
		buf.putLine("		<!-- separate connections used with and without JTA transaction"); 
		buf.putLine("		<no-tx-separate-pools />");
		buf.putLine("		-->");
		buf.putLine("");
		buf.putLine("		<!-- disable transaction interleaving"); 
		buf.putLine("		<track-connection-by-tx />");
		buf.putLine("		-->");
		buf.putLine("");
		buf.putLine("		<!--pooling parameters -->");
		buf.putLine("");
		buf.putLine("		<!-- leverage mysql integration features -->");
		buf.putLine("		<!--");  
		buf.putLine("		<exception-sorter-class-name>com.mysql.jdbc.integration.jboss.ExtendedMysqlExceptionSorter</exception-sorter-class-name>");
		buf.putLine("");
		buf.putLine("		<valid-connection-checker-class-name>com.mysql.jdbc.integration.jboss.MysqlValidConnectionChecker</valid-connection-checker-class-name>");
		buf.putLine("		-->");
		buf.putLine("");
		buf.putLine("		<validation>");
		buf.putLine("			<valid-connection-checker");
		buf.putLine("				class-name=\"org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLValidConnectionChecker\"></valid-connection-checker>");
		buf.putLine("			<exception-sorter");
		buf.putLine("				class-name=\"org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLExceptionSorter\"></exception-sorter>");
		buf.putLine("		</validation>");
		buf.putLine("");
		buf.putLine("		<!-- corresponding type-mapping in conf/standardjbosscmp-jdbc.xml"); 
		buf.putLine("		<metadata>");
		buf.putLine("			<type-mapping>mySQL</type-mapping>");
		buf.putLine("		</metadata>");
		buf.putLine("		-->");
		buf.putLine("	</datasource>");
		return buf.get();
	}
	
	protected String buildXmlClose() {
		Buf buf = new Buf();
		buf.putLine("</datasources>");
		return buf.get();
	}

	
	/*
	<!--  
	<drivers>
		<driver name="mysql-5-driver" module="mysql">
			<xa-datasource-class>com.mysql.jdbc.jdbc2.optional.MysqlXADataSource</xa-datasource-class>
		</driver>
	</drivers>
	-->
	
	<!--  
    <datasource 
        jndi-name="java:/seamspaceDatasource" 
        enabled="true" 
        use-java-context="true" pool-name="seamspacedb">
        <connection-url>jdbc:h2:mem:seamspacedb;DB_CLOSE_DELAY=-1</connection-url>
        <driver>h2</driver>
        <security>
            <user-name>sa</user-name>
            <password>sa</password>
        </security>
    </datasource>
	-->
	*/
	
}
