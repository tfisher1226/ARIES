package nam.model.attribute;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Attribute;
import nam.model.util.AttributeUtil;


@SessionScoped
@Named("attributeOverviewSection")
public class AttributeRecord_OverviewSection extends AbstractWizardPage<Attribute> implements Serializable {
	
	private Attribute attribute;
	
	
	public AttributeRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
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
		setAttribute(attribute);
	}
	
	@Override
	public void validate() {
		if (attribute == null) {
			validator.missing("Attribute");
		} else {
		}
	}
	
}
