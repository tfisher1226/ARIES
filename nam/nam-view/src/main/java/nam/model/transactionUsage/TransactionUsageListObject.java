package nam.model.transactionUsage;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.TransactionUsage;


public class TransactionUsageListObject extends AbstractListObject<TransactionUsage> implements Comparable<TransactionUsageListObject>, Serializable {
	
	private TransactionUsage transactionUsage;
	
	
	public TransactionUsageListObject(TransactionUsage transactionUsage) {
		this.transactionUsage = transactionUsage;
	}
	
	
	public TransactionUsage getTransactionUsage() {
		return transactionUsage;
	}
	
	@Override
	public Object getKey() {
		return getKey(transactionUsage);
	}
	
	public Object getKey(TransactionUsage transactionUsage) {
		return transactionUsage.value();
	}
	
	@Override
	public String getLabel() {
		return getLabel(transactionUsage);
	}
	
	public String getLabel(TransactionUsage transactionUsage) {
		return transactionUsage.value();
	}
	
	@Override
	public String toString() {
		return toString(transactionUsage);
	}
	
	@Override
	public String toString(TransactionUsage transactionUsage) {
		return transactionUsage.name();
	}
	
	@Override
	public int compareTo(TransactionUsageListObject other) {
		Object thisKey = getKey(this.transactionUsage);
		Object otherKey = getKey(other.transactionUsage);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		TransactionUsageListObject other = (TransactionUsageListObject) object;
		String thisText = toString(this.transactionUsage);
		String otherText = toString(other.transactionUsage);
		return thisText.equals(otherText);
	}
	
}
