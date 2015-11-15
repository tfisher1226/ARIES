package nam.model.moduleType;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.ModuleType;


public class ModuleTypeListObject extends AbstractListObject<ModuleType> implements Comparable<ModuleTypeListObject>, Serializable {
	
	private ModuleType moduleType;
	
	
	public ModuleTypeListObject(ModuleType moduleType) {
		this.moduleType = moduleType;
	}
	
	
	public ModuleType getModuleType() {
		return moduleType;
	}
	
	@Override
	public Object getKey() {
		return moduleType.name();
	}
	
	@Override
	public String getLabel() {
		return moduleType.name();
	}
	
	@Override
	public String toString() {
		return toString(moduleType);
	}
	
	@Override
	public String toString(ModuleType moduleType) {
		return moduleType.name();
	}
	
	@Override
	public int compareTo(ModuleTypeListObject other) {
		String thisText = this.moduleType.name();
		String otherText = other.moduleType.name();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ModuleTypeListObject other = (ModuleTypeListObject) object;
		String thisText = toString(this.moduleType);
		String otherText = toString(other.moduleType);
		return thisText.equals(otherText);
	}
	
}
