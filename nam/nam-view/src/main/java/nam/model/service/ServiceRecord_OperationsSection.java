package nam.model.service;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Service;
import nam.model.util.ServiceUtil;


@SessionScoped
@Named("serviceOperationsSection")
public class ServiceRecord_OperationsSection extends AbstractWizardPage<Service> implements Serializable {

	private Service service;


	public ServiceRecord_OperationsSection() {
		setName("Operations");
		setUrl("operations");
	}

	
	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}
	
	@Override
	public String getIcon() {
		return "/icons/nam/Service16.gif";
	}
	
	@Override
	public void initialize(Service service) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setService(service);
	}
	
	@Override
	public void validate() {
		if (service == null) {
			validator.missing("Service");
		} else {
		}
	}
	
}
