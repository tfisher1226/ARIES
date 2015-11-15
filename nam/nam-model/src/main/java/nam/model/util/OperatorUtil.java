package nam.model.util;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Result;

import org.aries.Assert;
import org.aries.util.TypeMap;


public class OperatorUtil {

	public static Operation createOperation(Method method) {
		Operation operation = new Operation();
		operation.setName(method.getName());

		Class<?>[] parameterTypes = method.getParameterTypes();
		for (int i = 0; i < parameterTypes.length; i++) {
			Class<?> parameterType = parameterTypes[i];
			Parameter parameter = ParameterUtil.createParameter(parameterType);
			String typeName = TypeMap.INSTANCE.getTypeName(parameterType);
			Assert.notNull(typeName, "ParameterType for method \""+method.getName()+"\" not found: "+parameterType.getCanonicalName());
			operation.getParameters().add(parameter);
		}
		
		Class<?> returnType = method.getReturnType();
		if (returnType != null) {
			Result result = ResultUtil.createResult(returnType);
			String typeName = result.getType();
			Assert.notNull(typeName, "ResultType for method \""+method.getName()+"\" not found: "+returnType.getCanonicalName());
			result.setType(typeName);
			operation.addToResults(result);
		}
		
		return operation;
	}
	
	public static boolean equals(Operation operation1, Operation operation2) {
		Assert.notNull(operation1, "Operation1 must be specified");
		Assert.notNull(operation2, "Operation2 must be specified");
		Assert.notNull(operation1.getName(), "Operation1 name must be specified");
		Assert.notNull(operation2.getName(), "Operation2 name must be specified");
		if (!operation1.getName().equals(operation2.getName()))
			return false;
		if (!ParameterUtil.equals(operation1.getParameters(), operation2.getParameters()))
			return false;
		if (!FaultUtil.equals(operation1.getFaults(), operation2.getFaults()))
			return false;
		if (!ResultUtil.equals(operation1.getResults(), operation2.getResults()))
			return false;
		return true;
	}

	public static Parameter getParameter(Operation operation) {
		if (operation.getParameters().size() > 0)
			return operation.getParameters().get(0);
		return null;
	}
	
	public static boolean containsParameter(Operation operation, Parameter parameter) {
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter existingParameter = iterator.next();
			if (ParameterUtil.equals(existingParameter, parameter))
				return true;
		}
		return false;
	}

	public static void addParameter(Operation operation, Parameter parameter) {
		if (!OperatorUtil.containsParameter(operation, parameter))
			operation.getParameters().add(parameter);
	}

	public static String getOperatorName(String operator) {
		if (operator.equals("==")) 
			return "EQ";
		if (operator.equals(">")) 
			return "GT";
		if (operator.equals("<")) 
			return "LT";
		if (operator.equals(">=")) 
			return "GE";
		if (operator.equals("<=")) 
			return "LE";
		return "";
	}
	
}
