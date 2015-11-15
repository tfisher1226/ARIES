package nam.model.util;

import java.util.Iterator;
import java.util.List;

import nam.model.ServiceState;
import nam.model.ServiceVariable;


public class ServiceStateUtil {

	public static String getVariableKey(String type, String name) {
		return type+"/"+name;
	}

	public static String getVariableKey(ServiceVariable serviceVariable) {
		return getVariableKey(serviceVariable.getType(), serviceVariable.getName());
	}

	public static List<ServiceVariable> getVariables(ServiceState serviceState) {
		List<ServiceVariable> variables = serviceState.getVariables();
		return variables;
	}

	public static ServiceVariable getVariableByName(ServiceState serviceState, String name) {
		List<ServiceVariable> variables = serviceState.getVariables();
		Iterator<ServiceVariable> iterator = variables.iterator();
		while (iterator.hasNext()) {
			ServiceVariable serviceVariable = (ServiceVariable) iterator.next();
			if (serviceVariable.getName().equals(name))
				return serviceVariable;
		}
		return null;
	}

	public static ServiceVariable getVariable(ServiceState serviceState, String type, String name) {
		String variableKey = getVariableKey(type, name);
		List<ServiceVariable> variables = serviceState.getVariables();
		Iterator<ServiceVariable> iterator = variables.iterator();
		while (iterator.hasNext()) {
			ServiceVariable serviceVariable = (ServiceVariable) iterator.next();
			String key = getVariableKey(serviceVariable);
			if (variableKey.equals(key))
				return serviceVariable;
		}
		return null;
	}

}
