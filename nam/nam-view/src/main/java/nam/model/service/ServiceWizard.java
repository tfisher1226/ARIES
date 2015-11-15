package nam.model.service;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Project;
import nam.model.Service;
import nam.model.util.ServiceUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("serviceWizard")
@SuppressWarnings("serial")
public class ServiceWizard extends AbstractDomainElementWizard<Service> implements Serializable {
	
	@Inject
	private ServiceDataManager serviceDataManager;
	
	@Inject
	private ServicePageManager servicePageManager;
	
	@Inject
	private ServiceEventManager serviceEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Service";
	}
	
	@Override
	public String getUrlContext() {
		return servicePageManager.getServiceWizardPage();
	}
	
	@Override
	public void initialize(Service service) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(servicePageManager.getSections());
		super.initialize(service);
	}
	
	@Override
	public boolean isBackEnabled() {
		return super.isBackEnabled();
	}
	
	@Override
	public boolean isNextEnabled() {
		return super.isNextEnabled();
	}
	
	@Override
	public boolean isFinishEnabled() {
		return super.isFinishEnabled();
	}
	
	@Override
	public String refresh() {
		String url = super.showPage();
		selectionContext.setUrl(url);
		servicePageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		servicePageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		servicePageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		servicePageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Service service = getInstance();
		serviceDataManager.saveService(service);
		serviceEventManager.fireSavedEvent(service);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Service service = getInstance();
		//TODO take this out soon
		if (service == null)
			service = new Service();
		serviceEventManager.fireCancelledEvent(service);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Service service = selectionContext.getSelection("service");
		String name = service.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("serviceWizard");
			display.error("Service name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
