package nam.model.interactor;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Interactor;
import nam.model.Project;
import nam.model.util.InteractorUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("interactorWizard")
@SuppressWarnings("serial")
public class InteractorWizard extends AbstractDomainElementWizard<Interactor> implements Serializable {
	
	@Inject
	private InteractorDataManager interactorDataManager;
	
	@Inject
	private InteractorPageManager interactorPageManager;
	
	@Inject
	private InteractorEventManager interactorEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Interactor";
	}
	
	@Override
	public String getUrlContext() {
		return interactorPageManager.getInteractorWizardPage();
	}
	
	@Override
	public void initialize(Interactor interactor) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(interactorPageManager.getSections());
		super.initialize(interactor);
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
		interactorPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		interactorPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		interactorPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		interactorPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Interactor interactor = getInstance();
		interactorDataManager.saveInteractor(interactor);
		interactorEventManager.fireSavedEvent(interactor);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Interactor interactor = getInstance();
		//TODO take this out soon
		if (interactor == null)
			interactor = new Interactor();
		interactorEventManager.fireCancelledEvent(interactor);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Interactor interactor = selectionContext.getSelection("interactor");
		String name = interactor.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("interactorWizard");
			display.error("Interactor name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
