package nam.model.listener;

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

import nam.model.Listener;
import nam.model.Project;
import nam.model.util.ListenerUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("listenerInfoManager")
public class ListenerInfoManager extends AbstractNamRecordManager<Listener> implements Serializable {
	
	@Inject
	private ListenerWizard listenerWizard;
	
	@Inject
	private ListenerDataManager listenerDataManager;
	
	@Inject
	private ListenerPageManager listenerPageManager;
	
	@Inject
	private ListenerEventManager listenerEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private ListenerHelper listenerHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ListenerInfoManager() {
		setInstanceName("listener");
	}
	
	
	public Listener getListener() {
		return getRecord();
	}
	
	public Listener getSelectedListener() {
		return selectionContext.getSelection("listener");
	}
	
	@Override
	public Class<Listener> getRecordClass() {
		return Listener.class;
	}
	
	@Override
	public boolean isEmpty(Listener listener) {
		return listenerHelper.isEmpty(listener);
	}
	
	@Override
	public String toString(Listener listener) {
		return listenerHelper.toString(listener);
	}
	
	@Override
	public void initialize() {
		Listener listener = selectionContext.getSelection("listener");
		if (listener != null)
			initialize(listener);
	}
	
	protected void initialize(Listener listener) {
		ListenerUtil.initialize(listener);
		listenerWizard.initialize(listener);
		setContext("listener", listener);
	}
	
	public void handleListenerSelected(@Observes @Selected Listener listener) {
		selectionContext.setSelection("listener",  listener);
		listenerPageManager.updateState(listener);
		listenerPageManager.refreshMembers();
		setRecord(listener);
	}
	
	@Override
	public String newRecord() {
		return newListener();
	}
	
	public String newListener() {
		try {
			Listener listener = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("listener",  listener);
			String url = listenerPageManager.initializeListenerCreationPage(listener);
			listenerPageManager.pushContext(listenerWizard);
			initialize(listener);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Listener create() {
		Listener listener = ListenerUtil.create();
		return listener;
	}
	
	@Override
	public Listener clone(Listener listener) {
		listener = ListenerUtil.clone(listener);
		return listener;
	}
	
	@Override
	public String viewRecord() {
		return viewListener();
	}
	
	public String viewListener() {
		Listener listener = selectionContext.getSelection("listener");
		String url = viewListener(listener);
		return url;
	}
	
	public String viewListener(Listener listener) {
		try {
			String url = listenerPageManager.initializeListenerSummaryView(listener);
			listenerPageManager.pushContext(listenerWizard);
			initialize(listener);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editListener();
	}
	
	public String editListener() {
		Listener listener = selectionContext.getSelection("listener");
		String url = editListener(listener);
		return url;
	}
	
	public String editListener(Listener listener) {
		try {
			//listener = clone(listener);
			selectionContext.resetOrigin();
			selectionContext.setSelection("listener",  listener);
			String url = listenerPageManager.initializeListenerUpdatePage(listener);
			listenerPageManager.pushContext(listenerWizard);
			initialize(listener);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveListener() {
		Listener listener = getListener();
		if (validateListener(listener)) {
			if (isImmediate())
				persistListener(listener);
			outject("listener", listener);
		}
	}
	
	public void persistListener(Listener listener) {
		saveListener(listener);
	}
	
	public void saveListener(Listener listener) {
		try {
			saveListenerToSystem(listener);
			listenerEventManager.fireAddedEvent(listener);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveListenerToSystem(Listener listener) {
		listenerDataManager.saveListener(listener);
	}
	
	public void handleSaveListener(@Observes @Add Listener listener) {
		saveListener(listener);
	}
	
	public void addListener(Listener listener) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichListener(Listener listener) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Listener listener) {
		return validateListener(listener);
	}
	
	public boolean validateListener(Listener listener) {
		Validator validator = getValidator();
		boolean isValid = ListenerUtil.validate(listener);
		Display display = getFromSession("display");
		display.setModule("listenerInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveListener() {
		display = getFromSession("display");
		display.setModule("listenerInfo");
		Listener listener = selectionContext.getSelection("listener");
		if (listener == null) {
			display.error("Listener record must be selected.");
		}
	}
	
	public String handleRemoveListener(@Observes @Remove Listener listener) {
		display = getFromSession("display");
		display.setModule("listenerInfo");
		try {
			display.info("Removing Listener "+ListenerUtil.getLabel(listener)+" from the system.");
			removeListenerFromSystem(listener);
			selectionContext.clearSelection("listener");
			listenerEventManager.fireClearSelectionEvent();
			listenerEventManager.fireRemovedEvent(listener);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeListenerFromSystem(Listener listener) {
		if (listenerDataManager.removeListener(listener))
			setRecord(null);
	}
	
	public void cancelListener() {
		BeanContext.removeFromSession("listener");
		listenerPageManager.removeContext(listenerWizard);
	}
	
}
