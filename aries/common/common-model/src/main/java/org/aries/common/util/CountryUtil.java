package org.aries.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.common.Country;
import org.aries.util.BaseUtil;


public class CountryUtil extends BaseUtil {
	
	public static final Country[] VALUES_ARRAY = new Country[] {
		Country.USA,
		Country.CAN,
		Country.MEX,
		Country.PR
	};
	
	public static final List<Country> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static Country getCountry(int ordinal) {
		if (ordinal == Country.USA.ordinal())
			return Country.USA;
		if (ordinal == Country.CAN.ordinal())
			return Country.CAN;
		if (ordinal == Country.MEX.ordinal())
			return Country.MEX;
		if (ordinal == Country.PR.ordinal())
			return Country.PR;
		return null;
	}
	
	public static String toString(Country country) {
		return country.name();
	}
	
	public static String toString(Collection<Country> countryList) {
		StringBuffer buf = new StringBuffer();
		Iterator<Country> iterator = countryList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Country country = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(country);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<Country> countryList) {
		Collections.sort(countryList, createCountryComparator());
	}
	
	public static Collection<Country> sortRecords(Collection<Country> countryCollection) {
		List<Country> list = new ArrayList<Country>(countryCollection);
		Collections.sort(list, createCountryComparator());
		return list;
	}
	
	public static Comparator<Country> createCountryComparator() {
		return new Comparator<Country>() {
			public int compare(Country country1, Country country2) {
				String text1 = country1.value();
				String text2 = country2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
