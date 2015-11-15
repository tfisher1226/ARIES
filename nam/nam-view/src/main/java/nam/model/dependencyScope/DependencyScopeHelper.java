package nam.model.dependencyScope;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.aries.ui.manager.AbstractEnumerationHelper;

import nam.model.DependencyScope;
import nam.model.util.DependencyScopeUtil;


@SessionScoped
@Named("dependencyScopeHelper")
public class DependencyScopeHelper extends AbstractEnumerationHelper<DependencyScope> implements Serializable {
	
	@Produces
	public DependencyScope[] getDependencyScopeArray() {
		return DependencyScope.values();
	}
	
	@Override
	public String toString(DependencyScope dependencyScope) {
		return dependencyScope.name();
	}
	
	@Override
	public String toString(Collection<DependencyScope> dependencyScopeList) {
		return DependencyScopeUtil.toString(dependencyScopeList);
	}
	
}
