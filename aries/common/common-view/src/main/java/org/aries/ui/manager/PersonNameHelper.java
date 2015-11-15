package org.aries.ui.manager;

import java.io.Serializable;


import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.common.PersonName;



@SessionScoped
@Named("personNameHelper")
@SuppressWarnings("serial")
public class PersonNameHelper implements Serializable {

	public boolean isEmpty(PersonName personName) {
		if (personName == null)
			return true;
		return StringUtils.isEmpty(personName.getFirstName()) ||
			StringUtils.isEmpty(personName.getLastName()); 
	}
	
	public String toString(PersonName personName) {
		if (isEmpty(personName))
			return "";
		String name = personName.getLastName()+", "+personName.getFirstName();
		name = StringEscapeUtils.escapeJavaScript(name);
		return name;
	}
}
