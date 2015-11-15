package bookshop2.ui.invoice;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import bookshop2.Invoice;
import bookshop2.util.InvoiceUtil;


public class InvoiceListObject extends AbstractListObject<Invoice> implements Comparable<InvoiceListObject>, Serializable {
	
	private Invoice invoice;
	
	
	public InvoiceListObject(Invoice invoice) {
		this.invoice = invoice;
	}

	
	public Invoice getInvoice() {
		return invoice;
	}
	
	@Override
	public String getLabel() {
		return toString(invoice);
	}
	
	@Override
	public String toString() {
		return toString(invoice);
	}
	
	@Override
	public String toString(Invoice invoice) {
		return InvoiceUtil.toString(invoice);
	}
	
	@Override
	public int compareTo(InvoiceListObject other) {
		String thisText = toString(this.invoice);
		String otherText = toString(other.invoice);
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		InvoiceListObject other = (InvoiceListObject) object;
		Long thisId = this.invoice.getId();
		Long otherId = other.invoice.getId();
		if (thisId == null)
			return false;
		if (otherId == null)
			return false;
		return thisId.equals(otherId);
	}

}
