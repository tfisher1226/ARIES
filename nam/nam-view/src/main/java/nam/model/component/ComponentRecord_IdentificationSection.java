package nam.model.component;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Component;
import nam.model.util.ComponentUtil;


@SessionScoped
@Named("componentIdentificationSection")
public class ComponentRecord_IdentificationSection extends AbstractWizardPage<Component> implements Serializable {
	
	private Component component;

	
	public ComponentRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setComponent(component);
	}
	
	@Override
	public void validate() {
		if (component == null) {
			validator.missing("Component");
		} else {
			if (StringUtils.isEmpty(component.getName()))
				validator.missing("Name");
			if (StringUtils.isEmpty(component.getBaseType()))
				validator.missing("BaseType");
			if (StringUtils.isEmpty(component.getType()))
				component.setType(component.getBaseType());
		}
	}

}
