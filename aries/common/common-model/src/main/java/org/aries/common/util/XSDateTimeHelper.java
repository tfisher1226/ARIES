package org.aries.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class XSDateTimeHelper {

	private static final Pattern TZ_REGEX = Pattern.compile("([+-][0-9][0-9]):?([0-9][0-9])$");

	public static Date parseDate(String text) {
		if (text == null)
			return null;
		Matcher matcher = TZ_REGEX.matcher(text);
		TimeZone timeZone = null;
		if (matcher.find()) {
			String tzCode = "GMT" + matcher.group(1) + matcher.group(2); // eg "GMT+0100"
			timeZone = TimeZone.getTimeZone(tzCode);
		}
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (timeZone != null) {
			formatter.setTimeZone(timeZone);
		}
		try {
			Date result = formatter.parse(text);
			return result;
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static Date parseTime(String text) {
		if (text == null)
			return null;
		Matcher matcher = TZ_REGEX.matcher(text);
		TimeZone timeZone = null;
		if (matcher.find()) {
			String tzCode = "GMT" + matcher.group(1) + matcher.group(2); // eg "GMT+0100"
			timeZone = TimeZone.getTimeZone(tzCode);
		}
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		if (timeZone != null) {
			formatter.setTimeZone(timeZone);
		}
		try {
			Date result = formatter.parse(text);
			return result;
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date parseDateTime(String text) {
		if (text == null)
			return null;
		Matcher matcher = TZ_REGEX.matcher(text);
		TimeZone timeZone = null;
		if (matcher.find()) {
			String tzCode = "GMT" + matcher.group(1) + matcher.group(2); // eg "GMT+0100"
			timeZone = TimeZone.getTimeZone(tzCode);
		}
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		if (timeZone != null) {
			formatter.setTimeZone(timeZone);
		}
		try {
			Date result = formatter.parse(text);
			return result;
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String printDate(Date date) {
		if (date == null)
			return null;
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}
	
	public static String printTime(Date date) {
		if (date == null)
			return null;
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		DateFormat tzFormatter = new SimpleDateFormat("Z");
		String timezone = tzFormatter.format(date);
		return formatter.format(date) + timezone.substring(0, 3) + ":" + timezone.substring(3);
	}
	
	// fix because the 'Z' formatter produces an output incompatible with the xsd:dateTime
	public static String printDateTime(Date date) {
		if (date == null)
			return null;
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		DateFormat tzFormatter = new SimpleDateFormat("Z");
		String timezone = tzFormatter.format(date);
		return formatter.format(date) + timezone.substring(0, 3) + ":" + timezone.substring(3);
	}

}
