package nam.model.listener;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Listener;
import nam.model.Project;
import nam.model.util.ListenerUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("listenerWizard")
@SuppressWarnings("serial")
public class ListenerWizard extends AbstractDomainElementWizard<Listener> implements Serializable {
	
	@Inject
	private ListenerDataManager listenerDataManager;
	
	@Inject
	private ListenerPageManager listenerPageManager;
	
	@Inject
	private ListenerEventManager listenerEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Listener";
	}
	
	@Override
	public String getUrlContext() {
		return listenerPageManager.getListenerWizardPage();
	}
	
	@Override
	public void initialize(Listener listener) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(listenerPageManager.getSections());
		super.initialize(listener);
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
		listenerPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		listenerPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		listenerPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		listenerPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Listener listener = getInstance();
		listenerDataManager.saveListener(listener);
		listenerEventManager.fireSavedEvent(listener);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Listener listener = getInstance();
		//TODO take this out soon
		if (listener == null)
			listener = new Listener();
		listenerEventManager.fireCancelledEvent(listener);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Listener listener = selectionContext.getSelection("listener");
		String name = listener.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("listenerWizard");
			display.error("Listener name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
