package nam.model.fault;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Fault;
import nam.model.Project;
import nam.model.util.FaultUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("faultWizard")
@SuppressWarnings("serial")
public class FaultWizard extends AbstractDomainElementWizard<Fault> implements Serializable {
	
	@Inject
	private FaultDataManager faultDataManager;
	
	@Inject
	private FaultPageManager faultPageManager;
	
	@Inject
	private FaultEventManager faultEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Fault";
	}
	
	@Override
	public String getUrlContext() {
		return faultPageManager.getFaultWizardPage();
	}
	
	@Override
	public void initialize(Fault fault) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(faultPageManager.getSections());
		super.initialize(fault);
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
		faultPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		faultPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		faultPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		faultPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Fault fault = getInstance();
		faultDataManager.saveFault(fault);
		faultEventManager.fireSavedEvent(fault);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Fault fault = getInstance();
		//TODO take this out soon
		if (fault == null)
			fault = new Fault();
		faultEventManager.fireCancelledEvent(fault);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Fault fault = selectionContext.getSelection("fault");
		String name = fault.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("faultWizard");
			display.error("Fault name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
