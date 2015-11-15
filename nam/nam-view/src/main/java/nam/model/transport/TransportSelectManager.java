package nam.model.transport;

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

import nam.model.Transport;
import nam.model.util.TransportUtil;


@SessionScoped
@Named("transportSelectManager")
public class TransportSelectManager extends AbstractSelectManager<Transport, TransportListObject> implements Serializable {
	
	@Inject
	private TransportDataManager transportDataManager;
	
	@Inject
	private TransportHelper transportHelper;
	
	
	@Override
	public String getClientId() {
		return "transportSelect";
	}
	
	@Override
	public String getTitle() {
		return "Transport Selection";
	}
	
	@Override
	protected Class<Transport> getRecordClass() {
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
	
	protected TransportHelper getTransportHelper() {
		return BeanContext.getFromSession("transportHelper");
	}
	
	protected TransportListManager getTransportListManager() {
		return BeanContext.getFromSession("transportListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshTransportList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Transport> recordList) {
		TransportListManager transportListManager = getTransportListManager();
		DataModel<TransportListObject> dataModel = transportListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshTransportList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Transport> refreshRecords() {
		try {
			Collection<Transport> records = transportDataManager.getTransportList();
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
	public void sortRecords(List<Transport> transportList) {
		Collections.sort(transportList, new Comparator<Transport>() {
			public int compare(Transport transport1, Transport transport2) {
				String text1 = TransportUtil.toString(transport1);
				String text2 = TransportUtil.toString(transport2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
