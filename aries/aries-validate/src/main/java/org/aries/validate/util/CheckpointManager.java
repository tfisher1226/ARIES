package org.aries.validate.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.MethodUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.AssertionFailure;
import org.aries.jaxb.JAXBReader;
import org.aries.jaxb.JAXBReaderImpl;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.util.ClassUtil;
import org.aries.util.FieldUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.ResourceUtil;
import org.aries.util.xpath.ExpressionUtil;
import org.aries.validate.Checkpoint;
import org.aries.validate.Checkpoints;
import org.aries.validate.Condition;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.xpath10.Expr;
import org.eclipse.bpel.xpath10.UnaryExpr;
import org.eclipse.bpel.xpath10.parser.XPath10Factory;


public class CheckpointManager {

	private static final Log log = LogFactory.getLog(CheckpointManager.class);
	
	protected static Map<String, Checkpoint> checkpointMap = new ConcurrentHashMap<String, Checkpoint>();

	protected static Map<String, Condition> conditionMap = new ConcurrentHashMap<String, Condition>();

	protected static Map<String, Variable> variables = new HashMap<String, Variable>();
	
	protected static List<String> configurations = new ArrayList<String>();
	
	protected static AtomicBoolean initialized = new AtomicBoolean(false);

	private static JAXBSessionCache jaxbSessionCache;
	

	public static void addConfiguration(String fileName) {
		CheckpointManager.configurations.add(fileName);
	}

	public static void addVariables(Map<String, Variable> variables) {
		CheckpointManager.variables.putAll(variables);
	}

	public static void addVariable(String key, Variable variable) {
		CheckpointManager.variables.put(key, variable);
	}

	public static void setJAXBSessionCache(JAXBSessionCache jaxbSessionCache) {
		CheckpointManager.jaxbSessionCache = jaxbSessionCache;
	}


	protected static void initialize() {
		synchronized (checkpointMap) {
			log.info("initializing...");
			checkpointMap.clear();
			conditionMap.clear();
			try {
				readConfigurations();
				initialized.set(true);
				log.info("initialized");
			} catch (Exception e) {
				initialized.set(false);
				log.info("initialization failed: "+e);
				throw new RuntimeException(e);
			}
		}
	}

	protected static void readConfigurations() throws Exception {
		readConfigurationIfExists("checkpoints.xml");
		Iterator<String> iterator = configurations.iterator();
		while (iterator.hasNext()) {
			String fileName = iterator.next();
			readConfiguration(fileName);
		}
	}
	
	protected static void readConfigurationIfExists(String fileName) throws Exception {
		if (ResourceUtil.getResourceAsStream(fileName) != null)
			readConfiguration(fileName);
	}

	protected static void readConfiguration(String fileName) throws Exception {
		//org.aries.checkpoint.init.Bootstrap.initialize();
		//JAXBSessionCache jaxbSessionCache = BeanContext.get(domain + ".jaxbSessionCache");
		jaxbSessionCache.addSchema("/schema/common/aries-validate-1.0.xsd", org.aries.validate.ObjectFactory.class);
		//JAXBSessionCache jaxbSessionCache = new JAXBSessionCache();
		//jaxbSessionCache.addSchema("/schema/aries-validate-1.0.xsd");
		JAXBReader jaxbReader = new JAXBReaderImpl();
		jaxbReader.setSchema(jaxbSessionCache.getSchema());
		jaxbReader.setPackagesToLoad(jaxbSessionCache.getPackagesToLoad());
		jaxbReader.initialize();
		Checkpoints checkpoints = jaxbReader.unmarshalFromClasspath(fileName);
		List<Checkpoint> list = checkpoints.getCheckpoints();
		initializeCheckpoints(list);
	}

