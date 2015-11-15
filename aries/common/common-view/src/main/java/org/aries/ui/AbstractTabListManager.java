package org.aries.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.enterprise.event.Observes;
import javax.faces.model.DataModel;

import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.runtime.BeanContext;
import org.aries.ui.event.ResetEvent;
import org.aries.ui.model.ListTableModel;
import org.aries.ui.tab.TabManager;


public abstract class AbstractTabListManager<R, T> extends AbstractRecordListManager<R> implements TabManager {
	
	public abstract String getRecordName(R record);

	protected abstract R getRecord(T rowObject);

	//public abstract void validate(List<R> records);
	
	protected abstract T createRowObject(R record);

	protected abstract void refreshModel();
	
	
	//protected Collection<R> recordList;

	protected Map<Object, R> recordByIdMap;

	protected Map<String, R> recordByNameMap;

	protected List<T> objectList;

	protected DataModel<T> dataModel;

	protected R selectedRecord;

	protected String selectedRecordId;

	protected String selectedRecordIndex;

	protected T selectedRowObject;
	
	private String viewId;
	
	private String tabId;

	private String tabLabel;
	
	private boolean tabVisible;
	
	

	@Override
	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}
	
	@Override
	public String getTabId() {
		return tabId;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}

	@Override
	public String getTabLabel() {
		return tabLabel;
	}
	
	public void setTabLabel(String tabLabel) {
		this.tabLabel = tabLabel;
	}
	
	public boolean isTabVisible() {
		return tabVisible;
	}

	public void setTabVisible(boolean tabVisible) {
		this.tabVisible = tabVisible;
	}

//	public Collection<R> getRecordList() {
//		return recordList;
//	}
//	
//	public void setRecordList(Collection<R> recordList) {
//		this.recordList = recordList;
//	}
	
	public Object getRecordId(R record) {
		//nothing by default
		return null;
	}

	public R getRecordById(Long id) {
		R role = recordByIdMap.get(id);
		return role;
	}
	
	public R getRecordByName(String name) {
		if (recordByNameMap == null)
			refreshModel();
		R role = recordByNameMap.get(name);
		return role;
	}
	
	public DataModel<T> getDataModel() {
		return dataModel;
	}

//	@Override
//	@SuppressWarnings("unchecked")
//	public List<T> getList() {
//		return (List<T>) dataModel.getWrappedData();
//	}
	

	
	//@Override
	public T getSelection() {
		return selectedRowObject;
	}

	//@Override
	public boolean hasSelection() {
		return selectedRecord != null;
	}

	//@Override
	public void setSelection(T rowObject) {
		selectedRecord = getRecord(rowObject);
		selectedRowObject = rowObject;
	}

	public R getSelectedRecord() {
		return selectedRecord;
	}
	
	public void setSelectedRecord(R selectedRecord) {
		this.selectedRecord = selectedRecord;
	}
	
	//@BypassInterceptors
	public String getSelectedRecordId() {
		return selectedRecordId;
	}
	
	public void setSelectedRecordId(String selectedRecordId) {
		//System.out.println("SET_SELECTED_RECORD_ID");
		this.selectedRecordId = selectedRecordId;
	}

	//@BypassInterceptors
	public String getSelectedRecordIndex() {
		return selectedRecordIndex;
	}
	
	public void setSelectedRecordIndex(String selectedRecordIndex) {
		this.selectedRecordIndex = selectedRecordIndex;
		if (selectedRecordIndex != null) {
			int rowIndex = Integer.parseInt(selectedRecordIndex);
			selectedRowObject = objectList.get(rowIndex);
			selectedRecord = getRecord(selectedRowObject);
			dataModel.setRowIndex(rowIndex);
			setSelectedRecord(selectedRecord);
		}
	}
	
	public boolean isSelected() {
		return selectedRowObject != null;
	}
	
	
	public String getHeader(String recordId) {
		if (!StringUtils.isEmpty(recordId)) {
			Long id = Long.parseLong(recordId);
			R record = recordByIdMap.get(id);
			String name = getRecordName(record);
			return name;
		}
		return "";
	}
	
	
	public void setTargetInstance(String instanceName) {
		super.setTargetInstance(instanceName);
		Collection<R> recordList = BeanContext.getFromSession(instanceName);
		if (recordList != null) {
			initialize(recordList);
		} else {
			//TODO post refresh even instead?
			refresh();
		}
	}
	
	
	/*
	 * Lifecycle related
	 */
	
	public void initialize(Collection<R> recordList) {
		Assert.notNull(recordList, "Record list must be specified");
		refreshModel(new ArrayList<R>(recordList));
	}
	
	public void clearSelection() {
		selectedRecord = null;
		selectedRecordId = null;
		selectedRecordIndex = null;
		selectedRowObject = null;
	}
	
//	//SEAM @Observer("org.aries.ui.reset")
//	public void reset(@Observes ResetEvent event) {
//		reset();
//	}
	
	public String refresh() {
		refreshModel();
		return null;
	}
	
	public void refreshModel(Collection<R> recordList) {
		if (recordList == null)
			refreshModel(new ArrayList<R>());
		else refreshModel(new ArrayList<R>(recordList));
	}
	
	public void refreshModel(List<R> recordList) {
		this.recordByIdMap = createRecordByIdMap(recordList);
		this.recordByNameMap = createRecordByNameMap(recordList);
		this.objectList = getObjectList(recordList);
		this.dataModel = getDataModel(objectList);
		this.recordList = recordList;
		//recordsPerPage = 20;
		clearSelection();
		setTitle();
		setHeader();
		setMessage();
		//show();
	}
	
	protected Map<Object, R> createRecordByIdMap(Collection<R> recordList) {
		Map<Object, R> map = new HashMap<Object, R>();
		if (recordList != null) {
			Iterator<R> iterator = recordList.iterator();
			while (iterator.hasNext()) {
				R record = iterator.next();
				Object id = getRecordId(record);
				map.put(id, record);
			}
		}
		return map;
	}
	
	protected Map<String, R> createRecordByNameMap(Collection<R> recordList) {
		Map<String, R> map = new HashMap<String, R>();
		if (recordList != null) {
			Iterator<R> iterator = recordList.iterator();
			while (iterator.hasNext()) {
				R record = iterator.next();
				String name = getRecordName(record);
				map.put(name, record);
			}
		}
		return map;
	}
	
	public DataModel<T> getDataModel(Collection<R> recordList) {
		List<T> objectList = getObjectList(recordList); 
		DataModel<T> dataModel = getDataModel(objectList);
		return dataModel;
	}

	public DataModel<T> getDataModel(List<T> objectList) {
		@SuppressWarnings("unchecked") DataModel<T> dataModel = new ListTableModel<T>(objectList);
		return dataModel;
	}

	public List<T> getObjectList(Collection<R> recordList) {
		//Assert.notNull(recordList, "User records should not be null");
		List<T> list = new ArrayList<T>();
		if (recordList != null) {
			for (R record : recordList) {
				T listObject = createRowObject(record);
				list.add(listObject);
			}
		}
		return list;
	}

	
	public void validate(Collection<R> recordList) {
		//do nothing for now - otherwise call the abstract helper 
	}

}
