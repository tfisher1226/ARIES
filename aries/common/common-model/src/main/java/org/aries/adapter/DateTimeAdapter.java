package org.aries.adapter;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;


public class DateTimeAdapter extends XmlAdapter<String, Date> {

    public Date unmarshal(String value) {
        return org.aries.common.util.XSDateTimeHelper.parseDateTime(value);
    }

    public String marshal(Date value) {
        return org.aries.common.util.XSDateTimeHelper.printDateTime(value);
    }

}
