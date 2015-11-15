package common.tx.service.completion;

import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.model.Header;
import common.tx.model.Notification;


public class CompletionInitiatorCallbackImpl extends CompletionInitiatorCallback {
	
    private Fault fault;

    private boolean aborted;

    private boolean committed;


    public Fault getFault() {
        return fault;
    }

    public boolean receivedAborted() {
        return aborted;
    }

    public boolean receivedCommitted() {
        return committed;
    }

    public void aborted(Header header, Notification notification) {
        this.aborted = true;
    }

    public void committed(Header header, Notification notification) {
        this.committed  = true;
    }

    public void fault(Header header, Fault fault) {
        this.fault = fault;
    }
    
}
