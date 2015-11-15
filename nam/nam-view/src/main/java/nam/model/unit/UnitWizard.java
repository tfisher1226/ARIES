package nam.model.unit;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Project;
import nam.model.Unit;
import nam.model.util.UnitUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("unitWizard")
@SuppressWarnings("serial")
public class UnitWizard extends AbstractDomainElementWizard<Unit> implements Serializable {
	
	@Inject
	private UnitDataManager unitDataManager;
	
	@Inject
	private UnitPageManager unitPageManager;
	
	@Inject
	private UnitEventManager unitEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Unit";
	}
	
	@Override
	public String getUrlContext() {
		return unitPageManager.getUnitWizardPage();
	}
	
	@Override
	public void initialize(Unit unit) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(unitPageManager.getSections());
		super.initialize(unit);
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
		unitPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		unitPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		unitPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		unitPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Unit unit = getInstance();
		unitDataManager.saveUnit(unit);
		unitEventManager.fireSavedEvent(unit);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Unit unit = getInstance();
		//TODO take this out soon
		if (unit == null)
			unit = new Unit();
		unitEventManager.fireCancelledEvent(unit);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Unit unit = selectionContext.getSelection("unit");
		String name = unit.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("unitWizard");
			display.error("Unit name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
