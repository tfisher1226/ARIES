package org.aries.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.DataModel;

import org.aries.runtime.BeanContext;
import org.aries.ui.model.ListTableModel;


@SuppressWarnings("serial")
public abstract class AbstractSelectManager<R, I> extends AbstractRecordManager<R> implements Serializable {

	protected abstract Collection<R> refreshRecords();

	protected abstract void sortRecords();

	protected abstract void sortRecords(List<R> recordList);
	
	protected abstract void populateItems(Collection<R> recordList);
	
	
	protected Object ownerRecord;
	
	protected Collection<R> masterList;
	
	protected List<R> recordList;
	
	protected R selectedRecord;

	protected Collection<R> selectedRecords;

	protected Collection<R> selectionList;
	
	private DataModel<I> selectedItems;
	
	//@In(required = false)
	//@Out(required = false)
	private List<R> selectedItems1;

	//@In(required = false)
	//@Out(required = false)
	private List<R> selectedItems2;
	
	protected boolean selectionRequired;
	
	private boolean multipleSelectionEnabled;

	
	public Object getOwnerRecord() {
		return ownerRecord;
	}

	public void setOwnerRecord(Object ownerRecord) {
		this.ownerRecord = ownerRecord;
	}
	
	@Override
	public boolean isEmpty(R record) {
		//true by default - selection only
		return true;
	}
	
	public boolean isEmpty() {
		return isEmpty(selectionList);
	}
	
	public boolean isEmpty(Collection<R> selectionList) {
		return selectionList == null || selectionList.isEmpty();
	}

	public Collection<R> getSelectedRecords() {
		return selectedRecords;
	}

	public void setSelectedRecords(Collection<R> selectedRecords) {
		this.selectedRecords = selectedRecords;
	}
	
	public Collection<R> getSelectionList() {
		return selectionList;
	}

	public void setSelectionList(Collection<R> selectionList) {
		this.selectionList = selectionList;
	}

	public Collection<R> getMasterList() {
		return masterList;
	}

	public Collection<R> getRecordList() {
		return recordList;
	}

	public DataModel<I> getSelectedItems() {
		return selectedItems;
	}
	
	public void setSelectedItems(DataModel<I> selectedItems) {
		this.selectedItems = selectedItems;
	}
	
	public List<R> getSelectedItems1() {
		return selectedItems1;
	}

	public void setSelectedItems1(List<R> selectedItems) {
		this.selectedItems1 = selectedItems;
	}

	public List<R> getSelectedItems2() {
		return selectedItems2;
	}

	public void setSelectedItems2(List<R> selectedItems) {
		this.selectedItems2 = selectedItems;
	}
	
	public boolean isSelectionRequired() {
		return selectionRequired;
	}

	public void setSelectionRequired(boolean selectionRequired) {
		this.selectionRequired = selectionRequired;
	}
	
	public boolean isMultipleSelectionEnabled() {
		return multipleSelectionEnabled;
	}

	public void setMultipleSelectionEnabled(boolean multipleSelectionEnabled) {
		this.multipleSelectionEnabled = multipleSelectionEnabled;
	}

	
	@SuppressWarnings("unchecked")
	public void setTargetInstance(String instanceName) {
		super.setTargetInstance(instanceName);
		Object instanceValue = BeanContext.getFromSession(instanceName);
		if (instanceValue == null)
			return;
		Collection<R> records = null;
		if (instanceValue instanceof Collection) {
			actionEvent = instanceName + "ListSelected";
			records = (Collection<R>) instanceValue;
		} else {
			actionEvent = instanceName + "Selected";
			R record = (R) instanceValue;
			records = new ArrayList<R>();
			records.add(record);
		}
		setSelectedRecords(records);
		populate(records);
	}
	
	
	public void initialize(Collection<R> roleList) {
		this.ownerRecord = null;
		populate(roleList);
	}
	
