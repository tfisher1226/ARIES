package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Element;
import nam.model.ModelLayerHelper;
import nam.model.Process;
import nam.model.Type;
import nam.model.Unit;
import nam.model.util.TypeUtil;
import nam.model.util.UnitUtil;

import org.aries.util.NameUtil;

import aries.codegen.MethodType;
import aries.codegen.SourceType;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelUnit;


/**
 * Builds a DataUnit State Processor {@link ModelClass} object given a {@link Unit} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class DataUnitStateProcessorBuilder extends AbstractDataUnitStateProcessorBuilder {

	public DataUnitStateProcessorBuilder(GenerationContext context) {
		super(context);
	}

	protected boolean isCacheUnitRelated() {
		return true;
	}
	
	public List<ModelClass> buildClasses(Process process) throws Exception {
		return buildClasses(process.getNamespace(), process.getDataUnits());
	}
	
	public List<ModelClass> buildClasses(String namespace, List<Unit> units) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			context.setUnit(unit);
			modelClasses.add(buildClass(namespace, unit));
		}
		return modelClasses;
	}
	
	public ModelClass buildClass(String namespace, Unit unit) throws Exception {
		String packageName = DataLayerHelper.getPersistenceUnitPackageName(namespace, unit);
		String stateClassName = DataLayerHelper.getPersistenceUnitClassName(unit) + "State";
		String processorClassName = DataLayerHelper.getPersistenceUnitClassName(unit) + "Processor";
		String beanName = DataLayerHelper.getPersistenceUnitNameUncapped(unit) + "Processor";
		String beanType = TypeUtil.getTypeFromNamespaceAndLocalPart(unit.getNamespace(), beanName);

		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(processorClassName);
		ModelClass modelClass = new ModelClass();
		modelClass.setType(beanType);
		modelClass.setPackageName(packageName);
		modelClass.setClassName(processorClassName);
		modelClass.setName(beanName);
		modelClass.addImplementedInterface("ServiceStateProcessor<"+stateClassName+">");
		modelClass.addImportedClass("common.tx.state.ServiceStateProcessor");
		initializeClass(modelClass, unit);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Unit unit) throws Exception {
		initializeImportedClasses(modelClass);
		initializeClassAnnotations(modelClass);
		initializeClassConstructors(modelClass, unit);
		initializeInstanceFields(modelClass, unit);
		initializeInstanceMethods(modelClass, unit);
	}

	protected void initializeClassAnnotations(ModelClass modelClass) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		switch (context.getServiceLayerBeanType()) {
		case EJB:
			classAnnotations.add(AnnotationUtil.createStartupAnnotation());
			classAnnotations.add(AnnotationUtil.createSingletonAnnotation());
			classAnnotations.add(AnnotationUtil.createLocalBeanAnnotation());
			classAnnotations.add(AnnotationUtil.createConcurrencyManagementAnnotation());
			//classAnnotations.add(AnnotationUtil.createTransactionAttributeAnnotation());
			//classAnnotations.add(AnnotationUtil.createTransactionManagementAnnotation());
			break;
			
		case SEAM:
			break;
		}
	}
	
	protected void initializeClassConstructors(ModelClass modelClass, Unit unit) throws Exception {
		ModelConstructor constructor = new ModelConstructor();
		constructor.setModifiers(Modifier.PUBLIC);
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				"// nothing for now"
		});
		constructor.addInitialSource(sourceCode);
		modelClass.addInstanceConstructor(constructor);
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Unit unit) throws Exception {
		CodeUtil.addStaticLoggerField(modelClass, className);
		
		List<Element> elements = UnitUtil.getElements(unit);
		Iterator<Element> iterator = elements.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Element item = iterator.next();
			String type = item.getType();
			String name = NameUtil.capName(item.getName());
			String packageName = util.getPackageNameFromType(type);
			String className = util.getClassNameFromType(type);
			String structure = item.getStructure();

			ModelAttribute modelAttribute = new ModelAttribute();
			modelAttribute.setModifiers(Modifier.PRIVATE);
			modelAttribute.setName("pending"+name);
			modelAttribute.setClassName(className);
			modelAttribute.setStructure(structure);
			modelAttribute.setGenerateGetter(false);
			modelAttribute.setGenerateSetter(false);
			modelClass.addImportedClass(packageName+"."+className);
			modelClass.addInstanceAttribute(modelAttribute);

			if (structure.equals("item")) {
				//nothing for now
				
			} else if (structure.equals("list")) {
				modelAttribute.setDefault("new ArrayList<"+className+">()");
				//modelAttribute.setGenerateAddMethod(true);
				//modelAttribute.setGenerateRemoveMethod(true);
				//modelAttribute.setGenerateClearMethod(true);
				modelClass.addImportedClass("java.util.List");
				modelClass.addImportedClass("java.util.ArrayList");
				//modelClass.addImportedClass("java.util.Collection");
				modelClass.addImportedClass(packageName+"."+className);

			} else if (structure.equals("set")) {
				modelAttribute.setDefault("new HashSet<"+className+">()");
				//modelAttribute.setGenerateAddMethod(true);
				//modelAttribute.setGenerateRemoveMethod(true);
				//modelAttribute.setGenerateClearMethod(true);
				modelClass.addImportedClass("java.util.Set");
				modelClass.addImportedClass("java.util.HashSet");
				//modelClass.addImportedClass("java.util.Collection");
				modelClass.addImportedClass(packageName+"."+className);

			} else if (structure.equals("map")) {
				String key = item.getKey();
				String keyPackageName = util.getPackageNameFromType(key);
				String keyClassName = util.getClassNameFromType(key);
				modelAttribute.setKeyPackageName(keyPackageName);
				modelAttribute.setKeyClassName(keyClassName);
				modelAttribute.setDefault("new LinkedHashMap<"+keyClassName+", "+className+">()");
				//modelAttribute.setGenerateAddMethod(true);
				//modelAttribute.setGenerateRemoveMethod(true);
				//modelAttribute.setGenerateClearMethod(true);
				//modelClass.addImportedClass("java.util.ArrayList");
				modelClass.addImportedClass("java.util.Iterator");
				modelClass.addImportedClass("java.util.LinkedHashMap");
				modelClass.addImportedClass("java.util.List");
				modelClass.addImportedClass("java.util.Map");
				modelClass.addImportedClass(packageName+"."+className);
				modelClass.addImportedClass(keyPackageName+"."+keyClassName);
			}
		}
	}

	protected ModelAttribute createAttribute_SerialVersionUID() {
		ModelAttribute staticAttribute = new ModelAttribute();
		staticAttribute.setModifiers(Modifier.PUBLIC+Modifier.FINAL+Modifier.STATIC);
		staticAttribute.setClassName("long");
		staticAttribute.setName("serialVersionUID");
		staticAttribute.setDefault(1L);
		return staticAttribute;
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Unit unit) throws Exception {
		createMethod_ResetState(modelClass, unit);
		createMethod_ValidateState(modelClass, unit);
		createMethod_UpdateState(modelClass, unit);
		createMethod_ProcessRequest(modelClass, unit);
		
		List<Element> elements = UnitUtil.getElements(unit);
		createMethods_DataAccess(modelClass, elements, SourceType.SharedCache);
	}
	
	protected void createMethod_ResetState(ModelClass modelClass, Unit unit) throws Exception {
		String stateClassName = DataLayerHelper.getPersistenceUnitClassName(unit) + "State";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("resetState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		ModelParameter modelParameter = CodeUtil.createParameter(stateClassName, "state");
		modelOperation.addParameter(modelParameter);

		List<String> sourceLines = new ArrayList<String>();
		List<Element> elements = UnitUtil.getElements(unit);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element item = iterator.next();
			String structure = item.getStructure();
			String name = NameUtil.capName(item.getName());
			if (structure.equals("item")) {
				sourceLines.add("state.set"+name+"(null);");
			} else {
				sourceLines.add("state.removeAll"+name+"();");
			}
		}
		
		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_ValidateState(ModelClass modelClass, Unit unit) throws Exception {
		String stateClassName = DataLayerHelper.getPersistenceUnitClassName(unit) + "State";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("validateState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(boolean.class.getName());
		ModelParameter modelParameter = CodeUtil.createParameter(stateClassName, "state");
		modelOperation.addParameter(modelParameter);
		
		Buf buf = new Buf();
		List<Element> elements = UnitUtil.getElements(unit);
		if (elements.size() == 0) {
			buf.putLine2("return false;");
		} else {
			buf.put2("return ");
			Iterator<Element> iterator = elements.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				Element item = iterator.next();
				String name = NameUtil.capName(item.getName());
				if (i > 0)
					buf.put(" && ");
				buf.put("pending"+name+" != null");
			}
			buf.putLine(";");
		}
		
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_UpdateState(ModelClass modelClass, Unit unit) throws Exception {
		String stateClassName = DataLayerHelper.getPersistenceUnitClassName(unit) + "State";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("updateState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		ModelParameter modelParameter = CodeUtil.createParameter(stateClassName, "state");
		modelOperation.addParameter(modelParameter);

		List<String> sourceLines = new ArrayList<String>();
		List<Element> elements = UnitUtil.getElements(unit);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element item = iterator.next();
			//String itemType = item.getType();
			//String itemName = item.getName();
			//String className = NameUtil.getLocalNameFromXSDType(itemType);
			String itemNameCapped = NameUtil.capName(item.getName());
			String pendingItemName = "pending"+itemNameCapped;
			String structure = item.getStructure();
			if (structure.equals("item")) {
				sourceLines.add("state.set"+itemNameCapped+"("+pendingItemName+");");
				//sourceLines.add("state.set"+itemNameCapped+"("+pendingItemName+");");
			} else if (structure.equals("list")) {
				sourceLines.add("state.addTo"+itemNameCapped+"("+pendingItemName+");");
				//sourceLines.add("state.get"+itemNameCapped+"().addAll("+pendingItemName+");");
			} else if (structure.equals("set")) {
				sourceLines.add("state.addTo"+itemNameCapped+"("+pendingItemName+");");
				//sourceLines.add("state.get"+itemNameCapped+"().addAll("+pendingItemName+");");
			} else if (structure.equals("map")) {
				//sourceLines.add("state.get"+name+"().clear();");
				//sourceLines.add("List<"+className+"> "+itemName+" = "+pendingItemName+".getElements();");
				//sourceLines.add("state.get"+itemNameCapped+"().putAll("+pendingItemName+");");
				sourceLines.add("state.addTo"+itemNameCapped+"("+pendingItemName+");");
			}
		}

		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_ProcessRequest(ModelClass modelClass, Unit unit) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("processRequest");
		modelOperation.setModifiers(Modifier.PUBLIC);
		//modelOperation.setResultType(packageName+"."+serviceName+"State");
		modelOperation.setResultType("void");

		/*
		//TODO Assuming only one service operation exists for now
		modelOperation.addParameter(CodeUtil.createParameter_CorrelationId());
		Operation operation = service.getOperations().get(0);
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			modelOperation.addParameter(CodeUtil.createParameter(parameters, parameter));
			modelClass.addImportedClass(TypeUtil.getJavaName(parameter.getType()));
		}
		
		List<String> sourceLines = new ArrayList<String>();
		Unit unit = service.getProcess();
		Unit unit = service.getCache();
		if (process == null || unit == null) {
			System.out.println();
			
		} else {
			String processName = process.getName();
			String unitName = unit.getName();
			//sourceLines.add("pendingBookOrders = request.getBookOrders();");
			//sourceLines.add("ProcessLocator processLocator = BeanContext.get(\"org.aries.processLocator\");");
			//sourceLines.add(processName+" process = processLocator.lookup("+processName+".class, correlationId);");
			//sourceLines.add("process.receive"+serviceName+"(pendingBookOrders);");
		}

		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelOperation.addInitialSource(sourceCode);
		*/
		
		modelClass.addInstanceOperation(modelOperation);
	}

	protected String getSharedStateSource(ModelUnit modelUnit, ModelOperation modelOperation, MethodType methodType, Type dataItem) {
		String type = dataItem.getType();
		String keyType = dataItem.getKey();
		String paramName = TypeUtil.getLocalPart(type);
		String keyParamName = null;
		String keyTypeName = null;
		if (keyType != null) {
			keyParamName = TypeUtil.getLocalPart(keyType);
			keyTypeName = NameUtil.capName(keyParamName);
		}
		String typeName = NameUtil.capName(paramName);
		String fieldName = NameUtil.capName(dataItem.getName());
		String helperName = ModelLayerHelper.getModelHelperBeanName(type);
		String structure = dataItem.getStructure();

		modelUnit.addImportedClass("java.util.ArrayList");
		if (structure.equals("map"))
			modelUnit.addImportedClass("java.util.HashMap");
		addImportedClassForHelper(modelUnit, dataItem);

		Buf buf = new Buf();
		switch (methodType) {
		case GetAsList: 
		case GetAllAsList: 
			buf.putLine2("synchronized (pending"+fieldName+") {");
			if (!structure.equals("map"))
				buf.putLine2("	List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">(pending"+fieldName+");");
			else buf.putLine2("	List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">(pending"+fieldName+".values());");
			buf.putLine2("	return "+paramName+"List;");
			buf.putLine2("}");
			break;
			
		case GetAllAsMap: 
			buf.putLine2("synchronized (pending"+fieldName+") {");
			buf.putLine2("	Map<"+keyTypeName+", "+typeName+"> "+paramName+"Map = new HashMap<"+keyTypeName+", "+typeName+">(pending"+fieldName+");");
			buf.putLine2("	return "+paramName+"Map;");
			buf.putLine2("}");
			break;

		case GetAsItemById: 
			buf.putLine2("synchronized (pending"+fieldName+") {");
			if (!structure.equals("map"))
				buf.putLine2("	Iterator<"+typeName+"> iterator = pending"+fieldName+".iterator();");
			else buf.putLine2("	Iterator<"+typeName+"> iterator = pending"+fieldName+".values().iterator();");
			buf.putLine2("	while (iterator.hasNext()) {");
			buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
			buf.putLine2("		Long id = "+paramName+".getId();");
			buf.putLine2("		if (id != null && id.equals("+paramName+"Id)) {");
			buf.putLine2("			return "+paramName+";");
			buf.putLine2("		}");
			buf.putLine2("	}");
			buf.putLine2("	return null;");
			buf.putLine2("}");
			break;
			
		case GetAsListByIds:
			buf.putLine2("return null;");
			break;
			
		case GetAsItemByKey: 
			buf.putLine2("synchronized (pending"+fieldName+") {");
			buf.putLine2("	return pending"+fieldName+".get("+paramName+"Key);");
			buf.putLine2("}");
			break;
			
		case GetAsMapByKeys: 
			buf.putLine2("synchronized (pending"+fieldName+") {");
			buf.putLine2("	return pending"+fieldName+";");
			buf.putLine2("}");
			break;
			
		case GetAsListByCriteria: 
			buf.putLine2("synchronized (pending"+fieldName+") {");
			buf.putLine2("	List<"+typeName+"> list = new ArrayList<"+typeName+">();");
			if (!structure.equals("map"))
				buf.putLine2("	Iterator<"+typeName+"> iterator = pending"+fieldName+".iterator();");
			else buf.putLine2("	Iterator<"+typeName+"> iterator = pending"+fieldName+".values().iterator();");
			buf.putLine2("	while (iterator.hasNext()) {");
			buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
			buf.putLine2("		if ("+helperName+".isMatch("+paramName+"Criteria, "+paramName+")) {");
			buf.putLine2("			list.add("+paramName+");");
			buf.putLine2("		}");
			buf.putLine2("	}");
			buf.putLine2("	return list;");
			buf.putLine2("}");
			break;
			
		case Set: 
			buf.putLine2("synchronized (pending"+fieldName+") {");
			if (structure.equals("item")) {
				buf.putLine2("	pending"+fieldName+" = "+fieldName+";"); 
			} else if (structure.equals("list")) {
				buf.putLine2("	pending"+fieldName+" = new ArrayList<"+typeName+">("+paramName+"List);"); 
			} else if (structure.equals("set")) {
				buf.putLine2("	pending"+fieldName+" = new HashSet<"+typeName+">("+paramName+"Set);"); 
			} else if (structure.equals("map")) {
				buf.putLine2("	pending"+fieldName+" = new LinkedHashMap<"+keyTypeName+", "+typeName+">("+paramName+"Map);"); 
			}
			buf.putLine2("}");
			break;
			
		case AddAsItem: 
			if (structure.equals("item")) {
			} else if (structure.equals("list")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	pending"+fieldName+".add("+paramName+");"); 
				buf.putLine2("}");
			} else if (structure.equals("set")) {
			} else if (structure.equals("map")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	"+keyTypeName+" "+paramName+"Key = "+helperName+".create"+typeName+"Key("+paramName+");");
				buf.putLine2("	pending"+fieldName+".put("+paramName+"Key, "+paramName+");"); 
				buf.putLine2("}");
			}
			break;
			
		case AddAsList: 
			if (structure.equals("map")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	Map<"+keyTypeName+", "+typeName+"> "+paramName+"Map = "+helperName+".create"+typeName+"Map("+paramName+"List);");
				buf.putLine2("	pending"+fieldName+".putAll("+paramName+"Map);"); 
				buf.putLine2("}");
			} else {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	pending"+fieldName+".addAll("+paramName+"List);"); 
				buf.putLine2("}");
			}
			break;
			
		case AddAsMap: 
			buf.putLine2("synchronized (pending"+fieldName+") {");
			buf.putLine2("	pending"+fieldName+".putAll("+paramName+"Map);"); 
			buf.putLine2("}");
			break;
			
		case RemoveAll: 
			buf.putLine2("synchronized (pending"+fieldName+") {");
			//buf.putLine2("	List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">();");
			//buf.putLine2("	"+paramName+"List.addAll(pending"+fieldName+".values());");
			buf.putLine2("	pending"+fieldName+".clear();");
			//buf.putLine2("	return "+paramName+"List;");
			buf.putLine2("}");
			break;
			
		case RemoveAsItem:
			if (structure.equals("map")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	"+keyTypeName+" "+paramName+"Key = "+helperName+".create"+typeName+"Key("+paramName+");");
				buf.putLine2("	pending"+fieldName+".remove("+paramName+"Key);"); 
				buf.putLine2("}");
			} else {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	pending"+fieldName+".remove("+paramName+");"); 
				buf.putLine2("}");
			}
			break;
			
		case RemoveAsItemById:
			buf.putLine2("synchronized (pending"+fieldName+") {");
			buf.putLine2("	"+typeName+" "+paramName+" = getFromPending"+fieldName+"("+paramName+"Id);");
			buf.putLine2("	if ("+paramName+" != null) {");
			buf.putLine2("		removeFromPending"+fieldName+"("+paramName+");");
			buf.putLine2("	}");
			buf.putLine2("}");
			break;
			
		case RemoveAsItemByKey:
			buf.putLine2("synchronized (pending"+fieldName+") {");
			buf.putLine2("	pending"+fieldName+".remove("+paramName+"Key);"); 
			buf.putLine2("}");
			break;
			
		case RemoveAsListByKeys:
			buf.putLine2("synchronized (pending"+fieldName+") {");
			buf.putLine2("	Iterator<"+keyTypeName+"> iterator = "+paramName+"Keys.iterator();");
			buf.putLine2("	while (iterator.hasNext()) {");
			buf.putLine2("		"+keyTypeName+" key = iterator.next();");
			buf.putLine2("		pending"+fieldName+".remove(key);");
			buf.putLine2("	}");
			buf.putLine2("}");
			break;
			
		case RemoveAsList:
			if (structure.equals("item")) {
			} else if (structure.equals("list")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	pending"+fieldName+".removeAll("+paramName+"List);"); 
				buf.putLine2("}");
			} else if (structure.equals("set")) {
			} else if (structure.equals("map")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	Collection<"+keyTypeName+"> "+paramName+"Keys = "+helperName+".create"+typeName+"Keys("+paramName+"List);");
				//buf.putLine2("	Set<"+keyTypeName+"> keySet = pending"+fieldName+".keySet();");
				buf.putLine2("	Iterator<"+keyTypeName+"> iterator = "+paramName+"Keys.iterator();");
				buf.putLine2("	while (iterator.hasNext()) {");
				buf.putLine2("		"+keyTypeName+" key = iterator.next();");
				buf.putLine2("		pending"+fieldName+".remove(key);");
				buf.putLine2("	}");
				buf.putLine2("}");
			}
			break;
			
		case RemoveAsListByCriteria:
			buf.putLine2("synchronized (pending"+fieldName+") {");
			buf.putLine2("	List<"+typeName+"> "+paramName+"List = getFromPending"+fieldName+"("+paramName+"Criteria);");
			buf.putLine2("	removeFromPending"+fieldName+"("+paramName+"List);");
			buf.putLine2("}");
			break;
		}
		return buf.get();
	}

}
