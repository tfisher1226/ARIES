package nam.ui.control;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.ui.Control;
import nam.ui.util.ControlUtil;


@SessionScoped
@Named("controlIdentificationSection")
public class ControlRecord_IdentificationSection extends AbstractWizardPage<Control> implements Serializable {
	
	private Control control;
	
	
	public ControlRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
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
