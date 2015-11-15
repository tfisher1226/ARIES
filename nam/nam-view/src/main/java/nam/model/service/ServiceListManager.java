package nam.model.service;

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

import nam.model.Service;
import nam.model.util.ServiceUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("serviceListManager")
public class ServiceListManager extends AbstractDomainListManager<Service, ServiceListObject> implements Serializable {
	
	@Inject
	private ServiceDataManager serviceDataManager;
	
	@Inject
	private ServiceEventManager serviceEventManager;
	
	@Inject
	private ServiceInfoManager serviceInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "serviceList";
	}
	
	@Override
	public String getTitle() {
		return "Service List";
	}
	
	@Override
	public Object getRecordKey(Service service) {
		return ServiceUtil.getKey(service);
	}
	
	@Override
	public String getRecordName(Service service) {
		return ServiceUtil.getLabel(service);
	}
	
	@Override
	protected Class<Service> getRecordClass() {
		return Service.class;
	}
	
	@Override
	protected Service getRecord(ServiceListObject rowObject) {
		return rowObject.getService();
	}
	
	@Override
	public Service getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? ServiceUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Service service) {
		super.setSelectedRecord(service);
		fireSelectedEvent(service);
	}
	
	protected void fireSelectedEvent(Service service) {
		serviceEventManager.fireSelectedEvent(service);
	}
	
	public boolean isSelected(Service service) {
		Service selection = selectionContext.getSelection("service");
		boolean selected = selection != null && selection.equals(service);
		return selected;
	}
	
	@Override
	protected ServiceListObject createRowObject(Service service) {
		ServiceListObject listObject = new ServiceListObject(service);
		listObject.setSelected(isSelected(service));
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
	protected Collection<Service> createRecordList() {
		try {
			Collection<Service> serviceList = serviceDataManager.getServiceList();
			if (serviceList != null)
				return serviceList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewService() {
		return viewService(selectedRecordKey);
	}
	
	public String viewService(Object recordKey) {
		Service service = recordByKeyMap.get(recordKey);
		return viewService(service);
	}
	
	public String viewService(Service service) {
		String url = serviceInfoManager.viewService(service);
		return url;
	}
	
	public String editService() {
		return editService(selectedRecordKey);
	}
	
	public String editService(Object recordKey) {
		Service service = recordByKeyMap.get(recordKey);
		return editService(service);
	}
	
	public String editService(Service service) {
		String url = serviceInfoManager.editService(service);
		return url;
	}
	
	public void removeService() {
		removeService(selectedRecordKey);
	}
	
	public void removeService(Object recordKey) {
		Service service = recordByKeyMap.get(recordKey);
		removeService(service);
	}
	
	public void removeService(Service service) {
		try {
			if (serviceDataManager.removeService(service))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelService(@Observes @Cancelled Service service) {
		try {
			//Object key = ServiceUtil.getKey(service);
			//recordByKeyMap.put(key, service);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("service");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateService(Collection<Service> serviceList) {
		return ServiceUtil.validate(serviceList);
	}
	
	public void exportServiceList(@Observes @Export String tableId) {
		//String tableId = "pageForm:serviceListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
