package nam.model.component;

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
import org.aries.ui.event.Selected;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import nam.model.Component;
import nam.model.Project;
import nam.model.util.ComponentUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("componentInfoManager")
public class ComponentInfoManager extends AbstractNamRecordManager<Component> implements Serializable {
	
	@Inject
	private ComponentWizard componentWizard;
	
	@Inject
	private ComponentDataManager componentDataManager;
	
	@Inject
	private ComponentPageManager componentPageManager;
	
	@Inject
	private ComponentEventManager componentEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private ComponentHelper componentHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ComponentInfoManager() {
		setInstanceName("component");
	}
	
	
	public Component getComponent() {
		return getRecord();
	}
	
	public Component getSelectedComponent() {
		return selectionContext.getSelection("component");
	}
	
	@Override
	public Class<Component> getRecordClass() {
		return Component.class;
	}
	
	@Override
	public boolean isEmpty(Component component) {
		return componentHelper.isEmpty(component);
	}
	
	@Override
	public String toString(Component component) {
		return componentHelper.toString(component);
	}
	
	@Override
	public void initialize() {
		Component component = selectionContext.getSelection("component");
		if (component != null)
			initialize(component);
	}
	
	protected void initialize(Component component) {
		componentWizard.initialize(component);
		setContext("component", component);
	}
	
	@Override
	public String newRecord() {
		return newComponent();
	}
	
	public String newComponent() {
		try {
			Component component = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("component", component);
			String url = componentPageManager.initializeComponentCreationPage(component);
			componentPageManager.pushContext(componentWizard);
			initialize(component);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Component create() {
		Component component = ComponentUtil.create();
		return component;
	}
	
	@Override
	public Component clone(Component component) {
		component = ComponentUtil.clone(component);
		return component;
	}
	
	@Override
	public String viewRecord() {
		return viewComponent();
	}
	
	public String viewComponent() {
		Component component = selectionContext.getSelection("component");
		String url = viewComponent(component);
		return url;
	}
	
	public String viewComponent(Component component) {
		try {
			String url = componentPageManager.initializeComponentSummaryView(component);
			componentPageManager.pushContext(componentWizard);
			initialize(component);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editComponent();
	}
	
	public String editComponent() {
		Component component = selectionContext.getSelection("component");
		String url = editComponent(component);
		return url;
	}
	
	public String editComponent(Component component) {
		try {
			//component = clone(component);
			selectionContext.resetOrigin();
			selectionContext.setSelection("component",  component);
			String url = componentPageManager.initializeComponentUpdatePage(component);
			componentPageManager.pushContext(componentWizard);
			initialize(component);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveComponent() {
		Component component = getComponent();
		if (validateComponent(component)) {
			if (isImmediate())
				persistComponent(component);
			outject("component", component);
		}
	}
	
	public void persistComponent(Component component) {
		saveComponent(component);
	}
	
	public void saveComponent(Component component) {
		try {
			saveComponentToSystem(component);
			componentEventManager.fireAddedEvent(component);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveComponentToSystem(Component component) {
		componentDataManager.saveComponent(component);
	}
	
	public void handleSaveComponent(@Observes @Add Component component) {
		saveComponent(component);
	}
	
	public void addComponent(Component component) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichComponent(Component component) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Component component) {
		return validateComponent(component);
	}
	
	public boolean validateComponent(Component component) {
		Validator validator = getValidator();
		boolean isValid = ComponentUtil.validate(component);
		Display display = getFromSession("display");
		display.setModule("componentInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveComponent() {
		display = getFromSession("display");
		display.setModule("componentInfo");
		Component component = selectionContext.getSelection("component");
		if (component == null) {
			display.error("Component record must be selected.");
		}
	}
	
	public String handleRemoveComponent(@Observes @Remove Component component) {
		display = getFromSession("display");
		display.setModule("componentInfo");
		try {
			display.info("Removing Component "+ComponentUtil.getLabel(component)+" from the system.");
			removeComponentFromSystem(component);
			selectionContext.clearSelection("component");
			componentEventManager.fireClearSelectionEvent();
			componentEventManager.fireRemovedEvent(component);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeComponentFromSystem(Component component) {
		if (componentDataManager.removeComponent(component))
			setRecord(null);
	}
	
	public void cancelComponent() {
		BeanContext.removeFromSession("component");
		componentPageManager.removeContext(componentWizard);
	}
	
}
