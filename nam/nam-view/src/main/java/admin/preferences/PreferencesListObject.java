package admin.preferences;

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
	public Object getKey() {
		return getKey(preferences);
	}
	
	public Object getKey(Preferences preferences) {
		return PreferencesUtil.getKey(preferences);
	}
	
	@Override
	public String getLabel() {
		return getLabel(preferences);
	}
	
	public String getLabel(Preferences preferences) {
		return PreferencesUtil.getLabel(preferences);
	}
	
	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
	}
	
	@Override
	public String getIcon() {
		return "/icons/nam/Preferences16.gif";
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
		Object thisKey = getKey(this.preferences);
		Object otherKey = getKey(other.preferences);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		PreferencesListObject other = (PreferencesListObject) object;
		Object thisKey = PreferencesUtil.getKey(this.preferences);
		Object otherKey = PreferencesUtil.getKey(other.preferences);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}

}
