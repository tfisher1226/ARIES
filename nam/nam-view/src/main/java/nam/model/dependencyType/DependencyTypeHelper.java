package nam.model.dependencyType;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.aries.ui.manager.AbstractEnumerationHelper;

import nam.model.DependencyType;
import nam.model.util.DependencyTypeUtil;


@SessionScoped
@Named("dependencyTypeHelper")
public class DependencyTypeHelper extends AbstractEnumerationHelper<DependencyType> implements Serializable {
	
	@Produces
	public DependencyType[] getDependencyTypeArray() {
		return DependencyType.values();
	}
	
	@Override
	public String toString(DependencyType dependencyType) {
		return dependencyType.name();
	}
	
	@Override
	public String toString(Collection<DependencyType> dependencyTypeList) {
		return DependencyTypeUtil.toString(dependencyTypeList);
	}
	
}
