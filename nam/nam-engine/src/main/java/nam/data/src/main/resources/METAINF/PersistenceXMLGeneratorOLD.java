package nam.data.src.main.resources.METAINF;

import java.util.Iterator;
import java.util.List;

import nam.model.Adapter;
import nam.model.Element;
import nam.model.Properties;
import nam.model.Property;
import nam.model.Unit;
import nam.model.util.ElementUtil;
import nam.model.util.PropertyUtil;
import nam.model.util.TypeUtil;
import aries.codegen.AbstractFileGenerator;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;


public class PersistenceXMLGeneratorOLD extends AbstractFileGenerator {

	private static final int JPA_VERSION_1 = 1;

	private static final int JPA_VERSION_2 = 2;

	private int jpaVersion = 2;

	
	public PersistenceXMLGeneratorOLD(GenerationContext context) {
		this.context = context;
	}

	public void generate(List<Unit> units) throws Exception {
		//context.setUnit(persistenceUnit);
		//setGeneratingTests(generatingTests);
		//setSourceFile("persistence.xml");
		//setSourceFolder("src/main/resources/META-INF");
		//setSourcePath(TEMPLATE_WORKSPACE+"/"+templateHome+"/"+sourceFolder);
		
		setTargetFile("persistence.xml");
		String sourceDirectory = !context.isOptionGenerateTests() ? "main" : "test"; 
		setTargetFolder("src/"+sourceDirectory+"/resources/META-INF");
		setTargetPath(context.getTargetPath()+"/"+targetFolder);

		Buf buf = new Buf();
		buf.put(generatePersistenceXmlOpen());
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			Properties properties = unit.getProperties();

			buf.put(generateUnitOpen(unit));
			buf.put(generateUnitProvider());
			if (!context.isOptionGenerateTests())
				buf.put(generateUnitSource(unit));
			if (PropertyUtil.isEnabled(properties, "generate-mappings"))
				buf.put(generateUnitMappingFile(unit));
			buf.put(generateUnitClasses(unit));
			buf.put(generateUnitAttributes(unit));
			buf.put(generateUnitProperties(unit));
			buf.put(generateUnitClose());
		}
		buf.put(generatePersistenceXmlClose());
		generateFile(buf.get());
	}

