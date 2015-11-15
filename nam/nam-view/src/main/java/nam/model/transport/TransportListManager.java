package nam.model.transport;

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

import nam.model.Transport;
import nam.model.util.TransportUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("transportListManager")
public class TransportListManager extends AbstractDomainListManager<Transport, TransportListObject> implements Serializable {
	
	@Inject
	private TransportDataManager transportDataManager;
	
	@Inject
	private TransportEventManager transportEventManager;
	
	@Inject
	private TransportInfoManager transportInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "transportList";
	}
	
	@Override
	public String getTitle() {
		return "Transport List";
	}
	
	@Override
	public Object getRecordKey(Transport transport) {
		return TransportUtil.getKey(transport);
	}
	
	@Override
	public String getRecordName(Transport transport) {
		return TransportUtil.getLabel(transport);
	}
	
	@Override
	protected Class<Transport> getRecordClass() {
		return Transport.class;
	}
	
	@Override
	protected Transport getRecord(TransportListObject rowObject) {
		return rowObject.getTransport();
	}
	
	@Override
	public Transport getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? TransportUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Transport transport) {
		super.setSelectedRecord(transport);
		fireSelectedEvent(transport);
	}
	
	protected void fireSelectedEvent(Transport transport) {
		transportEventManager.fireSelectedEvent(transport);
	}
	
	public boolean isSelected(Transport transport) {
		Transport selection = selectionContext.getSelection("transport");
		boolean selected = selection != null && selection.equals(transport);
		return selected;
	}
	
	@Override
	protected TransportListObject createRowObject(Transport transport) {
		TransportListObject listObject = new TransportListObject(transport);
		listObject.setSelected(isSelected(transport));
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
	protected Collection<Transport> createRecordList() {
		try {
			Collection<Transport> transportList = transportDataManager.getTransportList();
			if (transportList != null)
				return transportList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewTransport() {
		return viewTransport(selectedRecordKey);
	}
	
	public String viewTransport(Object recordKey) {
		Transport transport = recordByKeyMap.get(recordKey);
		return viewTransport(transport);
	}
	
	public String viewTransport(Transport transport) {
		String url = transportInfoManager.viewTransport(transport);
		return url;
	}
	
	public String editTransport() {
		return editTransport(selectedRecordKey);
	}
	
	public String editTransport(Object recordKey) {
		Transport transport = recordByKeyMap.get(recordKey);
		return editTransport(transport);
	}
	
	public String editTransport(Transport transport) {
		String url = transportInfoManager.editTransport(transport);
		return url;
	}
	
	public void removeTransport() {
		removeTransport(selectedRecordKey);
	}
	
	public void removeTransport(Object recordKey) {
		Transport transport = recordByKeyMap.get(recordKey);
		removeTransport(transport);
	}
	
	public void removeTransport(Transport transport) {
		try {
			if (transportDataManager.removeTransport(transport))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelTransport(@Observes @Cancelled Transport transport) {
		try {
			//Object key = TransportUtil.getKey(transport);
			//recordByKeyMap.put(key, transport);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("transport");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateTransport(Collection<Transport> transportList) {
		return TransportUtil.validate(transportList);
	}
	
	public void exportTransportList(@Observes @Export String tableId) {
		//String tableId = "pageForm:transportListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
