package nam.model.element;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.util.Validator;

import nam.model.Element;
import nam.model.Project;
import nam.model.util.ElementUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("elementInfoManager")
public class ElementInfoManager extends AbstractNamRecordManager<Element> implements Serializable {
	
	@Inject
	private ElementWizard elementWizard;
	
	@Inject
	private ElementDataManager elementDataManager;
	
	@Inject
	private ElementPageManager elementPageManager;
	
	@Inject
	private ElementEventManager elementEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private ElementHelper elementHelper;
	
	@Inject
	private SelectionContext selectionContext;

	
	public ElementInfoManager() {
		setInstanceName("element");
	}
	
	
	public Element getElement() {
		return getRecord();
	}

	public Element getSelectedElement() {
		return selectionContext.getSelection("element");
	}
	
	@Override
	public Class<Element> getRecordClass() {
		return Element.class;
	}
	
	@Override
	public boolean isEmpty(Element element) {
		return elementHelper.isEmpty(element);
	}
	
	@Override
	public String toString(Element element) {
		return elementHelper.toString(element);
	}
	
	@Override
	public void initialize() {
		Element element = selectionContext.getSelection("element");
		if (element != null)
			initialize(element);
	}
	
	protected void initialize(Element element) {
		elementWizard.initialize(element);
		setContext("element", element);
	}
	
	@Override
	public String newRecord() {
		return newElement();
	}
	
	public String newElement() {
		try {
			Element element = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("element",  element);
			String url = elementPageManager.initializeElementCreationPage(element);
			elementPageManager.pushContext(elementWizard);
			initialize(element);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}

	@Override
	public Element create() {
		Element element = ElementUtil.create();
		return element;
	}
	
	@Override
	public Element clone(Element element) {
		element = ElementUtil.clone(element);
		return element;
	}
	
	@Override
	public String viewRecord() {
		return viewElement();
	}
	
	public String viewElement() {
		Element element = selectionContext.getSelection("element");
		String url = viewElement(element);
		return url;
	}
	
	public String viewElement(Element element) {
		try {
			String url = elementPageManager.initializeElementSummaryView(element);
			elementPageManager.pushContext(elementWizard);
			initialize(element);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editElement();
	}
	
	public String editElement() {
		Element element = selectionContext.getSelection("element");
		String url = editElement(element);
		return url;
	}
			
	public String editElement(Element element) {
		try {
			//element = clone(element);
			selectionContext.resetOrigin();
			selectionContext.setSelection("element",  element);
			String url = elementPageManager.initializeElementUpdatePage(element);
			elementPageManager.pushContext(elementWizard);
			initialize(element);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveElement() {
		Element element = getElement();
		if (validateElement(element)) {
			saveElement(element);
		}
	}
	
	public void persistElement(Element element) {
		saveElement(element);
	}
	
	public void saveElement(Element element) {
		try {
			saveElementToSystem(element);
			elementEventManager.fireAddedEvent(element);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveElementToSystem(Element element) {
		elementDataManager.saveElement(element);
	}
	
	public void handleSaveElement(@Observes @Add Element element) {
		saveElement(element);
	}
	
	public void enrichElement(Element element) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Element element) {
		return validateElement(element);
	}
	
	public boolean validateElement(Element element) {
		Validator validator = getValidator();
		boolean isValid = ElementUtil.validate(element);
		Display display = getFromSession("display");
		display.setModule("elementInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveElement() {
		display = getFromSession("display");
		display.setModule("elementInfo");
		Element element = selectionContext.getSelection("element");
		if (element == null) {
			display.error("Element record must be selected.");
		}
	}
	
	public String handleRemoveElement(@Observes @Remove Element element) {
		display = getFromSession("display");
			display.setModule("elementInfo");
		try {
			display.info("Removing Element "+ElementUtil.getLabel(element)+" from the system.");
			removeElementFromSystem(element);
			selectionContext.clearSelection("element");
			elementEventManager.fireClearSelectionEvent();
			elementEventManager.fireRemovedEvent(element);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeElementFromSystem(Element element) {
		if (elementDataManager.removeElement(element))
			setRecord(null);
	}

	public void cancelElement() {
		BeanContext.removeFromSession("element");
		elementPageManager.removeContext(elementWizard);
	}
	
}
