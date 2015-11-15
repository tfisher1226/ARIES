package nam.model.persistence;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Persistence;
import nam.model.Project;
import nam.model.util.PersistenceUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("persistenceWizard")
@SuppressWarnings("serial")
public class PersistenceWizard extends AbstractDomainElementWizard<Persistence> implements Serializable {
	
	@Inject
	private PersistenceDataManager persistenceDataManager;
	
	@Inject
	private PersistencePageManager persistencePageManager;
	
	@Inject
	private PersistenceEventManager persistenceEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Persistence";
	}
	
	@Override
	public String getUrlContext() {
		return persistencePageManager.getPersistenceWizardPage();
	}
	
	@Override
	public void initialize(Persistence persistence) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(persistencePageManager.getSections());
		super.initialize(persistence);
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
		persistencePageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		persistencePageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		persistencePageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		persistencePageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Persistence persistence = getInstance();
		persistenceDataManager.savePersistence(persistence);
		persistenceEventManager.fireSavedEvent(persistence);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Persistence persistence = getInstance();
		//TODO take this out soon
		if (persistence == null)
			persistence = new Persistence();
		persistenceEventManager.fireCancelledEvent(persistence);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Persistence persistence = selectionContext.getSelection("persistence");
		String name = persistence.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("persistenceWizard");
			display.error("Persistence name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