	protected static void initializeCheckpoints(List<Checkpoint> checkpoints) {
		Iterator<Checkpoint> iterator = checkpoints.iterator();
		while (iterator.hasNext()) {
			Checkpoint checkpoint = iterator.next();
			String checkpointName = checkpoint.getName();
			List<Condition> conditions = checkpoint.getConditions();
			initializeConditions(checkpointName, conditions);
			assureCheckpointName(checkpoint);
			validateCheckpoint(checkpoint);
			addCheckpoint(checkpoint);
		}
	}

	protected static void assureCheckpointName(Checkpoint checkpoint) {
		String checkpointName = checkpoint.getName();
		List<Condition> conditions = checkpoint.getConditions();
		if (checkpointName == null && conditions.size() == 1)
			checkpointName = conditions.get(0).getName();
		if (checkpointName == null)
			checkpointName = "checkpoint"+checkpointMap.size();
		checkpoint.setName(checkpointName);
	}

	protected static void initializeConditions(String checkpointName, List<Condition> conditions) {
		Iterator<Condition> iterator = conditions.iterator();
		while (iterator.hasNext()) {
			Condition condition = iterator.next();
			String conditionName = condition.getName();
			String conditionKey = getConditionKey(checkpointName, conditionName);
			if (condition.getEnabled() == null)
				condition.setEnabled(true);
			if (condition.getForced() == null)
				condition.setForced(false);
			conditionMap.put(conditionKey, condition);
		}
	}

	protected static String getConditionKey(String checkpointName, String conditionName) {
		String conditionKey = checkpointName+"/"+conditionName;
		return conditionKey;
	}

	protected static void validateCheckpoint(Checkpoint checkpoint) {
		Assert.notNull(checkpoint.getName(), "Checkpoint name must be specified");
		List<Condition> conditions = checkpoint.getConditions();
		Assert.notNull(conditions, "Checkpoint condition must exist");
		Assert.isTrue(conditions.size() > 0, "At least one checkpoint condition must exist");
		Iterator<Condition> iterator = conditions.iterator();
		while (iterator.hasNext()) {
			Condition condition = iterator.next();
			validateCondition(condition);
		}
	}

	protected static void validateCondition(Condition condition) {
		//Assert.notNull(condition.getType(), "Checkpoint condition type must be specified");
	}
	

	public static void addCheckpoint(Checkpoint checkpoint) {
		checkpointMap.put(checkpoint.getName().toLowerCase(), checkpoint);
	}

	public static void removeCheckpoint(Checkpoint checkpoint) {
		checkpointMap.remove(checkpoint.getName().toLowerCase());
	}

	public static List<Checkpoint> getCheckpoints() {
		return new ArrayList<Checkpoint>(checkpointMap.values());
	}

	public static Checkpoint getCheckpoint(String checkpointName) {
		if (!initialized.get())
			initialize();
		Checkpoint checkpoint = checkpointMap.get(checkpointName.toLowerCase());
		if (checkpoint == null) {
			//point = new Checkpoint();
			//map.put(key, point);
			//log.info("%%%%%"+ checkpointMap.size());
			//Iterator<String> iterator = checkpointMap.keySet().iterator();
			//while (iterator.hasNext()) {
			//	String string = (String) iterator.next();
			//	log.info("%%%%%"+ string);
			//}
			throw new RuntimeException("Checkpoint not found: "+checkpointName);
		}
		return checkpoint;
	}

	public static Condition getCheckpointCondition(String checkpointName, String conditionName) {
		if (!initialized.get())
			initialize();
		String conditionKey = getConditionKey(checkpointName, conditionName);
		Condition condition = conditionMap.get(conditionKey);
		if (condition == null)
			throw new RuntimeException("Condition not found: "+conditionKey);
		return condition;
	}

	
//	protected static void notNull(String checkpointName, Object object) {
//		notNull(getCheckpoint(checkpointName), object, null);
//	}
//	
//	protected static void notNull(String checkpointName, Object object, String message) {
//		notNull(getCheckpoint(checkpointName), object, message);
//	}
//
//	protected static void notNull(Checkpoint checkpoint, Object object) {
//		notNull(checkpoint, object, null);
//	}
//
//	protected static void notNull(Checkpoint checkpoint, Object object, String message) {
//		if ((checkpoint.getEnabled() && object == null) || checkpoint.getForced()) {
//			if (message == null)
//				message = checkpoint.getMessage();
//			if (message == null)
//				message = "[check point failure] - object must not be null";
//			String exception = checkpoint.getException();
//			if (exception == null)
//				exception = "java.lang.RuntimeException";
//			throwException(message, exception);
//		}
//	}
	
