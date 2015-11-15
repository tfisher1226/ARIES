package nam.model.reference;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Reference;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;


@SessionScoped
@Named("referenceIdentificationSection")
public class ReferenceRecord_IdentificationSection extends AbstractWizardPage<Reference> implements Serializable {

	private Reference reference;

	
	public ReferenceRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setReference(reference);
	}
	
	@Override
	public void validate() {
		if (reference == null) {
			validator.missing("Reference");
		} else {
			if (StringUtils.isEmpty(reference.getName()))
				validator.missing("Name");
			if (StringUtils.isEmpty(reference.getLabel()))
				validator.missing("Label");
			if (StringUtils.isEmpty(reference.getStructure()))
				validator.missing("Structure");
			if (StringUtils.isEmpty(reference.getNamespace()))
				validator.missing("Namespace");
		}
	}

}
