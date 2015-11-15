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
import nam.service.ServiceLayerFactory;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;

import aries.codegen.MethodType;
import aries.codegen.SourceType;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelUnit;


/**
 * Builds a DataUnit Manager {@link ModelClass} object given a {@link Unit} Specification as input;
 * 
 * <h3>Properties</h3>
 * The following properties can be used to configure execution of this builder: 
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * <h3>Dependencies</h3>
 * Execution of this builder must come after the following builders: 
 * <ul>
 * <li>ProcessClassBuilder</li>
 * </ul>
 * 
 * @author tfisher
 */
public class DataUnitManagerBuilder extends AbstractDataUnitManagerBuilder {

	public DataUnitManagerBuilder(GenerationContext context) {
		super(context);
	}
	
	protected boolean isDataUnitManager() {
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
		String managerClassName = DataLayerHelper.getPersistenceUnitClassName(unit) + "Manager";
		String beanName = DataLayerHelper.getPersistenceUnitNameUncapped(unit) + "Manager";
		String beanType = TypeUtil.getTypeFromNamespaceAndLocalPart(unit.getNamespace(), beanName);

		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(managerClassName);
		ModelClass modelClass = new ModelClass();
		modelClass.setType(beanType);
		modelClass.setPackageName(packageName);
		modelClass.setClassName(managerClassName);
		modelClass.setName(beanName);
		modelClass.setParentClassName("AbstractStateManager<"+stateClassName+">");
		modelClass.addImplementedInterface(managerClassName+"MBean");
		modelClass.addImportedClass(packageName+"."+managerClassName+"MBean");
		initializeClass(modelClass, unit);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Unit unit) throws Exception {
		this.modelUnit = modelClass;
		initializeImportedClasses(modelClass);
		initializeClassAnnotations(modelClass);
		initializeClassConstructors(modelClass, unit);
		initializeInstanceFields(modelClass, unit);
		initializeInstanceMethods(modelClass, unit);
	}

	protected void initializeClassConstructors(ModelClass modelClass, Unit unit) throws Exception {
		modelClass.addInstanceConstructor(createConstructor(modelClass, unit));
	}
	
