package bookshop2.util;

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

import bookshop2.Payment;


public class PaymentUtil extends BaseUtil {
	
	public static boolean isEmpty(Payment payment) {
		if (payment == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(payment.getCurrency());
		return status;
	}
	
	public static boolean isEmpty(Collection<Payment> paymentList) {
		if (paymentList == null  || paymentList.size() == 0)
			return true;
		Iterator<Payment> iterator = paymentList.iterator();
		while (iterator.hasNext()) {
			Payment payment = iterator.next();
			if (!isEmpty(payment))
				return false;
		}
		return true;
	}
	
	public static String toString(Payment payment) {
		if (isEmpty(payment))
			return "Payment: [uninitialized] "+payment.toString();
		String text = payment.toString();
		return text;
	}
	
	public static String toString(Collection<Payment> paymentList) {
		if (isEmpty(paymentList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Payment> iterator = paymentList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Payment payment = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(payment);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Payment create() {
		Payment payment = new Payment();
		initialize(payment);
		return payment;
	}
	
	public static void initialize(Payment payment) {
		//nothing for now
	}
	
	public static boolean validate(Payment payment) {
		if (payment == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notNull(payment.getAmount(), "\"Amount\" must be specified");
		validator.notEmpty(payment.getCurrency(), "\"Currency\" must be specified");
		validator.notNull(payment.getDateTime(), "\"DateTime\" must be specified");
		validator.notNull(payment.getSalesTax(), "\"SalesTax\" must be specified");
		validator.notNull(payment.getTotal(), "\"Total\" must be specified");
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Payment> paymentList) {
		Validator validator = Validator.getValidator();
		Iterator<Payment> iterator = paymentList.iterator();
		while (iterator.hasNext()) {
			Payment payment = iterator.next();
			//TODO break or accumulate?
			validate(payment);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Payment> paymentList) {
		Collections.sort(paymentList, createPaymentComparator());
	}
	
	public static Collection<Payment> sortRecords(Collection<Payment> paymentCollection) {
		List<Payment> list = new ArrayList<Payment>(paymentCollection);
		Collections.sort(list, createPaymentComparator());
		return list;
	}
	
	public static Comparator<Payment> createPaymentComparator() {
		return new Comparator<Payment>() {
			public int compare(Payment payment1, Payment payment2) {
				int status = payment1.compareTo(payment2);
				return status;
			}
		};
	}
	
	public static Payment clone(Payment payment) {
		if (payment == null)
			return null;
		Payment clone = create();
		clone.setId(ObjectUtil.clone(payment.getId()));
		clone.setAmount(ObjectUtil.clone(payment.getAmount()));
		clone.setCurrency(ObjectUtil.clone(payment.getCurrency()));
		clone.setSalesTax(ObjectUtil.clone(payment.getSalesTax()));
		clone.setTotal(ObjectUtil.clone(payment.getTotal()));
		clone.setDateTime(ObjectUtil.clone(payment.getDateTime()));
		return clone;
	}
	
}
