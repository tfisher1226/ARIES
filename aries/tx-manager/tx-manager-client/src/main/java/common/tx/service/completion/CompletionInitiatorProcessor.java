package common.tx.service.completion;

import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.model.Header;
import common.tx.model.Notification;


public interface CompletionInitiatorProcessor {

	public void registerCallback(String instanceIdentifier, CompletionInitiatorCallback callback);

	public void removeCallback(String instanceIdentifier);
	
	public void handleAborted(Header header, Notification notification);

	public void handleCommitted(Header header, Notification notification);

	public void handleFault(Header header, Fault fault);

}
