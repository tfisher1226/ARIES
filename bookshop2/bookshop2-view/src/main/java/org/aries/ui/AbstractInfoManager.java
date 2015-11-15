package org.aries.ui;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractPanelManager;
import org.aries.ui.Display;


public abstract class AbstractInfoManager extends AbstractPanelManager {

	protected abstract boolean isRecordEmpty();

	protected abstract boolean isRecordValid();
	
	protected abstract Object createNewRecord();

	protected abstract String getRecordClassName();

	//public abstract boolean isEmpty(T record);
	
	//public abstract String toString(T record);
	
	//public abstract boolean validate(T record);


	
	protected String moduleName;
	
	protected String instanceName;
	
	private Object record;
	
	
//	public boolean isEmpty() {
//		return isEmpty(record);
//	}
	
	public Object getRecord() {
		return record;
	}

	public void setRecord(Object record) {
		this.record = record;
	}
	
	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

//	public T create() {
//		return null;
//	}
	
//	public T clone(T record) {
//		return record;
//	}

	
	public String getModuleName() {
		return moduleName;
	}

	protected Display getDisplay() {
		return getDisplay(getModuleName());
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
		return null;
	}

	public String getInstanceId() {
		if (instanceName != null)
			return getInstanceId(instanceName);
		return null;
	}
	
	public void activate() {
		String instanceId = getInstanceId();
		Object record = BeanContext.getFromSession(instanceId);
		if (record == null)
			record = createNewRecord();
		//TODO else record = cloneCurrentRecord(record);
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
	
	public void setContext(Object record) {
		setContext(getModuleName(), record);
	}
	
	public void setContext(String module, Object record) {
		//setRecord(record);
		setTitle();
		setHeader();
		setMessage();
	}

	public void setContext(String owner, String name) {
		setContext(getModuleName(), owner, name);
	}

	public void setTitle() {
		if (!isRecordEmpty()) {
			setTitle(getRecordClassName()+" Information");
		} else setTitle(getModuleName()); 
	}
	
	public void setHeader() {
		if (!isRecordEmpty()) {
			setHeader(getRecordClassName()+" Information for: "+getModuleName());
		} else setHeader("New "+getRecordClassName());
	}
	
	public void setMessage() {
		display = getDisplay();
		if (!isRecordEmpty()) {
			display.info("Specify information for "+getModuleName());
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
		String renderList = domain+"Form:"+prefix+""+field;
		return renderList;
	}
	

	public void show() {
		//default outjection
		if (record != null)
			outject(instanceName, record);
		super.show();
	}
	
	public String submit() {
		//setModule(targetField);
		setModified(true);
		if (isRecordValid()) {
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
