package nam.model.enumeration;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Enumeration;
import nam.model.util.EnumerationUtil;


@SessionScoped
@Named("enumerationOverviewSection")
public class EnumerationRecord_OverviewSection extends AbstractWizardPage<Enumeration> implements Serializable {
	
	private Enumeration enumeration;
	
	
	public EnumerationRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
	}
	
	
	public Enumeration getEnumeration() {
		return enumeration;
	}
	
	public void setEnumeration(Enumeration enumeration) {
		this.enumeration = enumeration;
	}
	
	@Override
	public void initialize(Enumeration enumeration) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setEnumeration(enumeration);
	}
	
	@Override
	public void validate() {
		if (enumeration == null) {
			validator.missing("Enumeration");
		} else {
		}
	}
	
}
