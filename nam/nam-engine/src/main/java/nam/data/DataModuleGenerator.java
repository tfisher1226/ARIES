package nam.data;

import java.util.Iterator;
import java.util.List;

import nam.data.src.main.java.DAOBeanGenerator;
import nam.data.src.main.java.EntityBeanGenerator;
import nam.data.src.main.java.ImporterBeanGenerator;
import nam.data.src.main.java.ManagerBeanGenerator;
import nam.data.src.main.java.MapperBeanGenerator;
import nam.data.src.main.java.QueryBeanGenerator;
import nam.data.src.main.java.RepositoryBeanGenerator;
import nam.data.src.main.resources.ComponentsXMLGenerator;
import nam.data.src.main.resources.HibernatePropertiesGenerator;
import nam.data.src.main.resources.ImportsSQLGenerator;
import nam.data.src.main.resources.JndiPropertiesGenerator;
import nam.data.src.main.resources.SeamPropertiesGenerator;
import nam.data.src.main.resources.METAINF.BeansXMLGenerator;
import nam.data.src.main.resources.METAINF.PersistenceXMLGenerator;
import nam.data.src.main.resources.METAINF.QueriesXMLGenerator;
import nam.data.src.test.java.CacheUnitHelperGenerator;
import nam.data.src.test.java.DaoITGenerator;
import nam.data.src.test.java.DaoITHelperGenerator;
import nam.data.src.test.java.DaoITSuiteGenerator;
import nam.data.src.test.java.DataUnitHelperGenerator;
import nam.data.src.test.java.EntityFixtureGenerator;
import nam.data.src.test.java.EntityITGenerator;
import nam.data.src.test.java.MapperFixtureGenerator;
import nam.data.src.test.resources.sql.CleanSQLGenerator;
import nam.data.src.test.resources.sql.PopulateSQLGenerator;
import nam.model.Cache;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Persistence;
import nam.model.Project;
import nam.model.Unit;
import nam.model.util.ModuleUtil;
import nam.model.util.PersistenceUtil;
import aries.codegen.application.AbstractModuleGenerator;
import aries.generation.engine.GenerationContext;


public class DataModuleGenerator extends AbstractModuleGenerator {

	private DataModuleBuilder dataModuleBuilder; 
	private DataProjectGenerator dataProjectGenerator;
	
	//main sources
	private RepositoryBeanGenerator repositoryBeanGenerator;
	private ManagerBeanGenerator managerBeanGenerator;
	private MapperBeanGenerator mapperBeanGenerator;
	private QueryBeanGenerator queryBeanGenerator;
	private ImporterBeanGenerator importerBeanGenerator;
	private DAOBeanGenerator daoBeanGenerator;
	private EntityBeanGenerator entityBeanGenerator;

	//main resources
	private BeansXMLGenerator beansXMLGenerator;
	private ComponentsXMLGenerator componentsXMLGenerator;
	private PersistenceXMLGenerator persistenceXMLGenerator;
	private QueriesXMLGenerator queriesXMLGenerator;
	private ImportsSQLGenerator importsSQLGenerator;
	private JndiPropertiesGenerator jndiPropertiesGenerator;
	private HibernatePropertiesGenerator hibernatePropertiesGenerator;
	private SeamPropertiesGenerator seamPropertiesGenerator;

	//test sources area
	private DaoITGenerator daoITGenerator;
	private DaoITHelperGenerator daoITHelperGenerator;
	private DaoITSuiteGenerator daoITSuiteGenerator;
	private DataUnitHelperGenerator dataUnitHelperGenerator;
	private CacheUnitHelperGenerator cacheUnitHelperGenerator;
	private EntityITGenerator entityITGenerator;
	private EntityFixtureGenerator entityFixtureGenerator;
	private MapperFixtureGenerator mapperFixtureGenerator;

