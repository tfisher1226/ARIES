package nam.model.statement;



public class Branch {

	private ConditionStatement conditionStatement;
	
	private boolean success;

	
	public ConditionStatement getConditionStatement() {
		return conditionStatement;
	}

	public void setConditionStatement(ConditionStatement conditionStatement) {
		this.conditionStatement = conditionStatement;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
