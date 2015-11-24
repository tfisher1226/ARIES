package nam.model.component;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Component;
import nam.model.util.ComponentUtil;


@SessionScoped
@Named("componentDocumentationSection")
public class ComponentRecord_DocumentationSection extends AbstractWizardPage<Component> implements Serializable {
	
	private Component component;

	
	public ComponentRecord_DocumentationSection() {
		setName("Documentation");
		setUrl("documentation");
	}

	
	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	@Override
	public void initialize(Component component) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setComponent(component);
	}
	
	@Override
	public void validate() {
		if (component == null) {
			validator.missing("Component");
		} else {
		}
	}

}