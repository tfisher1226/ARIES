package nam.model.operation;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Operation;
import nam.model.util.OperationUtil;


public class OperationListObject extends AbstractListObject<Operation> implements Comparable<OperationListObject>, Serializable {
	
	private Operation operation;
	
	
	public OperationListObject(Operation operation) {
		this.operation = operation;
	}
	
	
	public Operation getOperation() {
		return operation;
	}
	
	@Override
	public Object getKey() {
		return getKey(operation);
	}
	
	public Object getKey(Operation operation) {
		return OperationUtil.getKey(operation);
	}
	
	@Override
	public String getLabel() {
		return getLabel(operation);
	}
	
	public String getLabel(Operation operation) {
		return OperationUtil.getLabel(operation);
	}
	
	@Override
	public String toString() {
		return toString(operation);
	}
	
	@Override
	public String toString(Operation operation) {
		return OperationUtil.toString(operation);
	}
	
	@Override
	public int compareTo(OperationListObject other) {
		Object thisKey = getKey(this.operation);
		Object otherKey = getKey(other.operation);
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
		OperationListObject other = (OperationListObject) object;
		Object thisKey = OperationUtil.getKey(this.operation);
		Object otherKey = OperationUtil.getKey(other.operation);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
