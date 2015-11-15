package aries.generation;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.ProjectLevelHelper;
import nam.model.Attribute;
import nam.model.Command;
import nam.model.Element;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.Type;
import nam.model.UsageType;
import nam.model.util.FieldUtil;
import nam.model.util.TypeUtil;

import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.util.NameUtil;
import org.eclipse.emf.codegen.util.CodeGenUtil;

import aries.codegen.MethodType;
import aries.codegen.SourceType;
import aries.codegen.TestType;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelUnit;


public class AriesModelUtil {

	private GenerationContext context;
	
	private boolean cacheUnitRelated = false;

	private boolean dataUnitRelated = false;

	private boolean stateRelated = false;

	
	public AriesModelUtil(GenerationContext context) {
		this.context = context;
	}
	
	public boolean isCacheUnitRelated() {
		return cacheUnitRelated;
	}
	
	public void setCacheUnitRelated(boolean cacheUnitRelated) {
		this.cacheUnitRelated = cacheUnitRelated;
	}
	
	public boolean isDataUnitRelated() {
		return dataUnitRelated;
	}
	
	public void setDataUnitRelated(boolean dataUnitRelated) {
		this.dataUnitRelated = dataUnitRelated;
	}
	
	public boolean isStateRelated() {
		return stateRelated;
	}
	
	public void setStateRelated(boolean stateRelated) {
		this.stateRelated = stateRelated;
	}
	
	public boolean isName(String text) {
		if (text.charAt(0) == '$')
			return true;
		if (Character.isLetter(text.charAt(0)))
			return true;
		return false;
	}
	
	public boolean isCommand(String text) {
		if (text.equals("post"))
			return true;
		if (text.equals("reply"))
			return true;
		if (text.equals("send"))
			return true;
		if (text.equals("throw"))
			return true;
		return false;
	}

	public boolean isEndpoint(String text) {
		if (text.equals("invoke"))
			return true;
		if (text.equals("listen"))
			return true;
		if (text.equals("publish"))
			return true;
		if (text.equals("receive"))
			return true;
		if (text.equals("send"))
			return true;
		if (text.equals("subscribe"))
			return true;
		return false;
	}

	public <T extends Field> boolean isCollection(T field) {
		return FieldUtil.isCollection(field);
	}
	
	public boolean isRemovable(SourceType sourceType) {
		return true;
	}
	
	protected boolean isDataUnitManager() {
		return false;
	}
	
	public boolean isJMXManageable(SourceType sourceType) {
		return sourceType == SourceType.JMXInvocation;
	}
	
	public boolean isStateWritable(SourceType sourceType) {
		if (sourceType == SourceType.SharedCache)
			return true;
		boolean isCurrentStateWritable = sourceType == SourceType.CurrentState && false;
		boolean isPendingStateWritable = sourceType == SourceType.PendingState;
		boolean isPreparedStateWritable = sourceType == SourceType.PreparedState && false;
		boolean isStateWritable = isCurrentStateWritable || isPendingStateWritable || isPreparedStateWritable;
		return isStateWritable;
	}
	
	public boolean isResultRequired(MethodType methodType) {
		return isResultRequired(methodType, false);
	}

	public boolean isResultRequired(MethodType methodType, boolean isCacheRelated) {
		if (!isCacheUnitRelated() && !isStateRelated() &&
			(methodType == MethodType.AddAsItem || 
			methodType == MethodType.AddAsList || 
			methodType == MethodType.AddAsMap))
				return true;
		
//			methodType == MethodType.RemoveAll ||
//			methodType == MethodType.RemoveAsItem ||
//			methodType == MethodType.RemoveAsItemById ||
//			methodType == MethodType.RemoveAsItemByKey ||
//			methodType == MethodType.RemoveAsList ||
//			methodType == MethodType.RemoveAsListByIds ||
//			methodType == MethodType.RemoveAsListByKeys ||
//			methodType == MethodType.RemoveAsListByCriteria ||
//			methodType == MethodType.RemoveMatchingAsList))
//			return true;
		
		if (methodType == MethodType.GetAllAsList ||
			methodType == MethodType.GetAllAsMap ||
			methodType == MethodType.GetAsItem || 
			methodType == MethodType.GetAsItemById || 
			methodType == MethodType.GetAsListByIds || 
			methodType == MethodType.GetAsItemByKey || 
			methodType == MethodType.GetAsListByKeys ||
			methodType == MethodType.GetAsMapByKeys ||
			methodType == MethodType.GetAsListByCriteria ||
			methodType == MethodType.GetAsList ||
			methodType == MethodType.GetMatchingAsList ||
			methodType == MethodType.GetMatchingAsMap)
			return true;
		
		if (methodType == MethodType.Set)
			return false;
				
		return false;
	}
	
