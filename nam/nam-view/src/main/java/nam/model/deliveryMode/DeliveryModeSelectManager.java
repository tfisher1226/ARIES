package nam.model.deliveryMode;

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

import nam.model.DeliveryMode;


@SessionScoped
@Named("deliveryModeSelectManager")
public class DeliveryModeSelectManager extends AbstractSelectManager<DeliveryMode, DeliveryModeListObject> implements Serializable {
	
	@Inject
	private DeliveryModeHelper deliveryModeHelper;
	
	
	@Override
	public String getClientId() {
		return "deliveryModeSelect";
	}
	
	@Override
	public String getTitle() {
		return "DeliveryMode Selection";
	}
	
	@Override
	protected Class<DeliveryMode> getRecordClass() {
		return DeliveryMode.class;
	}
	
	@Override
	public String toString(DeliveryMode deliveryMode) {
		return deliveryMode.name();
	}
	
	protected DeliveryModeHelper getDeliveryModeHelper() {
		return BeanContext.getFromSession("deliveryModeHelper");
	}
	
	protected DeliveryModeListManager getDeliveryModeListManager() {
		return BeanContext.getFromSession("deliveryModeListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshDeliveryModeList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<DeliveryMode> recordList) {
		DeliveryModeListManager deliveryModeListManager = getDeliveryModeListManager();
		DataModel<DeliveryModeListObject> dataModel = deliveryModeListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshDeliveryModeList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<DeliveryMode> refreshRecords() {
		DeliveryMode[] values = DeliveryMode.values();
		List<DeliveryMode> masterList = new ArrayList<DeliveryMode>();
		for (DeliveryMode capability : values) {
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
	public void sortRecords(List<DeliveryMode> deliveryModeList) {
		Collections.sort(deliveryModeList);
	}
	
}
