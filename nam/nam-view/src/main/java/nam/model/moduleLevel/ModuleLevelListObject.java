package nam.model.moduleLevel;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.ModuleLevel;


public class ModuleLevelListObject extends AbstractListObject<ModuleLevel> implements Comparable<ModuleLevelListObject>, Serializable {
	
	private ModuleLevel moduleLevel;
	
	
	public ModuleLevelListObject(ModuleLevel moduleLevel) {
		this.moduleLevel = moduleLevel;
	}
	
	
	public ModuleLevel getModuleLevel() {
		return moduleLevel;
	}
	
	@Override
	public Object getKey() {
		return moduleLevel.name();
	}
	
	@Override
	public String getLabel() {
		return moduleLevel.name();
	}
	
	@Override
	public String toString() {
		return toString(moduleLevel);
	}
	
	@Override
	public String toString(ModuleLevel moduleLevel) {
		return moduleLevel.name();
	}
	
	@Override
	public int compareTo(ModuleLevelListObject other) {
		String thisText = this.moduleLevel.name();
		String otherText = other.moduleLevel.name();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ModuleLevelListObject other = (ModuleLevelListObject) object;
		String thisText = toString(this.moduleLevel);
		String otherText = toString(other.moduleLevel);
		return thisText.equals(otherText);
	}
	
}
