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

import nam.model.AcknowledgeMode;


public class AcknowledgeModeUtil extends BaseUtil {
	
	public static final AcknowledgeMode[] VALUES_ARRAY = new AcknowledgeMode[] {
		AcknowledgeMode.LAZY,
		AcknowledgeMode.AUTO,
		AcknowledgeMode.CLIENT
	};
	
	public static final List<AcknowledgeMode> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static AcknowledgeMode getAcknowledgeMode(int ordinal) {
		if (ordinal == AcknowledgeMode.LAZY.ordinal())
			return AcknowledgeMode.LAZY;
		if (ordinal == AcknowledgeMode.AUTO.ordinal())
			return AcknowledgeMode.AUTO;
		if (ordinal == AcknowledgeMode.CLIENT.ordinal())
			return AcknowledgeMode.CLIENT;
		return null;
	}
	
	public static String toString(AcknowledgeMode acknowledgeMode) {
		return acknowledgeMode.name();
	}
	
	public static String toString(Collection<AcknowledgeMode> acknowledgeModeList) {
		StringBuffer buf = new StringBuffer();
		Iterator<AcknowledgeMode> iterator = acknowledgeModeList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			AcknowledgeMode acknowledgeMode = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(acknowledgeMode);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<AcknowledgeMode> acknowledgeModeList) {
		Collections.sort(acknowledgeModeList, createAcknowledgeModeComparator());
	}
	
	public static Collection<AcknowledgeMode> sortRecords(Collection<AcknowledgeMode> acknowledgeModeCollection) {
		List<AcknowledgeMode> list = new ArrayList<AcknowledgeMode>(acknowledgeModeCollection);
		Collections.sort(list, createAcknowledgeModeComparator());
		return list;
	}
	
	public static Comparator<AcknowledgeMode> createAcknowledgeModeComparator() {
		return new Comparator<AcknowledgeMode>() {
			public int compare(AcknowledgeMode acknowledgeMode1, AcknowledgeMode acknowledgeMode2) {
				String text1 = acknowledgeMode1.value();
				String text2 = acknowledgeMode2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