	//test resources area
	private CleanSQLGenerator cleanSQLGenerator;
	private PopulateSQLGenerator populateSQLGenerator;

	
	public DataModuleGenerator(GenerationContext context) {
		dataModuleBuilder = new DataModuleBuilder(context);
		dataProjectGenerator = new DataProjectGenerator(context);

		//main sources
		repositoryBeanGenerator = new RepositoryBeanGenerator(context);
		managerBeanGenerator = new ManagerBeanGenerator(context);
		mapperBeanGenerator = new MapperBeanGenerator(context);
		queryBeanGenerator = new QueryBeanGenerator(context);
		importerBeanGenerator = new ImporterBeanGenerator(context);
		daoBeanGenerator = new DAOBeanGenerator(context);
		entityBeanGenerator = new EntityBeanGenerator(context);

		//main resources
		beansXMLGenerator = new BeansXMLGenerator(context);
		componentsXMLGenerator = new ComponentsXMLGenerator(context);
		persistenceXMLGenerator = new PersistenceXMLGenerator(context);
		queriesXMLGenerator = new QueriesXMLGenerator(context);
		importsSQLGenerator = new ImportsSQLGenerator(context);
		jndiPropertiesGenerator = new JndiPropertiesGenerator(context);
		hibernatePropertiesGenerator = new HibernatePropertiesGenerator(context);
		seamPropertiesGenerator = new SeamPropertiesGenerator(context);
		
		//test sources
		daoITGenerator = new DaoITGenerator(context);
		daoITHelperGenerator = new DaoITHelperGenerator(context);
		daoITSuiteGenerator = new DaoITSuiteGenerator(context);
		dataUnitHelperGenerator = new DataUnitHelperGenerator(context);
		cacheUnitHelperGenerator = new CacheUnitHelperGenerator(context);
		entityITGenerator = new EntityITGenerator(context);
		entityFixtureGenerator = new EntityFixtureGenerator(context);
		mapperFixtureGenerator = new MapperFixtureGenerator(context);

		//test resources
		cleanSQLGenerator = new CleanSQLGenerator(context);
		populateSQLGenerator = new PopulateSQLGenerator(context);
		this.context = context;
	}

	public void initialize(Project project, Module module) throws Exception {
		dataModuleBuilder.initialize(project, module);
	}
	
	public void generate(Project project, Module module) throws Exception {
		if (context.canGenerateData()) {
			System.out.println("\nGenerating DATA-module "+ModuleUtil.getModuleId(module));
			if (context.canGenerate("data", "project"))
				dataProjectGenerator.generate(project, module);
			generateContents(project, module);
		}
	}
	
	protected void generateContents(Project project, Module module) throws Exception {
		//TODO? generateContents(project, module, ProjectUtil.getPersistenceBlocks(project));
		generateContents(project, module, module.getPersistence());
	}
	
	protected void generateContents(Project project, Module module, Persistence persistence) throws Exception {
		context.buildParentElementMap(persistence);
		context.setPersistence(persistence);
		if (persistence != null) {
			if (context.canGenerate("sources")) {
				context.setOptionGenerateTests(false);
				generateMainSources(module, persistence);
				generateMainResources(module, persistence);
			}
			if (context.canGenerate("tests")) {
				context.setOptionGenerateTests(true);
				generateTestSources(module, persistence);
				generateTestResources(module, persistence);
			}
		}
	}

