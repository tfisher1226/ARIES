package org.aries.ui.manager;

import java.io.Serializable;


import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.common.ZipCode;
import org.aries.common.util.ZipCodeUtil;



@SessionScoped
@Named("zipCodeHelper")
@SuppressWarnings("serial")
public class ZipCodeHelper implements Serializable {

	public boolean isEmpty(ZipCode zipCode) {
		return ZipCodeUtil.isEmpty(zipCode);
	}

	public String toString(ZipCode zipCode) {
		//return ZipCodeUtil.toString(zipCode);
		return ZipCodeUtil.toZipCodeString(zipCode);
	}
	
}
