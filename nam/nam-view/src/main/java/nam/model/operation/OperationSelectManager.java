package nam.model.operation;

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

import nam.model.Operation;
import nam.model.util.OperationUtil;


@SessionScoped
@Named("operationSelectManager")
public class OperationSelectManager extends AbstractSelectManager<Operation, OperationListObject> implements Serializable {
	
	@Inject
	private OperationDataManager operationDataManager;
	
	@Inject
	private OperationHelper operationHelper;
	
	
	@Override
	public String getClientId() {
		return "operationSelect";
	}
	
	@Override
	public String getTitle() {
		return "Operation Selection";
	}
	
	@Override
	protected Class<Operation> getRecordClass() {
		return Operation.class;
	}
	
	@Override
	public boolean isEmpty(Operation operation) {
		return operationHelper.isEmpty(operation);
	}
	
	@Override
	public String toString(Operation operation) {
		return operationHelper.toString(operation);
	}
	
	protected OperationHelper getOperationHelper() {
		return BeanContext.getFromSession("operationHelper");
	}
	
	protected OperationListManager getOperationListManager() {
		return BeanContext.getFromSession("operationListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshOperationList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Operation> recordList) {
		OperationListManager operationListManager = getOperationListManager();
		DataModel<OperationListObject> dataModel = operationListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshOperationList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Operation> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Operation> operationList = BeanContext.getFromConversation(instanceId);
		return operationList;
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
	public void sortRecords(List<Operation> operationList) {
		Collections.sort(operationList, new Comparator<Operation>() {
			public int compare(Operation operation1, Operation operation2) {
				String text1 = OperationUtil.toString(operation1);
				String text2 = OperationUtil.toString(operation2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