	protected void generateMainSources(Module module, Persistence persistence) throws Exception {
		List<Unit> units = PersistenceUtil.getUnits(persistence);
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			if (unit.getRef() != null)
				continue;
			context.setUnit(unit);
			repositoryBeanGenerator.generateInterface(dataModuleBuilder.buildRepositoryBeanInterfaces(unit));
			repositoryBeanGenerator.generateClass(dataModuleBuilder.buildRepositoryBeanClasses(unit));
			managerBeanGenerator.generateInterfaces(dataModuleBuilder.buildManagerBeanInterfaces(unit));
			managerBeanGenerator.generateClasses(dataModuleBuilder.buildManagerBeanClasses(unit));
			//mapperBeanGenerator.generateClasses(dataModuleBuilder.buildMapperBeanClasses(unit));
			//daoBeanGenerator.generateInterfaces(dataModuleBuilder.buildDaoBeanInterfaces(unit));
			//daoBeanGenerator.generateClasses(dataModuleBuilder.buildDaoBeanClasses(unit));
			importerBeanGenerator.generateClasses(dataModuleBuilder.buildImporterBeanClasses(unit));
		}
		mapperBeanGenerator.generateClasses(dataModuleBuilder.buildMapperBeanClasses(persistence));
		queryBeanGenerator.generateClasses(dataModuleBuilder.buildQueryBeanClasses(persistence));
		daoBeanGenerator.generateInterfaces(dataModuleBuilder.buildDaoBeanInterfaces(persistence));
		daoBeanGenerator.generateClasses(dataModuleBuilder.buildDaoBeanClasses(persistence));
		entityBeanGenerator.generatePackages(dataModuleBuilder.buildEntityBeanPackages(persistence));
	}
	
	protected void generateMainResources(Module module, Persistence persistence) throws Exception {
		beansXMLGenerator.generateFile(dataModuleBuilder.buildBeansXML(persistence));
		componentsXMLGenerator.generateFile(dataModuleBuilder.buildComponentsXML(persistence));
		persistenceXMLGenerator.generateFile(dataModuleBuilder.buildPersistenceXML(persistence));
		queriesXMLGenerator.generateFiles(dataModuleBuilder.buildQueriesXML(persistence));
		importsSQLGenerator.generateFile(dataModuleBuilder.buildImportsSQL(persistence));
		seamPropertiesGenerator.generateFile(dataModuleBuilder.buildSeamProperties(persistence));
		hibernatePropertiesGenerator.generateFile(dataModuleBuilder.buildHibernateProperties(persistence));
	}
	
	protected void generateTestSources(Module module, Persistence persistence) throws Exception {
		List<Unit> units = PersistenceUtil.getUnits(persistence);
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			if (unit.getRef() != null)
				continue;
			context.setUnit(unit);
			daoITGenerator.generateTests(dataModuleBuilder.buildDaoITClasses(unit));
			daoITHelperGenerator.generateTest(dataModuleBuilder.buildDaoITHelperClass(unit));
			daoITSuiteGenerator.generateTest(dataModuleBuilder.buildDaoITSuiteClass(unit));
			dataUnitHelperGenerator.generateTest(dataModuleBuilder.buildDataUnitHelperClass(unit));
			entityITGenerator.generateTests(dataModuleBuilder.buildEntityITClasses(unit));
		}

		List<Cache> cacheUnits = PersistenceUtil.getCaches(persistence);
		Iterator<Cache> iterator2 = cacheUnits.iterator();
		while (iterator2.hasNext()) {
			Cache cacheUnit = iterator2.next();
			if (cacheUnit.getRef() != null)
				continue;
			context.setCache(cacheUnit);
			cacheUnitHelperGenerator.generateTest(dataModuleBuilder.buildCacheUnitHelperClass(cacheUnit));
		}
		
//		List<Namespace> namespaces = PersistenceUtil.getNamespaces(persistence);
//		Iterator<Namespace> iterator2 = namespaces.iterator();
//		while (iterator2.hasNext()) {
//			Namespace namespace = iterator2.next();
//			entityFixtureGenerator.generateTest(dataModuleBuilder.buildEntityFixtureClass(namespace));
//			mapperFixtureGenerator.generateTest(dataModuleBuilder.buildMapperFixtureClass(namespace));
//		}
		
		Namespace namespace = context.getNamespaceByUri(persistence.getNamespace());
		entityFixtureGenerator.generateTest(dataModuleBuilder.buildEntityFixtureClass(persistence));
		mapperFixtureGenerator.generateTest(dataModuleBuilder.buildMapperFixtureClass(persistence));
	}
	
	protected void generateTestResources(Module module, Persistence persistence) throws Exception {
		componentsXMLGenerator.generateFile(dataModuleBuilder.buildTestComponentsXML(persistence));
		jndiPropertiesGenerator.generateFile(dataModuleBuilder.buildTestJndiProperties(persistence));
		hibernatePropertiesGenerator.generateFile(dataModuleBuilder.buildTestHibernateProperties(persistence));
		cleanSQLGenerator.generateFiles(dataModuleBuilder.buildCleanSQL(persistence));
		populateSQLGenerator.generateFiles(dataModuleBuilder.buildPopulateSQL(persistence));
	}

}