	public boolean isParameterRequired(MethodType methodType, TestType testType) {
		return methodType == MethodType.GetAsItem ||
				methodType == MethodType.GetAsList ||
				methodType == MethodType.GetAsItemById || 
				methodType == MethodType.GetAsListByIds ||
				methodType == MethodType.GetAsItemByKey || 
				methodType == MethodType.GetAsListByKeys ||
				methodType == MethodType.GetAsMapByKeys ||
				methodType == MethodType.GetAsListByCriteria ||
				methodType == MethodType.GetMatchingAsList ||
				methodType == MethodType.GetMatchingAsMap ||
				methodType == MethodType.Set || 
				methodType == MethodType.AddAsItem || 
				methodType == MethodType.AddAsList || 
				methodType == MethodType.AddAsMap || 
				methodType == MethodType.RemoveAll ||
				methodType == MethodType.RemoveAsItem ||
				methodType == MethodType.RemoveAsItemById ||
				methodType == MethodType.RemoveAsItemByKey ||
				methodType == MethodType.RemoveAsList ||
				methodType == MethodType.RemoveAsListByIds ||
				methodType == MethodType.RemoveAsListByKeys ||
				methodType == MethodType.RemoveAsListByCriteria ||
				methodType == MethodType.RemoveMatchingAsList;
				//methodType == MethodType.RemoveAsMap;
	}
	
	public boolean isParameterGeneric(MethodType methodType) {
		return methodType == MethodType.GetAsItem ||
				methodType == MethodType.GetAsItemById || 
				methodType == MethodType.GetAsListByIds ||
				methodType == MethodType.GetAsItemByKey || 
				methodType == MethodType.GetAsListByKeys ||
				methodType == MethodType.GetAsMapByKeys ||
				methodType == MethodType.GetAsList ||
				methodType == MethodType.GetMatchingAsList ||
				methodType == MethodType.GetMatchingAsMap ||
				methodType == MethodType.AddAsItem || 
				methodType == MethodType.AddAsItem || 
				methodType == MethodType.AddAsList || 
				methodType == MethodType.AddAsMap || 
				methodType == MethodType.RemoveAll ||
				methodType == MethodType.RemoveAsItem ||
				methodType == MethodType.RemoveAsItemById ||
				methodType == MethodType.RemoveAsItemByKey ||
				methodType == MethodType.RemoveAsList ||
				methodType == MethodType.RemoveAsListByIds ||
				methodType == MethodType.RemoveAsListByKeys ||
				methodType == MethodType.RemoveAsListByCriteria ||
				methodType == MethodType.RemoveMatchingAsList;
				//methodType == MethodType.RemoveAsMap;
	}


	public String getClassNameFromType(String type) {
		if (type == null)
			return null;
		if (type.startsWith("{"))
			return TypeUtil.getClassName(type);
		return getClassNameFromXSDType(type);
	}

	public String getPackageNameFromType(String type) {
		if (type == null)
			return null;
		if (type.startsWith("{"))
			return TypeUtil.getPackageName(type);
		return getPackageNameFromXSDType(type);
	}
	
	public String getClassNameFromXSDType(String type) {
		String prefix = NameUtil.getPrefixFromXSDType(type);
		String localName = NameUtil.getLocalNameFromXSDType(type);
		//TODO handle these with external properties 
		if (prefix.startsWith("xs") && localName.equals("int"))
			return "Integer";
		if (prefix.startsWith("xs") && localName.equals("date"))
			return "Date";
		if (prefix.startsWith("xs") && localName.equals("dateTime"))
			return "Date";
		localName = NameUtil.capName(localName);
		return localName;
	}
	
	public String getPackageNameFromXSDType(String type) {
		NameUtil.validateXSDType(type);
		int colonIndex = type.indexOf(":");
		String prefix = type.substring(0, colonIndex);
		String simpleName = type.substring(colonIndex+1);
		//TODO handle these with external properties 
		if (prefix.startsWith("xs") && simpleName.equals("date"))
			return "java.util";
		if (prefix.startsWith("xs") && simpleName.equals("dateTime"))
			return "java.util";
		if (prefix.startsWith("xs") && CodeGenUtil.isJavaPrimitiveType(simpleName))
			return "java.lang";
		Namespace namespace = context.getNamespaceByPrefix(prefix);
//		if (namespace == null)
//			System.out.println();
		String packageName = ProjectLevelHelper.getPackageName(namespace.getUri());
		return packageName;
	}

