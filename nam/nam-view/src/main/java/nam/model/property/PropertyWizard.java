package nam.model.property;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Project;
import nam.model.Property;
import nam.model.util.PropertyUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("propertyWizard")
@SuppressWarnings("serial")
public class PropertyWizard extends AbstractDomainElementWizard<Property> implements Serializable {
	
	@Inject
	private PropertyDataManager propertyDataManager;
	
	@Inject
	private PropertyPageManager propertyPageManager;
	
	@Inject
	private PropertyEventManager propertyEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Property";
	}
	
	@Override
	public String getUrlContext() {
		return propertyPageManager.getPropertyWizardPage();
	}
	
	@Override
	public void initialize(Property property) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(propertyPageManager.getSections());
		super.initialize(property);
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
		propertyPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		propertyPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		propertyPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		propertyPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Property property = getInstance();
		propertyDataManager.saveProperty(property);
		propertyEventManager.fireSavedEvent(property);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Property property = getInstance();
		//TODO take this out soon
		if (property == null)
			property = new Property();
		propertyEventManager.fireCancelledEvent(property);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Property property = selectionContext.getSelection("property");
		String name = property.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("propertyWizard");
			display.error("Property name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
