package org.aries.byteman;

import java.util.Set;

import bookshop2.Book;
import bookshop2.Invoice;
import bookshop2.Shipment;
import bookshop2.util.Bookshop2Fixture;


public class BytemanHelper /*extends Helper*/ {

	protected BytemanHelper() {
	}

//	protected BytemanHelper(Rule rule) {
//		super(rule);
//	}

	public Set<Book> createEmptyBook_Set() {
		return Bookshop2Fixture.createEmptySet_Book();
	}
	
	public Shipment createShipment() {
		return Bookshop2Fixture.create_Shipment();
	}
	
	public Invoice createInvoice() {
		return Bookshop2Fixture.create_Invoice();
	}
	
}
