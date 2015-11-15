package nam.model.network;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.Network;
import nam.model.util.NetworkUtil;


@SessionScoped
@Named("networkSelectManager")
public class NetworkSelectManager extends AbstractSelectManager<Network, NetworkListObject> implements Serializable {
	
	@Inject
	private NetworkDataManager networkDataManager;
	
	@Inject
	private NetworkHelper networkHelper;
	
	
	@Override
	public String getClientId() {
		return "networkSelect";
	}
	
	@Override
	public String getTitle() {
		return "Network Selection";
	}
	
	@Override
	protected Class<Network> getRecordClass() {
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
	
	protected NetworkHelper getNetworkHelper() {
		return BeanContext.getFromSession("networkHelper");
	}
	
	protected NetworkListManager getNetworkListManager() {
		return BeanContext.getFromSession("networkListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshNetworkList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Network> recordList) {
		NetworkListManager networkListManager = getNetworkListManager();
		DataModel<NetworkListObject> dataModel = networkListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshNetworkList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Network> refreshRecords() {
		try {
			Collection<Network> records = networkDataManager.getNetworkList();
			return records;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public void activate() {
		initialize();
		super.show();
	}
	
	@Override
	public String submit() {
		setModule(targetField);
		return super.submit();
	}
	
	@Override
	public void sortRecords() {
		sortRecords(recordList);
	}
	
	@Override
	public void sortRecords(List<Network> networkList) {
		Collections.sort(networkList, new Comparator<Network>() {
			public int compare(Network network1, Network network2) {
				String text1 = NetworkUtil.toString(network1);
				String text2 = NetworkUtil.toString(network2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
