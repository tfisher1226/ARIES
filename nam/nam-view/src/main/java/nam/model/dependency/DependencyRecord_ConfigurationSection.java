package nam.model.dependency;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Dependency;
import nam.model.util.DependencyUtil;


@SessionScoped
@Named("dependencyConfigurationSection")
public class DependencyRecord_ConfigurationSection extends AbstractWizardPage<Dependency> implements Serializable {
	
	private Dependency dependency;
	
	
	public DependencyRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
	}
	
	
	public Dependency getDependency() {
		return dependency;
	}
	
	public void setDependency(Dependency dependency) {
		this.dependency = dependency;
	}
	
	@Override
	public void initialize(Dependency dependency) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setDependency(dependency);
	}
	
	@Override
	public void validate() {
		if (dependency == null) {
			validator.missing("Dependency");
		} else {
		}
	}
	
}