	public String getClassNameWithStructure(Field field) throws Exception {
		String structure = field.getStructure();
		String className = TypeUtil.getClassName(field.getType());
		if (structure.equalsIgnoreCase("item")) {
			return className;
		} else if (structure.equalsIgnoreCase("list")) {
			return "List<"+className+">";
		} else if (structure.equalsIgnoreCase("set")) {
			return "Set<"+className+">";
		} else if (structure.equalsIgnoreCase("map")) {
			Assert.notNull(field.getKey(), "Key for map field not found: "+field.getName());
			String keyClassName = TypeUtil.getClassName(field.getKey());
			return "Map<"+keyClassName+", "+className+">";
		}
		throw new Exception("Unexpected structure: "+structure);
	}

	
	public <T extends Field> String getResultType(MethodType methodType, ModelUnit modelUnit, String packageName, String className, String keyType, String structure) {
		//if (structure.equals("set"))
		//	System.out.println();
		
		if (methodType == MethodType.Set || 
			methodType == MethodType.RemoveAll ||
			methodType == MethodType.RemoveAsItem ||
			methodType == MethodType.RemoveAsItemById ||
			methodType == MethodType.RemoveAsItemByKey ||
			methodType == MethodType.RemoveAsList ||
			methodType == MethodType.RemoveAsListByIds ||
			methodType == MethodType.RemoveAsListByKeys ||
			methodType == MethodType.RemoveAsListByCriteria ||
			methodType == MethodType.RemoveMatchingAsList)
				return null;
			
		if (methodType == MethodType.AddAsItem)
			return "Long";
		if (methodType == MethodType.AddAsList)
			return "List<Long>";
		if (methodType == MethodType.AddAsMap) {
			//String keyPackageName = getPackageNameFromType(keyType);
			//String keyClassName = getClassNameFromType(keyType);
			//modelUnit.addImportedClass(keyPackageName+"."+keyClassName);
			//modelUnit.addImportedClass("java.util.Map");
			//return "Map<"+keyClassName+", Long>";
			return "List<Long>";
		}
		
		String resultTypeString = null;
		if (methodType == MethodType.GetAllAsList || 
			methodType == MethodType.GetAsList || 
			methodType == MethodType.GetAsListByIds || 
			methodType == MethodType.GetAsListByKeys || 
			methodType == MethodType.GetAsListByCriteria ||
			methodType == MethodType.GetMatchingAsList ||
			methodType == MethodType.AddAsList) {
			if (structure.equals("set"))
				structure = "set";
			else structure = "list";
		}
		if (methodType == MethodType.GetAsItem ||
			methodType == MethodType.GetAsItemById ||
			methodType == MethodType.GetAsItemByKey ||
			methodType == MethodType.AddAsItem ||
			methodType == MethodType.RemoveAsItem ||
			methodType == MethodType.RemoveAsItemByKey)
				structure = "item";
		
		if (structure.equals("map")) {
			String keyPackageName = getPackageNameFromType(keyType);
			String keyClassName = getClassNameFromType(keyType);
			modelUnit.addImportedClass(packageName+"."+className);
			modelUnit.addImportedClass(keyPackageName+"."+keyClassName);
			modelUnit.addImportedClass("java.util.Map");
			resultTypeString = "Map<"+keyClassName+", "+className+">";
			
		} else if (structure.equals("list")) {
			modelUnit.addImportedClass("java.util.List");
			resultTypeString = "List<"+className+">";

		} else if (structure.equals("set")) {
			modelUnit.addImportedClass("java.util.Set");
			resultTypeString = "Set<"+className+">";
			
		} else if (structure.equals("item")) {
			modelUnit.addImportedClass(packageName+"."+className);
			resultTypeString = className;
		}
		
		return resultTypeString;
	}
	
	protected <T extends Type> String getResultStructure(MethodType methodType, T dataItem) {
		//if (structure.equals("set"))
		//	System.out.println();
		
//		if (methodType == MethodType.Set || 
//			methodType == MethodType.RemoveAll ||
//			methodType == MethodType.RemoveAsItem ||
//			methodType == MethodType.RemoveAsItemById ||
//			methodType == MethodType.RemoveAsItemByKey ||
//			methodType == MethodType.RemoveAsList ||
//			methodType == MethodType.RemoveAsListByIds ||
//			methodType == MethodType.RemoveAsListByKeys ||
//			methodType == MethodType.RemoveAsListByCriteria ||
//			methodType == MethodType.RemoveMatchingAsList)
//				return null;
		
		String structure = dataItem.getStructure();
		if (methodType == MethodType.GetAllAsList || 
			methodType == MethodType.GetAsList || 
			methodType == MethodType.GetAsListByIds || 
			methodType == MethodType.GetAsListByKeys || 
			methodType == MethodType.GetAsListByCriteria ||
			methodType == MethodType.GetMatchingAsList || 
			methodType == MethodType.AddAsList ||
			methodType == MethodType.AddAsMap) {
			if (!structure.equals("set"))
				return "list";
		}
		if (methodType == MethodType.GetAsItem ||
			methodType == MethodType.GetAsItemById ||
			methodType == MethodType.GetAsItemByKey ||
			methodType == MethodType.AddAsItem)
				return "item";
		
		if (methodType == MethodType.GetAllAsMap ||
			methodType == MethodType.GetAsMapByKeys ||
			methodType == MethodType.GetMatchingAsMap)
				return "map";
				
		return structure;
	}
	
	
	public int getModifiers(SourceType sourceType) {
		switch (sourceType) {
		case PendingState:
			return Modifier.PUBLIC;
		case PreparedState:
			return Modifier.PUBLIC + Modifier.SYNCHRONIZED;
		case CurrentState:
		case SharedCache:
		case JMXInvocation:
			return Modifier.PUBLIC;
		}
		return 0;
	}

