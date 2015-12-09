package admin.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import admin.Account;


public class AccountUtil extends BaseUtil {
	
	public static Object getKey(Account account) {
		return account.getUser().getUserName();
	}
	
	public static String getLabel(Account account) {
		return account.getUser().getUserName();
	}
	
	public static boolean isEmpty(Account account) {
		if (account == null)
			return true;
		boolean status = false;
		status |= UserUtil.isEmpty(account.getUser());
		status |= StringUtils.isEmpty(account.getCreditCard());
		return status;
	}
	
	public static boolean isEmpty(Collection<Account> accountList) {
		if (accountList == null  || accountList.size() == 0)
			return true;
		Iterator<Account> iterator = accountList.iterator();
		while (iterator.hasNext()) {
			Account account = iterator.next();
			if (!isEmpty(account))
				return false;
		}
		return true;
	}
	
	public static String toString(Account account) {
		if (isEmpty(account))
			return "Account: [uninitialized] "+account.toString();
		String text = "";
		text += UserUtil.toString(account.getUser());
		return text;
	}
	
	public static String toString(Collection<Account> accountList) {
		if (isEmpty(accountList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Account> iterator = accountList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Account account = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(account);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Account create() {
		Account account = new Account();
		initialize(account);
		return account;
	}
	
	public static void initialize(Account account) {
		if (account.getEnabled() == null)
			account.setEnabled(true);
	}
	
	public static boolean validate(Account account) {
		if (account == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(account.getCreditCard(), "\"CreditCard\" must be specified");
		validator.isFalse(UserUtil.isEmpty(account.getUser()), "\"User\" must be specified");
		UserUtil.validate(account.getUser());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Account> accountList) {
		Validator validator = Validator.getValidator();
		Iterator<Account> iterator = accountList.iterator();
		while (iterator.hasNext()) {
			Account account = iterator.next();
			//TODO break or accumulate?
			validate(account);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Account> accountList) {
		Collections.sort(accountList, createAccountComparator());
	}
	
	public static Collection<Account> sortRecords(Collection<Account> accountCollection) {
		List<Account> list = new ArrayList<Account>(accountCollection);
		Collections.sort(list, createAccountComparator());
		return list;
	}
	
	public static void sortRecordsByCreditCard(List<Account> accountList) {
		Collections.sort(accountList, new Comparator<Account>() {
			public int compare(Account account1, Account account2) {
				String text1 = account1.getCreditCard();
				String text2 = account2.getCreditCard();
				return text1.compareTo(text2);
			}
		});
	}
	
	public static void sortRecordsByUser(List<Account> accountList) {
		Collections.sort(accountList, new Comparator<Account>() {
			public int compare(Account account1, Account account2) {
				int status = account1.compareTo(account2);
				return status;
			}
		});
	}
	
	public static Comparator<Account> createAccountComparator() {
		return new Comparator<Account>() {
			public int compare(Account account1, Account account2) {
				Object key1 = getKey(account1);
				Object key2 = getKey(account2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Account clone(Account account) {
		if (account == null)
			return null;
		Account clone = create();
		clone.setId(ObjectUtil.clone(account.getId()));
		clone.setUser(UserUtil.clone(account.getUser()));
		clone.setModels(ObjectUtil.clone(account.getModels(), String.class, String.class));
		clone.setCreditCard(ObjectUtil.clone(account.getCreditCard()));
		clone.setEnabled(ObjectUtil.clone(account.getEnabled()));
		return clone;
	}
	
}
