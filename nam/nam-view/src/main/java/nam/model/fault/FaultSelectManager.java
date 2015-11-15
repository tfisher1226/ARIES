package nam.model.fault;

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

import nam.model.Fault;
import nam.model.util.FaultUtil;


@SessionScoped
@Named("faultSelectManager")
public class FaultSelectManager extends AbstractSelectManager<Fault, FaultListObject> implements Serializable {
	
	@Inject
	private FaultDataManager faultDataManager;
	
	@Inject
	private FaultHelper faultHelper;
	
	
	@Override
	public String getClientId() {
		return "faultSelect";
	}
	
	@Override
	public String getTitle() {
		return "Fault Selection";
	}
	
	@Override
	protected Class<Fault> getRecordClass() {
		return Fault.class;
	}
	
	@Override
	public boolean isEmpty(Fault fault) {
		return faultHelper.isEmpty(fault);
	}
	
	@Override
	public String toString(Fault fault) {
		return faultHelper.toString(fault);
	}
	
	protected FaultHelper getFaultHelper() {
		return BeanContext.getFromSession("faultHelper");
	}
	
	protected FaultListManager getFaultListManager() {
		return BeanContext.getFromSession("faultListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshFaultList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Fault> recordList) {
		FaultListManager faultListManager = getFaultListManager();
		DataModel<FaultListObject> dataModel = faultListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshFaultList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Fault> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Fault> faultList = BeanContext.getFromConversation(instanceId);
		return faultList;
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
	public void sortRecords(List<Fault> faultList) {
		Collections.sort(faultList, new Comparator<Fault>() {
			public int compare(Fault fault1, Fault fault2) {
				String text1 = FaultUtil.toString(fault1);
				String text2 = FaultUtil.toString(fault2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
