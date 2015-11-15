package nam.data.src.test.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.data.DataLayerHelper;
import nam.model.Attribute;
import nam.model.Element;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Persistence;
import nam.model.Reference;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelReference;


public class MapperFixtureBuilder extends AbstractBeanBuilder {

	public MapperFixtureBuilder(GenerationContext context) {
		super(context);
		initialize();
	}

	protected boolean isMapperRequired(Element element) {
		if (ElementUtil.isTransient(element))
			return false;
		if (ElementUtil.isAbstract(element))
			return false;
		return true;
	}

	protected void initialize() {
	}
	
	public ModelClass buildClass(Persistence persistence) throws Exception {
		this.namespace = context.getNamespaceByUri(persistence.getNamespace());
		String packageName = DataLayerHelper.getMapperFixturePackageName(namespace.getUri());
		String className = DataLayerHelper.getMapperFixtureClassName(namespace);
		String beanName = NameUtil.uncapName(className);

		ModelClass modelClass = new ModelClass();
		modelClass.setType(TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, beanName));
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		modelClass.setParentClassName("BaseFixture");
		modelClass.addImportedClass("org.aries.util.BaseFixture");

//		modelClass.setType(TypeUtil.getTypeFromNamespace(namespace));
//		modelClass.setPackageName(packageName);
//		modelClass.setClassName(className);
//		modelClass.setName(beanName);
//		modelClass.setParentClassName("BaseFixture");
//		modelClass.addImportedClass("org.aries.util.BaseFixture");
		
//		initializeStructures();
//		this.namespaces.add(namespace);
//		this.dummyValueFactory.setNamespace(namespace);
		
		initializeClass(modelClass, persistence);
		return modelClass;
	}
	
//	public ModelClass buildClass(Unit unit) throws Exception {
//		//List<Element> elements = UnitUtil.getFunctionalElementsBasedOnType(unit);
//		List<Element> elements = UnitUtil.getElements(unit);
//		return buildClass(unit.getNamespace(), elements);
//	}

//	public ModelClass buildClass(Namespace namespace) throws Exception {
//		List<Element> elements = NamespaceUtil.getElements(namespace);
//		ElementUtil.sortTypesByQualifiedName(elements);
//		return buildClass(namespace, elements);
//	}

