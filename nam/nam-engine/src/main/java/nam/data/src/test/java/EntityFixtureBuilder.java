package nam.data.src.test.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nam.data.DataLayerHelper;
import nam.model.Attribute;
import nam.model.Element;
import nam.model.Enumeration;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Persistence;
import nam.model.Reference;
import nam.model.Unit;
import nam.model.src.main.java.DummyValueFactory;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.TypeUtil;
import nam.service.src.test.java.FixtureUtil;

import org.aries.util.NameUtil;
import org.eclipse.jdt.core.dom.Modifier;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


public class EntityFixtureBuilder extends AbstractBeanBuilder {

	private ModelClass modelClass;
	
	private List<Namespace> namespaces;
	
	protected Map<String, Reference> externalReferences;
	
	private DummyValueFactory dummyValueFactory;
	
	private Long randomId;
	
	
	public EntityFixtureBuilder(GenerationContext context) {
		super(context);
	}

	protected boolean isFixtureRequired(Element element) {
		if (ElementUtil.isTransient(element))
			return false;
		if (ElementUtil.isAbstract(element))
			return false;
		String elementName = element.getName();
		if (elementName.endsWith("Exception"))
			return false;
//		if (context.getElementByType(element.getType()+"Key") != null)
//			return false;
//		if (context.getElementByType(element.getType()+"Criteria") != null)
//			return false;
		return true;
	}

	protected boolean isFixtureRequired(Enumeration enumeration) {
		return true;
	}

	protected void initializeStructures() {
		namespaces = new ArrayList<Namespace>();
		dummyValueFactory = new DummyValueFactory(context);
		dummyValueFactory.setUseForEntityFixtures(true);
		externalReferences = new HashMap<String, Reference>();
	}
	
//	public ModelClass buildClass(Persistence persistence) throws Exception {
//		return buildClass(unit.getNamespace());
//	}
//	
//	public ModelClass buildClass(Unit unit) throws Exception {
//		return buildClass(unit.getNamespace());
//	}
	
//	public List<ModelClass> buildClasses(List<Namespace> namespaces) throws Exception {
//		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
//		Iterator<Namespace> iterator = namespaces.iterator();
//		Module module = context.getModule();
//		while (iterator.hasNext()) {
//			Namespace namespace = iterator.next();
//			if (namespace.getImported() == null || !namespace.getImported() || namespace.getUri().equals(module.getNamespace())) {
//				ModelClass modelClass = buildClass(namespace);
//				modelClasses.add(modelClass);
//			}
//		}
//		return modelClasses;
//	}
	
	public ModelClass buildClass(Persistence persistence) throws Exception {
		this.namespace = context.getNamespaceByUri(persistence.getNamespace());
		String packageName = DataLayerHelper.getEntityFixturePackageName(namespace);
		String className = DataLayerHelper.getEntityFixtureClassName(namespace);
		String beanName = NameUtil.uncapName(className);
		
		modelClass = new ModelClass();
		modelClass.setType(TypeUtil.getTypeFromNamespace(namespace));
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		modelClass.setParentClassName("BaseFixture");
		modelClass.addImportedClass("org.aries.util.BaseFixture");
		
		initializeStructures();
		this.namespaces.add(namespace);
		this.dummyValueFactory.setNamespace(namespace);
		initializeClass(modelClass, persistence);
		return modelClass;
	}
	

	protected Element getKeyElement(Element element) {
		String keyTypeName = getKeyElementTypeName(element);
		Element keyElement = context.getElementByType(keyTypeName);
		return keyElement;
	}

	protected String getKeyElementTypeName(Element element) {
		String typeName = ModelLayerHelper.getElementKeyTypeName(namespace, element);
		return typeName;
	}

	protected Element getCriteriaElement(Element element) {
		String criteriaTypeName = getCriteriaElementTypeName(element);
		Element criteriaElement = context.getElementByType(criteriaTypeName);
		return criteriaElement;
	}
	
	protected String getCriteriaElementTypeName(Element element) {
		String typeName = ModelLayerHelper.getElementCriteriaTypeName(namespace, element);
		return typeName;
	}
	
	
	public void initializeClass(ModelClass modelClass, Persistence persistence) throws Exception {
		List<Element> elements = PersistenceUtil.getElements(persistence);
		//List<Element> elements = NamespaceUtil.getElements(namespace);
		initializeImportedClasses(modelClass, elements);
		//initializeInstanceFields(modelClass, elements);
		buildEntityFixturesFromElements(modelClass, elements);
		buildEntityFixturesFromFields(modelClass, elements);
		buildOperationsForExternalReferences(modelClass, externalReferences);
		initializeImportedClasses(modelClass);
	}

	public void initializeImportedClasses(ModelClass modelClass, List<Element> elements) {
		modelClass.addImportedClass("java.util.Collection");
		modelClass.addImportedClass("org.aries.Assert");
	}