	protected ModelConstructor createConstructor(ModelClass modelClass, Unit unit) throws Exception {
		ModelConstructor constructor = new ModelConstructor();
		constructor.setModifiers(Modifier.PUBLIC);
		//String mbeanName = NameUtil.splitStringUsingUnderscores(className) + "_MBEAN_NAME";
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				//"MBeanUtil.registerMBean(this, "+mbeanName.toUpperCase()+");"
				//"setLatestStateFilename("+serviceName+"State.LATEST_STATE_FILENAME);",
				//"setShadowStateFilename("+serviceName+"State.SHADOW_STATE_FILENAME);"
		});
		constructor.addInitialSource(sourceCode);
		return constructor;
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Unit unit) throws Exception {
		CodeUtil.addStaticLoggerField(modelClass, className);
		ServiceLayerFactory.addReference_StatefulContext(modelClass, context.getProcess());
		ServiceLayerFactory.addReference_DataUnit(modelClass, context.getProcess().getNamespace(), unit);
		modelClass.addInstanceAttribute(createStateProcessorAttribute(unit));
		CodeUtil.addTransactionSynchronizationRegistryField(modelClass);
	}

	public ModelAttribute createStateProcessorAttribute(Unit unit) throws Exception {
		String packageName = DataLayerHelper.getPersistenceUnitPackageName(context.getProcess().getNamespace(), unit);
		String className = DataLayerHelper.getPersistenceUnitClassName(unit) + "Processor";
		
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		attribute.setName("stateProcessor");
		//attribute.setValue("new "+className+"()");
		//attribute.setGenerateGetter(true);
		//attribute.setGenerateSetter(true);
		attribute.addAnnotation(AnnotationUtil.createInjectAnnotation());
		return attribute;
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Unit unit) throws Exception {
		createOperation_GetName(modelClass, unit);
		createOperation_RegisterWithJMX(modelClass, unit);
		createOperation_UnregisterWithJMX(modelClass, unit);
		createOperation_CreateState(modelClass, unit);
		createOperation_ResetState(modelClass, unit);
		createOperation_UpdateState(modelClass, unit);
		createOperation_SaveState(modelClass, unit);
		createOperation_CommitState(modelClass, unit);
		createOperation_ClearContext(modelClass, unit);
		createMethods_DataAccess(modelClass, unit, SourceType.SharedCache);
		//createMethods_DataAccess(modelClass, unit, SourceType.CurrentState);
		//createMethods_DataAccess(modelClass, unit, SourceType.PendingState);
		//createMethods_DataAccess(modelClass, unit, SourceType.PreparedState);
	}
	
	protected void createOperation_GetName(ModelClass modelClass, Unit unit) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getName");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				"return \""+modelClass.getClassName()+"\";"
		});
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createOperation_RegisterWithJMX(ModelClass modelClass, Unit unit) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createPostConstructAnnotation());
		modelOperation.setName("registerWithJMX");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				"MBeanUtil.registerMBean(this, MBEAN_NAME);"
		});
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createOperation_UnregisterWithJMX(ModelClass modelClass, Unit unit) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createPreDestroyAnnotation());
		modelOperation.setName("unregisterWithJMX");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				"MBeanUtil.unregisterMBean(MBEAN_NAME);"
		});
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createOperation_CreateState(ModelClass modelClass, Unit unit) throws Exception {
		String packageName = DataLayerHelper.getPersistenceUnitPackageName(context.getProcess().getNamespace(), unit);
		String className = DataLayerHelper.getPersistenceUnitClassName(unit) + "State";
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(packageName+"."+className);
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				"return new "+className+"();"
		});
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createOperation_ResetState(ModelClass modelClass, Unit unit) throws Exception {
		String packageName = DataLayerHelper.getPersistenceUnitPackageName(context.getProcess().getNamespace(), unit);
		String className = DataLayerHelper.getPersistenceUnitClassName(unit) + "State";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("resetState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(packageName+"."+className);
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				className+" state = createState();",
				"return state;"
		});
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createOperation_UpdateState(ModelClass modelClass, Unit unit) throws Exception {
		String packageName = DataLayerHelper.getPersistenceUnitPackageName(context.getProcess().getNamespace(), unit);
		String className = DataLayerHelper.getPersistenceUnitClassName(unit) + "State";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("updateState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		Buf buf = new Buf();
		buf.putLine2("//TODO if this fails then we need to mark global TX as rollback only");
		buf.putLine2("updateState(stateProcessor);");
//		List<Field> fields = ElementUtil.getFields(unit);
//		Iterator<Field> iterator = fields.iterator();
//		while (iterator.hasNext()) {
//			Field field = iterator.next();
//			//String fieldType = field.getType();
//			String fieldName = field.getName();
//			String fieldNameCapped = NameUtil.capName(fieldName);
//			String structure = field.getStructure();
//			//String fieldClassName = TypeUtil.getClassName(fieldType);
//			//String fieldPackageName = TypeUtil.getPackageName(fieldType);
//			if (structure.equals("item"))
//				buf.putLine2(unitName+".set"+fieldNameCapped+"(stateProcessor.getPending"+fieldNameCapped+"());");
//			else buf.putLine2(unitName+".addTo"+fieldNameCapped+"(stateProcessor.getPending"+fieldNameCapped+"());");
//		}
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createOperation_SaveState(ModelClass modelClass, Unit unit) throws Exception {
		String packageName = DataLayerHelper.getPersistenceUnitPackageName(context.getProcess().getNamespace(), unit);
		String className = DataLayerHelper.getPersistenceUnitClassName(unit) + "State";
		String unitName = DataLayerHelper.getPersistenceUnitNameUncapped(context.getUnit());
		String beanName = "state";
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("saveState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("boolean");
		
		ModelParameter modelParameter = CodeUtil.createParameter(className, beanName);
		modelOperation.addParameter(modelParameter);

		Buf buf = new Buf();
		buf.putLine2("try {");

		List<Element> elements = UnitUtil.getElements(unit);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			String name = NameUtil.capName(element.getName());
			buf.putLine2("	"+unitName+".addTo"+name+"(state.getAll"+name+"());");
//			if (structure.equals("item")) {
//				buf.putLine2(beanName+".set"+fieldNameCapped+"("+beanName+".get"+fieldNameCapped+"());");
//			} else if (structure.equals("list")) {
//				buf.putLine2(beanName+".addTo"+fieldNameCapped+"("+beanName+".get"+fieldNameCapped+"());");
//			} else if (structure.equals("map")) {
//				buf.putLine2(beanName+".addTo"+fieldNameCapped+"("+beanName+".get"+fieldNameCapped+"AsMap());");
//			}
		}

		buf.putLine2("	return true;");
		buf.putLine3("");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	return false;");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createOperation_CommitState(ModelClass modelClass, Unit unit) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("commitState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		Buf buf = new Buf();
		buf.putLine2("stateProcessor.updateState(currentState);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createOperation_ClearContext(ModelClass modelClass, Unit unit) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("clearContext");
		modelOperation.setResultType("void");

		String unitName = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		modelOperation.addInitialSource("\t\t"+unitName+".clearContext();\n");
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethods_DataAccess(ModelUnit modelUnit, Unit unit, SourceType sourceType) throws Exception {
		if (unit != null) {
			//if (!StringUtils.isEmpty(unit.getRef()))
			//	unit = ModuleUtil.getCache(context.getModule(), unit.getRef());
			List<Element> elements = UnitUtil.getElements(unit);
			if (elements == null || elements.size() == 0) {
				log.warn("No items found in unit: "+unit.getName());
			//List<Item> items = CacheUtil.getItems(cache);
			//if (items == null || items.size() == 0) {
			//	log.warn("No items found in cache: "+cache.getName());
			} else {
				createMethods_DataAccess(modelUnit, elements, sourceType);
			}
		}
	}
	
	protected boolean isResultRequired(MethodType methodType) {
//		if (methodType == MethodType.RemoveAll ||
//			methodType == MethodType.RemoveAsItem ||
//			methodType == MethodType.RemoveAsItemByKey ||
//			methodType == MethodType.RemoveAsListByKeys ||
//			methodType == MethodType.RemoveAsList ||
//			methodType == MethodType.RemoveMatchingAsList)
//				return false;
		return super.isResultRequired(methodType);
	}
	
	protected String getSharedStateSource(ModelUnit modelUnit, ModelOperation modelOperation, MethodType methodType, Type dataItem) {
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		addImportedClassForHelper(modelUnit, dataItem);

		String contextName = ServiceLayerHelper.getProcessContextInstanceName(context.getProcess());
		String unitName = DataLayerHelper.getPersistenceUnitNameUncapped(context.getUnit());

		String type = dataItem.getType();
		String keyType = dataItem.getKey();
		String helperName = ModelLayerHelper.getModelHelperBeanName(type);
		String paramName = TypeUtil.getLocalPart(type);
		String keyParamName = null;
		String keyTypeName = null;
		if (keyType != null) {
			keyParamName = TypeUtil.getLocalPart(keyType);
			keyTypeName = NameUtil.capName(keyParamName);
		}
		String typeName = NameUtil.capName(paramName);
		String fieldName = NameUtil.capName(dataItem.getName());
		String instanceName = NameUtil.uncapName(fieldName);
		String structure = dataItem.getStructure();
		
		Buf buf = new Buf();
		switch (methodType) {
		case GetAllAsList: 
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	return currentState.getAll"+fieldName+"();"); 
			buf.putLine2("return "+unitName+".getAll"+fieldName+"();");
			break;
			
		case GetAllAsMap: 
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	return currentState.getAll"+fieldName+"AsMap();");
			buf.putLine2("return "+unitName+".getAll"+fieldName+"AsMap();");
			break;
			
		case GetAsItem: 
		case GetAsItemById: 
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	return currentState.getFrom"+fieldName+"("+paramName+"Id);"); 
			buf.putLine2("return "+unitName+".getFrom"+fieldName+"("+paramName+"Id);");
			break;
			
		case GetAsItemByKey: 
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	return currentState.getFrom"+fieldName+"("+paramName+"Key);"); 
			buf.putLine2("return "+unitName+".getFrom"+fieldName+"("+paramName+"Key);");
			break;
			
		case GetAsList: 
		case GetAsListByIds: 
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	return currentState.getFrom"+fieldName+"("+paramName+"Ids);"); 
			buf.putLine2("return "+unitName+".getFrom"+fieldName+"("+paramName+"Ids);");
			break;
			
		case GetAsListByKeys: 
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	return currentState.getFrom"+fieldName+"("+paramName+"Keys);"); 
			buf.putLine2("return "+unitName+".getFrom"+fieldName+"("+paramName+"Keys);");
			break;
			
		case GetAsMapByKeys: 
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	return currentState.getFrom"+fieldName+"AsMap("+paramName+"Keys);"); 
			buf.putLine2("return "+unitName+".getFrom"+fieldName+"AsMap("+paramName+"Keys);");
			break;
			
		case GetAsListByCriteria:
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	return currentState.getFrom"+fieldName+"("+paramName+"Criteria);"); 
			buf.putLine2("return "+unitName+".getFrom"+fieldName+"("+paramName+"Criteria);");
			break;
			
		case GetMatchingAsList: 
			if (structure.equals("list")) {
				String elementType = NameUtil.capName(TypeUtil.getLocalPart(dataItem.getType()));
				String packageName = TypeUtil.getPackageName(dataItem.getType());
				modelUnit.addImportedClass(packageName+"."+elementType + "Criteria");
				
				buf.putLine2("Collection<Long> "+paramName+"Ids = "+helperName+".get"+typeName+"Ids("+paramName+"List);");
				buf.putLine2(typeName+"Criteria "+paramName+"Criteria = new "+typeName+"Criteria();");
				buf.putLine2(paramName+"Criteria.addToIdList("+paramName+"Ids);");
				buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
				buf.putLine2("	return currentState.getMatching"+fieldName+"("+paramName+"List);"); 
				buf.putLine2("return "+unitName+".getMatching"+fieldName+"("+paramName+"List);");
				
			} else if (structure.equals("map")) {
				//buf.putLine2("List<"+keyTypeName+"> "+paramName+"Keys = "+helperName+".create"+typeName+"Keys("+paramName+"List);");
				buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
				buf.putLine2("	return currentState.getMatching"+fieldName+"("+paramName+"List);"); 
				buf.putLine2("return "+unitName+".getMatching"+fieldName+"("+paramName+"List);");
			}
			break;
			
		case GetMatchingAsMap:
			buf.putLine2("Collection<"+keyTypeName+"> "+paramName+"Keys = "+helperName+".create"+typeName+"Keys("+paramName+"List);");
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	return currentState.getFrom"+fieldName+"AsMap("+paramName+"Keys);"); 
			buf.putLine2("return "+unitName+".getFrom"+fieldName+"AsMap("+paramName+"Keys);");
			break;

		case Set: 
			if (structure.equals("map")) paramName += "Map";
			if (structure.equals("list")) paramName += "List";
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	stateProcessor.setPending"+fieldName+"("+paramName+");");
			buf.putLine2("else "+unitName+".set"+fieldName+"("+paramName+");");
			break;
		
		case AddAsItem: 
			if (structure.equals("list")) {
				buf.putLine2("if ("+contextName+".isGlobalTransactionActive()) {");
				buf.putLine2("	stateProcessor.addToPending"+fieldName+"("+paramName+");"); 
				buf.putLine2("	return null;");
				buf.putLine2("} else {");
				buf.putLine2("	return "+unitName+".addTo"+fieldName+"("+paramName+");");
				buf.putLine2("}");
			} else if (structure.equals("map")) {
				//buf.putLine2(keyTypeName+" "+paramName+"Key = "+helperName+".create"+typeName+"Key("+paramName+");");
				buf.putLine2("if ("+contextName+".isGlobalTransactionActive()) {");
				buf.putLine2("	stateProcessor.addToPending"+fieldName+"("+paramName+");"); 
				buf.putLine2("	return null;");
				buf.putLine2("} else {");
				buf.putLine2("	return "+unitName+".addTo"+fieldName+"("+paramName+");");
				buf.putLine2("}");
			}
			break;
			
		case AddAsList: 
			if (structure.equals("list")) {
				buf.putLine2("if ("+contextName+".isGlobalTransactionActive()) {");
				buf.putLine2("	stateProcessor.addToPending"+fieldName+"("+paramName+"List);");
				buf.putLine2("	return null;");
				buf.putLine2("} else {");
				buf.putLine2("	return "+unitName+".addTo"+fieldName+"("+paramName+"List);");
				buf.putLine2("}");
			} else if (structure.equals("map")) {
				buf.putLine2("if ("+contextName+".isGlobalTransactionActive()) {");
				buf.putLine2("	Map<"+keyTypeName+", "+typeName+"> "+paramName+"Map = "+helperName+".create"+typeName+"Map("+paramName+"List);");
				buf.putLine2("	stateProcessor.addToPending"+fieldName+"("+paramName+"Map);");
				buf.putLine2("	return null;");
				buf.putLine2("} else {");
				buf.putLine2("	return "+unitName+".addTo"+fieldName+"("+paramName+"List);");
				buf.putLine2("}");
			}
			break;
			
		case AddAsMap: 
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive()) {");
			buf.putLine2("	stateProcessor.addToPending"+fieldName+"("+paramName+"Map);");
			buf.putLine2("	return null;");
			buf.putLine2("} else {");
			buf.putLine2("	return "+unitName+".addTo"+fieldName+"("+paramName+"Map);");
			buf.putLine2("}");
			break;
			
		case RemoveAll: 
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	stateProcessor.removeAllPending"+fieldName+"();"); 
			buf.putLine2("else "+unitName+".removeAll"+fieldName+"();");
			break;
			
		case RemoveAsItem:
			if (structure.equals("list")) {
				buf.putLine2("if ("+contextName+".isGlobalTransactionActive()) {");
				buf.putLine2("	stateProcessor.removeFromPending"+fieldName+"("+paramName+");"); 
				buf.putLine2("} else {"); 
				buf.putLine2("	"+unitName+".removeFrom"+fieldName+"("+paramName+");");
				buf.putLine2("}");
			} else if (structure.equals("map")) {
				buf.putLine2("if ("+contextName+".isGlobalTransactionActive()) {");
				buf.putLine2("	"+keyTypeName+" "+paramName+"Key = "+helperName+".create"+typeName+"Key("+paramName+");");
				buf.putLine2("	stateProcessor.removeFromPending"+fieldName+"("+paramName+"Key);"); 
				buf.putLine2("} else {"); 
				buf.putLine2("	"+unitName+".removeFrom"+fieldName+"("+paramName+");");
				buf.putLine2("}");
			}
			break;
			
		case RemoveAsItemById:
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	stateProcessor.removeFromPending"+fieldName+"("+paramName+"Id);");
			buf.putLine2("else "+unitName+".removeFrom"+fieldName+"("+paramName+"Id);");
			break;
			
		case RemoveAsItemByKey:
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	stateProcessor.removeFromPending"+fieldName+"("+paramName+"Key);");
			buf.putLine2("else "+unitName+".removeFrom"+fieldName+"("+paramName+"Key);");
			break;
			
		case RemoveAsListByKeys:
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	stateProcessor.removeFromPending"+fieldName+"("+paramName+"Keys);");
			buf.putLine2("else "+unitName+".removeFrom"+fieldName+"("+paramName+"Keys);");
			break;
			
		case RemoveAsList:
			if (structure.equals("list")) {
				buf.putLine2("if ("+contextName+".isGlobalTransactionActive()) {");
				buf.putLine2("	stateProcessor.removeFromPending"+fieldName+"("+paramName+"List);");
				buf.putLine2("} else {");
				buf.putLine2("	"+unitName+".removeFrom"+fieldName+"("+paramName+"List);");
				buf.putLine2("}");
			} else if (structure.equals("map")) {
				buf.putLine2("if ("+contextName+".isGlobalTransactionActive()) {");
				//buf.putLine2("	List<"+keyTypeName+"> "+paramName+"Keys = "+helperName+".create"+typeName+"Keys("+paramName+"List);");
				buf.putLine2("	stateProcessor.removeFromPending"+fieldName+"("+paramName+"List);");
				buf.putLine2("} else {");
				buf.putLine2("	"+unitName+".removeFrom"+fieldName+"("+paramName+"List);");
				buf.putLine2("}");
			}
			break;
			
		case RemoveAsListByCriteria:
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive()) {");
			buf.putLine2("	stateProcessor.removeFromPending"+fieldName+"("+paramName+"Criteria);");
			buf.putLine2("} else {");
			buf.putLine2("	"+unitName+".removeFrom"+fieldName+"("+paramName+"Criteria);");
			buf.putLine2("}");
			break;
			
		case RemoveMatchingAsList:
			if (structure.equals("list")) {
				buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
				buf.putLine2("	stateProcessor.removeFromPending"+fieldName+"("+paramName+"List);"); 
				buf.putLine2("else "+unitName+".removeFrom"+fieldName+"("+paramName+"List);");
			} else if (structure.equals("map")) {
				buf.putLine2("List<"+keyTypeName+"> "+paramName+"Keys = "+helperName+".create"+typeName+"Keys("+paramName+"List);");
				buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
				buf.putLine2("	stateProcessor.removeFromPending"+fieldName+"("+paramName+"Keys);"); 
				buf.putLine2("else "+unitName+".removeFrom"+fieldName+"("+paramName+"Keys);");
			}
			break;
		}
		return buf.get();
	}

}
