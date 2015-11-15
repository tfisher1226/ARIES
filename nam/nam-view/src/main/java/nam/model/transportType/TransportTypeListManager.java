package nam.model.transportType;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.ui.AbstractDomainListManager;

import nam.model.TransportType;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("transportTypeListManager")
public class TransportTypeListManager extends AbstractDomainListManager<TransportType, TransportTypeListObject> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "transportTypeList";
	}
	
	@Override
	public String getTitle() {
		return "TransportType List";
	}
	
	@Override
	public Object getRecordKey(TransportType transportType) {
		return transportType.name();
	}
	
	@Override
	public String getRecordName(TransportType transportType) {
		return transportType.name();
	}
	
	@Override
	protected Class<TransportType> getRecordClass() {
		return TransportType.class;
	}
	
	@Override
	protected TransportType getRecord(TransportTypeListObject rowObject) {
		return rowObject.getTransportType();
	}
	
	@Override
	public TransportType getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? selectedRecord.name() : null;
	}
	
	@Override
	public void setSelectedRecord(TransportType transportType) {
		super.setSelectedRecord(transportType);
	}
	
	public boolean isSelected(TransportType transportType) {
		TransportType selection = selectionContext.getSelection("transportType");
		boolean selected = selection != null && selection.equals(transportType);
		return selected;
	}
	
	@Override
	protected TransportTypeListObject createRowObject(TransportType transportType) {
		TransportTypeListObject listObject = new TransportTypeListObject(transportType);
		listObject.setSelected(isSelected(transportType));
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
	protected Collection<TransportType> createRecordList() {
		try {
			Collection<TransportType> transportTypeList = Arrays.asList(TransportType.values());
			return transportTypeList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
}
