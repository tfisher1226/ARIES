package bookshop2.ui.invoice;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractTabListManager;

import bookshop2.Invoice;
import bookshop2.util.InvoiceUtil;


@SessionScoped
@Named("invoiceListManager")
public class InvoiceListManager extends AbstractTabListManager<Invoice, InvoiceListObject> implements Serializable {
	
	@Override
	public String getModule() {
		return "InvoiceList";
	}

	@Override
	public String getTitle() {
		return "Invoice List";
	}

	@Override
	public Object getRecordId(Invoice invoice) {
		return invoice.getId();
	}

	@Override
	public String getRecordName(Invoice invoice) {
		return InvoiceUtil.toString(invoice);
	}

	@Override
	protected Class<Invoice> getRecordClass() {
		return Invoice.class;
	}

	@Override
	protected Invoice getRecord(InvoiceListObject rowObject) {
		return rowObject.getInvoice();
	}
	
	@Override
	protected InvoiceListObject createRowObject(Invoice invoice) {
		return new InvoiceListObject(invoice);
	}

	protected InvoiceHelper getInvoiceHelper() {
		return BeanContext.getFromSession("invoiceHelper");
	}
	
	protected InvoiceInfoManager getInvoiceInfoManager() {
		return BeanContext.getFromSession("invoiceInfoManager");
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
	}
	
	@Override
	public void refreshModel() {
		refreshModel(recordList);
	}
	
	public void editInvoice() {
		editInvoice(selectedRecordId);
	}
	
	public void editInvoice(String recordId) {
		editInvoice(Long.parseLong(recordId));
	}
	
	public void editInvoice(Long recordId) {
		Invoice invoice = recordByIdMap.get(recordId);
		editInvoice(invoice);
	}
	
	public void editInvoice(Invoice invoice) {
		InvoiceInfoManager invoiceInfoManager = BeanContext.getFromSession("invoiceInfoManager");
		invoiceInfoManager.editInvoice(invoice);
	}
	
	//SEAM @Observer("org.aries.removeInvoice")
	public void removeInvoice() {
		removeInvoice(selectedRecordId);
	}
	
	public void removeInvoice(String recordId) {
		removeInvoice(Long.parseLong(recordId));
	}
	
	public void removeInvoice(Long recordId) {
		Invoice invoice = recordByIdMap.get(recordId);
		removeInvoice(invoice);
	}
	
	public void removeInvoice(Invoice invoice) {
			try {
				clearSelection();
				refresh();
			
			} catch (Exception e) {
			handleException(e);
		}
	}
	
	//SEAM @Observer("org.aries.cancelInvoice")
	public void cancelInvoice() {
		if (selectedRecord == null)
			return;
		try {
			BeanContext.removeFromSession("invoice");
			Long id = selectedRecord.getId();
			initialize(recordByIdMap.values());
			
		} catch (Exception e) {
			handleException(e);
		}
	}

	public boolean validateInvoice(Collection<Invoice> invoiceList) {
		return InvoiceUtil.validate(invoiceList);
	}
	
}