//	public void generate(List<ModelPackage> modelPackages, Unit unit) throws Exception {
//		//context.setUnit(persistenceUnit);
//		//setGeneratingTests(generatingTests);
//		//setSourceFile("persistence.xml");
//		//setSourceFolder("src/main/resources/META-INF");
//		//setSourcePath(TEMPLATE_WORKSPACE+"/"+templateHome+"/"+sourceFolder);
//		setTargetFile("persistence.xml");
//		String sourceDirectory = !context.isOptionGenerateTests() ? "main" : "test"; 
//		setTargetFolder("src/"+sourceDirectory+"/resources/META-INF");
//		setTargetPath(context.getTargetPath()+"/"+targetFolder);
//
//		Buf buf = new Buf();
//		buf.put(generatePersistenceXmlOpen());
//		buf.put(generateUnitOpen(unit));
//		buf.put(generateUnitProvider());
//		if (!context.isOptionGenerateTests())
//			buf.put(generateUnitSource(unit));
//		buf.put(generateUnitMappingFile(modelPackages));
//		buf.put(generateUnitClassList(modelPackages));
//		buf.put(generateUnitPropertyList(unit));
//		buf.put(generateUnitClose());
//		buf.put(generatePersistenceXmlClose());
//		generateFile(buf.get());
//	}

	protected String generatePersistenceXmlOpen() {
		Buf buf = new Buf();
		switch (jpaVersion) {
		case JPA_VERSION_1:
			buf.putLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			buf.putLine();
			buf.putLine("<persistence");
			buf.putLine("	version=\"1.0\"");
			buf.putLine("	xmlns=\"http://java.sun.com/xml/ns/persistence\"");
			buf.putLine("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
			buf.putLine("	xsi:schemaLocation=\"http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_1_0.xsd\">");
			break;
		case JPA_VERSION_2:
			buf.putLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			buf.putLine();
			buf.putLine("<persistence");
			buf.putLine("	version=\"2.0\"");
			buf.putLine("	xmlns=\"http://java.sun.com/xml/ns/persistence\"");
			buf.putLine("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
			buf.putLine("	xsi:schemaLocation=\"http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd\">");
			break;
		}
		return buf.get();
	}

	protected String generateUnitOpen(Unit unit) {
		String unitName = unit.getName();
		String transactionType = "RESOURCE_LOCAL";
		if (!context.isOptionGenerateTests() && unit.getTransacted() != null && unit.getTransacted().getLocal() == false) {
			transactionType = "JTA";
		}
		if (context.isOptionGenerateTests()) {
			if (unitName.endsWith("DS"))
				unitName = unitName.replace("DS", "TestDS");
			if (unitName.endsWith("Source"))
				unitName = unitName.replace("Source", "TestSource");
		}
		Buf buf = new Buf();
		buf.putLine();
		buf.putLine1("<persistence-unit name=\""+unitName+"\" transaction-type=\""+transactionType+"\">");
		return buf.get();
	}

	protected String generateUnitProvider() {
		Buf buf = new Buf();
		buf.putLine2("<provider>org.hibernate.ejb.HibernatePersistence</provider>");
		return buf.get();
	}

	protected String generateUnitSource(Unit unit) {
		Buf buf = new Buf();
		String jndiName = unit.getSource().getJndiName();
		buf.putLine2("<jta-data-source>"+jndiName+"</jta-data-source>");
		//buf.putLine2("<jta-data-source>java:comp/env/jdbc/"+jndiName+"</jta-data-source>");
		return buf.get();
	}

	protected String generateUnitMappingFile(Unit unit) {
		Buf buf = new Buf();
		buf.putLine();
		String fileName = "queries.xml";
		
//		List<Namespace> namespaceList = UnitUtil.getNamespaceList(unit);
//		Namespace namespace = namespaceList.get(0);
//		String packageName = NameUtil.getPackageFromNamespace(namespace.getUri());
		
		buf.putLine2("<mapping-file>META-INF/"+fileName+"</mapping-file>");
		
//		Iterator<Namespace> iterator = namespaceList.iterator();
//		while (iterator.hasNext()) {
//			Namespace namespace = iterator.next();
//		}
		return buf.get();
	}

	
	protected String generateUnitClasses(Unit unit) {
		Buf buf = new Buf();
		buf.putLine();
		String lastPackage = null;
		List<Element> elements = ElementUtil.getElements(unit.getElements());
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (!ElementUtil.isTransient(element)) {
				String className = TypeUtil.getClassName(element.getType());
				String packageName = TypeUtil.getPackageName(element.getType());
				if (lastPackage != null && !lastPackage.equals(packageName))
					buf.putLine();
				buf.putLine2("<class>"+packageName+".entity."+className+"Entity</class>");
				lastPackage = packageName;
			}
		}
//		lastPackage = null;
//		List<Class> classes = ElementUtil.getClasses(unit.getElements());
//		Iterator<Class> iterator2 = classes.iterator();
//		while (iterator2.hasNext()) {
//			Class element = iterator2.next();
//			String javaType = element.getName();
//			String packageName = NameUtil.getPackageName(javaType);
//			if (lastPackage != null && !lastPackage.equals(packageName))
//				buf.putLine();
//			buf.putLine2("<class>"+javaType+"</class>");
//			lastPackage = packageName;
//		}
		return buf.get();
	}
	
	protected String generateUnitAttributes(Unit unit) {
		Buf buf = new Buf();
		buf.putLine();
		buf.putLine2("<exclude-unlisted-classes>true</exclude-unlisted-classes>");
		return buf.get();
	}
	
	protected String generateUnitProperties(Unit unit) {
		Buf buf = new Buf();
		buf.putLine();
		buf.putLine2("<properties>");
		Adapter adapter = unit.getAdapter();
		Properties properties = adapter.getProperties();
		Iterator<Property> iterator = properties.getProperties().iterator();
		while (iterator.hasNext()) {
			Property property = iterator.next();
			buf.putLine3("<property name=\""+property.getName()+"\" value=\""+property.getValue()+"\"/>");
		}
		buf.putLine2("</properties>");
		return buf.get();
	}
	
//	protected String generateUnitClassList(List<ModelPackage> modelPackages) {
//		Buf buf = new Buf();
//		Iterator<ModelPackage> iterator = modelPackages.iterator();
//		while (iterator.hasNext()) {
//			ModelPackage modelPackage = iterator.next();
//			buf.put(generateUnitClassList(modelPackage));
//		}
//		buf.putLine();
//		buf.putLine2("<exclude-unlisted-classes>true</exclude-unlisted-classes>");
//		return buf.get();
//	}
//	
//	protected String generateUnitClassList(ModelPackage modelPackage) {
//		Buf buf = new Buf();
//		buf.putLine();
//		List<ModelClass> entities = modelPackage.getClasses();
//		for (ModelClass entity: entities) {
//			String className = entity.getClassName();
//			String packageName = entity.getPackageName();
//			buf.putLine2("<class>"+packageName+"."+className+"</class>");
//		}
//		return buf.get();
//	}

//	protected String generateUnitPropertyList(Unit unit) {
//		Buf buf = new Buf();
//		buf.putLine();
//		buf.putLine2("<properties>");
//		if (!context.isOptionGenerateTests()) {
//			buf.putLine3("<property name=\"hibernate.hbm2ddl.auto\" value=\"create-drop\"/>");
//			//buf.putLine3("<property name=\"hibernate.dialect\" value=\"org.hibernate.dialect.MySQL5Dialect\"/>");
//			buf.putLine3("<property name=\"hibernate.dialect\" value=\"org.hibernate.dialect.MySQLInnoDBDialect\"/>");
//			buf.putLine3("<property name=\"hibernate.cache.use_query_cache\" value=\"true\"/>");
//			buf.putLine3("<property name=\"hibernate.cache.use_second_level_cache\" value=\"true\"/>");
//			buf.putLine3("<property name=\"hibernate.cache.provider_class\" value=\"net.sf.ehcache.hibernate.EhCacheProvider\"/>");
//			buf.putLine3("<property name=\"hibernate.show_sql\" value=\"true\"/>");
//		} else {
//			buf.putLine3("<property name=\"hibernate.hbm2ddl.auto\" value=\"create-drop\"/>");
//			buf.putLine3("<property name=\"hibernate.dialect\" value=\"org.hibernate.dialect.MySQLInnoDBDialect\"/>");
//			buf.putLine3("<property name=\"hibernate.cache.use_query_cache\" value=\"true\"/>");
//			buf.putLine3("<property name=\"hibernate.cache.use_second_level_cache\" value=\"true\"/>");
//			buf.putLine3("<property name=\"hibernate.cache.region.factory_class\" value=\"org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory\"/>");
//			buf.putLine3("<property name=\"hibernate.cache.provider_class\" value=\"org.hibernate.cache.SingletonEhCacheProvider\"/>");
//
//			//test database source parameters 
//			Source dataSource = unit.getTestSource();
//			buf.putLine3("<property name=\"hibernate.connection.url\" value=\""+dataSource.getConnectionUrl()+"\"/>");
//			buf.putLine3("<property name=\"hibernate.connection.driver_class\" value=\""+dataSource.getDriverClass()+"\"/>");
//			buf.putLine3("<property name=\"hibernate.connection.username\" value=\""+dataSource.getUserName()+"\"/>");
//			buf.putLine3("<property name=\"hibernate.connection.password\" value=\""+dataSource.getPassword()+"\"/>");
//			buf.putLine3("<property name=\"hibernate.show_sql\" value=\"true\"/>");
//		}
//		buf.putLine2("</properties>");
//		return buf.get();
//	}

	protected String generateUnitClose() {
		Buf buf = new Buf();
		buf.putLine1("</persistence-unit>");
		return buf.get();
	}
	
	protected String generatePersistenceXmlClose() {
		Buf buf = new Buf();
		buf.putLine("</persistence>");
		return buf.get();
	}

}
