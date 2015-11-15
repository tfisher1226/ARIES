package nam.model.statement;

import nam.model.Command;


public class ReplyCommand extends Command {

	private String messageName;

	private String serviceName;

	private String operationName;
	
	private String processScope;
	
	
	public ReplyCommand() {
	}

	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
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

	public String getProcessScope() {
		return processScope;
	}

	public void setProcessScope(String processScope) {
		this.processScope = processScope;
	}
	
}
