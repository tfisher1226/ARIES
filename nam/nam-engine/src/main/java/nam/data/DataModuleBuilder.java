package nam.data;

import java.util.Collection;
import java.util.List;

import nam.data.src.main.java.DAOBeanBuilder;
import nam.data.src.main.java.EntityBeanBuilder;
import nam.data.src.main.java.ImporterBeanBuilder;
import nam.data.src.main.java.ManagerBeanBuilder;
import nam.data.src.main.java.MapperBeanBuilder;
import nam.data.src.main.java.QueryBeanBuilder;
import nam.data.src.main.java.RepositoryBeanBuilder;
import nam.data.src.main.resources.ComponentsXMLBuilder;
import nam.data.src.main.resources.HibernatePropertiesBuilder;
import nam.data.src.main.resources.ImportsSQLBuilder;
import nam.data.src.main.resources.JndiPropertiesBuilder;
import nam.data.src.main.resources.SeamPropertiesBuilder;
import nam.data.src.main.resources.METAINF.BeansXMLBuilder;
import nam.data.src.main.resources.METAINF.PersistenceXMLBuilder;
import nam.data.src.main.resources.METAINF.QueriesXMLBuilder;
import nam.data.src.test.java.CacheUnitHelperBuilder;
import nam.data.src.test.java.DaoITBuilder;
import nam.data.src.test.java.DaoITHelperBuilder;
import nam.data.src.test.java.DaoITSuiteBuilder;
import nam.data.src.test.java.DataUnitHelperBuilder;
import nam.data.src.test.java.EntityFixtureBuilder;
import nam.data.src.test.java.EntityITBuilder;
import nam.data.src.test.java.MapperFixtureBuilder;
import nam.data.src.test.resources.sql.CleanSQLBuilder;
import nam.data.src.test.resources.sql.PopulateSQLBuilder;
import nam.model.Cache;
import nam.model.Module;
import nam.model.Persistence;
import nam.model.Project;
import nam.model.Unit;
import aries.codegen.application.AbstractModuleBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelFile;
import aries.generation.model.ModelInterface;
import aries.generation.model.ModelPackage;


public class DataModuleBuilder extends AbstractModuleBuilder {

	private DataModuleHelper dataModuleHelper;

	//main sources
	private RepositoryBeanBuilder repositoryBeanBuilder;
	private ManagerBeanBuilder managerBeanBuilder;
	private MapperBeanBuilder mapperBeanBuilder;
	private QueryBeanBuilder queryBeanBuilder;
	private ImporterBeanBuilder importerBeanBuilder;
	private DAOBeanBuilder daoBeanBuilder;
	private EntityBeanBuilder entityBeanBuilder;

	//main resources
	private BeansXMLBuilder beansXMLBuilder;
	private ComponentsXMLBuilder componentsXMLBuilder;
	private PersistenceXMLBuilder persistenceXMLBuilder;
	private QueriesXMLBuilder queriesXMLBuilder;
	private ImportsSQLBuilder importsSQLBuilder;
	private JndiPropertiesBuilder jndiPropertiesBuilder;
	private HibernatePropertiesBuilder hibernatePropertiesBuilder;
	private SeamPropertiesBuilder seamPropertiesBuilder;

	//test sources
	private DaoITBuilder daoITBuilder;
	private DaoITHelperBuilder daoITHelperBuilder;
	private DaoITSuiteBuilder daoITSuiteBuilder;
	private CacheUnitHelperBuilder cacheUnitHelperBuilder;
	private DataUnitHelperBuilder dataUnitHelperBuilder;
	private EntityITBuilder entityITBuilder;
	private EntityFixtureBuilder entityFixtureBuilder;
	private MapperFixtureBuilder mapperFixtureBuilder;
	
