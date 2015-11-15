package org.aries.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.util.SeamConversationHelper;
import org.aries.util.NameUtil;


public abstract class AbstractRecordMapManager<K, T> extends AbstractPanelManager {

	//public abstract T createRecord();
	
	protected abstract boolean isEmpty();
	
	protected abstract boolean isEmpty(T record);
	
	protected abstract boolean isEmpty(Map<K, T> recordMap);
	
	public abstract boolean validate(Map<K, T> recordMap);

	public abstract boolean validate(K key, T record);
	
	protected abstract Class<T> getRecordClass();
	
	protected Map<K, T> recordMap;
	
	
	public AbstractRecordMapManager() {
		recordMap = create();
	}
	
	public Map<K, T> getRecordMap() {
		return recordMap;
	}

	public void setRecordMap(Map<K, T> recordMap) {
		this.recordMap = recordMap;
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
	
	
	public String getModule() {
		return getRecordClassName() + "Map";
	}

	protected Display getDisplay() {
		return getDisplay(getModule());
	}
	
	
	public Collection<T> getValues() {
		List<T> list = new ArrayList<T>();
		Iterator<T> iterator = recordMap.values().iterator();
		while (iterator.hasNext()) {
			T phoneNumber = iterator.next();
			if (!isEmpty(phoneNumber)) {
				list.add(phoneNumber);
			}
		}
		return list;
	}

	public int getValueCount() {
		return recordMap.size();
	}
	
	
	/**
	 * Initializes manager state.
	 * Executes at page load time.
	 * @return The path to next page or null. 
	 */
	public void initialize() {
		if (recordMap == null)
			recordMap = create();
		super.initialize();
	}
	
	public Map<K, T> create() {
		return null;
	}

	public Map<K, T> clone(Map<K, T> map) {
		return map;
	}
	
	public String refresh() {
		if (recordMap != null) {
			String name = NameUtil.uncapName(getModule());
			outject(name, recordMap);
		}
		return null;
	}
	
	public void show() {
		if (recordMap != null)
			outject(recordMap);
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
	

	public void setContext(Map<K, T> recordMap) {
		setContext(getModule(), recordMap);
	}
	
	public void setContext(String module, Map<K, T> recordMap) {
		setRecordMap(recordMap);
		setTitle();
		setHeader();
		setMessage();
	}

	public void setContext(String owner, String name) {
		setContext(getModule(), owner, name);
	}

	public void setTitle() {
		if (!isEmpty(recordMap)) {
			setTitle(getModule()+" Information");
		} else setTitle(getModule()); 
	}
	
	public void setHeader() {
		if (!isEmpty(recordMap)) {
			setHeader(getRecordClassName()+" Information for: "+getModule());
		} else setHeader("New "+getRecordClassName());
	}
	
	public void setMessage() {
		display = getDisplay();
		if (!isEmpty(recordMap)) {
			display.info("Specify information for "+getModule());
		} else display.info("Specify information for new "+getRecordClassName()+"");
	}
	
	public void setTargetInstance(String instanceName) {
		this.recordMap = BeanContext.getFromSession(instanceName);
		if (recordMap == null)
			recordMap = create();
		setActionEvent(instanceName+"Entered");
		setCancelEvent(instanceName+"Cancelled");
		//setInstanceId(instanceName);
	}
	
	public String getRenderList() {
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
	
}
