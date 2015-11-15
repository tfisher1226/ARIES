package nam.model.variable;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Variable;
import nam.model.util.VariableUtil;


public class VariableListObject extends AbstractListObject<Variable> implements Comparable<VariableListObject>, Serializable {
	
	private Variable variable;
	
	
	public VariableListObject(Variable variable) {
		this.variable = variable;
	}
	
	
	public Variable getVariable() {
		return variable;
	}
	
	@Override
	public Object getKey() {
		return getKey(variable);
	}
	
	public Object getKey(Variable variable) {
		return VariableUtil.getKey(variable);
	}
	
	@Override
	public String getLabel() {
		return getLabel(variable);
	}
	
	public String getLabel(Variable variable) {
		return VariableUtil.getLabel(variable);
	}
	
	@Override
	public String toString() {
		return toString(variable);
	}
	
	@Override
	public String toString(Variable variable) {
		return VariableUtil.toString(variable);
	}
	
	@Override
	public int compareTo(VariableListObject other) {
		Object thisKey = getKey(this.variable);
		Object otherKey = getKey(other.variable);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		VariableListObject other = (VariableListObject) object;
		Object thisKey = VariableUtil.getKey(this.variable);
		Object otherKey = VariableUtil.getKey(other.variable);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
