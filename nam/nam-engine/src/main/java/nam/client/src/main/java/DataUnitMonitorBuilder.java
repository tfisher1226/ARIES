package nam.client.src.main.java;

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
import nam.service.src.main.java.AbstractDataUnitManagerBuilder;

import org.aries.util.NameUtil;

import aries.codegen.MethodType;
import aries.codegen.SourceType;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelUnit;


/**
 * Builds a Unit State module {@link ModelClass} object given a {@link Unit} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class DataUnitMonitorBuilder extends AbstractDataUnitManagerBuilder {

	public DataUnitMonitorBuilder(GenerationContext context) {
		super(context);
	}
	
	public List<ModelClass> buildClasses(Process process) throws Exception {
		return buildClasses(process.getNamespace(), process.getDataUnits());
	}

	public List<ModelClass> buildClasses(String namespace, List<Unit> units) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			modelClasses.add(buildClass(namespace, unit));
		}
		return modelClasses;
	}
	
	public ModelClass buildClass(String namespace, Unit unit) throws Exception {
		String packageName = DataLayerHelper.getPersistenceUnitPackageName(namespace, unit);
		String monitorClassName = DataLayerHelper.getPersistenceUnitClassName(unit) + "Monitor";
		String beanName = DataLayerHelper.getPersistenceUnitNameUncapped(unit) + "Monitor";
		String beanType = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, beanName);

		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(monitorClassName);
		ModelClass modelClass = new ModelClass();
		modelClass.setType(beanType);
		modelClass.setPackageName(packageName);
		modelClass.setClassName(monitorClassName);
		modelClass.setName(beanName);
		//modelClass.setNamespace(namespace.getUri());
		modelClass.setParentClassName("AbstractMonitor");
		initializeClass(modelClass, unit);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Unit unit) throws Exception {
		this.modelUnit = modelClass;
		initializeImportedClasses(modelClass);
		initializeInstanceFields(modelClass, unit);
		initializeClassConstructors(modelClass, unit);
		initializeInstanceMethods(modelClass, unit);
	}

	protected void initializeImportedClasses(ModelClass modelClass) throws Exception {
		modelClass.addImportedClass("java.util.Collection");
		modelClass.addImportedClass("common.tx.management.AbstractMonitor");
	}

	protected void initializeInstanceFields(ModelClass modelClass, Unit unit) throws Exception {
		//CodeUtil.addStaticLoggerField(modelClass, className);
	}

	protected void initializeClassConstructors(ModelClass modelClass, Unit unit) throws Exception {
		createConstructor(modelClass, unit);
	}
	
	protected void createConstructor(ModelClass modelClass, Unit unit) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		ModelParameter hostNameParameter = CodeUtil.createParameter_HostName();
		ModelParameter portNumberParameter = CodeUtil.createParameter_PortNumber();
		modelConstructor.addParameter(hostNameParameter);
		modelConstructor.addParameter(portNumberParameter);
		List<String> sourceLines = new ArrayList<String>();
		sourceLines.add("setHost(host);");
		sourceLines.add("setPort(port);");
		sourceLines.add("getURL();");
		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelConstructor.addInitialSource(sourceCode);
		modelClass.addInstanceConstructor(modelConstructor);
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Unit unit) throws Exception {
		createMethod_GetMBeanName(modelClass, unit);
		createMethod_GetMBeanClass(modelClass, unit);
		createMethods_DataAccess(modelClass, unit, SourceType.SharedCache);
	}

	protected void createMethods_DataAccess(ModelUnit modelUnit, Unit unit, SourceType sourceType) throws Exception {
		if (unit != null) {
			List<Element> elements = UnitUtil.getElements(unit);
			if (elements == null || elements.size() == 0) {
				log.warn("No items found in unit: "+unit.getName());
			} else {
				createMethods_DataAccess(modelUnit, elements, sourceType);
			}
		}
	}
	
	protected void createMethod_GetMBeanName(ModelClass modelClass, Unit unit) {
		String className = DataLayerHelper.getPersistenceUnitClassName(unit) + "ManagerMBean";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getMBeanName");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		String sourceCode = CodeUtil.createMethodSource("return "+className+".MBEAN_NAME;\n");
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_GetMBeanClass(ModelClass modelClass, Unit unit) {
		String className = DataLayerHelper.getPersistenceUnitClassName(unit) + "ManagerMBean";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getMBeanClass");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("Class<?>");
		String sourceCode = CodeUtil.createMethodSource("return "+className+".class;\n");
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected boolean isResultRequired(MethodType methodType) {
		if (methodType == MethodType.RemoveAll ||
			methodType == MethodType.RemoveAsItem ||
			methodType == MethodType.RemoveAsItemByKey ||
			methodType == MethodType.RemoveAsListByKeys ||
			methodType == MethodType.RemoveAsList ||
			methodType == MethodType.RemoveMatchingAsList)
				return false;
		return super.isResultRequired(methodType);
	}
	
	protected String getSharedStateSource(ModelUnit modelUnit, ModelOperation modelOperation, MethodType methodType, Type dataItem) {
		modelOperation.addException("Exception");

		//String contextName = ServiceLayerHelper.getProcessContextInstanceName(context.getProcess());
		//String processorName = DataLayerHelper.getCacheUnitNameUncapped(context.getCache()) + "Processor";
		String helperName = ModelLayerHelper.getModelHelperBeanName(dataItem);
		//String cacheName = modelUnit.getName();
		//String cacheName = getBeanName();

		String type = dataItem.getType();
		String keyType = dataItem.getKey();
		String paramName = TypeUtil.getLocalPart(type);
		String typeName = NameUtil.capName(paramName);
		String typePackage = TypeUtil.getPackageName(type);
		String typeQualifiedName = TypeUtil.getQualifiedName(type);
		String keyParamName = null;
		String keyTypeName = null;
		String keyTypePackage = null;
		if (keyType != null) {
			keyParamName = TypeUtil.getLocalPart(keyType);
			keyTypeName = NameUtil.capName(keyParamName);
			keyTypePackage = TypeUtil.getPackageName(keyType);
		}
		String fieldName = NameUtil.capName(dataItem.getName());
		String instanceName = NameUtil.uncapName(fieldName);
		String structure = dataItem.getStructure();
		
		String criteriaClassName = typeName + "." + "Criteria";
		String criteriaQualifiedName = typeQualifiedName + "Criteria";
		
		Buf buf = new Buf();
		switch (methodType) {
		case GetAllAsList:  
			buf.putLine2("return getList(\"getAll"+fieldName+"\");");
			break;
			
		case GetAllAsMap: 
			buf.putLine2("return getMap(\"getAll"+fieldName+"AsMap\");");
			break;
			
		case GetAsItem: 
		case GetAsItemById: 
//			buf.putLine2("String[] parameterType = { \"java.lang.String\" };");
//			buf.putLine2("Object[] parameterValue = { bookKey };");
//			buf.putLine2("return invoke(\"getFrom"+fieldName+"ByKey\", parameterType, parameterValue);");
			buf.putLine2("return getObject(\"getFrom"+fieldName+"\", \"java.lang.Long\", "+paramName+"Id);");
			break;
			
		case GetAsItemByKey: 
//			buf.putLine2("String[] parameterType = { \"java.lang.String\" };");
//			buf.putLine2("Object[] parameterValue = { bookKey };");
//			buf.putLine2("return invoke(\"getFrom"+fieldName+"ByKey\", parameterType, parameterValue);");
			buf.putLine2("return getObject(\"getFrom"+fieldName+"ByKeys\", \"java.lang.String\", "+paramName+"Key);");
			break;
			
		case GetAsList: 
		case GetAsListByIds: 
//			buf.putLine2("String[] parameterType = { \"java.util.List\" };");
//			buf.putLine2("Object[] parameterValue = { bookKeys };");
//			buf.putLine2("return invoke(\"getFrom"+fieldName+"ByKeys\", parameterType, parameterValue);");
			buf.putLine2("return getList(\"getFrom"+fieldName+"\", \"java.util.Collection\", "+paramName+"Ids);");
			break;
			
		case GetAsListByKeys: 
//			buf.putLine2("String[] parameterType = { \"java.util.List\" };");
//			buf.putLine2("Object[] parameterValue = { bookKeys };");
//			buf.putLine2("return invoke(\"getFrom"+fieldName+"ByKeys\", parameterType, parameterValue);");
			buf.putLine2("return getList(\"getFrom"+fieldName+"ByKeys\", \"java.util.Collection\", "+paramName+"Keys);");
			break;
			
		case GetAsMapByKeys: 
//			buf.putLine2("String[] parameterType = { \"java.util.List\" };");
//			buf.putLine2("Object[] parameterValue = { bookKeys };");
//			buf.putLine2("return invoke(\"getFrom"+fieldName+"AsMap\", parameterType, parameterValue);");
			buf.putLine2("return getMap(\"getFrom"+fieldName+"ByKeys\", \"java.util.List\", "+paramName+"Keys);");
			break;
			
		case GetAsListByCriteria: 
			modelUnit.addImportedClass(criteriaQualifiedName);
			buf.putLine2("return getList(\"getFrom"+fieldName+"\", \""+criteriaQualifiedName+"\", "+paramName+"Criteria);");
			break;
			
		case GetMatchingAsList: 
//			buf.putLine2("String[] parameterType = { \"java.util.List\" };");
//			buf.putLine2("Object[] parameterValue = { bookList };");
//			buf.putLine2("return invoke(\"getMatching"+fieldName+"\", parameterType, parameterValue);");
			buf.putLine2("return getList(\"getFrom"+fieldName+"ByKeys\", \"java.util.Collection\", "+paramName+"List);");
			break;
			
		case GetMatchingAsMap: 
			buf.putLine2("Collection<"+keyTypeName+"> "+paramName+"Keys = "+helperName+".create"+typeName+"Keys("+paramName+"List);");
			buf.putLine2("return getMap(\"getFrom"+fieldName+"ByKeys\", \"java.util.Collection\", "+paramName+"Keys);");
			addImportedClassForHelper(modelUnit, dataItem);
			break;
			
		case Set: 
			buf.putLine2("invoke(\"set"+fieldName+"\", \"java.util.List\", "+paramName+"List);");
			break;
			
		case AddAsItem: 
			buf.putLine2("return getObject(\"addTo"+fieldName+"\", \""+typePackage+"."+typeName+"\", "+paramName+");");
			break;
			
		case AddAsList: 
			buf.putLine2("return getList(\"addTo"+fieldName+"\", \"java.util.Collection\", "+paramName+"List);");
			break;
			
		case AddAsMap: 
			buf.putLine2("return getList(\"addTo"+fieldName+"\", \"java.util.Map\", "+paramName+"Map);");
			break;
			
		case RemoveAll: 
			buf.putLine2("invoke(\"removeAll"+fieldName+"\");");
			break;
			
		case RemoveAsItem:
			buf.putLine2("invoke(\"removeFrom"+fieldName+"\", \""+typePackage+"."+typeName+"\", "+paramName+");");
			break;
			
		case RemoveAsItemById:
			buf.putLine2("invoke(\"removeFrom"+fieldName+"\", \"java.lang.Long\", "+paramName+"Id);");
			break;
			
		case RemoveAsItemByKey:
			buf.putLine2("invoke(\"removeFrom"+fieldName+"\", \"java.lang.String\", "+paramName+"Key);");
			break;
			
		case RemoveAsListByKeys:
			buf.putLine2("invoke(\"removeFrom"+fieldName+"\", \"java.util.Collection\", "+paramName+"Keys);");
			break;
			
		case RemoveAsList:
			buf.putLine2("invoke(\"removeFrom"+fieldName+"\", \"java.util.Collection\", "+paramName+"List);");
			break;
			
		case RemoveMatchingAsList:
			buf.putLine2("invoke(\"removeMatching"+fieldName+"\", \"java.util.Collection\", "+paramName+"List);");
			break;
			
		case RemoveAsListByCriteria:
			modelUnit.addImportedClass(criteriaQualifiedName);
			buf.putLine2("invoke(\"removeFrom"+fieldName+"\", \""+criteriaQualifiedName+"\", "+paramName+"Criteria);");
			break;
		}
		return buf.get();
	}
	
}
