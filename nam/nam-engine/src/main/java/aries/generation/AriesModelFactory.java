package aries.generation;

import nam.model.Application;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.util.ModuleFactory;
import aries.generation.engine.GenerationContext;


public class AriesModelFactory {

	private GenerationContext context;
	
//	private AriesModelParser parser;
	
	
	public AriesModelFactory(GenerationContext context) {
		this.context = context;
	}

//	public void setParser(AriesModelParser parser) {
//		this.parser = parser;
//	}
	
	public Application createApplication() {
		Application application = new Application();
		application.setName(context.getProjectNameCapped());
		application.setGroupId(context.getProjectGroupId());
		application.setArtifactId(context.getProjectName());
		return application;
	}
	
	public Module buildModelModule() {
		return buildModule(ModuleType.MODEL);
	}

	public Module buildClientModule() {
		return buildModule(ModuleType.CLIENT);
	}

	public Module buildServiceModule() {
		return buildModule(ModuleType.SERVICE);
	}

	public Module buildDataModule() {
		return buildModule(ModuleType.DATA);
	}

	public Module buildViewModule() {
		return buildModule(ModuleType.VIEW);
	}

	public Module buildModule(ModuleType moduleType) {
		Module module = ModuleFactory.createModule(moduleType);
		module.setGroupId(context.getProjectGroupId());
		module.setArtifactId(ModuleFactory.getDefaultArtifactId(context.getProject(), moduleType));
		//module.getDependencies().addAll(context.getModuleTemplateDependencies(moduleType));
		return module;
	}
	
//	public PersistenceUnit buildPersistenceUnit() {
//		PersistenceUnit persistenceUnit = new PersistenceUnit();
//		persistenceUnit.setName(getPersistenceUnitName());
//		persistenceUnit.setTransactionType(TransactionType.JTA);
//		//persistenceUnit.setDataSource(buildDataSource());
//		return persistenceUnit;
//	}

//	public DataSource buildDataSource(DataSourceVendor vendor) {
//		DataSource dataSource = new DataSource();
//		dataSource.setName(getPersistenceUnitName());
//		dataSource.setJndiName(getPersistenceUnitName());
//		dataSource.setUserName(getDatasourceUsername());
//		dataSource.setPassword(getDatasourcePassword());
//		dataSource.setDriverClass(getDatabaseDriverClass(vendor));
//		dataSource.setConnectionUrl(getDatabaseConnectionUrl(vendor));
//		dataSource.setConnectionUrl("jdbc:mysql://localhost:3306/"+getDatabaseName());
//		dataSource.setMaxActive(10);
//		dataSource.setMaxIdle(10);
//		dataSource.setMaxWait(10);
//		dataSource.setTransacted(true);
//		dataSource.setVendor(vendor);
//		return dataSource;
//	}

//	protected String getPersistenceUnitName() {
//		return context.isOptionGenerateTests() ? context.getProjectNameCapped()+"TestDS" : context.getProjectNameCapped()+"DS";
//	}
//
//	protected String getDatabaseName() {
//		return context.isOptionGenerateTests() ? "testDB" : context.getProjectName()+"DB";
//	}
//
//	protected String getDatasourceUsername() {
//		return context.isOptionGenerateTests() ? "test" : "manager";
//	}
//
//	protected String getDatasourcePassword() {
//		return context.isOptionGenerateTests() ? "test" : "manager";
//	}
//
//	protected String getDatabaseConnectionUrl(DataSourceVendor vendor) {
//		switch (vendor) {
//		case MYSQL: return "jdbc:mysql://localhost:3306/"+getDatabaseName();
//		case HSQL: return "jdbc:hsqldb:hsql://localhost:${port}/"+getDatabaseName();
//		default: return null;
//		}
//	}
//
//	protected String getDatabaseDriverClass(DataSourceVendor vendor) {
//		switch (vendor) {
//		case MYSQL: return "com.mysql.jdbc.Driver";
//		case HSQL: return "org.hsqldb.jdbcDriver";
//		default: return null;
//		}
//	}
	

}
