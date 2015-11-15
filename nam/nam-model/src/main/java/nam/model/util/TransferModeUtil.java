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

import nam.model.TransferMode;


public class TransferModeUtil extends BaseUtil {
	
	public static final TransferMode[] VALUES_ARRAY = new TransferMode[] {
		TransferMode.TEXT,
		TransferMode.BINARY
	};
	
	public static final List<TransferMode> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static TransferMode getTransferMode(int ordinal) {
		if (ordinal == TransferMode.TEXT.ordinal())
			return TransferMode.TEXT;
		if (ordinal == TransferMode.BINARY.ordinal())
			return TransferMode.BINARY;
		return null;
	}
	
	public static String toString(TransferMode transferMode) {
		return transferMode.name();
	}
	
	public static String toString(Collection<TransferMode> transferModeList) {
		StringBuffer buf = new StringBuffer();
		Iterator<TransferMode> iterator = transferModeList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			TransferMode transferMode = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(transferMode);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<TransferMode> transferModeList) {
		Collections.sort(transferModeList, createTransferModeComparator());
	}
	
	public static Collection<TransferMode> sortRecords(Collection<TransferMode> transferModeCollection) {
		List<TransferMode> list = new ArrayList<TransferMode>(transferModeCollection);
		Collections.sort(list, createTransferModeComparator());
		return list;
	}
	
	public static Comparator<TransferMode> createTransferModeComparator() {
		return new Comparator<TransferMode>() {
			public int compare(TransferMode transferMode1, TransferMode transferMode2) {
				String text1 = transferMode1.value();
				String text2 = transferMode2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
