package nam.data.src.main.resources.METAINF;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.model.Source;
import nam.model.Transacted;
import nam.model.Unit;
import nam.model.Vendor;
import aries.codegen.AbstractFileBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelPackage;
import aries.generation.model.data.ModelEntity;


public class PersistenceXMLBuilderOLD extends AbstractFileBuilder {

	public PersistenceXMLBuilderOLD(GenerationContext context) {
		this.context = context;
	}

	protected String getUnitName() {
		return context.getProjectNameCapped()+"DS";
	}

	protected String getDatabaseName() {
		return context.getProjectName()+"DB";
	}

	protected String getDatasourceUsername() {
		return "manager";
	}

	protected String getDatasourcePassword() {
		return "manager";
	}

	protected String getTestUnitName() {
		return context.getProjectNameCapped()+"TestDS";
	}

	protected String getTestDatabaseName() {
		return "testDB";
	}

	protected String getTestDatasourceUsername() {
		return "test";
	}

	protected String getTestDatasourcePassword() {
		return "test";
	}

//	protected String getUnitName() {
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

	protected String getDatabaseConnectionUrl(Vendor vendor) {
		switch (vendor) {
		case MYSQL: return "jdbc:mysql://localhost:3306/"+getDatabaseName();
		case HSQL: return "jdbc:hsqldb:hsql://localhost:${port}/"+getDatabaseName();
		default: return null;
		}
	}

	protected String getDatabaseDriverClass(Vendor vendor) {
		switch (vendor) {
		case MYSQL: return "com.mysql.jdbc.Driver";
		case HSQL: return "org.hsqldb.jdbcDriver";
		default: return null;
		}
	}
	

	
	/*
	 * Unit creation
	 * ------------------------
	 */
	
	public Unit buildUnit() {
		Unit persistenceUnit = buildUnit(null);
		return persistenceUnit;
	}
	
	public Unit buildUnit(List<ModelPackage> modelPackages) {
		Unit persistenceUnit = new Unit();
		persistenceUnit.setName(context.getProjectNameCapped());
		
		Transacted transacted = new Transacted();
		transacted.setLocal(false);
		persistenceUnit.setTransacted(transacted);

		//persistenceUnit.setSource(buildSource());
		//persistenceUnit.setTestSource(buildTestSource());
		//persistenceUnit.setEntities(createEntityList(modelPackages));
		//persistenceUnit.setQueries(queryList);
		//persistenceUnit.setProperties(properties);
		//persistenceUnit.setSchemas(schemas);
		//Iterator<ModelPackage> iterator = modelPackages.iterator();
		//while (iterator.hasNext()) {
		//	ModelPackage modelPackage = iterator.next();
		//	String packageName = modelPackage.getName();
		//	persistenceUnit.getPackages().add(packageName);
		//}
		return persistenceUnit;
	}

//	public ModelUnit buildUnitOLD(List<Namespace> namespaces) {
//		ModelUnit persistenceUnit = new ModelUnit();
//		persistenceUnit.setName(context.getProjectNameCapped()+"TestDS");
//		persistenceUnit.setTransactionType("JTA");
//		persistenceUnit.setSource(createSource());
//		persistenceUnit.setEntities(createEntityList(namespaces));
//		//persistenceUnit.setQueries(queryList);
//		//persistenceUnit.setProperties(properties);
//		//persistenceUnit.setSchemas(schemas);
//		Iterator<Namespace> iterator = namespaces.iterator();
//		while (iterator.hasNext()) {
//			Namespace namespace = iterator.next();
//			String packageName = namespace.getName();
//			persistenceUnit.getPackages().add(packageName);
//		}
//		return persistenceUnit;
//	}

	
	/*
	 * Source creation
	 * -------------------
	 */
	
	public Source buildSource(Vendor vendor) {
		Source dataSource = new Source();
		String persistenceUnitName = context.getProperty("persistenceUnitName");
		String datasourceUsername = context.getProperty("dataSourceUsername");
		String datasourcePassword = context.getProperty("dataSourcePassword");
		String databaseDriverClass = context.getProperty("databaseDriverClass");
		String databaseConnectionUrl = context.getProperty("databaseConnectionUrl");
		if (persistenceUnitName == null)
			persistenceUnitName = getUnitName();
		if (datasourceUsername == null)
			datasourceUsername = getDatasourceUsername();
		if (datasourcePassword == null)
			datasourcePassword = getDatasourcePassword();
		if (databaseDriverClass == null)
			databaseDriverClass = getDatabaseDriverClass(vendor);
		if (databaseConnectionUrl == null)
			databaseConnectionUrl = getDatabaseConnectionUrl(vendor);
		dataSource.setName(persistenceUnitName);
		dataSource.setJndiName(persistenceUnitName);
		dataSource.getJndiContext().setUserName(getDatasourceUsername());
		dataSource.getJndiContext().setPassword(getDatasourcePassword());
		dataSource.setDriverClass(databaseDriverClass);
		dataSource.getJndiContext().setConnectionUrl(databaseConnectionUrl);
		dataSource.setMaxActive(10);
		dataSource.setMaxIdle(10);
		dataSource.setMaxWait(10);
		dataSource.setTransacted(true);
		dataSource.setType(vendor.name());
		return dataSource;
	}
	
	public Source buildTestSource(Vendor vendor) {
		Source dataSource = new Source();
		dataSource.setName(getTestUnitName());
		dataSource.setJndiName(getTestUnitName());
		dataSource.setDriverClass("com.mysql.jdbc.Driver");
		dataSource.getJndiContext().setUserName(getTestDatasourceUsername());
		dataSource.getJndiContext().setPassword(getTestDatasourcePassword());
		dataSource.getJndiContext().setConnectionUrl("jdbc:mysql://localhost:3306/"+getTestDatabaseName());
		dataSource.setMaxActive(10);
		dataSource.setMaxIdle(10);
		dataSource.setMaxWait(10);
		dataSource.setTransacted(true);
		dataSource.setType(vendor.name());
		return dataSource;
	}

	
	/*
	 * EntityBean creation
	 * -------------------
	 */

	protected List<ModelEntity> createEntityList(List<ModelPackage> modelPackages) {
		List<ModelEntity> entityList = new ArrayList<ModelEntity>();
		Iterator<ModelPackage> iterator = modelPackages.iterator();
		while (iterator.hasNext()) {
			ModelPackage modelPackage = iterator.next();
			List<ModelEntity> list = createEntityList(modelPackage);
			entityList.addAll(list);
		}
		return entityList;
	}

	protected List<ModelEntity> createEntityList(ModelPackage modelPackage) {
		List<ModelEntity> entityList = new ArrayList<ModelEntity>();
		Iterator<ModelClass> iterator = modelPackage.getClasses().iterator();
		while (iterator.hasNext()) {
			ModelClass modelClass = iterator.next();
			ModelEntity modelEntity = buildEntity(modelClass);
			entityList.add(modelEntity);
		}
		return entityList;
	}

	protected ModelEntity buildEntity(ModelClass modelClass) {
		ModelEntity modelEntity = new ModelEntity();
		modelEntity.setClassName(modelClass.getClassName());
		modelEntity.setPackageName(modelClass.getPackageName());
		modelEntity.setIdKey(context.getIdKey(modelClass));
		return modelEntity;
	}


//	protected Entity createEntity(Element element) {
//		Entity entity = new Entity();
//		String type = element.getType();
//		String packageName = NameUtil.getPackageName(type);
//		String className = NameUtil.getClassName(type);
//		entity.setType(packageName+".entity."+className+"Entity");
//		//context.getIdKey(null);
//		return entity;
//	}

}
