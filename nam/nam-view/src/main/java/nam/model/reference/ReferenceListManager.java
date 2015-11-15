package nam.model.reference;

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

import nam.model.Reference;
import nam.model.util.ReferenceUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("referenceListManager")
public class ReferenceListManager extends AbstractDomainListManager<Reference, ReferenceListObject> implements Serializable {
	
	@Inject
	private ReferenceDataManager referenceDataManager;
	
	@Inject
	private ReferenceEventManager referenceEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "referenceList";
	}
	
	@Override
	public String getTitle() {
		return "Reference List";
	}
	
	@Override
	public Object getRecordKey(Reference reference) {
		return ReferenceUtil.getKey(reference);
	}
	
	@Override
	public String getRecordName(Reference reference) {
		return ReferenceUtil.toString(reference);
	}
	
	@Override
	protected Class<Reference> getRecordClass() {
		return Reference.class;
	}
	
	@Override
	protected Reference getRecord(ReferenceListObject rowObject) {
		return rowObject.getReference();
	}
	
	@Override
	public Reference getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? ReferenceUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Reference reference) {
		super.setSelectedRecord(reference);
		fireSelectedEvent(reference);
	}
	
	protected void fireSelectedEvent(Reference reference) {
		referenceEventManager.fireSelectedEvent(reference);
	}
	
	public boolean isSelected(Reference reference) {
		Reference selection = selectionContext.getSelection("reference");
		boolean selected = selection != null && selection.equals(reference);
		return selected;
	}
	
	@Override
	protected ReferenceListObject createRowObject(Reference reference) {
		ReferenceListObject listObject = new ReferenceListObject(reference);
		listObject.setSelected(isSelected(reference));
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
	
	public void handleRefresh(@Observes @Refresh Object object) {
		//refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Reference> createRecordList() {
		try {
			Collection<Reference> referenceList = referenceDataManager.getReferenceList();
			if (referenceList != null)
				return referenceList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewReference() {
		return viewReference(selectedRecordKey);
	}
	
	public String viewReference(Object recordKey) {
		Reference reference = recordByKeyMap.get(recordKey);
		return viewReference(reference);
	}
	
	public String viewReference(Reference reference) {
		ReferenceInfoManager referenceInfoManager = BeanContext.getFromSession("referenceInfoManager");
		String url = referenceInfoManager.viewReference(reference);
		return url;
	}
	
	public String editReference() {
		return editReference(selectedRecordKey);
	}
	
	public String editReference(Object recordKey) {
		Reference reference = recordByKeyMap.get(recordKey);
		return editReference(reference);
	}
	
	public String editReference(Reference reference) {
		ReferenceInfoManager referenceInfoManager = BeanContext.getFromSession("referenceInfoManager");
		String url = referenceInfoManager.editReference(reference);
		return url;
	}
	
	public void removeReference() {
		removeReference(selectedRecordKey);
	}
	
	public void removeReference(Object recordKey) {
		Reference reference = recordByKeyMap.get(recordKey);
		removeReference(reference);
	}
	
	public void removeReference(Reference reference) {
		try {
			if (referenceDataManager.removeReference(reference))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelReference(@Observes @Cancelled Reference reference) {
		try {
			//Object key = ReferenceUtil.getKey(reference);
			//recordByKeyMap.put(key, reference);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("reference");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateReference(Collection<Reference> referenceList) {
		return ReferenceUtil.validate(referenceList);
	}
	
	public void exportReferenceList(@Observes @Export String tableId) {
		//String tableId = "pageForm:referenceListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
