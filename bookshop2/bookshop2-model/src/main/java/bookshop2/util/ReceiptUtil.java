package bookshop2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import bookshop2.Receipt;


public class ReceiptUtil extends BaseUtil {
	
	public static boolean isEmpty(Receipt receipt) {
		if (receipt == null)
			return true;
		boolean status = false;
		status |= OrderUtil.isEmpty(receipt.getOrder());
		status |= PaymentUtil.isEmpty(receipt.getPayment());
		return status;
	}
	
	public static boolean isEmpty(Collection<Receipt> receiptList) {
		if (receiptList == null  || receiptList.size() == 0)
			return true;
		Iterator<Receipt> iterator = receiptList.iterator();
		while (iterator.hasNext()) {
			Receipt receipt = iterator.next();
			if (!isEmpty(receipt))
				return false;
		}
		return true;
	}
	
	public static String toString(Receipt receipt) {
		if (isEmpty(receipt))
			return "Receipt: [uninitialized] "+receipt.toString();
		String text = receipt.toString();
		return text;
	}
	
	public static String toString(Collection<Receipt> receiptList) {
		if (isEmpty(receiptList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Receipt> iterator = receiptList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Receipt receipt = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(receipt);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Receipt create() {
		Receipt receipt = new Receipt();
		initialize(receipt);
		return receipt;
	}
	
	public static void initialize(Receipt receipt) {
		//nothing for now
	}
	
	public static boolean validate(Receipt receipt) {
		if (receipt == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notNull(receipt.getDateTime(), "\"DateTime\" must be specified");
		validator.isFalse(OrderUtil.isEmpty(receipt.getOrder()), "\"Order\" must be specified");
		validator.isFalse(PaymentUtil.isEmpty(receipt.getPayment()), "\"Payment\" must be specified");
		validator.notNull(receipt.getTotal(), "\"Total\" must be specified");
		OrderUtil.validate(receipt.getOrder());
		PaymentUtil.validate(receipt.getPayment());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Receipt> receiptList) {
		Validator validator = Validator.getValidator();
		Iterator<Receipt> iterator = receiptList.iterator();
		while (iterator.hasNext()) {
			Receipt receipt = iterator.next();
			//TODO break or accumulate?
			validate(receipt);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Receipt> receiptList) {
		Collections.sort(receiptList, createReceiptComparator());
	}
	
	public static Collection<Receipt> sortRecords(Collection<Receipt> receiptCollection) {
		List<Receipt> list = new ArrayList<Receipt>(receiptCollection);
		Collections.sort(list, createReceiptComparator());
		return list;
	}
	
	public static Comparator<Receipt> createReceiptComparator() {
		return new Comparator<Receipt>() {
			public int compare(Receipt receipt1, Receipt receipt2) {
				int status = receipt1.compareTo(receipt2);
				return status;
			}
		};
	}
	
	public static Receipt clone(Receipt receipt) {
		if (receipt == null)
			return null;
		Receipt clone = create();
		clone.setId(ObjectUtil.clone(receipt.getId()));
		clone.setOrder(OrderUtil.clone(receipt.getOrder()));
		clone.setPayment(PaymentUtil.clone(receipt.getPayment()));
		clone.setTotal(ObjectUtil.clone(receipt.getTotal()));
		clone.setDateTime(ObjectUtil.clone(receipt.getDateTime()));
		return clone;
	}
	
}
