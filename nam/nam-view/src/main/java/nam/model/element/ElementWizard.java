package nam.model.element;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;
import org.aries.util.NameUtil;

import nam.model.Application;
import nam.model.Element;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.util.ElementUtil;
import nam.model.util.TypeUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("elementWizard")
@SuppressWarnings("serial")
public class ElementWizard extends AbstractDomainElementWizard<Element> implements Serializable {

	@Inject
	private ElementDataManager elementDataManager;
	
	@Inject
	private ElementPageManager elementPageManager;

	@Inject
	private ElementEventManager elementEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	

	@Override
	public String getName() {
		return "Element";
	}
	
	@Override
	public String getUrlContext() {
		return elementPageManager.getElementWizardPage();
	}
	
	@Override
	public void initialize(Element element) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(elementPageManager.getSections());
		super.initialize(element);
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
		elementPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		elementPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		elementPageManager.updateState();
		return url;
	}

	@Override
	public String next() {
		String url = super.next();
		elementPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}

	@Override
	public String finish() {
		Element element = getInstance();
		elementDataManager.saveElement(element);
		elementEventManager.fireSavedEvent(element);
		String url = selectionContext.popOrigin();
		return url;
	}

	@Override
	public String cancel() {
		Element element = getInstance();
		//TODO take this out soon
		if (element == null)
			element = new Element();
		elementEventManager.fireCancelledEvent(element);
		String url = selectionContext.popOrigin();
		return url;
	}

	public String populateDefaultValues() {
		Element element = selectionContext.getSelection("element");
		String name = element.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("elementWizard");
			display.error("Element name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String uri = element.getNamespace();
		Namespace namespace = getContext().getNamespaceByUri(uri);
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);

		element.setPublic(true);
		element.setRoot(true);
		element.setStructure("item");
		element.setType(TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, name));
		element.setDescription("This is an Element named \""+name+"\"");

		return getUrl();
	}
	
}
