package nam.data.src.main.resources.METAINF;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.ProjectLevelHelper;
import nam.data.DataLayerHelper;
import nam.model.Adapter;
import nam.model.Element;
import nam.model.Persistence;
import nam.model.Properties;
import nam.model.Property;
import nam.model.Reference;
import nam.model.Unit;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.PropertyUtil;
import nam.model.util.TypeUtil;
import nam.model.util.UnitUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractDataLayerFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class PersistenceXMLBuilder extends AbstractDataLayerFileBuilder {

	private String lastPackage;
	
	private String unitNamespace;
	
	private Set<String> importedNamespaces;

	private List<String> entityClassNames;
	
	
	public PersistenceXMLBuilder(GenerationContext context) {
		super(context);
	}

	protected boolean isExtendsAbstractParent(Element element) {
		//String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		//String concreteEntityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		return context.getParentElementByType(element.getType()) != null;
	}
	
	public ModelFile buildFile(boolean isTest, Persistence persistence) throws Exception {
		ModelFile modelFile = createMainResourcesFile("META-INF", "persistence.xml");
		modelFile.setTextContent(getFileContent(isTest, persistence));
		return modelFile;
	}

	protected String getFileContent(boolean isTest, Persistence persistence) throws Exception {
		context.buildParentElementMap(persistence);
		Buf buf = new Buf();
		buf.put(generatePersistenceXmlOpen());
		Collection<Unit> units = PersistenceUtil.getUnits(persistence);
		Iterator<Unit> iterator = units.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Unit unit = iterator.next();
			if (unit.getRef() != null)
				continue;
			//TODO
			//TODO generate description comment here
			//TODO
			//buf.putLine2("");
			buf.put(getFileContent(isTest, unit));
		}
		buf.put(generatePersistenceXmlClose());
		return buf.get();
	}
	
	protected String getFileContent(boolean isTest, Unit unit) throws Exception {
		Buf buf = new Buf();
		buf.put(generateUnitOpen(unit));
		buf.put(generateUnitProvider());
		if (!context.isOptionGenerateTests())
			buf.put(generateUnitSource(unit));
		Properties properties = unit.getProperties();
		if (PropertyUtil.isEnabled(properties, "generate-mappings"))
			buf.put(generateUnitMappingFile(unit));
		buf.put(generateUnitClasses(unit));
		buf.put(generateUnitAttributes(unit));
		buf.put(generateUnitProperties(unit));
		buf.put(generateUnitClose());
		return buf.get();
	}

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
		String unitName = NameUtil.uncapName(unit.getName());
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
		String jndiName = NameUtil.uncapName(unit.getSource().getJndiName());
		if (!jndiName.startsWith("java:/"))
			jndiName = "java:/" + jndiName;
		buf.putLine2("<jta-data-source>"+jndiName+"</jta-data-source>");
		//buf.putLine2("<jta-data-source>java:comp/env/jdbc/"+jndiName+"</jta-data-source>");
		return buf.get();
	}

	protected String generateUnitMappingFile(Unit unit) {
		Buf buf = new Buf();
		buf.putLine();
		String fileName = getQueriesfileName(unit);
		
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
		Set<String> unitClassSet = new HashSet<String>();
		Buf buf = new Buf();
		buf.putLine();
		
		lastPackage = null;
		unitNamespace = unit.getNamespace().getUri();
		importedNamespaces = UnitUtil.getImportedNamespaces(unit);
		entityClassNames = getEntityClassNameList(unit);
		
		Iterator<String> iterator = entityClassNames.iterator();
		while (iterator.hasNext()) {
			String qualifiedName = iterator.next();
			unitClassSet.add(qualifiedName);
		}
		
		List<String> unitClassList = new ArrayList<String>();
		unitClassList.addAll(unitClassSet);
		Collections.sort(unitClassList);
		iterator = unitClassList.iterator();
		while (iterator.hasNext()) {
			String qualifiedName = iterator.next();
			buf.putLine2("<class>"+qualifiedName+"</class>");
		}
		
//		List<Element> elements = UnitUtil.getAllElements(unit);
//		Iterator<Element> iterator = elements.iterator();
//		while (iterator.hasNext()) {
//			Element element = iterator.next();
//			buf.put(generateUnitClasses(element));
//		}
		
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
	
	protected List<String> getEntityClassNameList(Unit unit) {
		List<String> list = new ArrayList<String>();
		List<Element> elements = UnitUtil.getElements(unit);
		//List<Element> elements = UnitUtil.getAllElements(unit);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (ElementUtil.isTransient(element))
				continue;
			if (!ElementUtil.isRoot(element))
				continue;
			list.addAll(getEntityClassNameList(element));
		}
		return list;
	}

	protected List<String> getEntityClassNameList(Element element) {
		List<String> list = new ArrayList<String>();
		String elementType = element.getType();
		String elementNamespace = TypeUtil.getNamespace(elementType);
		String elementName = NameUtil.uncapName(element.getName());
		
//		if (elementName.equals("bookOrders"))
//			System.out.println();
		
		String packageName = null;
		if (importedNamespaces.contains(elementNamespace))
			packageName = ProjectLevelHelper.getPackageName(unitNamespace);
		else packageName = TypeUtil.getPackageName(elementType);

		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String concreteEntityClassName = DataLayerHelper.getConcreteEntityClassName(element);
//		if (concreteEntityClassName.equals(entityClassName))
//			concreteEntityClassName = entityClassName;

//		if (elementType.endsWith(elementName))
//			list.add(packageName+".entity."+concreteEntityClassName);
//			//concreteEntityClassName = DataLayerHelper.getInferredEntityClassName(unitNamespace, element);
			
		if (isExtendsAbstractParent(element))
			list.add(packageName+".entity.Abstract"+entityClassName);
		list.add(packageName+".entity."+concreteEntityClassName);
		
		List<Reference> references = ElementUtil.getReferences(element);
		Iterator<Reference> iterator = references.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			if (!FieldUtil.isInverse(reference)) {
				Element referenceElement = context.getElementByType(reference.getType());
				if (referenceElement == null)
					continue;
				if (reference.getContained()) {
					if (isExtendsAbstractParent(referenceElement)) {
						entityClassName = DataLayerHelper.getEntityClassName(referenceElement);
						list.add(packageName+".entity.Abstract"+entityClassName);
					}
					concreteEntityClassName = DataLayerHelper.getContainedEntityClassName(element, reference);
					list.add(packageName+".entity."+concreteEntityClassName);
					continue;
				}
				if (!ElementUtil.isSelfReferencing(element, reference)) {
					list.addAll(getEntityClassNameList(referenceElement));
					continue;
				}
			}
		}
		
		Collections.sort(list);
		return list;
	}
	
	
	
	
