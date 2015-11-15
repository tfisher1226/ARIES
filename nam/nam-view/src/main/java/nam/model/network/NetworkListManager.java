package nam.model.network;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.manager.ExportManager;

import nam.model.Network;
import nam.model.util.NetworkUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("networkListManager")
public class NetworkListManager extends AbstractDomainListManager<Network, NetworkListObject> implements Serializable {
	
	@Inject
	private NetworkDataManager networkDataManager;
	
	@Inject
	private NetworkEventManager networkEventManager;
	
	@Inject
	private NetworkInfoManager networkInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "networkList";
	}
	
	@Override
	public String getTitle() {
		return "Network List";
	}
	
	@Override
	public Object getRecordKey(Network network) {
		return NetworkUtil.getKey(network);
	}
	
	@Override
	public String getRecordName(Network network) {
		return NetworkUtil.getLabel(network);
	}
	
	@Override
	protected Class<Network> getRecordClass() {
		return Network.class;
	}
	
	@Override
	protected Network getRecord(NetworkListObject rowObject) {
		return rowObject.getNetwork();
	}
	
	@Override
	public Network getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? NetworkUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Network network) {
		super.setSelectedRecord(network);
		fireSelectedEvent(network);
	}
	
	protected void fireSelectedEvent(Network network) {
		networkEventManager.fireSelectedEvent(network);
	}
	
	public boolean isSelected(Network network) {
		Network selection = selectionContext.getSelection("network");
		boolean selected = selection != null && selection.equals(network);
		return selected;
	}
	
	@Override
	protected NetworkListObject createRowObject(Network network) {
		NetworkListObject listObject = new NetworkListObject(network);
		listObject.setSelected(isSelected(network));
		return listObject;
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
		else refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Network> createRecordList() {
		try {
			Collection<Network> networkList = networkDataManager.getNetworkList();
			if (networkList != null)
				return networkList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewNetwork() {
		return viewNetwork(selectedRecordKey);
	}
	
	public String viewNetwork(Object recordKey) {
		Network network = recordByKeyMap.get(recordKey);
		return viewNetwork(network);
	}
	
	public String viewNetwork(Network network) {
		String url = networkInfoManager.viewNetwork(network);
		return url;
	}
	
	public String editNetwork() {
		return editNetwork(selectedRecordKey);
	}
	
	public String editNetwork(Object recordKey) {
		Network network = recordByKeyMap.get(recordKey);
		return editNetwork(network);
	}
	
	public String editNetwork(Network network) {
		String url = networkInfoManager.editNetwork(network);
		return url;
	}
	
	public void removeNetwork() {
		removeNetwork(selectedRecordKey);
	}
	
	public void removeNetwork(Object recordKey) {
		Network network = recordByKeyMap.get(recordKey);
		removeNetwork(network);
	}
	
	public void removeNetwork(Network network) {
		try {
			if (networkDataManager.removeNetwork(network))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelNetwork(@Observes @Cancelled Network network) {
		try {
			//Object key = NetworkUtil.getKey(network);
			//recordByKeyMap.put(key, network);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("network");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateNetwork(Collection<Network> networkList) {
		return NetworkUtil.validate(networkList);
	}
	
	public void exportNetworkList(@Observes @Export String tableId) {
		//String tableId = "pageForm:networkListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
