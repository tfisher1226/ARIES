package nam.model.timeout;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Operation;
import nam.model.Timeout;
import nam.model.util.TimeoutUtil;
import nam.ui.design.SelectionContext;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.event.Selected;
import org.aries.ui.manager.ExportManager;


@SessionScoped
@Named("timeoutListManager")
public class TimeoutListManager extends AbstractDomainListManager<Timeout, TimeoutListObject> implements Serializable {
	
	@Inject
	private TimeoutDataManager timeoutDataManager;
	
	@Inject
	private TimeoutEventManager timeoutEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "timeoutList";
	}
	
	@Override
	public String getTitle() {
		return "Timeout List";
	}
	
	@Override
	public Object getRecordKey(Timeout timeout) {
		return TimeoutUtil.getKey(timeout);
	}
	
	@Override
	public String getRecordName(Timeout timeout) {
		return TimeoutUtil.toString(timeout);
	}
	
	@Override
	protected Class<Timeout> getRecordClass() {
		return Timeout.class;
	}
	
	@Override
	protected Timeout getRecord(TimeoutListObject rowObject) {
		return rowObject.getTimeout();
	}
	
	@Override
	public Timeout getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? TimeoutUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Timeout timeout) {
		super.setSelectedRecord(timeout);
		fireSelectedEvent(timeout);
	}
	
	protected void fireSelectedEvent(Timeout timeout) {
		timeoutEventManager.fireSelectedEvent(timeout);
	}
	
	@Override
	protected TimeoutListObject createRowObject(Timeout timeout) {
		return new TimeoutListObject(timeout);
	}
	
	protected TimeoutHelper getTimeoutHelper() {
		return BeanContext.getFromSession("timeoutHelper");
	}
	
	protected TimeoutInfoManager getTimeoutInfoManager() {
		return BeanContext.getFromSession("timeoutInfoManager");
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
	
	public void handleRefresh(@Observes @Refresh Object object) {
		//refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(recordList);
	}
	
	@Override
	protected Collection<Timeout> createRecordList() {
		try {
			Collection<Timeout> timeoutList = timeoutDataManager.getTimeoutList();
			return timeoutList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewTimeout() {
		return viewTimeout(selectedRecordKey);
	}
	
	public String viewTimeout(Object recordKey) {
		Timeout timeout = recordByKeyMap.get(recordKey);
		return viewTimeout(timeout);
	}
	
	public String viewTimeout(Timeout timeout) {
		TimeoutInfoManager timeoutInfoManager = BeanContext.getFromSession("timeoutInfoManager");
		String url = timeoutInfoManager.viewTimeout(timeout);
		return url;
	}
	
	public String editTimeout() {
		return editTimeout(selectedRecordKey);
	}
	
	public String editTimeout(Object recordKey) {
		Timeout timeout = recordByKeyMap.get(recordKey);
		return editTimeout(timeout);
	}
	
	public String editTimeout(Timeout timeout) {
		TimeoutInfoManager timeoutInfoManager = BeanContext.getFromSession("timeoutInfoManager");
		String url = timeoutInfoManager.editTimeout(timeout);
		return url;
	}
	
	public void removeTimeout() {
		removeTimeout(selectedRecordKey);
	}
	
	public void removeTimeout(Object recordKey) {
		Timeout timeout = recordByKeyMap.get(recordKey);
		removeTimeout(timeout);
	}
	
	public void removeTimeout(Timeout timeout) {
		try {
			if (timeoutDataManager.removeTimeout(timeout))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelTimeout(@Observes @Cancelled Timeout timeout) {
		try {
			//Object key = TimeoutUtil.getKey(timeout);
			//recordByKeyMap.put(key, timeout);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("timeout");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateTimeout(Collection<Timeout> timeoutList) {
		return TimeoutUtil.validate(timeoutList);
	}
	
	public void exportTimeoutList(@Observes @Export String tableId) {
		//String tableId = "pageForm:timeoutListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
