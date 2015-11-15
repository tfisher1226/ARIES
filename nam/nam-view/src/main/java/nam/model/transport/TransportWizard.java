package nam.model.transport;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Project;
import nam.model.Transport;
import nam.model.util.TransportUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("transportWizard")
@SuppressWarnings("serial")
public class TransportWizard extends AbstractDomainElementWizard<Transport> implements Serializable {
	
	@Inject
	private TransportDataManager transportDataManager;
	
	@Inject
	private TransportPageManager transportPageManager;
	
	@Inject
	private TransportEventManager transportEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Transport";
	}
	
	@Override
	public String getUrlContext() {
		return transportPageManager.getTransportWizardPage();
	}
	
	@Override
	public void initialize(Transport transport) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(transportPageManager.getSections());
		super.initialize(transport);
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
		transportPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		transportPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		transportPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		transportPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Transport transport = getInstance();
		transportDataManager.saveTransport(transport);
		transportEventManager.fireSavedEvent(transport);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Transport transport = getInstance();
		//TODO take this out soon
		if (transport == null)
			transport = new Transport();
		transportEventManager.fireCancelledEvent(transport);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Transport transport = selectionContext.getSelection("transport");
		String name = transport.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("transportWizard");
			display.error("Transport name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
