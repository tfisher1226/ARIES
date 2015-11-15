package nam.model.module;

import java.io.Serializable;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractListObject;

import nam.model.Module;
import nam.model.util.ModuleUtil;


public class ModuleListObject extends AbstractListObject<Module> implements Comparable<ModuleListObject>, Serializable {
	
	private Module module;
	
	
	public ModuleListObject(Module module) {
		this.module = module;
	}
	
	
	public Module getModule() {
		return module;
	}
	
	@Override
	public Object getKey() {
		return getKey(module);
	}
	
	public Object getKey(Module module) {
		return ModuleUtil.getKey(module);
	}
	
	@Override
	public String getLabel() {
		return getLabel(module);
	}
	
	public String getLabel(Module module) {
		return ModuleUtil.getLabel(module);
	}
	
	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
		fireChangeEvent(module, checked);
	}
	
	protected static void fireChangeEvent(Module module, boolean checked) {
		ModuleEventManager eventManager = BeanContext.getFromSession("moduleEventManager");
		if (checked) {
			eventManager.fireSelectedEvent(module);
		} else {
			eventManager.fireUnselectedEvent(module);
		}
	}
	
	@Override
	public String toString() {
		return toString(module);
	}
	
	@Override
	public String toString(Module module) {
		return ModuleUtil.toString(module);
	}
	
	@Override
	public int compareTo(ModuleListObject other) {
		Object thisKey = getKey(this.module);
		Object otherKey = getKey(other.module);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ModuleListObject other = (ModuleListObject) object;
		Object thisKey = ModuleUtil.getKey(this.module);
		Object otherKey = ModuleUtil.getKey(other.module);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
