package org.aries.adapter;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;


public class DateAdapter extends XmlAdapter<String, Date> {

    public Date unmarshal(String value) {
        return org.aries.common.util.XSDateTimeHelper.parseDate(value);
    }

    public String marshal(Date value) {
        return org.aries.common.util.XSDateTimeHelper.printDate(value);
    }

}
