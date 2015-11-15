package org.aries.ui;

import nam.model.Project;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.util.NameUtil;


public abstract class AbstractRecordManager<T> extends AbstractViewManager {

	//public abstract T createRecord();
	
	public abstract boolean isEmpty(T record);
	
	public abstract String toString(T record);
	
	public abstract boolean validate(T record);

	protected abstract Class<T> getRecordClass();
	

	private T record;
	
	private T originalRecord;
	
	protected String instanceName;
	
	private String clientId;
	
	
	public boolean isEmpty() {
		return isEmpty(record);
	}
	
	public T getRecord() {
		return record;
	}

	public void setRecord(T record) {
		this.record = record;
	}

	public void unsetRecord(T record) {
		if (this.record != null && this.record.equals(record))
			setRecord(null);
	}

	public T getOriginalRecord() {
		return originalRecord;
	}

	public void setOriginalRecord(T originalRecord) {
		this.originalRecord = originalRecord;
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
	
	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	protected Display getDisplay() {
		return getDisplay(getClientId());
	}


	public T create() {
		return null;
	}
	
	public T clone(T record) {
		return record;
	}


	/**
	 * Initializes manager state.
	 * Executes at page load time.
	 * @return The path to next page or null. 
	 */
	public void initialize() {
		super.initialize();
	}

	public void reset() {
		super.reset();
	}
	
	public String refresh() {
		if (record != null && originalRecord != null) {
			//String instanceId = getInstanceId();
			//record = BeanContext.getFromSession(instanceId);
			//record = clone(originalRecord);
		}
		return null;
	}

	public String getInstanceId() {
		if (instanceName != null)
			return getInstanceId(instanceName);
		return null;
	}
	
	public void activate() {
		String instanceId = getInstanceId();
		record = BeanContext.getFromSession(instanceId);
		if (record == null)
			record = create();
		else record = clone(record);
		initializeContext();
		super.activate();
	}
	
	protected void initializeContext() {
		setContext(instanceName);
	}

	public void setContext(String instanceName) {
		if (!StringUtils.isEmpty(instanceName)) {
			setActionEvent(instanceName + "Entered");
			setCancelEvent(instanceName + "Cancelled");
		}
	}
	
	public void setContext(T record) {
		setContext(getClientId(), record);
	}
	
	public void setContext(String clientId, T record) {
		setClientId(clientId);
		setRecord(record);
		setTitle();
		setHeader();
		setMessage();
	}

	public void setContext(String owner, String name) {
		setContext(getClientId(), owner, name);
	}

	public void setTitle() {
		if (!isEmpty(record)) {
			setTitle(getRecordClassName()+" Information");
		} else setTitle(getClientId()); 
	}
	
	public void setHeader() {
		if (!isEmpty(record)) {
			setHeader(getRecordClassName()+" Information for: "+getClientId());
		} else setHeader("New "+getRecordClassName());
	}
	
	public void setMessage() {
		display = getDisplay();
		if (!isEmpty(record)) {
			display.info("Specify information for "+getClientId());
		} else display.info("Specify information for new "+getRecordClassName()+" Record");
	}
	
	
	public String getTargetInstance() {
		return getInstanceName();
	}
	
	public void setTargetInstance(String targetInstance) {
		setInstanceName(targetInstance);
	}
	
	public String getRenderList() {
		if (StringUtils.isEmpty(targetDomain) && StringUtils.isEmpty(targetField))
			return "";
		String domain = targetDomain;
		String prefix = targetDomain;
		if (StringUtils.isEmpty(targetDomain)) {
			domain = "page";
			prefix = "";
		}
		String field = targetField;
		//String renderList = domain+"Form:"+prefix+""+field;
		String renderList = domain+field;
		return renderList;
	}
	

	public void show() {
		//default outjection
		if (record != null)
			outject(record);
		super.show();
	}
	
	public String submit() {
		//setModule(targetField);
		setModified(true);
		if (validate()) {
			outject(instanceName, record);
			if (actionEvent != null)
				raiseEvent(actionEvent);
		}
		return null;
	}
	
	
//	public boolean validate() {
//		return validate(getRecord());
//	}
	
}
