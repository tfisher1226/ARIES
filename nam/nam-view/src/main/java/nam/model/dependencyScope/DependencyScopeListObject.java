package nam.model.dependencyScope;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.DependencyScope;


public class DependencyScopeListObject extends AbstractListObject<DependencyScope> implements Comparable<DependencyScopeListObject>, Serializable {
	
	private DependencyScope dependencyScope;
	
	
	public DependencyScopeListObject(DependencyScope dependencyScope) {
		this.dependencyScope = dependencyScope;
	}
	
	
	public DependencyScope getDependencyScope() {
		return dependencyScope;
	}
	
	@Override
	public Object getKey() {
		return dependencyScope.name();
	}
	
	@Override
	public String getLabel() {
		return dependencyScope.name();
	}
	
	@Override
	public String toString() {
		return toString(dependencyScope);
	}
	
	@Override
	public String toString(DependencyScope dependencyScope) {
		return dependencyScope.name();
	}
	
	@Override
	public int compareTo(DependencyScopeListObject other) {
		String thisText = this.dependencyScope.name();
		String otherText = other.dependencyScope.name();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		DependencyScopeListObject other = (DependencyScopeListObject) object;
		String thisText = toString(this.dependencyScope);
		String otherText = toString(other.dependencyScope);
		return thisText.equals(otherText);
	}
	
}
