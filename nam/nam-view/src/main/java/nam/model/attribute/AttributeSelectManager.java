package nam.model.attribute;

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

import nam.model.Attribute;
import nam.model.util.AttributeUtil;


@SessionScoped
@Named("attributeSelectManager")
public class AttributeSelectManager extends AbstractSelectManager<Attribute, AttributeListObject> implements Serializable {
	
	@Inject
	private AttributeDataManager attributeDataManager;
	
	@Inject
	private AttributeHelper attributeHelper;
	
	
	@Override
	public String getClientId() {
		return "attributeSelect";
	}
	
	@Override
	public String getTitle() {
		return "Attribute Selection";
	}
	
	@Override
	protected Class<Attribute> getRecordClass() {
		return Attribute.class;
	}
	
	@Override
	public boolean isEmpty(Attribute attribute) {
		return attributeHelper.isEmpty(attribute);
	}
	
	@Override
	public String toString(Attribute attribute) {
		return attributeHelper.toString(attribute);
	}
	
	protected AttributeHelper getAttributeHelper() {
		return BeanContext.getFromSession("attributeHelper");
	}
	
	protected AttributeListManager getAttributeListManager() {
		return BeanContext.getFromSession("attributeListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshAttributeList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Attribute> recordList) {
		AttributeListManager attributeListManager = getAttributeListManager();
		DataModel<AttributeListObject> dataModel = attributeListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshAttributeList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Attribute> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Attribute> attributeList = BeanContext.getFromConversation(instanceId);
		return attributeList;
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
	public void sortRecords(List<Attribute> attributeList) {
		Collections.sort(attributeList, new Comparator<Attribute>() {
			public int compare(Attribute attribute1, Attribute attribute2) {
				String text1 = AttributeUtil.toString(attribute1);
				String text2 = AttributeUtil.toString(attribute2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
