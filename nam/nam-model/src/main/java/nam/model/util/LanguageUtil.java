package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import nam.model.Language;


public class LanguageUtil extends BaseUtil {
	
	public static Object getKey(Language language) {
		return language.getName();
	}
	
	public static String getLabel(Language language) {
		return language.getName();
	}
	
	public static boolean isEmpty(Language language) {
		if (language == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Language> languageList) {
		if (languageList == null  || languageList.size() == 0)
			return true;
		Iterator<Language> iterator = languageList.iterator();
		while (iterator.hasNext()) {
			Language language = iterator.next();
			if (!isEmpty(language))
				return false;
		}
		return true;
	}
	
	public static String toString(Language language) {
		if (isEmpty(language))
			return "Language: [uninitialized] "+language.toString();
		String text = language.toString();
		return text;
	}
	
	public static String toString(Collection<Language> languageList) {
		if (isEmpty(languageList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Language> iterator = languageList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Language language = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(language);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Language create() {
		Language language = new Language();
		initialize(language);
		return language;
	}
	
	public static void initialize(Language language) {
		//nothing for now
	}
	
	public static boolean validate(Language language) {
		if (language == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Language> languageList) {
		Validator validator = Validator.getValidator();
		Iterator<Language> iterator = languageList.iterator();
		while (iterator.hasNext()) {
			Language language = iterator.next();
			//TODO break or accumulate?
			validate(language);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Language> languageList) {
		Collections.sort(languageList, createLanguageComparator());
	}
	
	public static Collection<Language> sortRecords(Collection<Language> languageCollection) {
		List<Language> list = new ArrayList<Language>(languageCollection);
		Collections.sort(list, createLanguageComparator());
		return list;
	}
	
	public static Comparator<Language> createLanguageComparator() {
		return new Comparator<Language>() {
			public int compare(Language language1, Language language2) {
				Object key1 = getKey(language1);
				Object key2 = getKey(language2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Language clone(Language language) {
		if (language == null)
			return null;
		Language clone = create();
		clone.setName(ObjectUtil.clone(language.getName()));
		clone.setCode(ObjectUtil.clone(language.getCode()));
		return clone;
	}
	
	public static List<Language> clone(List<Language> languageList) {
		if (languageList == null)
			return null;
		List<Language> newList = new ArrayList<Language>();
		Iterator<Language> iterator = languageList.iterator();
		while (iterator.hasNext()) {
			Language language = iterator.next();
			Language clone = clone(language);
			newList.add(clone);
		}
		return newList;
	}
	
}
