package nam.model.property;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.Property;
import nam.model.util.PropertyUtil;


@SessionScoped
@Named("propertySelectManager")
public class PropertySelectManager extends AbstractSelectManager<Property, PropertyListObject> implements Serializable {
	
	@Inject
	private PropertyDataManager propertyDataManager;
	
	@Inject
	private PropertyHelper propertyHelper;
	
	
	@Override
	public String getClientId() {
		return "propertySelect";
	}
	
	@Override
	public String getTitle() {
		return "Property Selection";
	}
	
	@Override
	protected Class<Property> getRecordClass() {
		return Property.class;
	}
	
	@Override
	public boolean isEmpty(Property property) {
		return propertyHelper.isEmpty(property);
	}
	
	@Override
	public String toString(Property property) {
		return propertyHelper.toString(property);
	}
	
	protected PropertyHelper getPropertyHelper() {
		return BeanContext.getFromSession("propertyHelper");
	}
	
	protected PropertyListManager getPropertyListManager() {
		return BeanContext.getFromSession("propertyListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshPropertyList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Property> recordList) {
		PropertyListManager propertyListManager = getPropertyListManager();
		DataModel<PropertyListObject> dataModel = propertyListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshPropertyList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Property> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Property> propertyList = BeanContext.getFromConversation(instanceId);
		return propertyList;
	}
	
	@Override
	public void activate() {
		initialize();
		super.show();
	}
	
	@Override
	public String submit() {
		setModule(targetField);
		return super.submit();
	}
	
	@Override
	public void sortRecords() {
		sortRecords(recordList);
	}
	
	@Override
	public void sortRecords(List<Property> propertyList) {
		Collections.sort(propertyList, new Comparator<Property>() {
			public int compare(Property property1, Property property2) {
				String text1 = PropertyUtil.toString(property1);
				String text2 = PropertyUtil.toString(property2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
