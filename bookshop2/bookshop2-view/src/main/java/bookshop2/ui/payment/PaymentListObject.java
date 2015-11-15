package bookshop2.ui.payment;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import bookshop2.Payment;
import bookshop2.util.PaymentUtil;


public class PaymentListObject extends AbstractListObject<Payment> implements Comparable<PaymentListObject>, Serializable {
	
	private Payment payment;
	
	
	public PaymentListObject(Payment payment) {
		this.payment = payment;
	}

	
	public Payment getPayment() {
		return payment;
	}
	
	@Override
	public String getLabel() {
		return toString(payment);
	}
	
	@Override
	public String toString() {
		return toString(payment);
	}
	
	@Override
	public String toString(Payment payment) {
		return PaymentUtil.toString(payment);
	}
	
	@Override
	public int compareTo(PaymentListObject other) {
		String thisText = toString(this.payment);
		String otherText = toString(other.payment);
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		PaymentListObject other = (PaymentListObject) object;
		Long thisId = this.payment.getId();
		Long otherId = other.payment.getId();
		if (thisId == null)
			return false;
		if (otherId == null)
			return false;
		return thisId.equals(otherId);
	}

}