//	protected String generateUnitClasses(Element element) {
//		Buf buf = new Buf();
//		String elementType = element.getType();
//		String elementNamespace = TypeUtil.getNamespace(elementType);
//		
//		String packageName = null;
//		if (importedNamespaces.contains(elementNamespace))
//			packageName = ProjectLevelHelper.getPackageName(unitNamespace);
//		else packageName = TypeUtil.getPackageName(elementType);
//		if (lastPackage != null && !lastPackage.equals(packageName))
//			buf.putLine();
//
//		String entityClassName = DataLayerHelper.getEntityClassName(element);
//		String concreteEntityClassName = DataLayerHelper.getConcreteEntityClassName(element);
//		if (!concreteEntityClassName.equals(entityClassName))
//			concreteEntityClassName = entityClassName;
//
//		if (isExtendsAbstractParent(element))
//			buf.putLine2("<class>"+packageName+".entity.Abstract"+entityClassName+"</class>");
//		buf.putLine2("<class>"+packageName+".entity."+concreteEntityClassName+"</class>");
//		lastPackage = packageName;
//		
//		List<Field> fields = ElementUtil.getFields(element);
//		Iterator<Field> iterator = fields.iterator();
//		while (iterator.hasNext()) {
//			Field field = iterator.next();
//			Element fieldElement = context.getElementByType(field.getType());
//			if (fieldElement != null)
//				buf.put(generateUnitClasses(fieldElement));
//		}
//		return buf.get();
//	}
	
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
			String name = property.getName();
			String value = property.getValue();

			if (value.contains("${entityManagerFactoryName}"))
				value = value.replace("${entityManagerFactoryName}", getEntityManagerFactoryName(unit));
			
			buf.putLine3("<property name=\""+name+"\" value=\""+value+"\" />");
		}
		buf.putLine2("</properties>");
		return buf.get();
	}
	
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
