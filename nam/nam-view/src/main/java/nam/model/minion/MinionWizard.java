package nam.model.minion;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Minion;
import nam.model.Project;
import nam.model.util.MinionUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("minionWizard")
@SuppressWarnings("serial")
public class MinionWizard extends AbstractDomainElementWizard<Minion> implements Serializable {
	
	@Inject
	private MinionDataManager minionDataManager;
	
	@Inject
	private MinionPageManager minionPageManager;
	
	@Inject
	private MinionEventManager minionEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Minion";
	}
	
	@Override
	public String getUrlContext() {
		return minionPageManager.getMinionWizardPage();
	}
	
	@Override
	public void initialize(Minion minion) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(minionPageManager.getSections());
		super.initialize(minion);
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
		minionPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		minionPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		minionPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		minionPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Minion minion = getInstance();
		minionDataManager.saveMinion(minion);
		minionEventManager.fireSavedEvent(minion);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Minion minion = getInstance();
		//TODO take this out soon
		if (minion == null)
			minion = new Minion();
		minionEventManager.fireCancelledEvent(minion);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Minion minion = selectionContext.getSelection("minion");
		String name = minion.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("minionWizard");
			display.error("Minion name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
