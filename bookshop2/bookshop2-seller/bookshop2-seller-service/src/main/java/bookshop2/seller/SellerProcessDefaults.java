package bookshop2.seller;

import bookshop2.BooksAvailableMessage;
import bookshop2.BooksUnavailableMessage;
import bookshop2.OrderRequestMessage;
import bookshop2.PurchaseRequestMessage;
import bookshop2.ShipmentCompleteMessage;
import bookshop2.ShipmentFailedMessage;
import bookshop2.ShipmentScheduledMessage;


public class SellerProcessDefaults {

	public static boolean orderBooksEnabled(int option) {
		return true;
	}

	public static boolean booksAvailableEnabled(int option) {
		return true;
	}

	public static boolean booksUnavailableEnabled(int option) {
		return true;
	}
	
	public static boolean purchaseBooksEnabled(int option) {
		return true;
	}

	public static boolean shipmentScheduledEnabled(int option) {
		return true;
	}
	
	public static boolean shipmentCompleteEnabled(int option) {
		return true;
	}
	
	public static boolean shipmentFailedEnabled(int option) {
		return true;
	}

}
