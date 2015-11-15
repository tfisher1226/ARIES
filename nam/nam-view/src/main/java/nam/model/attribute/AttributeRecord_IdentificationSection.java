package nam.model.attribute;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Attribute;
import nam.model.util.AttributeUtil;


@SessionScoped
@Named("attributeIdentificationSection")
public class AttributeRecord_IdentificationSection extends AbstractWizardPage<Attribute> implements Serializable {

	private Attribute attribute;

	
	public AttributeRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
	}

	
	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	@Override
	public void initialize(Attribute attribute) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setAttribute(attribute);
	}
	
	@Override
	public void validate() {
		if (attribute == null) {
			validator.missing("Attribute");
		} else {
			if (StringUtils.isEmpty(attribute.getName()))
				validator.missing("Name");
			if (StringUtils.isEmpty(attribute.getLabel()))
				validator.missing("Label");
			if (StringUtils.isEmpty(attribute.getStructure()))
				validator.missing("Structure");
			if (StringUtils.isEmpty(attribute.getNamespace()))
				validator.missing("Namespace");
		}
	}

}
