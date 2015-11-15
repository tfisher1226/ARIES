package nam.model.language;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Language;
import nam.model.util.LanguageUtil;


public class LanguageListObject extends AbstractListObject<Language> implements Comparable<LanguageListObject>, Serializable {
	
	private Language language;
	
	
	public LanguageListObject(Language language) {
		this.language = language;
	}
	
	
	public Language getLanguage() {
		return language;
	}
	
	@Override
	public Object getKey() {
		return getKey(language);
	}
	
	public Object getKey(Language language) {
		return LanguageUtil.getKey(language);
	}
	
	@Override
	public String getLabel() {
		return getLabel(language);
	}
	
	public String getLabel(Language language) {
		return LanguageUtil.getLabel(language);
	}
	
	@Override
	public String toString() {
		return toString(language);
	}
	
	@Override
	public String toString(Language language) {
		return LanguageUtil.toString(language);
	}
	
	@Override
	public int compareTo(LanguageListObject other) {
		Object thisKey = getKey(this.language);
		Object otherKey = getKey(other.language);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		LanguageListObject other = (LanguageListObject) object;
		Object thisKey = LanguageUtil.getKey(this.language);
		Object otherKey = LanguageUtil.getKey(other.language);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
