package nam.model.util;

import java.util.Iterator;
import java.util.List;

import nam.model.ProcessState;
import nam.model.Variable;


public class ProcessStateUtil {

	public static String getVariableKey(String type, String name) {
		return type+"/"+name;
	}

	public static String getVariableKey(nam.model.Variable variable) {
		return getVariableKey(variable.getType(), variable.getName());
	}

	public static List<nam.model.Variable> getVariables(ProcessState ProcessState) {
		List<nam.model.Variable> variables = ProcessState.getVariables();
		return variables;
	}

	public static nam.model.Variable getVariableByName(ProcessState ProcessState, String name) {
		List<nam.model.Variable> variables = ProcessState.getVariables();
		Iterator<nam.model.Variable> iterator = variables.iterator();
		while (iterator.hasNext()) {
			nam.model.Variable variable = (nam.model.Variable) iterator.next();
			if (variable.getName().equals(name))
				return variable;
		}
		return null;
	}

	public static nam.model.Variable getVariable(ProcessState ProcessState, String type, String name) {
		String variableKey = getVariableKey(type, name);
		List<nam.model.Variable> variables = ProcessState.getVariables();
		Iterator<nam.model.Variable> iterator = variables.iterator();
		while (iterator.hasNext()) {
			nam.model.Variable variable = (nam.model.Variable) iterator.next();
			String key = getVariableKey(variable);
			if (variableKey.equals(key))
				return variable;
		}
		return null;
	}

}
