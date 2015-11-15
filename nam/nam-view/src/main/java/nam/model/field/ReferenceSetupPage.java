package nam.model.field;

import java.io.Serializable;

import nam.model.Field;
import nam.model.Reference;


@SuppressWarnings("serial")
public class ReferenceSetupPage extends AbstractFieldSetupPage<Reference> implements Serializable {

	//private static Log log = LogFactory.getLog(ReferenceSetupPage.class);

	private Reference reference;

	
	public ReferenceSetupPage(String owner) {
		setTitle("Specify Reference details.");
		//initialize(reference);
		setOwner(owner);
	}

	@Override
	protected Field getField() {
		return getReference();
	}
	
	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	public void initialize(Reference reference) {
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(true);
		setReference(reference);
	}
	
	public void validate() {
		super.validate(reference);
	}
	
}
