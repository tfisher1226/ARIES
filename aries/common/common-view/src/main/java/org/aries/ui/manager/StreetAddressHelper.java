package org.aries.ui.manager;

import java.io.Serializable;


import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.common.StreetAddress;
import org.aries.common.util.StreetAddressUtil;



@SessionScoped
@Named("streetAddressHelper")
@SuppressWarnings("serial")
public class StreetAddressHelper implements Serializable {

	public boolean isEmpty(StreetAddress streetAddress) {
		return StreetAddressUtil.isEmpty(streetAddress);
	}
	
	public String toString(StreetAddress streetAddress) {
		return toStringLine1(streetAddress) + "\n" + toStringLine2(streetAddress);
	}
	
	public String toStringLine1(StreetAddress streetAddress) {
		return StreetAddressUtil.toStringLine1(streetAddress);
	}
	
	public String toStringLine2(StreetAddress streetAddress) {
		return StreetAddressUtil.toStringLine2(streetAddress);
	}
	
}
