package org.aries.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;


public class BooleanAdapter extends XmlAdapter<String, Boolean> {

    public Boolean unmarshal(String value) {
    	if (value != null)
    		return Boolean.valueOf(value);
    	return Boolean.FALSE;
    }

    public String marshal(Boolean value) {
    	if (value != null)
    		return value.toString();
    	return Boolean.toString(false);
    }

}
