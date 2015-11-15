package org.aries.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.common.Language;
import org.aries.util.BaseUtil;


public class LanguageUtil extends BaseUtil {
	
	public static final Language[] VALUES_ARRAY = new Language[] {
		Language.ENGLISH,
		Language.SPANISH,
		Language.FRENCH,
		Language.ITALIAN,
		Language.GERMAN,
		Language.PORTUGUESE,
		Language.JAPANESE,
		Language.CHINESE,
		Language.KOREAN,
		Language.VIETNAMESE,
		Language.THAI,
		Language.OTHER
	};
	
	public static final List<Language> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static Language getLanguage(int ordinal) {
		if (ordinal == Language.ENGLISH.ordinal())
			return Language.ENGLISH;
		if (ordinal == Language.SPANISH.ordinal())
			return Language.SPANISH;
		if (ordinal == Language.FRENCH.ordinal())
			return Language.FRENCH;
		if (ordinal == Language.ITALIAN.ordinal())
			return Language.ITALIAN;
		if (ordinal == Language.GERMAN.ordinal())
			return Language.GERMAN;
		if (ordinal == Language.PORTUGUESE.ordinal())
			return Language.PORTUGUESE;
		if (ordinal == Language.JAPANESE.ordinal())
			return Language.JAPANESE;
		if (ordinal == Language.CHINESE.ordinal())
			return Language.CHINESE;
		if (ordinal == Language.KOREAN.ordinal())
			return Language.KOREAN;
		if (ordinal == Language.VIETNAMESE.ordinal())
			return Language.VIETNAMESE;
		if (ordinal == Language.THAI.ordinal())
			return Language.THAI;
		if (ordinal == Language.OTHER.ordinal())
			return Language.OTHER;
		return null;
	}
	
	public static String toString(Language language) {
		return language.name();
	}
	
	public static String toString(Collection<Language> languageList) {
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
				String text1 = language1.value();
				String text2 = language2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
