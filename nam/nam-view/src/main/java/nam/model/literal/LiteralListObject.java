package nam.model.literal;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Literal;
import nam.model.util.LiteralUtil;


public class LiteralListObject extends AbstractListObject<Literal> implements Comparable<LiteralListObject>, Serializable {
	
	private Literal literal;
	
	
	public LiteralListObject(Literal literal) {
		this.literal = literal;
	}
	
	
	public Literal getLiteral() {
		return literal;
	}
	
	@Override
	public Object getKey() {
		return getKey(literal);
	}
	
	public Object getKey(Literal literal) {
		return LiteralUtil.getKey(literal);
	}
	
	@Override
	public String getLabel() {
		return getLabel(literal);
	}
	
	public String getLabel(Literal literal) {
		return LiteralUtil.getLabel(literal);
	}
	
	@Override
	public String toString() {
		return toString(literal);
	}
	
	@Override
	public String toString(Literal literal) {
		return LiteralUtil.toString(literal);
	}
	
	@Override
	public int compareTo(LiteralListObject other) {
		Object thisKey = getKey(this.literal);
		Object otherKey = getKey(other.literal);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		LiteralListObject other = (LiteralListObject) object;
		Object thisKey = LiteralUtil.getKey(this.literal);
		Object otherKey = LiteralUtil.getKey(other.literal);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
