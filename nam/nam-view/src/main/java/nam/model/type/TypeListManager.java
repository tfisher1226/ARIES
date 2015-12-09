package nam.model.type;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.manager.ExportManager;

import nam.model.Type;
import nam.model.util.TypeUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("typeListManager")
public class TypeListManager extends AbstractDomainListManager<Type, TypeListObject> implements Serializable {
	
	@Inject
	private TypeDataManager typeDataManager;
	
	@Inject
	private TypeEventManager typeEventManager;
	
	@Inject
	private TypeInfoManager typeInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "typeList";
	}
	
	@Override
	public String getTitle() {
		return "Type List";
	}
	
	@Override
	public Object getRecordKey(Type type) {
		return TypeUtil.getKey(type);
	}
	
	@Override
	public String getRecordName(Type type) {
		return TypeUtil.getLabel(type);
	}
	
	@Override
	protected Class<Type> getRecordClass() {
		return Type.class;
	}
	
	@Override
	protected Type getRecord(TypeListObject rowObject) {
		return rowObject.getType();
	}
	
	@Override
	public Type getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? TypeUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Type type) {
		super.setSelectedRecord(type);
		fireSelectedEvent(type);
	}
	
	protected void fireSelectedEvent(Type type) {
		typeEventManager.fireSelectedEvent(type);
	}
	
	public boolean isSelected(Type type) {
		Type selection = selectionContext.getSelection("type");
		boolean selected = selection != null && selection.equals(type);
		return selected;
	}
	
	public boolean isChecked(Type type) {
		Collection<Type> selection = selectionContext.getSelection("typeSelection");
		boolean checked = selection != null && selection.contains(type);
		return checked;
	}
	
	@Override
	protected TypeListObject createRowObject(Type type) {
		TypeListObject listObject = new TypeListObject(type);
		listObject.setSelected(isSelected(type));
		listObject.setChecked(isChecked(type));
		return listObject;
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
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Type> createRecordList() {
		try {
			Collection<Type> typeList = typeDataManager.getTypeList();
			if (typeList != null)
				return typeList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewType() {
		return viewType(selectedRecordKey);
	}
	
	public String viewType(Object recordKey) {
		Type type = recordByKeyMap.get(recordKey);
		return viewType(type);
	}
	
	public String viewType(Type type) {
		String url = typeInfoManager.viewType(type);
		return url;
	}
	
	public String editType() {
		return editType(selectedRecordKey);
	}
	
	public String editType(Object recordKey) {
		Type type = recordByKeyMap.get(recordKey);
		return editType(type);
	}
	
	public String editType(Type type) {
		String url = typeInfoManager.editType(type);
		return url;
	}
	
	public void removeType() {
		removeType(selectedRecordKey);
	}
	
	public void removeType(Object recordKey) {
		Type type = recordByKeyMap.get(recordKey);
		removeType(type);
	}
	
	public void removeType(Type type) {
		try {
			if (typeDataManager.removeType(type))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelType(@Observes @Cancelled Type type) {
		try {
			//Object key = TypeUtil.getKey(type);
			//recordByKeyMap.put(key, type);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("type");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateType(Collection<Type> typeList) {
		return TypeUtil.validate(typeList);
	}
	
	public void exportTypeList(@Observes @Export String tableId) {
		//String tableId = "pageForm:typeListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
