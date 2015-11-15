package nam.model.statement;


public class TimeoutBlock extends AbstractBlock {

	private long timeoutValue;
	
	
	public TimeoutBlock() {
	}

	public long getTimeoutValue() {
		return timeoutValue;
	}

	public void setTimeoutValue(long timeoutValue) {
		this.timeoutValue = timeoutValue;
	}

}
