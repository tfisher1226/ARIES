package common.tx.service;

import common.tx.InstanceIdentifier;
import common.tx.model.Header;


public abstract class BaseNotificationProcessor extends BaseProcessor {
	
    protected String[] getIDs(Header header) {
        if (header != null) {
            InstanceIdentifier instanceIdentifier = header.getInstanceIdentifier();
            if (instanceIdentifier != null) {
                return new String[] {
                		instanceIdentifier.getInstanceIdentifier()
                };
            }
        }
        return null;
    }

}
