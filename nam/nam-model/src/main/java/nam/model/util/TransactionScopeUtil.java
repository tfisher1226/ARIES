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

import nam.model.TransactionScope;


public class TransactionScopeUtil extends BaseUtil {
	
	public static final TransactionScope[] VALUES_ARRAY = new TransactionScope[] {
		TransactionScope.EVENT,
		TransactionScope.METHOD,
		TransactionScope.THREAD,
		TransactionScope.PROCESS,
		TransactionScope.CONVERSATION
	};
	
	public static final List<TransactionScope> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static TransactionScope getTransactionScope(int ordinal) {
		if (ordinal == TransactionScope.EVENT.ordinal())
			return TransactionScope.EVENT;
		if (ordinal == TransactionScope.METHOD.ordinal())
			return TransactionScope.METHOD;
		if (ordinal == TransactionScope.THREAD.ordinal())
			return TransactionScope.THREAD;
		if (ordinal == TransactionScope.PROCESS.ordinal())
			return TransactionScope.PROCESS;
		if (ordinal == TransactionScope.CONVERSATION.ordinal())
			return TransactionScope.CONVERSATION;
		return null;
	}
	
	public static String toString(TransactionScope transactionScope) {
		return transactionScope.name();
	}
	
	public static String toString(Collection<TransactionScope> transactionScopeList) {
		StringBuffer buf = new StringBuffer();
		Iterator<TransactionScope> iterator = transactionScopeList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			TransactionScope transactionScope = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(transactionScope);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<TransactionScope> transactionScopeList) {
		Collections.sort(transactionScopeList, createTransactionScopeComparator());
	}
	
	public static Collection<TransactionScope> sortRecords(Collection<TransactionScope> transactionScopeCollection) {
		List<TransactionScope> list = new ArrayList<TransactionScope>(transactionScopeCollection);
		Collections.sort(list, createTransactionScopeComparator());
		return list;
	}
	
	public static Comparator<TransactionScope> createTransactionScopeComparator() {
		return new Comparator<TransactionScope>() {
			public int compare(TransactionScope transactionScope1, TransactionScope transactionScope2) {
				String text1 = transactionScope1.value();
				String text2 = transactionScope2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
