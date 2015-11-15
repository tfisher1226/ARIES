package nam.model.transactionScope;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import nam.model.TransactionScope;

import org.aries.ui.AbstractSelectManager;


@SessionScoped
@Named("transactionScopeSelectManager")
public class TransactionScopeSelectManager extends AbstractSelectManager<TransactionScope, TransactionScopeListObject> implements Serializable {
	
	@Override
	public String getClientId() {
		return "transactionScopeSelect";
	}
	
	@Override
	public String getTitle() {
		return "TransactionScope Selection";
	}
	
	@Override
	protected Class<TransactionScope> getRecordClass() {
		return TransactionScope.class;
	}
	
	@Override
	public String toString(TransactionScope transactionScope) {
		return transactionScope.name();
	}
	
//	protected TransactionScopeHelper getTransactionScopeHelper() {
//		return BeanContext.getFromSession("transactionScopeHelper");
//	}
//	
//	protected TransactionScopeListManager getTransactionScopeListManager() {
//		return BeanContext.getFromSession("transactionScopeListManager");
//	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshTransactionScopeList();
		populate(selectedRecords);
	}
	
	@Override
	protected TransactionScopeListObject createRowObject(TransactionScope record) {
		TransactionScopeListObject listObject = new TransactionScopeListObject(record);
		return listObject;
	}
	
	@Override
	public void populateItems(Collection<TransactionScope> recordList) {
		//TransactionScopeListManager transactionScopeListManager = getTransactionScopeListManager();
		//DataModel<TransactionScopeListObject> dataModel = transactionScopeListManager.getDataModel(recordList);
		DataModel<TransactionScopeListObject> dataModel = getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshTransactionScopeList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected List<TransactionScope> refreshRecords() {
		TransactionScope[] values = TransactionScope.values();
		List<TransactionScope> masterList = new ArrayList<TransactionScope>();
		for (TransactionScope capability : values) {
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
	public void sortRecords(List<TransactionScope> transactionScopeList) {
		Collections.sort(transactionScopeList);
	}
	
}
