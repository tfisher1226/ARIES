package bookshop2.ui.invoice;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;

import bookshop2.Invoice;
import bookshop2.util.InvoiceUtil;


@SessionScoped
@Named("invoiceHelper")
public class InvoiceHelper extends AbstractElementHelper<Invoice> implements Serializable {

	@Override
	public boolean isEmpty(Invoice invoice) {
		return InvoiceUtil.isEmpty(invoice);
	}
	
	@Override
	public String toString(Invoice invoice) {
		return ""; //invoice.getTitle()+" "+invoice.getAuthor();
	}
	
	@Override
	public String toString(Collection<Invoice> invoiceList) {
		return InvoiceUtil.toString(invoiceList);
	}
	
	@Override
	public boolean validate(Invoice invoice) {
		return InvoiceUtil.validate(invoice);
	}

	@Override
	public boolean validate(Collection<Invoice> invoiceList) {
		return InvoiceUtil.validate(invoiceList);
	}

}