	protected static void isValid(String checkpointName, Object object) {
		isValid(getCheckpoint(checkpointName), object);
	}
	
	protected static void isValid(String checkpointName, Object object, String message) {
		isValid(getCheckpoint(checkpointName), object, message);
	}

	protected static void isValid(Checkpoint checkpoint, Object object) {
		isValid(checkpoint, object, null);
	}

	//TODO accumulate all violations and report them all in one Exception
	protected static void isValid(Checkpoint checkpoint, Object object, String message) {
		List<Condition> conditions = checkpoint.getConditions();
		Iterator<Condition> iterator = conditions.iterator();
		while (iterator.hasNext()) {
			Condition condition = iterator.next();
			if (!isValid(condition, object) || condition.getForced())
				throwException(condition, message, null);
		}
	}
	
	protected static boolean isValid(Condition condition, Object object) {
		if (condition.getType().equals("notNull")) {
			Object fieldValue = null;
			String expression = condition.getExpression();
			if (!StringUtils.isEmpty(expression)) {
				String code = parseExpression(expression);
				fieldValue = executeExpression(object, code);
				if (fieldValue == null)
					return false;
				
			} else if (condition.getAnd() != null) {
				List<String> parts = condition.getAnd().getExpressions();
				Iterator<String> iterator = parts.iterator();
				while (iterator.hasNext()) {
					String part = iterator.next();
					String code = parseExpression(part);
					fieldValue = executeExpression(object, code);
					if (fieldValue == null)
						return false;
					
				}
				
			} else if (condition.getOr() != null) {
				List<String> parts = condition.getOr().getExpressions();
				Iterator<String> iterator = parts.iterator();
				while (iterator.hasNext()) {
					String part = iterator.next();
					String code = parseExpression(part);
					fieldValue = executeExpression(object, code);
					if (fieldValue == null)
						return false;
					
				}
				
			} else {
				fieldValue = object;
				if (fieldValue == null)
					return false;
			}
			
			return true;
		}
		
		if (condition.getType().equals("notEmpty")) {
			Object fieldValue = null;
			String expression = condition.getExpression();
			if (!StringUtils.isEmpty(expression)) {
				String code = parseExpression(expression);
				fieldValue = executeExpression(object, code);
				//String fieldName = getFieldName(object, expression);
				//Object fieldValue = getFieldValue(object, fieldName);
				if (fieldValue == null)
					return false;

			} else if (condition.getAnd() != null) {
				List<String> parts = condition.getAnd().getExpressions();
				Iterator<String> iterator = parts.iterator();
				while (iterator.hasNext()) {
					String part = iterator.next();
					String code = parseExpression(part);
					fieldValue = executeExpression(object, code);
					if (fieldValue == null)
						return false;
					
					if (fieldValue instanceof Integer) {
						Integer integerValue = (Integer) fieldValue;
						if (integerValue == 0)
							return false;
					}

					if (fieldValue instanceof Long) {
						Long longValue = (Long) fieldValue;
						if (longValue == 0)
							return false;
					}

					if (fieldValue instanceof Short) {
						Short shortValue = (Short) fieldValue;
						if (shortValue == 0)
							return false;
					}

					if (fieldValue instanceof Double) {
						Double doubleValue = (Double) fieldValue;
						if (doubleValue == 0)
							return false;
					}

					if (fieldValue instanceof Float) {
						Float floatValue = (Float) fieldValue;
						if (floatValue == 0)
							return false;
					}

					if (fieldValue instanceof Byte) {
						Byte byteValue = (Byte) fieldValue;
						if (byteValue == 0)
							return false;
					}

					if (fieldValue instanceof Date) {
						Date dateValue = (Date) fieldValue;
						return dateValue != null && dateValue.getTime() == 0;
					}

					if (fieldValue instanceof String) {
						String stringValue = (String) fieldValue;
						if (stringValue.length() == 0)
							return false;
					}
					
					if (fieldValue instanceof Date) {
						Date dateValue = (Date) fieldValue;
						if (dateValue != null && dateValue.getTime() == 0)
							return false;
					}

					if (fieldValue instanceof Collection) {
						Collection<?> collection = (Collection<?>) fieldValue;
						if (collection.size() == 0)
							return false;
					}
				}
				return true;
				
			} else if (condition.getOr() != null) {
				List<String> parts = condition.getOr().getExpressions();
				Iterator<String> iterator = parts.iterator();
				while (iterator.hasNext()) {
					String part = iterator.next();
					String code = parseExpression(part);
					fieldValue = executeExpression(object, code);
					if (fieldValue == null)
						continue;
					
					if (fieldValue instanceof Integer) {
						Integer integerValue = (Integer) fieldValue;
						if (integerValue != 0)
							return true;
					}
					
					if (fieldValue instanceof Long) {
						Long longValue = (Long) fieldValue;
						if (longValue != 0)
							return true;
					}

					if (fieldValue instanceof Short) {
						Short shortValue = (Short) fieldValue;
						if (shortValue != 0)
							return true;
					}

					if (fieldValue instanceof Double) {
						Double doubleValue = (Double) fieldValue;
						if (doubleValue != 0)
							return true;
					}

					if (fieldValue instanceof Float) {
						Float floatValue = (Float) fieldValue;
						if (floatValue != 0)
							return true;
					}

					if (fieldValue instanceof Byte) {
						Byte byteValue = (Byte) fieldValue;
						if (byteValue != 0)
							return true;
					}

					if (fieldValue instanceof String) {
						String stringValue = (String) fieldValue;
						if (stringValue.length() > 0)
							return true;
					}

					if (fieldValue instanceof Date) {
						Date dateValue = (Date) fieldValue;
						return dateValue != null && dateValue.getTime() > 0;
					}

					if (fieldValue instanceof Collection) {
						Collection<?> collection = (Collection<?>) fieldValue;
						if (collection.size() > 0)
							return true;
					}

				}
				return false;
				
			} else {
				fieldValue = object;
			}

			if (fieldValue instanceof Collection) {
				Collection<?> collection = (Collection<?>) fieldValue;
				return collection.size() != 0;
			}

			if (fieldValue instanceof Integer) {
				Integer integerValue = (Integer) fieldValue;
				return integerValue != 0;
			}

			if (fieldValue instanceof Long) {
				Long longValue = (Long) fieldValue;
				return longValue != 0;
			}

			if (fieldValue instanceof Short) {
				Short shortValue = (Short) fieldValue;
				return shortValue != 0;
			}

			if (fieldValue instanceof Double) {
				Double doubleValue = (Double) fieldValue;
				return doubleValue != 0;
			}

			if (fieldValue instanceof Float) {
				Float floatValue = (Float) fieldValue;
				return floatValue != 0;
			}

			if (fieldValue instanceof Byte) {
				Byte byteValue = (Byte) fieldValue;
				return byteValue != 0;
			}

			if (fieldValue instanceof Date) {
				Date dateValue = (Date) fieldValue;
				return dateValue != null && dateValue.getTime() != 0;
			}

			if (fieldValue instanceof String) {
				String fieldValueAsString = (String) fieldValue;
				return !StringUtils.isEmpty(fieldValueAsString);
			}

			if (fieldValue instanceof Enum<?>) {
				Enum<?> fieldValueAsEnum = (Enum<?>) fieldValue;
				return !StringUtils.isEmpty(fieldValueAsEnum.toString());
			}
			
			Class<?> fieldClass = fieldValue.getClass();
			throw new RuntimeException("Unexpected type: "+fieldClass);
		}
		
		if (condition.getType().equals("isTrue")) {
			Object fieldValue = null;
			String expression = condition.getExpression();
			if (!StringUtils.isEmpty(expression)) {
				String code = parseExpression(expression);
				fieldValue = executeExpression(object, code);
			} else {
				fieldValue = getFieldValue(object, "booleanField");
			}
			
			if (fieldValue == null)
				return false;
			if (fieldValue instanceof Boolean == false)
				return false;
			if (fieldValue.equals(Boolean.TRUE))
				return true;
			return false;
		}
		
		if (condition.getType().equals("isFalse")) {
			String expression = condition.getExpression();
			if (!StringUtils.isEmpty(expression)) {
				String code = parseExpression(expression);
				Object value = executeExpression(object, code);
				if (value instanceof Boolean == false)
					return false;
				if (value.equals(Boolean.FALSE))
					return true;
				return false;
			}
		}
		
		return true;
	}

