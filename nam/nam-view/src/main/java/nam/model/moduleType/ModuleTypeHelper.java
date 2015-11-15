package nam.model.moduleType;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.aries.ui.manager.AbstractEnumerationHelper;

import nam.model.ModuleType;
import nam.model.util.ModuleTypeUtil;


@SessionScoped
@Named("moduleTypeHelper")
public class ModuleTypeHelper extends AbstractEnumerationHelper<ModuleType> implements Serializable {
	
	@Produces
	public ModuleType[] getModuleTypeArray() {
		return ModuleType.values();
	}
	
	@Override
	public String toString(ModuleType moduleType) {
		return moduleType.name();
	}
	
	@Override
	public String toString(Collection<ModuleType> moduleTypeList) {
		return ModuleTypeUtil.toString(moduleTypeList);
	}
	
}
