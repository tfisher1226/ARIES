package nam.model.operation;

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

import nam.model.Operation;
import nam.model.util.OperationUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("operationListManager")
public class OperationListManager extends AbstractDomainListManager<Operation, OperationListObject> implements Serializable {
	
	@Inject
	private OperationDataManager operationDataManager;
	
	@Inject
	private OperationEventManager operationEventManager;
	
	@Inject
	private OperationInfoManager operationInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "operationList";
	}
	
	@Override
	public String getTitle() {
		return "Operation List";
	}
	
	@Override
	public Object getRecordKey(Operation operation) {
		return OperationUtil.getKey(operation);
	}
	
	@Override
	public String getRecordName(Operation operation) {
		return OperationUtil.getLabel(operation);
	}
	
	@Override
	protected Class<Operation> getRecordClass() {
		return Operation.class;
	}
	
	@Override
	protected Operation getRecord(OperationListObject rowObject) {
		return rowObject.getOperation();
	}
	
	@Override
	public Operation getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? OperationUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Operation operation) {
		super.setSelectedRecord(operation);
		fireSelectedEvent(operation);
	}
	
	protected void fireSelectedEvent(Operation operation) {
		operationEventManager.fireSelectedEvent(operation);
	}
	
	public boolean isSelected(Operation operation) {
		Operation selection = selectionContext.getSelection("operation");
		boolean selected = selection != null && selection.equals(operation);
		return selected;
	}
	
	public boolean isChecked(Operation operation) {
		Collection<Operation> selection = selectionContext.getSelection("operationSelection");
		boolean checked = selection != null && selection.contains(operation);
		return checked;
	}
	
	@Override
	protected OperationListObject createRowObject(Operation operation) {
		OperationListObject listObject = new OperationListObject(operation);
		listObject.setSelected(isSelected(operation));
		listObject.setChecked(isChecked(operation));
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
	protected Collection<Operation> createRecordList() {
		try {
			Collection<Operation> operationList = operationDataManager.getOperationList();
			if (operationList != null)
				return operationList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewOperation() {
		return viewOperation(selectedRecordKey);
	}
	
	public String viewOperation(Object recordKey) {
		Operation operation = recordByKeyMap.get(recordKey);
		return viewOperation(operation);
	}
	
	public String viewOperation(Operation operation) {
		String url = operationInfoManager.viewOperation(operation);
		return url;
	}
	
	public String editOperation() {
		return editOperation(selectedRecordKey);
	}
	
	public String editOperation(Object recordKey) {
		Operation operation = recordByKeyMap.get(recordKey);
		return editOperation(operation);
	}
	
	public String editOperation(Operation operation) {
		String url = operationInfoManager.editOperation(operation);
		return url;
	}
	
	public void removeOperation() {
		removeOperation(selectedRecordKey);
	}
	
	public void removeOperation(Object recordKey) {
		Operation operation = recordByKeyMap.get(recordKey);
		removeOperation(operation);
	}
	
	public void removeOperation(Operation operation) {
		try {
			if (operationDataManager.removeOperation(operation))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelOperation(@Observes @Cancelled Operation operation) {
		try {
			//Object key = OperationUtil.getKey(operation);
			//recordByKeyMap.put(key, operation);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("operation");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateOperation(Collection<Operation> operationList) {
		return OperationUtil.validate(operationList);
	}
	
	public void exportOperationList(@Observes @Export String tableId) {
		//String tableId = "pageForm:operationListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
