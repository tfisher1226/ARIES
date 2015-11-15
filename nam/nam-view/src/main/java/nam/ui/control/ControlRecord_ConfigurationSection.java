package nam.ui.control;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.ui.Control;
import nam.ui.util.ControlUtil;


@SessionScoped
@Named("controlConfigurationSection")
public class ControlRecord_ConfigurationSection extends AbstractWizardPage<Control> implements Serializable {
	
	private Control control;
	
	
	public ControlRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
	}
	
	
	public Control getControl() {
		return control;
	}
	
	public void setControl(Control control) {
		this.control = control;
	}
	
	@Override
	public void initialize(Control control) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setControl(control);
	}
	
	@Override
	public void validate() {
		if (control == null) {
			validator.missing("Control");
		} else {
		}
	}
	
}
