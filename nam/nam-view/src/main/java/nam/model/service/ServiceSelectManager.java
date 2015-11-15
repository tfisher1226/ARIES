package nam.model.service;

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

import nam.model.Service;
import nam.model.util.ServiceUtil;


@SessionScoped
@Named("serviceSelectManager")
public class ServiceSelectManager extends AbstractSelectManager<Service, ServiceListObject> implements Serializable {
	
	@Inject
	private ServiceDataManager serviceDataManager;
	
	@Inject
	private ServiceHelper serviceHelper;
	
	
	@Override
	public String getClientId() {
		return "serviceSelect";
	}
	
	@Override
	public String getTitle() {
		return "Service Selection";
	}
	
	@Override
	protected Class<Service> getRecordClass() {
		return Service.class;
	}
	
	@Override
	public boolean isEmpty(Service service) {
		return serviceHelper.isEmpty(service);
	}
	
	@Override
	public String toString(Service service) {
		return serviceHelper.toString(service);
	}
	
	protected ServiceHelper getServiceHelper() {
		return BeanContext.getFromSession("serviceHelper");
	}
	
	protected ServiceListManager getServiceListManager() {
		return BeanContext.getFromSession("serviceListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshServiceList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Service> recordList) {
		ServiceListManager serviceListManager = getServiceListManager();
		DataModel<ServiceListObject> dataModel = serviceListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshServiceList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Service> refreshRecords() {
		try {
			Collection<Service> records = serviceDataManager.getServiceList();
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
	public void sortRecords(List<Service> serviceList) {
		Collections.sort(serviceList, new Comparator<Service>() {
			public int compare(Service service1, Service service2) {
				String text1 = ServiceUtil.toString(service1);
				String text2 = ServiceUtil.toString(service2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