	public <T extends Type> String getOperationName(SourceType sourceType, MethodType methodType, T dataItem) {
		String name = NameUtil.capName(dataItem.getName());
		String structure = dataItem.getStructure();
		
		String operationName = getDataAccessMethodPrefix(methodType, structure);
		switch (sourceType) {
		case PendingState:
			//if (!isMethodWritable(methodType))
				operationName += "Pending";
			operationName += name;
			break;
		case PreparedState:
			operationName += "Prepared" + name;
			break;
		case CurrentState:
		case SharedCache:
		case JMXInvocation:
			operationName += name;
			break;
		}
		operationName += getDataAccessMethodSuffix(methodType, structure);
		return operationName;
	}

	public boolean isItemTypeParameterRequired(MethodType methodType, String structure) {
		return structure.equals("item") || 
				methodType == MethodType.GetAsItem || 
				methodType == MethodType.GetAsItemById || 
				methodType == MethodType.GetAsItemByKey || 
				methodType == MethodType.GetAsListByCriteria ||
				methodType == MethodType.AddAsItem || 
				methodType == MethodType.RemoveAsItem || 
				methodType == MethodType.RemoveAsItemById || 
				methodType == MethodType.RemoveAsItemByKey ||
				methodType == MethodType.RemoveAsListByCriteria;
	}
	
	public boolean isListTypeParameterRequired(MethodType methodType, String structure) {
		return structure.equals("list") || 
				methodType == MethodType.GetAllAsList || 
				methodType == MethodType.GetAsList || 
				methodType == MethodType.GetAsListByIds || 
				methodType == MethodType.GetAsListByKeys || 
				methodType == MethodType.GetAsMapByKeys || 
				methodType == MethodType.GetMatchingAsList ||
				methodType == MethodType.GetMatchingAsMap || 
				methodType == MethodType.AddAsList || 
				methodType == MethodType.RemoveAsList || 
				methodType == MethodType.RemoveAsListByIds || 
				methodType == MethodType.RemoveAsListByKeys ||
				methodType == MethodType.RemoveAsListByCriteria ||
				methodType == MethodType.RemoveMatchingAsList;
	}
	

	public String getDataAccessMethodPrefix(MethodType methodType, String structure) {
		switch (methodType) {
		case GetAllAsList:
		case GetAllAsMap: 
			return "getAll";
		case GetAsItem:
		case GetAsItemById:
		case GetAsItemByKey:
		case GetAsList:
		case GetAsListByIds:
		case GetAsListByKeys:
		case GetAsListByCriteria:
		case GetAsMapByKeys: 
			if (structure.equals("item"))
				return "get";
			return "getFrom";
		case GetMatchingAsList:
		case GetMatchingAsMap:
			return "getMatching";
		case Set: 
			return "set";
		case Unset: 
			return "unset";
		case AddAsItem: 
		case AddAsList: 
		case AddAsMap: 
			return "addTo";
		case RemoveAll: 
			return "removeAll";
		case RemoveAsItem: 
		case RemoveAsItemById: 
		case RemoveAsItemByKey: 
		case RemoveAsList: 
		case RemoveAsListByIds: 
		case RemoveAsListByKeys: 
		case RemoveAsListByCriteria: 
			return "removeFrom";
		case RemoveMatchingAsList: 
			return "removeFrom";
		default: return null;
		}
	}

