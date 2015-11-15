package nam.model.attribute;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Attribute;
import nam.model.Project;
import nam.model.util.AttributeUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("attributeWizard")
@SuppressWarnings("serial")
public class AttributeWizard extends AbstractDomainElementWizard<Attribute> implements Serializable {
	
	@Inject
	private AttributeDataManager attributeDataManager;
	
	@Inject
	private AttributePageManager attributePageManager;
	
	@Inject
	private AttributeEventManager attributeEventManager;

	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Attribute";
	}

	@Override
	public String getUrlContext() {
		return attributePageManager.getAttributeWizardPage();
	}

	@Override
	public void initialize(Attribute attribute) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(attributePageManager.getSections());
		super.initialize(attribute);
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
		attributePageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		attributePageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		attributePageManager.updateState();
		return url;
	}

	@Override
	public String next() {
		String url = super.next();
		attributePageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}

	@Override
	public String finish() {
		Attribute attribute = getInstance();
		attributeDataManager.saveAttribute(attribute);
		attributeEventManager.fireSavedEvent(attribute);
		String url = selectionContext.popOrigin();
		return url;
	}

	@Override
	public String cancel() {
		Attribute attribute = getInstance();
		//TODO take this out soon
		if (attribute == null)
			attribute = new Attribute();
		attributeEventManager.fireCancelledEvent(attribute);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Attribute attribute = selectionContext.getSelection("attribute");
		String name = attribute.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("attributeWizard");
			display.error("Attribute name must be specified");
			return null;
		}

		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
