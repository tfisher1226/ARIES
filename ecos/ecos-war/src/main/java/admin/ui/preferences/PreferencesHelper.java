package admin.ui.preferences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.model.DataModel;

import org.aries.ui.manager.AbstractElementHelper;
import org.aries.ui.model.ListTableModel;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.Preferences;
import admin.util.PreferencesUtil;


@Startup
@AutoCreate
@BypassInterceptors
@Name("preferencesHelper")
@Scope(ScopeType.SESSION)
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
		return getOpenNodes(preferences.getOpenNodes());
	}
	
	public DataModel<Boolean> getOpenNodes(Map<String, Boolean> openNodesMap) {
		List<Boolean> values = new ArrayList<Boolean>(openNodesMap.values());
		@SuppressWarnings("unchecked") DataModel<Boolean> dataModel = new ListTableModel<Boolean>(values);
		return dataModel;
	}
	
}
