package nam.model.statement;

import nam.model.Command;


public class PostCommand extends Command {

	private String eventName;

	private String serviceName;

	private String operationName;
	
	
	public PostCommand() {
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	
}
