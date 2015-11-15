package nam.model.container;

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

import nam.model.Container;
import nam.model.Project;
import nam.model.util.ContainerUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("containerInfoManager")
public class ContainerInfoManager extends AbstractNamRecordManager<Container> implements Serializable {
	
	@Inject
	private ContainerWizard containerWizard;
	
	@Inject
	private ContainerDataManager containerDataManager;
	
	@Inject
	private ContainerPageManager containerPageManager;
	
	@Inject
	private ContainerEventManager containerEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private ContainerHelper containerHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ContainerInfoManager() {
		setInstanceName("container");
	}
	
	
	public Container getContainer() {
		return getRecord();
	}
	
	public Container getSelectedContainer() {
		return selectionContext.getSelection("container");
	}
	
	@Override
	public Class<Container> getRecordClass() {
		return Container.class;
	}
	
	@Override
	public boolean isEmpty(Container container) {
		return containerHelper.isEmpty(container);
	}
	
	@Override
	public String toString(Container container) {
		return containerHelper.toString(container);
	}
	
	@Override
	public void initialize() {
		Container container = selectionContext.getSelection("container");
		if (container != null)
			initialize(container);
	}
	
	protected void initialize(Container container) {
		ContainerUtil.initialize(container);
		containerWizard.initialize(container);
		setContext("container", container);
	}
	
	public void handleContainerSelected(@Observes @Selected Container container) {
		selectionContext.setSelection("container",  container);
		containerPageManager.updateState(container);
		containerPageManager.refreshMembers();
		setRecord(container);
	}
	
	@Override
	public String newRecord() {
		return newContainer();
	}
	
	public String newContainer() {
		try {
			Container container = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("container",  container);
			String url = containerPageManager.initializeContainerCreationPage(container);
			containerPageManager.pushContext(containerWizard);
			initialize(container);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Container create() {
		Container container = ContainerUtil.create();
		return container;
	}
	
	@Override
	public Container clone(Container container) {
		container = ContainerUtil.clone(container);
		return container;
	}
	
	@Override
	public String viewRecord() {
		return viewContainer();
	}
	
	public String viewContainer() {
		Container container = selectionContext.getSelection("container");
		String url = viewContainer(container);
		return url;
	}
	
	public String viewContainer(Container container) {
		try {
			String url = containerPageManager.initializeContainerSummaryView(container);
			containerPageManager.pushContext(containerWizard);
			initialize(container);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editContainer();
	}
	
	public String editContainer() {
		Container container = selectionContext.getSelection("container");
		String url = editContainer(container);
		return url;
	}
	
	public String editContainer(Container container) {
		try {
			//container = clone(container);
			selectionContext.resetOrigin();
			selectionContext.setSelection("container",  container);
			String url = containerPageManager.initializeContainerUpdatePage(container);
			containerPageManager.pushContext(containerWizard);
			initialize(container);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveContainer() {
		Container container = getContainer();
		if (validateContainer(container)) {
			if (isImmediate())
				persistContainer(container);
			outject("container", container);
		}
	}
	
	public void persistContainer(Container container) {
		saveContainer(container);
	}
	
	public void saveContainer(Container container) {
		try {
			saveContainerToSystem(container);
			containerEventManager.fireAddedEvent(container);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveContainerToSystem(Container container) {
		containerDataManager.saveContainer(container);
	}
	
	public void handleSaveContainer(@Observes @Add Container container) {
		saveContainer(container);
	}
	
	public void addContainer(Container container) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichContainer(Container container) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Container container) {
		return validateContainer(container);
	}
	
	public boolean validateContainer(Container container) {
		Validator validator = getValidator();
		boolean isValid = ContainerUtil.validate(container);
		Display display = getFromSession("display");
		display.setModule("containerInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveContainer() {
		display = getFromSession("display");
		display.setModule("containerInfo");
		Container container = selectionContext.getSelection("container");
		if (container == null) {
			display.error("Container record must be selected.");
		}
	}
	
	public String handleRemoveContainer(@Observes @Remove Container container) {
		display = getFromSession("display");
		display.setModule("containerInfo");
		try {
			display.info("Removing Container "+ContainerUtil.getLabel(container)+" from the system.");
			removeContainerFromSystem(container);
			selectionContext.clearSelection("container");
			containerEventManager.fireClearSelectionEvent();
			containerEventManager.fireRemovedEvent(container);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeContainerFromSystem(Container container) {
		if (containerDataManager.removeContainer(container))
			setRecord(null);
	}
	
	public void cancelContainer() {
		BeanContext.removeFromSession("container");
		containerPageManager.removeContext(containerWizard);
	}
	
}
