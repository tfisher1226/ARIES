package admin.ui.preferences;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.faces.model.DataModel;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.Preferences;
import admin.util.PreferencesUtil;


@Startup
@AutoCreate
@BypassInterceptors
@Name("preferencesSelectManager")
@Scope(ScopeType.SESSION)
public class PreferencesSelectManager extends AbstractSelectManager<Preferences, PreferencesListObject> implements Serializable {
	
	@Override
	public String getClientId() {
		return "PreferencesSelect";
	}
	
	@Override
	public String getTitle() {
		return "Preferences Selection";
	}
	
	@Override
	protected Class<Preferences> getRecordClass() {
		return Preferences.class;
	}
	
	@Override
	public boolean isEmpty(Preferences preferences) {
		return getPreferencesHelper().isEmpty(preferences);
	}
	
	@Override
	public String toString(Preferences preferences) {
		return getPreferencesHelper().toString(preferences);
	}
	
	protected PreferencesHelper getPreferencesHelper() {
		return BeanContext.getFromSession("preferencesHelper");
	}
	
	protected PreferencesListManager getPreferencesListManager() {
		return BeanContext.getFromSession("mainPreferencesListManager");
	}
	
	@Override
	//@Observer("org.aries.ui.reset")
	public void initialize() {
		initializeContext(); 
		refreshPreferencesList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Preferences> recordList) {
		PreferencesListManager preferencesListManager = getPreferencesListManager();
		DataModel<PreferencesListObject> dataModel = preferencesListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	@Observer("org.aries.refreshPreferencesList")
	public void refreshPreferencesList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected List<Preferences> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Preferences> preferencesList = BeanContext.getFromConversation(instanceId);
		return preferencesList;
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
	public void sortRecords(List<Preferences> preferencesList) {
		sortRecordsByUser(preferencesList);
	}
	
	public void sortRecordsByUser(List<Preferences> preferencesList) {
		PreferencesUtil.sortRecordsByUser(preferencesList);
	}
	
}
