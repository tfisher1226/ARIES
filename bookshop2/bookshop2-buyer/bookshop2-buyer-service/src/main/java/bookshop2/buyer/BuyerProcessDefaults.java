package bookshop2.buyer;

import bookshop2.OrderAcceptedMessage;
import bookshop2.OrderRejectedMessage;
import bookshop2.OrderRequestMessage;
import bookshop2.PurchaseAcceptedMessage;
import bookshop2.PurchaseRejectedMessage;
import bookshop2.PurchaseRequestMessage;


public class BuyerProcessDefaults {

	public static boolean orderBooksEnabled(int option) {
		return true;
	}

	public static boolean orderAcceptedEnabled(int option) {
		return true;
	}

	public static boolean orderRejectedEnabled(int option) {
		return true;
	}

	public static boolean purchaseBooksEnabled(int option) {
		return true;
	}

	public static boolean purchaseAcceptedEnabled(int option) {
		return true;
	}

	public static boolean purchaseRejectedEnabled(int option) {
		return true;
	}

	public static boolean invokePurchaseBooksEnabled(int option) {
		return true;
	}
	
}
