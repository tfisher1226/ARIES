package nam.model.dependency;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Dependency;
import nam.model.util.DependencyUtil;


@SessionScoped
@Named("dependencyIdentificationSection")
public class DependencyRecord_IdentificationSection extends AbstractWizardPage<Dependency> implements Serializable {
	
	private Dependency dependency;
	
	
	public DependencyRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
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
