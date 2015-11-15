package bookshop2.supplier;

import bookshop2.QueryRequestMessage;
import bookshop2.ReservationRequestMessage;
import bookshop2.ShipmentCompleteMessage;
import bookshop2.ShipmentFailedMessage;
import bookshop2.ShipmentRequestMessage;
import bookshop2.ShipmentScheduledMessage;


public class SupplierProcessDefaults {
	
	public static boolean queryBooksEnabled(int option) {
		return true;
	}
	
	public static boolean reserveBooksEnabled(int option) {
		return true;
	}
	
	public static boolean shipBooksEnabled(int option) {
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
