package org.aries.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.enterprise.event.Observes;
import javax.faces.model.DataModel;

import nam.model.Attribute;
import nam.model.Element;
import nam.model.Field;
import nam.model.Item;
import nam.model.Reference;
import nam.model.util.ElementUtil;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.event.Refreshed;
import org.aries.ui.event.Removed;
import org.aries.ui.event.ResetEvent;
import org.aries.ui.model.ListTableModel;


@SuppressWarnings("serial")
public class FieldListManager extends AbstractRecordListManager<Field> implements Serializable {

	private Element type;
	
	private List<FieldListObject> objectList;

	private DataModel<FieldListObject> recordModel;

	private Map<String, Field> recordMap;

	private Field selectedRecord;

	private String selectedRecordId;

	private String selectedRowIndex;

	private FieldListObject selectedObject;

	
	public FieldListManager() {
	}
	
	protected void setType(Element type) {
		this.type = type;
	}

	//@Override
	protected boolean isEmpty(Field field) {
		return field == null || StringUtils.isEmpty(field.getName());
	}

	@Override
	protected Class<Field> getRecordClass() {
		return Field.class;
	}

	public DataModel<FieldListObject> getFieldList() {
		return recordModel;
	}
	
//	@Override
//	@SuppressWarnings("unchecked")
//	public List<FieldListObject> getList() {
//		return (List<FieldListObject>) recordModel.getWrappedData();
//	}
	
	public void clearSelection() {
		selectedRecord = null;
		selectedRecordId = null;
		selectedRowIndex = null;
		selectedObject = null;
	}
	
	//@Override
	public FieldListObject getSelection() {
		return selectedObject;
	}

	public String setSelection(FieldListObject rowObject) {
		selectedRecord = rowObject.getField();
		selectedObject = rowObject;
		return null;
	}

	public boolean isSelected() {
		boolean status = selectedObject != null;
		return status;
	}
	
	public String getSelectedRowIndex() {
		return selectedRowIndex;
	}
	
	public void setSelectedRowIndex(String selectedRowIndex) {
		this.selectedRowIndex = selectedRowIndex;
		if (selectedRowIndex != null) {
			int rowIndex = Integer.parseInt(selectedRowIndex);
			selectedObject = objectList.get(rowIndex);
			selectedRecord = selectedObject.getField();
			recordModel.setRowIndex(rowIndex);
		}
	}
	
	public Field getSelectedItem() {
		return selectedRecord;
	}
	
	public void setSelectedRecord(Field selectedItem) {
		this.selectedRecord = selectedItem;
	}
	
	public String getSelectedRecordId() {
		return selectedRecordId;
	}
	
	public void setSelectedRecordId(String selectedRecordId) {
		this.selectedRecordId = selectedRecordId;
	}

	
//	public void initialize(List<AbstractItem> records) {
//		this.records = records;
//		refresh();
//	}

//	public void observeReset(@Observes ResetEvent event) {
//		reset();
//	}
	
	public void reset() {
		recordModel = null;
		refresh();
	}

	public void removeRecord(FieldListObject rowObject) {
		objectList.remove(rowObject);
		refreshModel(objectList);
	}

	public void insertRecord(FieldListObject rowObject) {
		objectList.add(rowObject);
		refreshModel(objectList);
	}

	public void observeRefresh(@Observes @Refreshed List<Field> fieldList) {
		refreshModel();
	}

	public void refreshModel() {
		List<FieldListObject> fieldList = getFields();
		refreshModel(fieldList);
	}

	@SuppressWarnings("unchecked")
	protected void refreshModel(List<FieldListObject> recordList) {
		recordModel = new ListTableModel<FieldListObject>(recordList);
		recordMap = createRecordMapFromFields(recordModel);
		this.objectList = recordList;
		clearSelection();
	}
	
	protected Map<String, Field> createRecordMapFromFields(DataModel<FieldListObject> fieldList) {
		Map<String, Field> map = new HashMap<String, Field>();
		Iterator<FieldListObject> iterator = fieldList.iterator();
		while (iterator.hasNext()) {
			FieldListObject rowObject = iterator.next();
			Field record = rowObject.getField();
			String key = record.getName();
			map.put(key, record);
		}
		return map;
	}

	protected Map<String, Item> createRecordMapFromItems(DataModel<FieldListObject> roleList) {
		Map<String, Item> map = new HashMap<String, Item>();
		Iterator<FieldListObject> iterator = roleList.iterator();
		while (iterator.hasNext()) {
			FieldListObject rowObject = iterator.next();
			Item record = rowObject.getItem();
			String key = record.getName();
			map.put(key, record);
		}
		return map;
	}

	public List<FieldListObject> getFields() {
		try {
			List<FieldListObject> objects = new LinkedList<FieldListObject>(); 
			List<Attribute> attributes = ElementUtil.getAttributes(type);
			List<Reference> references = ElementUtil.getReferences(type);
			//XXX List<AbstractItem> records = roleService.getRoles();
			objects.addAll(packageRecordsFromFields(attributes)); 
			objects.addAll(packageRecordsFromFields(references)); 
			//List<RoleFieldListObject> objects = packageRecordsFromItems(records); 
			//display.info("Retrieved list of all Role records");
			return objects;
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	protected <T extends Field> List<FieldListObject> packageRecordsFromFields(List<T> fields) {
		List<FieldListObject> list = new ArrayList<FieldListObject>();
		//Assert.notNull(records, "Role records should not be null");
		if (fields != null) {
			for (T field : fields) {
				FieldListObject object = new FieldListObject(field, type);
				list.add(object);
			}
		}
		return list;
	}
	
	protected List<FieldListObject> packageRecordsFromItems(List<Item> items) {
		List<FieldListObject> list = new ArrayList<FieldListObject>();
		//Assert.notNull(records, "Role records should not be null");
		if (items != null) {
			for (Item item : items) {
				FieldListObject object = new FieldListObject(item);
				list.add(object);
			}
		}
		return list;
	}

	
	public void editField() {
		editField(selectedRecordId);
	}
	
	public void editField(Long recordId) {
		editField(Long.toString(recordId));
	}
	
	public void editField(String recordId) {
		editField(recordMap.get(recordId));
	}
	
	public void editField(Field record) {
		//XXX roleInfoManager.editRole(record);
	}
	
	public void observeRemove(@Observes @Removed Field event) {
		removeField();
	}

	public void removeField() {
		synchronized (objectList) {
			try {
				display.setModule("Role");
				//XXX roleService.deleteRole(selectedRecord);
				removeRecord(selectedObject);
				clearSelection();
			} catch (Exception e) {
				display.error("Error: "+e.getMessage());
			}
		}
	}

}
