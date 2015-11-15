package org.aries.registry;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.Service;
import nam.model.util.ServiceUtil;

import org.aries.nam.model.old.OperationDefinition;
import org.aries.nam.model.old.OperationDescripter;
import org.aries.nam.model.old.ParameterDefinition;
import org.aries.nam.model.old.ParameterDescripter;
import org.aries.nam.model.old.ResultDefinition;
import org.aries.nam.model.old.ResultDescripter;


public class ServiceRegistryMapper {
	
	public static List<OperationDescripter> createOperationDescripters(Service service) {
		List<OperationDescripter> operationDescripters = new ArrayList<OperationDescripter>();
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> operationIterator = operations.iterator();
		while (operationIterator.hasNext()) {
			Operation operation = operationIterator.next();
			OperationDescripter operationDescripter = createOperationDescripter(operation);
			operationDescripters.add(operationDescripter);
		}
		
		return operationDescripters;
	}

	public static OperationDescripter createOperationDescripter(Method method) {
		OperationDefinition operationDescripter = new OperationDefinition();
		operationDescripter.setOperationName(method.getName());
    	List<ParameterDescripter> parameterDescripters = createParameterDescripters(method.getParameterTypes());
    	operationDescripter.getParameterDescripters().addAll(parameterDescripters);
    	ResultDescripter result = createResultDescripter(method.getReturnType());
		operationDescripter.getResultDescripters().add(result);
    	return operationDescripter;
    }
	
	public static OperationDescripter createOperationDescripter(Operation operation) {
		OperationDefinition operationDescripter = new OperationDefinition();
		operationDescripter.setOperationName(operation.getName());
		operationDescripter.setParameterDescripters(createParameterDescripters(operation.getParameters()));
		operationDescripter.setResultDescripters(createResultDescripters(operation.getResults()));
		return operationDescripter;
	}
	
	public static List<ParameterDescripter> createParameterDescripters(List<Parameter> parameters) {
		List<ParameterDescripter> parameterDescripters = new ArrayList<ParameterDescripter>();
		if (parameters != null) {
			Iterator<Parameter> iterator = parameters.iterator();
			while (iterator.hasNext()) {
				Parameter parameter = iterator.next();
				ParameterDescripter parameterDescripter = createParameterDescripter(parameter);
				parameterDescripters.add(parameterDescripter);
			}
		}
    	return parameterDescripters;
	}

	public static List<ParameterDescripter> createParameterDescripters(Class<?>[] parameterTypes) {
		List<ParameterDescripter> parameterDescripters = new ArrayList<ParameterDescripter>();
		if (parameterTypes != null) {
	    	for (Class<?> parameterType : parameterTypes) {
	    		ParameterDescripter parameterDescripter = createParameterDescripter(parameterType);
				parameterDescripters.add(parameterDescripter);
			}
		}
    	return parameterDescripters;
	}
	
	public static ParameterDescripter createParameterDescripter(Parameter parameter) {
		ParameterDefinition parameterDescripter = new ParameterDefinition();
		parameterDescripter.setName(parameter.getName());
		parameterDescripter.setType(parameter.getType());
		return parameterDescripter;
	}
	
	public static ParameterDescripter createParameterDescripter(Class<?> parameterType) {
		ParameterDefinition parameterDescripter = new ParameterDefinition();
		parameterDescripter.setName(parameterType.getName());
		parameterDescripter.setType(parameterType.getCanonicalName());
		return parameterDescripter;
	}

	public static List<ResultDescripter> createResultDescripters(List<Result> results) {
		List<ResultDescripter> resultDescripters = new ArrayList<ResultDescripter>();
		if (results != null) {
			Iterator<Result> iterator = results.iterator();
			while (iterator.hasNext()) {
				Result result = iterator.next();
				ResultDescripter resultDescripter = createResultDescripter(result);
				resultDescripters.add(resultDescripter);
			}
		}
    	return resultDescripters;
	}

	public static ResultDescripter createResultDescripter(Result result) {
		if (result != null) {
			ResultDefinition resultDescripter = new ResultDefinition();
			resultDescripter.setName(result.getName());
			resultDescripter.setType(result.getType());
			return resultDescripter;
		}
		return null;
	}
	
	public static ResultDescripter createResultDescripter(Class<?> returnType) {
		if (returnType != null) {
			ResultDefinition resultDescripter = new ResultDefinition();
			resultDescripter.setName("result");
			resultDescripter.setType(returnType.getCanonicalName());
			return resultDescripter;
		}
		return null;
	}

}
