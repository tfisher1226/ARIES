package admin.ui.preferences;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import admin.Preferences;
import admin.util.PreferencesUtil;


public class PreferencesListObject extends AbstractListObject<Preferences> implements Comparable<PreferencesListObject>, Serializable {
	
	private Preferences preferences;

	
	public PreferencesListObject(Preferences preferences) {
		this.preferences = preferences;
	}

	
	public Preferences getPreferences() {
		return preferences;
	}

	@Override
	public String getLabel() {
		return toString(preferences);
	}
	
	@Override
	public String toString() {
		return toString(preferences);
	}
	
	@Override
	public String toString(Preferences preferences) {
		return PreferencesUtil.toString(preferences);
	}
	
	@Override
	public int compareTo(PreferencesListObject other) {
		String thisText = toString(this.preferences);
		String otherText = toString(other.preferences);
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		PreferencesListObject other = (PreferencesListObject) object;
		Long thisId = this.preferences.getId();
		Long otherId = other.preferences.getId();
		if (thisId == null)
			return false;
		if (otherId == null)
			return false;
		return thisId.equals(otherId);
	}

}
