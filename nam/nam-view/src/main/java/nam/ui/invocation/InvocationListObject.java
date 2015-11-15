package nam.ui.invocation;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.ui.Invocation;
import nam.ui.util.InvocationUtil;


public class InvocationListObject extends AbstractListObject<Invocation> implements Comparable<InvocationListObject>, Serializable {
	
	private Invocation invocation;
	
	
	public InvocationListObject(Invocation invocation) {
		this.invocation = invocation;
	}
	
	
	public Invocation getInvocation() {
		return invocation;
	}
	
	@Override
	public Object getKey() {
		return getKey(invocation);
	}
	
	public Object getKey(Invocation invocation) {
		return InvocationUtil.getKey(invocation);
	}
	
	@Override
	public String getLabel() {
		return getLabel(invocation);
	}
	
	public String getLabel(Invocation invocation) {
		return InvocationUtil.getLabel(invocation);
	}
	
	@Override
	public String toString() {
		return toString(invocation);
	}
	
	@Override
	public String toString(Invocation invocation) {
		return InvocationUtil.toString(invocation);
	}
	
	@Override
	public int compareTo(InvocationListObject other) {
		Object thisKey = getKey(this.invocation);
		Object otherKey = getKey(other.invocation);
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
		InvocationListObject other = (InvocationListObject) object;
		Object thisKey = InvocationUtil.getKey(this.invocation);
		Object otherKey = InvocationUtil.getKey(other.invocation);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
