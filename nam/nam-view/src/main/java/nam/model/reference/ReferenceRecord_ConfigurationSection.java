package nam.model.reference;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.Reference;
import nam.model.util.ReferenceUtil;


@SessionScoped
@Named("referenceConfigurationSection")
public class ReferenceRecord_ConfigurationSection extends AbstractWizardPage<Reference> implements Serializable {

	private Reference reference;

	
	public ReferenceRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
	}
	
	
	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	@Override
	public void initialize(Reference reference) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setReference(reference);
	}
	
	@Override
	public void validate() {
		if (reference == null) {
			validator.missing("Reference");
		} else {
		}
	}
	
}
