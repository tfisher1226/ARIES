package nam.model.property;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Property;
import nam.model.util.PropertyUtil;


@SessionScoped
@Named("propertyOverviewSection")
public class PropertyRecord_OverviewSection extends AbstractWizardPage<Property> implements Serializable {
	
	private Property property;
	
	
	public PropertyRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
	}
	
	
	public Property getProperty() {
		return property;
	}
	
	public void setProperty(Property property) {
		this.property = property;
	}
	
	@Override
	public void initialize(Property property) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setProperty(property);
	}
	
	@Override
	public void validate() {
		if (property == null) {
			validator.missing("Property");
		} else {
		}
	}
	
}
