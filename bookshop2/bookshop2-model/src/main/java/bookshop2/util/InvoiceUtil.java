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

import bookshop2.Invoice;


public class InvoiceUtil extends BaseUtil {
	
	public static boolean isEmpty(Invoice invoice) {
		if (invoice == null)
			return true;
		boolean status = false;
		status |= OrderUtil.isEmpty(invoice.getOrder());
		status |= PaymentUtil.isEmpty(invoice.getPayment());
		return status;
	}
	
	public static boolean isEmpty(Collection<Invoice> invoiceList) {
		if (invoiceList == null  || invoiceList.size() == 0)
			return true;
		Iterator<Invoice> iterator = invoiceList.iterator();
		while (iterator.hasNext()) {
			Invoice invoice = iterator.next();
			if (!isEmpty(invoice))
				return false;
		}
		return true;
	}
	
	public static String toString(Invoice invoice) {
		if (isEmpty(invoice))
			return "Invoice: [uninitialized] "+invoice.toString();
		String text = invoice.toString();
		return text;
	}
	
	public static String toString(Collection<Invoice> invoiceList) {
		if (isEmpty(invoiceList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Invoice> iterator = invoiceList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Invoice invoice = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(invoice);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Invoice create() {
		Invoice invoice = new Invoice();
		initialize(invoice);
		return invoice;
	}
	
	public static void initialize(Invoice invoice) {
		//nothing for now
	}
	
	public static boolean validate(Invoice invoice) {
		if (invoice == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notNull(invoice.getDateTime(), "\"DateTime\" must be specified");
		validator.isFalse(OrderUtil.isEmpty(invoice.getOrder()), "\"Order\" must be specified");
		validator.isFalse(PaymentUtil.isEmpty(invoice.getPayment()), "\"Payment\" must be specified");
		OrderUtil.validate(invoice.getOrder());
		PaymentUtil.validate(invoice.getPayment());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Invoice> invoiceList) {
		Validator validator = Validator.getValidator();
		Iterator<Invoice> iterator = invoiceList.iterator();
		while (iterator.hasNext()) {
			Invoice invoice = iterator.next();
			//TODO break or accumulate?
			validate(invoice);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Invoice> invoiceList) {
		Collections.sort(invoiceList, createInvoiceComparator());
	}
	
	public static Collection<Invoice> sortRecords(Collection<Invoice> invoiceCollection) {
		List<Invoice> list = new ArrayList<Invoice>(invoiceCollection);
		Collections.sort(list, createInvoiceComparator());
		return list;
	}
	
	public static Comparator<Invoice> createInvoiceComparator() {
		return new Comparator<Invoice>() {
			public int compare(Invoice invoice1, Invoice invoice2) {
				int status = invoice1.compareTo(invoice2);
				return status;
			}
		};
	}
	
	public static Invoice clone(Invoice invoice) {
		if (invoice == null)
			return null;
		Invoice clone = create();
		clone.setId(ObjectUtil.clone(invoice.getId()));
		clone.setOrder(OrderUtil.clone(invoice.getOrder()));
		clone.setPayment(PaymentUtil.clone(invoice.getPayment()));
		clone.setDateTime(ObjectUtil.clone(invoice.getDateTime()));
		return clone;
	}
	
}
