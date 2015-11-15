package nam.data.src.test.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.data.DataLayerHelper;
import nam.model.Element;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Reference;
import nam.model.Unit;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.TypeUtil;
import nam.model.util.UnitUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelReference;


public class DaoITHelperBuilder extends AbstractBeanBuilder {

	public DaoITHelperBuilder(GenerationContext context) {
		super(context);
	}

	protected void initialize() {
	}
	
	public ModelClass buildClass(Unit unit) throws Exception {
		return buildClass(unit, unit.getNamespace());
	}
	
	protected ModelClass buildClass(Unit unit, Namespace namespace) throws Exception {
		String packageName = DataLayerHelper.getDAOPackageName(namespace);
		String className = NameUtil.capName(unit.getName()) + "DaoITHelper";
		String beanName = NameUtil.uncapName(className);

		ModelClass modelClass = new ModelClass();
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		//modelClass.setType(daoType);
		initializeClass(modelClass, unit, namespace);
		return modelClass;
	}

	public void initializeClass(ModelClass modelClass, Unit unit, Namespace namespace) throws Exception {
		//List<Element> elements = UnitUtil.getFunctionalElementsBasedOnName(unit);
		List<Element> elements = UnitUtil.getElements(unit);
		initializeImportedClasses(modelClass, unit, namespace, elements);
		initializeInstanceConstructor(modelClass, unit, namespace, elements);
		initializeInstanceFields(modelClass, unit, namespace, elements);
		initializeInstanceMethods(modelClass, unit, namespace, elements);
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Unit unit, Namespace namespace, List<Element> elements) {
		String mapperFixtureQualifiedName = DataLayerHelper.getMapperFixtureQualifiedName(namespace);
		
		modelClass.addImportedClass("javax.persistence.EntityManager");
		modelClass.addImportedClass(mapperFixtureQualifiedName);
		
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			String elementQualifiedName = ModelLayerHelper.getElementQualifiedName(element);
			String entityQualifiedName = DataLayerHelper.getEntityQualifiedName(namespace, element);
			String modelFixtureQualifiedName = ModelLayerHelper.getModelFixtureQualifiedName(element);
			modelClass.addImportedClass(elementQualifiedName);
			modelClass.addImportedClass(entityQualifiedName);
			modelClass.addImportedClass(modelFixtureQualifiedName);
		}
	}

	protected void initializeInstanceConstructor(ModelClass modelClass, Unit unit, Namespace namespace, List<Element> elements) { 
		modelClass.getInstanceConstructors().add(createInstanceConstructor(modelClass, unit, namespace, elements));
	}

	protected ModelConstructor createInstanceConstructor(ModelClass modelClass, Unit unit, Namespace namespace, List<Element> elements) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		modelConstructor.addParameter(createParameter("EntityManager", "entityManager"));
		modelConstructor.addInitialSource(createSourceCode_Constructor(elements));
		modelClass.addImportedClass("javax.persistence.EntityManager");
		return modelConstructor;
	}
	
	protected String createSourceCode_Constructor(List<Element> elements) {
		Buf buf = new Buf();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
			String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
			buf.putLine2(daoNameUncapped+" = create"+elementNameCapped+"Dao(entityManager);");
		}
		return buf.get();
	}

	protected void initializeInstanceFields(ModelClass modelClass, Unit unit, Namespace namespace, List<Element> elements) throws Exception {
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			initializeInstanceFields(modelClass, namespace, element);
		}
	}

	protected void initializeInstanceFields(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		modelClass.addInstanceReferences(createInstanceField_DAOBean_ForReferences(modelClass, namespace, element));
		modelClass.addInstanceReference(createInstanceField_DAOBean(modelClass, namespace, element));
	}

	protected List<ModelReference> createInstanceField_DAOBean_ForReferences(ModelClass modelClass, Namespace namespace, Element element) {
		List<ModelReference> modelReferences = new ArrayList<ModelReference>();
		Set<Reference> nonContainmentReferences = ElementUtil.getNonContainmentReferences(element);
		Iterator<Reference> iterator = nonContainmentReferences.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			String referenceType = reference.getType();
			String referenceNamespaceUri = TypeUtil.getNamespace(referenceType);
			Namespace referenceNamespace = context.getNamespaceByUri(referenceNamespaceUri);
			Element referencedElement = context.getElementByType(referenceType);
			if (!NamespaceUtil.equals(referenceNamespace, namespace)) {
				modelReferences.add(createInstanceField_DAOBean(modelClass, referenceNamespace, referencedElement));
				String daoQualifiedName = DataLayerHelper.getDAOQualifiedInterfaceName(referenceNamespace, referencedElement);
				modelClass.addImportedClass(daoQualifiedName);
			}
		}
		return modelReferences;
	}
	
	protected ModelReference createInstanceField_DAOBean(ModelClass modelClass, Namespace namespace, Element element) {
		String packageName = DataLayerHelper.getDAOPackageName(namespace);
		String interfaceName = DataLayerHelper.getDAOInterfaceName(element);
		//String className = DataLayerHelper.getDAOClassName(element);
		String qualifiedName = DataLayerHelper.getDAOQualifiedInterfaceName(namespace, element);
		String localPart = DataLayerHelper.getDAONameUncapped(element);
		String typeName = org.aries.util.TypeUtil.getDerivedType(element.getType(), localPart);
		String beanName = NameUtil.uncapName(interfaceName);

		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(interfaceName);
		modelReference.setName(beanName);
		modelReference.setType(typeName);
		modelReference.setStructure("item");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		modelReference.addImportedClass(qualifiedName);
		return modelReference;
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Unit unit, Namespace namespace, List<Element> elements) throws Exception {
		modelClass.addInstanceOperations(createOperations_CreateDao(modelClass, elements));
		modelClass.addInstanceOperations(createOperations_CreateEntity(modelClass, namespace, elements));
		modelClass.addInstanceOperations(createOperations_PersistEntity(modelClass, namespace, elements));
	}
	
