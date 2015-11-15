package nam.model.dependency;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Dependency;
import nam.model.util.DependencyUtil;


@SessionScoped
@Named("dependencyHelper")
public class DependencyHelper extends AbstractElementHelper<Dependency> implements Serializable {
	
	@Override
	public boolean isEmpty(Dependency dependency) {
		return DependencyUtil.isEmpty(dependency);
	}
	
	@Override
	public String toString(Dependency dependency) {
		return DependencyUtil.toString(dependency);
	}
	
	@Override
	public String toString(Collection<Dependency> dependencyList) {
		return DependencyUtil.toString(dependencyList);
	}
	
	@Override
	public boolean validate(Dependency dependency) {
		return DependencyUtil.validate(dependency);
	}
	
	@Override
	public boolean validate(Collection<Dependency> dependencyList) {
		return DependencyUtil.validate(dependencyList);
	}
	
}
