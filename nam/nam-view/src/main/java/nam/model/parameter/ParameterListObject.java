package nam.model.parameter;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Parameter;
import nam.model.util.ParameterUtil;


public class ParameterListObject extends AbstractListObject<Parameter> implements Comparable<ParameterListObject>, Serializable {
	
	private Parameter parameter;
	
	
	public ParameterListObject(Parameter parameter) {
		this.parameter = parameter;
	}
	
	
	public Parameter getParameter() {
		return parameter;
	}
	
	@Override
	public Object getKey() {
		return getKey(parameter);
	}
	
	public Object getKey(Parameter parameter) {
		return ParameterUtil.getKey(parameter);
	}
	
	@Override
	public String getLabel() {
		return getLabel(parameter);
	}
	
	public String getLabel(Parameter parameter) {
		return ParameterUtil.getLabel(parameter);
	}
	
	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
	}
	
	@Override
	public String getIcon() {
		return "/icons/nam/Parameter16.gif";
	}
	
	@Override
	public String toString() {
		return toString(parameter);
	}
	
	@Override
	public String toString(Parameter parameter) {
		return ParameterUtil.toString(parameter);
	}
	
	@Override
	public int compareTo(ParameterListObject other) {
		Object thisKey = getKey(this.parameter);
		Object otherKey = getKey(other.parameter);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ParameterListObject other = (ParameterListObject) object;
		Object thisKey = ParameterUtil.getKey(this.parameter);
		Object otherKey = ParameterUtil.getKey(other.parameter);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
