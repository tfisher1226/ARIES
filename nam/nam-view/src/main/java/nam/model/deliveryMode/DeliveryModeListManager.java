package nam.model.deliveryMode;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.ui.AbstractDomainListManager;

import nam.model.DeliveryMode;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("deliveryModeListManager")
public class DeliveryModeListManager extends AbstractDomainListManager<DeliveryMode, DeliveryModeListObject> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "deliveryModeList";
	}
	
	@Override
	public String getTitle() {
		return "DeliveryMode List";
	}
	
	@Override
	public Object getRecordKey(DeliveryMode deliveryMode) {
		return deliveryMode.name();
	}
	
	@Override
	public String getRecordName(DeliveryMode deliveryMode) {
		return deliveryMode.name();
	}
	
	@Override
	protected Class<DeliveryMode> getRecordClass() {
		return DeliveryMode.class;
	}
	
	@Override
	protected DeliveryMode getRecord(DeliveryModeListObject rowObject) {
		return rowObject.getDeliveryMode();
	}
	
	@Override
	public DeliveryMode getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? selectedRecord.name() : null;
	}
	
	@Override
	public void setSelectedRecord(DeliveryMode deliveryMode) {
		super.setSelectedRecord(deliveryMode);
	}
	
	public boolean isSelected(DeliveryMode deliveryMode) {
		DeliveryMode selection = selectionContext.getSelection("deliveryMode");
		boolean selected = selection != null && selection.equals(deliveryMode);
		return selected;
	}
	
	@Override
	protected DeliveryModeListObject createRowObject(DeliveryMode deliveryMode) {
		DeliveryModeListObject listObject = new DeliveryModeListObject(deliveryMode);
		listObject.setSelected(isSelected(deliveryMode));
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
	protected Collection<DeliveryMode> createRecordList() {
		try {
			Collection<DeliveryMode> deliveryModeList = Arrays.asList(DeliveryMode.values());
			return deliveryModeList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
}
