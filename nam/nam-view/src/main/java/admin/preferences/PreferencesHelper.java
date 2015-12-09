package admin.preferences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;
import org.aries.ui.model.ListTableModel;

import admin.Preferences;
import admin.util.PreferencesUtil;


@SessionScoped
@Named("preferencesHelper")
public class PreferencesHelper extends AbstractElementHelper<Preferences> implements Serializable {

	@Override
	public boolean isEmpty(Preferences preferences) {
		return PreferencesUtil.isEmpty(preferences);
	}

	@Override
	public String toString(Preferences preferences) {
		return PreferencesUtil.toString(preferences);
	}
	
	@Override
	public String toString(Collection<Preferences> preferencesList) {
		return PreferencesUtil.toString(preferencesList);
	}
	
	@Override
	public boolean validate(Preferences preferences) {
		return PreferencesUtil.validate(preferences);
	}
	
	@Override
	public boolean validate(Collection<Preferences> preferencesList) {
		return PreferencesUtil.validate(preferencesList);
	}
	
	public DataModel<Boolean> getOpenNodes(Preferences preferences) {
		if (preferences == null)
			return null;
		preferences.setOpenNodes(preferences.getOpenNodes());
		return getOpenNodes(preferences.getOpenNodes());
	}

	public DataModel<Boolean> getOpenNodes(Map<String, Boolean> openNodesMap) {
		List<Boolean> values = new ArrayList<Boolean>(openNodesMap.values());
		@SuppressWarnings("unchecked") DataModel<Boolean> dataModel = new ListTableModel<Boolean>(values);
		return dataModel;
	}

	public DataModel<String> getWorkState(Preferences preferences) {
		if (preferences == null)
			return null;
		return getWorkState(preferences.getWorkState());
	}
	
	public DataModel<String> getWorkState(Map<String, String> workStateMap) {
		List<String> values = new ArrayList<String>(workStateMap.values());
		@SuppressWarnings("unchecked") DataModel<String> dataModel = new ListTableModel<String>(values);
		return dataModel;
	}
	
	
//	public DataModel<Boolean> getOpenNodes(Map openNodesMap) {
//		List<MapEntry> entries = openNodesMap.getEntries();
//		Boolean[] array = (Boolean[]) entries.toArray();
//		List<Boolean> values = Arrays.asList(array);
//		@SuppressWarnings("unchecked") DataModel<Boolean> dataModel = new ListTableModel<Boolean>(values);
//		return dataModel;
//	}
	
}
