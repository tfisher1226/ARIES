package nam.model.transactionUsage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.TransactionScope;
import nam.model.TransactionUsage;
import nam.model.transactionScope.TransactionScopeListObject;


@SessionScoped
@Named("transactionUsageSelectManager")
public class TransactionUsageSelectManager extends AbstractSelectManager<TransactionUsage, TransactionUsageListObject> implements Serializable {
	
	@Override
	public String getClientId() {
		return "transactionUsageSelect";
	}
	
	@Override
	public String getTitle() {
		return "TransactionUsage Selection";
	}
	
	@Override
	protected Class<TransactionUsage> getRecordClass() {
		return TransactionUsage.class;
	}
	
	@Override
	public String toString(TransactionUsage transactionUsage) {
		return transactionUsage.name();
	}
	
//	protected TransactionUsageHelper getTransactionUsageHelper() {
//		return BeanContext.getFromSession("transactionUsageHelper");
//	}
//	
//	protected TransactionUsageListManager getTransactionUsageListManager() {
//		return BeanContext.getFromSession("transactionUsageListManager");
//	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshTransactionUsageList();
		populate(selectedRecords);
	}
	
	@Override
	protected TransactionUsageListObject createRowObject(TransactionUsage record) {
		TransactionUsageListObject listObject = new TransactionUsageListObject(record);
		return listObject;
	}
	
	@Override
	public void populateItems(Collection<TransactionUsage> recordList) {
		DataModel<TransactionUsageListObject> dataModel = getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshTransactionUsageList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected List<TransactionUsage> refreshRecords() {
		TransactionUsage[] values = TransactionUsage.values();
		List<TransactionUsage> masterList = new ArrayList<TransactionUsage>();
		for (TransactionUsage capability : values) {
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
	public void sortRecords(List<TransactionUsage> transactionUsageList) {
		Collections.sort(transactionUsageList);
	}
	
}
