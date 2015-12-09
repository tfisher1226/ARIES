package admin.preferences;

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

import admin.Preferences;
import admin.util.PreferencesUtil;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("preferencesListManager")
public class PreferencesListManager extends AbstractDomainListManager<Preferences, PreferencesListObject> implements Serializable {
	
	@Inject
	private PreferencesDataManager preferencesDataManager;
	
	@Inject
	private PreferencesEventManager preferencesEventManager;
	
	@Inject
	private PreferencesInfoManager preferencesInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "preferencesList";
	}
	
	@Override
	public String getTitle() {
		return "Preferences List";
	}

	public Object getRecordId(Preferences preferences) {
		return preferences.getId();
	}
	
	@Override
	public Object getRecordKey(Preferences preferences) {
		return PreferencesUtil.getKey(preferences);
	}
	
	@Override
	public String getRecordName(Preferences preferences) {
		return PreferencesUtil.getLabel(preferences);
	}

	@Override
	protected Class<Preferences> getRecordClass() {
		return Preferences.class;
	}

	@Override
	protected Preferences getRecord(PreferencesListObject rowObject) {
		return rowObject.getPreferences();
	}
	
	@Override
	public Preferences getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? PreferencesUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Preferences preferences) {
		super.setSelectedRecord(preferences);
		fireSelectedEvent(preferences);
	}

	protected void fireSelectedEvent(Preferences preferences) {
		preferencesEventManager.fireSelectedEvent(preferences);
	}

	public boolean isSelected(Preferences preferences) {
		Preferences selection = selectionContext.getSelection("preferences");
		boolean selected = selection != null && selection.equals(preferences);
		return selected;
	}
	
	public boolean isChecked(Preferences preferences) {
		Collection<Preferences> selection = selectionContext.getSelection("preferencesSelection");
		boolean checked = selection != null && selection.contains(preferences);
		return checked;
	}
	
	@Override
	protected PreferencesListObject createRowObject(Preferences preferences) {
		PreferencesListObject listObject = new PreferencesListObject(preferences);
		listObject.setSelected(isSelected(preferences));
		listObject.setChecked(isChecked(preferences));
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
	protected Collection<Preferences> createRecordList() {
		try {
			Collection<Preferences> preferencesList = preferencesDataManager.getPreferencesList();
			if (preferencesList != null)
				return preferencesList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}

	public String viewPreferences() {
		return viewPreferences(selectedRecordKey);
	}
	
	public String viewPreferences(Object recordKey) {
		Preferences preferences = recordByKeyMap.get(recordKey);
		return viewPreferences(preferences);
	}
	
	public String viewPreferences(Preferences preferences) {
		String url = preferencesInfoManager.viewPreferences(preferences);
		return url;
	}
	
	public String editPreferences() {
		return editPreferences(selectedRecordKey);
	}
	
	public String editPreferences(Object recordKey) {
		Preferences preferences = recordByKeyMap.get(recordKey);
		return editPreferences(preferences);
	}
	
	public String editPreferences(Preferences preferences) {
		String url = preferencesInfoManager.editPreferences(preferences);
		return url;
	}

	public void removePreferences() {
		removePreferences(selectedRecordKey);
	}
	
	public void removePreferences(Object recordKey) {
		Preferences preferences = recordByKeyMap.get(recordKey);
		removePreferences(preferences);
	}
	
	public void removePreferences(Preferences preferences) {
			try {
			if (preferencesDataManager.removePreferences(preferences))
				clearSelection();
				refresh();
			
			} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelPreferences(@Observes @Cancelled Preferences preferences) {
		try {
			//Object key = PreferencesUtil.getKey(preferences);
			//recordByKeyMap.put(key, preferences);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("preferences");
		} catch (Exception e) {
			handleException(e);
			}
		}
	
	public boolean validatePreferences(Collection<Preferences> preferencesList) {
		return PreferencesUtil.validate(preferencesList);
	}
	
	public void exportPreferencesList(@Observes @Export String tableId) {
		//String tableId = "pageForm:preferencesListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}

}
