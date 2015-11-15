package nam.model.moduleLevel;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.aries.ui.manager.AbstractEnumerationHelper;

import nam.model.ModuleLevel;
import nam.model.util.ModuleLevelUtil;


@SessionScoped
@Named("moduleLevelHelper")
public class ModuleLevelHelper extends AbstractEnumerationHelper<ModuleLevel> implements Serializable {
	
	@Produces
	public ModuleLevel[] getModuleLevelArray() {
		return ModuleLevel.values();
	}
	
	@Override
	public String toString(ModuleLevel moduleLevel) {
		return moduleLevel.name();
	}
	
	@Override
	public String toString(Collection<ModuleLevel> moduleLevelList) {
		return ModuleLevelUtil.toString(moduleLevelList);
	}
	
}
