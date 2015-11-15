package nam.model.dependencyType;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.DependencyType;


public class DependencyTypeListObject extends AbstractListObject<DependencyType> implements Comparable<DependencyTypeListObject>, Serializable {
	
	private DependencyType dependencyType;
	
	
	public DependencyTypeListObject(DependencyType dependencyType) {
		this.dependencyType = dependencyType;
	}
	
	
	public DependencyType getDependencyType() {
		return dependencyType;
	}
	
	@Override
	public Object getKey() {
		return dependencyType.name();
	}
	
	@Override
	public String getLabel() {
		return dependencyType.name();
	}
	
	@Override
	public String toString() {
		return toString(dependencyType);
	}
	
	@Override
	public String toString(DependencyType dependencyType) {
		return dependencyType.name();
	}
	
	@Override
	public int compareTo(DependencyTypeListObject other) {
		String thisText = this.dependencyType.name();
		String otherText = other.dependencyType.name();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		DependencyTypeListObject other = (DependencyTypeListObject) object;
		String thisText = toString(this.dependencyType);
		String otherText = toString(other.dependencyType);
		return thisText.equals(otherText);
	}
	
}
