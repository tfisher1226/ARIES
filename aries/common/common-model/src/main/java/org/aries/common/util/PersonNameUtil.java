package org.aries.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.common.PersonName;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class PersonNameUtil extends BaseUtil {

	public static boolean isEmpty(PersonName personName) {
		if (personName == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(personName.getLastName());
		status |= StringUtils.isEmpty(personName.getFirstName());
		return status;
	}
	
	public static boolean isEmpty(Collection<PersonName> personNameList) {
		if (personNameList == null  || personNameList.size() == 0)
			return true;
		Iterator<PersonName> iterator = personNameList.iterator();
		while (iterator.hasNext()) {
			PersonName personName = iterator.next();
			if (!isEmpty(personName))
				return false;
		}
		return true;
	}
	
	public static String toString(PersonName personName) {
		if (personName == null)
			return "";
		if (isEmpty(personName))
			return "";
		String lastName = personName.getLastName();
		String firstName = personName.getFirstName();
		String middleInitial = personName.getMiddleInitial();
		String fullName = lastName +", "+firstName;
		if (middleInitial != null && !middleInitial.isEmpty())
			fullName += ", "+ middleInitial;
		return fullName;
	}
	
	public static String toString(Collection<PersonName> personNameList) {
		if (isEmpty(personNameList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<PersonName> iterator = personNameList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			PersonName personName = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(personName);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}

	public static String toPersonNameString(PersonName personName) {
		if (personName != null && !isEmpty(personName))
			return personName.getFirstName() + " " + personName.getLastName();
		return "";
	}
	
	public static PersonName create() {
		PersonName personName = new PersonName();
		initialize(personName);
		return personName;
	}
	
	public static void initialize(PersonName personName) {
		//nothing for now
	}
	
	public static boolean validate(PersonName personName) {
		if (personName == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(personName.getFirstName(), "\"FirstName\" must be specified");
		validator.notEmpty(personName.getLastName(), "\"LastName\" must be specified");
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<PersonName> personNameList) {
		Validator validator = Validator.getValidator();
		Iterator<PersonName> iterator = personNameList.iterator();
		while (iterator.hasNext()) {
			PersonName personName = iterator.next();
			//TODO break or accumulate?
			validate(personName);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<PersonName> personNameList) {
		Collections.sort(personNameList, createPersonNameComparator());
	}
	
	public static Collection<PersonName> sortRecords(Collection<PersonName> personNameCollection) {
		List<PersonName> list = new ArrayList<PersonName>(personNameCollection);
		Collections.sort(list, createPersonNameComparator());
		return list;
	}
	
	public static Comparator<PersonName> createPersonNameComparator() {
		return new Comparator<PersonName>() {
			public int compare(PersonName personName1, PersonName personName2) {
				int status = personName1.compareTo(personName2);
				return status;
			}
		};
	}
	
	public static PersonName clone(PersonName personName) {
		if (personName == null)
			return null;
		PersonName clone = create();
		clone.setId(ObjectUtil.clone(personName.getId()));
		clone.setLastName(ObjectUtil.clone(personName.getLastName()));
		clone.setFirstName(ObjectUtil.clone(personName.getFirstName()));
		clone.setMiddleInitial(ObjectUtil.clone(personName.getMiddleInitial()));
		return clone;
	}

	public static PersonName convert(String value) {
		PersonName personName = new PersonName();
		int indexOf = value.indexOf(",");
		if (indexOf == -1)
			return null;
		String firstName = value.substring(0, indexOf);
		String lastName = value.substring(indexOf+1);
		String middleInitial = null;
		if (lastName.contains(",")) {
			indexOf = lastName.indexOf(",");
			lastName = value.substring(0, indexOf);
			String middleName = value.substring(indexOf+1);
			if (!middleName.isEmpty()) {
				middleInitial = Character.toString(middleName.charAt(0));
			}
		}
		personName.setFirstName(firstName);
		personName.setLastName(lastName);
		personName.setMiddleInitial(middleInitial);
		return personName;
	}

}
