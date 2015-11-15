package nam.model.network;

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

import nam.model.Network;
import nam.model.Project;
import nam.model.util.NetworkUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("networkInfoManager")
public class NetworkInfoManager extends AbstractNamRecordManager<Network> implements Serializable {
	
	@Inject
	private NetworkWizard networkWizard;
	
	@Inject
	private NetworkDataManager networkDataManager;
	
	@Inject
	private NetworkPageManager networkPageManager;
	
	@Inject
	private NetworkEventManager networkEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private NetworkHelper networkHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public NetworkInfoManager() {
		setInstanceName("network");
	}
	
	
	public Network getNetwork() {
		return getRecord();
	}
	
	public Network getSelectedNetwork() {
		return selectionContext.getSelection("network");
	}
	
	@Override
	public Class<Network> getRecordClass() {
		return Network.class;
	}
	
	@Override
	public boolean isEmpty(Network network) {
		return networkHelper.isEmpty(network);
	}
	
	@Override
	public String toString(Network network) {
		return networkHelper.toString(network);
	}
	
	@Override
	public void initialize() {
		Network network = selectionContext.getSelection("network");
		if (network != null)
			initialize(network);
	}
	
	protected void initialize(Network network) {
		NetworkUtil.initialize(network);
		networkWizard.initialize(network);
		setContext("network", network);
	}
	
	public void handleNetworkSelected(@Observes @Selected Network network) {
		selectionContext.setSelection("network",  network);
		networkPageManager.updateState(network);
		networkPageManager.refreshMembers();
		setRecord(network);
	}
	
	@Override
	public String newRecord() {
		return newNetwork();
	}
	
	public String newNetwork() {
		try {
			Network network = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("network",  network);
			String url = networkPageManager.initializeNetworkCreationPage(network);
			networkPageManager.pushContext(networkWizard);
			initialize(network);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Network create() {
		Network network = NetworkUtil.create();
		return network;
	}
	
	@Override
	public Network clone(Network network) {
		network = NetworkUtil.clone(network);
		return network;
	}
	
	@Override
	public String viewRecord() {
		return viewNetwork();
	}
	
	public String viewNetwork() {
		Network network = selectionContext.getSelection("network");
		String url = viewNetwork(network);
		return url;
	}
	
	public String viewNetwork(Network network) {
		try {
			String url = networkPageManager.initializeNetworkSummaryView(network);
			networkPageManager.pushContext(networkWizard);
			initialize(network);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editNetwork();
	}
	
	public String editNetwork() {
		Network network = selectionContext.getSelection("network");
		String url = editNetwork(network);
		return url;
	}
	
	public String editNetwork(Network network) {
		try {
			//network = clone(network);
			selectionContext.resetOrigin();
			selectionContext.setSelection("network",  network);
			String url = networkPageManager.initializeNetworkUpdatePage(network);
			networkPageManager.pushContext(networkWizard);
			initialize(network);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveNetwork() {
		Network network = getNetwork();
		if (validateNetwork(network)) {
			if (isImmediate())
				persistNetwork(network);
			outject("network", network);
		}
	}
	
	public void persistNetwork(Network network) {
		saveNetwork(network);
	}
	
	public void saveNetwork(Network network) {
		try {
			saveNetworkToSystem(network);
			networkEventManager.fireAddedEvent(network);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveNetworkToSystem(Network network) {
		networkDataManager.saveNetwork(network);
	}
	
	public void handleSaveNetwork(@Observes @Add Network network) {
		saveNetwork(network);
	}
	
	public void addNetwork(Network network) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichNetwork(Network network) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Network network) {
		return validateNetwork(network);
	}
	
	public boolean validateNetwork(Network network) {
		Validator validator = getValidator();
		boolean isValid = NetworkUtil.validate(network);
		Display display = getFromSession("display");
		display.setModule("networkInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveNetwork() {
		display = getFromSession("display");
		display.setModule("networkInfo");
		Network network = selectionContext.getSelection("network");
		if (network == null) {
			display.error("Network record must be selected.");
		}
	}
	
	public String handleRemoveNetwork(@Observes @Remove Network network) {
		display = getFromSession("display");
		display.setModule("networkInfo");
		try {
			display.info("Removing Network "+NetworkUtil.getLabel(network)+" from the system.");
			removeNetworkFromSystem(network);
			selectionContext.clearSelection("network");
			networkEventManager.fireClearSelectionEvent();
			networkEventManager.fireRemovedEvent(network);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeNetworkFromSystem(Network network) {
		if (networkDataManager.removeNetwork(network))
			setRecord(null);
	}
	
	public void cancelNetwork() {
		BeanContext.removeFromSession("network");
		networkPageManager.removeContext(networkWizard);
	}
	
}
