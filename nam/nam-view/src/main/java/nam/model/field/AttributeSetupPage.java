package nam.model.field;

import java.io.Serializable;

import nam.model.Attribute;
import nam.model.Field;


@SuppressWarnings("serial")
public class AttributeSetupPage extends AbstractFieldSetupPage<Attribute> implements Serializable {

	//private static Log log = LogFactory.getLog(AttributeSetupPage.class);

	private Attribute attribute;

	
	public AttributeSetupPage(String owner) {
		//setTitle("Specify Attribute details.");
		//initialize(attribute);
		setOwner(owner);
	}

	@Override
	protected Field getField() {
		return getAttribute();
	}
	
	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public String getMinOccurs() {
		return attribute != null ? Integer.toString(attribute.getMinOccurs()) : null;
	}

	public void setMinOccurs(String minOccurs) {
		attribute.setMinOccurs(Integer.parseInt(minOccurs));
	}
	
	public void initialize(Attribute attribute) {
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(true);
		setAttribute(attribute);
	}
	
	public void validate() {
		super.validate(attribute);
	}
	
}
