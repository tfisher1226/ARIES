package nam.model.master;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Master;
import nam.model.Project;
import nam.model.util.MasterUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("masterWizard")
@SuppressWarnings("serial")
public class MasterWizard extends AbstractDomainElementWizard<Master> implements Serializable {
	
	@Inject
	private MasterDataManager masterDataManager;
	
	@Inject
	private MasterPageManager masterPageManager;
	
	@Inject
	private MasterEventManager masterEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Master";
	}
	
	@Override
	public String getUrlContext() {
		return masterPageManager.getMasterWizardPage();
	}
	
	@Override
	public void initialize(Master master) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(masterPageManager.getSections());
		super.initialize(master);
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
		masterPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		masterPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		masterPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		masterPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Master master = getInstance();
		masterDataManager.saveMaster(master);
		masterEventManager.fireSavedEvent(master);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Master master = getInstance();
		//TODO take this out soon
		if (master == null)
			master = new Master();
		masterEventManager.fireCancelledEvent(master);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Master master = selectionContext.getSelection("master");
		String name = master.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("masterWizard");
			display.error("Master name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
