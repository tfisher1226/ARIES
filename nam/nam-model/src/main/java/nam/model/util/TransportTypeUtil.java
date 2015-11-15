package nam.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;

import nam.model.TransportType;


public class TransportTypeUtil extends BaseUtil {
	
	public static final TransportType[] VALUES_ARRAY = new TransportType[] {
		TransportType.RMI,
		TransportType.EJB,
		TransportType.HTTP,
		TransportType.JMS
	};
	
	public static final List<TransportType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static TransportType getTransportType(int ordinal) {
		if (ordinal == TransportType.RMI.ordinal())
			return TransportType.RMI;
		if (ordinal == TransportType.EJB.ordinal())
			return TransportType.EJB;
		if (ordinal == TransportType.HTTP.ordinal())
			return TransportType.HTTP;
		if (ordinal == TransportType.JMS.ordinal())
			return TransportType.JMS;
		return null;
	}
	
	public static String toString(TransportType transportType) {
		return transportType.name();
	}
	
	public static String toString(Collection<TransportType> transportTypeList) {
		StringBuffer buf = new StringBuffer();
		Iterator<TransportType> iterator = transportTypeList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			TransportType transportType = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(transportType);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<TransportType> transportTypeList) {
		Collections.sort(transportTypeList, createTransportTypeComparator());
	}
	
	public static Collection<TransportType> sortRecords(Collection<TransportType> transportTypeCollection) {
		List<TransportType> list = new ArrayList<TransportType>(transportTypeCollection);
		Collections.sort(list, createTransportTypeComparator());
		return list;
	}
	
	public static Comparator<TransportType> createTransportTypeComparator() {
		return new Comparator<TransportType>() {
			public int compare(TransportType transportType1, TransportType transportType2) {
				String text1 = transportType1.value();
				String text2 = transportType2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
