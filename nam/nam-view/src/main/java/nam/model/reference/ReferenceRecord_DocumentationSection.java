package nam.model.reference;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.Reference;
import nam.model.util.ReferenceUtil;


@SessionScoped
@Named("referenceDocumentationSection")
public class ReferenceRecord_DocumentationSection extends AbstractWizardPage<Reference> implements Serializable {
	
	private Reference reference;

	
	public ReferenceRecord_DocumentationSection() {
		setName("Documentation");
		setUrl("documentation");
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
