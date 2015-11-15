package nam.model.field;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Field;
import nam.model.util.FieldUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractTabListManager;
import org.aries.ui.manager.ExportManager;


@SessionScoped
@Named("fieldListManager")
public class FieldListManager extends AbstractTabListManager<Field, FieldListObject> implements Serializable {

	@Override
	public String getModule() {
		return "FieldList";
	}

	@Override
	public String getTitle() {
		return "Field List";
	}

	@Override
	public String getRecordName(Field field) {
		return FieldUtil.toString(field);
	}

	@Override
	protected Class<Field> getRecordClass() {
		return Field.class;
	}
	
	@Override
	protected Field getRecord(FieldListObject rowObject) {
		return rowObject.getField();
	}

	@Override
	protected FieldListObject createRowObject(Field field) {
		return new FieldListObject(field);
	}

	protected FieldHelper getFieldHelper() {
		return BeanContext.getFromSession("fieldHelper");
	}

	protected FieldInfoManager getFieldInfoManager() {
		return BeanContext.getFromSession("fieldInfoManager");
	}

	@Override
	public void reset() {
		refresh();
	}

	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
	}

	@Override
	public void refreshModel() {
		refreshModel(recordList);
	}

	public void editField() {
		editField(selectedRecordId);
	}
	
	public void editField(String recordId) {
		editField(Long.parseLong(recordId));
	}

	public void editField(Long recordId) {
		Field field = recordByIdMap.get(recordId);
		editField(field);
		}
	
	public void editField(Field field) {
		FieldInfoManager fieldInfoManager = BeanContext.getFromSession("fieldInfoManager");
		fieldInfoManager.editField(field);
	}

	public void removeField() {
		removeField(selectedRecordId);
	}

	public void removeField(String recordId) {
		removeField(Long.parseLong(recordId));
	}

	public void removeField(Long recordId) {
		Field field = recordByIdMap.get(recordId);
		removeField(field);
	}

	public void removeField(Field field) {
		try {
			clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}

	public boolean validateField(Collection<Field> fieldList) {
		return FieldUtil.validate(fieldList);
	}
	
	public void exportFieldList() {
		String tableId = "pageForm:fieldListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}

}
