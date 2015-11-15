package nam.model.src.main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nam.model.Attribute;
import nam.model.Element;
import nam.model.Enumeration;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Reference;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.TypeUtil;

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


public class ModelFixtureClassBuilder extends AbstractBeanBuilder {

	private ModelClass modelClass;
	
	private List<Namespace> namespaces;
	
	protected Map<String, Reference> externalReferences;
	
	private DummyValueFactory dummyValueFactory;
	
	private Long baseId;
	
	
	public ModelFixtureClassBuilder(GenerationContext context) {
		super(context);
	}

	protected boolean isFixtureRequired(Element element) {
		if (ElementUtil.isAbstract(element))
			return false;
		if (ElementUtil.isTransient(element))
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
		externalReferences = new HashMap<String, Reference>();
	}
	
	public List<ModelClass> buildClasses(List<Namespace> namespaces) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Namespace> iterator = namespaces.iterator();
		Module module = context.getModule();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			if (namespace.getImported() == null || !namespace.getImported() || namespace.getUri().equals(module.getNamespace())) {
				ModelClass modelClass = buildClass(namespace);
				modelClasses.add(modelClass);
			}
		}
		return modelClasses;
	}
	
	public ModelClass buildClass(Namespace namespace) throws Exception {
		String packageName = ModelLayerHelper.getModelFixturePackageName(namespace);
		String className = ModelLayerHelper.getModelFixtureClassName(namespace);
		modelClass = new ModelClass();
		modelClass.setType(TypeUtil.getTypeFromNamespace(namespace));
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		modelClass.setParentClassName("BaseFixture");
		modelClass.addImportedClass("org.aries.util.BaseFixture");
		
		initializeStructures();
		this.namespace = namespace;
		this.namespaces.add(namespace);
		dummyValueFactory.setNamespace(namespace);
		initializeClass(modelClass, namespace);
		return modelClass;
	}

	public void initializeClass(ModelClass modelClass, Namespace namespace) throws Exception {
		List<Element> elements = NamespaceUtil.getElements(namespace);
		List<Enumeration> enumerations = NamespaceUtil.getEnumerations(namespace);
		initializeStaticFields(modelClass, elements);
		initializeStaticMethods(modelClass, elements);
		buildFixturesForElements(modelClass, elements);
		buildFixturesForEnumeration(modelClass, enumerations);
		buildOperationsForExternalReferences(modelClass, externalReferences);
		initializeImportedClasses(modelClass);
	}

	public void initializeStaticFieldsNOTUSED(ModelClass modelClass, List<Element> elements) {
		createAttribute_GlobalCounter(modelClass);
	}
	
	public void initializeStaticFields(ModelClass modelClass, List<Element> elements) {
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (!isFixtureRequired(element))
				continue;
			baseId = 100L;
			
			Element keyElement = context.getElementByType(element.getType()+"Key");
			Element criteriaElement = context.getElementByType(element.getType()+"Criteria");

			createAttribute_ElementCounter(modelClass, element);
			if (keyElement != null)
				createAttribute_ElementCounter(modelClass, keyElement);
			if (criteriaElement != null)
				createAttribute_ElementCounter(modelClass, criteriaElement);
		}
	}

	protected void createAttribute_GlobalCounter(ModelClass modelClass) {
		ModelAttribute modelAttribute = new ModelAttribute();
		modelAttribute.setModifiers(Modifier.PRIVATE + Modifier.STATIC);
		modelAttribute.setName("counter");
		modelAttribute.setClassName("long");
		modelAttribute.setDefault(1L);
		modelAttribute.setGenerateGetter(false);
		modelAttribute.setGenerateSetter(false);
		modelClass.addStaticAttribute(modelAttribute);
	}
	
	protected void createAttribute_ElementCounter(ModelClass modelClass, Element element) {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		ModelAttribute modelAttribute = new ModelAttribute();
		modelAttribute.setModifiers(Modifier.PRIVATE + Modifier.STATIC);
		modelAttribute.setName(elementNameUncapped+"Count");
		modelAttribute.setClassName("long");
		modelAttribute.setDefault(0L);
		modelAttribute.setGenerateGetter(false);
		modelAttribute.setGenerateSetter(false);
		modelClass.addStaticAttribute(modelAttribute);
	}

	public void initializeStaticMethods(ModelClass modelClass, List<Element> elements) {
		createOperation_Reset(modelClass, elements);
	}

	protected void createOperation_Reset(ModelClass modelClass, List<Element> elements) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("reset");
		
		Buf buf = new Buf();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (!isFixtureRequired(element))
				continue;
			String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
			buf.putLine2(elementNameUncapped+"Count = 0L;");
		}
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	public void buildFixturesForEnumeration(ModelClass modelClass, List<Enumeration> enumerations) throws Exception {
		Iterator<Enumeration> iterator = enumerations.iterator();
		while (iterator.hasNext()) {
			Enumeration enumeration = iterator.next();
			if (!isFixtureRequired(enumeration))
				continue;
			buildFixturesForEnumeration(modelClass, enumeration);
		}
	}
	
	public void buildFixturesForEnumeration(ModelClass modelClass, Enumeration enumeration) {
		//TODO none yet
	}

	public void buildFixturesForElements(ModelClass modelClass, List<Element> elements) throws Exception {
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (!isFixtureRequired(element))
				continue;
			baseId = 10L;
			
			Element keyElement = context.getElementByType(element.getType()+"Key");
			Element criteriaElement = context.getElementByType(element.getType()+"Criteria");

			buildFixturesForElement(modelClass, element);
			if (keyElement != null)
				buildFixturesForElement(modelClass, keyElement);
			if (criteriaElement != null)
				buildFixturesForElement(modelClass, criteriaElement);
		}
	}

	protected void buildFixturesForElement(ModelClass modelClass, Element element) throws Exception {
		if (ElementUtil.isMessageElement(element)) {
			createMessageRecordFactory(modelClass, element, false, false, false);
			createMessageRecordFactory(modelClass, element, true, false, false);
			createMessageRecordFactory(modelClass, element, true, false, true);
			createMessageRecordFactory(modelClass, element, true, true, false);
			createMessageRecordFactory(modelClass, element, true, true, true);
		} else {
			createFixtureRecordFactory(modelClass, element, false, false);
			createFixtureRecordFactory(modelClass, element, true, true);
			createFixtureRecordFactory(modelClass, element, true, false);
		}
			
		createFixtureCollectionFactory(modelClass, element, false, false, false, false);
		createFixtureCollectionFactory(modelClass, element, false, true, false, false);
		createFixtureCollectionFactory(modelClass, element, false, true, false, true);
		createFixtureCollectionFactory(modelClass, element, false, true, true, true);
		createFixtureCollectionFactory(modelClass, element, true, false, false, false);
		createFixtureCollectionFactory(modelClass, element, true, true, false, false);
		createFixtureCollectionFactory(modelClass, element, true, true, false, true);
		createFixtureCollectionFactory(modelClass, element, true, true, true, true);
		createFixtureMapFactory(modelClass, element, false, false, false);
		createFixtureMapFactory(modelClass, element, true, true, false);
		createFixtureMapFactory(modelClass, element, true, true, true);

//		if (element.getName().equalsIgnoreCase("Payment"))
//			System.out.println();
		
		Element keyElement = CodeUtil.getKeyElement(element);
		if (keyElement != null) {
			createFixtureRecordFactory(modelClass, keyElement, false, false);
			createFixtureRecordFactory(modelClass, keyElement, true, true);
			createFixtureRecordFactory(modelClass, keyElement, true, false);
			createFixtureCollectionFactory(modelClass, keyElement, false, false, false, false);
			createFixtureCollectionFactory(modelClass, keyElement, false, true, false, false);
			createFixtureCollectionFactory(modelClass, keyElement, false, true, false, true);
			createFixtureCollectionFactory(modelClass, keyElement, false, true, true, true);
		}
		
		createOperation_assertElementExists(modelClass, namespace, element); 
		createOperation_assertElementCorrect(modelClass, namespace, element); 
		createOperation_assertElementCorrect2(modelClass, namespace, element); 
		
		if (!ElementUtil.isCriteriaElement(element)) {
			createOperation_assertElementsSame(modelClass, namespace, element); 
			
			if (ElementUtil.isUserDefined(element)) {
				createOperation_assertElementsSame2(modelClass, namespace, element); 
				createOperation_assertElementsSame3(modelClass, namespace, element);
			}
			
			createOperation_assertElementsSame4(modelClass, namespace, element); 
			createOperation_assertElementsSame5(modelClass, namespace, element); 

			if (ElementUtil.isUserDefined(element)) {
				createOperation_assertElementsSame6(modelClass, namespace, element);
				createOperation_assertElementsSame7(modelClass, namespace, element); 
			}
			
			createOperation_assertElementsSame8(modelClass, namespace, element); 
			
			if (CodeUtil.hasKeyElement(element)) {
				createOperation_assertElementsSame9(modelClass, namespace, element); 
				createOperation_assertElementsSame10(modelClass, namespace, element); 
			}
		}
	}
	
	protected void createFixtureRecordFactory(ModelClass modelClass, Type type, boolean doPopulate, boolean addIndex) throws Exception {
		String elementType = type.getType();
		String packageName = TypeUtil.getPackageName(elementType);
		String className = TypeUtil.getClassName(elementType);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		String methodCall = doPopulate ? "create_" : "createEmpty_";
		modelOperation.setName(methodCall+className);
		modelOperation.setResultType(className);
		
//		if (className.equals("Preferences"))
//			System.out.println();
		
		if (doPopulate) {
			if (type instanceof Element) {
				Element element = (Element) type;
				Element container = getContainingElement(element);
				if (container != null) {
					String containerName = NameUtil.uncapName(container.getName());
					String containerClassName = TypeUtil.getClassName(container.getType());
					ModelParameter parameter = CodeUtil.createParameter(containerClassName, containerName);
					modelOperation.addParameter(parameter);	
				}
			}
		
			if (addIndex == false) {
				modelOperation.addParameter(CodeUtil.createParameter("long", "index"));
			}
		}
		
		modelClass.addInstanceOperation(modelOperation);
		modelClass.addImportedClass(packageName + "." + className);
		modelOperation.addInitialSource(createFixtureRecordFactory_Source(type, doPopulate, addIndex));
		//if (type instanceof Element)
		//	initializeClass(modelClass, (Element) type);
	}

	protected String createFixtureRecordFactory_Source(Type type, boolean doPopulate, boolean addIndex) {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
//		if (elementClassName.equalsIgnoreCase("mapEntry"))
//			System.out.println();
		
		boolean contained = false;
		String containerName = null;
		
		Buf buf = new Buf();
		if (doPopulate && type instanceof Element) {
			Element element = (Element) type;
			Element container = getContainingElement(element);
			if (container != null) {
				contained = true;
				containerName = NameUtil.uncapName(container.getName());
			}
		}
		if (addIndex) {
			String beanName = NameUtil.uncapName(elementClassName);
			if (contained) 
				buf.putLine2(elementClassName+" "+beanName+" = create_"+elementClassName+"("+containerName+", 1);");
				//buf.putLine2(elementClassName+" "+beanName+" = create"+elementClassName+"("+containerName+", "+beanName+"Count++);");
			else buf.putLine2(elementClassName+" "+beanName+" = create_"+elementClassName+"(1);");
			//else buf.putLine2(elementClassName+" "+beanName+" = create"+elementClassName+"("+beanName+"Count++);");
			buf.putLine2("return "+beanName+";");
			return buf.get();
		}
		
		String beanName = NameUtil.uncapName(elementClassName);
		String methodCall = doPopulate ? "createEmpty_" : "new ";
		buf.putLine2(elementClassName+" "+beanName+" = "+methodCall + elementClassName+"();");

		if (doPopulate && type instanceof Element) {
			Element element = (Element) type;
			//buf.putLine2("String ext = index == 0 ? \"\" : \"\"+index;");
			buf.putLine2("long value = createValue(index, "+elementNameUncapped+"Count);");
			buf.put(dummyValueFactory.createSampleValuesForAttributes(element, ElementUtil.getAttributes(element)));
			buf.put(dummyValueFactory.createSampleValuesForReferences(element, ElementUtil.getReferences(element)));
			addImportedClassesForAttributes(modelClass, ElementUtil.getAttributes(element));
			addImportedClassesForReferences(modelClass, ElementUtil.getReferences(element));
		}

		if (doPopulate && type instanceof Element) {
			Element element = (Element) type;
			if (ElementUtil.isUserDefined(element) && !ElementUtil.isTransient(element) && ElementUtil.hasIdField(element)) {
				//Long randomId = IdGenerator.createRequestId();
				buf.putLine2(beanName+".setId(value);");
			}
		}
		
		if (!doPopulate)
			buf.putLine2(elementNameUncapped+"Count++;");
		buf.putLine2("return "+beanName+";");
		return buf.get();
	}

	protected void createMessageRecordFactory(ModelClass modelClass, Type type, boolean doPopulate, boolean addActionTypes, boolean addIndex) throws Exception {
		String elementType = type.getType();
		String packageName = TypeUtil.getPackageName(elementType);
		String className = TypeUtil.getClassName(elementType);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		String methodCall = doPopulate ? "create_" : "createEmpty_";
		modelOperation.setName(methodCall+className);
		modelOperation.setResultType(className);
		
		if (doPopulate) {
			if (type instanceof Element) {
				Element element = (Element) type;
				Element container = getContainingElement(element);
				if (container != null) {
					String containerName = NameUtil.uncapName(container.getName());
					String containerClassName = TypeUtil.getClassName(container.getType());
					ModelParameter parameter = CodeUtil.createParameter(containerClassName, containerName);
					modelOperation.addParameter(parameter);	
				}
			}
		
			if (addActionTypes) {
				modelOperation.addParameter(CodeUtil.createParameter("boolean", "cancel"));
				modelOperation.addParameter(CodeUtil.createParameter("boolean", "undo"));
			}
			
			if (addIndex) {
				modelOperation.addParameter(CodeUtil.createParameter("long", "index"));
			}
		}
		
		modelClass.addInstanceOperation(modelOperation);
		modelClass.addImportedClass(packageName + "." + className);
		modelOperation.addInitialSource(createMessageRecordFactory_Source(type, doPopulate, addActionTypes, addIndex));
	}

	protected String createMessageRecordFactory_Source(Type type, boolean doPopulate, boolean addActionTypes, boolean addIndex) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);

		String className = TypeUtil.getClassName(type.getType());
		//if (type.getName().equalsIgnoreCase("OrderRequestMessage"))
			//System.out.println();
		
		boolean contained = false;
		String containerName = null;
		
		Buf buf = new Buf();
		if (doPopulate && type instanceof Element) {
			Element element = (Element) type;
			Element container = getContainingElement(element);
			if (container != null) {
				contained = true;
				containerName = NameUtil.uncapName(container.getName());
			}
		}

		if (doPopulate && !addIndex && !addActionTypes && type instanceof Element) {
			String beanName = NameUtil.uncapName(className);
			if (contained) 
				buf.putLine2(className+" "+beanName+" = create_"+className+"("+containerName+", false, false);");
			else buf.putLine2(className+" "+beanName+" = create_"+className+"(false, false);");
			buf.putLine2("return "+beanName+";");
			return buf.get();
		}
		
		if (addIndex && !addActionTypes) {
			String beanName = NameUtil.uncapName(className);
			if (contained) 
				buf.putLine2(className+" "+beanName+" = create_"+className+"("+containerName+", false, false, index);");
			else buf.putLine2(className+" "+beanName+" = create_"+className+"(false, false, index);");
			buf.putLine2("return "+beanName+";");
			return buf.get();
		}
		
		if (doPopulate && addActionTypes && !addIndex && type instanceof Element) {
			String beanName = NameUtil.uncapName(className);
			if (contained) 
				buf.putLine2(className+" "+beanName+" = create_"+className+"("+containerName+", cancel, undo, 1);");
			else buf.putLine2(className+" "+beanName+" = create_"+className+"(cancel, undo, 1);");
			buf.putLine2("return "+beanName+";");
			return buf.get();
		}
		
		String methodCall = "create_";
		if (doPopulate && addActionTypes)
			methodCall = "createEmpty_";
		else if (!doPopulate && !addActionTypes)
			methodCall = "new ";

		String beanName = NameUtil.uncapName(className);
		buf.putLine2(className+" "+beanName+" = "+methodCall+className+"();");

		if (doPopulate && addActionTypes && addIndex && type instanceof Element) {
			Element element = (Element) type;
			//buf.putLine2("String ext = index == 0 ? \"\" : \"\"+index;");
			buf.putLine2("long value = createValue(index, "+elementNameUncapped+"Count);");
			buf.put(dummyValueFactory.createSampleValuesForAttributes(element, ElementUtil.getAttributes(element)));
			buf.put(dummyValueFactory.createSampleValuesForReferences(element, ElementUtil.getReferences(element)));
			addImportedClassesForAttributes(modelClass, ElementUtil.getAttributes(element));
			addImportedClassesForReferences(modelClass, ElementUtil.getReferences(element));
			buf.putLine2(beanName+".setCancelRequest(cancel);");
			buf.putLine2(beanName+".setUndoRequest(undo);");

			if (ElementUtil.isUserDefined(element) && !ElementUtil.isTransient(element) && ElementUtil.hasIdField(element)) {
				//Long randomId = IdGenerator.createRequestId();
				buf.putLine2(beanName+".setId(value);");
				//buf.putLine2(elementNameUncapped+"Count++;");
			}
		}
		
		if (!doPopulate)
			buf.putLine2(elementNameUncapped+"Count++;");
		buf.putLine2("return "+beanName+";");
		return buf.get();
	}

	protected void createFixtureCollectionFactory(ModelClass modelClass, Type type, boolean isSet, boolean doPopulate, boolean addIndex, boolean addSize) throws Exception {
		String elementType = type.getType();
		String elementName = NameUtil.uncapName(type.getName());
		String packageName = TypeUtil.getPackageName(elementType);
		String className = TypeUtil.getClassName(elementType);
		String structure = isSet ? "set" : "list";
		String structureCapped = NameUtil.capName(structure); 
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		String methodCall = doPopulate ? "create" : "createEmpty";
		modelOperation.setName(methodCall + structureCapped + "_" + className);
		modelOperation.setResultType(structureCapped+"<"+className+">");
		
		boolean contained = false;
		String containerName = null;
		
//		if (className.equals("Preferences"))
//			System.out.println();
		
		if (doPopulate && type instanceof Element) {
			Element element = (Element) type;
			Element container = getContainingElement(element);
			if (container != null) {
				contained = true;
				containerName = NameUtil.uncapName(container.getName());
				String containerClassName = TypeUtil.getClassName(container.getType());
				ModelParameter parameter = CodeUtil.createParameter(containerClassName, containerName);
				modelOperation.addParameter(parameter);	
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
				String source = createFixtureCollectionFactory_Source(className, type, isSet, doPopulate);
				modelOperation.addInitialSource(source);
			} else {
				Buf buf = new Buf();
				if (contained)
					buf.putLine2("return create"+structureCapped+"_"+className+"("+containerName+", 1, size);");
					//buf.putLine2("return create"+className+"_"+structure+"("+containerName+", "+elementName+"Count++, size);");
				else buf.putLine2("return create"+structureCapped+"_"+className+"(1, size);");
				//else buf.putLine2("return create"+className+"_"+structure+"("+elementName+"Count++, size);");
				modelOperation.addInitialSource(buf.get());
			}
			
		} else if (doPopulate) {
			Buf buf = new Buf();
			if (contained)
				buf.putLine2("return "+modelOperation.getName()+"("+containerName+", 2);");
			else buf.putLine2("return "+modelOperation.getName()+"(2);");
			modelOperation.addInitialSource(buf.get());
			
		} else {
			Buf buf = new Buf();
			if (isSet)
				buf.putLine2("return new HashSet<"+className+">();");
			else buf.putLine2("return new ArrayList<"+className+">();");
			modelOperation.addInitialSource(buf.get());
		}
		modelClass.addInstanceOperation(modelOperation);
		modelClass.addImportedClass("java.util.Date");
		modelClass.addImportedClass("java.util.List");
		modelClass.addImportedClass("java.util.Set");
		modelClass.addImportedClass("java.util.ArrayList");
		modelClass.addImportedClass("java.util.HashSet");
		modelClass.addImportedClass("java.util.Iterator");
		modelClass.addImportedClass(packageName + "." + className);
	}

	protected String createFixtureCollectionFactory_Source(String className, Type type, boolean isSet, boolean doPopulate) {
		String structure = isSet ? "Set" : "List";
		
		Buf buf = new Buf();
		String beanName = NameUtil.uncapName(className);
		if (doPopulate) {
			if (isSet) {
				String invocation = doPopulate ? "createEmptySet_"+className+"()" : "new HashSet<"+className+">()";
				buf.putLine2("Set<"+className+"> "+beanName+"Set = "+invocation+";");
			} else {
				String invocation = doPopulate ? "createEmptyList_"+className+"()" : "new ArrayList<"+className+">()";
				buf.putLine2("List<"+className+"> "+beanName+"List = "+invocation+";");
			}
			if (type instanceof Element) {
				Element element = (Element) type;
				
				boolean contained = false;
				buf.putLine2("long limit = index + size;");
				buf.putLine2("for (; index < limit; index++)");
				
				Element container = getContainingElement(element);
				if (container != null) {
					contained = true;
					String containerName = NameUtil.uncapName(container.getName());
					buf.putLine2("	"+beanName+structure+".add(create_"+className+"("+containerName+", index));");
				}
				
				if (!contained) {
					buf.putLine2("	"+beanName+structure+".add(create_"+className+"(index));");
				}
				
				//buf.putLine2("Iterator<"+className+"> iterator = "+beanName+"List.iterator();");
				//buf.putLine2("	"+beanName+"List.add(create"+className+"(index));");
				//buf.putLine2("	"+className+" "+beanName+" = ("+className+") iterator.next();");
				//buf.put(createSampleValuesForReferences(beanName, element.getReferences(), 1));
				//buf.put(createSampleValuesForAttributes(beanName, element.getAttributes(), 1));
				//buf.putLine2("}");
			}

		} else {
			if (isSet)
				buf.putLine2("Set<"+className+"> "+beanName+"Set = new HashSet<"+className+">();");
			else buf.putLine2("List<"+className+"> "+beanName+"List = new ArrayList<"+className+">();");
		}

		buf.putLine2("return "+beanName+structure+";");
		return buf.get();
	}
	
	protected void createFixtureMapFactory(ModelClass modelClass, Type type, boolean populate, boolean addIndex, boolean addSize) throws Exception {
		Element keyElement = context.getElementByName(type.getName()+"Key");
		if (keyElement == null)
			return;
		
		String elementType = type.getType();
		String packageName = TypeUtil.getPackageName(elementType);
		String className = TypeUtil.getClassName(elementType);
		String keyElementType = keyElement.getType();
		String keyPackageName = TypeUtil.getPackageName(keyElementType);
		String keyClassName = TypeUtil.getClassName(keyElementType);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		String methodCall = populate ? "createMap_" : "createEmptyMap_";
		modelOperation.setName(methodCall + className);
		modelOperation.setResultType("Map<"+keyClassName+", "+className+">");
		
		boolean contained = false;
		String containerName = null;
		
		if (populate && type instanceof Element) {
			Element element = (Element) type;
			Element container = getContainingElement(element);
			if (container != null) {
				contained = true;
				containerName = NameUtil.uncapName(container.getName());
				String containerClassName = TypeUtil.getClassName(container.getType());
				ModelParameter parameter = CodeUtil.createParameter(containerClassName, containerName);
				modelOperation.addParameter(parameter);	
			}
		}
		if (addSize) {
			ModelParameter parameter = CodeUtil.createParameter("int", "size");
			modelOperation.addParameter(parameter);	
			String source = createFixtureMapFactory_Source(className, keyClassName, type, populate);
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
		modelClass.addInstanceOperation(modelOperation);
		modelClass.addImportedClass("java.util.Date");
		modelClass.addImportedClass("java.util.Map");
		modelClass.addImportedClass("java.util.HashMap");
		modelClass.addImportedClass("java.util.Iterator");
		modelClass.addImportedClass(packageName + "." + className);
		modelClass.addImportedClass(keyPackageName + "." + keyClassName);
		//if (type instanceof Element)
		//	initializeClass(modelClass, (Element) type);
	}

	protected String createFixtureMapFactory_Source(String className, String keyClassName, Type type, boolean populate) {
		Buf buf = new Buf();
		String beanName = NameUtil.uncapName(className);
		if (populate) {
			String invocation = populate ? "createEmptyMap_"+className+"()" : "new HashMap<"+keyClassName+", "+className+">()";
			buf.putLine2("Map<"+keyClassName+", "+className+"> "+beanName+"Map = "+invocation+";");
			if (type instanceof Element) {
				Element element = (Element) type;
				
				boolean contained = false;
				buf.putLine2("for (long index=1; index < size; index++)");
				
				Element container = getContainingElement(element);
				if (container != null) {
					contained = true;
					String containerName = NameUtil.uncapName(container.getName());
					buf.putLine2("	"+beanName+"Map.put(create_"+keyClassName+"(index), create_"+className+"(index));");
				}
				
				if (!contained) {
					buf.putLine2("	"+beanName+"Map.put(create_"+keyClassName+"(index), create_"+className+"(index));");
				}
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
	protected void createOperation_assertElementExists(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assert"+elementClassName+"Exists");
		modelOperation.addParameter(CodeUtil.createParameter("collection", elementPackageName, elementClassName, elementNameUncapped+"List"));
		modelOperation.addParameter(CodeUtil.createParameter("item", elementPackageName, elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+elementNameUncapped+"List, \""+elementClassName+" list must be specified\");");
		buf.putLine2("Assert.notNull("+elementNameUncapped+", \""+elementClassName+" record must be specified\");");
		buf.putLine2("Iterator<"+elementClassName+"> iterator = "+elementNameUncapped+"List.iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		//buf.putLine2("for (int i=0; iterator.hasNext(); i++) {");
		buf.putLine2("	"+elementClassName+" "+elementNameUncapped+"1 = iterator.next();");
		buf.putLine2("	if ("+elementNameUncapped+"1.equals("+elementNameUncapped+")) {");
		buf.putLine2("		return;");
		buf.putLine2("	}");
		buf.putLine2("}");
		buf.putLine2("throw new AssertionError(\"[Assertion failed] - "+elementClassName+" should exist: \"+"+elementNameUncapped+");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//assertElementCorrect(Element element) 
	protected void createOperation_assertElementCorrect(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assert"+elementClassName+"Correct");
		modelOperation.addParameter(CodeUtil.createParameter("item", elementPackageName, elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		if (ElementUtil.hasIdField(element)) {
			buf.putLine2("long value = "+elementNameUncapped+".getId();");
			//buf.putLine2("long id = "+elementNameUncapped+".getId();");
			//buf.putLine2("long index = getIndexFromId(id);");
			//if (ElementUtil.hasUniqueField(element))
			//	buf.putLine2("long value = getUniqueValueFromId(id);");
			////buf.putLine2("long index = "+elementNameUncapped+".getId() / 10L;");
		}
		
		List<Attribute> attributes = ElementUtil.getAttributes(element);
		List<Reference> references = ElementUtil.getReferences(element);
		
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
				buf.putLine2("assertObjectExists(\""+fieldNameCapped+"\", "+elementNameUncapped+".get"+fieldNameCapped+"());");
			}
	
			//check required references exist
			Iterator<Reference> iterator2 = references.iterator();
			while (iterator2.hasNext()) {
				Reference reference = iterator2.next();
				if (!FieldUtil.isRequired(reference))
					continue;
				String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(reference);
				buf.putLine2("assertObjectExists(\""+fieldNameCapped+"\", "+elementNameUncapped+".get"+fieldNameCapped+"());");
			}
		}
		
		//check attributes have correct values
		Iterator<Attribute> iterator1 = attributes.iterator();
		while (iterator1.hasNext()) {
			Attribute attribute = iterator1.next();
			if (attribute.getName().equalsIgnoreCase("id"))
				continue;
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(attribute);
			if (FieldUtil.isUnique(attribute))
				buf.putLine2("assertObjectCorrect(\""+fieldNameCapped+"\", "+elementNameUncapped+".get"+fieldNameCapped+"(), value);");
			else if (ElementUtil.hasIdField(element))
				buf.putLine2("assertObjectCorrect(\""+fieldNameCapped+"\", "+elementNameUncapped+".get"+fieldNameCapped+"(), value);");
			else buf.putLine2("assertObjectCorrect(\""+fieldNameCapped+"\", "+elementNameUncapped+".get"+fieldNameCapped+"());");
		}

		//check references have correct values
		Iterator<Reference> iterator2 = references.iterator();
		while (iterator2.hasNext()) {
			Reference reference = iterator2.next();
			String fieldClassName = ModelLayerHelper.getFieldClassName(reference);
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(reference);
			String referenceNamespace = TypeUtil.getNamespace(reference.getType());
			
			if (!referenceNamespace.equals(namespace.getUri())) {
				String fixtureClassName = ModelLayerHelper.getModelFixtureClassName(referenceNamespace);
				buf.putLine2(fixtureClassName+".assert"+fieldClassName+"Correct("+elementNameUncapped+".get"+fieldNameCapped+"());");
			} else buf.putLine2("assert"+fieldClassName+"Correct("+elementNameUncapped+".get"+fieldNameCapped+"());");
		}
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//assertElementCorrect(Collection<Element> elements) 
	protected void createOperation_assertElementCorrect2(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assert"+elementClassName+"Correct");
		modelOperation.addParameter(CodeUtil.createParameter("collection", elementPackageName, elementClassName, elementNameUncapped+"List"));
		
		Buf buf = new Buf();
		buf.putLine2("Assert.isTrue("+elementNameUncapped+"List.size() == 2, \""+elementClassName+" count not correct\");");
		buf.putLine2("Iterator<"+elementClassName+"> iterator = "+elementNameUncapped+"List.iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		buf.putLine2("	"+elementClassName+" "+elementNameUncapped+" = iterator.next();");
		buf.putLine2("	assert"+elementClassName+"Correct("+elementNameUncapped+");");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//assertElementsSame(Element element1, Element element2) 
	protected void createOperation_assertElementsSame(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assertSame"+elementClassName+"");
		modelOperation.addParameter(CodeUtil.createParameter("item", elementPackageName, elementClassName, elementNameUncapped+"1"));
		modelOperation.addParameter(CodeUtil.createParameter("item", elementPackageName, elementClassName, elementNameUncapped+"2"));
		
		Buf buf = new Buf();
		if (ElementUtil.isUserDefined(element))
			buf.putLine2("assertSame"+elementClassName+"("+elementNameUncapped+"1, "+elementNameUncapped+"2, false, \"\");");
		else buf.putLine2("assertSame"+elementClassName+"("+elementNameUncapped+"1, "+elementNameUncapped+"2, \"\");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	//assertElementsSame(Element element1, Element element2, String message) 
	protected void createOperation_assertElementsSame2(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assertSame"+elementClassName+"");
		modelOperation.addParameter(CodeUtil.createParameter("item", elementPackageName, elementClassName, elementNameUncapped+"1"));
		modelOperation.addParameter(CodeUtil.createParameter("item", elementPackageName, elementClassName, elementNameUncapped+"2"));
		modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "String", "message"));
		
		Buf buf = new Buf();
		buf.putLine2("assertSame"+elementClassName+"("+elementNameUncapped+"1, "+elementNameUncapped+"2, false, message);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//assertElementsSame(Element element1, Element element2, boolean checkIds) 
	protected void createOperation_assertElementsSame3(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assertSame"+elementClassName+"");
		modelOperation.addParameter(CodeUtil.createParameter("item", elementPackageName, elementClassName, elementNameUncapped+"1"));
		modelOperation.addParameter(CodeUtil.createParameter("item", elementPackageName, elementClassName, elementNameUncapped+"2"));
		modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "boolean", "checkIds"));
		
		Buf buf = new Buf();
		buf.putLine2("assertSame"+elementClassName+"("+elementNameUncapped+"1, "+elementNameUncapped+"2, checkIds, \"\");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//assertElementsSame(Element element1, Element element2, boolean checkIds, String message) 
	protected void createOperation_assertElementsSame4(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assertSame"+elementClassName+"");
		modelOperation.addParameter(CodeUtil.createParameter("item", elementPackageName, elementClassName, elementNameUncapped+"1"));
		modelOperation.addParameter(CodeUtil.createParameter("item", elementPackageName, elementClassName, elementNameUncapped+"2"));
		if (ElementUtil.isUserDefined(element))
			modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "boolean", "checkIds"));
		modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "String", "message"));
		
		Buf buf = new Buf();
		buf.putLine2("assertObjectExists(\""+elementClassName+"1\", "+elementNameUncapped+"1);");
		buf.putLine2("assertObjectExists(\""+elementClassName+"2\", "+elementNameUncapped+"2);");
		//buf.putLine2("Assert.notNull("+elementNameUncapped+"1, \""+elementClassName+"1 must be specified\");");
		//buf.putLine2("Assert.notNull("+elementNameUncapped+"2, \""+elementClassName+"2 must be specified\");");
		
		if (ElementUtil.isUserDefined(element)) {
			buf.putLine2("if (checkIds)");
			buf.putLine2("	assertObjectEquals(\"Id\", "+elementNameUncapped+"1.getId(), "+elementNameUncapped+"2.getId(), message);");
		}
		
		List<Attribute> attributes = ElementUtil.getAttributes(element);
		Iterator<Attribute> iterator1 = attributes.iterator();
		while (iterator1.hasNext()) {
			Attribute attribute = iterator1.next();
			if (attribute.getName().equalsIgnoreCase("id"))
				continue;
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(attribute);
			//String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(attribute);
			buf.putLine2("assertObjectEquals(\""+fieldNameCapped+"\", "+elementNameUncapped+"1.get"+fieldNameCapped+"(), "+elementNameUncapped+"2.get"+fieldNameCapped+"(), message);");
			//buf.putLine2("Assert.equals("+elementNameUncapped+"1.get"+fieldNameCapped+"(), "+elementNameUncapped+"2.get"+fieldNameCapped+"(), message+\": "+fieldNameCapped+" fields not equal: "+fieldNameUncapped+"1=\"+"+elementNameUncapped+"1.get"+fieldNameCapped+"()+\", "+fieldNameUncapped+"2=\"+"+elementNameUncapped+"2.get"+fieldNameCapped+"());");
		}
		
		List<Reference> references = ElementUtil.getReferences(element);
		Iterator<Reference> iterator2 = references.iterator();
		while (iterator2.hasNext()) {
			Reference reference = iterator2.next();
			String fieldNamespace = TypeUtil.getNamespace(reference.getType());
			String fieldClassName = ModelLayerHelper.getFieldClassName(reference);
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(reference);
			
			if (!fieldNamespace.equals(namespace.getUri())) {
				String fixtureClassName = ModelLayerHelper.getModelFixtureClassName(fieldNamespace);
				buf.putLine2(fixtureClassName+".assertSame"+fieldClassName+"("+elementNameUncapped+"1.get"+fieldNameCapped+"(), "+elementNameUncapped+"2.get"+fieldNameCapped+"(), message);");	
			} else buf.putLine2("assertSame"+fieldClassName+"("+elementNameUncapped+"1.get"+fieldNameCapped+"(), "+elementNameUncapped+"2.get"+fieldNameCapped+"(), message);");
			//buf.putLine2("assertObjectEquals(\""+fieldNameCapped+"\", "+elementNameUncapped+"1.get"+fieldNameCapped+"(), "+elementNameUncapped+"1.get"+fieldNameCapped+"(), "+elementNameUncapped+"2.get"+fieldNameCapped+"(), message);");
			
			Module module = context.getModule();
			if (module != null) {
				String moduleNamespace = module.getNamespace();
				if (!fieldNamespace.equals(moduleNamespace)) {
					externalReferences.put(reference.getType(), reference);
				}
			}
		}
		
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	//assertElementsSame(Collection<Element> list1, Collection<Element> list2) 
	protected void createOperation_assertElementsSame5(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assertSame"+elementClassName+"");
		modelOperation.addParameter(CodeUtil.createParameter("collection", elementPackageName, elementClassName, elementNameUncapped+"List1"));
		modelOperation.addParameter(CodeUtil.createParameter("collection", elementPackageName, elementClassName, elementNameUncapped+"List2"));
		
		Buf buf = new Buf();
		if (ElementUtil.isUserDefined(element))
			buf.putLine2("assertSame"+elementClassName+"("+elementNameUncapped+"List1, "+elementNameUncapped+"List2, false, \"\");");
		else buf.putLine2("assertSame"+elementClassName+"("+elementNameUncapped+"List1, "+elementNameUncapped+"List2, \"\");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	//assertElementsSame(Collection<Element> list1, Collection<Element> list2, String message) 
	protected void createOperation_assertElementsSame6(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assertSame"+elementClassName+"");
		modelOperation.addParameter(CodeUtil.createParameter("collection", elementPackageName, elementClassName, elementNameUncapped+"List1"));
		modelOperation.addParameter(CodeUtil.createParameter("collection", elementPackageName, elementClassName, elementNameUncapped+"List2"));
		modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "String", "message"));

		Buf buf = new Buf();
		buf.putLine2("assertSame"+elementClassName+"("+elementNameUncapped+"List1, "+elementNameUncapped+"List2, false, message);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//assertElementsSame(Collection<Element> list1, Collection<Element> list2, boolean checkIds) 
	protected void createOperation_assertElementsSame7(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assertSame"+elementClassName+"");
		modelOperation.addParameter(CodeUtil.createParameter("collection", elementPackageName, elementClassName, elementNameUncapped+"List1"));
		modelOperation.addParameter(CodeUtil.createParameter("collection", elementPackageName, elementClassName, elementNameUncapped+"List2"));
		if (ElementUtil.isUserDefined(element))
			modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "boolean", "checkIds"));
		
		Buf buf = new Buf();
		if (ElementUtil.isUserDefined(element))
			buf.putLine2("assertSame"+elementClassName+"("+elementNameUncapped+"List1, "+elementNameUncapped+"List2, checkIds, \"\");");
		else buf.putLine2("assertSame"+elementClassName+"("+elementNameUncapped+"List1, "+elementNameUncapped+"List2, \"\");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//assertElementsSame(Collection<Element> list1, Collection<Element> list2, boolean checkIds, String message) 
	protected void createOperation_assertElementsSame8(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assertSame"+elementClassName+"");
		modelOperation.addParameter(CodeUtil.createParameter("collection", elementPackageName, elementClassName, elementNameUncapped+"List1"));
		modelOperation.addParameter(CodeUtil.createParameter("collection", elementPackageName, elementClassName, elementNameUncapped+"List2"));
		if (ElementUtil.isUserDefined(element))
			modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "boolean", "checkIds"));
		modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "String", "message"));
		
		String utilClassName = elementClassName+"Util";
		if (ElementUtil.isEventElement(element)) {
			utilClassName = "EventUtil";
			modelClass.addImportedClass("org.aries.event.util.EventUtil");
		} else if (ElementUtil.isMessageElement(element)) {
			utilClassName = "MessageUtil";
			modelClass.addImportedClass("org.aries.message.util.MessageUtil");
		}
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+elementNameUncapped+"List1, \""+elementClassName+" list1 must be specified\");");
		buf.putLine2("Assert.notNull("+elementNameUncapped+"List2, \""+elementClassName+" list2 must be specified\");");
		buf.putLine2("Assert.equals("+elementNameUncapped+"List1.size(), "+elementNameUncapped+"List2.size(), \""+elementClassName+" count not equal\");");
		buf.putLine2("Collection<"+elementClassName+"> sortedRecords1 = "+utilClassName+".sortRecords("+elementNameUncapped+"List1);");
		buf.putLine2("Collection<"+elementClassName+"> sortedRecords2 = "+utilClassName+".sortRecords("+elementNameUncapped+"List2);");
		buf.putLine2("Iterator<"+elementClassName+"> list1Iterator = sortedRecords1.iterator();");
		buf.putLine2("Iterator<"+elementClassName+"> list2Iterator = sortedRecords2.iterator();");
		buf.putLine2("while (list1Iterator.hasNext() && list2Iterator.hasNext()) {");
		buf.putLine2("	"+elementClassName+" "+elementNameUncapped+"1 = list1Iterator.next();");
		buf.putLine2("	"+elementClassName+" "+elementNameUncapped+"2 = list2Iterator.next();");
		if (ElementUtil.isUserDefined(element))
			buf.putLine2("	assertSame"+elementClassName+"("+elementNameUncapped+"1, "+elementNameUncapped+"2, checkIds, message);");
		else buf.putLine2("	assertSame"+elementClassName+"("+elementNameUncapped+"1, "+elementNameUncapped+"2, message);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//assertElementsSame(Collection<Element> list1, Collection<Element> list2) 
	protected void createOperation_assertElementsSame9(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assertSame"+elementClassName+"");
		modelOperation.addParameter(CodeUtil.createParameter("map", elementPackageName, elementClassName+"Key", elementClassName, elementNameUncapped+"Map1"));
		modelOperation.addParameter(CodeUtil.createParameter("map", elementPackageName, elementClassName+"Key", elementClassName, elementNameUncapped+"Map2"));
		
		Buf buf = new Buf();
		buf.putLine2("assertSame"+elementClassName+"("+elementNameUncapped+"Map1, "+elementNameUncapped+"Map2, \"\");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	//assertElementsSame(Collection<Element> list1, Collection<Element> list2, String message) 
	protected void createOperation_assertElementsSame10(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("assertSame"+elementClassName+"");
		modelOperation.addParameter(CodeUtil.createParameter("map", elementPackageName, elementClassName+"Key", elementClassName, elementNameUncapped+"Map1"));
		modelOperation.addParameter(CodeUtil.createParameter("map", elementPackageName, elementClassName+"Key", elementClassName, elementNameUncapped+"Map2"));
		modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "String", "message"));
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+elementNameUncapped+"Map1, \""+elementClassName+" map1 must be specified\");");
		buf.putLine2("Assert.notNull("+elementNameUncapped+"Map2, \""+elementClassName+" map2 must be specified\");");
		buf.putLine2("Assert.isTrue("+elementNameUncapped+"Map1.size() == "+elementNameUncapped+"Map2.size(), \""+elementClassName+" count not correct\");");
		buf.putLine2("Set<"+elementClassName+"Key> keySet = "+elementNameUncapped+"Map1.keySet();");
		buf.putLine2("Iterator<"+elementClassName+"Key> iterator = keySet.iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		buf.putLine2("	"+elementClassName+"Key "+elementNameUncapped+"Key = iterator.next();");
		buf.putLine2("	"+elementClassName+" "+elementNameUncapped+"1 = "+elementNameUncapped+"Map1.get("+elementNameUncapped+"Key);");
		buf.putLine2("	"+elementClassName+" "+elementNameUncapped+"2 = "+elementNameUncapped+"Map2.get("+elementNameUncapped+"Key);");
		//buf.putLine2("	"+elementNameUncapped+"1.equals("+elementNameUncapped+"2);");
		buf.putLine2("	assertSame"+elementClassName+"("+elementNameUncapped+"1, "+elementNameUncapped+"2);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
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
		modelClass.addImportedClass("java.util.Collection");
		modelClass.addImportedClass("org.aries.Assert");
	}

	protected void addImportedClassesForAttributes(ModelClass modelClass, List<Attribute> attributes) {
		Iterator<Attribute> iterator = attributes.iterator();
		while (iterator.hasNext()) {
			Attribute attribute = iterator.next();
			String type = attribute.getType();
			boolean isElement = context.getElementByType(type) != null;
			boolean isEnum = context.getEnumerationByType(type) != null;
			
			if (isElement) {
				Element attributeElement = context.getElementByType(type);
				//String attributePackageName = TypeUtil.getPackageName(type);
				if (attributeElement != null) {
					String fixtureQualifiedName = ModelLayerHelper.getFixtureQualifiedName(type);
					String attributeQualifiedName = ModelLayerHelper.getFieldQualifiedName(attribute);
					modelClass.addImportedClass(fixtureQualifiedName);
					modelClass.addImportedClass(attributeQualifiedName);

					//String attributeFixtureName = FixtureUtil.getFixtureNameFromPackageName(attributePackageName);
					//String attributeClassName = NameUtil.capName(attributeFixtureName);
					//String attributeQualifiedName = attributePackageName + "." + attributeClassName;
					//modelClass.addImportedClass(attributeQualifiedName);
				}
				
			} else if (isEnum) {
				Enumeration attributeEnum = context.getEnumerationByType(type);
				//String attributePackageName = TypeUtil.getPackageName(type);
				if (attributeEnum != null) {
					String fixtureQualifiedName = ModelLayerHelper.getFixtureQualifiedName(type);
					String attributeQualifiedName = ModelLayerHelper.getFieldQualifiedName(attribute);
					modelClass.addImportedClass(fixtureQualifiedName);
					modelClass.addImportedClass(attributeQualifiedName);
				}
			}
		}
	}
	
	protected void addImportedClassesForReferences(ModelClass modelClass, List<Reference> references) {
		Iterator<Reference> iterator = references.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			String type = reference.getType();
			Element referenceElement = context.getElementByType(type);
			//String referencePackageName = TypeUtil.getPackageName(type);
			if (referenceElement != null) {
				String fixtureQualifiedName = ModelLayerHelper.getFixtureQualifiedName(type);
				modelClass.addImportedClass(fixtureQualifiedName);
				
				//String referenceFixtureName = FixtureUtil.getFixtureNameFromPackageName(referencePackageName);
				//String referenceClassName = NameUtil.capName(referenceFixtureName);
				//String referenceQualifiedName = referencePackageName + "." + referenceClassName;
				//modelClass.addImportedClass(referenceQualifiedName);
			}
		}
	}
	
}
