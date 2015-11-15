package nam.model.enumeration;

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

import nam.model.Enumeration;
import nam.model.util.EnumerationUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("enumerationListManager")
public class EnumerationListManager extends AbstractDomainListManager<Enumeration, EnumerationListObject> implements Serializable {
	
	@Inject
	private EnumerationDataManager enumerationDataManager;
	
	@Inject
	private EnumerationEventManager enumerationEventManager;
	
	@Inject
	private EnumerationInfoManager enumerationInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "enumerationList";
	}
	
	@Override
	public String getTitle() {
		return "Enumeration List";
	}
	
	@Override
	public Object getRecordKey(Enumeration enumeration) {
		return EnumerationUtil.getKey(enumeration);
	}
	
	@Override
	public String getRecordName(Enumeration enumeration) {
		return EnumerationUtil.getLabel(enumeration);
	}
	
	@Override
	protected Class<Enumeration> getRecordClass() {
		return Enumeration.class;
	}
	
	@Override
	protected Enumeration getRecord(EnumerationListObject rowObject) {
		return rowObject.getEnumeration();
	}
	
	@Override
	public Enumeration getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? EnumerationUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Enumeration enumeration) {
		super.setSelectedRecord(enumeration);
		fireSelectedEvent(enumeration);
	}
	
	protected void fireSelectedEvent(Enumeration enumeration) {
		enumerationEventManager.fireSelectedEvent(enumeration);
	}
	
	public boolean isSelected(Enumeration enumeration) {
		Enumeration selection = selectionContext.getSelection("enumeration");
		boolean selected = selection != null && selection.equals(enumeration);
		return selected;
	}
	
	@Override
	protected EnumerationListObject createRowObject(Enumeration enumeration) {
		EnumerationListObject listObject = new EnumerationListObject(enumeration);
		listObject.setSelected(isSelected(enumeration));
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
	protected Collection<Enumeration> createRecordList() {
		try {
			Collection<Enumeration> enumerationList = enumerationDataManager.getEnumerationList();
			if (enumerationList != null)
				return enumerationList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewEnumeration() {
		return viewEnumeration(selectedRecordKey);
	}
	
	public String viewEnumeration(Object recordKey) {
		Enumeration enumeration = recordByKeyMap.get(recordKey);
		return viewEnumeration(enumeration);
	}
	
	public String viewEnumeration(Enumeration enumeration) {
		String url = enumerationInfoManager.viewEnumeration(enumeration);
		return url;
	}
	
	public String editEnumeration() {
		return editEnumeration(selectedRecordKey);
	}
	
	public String editEnumeration(Object recordKey) {
		Enumeration enumeration = recordByKeyMap.get(recordKey);
		return editEnumeration(enumeration);
	}
	
	public String editEnumeration(Enumeration enumeration) {
		String url = enumerationInfoManager.editEnumeration(enumeration);
		return url;
	}
	
	public void removeEnumeration() {
		removeEnumeration(selectedRecordKey);
	}
	
	public void removeEnumeration(Object recordKey) {
		Enumeration enumeration = recordByKeyMap.get(recordKey);
		removeEnumeration(enumeration);
	}
	
	public void removeEnumeration(Enumeration enumeration) {
		try {
			if (enumerationDataManager.removeEnumeration(enumeration))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelEnumeration(@Observes @Cancelled Enumeration enumeration) {
		try {
			//Object key = EnumerationUtil.getKey(enumeration);
			//recordByKeyMap.put(key, enumeration);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("enumeration");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateEnumeration(Collection<Enumeration> enumerationList) {
		return EnumerationUtil.validate(enumerationList);
	}
	
	public void exportEnumerationList(@Observes @Export String tableId) {
		//String tableId = "pageForm:enumerationListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
