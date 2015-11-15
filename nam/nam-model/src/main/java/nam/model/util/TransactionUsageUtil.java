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

import nam.model.TransactionUsage;


public class TransactionUsageUtil extends BaseUtil {
	
	public static final TransactionUsage[] VALUES_ARRAY = new TransactionUsage[] {
		TransactionUsage.MANDATORY,
		TransactionUsage.SUPPORTED,
		TransactionUsage.REQUIRED,
		TransactionUsage.REQUIRES_NEW
	};
	
	public static final List<TransactionUsage> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static TransactionUsage getTransactionUsage(int ordinal) {
		if (ordinal == TransactionUsage.MANDATORY.ordinal())
			return TransactionUsage.MANDATORY;
		if (ordinal == TransactionUsage.SUPPORTED.ordinal())
			return TransactionUsage.SUPPORTED;
		if (ordinal == TransactionUsage.REQUIRED.ordinal())
			return TransactionUsage.REQUIRED;
		if (ordinal == TransactionUsage.REQUIRES_NEW.ordinal())
			return TransactionUsage.REQUIRES_NEW;
		return null;
	}
	
	public static String toString(TransactionUsage transactionUsage) {
		return transactionUsage.name();
	}
	
	public static String toString(Collection<TransactionUsage> transactionUsageList) {
		StringBuffer buf = new StringBuffer();
		Iterator<TransactionUsage> iterator = transactionUsageList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			TransactionUsage transactionUsage = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(transactionUsage);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<TransactionUsage> transactionUsageList) {
		Collections.sort(transactionUsageList, createTransactionUsageComparator());
	}
	
	public static Collection<TransactionUsage> sortRecords(Collection<TransactionUsage> transactionUsageCollection) {
		List<TransactionUsage> list = new ArrayList<TransactionUsage>(transactionUsageCollection);
		Collections.sort(list, createTransactionUsageComparator());
		return list;
	}
	
	public static Comparator<TransactionUsage> createTransactionUsageComparator() {
		return new Comparator<TransactionUsage>() {
			public int compare(TransactionUsage transactionUsage1, TransactionUsage transactionUsage2) {
				String text1 = transactionUsage1.value();
				String text2 = transactionUsage2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