	public String getDataAccessMethodSuffix(MethodType methodType, String structure) {
		boolean addRecordsSuffix = false;
		
		switch (methodType) {
		case GetAllAsList:
		case GetAsList:
		case GetMatchingAsList:
		case AddAsItem:
		case AddAsList:
			if (addRecordsSuffix)
				return "Records";
			return "";

		case GetAsItemById:
		case RemoveAsItemById: 
			if (addRecordsSuffix)
				return "Record";
			//return "ById";
			return "";
			
		case GetAsItemByKey:
		case RemoveAsItemByKey: 
			if (addRecordsSuffix)
				return "Records";
			//return "ByKey";
			return "";
			
		case GetAsListByIds:
		case RemoveAsListByIds: 
			if (addRecordsSuffix)
				return "Records";
			//return "ByIds";
			return "";
			
		//case GetAsListByKeys:
		case GetAsMapByKeys:
			return "AsMap";

		case RemoveAsListByKeys: 
			if (addRecordsSuffix)
				return "Records";
			//return "ByKeys";
			return "";

		case GetAsListByCriteria:
		case RemoveAsListByCriteria:
			if (addRecordsSuffix)
				return "Records";
			return "";


		case GetAllAsMap:
		case GetMatchingAsMap:
		//case AddAsMap:
			if (addRecordsSuffix)
				return "RecordsAsMap";
			return "AsMap";
			
		default: 
			if (addRecordsSuffix)
				return "Record";
			return "";
		}
	}

	
	public String getSharedStateSource(ModelUnit modelUnit, ModelOperation modelOperation, MethodType methodType, Type dataItem) {
		//do nothing by default
		return "";
	}
	
	public String getSharedStateSource(ModelUnit modelUnit, ModelOperation modelOperation, MethodType methodType, TestType testType, Type dataItem) {
		//do nothing by default
		return "";
	}
	

	
	protected <T extends Type> List<Parameter> createParameters(T dataItem, SourceType sourceType, MethodType methodType) {
		String type = dataItem.getType();
		String key = dataItem.getKey();
		//String name = NameUtil.capName(dataItem.getName());
		//String nameUncapped = NameUtil.uncapName(dataItem.getName());
		String name = TypeUtil.getLocalPart(type);
		String structure = dataItem.getStructure();
		String longType = TypeUtil.getDefaultType("Long");

		List<Parameter> parameters = new ArrayList<Parameter>();
		//String paramName = dataItem.getName();

		if (methodType == MethodType.GetAllAsList || methodType == MethodType.GetAllAsMap || methodType == MethodType.RemoveAll)
			return parameters;

		boolean itemTypeRequired = isItemTypeParameterRequired(methodType, structure);
		boolean listTypeRequired = isListTypeParameterRequired(methodType, structure);
		boolean isParameterGeneric = isParameterGeneric(methodType);

//		if (dataItem.getName().equals("bookOrders"))
//			System.out.println();
//		if (dataItem.getName().equals("bookOrders") && methodType == MethodType.GetAsListByIds)
//			System.out.println();
//		if (dataItem.getName().equals("reservedBooks") && methodType == MethodType.GetAsListByIds)
//			System.out.println();
//		if (methodType == MethodType.RemoveAsItem)
//			System.out.println();
		
		if (itemTypeRequired) {
			if (methodType == MethodType.GetAsItemById || methodType == MethodType.RemoveAsItemById) {
				parameters.add(createParameter(name + "Id", longType));

			} else if (methodType == MethodType.GetAsItem || methodType == MethodType.GetAsItemByKey) {
				if (structure.equals("map")) {
					parameters.add(createParameter(name + "Key", key, null, "item", true));
					
				} else if (!structure.equals("item")) {
					parameters.add(createParameter(name, type));
				}

			} else if (methodType == MethodType.GetAsListByCriteria || methodType == MethodType.RemoveAsListByCriteria) {
				parameters.add(createParameter(name + "Criteria", type + "Criteria"));
				
			} else if (methodType == MethodType.AddAsItem) {
				//if (structure.equals("map") && !isDataUnitManager()) {
				//	parameters.add(createParameter(name + "Key", key, null, "item", true));
				//}
				
				parameters.add(createParameter(name, type, null, "item", true));

			} else if (methodType == MethodType.RemoveAsItem) {
				parameters.add(createParameter(name, type, null, "item", true));

			} else if (structure.equals("map") && methodType == MethodType.RemoveAsItemByKey) {
				parameters.add(createParameter(name + "Key", key, null, "item", true));

			} else {
				parameters.add(createParameter(name, type, null, structure, true));
			}
			
		} else if (listTypeRequired) {
			if (!isParameterGeneric) {
				if (structure.equals("list"))
					name += "List";
				if (structure.equals("set"))
					name += "Set";
				if (structure.equals("map"))
					name += "List";
				parameters.add(createParameter(name, type, null, structure, true));
			
			} else if (methodType == MethodType.GetAsItemById || methodType == MethodType.RemoveAsItemById) {
				parameters.add(createParameter(name + "Id", longType));
				
			} else if (methodType == MethodType.GetMatchingAsList || methodType == MethodType.GetMatchingAsMap || methodType == MethodType.AddAsList || methodType == MethodType.RemoveAsList) {
				parameters.add(createParameter(name + "List", type, key, "list", true));
				
			} else if (structure.equals("map") && methodType == MethodType.GetAsList || methodType == MethodType.GetAsListByKeys || methodType == MethodType.GetAsMapByKeys || methodType == MethodType.RemoveAsListByKeys) {
				parameters.add(createParameter(name + "Keys", key, null, "list", true));

			} else if (methodType == MethodType.GetAsListByIds || methodType == MethodType.RemoveAsListByIds) {
				parameters.add(createParameter(name + "Ids", longType , null, "list", true));

			} else {
				if (structure.equals("list"))
					name += "List";
				if (structure.equals("set"))
					name += "Set";
				if (structure.equals("map"))
					name += "List";
				parameters.add(createParameter(name, type, null, structure, true));
			}
			
		} else if (structure.equals("set")) {
			parameters.add(createParameter(name + "Set", type, null, structure, true));
			
		} else if (structure.equals("map")) {
			parameters.add(createParameter(name + "Map", type, key, structure, true));
		}
		
		return parameters;
	}
	
