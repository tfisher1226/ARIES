package org.aries.ui.manager;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.common.EmailAddress;
import org.aries.common.util.EmailAddressUtil;


@SessionScoped
@Named("emailAddressHelper")
@SuppressWarnings("serial")
public class EmailAddressHelper implements Serializable {

	public boolean isEmpty(EmailAddress emailAddress) {
		return EmailAddressUtil.isEmpty(emailAddress);
	}
	
	public String toString(EmailAddress emailAddress) {
		return EmailAddressUtil.toString(emailAddress);
	}
	
}
