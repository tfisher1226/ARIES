package nam.model.transportType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.TransportType;


@SessionScoped
@Named("transportTypeSelectManager")
public class TransportTypeSelectManager extends AbstractSelectManager<TransportType, TransportTypeListObject> implements Serializable {
	
	@Inject
	private TransportTypeHelper transportTypeHelper;
	
	
	@Override
	public String getClientId() {
		return "transportTypeSelect";
	}
	
	@Override
	public String getTitle() {
		return "TransportType Selection";
	}
	
	@Override
	protected Class<TransportType> getRecordClass() {
		return TransportType.class;
	}
	
	@Override
	public String toString(TransportType transportType) {
		return transportType.name();
	}
	
	protected TransportTypeHelper getTransportTypeHelper() {
		return BeanContext.getFromSession("transportTypeHelper");
	}
	
	protected TransportTypeListManager getTransportTypeListManager() {
		return BeanContext.getFromSession("transportTypeListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshTransportTypeList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<TransportType> recordList) {
		TransportTypeListManager transportTypeListManager = getTransportTypeListManager();
		DataModel<TransportTypeListObject> dataModel = transportTypeListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshTransportTypeList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<TransportType> refreshRecords() {
		TransportType[] values = TransportType.values();
		List<TransportType> masterList = new ArrayList<TransportType>();
		for (TransportType capability : values) {
			masterList.add(capability);
		}
		return masterList;
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
	public void sortRecords(List<TransportType> transportTypeList) {
		Collections.sort(transportTypeList);
	}
	
}
