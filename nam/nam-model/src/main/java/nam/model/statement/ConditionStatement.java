package nam.model.statement;

import java.util.Comparator;



public class ConditionStatement extends Statement implements Comparator<ConditionStatement> {

	private ExpressionStatement expressionStatement1;

	private ExpressionStatement expressionStatement2;

	private String operator;
	
	private String text;

	
	public ExpressionStatement getExpressionStatement1() {
		return expressionStatement1;
	}

	public void setExpressionStatement1(ExpressionStatement expressionStatement1) {
		this.expressionStatement1 = expressionStatement1;
	}

	public ExpressionStatement getExpressionStatement2() {
		return expressionStatement2;
	}

	public void setExpressionStatement2(ExpressionStatement expressionStatement2) {
		this.expressionStatement2 = expressionStatement2;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public int compare(ConditionStatement conditionStatement1, ConditionStatement conditionStatement2) {
		String text1 = conditionStatement1.toString();
		String text2 = conditionStatement2.toString();
		return text1.compareTo(text2);
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		ConditionStatement other = (ConditionStatement) object;
		int status = compare(this, other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (text != null)
			hashCode += text.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return text;
	}

}