	//test resources
	private CleanSQLBuilder cleanSQLBuilder;
	private PopulateSQLBuilder populateSQLBuilder;

	
	public DataModuleBuilder(GenerationContext context) {
		dataModuleHelper = new DataModuleHelper(context);

		//main sources
		repositoryBeanBuilder = new RepositoryBeanBuilder(context);
		managerBeanBuilder = new ManagerBeanBuilder(context);
		mapperBeanBuilder = new MapperBeanBuilder(context);
		queryBeanBuilder = new QueryBeanBuilder(context);
		importerBeanBuilder = new ImporterBeanBuilder(context);
		daoBeanBuilder = new DAOBeanBuilder(context);
		entityBeanBuilder = new EntityBeanBuilder(context);
		
		//main resources
		beansXMLBuilder = new BeansXMLBuilder(context);
		componentsXMLBuilder = new ComponentsXMLBuilder(context);
		persistenceXMLBuilder = new PersistenceXMLBuilder(context);
		queriesXMLBuilder = new QueriesXMLBuilder(context);
		importsSQLBuilder = new ImportsSQLBuilder(context);
		jndiPropertiesBuilder = new JndiPropertiesBuilder(context);
		hibernatePropertiesBuilder = new HibernatePropertiesBuilder(context);
		seamPropertiesBuilder = new SeamPropertiesBuilder(context);
		
		//test sources
		daoITBuilder = new DaoITBuilder(context);
		daoITHelperBuilder = new DaoITHelperBuilder(context);
		daoITSuiteBuilder = new DaoITSuiteBuilder(context);
		dataUnitHelperBuilder = new DataUnitHelperBuilder(context);
		cacheUnitHelperBuilder = new CacheUnitHelperBuilder(context);
		entityITBuilder = new EntityITBuilder(context);
		entityFixtureBuilder = new EntityFixtureBuilder(context);
		mapperFixtureBuilder = new MapperFixtureBuilder(context);
		
		//test resources
		cleanSQLBuilder = new CleanSQLBuilder(context);
		populateSQLBuilder = new PopulateSQLBuilder(context);
		this.context = context;
	}
	
//	public void initialize(Project project) throws Exception {
//		//ModuleFactory moduleFactory = new ModuleFactory();
//		//Module module = moduleFactory.createModule(ModuleType.DATA);
//		List<Module> modules = ProjectUtil.getDataModules(project);
//		Iterator<Module> iterator = modules.iterator();
//		while (iterator.hasNext()) {
//			Module module = iterator.next();
//			initialize(project, module);
//		}
//	}
	
	public void initialize(Project project, Module module) throws Exception {
		dataModuleHelper.initialize(project, module);
		context.initialize(project, module);
	}

//	public Unit buildUnit() throws Exception {
//		Unit persistenceUnit = persistenceXMLBuilder.buildUnit();
//		return persistenceUnit;
//	}
//
//	public Unit buildUnit(List<ModelPackage> modelPackages) throws Exception {
//		Unit persistenceUnit = persistenceXMLBuilder.buildUnit(modelPackages);
//		return persistenceUnit;
//	}
//
//	public Source buildSource(Vendor vendor) throws Exception {
//		Source dataSource = persistenceXMLBuilder.buildSource(vendor);
//		return dataSource;
//	}
//
//	public Source buildTestSource(Vendor vendor) throws Exception {
//		Source testSource = persistenceXMLBuilder.buildTestSource(vendor);
//		return testSource;
//	}
//
//	public List<ModelPackage> buildModelPackages(Project project) throws Exception {
//		List<Namespace> namespaces = ProjectUtil.getNamespaces(project);
//		List<ModelPackage> modelPackages = buildModelPackages(namespaces);
//		return modelPackages;
//	}
	
	
	/*
	 * Main sources
	 * ------------
	 */

	public ModelInterface buildRepositoryBeanInterfaces(Unit unit) throws Exception {
		ModelInterface modelInterface = repositoryBeanBuilder.buildInterface(unit);
		return modelInterface;
	}

	public ModelClass buildRepositoryBeanClasses(Unit unit) throws Exception {
		ModelClass modelClass = repositoryBeanBuilder.buildClass(unit);
		return modelClass;
	}

