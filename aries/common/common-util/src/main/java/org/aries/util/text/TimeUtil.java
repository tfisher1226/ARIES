package org.aries.util.text;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeUtil {

	public static SimpleDateFormat TimeStampFormat = new SimpleDateFormat("MM:dd.yyyy hh:mm:ss aaa"); 
	
	public static String getTimeStamp() {
		return getTimeStamp(new Date());
	}

	public static String getTimeStamp(Date date) {
		TimeStampFormat.format(date);
		return null;
	}

}
