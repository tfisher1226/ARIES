package nam.client.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Cache;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Process;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;
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
 * Builds a Cache Monitor module {@link ModelClass} object given a {@link Cache} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class CacheUnitMonitorBuilder extends AbstractDataUnitManagerBuilder {

	public CacheUnitMonitorBuilder(GenerationContext context) {
		super(context);
	}
	
	public List<ModelClass> buildClasses(Process process) throws Exception {
		return buildClasses(process.getNamespace(), process.getCacheUnits());
	}

	public List<ModelClass> buildClasses(String namespace, List<Cache> cacheUnits) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cacheUnit = iterator.next();
			modelClasses.add(buildClass(namespace, cacheUnit));
		}
		return modelClasses;
	}
	
	public ModelClass buildClass(String namespace, Cache cache) throws Exception {
		String packageName = DataLayerHelper.getCacheUnitPackageName(namespace, cache);
		String monitorClassName = DataLayerHelper.getCacheUnitInterfaceName(cache) + "Monitor";
		String beanName = DataLayerHelper.getCacheUnitNameUncapped(cache) + "Monitor";
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
		initializeClass(modelClass, cache);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Cache cache) throws Exception {
		this.modelUnit = modelClass;
		initializeImportedClasses(modelClass);
		initializeInstanceFields(modelClass, cache);
		initializeClassConstructors(modelClass, cache);
		initializeInstanceMethods(modelClass, cache);
	}

	protected void initializeImportedClasses(ModelClass modelClass) throws Exception {
		modelClass.addImportedClass("java.util.Collection");
		modelClass.addImportedClass("common.tx.management.AbstractMonitor");
	}

	protected void initializeInstanceFields(ModelClass modelClass, Cache cache) throws Exception {
		//CodeUtil.addStaticLoggerField(modelClass, className);
	}

	protected void initializeClassConstructors(ModelClass modelClass, Cache cache) throws Exception {
		createConstructor(modelClass, cache);
	}
	
	protected void createConstructor(ModelClass modelClass, Cache cache) {
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

	protected void initializeInstanceMethods(ModelClass modelClass, Cache cache) throws Exception {
		createMethod_GetMBeanName(modelClass, cache);
		createMethod_GetMBeanClass(modelClass, cache);
		createMethods_DataAccess(modelClass, cache, SourceType.SharedCache);
	}

//	protected String getDataAccessMethodPrefix(MethodType methodType, String structure) {
//		switch (methodType) {
//		case GetAllAsList:
//		case GetAllAsMap: 
//			return "get";
//		default: 
//			String prefix = super.getDataAccessMethodPrefix(methodType, structure);
//			return prefix;
//		}
//	}
	
	protected void createMethods_DataAccess(ModelUnit modelUnit, Cache cache, SourceType sourceType) throws Exception {
		if (cache != null) {
			List<Field> fields = ElementUtil.getFields(cache);
			if (fields == null || fields.size() == 0) {
				log.warn("No items found in cache: "+cache.getName());
			} else {
				createMethods_DataAccess(modelUnit, fields, sourceType);
			}
		}
	}
	
	protected void createMethod_GetMBeanName(ModelClass modelClass, Cache cache) {
		String className = DataLayerHelper.getCacheUnitInterfaceName(cache) + "ManagerMBean";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getMBeanName");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		String sourceCode = CodeUtil.createMethodSource("return "+className+".MBEAN_NAME;\n");
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_GetMBeanClass(ModelClass modelClass, Cache cache) {
		String className = DataLayerHelper.getCacheUnitInterfaceName(cache) + "ManagerMBean";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getMBeanClass");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("Class<?>");
		String sourceCode = CodeUtil.createMethodSource("return "+className+".class;\n");
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected String getSharedStateSource(ModelUnit modelUnit, ModelOperation modelOperation, MethodType methodType, Type dataItem) {
		modelOperation.addException("Exception");
		
//		if (context.getProcess() == null)
//			System.out.println();

		String contextName = ServiceLayerHelper.getProcessContextInstanceName(context.getProcess());
		String processorName = DataLayerHelper.getCacheUnitNameUncapped(context.getCache()) + "Processor";
		String helperName = ModelLayerHelper.getModelHelperBeanName(dataItem);
		//String cacheName = modelUnit.getName();
		String cacheName = getBeanName();
		
		String type = dataItem.getType();
		String keyType = dataItem.getKey();
		//String paramName = dataItem.getName();
		String paramName = TypeUtil.getLocalPart(type);
		String typeName = NameUtil.capName(paramName);
		String typePackage = TypeUtil.getPackageName(type);
		String typeQualifiedName = TypeUtil.getQualifiedName(type);
		String keyParamName = null;
		String keyTypeName = null;
		String keyTypePackage = null;
		String keyQualifiedName = null;
		if (keyType != null) {
			keyParamName = TypeUtil.getLocalPart(keyType);
			keyTypeName = NameUtil.capName(keyParamName);
			keyTypePackage = TypeUtil.getPackageName(keyType);
			keyQualifiedName = TypeUtil.getQualifiedName(keyType);
		}
		String fieldName = NameUtil.capName(dataItem.getName());
		String instanceName = NameUtil.uncapName(fieldName);
		String structure = dataItem.getStructure();
		
		String criteriaClassName = typeName + "." + "Criteria";
		String criteriaQualifiedName = typeQualifiedName + "Criteria";
		
		Buf buf = new Buf();
		switch (methodType) {
		case GetAllAsList:  
			if (structure.equals("item")) {
				buf.putLine2("return getList(\"getAll"+fieldName+"\");");
			} else if (structure.equals("list")) {
				buf.putLine2("return getList(\"getAll"+fieldName+"\");");
			} else if (structure.equals("set")) {
				buf.putLine2("return getSet(\"getAll"+fieldName+"\");");
			} else if (structure.equals("map")) {
				buf.putLine2("return getList(\"getAll"+fieldName+"\");");
			}
			break;
			
		case GetAllAsMap: 
			buf.putLine2("return getMap(\"getAll"+fieldName+"AsMap\");");
			break;
			
		case GetAsItem: 
		case GetAsItemById: 
//			buf.putLine2("String[] parameterType = { \"java.util.String\" };");
//			buf.putLine2("Object[] parameterValue = { bookKey };");
//			buf.putLine2("return invoke(\"getFrom"+fieldName+"ByKey\", parameterType, parameterValue);");
			
			if (structure.equals("item")) {
				buf.putLine2("return getObject(\"get"+fieldName+"\");");
			} else if (structure.equals("list")) {
				buf.putLine2("return getObject(\"getFrom"+fieldName+"\", \"java.lang.Long\", "+paramName+"Id);");
			} else if (structure.equals("set")) {
				buf.putLine2("return getObject(\"getFrom"+fieldName+"\", \"java.lang.Long\", "+paramName+"Id);");
			} else if (structure.equals("map")) {
				buf.putLine2("return getObject(\"getFrom"+fieldName+"\", \"java.lang.Long\", "+paramName+"Id);");
			}
			
			break;
			
		case GetAsItemByKey: 
//			buf.putLine2("String[] parameterType = { \"java.util.String\" };");
//			buf.putLine2("Object[] parameterValue = { bookKey };");
//			buf.putLine2("return invoke(\"getFrom"+fieldName+"ByKey\", parameterType, parameterValue);");
			
			if (structure.equals("item")) {
				buf.putLine2("return getObject(\"get"+fieldName+"\");");
			} else if (structure.equals("list")) {
				buf.putLine2("return getObject(\"getFrom"+fieldName+"\", \""+keyQualifiedName+"\", "+paramName+"Key);");
			} else if (structure.equals("set")) {
				buf.putLine2("return getObject(\"getFrom"+fieldName+"\", \""+keyQualifiedName+"\", "+paramName+"Key);");
			} else if (structure.equals("map")) {
				buf.putLine2("return getObject(\"getFrom"+fieldName+"\", \""+keyQualifiedName+"\", "+paramName+"Key);");
			}
			
			break;
			
		case GetAsList: 
		case GetAsListByIds: 
			if (structure.equals("set"))
				buf.putLine2("return getSet(\"getFrom"+fieldName+"\", \"java.util.Collection\", "+paramName+"Ids);");
			else buf.putLine2("return getList(\"getFrom"+fieldName+"\", \"java.util.Collection\", "+paramName+"Ids);");
			break;
			
		case GetAsListByKeys: 
//			buf.putLine2("String[] parameterType = { \"java.util.List\" };");
//			buf.putLine2("Object[] parameterValue = { bookKeys };");
//			buf.putLine2("return invoke(\"getFrom"+fieldName+"ByKeys\", parameterType, parameterValue);");
			if (structure.equals("set"))
				buf.putLine2("return getSet(\"getFrom"+fieldName+"\", \"java.util.Collection\", "+paramName+"Keys);");
			else buf.putLine2("return getList(\"getFrom"+fieldName+"\", \"java.util.Collection\", "+paramName+"Keys);");
			break;
			
		case GetAsMapByKeys: 
//			buf.putLine2("String[] parameterType = { \"java.util.List\" };");
//			buf.putLine2("Object[] parameterValue = { bookKeys };");
//			buf.putLine2("return invoke(\"getFrom"+fieldName+"AsMap\", parameterType, parameterValue);");
			buf.putLine2("return getMap(\"getFrom"+fieldName+"AsMap\", \"java.util.Collection\", "+paramName+"Keys);");
			break;
			
		case GetMatchingAsList: 
			if (structure.equals("item")) {
				buf.putLine2("return getList(\"getMatching"+fieldName+"\", \""+typeQualifiedName+"\", "+paramName+");");
			} else if (structure.equals("list")) {
				buf.putLine2("return getList(\"getMatching"+fieldName+"\", \"java.util.Collection\", "+paramName+"List);");
			} else if (structure.equals("set")) {
				buf.putLine2("return getSet(\"getMatching"+fieldName+"\", \"java.util.Collection\", "+paramName+"Set);");
			} else if (structure.equals("map")) {
				buf.putLine2("return getList(\"getMatching"+fieldName+"\", \"java.util.Collection\", "+paramName+"List);");
			}
			
//			buf.putLine2("String[] parameterType = { \"java.util.List\" };");
//			buf.putLine2("Object[] parameterValue = { bookList };");
//			buf.putLine2("return invoke(\"getMatching"+fieldName+"\", parameterType, parameterValue);");
			break;
			
		case GetMatchingAsMap: 
			buf.putLine2("return getMap(\"getMatching"+fieldName+"AsMap\", \"java.util.Collection\", "+paramName+"List);");
			break;
			
		case GetAsListByCriteria: 
			modelUnit.addImportedClass(criteriaQualifiedName);
			if (structure.equals("set"))
				buf.putLine2("return getSet(\"getFrom"+fieldName+"\", \""+criteriaQualifiedName+"\", "+paramName+"Criteria);");
			else buf.putLine2("return getList(\"getFrom"+fieldName+"\", \""+criteriaQualifiedName+"\", "+paramName+"Criteria);");
			break;
			
		case Set: 
			if (structure.equals("item")) {
				buf.putLine2("invoke(\"set"+fieldName+"\", \""+typeQualifiedName+"\", "+paramName+");");
			} else if (structure.equals("list")) {
				buf.putLine2("invoke(\"set"+fieldName+"\", \"java.util.Collection\", "+paramName+"List);");
			} else if (structure.equals("set")) {
				buf.putLine2("invoke(\"set"+fieldName+"\", \"java.util.Collection\", "+paramName+"Set);");
			} else if (structure.equals("map")) {
				buf.putLine2("invoke(\"set"+fieldName+"\", \"java.util.Map\", "+paramName+"Map);");
			}
			break;
			
		case AddAsItem: 
			buf.putLine2("invoke(\"addTo"+fieldName+"\", \""+typePackage+"."+typeName+"\", "+paramName+");");
			break;
			
		case AddAsList: 
			if (structure.equals("item")) {
				buf.putLine2("invoke(\"addTo"+fieldName+"\", \""+typeQualifiedName+"\", "+paramName+");");
			} else if (structure.equals("list")) {
				buf.putLine2("invoke(\"addTo"+fieldName+"\", \"java.util.Collection\", "+paramName+"List);");
			} else if (structure.equals("set")) {
				buf.putLine2("invoke(\"addTo"+fieldName+"\", \"java.util.Collection\", "+paramName+"Set);");
			} else if (structure.equals("map")) {
				buf.putLine2("invoke(\"addTo"+fieldName+"\", \"java.util.Collection\", "+paramName+"List);");
			}
			break;
			
		case AddAsMap: 
			buf.putLine2("invoke(\"addTo"+fieldName+"\", \"java.util.Map\", "+paramName+"Map);");
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
			buf.putLine2("invoke(\"removeFrom"+fieldName+"\", \""+keyQualifiedName+"\", "+paramName+"Key);");
			break;
			
		case RemoveAsListByKeys:
			buf.putLine2("invoke(\"removeFrom"+fieldName+"\", \"java.util.Collection\", "+paramName+"Keys);");
			break;
			
		case RemoveAsList:
			if (structure.equals("item")) {
				buf.putLine2("invoke(\"removeFrom"+fieldName+"\", \""+typeQualifiedName+"\", "+paramName+");");
			} else if (structure.equals("list")) {
				buf.putLine2("invoke(\"removeFrom"+fieldName+"\", \"java.util.Collection\", "+paramName+"List);");
			} else if (structure.equals("set")) {
				buf.putLine2("invoke(\"removeFrom"+fieldName+"\", \"java.util.Collection\", "+paramName+"Set);");
			} else if (structure.equals("map")) {
				buf.putLine2("invoke(\"removeFrom"+fieldName+"\", \"java.util.Collection\", "+paramName+"List);");
			}
			break;
			
		case RemoveMatchingAsList:
			buf.putLine2("invoke(\"removeFrom"+fieldName+"\", \"java.util.Collection\", "+paramName+"List);");
			break;
			
		case RemoveAsListByCriteria:
			modelUnit.addImportedClass(criteriaQualifiedName);
			buf.putLine2("invoke(\"removeFrom"+fieldName+"\", \""+criteriaQualifiedName+"\", "+paramName+"Criteria);");
			break;
		}
		return buf.get();
	}
	
}
