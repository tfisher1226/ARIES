package nam.model.timeout;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Project;
import nam.model.Timeout;
import nam.model.util.TimeoutUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("timeoutWizard")
@SuppressWarnings("serial")
public class TimeoutWizard extends AbstractDomainElementWizard<Timeout> implements Serializable {
	
	@Inject
	private TimeoutDataManager timeoutDataManager;
	
	@Inject
	private TimeoutPageManager timeoutPageManager;
	
	@Inject
	private TimeoutEventManager timeoutEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Timeout";
	}
	
	@Override
	public String getUrlContext() {
		return timeoutPageManager.getTimeoutWizardPage();
	}
	
	@Override
	public void initialize(Timeout timeout) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(timeoutPageManager.getSections());
		super.initialize(timeout);
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
		timeoutPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		timeoutPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		timeoutPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		timeoutPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Timeout timeout = getInstance();
		timeoutDataManager.saveTimeout(timeout);
		timeoutEventManager.fireSavedEvent(timeout);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Timeout timeout = getInstance();
		//TODO take this out soon
		if (timeout == null)
			timeout = new Timeout();
		timeoutEventManager.fireCancelledEvent(timeout);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Timeout timeout = selectionContext.getSelection("timeout");
		String name = timeout.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("timeoutWizard");
			display.error("Timeout name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