	public void populate(Collection<R> roleList) {
		this.recordList = createRecordList();
		this.selectedItems1 = new ArrayList<R>();
		this.selectedItems2 = new ArrayList<R>();
		if (roleList != null) {
			List<R> roleList1 = getItemList1();
			List<R> roleList2 = getItemList2();
			Iterator<R> iterator = roleList.iterator();
			while (iterator.hasNext()) {
				R item = iterator.next();
				if (roleList1.contains(item))
					selectedItems1.add(item);			
				if (roleList2.contains(item))
					selectedItems2.add(item);
			}
		}
		List<R> list = getSelectedRecordList();
		this.selectedRecords = list;
		populateItems(list);
	}

	public R createRecord() {
		throw new UnsupportedOperationException();
	}
	
	protected List<R> createRecordList() {
		if (masterList == null)
			masterList = refreshRecords();
		List<R> recordList = new ArrayList<R>();
		if (masterList == null)
			return recordList;
		Iterator<R> iterator = masterList.iterator();
		while (iterator.hasNext()) {
			R role = iterator.next();
			//this check is included for SelectManager's of self-referencing relationships
			if (ownerRecord != null && !role.equals(ownerRecord)) {
				recordList.add(role);
			} else if (ownerRecord == null) {
				recordList.add(role);
			}
		}
		sortRecords(recordList);
		return recordList;
	}

	public R getSelectedRecord() {
		if (selectedItems1 != null)
			selectedRecords.addAll(selectedItems1);
		if (selectedItems2 != null)
			selectedRecords.addAll(selectedItems2);
		if (selectedRecords.size() > 0)
			return selectedRecords.iterator().next();
		return null;
	}
	
	public List<R> getSelectedRecordList() {
		List<R> selectedRecords = new ArrayList<R>();
		if (selectedItems1 != null)
			selectedRecords.addAll(selectedItems1);
		if (selectedItems2 != null)
			selectedRecords.addAll(selectedItems2);
		return selectedRecords;
	}


	//@Factory(value = "roleList1")
	public List<R> getItemList1() {
		List<R> list = new ArrayList<R>();
		if (recordList == null)
			return list;
		List<R> tmpList = new ArrayList<R>(recordList);
		if (tmpList != null) {
			int size = tmpList.size();
			if (size > 0) {
				int midPoint = size / 2;
				for (int i=0; i <= midPoint; i++) {
					R record = tmpList.get(i);
					list.add(record);
				}
			}
		}
		return list;
	}
	
	//@Factory(value = "roleList2")
	public List<R> getItemList2() {
		List<R> list = new ArrayList<R>();
		if (recordList == null)
			return list;
		List<R> tmpList = new ArrayList<R>(recordList);
		if (tmpList != null) {
			int size = tmpList.size();
			if (size > 0) {
				int midPoint = (size / 2) + 1;
				for (int i=midPoint; i < size; i++) {
					R record = tmpList.get(i);
					list.add(record);
				}
			}
		}
		return list;
	}
	
	public DataModel<I> getDataModel(Collection<R> recordList) {
		List<I> objectList = getObjectList(recordList); 
		DataModel<I> dataModel = getDataModel(objectList);
		return dataModel;
	}
	
	public DataModel<I> getDataModel(List<I> objectList) {
		@SuppressWarnings("unchecked") DataModel<I> dataModel = new ListTableModel<I>(objectList);
		return dataModel;
	}

	public List<I> getObjectList(Collection<R> recordList) {
		//Assert.notNull(recordList, "User records should not be null");
		List<I> list = new ArrayList<I>();
		if (recordList != null) {
			for (R record : recordList) {
				I listObject = createRowObject(record);
				list.add(listObject);
			}
		}
		return list;
	}
	
	protected I createRowObject(R record) {
		return null;
	}
	
	public void setContext(String instanceName) {
		setActionEvent(instanceName + "Selected");
		setCancelEvent(instanceName + "Cancelled");
	}
	
	public String submit() {
		List<R> selectedRecords = getSelectedRecordList();
		populateItems(selectedRecords);
		
		if (actionEvent != null)
			raiseEvent(actionEvent);
		setValidated(true);
		return null;
	}


	//@Override
	public boolean validate(R record) {
		//true by default - selection only
		return true;
	}
	
	@Override
	public String cancel() {
		selectedItems1 = null;
		selectedItems2 = null;
		return super.cancel();
	}
	
}
