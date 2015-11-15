package admin.ui.preferences;

import java.io.Serializable;
import java.util.Collection;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractTabListManager;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Role;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.Preferences;
import admin.util.PreferencesUtil;


@Startup
@AutoCreate
@BypassInterceptors
@Name("preferencesListManager")
@Scope(ScopeType.SESSION)
@Role(name = "mainPreferencesListManager", scope = ScopeType.SESSION)
public class PreferencesListManager extends AbstractTabListManager<Preferences, PreferencesListObject> implements Serializable {
	
	@Override
	public String getModule() {
		return "PreferencesList";
	}
	
	@Override
	public String getTitle() {
		return "Preferences List";
	}

	@Override
	public Object getRecordId(Preferences preferences) {
		return preferences.getId();
	}

	@Override
	public String getRecordName(Preferences preferences) {
		return PreferencesUtil.toString(preferences);
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
	protected PreferencesListObject createRowObject(Preferences preferences) {
		return new PreferencesListObject(preferences);
	}

	protected PreferencesHelper getPreferencesHelper() {
		return BeanContext.getFromSession("preferencesHelper");
	}

	protected PreferencesInfoManager getPreferencesInfoManager() {
		return BeanContext.getFromSession("preferencesInfoManager");
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

	public void editPreferences() {
		editPreferences(selectedRecordId);
	}

	public void editPreferences(String recordId) {
		editPreferences(Long.parseLong(recordId));
	}
	
	public void editPreferences(Long recordId) {
		Preferences preferences = recordByIdMap.get(recordId);
		editPreferences(preferences);
	}
	
	public void editPreferences(Preferences preferences) {
		PreferencesInfoManager preferencesInfoManager = BeanContext.getFromSession("preferencesInfoManager");
		preferencesInfoManager.editPreferences(preferences);
	}

	@Observer("org.aries.removePreferences")
	public void removePreferences() {
		removePreferences(selectedRecordId);
	}
	
	public void removePreferences(String recordId) {
		removePreferences(Long.parseLong(recordId));
	}
	
	public void removePreferences(Long recordId) {
		Preferences preferences = recordByIdMap.get(recordId);
		removePreferences(preferences);
	}
	
	public void removePreferences(Preferences preferences) {
			try {
				clearSelection();
				refresh();
			
			} catch (Exception e) {
			handleException(e);
		}
	}
	
	@Observer("org.aries.cancelPreferences")
	public void cancelPreferences() {
		if (selectedRecord == null)
			return;
		try {
			BeanContext.removeFromSession("preferences");
			Long id = selectedRecord.getId();
			initialize(recordByIdMap.values());
			
		} catch (Exception e) {
			handleException(e);
			}
		}
	
	public boolean validatePreferences(Collection<Preferences> preferencesList) {
		return PreferencesUtil.validate(preferencesList);
	}
	
//	@Observer("org.aries.exportPreferencesList")
//	public void exportPreferencesList() {
//		String tableId = "pageForm:preferencesListTable";
//		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
//		exportManager.exportToXLS(tableId);
//	}

}
