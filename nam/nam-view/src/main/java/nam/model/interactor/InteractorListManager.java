package nam.model.interactor;

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

import nam.model.Interactor;
import nam.model.util.InteractorUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("interactorListManager")
public class InteractorListManager extends AbstractDomainListManager<Interactor, InteractorListObject> implements Serializable {
	
	@Inject
	private InteractorDataManager interactorDataManager;
	
	@Inject
	private InteractorEventManager interactorEventManager;
	
	@Inject
	private InteractorInfoManager interactorInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "interactorList";
	}
	
	@Override
	public String getTitle() {
		return "Interactor List";
	}
	
	@Override
	public Object getRecordKey(Interactor interactor) {
		return InteractorUtil.getKey(interactor);
	}
	
	@Override
	public String getRecordName(Interactor interactor) {
		return InteractorUtil.getLabel(interactor);
	}
	
	@Override
	protected Class<Interactor> getRecordClass() {
		return Interactor.class;
	}
	
	@Override
	protected Interactor getRecord(InteractorListObject rowObject) {
		return rowObject.getInteractor();
	}
	
	@Override
	public Interactor getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? InteractorUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Interactor interactor) {
		super.setSelectedRecord(interactor);
		fireSelectedEvent(interactor);
	}
	
	protected void fireSelectedEvent(Interactor interactor) {
		interactorEventManager.fireSelectedEvent(interactor);
	}
	
	public boolean isSelected(Interactor interactor) {
		Interactor selection = selectionContext.getSelection("interactor");
		boolean selected = selection != null && selection.equals(interactor);
		return selected;
	}
	
	@Override
	protected InteractorListObject createRowObject(Interactor interactor) {
		InteractorListObject listObject = new InteractorListObject(interactor);
		listObject.setSelected(isSelected(interactor));
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
	protected Collection<Interactor> createRecordList() {
		try {
			Collection<Interactor> interactorList = interactorDataManager.getInteractorList();
			if (interactorList != null)
				return interactorList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewInteractor() {
		return viewInteractor(selectedRecordKey);
	}
	
	public String viewInteractor(Object recordKey) {
		Interactor interactor = recordByKeyMap.get(recordKey);
		return viewInteractor(interactor);
	}
	
	public String viewInteractor(Interactor interactor) {
		String url = interactorInfoManager.viewInteractor(interactor);
		return url;
	}
	
	public String editInteractor() {
		return editInteractor(selectedRecordKey);
	}
	
	public String editInteractor(Object recordKey) {
		Interactor interactor = recordByKeyMap.get(recordKey);
		return editInteractor(interactor);
	}
	
	public String editInteractor(Interactor interactor) {
		String url = interactorInfoManager.editInteractor(interactor);
		return url;
	}
	
	public void removeInteractor() {
		removeInteractor(selectedRecordKey);
	}
	
	public void removeInteractor(Object recordKey) {
		Interactor interactor = recordByKeyMap.get(recordKey);
		removeInteractor(interactor);
	}
	
	public void removeInteractor(Interactor interactor) {
		try {
			if (interactorDataManager.removeInteractor(interactor))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelInteractor(@Observes @Cancelled Interactor interactor) {
		try {
			//Object key = InteractorUtil.getKey(interactor);
			//recordByKeyMap.put(key, interactor);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("interactor");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateInteractor(Collection<Interactor> interactorList) {
		return InteractorUtil.validate(interactorList);
	}
	
	public void exportInteractorList(@Observes @Export String tableId) {
		//String tableId = "pageForm:interactorListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
