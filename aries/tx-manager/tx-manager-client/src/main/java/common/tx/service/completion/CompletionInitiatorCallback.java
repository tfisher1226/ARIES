package common.tx.service.completion;

import org.aries.tx.util.Callback;
import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.model.Header;
import common.tx.model.Notification;


public abstract class CompletionInitiatorCallback extends Callback {

	public abstract void aborted(Header header, Notification notification);

    public abstract void committed(Header header, Notification notification);

    public abstract void fault(Header header, Fault fault);
    
}
