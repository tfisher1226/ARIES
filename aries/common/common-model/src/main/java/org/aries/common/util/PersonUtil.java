package org.aries.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.common.Person;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class PersonUtil extends BaseUtil {
	
	public static boolean isEmpty(Person person) {
		if (person == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(person.getUserId());
		status |= PersonNameUtil.isEmpty(person.getName());
		return status;
	}
	
	public static boolean isEmpty(Collection<Person> personList) {
		if (personList == null  || personList.size() == 0)
			return true;
		Iterator<Person> iterator = personList.iterator();
		while (iterator.hasNext()) {
			Person person = iterator.next();
			if (!isEmpty(person))
				return false;
		}
		return true;
	}
	
	public static String toString(Person person) {
		if (isEmpty(person))
			return "Person: [uninitialized] "+person.toString();
		String text = person.toString();
		return text;
	}
	
	public static String toString(Collection<Person> personList) {
		if (isEmpty(personList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Person> iterator = personList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Person person = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(person);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Person create() {
		Person person = new Person();
		initialize(person);
		return person;
	}
	
	public static void initialize(Person person) {
		//nothing for now
	}
	
	public static boolean validate(Person person) {
		if (person == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.isFalse(PersonNameUtil.isEmpty(person.getName()), "\"Name\" must be specified");
		validator.notEmpty(person.getUserId(), "\"UserId\" must be specified");
		EmailAddressUtil.validate(person.getEmailAddress());
		PersonNameUtil.validate(person.getName());
		PhoneNumberUtil.validate(person.getPhoneNumber());
		StreetAddressUtil.validate(person.getStreetAddress());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Person> personList) {
		Validator validator = Validator.getValidator();
		Iterator<Person> iterator = personList.iterator();
		while (iterator.hasNext()) {
			Person person = iterator.next();
			//TODO break or accumulate?
			validate(person);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Person> personList) {
		Collections.sort(personList, createPersonComparator());
	}
	
	public static Collection<Person> sortRecords(Collection<Person> personCollection) {
		List<Person> list = new ArrayList<Person>(personCollection);
		Collections.sort(list, createPersonComparator());
		return list;
	}
	
	public static Comparator<Person> createPersonComparator() {
		return new Comparator<Person>() {
			public int compare(Person person1, Person person2) {
				int status = person1.compareTo(person2);
				return status;
			}
		};
	}
	
	public static Person clone(Person person) {
		if (person == null)
			return null;
		Person clone = create();
		clone.setId(ObjectUtil.clone(person.getId()));
		clone.setUserId(ObjectUtil.clone(person.getUserId()));
		clone.setName(PersonNameUtil.clone(person.getName()));
		clone.setPhoneNumber(PhoneNumberUtil.clone(person.getPhoneNumber()));
		clone.setEmailAddress(EmailAddressUtil.clone(person.getEmailAddress()));
		clone.setStreetAddress(StreetAddressUtil.clone(person.getStreetAddress()));
		return clone;
	}
	
}
