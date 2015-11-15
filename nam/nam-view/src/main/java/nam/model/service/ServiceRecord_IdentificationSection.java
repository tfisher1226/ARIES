package nam.model.service;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Service;
import nam.model.util.ServiceUtil;


@SessionScoped
@Named("serviceIdentificationSection")
public class ServiceRecord_IdentificationSection extends AbstractWizardPage<Service> implements Serializable {

	private Service service;


	public ServiceRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
	}

	
	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}
	
	@Override
	public void initialize(Service service) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setService(service);
	}
	
	@Override
	public void validate() {
		if (service == null) {
			validator.missing("Service");
		} else {
			if (StringUtils.isEmpty(service.getName()))
				validator.missing("Name");
			if (StringUtils.isEmpty(service.getElement()))
				validator.missing("Element");
		}
	}
	
}
