package nam.model.enumeration;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Enumeration;
import nam.model.util.EnumerationUtil;


@SessionScoped
@Named("enumerationIdentificationSection")
public class EnumerationRecord_IdentificationSection extends AbstractWizardPage<Enumeration> implements Serializable {
	
	private Enumeration enumeration;
	
	
	public EnumerationRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setPopulateVisible(true);
		setPopulateEnabled(true);
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
