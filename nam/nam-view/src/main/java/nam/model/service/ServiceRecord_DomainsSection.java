package nam.model.service;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Service;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;


@SessionScoped
@Named("serviceDomainsSection")
public class ServiceRecord_DomainsSection extends AbstractWizardPage<Service> {

	private Service service;


	public ServiceRecord_DomainsSection() {
		//setTitle("Specify Service information.");
		setName("Domains");
		setUrl("domains");
		//setOwner(owner);
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}
	
	public void initialize(Service service) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setService(service);
	}
	
}
