package nam.model.dependency;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Dependency;
import nam.model.util.DependencyUtil;


public class DependencyListObject extends AbstractListObject<Dependency> implements Comparable<DependencyListObject>, Serializable {
	
	private Dependency dependency;
	
	
	public DependencyListObject(Dependency dependency) {
		this.dependency = dependency;
	}
	
	
	public Dependency getDependency() {
		return dependency;
	}
	
	@Override
	public Object getKey() {
		return getKey(dependency);
	}
	
	public Object getKey(Dependency dependency) {
		return DependencyUtil.getKey(dependency);
	}
	
	@Override
	public String getLabel() {
		return getLabel(dependency);
	}
	
	public String getLabel(Dependency dependency) {
		return DependencyUtil.getLabel(dependency);
	}
	
	@Override
	public String toString() {
		return toString(dependency);
	}
	
	@Override
	public String toString(Dependency dependency) {
		return DependencyUtil.toString(dependency);
	}
	
	@Override
	public int compareTo(DependencyListObject other) {
		Object thisKey = getKey(this.dependency);
		Object otherKey = getKey(other.dependency);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		DependencyListObject other = (DependencyListObject) object;
		Object thisKey = DependencyUtil.getKey(this.dependency);
		Object otherKey = DependencyUtil.getKey(other.dependency);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
