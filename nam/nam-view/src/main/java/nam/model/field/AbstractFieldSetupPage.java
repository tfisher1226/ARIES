package nam.model.field;

import java.io.Serializable;

import nam.model.Field;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;


@SuppressWarnings("serial")
public abstract class AbstractFieldSetupPage<T extends Field> extends AbstractWizardPage<T> implements Serializable {

	protected abstract Field getField();
	
	
	public String getMinOccurs() {
		return getField() != null ? Integer.toString(getField().getMinOccurs()) : null;
	}

	public void setMinOccurs(String minOccurs) {
		getField().setMinOccurs(Integer.parseInt(minOccurs));
	}

	public String getMaxOccurs() {
		return getField() != null ? Integer.toString(getField().getMaxOccurs()) : null;
	}

	public void setMaxOccurs(String maxOccurs) {
		getField().setMaxOccurs(Integer.parseInt(maxOccurs));
	}
	
	public void validate(Field field) {
		if (StringUtils.isEmpty(field.getName()))
			validator.missing("Name");
		if (StringUtils.isEmpty(field.getType()))
			validator.missing("Type");
		int minOccurs = field.getMinOccurs();
		int maxOccurs = field.getMaxOccurs();
		if (minOccurs != 0 && minOccurs != 1)
			validator.invalid("MinOccurs", "min-occurs must 0 or 1");
		if (maxOccurs < 0)
			validator.invalid("MaxOccurs", "max-occurs cannot be less than 0");
		if (maxOccurs < minOccurs)
			validator.invalid("MaxOccurs", "max-occurs cannot be less than min-occurs");
	}
	
}