	public static Parameter createParameter(String name, String type) {
		return createParameter(name, type, null);
	}

	public static Parameter createParameter(String name, String type, String key) {
		return createParameter(name, type, key, "item", true);
	}
	
	public static Parameter createParameter(String name, String type, String key, String structure, boolean required) {
		Parameter parameter = new Parameter();
		parameter.setName(name);
		parameter.setType(type);
		parameter.setKey(key);
		parameter.setConstruct(structure);
		parameter.setRequired(required);
		return parameter;
	}
	
	
	public <T extends Type> List<ModelParameter> createModelParameters(MethodType methodType, ModelUnit modelUnit, T dataItem, String packageName, String className, String structure) {
		List<ModelParameter> modelParameters = new ArrayList<ModelParameter>();
		String type = dataItem.getType();
		String key = dataItem.getKey();
		//String paramName = dataItem.getName();
		String paramName = TypeUtil.getLocalPart(type);
		String keyPackageName = null;
		String keyClassName = null;
		if (key != null) {
			keyPackageName = getPackageNameFromType(key);
			keyClassName = getClassNameFromType(key);
			if (!TypeUtil.isDefaultType(key)) {
				Element keyElement = context.getElementByType(key);
				if (keyElement != null) {
					keyPackageName = getPackageNameFromType(key);
					keyClassName = getClassNameFromType(key);
					modelUnit.addImportedClass(keyPackageName+"."+keyClassName);
				}
			}
		}
		
		boolean itemTypeRequired = isItemTypeParameterRequired(methodType, structure);
		boolean listTypeRequired = isListTypeParameterRequired(methodType, structure);
		boolean isParameterGeneric = isParameterGeneric(methodType);

		if (methodType == MethodType.GetAllAsList || methodType == MethodType.GetAllAsMap || methodType == MethodType.RemoveAll)
			return modelParameters;
			
//		if (dataItem.getName().equals("reservedBooks") && methodType == MethodType.RemoveAsList)
//			System.out.println();
		
		if (itemTypeRequired) {
			if (methodType == MethodType.GetAsItemByKey || methodType == MethodType.RemoveAsItemByKey) {
				if (structure.equals("map")) {
					modelParameters.add(CodeUtil.createParameter(keyClassName, paramName + "Key"));
					modelUnit.addImportedClass(keyPackageName+"."+keyClassName);
					
				} else if (!structure.equals("item")) {
					modelParameters.add(CodeUtil.createParameter(className, paramName));
					modelUnit.addImportedClass(packageName+"."+className);
				}

			} else if (methodType == MethodType.GetAsItemById || methodType == MethodType.RemoveAsItemById) {
				modelParameters.add(createModelParameter(paramName + "Id", "Long"));
				
			} else if (methodType == MethodType.GetAsListByCriteria || methodType == MethodType.RemoveAsListByCriteria) {
				modelParameters.add(CodeUtil.createParameter(className + "Criteria", paramName + "Criteria"));
				modelUnit.addImportedClass(packageName+"."+className + "Criteria");
				
			} else if (methodType == MethodType.AddAsItem) {
				//if (structure.equals("map") && !isDataUnitManager())
				//if (structure.equals("map") && !isDataUnitManager() && isCacheRelated()) {
				//	String key = dataItem.getKey();
				//	String keyPackageName = getPackageNameFromType(key);
				//	String keyClassName = getClassNameFromType(key);
				//	modelUnit.addImportedClass(keyPackageName+"."+keyClassName);
				//	modelParameters.add(CodeUtil.createParameter(keyClassName, paramName + "Key"));
				//}
				
				modelUnit.addImportedClass(packageName+"."+className);
				modelParameters.add(CodeUtil.createParameter(className, paramName));

			} else if (methodType == MethodType.RemoveAsItem) {
				if (structure.equals("list")) {
					String typePackageName = getPackageNameFromType(type);
					String typeClassName = getClassNameFromType(type);
					modelUnit.addImportedClass(typePackageName+"."+typeClassName);
					modelParameters.add(CodeUtil.createParameter(typeClassName, paramName));
					
//				} else if (structure.equals("map") && isCacheUnit()) {
//					String key = dataItem.getKey();
//					String keyPackageName = getPackageNameFromType(key);
//					String keyClassName = getClassNameFromType(key);
//					modelUnit.addImportedClass(keyPackageName+"."+keyClassName);
//					modelParameters.add(CodeUtil.createParameter(keyClassName, typeName + "Key"));
					
				} else {
					modelUnit.addImportedClass(packageName+"."+className);
					modelParameters.add(CodeUtil.createParameter(className, paramName));
				}

			} else if (structure.equals("map") && methodType == MethodType.RemoveAsItemByKey) {
				modelUnit.addImportedClass(keyPackageName+"."+keyClassName);
				modelParameters.add(CodeUtil.createParameter(keyClassName, paramName + "Key"));

			} else if (methodType == MethodType.GetAsItem) {
				//should do nothing here - no params

			} else {
				modelUnit.addImportedClass(packageName+"."+className);
				modelParameters.add(CodeUtil.createParameter(className, paramName));
			}
			
		} else if (listTypeRequired) {
			if (!isParameterGeneric) {
				if (structure.equals("list"))
					paramName += "List";
				if (structure.equals("set"))
					paramName += "Set";
				if (structure.equals("map"))
					paramName += "List";
				modelUnit.addImportedClass("java.util.Collection");
				String parameterType = "Collection<"+className+">";
				modelParameters.add(CodeUtil.createParameter(parameterType, paramName));
				
			} else if (methodType == MethodType.GetMatchingAsList || methodType == MethodType.GetMatchingAsMap || methodType == MethodType.AddAsList || methodType == MethodType.RemoveAsList) {
				if (structure.equals("list"))
					paramName += "List";
				if (structure.equals("set"))
					paramName += "Set";
				if (structure.equals("map"))
					paramName += "List";
				modelParameters.add(createModelParameter(paramName, className, keyClassName, "collection"));
				
			} else if (structure.equals("map") && methodType == MethodType.GetAsList || methodType == MethodType.GetAsListByKeys || methodType == MethodType.GetAsMapByKeys || methodType == MethodType.RemoveAsListByKeys) {
				modelParameters.add(createModelParameter(paramName + "Keys", keyClassName, null, "collection"));

			} else if (methodType == MethodType.GetAsList || methodType == MethodType.GetAsListByIds || methodType == MethodType.RemoveAsListByIds) {
				modelParameters.add(createModelParameter(paramName + "Ids", "Long", null, "collection"));

			} else if (structure.equals("map") && (methodType == MethodType.GetAsListByKeys || methodType == MethodType.GetAsMapByKeys || methodType == MethodType.RemoveAsListByKeys)) {
				paramName += "Keys";
				modelUnit.addImportedClass("java.util.Collection");
				String parameterType = "Collection<"+keyClassName+">";
				modelParameters.add(CodeUtil.createParameter(parameterType, paramName));
				
			} else {
				if (structure.equals("list"))
					paramName += "List";
				if (structure.equals("set"))
					paramName += "Set";
				if (structure.equals("map"))
					paramName += "List";
				modelUnit.addImportedClass("java.util.Collection");
				String parameterType = "Collection<"+className+">";
				modelParameters.add(CodeUtil.createParameter(parameterType, paramName));
			}
			
		} else if (structure.equals("set")) {
			paramName += "Set";
			if (!isParameterGeneric) {
				modelUnit.addImportedClass("java.util.Collection");
				String parameterType = "Collection<"+className+">";
				modelParameters.add(CodeUtil.createParameter(parameterType, paramName));
			} else {
				modelUnit.addImportedClass("java.util.Collection");
				String parameterType = "Collection<"+className+">";
				modelParameters.add(CodeUtil.createParameter(parameterType, paramName));
			}
			
		} else if (structure.equals("map")) {
			paramName += "Map";
			String parameterType = "Map<"+keyClassName+", "+className+">";
			modelUnit.addImportedClass("java.util.Map");
			modelUnit.addImportedClass(packageName+"."+className);
			modelUnit.addImportedClass(keyPackageName+"."+keyClassName);
			modelParameters.add(CodeUtil.createParameter(parameterType, paramName));
		}
		
		return modelParameters;
	}
	

