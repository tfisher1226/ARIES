package nam.model.transferMode;

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

import nam.model.TransferMode;


@SessionScoped
@Named("transferModeSelectManager")
public class TransferModeSelectManager extends AbstractSelectManager<TransferMode, TransferMode> implements Serializable {
	
	@Override
	public String getClientId() {
		return "transferModeSelect";
	}
	
	@Override
	public String getTitle() {
		return "TransferMode Selection";
	}
	
	@Override
	protected Class<TransferMode> getRecordClass() {
		return TransferMode.class;
	}
	
	@Override
	public String toString(TransferMode transferMode) {
		return transferMode.name();
	}
	
//	protected TransferModeHelper getTransferModeHelper() {
//		return BeanContext.getFromSession("transferModeHelper");
//	}
//	
//	protected TransferModeListManager getTransferModeListManager() {
//		return BeanContext.getFromSession("transferModeListManager");
//	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshTransferModeList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<TransferMode> recordList) {
		//TransferModeListManager transferModeListManager = getTransferModeListManager();
		//DataModel<TransferModeListObject> dataModel = transferModeListManager.getDataModel(recordList);
		//setSelectedItems(dataModel);
	}
	
	public void refreshTransferModeList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected List<TransferMode> refreshRecords() {
		TransferMode[] values = TransferMode.values();
		List<TransferMode> masterList = new ArrayList<TransferMode>();
		for (TransferMode capability : values) {
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
	public void sortRecords(List<TransferMode> transferModeList) {
		Collections.sort(transferModeList);
	}
	
}
