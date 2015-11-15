package nam.model.transport;

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

import nam.model.Project;
import nam.model.Transport;
import nam.model.util.ProjectUtil;
import nam.model.util.TransportUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("transportInfoManager")
public class TransportInfoManager extends AbstractNamRecordManager<Transport> implements Serializable {
	
	@Inject
	private TransportWizard transportWizard;
	
	@Inject
	private TransportDataManager transportDataManager;
	
	@Inject
	private TransportPageManager transportPageManager;
	
	@Inject
	private TransportEventManager transportEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private TransportHelper transportHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public TransportInfoManager() {
		setInstanceName("transport");
	}
	
	
	public Transport getTransport() {
		return getRecord();
	}
	
	public Transport getSelectedTransport() {
		return selectionContext.getSelection("transport");
	}
	
	@Override
	public Class<Transport> getRecordClass() {
		return Transport.class;
	}
	
	@Override
	public boolean isEmpty(Transport transport) {
		return transportHelper.isEmpty(transport);
	}
	
	@Override
	public String toString(Transport transport) {
		return transportHelper.toString(transport);
	}
	
	@Override
	public void initialize() {
		Transport transport = selectionContext.getSelection("transport");
		if (transport != null)
			initialize(transport);
	}
	
	protected void initialize(Transport transport) {
		TransportUtil.initialize(transport);
		transportWizard.initialize(transport);
		setContext("transport", transport);
	}
	
	public void handleTransportSelected(@Observes @Selected Transport transport) {
		selectionContext.setSelection("transport",  transport);
		transportPageManager.updateState(transport);
		transportPageManager.refreshMembers();
		setRecord(transport);
	}
	
	@Override
	public String newRecord() {
		return newTransport();
	}
	
	public String newTransport() {
		try {
			Transport transport = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("transport",  transport);
			String url = transportPageManager.initializeTransportCreationPage(transport);
			transportPageManager.pushContext(transportWizard);
			initialize(transport);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Transport create() {
		Transport transport = TransportUtil.create();
		return transport;
	}
	
	@Override
	public Transport clone(Transport transport) {
		transport = TransportUtil.clone(transport);
		return transport;
	}
	
	@Override
	public String viewRecord() {
		return viewTransport();
	}
	
	public String viewTransport() {
		Transport transport = selectionContext.getSelection("transport");
		String url = viewTransport(transport);
		return url;
	}
	
	public String viewTransport(Transport transport) {
		try {
			String url = transportPageManager.initializeTransportSummaryView(transport);
			transportPageManager.pushContext(transportWizard);
			initialize(transport);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editTransport();
	}
	
	public String editTransport() {
		Transport transport = selectionContext.getSelection("transport");
		String url = editTransport(transport);
		return url;
	}
	
	public String editTransport(Transport transport) {
		try {
			//transport = clone(transport);
			selectionContext.resetOrigin();
			selectionContext.setSelection("transport",  transport);
			String url = transportPageManager.initializeTransportUpdatePage(transport);
			transportPageManager.pushContext(transportWizard);
			initialize(transport);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveTransport() {
		Transport transport = getTransport();
		if (validateTransport(transport)) {
			if (isImmediate())
				persistTransport(transport);
			outject("transport", transport);
		}
	}
	
	public void persistTransport(Transport transport) {
		saveTransport(transport);
	}
	
	public void saveTransport(Transport transport) {
		try {
			saveTransportToSystem(transport);
			transportEventManager.fireAddedEvent(transport);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveTransportToSystem(Transport transport) {
		transportDataManager.saveTransport(transport);
	}
	
	public void handleSaveTransport(@Observes @Add Transport transport) {
		saveTransport(transport);
	}
	
	public void addTransport(Transport transport) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichTransport(Transport transport) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Transport transport) {
		return validateTransport(transport);
	}
	
	public boolean validateTransport(Transport transport) {
		Validator validator = getValidator();
		boolean isValid = TransportUtil.validate(transport);
		Display display = getFromSession("display");
		display.setModule("transportInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveTransport() {
		display = getFromSession("display");
		display.setModule("transportInfo");
		Transport transport = selectionContext.getSelection("transport");
		if (transport == null) {
			display.error("Transport record must be selected.");
		}
	}
	
	public String handleRemoveTransport(@Observes @Remove Transport transport) {
		display = getFromSession("display");
		display.setModule("transportInfo");
		try {
			display.info("Removing Transport "+TransportUtil.getLabel(transport)+" from the system.");
			removeTransportFromSystem(transport);
			selectionContext.clearSelection("transport");
			transportEventManager.fireClearSelectionEvent();
			transportEventManager.fireRemovedEvent(transport);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeTransportFromSystem(Transport transport) {
		if (transportDataManager.removeTransport(transport))
			setRecord(null);
	}
	
	public void cancelTransport() {
		BeanContext.removeFromSession("transport");
		transportPageManager.removeContext(transportWizard);
	}
	
}
