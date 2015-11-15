package org.aries.ui.tag;

import javax.el.ELContext;
import javax.el.ValueExpression;


@SuppressWarnings("serial")
public class FromParentValueExpression extends ValueExpression {

	private ValueExpression valueExpression;

	public FromParentValueExpression(ValueExpression valueExpression) {
		this.valueExpression = valueExpression;
	}

	@Override
	public Class<?> getExpectedType() {
		return valueExpression.getExpectedType();
	}

	@Override
	public Class<?> getType(ELContext context) {
		return valueExpression.getType(context);
	}

	@Override
	public Object getValue(ELContext context) {
		if (valueExpression != null)
			return valueExpression.getValue(context);
		return null;
	}

	@Override
	public boolean isReadOnly(ELContext context) {
		return valueExpression.isReadOnly(context);
	}

	@Override
	public void setValue(ELContext context, Object value) {
		valueExpression.setValue(context, value);
	}

	@Override
	public boolean equals(Object object) {
		return valueExpression.equals(object);
	}

	@Override
	public String getExpressionString() {
		return valueExpression.getExpressionString();
	}

	@Override
	public int hashCode() {
		return valueExpression.hashCode();
	}

	@Override
	public boolean isLiteralText() {
		return valueExpression.isLiteralText();
	}

}