	//TODO fix this...
	protected static String getFieldName(Object object, String expression) {
		Assert.notNull(expression, "Expression must be specified");
		String code = parseExpression(expression);
		Object result = executeExpression(object, code);
		return (String) result;
	}

	protected static Object getFieldValue(Object object, String fieldName) {
		try {
			Object fieldValue = FieldUtil.getFieldValue(object, fieldName);
			return fieldValue;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static void throwException(Condition condition, String message, String exception) {
		if (message == null)
			message = condition.getMessage();
		if (exception == null)
			exception = condition.getException();
		if (message == null)
			message = "[check point failure] - object must not be null";
		throwException(message, exception);
	}


	protected static void throwException(String message, String exception) {
		Class<?>[] parameterTypes = new Class<?>[] {String.class};
		Object[] parameterValues = new Object[] {message};
		Class<?> exceptionClass = null;
		if (exception != null) {
			try {
				exceptionClass = ClassUtil.loadClass(exception);
			} catch (ClassNotFoundException e) {
				//ignore for now
			}
		}
		if (exceptionClass == null)
			exceptionClass = AssertionFailure.class;
		Exception exceptionObject = ObjectUtil.loadObject(exceptionClass, parameterTypes, parameterValues);
		if (exceptionObject instanceof RuntimeException == false)
			throw new RuntimeException(exceptionObject);
		throw (RuntimeException) exceptionObject;
	}


	protected static String parseExpression(String expression) {
		//BPELExpressionParser parser = new BPELExpressionParser();
		//parser.setVariablesMap(variables);
		////parser.setLeftHandSide(leftHandSide);
		//parser.setVerbose(false);
		//Expr expr = buildExpression(expression);
		//String output = parser.visit(expr);
		if (expression.startsWith("$"))
			expression = expression.substring(1);
		expression = expression.replaceAll("/", ".");
		String output = ExpressionUtil.parseExpression(expression);
		return output;
	}
	
	protected static Expr buildExpression(String inputText) {
		Expr expr1 = XPath10Factory.create(inputText);
		if (expr1 instanceof UnaryExpr) {
			UnaryExpr unaryExpr = (UnaryExpr) expr1;
			expr1 = unaryExpr.getExpr();
		}
		return expr1;
	}

//	protected static Object executeCode(Object object, String code) {
//	}

	//TODO only supporting for now one "and" or one "or"
	protected static Object executeExpression(Object object, String code) {
		if (code.contains(" and ")) {
			int indexOf = code.indexOf(" and ");
			String block1 = code.substring(0, indexOf-1).trim();
			String block2 = code.substring(indexOf+1).trim();
			Object result1 = executeStatement(object, block1);
			Object result2 = executeStatement(object, block1);
			return null;
		}
		if (code.contains(" or ")) {
			int indexOf = code.indexOf(" or ");
			String block1 = code.substring(indexOf-1).trim();
			String block2 = code.substring(indexOf+1).trim();
			Object result1 = executeStatement(object, block1);
			Object result2 = executeStatement(object, block1);
			return null;
		}
		Object result = executeStatement(object, code);
		return result;
	}
	
	protected static Object executeStatement(Object object, String code) {
		//obtain method from code
		String objectName = null; 
		String methodName = null; 
		String callChain = null; 
		String subsequentCode = null; 

		int indexOfDot = code.indexOf(".");
		if (indexOfDot == -1) {
			callChain = code;
		} else {
			String firstToken = code.substring(0, indexOfDot);
			callChain = code.substring(indexOfDot+1);
			if (firstToken.contains("(")) {
				//method name
				methodName = firstToken;
				subsequentCode = callChain;

			} else {
				//variable name
				objectName = firstToken;
			}
		}
		
		if (methodName == null) {
			int nextIndexOfDot = callChain.indexOf(".");
			if (nextIndexOfDot == -1) {
				methodName = callChain;
				subsequentCode = null;
			} else {
				methodName = callChain.substring(0, nextIndexOfDot);
				subsequentCode = callChain.substring(nextIndexOfDot+1);
			}
		}
		
		//validate method has no args? assuming it for now...
		Assert.notNull(methodName, "Method name must exist");
		methodName = methodName.replace("()", "");

		try {
			//invoke methodName on object
			//String objectName = objectHierarchy;
			Object result = MethodUtils.invokeMethod(object, methodName, null);
			if (subsequentCode != null && result != null)
				//drill-down recursively to get the object to return
				result = executeStatement(result, subsequentCode);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//validate object has the method specified in the code
		//invoke method on the object
		return null;
	}
	
	//message.header.getExpired()
	protected static Object executeCodeOLD(Object object, String code) {
		//obtain method from code
		String methodName = null; 
		int indexOfDot = code.lastIndexOf(".");
		if (indexOfDot == -1) {
			methodName = code;
		} else {
			methodName = code.substring(indexOfDot+1);
		}
		//validate method has no args? assuming it for now...
		Assert.notNull(methodName, "Method name must exist");
		methodName = methodName.replace("()", "");
		
		//drill-down recursively to get the object to invoke
		String objectHierarchy = code.substring(0, indexOfDot);
		while (true) {
			int index = objectHierarchy.indexOf(".");
			if (index == -1) {

				//invoke methodName on objectName
				String objectName = objectHierarchy;
				try {
					Object result = MethodUtils.invokeMethod(object, methodName, null);
					return result;
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			} else {
				String fieldName = null; 
				String objectName = objectHierarchy.substring(0, index); 
				objectHierarchy = objectHierarchy.substring(index+1);
				int index2 = objectHierarchy.indexOf(".");
				if (index2 == -1) {
					fieldName = objectHierarchy;
				} else {
					fieldName = objectHierarchy.substring(0, index); 
				}
				
				try {
					String fieldNameCapped = NameUtil.capName(fieldName);
					Class<?>[] arguments = new Class<?>[] {};
					Method declaredMethod = object.getClass().getDeclaredMethod("get"+fieldNameCapped, arguments);
					if (declaredMethod == null)
						declaredMethod = object.getClass().getDeclaredMethod("is"+fieldNameCapped, null);
					Assert.notNull(declaredMethod, "Accessor method not found for: "+fieldName);
					
					Object[] args = new Object[] {};
					Object fieldInstance = declaredMethod.invoke(object, args);
					Assert.notNull(fieldInstance, "Field instance not found for: "+fieldName);
					object = fieldInstance;
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				continue;
			}
		}
		
		//validate object has the method specified in the code
		//invoke method on the object
		return null;
	}


}
