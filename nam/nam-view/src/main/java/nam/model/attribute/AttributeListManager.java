package nam.model.attribute;

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

import nam.model.Attribute;
import nam.model.util.AttributeUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("attributeListManager")
public class AttributeListManager extends AbstractDomainListManager<Attribute, AttributeListObject> implements Serializable {
	
	@Inject
	private AttributeDataManager attributeDataManager;
	
	@Inject
	private AttributeEventManager attributeEventManager;
	
	@Inject
	private AttributeInfoManager attributeInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "attributeList";
	}
	
	@Override
	public String getTitle() {
		return "Attribute List";
	}
	
	@Override
	public Object getRecordKey(Attribute attribute) {
		return AttributeUtil.getKey(attribute);
	}
	
	@Override
	public String getRecordName(Attribute attribute) {
		return AttributeUtil.getLabel(attribute);
	}
	
	@Override
	protected Class<Attribute> getRecordClass() {
		return Attribute.class;
	}
	
	@Override
	protected Attribute getRecord(AttributeListObject rowObject) {
		return rowObject.getAttribute();
	}
	
	@Override
	public Attribute getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? AttributeUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Attribute attribute) {
		super.setSelectedRecord(attribute);
		fireSelectedEvent(attribute);
	}
	
	protected void fireSelectedEvent(Attribute attribute) {
		attributeEventManager.fireSelectedEvent(attribute);
	}
	
	public boolean isSelected(Attribute attribute) {
		Attribute selection = selectionContext.getSelection("attribute");
		boolean selected = selection != null && selection.equals(attribute);
		return selected;
	}
	
	@Override
	protected AttributeListObject createRowObject(Attribute attribute) {
		AttributeListObject listObject = new AttributeListObject(attribute);
		listObject.setSelected(isSelected(attribute));
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
	protected Collection<Attribute> createRecordList() {
		try {
			Collection<Attribute> attributeList = attributeDataManager.getAttributeList();
			if (attributeList != null)
				return attributeList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewAttribute() {
		return viewAttribute(selectedRecordKey);
	}
	
	public String viewAttribute(Object recordKey) {
		Attribute attribute = recordByKeyMap.get(recordKey);
		return viewAttribute(attribute);
	}
	
	public String viewAttribute(Attribute attribute) {
		String url = attributeInfoManager.viewAttribute(attribute);
		return url;
	}
	
	public String editAttribute() {
		return editAttribute(selectedRecordKey);
	}
	
	public String editAttribute(Object recordKey) {
		Attribute attribute = recordByKeyMap.get(recordKey);
		return editAttribute(attribute);
	}
	
	public String editAttribute(Attribute attribute) {
		String url = attributeInfoManager.editAttribute(attribute);
		return url;
	}
	
	public void removeAttribute() {
		removeAttribute(selectedRecordKey);
	}
	
	public void removeAttribute(Object recordKey) {
		Attribute attribute = recordByKeyMap.get(recordKey);
		removeAttribute(attribute);
	}
	
	public void removeAttribute(Attribute attribute) {
		try {
			if (attributeDataManager.removeAttribute(attribute))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelAttribute(@Observes @Cancelled Attribute attribute) {
		try {
			//Object key = AttributeUtil.getKey(attribute);
			//recordByKeyMap.put(key, attribute);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("attribute");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateAttribute(Collection<Attribute> attributeList) {
		return AttributeUtil.validate(attributeList);
	}
	
	public void exportAttributeList(@Observes @Export String tableId) {
		//String tableId = "pageForm:attributeListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
