package nam.model.timeout;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Timeout;
import nam.model.util.TimeoutUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;


@SessionScoped
@Named("timeoutSelectManager")
public class TimeoutSelectManager extends AbstractSelectManager<Timeout, TimeoutListObject> implements Serializable {
	
	@Inject
	private TimeoutDataManager timeoutDataManager;
	
	
	@Override
	public String getClientId() {
		return "timeoutSelect";
	}
	
	@Override
	public String getTitle() {
		return "Timeout Selection";
	}
	
	@Override
	protected Class<Timeout> getRecordClass() {
		return Timeout.class;
	}
	
	@Override
	public boolean isEmpty(Timeout timeout) {
		return getTimeoutHelper().isEmpty(timeout);
	}
	
	@Override
	public String toString(Timeout timeout) {
		return getTimeoutHelper().toString(timeout);
	}
	
	protected TimeoutHelper getTimeoutHelper() {
		return BeanContext.getFromSession("timeoutHelper");
	}
	
	protected TimeoutListManager getTimeoutListManager() {
		return BeanContext.getFromSession("timeoutListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshTimeoutList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Timeout> recordList) {
		TimeoutListManager timeoutListManager = getTimeoutListManager();
		DataModel<TimeoutListObject> dataModel = timeoutListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshTimeoutList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Timeout> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Timeout> timeoutList = BeanContext.getFromConversation(instanceId);
		return timeoutList;
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
	public void sortRecords(List<Timeout> timeoutList) {
		Collections.sort(timeoutList, new Comparator<Timeout>() {
			public int compare(Timeout timeout1, Timeout timeout2) {
				String text1 = TimeoutUtil.toString(timeout1);
				String text2 = TimeoutUtil.toString(timeout2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