	public static Collection<ModelParameter> createModelParameters(Operation operation) {
		Collection<ModelParameter> modelParameters = new ArrayList<ModelParameter>();
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			ModelParameter createModelParameter = createModelParameter(parameter);
			modelParameters.add(createModelParameter);
		}
		return modelParameters;
	}

	public static ModelParameter createModelParameter(Parameter parameter) {
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setName(parameter.getName());
		String parameterType = parameter.getType();
		String parameterKeyType = parameter.getKey();
		modelParameter.setClassName(TypeUtil.getClassName(parameterType));
		modelParameter.setPackageName(TypeUtil.getPackageName(parameterType));
		modelParameter.setKeyClassName(TypeUtil.getClassName(parameterKeyType));
		modelParameter.setKeyPackageName(TypeUtil.getPackageName(parameterKeyType));
		modelParameter.setConstruct(parameter.getConstruct());
		return modelParameter;
	}

	public static ModelParameter createModelParameter(String name, String type) {
		return createModelParameter(name, type, null);
	}

	public static ModelParameter createModelParameter(String name, String type, String key) {
		return createModelParameter(name, type, key, "item");
	}
	
	public static ModelParameter createModelParameter(String name, String type, String key, String structure) {
		ModelParameter parameter = new ModelParameter();
		parameter.setName(name);
		parameter.setClassName(type);
		parameter.setKeyClassName(key);
		parameter.setConstruct(structure);
		return parameter;
	}

	
	protected <T extends Type> Result createResult(T dataItem, SourceType sourceType, MethodType methodType) {
		Result result = new Result();
		result.setName(getResultName(dataItem, sourceType, methodType));
		result.setType(getResultType(dataItem, sourceType, methodType));
		result.setKey(getResultKey(dataItem, sourceType, methodType));
		result.setConstruct(getResultStructure(methodType, dataItem));
		return result;
	}

	public <T extends Type> String getResultName(T dataItem, SourceType sourceType, MethodType methodType) {
		String elementType = ModelLayerHelper.getElementTypeLocalPartUncapped(dataItem);
		if (methodType == MethodType.AddAsItem)
			return elementType+"Id";

		if (methodType == MethodType.AddAsList ||
			methodType == MethodType.AddAsMap)
			return elementType+"Ids";

		if (isResultRequired(methodType)) {
//			if (structure.equals("set"))
//				return dataItem.getName() + "Set";
//			else return dataItem.getName() + "List";
			return dataItem.getName();
		}

		return null;
	}
	
	public <T extends Type> String getResultType(T dataItem, SourceType sourceType, MethodType methodType) {
		String structure = dataItem.getStructure();
		
		if (methodType == MethodType.AddAsItem ||
			methodType == MethodType.AddAsList ||
			methodType == MethodType.AddAsMap)
				return TypeUtil.getDefaultType("Long");
				
		if (isResultRequired(methodType)) {
			if (structure.equals("set"))
				return dataItem.getType();
			else return dataItem.getType();
		}

		return null;
	}
	
	public <T extends Type> String getResultKey(T dataItem, SourceType sourceType, MethodType methodType) {
		if (isResultRequired(methodType)) {
			if (methodType == MethodType.GetAllAsMap ||
				methodType == MethodType.GetMatchingAsMap ||
				methodType == MethodType.GetAsMapByKeys ||
				methodType == MethodType.AddAsMap ||
				methodType == MethodType.AddAsMap)
					return dataItem.getKey();
		}
		return null;
	}
	
	
	public void printCommandSource(Command command) {
		String source = getCommandSource(command);
		if (StringUtils.isEmpty(source))
			source = command.getText();
		System.out.println(">>> "+source);
	}
	
	public String getCommandSource(Command command) {
		StringBuffer buf = new StringBuffer();
		Iterator<String> iterator = command.getTokens().iterator();
		while (iterator.hasNext()) {
			String token = iterator.next();
			buf.append(token);
		}
		return buf.toString();
	}
	
	
	public Attribute createAttribute_Id() {
		Attribute attribute = new Attribute();
		attribute.setType(TypeUtil.getDefaultIdType());
		attribute.setId(true);
		attribute.setName("id");
		return attribute;
	}

	public Attribute createAttribute_Reason() {
		Attribute attribute = new Attribute();
		attribute.setType(TypeUtil.getDefaultType("String"));
		attribute.setUnique(true);
		attribute.setName("reason");
		return attribute;
	}

}