	//NOTUSED
	public void initializeInstanceFields(ModelClass modelClass, List<Element> elements) {
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (!isFixtureRequired(element))
				continue;
			randomId = 10L;
			
			createAttribute_ElementCounter(modelClass, element);
			Element keyElement = getKeyElement(element);
			Element criteriaElement = getCriteriaElement(element);
			if (keyElement != null)
				createAttribute_ElementCounter(modelClass, keyElement);
			if (criteriaElement != null)
				createAttribute_ElementCounter(modelClass, criteriaElement);
		}
	}

	//NOTUSED
	protected void createAttribute_ElementCounter(ModelClass modelClass, Element element) {
		String entityNameUncapped = DataLayerHelper.getEntityNameUncapped(element);
		ModelAttribute modelAttribute = new ModelAttribute();
		modelAttribute.setModifiers(Modifier.PRIVATE + Modifier.STATIC);
		modelAttribute.setName(entityNameUncapped+"Count");
		modelAttribute.setClassName("long");
		modelAttribute.setDefault(0L);
		modelAttribute.setGenerateGetter(false);
		modelAttribute.setGenerateSetter(false);
		modelClass.addStaticAttribute(modelAttribute);
	}

	public void buildEntityFixturesFromElements(ModelClass modelClass, List<Element> elements) throws Exception {
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (!isFixtureRequired(element))
				continue;
			randomId = 10L;
			
			buildEntityFixtures(modelClass, element);
			Element keyElement = getKeyElement(element);
			Element criteriaElement = getCriteriaElement(element);
			if (keyElement != null)
				buildEntityFixtures(modelClass, keyElement);
			if (criteriaElement != null)
				buildEntityFixtures(modelClass, criteriaElement);
		}
	}

	protected void buildEntityFixturesFromFields(ModelClass modelClass, List<Element> elements) throws Exception {
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (!isFixtureRequired(element))
				continue;
			buildEntityFixturesFromFields(modelClass, element);
		}
	}
	
	// Do this for non-contained as well as contained references.
	protected void buildEntityFixturesFromFields(ModelClass modelClass, Element element) throws Exception {
		List<Reference> references = ElementUtil.getReferences(element);
		Iterator<Reference> iterator = references.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			//Skip "inverse" field relationships
			if (reference.getInverse())
				continue;
			// Skip self-referencing field relationships
			if (element.getType().equals(reference.getType()))
				continue;
			buildEntityFixturesFromFields(modelClass, element, reference);
		}
	}
	
	/**
	 * Fabricates a new element to handle contained fields.
	 * This is done to satisfy handling of contained fields (i.e. where containment is true)
	 * so as to be stored in separate database tables.
	 * TODO move the creation of this to somewhere centralized..
	 */
	protected void buildEntityFixturesFromFields(ModelClass modelClass, Element element, Reference reference) throws Exception {
//		String typeName = ModelLayerHelper.getInferredElementTypeName(namespace, element);
//		String localPart = TypeUtil.getLocalPart(typeName);
//		String beanName = NameUtil.uncapName(localPart);
//
//		if (reference.getContained()) {
//			typeName = ModelLayerHelper.getContainedFieldTypeName(namespace, element, reference);
//			localPart = TypeUtil.getLocalPart(typeName);
//			beanName = NameUtil.uncapName(localPart);
//		}
//		
//		Element newElement = new Element();
//		newElement.setName(beanName);
//		newElement.setType(typeName);
//		newElement.setStructure("item");
//		
//		modelClass.addImportedClass(newElement);
		buildEntityFixtures(modelClass, element, reference);
	}

	protected void buildEntityFixtures(ModelClass modelClass, Element element) throws Exception {
		buildEntityFixtures(modelClass, element, null);
	}
	
	protected void buildEntityFixtures(ModelClass modelClass, Element element, Reference reference) throws Exception {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		
		if (!ElementUtil.isMessageElement(element)) {
			createFixtureRecordFactory(modelClass, element, reference, false, false);
			createFixtureRecordFactory(modelClass, element, reference, true, true);
			createFixtureRecordFactory(modelClass, element, reference, true, false);
		} else {
			createMessageRecordFactory(modelClass, element, false, false, false);
			createMessageRecordFactory(modelClass, element, true, false, false);
			createMessageRecordFactory(modelClass, element, true, false, true);
			createMessageRecordFactory(modelClass, element, true, true, false);
			createMessageRecordFactory(modelClass, element, true, true, true);
		}
			
		createFixtureCollectionFactory(modelClass, element, reference, false, false, false, false);
		createFixtureCollectionFactory(modelClass, element, reference, false, true, false, false);
		createFixtureCollectionFactory(modelClass, element, reference, false, true, false, true);
		createFixtureCollectionFactory(modelClass, element, reference, false, true, true, true);
		createFixtureCollectionFactory(modelClass, element, reference, true, false, false, false);
		createFixtureCollectionFactory(modelClass, element, reference, true, true, false, false);
		createFixtureCollectionFactory(modelClass, element, reference, true, true, false, true);
		createFixtureCollectionFactory(modelClass, element, reference, true, true, true, true);
		createFixtureMapFactory(modelClass, element, false, false, false);
		createFixtureMapFactory(modelClass, element, true, true, false);
		createFixtureMapFactory(modelClass, element, true, true, true);

//		if (element.getName().equalsIgnoreCase("Payment"))
//			System.out.println();
		
		Element keyElement = getKeyElement(element);
		if (keyElement != null) {
			createFixtureRecordFactory(modelClass, keyElement, null, false, false);
			createFixtureRecordFactory(modelClass, keyElement, null, true, true);
			createFixtureRecordFactory(modelClass, keyElement, null, true, false);
			createFixtureCollectionFactory(modelClass, keyElement, null, false, false, false, false);
			createFixtureCollectionFactory(modelClass, keyElement, null, false, true, false, false);
			createFixtureCollectionFactory(modelClass, keyElement, null, false, true, false, true);
			createFixtureCollectionFactory(modelClass, keyElement, null, false, true, true, true);
		}
		
		createOperation_assertElementExists(modelClass, namespace, element, reference); 
		createOperation_assertElementCorrect(modelClass, namespace, element, reference); 
		createOperation_assertElementCorrect2(modelClass, namespace, element, reference); 
		
		if (!ElementUtil.isCriteriaElement(targetElement)) {
			createOperation_assertElementsSame(modelClass, namespace, targetElement); 
			
			if (ElementUtil.isUserDefined(targetElement)) {
				createOperation_assertElementsSame2(modelClass, namespace, targetElement); 
				createOperation_assertElementsSame3(modelClass, namespace, targetElement);
			}
			
			createOperation_assertElementsSame4(modelClass, namespace, targetElement); 
			createOperation_assertElementsSame5(modelClass, namespace, targetElement);

			if (ElementUtil.isUserDefined(targetElement)) {
				createOperation_assertElementsSame6(modelClass, namespace, targetElement); 
				createOperation_assertElementsSame7(modelClass, namespace, targetElement); 
			}

			createOperation_assertElementsSame8(modelClass, namespace, targetElement);
			
			if (CodeUtil.hasKeyElement(targetElement)) {
				createOperation_assertElementsSame9(modelClass, namespace, targetElement); 
				createOperation_assertElementsSame10(modelClass, namespace, targetElement); 
			}
		}
	}
	
	protected void createFixtureRecordFactory(ModelClass modelClass, Element element, Reference reference, boolean doPopulate, boolean addIndex) throws Exception {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityPackageName = entityClassHelper.getEntityPackageName();
		String entityClassName = entityClassHelper.getEntityClassName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		
//		String elementClassName = ModelLayerHelper.getElementClassName(element);
//		if (elementClassName.startsWith("Abstract"))
//			System.out.println();
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		String methodCall = doPopulate ? "create_" : "createEmpty_";
		modelOperation.setName(methodCall + entityClassName);
		modelOperation.setResultType(entityClassName);
		
//		if (entityClassName.equals("EmailBoxEntity"))
//			System.out.println();

		if (doPopulate) {
			if (parentEntityClassName != null)
				modelOperation.addParameter(createParameter(parentEntityClassName, parentEntityBeanName));

//			Element container = getContainingElement(element);
//			if (container != null) {
//				String containerName = NameUtil.uncapName(container.getName());
//				String containerClassName = DataLayerHelper.getEntityClassName(container);
//				ModelParameter parameter = CodeUtil.createParameter(containerClassName, containerName);
//				modelOperation.addParameter(parameter);	
//			}
		
			if (addIndex == false) {
				modelOperation.addParameter(CodeUtil.createParameter("long", "index"));
			}
		}
		
//		if (entityClassName.equals("PersonNameEntity"))
//			System.out.println();

		modelOperation.addInitialSource(createFixtureRecordFactory_Source(element, reference, doPopulate, addIndex));
		String entityQualifiedName = entityPackageName + "." + entityClassName;
		
		modelClass.addImportedClass(entityQualifiedName);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected String createFixtureRecordFactory_Source(Element element, Reference reference, boolean doPopulate, boolean addIndex) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		
//		if (entityClassName.equals("EmailBoxEntity"))
//			System.out.println();
//		if (element.getName().equalsIgnoreCase("EmailBox"))
//			System.out.println();
//		if (element.getName().equalsIgnoreCase("EmailBox") && reference != null && reference.getName().equals("emailAccount"))
//			System.out.println();

		Buf buf = new Buf();
		if (addIndex) {
			if (parentEntityClassName != null) 
				buf.putLine2(entityClassName+" "+entityBeanName+" = create_"+entityClassName+"("+parentEntityBeanName+", 1);");
				//buf.putLine2(entityClassName+" "+entityBeanName+" = create"+entityClassName+"("+parentEntityBeanName+", "+beanName+"Count++);");
			else buf.putLine2(entityClassName+" "+entityBeanName+" = create_"+entityClassName+"(1);");
			//else buf.putLine2(entityClassName+" "+entityBeanName+" = create"+entityClassName+"("+beanName+"Count++);");
			buf.putLine2("return "+entityBeanName+";");
			return buf.get();
		}
		String methodCall = doPopulate ? "createEmpty_" : "new ";
		buf.putLine2(entityClassName+" "+entityBeanName+" = "+methodCall + entityClassName+"();");

		if (doPopulate) {
			if (parentEntityClassName != null) {
				Element containingElement = getContainingElement(targetElement);
				if (containingElement == null)
					System.out.println();
				String elementNameCapped = ModelLayerHelper.getElementNameCapped(containingElement);
				buf.putLine2(entityBeanName+".set"+elementNameCapped+"("+parentEntityBeanName+");");
			}
			
			List<Attribute> targetAttributes = ElementUtil.getAttributes(targetElement);
			List<Reference> targetReferences = ElementUtil.getReferences(targetElement);
			buf.put(dummyValueFactory.createSampleValuesForAttributes(targetElement, targetAttributes));
			buf.put(dummyValueFactory.createSampleValuesForReferences(targetElement, targetReferences));
			addImportedClassesForAttributes(modelClass, targetElement, targetAttributes);
			addImportedClassesForReferences(modelClass, targetElement, targetReferences);
		}

		if (doPopulate) {
			if (ElementUtil.isUserDefined(element) && !ElementUtil.isTransient(element) && ElementUtil.hasIdField(element)) {
				//Long randomId = IdGenerator.createRequestId();
				buf.putLine2(entityBeanName+".setId("+randomId+++"L * index);");
			}
		}
		
		buf.putLine2("return "+entityBeanName+";");
		return buf.get();
	}

	protected void createMessageRecordFactory(ModelClass modelClass, Element element, boolean doPopulate, boolean addActionTypes, boolean addIndex) throws Exception {
		String packageName = DataLayerHelper.getInferredEntityPackageName(this.namespace, element);
		String className = DataLayerHelper.getInferredEntityClassName(this.namespace, element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		String methodCall = doPopulate ? "create_" : "createEmpty_";
		modelOperation.setName(methodCall + className);
		modelOperation.setResultType(className);
		
		if (doPopulate) {
			Element container = getContainingElement(element);
			if (container != null) {
				String containerClassName = DataLayerHelper.getEntityClassName(container);
				String containerName = NameUtil.uncapName(containerClassName);
				ModelParameter parameter = CodeUtil.createParameter(containerClassName, containerName);
				modelOperation.addParameter(parameter);	
			}
		
			if (addActionTypes) {
				modelOperation.addParameter(CodeUtil.createParameter("boolean", "cancel"));
				modelOperation.addParameter(CodeUtil.createParameter("boolean", "undo"));
			}
			
			if (addIndex) {
				modelOperation.addParameter(CodeUtil.createParameter("long", "index"));
			}
		}
		
		modelOperation.addInitialSource(createMessageRecordFactory_Source(element, doPopulate, addActionTypes, addIndex));
		String qualifiedName = packageName + "." + className;
		modelClass.addImportedClass(qualifiedName);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected String createMessageRecordFactory_Source(Element element, boolean doPopulate, boolean addActionTypes, boolean addIndex) {
		String className = DataLayerHelper.getInferredEntityClassName(this.namespace, element);
		String beanName =  DataLayerHelper.getInferredEntityBeanName(this.namespace, element);

//		if (type.getName().equalsIgnoreCase("OrderRequestMessage"))
//			System.out.println();
		
		boolean contained = false;
		String containerName = null;
		
		Buf buf = new Buf();
		if (doPopulate) {
			Element container = getContainingElement(element);
			if (container != null) {
				contained = true;
				containerName = NameUtil.uncapName(container.getName());
			}
		}

		if (doPopulate && !addIndex && !addActionTypes) {
			if (contained) 
				buf.putLine2(className+" "+beanName+" = create_"+className+"("+containerName+", false, false);");
			else buf.putLine2(className+" "+beanName+" = create_"+className+"(false, false);");
			buf.putLine2("return "+beanName+";");
			return buf.get();
		}
		
		if (addIndex && !addActionTypes) {
			if (contained) 
				buf.putLine2(className+" "+beanName+" = create_"+className+"("+containerName+", false, false, index);");
			else buf.putLine2(className+" "+beanName+" = create_"+className+"(false, false, index);");
			buf.putLine2("return "+beanName+";");
			return buf.get();
		}
		
		if (doPopulate && addActionTypes && !addIndex) {
			if (contained) 
				buf.putLine2(className+" "+beanName+" = create_"+className+"("+containerName+", cancel, undo, 0);");
			else buf.putLine2(className+" "+beanName+" = create_"+className+"(cancel, undo, 0);");
			buf.putLine2("return "+beanName+";");
			return buf.get();
		}
		
		String methodCall = "create_";
		if (doPopulate && addActionTypes)
			methodCall = "createEmpty_";
		else if (!doPopulate && !addActionTypes)
			methodCall = "new ";

		buf.putLine2(className+" "+beanName+" = "+methodCall+className+"();");

		if (doPopulate && addActionTypes && addIndex) {
			//buf.putLine2("String ext = index == 0 ? \"\" : \"\"+index;");
			buf.put(dummyValueFactory.createSampleValuesForAttributes(element, ElementUtil.getAttributes(element)));
			buf.put(dummyValueFactory.createSampleValuesForReferences(element, ElementUtil.getReferences(element)));
			addImportedClassesForAttributes(modelClass, element, ElementUtil.getAttributes(element));
			addImportedClassesForReferences(modelClass, element, ElementUtil.getReferences(element));
			buf.putLine2(beanName+".setCancelRequest(cancel);");
			buf.putLine2(beanName+".setUndoRequest(undo);");

			if (ElementUtil.isUserDefined(element) && !ElementUtil.isTransient(element) && ElementUtil.hasIdField(element)) {
				//Long randomId = IdGenerator.createRequestId();
				buf.putLine2(beanName+".setId("+randomId+++"L * index);");
			}
		}
		
		buf.putLine2("return "+beanName+";");
		return buf.get();
	}

	protected void createFixtureCollectionFactory(ModelClass modelClass, Element element, Reference reference, boolean isSet, boolean doPopulate, boolean addIndex, boolean addSize) throws Exception {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityPackageName = entityClassHelper.getEntityPackageName();
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		String structure = isSet ? "Set" : "List";
		
//		String packageName = DataLayerHelper.getInferredEntityPackageName(this.namespace, element);
//		String className = DataLayerHelper.getInferredEntityClassName(this.namespace, element);
//		String beanName =  DataLayerHelper.getInferredEntityBeanName(this.namespace, element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		String methodCall = doPopulate ? "create" : "createEmpty";
		//modelOperation.setName(methodCall+entityClassName+"_"+structure);
		modelOperation.setName(methodCall + structure + "_" + entityClassName);
		modelOperation.setResultType(structure+"<"+entityClassName+">");
		
//		boolean contained = false;
//		String containerName = null;
		
//		if (className.equals("Preferences"))
//			System.out.println();
		
		if (doPopulate) {
			if (parentEntityClassName != null) {
				modelOperation.addParameter(createParameter(parentEntityClassName, parentEntityBeanName));
			}
		}
		if (addIndex) {
			ModelParameter parameter = CodeUtil.createParameter("long", "index");
			modelOperation.addParameter(parameter);	
		}
		
		if (addSize) {
			ModelParameter parameter = CodeUtil.createParameter("int", "size");
			modelOperation.addParameter(parameter);	
			if (addIndex) {
				String source = createFixtureCollectionFactory_Source(element, reference, isSet, doPopulate);
				modelOperation.addInitialSource(source);
			} else {
				Buf buf = new Buf();
				if (parentEntityClassName != null)
					buf.putLine2("return create"+structure+"_"+entityClassName+"("+parentEntityBeanName+", 1, size);");
					//buf.putLine2("return create"+entityClassName+structure+"("+parentEntityBeanName+", "+beanName+"Count++, size);");
				else buf.putLine2("return create"+structure+"_"+entityClassName+"(1, size);");
				//else buf.putLine2("return create"+entityClassName+structure+"("+beanName+"Count++, size);");
				modelOperation.addInitialSource(buf.get());
			}
			
		} else if (doPopulate) {
			Buf buf = new Buf();
			if (parentEntityClassName != null)
				buf.putLine2("return "+modelOperation.getName()+"("+parentEntityBeanName+", 2);");
			else buf.putLine2("return "+modelOperation.getName()+"(2);");
			modelOperation.addInitialSource(buf.get());
			
		} else {
			Buf buf = new Buf();
			if (isSet)
				buf.putLine2("return new HashSet<"+entityClassName+">();");
			else buf.putLine2("return new ArrayList<"+entityClassName+">();");
			modelOperation.addInitialSource(buf.get());
		}

		modelClass.addImportedClass("java.util.Date");
		modelClass.addImportedClass("java.util.List");
		modelClass.addImportedClass("java.util.Set");
		modelClass.addImportedClass("java.util.ArrayList");
		modelClass.addImportedClass("java.util.HashSet");
		modelClass.addImportedClass("java.util.Iterator");
		
		String entityQualifiedName = entityPackageName + "." + entityClassName;
		modelClass.addImportedClass(entityQualifiedName);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected String createFixtureCollectionFactory_Source(Element element, Reference reference, boolean isSet, boolean doPopulate) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		//String entityPackageName = entityClassHelper.getEntityPackageName();
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		String structure = isSet ? "Set" : "List";
		
		Buf buf = new Buf();
		if (doPopulate) {
			if (isSet) {
				String invocation = doPopulate ? "createEmptySet_"+entityClassName+"()" : "new HashSet<"+entityClassName+">()";
				buf.putLine2("Set<"+entityClassName+"> "+entityBeanName+"Set = "+invocation+";");
			} else {
				String invocation = doPopulate ? "createEmptyList_"+entityClassName+"()" : "new ArrayList<"+entityClassName+">()";
				buf.putLine2("List<"+entityClassName+"> "+entityBeanName+"List = "+invocation+";");
			}
			
			buf.putLine2("long limit = index + size;");
			buf.putLine2("for (; index < limit; index++)");
			
			if (parentEntityClassName != null)
				buf.putLine2("	"+entityBeanName+structure+".add(create_"+entityClassName+"("+parentEntityBeanName+", index));");
			else buf.putLine2("	"+entityBeanName+structure+".add(create_"+entityClassName+"(index));");
			
			//buf.putLine2("Iterator<"+className+"> iterator = "+beanName+"List.iterator();");
			//buf.putLine2("	"+beanName+"List.add(create"+className+"(index));");
			//buf.putLine2("	"+className+" "+beanName+" = ("+className+") iterator.next();");
			//buf.put(createSampleValuesForReferences(beanName, element.getReferences(), 1));
			//buf.put(createSampleValuesForAttributes(beanName, element.getAttributes(), 1));
			//buf.putLine2("}");

		} else {
			if (isSet)
				buf.putLine2("Set<"+entityClassName+"> "+entityBeanName+"Set = new HashSet<"+entityClassName+">();");
			else buf.putLine2("List<"+entityClassName+"> "+entityBeanName+"List = new ArrayList<"+entityClassName+">();");
		}

		buf.putLine2("return "+entityBeanName+structure+";");
		return buf.get();
	}
	
	protected void createFixtureMapFactory(ModelClass modelClass, Element element, boolean populate, boolean addIndex, boolean addSize) throws Exception {
		Element keyElement = getKeyElement(element);
		if (keyElement == null)
			return;
		
		String packageName = DataLayerHelper.getInferredEntityPackageName(this.namespace, element);
		String className = DataLayerHelper.getInferredEntityClassName(this.namespace, element);
		String keyElementType = keyElement.getType();
		String keyPackageName = TypeUtil.getPackageName(keyElementType);
		String keyClassName = TypeUtil.getClassName(keyElementType);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		String methodCall = populate ? "create" : "createEmpty";
		modelOperation.setName(methodCall + "Map_" + className);
		modelOperation.setResultType("Map<"+keyClassName+", "+className+">");
		
		boolean contained = false;
		String containerName = null;
		
		if (populate) {
			Element container = getContainingElement(element);
			if (container != null) {
				contained = true;
				String containerClassName = DataLayerHelper.getEntityClassName(container);
				containerName = NameUtil.uncapName(containerClassName);
				ModelParameter parameter = CodeUtil.createParameter(containerClassName, containerName);
				modelOperation.addParameter(parameter);	
			}
		}
		if (addSize) {
			ModelParameter parameter = CodeUtil.createParameter("int", "size");
			modelOperation.addParameter(parameter);	
			String source = createFixtureMapFactory_Source(className, keyClassName, element, populate);
			modelOperation.addInitialSource(source);
		} else if (populate) {
			Buf buf = new Buf();
			if (contained)
				buf.putLine2("return "+modelOperation.getName()+"("+containerName+", 2);");
			else buf.putLine2("return "+modelOperation.getName()+"(2);");
			modelOperation.addInitialSource(buf.get());
		} else {
			Buf buf = new Buf();
			buf.putLine2("return new HashMap<"+keyClassName+", "+className+">();");
			modelOperation.addInitialSource(buf.get());
		}
		
		String qualifiedName = packageName + "." + className;
		String keyQualifiedName = keyPackageName + "." + keyClassName;

		modelClass.addInstanceOperation(modelOperation);
		modelClass.addImportedClass("java.util.Date");
		modelClass.addImportedClass("java.util.Map");
		modelClass.addImportedClass("java.util.HashMap");
		modelClass.addImportedClass("java.util.Iterator");
		modelClass.addImportedClass(qualifiedName);
		modelClass.addImportedClass(keyQualifiedName);
	}

	protected String createFixtureMapFactory_Source(String className, String keyClassName, Element element, boolean populate) {
		String beanName =  DataLayerHelper.getInferredEntityBeanName(this.namespace, element);
		
		Buf buf = new Buf();
		if (populate) {
			String invocation = populate ? "createEmptyMap_"+className+"()" : "new HashMap<"+keyClassName+", "+className+">()";
			buf.putLine2("Map<"+keyClassName+", "+className+"> "+beanName+"Map = "+invocation+";");

			boolean contained = false;
			buf.putLine2("for (long index=0; index < size; index++)");
			
			Element container = getContainingElement(element);
			if (container != null) {
				contained = true;
				String containerName = NameUtil.uncapName(container.getName());
				buf.putLine2("	"+beanName+"Map.put(create_"+keyClassName+"(index), create_"+className+"(index));");
			}
			
			if (!contained) {
				buf.putLine2("	"+beanName+"Map.put(create_"+keyClassName+"(index), create_"+className+"(index));");
			}

		} else {
			buf.putLine2("Map<"+keyClassName+", "+className+"> "+beanName+"Map = new HashMap<"+keyClassName+", "+className+">();");
		}

		buf.putLine2("return "+beanName+"Map;");
		return buf.get();
	}
	


//	protected void createFixtureRecordFactory_Default(ModelClass modelClass, Element element) {
//		String elementType = element.getType();
//		String packageName = TypeUtil.getPackageName(elementType);
//		String className = TypeUtil.getClassName(elementType);
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
//		modelOperation.setName("createEmpty"+className);
//		modelOperation.setResultType(className);
//		modelClass.addInstanceOperation(modelOperation);
//		modelClass.addImportedClass(packageName + "." + className);
//		modelOperation.addInitialSource(createEmptyFactoryMethodSource(className));
//	}
//
//	protected void createFixtureRecordFactory_Populated(ModelClass modelClass, Type type) throws Exception {
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.setName("create"+NameUtil.getSimpleName(type.getType()));
//		modelOperation.setResultType(NameUtil.getSimpleName(type.getType()));
//		modelClass.addInstanceOperation(modelOperation);
//		modelClass.addImportedClass(ClassUtils.getPackageName(type.getType()) + "." + ClassUtils.getShortClassName(type.getType()));
//		if (type instanceof Element)
//			initializeClass(modelClass, (Element) type);
//	}

	
//	protected void buildFixtureListFactoryMethod(ModelClass modelClass, Type type) throws Exception {
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.setName("create"+NameUtil.getSimpleName(type.getType())+"List");
//		modelOperation.setResultType("List<"+NameUtil.getSimpleName(type.getType())+">");
//		modelClass.addInstanceOperation(modelOperation);
//		modelClass.addImportedClass("java.util.List");
//		modelClass.addImportedClass("java.util.ArrayList");
//		if (type instanceof Element)
//			initializeClass(modelClass, (Element) type);
//	}

//	protected void buildFixturesForElement(ModelClass modelClass, Element element) throws Exception {
//		//buildFixtureRecordFactoryMethod(modelClass, element);
//		//buildFixtureListFactoryMethod(modelClass, element);
//		createEmptyFactoryMethod(modelClass, element);
//		createSampleFactoryMethod(modelClass, element);
//	}
//
//	protected void createEmptyFactoryMethod(ModelClass modelClass, Element element) {
//		String elementType = element.getType();
//		String packageName = TypeUtil.getPackageName(elementType);
//		String className = TypeUtil.getClassName(elementType);
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
//		modelOperation.setName("createEmpty"+className);
//		modelOperation.setResultType(className);
//		modelClass.addInstanceOperation(modelOperation);
//		modelClass.addImportedClass(packageName + "." + className);
//		modelOperation.addInitialSource(createEmptyFactoryMethodSource(className));
//	}

	

	//assertElementExists(Collection<Element> elements, Element element) 
	protected void createOperation_assertElementExists(ModelClass modelClass, Namespace namespace, Element element, Reference reference) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityPackageName = entityClassHelper.getEntityPackageName();
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		//String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		//String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assert"+entityClassName+"Exists");
		modelOperation.addParameter(CodeUtil.createParameter("collection", entityPackageName, entityClassName, entityBeanName+"List"));
		modelOperation.addParameter(CodeUtil.createParameter("item", entityPackageName, entityClassName, entityBeanName));
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+entityBeanName+"List, \""+entityClassName+" list must be specified\");");
		buf.putLine2("Assert.notNull("+entityBeanName+", \""+entityClassName+" record must be specified\");");
		buf.putLine2("Iterator<"+entityClassName+"> iterator = "+entityBeanName+"List.iterator();");
		buf.putLine2("for (int i=0; iterator.hasNext(); i++) {");
		buf.putLine2("	"+entityClassName+" "+entityBeanName+"1 = iterator.next();");
		buf.putLine2("	if ("+entityBeanName+"1.equals("+entityBeanName+")) {");
		buf.putLine2("		return;");
		buf.putLine2("	}");
		buf.putLine2("}");
		buf.putLine2("throw new AssertionError(\"[Assertion failed] - "+entityClassName+" should exist: \"+"+entityBeanName+");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//assertElementCorrect(Element element) 
	protected void createOperation_assertElementCorrect(ModelClass modelClass, Namespace namespace, Element element, Reference reference) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityPackageName = entityClassHelper.getEntityPackageName();
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		//String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		//String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element targetElement = entityClassHelper.getTargetElement();

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assert"+entityClassName+"Correct");
		modelOperation.addParameter(CodeUtil.createParameter("item", entityPackageName, entityClassName, entityBeanName));
		
		Buf buf = new Buf();
		if (ElementUtil.hasIdField(element))
			buf.putLine2("long index = "+entityBeanName+".getId() / 10L;");

		List<Attribute> attributes = ElementUtil.getAttributes(targetElement);
		List<Reference> references = ElementUtil.getReferences(targetElement);
		
		boolean checkForNullsFirst = false;
		if (checkForNullsFirst ) {
			//check required attributes exist
			Iterator<Attribute> iterator1 = attributes.iterator();
			while (iterator1.hasNext()) {
				Attribute attribute = iterator1.next();
				if (attribute.getName().equalsIgnoreCase("id"))
					continue;
				if (!FieldUtil.isRequired(attribute))
					continue;
				String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(attribute);
				buf.putLine2("assertObjectExists(\""+fieldNameCapped+"\", "+entityBeanName+".get"+fieldNameCapped+"());");
			}
	
			//check required references exist
			Iterator<Reference> iterator2 = references.iterator();
			while (iterator2.hasNext()) {
				Reference reference2 = iterator2.next();
				if (!FieldUtil.isRequired(reference2))
					continue;
				String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(reference2);
				buf.putLine2("assertObjectExists(\""+fieldNameCapped+"\", "+entityBeanName+".get"+fieldNameCapped+"());");
			}
		}
		
		//check attributes have correct values
		Iterator<Attribute> iterator1 = attributes.iterator();
		while (iterator1.hasNext()) {
			Attribute attribute = iterator1.next();
			if (attribute.getName().equalsIgnoreCase("id"))
				continue;
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(attribute);
			if (ElementUtil.hasIdField(element))
				buf.putLine2("assertObjectCorrect(\""+fieldNameCapped+"\", "+entityBeanName+".get"+fieldNameCapped+"(), index);");
			else buf.putLine2("assertObjectCorrect(\""+fieldNameCapped+"\", "+entityBeanName+".get"+fieldNameCapped+"());");
		}

		//check references have correct values
		Iterator<Reference> iterator2 = references.iterator();
		while (iterator2.hasNext()) {
			Reference reference2 = iterator2.next();
			if (FieldUtil.isInverse(reference2))
				continue;
			
			String fieldClassName = null;
			if (reference2.getContained())
				fieldClassName = DataLayerHelper.getContainedEntityClassName(element, reference2);
			else fieldClassName = DataLayerHelper.getFieldEntityClassName(reference2);
			
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(reference2);
			String referenceNamespace = TypeUtil.getNamespace(reference2.getType());
			String elementNamespace = TypeUtil.getNamespace(element.getType());
			
			if (!referenceNamespace.equals(elementNamespace)) {
				String fixtureClassName = DataLayerHelper.getEntityFixtureClassName(referenceNamespace);
				buf.putLine2(fixtureClassName+".assert"+fieldClassName+"Correct("+entityBeanName+".get"+fieldNameCapped+"());");
			} else buf.putLine2("assert"+fieldClassName+"Correct("+entityBeanName+".get"+fieldNameCapped+"());");
		}
		
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//assertElementCorrect(Collection<Element> elements) 
	protected void createOperation_assertElementCorrect2(ModelClass modelClass, Namespace namespace, Element element, Reference reference) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityPackageName = entityClassHelper.getEntityPackageName();
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		//String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		//String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assert"+entityClassName+"Correct");
		modelOperation.addParameter(CodeUtil.createParameter("collection", entityPackageName, entityClassName, entityBeanName+"List"));
		
		Buf buf = new Buf();
		buf.putLine2("Assert.isTrue("+entityBeanName+"List.size() == 2, \""+entityClassName+" count not correct\");");
		buf.putLine2("Iterator<"+entityClassName+"> iterator = "+entityBeanName+"List.iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		buf.putLine2("	"+entityClassName+" "+entityBeanName+" = iterator.next();");
		buf.putLine2("	assert"+entityClassName+"Correct("+entityBeanName+");");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//assertElementsSame(Element element1, Element element2) 
	protected void createOperation_assertElementsSame(ModelClass modelClass, Namespace namespace, Element element) {
		String packageName = DataLayerHelper.getInferredEntityPackageName(this.namespace, element);
		String className = DataLayerHelper.getInferredEntityClassName(this.namespace, element);
		String beanName = DataLayerHelper.getInferredEntityBeanName(this.namespace, element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assertSame"+className+"");
		modelOperation.addParameter(CodeUtil.createParameter("item", packageName, className, beanName+"1"));
		modelOperation.addParameter(CodeUtil.createParameter("item", packageName, className, beanName+"2"));
		
		Buf buf = new Buf();
		if (ElementUtil.isUserDefined(element))
			buf.putLine2("assertSame"+className+"("+beanName+"1, "+beanName+"2, false, \"\");");
		else buf.putLine2("assertSame"+className+"("+beanName+"1, "+beanName+"2, \"\");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//assertElementsSame(Element element1, Element element2, String message) 
	protected void createOperation_assertElementsSame2(ModelClass modelClass, Namespace namespace, Element element) {
		String packageName = DataLayerHelper.getInferredEntityPackageName(this.namespace, element);
		String className = DataLayerHelper.getInferredEntityClassName(this.namespace, element);
		String beanName = DataLayerHelper.getInferredEntityBeanName(this.namespace, element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assertSame"+className+"");
		modelOperation.addParameter(CodeUtil.createParameter("item", packageName, className, beanName+"1"));
		modelOperation.addParameter(CodeUtil.createParameter("item", packageName, className, beanName+"2"));
		modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "String", "message"));

		Buf buf = new Buf();
		buf.putLine2("assertSame"+className+"("+beanName+"1, "+beanName+"2, false, message);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//assertElementsSame(Element element1, Element element2, boolean checkIds) 
	protected void createOperation_assertElementsSame3(ModelClass modelClass, Namespace namespace, Element element) {
		String packageName = DataLayerHelper.getInferredEntityPackageName(this.namespace, element);
		String className = DataLayerHelper.getInferredEntityClassName(this.namespace, element);
		String beanName = DataLayerHelper.getInferredEntityBeanName(this.namespace, element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assertSame"+className+"");
		modelOperation.addParameter(CodeUtil.createParameter("item", packageName, className, beanName+"1"));
		modelOperation.addParameter(CodeUtil.createParameter("item", packageName, className, beanName+"2"));
		modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "boolean", "checkIds"));
		
		Buf buf = new Buf();
		buf.putLine2("assertSame"+className+"("+beanName+"1, "+beanName+"2, checkIds, \"\");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//assertElementsSame(Element element1, Element element2, String message) 
	protected void createOperation_assertElementsSame4(ModelClass modelClass, Namespace namespace, Element element) {
		String packageName = DataLayerHelper.getInferredEntityPackageName(this.namespace, element);
		String className = DataLayerHelper.getInferredEntityClassName(this.namespace, element);
		String beanName = DataLayerHelper.getInferredEntityBeanName(this.namespace, element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assertSame"+className+"");
		modelOperation.addParameter(CodeUtil.createParameter("item", packageName, className, beanName+"1"));
		modelOperation.addParameter(CodeUtil.createParameter("item", packageName, className, beanName+"2"));
		if (ElementUtil.isUserDefined(element))
			modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "boolean", "checkIds"));
		modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "String", "message"));
		
		Buf buf = new Buf();
		buf.putLine2("assertObjectExists(\""+className+"1\", "+beanName+"1);");
		buf.putLine2("assertObjectExists(\""+className+"2\", "+beanName+"2);");
		//buf.putLine2("Assert.notNull("+entityNameUncapped+"1, \""+entityClassName+"1 must be specified\");");
		//buf.putLine2("Assert.notNull("+entityNameUncapped+"2, \""+entityClassName+"2 must be specified\");");
		
		if (ElementUtil.isUserDefined(element)) {
			buf.putLine2("if (checkIds)");
			buf.putLine2("	assertObjectEquals(\"Id\", "+beanName+"1.getId(), "+beanName+"2.getId(), message);");
		}
		
		List<Attribute> attributes = ElementUtil.getAttributes(element);
		Iterator<Attribute> iterator1 = attributes.iterator();
		while (iterator1.hasNext()) {
			Attribute attribute = iterator1.next();
			if (attribute.getName().equalsIgnoreCase("id"))
				continue;
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(attribute);
			//String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(attribute);
			buf.putLine2("assertObjectEquals(\""+fieldNameCapped+"\", "+beanName+"1.get"+fieldNameCapped+"(), "+beanName+"2.get"+fieldNameCapped+"(), message);");
			//buf.putLine2("Assert.equals("+entityNameUncapped+"1.get"+fieldNameCapped+"(), "+entityNameUncapped+"2.get"+fieldNameCapped+"(), message+\": "+fieldNameCapped+" fields not equal: "+fieldNameUncapped+"1=\"+"+entityNameUncapped+"1.get"+fieldNameCapped+"()+\", "+fieldNameUncapped+"2=\"+"+entityNameUncapped+"2.get"+fieldNameCapped+"());");
		}
		
		List<Reference> references = ElementUtil.getReferences(element);
		Iterator<Reference> iterator2 = references.iterator();
		while (iterator2.hasNext()) {
			Reference reference = iterator2.next();
			EntityClassHelper entityClassHelper = new EntityClassHelper(context);
			entityClassHelper.initialize(namespace, element, reference);
			String fieldClassName = entityClassHelper.getEntityClassName();
			Element targetElement = entityClassHelper.getTargetElement();
			String fieldNamespace = TypeUtil.getNamespace(targetElement.getType());
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(reference);
			String entityNameCapped = DataLayerHelper.getEntityClassName(reference);
			if (reference.getContained())
				entityNameCapped = fieldClassName;
			
			if (!fieldNamespace.equals(namespace.getUri())) {
				String fixtureClassName = DataLayerHelper.getEntityFixtureClassName(fieldNamespace);
				if (ElementUtil.isUserDefined(element))
					buf.putLine2(fixtureClassName+".assertSame"+fieldClassName+"("+beanName+"1.get"+fieldNameCapped+"(), "+beanName+"2.get"+fieldNameCapped+"(), checkIds, message);");	
				else buf.putLine2(fixtureClassName+".assertSame"+fieldClassName+"("+beanName+"1.get"+fieldNameCapped+"(), "+beanName+"2.get"+fieldNameCapped+"(), message);");	
			} else {
				if (ElementUtil.isUserDefined(element))
					buf.putLine2("assertSame"+entityNameCapped+"("+beanName+"1.get"+fieldNameCapped+"(), "+beanName+"2.get"+fieldNameCapped+"(), checkIds, message);");
				else buf.putLine2("assertSame"+entityNameCapped+"("+beanName+"1.get"+fieldNameCapped+"(), "+beanName+"2.get"+fieldNameCapped+"(), message);");
			}
			
			if (!fieldNamespace.equals(this.namespace))
				externalReferences.put(reference.getType(), reference);
		}
		
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	//assertElementsSame(Collection<Element> list1, Collection<Element> list2) 
	protected void createOperation_assertElementsSame5(ModelClass modelClass, Namespace namespace, Element element) {
		String packageName = DataLayerHelper.getInferredEntityPackageName(this.namespace, element);
		String className = DataLayerHelper.getInferredEntityClassName(this.namespace, element);
		String beanName = DataLayerHelper.getInferredEntityBeanName(this.namespace, element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assertSame"+className+"");
		modelOperation.addParameter(CodeUtil.createParameter("collection", packageName, className, beanName+"List1"));
		modelOperation.addParameter(CodeUtil.createParameter("collection", packageName, className, beanName+"List2"));
		
		Buf buf = new Buf();
		if (ElementUtil.isUserDefined(element))
			buf.putLine2("assertSame"+className+"("+beanName+"List1, "+beanName+"List2, false, \"\");");
		else buf.putLine2("assertSame"+className+"("+beanName+"List1, "+beanName+"List2, \"\");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	//assertElementsSame(Collection<Element> list1, Collection<Element> list2, String message) 
	protected void createOperation_assertElementsSame6(ModelClass modelClass, Namespace namespace, Element element) {
		String packageName = DataLayerHelper.getInferredEntityPackageName(this.namespace, element);
		String className = DataLayerHelper.getInferredEntityClassName(this.namespace, element);
		String beanName = DataLayerHelper.getInferredEntityBeanName(this.namespace, element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assertSame"+className+"");
		modelOperation.addParameter(CodeUtil.createParameter("collection", packageName, className, beanName+"List1"));
		modelOperation.addParameter(CodeUtil.createParameter("collection", packageName, className, beanName+"List2"));
		modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "String", "message"));
		
		Buf buf = new Buf();
		buf.putLine2("assertSame"+className+"("+beanName+"List1, "+beanName+"List2, \"\");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	//assertElementsSame(Collection<Element> list1, Collection<Element> list2, boolean checkIds) 
	protected void createOperation_assertElementsSame7(ModelClass modelClass, Namespace namespace, Element element) {
		String packageName = DataLayerHelper.getInferredEntityPackageName(this.namespace, element);
		String className = DataLayerHelper.getInferredEntityClassName(this.namespace, element);
		String beanName = DataLayerHelper.getInferredEntityBeanName(this.namespace, element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assertSame"+className+"");
		modelOperation.addParameter(CodeUtil.createParameter("collection", packageName, className, beanName+"List1"));
		modelOperation.addParameter(CodeUtil.createParameter("collection", packageName, className, beanName+"List2"));
		if (ElementUtil.isUserDefined(element))
			modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "boolean", "checkIds"));
		
		Buf buf = new Buf();
		if (ElementUtil.isUserDefined(element))
			buf.putLine2("assertSame"+className+"("+beanName+"List1, "+beanName+"List2, checkIds, \"\");");
		else buf.putLine2("assertSame"+className+"("+beanName+"List1, "+beanName+"List2, \"\");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	//assertElementsSame(Collection<Element> list1, Collection<Element> list2, boolean checkIds, String message) 
	protected void createOperation_assertElementsSame8(ModelClass modelClass, Namespace namespace, Element element) {
		String packageName = DataLayerHelper.getInferredEntityPackageName(this.namespace, element);
		String className = DataLayerHelper.getInferredEntityClassName(this.namespace, element);
		String beanName = DataLayerHelper.getInferredEntityBeanName(this.namespace, element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assertSame"+className+"");
		modelOperation.addParameter(CodeUtil.createParameter("collection", packageName, className, beanName+"List1"));
		modelOperation.addParameter(CodeUtil.createParameter("collection", packageName, className, beanName+"List2"));
		if (ElementUtil.isUserDefined(element))
			modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "boolean", "checkIds"));
		modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "String", "message"));
		
		String utilClassName = className+"Util";
		if (ElementUtil.isEventElement(element)) {
			utilClassName = "EventUtil";
			modelClass.addImportedClass("org.aries.event.util.EventUtil");
		} else if (ElementUtil.isMessageElement(element)) {
			utilClassName = "MessageUtil";
			modelClass.addImportedClass("org.aries.message.util.MessageUtil");
		}
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+beanName+"List1, \""+className+" list1 must be specified\");");
		buf.putLine2("Assert.notNull("+beanName+"List2, \""+className+" list2 must be specified\");");
		buf.putLine2("Assert.equals("+beanName+"List1.size(), "+beanName+"List2.size(), \""+className+" count not equal\");");
		
		//buf.putLine2("Collection<"+entityClassName+"> sortedRecords1 = "+utilClassName+".sortRecords("+entityNameUncapped+"List1);");
		//buf.putLine2("Collection<"+entityClassName+"> sortedRecords2 = "+utilClassName+".sortRecords("+entityNameUncapped+"List2);");
		//buf.putLine2("Iterator<"+entityClassName+"> list1Iterator = sortedRecords1.iterator();");
		//buf.putLine2("Iterator<"+entityClassName+"> list2Iterator = sortedRecords2.iterator();");
		//buf.putLine2("while (list1Iterator.hasNext() && list2Iterator.hasNext()) {");
		//buf.putLine2("	"+entityClassName+" "+entityNameUncapped+"1 = list1Iterator.next();");
		//buf.putLine2("	"+entityClassName+" "+entityNameUncapped+"2 = list2Iterator.next();");
		//buf.putLine2("	assertSame"+entityClassName+"("+entityNameUncapped+"1, "+entityNameUncapped+"2, message);");
		//buf.putLine2("}");
		
		buf.putLine2("Iterator<"+className+"> iterator1 = "+beanName+"List1.iterator();");
		buf.putLine2("while (iterator1.hasNext()) {");
		buf.putLine2("	"+className+" "+beanName+"1 = iterator1.next();");
		buf.putLine2("	Iterator<"+className+"> iterator2 = "+beanName+"List2.iterator();");
		buf.putLine2("	boolean isFound = false;");
		buf.putLine2("	while (iterator2.hasNext()) {");
		buf.putLine2("		"+className+" "+beanName+"2 = iterator2.next();");
		buf.putLine2("		if ("+beanName+"1.getId().equals("+beanName+"2.getId())) {");
		if (ElementUtil.isUserDefined(element))
			buf.putLine2("			assertSame"+className+"("+beanName+"1, "+beanName+"2, checkIds, message);");
		else buf.putLine2("			assertSame"+className+"("+beanName+"1, "+beanName+"2, message);");
		buf.putLine2("			isFound = true;");
		buf.putLine2("			break;");
		buf.putLine2("		}");
		buf.putLine2("	}");
		buf.putLine2("	");			
		buf.putLine2("	//if (!isFound)");
		buf.putLine2("	//	System.out.println();");
		buf.putLine2("	Assert.isTrue(isFound, \""+elementClassName+" record not found: \"+"+beanName+"1);");
		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//assertElementsSame(Collection<Element> list1, Collection<Element> list2) 
	protected void createOperation_assertElementsSame9(ModelClass modelClass, Namespace namespace, Element element) {
		String packageName = DataLayerHelper.getInferredEntityPackageName(this.namespace, element);
		String className = DataLayerHelper.getInferredEntityClassName(this.namespace, element);
		String beanName = DataLayerHelper.getInferredEntityBeanName(this.namespace, element);
		String baseClassName = ModelLayerHelper.getElementClassName(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assertSame"+className+"");
		modelOperation.addParameter(CodeUtil.createParameter("map", packageName, baseClassName+"Key", className, beanName+"Map1"));
		modelOperation.addParameter(CodeUtil.createParameter("map", packageName, baseClassName+"Key", className, beanName+"Map2"));
		
		Buf buf = new Buf();
		buf.putLine2("assertSame"+className+"("+beanName+"Map1, "+beanName+"Map2, \"\");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
		modelClass.addImportedClass("java.util.Map");
	}
	
	//assertElementsSame(Collection<Element> list1, Collection<Element> list2) 
	protected void createOperation_assertElementsSame10(ModelClass modelClass, Namespace namespace, Element element) {
		String packageName = DataLayerHelper.getInferredEntityPackageName(this.namespace, element);
		String className = DataLayerHelper.getInferredEntityClassName(this.namespace, element);
		String beanName = DataLayerHelper.getInferredEntityBeanName(this.namespace, element);
		String baseQualifiedName = ModelLayerHelper.getElementQualifiedName(element);
		String baseClassName = ModelLayerHelper.getElementClassName(element);
		String baseBeanName = ModelLayerHelper.getElementBeanName(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assertSame"+className+"");
		modelOperation.addParameter(CodeUtil.createParameter("map", packageName, baseClassName+"Key", className, beanName+"Map1"));
		modelOperation.addParameter(CodeUtil.createParameter("map", packageName, baseClassName+"Key", className, beanName+"Map2"));
		modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "String", "message"));
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+beanName+"Map1, \""+className+" map1 must be specified\");");
		buf.putLine2("Assert.notNull("+beanName+"Map2, \""+className+" map2 must be specified\");");
		buf.putLine2("Assert.isTrue("+beanName+"Map1.size() == "+beanName+"Map2.size(), \""+className+" count not correct\");");
		buf.putLine2("Set<"+baseClassName+"Key> keySet = "+beanName+"Map1.keySet();");
		buf.putLine2("Iterator<"+baseClassName+"Key> iterator = keySet.iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		buf.putLine2("	"+baseClassName+"Key "+baseBeanName+"Key = iterator.next();");
		buf.putLine2("	"+className+" "+beanName+"1 = "+beanName+"Map1.get("+baseBeanName+"Key);");
		buf.putLine2("	"+className+" "+beanName+"2 = "+beanName+"Map2.get("+baseBeanName+"Key);");
		//buf.putLine2("	"+entityNameUncapped+"1.equals("+entityNameUncapped+"2);");
		buf.putLine2("	assertSame"+className+"("+beanName+"1, "+beanName+"2);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
		modelClass.addImportedClass(baseQualifiedName + "Key");
		modelClass.addImportedClass("java.util.Map");
	}
	
	
	
	protected void buildOperationsForExternalReferences(ModelClass modelClass, Map<String, Reference> externalReferences) {
		Iterator<Reference> iterator = externalReferences.values().iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			String uri = reference.getNamespace();
			Namespace namespace = context.getNamespaceByUri(uri);
			createOperation_assertObjectEquals(modelClass, namespace, reference); 
		}
	}

	protected void createOperation_assertObjectEquals(ModelClass modelClass, Namespace namespace, Reference reference) {
	}


	
	protected void initializeImportedClasses(ModelClass modelClass) throws Exception {
		//modelClass.addImportedClass("java.io.Serializable");
	}

	protected void addImportedClassesForAttributes(ModelClass modelClass, Element element, List<Attribute> attributes) {
		Iterator<Attribute> iterator = attributes.iterator();
		while (iterator.hasNext()) {
			Attribute attribute = iterator.next();
			String type = attribute.getType();
			boolean isElement = context.getElementByType(type) != null;
			boolean isEnum = context.getEnumerationByType(type) != null;
			
			if (isElement) {
				Element attributeElement = context.getElementByType(type);
				String attributePackageName = TypeUtil.getPackageName(type);
				if (attributeElement != null) {
					String attributeFixtureName = FixtureUtil.getFixtureNameFromPackageName(attributePackageName);
					String attributeClassName = NameUtil.capName(attributeFixtureName);
					String attributeQualifiedName = attributePackageName + "." + attributeClassName;
					modelClass.addImportedClass(attributeQualifiedName);
				}
				
			} else if (isEnum) {
				Enumeration attributeEnum = context.getEnumerationByType(type);
				if (attributeEnum != null) {
					String attributeQualifiedName = ModelLayerHelper.getEnumerationQualifiedName(attributeEnum);
					//String attributeFixtureClassName = DataLayerHelper.getEntityFixtureClassName(attributeEnum);
					//String attributeFixturePackageName = DataLayerHelper.getEntityFixturePackageName(attributeEnum);
					//String attributeFixtureQualifiedName = attributePackageName + "." + attributeFixtureClassName;
					modelClass.addImportedClass(attributeQualifiedName);
					//modelClass.addImportedClass(attributeFixtureQualifiedName);
				}
			}
		}
	}
	
	protected void addImportedClassesForReferences(ModelClass modelClass, Element element, List<Reference> references) {
		Iterator<Reference> iterator = references.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			if (reference.getContained())
				continue;
			
			String type = reference.getType();
			Element referenceElement = context.getElementByType(type);
			if (referenceElement != null) {
				//String referenceNamespace = TypeUtil.getNamespace(type);
				//String referenceFixturePackageName = DataLayerHelper.getEntityFixturePackageName(referenceNamespace);
				//String referenceFixtureClassName = DataLayerHelper.getEntityFixtureClassName(referenceElement);
				//String referenceFixtureQualifiedName = referenceFixturePackageName + "." + referenceFixtureClassName;
				String referenceFixtureQualifiedName = DataLayerHelper.getEntityFixtureQualifiedName(referenceElement);
				modelClass.addImportedClass(referenceFixtureQualifiedName);
			}
		}
	}
	
}
