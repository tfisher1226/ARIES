package bookshop2.ui.invoice;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractRecordManager;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import admin.ui.action.ActionListManager;
import admin.ui.action.ActionSelectManager;
import bookshop2.Invoice;
import bookshop2.OrderRequestMessage;
import bookshop2.util.InvoiceUtil;
import bookshop2.util.Bookshop2Fixture;


@SessionScoped
@Named("invoiceInfoManager")
public class InvoiceInfoManager extends AbstractRecordManager<Invoice> implements Serializable {
	
	private ActionSelectManager actionSelectManager;

	private ActionListManager actionListManager;
	
	@Inject
	@Updated
	private Event<Invoice> updatedEvent;
	
	
	public InvoiceInfoManager() {
		setInstanceName("invoice");
	}


	public Invoice getInvoice() {
		return getRecord();
	}

	@Override
	public Class<Invoice> getRecordClass() {
		return Invoice.class;
	}
	
	@Override
	public boolean isEmpty(Invoice invoice) {
		return getInvoiceHelper().isEmpty(invoice);
	}
	
	@Override
	public String toString(Invoice invoice) {
		return getInvoiceHelper().toString(invoice);
	}
	
	protected InvoiceHelper getInvoiceHelper() {
		return BeanContext.getFromSession("invoiceHelper");
	}
	
	protected void initialize(Invoice invoice) {
		InvoiceUtil.initialize(invoice);
		initializeOutjectedState(invoice);
		setContext("invoice", invoice);
	}
	
	protected void initializeOutjectedState(Invoice invoice) {
		outject(instanceName, invoice);
	}
	
	public void populate() {
		try {
			Invoice invoice = Bookshop2Fixture.create_Invoice();
			initialize(invoice);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void activate() {
		initializeContext();
		Invoice invoice = BeanContext.getFromSession(getInstanceId());
		if (invoice == null)
			newInvoice();
		else editInvoice(invoice);
	}
	
	//SEAM @Begin(join = true)
	public void newInvoice() {
		try {
			Invoice invoice = create();
			initialize(invoice);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	@Override
	public Invoice create() {
		Invoice invoice = InvoiceUtil.create();
		return invoice;
	}
	
	@Override
	public Invoice clone(Invoice invoice) {
		invoice = InvoiceUtil.clone(invoice);
		return invoice;
	}
	
	//SEAM @Begin(join = true)
	public void editInvoice(Invoice invoice) {
		try {
			//invoice = clone(invoice);
			initialize(invoice);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}

	//SEAM @Observer("org.aries.saveInvoice")
	public void saveInvoice() {
		setModule("InvoiceDialog");
		Invoice invoice = getInvoice();
		enrichInvoice(invoice);
		if (validate(invoice)) {
			saveInvoice(invoice);
			outject("invoice", invoice);
			//raiseEvent(actionEvent);
			updatedEvent.fire(invoice);
		}
	}

	public void processInvoice(Invoice invoice) {
		Long id = invoice.getId();
		if (id != null) {
			saveInvoice(invoice);
		}
	}
	
	public void saveInvoice(Invoice invoice) {
		try {
			raiseEvent("org.aries.refreshInvoiceList");
			raiseEvent(actionEvent);
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichInvoice(Invoice invoice) {
		//nothing for now
	}
	
	public boolean validate(Invoice invoice) {
		Validator validator = getValidator();
		boolean isValid = InvoiceUtil.validate(invoice);
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}

}
