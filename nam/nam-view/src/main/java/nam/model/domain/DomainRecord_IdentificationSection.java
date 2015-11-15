package nam.model.domain;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Domain;
import nam.model.util.DomainUtil;


@SessionScoped
@Named("domainIdentificationSection")
public class DomainRecord_IdentificationSection extends AbstractWizardPage<Domain> implements Serializable {
	
	private Domain domain;
	
	
	public DomainRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
	}
	
	
	public Domain getDomain() {
		return domain;
	}
	
	public void setDomain(Domain domain) {
		this.domain = domain;
	}
	
	@Override
	public void initialize(Domain domain) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setDomain(domain);
	}
	
	@Override
	public void validate() {
		if (domain == null) {
			validator.missing("Domain");
		} else {
		}
	}
	
}
