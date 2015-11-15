package nam.model.fault;

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

import nam.model.Fault;
import nam.model.util.FaultUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("faultListManager")
public class FaultListManager extends AbstractDomainListManager<Fault, FaultListObject> implements Serializable {
	
	@Inject
	private FaultDataManager faultDataManager;
	
	@Inject
	private FaultEventManager faultEventManager;
	
	@Inject
	private FaultInfoManager faultInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "faultList";
	}
	
	@Override
	public String getTitle() {
		return "Fault List";
	}
	
	@Override
	public Object getRecordKey(Fault fault) {
		return FaultUtil.getKey(fault);
	}
	
	@Override
	public String getRecordName(Fault fault) {
		return FaultUtil.getLabel(fault);
	}
	
	@Override
	protected Class<Fault> getRecordClass() {
		return Fault.class;
	}
	
	@Override
	protected Fault getRecord(FaultListObject rowObject) {
		return rowObject.getFault();
	}
	
	@Override
	public Fault getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? FaultUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Fault fault) {
		super.setSelectedRecord(fault);
		fireSelectedEvent(fault);
	}
	
	protected void fireSelectedEvent(Fault fault) {
		faultEventManager.fireSelectedEvent(fault);
	}
	
	public boolean isSelected(Fault fault) {
		Fault selection = selectionContext.getSelection("fault");
		boolean selected = selection != null && selection.equals(fault);
		return selected;
	}
	
	@Override
	protected FaultListObject createRowObject(Fault fault) {
		FaultListObject listObject = new FaultListObject(fault);
		listObject.setSelected(isSelected(fault));
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
	protected Collection<Fault> createRecordList() {
		try {
			Collection<Fault> faultList = faultDataManager.getFaultList();
			if (faultList != null)
				return faultList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewFault() {
		return viewFault(selectedRecordKey);
	}
	
	public String viewFault(Object recordKey) {
		Fault fault = recordByKeyMap.get(recordKey);
		return viewFault(fault);
	}
	
	public String viewFault(Fault fault) {
		String url = faultInfoManager.viewFault(fault);
		return url;
	}
	
	public String editFault() {
		return editFault(selectedRecordKey);
	}
	
	public String editFault(Object recordKey) {
		Fault fault = recordByKeyMap.get(recordKey);
		return editFault(fault);
	}
	
	public String editFault(Fault fault) {
		String url = faultInfoManager.editFault(fault);
		return url;
	}
	
	public void removeFault() {
		removeFault(selectedRecordKey);
	}
	
	public void removeFault(Object recordKey) {
		Fault fault = recordByKeyMap.get(recordKey);
		removeFault(fault);
	}
	
	public void removeFault(Fault fault) {
		try {
			if (faultDataManager.removeFault(fault))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelFault(@Observes @Cancelled Fault fault) {
		try {
			//Object key = FaultUtil.getKey(fault);
			//recordByKeyMap.put(key, fault);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("fault");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateFault(Collection<Fault> faultList) {
		return FaultUtil.validate(faultList);
	}
	
	public void exportFaultList(@Observes @Export String tableId) {
		//String tableId = "pageForm:faultListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