//	protected void initializeInstanceMethods(ModelClass modelClass, Namespace namespace, Element element) {
//		Reference inverseReference = ElementUtil.getInverseReference(element);
//		modelClass.addInstanceOperation(createOperation_CreateDao(modelClass, element));
//		if (inverseReference != null)
//			modelClass.addInstanceOperation(createOperation_CreateEntity1(modelClass, element));
//		modelClass.addInstanceOperation(createOperation_CreateEntity2(modelClass, element));
//	}
	
	protected List<ModelOperation> createOperations_CreateDao(ModelClass modelClass, List<Element> elements) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			modelClass.addInstanceOperation(createOperation_CreateDao(modelClass, element));
		}
		return modelOperations;
	}

	protected ModelOperation createOperation_CreateDao(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String resultClassName = DataLayerHelper.getDAOInterfaceName(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+elementNameCapped+"Dao");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(resultClassName);
		//modelOperation.addException("Exception");
		modelOperation.addParameter(createParameter("EntityManager", "entityManager"));
		modelOperation.addInitialSource(createSourceCode_CreateDao(modelClass, element));
		return modelOperation;
	}
	
	protected String createSourceCode_CreateDao(ModelClass modelClass, Element element) {
		//Reference inverseReference = ElementUtil.getInverseReference(element);
		//String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		//String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String daoInterfaceName = DataLayerHelper.getDAOInterfaceName(element);
		String daoClassName = DataLayerHelper.getDAOClassName(element);
		String daoBeanName = NameUtil.uncapName(daoInterfaceName);

		Buf buf = new Buf();
		buf.putLine2(daoClassName+" "+daoBeanName+" = new "+daoClassName+"();");
		buf.putLine2(daoBeanName+".em = entityManager;");
		buf.putLine2("return "+daoBeanName+";");
		return buf.get();
	}

	protected List<ModelOperation> createOperations_CreateEntity(ModelClass modelClass, Namespace namespace, List<Element> elements) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Set<String> elementsProcessSoFar = new HashSet<String>();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			String elementType = element.getType();
			if (!elementsProcessSoFar.contains(elementType)) {
				modelOperations.addAll(createOperations_CreateEntity_ForReferences(modelClass, namespace, element));
				Reference inverseReference = ElementUtil.getInverseReference(element);
				if (inverseReference != null)
					modelOperations.add(createOperation_CreateEntity1(modelClass, namespace, element));
				modelOperations.add(createOperation_CreateEntity2(modelClass, namespace, element));
				elementsProcessSoFar.add(elementType);
			}
		}
		return modelOperations;
	}
	
	protected List<ModelOperation> createOperations_CreateEntity_ForReferences(ModelClass modelClass, Namespace namespace, Element element) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Set<Reference> nonContainmentReferences = ElementUtil.getNonContainmentReferences(element);
		Iterator<Reference> iterator = nonContainmentReferences.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			String referenceType = reference.getType();
			String referenceNamespaceUri = TypeUtil.getNamespace(referenceType);
			Namespace referenceNamespace = context.getNamespaceByUri(referenceNamespaceUri);
			Element referencedElement = context.getElementByType(referenceType);
			if (!NamespaceUtil.equals(referenceNamespace, namespace)) {
				modelOperations.add(createOperation_CreateEntity2(modelClass, referenceNamespace, referencedElement));
				String elementQualifiedName = ModelLayerHelper.getElementQualifiedName(referencedElement);
				String entityQualifiedName = DataLayerHelper.getEntityQualifiedName(referenceNamespace, referencedElement);
				modelClass.addImportedClass(elementQualifiedName);
				modelClass.addImportedClass(entityQualifiedName);
			}
		}
		return modelOperations;
	}
	
	protected ModelOperation createOperation_CreateEntity1(ModelClass modelClass, Namespace namespace, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+entityClassName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(entityClassName);
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_CreateEntity1(modelClass, namespace, element));
		return modelOperation;
	}

	protected String createSourceCode_CreateEntity1(ModelClass modelClass, Namespace namespace, Element element) {
		String entityNameUncapped = DataLayerHelper.getEntityNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		Element container = context.getElementByType(inverseReference.getType());
		String containerClassName = DataLayerHelper.getEntityClassName(container);
		
		Buf buf = new Buf();
		buf.putLine2(entityClassName+" "+entityNameUncapped+" = create"+entityClassName+"(create"+containerClassName+"());");
		buf.putLine2("return "+entityNameUncapped+";");
		return buf.get();
	}
	
	protected ModelOperation createOperation_CreateEntity2(ModelClass modelClass, Namespace namespace, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+entityClassName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(entityClassName);
		modelOperation.addException("Exception");
		if (inverseReference != null) {
			Element container = context.getElementByType(inverseReference.getType());
			String containerName = DataLayerHelper.getEntityNameUncapped(container);
			String containerClassName = DataLayerHelper.getEntityClassName(container);
			Set<Reference> containingReferences = ElementUtil.getContainingReferences(container, element);
			modelOperation.addParameter(createParameter(containerClassName, containerName));
		}
		
		modelOperation.addInitialSource(createSourceCode_CreateEntity2(modelClass, namespace, element));
		return modelOperation;
	}

	protected String createSourceCode_CreateEntity2(ModelClass modelClass, Namespace namespace, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityNameUncapped = DataLayerHelper.getEntityNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);

		String modelFixtureClassName = ModelLayerHelper.getModelFixtureClassName(element);
		String mapperFixtureClassName = DataLayerHelper.getMapperFixtureClassName(namespace);
		modelClass.addImportedClass(ModelLayerHelper.getModelFixtureQualifiedName(namespace));
		modelClass.addImportedClass(DataLayerHelper.getMapperFixtureQualifiedName(namespace));
		
		String containerEntityName = null;
		if (inverseReference != null) {
			Element container = context.getElementByType(inverseReference.getType());
			containerEntityName = DataLayerHelper.getEntityNameUncapped(container);
			//Set<Reference> containingReferences = ElementUtil.getContainingReferences(container, element);
		}
		
		Buf buf = new Buf();
		if (inverseReference != null) {
			buf.putLine2(elementClassName+" "+elementNameUncapped+" = "+modelFixtureClassName+".create"+elementClassName+"(null);");
			buf.putLine2(entityClassName+" "+entityNameUncapped+" = "+mapperFixtureClassName+".get"+elementClassName+"Mapper().toEntity("+containerEntityName+", "+elementNameUncapped+");");
		} else {
			buf.putLine2(elementClassName+" "+elementNameUncapped+" = "+modelFixtureClassName+".create"+elementClassName+"();");
			buf.putLine2(entityClassName+" "+entityNameUncapped+" = "+mapperFixtureClassName+".get"+elementClassName+"Mapper().toEntity("+elementNameUncapped+");");
		}
		
		Set<Reference> nonContainmentReferences = ElementUtil.getNonContainmentReferences(element);
		Iterator<Reference> iterator = nonContainmentReferences.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			String referenceNameCapped = NameUtil.capName(reference.getName());
			Element referencedElement = context.getElementByType(reference.getType());
			String referencedEntityClassName = DataLayerHelper.getEntityClassName(referencedElement);
			buf.putLine2(entityNameUncapped+".set"+referenceNameCapped+"(createAndPersist"+referencedEntityClassName+"());");
		}

		if (inverseReference != null && FieldUtil.isMappedBy(inverseReference)) {
			buf.putLine2(containerEntityName+".set"+elementNameCapped+"("+entityNameUncapped+");");
		}
		
		buf.putLine2("return "+entityNameUncapped+";");
		return buf.get();
	}
	
	protected List<ModelOperation> createOperations_PersistEntity(ModelClass modelClass, Namespace namespace, List<Element> elements) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Set<String> elementsProcessSoFar = new HashSet<String>();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			String elementType = element.getType();
			if (!elementsProcessSoFar.contains(elementType)) {
				modelClass.addInstanceOperations(createOperations_CreateAndPersistEntity_ForReferences(modelClass, namespace, element));
				modelClass.addInstanceOperation(createOperation_CreateAndPersistEntity(modelClass, element));
				modelClass.addInstanceOperations(createOperations_PersistEntity_ForReferences(modelClass, namespace, element));
				modelClass.addInstanceOperation(createOperation_PersistEntity(modelClass, element));
				elementsProcessSoFar.add(elementType);
			}
		}
		return modelOperations;
	}

	protected List<ModelOperation> createOperations_CreateAndPersistEntity_ForReferences(ModelClass modelClass, Namespace namespace, Element element) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Set<Reference> nonContainmentReferences = ElementUtil.getNonContainmentReferences(element);
		Iterator<Reference> iterator = nonContainmentReferences.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			String referenceType = reference.getType();
			String referenceNamespaceUri = TypeUtil.getNamespace(referenceType);
			Namespace referenceNamespace = context.getNamespaceByUri(referenceNamespaceUri);
			Element referencedElement = context.getElementByType(referenceType);
			if (!NamespaceUtil.equals(referenceNamespace, namespace)) {
				modelOperations.add(createOperation_CreateAndPersistEntity(modelClass, referencedElement));
				String entityQualifiedName = DataLayerHelper.getEntityQualifiedName(referenceNamespace, referencedElement);
				modelClass.addImportedClass(entityQualifiedName);
			}
		}
		return modelOperations;
	}
	
	protected ModelOperation createOperation_CreateAndPersistEntity(ModelClass modelClass, Element element) {
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createAndPersist"+entityClassName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(entityClassName);
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_createAndPersistEntity(modelClass, element));
		return modelOperation;	
	}
	
	protected List<ModelOperation> createOperations_PersistEntity_ForReferences(ModelClass modelClass, Namespace namespace, Element element) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Set<Reference> nonContainmentReferences = ElementUtil.getNonContainmentReferences(element);
		Iterator<Reference> iterator = nonContainmentReferences.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			String referenceType = reference.getType();
			String referenceNamespaceUri = TypeUtil.getNamespace(referenceType);
			Namespace referenceNamespace = context.getNamespaceByUri(referenceNamespaceUri);
			Element referencedElement = context.getElementByType(referenceType);
			if (!NamespaceUtil.equals(referenceNamespace, namespace)) {
				modelOperations.add(createOperation_PersistEntity(modelClass, referencedElement));
				String entityQualifiedName = DataLayerHelper.getEntityQualifiedName(referenceNamespace, referencedElement);
				modelClass.addImportedClass(entityQualifiedName);
			}
		}
		return modelOperations;
	}
	
	protected String createSourceCode_createAndPersistEntity(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String entityNameUncapped = DataLayerHelper.getEntityNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2(entityClassName+" "+entityNameUncapped+" = create"+entityClassName+"();");
		buf.putLine2("Long id = persist"+entityClassName+"("+entityNameUncapped+");");
		buf.putLine2(entityNameUncapped+".setId(id);");
		buf.putLine2("return "+entityNameUncapped+";");
		return buf.get();
	}

	protected ModelOperation createOperation_PersistEntity(ModelClass modelClass, Element element) {
		String entityNameUncapped = DataLayerHelper.getEntityNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("persist"+entityClassName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("Long");
		modelOperation.addException("Exception");
		modelOperation.addParameter(createParameter(entityClassName, entityNameUncapped));
		modelOperation.addInitialSource(createSourceCode_PersistEntity(modelClass, element));
		return modelOperation;	
	}
	
	protected String createSourceCode_PersistEntity(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String entityNameUncapped = DataLayerHelper.getEntityNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("Long id = "+daoNameUncapped+".add"+elementNameCapped+"("+entityNameUncapped+");");
		buf.putLine2("return id;");
		return buf.get();
	}

}
