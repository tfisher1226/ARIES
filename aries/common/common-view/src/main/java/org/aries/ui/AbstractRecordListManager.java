package org.aries.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.aries.runtime.BeanContext;
import org.aries.ui.util.SeamConversationHelper;
import org.aries.util.NameUtil;


public abstract class AbstractRecordListManager<T> extends AbstractPanelManager {
	
	protected abstract Class<T> getRecordClass();
	
	protected Collection<T> recordList;
	
	
	public boolean isEmpty() {
		return isEmpty(recordList);
	}
	
	public boolean isEmpty(Collection<T> recordList) {
		return recordList == null || recordList.isEmpty();
	}
	
	public Collection<T> getRecordList() {
		if (recordList == null)
			recordList = createRecordList();
		return recordList;
	}

	public void setRecordList(Collection<T> recordList) {
		this.recordList = recordList;
	}
	
	protected Collection<T> createRecordList() {
		return new ArrayList<T>();
	}
	
//	protected String getRecordText() {
//		return "unspecified";
//	}

//	@SuppressWarnings("unchecked")
//	protected Class<T> getRecordClass() {
//		return (Class<T>) record.getClass();
//	}
	
	protected String getRecordClassName() {
		return NameUtil.getSimpleName(getRecordClass().getName());
	}
	
	
	//TODO TAKE THIS OUT
	public String getModule() {
		return getRecordClassName() + "List";
	}

	public String getClientId() {
		return getRecordClassName() + "List";
	}

	protected Display getDisplay() {
		return getDisplay(getClientId());
	}
	
	
	/**
	 * Initializes manager state.
	 * Executes at page load time.
	 * @return The path to next page or null. 
	 */
	public void initialize() {
		super.initialize();
	}
	
	public String refresh() {
		if (recordList != null) {
			String name = getClientId().toLowerCase();
			outject(name, recordList);
		}
		return null;
	}
	
	public void show() {
		if (recordList != null)
			outject(recordList);
		super.show();
	}
	
	
	protected SeamConversationHelper getConversationHelper() {
		return BeanContext.getFromSession("seamConversationHelper");
	}
	
	public void outject(Object instance) {
		getConversationHelper().outject(instance);
	}
	
	public void outject(String name, Object instance) {
		getConversationHelper().outject(name, instance);
	}
	

	public void setContext(List<T> recordList) {
		setContext(getClientId(), recordList);
	}
	
	public void setContext(String module, List<T> recordList) {
		setRecordList(recordList);
		setTitle();
		setHeader();
		setMessage();
	}

	public void setContext(String owner, String name) {
		setContext(getClientId(), owner, name);
	}

	public void setTitle() {
		if (!isEmpty(recordList)) {
			setTitle(getClientId()+" List Information");
		} else setTitle(getClientId()); 
	}
	
	public void setHeader() {
		if (!isEmpty(recordList)) {
			setHeader(getRecordClassName()+" List Information for: "+getClientId());
		} else setHeader("New "+getRecordClassName());
	}
	
	public void setMessage() {
		display = getDisplay();
		if (!isEmpty(recordList)) {
			display.info("Specify information for "+getClientId());
		} else display.info("Specify information for new "+getRecordClassName()+" List");
	}
	
	public void setTargetInstance(String instanceName) {
		this.recordList = BeanContext.getFromSession(instanceName);
		setActionEvent(instanceName+"Entered");
		setCancelEvent(instanceName+"Cancelled");
	}
	
//	public String getRenderList() {
//		return targetDomain+"Form:"+targetDomain+""+targetField;
//		//return fieldName+"PersonNameModule, "+fieldName+"PersonNameDialog";
//		//return #{parent}Form:#{parent}#{type}
//	}
	
}
