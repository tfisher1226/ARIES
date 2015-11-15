package nam.model.enumeration;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Enumeration;
import nam.model.Project;
import nam.model.util.EnumerationUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("enumerationWizard")
@SuppressWarnings("serial")
public class EnumerationWizard extends AbstractDomainElementWizard<Enumeration> implements Serializable {
	
	@Inject
	private EnumerationDataManager enumerationDataManager;
	
	@Inject
	private EnumerationPageManager enumerationPageManager;
	
	@Inject
	private EnumerationEventManager enumerationEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Enumeration";
	}
	
	@Override
	public String getUrlContext() {
		return enumerationPageManager.getEnumerationWizardPage();
	}
	
	@Override
	public void initialize(Enumeration enumeration) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(enumerationPageManager.getSections());
		super.initialize(enumeration);
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
		enumerationPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		enumerationPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		enumerationPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		enumerationPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Enumeration enumeration = getInstance();
		enumerationDataManager.saveEnumeration(enumeration);
		enumerationEventManager.fireSavedEvent(enumeration);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Enumeration enumeration = getInstance();
		//TODO take this out soon
		if (enumeration == null)
			enumeration = new Enumeration();
		enumerationEventManager.fireCancelledEvent(enumeration);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Enumeration enumeration = selectionContext.getSelection("enumeration");
		String name = enumeration.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("enumerationWizard");
			display.error("Enumeration name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
