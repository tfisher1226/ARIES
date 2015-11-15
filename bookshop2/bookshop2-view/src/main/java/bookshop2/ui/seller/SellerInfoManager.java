package bookshop2.ui.seller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractInfoManager;
import org.aries.ui.manager.EmailAddressManager;
import org.aries.ui.manager.PersonNameManager;
import org.aries.ui.manager.PhoneNumberManager;
import org.aries.ui.manager.StreetAddressManager;

import bookshop2.Book;
import bookshop2.Order;
import bookshop2.OrderRequestMessage;
import bookshop2.util.Bookshop2Fixture;


@SessionScoped
@Named("sellerInfoManager")
public class SellerInfoManager extends AbstractInfoManager implements Serializable {

	private Book book;

	private Order order;

	private OrderRequestMessage orderRequestMessage;


	private EmailAddressManager emailAddressManager;

	private PersonNameManager personNameManager;
	
	private PhoneNumberManager phoneNumberManager;

	private StreetAddressManager streetAddressManager;
	
	
	public OrderRequestMessage getOrderRequestMessage() {
		return orderRequestMessage;
	}

	public void setOrderRequestMessage(OrderRequestMessage orderRequestMessage) {
		this.orderRequestMessage = orderRequestMessage;
	}

	
	public SellerInfoManager() {
		orderRequestMessage = Bookshop2Fixture.create_OrderRequestMessage();
	}
	
	@Override
	protected boolean isRecordEmpty() {
		return false;
	}

	@Override
	protected boolean isRecordValid() {
		return false;
	}
	
	@Override
	protected Object createNewRecord() {
		return null;
	}

	@Override
	protected String getRecordClassName() {
		return null;
	}

}
