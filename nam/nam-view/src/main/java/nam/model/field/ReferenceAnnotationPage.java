package nam.model.field;

import java.io.Serializable;

import nam.model.Reference;
import nam.model.annotation.AnnotationListManager;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractWizardPage;


@SuppressWarnings("serial")
public class ReferenceAnnotationPage extends AbstractWizardPage<Reference> implements Serializable {

	private Reference reference;
	
	private AnnotationListManager annotationListManager;
	

	public ReferenceAnnotationPage(String owner) {
		setTitle("Select Annotation.");
		//initialize(field);
		setOwner(owner);
	}

	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}
	
	public boolean isVisible() {
		return super.isVisible();
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
	}

	public void initialize(Reference reference) {
		//annotationListManager = BeanContext.get("annotationListManager");
		//annotationListManager.refresh(reference);
		setBackEnabled(true);
		setNextEnabled(false);
		setFinishEnabled(true);
		setReference(reference);
	}

	public void validate() {
	}

}
