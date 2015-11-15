package nam.model.modules;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Modules;
import nam.model.util.ModulesUtil;


public class ModulesListObject extends AbstractListObject<Modules> implements Comparable<ModulesListObject>, Serializable {
	
	private Modules modules;
	
	
	public ModulesListObject(Modules modules) {
		this.modules = modules;
	}
	
	
	public Modules getModules() {
		return modules;
	}
	
	@Override
	public String getLabel() {
		return toString(modules);
	}
	
	@Override
	public String toString() {
		return toString(modules);
	}
	
	@Override
	public String toString(Modules modules) {
		return ModulesUtil.toString(modules);
	}
	
	@Override
	public int compareTo(ModulesListObject other) {
		String thisText = toString(this.modules);
		String otherText = toString(other.modules);
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ModulesListObject other = (ModulesListObject) object;
		return this == other;
	}
	
}
