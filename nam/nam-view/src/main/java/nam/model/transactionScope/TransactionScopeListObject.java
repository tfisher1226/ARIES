package nam.model.transactionScope;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.TransactionScope;


public class TransactionScopeListObject extends AbstractListObject<TransactionScope> implements Comparable<TransactionScopeListObject>, Serializable {
	
	private TransactionScope transactionScope;
	
	
	public TransactionScopeListObject(TransactionScope transactionScope) {
		this.transactionScope = transactionScope;
	}
	
	
	public TransactionScope getTransactionScope() {
		return transactionScope;
	}
	
	@Override
	public Object getKey() {
		return getKey(transactionScope);
	}
	
	public Object getKey(TransactionScope transactionScope) {
		return transactionScope.value();
	}
	
	@Override
	public String getLabel() {
		return getLabel(transactionScope);
	}
	
	public String getLabel(TransactionScope transactionScope) {
		return transactionScope.name();
	}
	
	@Override
	public String toString() {
		return toString(transactionScope);
	}
	
	@Override
	public String toString(TransactionScope transactionScope) {
		return transactionScope.name();
	}
	
	@Override
	public int compareTo(TransactionScopeListObject other) {
		Object thisKey = getKey(this.transactionScope);
		Object otherKey = getKey(other.transactionScope);
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
		TransactionScopeListObject other = (TransactionScopeListObject) object;
		String thisText = toString(this.transactionScope);
		String otherText = toString(other.transactionScope);
		return thisText.equals(otherText);
	}
	
}