	public List<ModelInterface> buildManagerBeanInterfaces(Unit unit) throws Exception {
		List<ModelInterface> modelInterface = managerBeanBuilder.buildInterfaces(unit);
		return modelInterface;
	}

	public Collection<ModelClass> buildManagerBeanClasses(Unit unit) throws Exception {
		Collection<ModelClass> modelClasses = managerBeanBuilder.buildClasses(unit);
		return modelClasses;
	}

//	public List<ModelClass> buildMapperBeanClasses(Unit unit) throws Exception {
//		List<ModelClass> modelClasses = mapperBeanBuilder.buildClasses(unit);
//		return modelClasses;
//	}

	public Collection<ModelClass> buildImporterBeanClasses(Unit unit) throws Exception {
		Collection<ModelClass> modelClasses = importerBeanBuilder.buildClasses(unit);
		return modelClasses;
	}

//	public List<ModelInterface> buildDaoBeanInterfaces(Unit unit) throws Exception {
//		List<ModelInterface> modelInterfaces = daoBeanBuilder.buildInterfaces(unit);
//		return modelInterfaces;
//	}
//
//	public List<ModelClass> buildDaoBeanClasses(Unit unit) throws Exception {
//		List<ModelClass> modelClasses = daoBeanBuilder.buildClasses(unit);
//		return modelClasses;
//	}
	
	public Collection<ModelClass> buildMapperBeanClasses(Persistence persistence) throws Exception {
		Collection<ModelClass> modelClasses = mapperBeanBuilder.buildClasses(persistence);
		return modelClasses;
	}
	
	public Collection<ModelClass> buildQueryBeanClasses(Persistence persistence) throws Exception {
		Collection<ModelClass> modelClasses = queryBeanBuilder.buildClasses(persistence);
		return modelClasses;
	}
	
	public List<ModelInterface> buildDaoBeanInterfaces(Persistence persistence) throws Exception {
		List<ModelInterface> modelInterfaces = daoBeanBuilder.buildInterfaces(persistence);
		return modelInterfaces;
	}

	public List<ModelClass> buildDaoBeanClasses(Persistence persistence) throws Exception {
		List<ModelClass> modelClasses = daoBeanBuilder.buildClasses(persistence);
		return modelClasses;
	}
	
	public List<ModelPackage> buildEntityBeanPackages(Persistence persistence) throws Exception {
		List<ModelPackage> modelPackages = entityBeanBuilder.buildPackages(persistence);
		return modelPackages;
	}
	

	/*
	 * Main resources
	 * --------------
	 */

	public ModelFile buildBeansXML(Persistence persistence) throws Exception {
		ModelFile modelFile = beansXMLBuilder.buildFile();
		return modelFile;
	}

	public ModelFile buildComponentsXML(Persistence persistence) throws Exception {
		ModelFile modelFile = componentsXMLBuilder.buildFile(persistence);
		return modelFile;
	}

	public ModelFile buildPersistenceXML(Persistence persistence) throws Exception {
		ModelFile modelFile = persistenceXMLBuilder.buildFile(persistence);
		return modelFile;
	}

	public List<ModelFile> buildQueriesXML(Persistence persistence) throws Exception {
		List<ModelFile> modelFiles = queriesXMLBuilder.buildFiles(persistence);
		return modelFiles;
	}

	public ModelFile buildImportsSQL(Persistence persistence) throws Exception {
		ModelFile modelFile = importsSQLBuilder.buildFile(persistence);
		return modelFile;
	}

	public ModelFile buildJndiProperties(Persistence persistence) throws Exception {
		ModelFile modelFile = jndiPropertiesBuilder.buildFile(persistence);
		return modelFile;
	}

	public ModelFile buildHibernateProperties(Persistence persistence) throws Exception {
		ModelFile modelFile = hibernatePropertiesBuilder.buildFile(persistence);
		return modelFile;
	}
	
