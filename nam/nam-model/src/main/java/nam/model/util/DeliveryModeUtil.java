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

import nam.model.DeliveryMode;


public class DeliveryModeUtil extends BaseUtil {
	
	public static final DeliveryMode[] VALUES_ARRAY = new DeliveryMode[] {
		DeliveryMode.PERSISTENT,
		DeliveryMode.NON_PERSISTENT
	};
	
	public static final List<DeliveryMode> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static DeliveryMode getDeliveryMode(int ordinal) {
		if (ordinal == DeliveryMode.PERSISTENT.ordinal())
			return DeliveryMode.PERSISTENT;
		if (ordinal == DeliveryMode.NON_PERSISTENT.ordinal())
			return DeliveryMode.NON_PERSISTENT;
		return null;
	}
	
	public static String toString(DeliveryMode deliveryMode) {
		return deliveryMode.name();
	}
	
	public static String toString(Collection<DeliveryMode> deliveryModeList) {
		StringBuffer buf = new StringBuffer();
		Iterator<DeliveryMode> iterator = deliveryModeList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			DeliveryMode deliveryMode = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(deliveryMode);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<DeliveryMode> deliveryModeList) {
		Collections.sort(deliveryModeList, createDeliveryModeComparator());
	}
	
	public static Collection<DeliveryMode> sortRecords(Collection<DeliveryMode> deliveryModeCollection) {
		List<DeliveryMode> list = new ArrayList<DeliveryMode>(deliveryModeCollection);
		Collections.sort(list, createDeliveryModeComparator());
		return list;
	}
	
	public static Comparator<DeliveryMode> createDeliveryModeComparator() {
		return new Comparator<DeliveryMode>() {
			public int compare(DeliveryMode deliveryMode1, DeliveryMode deliveryMode2) {
				String text1 = deliveryMode1.value();
				String text2 = deliveryMode2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