//	public ModelClass buildClass(Namespace namespace, List<Element> elements) throws Exception {
//		String packageName = DataLayerHelper.getMapperFixturePackageName(namespace.getUri());
//		String className = DataLayerHelper.getMapperFixtureClassName(namespace);
//		String beanName = NameUtil.uncapName(className);
//		this.namespace = namespace;
//		
//		ModelClass modelClass = new ModelClass();
//		modelClass.setType(TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, beanName));
//		modelClass.setPackageName(packageName);
//		modelClass.setClassName(className);
//		modelClass.setName(beanName);
//		initializeClass(modelClass, elements);
//		return modelClass;
//	}

	protected Element createContainedElement(Element element, Reference reference) {
		//String containedReferencePackageName = DataLayerHelper.getContainedEntityFieldPackageName(element);
		String containedElementClassName = ModelLayerHelper.getContainedFieldClassName(element, reference);
		String containedElementTypeName = ModelLayerHelper.getContainedFieldTypeName(namespace, element, reference);
		String containedElementBeanName = NameUtil.uncapName(containedElementClassName);
		
		Element newElement = new Element();
		newElement.setName(containedElementBeanName);
		newElement.setType(containedElementTypeName);
		newElement.setStructure("item");
		return newElement;
	}

	protected void initializeClass(ModelClass modelClass, Persistence persistence) throws Exception {
		List<Element> elements = PersistenceUtil.getElements(persistence);
		initializeImportedClasses(modelClass);
		initializeInstanceFields(modelClass, elements);
		initializeInstanceMethods(modelClass, elements);
	}

	protected void initializeImportedClasses(ModelClass modelClass) throws Exception {
		modelClass.addImportedClass("org.aries.util.FieldUtil");
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, List<Element> elements) throws Exception {
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (!isMapperRequired(element))
				continue;
			initializeInstanceFields(modelClass, element);
		}
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Element element) throws Exception {
		modelClass.addInstanceReference(createInstanceField_MapperFixtureBean(element));
		List<Reference> references = ElementUtil.getReferences(element);
		Iterator<Reference> iterator = references.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			if (!reference.getContained())
				continue;
			
			Element newElement = createContainedElement(element, reference);
			modelClass.addInstanceReference(createInstanceField_MapperFixtureBean(newElement));
			//modelClass.addImportedClass(newElement);
		}
	}
	
	protected ModelReference createInstanceField_MapperFixtureBean(Element element) {
		//String mapperPackageName = DataLayerHelper.getMapperPackageName(namespace);
		//String mapperInterfaceName = DataLayerHelper.getMapperInterfaceName(element);
		//String mapperQualifiedName = DataLayerHelper.getMapperQualifiedName(namespace, element);

		String mapperPackageName = DataLayerHelper.getInferredMapperPackageName(namespace, element);
		String mapperClassName = DataLayerHelper.getInferredMapperClassName(namespace, element);
		String mapperTypeName = DataLayerHelper.getInferredMapperTypeName(namespace, element);
		String mapperQualifiedName = mapperPackageName + "." + mapperClassName;
		String mapperBeanName = NameUtil.uncapName(mapperClassName);
		
		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE + Modifier.STATIC);
		modelReference.setPackageName(mapperPackageName);
		modelReference.setClassName(mapperClassName);
		modelReference.setName(mapperBeanName);
		modelReference.setType(mapperTypeName);
		modelReference.setStructure("item");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		modelReference.addImportedClass(mapperQualifiedName);
		return modelReference;
	}
	
	
	protected void initializeInstanceMethods(ModelClass modelClass, List<Element> elements) throws Exception {
		modelClass.addInstanceOperation(createOperation_Clear(modelClass, elements));
		modelClass.addInstanceOperations(createOperations_GetMapperFixture(modelClass, elements));
	}
	
	protected ModelOperation createOperation_Clear(ModelClass modelClass, List<Element> elements) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("clear");
		
		Buf buf = new Buf();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (!isMapperRequired(element))
				continue;
			String elementBeanName = ModelLayerHelper.getElementNameUncapped(element);
			buf.putLine2(elementBeanName+"Mapper = null;");
			
			List<Reference> references = ElementUtil.getReferences(element);
			Iterator<Reference> iterator2 = references.iterator();
			while (iterator2.hasNext()) {
				Reference reference = iterator2.next();
				if (!reference.getContained())
					continue;
				
				String fieldBeanName = elementBeanName + ModelLayerHelper.getElementClassName(reference);
				buf.putLine2(fieldBeanName+"Mapper = null;");
			}
		}
		
		modelOperation.addInitialSource(buf.get());
		//modelOperation.addImportedClass(element);
		return modelOperation;
	}

	protected List<ModelOperation> createOperations_GetMapperFixture(ModelClass modelClass, List<Element> elements) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (!isMapperRequired(element))
				continue;
			modelOperations.addAll(createOperations_GetMapperFixture(modelClass, element));
		}
		return modelOperations;
	}

	protected List<ModelOperation> createOperations_GetMapperFixture(ModelClass modelClass, Element element) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		modelOperations.add(createOperation_GetMapperFixture(modelClass, element));
		List<Reference> references = ElementUtil.getReferences(element);
		Iterator<Reference> iterator = references.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			if (!reference.getContained())
				continue;
			
			Element newElement = createContainedElement(element, reference);
			modelOperations.add(createOperation_GetMapperFixture(modelClass, newElement));
		}
		return modelOperations;
	}
	
	protected ModelOperation createOperation_GetMapperFixture(ModelClass modelClass, Element element) {
		//String className = DataLayerHelper.getMapperClassName(element);
		String className = DataLayerHelper.getInferredMapperClassName(namespace, element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("get"+className);
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setResultType(className);
		//modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_GetMapperFixture(modelClass, element));
		//modelOperation.addImportedClass(element);
		return modelOperation;
	}

	protected String createSourceCode_GetMapperFixture(ModelClass modelClass, Element element) {
		String className = DataLayerHelper.getInferredMapperClassName(this.namespace, element);
		//String interfaceName = DataLayerHelper.getMapperInterfaceName(element);
		//String className = DataLayerHelper.getMapperClassName(element);
		//String beanName = NameUtil.uncapName(interfaceName);
		String beanName = NameUtil.uncapName(className);
		Set<String> mappersSpecified = new HashSet<String>();
		
//		if (mapperName.equals("permissionMapper"))
//			System.out.println();
		
		Buf buf = new Buf();
		buf.putLine2("if ("+beanName+" == null) {");
		buf.putLine2("	"+beanName+" = new "+className+"();");
		
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String fieldType = field.getType();
			if (field instanceof Attribute)
				continue;
			
			Reference reference = (Reference) field;
			if (FieldUtil.isInverse(reference))
				continue;
			
			EntityClassHelper entityClassHelper = new EntityClassHelper(context);
			entityClassHelper.initialize(this.namespace, element, reference);
			Element targetElement = entityClassHelper.getTargetElement();
			
			String fieldPackageName = ModelLayerHelper.getFieldPackageName(field);
			Namespace fieldNamespace = context.getNamespaceByPackageName(fieldPackageName);
			if (fieldNamespace == null)
				continue;
			
			String fieldClassName = ModelLayerHelper.getFieldClassName(field);
			String fieldMapperClassName = fieldClassName + "Mapper";
			String fieldMapperBeanName = NameUtil.uncapName(fieldMapperClassName);

			if (reference.getContained()) {
				fieldMapperClassName = DataLayerHelper.getContainedMapperClassName(element, reference);
				fieldMapperBeanName = NameUtil.uncapName(fieldMapperClassName);
				String namespaceUri = TypeUtil.getNamespace(targetElement.getType());
				fieldNamespace = context.getNamespaceByUri(namespaceUri);
			}
			
			if (fieldMapperBeanName.equals(beanName))
				continue;
			if (mappersSpecified.contains(fieldMapperBeanName))
				continue;
			
			mappersSpecified.add(fieldMapperBeanName);
			String mapperFixturePrefix = "";
			if (!NamespaceUtil.equals(this.namespace, fieldNamespace)) {
				String mapperFixtureClassName = DataLayerHelper.getMapperFixtureClassName(fieldNamespace);
				modelClass.addImportedClass(fieldPackageName + ".util." + mapperFixtureClassName);
				mapperFixturePrefix = mapperFixtureClassName + ".";
			}
			
//			String fieldMapperFixtureBaseName = NameUtil.getLastSegmentFromPackageName(fieldPackageName);
//			String fieldMapperFixtureClassName = NameUtil.capName(fieldMapperFixtureBaseName) + "MapperFixture";
//			String fieldMapperClassName = ModelLayerHelper.getFieldClassName(field) + "Mapper";
			
			//buf.putLine2("	"+beanName+"."+fieldMapperBeanName+" = "+mapperFixtureClassName+".get"+fieldMapperClassName+"();");
			buf.putLine2("	FieldUtil.setFieldValue("+beanName+", \""+fieldMapperBeanName+"\", "+mapperFixturePrefix+"get"+fieldMapperClassName+"());");
		}
		
		buf.putLine2("}");
		buf.putLine2("return "+beanName+";");
		return buf.get();
	}

}
