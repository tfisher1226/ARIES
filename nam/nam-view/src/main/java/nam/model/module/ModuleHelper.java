package nam.model.module;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Module;
import nam.model.util.ModuleUtil;


@SessionScoped
@Named("moduleHelper")
public class ModuleHelper extends AbstractElementHelper<Module> implements Serializable {
	
	@Override
	public boolean isEmpty(Module module) {
		return ModuleUtil.isEmpty(module);
	}
	
	@Override
	public String toString(Module module) {
		return ModuleUtil.toString(module);
	}
	
	@Override
	public String toString(Collection<Module> moduleList) {
		return ModuleUtil.toString(moduleList);
	}
	
	@Override
	public boolean validate(Module module) {
		return ModuleUtil.validate(module);
	}
	
	@Override
	public boolean validate(Collection<Module> moduleList) {
		return ModuleUtil.validate(moduleList);
	}
	
}