	public ModelFile buildSeamProperties(Persistence persistence) throws Exception {
		ModelFile modelFile = seamPropertiesBuilder.buildFile(persistence);
		return modelFile;
	}

	
	/*
	 * Test sources
	 * ------------
	 */
	
	public List<ModelClass> buildDaoITClasses(Unit unit) throws Exception {
		List<ModelClass> modelClasses = daoITBuilder.buildClasses(unit);
		return modelClasses;
	}
	
	public ModelClass buildDaoITHelperClass(Unit unit) throws Exception {
		ModelClass modelClasses = daoITHelperBuilder.buildClass(unit);
		return modelClasses;
	}
	
	public ModelClass buildDaoITSuiteClass(Unit unit) throws Exception {
		ModelClass modelClasses = daoITSuiteBuilder.buildClass(unit);
		return modelClasses;
	}
	
	public ModelClass buildDataUnitHelperClass(Unit unit) throws Exception {
		ModelClass modelClasses = dataUnitHelperBuilder.buildClass(unit);
		return modelClasses;
	}
	
	public ModelClass buildCacheUnitHelperClass(Cache cache) throws Exception {
		ModelClass modelClasses = cacheUnitHelperBuilder.buildClass(cache);
		return modelClasses;
	}
	
	public List<ModelClass> buildEntityITClasses(Unit unit) throws Exception {
		List<ModelClass> modelClasses = entityITBuilder.buildClasses(unit);
		return modelClasses;
	}
	
	public ModelClass buildEntityFixtureClass(Persistence persistence) throws Exception {
		ModelClass modelClass = entityFixtureBuilder.buildClass(persistence);
		return modelClass;
	}
	
	public ModelClass buildMapperFixtureClass(Persistence persistence) throws Exception {
		ModelClass modelClass = mapperFixtureBuilder.buildClass(persistence);
		return modelClass;
	}
	
	
	/*
	 * Test resources
	 * --------------
	 */
	
	public ModelFile buildTestJndiProperties(Persistence persistence) throws Exception {
		ModelFile modelFile = jndiPropertiesBuilder.buildFile(true, persistence);
		return modelFile;
	}
	
	public ModelFile buildTestComponentsXML(Persistence persistence) throws Exception {
		ModelFile modelFile = componentsXMLBuilder.buildFile(true, persistence);
		return modelFile;
	}
	
	public ModelFile buildTestHibernateProperties(Persistence persistence) throws Exception {
		ModelFile modelFile = hibernatePropertiesBuilder.buildFile(true, persistence);
		return modelFile;
	}

	
	/*
	 * Test resources ddl
	 * ------------------
	 */
	
	public List<ModelFile> buildCleanSQL(Persistence persistence) throws Exception {
		List<ModelFile> modelFiles = cleanSQLBuilder.buildFiles(persistence);
		return modelFiles;
	}

	public List<ModelFile> buildPopulateSQL(Persistence persistence) throws Exception {
		List<ModelFile> modelFiles = populateSQLBuilder.buildFiles(persistence);
		return modelFiles;
	}

	
//	public List<ModelPackage> buildModelPackages(List<Namespace> namespaces) throws Exception {
//		List<ModelPackage> modelPackages = new ArrayList<ModelPackage>();
//		Iterator<Namespace> iterator = namespaces.iterator();
//		while (iterator.hasNext()) {
//			Namespace namespace = iterator.next();
//			if (namespace.getImported() == null || namespace.getImported() == false) {
//				ModelPackage modelPackage = buildModelPackage(namespace);
//				modelPackages.add(modelPackage);
//			}
//		}
//		return modelPackages;
//	}
//	
//	public ModelPackage buildModelPackage(Namespace namespace) throws Exception {
//		ModelPackage modelPackage = buildModelPackage(NamespaceUtil.getElements(namespace));
//		modelPackage.setName(NameUtil.getPackageFromNamespace(namespace.getUri()));
//		return modelPackage;
//	}

}
