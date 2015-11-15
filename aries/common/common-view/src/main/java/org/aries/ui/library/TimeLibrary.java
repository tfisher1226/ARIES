package org.aries.ui.library;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;


public class TimeLibrary implements Serializable {
	
	public String toTimeString(Date dateTime) {
		DateFormat dateFormat = DateFormat.getDateTimeInstance();
		return dateTime != null ? dateFormat.format(dateTime) : "";
	}
	
	public String toTimeString(DateTime dateTime) {
		return dateTime != null ? dateTime.toString("yyyy-MM-dd [HH:mm:ss]") : "";
	}
	
	public String toSimpleDate(Date dateTime) {
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); 
		return dateTime != null ? formatter.format(dateTime) : "";
	}
	
	/*
	public String toTimeString(DateMidnight dateMidnight) {
		return dateMidnight != null ? dateMidnight.toString("yyyy-MM-dd [HH:mm:ss]") : "";
	}
	*/

	public String toDateString(Date dateTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
		return dateTime != null ? formatter.format(dateTime) : "";
	}

	public String toDateString(DateTime dateTime) {
		return dateTime != null ? dateTime.toString("yyyy-MM-dd") : "";
	}

	/*
	public String toDateString(DateMidnight dateMidnight) {
		return dateMidnight != null ? dateMidnight.toString("yyyy-MM-dd") : "";
	}
	*/

	public String toActiveString(Boolean active) {
		return active ? "Active" : "Inactive";
	}
	
	public Boolean toActiveInactiveBoolean(String active) {
		return active.equals("Inactive") ? Boolean.FALSE : Boolean.TRUE;
	}
}
