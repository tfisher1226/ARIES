package nam.model.property;

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

import nam.model.Property;
import nam.model.util.PropertyUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("propertyListManager")
public class PropertyListManager extends AbstractDomainListManager<Property, PropertyListObject> implements Serializable {
	
	@Inject
	private PropertyDataManager propertyDataManager;
	
	@Inject
	private PropertyEventManager propertyEventManager;
	
	@Inject
	private PropertyInfoManager propertyInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "propertyList";
	}
	
	@Override
	public String getTitle() {
		return "Property List";
	}
	
	@Override
	public Object getRecordKey(Property property) {
		return PropertyUtil.getKey(property);
	}
	
	@Override
	public String getRecordName(Property property) {
		return PropertyUtil.getLabel(property);
	}
	
	@Override
	protected Class<Property> getRecordClass() {
		return Property.class;
	}
	
	@Override
	protected Property getRecord(PropertyListObject rowObject) {
		return rowObject.getProperty();
	}
	
	@Override
	public Property getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? PropertyUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Property property) {
		super.setSelectedRecord(property);
		fireSelectedEvent(property);
	}
	
	protected void fireSelectedEvent(Property property) {
		propertyEventManager.fireSelectedEvent(property);
	}
	
	public boolean isSelected(Property property) {
		Property selection = selectionContext.getSelection("property");
		boolean selected = selection != null && selection.equals(property);
		return selected;
	}
	
	@Override
	protected PropertyListObject createRowObject(Property property) {
		PropertyListObject listObject = new PropertyListObject(property);
		listObject.setSelected(isSelected(property));
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
	protected Collection<Property> createRecordList() {
		try {
			Collection<Property> propertyList = propertyDataManager.getPropertyList();
			if (propertyList != null)
				return propertyList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewProperty() {
		return viewProperty(selectedRecordKey);
	}
	
	public String viewProperty(Object recordKey) {
		Property property = recordByKeyMap.get(recordKey);
		return viewProperty(property);
	}
	
	public String viewProperty(Property property) {
		String url = propertyInfoManager.viewProperty(property);
		return url;
	}
	
	public String editProperty() {
		return editProperty(selectedRecordKey);
	}
	
	public String editProperty(Object recordKey) {
		Property property = recordByKeyMap.get(recordKey);
		return editProperty(property);
	}
	
	public String editProperty(Property property) {
		String url = propertyInfoManager.editProperty(property);
		return url;
	}
	
	public void removeProperty() {
		removeProperty(selectedRecordKey);
	}
	
	public void removeProperty(Object recordKey) {
		Property property = recordByKeyMap.get(recordKey);
		removeProperty(property);
	}
	
	public void removeProperty(Property property) {
		try {
			if (propertyDataManager.removeProperty(property))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelProperty(@Observes @Cancelled Property property) {
		try {
			//Object key = PropertyUtil.getKey(property);
			//recordByKeyMap.put(key, property);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("property");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateProperty(Collection<Property> propertyList) {
		return PropertyUtil.validate(propertyList);
	}
	
	public void exportPropertyList(@Observes @Export String tableId) {
		//String tableId = "pageForm:propertyListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
