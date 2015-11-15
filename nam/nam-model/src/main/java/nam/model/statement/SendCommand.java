package nam.model.statement;

import java.util.ArrayList;
import java.util.List;


public class SendCommand extends BlockStatement {

	private String messageName;

	private String serviceName;

	private String operationName;
	
	private List<String> returnMessages = new ArrayList<String>();
	
	
	public SendCommand() {
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
	
	public List<String> getReturnMessages() {
		return returnMessages;
	}

	public void setReturnMessages(List<String> returnMessages) {
		this.returnMessages = returnMessages;
	}
	
	public void addReturnMessages(String returnMessage) {
		this.returnMessages.add(returnMessage);
	}
	
}
